package net.gobies.moreartifacts.item.souls;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.gobies.moreartifacts.util.SoulUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class BloodSoulItem extends SoulItem {
    public BloodSoulItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    /*

    // Upgrades by have it equip and killing mobs under certain conditions/etc
    TODO: Stage 1 = Attacks have a chance to heal for half a heart
    TODO: Stage 2 = Attacks have a chance to heal for a full heart
    TODO: Stage 3 = Attacks heal for 10% of damage dealt
    TODO: Stage 4 = Attacks heal for 15% of damage dealt + 1 Heart
    TODO: Stage 5 = Attacks heal for 20% of damage dealt + 2 Hearts


    // Stage 2-3: Cannot unequip while at full health
    // Stage 4: Cannot unequip at all
    // Stage 5: No longer an item
    // Icon in inventory to inform what soul the player has if any
    */

    private static final UUID MAX_HEALTH = UUID.fromString("b223ce63-e4c2-4b95-a63c-85c166e41c81");
    private static final UUID ATTACK_SPEED = UUID.fromString("39d7cfe1-2854-4609-9bb0-0ffebcb029c1");
    private static final UUID ARMOR = UUID.fromString("47f4de22-72a7-42a3-b8ed-874e49747e9f");
    private static final UUID ARMOR_TOUGHNESS = UUID.fromString("26a47eb3-bf85-4417-af75-287cf04f77f3");

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = LinkedHashMultimap.create();
        int currentSoulStage = SoulUtil.getSoulStage(stack);
        double healthBonus = 0.25 + (currentSoulStage * 0.10);
        double attackSpeedBonus = 0.05 + (currentSoulStage * 0.05);
        double armorPenalty = 0.0 + (currentSoulStage * 0.10);
        double armorToughnessPenalty = 0.0 + (currentSoulStage * 0.05);
        modifiers.put(Attributes.MAX_HEALTH, new AttributeModifier(uuid, String.valueOf(MAX_HEALTH), healthBonus, AttributeModifier.Operation.MULTIPLY_BASE));
        modifiers.put(Attributes.ATTACK_SPEED, new AttributeModifier(uuid, String.valueOf(ATTACK_SPEED), attackSpeedBonus, AttributeModifier.Operation.MULTIPLY_BASE));
        modifiers.put(Attributes.ARMOR, new AttributeModifier(uuid, String.valueOf(ARMOR), -armorPenalty, AttributeModifier.Operation.MULTIPLY_BASE));
        modifiers.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, String.valueOf(ARMOR_TOUGHNESS), -armorToughnessPenalty, AttributeModifier.Operation.MULTIPLY_BASE));
        return modifiers;
    }

    @Override
    public boolean canUnequip(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            if (player.isCreative()) return true;
            int stage = SoulUtil.getSoulStage(stack);
            if (stage >= 2 && stage <= 3) {
                if (player.getHealth() < player.getMaxHealth()) {
                    return false;
                }
            } else if (stage == 4) {
                return false;
            }
        }

        return super.canUnequip(slotContext, stack);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        int stage = SoulUtil.getSoulStage(pStack);
        CompoundTag nbt = pStack.getOrCreateTag();
        int currentKills = nbt.getInt(SoulUtil.KILLS);

        // Lifesteal Tooltips
        if (stage == 1) {
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.blood_soul.lifesteal1").withStyle(ChatFormatting.GOLD));
        } else if (stage == 2) {
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.blood_soul.lifesteal2").withStyle(ChatFormatting.GOLD));
        } else if (stage == 3) {
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.blood_soul.lifesteal3").withStyle(ChatFormatting.GOLD));
        } else if (stage == 4) {
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.blood_soul.lifesteal4").withStyle(ChatFormatting.GOLD));
        } else {
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.blood_soul.lifesteal5").withStyle(ChatFormatting.GOLD));
        }

        // Evolve Tooltips
        if (stage == 1) {
            pTooltipComponents.add(Component.literal(" "));
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.blood_soul.evolve_1").withStyle(ChatFormatting.GOLD));
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.shadow_soul.kills_required", currentKills, 100).withStyle(ChatFormatting.RED));
        } else if (stage == 2) {
            pTooltipComponents.add(Component.literal(" "));
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.blood_soul.evolve_1").withStyle(ChatFormatting.GOLD));
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.shadow_soul.kills_required", currentKills, 300).withStyle(ChatFormatting.RED));
        } else if (stage == 3) {
            pTooltipComponents.add(Component.literal(" "));
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.blood_soul.evolve_1").withStyle(ChatFormatting.GOLD));
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.shadow_soul.kills_required", currentKills, 500).withStyle(ChatFormatting.RED));
        } else if (stage == 4) {
            pTooltipComponents.add(Component.literal(" "));
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.blood_soul.evolve_1").withStyle(ChatFormatting.GOLD));
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.shadow_soul.kills_required", currentKills, 1000).withStyle(ChatFormatting.RED));
        }
    }
}









