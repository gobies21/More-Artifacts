package net.gobies.moreartifacts.effect;

import net.gobies.moreartifacts.compat.apothecary.ApothecaryCompat;
import net.gobies.moreartifacts.util.ModLoadedUtil;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

public class Sinful extends MobEffect {
    public Sinful(MobEffectCategory category, int color) {
        super(category, color);
    }

    private static final UUID ATTACK_DAMAGE = UUID.fromString("47c43491-7343-43fa-93b2-de550a73d57d");
    private static final UUID DAMAGE_MULTIPLIER = UUID.fromString("0a70d523-2f50-471a-9c4a-559af46967bc");
    private static final UUID ARMOR = UUID.fromString("0dab7f6d-c2d3-4b26-afd1-df2188d50e63");
    private static final UUID ARMOR_TOUGHNESS = UUID.fromString("8a01eaaa-d9d2-419d-abf8-51551b9fc8e6");

    @Override
    public void addAttributeModifiers(@NotNull LivingEntity livingEntity, @NotNull AttributeMap attributeMap, int amplifier) {
        if (ModLoadedUtil.isApothecaryLoaded()) {
            this.getAttributeModifiers().put(ApothecaryCompat.damageMultiplier(), createModifier4());
        } else {
            this.getAttributeModifiers().put(Attributes.ATTACK_DAMAGE, createModifier());
        }
        this.getAttributeModifiers().put(Attributes.ARMOR, createModifier2());
        this.getAttributeModifiers().put(Attributes.ARMOR_TOUGHNESS, createModifier3());
        super.addAttributeModifiers(livingEntity, attributeMap, amplifier);
    }

    @Override
    public @NotNull Map<Attribute, AttributeModifier> getAttributeModifiers() {
        Map<Attribute, AttributeModifier> modifiers = super.getAttributeModifiers();
        if (ModLoadedUtil.isApothecaryLoaded()) {
            modifiers.put(ApothecaryCompat.damageMultiplier(), createModifier4());
        } else {
            modifiers.put(Attributes.ATTACK_DAMAGE, createModifier());
        }
        modifiers.put(Attributes.ARMOR, createModifier2());
        modifiers.put(Attributes.ARMOR_TOUGHNESS, createModifier3());
        return modifiers;
    }

    private AttributeModifier createModifier4() {
        return new AttributeModifier(DAMAGE_MULTIPLIER, this::getDescriptionId, 0.05, AttributeModifier.Operation.MULTIPLY_BASE);
    }

    private AttributeModifier createModifier() {
        return new AttributeModifier(ATTACK_DAMAGE, this::getDescriptionId, 0.05, AttributeModifier.Operation.MULTIPLY_BASE);
    }

    private AttributeModifier createModifier2() {
        return new AttributeModifier(ARMOR, this::getDescriptionId, 2.0, AttributeModifier.Operation.ADDITION);
    }

    private AttributeModifier createModifier3() {
        return new AttributeModifier(ARMOR_TOUGHNESS, this::getDescriptionId, 2.0, AttributeModifier.Operation.ADDITION);
    }
}
