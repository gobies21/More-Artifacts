package net.gobies.moreartifacts.event;

import net.gobies.moreartifacts.Config;
import net.gobies.moreartifacts.compat.spartanweaponry.SpartanWeaponryCompat;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.util.CurioHandler;
import net.gobies.moreartifacts.util.DamageCalculator;
import net.gobies.moreartifacts.util.MAUtils;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;

public class DamageEvents {
    private static final Map<Player, Double> generalDamageReductionMap = new HashMap<>();
    private static final Map<Player, Double> fireDamageReductionMap = new HashMap<>();
    private static final Map<Player, Double> generalDamageIncreaseMap = new HashMap<>();
    private static final Map<Player, Map<Item, Boolean>> equippedArtifactsMap = new HashMap<>();

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
        if (event.getEntity() instanceof Player player) {
            double generalReduction = getTotalDamageReduction(player);
            double fireReduction = getFireDamageReduction(player);

            double finalReduction = event.getAmount();

            if (MAUtils.isFireDMGReduced(event)) {
                // Combines damage reductions to be one value for fire damage reduction
                double combinedFireReduction = generalReduction + fireReduction;
                finalReduction = combinedFireReduction >= 1.0 ? 0.0 : finalReduction * (1.0 - combinedFireReduction);
            } else {
                finalReduction *= (1.0 - generalReduction);
            }

            event.setAmount((float) finalReduction);

            System.out.println("Total Damage Reduction for " + player.getName().getString() + ": " + String.format("%.2f", generalReduction * 100) + "%");
            System.out.println("Fire Damage Reduction for " + player.getName().getString() + ": " + String.format("%.2f", fireReduction * 100) + "%");
        }

        if (event.getSource().getEntity() instanceof Player player) {
            LivingEntity target = event.getEntity();
            double generalIncrease = 0.0;

            generalIncrease += DamageCalculator.getDamageIncrease(player, MAItems.GildedScarf.get(), Config.GILDED_DAMAGE_DEALT.get());

            if (player.getRandom().nextFloat() < Config.ENDER_DRAGON_CLAW_CHANCE.get() && CurioHandler.isCurioEquipped(player, MAItems.EnderDragonClaw.get())) {
                generalIncrease += DamageCalculator.getDamageIncrease(player, MAItems.EnderDragonClaw.get(), Config.ENDER_DRAGON_CLAW_DAMAGE.get());
                player.level().playSound(null, player.blockPosition(), SoundEvents.ENDER_DRAGON_HURT, SoundSource.PLAYERS, 0.6f, 1.4f);
            }

            if (target.getMobType() == MobType.ILLAGER) {
                generalIncrease += DamageCalculator.getDamageIncrease(player, MAItems.LuckyEmeraldRing.get(), Config.EMERALD_RING_DAMAGE.get());
            }

            if (target.hasEffect(MobEffects.POISON)) {
                generalIncrease += DamageCalculator.getDamageIncrease(player, MAItems.VenomStone.get(), Config.VENOM_STONE_DAMAGE.get());
            }

            if (target.hasEffect(MobEffects.WITHER)) {
                generalIncrease += DamageCalculator.getDamageIncrease(player, MAItems.DecayStone.get(), Config.DECAY_STONE_DAMAGE.get());
            }

            if (target.isOnFire()) {
                generalIncrease += DamageCalculator.getDamageIncrease(player, MAItems.FireStone.get(), Config.FIRE_STONE_DAMAGE.get());
            }

            if (target.hasEffect(MobEffects.MOVEMENT_SLOWDOWN) || target.isFreezing()) {
                generalIncrease += DamageCalculator.getDamageIncrease(player, MAItems.IceStone.get(), Config.ICE_STONE_DAMAGE.get());
            }

            if (event.getSource().is(DamageTypes.ARROW) || SpartanWeaponryCompat.isArmorPiercingBolt(event.getSource())) {
                generalIncrease += DamageCalculator.getDamageIncrease(player, MAItems.MagicQuiver.get(), Config.MAGIC_QUIVER_DAMAGE.get());
                generalIncrease += DamageCalculator.getDamageIncrease(player, MAItems.EnvenomedQuiver.get(), Config.ENVENOMED_QUIVER_DAMAGE.get());
                generalIncrease += DamageCalculator.getDamageIncrease(player, MAItems.MoltenQuiver.get(), Config.MOLTEN_QUIVER_DAMAGE.get());

                if (target.isOnFire()) {
                    generalIncrease += DamageCalculator.getDamageIncrease(player, MAItems.MoltenQuiver.get(), Config.MOLTEN_QUIVER_ONFIRE_DAMAGE.get());
                }
            }

            generalIncrease = Math.min(generalIncrease, Config.MAX_DAMAGE_INCREASE.get());
            event.setAmount((float) (event.getAmount() * (1.0 + generalIncrease)));

            System.out.println("Total Damage Increase for " + player.getName().getString() + ": " + String.format("%.2f", generalIncrease * 100) + "%");
        }
    }

    @SubscribeEvent
    public static void onTick(LivingEvent.LivingTickEvent event) {
        if (event.getEntity() instanceof Player player) {
            Map<Item, Boolean> currentEquippedState = DamageCalculator.getCurrentEquippedState(player);

            Map<Item, Boolean> lastEquippedState = equippedArtifactsMap.getOrDefault(player, new HashMap<>());

            if (!currentEquippedState.equals(lastEquippedState)) {
                updateDamageReduction(player);
                updateDamageIncrease(player);

                equippedArtifactsMap.put(player, currentEquippedState);

                System.out.println("Last Equipped State Map for " + player.getName().getString() + ": " + currentEquippedState);
            }
        }
    }
}