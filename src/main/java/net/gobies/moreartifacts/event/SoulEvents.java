package net.gobies.moreartifacts.event;

import net.gobies.moreartifacts.compat.betterstealth.BetterStealthCompat;
import net.gobies.moreartifacts.util.MAUtils;
import net.gobies.moreartifacts.util.ModLoadedUtil;
import net.gobies.moreartifacts.util.SoulUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.*;

public class SoulEvents {

    public static void register() {
        MinecraftForge.EVENT_BUS.register(new SoulEvents());
    }

    private static final UUID SHADOW_SPEED = UUID.fromString("6a4fa28a-7e3e-4d43-bc97-9e48b813b19b");
    private static final UUID SHADOW_ARMOR = UUID.fromString("7f86dbdf-dc8a-4467-89fb-c3491ea9642b");
    private static final UUID SHADOW_STEALTH = UUID.fromString("0a6493b8-6ee4-436f-b2f5-b2230489cf00");

    private static final UUID BLOOD_MAX_HEALTH = UUID.fromString("b223ce63-e4c2-4b95-a63c-85c166e41c81");
    private static final UUID BLOOD_ATTACK_SPEED = UUID.fromString("39d7cfe1-2854-4609-9bb0-0ffebcb029c1");
    private static final UUID BLOOD_ARMOR = UUID.fromString("47f4de22-72a7-42a3-b8ed-874e49747e9f");
    private static final UUID BLOOD_ARMOR_TOUGHNESS = UUID.fromString("26a47eb3-bf85-4417-af75-287cf04f77f3");

    private static final Set<UUID> activeAbility = new HashSet<>();
    private static final Set<UUID> darkAreaCache = new HashSet<>();

    @SubscribeEvent
    public void onSoulTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.level().isClientSide()) return;
        Player player = event.player;
        CompoundTag persistentData = player.getPersistentData();

        String activeSoul = persistentData.getString(SoulUtil.SOUL_KEY);

        if (SoulUtil.SHADOW.equals(activeSoul)) {
            tickShadowSoul(player, persistentData);
        } else if (SoulUtil.BLOOD.equals(activeSoul)) {
            tickBloodSoul(player, persistentData);
        }
    }

    private static void tickShadowSoul(Player player, CompoundTag persistentData) {
        UUID playerId = player.getUUID();
        boolean isDarkArea;

        if (player.tickCount % 5 == 0) {
            if (player.tickCount % 10 == 0) {
                manageShadowAttributes(player);
            }

            BlockPos pos = player.blockPosition();
            int lightLevel = player.level().getMaxLocalRawBrightness(pos);
            isDarkArea = lightLevel <= 7;

            if (isDarkArea) {
                darkAreaCache.add(playerId);
            } else {
                darkAreaCache.remove(playerId);
            }

            if (ModLoadedUtil.isBetterStealthLoaded()) {
                updateShadowStealthModifier(player, isDarkArea);
            }
        } else {
            isDarkArea = darkAreaCache.contains(playerId);
        }

        if (!persistentData.contains(SoulUtil.ABILITY_TOGGLE)) {
            persistentData.putBoolean(SoulUtil.ABILITY_TOGGLE, true);
        }

        boolean abilityActive = persistentData.getBoolean(SoulUtil.ABILITY_TOGGLE) && isDarkArea;
        boolean isActive = activeAbility.contains(playerId);
        MobEffectInstance currentEffect = player.getEffect(MobEffects.INVISIBILITY);

        if (abilityActive) {
            if (currentEffect == null || !isActive) {
                player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 210, 0, true, false));
                activeAbility.add(playerId);
            } else if (currentEffect.getDuration() <= 110) {
                player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 210, 0, true, false));
            }
        } else if (isActive) {
            if (currentEffect != null) {
                player.removeEffect(MobEffects.INVISIBILITY);
            }
            activeAbility.remove(playerId);
        }
    }

    private static void manageShadowAttributes(Player player) {
        AttributeInstance speedAttr = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speedAttr != null && speedAttr.getModifier(SHADOW_SPEED) == null) {
            speedAttr.addPermanentModifier(new AttributeModifier(SHADOW_SPEED, "Shadow Speed", 0.40, AttributeModifier.Operation.MULTIPLY_BASE));
        }

        AttributeInstance armorAttr = player.getAttribute(Attributes.ARMOR);
        if (armorAttr != null && armorAttr.getModifier(SHADOW_ARMOR) == null) {
            armorAttr.addPermanentModifier(new AttributeModifier(SHADOW_ARMOR, "Shadow Armor Penalty", -0.30, AttributeModifier.Operation.MULTIPLY_BASE));
        }
    }

    private static void updateShadowStealthModifier(Player player, boolean isDarkArea) {
        if (isDarkArea) {
            MAUtils.addAttributes(player, BetterStealthCompat.stealthAttribute(), 0.5, AttributeModifier.Operation.ADDITION, String.valueOf(SHADOW_STEALTH));
        } else {
            MAUtils.removeAttributes(player, BetterStealthCompat.stealthAttribute(), String.valueOf(SHADOW_STEALTH));
        }
    }

    private void tickBloodSoul(Player player, CompoundTag persistentData) {
        UUID playerId = player.getUUID();
        if (player.tickCount % 5 == 0) {
            if (player.tickCount % 10 == 0) {
                manageBloodAttributes(player);
            }

            if (!persistentData.contains(SoulUtil.ABILITY_TOGGLE)) {
                persistentData.putBoolean(SoulUtil.ABILITY_TOGGLE, true);
            }

            boolean abilityActive = persistentData.getBoolean(SoulUtil.ABILITY_TOGGLE);
            boolean isActive = activeAbility.contains(playerId);
            MobEffectInstance currentEffect = player.getEffect(MobEffects.REGENERATION);

            if (abilityActive) {
                if (currentEffect == null || !isActive) {
                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 210, 0, true, false));
                    activeAbility.add(playerId);
                } else if (currentEffect.getDuration() <= 110) {
                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 210, 0, true, false));
                }
            } else if (isActive) {
                if (currentEffect != null) {
                    player.removeEffect(MobEffects.REGENERATION);
                }
                activeAbility.remove(playerId);
            }
        }
    }


    private static void manageBloodAttributes(Player player) {
        AttributeInstance healthAttr = player.getAttribute(Attributes.MAX_HEALTH);
        if (healthAttr != null && healthAttr.getModifier(BLOOD_MAX_HEALTH) == null) {
            healthAttr.addPermanentModifier(new AttributeModifier(BLOOD_MAX_HEALTH, "Blood Max Health", 0.75, AttributeModifier.Operation.MULTIPLY_BASE));
        }

        AttributeInstance attackSpeedAttr = player.getAttribute(Attributes.ATTACK_SPEED);
        if (attackSpeedAttr != null && attackSpeedAttr.getModifier(BLOOD_ATTACK_SPEED) == null) {
            attackSpeedAttr.addPermanentModifier(new AttributeModifier(BLOOD_ATTACK_SPEED, "Blood Attack Speed", 0.30, AttributeModifier.Operation.MULTIPLY_BASE));
        }

        AttributeInstance armorAttr = player.getAttribute(Attributes.ARMOR);
        if (armorAttr != null && armorAttr.getModifier(BLOOD_ARMOR) == null) {
            armorAttr.addPermanentModifier(new AttributeModifier(BLOOD_ARMOR, "Blood Armor Penalty", -0.50, AttributeModifier.Operation.MULTIPLY_BASE));
        }

        AttributeInstance armorToughnessAttr = player.getAttribute(Attributes.ARMOR_TOUGHNESS);
        if (armorToughnessAttr != null && armorToughnessAttr.getModifier(BLOOD_ARMOR_TOUGHNESS) == null) {
            armorToughnessAttr.addPermanentModifier(new AttributeModifier(BLOOD_ARMOR_TOUGHNESS, "Blood Armor Toughness Penalty", -0.25, AttributeModifier.Operation.MULTIPLY_BASE));
        }
    }

    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event) {
        Player oldPlayer = event.getOriginal();
        Player newPlayer = event.getEntity();

        CompoundTag oldData = oldPlayer.getPersistentData();
        CompoundTag newData = newPlayer.getPersistentData();

        String activeSoul = oldData.getString(SoulUtil.SOUL_KEY);

        if (!activeSoul.isEmpty()) {
            newData.putString(SoulUtil.SOUL_KEY, activeSoul);

            if (SoulUtil.SHADOW.equals(activeSoul)) {
                newData.putBoolean(SoulUtil.ABILITY_TOGGLE, oldData.getBoolean(SoulUtil.ABILITY_TOGGLE));
                manageShadowAttributes(newPlayer);
            } else if (SoulUtil.BLOOD.equals(activeSoul)) {
                manageBloodAttributes(newPlayer);
            }
        }
    }

    public static void clearMaps(UUID uuid) {
        activeAbility.remove(uuid);
        darkAreaCache.remove(uuid);
    }
}