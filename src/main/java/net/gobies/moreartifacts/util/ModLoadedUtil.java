package net.gobies.moreartifacts.util;

import net.minecraftforge.fml.ModList;

public class ModLoadedUtil {

    private static final boolean spartanWeaponry = ModList.get().isLoaded("spartanweaponry");
    private static final boolean enhancedVisuals = ModList.get().isLoaded("enhancedvisuals");
    private static final boolean iceAndFire = ModList.get().isLoaded("iceandfire");
    private static final boolean iceAndFire2 = ModList.get().isLoaded("iceandfire2");
    private static final boolean potionRings = ModList.get().isLoaded("potionrings2");
    private static final boolean apothecary = ModList.get().isLoaded("apothecary");
    private static final boolean jei = ModList.get().isLoaded("jei");
    private static final boolean coldSweat = ModList.get().isLoaded("cold_sweat");
    private static final boolean betterStealth = ModList.get().isLoaded("betterstealth");
    private static final boolean firstAid = ModList.get().isLoaded("firstaid");
    private static final boolean ironsSpellbooks = ModList.get().isLoaded("irons_spellbooks");

    public static boolean isSpartanWeaponryLoaded() {
        return spartanWeaponry;
    }

    public static boolean isEnhancedVisualsLoaded() {
        return enhancedVisuals;
    }

    public static boolean isIceandFireLoaded() {
        return iceAndFire;
    }

    public static boolean isIceandFire2Loaded() {
        return iceAndFire2;
    }

    public static boolean isPotionRingsLoaded() {
        return potionRings;
    }

    public static boolean isApothecaryLoaded() {
        return apothecary;
    }

    public static boolean isJeiLoaded() {
        return jei;
    }

    public static boolean isColdSweatLoaded() {
        return coldSweat;
    }

    public static boolean isBetterStealthLoaded() {
        return betterStealth;
    }

    public static boolean isFirstAidLoaded() {
        return firstAid;
    }

    public static boolean isIronsSpellbooksLoaded() {
        return ironsSpellbooks;
    }
}