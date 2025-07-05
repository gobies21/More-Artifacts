package net.gobies.moreartifacts.compat.spartanweaponry;

import com.oblivioussp.spartanweaponry.init.ModDamageTypes;
import net.gobies.moreartifacts.Config;
import net.gobies.moreartifacts.MoreArtifacts;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.util.CurioHandler;
import net.gobies.moreartifacts.util.DamageManager;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class SpartanWeaponryCompat {

    public static void loadCompat() {
        MinecraftForge.EVENT_BUS.register(new SpartanWeaponryCompat());
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player attacker) {
            LivingEntity target = event.getEntity();
            if (event.getSource().is(ModDamageTypes.KEY_ARMOR_PIERCING_BOLT)) {
                if (CurioHandler.isCurioEquipped(attacker, MAItems.MagicQuiver.get())) {
                    DamageManager.updateDamageIncrease(attacker, target, event);
                }
                if (CurioHandler.isCurioEquipped(attacker, MAItems.EnvenomedQuiver.get())) {
                    DamageManager.updateDamageIncrease(attacker, target, event);
                    target.addEffect(new MobEffectInstance(MobEffects.POISON, 20 * Config.ENVENOMED_QUIVER_POISON_DURATION.get(), Config.ENVENOMED_QUIVER_POISON_LEVEL.get()));
                    target.addEffect(new MobEffectInstance(MobEffects.WITHER, 20 * Config.ENVENOMED_QUIVER_WITHER_DURATION.get(), Config.ENVENOMED_QUIVER_WITHER_LEVEL.get()));
                }
                if (CurioHandler.isCurioEquipped(attacker, MAItems.MoltenQuiver.get())) {
                    DamageManager.updateDamageIncrease(attacker, target, event);
                    if (!target.isOnFire()) {
                        target.setSecondsOnFire(Config.MOLTEN_QUIVER_DURATION.get());
                    }
                    if (target.isOnFire()) {
                        DamageManager.updateDamageIncrease(attacker, target, event);
                    }
                }
            }
        }
    }

    public static boolean isArmorPiercingBolt(DamageSource damageType) {
        return MoreArtifacts.isSpartanWeaponryLoaded() && damageType.is(ModDamageTypes.KEY_ARMOR_PIERCING_BOLT);
    }
}
