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
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class VanirMaskItem extends Item implements ICurioItem {
    private static final UUID ARMOR = UUID.fromString("fff1f665-7b16-4011-9ae1-14086c9e6986");
    private static final UUID ARMOR_TOUGHNESS = UUID.fromString("5da834f0-8540-4090-8449-7e1e9fc08c84");
    private static final UUID MAX_HEALTH = UUID.fromString("4f2fea41-36be-4394-9a54-7ff422581455");
    private static final UUID MOVEMENT_SPEED = UUID.fromString("06e082d0-5670-4fc3-be34-0b6ddc8b84d7");

    public VanirMaskItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.EPIC));
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = LinkedHashMultimap.create();
        modifiers.put(Attributes.ARMOR, new AttributeModifier(uuid, String.valueOf(ARMOR), CommonConfig.VANIR_MASK_ARMOR_INCREASE.get(), AttributeModifier.Operation.MULTIPLY_BASE));
        modifiers.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, String.valueOf(ARMOR_TOUGHNESS), CommonConfig.VANIR_MASK_ARMOR_TOUGHNESS_INCREASE.get(), AttributeModifier.Operation.MULTIPLY_BASE));
        modifiers.put(Attributes.MAX_HEALTH, new AttributeModifier(uuid, String.valueOf(MAX_HEALTH), CommonConfig.VANIR_MASK_HEALTH_INCREASE.get(), AttributeModifier.Operation.MULTIPLY_BASE));
        modifiers.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, String.valueOf(MOVEMENT_SPEED), CommonConfig.VANIR_MASK_SPEED_INCREASE.get(), AttributeModifier.Operation.MULTIPLY_BASE));
        return modifiers;
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        double damageIncrease = CommonConfig.VANIR_MASK_DAMAGE_INCREASE.get();
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.vanir.mask.bloodflow").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.vanir_mask.damage", String.format("%.0f", damageIncrease)).withStyle(ChatFormatting.BLUE));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}










