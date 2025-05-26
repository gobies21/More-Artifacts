package net.gobies.moreartifacts.compat.iceandfire;

import com.github.alexthe666.iceandfire.entity.props.EntityDataProvider;
import net.gobies.moreartifacts.Config;
import net.gobies.moreartifacts.util.CurioHandler;
import net.gobies.moreartifacts.item.MAItems;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class IceStoneFreeze {

    public static void loadCompat() {
        MinecraftForge.EVENT_BUS.register(new IceStoneFreeze());
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player attacker) {
            if (CurioHandler.isCurioEquipped(attacker, MAItems.IceStone.get())) {
                RandomSource random = attacker.getRandom();
                LivingEntity target = event.getEntity();
                if (random.nextFloat() < Config.ICE_STONE_ENCASED_CHANCE.get()) {
                    EntityDataProvider.getCapability(target).ifPresent(data -> data.frozenData.setFrozen(target, 20 * Config.ICE_STONE_ENCASED_DURATION.get()));
                }
            }
        }
    }
}
