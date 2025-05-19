package net.gobies.moreartifacts.init;

import net.gobies.moreartifacts.client.renderer.SunglassesRenderer;
import net.gobies.moreartifacts.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@Mod.EventBusSubscriber(
        bus = Mod.EventBusSubscriber.Bus.MOD,
        value = {Dist.CLIENT}
)
public class MoreArtifactsModels {
//    @SubscribeEvent
//    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
//        event.registerLayerDefinition(SunglassesModel.LAYER_LOCATION, SunglassesModel::createBodyLayer);
//    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent evt) {
       // CuriosRendererRegistry.register(ModItems.Sunglasses.get(), SunglassesRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        //event.registerLayerDefinition(SunglassesModel.LAYER_LOCATION, SunglassesModel::createBodyLayer);
    }
}