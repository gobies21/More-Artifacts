package net.gobies.moreartifacts.compat.enhancedvisuals;

import net.gobies.moreartifacts.util.CurioHandler;
import net.gobies.moreartifacts.item.MAItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import team.creative.enhancedvisuals.api.event.SelectEndermanEvent;
import team.creative.enhancedvisuals.api.event.VisualExplosionEvent;

public class EnhancedVisualsRender {

    public static void loadCompat() {
        MinecraftForge.EVENT_BUS.register(new EnhancedVisualsRender());
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void SelectEndermanEvent(SelectEndermanEvent event) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player != null && !event.isCanceled()) {
            if (CurioHandler.isCurioEquipped(player, MAItems.TrueEnderianScarf.get())) {
                event.setCanceled(true);
            }
        }
    }
    //not working for some unknown reason
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void VisualExplosionEvent(VisualExplosionEvent event) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player != null && !event.isCanceled()) {
            if (CurioHandler.isCurioEquipped(player, MAItems.HeroShield.get())) {
                event.setCanceled(true);
            }
        }
    }
}