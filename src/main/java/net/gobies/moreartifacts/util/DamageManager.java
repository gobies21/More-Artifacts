package net.gobies.moreartifacts.util;

import net.gobies.moreartifacts.Config;
import net.gobies.moreartifacts.compat.spartanweaponry.SpartanWeaponryCompat;
import net.gobies.moreartifacts.init.MAItems;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;

public class DamageManager {
    private static final Map<Player, Double> damageReductionMap = new HashMap<>();
    private static final Map<Player, Double> fireDamageReductionMap = new HashMap<>();
    private static final Map<Player, Double> damageIncreaseMap = new HashMap<>();

    public static double getTotalDamageReduction(Player player) {
        return damageReductionMap.getOrDefault(player, 0.0);
    }

    public static double getFireDamageReduction(Player player) {
        return fireDamageReductionMap.getOrDefault(player, 0.0);
    }

    public static void updateDamageReduction(Player player, LivingHurtEvent event) {
        double totalReduction = calculateTotalDamageReduction(player);
        double fireReduction = calculateFireDamageReduction(player, event);

        damageReductionMap.put(player, totalReduction);
        fireDamageReductionMap.put(player, fireReduction);
    }

    private static double calculateTotalDamageReduction(Player player) {
        double totalReduction = 0.0;

        totalReduction += getDamageReduction(player, MAItems.EnderianScarf.get(), Config.ENDERIAN_DAMAGE_TAKEN.get());
        totalReduction += getDamageReduction(player, MAItems.GildedScarf.get(), Config.GILDED_DAMAGE_TAKEN.get());
        totalReduction += getDamageReduction(player, MAItems.TrueEnderianScarf.get(), Config.TRUE_ENDERIAN_DAMAGE_TAKEN.get());

        return Math.min(totalReduction, Config.MAX_DAMAGE_REDUCTION.get()); // Damage Reduction Cap
    }

    private static double calculateFireDamageReduction(Player player, LivingHurtEvent event) {
        double fireReduction = 0.0;

        if (event.getSource().is(DamageTypes.ON_FIRE) || event.getSource().is(DamageTypes.IN_FIRE)) {
            fireReduction += getDamageReduction(player, MAItems.ObsidianSkull.get(), Config.SKULL_FIRE_DAMAGE_TAKEN.get());
            fireReduction += getDamageReduction(player, MAItems.ObsidianShield.get(), Config.OBSIDIAN_SHIELD_FIRE_DAMAGE_TAKEN.get());
            fireReduction += getDamageReduction(player, MAItems.AnkhShield.get(), Config.ANKH_SHIELD_FIRE_DAMAGE_TAKEN.get());
        }

        return Math.min(fireReduction, Config.MAX_FIRE_DAMAGE_REDUCTION.get()); // Fire Damage Reduction Cap
    }

    private static double getDamageReduction(Player player, Item item, double configValue) {
        if (item == MAItems.ObsidianShield.get() || item == MAItems.AnkhShield.get()) {
            return CurioHandler.isCurioEquipped(player, item) ? 1.0 - configValue : ShieldHandler.isShieldEquipped(player, item) ? 1.0 - configValue : 0.0;
        } else {
            return CurioHandler.isCurioEquipped(player, item) ? 1.0 - configValue : 0.0;
        }
    }

    public static double getTotalDamageIncrease(Player player) {
        return damageIncreaseMap.getOrDefault(player, 0.0);
    }

    public static void updateDamageIncrease(Player player, LivingEntity target, LivingHurtEvent event) {
        double totalIncrease = calculateDamageIncrease(player, target, event);

        totalIncrease = Math.min(totalIncrease, Config.MAX_DAMAGE_INCREASE.get());
        damageIncreaseMap.put(player, totalIncrease);
    }

    private static double calculateDamageIncrease(Player player, LivingEntity target, LivingHurtEvent event) {
        double totalIncrease = 0.0;

        totalIncrease += getDamageIncrease(player, MAItems.GildedScarf.get(), Config.GILDED_DAMAGE_DEALT.get());
        totalIncrease += getDamageIncrease(player, MAItems.EnderDragonClaw.get(), Config.ENDER_DRAGON_CLAW_DAMAGE.get());

        if (target.getMobType() == MobType.ILLAGER) {
            totalIncrease += getDamageIncrease(player, MAItems.LuckyEmeraldRing.get(), Config.EMERALD_RING_DAMAGE.get());
        }

        if (target.hasEffect(MobEffects.POISON)) {
            totalIncrease += getDamageIncrease(player, MAItems.VenomStone.get(), Config.VENOM_STONE_DAMAGE.get());
        }

        if (target.hasEffect(MobEffects.WITHER)) {
            totalIncrease += getDamageIncrease(player, MAItems.DecayStone.get(), Config.DECAY_STONE_DAMAGE.get());
        }

        if (target.isOnFire()) {
            totalIncrease += getDamageIncrease(player, MAItems.FireStone.get(), Config.FIRE_STONE_DAMAGE.get());
        }

        if (target.hasEffect(MobEffects.MOVEMENT_SLOWDOWN) || target.isFreezing()) {
            totalIncrease += getDamageIncrease(player, MAItems.IceStone.get(), Config.ICE_STONE_DAMAGE.get());
        }

        if (event.getSource().is(DamageTypes.ARROW) || SpartanWeaponryCompat.isArmorPiercingBolt(event.getSource())) {
            totalIncrease += getDamageIncrease(player, MAItems.MagicQuiver.get(), Config.MAGIC_QUIVER_DAMAGE.get());
            totalIncrease += getDamageIncrease(player, MAItems.EnvenomedQuiver.get(), Config.ENVENOMED_QUIVER_DAMAGE.get());
            totalIncrease += getDamageIncrease(player, MAItems.MoltenQuiver.get(), Config.MOLTEN_QUIVER_DAMAGE.get());
        }

        if (event.getSource().is(DamageTypes.ARROW) && target.isOnFire() || SpartanWeaponryCompat.isArmorPiercingBolt(event.getSource())) {
            totalIncrease += getDamageIncrease(player, MAItems.MoltenQuiver.get(), Config.MOLTEN_QUIVER_ONFIRE_DAMAGE.get());
        }

        return totalIncrease;
    }

    private static double getDamageIncrease(Player player, Item item, double configValue) {
        return CurioHandler.isCurioEquipped(player, item) ? configValue - 1.0 : 0.0;
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            double totalIncrease = getTotalDamageIncrease(player);
            event.setAmount((float) (event.getAmount() * (1.0 + totalIncrease)));
            System.out.println("Total Damage Increase for " + player.getName().getString() + ": " + String.format("%.2f", totalIncrease * 100) + "%");
        }
        if (event.getEntity() instanceof Player player) {
            double totalReduction = getTotalDamageReduction(player);
            event.setAmount((float) (event.getAmount() * (1.0 - totalReduction)));
            System.out.println("Total Damage Reduction for " + player.getName().getString() + ": " + String.format("%.2f", totalReduction * 100) + "%");
        }
        if (event.getEntity() instanceof Player player) {
            double fireReduction = getFireDamageReduction(player);
            event.setAmount((float) (event.getAmount() * (1.0 - fireReduction)));
            System.out.println("Fire Damage Reduction for " + player.getName().getString() + ": " + String.format("%.2f", fireReduction * 100) + "%");
        }
    }
}