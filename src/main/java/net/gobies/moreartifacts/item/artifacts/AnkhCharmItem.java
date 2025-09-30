package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.util.MAUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class AnkhCharmItem extends Item implements ICurioItem {
    public AnkhCharmItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.EPIC));
    }

    public static void additionalEffectImmunity(MobEffectEvent.Applicable event) {
        MobEffectInstance effects = event.getEffectInstance();
        List<MobEffectInstance> effectsToUpdate = new ArrayList<>(List.of(effects));
        for (MobEffectInstance effectInstance : effectsToUpdate) {
            MobEffect effect = effectInstance.getEffect();
            ResourceLocation effectId = ForgeRegistries.MOB_EFFECTS.getKey(effect);
            if (effectId != null && CommonConfig.ANKH_CHARM_ADDITIONAL_EFFECTS.get().contains(effectId.toString())) {
                event.setResult(MobEffectEvent.Result.DENY);
            }
        }
    }

    public static void removeAdditionalEffects(Player player) {
        for (MobEffect effect : ForgeRegistries.MOB_EFFECTS) {
            ResourceLocation effectId = ForgeRegistries.MOB_EFFECTS.getKey(effect);
            if (effectId != null && CommonConfig.ANKH_CHARM_ADDITIONAL_EFFECTS.get().contains(effectId.toString())) {
                if (player.hasEffect(effect)) {
                    player.removeEffect(effect);
                }
            }
        }
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            MAUtils.removeHarmfulEffects(player);
            removeAdditionalEffects(player);
        }
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.ankh_charm").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}



