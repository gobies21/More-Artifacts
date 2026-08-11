package net.gobies.moreartifacts.util;

import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.init.MAItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import top.theillusivec4.curios.api.CuriosApi;

public record DodgeProperties(double distance, double lift, double cooldown, boolean blink) {
    public DodgeProperties(double distance, double lift, double cooldown) {
        this(distance, lift, cooldown, false);
    }

    // Functionality and util methods of dodge mechanics
    public static String DODGE_COOLDOWN = "dodge_cooldown";

    public static DodgeProperties getDodgeProperties(Item item) {
        if (item == MAItems.MechanicalGears.get()) return new DodgeProperties(1.0, 0.2, 2.0);
        if (item == MAItems.HighJumpers.get()) return new DodgeProperties(1.2, 0.5, 2.0);
        if (item == MAItems.BlazingTreads.get()) return new DodgeProperties(1.2, 0.2, 2.0);
        if (item == MAItems.SculkTreads.get()) return new DodgeProperties(1.5, 0.2, 2.0);
        if (item == MAItems.EnderianTreads.get()) return new DodgeProperties(1.5, 0.0, CommonConfig.ENDERIAN_TREADS_COOLDOWN.get(), true);
        return null;
    }

    public static ItemStack getDodgeItem(LivingEntity entity) {
        var inventory = CuriosApi.getCuriosInventory(entity);
        if (!inventory.isPresent()) return ItemStack.EMPTY;

        for (var category : inventory.orElseThrow(IllegalStateException::new).getCurios().values()) {
            var slots = category.getStacks();
            for (int i = 0; i < slots.getSlots(); i++) {
                ItemStack stack = slots.getStackInSlot(i);

                if (!stack.isEmpty() && getDodgeProperties(stack.getItem()) != null) {
                    return stack;
                }
            }
        }
        return ItemStack.EMPTY;
    }

    public static double getDistance(ItemStack stack, LivingEntity entity) {
        DodgeProperties properties = getDodgeProperties(stack.getItem());
        if (properties == null) return 1.5;

        double distance = properties.distance;

        if (properties.blink && CommonConfig.ENABLE_AIR_DODGE.get()) {
            if (entity.isSprinting()) {
                distance -= 0.30;
            }
            if (!entity.onGround()) {
                distance -= 0.20;
            }
        }

        if (CurioHandler.isCurioEquipped(entity, MAItems.StealthManual.get())) {
            distance *= 1.20;
        }
        return Math.max(0.2, distance);
    }

    public static double getLift(ItemStack stack) {
        DodgeProperties properties = getDodgeProperties(stack.getItem());
        return properties != null ? properties.lift : 0.0;
    }

    public static double getDodgeCooldown(ItemStack stack) {
        DodgeProperties properties = getDodgeProperties(stack.getItem());
        return properties != null ? properties.cooldown : 1.5;
    }

    public static int getInvulnerableFrames(LivingEntity entity) {
        return 5;
    }

    public static boolean isBlink(ItemStack stack) {
        DodgeProperties properties = getDodgeProperties(stack.getItem());
        return properties != null && properties.blink;
    }

    public static void executeDodge(ServerPlayer player, int direction, double distance, double lift, boolean blink) {
        player.invulnerableTime += getInvulnerableFrames(player);
        float finalYaw = player.getYRot();
        if (player.isPassenger()) return;
        switch (direction) {
            case 1 -> finalYaw -= 90.0F;  // Left
            case 2 -> finalYaw += 180.0F; // Backward
            case 3 -> finalYaw += 90.0F;  // Right
        }

        double radians = Math.toRadians(finalYaw);
        double motionX = -Math.sin(radians);
        double motionZ = Math.cos(radians);

        ServerLevel world = player.serverLevel();
        if (blink) {
            Vec3 originalPosition = player.position();

            world.playSound(null, player.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);

            double teleportDistance = distance * 4.0;

            double finalX = player.getX();
            double finalY = player.getY();
            double finalZ = player.getZ();

            for (int step = 1; step <= (int) teleportDistance; step++) {
                double nextX = player.getX() + (motionX * step);
                double nextZ = player.getZ() + (motionZ * step);

                BlockPos basePos = new BlockPos((int) Math.floor(nextX), player.blockPosition().getY(), (int) Math.floor(nextZ));

                boolean safeDodge = false;
                double matchedY = finalY;

                for (int yOffset = -3; yOffset <= 3; yOffset++) {
                    BlockPos feet = basePos.above(yOffset);
                    BlockPos head = feet.above();
                    BlockPos ground = feet.below();

                    boolean hasGround = !world.getBlockState(ground).getCollisionShape(world, ground).isEmpty();
                    boolean feetClear = world.getBlockState(feet).getCollisionShape(world, feet).isEmpty();
                    boolean headClear = world.getBlockState(head).getCollisionShape(world, head).isEmpty();

                    if (hasGround && feetClear && headClear) {
                        matchedY = feet.getY();
                        safeDodge = true;
                        break;
                    }
                }

                if (!safeDodge) {
                    break;
                }

                finalX = nextX;
                finalY = matchedY;
                finalZ = nextZ;
            }

            player.teleportTo(finalX, finalY, finalZ);
            player.resetFallDistance();

            player.playSound(SoundEvents.ENDERMAN_TELEPORT, 2.0F, 1.0F);
            MAUtils.spawnPortalParticles(world, originalPosition);
            MAUtils.spawnPortalParticles(world, player.position());
        } else {
            float randomPitch = 1.2F + player.getRandom().nextFloat() * 0.1F;
            world.playSound(null, player.blockPosition(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 1.0F, randomPitch);

            double verticalVelocity = lift;
            if (lift <= 0) {
                verticalVelocity = player.getDeltaMovement().y;
            }

            double dashX = motionX * distance;
            double dashZ = motionZ * distance;
            Vec3 newVelocity = new Vec3(dashX, verticalVelocity, dashZ);

            player.setDeltaMovement(newVelocity);
            player.hurtMarked = true;
        }
    }
}
