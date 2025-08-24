package net.gobies.moreartifacts.compat.enhancedvisuals;

import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.util.CurioHandler;
import net.gobies.moreartifacts.init.MAItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import team.creative.enhancedvisuals.api.event.SelectEndermanEvent;
import team.creative.enhancedvisuals.api.event.VisualExplosionEvent;

public class EnhancedVisualsCompat {

    public static void loadCompat() {
        MinecraftForge.EVENT_BUS.register(new EnhancedVisualsCompat());
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void SelectEndermanEvent(SelectEndermanEvent event) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player != null && !event.isCanceled() && CommonConfig.TRUE_ENDERIAN_COMPAT.get()) {
            if (CurioHandler.isCurioEquipped(player, MAItems.TrueEnderianScarf.get())) {
                event.setCanceled(true);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void VisualExplosionEvent(VisualExplosionEvent event) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player != null && !event.isCanceled() && CommonConfig.HERO_SHIELD_COMPAT.get()) {
            if (CurioHandler.isCurioEquipped(player, MAItems.HeroShield.get())) {
                event.setCanceled(true);
            }
        }
    }
}