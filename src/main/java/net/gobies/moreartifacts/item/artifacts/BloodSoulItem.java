package net.gobies.moreartifacts.item.artifacts;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.gobies.moreartifacts.util.SoulUtil;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class BloodSoulItem extends SoulStoneItem {
    public BloodSoulItem(Properties properties) {
        super(properties.stacksTo(1));
    }

        /*

    // Upgrades by have it equip and killing mobs under certain conditions/etc
    TODO: Stage 1 = Attacks have a chance to heal for half a heart
    TODO: Stage 2 = Attacks have a chance to heal for a full heart
    TODO: Stage 3 = Attacks heal for 10% of damage dealt
    TODO: Stage 4 = Attacks heal for 20% of damage dealt + 1 Heart
    TODO: Stage 5 = Attacks heal for 20% of damage dealt + 1 Heart


    // Stage 2-3: Cannot unequip in darkness
    // Stage 4: Cannot unequip at all
    // Stage 5: No longer an item
    // Icon in inventory to inform what soul the player has if any
    */

    private static final UUID MAX_HEALTH = UUID.randomUUID();
    private static final UUID ATTACK_SPEED = UUID.randomUUID();
    private static final UUID ARMOR = UUID.randomUUID();
    private static final UUID ARMOR_TOUGHNESS = UUID.randomUUID();

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
}









