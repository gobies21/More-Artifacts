package net.gobies.moreartifacts.item;

import net.gobies.moreartifacts.MoreArtifacts;
import net.gobies.moreartifacts.item.artifacts.*;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item>ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MoreArtifacts.MOD_ID);
    public static final RegistryObject<Item> Bezoar = ITEMS.register("bezoar", () -> new BezoarItem(new Item.Properties()));
    public static final RegistryObject<Item> Vitamins = ITEMS.register("vitamins", () -> new VitaminsItem(new Item.Properties()));
    public static final RegistryObject<Item> FastClock = ITEMS.register("fast_clock", () -> new FastClockItem(new Item.Properties()));
    public static final RegistryObject<Item> Sunglasses = ITEMS.register("sunglasses", () -> new SunglassesItem(new Item.Properties()));
    public static final RegistryObject<Item> Nectar = ITEMS.register("nectar", () -> new NectarItem(new Item.Properties()));
    public static final RegistryObject<Item> DesertCharm = ITEMS.register("desert_charm", () -> new DesertCharmItem(new Item.Properties()));
    public static final RegistryObject<Item> ShulkerHeart = ITEMS.register("shulker_heart", () -> new ShulkerHeartItem(new Item.Properties()));
    public static final RegistryObject<Item> WitherShard = ITEMS.register("wither_shard", () -> new WitherShardItem(new Item.Properties()));
    public static final RegistryObject<Item> SculkLens = ITEMS.register("sculk_lens", () -> new SculkLensItem(new Item.Properties()));
    public static final RegistryObject<Item> CobaltShield = ITEMS.register("cobalt_shield", () -> new CobaltShieldItem(new Item.Properties()));
    public static final RegistryObject<Item> WitheredBezoar = ITEMS.register("withered_bezoar", () -> new WitheredBezoarItem(new Item.Properties()));
    public static final RegistryObject<Item> SculkShades = ITEMS.register("sculk_shades", () -> new SculkShadesItem(new Item.Properties()));
    public static final RegistryObject<Item> ShulkedClock = ITEMS.register("shulked_clock", () -> new ShulkedClockItem(new Item.Properties()));
    public static final RegistryObject<Item> PurificationCharm = ITEMS.register("purification_charm", () -> new PurificationCharmItem(new Item.Properties()));
    public static final RegistryObject<Item> ObsidianSkull = ITEMS.register("obsidian_skull", () -> new ObsidianSkullItem(new Item.Properties()));
    public static final RegistryObject<Item> ObsidianShield = ITEMS.register("obsidian_shield", () -> new ObsidianShieldItem(new Item.Properties()));
    public static final RegistryObject<Item> AnkhCharm = ITEMS.register("ankh_charm", () -> new AnkhCharmItem(new Item.Properties()));
    public static final RegistryObject<Item> AnkhShield = ITEMS.register("ankh_shield", () -> new AnkhShieldItem(new Item.Properties()));
    public static final RegistryObject<Item> MelodyPlushie = ITEMS.register("melody_plushie", () -> new MelodyPlushieItem(new Item.Properties()));
    public static final RegistryObject<Item> HeroShield = ITEMS.register("hero_shield", () -> new HeroShieldItem(new Item.Properties()));
    public static final RegistryObject<Item> MechanicalGlove = ITEMS.register("mechanical_glove", () -> new MechanicalGloveItem(new Item.Properties()));
    public static final RegistryObject<Item> EnderianScarf = ITEMS.register("enderian_scarf", () -> new EnderianScarfItem(new Item.Properties()));
    public static final RegistryObject<Item> TrueEnderianScarf = ITEMS.register("true_enderian_scarf", () -> new TrueEnderianScarfItem(new Item.Properties()));
    public static final RegistryObject<Item> GildedScarf = ITEMS.register("gilded_scarf", () -> new GildedScarfItem(new Item.Properties()));
    public static final RegistryObject<Item> Balloon = ITEMS.register("balloon", () -> new BalloonItem(new Item.Properties()));
    public static final RegistryObject<Item> LuckyEmeraldRing = ITEMS.register("lucky_emerald_ring", () -> new LuckyEmeraldRingItem(new Item.Properties()));
    public static final RegistryObject<Item> SpectreAmulet = ITEMS.register("spectre_amulet", () -> new SpectreAmuletItem(new Item.Properties()));
    public static final RegistryObject<Item> VenomAmulet = ITEMS.register("venom_amulet", () -> new VenomAmuletItem(new Item.Properties()));
    public static final RegistryObject<Item> DecayAmulet = ITEMS.register("decay_amulet", () -> new DecayAmuletItem(new Item.Properties()));
    public static final RegistryObject<Item> NecroplasmAmulet = ITEMS.register("necroplasm_amulet", () -> new NecroplasmAmuletItem(new Item.Properties()));
    public static final RegistryObject<Item> Shackle = ITEMS.register("shackle", () -> new ShackleItem(new Item.Properties()));
    public static final RegistryObject<Item> MagicQuiver = ITEMS.register("magic_quiver", () -> new MagicQuiverItem(new Item.Properties()));
    public static final RegistryObject<Item> EnvenomedQuiver = ITEMS.register("envenomed_quiver", () -> new EnvenomedQuiverItem(new Item.Properties()));
    public static final RegistryObject<Item> MoltenQuiver = ITEMS.register("molten_quiver", () -> new MoltenQuiverItem(new Item.Properties()));
    public static final RegistryObject<Item> WoodenHeadgear = ITEMS.register("wooden_headgear", () -> new WoodenHeadgearItem(new Item.Properties()));
    public static final RegistryObject<Item> GoldenHeadgear = ITEMS.register("golden_headgear", () -> new GoldenHeadgearItem(new Item.Properties()));
    public static final RegistryObject<Item> NetheriteHeadgear = ITEMS.register("netherite_headgear", () -> new NetheriteHeadgearItem(new Item.Properties()));
    public static final RegistryObject<Item> EnderianEye = ITEMS.register("enderian_eye", () -> new EnderianEyeItem(new Item.Properties()));
    public static final RegistryObject<Item> EnderDragonClaw = ITEMS.register("ender_dragon_claw", () -> new EnderDragonClawItem(new Item.Properties()));
    public static final RegistryObject<Item> MechanicalClaw = ITEMS.register("mechanical_claw", () -> new MechanicalClawItem(new Item.Properties()));
    public static final RegistryObject<Item> EchoGlove = ITEMS.register("echo_glove", () -> new EchoGloveItem(new Item.Properties()));
    public static final RegistryObject<Item> VenomStone = ITEMS.register("venom_stone", () -> new VenomStoneItem(new Item.Properties()));
    public static final RegistryObject<Item> DecayStone = ITEMS.register("decay_stone", () -> new DecayStoneItem(new Item.Properties()));
    public static final RegistryObject<Item> FireStone = ITEMS.register("fire_stone", () -> new FireStoneItem(new Item.Properties()));
    public static final RegistryObject<Item> IceStone = ITEMS.register("ice_stone", () -> new IceStoneItem(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}