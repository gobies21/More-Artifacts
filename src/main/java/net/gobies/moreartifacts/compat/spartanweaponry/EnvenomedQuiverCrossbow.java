package net.gobies.moreartifacts.compat.spartanweaponry;

import com.oblivioussp.spartanweaponry.init.ModDamageTypes;
import net.gobies.moreartifacts.Config;
import net.gobies.moreartifacts.util.CurioHandler;
import net.gobies.moreartifacts.item.ModItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EnvenomedQuiverCrossbow {

    public static void loadCompat() {
        MinecraftForge.EVENT_BUS.register(new EnvenomedQuiverCrossbow());
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player attacker) {
            if (CurioHandler.isCurioEquipped(attacker, ModItems.EnvenomedQuiver.get())) {
                if (event.getSource().is(ModDamageTypes.KEY_ARMOR_PIERCING_BOLT)) {
                    event.setAmount((float) (event.getAmount() * Config.ENVENOMED_QUIVER_DAMAGE.get()));
                    LivingEntity target = event.getEntity();
                    target.addEffect(new MobEffectInstance(MobEffects.POISON, 20 * Config.ENVENOMED_QUIVER_POISON_DURATION.get(), Config.ENVENOMED_QUIVER_POISON_LEVEL.get()));
                    target.addEffect(new MobEffectInstance(MobEffects.WITHER, 20 * Config.ENVENOMED_QUIVER_WITHER_DURATION.get(), Config.ENVENOMED_QUIVER_WITHER_LEVEL.get()));
                }
            }
        }
    }
}

