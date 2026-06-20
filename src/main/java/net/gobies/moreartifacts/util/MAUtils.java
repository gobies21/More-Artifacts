package net.gobies.moreartifacts.util;

import net.gobies.moreartifacts.config.CommonConfig;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.fml.ModList;

import java.util.*;

public class MAUtils {
    private static final Map<UUID, Long> cooldownMap = new HashMap<>();

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
    // Method to make the player immune to freezing
    public static void makeFreezingImmune(LivingAttackEvent event) {
        if (event.getSource().is(DamageTypes.FREEZE)) {
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
    // Method to remove all vanilla harmful effects
    public static void removeHarmfulEffects(Player player) {
        if (player.getActiveEffects().isEmpty()) return;
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
        long lastTeleportTime = cooldownMap.getOrDefault(player.getUUID(), 0L);
        return (currentTime - lastTeleportTime) / 1000.0;
    }
    // Method to update the cooldown of an artifact
    public static void updateCooldown(Player player) {
        cooldownMap.put(player.getUUID(), System.currentTimeMillis());
    }
    // Method to check if the player is ready to teleport
    public static boolean isReadyForTeleport(Player player, double cooldown) {
        return getCooldownTimer(player, cooldown) >= cooldown;
    }
    // Method to reset the cooldown timer of an artifact
    public static void resetCooldownTimer(Player player) {
        cooldownMap.put(player.getUUID(), 0L);
    }
    // Method to get looting values based off if JLME is installed
    public static float lootingValues() {
        if (ModList.get().isLoaded("jlme")) {
            return 0.005f;
        } else {
            return 0.01f;
        }
    }
    public static void spawnPortalParticles(ServerPlayer serverPlayer, Vec3 position) {
        if (position != null) {
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    for (int z = -1; z <= 1; z++) {
                        Vec3 particlePos = position.add(x * 0.3, y * 0.5, z * 0.3);
                        serverPlayer.connection.send(new ClientboundLevelParticlesPacket(ParticleTypes.PORTAL, true, particlePos.x, particlePos.y, particlePos.z, 0.1F, 0.1F, 0.1F, 0.1F, 10));
                    }
                }
            }
        }
    }
    // Method to log debug messages
    public static void logDebug(String message) {
        if (CommonConfig.ENABLE_DEBUG.get()) {
            System.out.println(message);
        }
    }
    public static void clearMaps(UUID uuid) {
        cooldownMap.remove(uuid);
    }
}