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
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import javax.annotation.Nullable;
import java.util.*;

public class MechanicalClawItem extends Item implements ICurioItem {
    public MechanicalClawItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.RARE));
    }

    private static final UUID ATTACK_DAMAGE_UUID = UUID.randomUUID();

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            MAUtils.addAttributes(player, Attributes.ATTACK_DAMAGE, CommonConfig.MECHANICAL_CLAW_DAMAGE.get(), AttributeModifier.Operation.MULTIPLY_BASE, String.valueOf(ATTACK_DAMAGE_UUID));

        }
    }

    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            MAUtils.removeAttributes(player, Attributes.ATTACK_DAMAGE, String.valueOf(ATTACK_DAMAGE_UUID));
        }
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        double clawDamage = (CommonConfig.MECHANICAL_CLAW_DAMAGE.get() * 100);
        double bleedChance = (CommonConfig.MECHANICAL_CLAW_BLEED_CHANCE.get() * 100);
        int bleedDamage = (CommonConfig.MECHANICAL_CLAW_BLEED_DAMAGE.get());
        int bleedDuration = (CommonConfig.MECHANICAL_CLAW_BLEED_DURATION.get());
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.mechanical_claw.damage", String.format("%.1f", clawDamage)).withStyle(ChatFormatting.DARK_AQUA));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.mechanical_claw.bleed_chance", String.format("%.1f", bleedChance)).withStyle(ChatFormatting.DARK_AQUA));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.mechanical_claw.bleed_damage_duration", bleedDamage, bleedDuration).withStyle(ChatFormatting.DARK_AQUA));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}


