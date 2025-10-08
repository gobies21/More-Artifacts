package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.util.MAUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class MelodyPlushieItem extends Item implements ICurioItem {
    public MelodyPlushieItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.EPIC));
    }

    private static final UUID MAX_HEALTH = UUID.randomUUID();

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            if (!player.hasEffect(MobEffects.REGENERATION)) {
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, -1, CommonConfig.PLUSHIE_REGEN_LEVEL.get() - 1, false, false));
            }
        }
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            MAUtils.addAttributes(player, Attributes.MAX_HEALTH, CommonConfig.PLUSHIE_HEALTH.get(), AttributeModifier.Operation.MULTIPLY_BASE, String.valueOf(MAX_HEALTH));

        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            player.removeEffect(MobEffects.REGENERATION);
            MAUtils.removeAttributes(player, Attributes.MAX_HEALTH, String.valueOf(MAX_HEALTH));
            if (!player.isCreative() && !player.isSpectator()) {
                player.setHealth(player.getHealth() - 0.1F);
            }
        }
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        double maxHealth = CommonConfig.PLUSHIE_HEALTH.get() * 100;
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.melody_plushie.regen").withStyle(ChatFormatting.LIGHT_PURPLE));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.melody_plushie.health", String.format("%.1f", maxHealth)).withStyle(ChatFormatting.DARK_AQUA));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.melody_plushie.sleep").withStyle(ChatFormatting.DARK_AQUA));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}






