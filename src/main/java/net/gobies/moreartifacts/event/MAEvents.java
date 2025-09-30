package net.gobies.moreartifacts.event;

import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.item.artifacts.AnkhCharmItem;
import net.gobies.moreartifacts.item.artifacts.AnkhShieldItem;
import net.gobies.moreartifacts.util.CurioHandler;
import net.gobies.moreartifacts.util.MAUtils;
import net.gobies.moreartifacts.util.ShieldHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MAEvents {

    public static void register() {
        MinecraftForge.EVENT_BUS.register(new MAEvents());
    }

    @SubscribeEvent
    public void onMobEffectApplicable(MobEffectEvent.Applicable event) {
        if (event.getEntity() instanceof Player player) {
            if (CurioHandler.isCurioEquipped(player, MAItems.AnkhShield.get()) || ShieldHandler.isShieldEquipped(player, MAItems.AnkhShield.get())) {
                MAUtils.harmfulEffectImmunity(event);
                AnkhShieldItem.additionalEffectImmunity(event);
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.AnkhCharm.get()) || ShieldHandler.isShieldEquipped(player, MAItems.AnkhCharm.get())) {
                MAUtils.harmfulEffectImmunity(event);
                AnkhCharmItem.additionalEffectImmunity(event);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Player player = event.player;
            if (ShieldHandler.isShieldEquipped(player, MAItems.AnkhShield.get())) {
                MAUtils.removeHarmfulEffects(player);
                AnkhShieldItem.removeAdditionalEffects(player);
            }
            if (ShieldHandler.isShieldEquipped(player, MAItems.AnkhCharm.get())) {
                MAUtils.removeHarmfulEffects(player);
                AnkhCharmItem.removeAdditionalEffects(player);
            }
        }
    }
}
