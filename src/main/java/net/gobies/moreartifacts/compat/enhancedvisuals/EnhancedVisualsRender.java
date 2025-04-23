package net.gobies.moreartifacts.compat.enhancedvisuals;

import net.gobies.moreartifacts.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static net.gobies.moreartifacts.init.MoreArtifactsCurioHandler.isCurioEquipped;

public class EnhancedVisualsRender {

    public static void loadCompat() {
        MinecraftForge.EVENT_BUS.register(new EnhancedVisualsRender());
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onEnderRender(team.creative.enhancedvisuals.api.event.SelectEndermanEvent event) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player != null && !event.isCanceled()) {
            if (isCurioEquipped(player, ModItems.TrueEnderianScarf.get())) {
                        event.setCanceled(true);
                    }
                }
            }
    //not working???
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onExplosionRender(team.creative.enhancedvisuals.api.event.VisualExplosionEvent event) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player != null) {
            if (isCurioEquipped(player, ModItems.HeroShield.get())) {
                event.setCanceled(true);
            }
        }
    }
}