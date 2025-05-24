package net.gobies.moreartifacts.compat.spartanweaponry;

import com.oblivioussp.spartanweaponry.init.ModDamageTypes;
import net.gobies.moreartifacts.Config;
import net.gobies.moreartifacts.util.CurioHandler;
import net.gobies.moreartifacts.item.ModItems;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MagicQuiverCrossbow {

    public static void loadCompat() {
        MinecraftForge.EVENT_BUS.register(new MagicQuiverCrossbow());
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player attacker) {
            if (CurioHandler.isCurioEquipped(attacker, ModItems.MagicQuiver.get())) {
                if (event.getSource().is(ModDamageTypes.KEY_ARMOR_PIERCING_BOLT)) {
                    event.setAmount((float) (event.getAmount() * Config.MAGIC_QUIVER_DAMAGE.get()));
                }
            }
        }
    }
}
