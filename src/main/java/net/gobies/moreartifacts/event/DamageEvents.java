package net.gobies.moreartifacts.event;

import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.compat.spartanweaponry.SpartanWeaponryCompat;
import net.gobies.moreartifacts.effect.MAStatusEffects;
import net.gobies.moreartifacts.helper.SoulType;
import net.gobies.moreartifacts.init.MAEffects;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.init.MAParticles;
import net.gobies.moreartifacts.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

@Mod.EventBusSubscriber
public class DamageEvents {
    private static final Map<UUID, Double> generalDamageReductionMap = new HashMap<>();
    private static final Map<UUID, Double> fireDamageReductionMap = new HashMap<>();
    private static final Map<UUID, Double> generalDamageIncreaseMap = new HashMap<>();
    private static final Map<UUID, Map<Item, Boolean>> equippedArtifactsMap = new HashMap<>();
    private static final Map<UUID, Long> lastHealTimeMap = new HashMap<>();

    public static double getTotalDamageReduction(Player player) {
        return generalDamageReductionMap.getOrDefault(player.getUUID(), 0.0);
    }

    public static double getFireDamageReduction(Player player) {
        return fireDamageReductionMap.getOrDefault(player.getUUID(), 0.0);
    }

    public static double getTotalDamageIncrease(Player player) {
        return generalDamageIncreaseMap.getOrDefault(player.getUUID(), 0.0);
    }

    public static void updateDamageReduction(Player player) {
        double totalReduction = DamageCalculator.calculateTotalDamageReduction(player);
        double fireReduction = DamageCalculator.calculateFireDamageReduction(player);

        generalDamageReductionMap.put(player.getUUID(), totalReduction);
        fireDamageReductionMap.put(player.getUUID(), fireReduction);
    }

    public static void updateDamageIncrease(Player player) {
        double generalIncrease = DamageCalculator.calculateDamageIncrease(player);

        generalDamageIncreaseMap.put(player.getUUID(), generalIncrease);
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.isCanceled()) return;
        LivingEntity livingEntity = event.getEntity();
        UUID targetId = livingEntity.getUUID();
        DamageSource source = event.getSource();
        if (livingEntity instanceof Player player) {
            double generalReduction = getTotalDamageReduction(player);
            double fireReduction = getFireDamageReduction(player);

            double finalReduction = event.getAmount();

            if (MAUtils.isFire(event)) {
                // Combines damage reductions to be one value for fire damage reduction
                double combinedFireReduction = generalReduction + fireReduction;
                finalReduction = combinedFireReduction >= 1.0 ? 0.0 : finalReduction * (1.0 - combinedFireReduction);
                MAUtils.logDebug("Fire Damage Reduction for " + player.getName().getString() + ": " + String.format("%.2f", fireReduction * 100) + "%");
            } else {
                finalReduction *= (1.0 - generalReduction);
            }

            if (CurioHandler.isCurioEquipped(player, MAItems.TaintedMirror.get())) {
                if (source.getEntity() != null && source.getEntity() instanceof LivingEntity attacker) {
                    float damageToReflect = event.getAmount();

                    if (attacker instanceof Player) {
                        damageToReflect *= CommonConfig.TAINTED_MIRROR_PLAYER_DAMAGE.get() ? 1.0F : 0.50F;
                    }

                    attacker.hurt(event.getSource(), damageToReflect);
                    player.invulnerableTime += 12;
                }
            }

            if (source.is(DamageTypes.ARROW) || SpartanWeaponryCompat.isArmorPiercingBolt(source)) {
                if (CurioHandler.isCurioEquipped(player, MAItems.WoodenHeadgear.get())) {
                    finalReduction *= (1.0 - CommonConfig.WOODEN_HEADGEAR_ARROW_DAMAGE_REDUCTION.get());
                } else if (CurioHandler.isCurioEquipped(player, MAItems.GoldenHeadgear.get())) {
                    finalReduction *= (1.0 - CommonConfig.GOLDEN_HEADGEAR_ARROW_DAMAGE_REDUCTION.get());
                } else if (CurioHandler.isCurioEquipped(player, MAItems.NetheriteHeadgear.get())) {
                    finalReduction *= (1.0 - CommonConfig.NETHERITE_HEADGEAR_ARROW_DAMAGE_REDUCTION.get());
                }
            }

            if (CurioHandler.isCurioEquipped(player, MAItems.FrostedShield.get()) || ShieldHandler.isShieldEquipped(player, MAItems.FrostedShield.get())) {
                if (player.getHealth() <= player.getMaxHealth() / 2 || player.getHealth() <= (player.getMaxHealth() / 2) + (BrokenHeartSystem.getBrokenHearts(player) * 2)) {
                    finalReduction *= (1.0 - CommonConfig.FROSTED_SHIELD_DAMAGE_REDUCTION.get());
                }
            }

            event.setAmount((float) finalReduction);

            MAUtils.logDebug("Total Damage Reduction for " + player.getName().getString() + ": " + String.format("%.2f", generalReduction * 100) + "%");
        }

        if (event.getSource().getEntity() instanceof Player player) {
            LivingEntity target = event.getEntity();
            RandomSource random = player.getRandom();
            double generalIncrease = 0.0;

            if (!ModLoadedUtil.isApothecaryLoaded()) {
                if (CurioHandler.isCurioEquipped(player, MAItems.GildedScarf.get())) {
                    generalIncrease += DamageCalculator.getDamageIncrease(player, MAItems.GildedScarf.get(), CommonConfig.GILDED_DAMAGE_DEALT.get());
                }
            }

            if (CurioHandler.isCurioEquipped(player, MAItems.BrokenHeart.get())) {
                if (player instanceof ServerPlayer serverPlayer) {
                    int brokenHeartsCount = BrokenHeartSystem.getBrokenHearts(serverPlayer);

                    if (brokenHeartsCount > 0) {
                        generalIncrease += (brokenHeartsCount * (CommonConfig.BROKEN_HEART_DAMAGE_INCREASE.get() - 1));
                    }
                }
            }

            int dragonClawCount = CurioHandler.getCurioCount(player, MAItems.EnderDragonClaw.get());
            if (LuckHelper.roll(player, CommonConfig.ENDER_DRAGON_CLAW_CHANCE.get(), CommonConfig.ENDER_DRAGON_CLAW_LUCK_FACTOR.get())) {
                for (int i = 0; i < dragonClawCount; i++) {
                    float randomPitch = 1.3f + random.nextFloat() * 0.2f;
                    player.level().playSound(null, player.blockPosition(), SoundEvents.ENDER_DRAGON_HURT, SoundSource.PLAYERS, 0.6f, randomPitch);
                    generalIncrease += DamageCalculator.getDamageIncrease(player, MAItems.EnderDragonClaw.get(), CommonConfig.ENDER_DRAGON_CLAW_DAMAGE.get());
                }
            }

            int luckyRingCount = CurioHandler.getCurioCount(player, MAItems.LuckyEmeraldRing.get());
            for (int i = 0; i < luckyRingCount; i++) {
                if (target.getMobType() == MobType.ILLAGER) {
                    generalIncrease += DamageCalculator.getDamageIncrease(player, MAItems.LuckyEmeraldRing.get(), CommonConfig.EMERALD_RING_DAMAGE.get());
                }
            }

            int venomStoneCount = CurioHandler.getCurioCount(player, MAItems.VenomStone.get());
            for (int i = 0; i < venomStoneCount; i++) {
                if (target.hasEffect(MobEffects.POISON) || target.hasEffect(MAEffects.Virulent.get())) {
                    generalIncrease += DamageCalculator.getDamageIncrease(player, MAItems.VenomStone.get(), CommonConfig.VENOM_STONE_DAMAGE.get());
                    MobEffectInstance poisonEffect = target.getEffect(MobEffects.POISON);
                    if (poisonEffect != null) {
                        int poisonDuration = poisonEffect.getDuration();
                        int poisonAmplifier = poisonEffect.getAmplifier();
                        double deadlyChance = CommonConfig.VENOM_STONE_DEADLY_CHANCE.get() * venomStoneCount;
                        if (LuckHelper.roll(player, deadlyChance, CommonConfig.VENOM_STONE_LUCK_FACTOR.get())) {
                            target.removeEffect(MobEffects.POISON);
                            target.addEffect(new MobEffectInstance(MAEffects.Virulent.get(), poisonDuration, poisonAmplifier));
                        }
                    }
                } else if (!target.hasEffect(MAEffects.Virulent.get())) {
                    double poisonChance = CommonConfig.VENOM_STONE_CHANCE.get() * venomStoneCount;
                    if (LuckHelper.roll(player, poisonChance, CommonConfig.VENOM_STONE_LUCK_FACTOR.get())) {
                        target.addEffect(new MobEffectInstance(MobEffects.POISON, 20 * CommonConfig.VENOM_STONE_DURATION.get(), CommonConfig.VENOM_STONE_LEVEL.get() - 1));
                    }
                }
            }

            int decayStoneCount = CurioHandler.getCurioCount(player, MAItems.DecayStone.get());
            for (int i = 0; i < decayStoneCount; i++) {
                if (target.hasEffect(MobEffects.WITHER)) {
                    generalIncrease += DamageCalculator.getDamageIncrease(player, MAItems.DecayStone.get(), CommonConfig.DECAY_STONE_DAMAGE.get());
                    long currentTime = player.level().getGameTime();
                    Long lastHealTime = lastHealTimeMap.get(player.getUUID());
                    if (lastHealTime == null || currentTime - lastHealTime > 20) {
                        int healAmount = CommonConfig.DECAY_STONE_HEAL_AMOUNT.get() * decayStoneCount;
                        player.heal(2 * healAmount);
                        lastHealTimeMap.put(player.getUUID(), currentTime);
                    }
                }

                double decayChance = CommonConfig.DECAY_STONE_CHANCE.get() * decayStoneCount;
                if (LuckHelper.roll(player, decayChance, CommonConfig.DECAY_STONE_LUCK_FACTOR.get())) {
                    target.addEffect(new MobEffectInstance(MobEffects.WITHER, 20 * CommonConfig.DECAY_STONE_DURATION.get(), CommonConfig.DECAY_STONE_LEVEL.get() - 1));
                }
            }

            int fireStoneCount = CurioHandler.getCurioCount(player, MAItems.FireStone.get());
            for (int i = 0; i < fireStoneCount; i++) {
                if (target.isOnFire()) {
                    generalIncrease += DamageCalculator.getDamageIncrease(player, MAItems.FireStone.get(), CommonConfig.FIRE_STONE_DAMAGE.get());
                }

                double fireChance = CommonConfig.FIRE_STONE_CHANCE.get() * fireStoneCount;
                if (LuckHelper.roll(player, fireChance, CommonConfig.FIRE_STONE_LUCK_FACTOR.get())) {
                    target.setSecondsOnFire(CommonConfig.FIRE_STONE_DURATION.get());
                }
            }

            int iceStoneCount = CurioHandler.getCurioCount(player, MAItems.IceStone.get());
            for (int i = 0; i < iceStoneCount; i++) {
                if (target.hasEffect(MobEffects.MOVEMENT_SLOWDOWN) || target.isFreezing()) {
                    generalIncrease += DamageCalculator.getDamageIncrease(player, MAItems.IceStone.get(), CommonConfig.ICE_STONE_DAMAGE.get());
                }

                double iceChance = CommonConfig.ICE_STONE_CHANCE.get() * iceStoneCount;
                if (LuckHelper.roll(player, iceChance, CommonConfig.ICE_STONE_LUCK_FACTOR.get())) {
                    target.setTicksFrozen(100 * CommonConfig.ICE_STONE_DURATION.get());
                    target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * CommonConfig.ICE_STONE_DURATION.get(), 0, false, false));
                }
            }

            if (event.getSource().is(DamageTypes.ARROW) || SpartanWeaponryCompat.isArmorPiercingBolt(event.getSource())) {
                int magicQuiverCount = CurioHandler.getCurioCount(player, MAItems.MagicQuiver.get());
                for (int i = 0; i < magicQuiverCount; i++) {
                    if (!ModLoadedUtil.isApothecaryLoaded()) {
                        generalIncrease += DamageCalculator.getDamageIncrease(player, MAItems.MagicQuiver.get(), CommonConfig.MAGIC_QUIVER_DAMAGE.get());
                    }
                }

                int envenomedQuiverCount = CurioHandler.getCurioCount(player, MAItems.EnvenomedQuiver.get());
                for (int i = 0; i < envenomedQuiverCount; i++) {
                    if (!ModLoadedUtil.isApothecaryLoaded()) {
                        generalIncrease += DamageCalculator.getDamageIncrease(player, MAItems.EnvenomedQuiver.get(), CommonConfig.ENVENOMED_QUIVER_DAMAGE.get());
                    }
                    target.addEffect(new MobEffectInstance(MobEffects.POISON, 20 * CommonConfig.ENVENOMED_QUIVER_POISON_DURATION.get(), CommonConfig.ENVENOMED_QUIVER_POISON_LEVEL.get()));
                    target.addEffect(new MobEffectInstance(MobEffects.WITHER, 20 * CommonConfig.ENVENOMED_QUIVER_WITHER_DURATION.get(), CommonConfig.ENVENOMED_QUIVER_WITHER_LEVEL.get()));
                }

                int moltenQuiverCount = CurioHandler.getCurioCount(player, MAItems.MoltenQuiver.get());
                for (int i = 0; i < moltenQuiverCount; i++) {
                    if (!ModLoadedUtil.isApothecaryLoaded()) {
                        generalIncrease += DamageCalculator.getDamageIncrease(player, MAItems.MoltenQuiver.get(), CommonConfig.MOLTEN_QUIVER_DAMAGE.get());
                    }
                    target.setSecondsOnFire(CommonConfig.MOLTEN_QUIVER_DURATION.get());

                    if (target.isOnFire()) {
                        generalIncrease += DamageCalculator.getDamageIncrease(player, MAItems.MoltenQuiver.get(), CommonConfig.MOLTEN_QUIVER_ONFIRE_DAMAGE.get());
                    }
                }
                int frozenQuiverCount = CurioHandler.getCurioCount(player, MAItems.FrozenQuiver.get());
                for (int i = 0; i < frozenQuiverCount; i++) {
                    if (!ModLoadedUtil.isApothecaryLoaded()) {
                        generalIncrease += DamageCalculator.getDamageIncrease(player, MAItems.FrozenQuiver.get(), CommonConfig.FROZEN_QUIVER_DAMAGE.get());
                    }
                    float frozenDamage = event.getAmount();
                    int roundedFreeze = Math.round(frozenDamage);
                    int totalFreeze = (roundedFreeze + 10) * CommonConfig.FROZEN_QUIVER_MULTIPLIER.get();
                    int freezeCap = Math.min(totalFreeze, 200);
                    target.setTicksFrozen(target.getTicksFrozen() + freezeCap);
                    target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, totalFreeze / 2, 0, false, false));
                }
            } else if (source.is(DamageTypes.PLAYER_ATTACK)) {
                int frostGauntletCount = CurioHandler.getCurioCount(player, MAItems.FrostGauntlet.get());
                for (int i = 0; i < frostGauntletCount; i++) {
                    int frozenTicks = target.getTicksFrozen();
                    target.setTicksFrozen((frozenTicks + 80));
                }
            }

            if (CurioHandler.isCurioEquipped(player, MAItems.VanirMask.get())) {
                event.setAmount((float) (event.getAmount() + CommonConfig.VANIR_MASK_DAMAGE_INCREASE.get()));
            }

            int rubyRingCount = CurioHandler.getCurioCount(player, MAItems.RubyRing.get());
            for (int i = 0; i < rubyRingCount; i++) {
                float maxHealth = player.getMaxHealth();
                float additionalDamagePercentage = 1.0F;
                double configDamageIncrease = CommonConfig.RUBY_RING_DAMAGE_INCREASE.get();
                double healthThreshold = CommonConfig.RUBY_RING_HEALTH_THRESHOLD.get();
                double healthThresholdCap = CommonConfig.RUBY_RING_HEALTH_CAP.get(); // Get the max threshold count from config

                int maxAllowedThresholds = (int) (healthThresholdCap / healthThreshold);

                int thresholdCount = (int) (maxHealth / healthThreshold);
                thresholdCount = Math.min(thresholdCount, maxAllowedThresholds);

                for (int h = 0; h < thresholdCount; h++) {
                    additionalDamagePercentage += (float) configDamageIncrease;
                }
                generalIncrease += DamageCalculator.getDamageIncrease(player, MAItems.RubyRing.get(), additionalDamagePercentage);
            }

            ItemStack equippedSoul = SoulUtil.isSoulEquipped(player);
            if (!equippedSoul.isEmpty() && equippedSoul.is(MAItems.ShadowSoul.get())) {
                BlockPos pos = target.blockPosition();
                int lightLevel = target.level().getMaxLocalRawBrightness(pos);
                if (lightLevel <= 7) {
                    int soulLevel = SoulUtil.getSoulStage(equippedSoul);
                    double damageScale = 1.0 + (soulLevel * 0.10);
                    generalIncrease += DamageCalculator.getDamageIncrease(player, MAItems.ShadowSoul.get(), damageScale);
                }
            } else if (SoulType.SHADOW.key.equals(player.getPersistentData().getString(SoulUtil.SOUL_KEY))) {
                BlockPos pos = target.blockPosition();
                int lightLevel = target.level().getMaxLocalRawBrightness(pos);
                if (lightLevel <= 7) {
                    generalIncrease += 0.50;
                }
            }
            if (!equippedSoul.isEmpty() && equippedSoul.is(MAItems.BloodSoul.get())) {
                int soulLevel = SoulUtil.getSoulStage(equippedSoul);
                if (soulLevel == 1) {
                    if (LuckHelper.roll(player, 0.50, 0.05)) {
                        player.heal(1);
                    }
                } else if (soulLevel == 2) {
                    if (LuckHelper.roll(player, 0.50, 0.05)) {
                        player.heal(2);
                    }
                } else if (soulLevel == 3) {
                    player.heal(event.getAmount() * 0.10F);
                } else if (soulLevel == 4) {
                    player.heal(event.getAmount() * 0.15F + 2);
                }
            } else if (SoulType.BLOOD.key.equals(player.getPersistentData().getString(SoulUtil.SOUL_KEY))) {
                player.heal(event.getAmount() * 0.20F + 4);
            }

            generalIncrease = Math.min(generalIncrease, CommonConfig.MAX_DAMAGE_INCREASE.get());
            event.setAmount((float) (event.getAmount() * (1.0 + generalIncrease)));

            MAUtils.logDebug("Total Damage Increase for " + player.getName().getString() + ": " + String.format("%.2f", generalIncrease * 100) + "%");
        }
        if (MAStatusEffects.bleeding().containsKey(targetId)) {
            event.setAmount((float) (event.getAmount() * (1 + CommonConfig.BLEED_DAMAGE_RECEIVED.get())));
        } else if (MAStatusEffects.frostedWounds().containsKey(targetId)) {
            event.setAmount((float) (event.getAmount() * (1 + CommonConfig.FROSTED_WOUNDS_DAMAGE_RECEIVED.get())));
        }
    }

    @SubscribeEvent
    public static void onTick(TickEvent.PlayerTickEvent event) {
        if (event.player.level().isClientSide) return;
        if (event.phase != TickEvent.Phase.START) return;
        Player player = event.player;
        if (player.tickCount % 5 != 0) return;

        UUID playerUUID = player.getUUID();
        Map<Item, Boolean> equippedArtifacts = equippedArtifactsMap.getOrDefault(playerUUID, new HashMap<>());

        Map<Item, Boolean> currentEquippedState = DamageCalculator.getCurrentEquipState(player);
        if (!currentEquippedState.equals(equippedArtifacts)) {
            updateDamageReduction(player);
            updateDamageIncrease(player);

            equippedArtifactsMap.put(playerUUID, currentEquippedState);

            MAUtils.logDebug("Artifacts equipped for " + player.getName().getString() + ": " + currentEquippedState);
        }
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        if (event.isCanceled()) return;
        LivingEntity target = event.getEntity();
        if (!(event.getSource().getDirectEntity() instanceof Player player)) {
            return;
        }
        if (!event.getSource().is(DamageTypes.PLAYER_ATTACK)) {
            return;
        }
        if (target instanceof AbstractSkeleton || target instanceof AbstractGolem || target instanceof Slime) {
            return;
        }
        ResourceLocation entityName = ForgeRegistries.ENTITY_TYPES.getKey(target.getType());
        if (entityName != null && CommonConfig.BLEED_BLACKLISTED_ENTITIES.get().contains(entityName.toString())) {
            return;
        }
        UUID targetId = target.getUUID();

        int clawCount = CurioHandler.getCurioCount(player, MAItems.MechanicalClaw.get());
        if (clawCount > 0) {
            int bleedDuration = (CommonConfig.MECHANICAL_CLAW_BLEED_DURATION.get() * 20) * clawCount;
            double bleedChance = CommonConfig.MECHANICAL_CLAW_BLEED_CHANCE.get() * clawCount;
            if (LuckHelper.roll(player, bleedChance, CommonConfig.MECHANICAL_CLAW_LUCK_FACTOR.get())) {
                if (MAStatusEffects.frostedWounds().containsKey(targetId)) {
                    int frostTicks = MAStatusEffects.frostedWounds().getOrDefault(targetId, 0);
                    MAStatusEffects.frostedWounds().put(targetId, frostTicks + bleedDuration);
                } else {
                    MAStatusEffects.bleeding().put(targetId, bleedDuration);
                }
            }
        }

        int frostCount = CurioHandler.getCurioCount(player, MAItems.FrostGauntlet.get());
        if (frostCount > 0 && MAStatusEffects.bleeding().containsKey(targetId) && target.getTicksFrozen() > 0) {
            MAStatusEffects.bleeding().remove(targetId);
            int baseDuration = 20 * CommonConfig.FROSTED_WOUNDS_DURATION.get();
            int frostTicks = MAStatusEffects.frostedWounds().getOrDefault(targetId, 0);
            MAStatusEffects.frostedWounds().put(targetId, frostTicks + (baseDuration * frostCount));
        }
    }

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide) return;

        UUID entityId = entity.getUUID();
        if (MAStatusEffects.bleeding().containsKey(entityId)) {
            int remainingTicks = MAStatusEffects.bleeding().get(entityId);
            if (remainingTicks <= 0) {
                MAStatusEffects.bleeding().remove(entityId);
                return;
            }

            if (remainingTicks % 20 == 0) {
                DamageSource bleedDamage = entity.level().damageSources().magic();
                int prevInvulnerableTime = entity.invulnerableTime;
                entity.hurt(bleedDamage, CommonConfig.MECHANICAL_CLAW_BLEED_DAMAGE.get());
                entity.invulnerableTime = prevInvulnerableTime;
            }
            MAStatusEffects.bleeding().put(entityId, remainingTicks - 1);
            if (entity.level() instanceof ServerLevel serverLevel && remainingTicks % 20 == 0) {
                int particleCount = 5 + entity.getRandom().nextInt(6);
                serverLevel.sendParticles(MAParticles.BLOOD.get(), entity.getX(), entity.getY() + (entity.getBbHeight() * 0.5), entity.getZ(), particleCount, 0.3, 0.3, 0.2, 0.01);
            }
        }

        if (MAStatusEffects.frostedWounds().containsKey(entityId)) {
            int remainingTicks = MAStatusEffects.frostedWounds().get(entityId);
            if (remainingTicks <= 0) {
                MAStatusEffects.frostedWounds().remove(entityId);
                return;
            }

            if (remainingTicks % 20 == 0) {
                DamageSource frostedWoundsDamage = entity.level().damageSources().freeze();
                int prevInvulnerableTime = entity.invulnerableTime;
                entity.hurt(frostedWoundsDamage, CommonConfig.FROSTED_WOUNDS_DAMAGE.get());
                entity.setTicksFrozen(CommonConfig.FROSTED_WOUNDS_DURATION.get() * 15);
                entity.invulnerableTime = prevInvulnerableTime;
            }
            MAStatusEffects.frostedWounds().put(entityId, remainingTicks - 1);
            if (entity.level() instanceof ServerLevel serverLevel && remainingTicks % 20 == 0) {
                int particleCount = 5 + entity.getRandom().nextInt(6);
                serverLevel.sendParticles(ParticleTypes.SNOWFLAKE, entity.getX(), entity.getY() + (entity.getBbHeight() * 0.5), entity.getZ(), particleCount, 0.3, 0.3, 0.2, 0.01);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.isCanceled()) return;
        LivingEntity target = event.getEntity();
        Entity source = event.getSource().getEntity();
        if (source instanceof Player attacker && target.isOnFire()) {
            if (CurioHandler.isCurioEquipped(attacker, MAItems.FireStone.get())) {
                Level world = target.level();
                BlockPos targetPos = target.blockPosition();
                int radiusXZ = 7;
                int radiusY = 4;
                AABB area = new AABB(targetPos.offset(-radiusXZ, -radiusY, -radiusXZ), targetPos.offset(radiusXZ + 1, radiusY + 1, radiusXZ + 1));
                List<LivingEntity> nearbyEntities = world.getEntitiesOfClass(LivingEntity.class, area);

                for (LivingEntity entity : nearbyEntities) {
                    if (entity instanceof Enemy) {
                        if (entity != attacker && attacker.hasLineOfSight(entity)) {
                            entity.setSecondsOnFire(2 * CommonConfig.FIRE_STONE_DURATION.get());
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onProjectileSpawn(EntityJoinLevelEvent event) {
        if (!(event.getEntity() instanceof Projectile projectile)) return;
        if (!(projectile.getOwner() instanceof Player player)) return;
        if (event.getLevel().isClientSide()) return;
        boolean isArrow = projectile instanceof AbstractArrow;
        boolean isBolt = SpartanWeaponryCompat.isArmorPiercingBolt(projectile);
        if (!isArrow && !isBolt) return;

        int magicQuiverCount = CurioHandler.getCurioCount(player, MAItems.MagicQuiver.get());
        int envenomedQuiverCount = CurioHandler.getCurioCount(player, MAItems.EnvenomedQuiver.get());
        int moltenQuiverCount = CurioHandler.getCurioCount(player, MAItems.MoltenQuiver.get());
        int frozenQuiverCount = CurioHandler.getCurioCount(player, MAItems.FrozenQuiver.get());

        for (int i = 0; i < magicQuiverCount; i++) {
            double velocityMultiplier = 1.0 + (CommonConfig.MAGIC_QUIVER_VELOCITY.get() * magicQuiverCount);
            Vec3 motion = projectile.getDeltaMovement();
            projectile.setDeltaMovement(motion.scale(velocityMultiplier));
        }
        for (int i = 0; i < envenomedQuiverCount; i++) {
            double velocityMultiplier = 1.0 + (CommonConfig.ENVENOMED_QUIVER_VELOCITY.get() * envenomedQuiverCount);
            Vec3 motion = projectile.getDeltaMovement();
            projectile.setDeltaMovement(motion.scale(velocityMultiplier));
        }
        for (int i = 0; i < moltenQuiverCount; i++) {
            double velocityMultiplier = 1.0 + (CommonConfig.MOLTEN_QUIVER_VELOCITY.get() * moltenQuiverCount);
            Vec3 motion = projectile.getDeltaMovement();
            projectile.setDeltaMovement(motion.scale(velocityMultiplier));
        }
        for (int i = 0; i < frozenQuiverCount; i++) {
            double velocityMultiplier = 1.0 + (CommonConfig.FROZEN_QUIVER_VELOCITY.get() * frozenQuiverCount);
            Vec3 motion = projectile.getDeltaMovement();
            projectile.setDeltaMovement(motion.scale(velocityMultiplier));
        }
        if (CurioHandler.isCurioEquipped(player, MAItems.MoltenQuiver.get())) {
            projectile.setSecondsOnFire(CommonConfig.MOLTEN_QUIVER_DURATION.get());
        }
    }

    public static void clearMaps(UUID uuid) {
        generalDamageReductionMap.remove(uuid);
        fireDamageReductionMap.remove(uuid);
        generalDamageIncreaseMap.remove(uuid);
        equippedArtifactsMap.remove(uuid);
        lastHealTimeMap.remove(uuid);
    }
}