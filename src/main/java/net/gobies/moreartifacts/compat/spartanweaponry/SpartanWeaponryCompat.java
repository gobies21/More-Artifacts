package net.gobies.moreartifacts.compat.spartanweaponry;

import com.oblivioussp.spartanweaponry.init.ModDamageTypes;
import net.gobies.moreartifacts.util.ModLoadedUtil;
import net.minecraft.world.damagesource.DamageSource;

public class SpartanWeaponryCompat {

    public static void loadCompat() {
    }

    public static boolean isArmorPiercingBolt(DamageSource damageType) {
        if (ModLoadedUtil.isSpartanWeaponryLoaded()) {
            return damageType.is(ModDamageTypes.KEY_ARMOR_PIERCING_BOLT);
        }
        return false;
    }
}
