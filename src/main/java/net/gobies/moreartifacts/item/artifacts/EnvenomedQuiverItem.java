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

public class EnvenomedQuiverItem extends Item implements ICurioItem {
    public EnvenomedQuiverItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.RARE));
    }

    private static final UUID PROJECTILE_DAMAGE = UUID.fromString("f4b8c347-261d-42d6-842b-54fd759d7d72");

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = LinkedHashMultimap.create();
        if (ModLoadedUtil.isApothecaryLoaded()) {
            modifiers.put(ApothecaryCompat.projectileDamage(), new AttributeModifier(uuid, String.valueOf(PROJECTILE_DAMAGE), (CommonConfig.ENVENOMED_QUIVER_DAMAGE.get() - 1), AttributeModifier.Operation.MULTIPLY_BASE));
        }
        return modifiers;
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        double arrowDamage = (CommonConfig.ENVENOMED_QUIVER_DAMAGE.get() - 1) * 100;
        double arrowSave = (CommonConfig.ENVENOMED_QUIVER_AMMO.get() * 100);
        if (!ModLoadedUtil.isApothecaryLoaded()) {
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.envenomed_quiver.damage", String.format("%.0f", arrowDamage)).withStyle(ChatFormatting.BLUE));
        }
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.envenomed_quiver.save_arrow", String.format("%.0f", arrowSave)).withStyle(ChatFormatting.BLUE));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.envenomed_quiver.inflict").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}








