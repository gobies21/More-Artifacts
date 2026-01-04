package net.gobies.moreartifacts.compat.coldsweat;

import com.momosoftworks.coldsweat.core.init.AttributeInit;
import net.gobies.moreartifacts.util.ModLoadedUtil;
import net.minecraft.world.entity.ai.attributes.Attribute;

public class ColdSweatCompat {

    public static Attribute heatResistanceAttribute() {
        if (ModLoadedUtil.isColdSweatLoaded()) {
            return AttributeInit.HEAT_RESISTANCE.get();

        }
        return null;
    }

    public static Attribute coldResistanceAttribute() {
        if (ModLoadedUtil.isColdSweatLoaded()) {
            return AttributeInit.COLD_RESISTANCE.get();

        }
        return null;
    }
}
