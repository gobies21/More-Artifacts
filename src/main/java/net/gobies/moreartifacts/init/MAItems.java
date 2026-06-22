package net.gobies.moreartifacts.init;

import net.gobies.moreartifacts.MoreArtifacts;
import net.gobies.moreartifacts.item.artifacts.*;
import net.gobies.moreartifacts.item.misc.*;
import net.gobies.moreartifacts.item.materials.ArtifactSmithingTemplateItem;
import net.gobies.moreartifacts.item.materials.ShadowDustItem;
import net.gobies.moreartifacts.item.souls.BloodSoulItem;
import net.gobies.moreartifacts.item.souls.ShadowSoulItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.List;

public class MAItems {
    public static final DeferredRegister<Item> ARTIFACTS;
    public static final RegistryObject<Item> Bezoar;
    public static final RegistryObject<Item> Vitamins;
    public static final RegistryObject<Item> FastClock;
    public static final RegistryObject<Item> Sunglasses;
    public static final RegistryObject<Item> Nectar;
    public static final RegistryObject<Item> DesertCharm;
    public static final RegistryObject<Item> ShulkerHeart;
    public static final RegistryObject<Item> WitherShard;
    public static final RegistryObject<Item> SculkLens;
    public static final RegistryObject<Item> IceCrystal;
    public static final RegistryObject<Item> WitheredBezoar;
    public static final RegistryObject<Item> SculkShades;
    public static final RegistryObject<Item> ShulkedClock;
    public static final RegistryObject<Item> PurificationCharm;
    public static final RegistryObject<Item> ObsidianSkull;
    public static final RegistryObject<Item> AnkhCharm;
    public static final RegistryObject<Item> CobaltShield;
    public static final RegistryObject<Item> ObsidianShield;
    public static final RegistryObject<Item> AnkhShield;
    public static final RegistryObject<Item> MelodyPlushie;
    public static final RegistryObject<Item> HeroShield;
    public static final RegistryObject<Item> MechanicalGlove;
    public static final RegistryObject<Item> EnderianScarf;
    public static final RegistryObject<Item> TrueEnderianScarf;
    public static final RegistryObject<Item> GildedScarf;
    public static final RegistryObject<Item> Balloon;
    public static final RegistryObject<Item> LuckyEmeraldRing;
    public static final RegistryObject<Item> SpectreAmulet;
    public static final RegistryObject<Item> VenomAmulet;
    public static final RegistryObject<Item> DecayAmulet;
    public static final RegistryObject<Item> NecroplasmAmulet;
    public static final RegistryObject<Item> Shackle;
    public static final RegistryObject<Item> MagicQuiver;
    public static final RegistryObject<Item> EnvenomedQuiver;
    public static final RegistryObject<Item> MoltenQuiver;
    public static final RegistryObject<Item> WoodenHeadgear;
    public static final RegistryObject<Item> GoldenHeadgear;
    public static final RegistryObject<Item> NetheriteHeadgear;
    public static final RegistryObject<Item> EnderianEye;
    public static final RegistryObject<Item> EnderDragonClaw;
    public static final RegistryObject<Item> MechanicalClaw;
    public static final RegistryObject<Item> EchoGlove;
    public static final RegistryObject<Item> VenomStone;
    public static final RegistryObject<Item> DecayStone;
    public static final RegistryObject<Item> FireStone;
    public static final RegistryObject<Item> IceStone;
    public static final RegistryObject<Item> VanirMask;
    public static final RegistryObject<Item> RubyRing;
    public static final RegistryObject<Item> TaintedMirror;
    public static final RegistryObject<Item> NaturesMantle;
    public static final RegistryObject<Item> LeatherTreads;
    public static final RegistryObject<Item> DuneTreads;
    public static final RegistryObject<Item> BlazingTreads;
    public static final RegistryObject<Item> EnderianTreads;
    public static final RegistryObject<Item> SculkTreads;
    public static final RegistryObject<Item> MechanicalGears;
    public static final RegistryObject<Item> HighJumpers;
    public static final RegistryObject<Item> DragonEye;
    public static final RegistryObject<Item> RecallPotion;
    public static final RegistryObject<Item> ShadowDust;
    public static final RegistryObject<Item> ArtifactSmithingTemplate;

    public static final RegistryObject<Item> FrozenQuiver;
    public static final RegistryObject<Item> FrostedShield;
    public static final RegistryObject<Item> FrostGauntlet;

    public static final RegistryObject<Item> FieryScarf;
    public static final RegistryObject<Item> StealthManual;
    public static final RegistryObject<Item> CursedDoll;
    public static final RegistryObject<Item> GuardianThornNecklace;
    public static final RegistryObject<Item> HolyMantle;

    public static final RegistryObject<Item> BrokenHeart;

    public static final RegistryObject<Item> ShadowSoul;
    public static final RegistryObject<Item> BloodSoul;

    public static final RegistryObject<Item> GraveScroll;
    public static final RegistryObject<Item> Necklace;

    public static void register(IEventBus eventBus) {
        ARTIFACTS.register(eventBus);
    }

    static {
        ARTIFACTS = DeferredRegister.create(ForgeRegistries.ITEMS, MoreArtifacts.MOD_ID);
        Bezoar = ARTIFACTS.register("bezoar", () -> new BezoarItem(new Item.Properties()));
        Vitamins = ARTIFACTS.register("vitamins", () -> new VitaminsItem(new Item.Properties()));
        FastClock = ARTIFACTS.register("fast_clock", () -> new FastClockItem(new Item.Properties()));
        Sunglasses = ARTIFACTS.register("sunglasses", () -> new SunglassesItem(new Item.Properties()));
        Nectar = ARTIFACTS.register("nectar", () -> new NectarItem(new Item.Properties()));
        DesertCharm = ARTIFACTS.register("desert_charm", () -> new DesertCharmItem(new Item.Properties()));
        ShulkerHeart = ARTIFACTS.register("shulker_heart", () -> new ShulkerHeartItem(new Item.Properties()));
        WitherShard = ARTIFACTS.register("wither_shard", () -> new WitherShardItem(new Item.Properties()));
        SculkLens = ARTIFACTS.register("sculk_lens", () -> new SculkLensItem(new Item.Properties()));
        IceCrystal = ARTIFACTS.register("ice_crystal", () -> new IceCrystalItem(new Item.Properties()));
        WitheredBezoar = ARTIFACTS.register("withered_bezoar", () -> new WitheredBezoarItem(new Item.Properties()));
        SculkShades = ARTIFACTS.register("sculk_shades", () -> new SculkShadesItem(new Item.Properties()));
        ShulkedClock = ARTIFACTS.register("shulked_clock", () -> new ShulkedClockItem(new Item.Properties()));
        PurificationCharm = ARTIFACTS.register("purification_charm", () -> new PurificationCharmItem(new Item.Properties()));
        ObsidianSkull = ARTIFACTS.register("obsidian_skull", () -> new ObsidianSkullItem(new Item.Properties()));
        AnkhCharm = ARTIFACTS.register("ankh_charm", () -> new AnkhCharmItem(new Item.Properties()));
        CobaltShield = ARTIFACTS.register("cobalt_shield", () -> new CobaltShieldItem(new Item.Properties()));
        ObsidianShield = ARTIFACTS.register("obsidian_shield", () -> new ObsidianShieldItem(new Item.Properties()));
        AnkhShield = ARTIFACTS.register("ankh_shield", () -> new AnkhShieldItem(new Item.Properties()));
        MelodyPlushie = ARTIFACTS.register("melody_plushie", () -> new MelodyPlushieItem(new Item.Properties()));
        HeroShield = ARTIFACTS.register("hero_shield", () -> new HeroShieldItem(new Item.Properties()));
        MechanicalGlove = ARTIFACTS.register("mechanical_glove", () -> new MechanicalGloveItem(new Item.Properties()));
        EnderianScarf = ARTIFACTS.register("enderian_scarf", () -> new EnderianScarfItem(new Item.Properties()));
        TrueEnderianScarf = ARTIFACTS.register("true_enderian_scarf", () -> new TrueEnderianScarfItem(new Item.Properties()));
        GildedScarf = ARTIFACTS.register("gilded_scarf", () -> new GildedScarfItem(new Item.Properties()));
        Balloon = ARTIFACTS.register("balloon", () -> new BalloonItem(new Item.Properties()));
        LuckyEmeraldRing = ARTIFACTS.register("lucky_emerald_ring", () -> new LuckyEmeraldRingItem(new Item.Properties()));
        SpectreAmulet = ARTIFACTS.register("spectre_amulet", () -> new SpectreAmuletItem(new Item.Properties()));
        VenomAmulet = ARTIFACTS.register("venom_amulet", () -> new VenomAmuletItem(new Item.Properties()));
        DecayAmulet = ARTIFACTS.register("decay_amulet", () -> new DecayAmuletItem(new Item.Properties()));
        NecroplasmAmulet = ARTIFACTS.register("necroplasm_amulet", () -> new NecroplasmAmuletItem(new Item.Properties()));
        Shackle = ARTIFACTS.register("shackle", () -> new ShackleItem(new Item.Properties()));
        MagicQuiver = ARTIFACTS.register("magic_quiver", () -> new MagicQuiverItem(new Item.Properties()));
        EnvenomedQuiver = ARTIFACTS.register("envenomed_quiver", () -> new EnvenomedQuiverItem(new Item.Properties()));
        MoltenQuiver = ARTIFACTS.register("molten_quiver", () -> new MoltenQuiverItem(new Item.Properties()));
        WoodenHeadgear = ARTIFACTS.register("wooden_headgear", () -> new WoodenHeadgearItem(new Item.Properties()));
        GoldenHeadgear = ARTIFACTS.register("golden_headgear", () -> new GoldenHeadgearItem(new Item.Properties()));
        NetheriteHeadgear = ARTIFACTS.register("netherite_headgear", () -> new NetheriteHeadgearItem(new Item.Properties()));
        EnderianEye = ARTIFACTS.register("enderian_eye", () -> new EnderianEyeItem(new Item.Properties()));
        EnderDragonClaw = ARTIFACTS.register("ender_dragon_claw", () -> new EnderDragonClawItem(new Item.Properties()));
        MechanicalClaw = ARTIFACTS.register("mechanical_claw", () -> new MechanicalClawItem(new Item.Properties()));
        EchoGlove = ARTIFACTS.register("echo_glove", () -> new EchoGloveItem(new Item.Properties()));
        VenomStone = ARTIFACTS.register("venom_stone", () -> new VenomStoneItem(new Item.Properties()));
        DecayStone = ARTIFACTS.register("decay_stone", () -> new DecayStoneItem(new Item.Properties()));
        FireStone = ARTIFACTS.register("fire_stone", () -> new FireStoneItem(new Item.Properties()));
        IceStone = ARTIFACTS.register("ice_stone", () -> new IceStoneItem(new Item.Properties()));
        RubyRing = ARTIFACTS.register("ruby_ring", () -> new RubyRingItem(new Item.Properties()));
        TaintedMirror = ARTIFACTS.register("tainted_mirror", () -> new TaintedMirrorItem(new Item.Properties()));
        NaturesMantle = ARTIFACTS.register("natures_mantle", () -> new NaturesMantleItem(new Item.Properties()));
        VanirMask = ARTIFACTS.register("vanir_mask", () -> new VanirMaskItem(new Item.Properties()));
        LeatherTreads = ARTIFACTS.register("leather_treads", () -> new LeatherTreadsItem(new Item.Properties()));
        DuneTreads = ARTIFACTS.register("dune_treads", () -> new DuneTreadsItem(new Item.Properties()));
        BlazingTreads = ARTIFACTS.register("blazing_treads", () -> new BlazingTreadsItem(new Item.Properties()));
        EnderianTreads = ARTIFACTS.register("enderian_treads", () -> new EnderianTreadsItem(new Item.Properties()));
        SculkTreads = ARTIFACTS.register("sculk_treads", () -> new SculkTreadsItem(new Item.Properties()));
        MechanicalGears = ARTIFACTS.register("mechanical_gears", () -> new MechanicalGearsItem(new Item.Properties()));
        HighJumpers = ARTIFACTS.register("high_jumpers", () -> new HighJumpersItem(new Item.Properties()));
        DragonEye = ARTIFACTS.register("dragon_eye", () -> new DragonEyeItem(new Item.Properties()));

        FrozenQuiver = ARTIFACTS.register("frozen_quiver", () -> new FrozenQuiverItem(new Item.Properties()));
        FrostedShield = ARTIFACTS.register("frosted_shield", () -> new FrostedShieldItem(new Item.Properties()));
        FrostGauntlet = ARTIFACTS.register("frost_gauntlet", () -> new FrostGauntletItem(new Item.Properties()));

        FieryScarf = ARTIFACTS.register("fiery_scarf", () -> new FieryScarfItem(new Item.Properties()));
        StealthManual = ARTIFACTS.register("stealth_manual", () -> new StealthManualItem(new Item.Properties()));
        CursedDoll = ARTIFACTS.register("cursed_doll", () -> new CursedDollItem(new Item.Properties()));
        GuardianThornNecklace = ARTIFACTS.register("guardian_thorn_necklace", () -> new GuardianThornNecklaceItem(new Item.Properties()));
        HolyMantle = ARTIFACTS.register("holy_mantle", () -> new HolyMantleItem(new Item.Properties()));
        BrokenHeart = ARTIFACTS.register("broken_heart", () -> new BrokenHeartItem(new Item.Properties()));
        ShadowSoul = ARTIFACTS.register("shadow_soul", () -> new ShadowSoulItem(new Item.Properties()));
        BloodSoul = ARTIFACTS.register("blood_soul", () -> new BloodSoulItem(new Item.Properties()));


        Necklace = ARTIFACTS.register("necklace", () -> new NecklaceItem(new Item.Properties()));

        RecallPotion = ARTIFACTS.register("recall_potion", () -> new RecallPotionItem(new Item.Properties()));
        GraveScroll = ARTIFACTS.register("grave_scroll", () -> new GraveScrollItem(new Item.Properties()));
        ShadowDust = ARTIFACTS.register("shadow_dust", () -> new ShadowDustItem(new Item.Properties()));
        ArtifactSmithingTemplate = ARTIFACTS.register("artifact_upgrade_smithing_template", () -> new ArtifactSmithingTemplateItem(new Item.Properties()));

    }

    public static List<RegistryObject<Item>> getAllArtifacts() {
        return Arrays.asList(
                Bezoar, Vitamins, FastClock, Sunglasses, Nectar, DesertCharm, ShulkerHeart, WitherShard, SculkLens, IceCrystal, CobaltShield, WitheredBezoar,
                SculkShades, ShulkedClock, PurificationCharm, ObsidianSkull, ObsidianShield, AnkhCharm, AnkhShield, MelodyPlushie, MechanicalGlove,
                EnderianScarf, TrueEnderianScarf, GildedScarf, Balloon, LuckyEmeraldRing, SpectreAmulet, VenomAmulet, DecayAmulet, NecroplasmAmulet,
                Shackle, MagicQuiver, EnvenomedQuiver, MoltenQuiver, WoodenHeadgear, GoldenHeadgear, NetheriteHeadgear, EnderianEye, EnderDragonClaw,
                MechanicalClaw, EchoGlove, VenomStone, DecayStone, FireStone, IceStone, RubyRing, TaintedMirror, NaturesMantle, VanirMask, LeatherTreads,
                DuneTreads, BlazingTreads, EnderianTreads, SculkTreads, MechanicalGears, HighJumpers, DragonEye, FrozenQuiver, FrostedShield, FrostGauntlet,
                FieryScarf, StealthManual, CursedDoll, GuardianThornNecklace, HolyMantle, BrokenHeart, Necklace, RecallPotion, GraveScroll);
    }
}