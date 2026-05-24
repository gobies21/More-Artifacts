package net.gobies.moreartifacts.compat.betterstealth;

import net.gobies.moreartifacts.util.ModLoadedUtil;
import net.jrdemiurge.betterstealth.attribute.ModAttributes;
import net.minecraft.world.entity.ai.attributes.Attribute;

public class BetterStealthCompat {

    public static Attribute stealthAttribute() {
        if (ModLoadedUtil.isBetterStealthLoaded()) {
            return ModAttributes.STEALTH.get();
        }
        return null;
    }
}
