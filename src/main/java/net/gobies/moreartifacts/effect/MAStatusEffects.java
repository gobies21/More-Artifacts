package net.gobies.moreartifacts.effect;

import java.util.*;

public class MAStatusEffects {

    private static final Map<UUID, Integer> BLEEDING = new HashMap<>();
    private static final Map<UUID, Integer> FROSTED_WOUNDS = new HashMap<>();

    public static Map<UUID, Integer> bleeding() {
        return BLEEDING;

    }
    public static Map<UUID, Integer> frosted_wounds() {
        return FROSTED_WOUNDS;
    }

    public static void clearMaps(UUID uuid) {
        BLEEDING.remove(uuid);
        FROSTED_WOUNDS.remove(uuid);
    }
}