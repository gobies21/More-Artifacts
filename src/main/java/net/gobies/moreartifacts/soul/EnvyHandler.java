package net.gobies.moreartifacts.soul;

import net.gobies.moreartifacts.compat.apothecary.ApothecaryCompat;
import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.helper.SoulAttributes;
import net.gobies.moreartifacts.helper.SoulSyncHelper;
import net.gobies.moreartifacts.helper.SoulType;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.init.MAParticles;
import net.gobies.moreartifacts.init.MASounds;
import net.gobies.moreartifacts.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class EnvyHandler extends SoulUtil {

    public static final Set<UUID> darkAreaCache = new HashSet<>();
    private static boolean isConverted = false;

    public static MobEffect getEffect() {
        return ForgeRegistries.MOB_EFFECTS.getValue(ResourceLocation.tryParse(CommonConfig.ENVY_EFFECT.get()));
    }

    public static void tickEnvySoul(Player player, CompoundTag persistentData) {
        UUID playerId = player.getUUID();
        boolean isDarkArea;

        if (player.tickCount % 5 == 0) {
            if (player.tickCount % 10 == 0) {
                SoulAttributes.manageEnvyAttributes(player);
            }

            BlockPos pos = player.blockPosition();
            int lightLevel = player.level().getMaxLocalRawBrightness(pos);
            isDarkArea = lightLevel <= 7;

            if (isDarkArea) {
                darkAreaCache.add(playerId);
            } else {
                darkAreaCache.remove(playerId);
            }

            isDarkArea = darkAreaCache.contains(playerId);

            if (!persistentData.contains(SoulUtil.PASSIVE_TOGGLE)) {
                SoulUtil.setActive(player, true);
            }

            boolean abilityActive = SoulUtil.getPassive(player) && isDarkArea;
            boolean isActive = activePassive.contains(playerId);
            MobEffectInstance currentEffect = player.getEffect(getEffect());

            if (abilityActive) {
                if (currentEffect == null || !isActive) {
                    player.addEffect(new MobEffectInstance(getEffect(), 210, 0, true, false));
                    activePassive.add(playerId);
                } else if (currentEffect.getDuration() <= 110) {
                    player.addEffect(new MobEffectInstance(getEffect(), 210, 0, true, false));
                }
            } else if (isActive) {
                if (currentEffect != null) {
                    player.removeEffect(getEffect());
                }
                activePassive.remove(playerId);
            }
            MAUtils.removeEffect(player, MobEffects.MOVEMENT_SLOWDOWN, MobEffects.BLINDNESS, MobEffects.DARKNESS);
        }
    }

    public static void handleDamageConversion(LivingEntity livingEntity, LivingEntity target, DamageSource source, LivingHurtEvent event) {
        if (!isConverted) {
            if (livingEntity instanceof Player player && !source.is(DamageTypes.MAGIC)) {
                if (source.getDirectEntity() != player) return;
                float amount = event.getAmount();

                float ringConversionRate = 0f;
                float soulConversionRate = 0f;

                int envyRingCount = CurioHandler.getCurioCount(player, MAItems.EnvyRing.get());
                if (LuckHelper.roll(player, 0.25 * envyRingCount, 0.025)) {
                    ringConversionRate = 1.0f;
                }

                if (CurioHandler.isCurioEquipped(player, MAItems.EnvySoul.get())) {
                    ItemStack stack = CurioHandler.getEquippedCurio(player, MAItems.EnvySoul.get());
                    int stage = SoulUtil.getSoulStage(stack);
                    if (stage >= 1 && stage <= 4) {
                        soulConversionRate = stage * CommonConfig.ENVY_MAGIC_DAMAGE_CONVERSION.get().floatValue();
                    }
                } else {
                    if (SoulUtil.hasSoul(player, SoulType.ENVY)) {
                        soulConversionRate = 5 * CommonConfig.ENVY_MAGIC_DAMAGE_CONVERSION.get().floatValue();
                    }
                }

                float conversionRate = Math.max(ringConversionRate, soulConversionRate);

                if (conversionRate > 0f) {
                    float magicDamage = amount * conversionRate;
                    float physicalDamage = amount - magicDamage;
                    event.setAmount(physicalDamage);

                    var magicSource = livingEntity.level().damageSources().indirectMagic(player, player);
                    LivingHurtEvent magicEvent = new LivingHurtEvent(target, magicSource, magicDamage);

                    isConverted = true;
                    MinecraftForge.EVENT_BUS.post(magicEvent);
                    isConverted = false;

                    if (!magicEvent.isCanceled()) {
                        event.setAmount(physicalDamage + magicEvent.getAmount());
                    }

                    if (CurioHandler.isCurioEquipped(player, MAItems.EnvyRing.get())) {
                        SoulUtil.applySinful(player, 210, 2);
                    }
                }
            }
        }
    }

    public static void handleMagicDamageIncrease(LivingEntity livingEntity, DamageSource source, LivingHurtEvent event) {
        if (ModLoadedUtil.isApothecaryLoaded()) return;
        if (source.is(DamageTypes.MAGIC) || source.is(DamageTypes.INDIRECT_MAGIC)) {
            if (CurioHandler.isCurioEquipped(livingEntity, MAItems.EnvySoul.get())) {
                ItemStack stack = CurioHandler.getEquippedCurio(livingEntity, MAItems.EnvySoul.get());
                int stage = SoulUtil.getSoulStage(stack);
                if (stage >= 1 && stage <= 4) {
                    event.setAmount(event.getAmount() * (1.0f + (stage * CommonConfig.ENVY_MAGIC_DAMAGE.get().floatValue())));
                }
            } else if (livingEntity instanceof Player player && SoulUtil.hasSoul(player, SoulType.ENVY)) {
                event.setAmount(event.getAmount() * (1.0f + (5 * CommonConfig.ENVY_MAGIC_DAMAGE.get().floatValue())));
            }
        }
    }

    public static void handleFlight(Player player) {
        if (player.isCreative() || player.isSpectator()) return;
        boolean spellbooksCompat = ModLoadedUtil.isIronsSpellbooksLoaded() && CommonConfig.IRONS_SPELLBOOKS_MANA_COMPAT.get();

        float currentMana = ManaUtil.getMana(player);
        float flightCost;
        if (spellbooksCompat) {
            float maxMana = ManaUtil.getMaxMana(player);

            float flightTime = CommonConfig.ENVY_SPELLBOOKS_FLIGHT_TIME.get().floatValue(); // Seconds
            float manaCost = CommonConfig.ENVY_FLIGHT_MANA_COST.get().floatValue();
            float offset = manaCost * CommonConfig.ENVY_SPELLBOOKS_FLIGHT_MANA_OFFSET.get().floatValue();

            flightCost = (maxMana / (flightTime * 20.0f)) + offset;
        } else {
            float manaCost = CommonConfig.ENVY_FLIGHT_MANA_COST.get().floatValue();
            flightCost = manaCost * 0.05f;
        }

        float startCost;
        if (!spellbooksCompat) {
            startCost = CommonConfig.ENVY_FLIGHT_START_THRESHOLD.get().floatValue();
        } else {
            startCost = CommonConfig.ENVY_SPELLBOOKS_FLIGHT_START_COST.get().floatValue();
        }
        float requiredMana = player.getAbilities().flying ? flightCost : startCost;

        if (currentMana >= requiredMana) {
            if (!player.getAbilities().mayfly) {
                player.getAbilities().mayfly = true;
                player.onUpdateAbilities();
            }
            if (player.getAbilities().flying) {
                if (player.hasEffect(ApothecaryCompat.flight())) return;
                if (Objects.requireNonNull(player.level().getServer()).getTickCount() % 20 == 0) {
                    ManaUtil.setManaCost(player, flightCost * 20);
                }
            }
        } else {
            if (player.getAbilities().mayfly) {
                player.getAbilities().flying = false;
                player.getAbilities().mayfly = false;
                player.onUpdateAbilities();
            }
        }
        if (currentMana <= 0.5) {
            if (SoulUtil.getActive(player)) {
                SoulUtil.setActive(player, false);
                SoulSyncHelper.syncSouls((ServerPlayer) player);
            }
        }
    }

    public static void handleManaShield(Player player, LivingDamageEvent event) {
        if (event.getAmount() <= 0) return;
        float incomingDamage = event.getAmount();
        float currentMana = ManaUtil.getMana(player);

        float damageMultiplier = CommonConfig.ENVY_MANA_SHIELD_DAMAGE_MULTIPLIER.get().floatValue();
        float armorMultiplier = CommonConfig.ENVY_MANA_SHIELD_ARMOR_MULTIPLIER.get().floatValue();
        float toughnessMultiplier = CommonConfig.ENVY_MANA_SHIELD_ARMOR_TOUGHNESS_MULTIPLIER.get().floatValue();

        boolean spellbooksCompat = ModLoadedUtil.isIronsSpellbooksLoaded() && CommonConfig.IRONS_SPELLBOOKS_MANA_COMPAT.get();
        boolean apothecaryCompat = ModLoadedUtil.isApothecaryLoaded();
        float maxManaScale = 1.0f;
        if (spellbooksCompat) {
            damageMultiplier = CommonConfig.ENVY_SPELLBOOKS_MANA_SHIELD_DAMAGE_MULTIPLIER.get().floatValue();
            armorMultiplier = CommonConfig.ENVY_SPELLBOOKS_MANA_SHIELD_ARMOR_MULTIPLIER.get().floatValue();

            float maxMana = ManaUtil.getMaxMana(player);
            float maxManaThresholds = (maxMana - 100.0f) / 100.0f;

            maxManaScale += Math.round((float) Math.pow(maxManaThresholds, CommonConfig.ENVY_SPELLBOOKS_MANA_SHIELD_MAX_MANA_SCALE.get()));
            damageMultiplier *= maxManaScale;
        }
        if (ModLoadedUtil.isFirstAidLoaded()) {
            damageMultiplier += CommonConfig.ENVY_FIRST_AID_MANA_SHIELD_DAMAGE_MULTIPLIER.get().floatValue(); // First aid added multiplier
        }

        double armor = player.getArmorValue();
        double armorToughness = player.getAttributeValue(Attributes.ARMOR_TOUGHNESS);
        damageMultiplier += (float) ((armor * armorMultiplier) + (armorToughness * toughnessMultiplier));

        if (apothecaryCompat) {
            double magicShielding = player.getAttributeValue(ApothecaryCompat.magicShielding());
            double magicShieldDefense = 0.25D;
            if (spellbooksCompat) {
                magicShieldDefense = 0.5D;
            }
            if (magicShielding != 0.0D) {
                damageMultiplier -= (float) (Math.min(magicShielding * magicShieldDefense, 20.0D));
            }
        }

        damageMultiplier = Math.max(damageMultiplier, 2.0f); // Multiplier cap

        if (currentMana >= 0) {
            float diminishingFactor = CommonConfig.ENVY_MANA_SHIELD_DIMINISHING_FACTOR.get().floatValue();
            float damageThreshold = CommonConfig.ENVY_MANA_SHIELD_DIMINISHING_THRESHOLD.get().floatValue();
            float scaledDamage = incomingDamage;

            if (incomingDamage > damageThreshold) {
                scaledDamage = (float) Math.pow(incomingDamage, diminishingFactor);
            }

            float manaDamageCost = Math.round(scaledDamage * damageMultiplier);

            if (currentMana >= manaDamageCost) {
                ManaUtil.setManaCost(player, manaDamageCost);
                event.setAmount(0.0f);
                if (player.level() instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(
                            MAParticles.MANA_SHIELD_DAMAGE.get(),
                            player.getX() + (player.getRandom().nextDouble() - 0.5D) * 1.2D,
                            player.getY() + player.getBbHeight(),
                            player.getZ() + (player.getRandom().nextDouble() - 0.5D) * 1.2D,
                            1,
                            0.0D, 0.0D, 0.0D,
                            0.0D
                    );
                }
                MAUtils.logDebug("Mana damaged for " + player.getName().getString() + " {Mana Cost: " + manaDamageCost + " Original Damage:" + incomingDamage + " Multiplier:" + damageMultiplier + "}");
            } else {
                ManaUtil.setMana(player, 0.0f);

                float damageAbsorbed = (float) Math.pow(currentMana / damageMultiplier, 1.0f / diminishingFactor);
                float damageTaken = Math.max(0.0f, incomingDamage - damageAbsorbed);
                event.setAmount(damageTaken);
                if (player.level() instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(
                            MAParticles.MANA_SHIELD_BREAK.get(),
                            player.getX() + (player.getRandom().nextDouble() - 0.5D) * 1.2D,
                            player.getY() + player.getBbHeight(),
                            player.getZ() + (player.getRandom().nextDouble() - 0.5D) * 1.2D,
                            1,
                            0.0D, 0.0D, 0.0D,
                            0.0D
                    );
                }
                MAUtils.logDebug("Mana shield broken for " + player.getName().getString() + " {Mana Consumed: " + currentMana + " {Damage Taken: " + damageTaken + " Original Damage:" + incomingDamage + "}" + " Multiplier:" + damageMultiplier + "}");
                SoulUtil.setActive(player, false);
                SoulSyncHelper.syncSouls((ServerPlayer) player);
                if (CommonConfig.MANA_SHIELD_SOUNDS.get()) {
                    player.level().playSound(null, player.getX(), player.getY(), player.getZ(), MASounds.MANA_SHIELD_BREAK.get(), SoundSource.PLAYERS, 1.0F, MAUtils.getRandomPitch());
                }
            }
        }
    }
}