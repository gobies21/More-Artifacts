package net.gobies.moreartifacts;

import com.mojang.logging.LogUtils;
import net.gobies.moreartifacts.client.overlay.EnderianEyeOverlay;
import net.gobies.moreartifacts.compat.apothecary.ApothecaryCompat;
import net.gobies.moreartifacts.compat.enhancedvisuals.EnhancedVisualsCompat;
import net.gobies.moreartifacts.compat.iceandfire.IceandFireCompat;
import net.gobies.moreartifacts.compat.spartanweaponry.SpartanWeaponryCompat;
import net.gobies.moreartifacts.init.MABrewing;
import net.gobies.moreartifacts.init.MAModelLayer;
import net.gobies.moreartifacts.init.MAProperties;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.init.MACreativeTab;
import net.gobies.moreartifacts.network.PacketHandler;
import net.gobies.moreartifacts.event.DamageEvents;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
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

        MAItems.register(modBus);

        MACreativeTab.register(modBus);

        modBus.addListener(this::setupEntityModelLayers);

        modBus.addListener(this::commonSetup);

        modBus.addListener(this::clientSetup);

        PacketHandler.register();

        MinecraftForge.EVENT_BUS.register(this);

        MinecraftForge.EVENT_BUS.register(DamageEvents.class);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(MABrewing::register);

        if (isSpartanWeaponryLoaded()) {
            SpartanWeaponryCompat.loadCompat();
            LOGGER.info("[More Artifacts] Spartan Weaponry Compat Loaded");
        }
        if (isEnhancedVisualsLoaded() && (Config.TRUE_ENDERIAN_COMPAT.get())) {
            EnhancedVisualsCompat.loadCompat();
            LOGGER.info("[More Artifacts] Enhanced Visuals Compat Loaded");
        }
        if (isIceandFireLoaded() && (Config.ICE_STONE_COMPAT.get())) {
            IceandFireCompat.loadCompat();
            LOGGER.info("[More Artifacts] Ice and Fire Compat Loaded");
        }
        if (isPotionRingsLoaded()) {
            LOGGER.info("[More Artifacts] Potion Rings Compat Mixin Loaded");
        }
        if (isApothecaryLoaded()) {
            ApothecaryCompat.loadCompat();
            LOGGER.info("[More Artifacts] Apothecary Compat Loaded");
        }
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        MAProperties.addItemProperties();
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

    private void setupEntityModelLayers(final EntityRenderersEvent.RegisterLayerDefinitions event) {
        MAModelLayer.registers(event);
    }

    public static boolean isSpartanWeaponryLoaded() {
        return ModList.get().isLoaded("spartanweaponry");
    }

    public static boolean isEnhancedVisualsLoaded() {
        return ModList.get().isLoaded("enhancedvisuals");
    }

    public static boolean isIceandFireLoaded() {
        return ModList.get().isLoaded("iceandfire");
    }

    public static boolean isPotionRingsLoaded() {
        return ModList.get().isLoaded("potionrings2");
    }

    public static boolean isApothecaryLoaded() {
        return ModList.get().isLoaded("apothecary");
    }
}







