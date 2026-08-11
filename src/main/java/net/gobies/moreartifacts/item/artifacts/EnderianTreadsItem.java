package net.gobies.moreartifacts.item.artifacts;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.gobies.moreartifacts.config.CommonConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.UUID;

public class EnderianTreadsItem extends Item implements ICurioItem {
    public EnderianTreadsItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.EPIC));
    }

    private static final UUID SPEED = UUID.fromString("07c0b340-7cc3-4bca-918f-2a9927cc4785");

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = LinkedHashMultimap.create();
        modifiers.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, String.valueOf(SPEED), CommonConfig.ENDERIAN_TREADS_SPEED.get(), AttributeModifier.Operation.MULTIPLY_BASE));
        return modifiers;
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.enderian_treads.blink").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
