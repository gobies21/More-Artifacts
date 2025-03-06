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
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.MelodyPlushie.get()))
                    .title(Component.translatable("creativetab.moreartifacts_tab"))
                            .displayItems((pParameters, pOutput) -> {
                                pOutput.accept(ModItems.Bezoar.get());
                                pOutput.accept(ModItems.Vitamins.get());
                                pOutput.accept(ModItems.FastClock.get());
                                pOutput.accept(ModItems.Sunglasses.get());
                                pOutput.accept(ModItems.Nectar.get());
                                pOutput.accept(ModItems.DesertCharm.get());
                                pOutput.accept(ModItems.ShulkerHeart.get());
                                pOutput.accept(ModItems.WitherShard.get());
                                pOutput.accept(ModItems.SculkLens.get());
                                pOutput.accept(ModItems.CobaltShield.get());
                                pOutput.accept(ModItems.WitheredBezoar.get());
                                pOutput.accept(ModItems.SculkShades.get());
                                pOutput.accept(ModItems.ShulkedClock.get());
                                pOutput.accept(ModItems.PurificationCharm.get());
                                pOutput.accept(ModItems.ObsidianSkull.get());
                                pOutput.accept(ModItems.ObsidianShield.get());
                                pOutput.accept(ModItems.AnkhCharm.get());
                                pOutput.accept(ModItems.AnkhShield.get());
                                pOutput.accept(ModItems.MelodyPlushie.get());
                                pOutput.accept(ModItems.HeroShield.get());
                            })
                            .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
