package net.gobies.moreartifacts.item.materials;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

;import javax.annotation.Nullable;
import java.util.List;

public class ShadowGemItem extends Item {
    public ShadowGemItem(Properties properties) {
        super(new Properties().stacksTo(64).rarity(Rarity.UNCOMMON));
    }
    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level
            pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("moreartifacts.shadow_gem.tooltip"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}










