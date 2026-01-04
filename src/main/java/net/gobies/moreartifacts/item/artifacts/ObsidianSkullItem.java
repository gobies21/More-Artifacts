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

public class ObsidianSkullItem extends Item implements ICurioItem {
    public ObsidianSkullItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.UNCOMMON).fireResistant());
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        double fireDamageReduction = (CommonConfig.SKULL_FIRE_DAMAGE_REDUCTION.get()) * 100;
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.burning_immune").withStyle(ChatFormatting.GRAY));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.obsidian_skull.fire_damage", String.format("%.1f", fireDamageReduction)).withStyle(ChatFormatting.DARK_AQUA));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}






