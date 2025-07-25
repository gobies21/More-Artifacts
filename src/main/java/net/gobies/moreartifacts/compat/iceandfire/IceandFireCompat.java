package net.gobies.moreartifacts.compat.iceandfire;

import com.github.alexthe666.iceandfire.entity.props.EntityDataProvider;
import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.util.CurioHandler;
import net.gobies.moreartifacts.init.MAItems;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class IceandFireCompat {

    public static void loadCompat() {
        MinecraftForge.EVENT_BUS.register(new IceandFireCompat());
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player attacker) {
            RandomSource random = attacker.getRandom();
            LivingEntity target = event.getEntity();
            if (CurioHandler.isCurioEquipped(attacker, MAItems.IceStone.get())) {
                if (random.nextFloat() < CommonConfig.ICE_STONE_ENCASED_CHANCE.get() && CommonConfig.ICE_STONE_COMPAT.get()) {
                    EntityDataProvider.getCapability(target).ifPresent(data -> data.frozenData.setFrozen(target, 20 * CommonConfig.ICE_STONE_ENCASED_DURATION.get()));
                }
            }
        }
    }
}