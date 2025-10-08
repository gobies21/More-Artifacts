package net.gobies.moreartifacts.init;

import net.gobies.moreartifacts.client.models.*;
import net.gobies.moreartifacts.client.renderer.*;
import net.minecraftforge.client.event.EntityRenderersEvent;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

public class MAModels {

    public static void modelSetup() {
        CuriosRendererRegistry.register(MAItems.Sunglasses.get(), SunglassesRenderer::new);
        CuriosRendererRegistry.register(MAItems.SculkShades.get(), SculkShadesRenderer::new);
        CuriosRendererRegistry.register(MAItems.SculkLens.get(), SculkLensRenderer::new);
        CuriosRendererRegistry.register(MAItems.CobaltShield.get(), CobaltShieldRenderer::new);
        CuriosRendererRegistry.register(MAItems.ObsidianShield.get(), ObsidianShieldRenderer::new);
        CuriosRendererRegistry.register(MAItems.AnkhShield.get(), AnkhShieldRenderer::new);
        CuriosRendererRegistry.register(MAItems.HeroShield.get(), HeroShieldRenderer::new);
        CuriosRendererRegistry.register(MAItems.MelodyPlushie.get(), MelodyPlushieRenderer::new);
        CuriosRendererRegistry.register(MAItems.EnderianScarf.get(), EnderianScarfRenderer::new);
        CuriosRendererRegistry.register(MAItems.GildedScarf.get(), GildedScarfRenderer::new);
        CuriosRendererRegistry.register(MAItems.TrueEnderianScarf.get(), TrueEnderianScarfRenderer::new);
        CuriosRendererRegistry.register(MAItems.SpectreAmulet.get(), SpectreAmuletRenderer::new);
        CuriosRendererRegistry.register(MAItems.VenomAmulet.get(), VenomAmuletRenderer::new);
        CuriosRendererRegistry.register(MAItems.DecayAmulet.get(), DecayAmuletRenderer::new);
        CuriosRendererRegistry.register(MAItems.NecroplasmAmulet.get(), NecroplasmAmuletRenderer::new);
        //CuriosRendererRegistry.register(MAItems.MagicQuiver.get(), MagicQuiverRenderer::new);
        //CuriosRendererRegistry.register(MAItems.MechanicalGlove.get(), MechanicalGloveRenderer::new);

    }

    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(MAModelLayer.SUNGLASSES, SunglassesModel::createBodyLayer);
        event.registerLayerDefinition(MAModelLayer.SCULK_SHADES, SculkShadesModel::createBodyLayer);
        event.registerLayerDefinition(MAModelLayer.SCULK_LENS, SculkLensModel::createBodyLayer);
        event.registerLayerDefinition(MAModelLayer.COBALT_SHIELD, CobaltShieldModel::createBodyLayer);
        event.registerLayerDefinition(MAModelLayer.ANKH_SHIELD, AnkhShieldModel::createBodyLayer);
        event.registerLayerDefinition(MAModelLayer.OBSIDIAN_SHIELD, ObsidianShieldModel::createBodyLayer);
        event.registerLayerDefinition(MAModelLayer.HERO_SHIELD, HeroShieldModel::createBodyLayer);
        event.registerLayerDefinition(MAModelLayer.MELODY_PLUSHIE, MelodyPlushieModel::createBodyLayer);
        event.registerLayerDefinition(MAModelLayer.ENDERIAN_SCARF, EnderianScarfModel::createBodyLayer);
        event.registerLayerDefinition(MAModelLayer.GILDED_SCARF, GildedScarfModel::createBodyLayer);
        event.registerLayerDefinition(MAModelLayer.TRUE_ENDERIAN_SCARF, TrueEnderianScarfModel::createBodyLayer);
        event.registerLayerDefinition(MAModelLayer.SPECTRE_AMULET, SpectreAmuletModel::createBodyLayer);
        event.registerLayerDefinition(MAModelLayer.VENOM_AMULET, VenomAmuletModel::createBodyLayer);
        event.registerLayerDefinition(MAModelLayer.DECAY_AMULET, DecayAmuletModel::createBodyLayer);
        event.registerLayerDefinition(MAModelLayer.NECROPLASM_AMULET, NecroplasmAmuletModel::createBodyLayer);
        event.registerLayerDefinition(MAModelLayer.MAGIC_QUIVER, MagicQuiverModel::createBodyLayer);
        event.registerLayerDefinition(MAModelLayer.MECHANICAL_GLOVE, MechanicalGloveModel::createBodyLayer);
    }
}