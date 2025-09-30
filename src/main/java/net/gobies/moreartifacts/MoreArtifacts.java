package net.gobies.moreartifacts;

import com.mojang.logging.LogUtils;
import net.gobies.moreartifacts.client.overlay.EnderianEyeOverlay;
import net.gobies.moreartifacts.compat.apothecary.ApothecaryCompat;
import net.gobies.moreartifacts.compat.enhancedvisuals.EnhancedVisualsCompat;
import net.gobies.moreartifacts.compat.iceandfire.IceandFireCompat;
import net.gobies.moreartifacts.compat.spartanweaponry.SpartanWeaponryCompat;
import net.gobies.moreartifacts.config.ClientConfig;
import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.event.MAEvents;
import net.gobies.moreartifacts.init.*;
import net.gobies.moreartifacts.network.PacketHandler;
import net.gobies.moreartifacts.util.ModLoadedUtil;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.util.thread.SidedThreadGroups;
import org.slf4j.Logger;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

@Mod(MoreArtifacts.MOD_ID)
public class MoreArtifacts {
    public static final String MOD_ID = "moreartifacts";
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Collection<AbstractMap.SimpleEntry<Runnable, Integer>> workQueue = new ConcurrentLinkedQueue<>();

    public MoreArtifacts() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        MinecraftForge.EVENT_BUS.register(this);

        MAItems.register(modBus);

        MAEffects.register(modBus);

        MACreativeTab.register(modBus);

        MAEvents.register();

        modBus.addListener(this::commonSetup);

        modBus.addListener(this::clientSetup);

        modBus.addListener(this::setupEntityModelLayers);

        PacketHandler.register();

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);

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
        }
        if (ModLoadedUtil.isPotionRingsLoaded()) {
            log("[More Artifacts] Potion Rings Compat Mixin Loaded");
        }
        if (ModLoadedUtil.isApothecaryLoaded()) {
            ApothecaryCompat.loadCompat();
            log("[More Artifacts] Apothecary Compat Loaded");
        }
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(MAProperties::addItemProperties);
        MAModels.modelSetup();
        MinecraftForge.EVENT_BUS.register(EnderianEyeOverlay.class);
    }

    public static void queueServerWork(int tick, Runnable action) {
        if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER) {
            workQueue.add(new AbstractMap.SimpleEntry<>(action, tick));
        }
    }

    @SubscribeEvent
    public void tick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            List<AbstractMap.SimpleEntry<Runnable, Integer>> actions = new ArrayList<>();
            workQueue.forEach((work) -> {
                work.setValue(work.getValue() - 1);
                if (work.getValue() == 0) {
                    actions.add(work);
                }

            });
            actions.forEach((e) -> (e.getKey()).run());
            workQueue.removeAll(actions);
        }
    }

    private static void log(String message) {
        LOGGER.info(message);
    }

    private void setupEntityModelLayers(final EntityRenderersEvent.RegisterLayerDefinitions event) {
        MAModels.registerLayerDefinitions(event);
    }
}







