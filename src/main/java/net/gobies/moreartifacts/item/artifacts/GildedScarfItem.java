package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.config.CommonConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
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

public class GildedScarfItem extends Item implements ICurioItem {
    public GildedScarfItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.RARE).fireResistant());
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
        double damageTaken = (100 - CommonConfig.GILDED_DAMAGE_TAKEN.get() * 100);
        double damageDealt = (CommonConfig.GILDED_DAMAGE_DEALT.get() - 1) * 100;
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.gilded_scarf.neutral").withStyle(ChatFormatting.YELLOW));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.gilded_scarf.damage_taken", String.format("%.1f", damageTaken)).withStyle(ChatFormatting.DARK_AQUA));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.gilded_scarf.damage_dealt", String.format("%.1f", damageDealt)).withStyle(ChatFormatting.DARK_AQUA));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}








