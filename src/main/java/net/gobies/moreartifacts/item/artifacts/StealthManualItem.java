package net.gobies.moreartifacts.item.artifacts;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.gobies.moreartifacts.compat.betterstealth.BetterStealthCompat;
import net.gobies.moreartifacts.util.MAUtils;
import net.gobies.moreartifacts.util.ModLoadedUtil;
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
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class StealthManualItem extends Item implements ICurioItem {
    public StealthManualItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.UNCOMMON));
    }

    private static final UUID SPEED = UUID.fromString("2d4cf39b-6519-43a5-b0c8-a378d95c2e7c");
    private static final UUID STEALTH = UUID.fromString("73c64883-b097-4034-9b86-0661d9319213");

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            updateSpeedModifier(player);
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = LinkedHashMultimap.create();
        if (ModLoadedUtil.isBetterStealthLoaded()) {
            modifiers.put(BetterStealthCompat.stealthAttribute(), new AttributeModifier(uuid, String.valueOf(STEALTH), 0.2, AttributeModifier.Operation.ADDITION));
        }
        return modifiers;
    }

    private void updateSpeedModifier(Player player) {
        if (player.isCrouching()) {
            MAUtils.addAttributes(player, Attributes.MOVEMENT_SPEED, 0.50, AttributeModifier.Operation.MULTIPLY_BASE, String.valueOf(SPEED));
        } else {
            MAUtils.removeAttributes(player, Attributes.MOVEMENT_SPEED, String.valueOf(SPEED));
        }
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.stealth_manual.sneak").withStyle(ChatFormatting.GRAY));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.stealth_manual.cobweb").withStyle(ChatFormatting.GRAY));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}








