package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.util.MAUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
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

public class VanirMaskItem extends Item implements ICurioItem {
    private static final UUID ARMOR = UUID.fromString("fff1f665-7b16-4011-9ae1-14086c9e6986");
    private static final UUID ARMOR_TOUGHNESS = UUID.fromString("5da834f0-8540-4090-8449-7e1e9fc08c84");
    private static final UUID MAX_HEALTH = UUID.fromString("4f2fea41-36be-4394-9a54-7ff422581455");
    private static final UUID MOVEMENT_SPEED = UUID.fromString("06e082d0-5670-4fc3-be34-0b6ddc8b84d7");

    public VanirMaskItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.EPIC));
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            MAUtils.addAttributes(player, Attributes.ARMOR, CommonConfig.VANIR_MASK_ARMOR_INCREASE.get(), AttributeModifier.Operation.MULTIPLY_BASE, String.valueOf(ARMOR));
            MAUtils.addAttributes(player, Attributes.ARMOR_TOUGHNESS, CommonConfig.VANIR_MASK_ARMOR_TOUGHNESS_INCREASE.get(), AttributeModifier.Operation.MULTIPLY_BASE, String.valueOf(ARMOR_TOUGHNESS));
            MAUtils.addAttributes(player, Attributes.MAX_HEALTH, CommonConfig.VANIR_MASK_HEALTH_INCREASE.get(), AttributeModifier.Operation.MULTIPLY_BASE, String.valueOf(MAX_HEALTH));
            MAUtils.addAttributes(player, Attributes.MOVEMENT_SPEED, CommonConfig.VANIR_MASK_SPEED_INCREASE.get(), AttributeModifier.Operation.MULTIPLY_BASE, String.valueOf(MOVEMENT_SPEED));
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            MAUtils.removeAttributes(player, Attributes.ARMOR, String.valueOf(ARMOR));
            MAUtils.removeAttributes(player, Attributes.ARMOR_TOUGHNESS, String.valueOf(ARMOR_TOUGHNESS));
            MAUtils.removeAttributes(player, Attributes.MAX_HEALTH, String.valueOf(MAX_HEALTH));
            MAUtils.removeAttributes(player, Attributes.MOVEMENT_SPEED, String.valueOf(MOVEMENT_SPEED));
        }
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        double damageIncrease = CommonConfig.VANIR_MASK_DAMAGE_INCREASE.get();
        double healthIncrease = CommonConfig.VANIR_MASK_HEALTH_INCREASE.get() * 100;
        double speedIncrease = CommonConfig.VANIR_MASK_SPEED_INCREASE.get() * 100;
        double armorIncrease = CommonConfig.VANIR_MASK_ARMOR_INCREASE.get() * 100;
        double armorToughnessIncrease = CommonConfig.VANIR_MASK_ARMOR_TOUGHNESS_INCREASE.get() * 100;
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.vanir.mask.bloodflow").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.hold.ctrl"));
        if (Screen.hasControlDown()) {
            pTooltipComponents.remove(2);
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.vanir_mask.damage", String.format("%.0f", damageIncrease)).withStyle(ChatFormatting.BLUE));
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.vanir_mask.health", String.format("%.0f", healthIncrease)).withStyle(ChatFormatting.BLUE));
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.vanir_mask.speed", String.format("%.0f", speedIncrease)).withStyle(ChatFormatting.BLUE));
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.vanir_mask.armor", String.format("%.0f", armorIncrease)).withStyle(ChatFormatting.BLUE));
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.vanir_mask.armor_toughness", String.format("%.0f", armorToughnessIncrease)).withStyle(ChatFormatting.BLUE));
            super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        }
    }

}










