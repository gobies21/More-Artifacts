package net.gobies.moreartifacts.init;

import net.gobies.moreartifacts.MoreArtifacts;
import net.gobies.moreartifacts.item.artifacts.*;
import net.gobies.moreartifacts.item.materials.ArtifactSmithingTemplateItem;
import net.gobies.moreartifacts.item.materials.ShadowDustItem;
import net.gobies.moreartifacts.item.potions.RecallPotionItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.List;

public class MAItems {
    public static final DeferredRegister<Item> ITEMS;
    public static final RegistryObject<Item> Bezoar;
    public static final RegistryObject<Item> Vitamins;
    public static final RegistryObject<Item> FastClock;
    public static final RegistryObject<Item> Sunglasses;
    public static final RegistryObject<Item> Nectar;
    public static final RegistryObject<Item> DesertCharm;
    public static final RegistryObject<Item> ShulkerHeart;
    public static final RegistryObject<Item> WitherShard;
    public static final RegistryObject<Item> SculkLens;
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
    public static final RegistryObject<Item> LeatherTreads;
    public static final RegistryObject<Item> DuneTreads;
    public static final RegistryObject<Item> BlazingTreads;
    public static final RegistryObject<Item> EnderianTreads;
    public static final RegistryObject<Item> SculkTreads;
    public static final RegistryObject<Item> MechanicalGears;
    public static final RegistryObject<Item> HighJumpers;
    public static final RegistryObject<Item> RubyRing;
    public static final RegistryObject<Item> RecallPotion;
    public static final RegistryObject<Item> ShadowDust;
    public static final RegistryObject<Item> ArtifactSmithingTemplate;

    public MAItems() {
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    static {
        ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MoreArtifacts.MOD_ID);
        Bezoar = ITEMS.register("bezoar", () -> new BezoarItem(new Item.Properties()));
        Vitamins = ITEMS.register("vitamins", () -> new VitaminsItem(new Item.Properties()));
        FastClock = ITEMS.register("fast_clock", () -> new FastClockItem(new Item.Properties()));
        Sunglasses = ITEMS.register("sunglasses", () -> new SunglassesItem(new Item.Properties()));
        Nectar = ITEMS.register("nectar", () -> new NectarItem(new Item.Properties()));
        DesertCharm = ITEMS.register("desert_charm", () -> new DesertCharmItem(new Item.Properties()));
        ShulkerHeart = ITEMS.register("shulker_heart", () -> new ShulkerHeartItem(new Item.Properties()));
        WitherShard = ITEMS.register("wither_shard", () -> new WitherShardItem(new Item.Properties()));
        SculkLens = ITEMS.register("sculk_lens", () -> new SculkLensItem(new Item.Properties()));
        WitheredBezoar = ITEMS.register("withered_bezoar", () -> new WitheredBezoarItem(new Item.Properties()));
        SculkShades = ITEMS.register("sculk_shades", () -> new SculkShadesItem(new Item.Properties()));
        ShulkedClock = ITEMS.register("shulked_clock", () -> new ShulkedClockItem(new Item.Properties()));
        PurificationCharm = ITEMS.register("purification_charm", () -> new PurificationCharmItem(new Item.Properties()));
        ObsidianSkull = ITEMS.register("obsidian_skull", () -> new ObsidianSkullItem(new Item.Properties()));
        AnkhCharm = ITEMS.register("ankh_charm", () -> new AnkhCharmItem(new Item.Properties()));
        CobaltShield = ITEMS.register("cobalt_shield", () -> new CobaltShieldItem(new Item.Properties()));
        ObsidianShield = ITEMS.register("obsidian_shield", () -> new ObsidianShieldItem(new Item.Properties()));
        AnkhShield = ITEMS.register("ankh_shield", () -> new AnkhShieldItem(new Item.Properties()));
        MelodyPlushie = ITEMS.register("melody_plushie", () -> new MelodyPlushieItem(new Item.Properties()));
        HeroShield = ITEMS.register("hero_shield", () -> new HeroShieldItem(new Item.Properties()));
        MechanicalGlove = ITEMS.register("mechanical_glove", () -> new MechanicalGloveItem(new Item.Properties()));
        EnderianScarf = ITEMS.register("enderian_scarf", () -> new EnderianScarfItem(new Item.Properties()));
        TrueEnderianScarf = ITEMS.register("true_enderian_scarf", () -> new TrueEnderianScarfItem(new Item.Properties()));
        GildedScarf = ITEMS.register("gilded_scarf", () -> new GildedScarfItem(new Item.Properties()));
        Balloon = ITEMS.register("balloon", () -> new BalloonItem(new Item.Properties()));
        LuckyEmeraldRing = ITEMS.register("lucky_emerald_ring", () -> new LuckyEmeraldRingItem(new Item.Properties()));
        SpectreAmulet = ITEMS.register("spectre_amulet", () -> new SpectreAmuletItem(new Item.Properties()));
        VenomAmulet = ITEMS.register("venom_amulet", () -> new VenomAmuletItem(new Item.Properties()));
        DecayAmulet = ITEMS.register("decay_amulet", () -> new DecayAmuletItem(new Item.Properties()));
        NecroplasmAmulet = ITEMS.register("necroplasm_amulet", () -> new NecroplasmAmuletItem(new Item.Properties()));
        Shackle = ITEMS.register("shackle", () -> new ShackleItem(new Item.Properties()));
        MagicQuiver = ITEMS.register("magic_quiver", () -> new MagicQuiverItem(new Item.Properties()));
        EnvenomedQuiver = ITEMS.register("envenomed_quiver", () -> new EnvenomedQuiverItem(new Item.Properties()));
        MoltenQuiver = ITEMS.register("molten_quiver", () -> new MoltenQuiverItem(new Item.Properties()));
        WoodenHeadgear = ITEMS.register("wooden_headgear", () -> new WoodenHeadgearItem(new Item.Properties()));
        GoldenHeadgear = ITEMS.register("golden_headgear", () -> new GoldenHeadgearItem(new Item.Properties()));
        NetheriteHeadgear = ITEMS.register("netherite_headgear", () -> new NetheriteHeadgearItem(new Item.Properties()));
        EnderianEye = ITEMS.register("enderian_eye", () -> new EnderianEyeItem(new Item.Properties()));
        EnderDragonClaw = ITEMS.register("ender_dragon_claw", () -> new EnderDragonClawItem(new Item.Properties()));
        MechanicalClaw = ITEMS.register("mechanical_claw", () -> new MechanicalClawItem(new Item.Properties()));
        EchoGlove = ITEMS.register("echo_glove", () -> new EchoGloveItem(new Item.Properties()));
        VenomStone = ITEMS.register("venom_stone", () -> new VenomStoneItem(new Item.Properties()));
        DecayStone = ITEMS.register("decay_stone", () -> new DecayStoneItem(new Item.Properties()));
        FireStone = ITEMS.register("fire_stone", () -> new FireStoneItem(new Item.Properties()));
        IceStone = ITEMS.register("ice_stone", () -> new IceStoneItem(new Item.Properties()));
        RubyRing = ITEMS.register("ruby_ring", () -> new RubyRingItem(new Item.Properties()));
        VanirMask = ITEMS.register("vanir_mask", () -> new VanirMaskItem(new Item.Properties()));
        LeatherTreads = ITEMS.register("leather_treads", () -> new LeatherTreadsItem(new Item.Properties()));
        DuneTreads = ITEMS.register("dune_treads", () -> new DuneTreadsItem(new Item.Properties()));
        BlazingTreads = ITEMS.register("blazing_treads", () -> new BlazingTreadsItem(new Item.Properties()));
        EnderianTreads = ITEMS.register("enderian_treads", () -> new EnderianTreadsItem(new Item.Properties()));
        SculkTreads = ITEMS.register("sculk_treads", () -> new SculkTreadsItem(new Item.Properties()));
        MechanicalGears = ITEMS.register("mechanical_gears", () -> new MechanicalGearsItem(new Item.Properties()));
        HighJumpers = ITEMS.register("high_jumpers", () -> new HighJumpersItem(new Item.Properties()));
        RecallPotion = ITEMS.register("recall_potion", () -> new RecallPotionItem(new Item.Properties()));
        ShadowDust = ITEMS.register("shadow_dust", () -> new ShadowDustItem(new Item.Properties()));
        ArtifactSmithingTemplate = ITEMS.register("artifact_upgrade_smithing_template", () -> new ArtifactSmithingTemplateItem(new Item.Properties()));

    }

    public static List<RegistryObject<Item>> getAllArtifacts() {
        return Arrays.asList(
                Bezoar, Vitamins, FastClock, Sunglasses, Nectar, DesertCharm, ShulkerHeart, WitherShard, SculkLens, CobaltShield, WitheredBezoar,
                SculkShades, ShulkedClock, PurificationCharm, ObsidianSkull, ObsidianShield, AnkhCharm, AnkhShield, MelodyPlushie, MechanicalGlove,
                EnderianScarf, TrueEnderianScarf, GildedScarf, Balloon, LuckyEmeraldRing, SpectreAmulet, VenomAmulet, DecayAmulet, NecroplasmAmulet,
                Shackle, MagicQuiver, EnvenomedQuiver, MoltenQuiver, WoodenHeadgear, GoldenHeadgear, NetheriteHeadgear, EnderianEye, EnderDragonClaw,
                MechanicalClaw, EchoGlove, VenomStone, DecayStone, FireStone, IceStone, RubyRing, VanirMask);
    }
}