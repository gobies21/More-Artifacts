package net.gobies.moreartifacts.init;

import net.gobies.moreartifacts.MoreArtifacts;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class MACreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MoreArtifacts.MOD_ID);
    public static final RegistryObject<CreativeModeTab> MORE_ARTIFACTS_TAB = CREATIVE_MODE_TABS.register("moreartifacts_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(MAItems.MelodyPlushie.get()))
                    .title(Component.translatable("creativetab.moreartifacts_tab"))
                            .displayItems((pParameters, pOutput) -> {
                                pOutput.accept(MAItems.Bezoar.get());
                                pOutput.accept(MAItems.Vitamins.get());
                                pOutput.accept(MAItems.FastClock.get());
                                pOutput.accept(MAItems.Sunglasses.get());
                                pOutput.accept(MAItems.Nectar.get());
                                pOutput.accept(MAItems.DesertCharm.get());
                                pOutput.accept(MAItems.ShulkerHeart.get());
                                pOutput.accept(MAItems.WitherShard.get());
                                pOutput.accept(MAItems.SculkLens.get());
                                pOutput.accept(MAItems.WitheredBezoar.get());
                                pOutput.accept(MAItems.SculkShades.get());
                                pOutput.accept(MAItems.ShulkedClock.get());
                                pOutput.accept(MAItems.PurificationCharm.get());
                                pOutput.accept(MAItems.ObsidianSkull.get());
                                pOutput.accept(MAItems.AnkhCharm.get());
                                pOutput.accept(MAItems.CobaltShield.get());
                                pOutput.accept(MAItems.ObsidianShield.get());
                                pOutput.accept(MAItems.AnkhShield.get());
                                pOutput.accept(MAItems.MelodyPlushie.get());
                                pOutput.accept(MAItems.HeroShield.get());
                                pOutput.accept(MAItems.MechanicalGlove.get());
                                pOutput.accept(MAItems.MechanicalClaw.get());
                                pOutput.accept(MAItems.EchoGlove.get());
                                pOutput.accept(MAItems.EnderianScarf.get());
                                pOutput.accept(MAItems.TrueEnderianScarf.get());
                                pOutput.accept(MAItems.GildedScarf.get());
                                pOutput.accept(MAItems.Balloon.get());
                                pOutput.accept(MAItems.LuckyEmeraldRing.get());
                                pOutput.accept(MAItems.SpectreAmulet.get());
                                pOutput.accept(MAItems.VenomAmulet.get());
                                pOutput.accept(MAItems.DecayAmulet.get());
                                pOutput.accept(MAItems.NecroplasmAmulet.get());
                                pOutput.accept(MAItems.Shackle.get());
                                pOutput.accept(MAItems.MagicQuiver.get());
                                pOutput.accept(MAItems.EnvenomedQuiver.get());
                                pOutput.accept(MAItems.MoltenQuiver.get());
                                pOutput.accept(MAItems.WoodenHeadgear.get());
                                pOutput.accept(MAItems.GoldenHeadgear.get());
                                pOutput.accept(MAItems.NetheriteHeadgear.get());
                                pOutput.accept(MAItems.EnderianEye.get());
                                pOutput.accept(MAItems.EnderDragonClaw.get());
                                pOutput.accept(MAItems.VenomStone.get());
                                pOutput.accept(MAItems.DecayStone.get());
                                pOutput.accept(MAItems.FireStone.get());
                                pOutput.accept(MAItems.IceStone.get());
                                pOutput.accept(MAItems.VanirMask.get());
                                pOutput.accept(MAItems.RecallPotion.get());
                                pOutput.accept(MAItems.ShadowDust.get());
                                pOutput.accept(MAItems.ArtifactSmithingTemplate.get());
                            })
                            .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
