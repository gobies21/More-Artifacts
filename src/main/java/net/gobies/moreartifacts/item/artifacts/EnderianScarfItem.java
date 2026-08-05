package net.gobies.moreartifacts.item.artifacts;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.gobies.moreartifacts.compat.apothecary.ApothecaryCompat;
import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.util.ModLoadedUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
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

public class EnderianScarfItem extends Item implements ICurioItem {
    public EnderianScarfItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.UNCOMMON));
    }

    private static final UUID DAMAGE_RESISTANCE = UUID.fromString("e6b5f0e6-8ca5-4c67-8bb6-d3769d2e359d");

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = LinkedHashMultimap.create();
        if (ModLoadedUtil.isApothecaryLoaded()) {
            modifiers.put(ApothecaryCompat.damageResistance(), new AttributeModifier(uuid, String.valueOf(DAMAGE_RESISTANCE), CommonConfig.ENDERIAN_DAMAGE_REDUCTION.get(), AttributeModifier.Operation.MULTIPLY_BASE));
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
        double damageTaken = CommonConfig.ENDERIAN_DAMAGE_REDUCTION.get() * 100;
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.enderian_scarf.damage_taken", String.format("%.0f", damageTaken)).withStyle(ChatFormatting.BLUE));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}








