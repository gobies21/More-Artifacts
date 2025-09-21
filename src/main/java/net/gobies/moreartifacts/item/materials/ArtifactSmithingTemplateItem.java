package net.gobies.moreartifacts.item.materials;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class ArtifactSmithingTemplateItem extends Item {
    public ArtifactSmithingTemplateItem(Properties properties) {
        super(properties.stacksTo(64).rarity(Rarity.COMMON));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level
            pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.artifact_upgrade_smithing_template.upgrade").withStyle(ChatFormatting.GRAY));
        pTooltipComponents.add(Component.literal(""));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.artifact_upgrade_smithing_template.applies_to").withStyle(ChatFormatting.GRAY));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.artifact_upgrade_smithing_template.artifacts").withStyle(ChatFormatting.BLUE));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.artifact_upgrade_smithing_template.ingredients").withStyle(ChatFormatting.GRAY));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.artifact_upgrade_smithing_template.artifacts").withStyle(ChatFormatting.BLUE));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
