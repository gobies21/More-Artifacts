package net.gobies.moreartifacts.effect;

import java.util.*;

public class MAStatusEffects {

    private static final Map<UUID, Integer> BLEEDING = new HashMap<>();
    private static final Map<UUID, Integer> FROSTEDWOUNDS = new HashMap<>();

    public static Map<UUID, Integer> bleeding() {
        return BLEEDING;

    }
    public static Map<UUID, Integer> frostedWounds() {
        return FROSTEDWOUNDS;
    }

    public static void clearMaps(UUID uuid) {
        BLEEDING.remove(uuid);
        FROSTEDWOUNDS.remove(uuid);
    }
}