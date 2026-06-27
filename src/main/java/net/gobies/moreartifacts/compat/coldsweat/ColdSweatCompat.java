package net.gobies.moreartifacts.compat.coldsweat;

import com.momosoftworks.coldsweat.core.init.AttributeInit;
import net.minecraft.world.entity.ai.attributes.Attribute;

public class ColdSweatCompat {

    public static Attribute heatToleranceAttribute() {
        return AttributeInit.HEAT_DAMPENING.get();
    }

    public static Attribute coldToleranceAttribute() {
        return AttributeInit.COLD_DAMPENING.get();
    }
}