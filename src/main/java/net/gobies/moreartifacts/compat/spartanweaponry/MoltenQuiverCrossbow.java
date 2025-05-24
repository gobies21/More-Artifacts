package net.gobies.moreartifacts.compat.spartanweaponry;

import com.oblivioussp.spartanweaponry.init.ModDamageTypes;
import net.gobies.moreartifacts.Config;
import net.gobies.moreartifacts.util.CurioHandler;
import net.gobies.moreartifacts.item.ModItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MoltenQuiverCrossbow {

    public static void loadCompat() {
        MinecraftForge.EVENT_BUS.register(new MoltenQuiverCrossbow());
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player attacker) {
            if (CurioHandler.isCurioEquipped(attacker, ModItems.MoltenQuiver.get())) {
                if (event.getSource().is(ModDamageTypes.KEY_ARMOR_PIERCING_BOLT)) {
                    event.setAmount((float) (event.getAmount() * Config.MOLTEN_QUIVER_DAMAGE.get()));
                    LivingEntity target = event.getEntity();
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