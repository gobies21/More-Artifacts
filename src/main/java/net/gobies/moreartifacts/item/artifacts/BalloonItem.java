package net.gobies.moreartifacts.item.artifacts;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.gobies.moreartifacts.compat.apothecary.ApothecaryCompat;
import net.gobies.moreartifacts.util.ModLoadedUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class BalloonItem extends Item implements ICurioItem {
    public BalloonItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.COMMON));
    }

    private static final UUID JUMP_HEIGHT = UUID.fromString("17e2c070-b3c2-4b5c-bd9c-41ac4300a7b0");

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = LinkedHashMultimap.create();
        if (ModLoadedUtil.isApothecaryLoaded()) {
            modifiers.put(ApothecaryCompat.jumpHeight(), new AttributeModifier(uuid, String.valueOf(JUMP_HEIGHT), 1, AttributeModifier.Operation.ADDITION));
        }
        return modifiers;
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        if (ModLoadedUtil.isApothecaryLoaded()) return;
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.balloon").withStyle(ChatFormatting.GRAY));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}









