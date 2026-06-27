package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.util.CurioHandler;
import net.gobies.moreartifacts.util.MAUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.*;

public class AnkhShieldItem extends ShieldItem implements ICurioItem {
    public AnkhShieldItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.EPIC).fireResistant().durability(1500));
    }

    private static final UUID KNOCKBACK_RESISTANCE = UUID.fromString("29d403be-8c13-482a-8b36-9b2921dacf62");

    public static void additionalEffectImmunity(MobEffectEvent.Applicable event) {
        if (CommonConfig.ANKH_SHIELD_ADDITIONAL_EFFECTS.get().isEmpty()) return;
        MobEffectInstance effects = event.getEffectInstance();
        List<MobEffectInstance> effectsToUpdate = new ArrayList<>(List.of(effects));
        for (MobEffectInstance effectInstance : effectsToUpdate) {
            MobEffect effect = effectInstance.getEffect();
            ResourceLocation effectId = ForgeRegistries.MOB_EFFECTS.getKey(effect);
            if (effectId != null && CommonConfig.ANKH_SHIELD_ADDITIONAL_EFFECTS.get().contains(effectId.toString())) {
                event.setResult(MobEffectEvent.Result.DENY);
                return;
            }
        }
    }

    public static void removeAdditionalEffects(Player player) {
        if (player.getActiveEffects().isEmpty()) return;
        List<? extends String> configList = CommonConfig.ANKH_SHIELD_ADDITIONAL_EFFECTS.get();
        if (configList.isEmpty()) return;

        Set<String> configSet = new HashSet<>(configList);

        List<MobEffectInstance> activeEffects = new ArrayList<>(player.getActiveEffects());
        for (MobEffectInstance instance : activeEffects) {
            MobEffect effect = instance.getEffect();
            ResourceLocation effectId = ForgeRegistries.MOB_EFFECTS.getKey(effect);

            if (effectId != null && configSet.contains(effectId.toString())) {
                player.removeEffect(effect);
            }
        }
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            if (CurioHandler.isCurioEquipped(player, MAItems.AnkhShield.get())) {
                MAUtils.removeHarmfulEffects(player);
                removeAdditionalEffects(player);
            }
        }
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            MAUtils.addAttributes(player, Attributes.KNOCKBACK_RESISTANCE, 1.0, AttributeModifier.Operation.ADDITION, String.valueOf(KNOCKBACK_RESISTANCE));
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            MAUtils.removeAttributes(player, Attributes.KNOCKBACK_RESISTANCE, String.valueOf(KNOCKBACK_RESISTANCE));
        }
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, @NotNull ItemStack repair) {
        return toRepair.getItem() == this && repair.getItem() == Items.OBSIDIAN;
    }

    @Override
    public boolean isEnchantable(@NotNull ItemStack pStack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        double fireDamageReduction = (1.0 - CommonConfig.ANKH_SHIELD_FIRE_DAMAGE_REDUCTION.get()) * 100;
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.ankh_shield.immunity").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.ankh_shield.immunity_debuff").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.ankh_shield.fire_damage", String.format("%.1f", fireDamageReduction)).withStyle(ChatFormatting.DARK_AQUA));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.hold.ctrl").withStyle(ChatFormatting.GRAY));
        if (Screen.hasControlDown()) {
            pTooltipComponents.remove(4);
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.normal.shield").withStyle(ChatFormatting.GRAY));
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.shield.obsidian").withStyle(ChatFormatting.GRAY));
            super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        }
    }
}