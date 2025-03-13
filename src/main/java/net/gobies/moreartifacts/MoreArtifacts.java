package net.gobies.moreartifacts;

import com.mojang.logging.LogUtils;
import net.gobies.moreartifacts.compat.enhancedvisuals.EnhancedVisualsRender;
import net.gobies.moreartifacts.compat.spartanweaponry.EnvenomedQuiverCrossbow;
import net.gobies.moreartifacts.compat.spartanweaponry.MagicQuiverCrossbow;
import net.gobies.moreartifacts.compat.spartanweaponry.MoltenQuiverCrossbow;
import net.gobies.moreartifacts.item.ModCreativeModeTabs;
import net.gobies.moreartifacts.item.ModItems;
import net.gobies.moreartifacts.loot.ModLootModifiers;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import static net.gobies.moreartifacts.MoreArtifacts.MOD_ID;

@Mod(MOD_ID)
public class MoreArtifacts {

    public static final String MOD_ID = "moreartifacts";
    private static final Logger LOGGER = LogUtils.getLogger();

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
    }
        @SubscribeEvent
        public static void onClientSetup (FMLClientSetupEvent event){
            if (ModList.get().isLoaded("enhancedvisuals")) {
                EnhancedVisualsRender.loadCompat();
            }
        }
    }








