package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.util.MAUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.UUID;

public class DuneTreadsItem extends Item implements ICurioItem {
    public DuneTreadsItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.UNCOMMON));
    }

    private static final UUID SPEED = UUID.randomUUID();
    private static final UUID SAND_SPEED_BONUS = UUID.randomUUID();

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            MAUtils.addAttributes(player, Attributes.MOVEMENT_SPEED, CommonConfig.DUNE_TREADS_SPEED.get(), AttributeModifier.Operation.MULTIPLY_BASE, String.valueOf(SPEED));
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            MAUtils.removeAttributes(player, Attributes.MOVEMENT_SPEED, String.valueOf(SPEED));
            MAUtils.removeAttributes(player, Attributes.MOVEMENT_SPEED, String.valueOf(SAND_SPEED_BONUS));
        }
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            updateSpeedModifier(player);
        }
    }

    private void updateSpeedModifier(Player player) {
        BlockState blockState = player.level().getBlockState(player.blockPosition().below());
        if (blockState.is(Tags.Blocks.SAND)) {
            MAUtils.addAttributes(player, Attributes.MOVEMENT_SPEED, CommonConfig.DUNE_TREADS_SAND_SPEED.get(), AttributeModifier.Operation.MULTIPLY_BASE, String.valueOf(SAND_SPEED_BONUS));
        } else {
            MAUtils.removeAttributes(player, Attributes.MOVEMENT_SPEED, String.valueOf(SAND_SPEED_BONUS));
        }
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        double movementSpeed = CommonConfig.DUNE_TREADS_SPEED.get() * 100;
        double sandSpeed = CommonConfig.DUNE_TREADS_SAND_SPEED.get() * 100;
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.dune_treads.speed", String.format("%.1f", movementSpeed)).withStyle(ChatFormatting.DARK_AQUA));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.dune_treads.bonus_on_sand", String.format("%.1f", sandSpeed)).withStyle(ChatFormatting.DARK_AQUA));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}