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

public class LeatherTreadsItem extends Item implements ICurioItem {
    public LeatherTreadsItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.COMMON));
    }

    private static final UUID SPEED = UUID.fromString("f195b58b-6957-4166-beea-6e42ee8439f6");

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = LinkedHashMultimap.create();
        modifiers.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, String.valueOf(SPEED), CommonConfig.LEATHER_TREADS_SPEED.get(), AttributeModifier.Operation.MULTIPLY_BASE));
        return modifiers;
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }
}
