package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.util.MAUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;

public class RedBeltItem extends Item implements ICurioItem {
    public RedBeltItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.UNCOMMON));
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        float dodgeDistance = (CommonConfig.RED_BELT_DODGE_DISTANCE.get().floatValue() - 1) * 100;
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.red_belt.dodge_distance", MAUtils.formatDecimal(dodgeDistance)).withStyle(ChatFormatting.BLUE));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.red_belt.invul").withStyle(ChatFormatting.GRAY));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}








