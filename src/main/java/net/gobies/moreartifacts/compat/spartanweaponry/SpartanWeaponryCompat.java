package net.gobies.moreartifacts.compat.spartanweaponry;

import com.oblivioussp.spartanweaponry.init.ModDamageTypes;
import net.gobies.moreartifacts.util.ModLoadedUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class SpartanWeaponryCompat {

    public static void loadCompat() {
    }

    public static boolean isArmorPiercingBolt(DamageSource damageType) {
        if (ModLoadedUtil.isSpartanWeaponryLoaded()) {
            return damageType.is(ModDamageTypes.KEY_ARMOR_PIERCING_BOLT);
        }
        return false;
    }

    public static boolean isArmorPiercingBolt(Entity entity) {
        if (ModLoadedUtil.isSpartanWeaponryLoaded()) {
            return Objects.equals(ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()), new ResourceLocation("spartanweaponry", "armor_piercing_bolt"));
        }
        return false;
    }
}
