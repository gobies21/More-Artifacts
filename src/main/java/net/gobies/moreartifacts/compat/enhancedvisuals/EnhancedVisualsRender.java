package net.gobies.moreartifacts.compat.enhancedvisuals;

import net.gobies.moreartifacts.MoreArtifacts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.gobies.moreartifacts.item.ModItems;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import team.creative.enhancedvisuals.api.event.SelectEndermanEvent;
import team.creative.enhancedvisuals.api.event.VisualExplosionEvent;
import top.theillusivec4.curios.api.CuriosApi;

@Mod.EventBusSubscriber(modid = MoreArtifacts.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EnhancedVisualsRender {


    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        loadCompat();
    }

    public static void loadCompat() {
        MinecraftForge.EVENT_BUS.register(new EnhancedVisualsRender());
    }

    @SubscribeEvent
    public void onEnderRender(SelectEndermanEvent event) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player != null && !event.isCanceled()) {
            CuriosApi.getCuriosHelper().findEquippedCurio(ModItems.TrueEnderianScarf.get(), player).ifPresent((slot) -> {
                event.setCanceled(true);
            });
        }
    }

    //not working???
    @SubscribeEvent
    public void onExplosionRender(VisualExplosionEvent event) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player != null && !event.isCanceled()) {
            CuriosApi.getCuriosHelper().findEquippedCurio(ModItems.HeroShield.get(), player).ifPresent((slot) -> {
                event.setCanceled(true);
            });
        }
    }
}