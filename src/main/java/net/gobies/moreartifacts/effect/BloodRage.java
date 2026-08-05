package net.gobies.moreartifacts.effect;

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

public class BloodRage extends MobEffect {
    public BloodRage(MobEffectCategory category, int color) {
        super(category, color);
    }

    private static final UUID ATTACK_SPEED = UUID.fromString("0dab7f6d-c2d3-4b26-afd1-df2188d50e63");
    private static final UUID ATTACK_DAMAGE = UUID.fromString("47c43491-7343-43fa-93b2-de550a73d57d");
    private static final UUID SPEED = UUID.fromString("8a01eaaa-d9d2-419d-abf8-51551b9fc8e6");

    @Override
    public void addAttributeModifiers(@NotNull LivingEntity livingEntity, @NotNull AttributeMap attributeMap, int amplifier) {
        this.getAttributeModifiers().put(Attributes.ATTACK_SPEED, createModifier());
        this.getAttributeModifiers().put(Attributes.ATTACK_DAMAGE, createModifier2());
        this.getAttributeModifiers().put(Attributes.MOVEMENT_SPEED, createModifier3());
        super.addAttributeModifiers(livingEntity, attributeMap, amplifier);
    }

    @Override
    public @NotNull Map<Attribute, AttributeModifier> getAttributeModifiers() {
        Map<Attribute, AttributeModifier> modifiers = super.getAttributeModifiers();
        modifiers.put(Attributes.ATTACK_SPEED, createModifier());
        modifiers.put(Attributes.ATTACK_DAMAGE, createModifier2());
        modifiers.put(Attributes.MOVEMENT_SPEED, createModifier3());
        return modifiers;
    }

    private AttributeModifier createModifier() {
        return new AttributeModifier(ATTACK_SPEED, this::getDescriptionId, 0.05, AttributeModifier.Operation.MULTIPLY_BASE);
    }

    private AttributeModifier createModifier2() {
        return new AttributeModifier(ATTACK_DAMAGE, this::getDescriptionId, 0.05, AttributeModifier.Operation.MULTIPLY_BASE);
    }

    private AttributeModifier createModifier3() {
        return new AttributeModifier(SPEED, this::getDescriptionId, 0.05, AttributeModifier.Operation.MULTIPLY_BASE);
    }
}
