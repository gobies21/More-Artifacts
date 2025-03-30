package net.gobies.moreartifacts;

import com.mojang.logging.LogUtils;
import net.gobies.moreartifacts.compat.enhancedvisuals.EnhancedVisualsRender;
import net.gobies.moreartifacts.compat.spartanweaponry.EnvenomedQuiverCrossbow;
import net.gobies.moreartifacts.compat.spartanweaponry.MagicQuiverCrossbow;
import net.gobies.moreartifacts.compat.spartanweaponry.MoltenQuiverCrossbow;
import net.gobies.moreartifacts.item.ModCreativeModeTabs;
import net.gobies.moreartifacts.item.ModItems;
import net.gobies.moreartifacts.loot.ModLootModifiers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.util.thread.SidedThreadGroups;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.slf4j.Logger;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

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

        ModItems.register(modBus);

        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        if (ModList.get().isLoaded("spartanweaponry")) {
            MagicQuiverCrossbow.loadCompat();
            EnvenomedQuiverCrossbow.loadCompat();
            MoltenQuiverCrossbow.loadCompat();

        }
        if (ModList.get().isLoaded("enhancedvisuals")) {
            EnhancedVisualsRender.loadCompat();
        }
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
                work.setValue((Integer)work.getValue() - 1);
                if ((Integer)work.getValue() == 0) {
                    actions.add(work);
                }

            });
            actions.forEach((e) -> ((Runnable)e.getKey()).run());
            workQueue.removeAll(actions);
        }

    }
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation(MOD_ID, MOD_ID), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
    private static int messageID = 0;

    public static <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
        PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
        messageID++;
    }

}








