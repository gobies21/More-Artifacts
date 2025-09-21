package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.util.CurioHandler;
import net.gobies.moreartifacts.util.MAUtils;
import net.gobies.moreartifacts.util.ModLoadedUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
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
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class TrueEnderianScarfItem extends Item implements ICurioItem {
    public TrueEnderianScarfItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.RARE));
    }

    static {
        MinecraftForge.EVENT_BUS.register(TrueEnderianScarfItem.class);
    }

    private static final UUID ENTITY_REACH = UUID.randomUUID();
    private static final UUID BLOCK_REACH = UUID.randomUUID();

    @Override
    public boolean isEnderMask(SlotContext slotContext, EnderMan enderMan, ItemStack stack) {
        LivingEntity var5 = slotContext.entity();
        return var5 instanceof Player && stack.getItem() instanceof TrueEnderianScarfItem;
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            MAUtils.addAttributes(player, ForgeMod.ENTITY_REACH.get(), CommonConfig.TRUE_ENDERIAN_REACH.get(), AttributeModifier.Operation.ADDITION, String.valueOf(ENTITY_REACH));
            MAUtils.addAttributes(player, ForgeMod.BLOCK_REACH.get(), CommonConfig.TRUE_ENDERIAN_REACH.get(), AttributeModifier.Operation.ADDITION, String.valueOf(BLOCK_REACH));
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            MAUtils.removeAttributes(player, ForgeMod.ENTITY_REACH.get(), String.valueOf(ENTITY_REACH));
            MAUtils.removeAttributes(player, ForgeMod.BLOCK_REACH.get(), String.valueOf(BLOCK_REACH));
        }
    }

    @SubscribeEvent
    public static void onEntityAttacked(LivingAttackEvent event) {
        if (event.getEntity() instanceof Player player && event.getSource().getEntity() != null && !event.isCanceled()) {
            if (CurioHandler.isCurioEquipped(player, MAItems.TrueEnderianScarf.get())) {
                if (!player.isCreative() || !player.isSpectator()) {
                    RandomSource playerRandom = player.getRandom();
                    if (playerRandom.nextFloat() >= CommonConfig.TRUE_ENDERIAN_EVADE.get()) {
                        return;
                    }
                    Level level = player.level();
                    if (!(level instanceof ServerLevel)) {
                        return;
                    }
                    event.setCanceled(true);
                    LivingEntity livingEntity = event.getEntity();
                    double d0 = livingEntity.getX();
                    double d2 = livingEntity.getZ();
                    double maxRadius = 4d;
                    var entityRandom = livingEntity.getRandom();

                    for (int i = 0; i < 12; ++i) {
                        var minRadius = maxRadius / 2;
                        Vec3 vec = new Vec3((entityRandom.nextInt((int) minRadius, (int) maxRadius)), 0, 0);
                        int degrees = entityRandom.nextInt(360);
                        vec = vec.yRot(degrees * Mth.DEG_TO_RAD);

                        double x = d0 + vec.x;
                        if (level instanceof ServerLevel serverLevel) {
                            double y = Mth.clamp(livingEntity.getY() + (livingEntity.getRandom().nextInt((int) maxRadius) - maxRadius / 2), level.getMinBuildHeight(), (level.getMinBuildHeight() + serverLevel.getLogicalHeight() - 1));
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
                }
            }
        }
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        double scarfDamageTaken = (100 - CommonConfig.TRUE_ENDERIAN_DAMAGE_TAKEN.get() * 100);
        double scarfEvadeChance = CommonConfig.TRUE_ENDERIAN_EVADE.get() * 100;
        double scarfReach = CommonConfig.TRUE_ENDERIAN_REACH.get();
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.true_enderian_scarf.neutral").withStyle(ChatFormatting.LIGHT_PURPLE));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.true_enderian_scarf.damage_taken", String.format("%.1f", scarfDamageTaken)).withStyle(ChatFormatting.DARK_AQUA));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.true_enderian_scarf.evade_chance", String.format("%.1f", scarfEvadeChance)).withStyle(ChatFormatting.DARK_AQUA));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.true_enderian_scarf.reach", scarfReach).withStyle(ChatFormatting.DARK_AQUA));
        if (ModLoadedUtil.isEnhancedVisualsLoaded() && (CommonConfig.TRUE_ENDERIAN_COMPAT.get())) {
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.hold.ctrl"));
            if (Screen.hasControlDown()) {
                pTooltipComponents.remove(5);
                pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.true_enderian_scarf.enhanced_visuals").withStyle(ChatFormatting.GRAY));
            }
            super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        }
    }
}




