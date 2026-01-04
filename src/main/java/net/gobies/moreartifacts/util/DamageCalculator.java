package net.gobies.moreartifacts.util;

import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.init.MAItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class DamageCalculator {
    private static final List<Item> itemsToCheck = MAItems.getAllArtifacts().stream()
            .map(RegistryObject::get)
            .toList();

    public static double calculateTotalDamageReduction(Player player) {
        double generalReduction = 0.0;

        generalReduction += getDamageReduction(player, MAItems.EnderianScarf.get(), (1 - CommonConfig.ENDERIAN_DAMAGE_REDUCTION.get()));
        generalReduction += getDamageReduction(player, MAItems.GildedScarf.get(), (1 - CommonConfig.GILDED_DAMAGE_REDUCTION.get()));
        generalReduction += getDamageReduction(player, MAItems.TrueEnderianScarf.get(), (1 - CommonConfig.TRUE_ENDERIAN_DAMAGE_REDUCTION.get()));

        return Math.min(generalReduction, CommonConfig.MAX_DAMAGE_REDUCTION.get()); // Damage Reduction Cap
    }

    public static double calculateFireDamageReduction(Player player) {
        double fireReduction = 0.0;

        fireReduction += getDamageReduction(player, MAItems.ObsidianSkull.get(), (1 - CommonConfig.SKULL_FIRE_DAMAGE_REDUCTION.get()));
        fireReduction += getDamageReduction(player, MAItems.ObsidianShield.get(), (1 - CommonConfig.OBSIDIAN_SHIELD_FIRE_DAMAGE_REDUCTION.get()));
        fireReduction += getDamageReduction(player, MAItems.AnkhShield.get(), (1 - CommonConfig.ANKH_SHIELD_FIRE_DAMAGE_REDUCTION.get()));

        return Math.min(fireReduction, CommonConfig.MAX_FIRE_DAMAGE_REDUCTION.get()); // Fire Damage Reduction Cap
    }

    public static double calculateDamageIncrease(Player player) {
        double generalIncrease = 0.0;

        generalIncrease += getDamageIncrease(player, MAItems.GildedScarf.get(), CommonConfig.GILDED_DAMAGE_DEALT.get());
        generalIncrease += getDamageIncrease(player, MAItems.EnderDragonClaw.get(), CommonConfig.ENDER_DRAGON_CLAW_DAMAGE.get());
        generalIncrease += getDamageIncrease(player, MAItems.LuckyEmeraldRing.get(), CommonConfig.EMERALD_RING_DAMAGE.get());
        generalIncrease += getDamageIncrease(player, MAItems.VenomStone.get(), CommonConfig.VENOM_STONE_DAMAGE.get());
        generalIncrease += getDamageIncrease(player, MAItems.DecayStone.get(), CommonConfig.DECAY_STONE_DAMAGE.get());
        generalIncrease += getDamageIncrease(player, MAItems.FireStone.get(), CommonConfig.FIRE_STONE_DAMAGE.get());
        generalIncrease += getDamageIncrease(player, MAItems.IceStone.get(), CommonConfig.ICE_STONE_DAMAGE.get());
        generalIncrease += getDamageIncrease(player, MAItems.MagicQuiver.get(), CommonConfig.MAGIC_QUIVER_DAMAGE.get());
        generalIncrease += getDamageIncrease(player, MAItems.EnvenomedQuiver.get(), CommonConfig.ENVENOMED_QUIVER_DAMAGE.get());
        generalIncrease += getDamageIncrease(player, MAItems.MoltenQuiver.get(), CommonConfig.MOLTEN_QUIVER_DAMAGE.get());
        generalIncrease += getDamageIncrease(player, MAItems.MoltenQuiver.get(), CommonConfig.MOLTEN_QUIVER_ONFIRE_DAMAGE.get());
        generalIncrease += getDamageIncrease(player, MAItems.RubyRing.get(), CommonConfig.RUBY_RING_DAMAGE_INCREASE.get());

        return Math.min(generalIncrease, CommonConfig.MAX_DAMAGE_INCREASE.get()); // Damage Increase Cap
    }

    public static double getDamageReduction(Player player, Item item, double configValue) {
        if (item == MAItems.ObsidianShield.get() || item == MAItems.AnkhShield.get()) {
            return CurioHandler.isCurioEquipped(player, item) ? 1.0 - configValue : ShieldHandler.isShieldEquipped(player, item) ? 1.0 - configValue : 0.0;
        } else {
            return CurioHandler.isCurioEquipped(player, item) ? 1.0 - configValue : 0.0;
        }
    }

    public static double getDamageIncrease(Player player, Item item, double configValue) {
        return CurioHandler.isCurioEquipped(player, item) ? configValue - 1.0 : 0.0;
    }

    public static Map<Item, Boolean> getCurrentEquipState(Player player) {
        Map<Item, Boolean> currentEquippedState = new HashMap<>();

        for (Item item : itemsToCheck) {
            boolean isEquipped = CurioHandler.isCurioEquipped(player, item);
            if (isEquipped) {
                currentEquippedState.put(item, true);
            }
        }

        for (Item item : Arrays.asList(MAItems.CobaltShield.get(), MAItems.ObsidianShield.get(), MAItems.AnkhShield.get())) {
            if (ShieldHandler.isShieldEquipped(player, item)) {
                currentEquippedState.put(item, true);
            }
        }

        return currentEquippedState;
    }
}