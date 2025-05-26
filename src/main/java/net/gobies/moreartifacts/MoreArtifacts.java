package net.gobies.moreartifacts;

import com.mojang.logging.LogUtils;
import net.gobies.moreartifacts.compat.enhancedvisuals.EnhancedVisualsRender;
import net.gobies.moreartifacts.compat.iceandfire.IceStoneFreeze;
import net.gobies.moreartifacts.compat.spartanweaponry.CrossbowQuiver;
import net.gobies.moreartifacts.init.MABrewing;
import net.gobies.moreartifacts.init.MAModelLayer;
import net.gobies.moreartifacts.init.MAProperties;
import net.gobies.moreartifacts.item.MAItems;
import net.gobies.moreartifacts.item.MACreativeTab;
import net.gobies.moreartifacts.loot.ModLootModifiers;
import net.gobies.moreartifacts.network.NetworkHandler;
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

        ModLootModifiers.register(modBus);

        modBus.addListener(this::setupEntityModelLayers);

        modBus.addListener(this::commonSetup);

        modBus.addListener(this::clientSetup);

        MABrewing.register(modBus);

        NetworkHandler.register();

        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        if (ModList.get().isLoaded("spartanweaponry")) {
            CrossbowQuiver.loadCompat();
            LOGGER.info("[More Artifacts] Spartan Weaponry Compat Loaded");
        }
        if (ModList.get().isLoaded("enhancedvisuals") && (Config.TRUE_ENDERIAN_COMPAT.get())) {
            EnhancedVisualsRender.loadCompat();
            LOGGER.info("[More Artifacts] Enhanced Visuals Compat Loaded");
        }
        if (ModList.get().isLoaded("iceandfire") && (Config.ICE_STONE_COMPAT.get())) {
            IceStoneFreeze.loadCompat();
            LOGGER.info("[More Artifacts] Ice and Fire Compat Loaded");
        }
        if (ModList.get().isLoaded("potionrings2")) {
            LOGGER.info("[More Artifacts] Potion Rings Compat Mixin Loaded");
        }
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        MAProperties.addItemProperties();
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

}







