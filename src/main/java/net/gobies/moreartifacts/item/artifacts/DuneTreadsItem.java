package net.gobies.moreartifacts.item.artifacts;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.util.MAUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
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

    private static final UUID SPEED = UUID.fromString("47fa3f4d-1aed-429a-8894-ecaddeb446e2");
    private static final UUID SAND_SPEED_BONUS = UUID.fromString("ae90f103-452c-4f44-909d-55be869b31f1");

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = LinkedHashMultimap.create();
        modifiers.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, String.valueOf(SPEED), CommonConfig.DUNE_TREADS_SPEED.get(), AttributeModifier.Operation.MULTIPLY_BASE));
        return modifiers;
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
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
        double sandSpeed = CommonConfig.DUNE_TREADS_SAND_SPEED.get() * 100;
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.dune_treads.bonus_on_sand", String.format("%.0f", sandSpeed)).withStyle(ChatFormatting.BLUE));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}