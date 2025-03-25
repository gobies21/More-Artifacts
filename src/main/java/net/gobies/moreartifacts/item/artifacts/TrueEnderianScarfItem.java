package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.Config;
import net.gobies.moreartifacts.item.ModItems;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.KeyboardInput;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
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
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;


public class TrueEnderianScarfItem extends Item implements ICurioItem {
    public TrueEnderianScarfItem(Properties properties) {
        super(new Properties().stacksTo(1).rarity(Rarity.RARE));
    }

    public boolean isEnderMask(SlotContext slotContext, EnderMan enderMan, ItemStack stack) {
        LivingEntity var5 = slotContext.entity();
        if (var5 instanceof Player && stack.getItem() instanceof TrueEnderianScarfItem) {
            return true;
        } else {
            return false;
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player player) {
            CuriosApi.getCuriosHelper().findEquippedCurio(ModItems.TrueEnderianScarf.get(), player).ifPresent((slot) -> {
                event.setAmount((float) (event.getAmount() * Config.TRUE_ENDERIAN_DAMAGE_TAKEN.get()));
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
                                    new AttributeModifier(ENTITY_REACH_UUID, "True Scarf Entity Reach", Config.TRUE_ENDERIAN_REACH.get(), AttributeModifier.Operation.ADDITION));
                            attribute2.addTransientModifier(
                                    new AttributeModifier(BLOCK_REACH_UUID, "True Scarf Block Reach", Config.TRUE_ENDERIAN_REACH.get(), AttributeModifier.Operation.ADDITION));
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

    //Needs refining

    @SubscribeEvent
    public static void onEntityAttacked(LivingAttackEvent event) {
        if (event.getEntity() instanceof Player player && event.getSource().getEntity() != null) {
            CuriosApi.getCuriosHelper().findEquippedCurio(ModItems.TrueEnderianScarf.get(), player).ifPresent((slot) -> {
                RandomSource playerRandom = player.getRandom();
                if (playerRandom.nextFloat() >= Config.TRUE_ENDERIAN_EVADE.get()) {
                    return;
                }
                Level level = player.level();
                if (!(level instanceof ServerLevel)) {
                    return;
                }
                event.setCanceled(true);
                LivingEntity livingEntity = event.getEntity();
                double d0 = livingEntity.getX();
                double d1 = livingEntity.getY();
                double d2 = livingEntity.getZ();
                double maxRadius = 4d;
                Level entityLevel = livingEntity.level();
                var entityRandom = livingEntity.getRandom();

                for (int i = 0; i < 12; ++i) {
                    var minRadius = maxRadius / 2;
                    Vec3 vec = new Vec3((double) entityRandom.nextInt((int) minRadius, (int) maxRadius), 0, 0);
                    int degrees = entityRandom.nextInt(360);
                    vec = vec.yRot(degrees * Mth.DEG_TO_RAD);

                    double x = d0 + vec.x;
                    if (level instanceof ServerLevel serverLevel) {
                        double y = Mth.clamp(livingEntity.getY() + (double) (livingEntity.getRandom().nextInt((int) maxRadius) - maxRadius / 2), (double) level.getMinBuildHeight(), (double) (level.getMinBuildHeight() + serverLevel.getLogicalHeight() - 1));
                        double z = d2 + vec.z;

                        if (livingEntity.isPassenger()) {
                            livingEntity.stopRiding();
                        }

                        if (livingEntity.randomTeleport(x, y, z, true)) {
                            if (event.getSource().getEntity() != null) {
                                livingEntity.lookAt(EntityAnchorArgument.Anchor.EYES, event.getSource().getEntity().getEyePosition());
                            }
                            player.level().playSound(null, player.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, player.getSoundSource(), 1.0F, 1.0F);
                            livingEntity.playSound(SoundEvents.ENDERMAN_TELEPORT, 2.0F, 1.0F);
                            break;
                        }

                        if (maxRadius > 2) {
                            maxRadius--;
                        }
                    }
                }

            });
        }
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("§5Endermen are neutral"));
        pTooltipComponents.add(Component.literal(String.format("§6Reduces damage taken by §3%.1f%%", (100 - Config.TRUE_ENDERIAN_DAMAGE_TAKEN.get() * 100))));
        pTooltipComponents.add(Component.literal(String.format("§3%.1f%% §6Chance to evade an attack", Config.TRUE_ENDERIAN_EVADE.get() * 100)));
        pTooltipComponents.add(Component.literal(String.format("§6Increases reach by §3%.1f", Config.TRUE_ENDERIAN_REACH.get())));
        if (ModList.get().isLoaded("enhancedvisuals")) {
            pTooltipComponents.add(Component.literal("§8<Hold Ctrl>"));
            if (Screen.hasControlDown()) {
                pTooltipComponents.remove(5);
                pTooltipComponents.add(Component.literal("§7Disables Endermen static §6(Enhanced Visuals)"));
            }
            super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        }
    }
}




