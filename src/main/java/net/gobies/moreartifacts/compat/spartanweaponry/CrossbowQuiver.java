package net.gobies.moreartifacts.compat.spartanweaponry;

import com.oblivioussp.spartanweaponry.init.ModDamageTypes;
import net.gobies.moreartifacts.Config;
import net.gobies.moreartifacts.item.MAItems;
import net.gobies.moreartifacts.util.CurioHandler;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CrossbowQuiver {

    public static void loadCompat() {
        MinecraftForge.EVENT_BUS.register(new CrossbowQuiver());
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player attacker) {
            LivingEntity target = event.getEntity();
            if (event.getSource().is(ModDamageTypes.KEY_ARMOR_PIERCING_BOLT)) {
                if (CurioHandler.isCurioEquipped(attacker, MAItems.MagicQuiver.get())) {
                    event.setAmount((float) (event.getAmount() * Config.MAGIC_QUIVER_DAMAGE.get()));
                }
                if (CurioHandler.isCurioEquipped(attacker, MAItems.EnvenomedQuiver.get())) {
                    event.setAmount((float) (event.getAmount() * Config.ENVENOMED_QUIVER_DAMAGE.get()));
                    target.addEffect(new MobEffectInstance(MobEffects.POISON, 20 * Config.ENVENOMED_QUIVER_POISON_DURATION.get(), Config.ENVENOMED_QUIVER_POISON_LEVEL.get()));
                    target.addEffect(new MobEffectInstance(MobEffects.WITHER, 20 * Config.ENVENOMED_QUIVER_WITHER_DURATION.get(), Config.ENVENOMED_QUIVER_WITHER_LEVEL.get()));
                }
                if (CurioHandler.isCurioEquipped(attacker, MAItems.MoltenQuiver.get())) {
                    event.setAmount((float) (event.getAmount() * Config.MOLTEN_QUIVER_DAMAGE.get()));
                    if (!target.isOnFire()) {
                        target.setSecondsOnFire(Config.MOLTEN_QUIVER_DURATION.get());
                    }
                    if (target.isOnFire()) {
                        event.setAmount((float) (event.getAmount() * Config.MOLTEN_QUIVER_ONFIRE_DAMAGE.get()));
                    }
                }
            }
        }
    }
}
