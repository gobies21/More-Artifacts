package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.util.ModLoadedUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import javax.annotation.Nullable;
import java.util.List;

public class HeroShieldItem extends Item implements ICurioItem {
    public HeroShieldItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.EPIC).fireResistant());
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            MobEffect effect = MobEffects.DAMAGE_RESISTANCE;
            MobEffectInstance mobEffect = player.getEffect(effect);
            if (mobEffect == null || mobEffect.getDuration() <= 110) {
                player.addEffect(new MobEffectInstance(effect, 210, CommonConfig.HERO_SHIELD_RES_LEVEL.get() - 1, true, false));
            }
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            player.removeEffect(MobEffects.DAMAGE_RESISTANCE);
        }
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        CompoundTag tag = pStack.getTag();
        int hitCount = tag != null ? tag.getInt("HitCount") : 0;
        int ignoreDamage = CommonConfig.IGNORE_DAMAGE_CHANCE.get();
        double explosionDamage = CommonConfig.EXPLOSION_DAMAGE_REDUCTION.get() * 100;
        double ignoreChance = CommonConfig.FIRST_AID_IGNORE_CHANCE.get() * 100;
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.hero_shield.resistance").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.hero_shield.damage_ignore", ignoreDamage, hitCount, ignoreDamage).withStyle(ChatFormatting.BLUE));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.hero_shield.explosion_damage", String.format("%.0f", explosionDamage)).withStyle(ChatFormatting.BLUE));
        if (ModLoadedUtil.isEnhancedVisualsLoaded() && CommonConfig.HERO_SHIELD_ENHANCED_VISUALS_COMPAT.get() || ModLoadedUtil.isFirstAidLoaded() && CommonConfig.HERO_SHIELD_FIRST_AID_COMPAT.get() || ModLoadedUtil.isApothecaryLoaded()) {
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.hold.ctrl"));
            if (Screen.hasControlDown()) {
                pTooltipComponents.remove(4);
                if (ModLoadedUtil.isFirstAidLoaded() && CommonConfig.HERO_SHIELD_FIRST_AID_COMPAT.get()) {
                    pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.hero_shield.first_aid", String.format("%.0f", ignoreChance)).withStyle(ChatFormatting.BLUE));
                }
                if (ModLoadedUtil.isApothecaryLoaded()) {
                    pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.hero_shield.apothecary").withStyle(ChatFormatting.GRAY));
                }
                if (ModLoadedUtil.isEnhancedVisualsLoaded() && CommonConfig.HERO_SHIELD_ENHANCED_VISUALS_COMPAT.get()) {
                    pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.hero_shield.enhanced_visuals").withStyle(ChatFormatting.GRAY));
                }
            }
            super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        }
    }
}




