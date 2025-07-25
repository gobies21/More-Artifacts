package net.gobies.moreartifacts.util;

import net.minecraftforge.fml.ModList;

public class ModLoadedUtil {

    public static boolean isSpartanWeaponryLoaded() {
        return ModList.get().isLoaded("spartanweaponry");
    }

    public static boolean isEnhancedVisualsLoaded() {
        return ModList.get().isLoaded("enhancedvisuals");
    }

    public static boolean isIceandFireLoaded() {
        return ModList.get().isLoaded("iceandfire");
    }

    public static boolean isPotionRingsLoaded() {
        return ModList.get().isLoaded("potionrings2");
    }

    public static boolean isApothecaryLoaded() {
        return ModList.get().isLoaded("apothecary");
    }

    public static boolean isJLMELoaded() {
        return ModList.get().isLoaded("jlme");
    }
}