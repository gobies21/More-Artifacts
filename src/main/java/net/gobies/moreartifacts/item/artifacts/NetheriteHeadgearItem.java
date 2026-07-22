package net.gobies.moreartifacts.item.artifacts;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.util.ModLoadedUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class NetheriteHeadgearItem extends Item implements ICurioItem {
    public NetheriteHeadgearItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.RARE).fireResistant());
    }

    private static final UUID ARMOR = UUID.fromString("91129a68-a553-4ec2-941f-667fc38751a7");

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = LinkedHashMultimap.create();
        modifiers.put(Attributes.ARMOR, new AttributeModifier(uuid, String.valueOf(ARMOR), CommonConfig.NETHERITE_HEADGEAR_ARMOR.get(), AttributeModifier.Operation.ADDITION));

        return modifiers;
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public ICurio.@NotNull SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_NETHERITE, 1.0F, 1.0F);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        double arrowDamageReduction = CommonConfig.NETHERITE_HEADGEAR_ARROW_DAMAGE_REDUCTION.get() * 100;
        double headshotDamageReduction = CommonConfig.NETHERITE_HEADGEAR_HEADSHOT_DAMAGE_REDUCTION.get() * 100;
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.netherite_headgear.arrow.damage", String.format("%.0f", arrowDamageReduction)).withStyle(ChatFormatting.BLUE));
        if (ModLoadedUtil.isFirstAidLoaded() && CommonConfig.NETHERITE_HEADGEAR_FIRST_AID_COMPAT.get()) {
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.hold.ctrl"));
            if (Screen.hasControlDown()) {
                pTooltipComponents.remove(2);
                pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.headgear.headshot", String.format("%.0f", headshotDamageReduction)).withStyle(ChatFormatting.BLUE));
            }
            super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        }
    }
}








