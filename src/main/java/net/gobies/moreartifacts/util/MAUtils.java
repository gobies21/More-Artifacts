package net.gobies.moreartifacts.util;

import net.gobies.moreartifacts.MoreArtifacts;
import net.gobies.moreartifacts.config.CommonConfig;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.text.DecimalFormat;
import java.util.*;

public class MAUtils {

    // Method to make the player immune to burning
    public static void makeBurningImmune(LivingAttackEvent event) {
        if (event.getSource().is(DamageTypes.HOT_FLOOR)) {
            event.setCanceled(true);
        }
    }

    // Method to make the player immune to fire
    public static void makeFireImmune(LivingAttackEvent event) {
        if (event.getSource().is(DamageTypes.HOT_FLOOR) || event.getSource().is(DamageTypes.ON_FIRE) || event.getSource().is(DamageTypes.IN_FIRE)) {
            event.setCanceled(true);
        }
    }

    // Method to make the player immune to freezing
    public static void makeFreezingImmune(LivingAttackEvent event) {
        if (event.getSource().is(DamageTypes.FREEZE)) {
            event.setCanceled(true);
        }
    }
    // Method to make the player immune to lightning
    public static void makeLightningImmune(LivingAttackEvent event) {
        if (event.getSource().is(DamageTypes.LIGHTNING_BOLT)) {
            event.setCanceled(true);
        }
    }

    // Method to grant immunity to all vanilla harmful effects
    public static void makeHarmfulEffectImmune(MobEffectEvent event) {
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
    public static void makeEffectImmune(MobEffectEvent event, MobEffect... specificEffects) {
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
        UUID modifierId = new UUID(player.getUUID().getMostSignificantBits(), player.getUUID().getLeastSignificantBits() ^ name.hashCode());
        AttributeInstance attributeInstance = player.getAttribute(attribute);
        if (attributeInstance != null && attributeInstance.getModifier(modifierId) == null) {
            AttributeModifier modifier = new AttributeModifier(modifierId, attribute.getDescriptionId(), amount, operation);
            attributeInstance.addTransientModifier(modifier);
        }
    }

    // Method to remove attributes from a player's attribute map
    public static void removeAttributes(Player player, Attribute attribute, String name) {
        UUID modifierId = new UUID(player.getUUID().getMostSignificantBits(), player.getUUID().getLeastSignificantBits() ^ name.hashCode());
        AttributeInstance attributeInstance = player.getAttribute(attribute);
        if (attributeInstance != null && attributeInstance.getModifier(modifierId) != null) {
            attributeInstance.removeModifier(modifierId);
        }
    }

    // Method to get looting values
    public static float lootingValues() {
        return 0.005F;
    }

    // Method to spawn portal particles from and to the players positions
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

    // Method to spawn portal particles from and to the players positions
    public static void spawnPortalParticles(ServerLevel world, Vec3 position) {
        if (position != null && world != null) {
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    for (int z = -1; z <= 1; z++) {
                        Vec3 particlePos = position.add(x * 0.3, y * 0.5, z * 0.3);
                        world.sendParticles(ParticleTypes.PORTAL, particlePos.x, particlePos.y, particlePos.z, 10, 0.1D, 0.1D, 0.1D, 0.1D);
                    }
                }
            }
        }
    }

    // Method to award advancements to the player
    public static void awardAdvancement(ServerPlayer serverPlayer, String name) {
        var server = serverPlayer.getServer();
        if (server != null) {
            var advancement = server.getAdvancements().getAdvancement(new ResourceLocation(MoreArtifacts.MOD_ID, name));
            if (advancement != null) {
                var progress = serverPlayer.getAdvancements().getOrStartProgress(advancement);
                if (!progress.isDone()) {
                    serverPlayer.getAdvancements().award(advancement, name);
                }
            }
        }
    }

    // Method to get bleeding immune entities
    public static boolean bleedImmune(LivingEntity target) {
        if (target instanceof AbstractSkeleton || target instanceof AbstractGolem || target instanceof Slime) {
            return true;
        }
        ResourceLocation entityName = ForgeRegistries.ENTITY_TYPES.getKey(target.getType());
        return entityName != null && CommonConfig.BLEED_BLACKLISTED_ENTITIES.get().contains(entityName.toString());
    }

    // Random pitch
    public static float getRandomPitch() {
        Random random = new Random();
        return 0.9f + random.nextFloat() * 0.2f;
    }

    // Random pitch range
    public static float getRandomPitchRange() {
        Random random = new Random();
        return 0.8f + random.nextFloat() * 0.3f;
    }

    private static final DecimalFormat FORMAT = new DecimalFormat("#.##");

    // Decimal format
    public static String formatDecimal(double value) {
        return FORMAT.format(value);
    }

    // Method to log debug messages
    public static void logDebug(String message) {
        if (CommonConfig.ENABLE_DEBUG.get()) {
            System.out.println(message);
        }
    }
}