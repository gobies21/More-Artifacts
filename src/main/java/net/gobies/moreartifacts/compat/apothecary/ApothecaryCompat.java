package net.gobies.moreartifacts.compat.apothecary;

import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.util.CurioHandler;
import net.gobies.moreartifacts.util.MAUtils;
import net.gobies.moreartifacts.util.ShieldHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.gobies.apothecary.init.AEffects;

public class ApothecaryCompat {

    public static void loadCompat() {
        MinecraftForge.EVENT_BUS.register(new ApothecaryCompat());
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Player player = event.player;

            //Burning Immunity
            if (CurioHandler.isCurioEquipped(player, MAItems.ObsidianSkull.get())) {
                MAUtils.removeEffect(player, AEffects.Burning.get());
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.ObsidianShield.get())) {
                MAUtils.removeEffect(player, AEffects.Burning.get());
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.AnkhShield.get())) {
                MAUtils.removeEffect(player, AEffects.Burning.get());
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.FireStone.get())) {
                MAUtils.removeEffect(player, AEffects.Burning.get());
            }
            if (ShieldHandler.isShieldEquipped(player, MAItems.ObsidianShield.get())) {
                MAUtils.removeEffect(player, AEffects.Burning.get());
            }
            if (ShieldHandler.isShieldEquipped(player, MAItems.AnkhShield.get())) {
                MAUtils.removeEffect(player, AEffects.Burning.get());
            }
            //Chilled Immunity
            if (CurioHandler.isCurioEquipped(player, MAItems.IceStone.get())) {
                MAUtils.removeEffect(player, AEffects.Chilled.get());
            }
        }
    }

    @SubscribeEvent
    public void onMobEffectApplicable(MobEffectEvent.Applicable event) {
        if (event.getEntity() instanceof Player player) {
            //Burning Immunity
            if (CurioHandler.isCurioEquipped(player, MAItems.ObsidianSkull.get())) {
                MAUtils.harmfulSpecificEffectImmune(event, AEffects.Burning.get());
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.ObsidianShield.get())) {
                MAUtils.harmfulSpecificEffectImmune(event, AEffects.Burning.get());
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.AnkhShield.get())) {
                MAUtils.harmfulSpecificEffectImmune(event, AEffects.Burning.get());
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.FireStone.get())) {
                MAUtils.harmfulSpecificEffectImmune(event, AEffects.Burning.get());
            }
            if (ShieldHandler.isShieldEquipped(player, MAItems.ObsidianShield.get())) {
                MAUtils.harmfulSpecificEffectImmune(event, AEffects.Burning.get());
            }
            if (ShieldHandler.isShieldEquipped(player, MAItems.AnkhShield.get())) {
                MAUtils.harmfulSpecificEffectImmune(event, AEffects.Burning.get());
            }
            //Chilled Immunity
            if (CurioHandler.isCurioEquipped(player, MAItems.IceStone.get())) {
                MAUtils.harmfulSpecificEffectImmune(event, AEffects.Chilled.get());
            }
        }
    }
}
