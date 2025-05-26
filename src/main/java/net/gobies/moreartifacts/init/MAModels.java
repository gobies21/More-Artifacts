package net.gobies.moreartifacts.init;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(
        bus = Mod.EventBusSubscriber.Bus.MOD,
        value = {Dist.CLIENT}
)
public class MAModels {
//    @SubscribeEvent
//    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
//        event.registerLayerDefinition(SunglassesModel.LAYER_LOCATION, SunglassesModel::createBodyLayer);
//    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
       // CuriosRendererRegistry.register(MAItems.Sunglasses.get(), SunglassesRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        //event.registerLayerDefinition(SunglassesModel.LAYER_LOCATION, SunglassesModel::createBodyLayer);
    }
}