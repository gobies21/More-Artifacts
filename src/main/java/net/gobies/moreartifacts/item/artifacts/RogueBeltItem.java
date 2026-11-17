package net.gobies.moreartifacts.item.artifacts;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.gobies.moreartifacts.compat.betterstealth.BetterStealthCompat;
import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.util.MAUtils;
import net.gobies.moreartifacts.util.ModLoadedUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
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

public class RogueBeltItem extends Item implements ICurioItem {
    public RogueBeltItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.RARE));
    }

    private static final UUID STEALTH = UUID.fromString("13c64883-b097-4034-9b86-0661d9319215");

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = LinkedHashMultimap.create();
        if (ModLoadedUtil.isBetterStealthLoaded()) {
            modifiers.put(BetterStealthCompat.stealthAttribute(), new AttributeModifier(uuid, String.valueOf(STEALTH), 0.2, AttributeModifier.Operation.ADDITION));
        }
        return modifiers;
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        float dodgeDistance = (CommonConfig.RED_BELT_DODGE_DISTANCE.get().floatValue() - 1) * 100;
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.rogue_belt.dodge_distance", MAUtils.formatDecimal(dodgeDistance)).withStyle(ChatFormatting.BLUE));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.stealth_manual.sneak").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.red_belt.invul").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.stealth_manual.cobweb").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.rogue_belt.air_dodge").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}








