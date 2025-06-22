package net.gobies.moreartifacts.compat.apothecary;

import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.util.CurioHandler;
import net.gobies.moreartifacts.util.ShieldHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.gobies.apothecary.init.AEffects;

public class EffectImmunity {

    public static void loadCompat() {
        MinecraftForge.EVENT_BUS.register(new EffectImmunity());
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Player player = event.player;

            //Burning Immunity
            if (CurioHandler.isCurioEquipped(player, MAItems.ObsidianSkull.get())) {
                if (player.hasEffect(AEffects.Burning.get())) {
                    player.removeEffect(AEffects.Burning.get());
                }
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.ObsidianShield.get())) {
                if (player.hasEffect(AEffects.Burning.get())) {
                    player.removeEffect(AEffects.Burning.get());
                }
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.AnkhShield.get())) {
                if (player.hasEffect(AEffects.Burning.get())) {
                    player.removeEffect(AEffects.Burning.get());
                }
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.FireStone.get())) {
                if (player.hasEffect(AEffects.Burning.get())) {
                    player.removeEffect(AEffects.Burning.get());
                }
            }
            if (ShieldHandler.isShieldEquipped(player, MAItems.ObsidianShield.get())) {
                if (player.hasEffect(AEffects.Burning.get())) {
                    player.removeEffect(AEffects.Burning.get());
                }
            }
            if (ShieldHandler.isShieldEquipped(player, MAItems.AnkhShield.get())) {
                if (player.hasEffect(AEffects.Burning.get())) {
                    player.removeEffect(AEffects.Burning.get());
                }
            }

            //Chilled Immunity
            if (CurioHandler.isCurioEquipped(player, MAItems.IceStone.get())) {
                if (player.hasEffect(AEffects.Chilled.get())) {
                    player.removeEffect(AEffects.Chilled.get());
                }
            }
        }
    }

    @SubscribeEvent
    public void onMobEffectApplicable(MobEffectEvent.Applicable event) {
        if (event.getEntity() instanceof Player player) {
            event.getEffectInstance();

            //Burning Immunity
            if (event.getEffectInstance().getEffect() == AEffects.Burning.get()) {
                if (CurioHandler.isCurioEquipped(player, MAItems.ObsidianSkull.get())) {
                    event.setResult(MobEffectEvent.Result.DENY);
                }
                if (CurioHandler.isCurioEquipped(player, MAItems.ObsidianShield.get())) {
                    event.setResult(MobEffectEvent.Result.DENY);
                }
                if (CurioHandler.isCurioEquipped(player, MAItems.AnkhShield.get())) {
                    event.setResult(MobEffectEvent.Result.DENY);
                }
                if (CurioHandler.isCurioEquipped(player, MAItems.FireStone.get())) {
                    event.setResult(MobEffectEvent.Result.DENY);
                }
                if (ShieldHandler.isShieldEquipped(player, MAItems.ObsidianShield.get())) {
                    event.setResult(MobEffectEvent.Result.DENY);
                }
                if (ShieldHandler.isShieldEquipped(player, MAItems.AnkhShield.get())) {
                    event.setResult(MobEffectEvent.Result.DENY);
                }
            }

            //Chilled Immunity
            if (event.getEffectInstance().getEffect() == AEffects.Chilled.get()) {
                if (CurioHandler.isCurioEquipped(player, MAItems.IceStone.get())) {
                    event.setResult(MobEffectEvent.Result.DENY);
                }
            }
        }
    }
}