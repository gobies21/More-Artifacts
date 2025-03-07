package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class TrueEnderianScarfItem extends Item implements ICurioItem {
    public TrueEnderianScarfItem(Properties properties) {
        super(new Properties().stacksTo(1).rarity(Rarity.COMMON));
    }

    public boolean isEnderMask(SlotContext slotContext, EnderMan enderMan, ItemStack stack) {
        LivingEntity var5 = slotContext.entity();
        if (var5 instanceof Player && stack.getItem() instanceof TrueEnderianScarfItem) {
            return true; // Make Endermen neutral when the TrueEnderianScarfItem is equipped
        } else {
            return false;
        }
    }
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player player) {
            CuriosApi.getCuriosHelper().findEquippedCurio(ModItems.TrueEnderianScarf.get(), player).ifPresent((slot) -> {
                event.setAmount(event.getAmount() * 0.90f);
            });
        }
    }

    private static final UUID ENTITY_REACH_UUID = UUID.randomUUID();
    private static final UUID BLOCK_REACH_UUID = UUID.randomUUID();

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity livingEntity = slotContext.entity();
        if (livingEntity instanceof Player entity) {
            var attribute = entity.getAttribute(ForgeMod.ENTITY_REACH.get());
            var attribute2 = entity.getAttribute(ForgeMod.BLOCK_REACH.get());
            if (attribute != null) {
                if (attribute2 != null) {
                    if (attribute.getModifier(ENTITY_REACH_UUID) == null && stack.getItem() instanceof TrueEnderianScarfItem) {
                        if (attribute2.getModifier(BLOCK_REACH_UUID) == null && stack.getItem() instanceof TrueEnderianScarfItem) {
                            attribute.addTransientModifier(
                                    new AttributeModifier(ENTITY_REACH_UUID, "True Scarf Entity Reach", 1.0, AttributeModifier.Operation.ADDITION));
                            attribute2.addTransientModifier(
                                    new AttributeModifier(BLOCK_REACH_UUID, "True Scarf Block Reach", 1.0, AttributeModifier.Operation.ADDITION));
                        }
                    }
                }
            }
        }
    }
    public void onUnequip(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        if (livingEntity instanceof Player entity) {
            var entityReach = entity.getAttribute(ForgeMod.ENTITY_REACH.get());
            var blockReach = entity.getAttribute(ForgeMod.BLOCK_REACH.get());
            if (entityReach != null) {
                entityReach.removeModifier(ENTITY_REACH_UUID);
            }
            if (blockReach != null) {
                blockReach.removeModifier(BLOCK_REACH_UUID);
            }
        }
    }

    static {
        MinecraftForge.EVENT_BUS.register(TrueEnderianScarfItem.class);
    }

    @SubscribeEvent
    public static void onEntityAttacked(LivingAttackEvent event) {
        if (event.getEntity() instanceof Player player) {
            // Check if the player is wearing the TrueEnderianScarf
            CuriosApi.getCuriosHelper().findEquippedCurio(ModItems.TrueEnderianScarf.get(), player).ifPresent((slot) -> {
                RandomSource random = player.getRandom();
                if (random.nextFloat() < 0.1f) {
                    Level level = player.level();
                    double randomX = player.getX() + (level.random.nextDouble() * 8 - 4);
                    double randomZ = player.getZ() + (level.random.nextDouble() * 8 - 4);
                    double groundY = level.getHeight(net.minecraft.world.level.levelgen.Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (int) randomX, (int) randomZ);

                    // Check if teleport target is valid (block below is solid and target block is empty)
                    if (level.getBlockState(new BlockPos((int) randomX, (int) (groundY - 1), (int) randomZ)).isSolid() &&
                            level.isEmptyBlock(new BlockPos((int) randomX, (int) groundY, (int) randomZ))) {
                        if (player.isPassenger()) {
                            player.stopRiding();
                        }

                        // Spawn portal particles at player's original position
                        for (int i = 0; i < 15; i++) {
                            player.level().addParticle(
                                    net.minecraft.core.particles.ParticleTypes.PORTAL,
                                    player.getX(),
                                    player.getY() + player.getEyeHeight(),
                                    player.getZ(),
                                    (level.random.nextDouble() - 0.5) * 2.0,
                                    level.random.nextDouble() - 0.5,
                                    (level.random.nextDouble() - 0.5) * 2.0
                            );
                        }

                        player.teleportTo(randomX, groundY, randomZ); // Teleport player to the ground
                        player.level().playSound(null, player.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, player.getSoundSource(), 1.0F, 1.0F);

                        // Spawn portal particles at player's teleportation position
                        for (int i = 0; i < 15; i++) {
                            player.level().addParticle(
                                    net.minecraft.core.particles.ParticleTypes.PORTAL,
                                    player.getX(),
                                    player.getY() + player.getEyeHeight(),
                                    player.getZ(),
                                    (level.random.nextDouble() - 0.5) * 2.0,
                                    level.random.nextDouble() - 0.5,
                                    (level.random.nextDouble() - 0.5) * 2.0
                            );
                        }

                        event.setCanceled(true); // Ignore the damage
                    }
                }
            });
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("§5Endermen are neutral"));
        pTooltipComponents.add(Component.literal("§6Reduces damage taken by §310.0%"));
        pTooltipComponents.add(Component.literal("§310.0% §6Chance to evade an attack"));
        pTooltipComponents.add(Component.literal("§6Increases reach by §31.0"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}







