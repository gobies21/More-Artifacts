package net.gobies.moreartifacts.init;

import net.gobies.moreartifacts.client.models.*;
import net.gobies.moreartifacts.client.renderer.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class MAModels {

    public static void modelSetup() {
        CuriosRendererRegistry.register(MAItems.Sunglasses.get(), SunglassesRenderer::new);
        CuriosRendererRegistry.register(MAItems.SculkShades.get(), SculkShadesRenderer::new);
        CuriosRendererRegistry.register(MAItems.SculkLens.get(), SculkLensRenderer::new);
        CuriosRendererRegistry.register(MAItems.CobaltShield.get(), CobaltShieldRenderer::new);
        CuriosRendererRegistry.register(MAItems.ObsidianShield.get(), ObsidianShieldRenderer::new);
        CuriosRendererRegistry.register(MAItems.AnkhShield.get(), AnkhShieldRenderer::new);
        CuriosRendererRegistry.register(MAItems.HeroShield.get(), HeroShieldRenderer::new);
    }

    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(MAModelLayer.SUNGLASSES, SunglassesModel::createBodyLayer);
        event.registerLayerDefinition(MAModelLayer.SCULK_SHADES, SculkShadesModel::createBodyLayer);
        event.registerLayerDefinition(MAModelLayer.SCULK_LENS, SculkLensModel::createBodyLayer);
        event.registerLayerDefinition(MAModelLayer.COBALT_SHIELD, CobaltShieldModel::createBodyLayer);
        event.registerLayerDefinition(MAModelLayer.ANKH_SHIELD, AnkhShieldModel::createBodyLayer);
        event.registerLayerDefinition(MAModelLayer.OBSIDIAN_SHIELD, ObsidianShieldModel::createBodyLayer);
        event.registerLayerDefinition(MAModelLayer.HERO_SHIELD, HeroShieldModel::createBodyLayer);
    }
}