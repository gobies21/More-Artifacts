package net.gobies.moreartifacts.util;

import net.gobies.moreartifacts.config.CommonConfig;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;

import java.util.*;

public class MAUtils {
    private static final Map<Player, Long> cooldownMap = new HashMap<>();

    // Method to detect if the damage source is fire
    public static boolean isFire(LivingHurtEvent event) {
        return event.getSource().is(DamageTypes.ON_FIRE) || event.getSource().is(DamageTypes.IN_FIRE);
    }

    // Method to make the player immune to burning
    public static void makeBurningImmune(LivingAttackEvent event) {
        if (event.getSource().is(DamageTypes.HOT_FLOOR)) {
            event.setCanceled(true);
        }
    }
    // Method to grant immunity to all harmful effects
    public static void harmfulEffectImmunity(MobEffectEvent event) {
        List<MobEffect> harmfulEffects = Arrays.asList(
                MobEffects.POISON,
                MobEffects.WITHER,
                MobEffects.HUNGER,
                MobEffects.CONFUSION,
                MobEffects.MOVEMENT_SLOWDOWN,
                MobEffects.LEVITATION,
                MobEffects.DIG_SLOWDOWN,
                MobEffects.WEAKNESS,
                MobEffects.BLINDNESS,
                MobEffects.DARKNESS
        );

        MobEffect effect = Objects.requireNonNull(event.getEffectInstance()).getEffect();
        if (harmfulEffects.contains(effect)) {
            event.setResult(MobEffectEvent.Result.DENY);
        }
    }
    // Method to grant immunity to specific harmful effects
    public static void harmfulSpecificEffectImmune(MobEffectEvent event, MobEffect... specificEffects) {
        List<MobEffect> effectsList = Arrays.asList(specificEffects);
        MobEffect effect = Objects.requireNonNull(event.getEffectInstance()).getEffect();
        if (effectsList.contains(effect)) {
            event.setResult(MobEffectEvent.Result.DENY);
        }
    }
    // Method to remove all harmful effects
    public static void removeHarmfulEffects(Player player) {
        List<MobEffect> harmfulEffects = Arrays.asList(
                MobEffects.POISON,
                MobEffects.WITHER,
                MobEffects.HUNGER,
                MobEffects.CONFUSION,
                MobEffects.MOVEMENT_SLOWDOWN,
                MobEffects.LEVITATION,
                MobEffects.DIG_SLOWDOWN,
                MobEffects.WEAKNESS,
                MobEffects.BLINDNESS,
                MobEffects.DARKNESS
        );

        for (MobEffect effect : harmfulEffects) {
            removeEffect(player, effect);
        }
    }
    // Method to remove specific harmful effects
    public static void removeEffect(Player player, MobEffect... effects) {
        for (MobEffect effect : effects) {
            if (player.hasEffect(effect)) {
                player.removeEffect(effect);
            }
        }
    }
    // Method to add attributes from a player's attribute map
    public static void addAttributes(Player player, Attribute attribute, double amount, AttributeModifier.Operation operation, String name) {
        UUID modifierId = UUID.nameUUIDFromBytes((player.getUUID() + name).getBytes());
        if (player.getAttribute(attribute) != null) {
            AttributeInstance attributeInstance = player.getAttribute(attribute);
            if (Objects.requireNonNull(attributeInstance).getModifier(modifierId) == null) {
                AttributeModifier modifier = new AttributeModifier(modifierId, attribute.getDescriptionId(), amount, operation);
                attributeInstance.addTransientModifier(modifier);
            }
        }
    }
    // Method to remove attributes from a player's attribute map
    public static void removeAttributes(Player player, Attribute attribute, String name) {
        UUID modifierId = UUID.nameUUIDFromBytes((player.getUUID() + name).getBytes());
        if (player.getAttribute(attribute) != null) {
            AttributeInstance attributeInstance = player.getAttribute(attribute);
            AttributeModifier modifier = Objects.requireNonNull(attributeInstance).getModifier(modifierId);
            if (modifier != null) {
                attributeInstance.removeModifier(modifierId);
            }
        }
    }
    // Method to get the cooldown of an artifact
    public static double getCooldownTimer(Player player, double cooldown) {
        long currentTime = System.currentTimeMillis();
        long lastTeleportTime = cooldownMap.getOrDefault(player, 0L);
        return (currentTime - lastTeleportTime) / 1000.0;
    }
    // Method to update the cooldown of an artifact
    public static void updateCooldown(Player player) {
        cooldownMap.put(player, System.currentTimeMillis());
    }
    // Method to check if the player is ready to teleport
    public static boolean isReadyForTeleport(Player player, double cooldown) {
        return getCooldownTimer(player, cooldown) >= cooldown;
    }
    // Method to reset the cooldown timer of an artifact
    public static void resetCooldownTimer(Player player) {
        cooldownMap.put(player, 0L);
    }
    // Method to get looting values based off if JLME is installed
    public static float lootingValues() {
        if (ModLoadedUtil.isJLMELoaded()) {
            return 0.005f;
        } else {
            return 0.01f;
        }
    }
    // Method to log debug messages
    public static void logDebug(String message) {
        if (CommonConfig.ENABLE_DEBUG.get()) {
            System.out.println(message);
        }
    }
}