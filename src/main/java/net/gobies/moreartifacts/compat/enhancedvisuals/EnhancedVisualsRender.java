package net.gobies.moreartifacts.compat.enhancedvisuals;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.gobies.moreartifacts.item.ModItems;
import team.creative.enhancedvisuals.api.event.SelectEndermanEvent;
import team.creative.enhancedvisuals.api.event.VisualExplosionEvent;
import top.theillusivec4.curios.api.CuriosApi;

public class EnhancedVisualsRender {
    public EnhancedVisualsRender() {
    }

    public static void loadCompat() {
        MinecraftForge.EVENT_BUS.register(new EnhancedVisualsRender());
    }

    Minecraft mc = Minecraft.getInstance();

    @SubscribeEvent
    public void onEnderRender(SelectEndermanEvent event) {
        if (mc.player != null) {
        }
        CuriosApi.getCuriosHelper().findEquippedCurio(ModItems.TrueEnderianScarf.get(), mc.player).ifPresent((slot) -> {
            event.setCanceled(true);
        });
    }

    //not working???
    @SubscribeEvent
    public void onExplosionRender(VisualExplosionEvent event) {
        System.out.println("HERO SHIELD.");
        if (mc.player != null) {
        }
        CuriosApi.getCuriosHelper().findEquippedCurio(ModItems.HeroShield.get(), mc.player).ifPresent((slot) -> {
            event.setCanceled(true);
        });
    }
}