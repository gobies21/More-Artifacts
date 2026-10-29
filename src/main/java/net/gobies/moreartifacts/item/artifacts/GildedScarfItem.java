package net.gobies.moreartifacts.item.artifacts;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.gobies.moreartifacts.compat.apothecary.ApothecaryCompat;
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

public class GildedScarfItem extends Item implements ICurioItem {
    public GildedScarfItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.RARE).fireResistant());
    }

    private static final UUID DAMAGE_RESISTANCE = UUID.fromString("e6b5f0e6-8ca5-4c67-8bb6-d3769d2e359d");
    private static final UUID DAMAGE_MULTIPLIER = UUID.fromString("29904fd7-a868-4618-bb11-00404ee1cfcc");

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = LinkedHashMultimap.create();
        if (ModLoadedUtil.isApothecaryLoaded()) {
            modifiers.put(ApothecaryCompat.damageResistance(), new AttributeModifier(uuid, String.valueOf(DAMAGE_RESISTANCE), CommonConfig.GILDED_DAMAGE_REDUCTION.get(), AttributeModifier.Operation.MULTIPLY_BASE));
            modifiers.put(ApothecaryCompat.damageMultiplier(), new AttributeModifier(uuid, String.valueOf(DAMAGE_MULTIPLIER), (CommonConfig.GILDED_DAMAGE_DEALT.get() - 1), AttributeModifier.Operation.MULTIPLY_BASE));
        }
        return modifiers;
    }

    @Override
    public boolean makesPiglinsNeutral(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.gilded_scarf.neutral").withStyle(ChatFormatting.YELLOW));
        if (!ModLoadedUtil.isApothecaryLoaded()) {
            double damageTaken = CommonConfig.GILDED_DAMAGE_REDUCTION.get() * 100;
            double damageDealt = (CommonConfig.GILDED_DAMAGE_DEALT.get() - 1) * 100;
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.gilded_scarf.damage_taken", MAUtils.formatDecimal(damageTaken)).withStyle(ChatFormatting.BLUE));
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.gilded_scarf.damage_dealt", MAUtils.formatDecimal(damageDealt)).withStyle(ChatFormatting.BLUE));
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}








