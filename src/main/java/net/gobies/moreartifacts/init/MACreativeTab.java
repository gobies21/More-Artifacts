package net.gobies.moreartifacts.init;

import net.gobies.moreartifacts.MoreArtifacts;
import net.gobies.moreartifacts.item.artifacts.DragonEyeItem;
import net.gobies.moreartifacts.util.ModLoadedUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class MACreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS;
    public static final RegistryObject<CreativeModeTab> MORE_ARTIFACTS_TAB;

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

    static {
        CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MoreArtifacts.MOD_ID);
        MORE_ARTIFACTS_TAB = CREATIVE_MODE_TABS.register("moreartifacts_tab",
                () -> CreativeModeTab.builder()
                        .icon(() -> new ItemStack(MAItems.MelodyPlushie.get()))
                        .title(Component.translatable("creativetab.moreartifacts_tab"))
                        .displayItems((parameters, output) -> {
                            MAItems.ARTIFACTS.getEntries().forEach(item -> {
                                output.accept(item.get());
                            });
                            /*
                            if (ModLoadedUtil.isIceandFireLoaded()) {
                                output.accept(DragonEyeItem.createFireDragonEye());
                                output.accept(DragonEyeItem.createIceDragonEye());
                                output.accept(DragonEyeItem.createLightningDragonEye());
                            }

                             */
                        })
                        .build()
        );
    }
}
