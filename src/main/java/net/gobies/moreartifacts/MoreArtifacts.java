package net.gobies.moreartifacts;

import com.mojang.logging.LogUtils;
import net.gobies.moreartifacts.compat.enhancedvisuals.EnhancedVisualsRender;
import net.gobies.moreartifacts.compat.iceandfire.IceStoneFreeze;
import net.gobies.moreartifacts.compat.spartanweaponry.EnvenomedQuiverCrossbow;
import net.gobies.moreartifacts.compat.spartanweaponry.MagicQuiverCrossbow;
import net.gobies.moreartifacts.compat.spartanweaponry.MoltenQuiverCrossbow;
import net.gobies.moreartifacts.init.MoreArtifactsBrewing;
import net.gobies.moreartifacts.init.MoreArtifactsCurioHandler;
import net.gobies.moreartifacts.init.MoreArtifactsModelLayer;
import net.gobies.moreartifacts.item.ModCreativeModeTabs;
import net.gobies.moreartifacts.item.ModItems;
import net.gobies.moreartifacts.loot.ModLootModifiers;
import net.gobies.moreartifacts.network.NetworkHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.util.thread.SidedThreadGroups;
import org.slf4j.Logger;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import static net.gobies.moreartifacts.MoreArtifacts.MOD_ID;


@Mod(MOD_ID)
public class MoreArtifacts {
    public static final String MOD_ID = "moreartifacts";
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Collection<AbstractMap.SimpleEntry<Runnable, Integer>> workQueue = new ConcurrentLinkedQueue<>();

    public MoreArtifacts() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModCreativeModeTabs.register(modBus);

        ModLootModifiers.register(modBus);

        MoreArtifactsCurioHandler.register();

        modBus.addListener(this::setupEntityModelLayers);

        modBus.addListener(this::setup);

        MoreArtifactsBrewing.register(modBus);

        ModItems.register(modBus);

        NetworkHandler.register();

        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);

    }

        private void setup(final FMLCommonSetupEvent event) {
            event.enqueueWork(() -> {

                if (ModList.get().isLoaded("spartanweaponry")) {
                    MagicQuiverCrossbow.loadCompat();
                    EnvenomedQuiverCrossbow.loadCompat();
                    MoltenQuiverCrossbow.loadCompat();
                }
                if (ModList.get().isLoaded("enhancedvisuals") && (Config.TRUE_ENDERIAN_COMPAT.get())) {
                    EnhancedVisualsRender.loadCompat();
                }
                if (ModList.get().isLoaded("iceandfire") && (Config.ICE_STONE_COMPAT.get())) {
                    IceStoneFreeze.loadCompat();
                }
            });
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
                work.setValue((Integer) work.getValue() - 1);
                if ((Integer) work.getValue() == 0) {
                    actions.add(work);
                }

            });
            actions.forEach((e) -> ((Runnable) e.getKey()).run());
            workQueue.removeAll(actions);
        }
    }
    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
    private void setupEntityModelLayers(final EntityRenderersEvent.RegisterLayerDefinitions event) {
        MoreArtifactsModelLayer.registers(event);
    }

}







