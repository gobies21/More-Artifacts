package net.gobies.moreartifacts.compat.betterstealth;

import net.jrdemiurge.betterstealth.attribute.ModAttributes;
import net.minecraft.world.entity.ai.attributes.Attribute;

public class BetterStealthCompat {

    public static Attribute stealthAttribute() {
        return ModAttributes.STEALTH.get();
    }
}
