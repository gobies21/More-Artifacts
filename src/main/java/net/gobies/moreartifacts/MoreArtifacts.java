package net.gobies.moreartifacts;

import com.mojang.logging.LogUtils;
import net.gobies.moreartifacts.compat.apothecary.ApothecaryCompat;
import net.gobies.moreartifacts.compat.enhancedvisuals.EnhancedVisualsCompat;
import net.gobies.moreartifacts.compat.firstaid.FirstAidCompat;
import net.gobies.moreartifacts.compat.iceandfire.IceandFireCompat;
import net.gobies.moreartifacts.compat.jei.JeiCompat;
import net.gobies.moreartifacts.compat.spartanweaponry.SpartanWeaponryCompat;
import net.gobies.moreartifacts.config.ClientConfig;
import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.effect.MAStatusEffects;
import net.gobies.moreartifacts.event.DamageEvents;
import net.gobies.moreartifacts.event.MAEvents;
import net.gobies.moreartifacts.event.SoulEvents;
import net.gobies.moreartifacts.helper.LifecycleManager;
import net.gobies.moreartifacts.init.*;
import net.gobies.moreartifacts.item.artifacts.DragonEyeItem;
import net.gobies.moreartifacts.item.souls.ShadowSoulItem;
import net.gobies.moreartifacts.network.ManageRequests;
import net.gobies.moreartifacts.network.PacketHandler;
import net.gobies.moreartifacts.util.CooldownHandler;
import net.gobies.moreartifacts.util.ModLoadedUtil;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.*;

@Mod(MoreArtifacts.MOD_ID)
public class MoreArtifacts {
    public static final String MOD_ID = "moreartifacts";
    public static final Logger LOGGER = LogUtils.getLogger();

    public MoreArtifacts() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        MAItems.register(modBus);
        MAEffects.register(modBus);
        MAParticles.register(modBus);
        MASounds.register(modBus);
        MACreativeTab.register(modBus);
        MAEvents.register();
        SoulEvents.register();
        modBus.addListener(this::commonSetup);
        modBus.addListener(this::clientSetup);
        modBus.addListener(this::setupEntityModelLayers);
        PacketHandler.registerMessages();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
        cleanupMaps();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(MABrewing::register);
        if (ModLoadedUtil.isSpartanWeaponryLoaded()) {
            SpartanWeaponryCompat.loadCompat();
            log("[More Artifacts] Spartan Weaponry Compat Loaded");
        }
        if (ModLoadedUtil.isEnhancedVisualsLoaded()) {
            EnhancedVisualsCompat.loadCompat();
            log("[More Artifacts] Enhanced Visuals Compat Loaded");
        }
        if (ModLoadedUtil.isIceandFireLoaded()) {
            IceandFireCompat.loadCompat();
            log("[More Artifacts] Ice and Fire Compat Loaded");

            if (ModLoadedUtil.isJeiLoaded()) {
                MinecraftForge.EVENT_BUS.register(JeiCompat.class);
            }
        }
        if (ModLoadedUtil.isPotionRingsLoaded()) {
            log("[More Artifacts] Potion Rings Compat Mixin Loaded");
        }
        if (ModLoadedUtil.isApothecaryLoaded()) {
            ApothecaryCompat.loadCompat();
            log("[More Artifacts] Apothecary Compat Loaded");
        }
        if (ModLoadedUtil.isFirstAidLoaded()) {
            FirstAidCompat.loadCompat();
            log("[More Artifacts] First Aid Compat Loaded");
        }
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(MAProperties::addItemProperties);
        event.enqueueWork(MAProperties::registerItemProperties);
        MAModels.modelSetup();
    }

    private static void log(String message) {
        LOGGER.info(message);
    }

    private void setupEntityModelLayers(final EntityRenderersEvent.RegisterLayerDefinitions event) {
        MAModels.registerLayerDefinitions(event);
    }

    private void cleanupMaps() {
        LifecycleManager.registerCleanupHook(DamageEvents::clearMaps);
        LifecycleManager.registerCleanupHook(CooldownHandler::clearMaps);
        LifecycleManager.registerCleanupHook(DragonEyeItem::clearMaps);
        LifecycleManager.registerCleanupHook(ShadowSoulItem::clearMaps);
        LifecycleManager.registerCleanupHook(MAStatusEffects::clearMaps);
        LifecycleManager.registerCleanupHook(SoulEvents::clearMaps);
        LifecycleManager.registerCleanupHook(ManageRequests::clearMaps);
    }
}







