package net.gobies.moreartifacts.util;

import net.gobies.moreartifacts.Config;
import net.gobies.moreartifacts.compat.spartanweaponry.SpartanWeaponryCompat;
import net.gobies.moreartifacts.init.MAItems;
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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DamageManager {
    private static final Map<Player, Double> generalDamageReductionMap = new HashMap<>();
    private static final Map<Player, Double> fireDamageReductionMap = new HashMap<>();
    private static final Map<Player, Double> generalDamageIncreaseMap = new HashMap<>();
    private static final Map<Player, Map<Item, Boolean>> lastEquippedStateMap = new HashMap<>();

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
        double totalReduction = calculateTotalDamageReduction(player);
        double fireReduction = calculateFireDamageReduction(player);

        generalDamageReductionMap.put(player, totalReduction);
        fireDamageReductionMap.put(player, fireReduction);
    }

    public static void updateDamageIncrease(Player player) {
        double generalIncrease = calculateDamageIncrease(player);

        generalDamageIncreaseMap.put(player, generalIncrease);
    }

    private static double calculateTotalDamageReduction(Player player) {
        double generalReduction = 0.0;

        generalReduction += getDamageReduction(player, MAItems.EnderianScarf.get(), Config.ENDERIAN_DAMAGE_TAKEN.get());
        generalReduction += getDamageReduction(player, MAItems.GildedScarf.get(), Config.GILDED_DAMAGE_TAKEN.get());
        generalReduction += getDamageReduction(player, MAItems.TrueEnderianScarf.get(), Config.TRUE_ENDERIAN_DAMAGE_TAKEN.get());

        return Math.min(generalReduction, Config.MAX_DAMAGE_REDUCTION.get()); // Damage Reduction Cap
    }

    private static double calculateFireDamageReduction(Player player) {
        double fireReduction = 0.0;

        fireReduction += getDamageReduction(player, MAItems.ObsidianSkull.get(), Config.SKULL_FIRE_DAMAGE_TAKEN.get());
        fireReduction += getDamageReduction(player, MAItems.ObsidianShield.get(), Config.OBSIDIAN_SHIELD_FIRE_DAMAGE_TAKEN.get());
        fireReduction += getDamageReduction(player, MAItems.AnkhShield.get(), Config.ANKH_SHIELD_FIRE_DAMAGE_TAKEN.get());

        return Math.min(fireReduction, Config.MAX_FIRE_DAMAGE_REDUCTION.get()); // Fire Damage Reduction Cap
    }

    private static double calculateDamageIncrease(Player player) {
        double generalIncrease = 0.0;

        generalIncrease += getDamageIncrease(player, MAItems.GildedScarf.get(), Config.GILDED_DAMAGE_DEALT.get());
        generalIncrease += getDamageIncrease(player, MAItems.EnderDragonClaw.get(), Config.ENDER_DRAGON_CLAW_DAMAGE.get());
        generalIncrease += getDamageIncrease(player, MAItems.LuckyEmeraldRing.get(), Config.EMERALD_RING_DAMAGE.get());
        generalIncrease += getDamageIncrease(player, MAItems.VenomStone.get(), Config.VENOM_STONE_DAMAGE.get());
        generalIncrease += getDamageIncrease(player, MAItems.DecayStone.get(), Config.DECAY_STONE_DAMAGE.get());
        generalIncrease += getDamageIncrease(player, MAItems.FireStone.get(), Config.FIRE_STONE_DAMAGE.get());
        generalIncrease += getDamageIncrease(player, MAItems.IceStone.get(), Config.ICE_STONE_DAMAGE.get());
        generalIncrease += getDamageIncrease(player, MAItems.MagicQuiver.get(), Config.MAGIC_QUIVER_DAMAGE.get());
        generalIncrease += getDamageIncrease(player, MAItems.EnvenomedQuiver.get(), Config.ENVENOMED_QUIVER_DAMAGE.get());
        generalIncrease += getDamageIncrease(player, MAItems.MoltenQuiver.get(), Config.MOLTEN_QUIVER_DAMAGE.get());
        generalIncrease += getDamageIncrease(player, MAItems.MoltenQuiver.get(), Config.MOLTEN_QUIVER_ONFIRE_DAMAGE.get());

        return Math.min(generalIncrease, Config.MAX_DAMAGE_INCREASE.get());
    }

    private static double getDamageReduction(Player player, Item item, double configValue) {
        if (item == MAItems.ObsidianShield.get() || item == MAItems.AnkhShield.get()) {
            return CurioHandler.isCurioEquipped(player, item) ? 1.0 - configValue : ShieldHandler.isShieldEquipped(player, item) ? 1.0 - configValue : 0.0;
        } else {
            return CurioHandler.isCurioEquipped(player, item) ? 1.0 - configValue : 0.0;
        }
    }

    private static double getDamageIncrease(Player player, Item item, double configValue) {
        return CurioHandler.isCurioEquipped(player, item) ? configValue - 1.0 : 0.0;
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

            generalIncrease += getDamageIncrease(player, MAItems.GildedScarf.get(), Config.GILDED_DAMAGE_DEALT.get());

            if (player.getRandom().nextFloat() < Config.ENDER_DRAGON_CLAW_CHANCE.get() && CurioHandler.isCurioEquipped(player, MAItems.EnderDragonClaw.get())) {
                generalIncrease += getDamageIncrease(player, MAItems.EnderDragonClaw.get(), Config.ENDER_DRAGON_CLAW_DAMAGE.get());
                player.level().playSound(null, player.blockPosition(), SoundEvents.ENDER_DRAGON_HURT, SoundSource.PLAYERS, 0.6f, 1.4f);
            }

            if (target.getMobType() == MobType.ILLAGER) {
                generalIncrease += getDamageIncrease(player, MAItems.LuckyEmeraldRing.get(), Config.EMERALD_RING_DAMAGE.get());
            }

            if (target.hasEffect(MobEffects.POISON)) {
                generalIncrease += getDamageIncrease(player, MAItems.VenomStone.get(), Config.VENOM_STONE_DAMAGE.get());
            }

            if (target.hasEffect(MobEffects.WITHER)) {
                generalIncrease += getDamageIncrease(player, MAItems.DecayStone.get(), Config.DECAY_STONE_DAMAGE.get());
            }

            if (target.isOnFire()) {
                generalIncrease += getDamageIncrease(player, MAItems.FireStone.get(), Config.FIRE_STONE_DAMAGE.get());
            }

            if (target.hasEffect(MobEffects.MOVEMENT_SLOWDOWN) || target.isFreezing()) {
                generalIncrease += getDamageIncrease(player, MAItems.IceStone.get(), Config.ICE_STONE_DAMAGE.get());
            }

            if (event.getSource().is(DamageTypes.ARROW) || SpartanWeaponryCompat.isArmorPiercingBolt(event.getSource())) {
                generalIncrease += getDamageIncrease(player, MAItems.MagicQuiver.get(), Config.MAGIC_QUIVER_DAMAGE.get());
                generalIncrease += getDamageIncrease(player, MAItems.EnvenomedQuiver.get(), Config.ENVENOMED_QUIVER_DAMAGE.get());
                generalIncrease += getDamageIncrease(player, MAItems.MoltenQuiver.get(), Config.MOLTEN_QUIVER_DAMAGE.get());

                if (target.isOnFire()) {
                    generalIncrease += getDamageIncrease(player, MAItems.MoltenQuiver.get(), Config.MOLTEN_QUIVER_ONFIRE_DAMAGE.get());
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
            Map<Item, Boolean> currentEquippedState = new HashMap<>();

            List<Item> itemsToCheck = Arrays.asList(
                    MAItems.EnderianScarf.get(),
                    MAItems.GildedScarf.get(),
                    MAItems.TrueEnderianScarf.get(),
                    MAItems.ObsidianSkull.get(),
                    MAItems.ObsidianShield.get(),
                    MAItems.AnkhShield.get(),
                    MAItems.LuckyEmeraldRing.get(),
                    MAItems.VenomStone.get(),
                    MAItems.DecayStone.get(),
                    MAItems.FireStone.get(),
                    MAItems.IceStone.get(),
                    MAItems.MagicQuiver.get(),
                    MAItems.EnvenomedQuiver.get(),
                    MAItems.MoltenQuiver.get(),
                    MAItems.EnderDragonClaw.get()
            );

            for (Item item : itemsToCheck) {
                boolean isEquipped = CurioHandler.isCurioEquipped(player, item);
                if (isEquipped) {
                    currentEquippedState.put(item, true);
                }
            }

            for (Item item : Arrays.asList(MAItems.ObsidianShield.get(), MAItems.AnkhShield.get())) {
                if (ShieldHandler.isShieldEquipped(player, item)) {
                    currentEquippedState.put(item, true);
                }
            }

            Map<Item, Boolean> lastEquippedState = lastEquippedStateMap.getOrDefault(player, new HashMap<>());

            if (!currentEquippedState.equals(lastEquippedState)) {
                updateDamageReduction(player);
                updateDamageIncrease(player);

                lastEquippedStateMap.put(player, currentEquippedState);

                System.out.println("Last Equipped State Map for " + player.getName().getString() + ": " + currentEquippedState);
            }
        }
    }
}