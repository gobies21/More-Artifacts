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

public class MoltenQuiverItem extends Item implements ICurioItem {
    public MoltenQuiverItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.RARE));
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        double arrowDamage = (CommonConfig.MOLTEN_QUIVER_DAMAGE.get() - 1) * 100;
        double arrowSave = (CommonConfig.MOLTEN_QUIVER_AMMO.get() * 100);
        double arrowFireDamage = (CommonConfig.MOLTEN_QUIVER_ONFIRE_DAMAGE.get() - 1) * 100;
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.molten_quiver.increased_damage", String.format("%.1f", arrowDamage)).withStyle(ChatFormatting.DARK_AQUA));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.molten_quiver.save_arrow", String.format("%.1f", arrowSave)).withStyle(ChatFormatting.DARK_AQUA));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.molten_quiver.fire").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.molten_quiver.increased_fire_damage", String.format("%.1f", arrowFireDamage)).withStyle(ChatFormatting.DARK_AQUA));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}








