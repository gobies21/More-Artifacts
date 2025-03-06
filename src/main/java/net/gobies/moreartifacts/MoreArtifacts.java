package net.gobies.moreartifacts;

import com.mojang.logging.LogUtils;
import net.gobies.moreartifacts.item.ModCreativeModeTabs;
import net.gobies.moreartifacts.item.ModItems;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import static net.gobies.moreartifacts.MoreArtifacts.MOD_ID;

@Mod(MOD_ID)
public class MoreArtifacts {

    public MoreArtifacts() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static final String MOD_ID = "moreartifacts";
    private static final Logger LOGGER = LogUtils.getLogger();

    public MoreArtifacts(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);

        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }


    private void commonSetup(final FMLCommonSetupEvent event) {


        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
    }
}

