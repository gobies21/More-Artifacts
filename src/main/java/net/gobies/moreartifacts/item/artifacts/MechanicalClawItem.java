package net.gobies.moreartifacts.item.artifacts;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.gobies.moreartifacts.config.CommonConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
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
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import javax.annotation.Nullable;
import java.util.*;

public class MechanicalClawItem extends Item implements ICurioItem {
    public MechanicalClawItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.RARE));
    }

    private static final UUID ATTACK_DAMAGE_UUID = UUID.fromString("27bfedd6-295c-446b-acb2-5c51f23927fa");
    private static final UUID ATTACK_SPEED_UUID = UUID.fromString("7eda7476-8642-4647-bf7a-db277b8ab629");

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = LinkedHashMultimap.create();
        modifiers.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid, String.valueOf(ATTACK_DAMAGE_UUID), CommonConfig.MECHANICAL_CLAW_DAMAGE.get(), AttributeModifier.Operation.MULTIPLY_BASE));
        modifiers.put(Attributes.ATTACK_SPEED, new AttributeModifier(uuid, String.valueOf(ATTACK_SPEED_UUID), CommonConfig.MECHANICAL_CLAW_SPEED.get(), AttributeModifier.Operation.MULTIPLY_BASE));

        return modifiers;
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        double bleedChance = CommonConfig.MECHANICAL_CLAW_BLEED_CHANCE.get() * 100;
        int bleedDamage = CommonConfig.MECHANICAL_CLAW_BLEED_DAMAGE.get();
        int bleedDuration = CommonConfig.MECHANICAL_CLAW_BLEED_DURATION.get();
        double damageReceived = CommonConfig.BLEED_DAMAGE_RECEIVED.get() * 100;
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.mechanical_claw.bleed_chance", String.format("%.0f", bleedChance)).withStyle(ChatFormatting.BLUE));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.mechanical_claw.bleed_damage_duration", bleedDamage, bleedDuration).withStyle(ChatFormatting.BLUE));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.mechanical_claw.bleed_damage_received", String.format("%.0f", damageReceived)).withStyle(ChatFormatting.BLUE));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}


