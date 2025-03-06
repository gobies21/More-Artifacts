package net.gobies.moreartifacts.item;

import net.gobies.moreartifacts.MoreArtifacts;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import javax.swing.*;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MoreArtifacts.MOD_ID);
    public static final RegistryObject<CreativeModeTab> MORE_ARTIFACTS_TAB = CREATIVE_MODE_TABS.register("moreartifacts_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.Bezoar.get()))
                    .title(Component.translatable("creativetab.moreartifacts_tab"))
                            .displayItems((pParameters, pOutput) -> {
                                pOutput.accept(ModItems.Bezoar.get());
                                pOutput.accept(ModItems.Vitamins.get());
                            })
                            .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
