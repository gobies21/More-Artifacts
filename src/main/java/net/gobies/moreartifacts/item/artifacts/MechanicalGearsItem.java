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

public class MechanicalGearsItem extends Item implements ICurioItem {
    public MechanicalGearsItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.UNCOMMON));
    }

    private static final UUID SPEED = UUID.fromString("42784154-719a-4d50-a2e5-9063aab38a5e");

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = LinkedHashMultimap.create();
        modifiers.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, String.valueOf(SPEED), CommonConfig.MECHANICAL_GEARS_SPEED.get(), AttributeModifier.Operation.MULTIPLY_BASE));
        return modifiers;
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        double dodgeChance = CommonConfig.MECHANICAL_GEARS_DODGE_CHANCE.get() * 100;
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.mechanical_gears.evade", String.format("%.0f", dodgeChance)).withStyle(ChatFormatting.BLUE));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.mechanical_gears.dodge").withStyle(ChatFormatting.GRAY));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
