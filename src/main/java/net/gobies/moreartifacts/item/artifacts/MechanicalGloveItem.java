package net.gobies.moreartifacts.item.artifacts;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.gobies.moreartifacts.config.CommonConfig;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.UUID;

public class MechanicalGloveItem extends Item implements ICurioItem {
    public MechanicalGloveItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.COMMON));
    }

    private static final UUID ATTACK_DAMAGE_UUID = UUID.fromString("b62d5c53-c1f4-442c-a1e3-9d27fa245e76");
    private static final UUID ATTACK_SPEED_UUID = UUID.fromString("f4b06bde-b95d-4a76-8987-86e45c4c533f");

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = LinkedHashMultimap.create();
        modifiers.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid, String.valueOf(ATTACK_DAMAGE_UUID), CommonConfig.MECHANICAL_GLOVE_DAMAGE.get(), AttributeModifier.Operation.ADDITION));
        modifiers.put(Attributes.ATTACK_SPEED, new AttributeModifier(uuid, String.valueOf(ATTACK_SPEED_UUID), CommonConfig.MECHANICAL_GLOVE_SPEED.get(), AttributeModifier.Operation.MULTIPLY_BASE));

        return modifiers;
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }
}




