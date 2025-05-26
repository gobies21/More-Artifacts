package net.gobies.moreartifacts.util;

import net.gobies.moreartifacts.Config;
import net.gobies.moreartifacts.item.artifacts.EnderianEyeItem;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Map;

public class Teleport {
    private static final Map<Entity, Long> cooldownMap = new HashMap<>();

    //not perfect, but it works for now
    public static Vec3 solveTeleportDestination(Level level, LivingEntity entity, BlockPos blockPos, Vec3 vec3) {
        Vec3 start = entity.getEyePosition(1f);
        Vec3 direction = entity.getViewVector(1f);
        double distance = Math.min(Config.ENDERIAN_EYE_RADIUS.get(), entity.getEyePosition(1f).distanceTo(vec3));
        Vec3 end = start.add(direction.scale(distance));

        HitResult hitResult = level.clip(new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity));
        Vec3 hitVec = hitResult.getLocation();

        if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos hitPos = BlockPos.containing(hitVec);
            Vec3 adjustedPos = hitVec.subtract(direction.scale(0.2)); // step back from hit surface
            BlockPos standPos = BlockPos.containing(adjustedPos);

            // if looking at block side (not top), try to teleport on top
            if (Math.abs(direction.y) < 0.9) { // not looking straight up/down
                BlockPos topPos = hitPos.above();
                if (level.getBlockState(topPos).isAir() &&
                        level.getBlockState(topPos.above()).isAir() &&
                        !level.getBlockState(hitPos).isAir()) {
                    return new Vec3(hitPos.getX() + 0.5, topPos.getY(), hitPos.getZ() + 0.5);
                }
            }

            // check for safe standing position
            if (!level.getBlockState(standPos.below()).isAir()) {
                if (level.getBlockState(standPos).isAir() &&
                        level.getBlockState(standPos.above()).isAir()) {
                    return new Vec3(adjustedPos.x, standPos.getY(), adjustedPos.z);
                }
            }
        } else {
            BlockPos groundPos = BlockPos.containing(hitVec);
            int maxSearchDepth = 5;
            int searchDepth = 0;

            while (groundPos.getY() > level.getMinBuildHeight() &&
                    level.getBlockState(groundPos).isAir() &&
                    searchDepth < maxSearchDepth) {
                groundPos = groundPos.below();
                searchDepth++;
            }

            if (searchDepth < maxSearchDepth &&
                    !level.getBlockState(groundPos).isAir() &&
                    level.getBlockState(groundPos.above()).isAir() &&
                    level.getBlockState(groundPos.above(2)).isAir()) {
                return new Vec3(hitVec.x, groundPos.getY() + 1.0, hitVec.z);
            }
        }

        Vec3 fallbackPos = hitVec.subtract(direction.scale(0.3));
        return new Vec3(fallbackPos.x, fallbackPos.y, fallbackPos.z);
    }

    // method to handle the teleportation
    public static void teleportPlayer(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null) return;

        // define the start and end points of the raycast
        Vec3 start = entity.getEyePosition(1f);
        Vec3 end = start.add(entity.getViewVector(1f).scale(Config.ENDERIAN_EYE_RADIUS.get()));

        // solve teleport destination
        Vec3 targetPosition = solveTeleportDestination((Level) world, (LivingEntity) entity, new BlockPos((int) x, (int) y, (int) z), end);
        long currentTime = System.currentTimeMillis();
        long lastTeleportTime = cooldownMap.getOrDefault(entity, 0L);

        if (currentTime - lastTeleportTime >= 1000 * Config.ENDERIAN_EYE_COOLDOWN.get()) {

            // teleport the entity to the hit position
            entity.teleportTo(targetPosition.x, targetPosition.y, targetPosition.z);

            // if the entity is a ServerPlayer, update its connection to reflect the new position
            if (entity instanceof ServerPlayer serverPlayer) {
                serverPlayer.connection.teleport(targetPosition.x, targetPosition.y, targetPosition.z, entity.getYRot(), entity.getXRot());
                cooldownMap.put(entity, currentTime);
                if (world instanceof Level level) {
                    if (!level.isClientSide()) {
                        level.playSound(null, targetPosition.x, targetPosition.y, targetPosition.z, SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
                        level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 2.0F, 1.0F);
                        EnderianEyeItem.enderianEyeParticles((Player) entity, Teleport.solveTeleportDestination(level, (LivingEntity) entity, entity.blockPosition(), entity.getEyePosition(1f)));

                    }
                }
            }
        }
    }
}
