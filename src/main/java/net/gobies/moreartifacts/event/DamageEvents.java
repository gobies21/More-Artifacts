package net.gobies.moreartifacts.event;

import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.compat.spartanweaponry.SpartanWeaponryCompat;
import net.gobies.moreartifacts.init.MAEffects;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.util.CurioHandler;
import net.gobies.moreartifacts.util.DamageCalculator;
import net.gobies.moreartifacts.util.MAUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
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
    private static final Map<Player, Double> generalDamageReductionMap = new HashMap<>();
    private static final Map<Player, Double> fireDamageReductionMap = new HashMap<>();
    private static final Map<Player, Double> generalDamageIncreaseMap = new HashMap<>();
    private static final Map<Player, Map<Item, Boolean>> equippedArtifactsMap = new HashMap<>();
    private static final Map<Player, Long> lastHealTimeMap = new HashMap<>();

    public static double getTotalDamageReduction(Player player) {
        return generalDamageReductionMap.getOrDefault(player, 0.0);
    }

    public static double getFireDamageReduction(Player player) {
        return fireDamageReductionMap.getOrDefault(player, 0.0);
    }

    public static double getTotalDamageIncrease(Player player) {
        return generalDamageIncreaseMap.getOrDefault(player, 0.0);
    }

    public static void updateDamageReduction(Player player) {
        double totalReduction = DamageCalculator.calculateTotalDamageReduction(player);
        double fireReduction = DamageCalculator.calculateFireDamageReduction(player);

        generalDamageReductionMap.put(player, totalReduction);
        fireDamageReductionMap.put(player, fireReduction);
    }

    public static void updateDamageIncrease(Player player) {
        double generalIncrease = DamageCalculator.calculateDamageIncrease(player);

        generalDamageIncreaseMap.put(player, generalIncrease);
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onLivingHurt(LivingHurtEvent event) {
        DamageSource source = event.getSource();
        if (event.getEntity() instanceof Player player) {
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

            event.setAmount((float) finalReduction);

            MAUtils.logDebug("Total Damage Reduction for " + player.getName().getString() + ": " + String.format("%.2f", generalReduction * 100) + "%");
        }

        if (event.getSource().getEntity() instanceof Player player) {
            LivingEntity target = event.getEntity();
            RandomSource random = player.getRandom();
            double generalIncrease = 0.0;

            if (CurioHandler.isCurioEquipped(player, MAItems.GildedScarf.get())) {
                generalIncrease += DamageCalculator.getDamageIncrease(player, MAItems.GildedScarf.get(), CommonConfig.GILDED_DAMAGE_DEALT.get());
            }

            int dragonClawCount = CurioHandler.getCurioCount(player, MAItems.EnderDragonClaw.get());
            if (player.getRandom().nextFloat() < CommonConfig.ENDER_DRAGON_CLAW_CHANCE.get()) {
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
                        if (random.nextFloat() < deadlyChance) {
                            target.removeEffect(MobEffects.POISON);
                            target.addEffect(new MobEffectInstance(MAEffects.Virulent.get(), poisonDuration, poisonAmplifier));
                        }
                    }
                } else if (!target.hasEffect(MAEffects.Virulent.get())) {
                    double poisonChance = CommonConfig.VENOM_STONE_CHANCE.get() * venomStoneCount;
                    if (random.nextFloat() < poisonChance) {
                        target.addEffect(new MobEffectInstance(MobEffects.POISON, 20 * CommonConfig.VENOM_STONE_DURATION.get(), CommonConfig.VENOM_STONE_LEVEL.get() - 1));
                    }
                }
            }

            int decayStoneCount = CurioHandler.getCurioCount(player, MAItems.DecayStone.get());
            for (int i = 0; i < decayStoneCount; i++) {
                if (target.hasEffect(MobEffects.WITHER)) {
                    generalIncrease += DamageCalculator.getDamageIncrease(player, MAItems.DecayStone.get(), CommonConfig.DECAY_STONE_DAMAGE.get());
                    long currentTime = player.level().getGameTime();
                    Long lastHealTime = lastHealTimeMap.get(player);
                    if (lastHealTime == null || currentTime - lastHealTime > 20) {
                        int healAmount = CommonConfig.DECAY_STONE_HEAL_AMOUNT.get() * decayStoneCount;
                        player.heal(2 * healAmount);
                        lastHealTimeMap.put(player, currentTime);
                    }
                }

                double decayChance = CommonConfig.DECAY_STONE_CHANCE.get() * decayStoneCount;
                if (random.nextFloat() < decayChance) {
                    target.addEffect(new MobEffectInstance(MobEffects.WITHER, 20 * CommonConfig.DECAY_STONE_DURATION.get(), CommonConfig.DECAY_STONE_LEVEL.get() - 1));
                }
            }

            int fireStoneCount = CurioHandler.getCurioCount(player, MAItems.FireStone.get());
            for (int i = 0; i < fireStoneCount; i++) {
                if (target.isOnFire()) {
                    generalIncrease += DamageCalculator.getDamageIncrease(player, MAItems.FireStone.get(), CommonConfig.FIRE_STONE_DAMAGE.get());
                }

                double fireChance = CommonConfig.FIRE_STONE_CHANCE.get() * fireStoneCount;
                if (random.nextFloat() < fireChance) {
                    target.setSecondsOnFire(CommonConfig.FIRE_STONE_DURATION.get());
                }
            }

            int iceStoneCount = CurioHandler.getCurioCount(player, MAItems.IceStone.get());
            for (int i = 0; i < iceStoneCount; i++) {
                if (target.hasEffect(MobEffects.MOVEMENT_SLOWDOWN) || target.isFreezing()) {
                    generalIncrease += DamageCalculator.getDamageIncrease(player, MAItems.IceStone.get(), CommonConfig.ICE_STONE_DAMAGE.get());
                }

                double iceChance = CommonConfig.ICE_STONE_CHANCE.get() * iceStoneCount;
                if (random.nextFloat() < iceChance) {
                    target.setTicksFrozen(100 * CommonConfig.ICE_STONE_DURATION.get());
                    target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * CommonConfig.ICE_STONE_DURATION.get(), 0));
                }
            }

            if (event.getSource().is(DamageTypes.ARROW) || SpartanWeaponryCompat.isArmorPiercingBolt(event.getSource())) {
                int magicQuiverCount = CurioHandler.getCurioCount(player, MAItems.MagicQuiver.get());
                for (int i = 0; i < magicQuiverCount; i++) {
                    generalIncrease += DamageCalculator.getDamageIncrease(player, MAItems.MagicQuiver.get(), CommonConfig.MAGIC_QUIVER_DAMAGE.get());
                }

                int envenomedQuiverCount = CurioHandler.getCurioCount(player, MAItems.EnvenomedQuiver.get());
                for (int i = 0; i < envenomedQuiverCount; i++) {
                    generalIncrease += DamageCalculator.getDamageIncrease(player, MAItems.EnvenomedQuiver.get(), CommonConfig.ENVENOMED_QUIVER_DAMAGE.get());
                    target.addEffect(new MobEffectInstance(MobEffects.POISON, 20 * CommonConfig.ENVENOMED_QUIVER_POISON_DURATION.get(), CommonConfig.ENVENOMED_QUIVER_POISON_LEVEL.get()));
                    target.addEffect(new MobEffectInstance(MobEffects.WITHER, 20 * CommonConfig.ENVENOMED_QUIVER_WITHER_DURATION.get(), CommonConfig.ENVENOMED_QUIVER_WITHER_LEVEL.get()));
                }

                int moltenQuiverCount = CurioHandler.getCurioCount(player, MAItems.MoltenQuiver.get());
                for (int i = 0; i < moltenQuiverCount; i++) {
                    generalIncrease += DamageCalculator.getDamageIncrease(player, MAItems.MoltenQuiver.get(), CommonConfig.MOLTEN_QUIVER_DAMAGE.get());
                    target.setSecondsOnFire(CommonConfig.MOLTEN_QUIVER_DURATION.get());

                    if (target.isOnFire()) {
                        generalIncrease += DamageCalculator.getDamageIncrease(player, MAItems.MoltenQuiver.get(), CommonConfig.MOLTEN_QUIVER_ONFIRE_DAMAGE.get());
                    }
                }

                if (CurioHandler.isCurioEquipped(player, MAItems.WoodenHeadgear.get())) {
                    event.setAmount((float) (event.getAmount() * CommonConfig.WOODEN_HEADGEAR_ARROW_DAMAGE_TAKEN.get()));
                }

                if (CurioHandler.isCurioEquipped(player, MAItems.GoldenHeadgear.get())) {
                    event.setAmount((float) (event.getAmount() * CommonConfig.GOLDEN_HEADGEAR_ARROW_DAMAGE_TAKEN.get()));
                }

                if (CurioHandler.isCurioEquipped(player, MAItems.NetheriteHeadgear.get())) {
                    event.setAmount((float) (event.getAmount() * CommonConfig.NETHERITE_HEADGEAR_ARROW_DAMAGE_TAKEN.get()));
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

            generalIncrease = Math.min(generalIncrease, CommonConfig.MAX_DAMAGE_INCREASE.get());
            event.setAmount((float) (event.getAmount() * (1.0 + generalIncrease)));

            MAUtils.logDebug("Total Damage Increase for " + player.getName().getString() + ": " + String.format("%.2f", generalIncrease * 100) + "%");
        }
    }

    @SubscribeEvent
    public static void onTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (player != null) {
            Map<Item, Boolean> currentEquippedState = DamageCalculator.getCurrentEquipState(player);

            Map<Item, Boolean> equippedArtifacts = equippedArtifactsMap.getOrDefault(player, new HashMap<>());

            if (!currentEquippedState.equals(equippedArtifacts)) {
                updateDamageReduction(player);
                updateDamageIncrease(player);

                equippedArtifactsMap.put(player, currentEquippedState);

                MAUtils.logDebug("Artifacts equipped for " + player.getName().getString() + ": " + currentEquippedState);
            }
        }
    }

    private static final Map<UUID, Integer> BLEEDING_ENTITIES = new HashMap<>();

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
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

        int clawCount = CurioHandler.getCurioCount(player, MAItems.MechanicalClaw.get());
        for (int i = 0; i < clawCount; i++) {
            int bleedDuration = (CommonConfig.MECHANICAL_CLAW_BLEED_DURATION.get() * 20) * clawCount;
            double bleedChance = CommonConfig.MECHANICAL_CLAW_BLEED_CHANCE.get() * clawCount;
            UUID targetId = target.getUUID();
            if (target.getRandom().nextFloat() < bleedChance) {
                BLEEDING_ENTITIES.put(targetId, bleedDuration);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide) return;

        UUID entityId = entity.getUUID();
        if (BLEEDING_ENTITIES.containsKey(entityId)) {
            int remainingTicks = BLEEDING_ENTITIES.get(entityId);
            if (remainingTicks <= 0) {
                BLEEDING_ENTITIES.remove(entityId);
                return;
            }

            if (remainingTicks % 20 == 0) {
                DamageSource bleedDamage = new DamageSource(entity.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MAGIC));
                int prevInvulnerableTime = entity.invulnerableTime;
                entity.hurt(bleedDamage, CommonConfig.MECHANICAL_CLAW_BLEED_DAMAGE.get());
                entity.invulnerableTime = prevInvulnerableTime;
            }

            BLEEDING_ENTITIES.put(entityId, remainingTicks - 1);
        }
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
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
                    if (entity instanceof TamableAnimal && ((TamableAnimal) entity).isTame()) {
                        continue;
                    }
                    if (entity != attacker && attacker.hasLineOfSight(entity)) {
                        entity.setSecondsOnFire(2 * CommonConfig.FIRE_STONE_DURATION.get());
                    }
                }
            }
        }
    }
}