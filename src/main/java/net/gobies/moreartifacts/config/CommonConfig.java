package net.gobies.moreartifacts.config;

import net.gobies.moreartifacts.MoreArtifacts;
import net.gobies.moreartifacts.helper.SoulAttributes;
import net.gobies.moreartifacts.init.MAItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = MoreArtifacts.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonConfig {
    private static final String FILENAME = "moreartifacts-common.toml";

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    private static final Map<String, ForgeConfigSpec.BooleanValue> TOGGLE = new HashMap<>();

    /*
    // Artifacts
    */
    public static ForgeConfigSpec.ConfigValue<Double> SKULL_FIRE_DAMAGE_REDUCTION;

    public static ForgeConfigSpec.ConfigValue<Double> OBSIDIAN_SHIELD_FIRE_DAMAGE_REDUCTION;

    public static ForgeConfigSpec.ConfigValue<List<? extends String>> ANKH_CHARM_ADDITIONAL_EFFECTS;

    public static ForgeConfigSpec.ConfigValue<Double> ANKH_SHIELD_FIRE_DAMAGE_REDUCTION;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> ANKH_SHIELD_ADDITIONAL_EFFECTS;

    public static ForgeConfigSpec.ConfigValue<Double> DECAY_AMULET_WITHER_CHANCE;
    public static ForgeConfigSpec.ConfigValue<Integer> DECAY_AMULET_WITHER_DURATION;
    public static ForgeConfigSpec.ConfigValue<Integer> DECAY_AMULET_WITHER_LEVEL;
    public static ForgeConfigSpec.ConfigValue<Double> DECAY_AMULET_LUCK_FACTOR;

    public static ForgeConfigSpec.ConfigValue<Double> VENOM_AMULET_POISON_CHANCE;
    public static ForgeConfigSpec.ConfigValue<Integer> VENOM_AMULET_POISON_DURATION;
    public static ForgeConfigSpec.ConfigValue<Integer> VENOM_AMULET_POISON_LEVEL;
    public static ForgeConfigSpec.ConfigValue<Double> VENOM_AMULET_LUCK_FACTOR;

    public static ForgeConfigSpec.ConfigValue<Double> SPECTRE_AMULET_HEAL_CHANCE;
    public static ForgeConfigSpec.ConfigValue<Double> SPECTRE_AMULET_HEAL_AMOUNT;
    public static ForgeConfigSpec.ConfigValue<Double> SPECTRE_AMULET_LUCK_FACTOR;

    public static ForgeConfigSpec.ConfigValue<Double> NECROPLASM_AMULET_HEAL_CHANCE;
    public static ForgeConfigSpec.ConfigValue<Double> NECROPLASM_AMULET_HEAL_AMOUNT;
    public static ForgeConfigSpec.ConfigValue<Double> NECROPLASM_AMULET_POISON_CHANCE;
    public static ForgeConfigSpec.ConfigValue<Integer> NECROPLASM_AMULET_POISON_LEVEL;
    public static ForgeConfigSpec.ConfigValue<Integer> NECROPLASM_AMULET_POISON_DURATION;
    public static ForgeConfigSpec.ConfigValue<Double> NECROPLASM_AMULET_WITHER_CHANCE;
    public static ForgeConfigSpec.ConfigValue<Integer> NECROPLASM_AMULET_WITHER_LEVEL;
    public static ForgeConfigSpec.ConfigValue<Integer> NECROPLASM_AMULET_WITHER_DURATION;
    public static ForgeConfigSpec.ConfigValue<Double> NECROPLASM_AMULET_LUCK_FACTOR;

    public static ForgeConfigSpec.ConfigValue<Integer> MECHANICAL_GLOVE_DAMAGE;
    public static ForgeConfigSpec.ConfigValue<Double> MECHANICAL_GLOVE_SPEED;

    public static ForgeConfigSpec.ConfigValue<Integer> FROST_GAUNTLET_DAMAGE;
    public static ForgeConfigSpec.ConfigValue<Double> FROST_GAUNTLET_ATTACK_SPEED;
    public static ForgeConfigSpec.ConfigValue<Integer> FROSTED_WOUNDS_DAMAGE;
    public static ForgeConfigSpec.ConfigValue<Integer> FROSTED_WOUNDS_DURATION;
    public static ForgeConfigSpec.ConfigValue<Double> FROSTED_WOUNDS_DAMAGE_RECEIVED;

    public static ForgeConfigSpec.ConfigValue<Double> FROSTED_SHIELD_DAMAGE_REDUCTION;

    public static ForgeConfigSpec.ConfigValue<Integer> SHACKLE_ARMOR;

    public static ForgeConfigSpec.ConfigValue<Integer> IGNORE_DAMAGE_CHANCE;
    public static ForgeConfigSpec.ConfigValue<Double> EXPLOSION_DAMAGE_REDUCTION;
    public static ForgeConfigSpec.ConfigValue<Integer> HERO_SHIELD_RES_LEVEL;
    public static ForgeConfigSpec.ConfigValue<Boolean> HERO_SHIELD_ENHANCED_VISUALS_COMPAT;
    public static ForgeConfigSpec.ConfigValue<Boolean> HERO_SHIELD_FIRST_AID_COMPAT;
    public static ForgeConfigSpec.ConfigValue<Double> FIRST_AID_IGNORE_CHANCE;

    public static ForgeConfigSpec.ConfigValue<Integer> SHADOW_AEGIS_IGNORE_DAMAGE_CHANCE;
    public static ForgeConfigSpec.ConfigValue<Double> SHADOW_AEGIS_EXPLOSION_DAMAGE_REDUCTION;
    public static ForgeConfigSpec.ConfigValue<Integer> SHADOW_AEGIS_RES_LEVEL;
    public static ForgeConfigSpec.ConfigValue<Boolean> SHADOW_AEGIS_ENHANCED_VISUALS_COMPAT;
    public static ForgeConfigSpec.ConfigValue<Boolean> SHADOW_AEGIS_FIRST_AID_COMPAT;
    public static ForgeConfigSpec.ConfigValue<Double> SHADOW_AEGIS_FIRST_AID_IGNORE_CHANCE;

    public static ForgeConfigSpec.ConfigValue<Double> ENDERIAN_DAMAGE_REDUCTION;

    public static ForgeConfigSpec.ConfigValue<Double> TRUE_ENDERIAN_DAMAGE_REDUCTION;
    public static ForgeConfigSpec.ConfigValue<Double> TRUE_ENDERIAN_EVADE;
    public static ForgeConfigSpec.ConfigValue<Double> TRUE_ENDERIAN_REACH;
    public static ForgeConfigSpec.ConfigValue<Double> TRUE_ENDERIAN_LUCK_FACTOR;
    public static ForgeConfigSpec.ConfigValue<Boolean> TRUE_ENDERIAN_COMPAT;

    public static ForgeConfigSpec.ConfigValue<Double> GILDED_DAMAGE_REDUCTION;
    public static ForgeConfigSpec.ConfigValue<Double> GILDED_DAMAGE_DEALT;

    public static ForgeConfigSpec.ConfigValue<Double> PLUSHIE_HEALTH;
    public static ForgeConfigSpec.ConfigValue<Integer> PLUSHIE_HEALTH_BOOST_LEVEL;
    public static ForgeConfigSpec.ConfigValue<Integer> PLUSHIE_DURATION;
    public static ForgeConfigSpec.ConfigValue<Integer> PLUSHIE_REGEN_LEVEL;

    public static ForgeConfigSpec.ConfigValue<Double> EMERALD_RING_DAMAGE;
    public static ForgeConfigSpec.ConfigValue<Double> EMERALD_RING_EMERALDS;
    public static ForgeConfigSpec.ConfigValue<Double> EMERALD_RING_LUCK_FACTOR;
    public static ForgeConfigSpec.ConfigValue<Integer> EMERALD_RING_LUCK;

    public static ForgeConfigSpec.ConfigValue<Double> MAGIC_QUIVER_DAMAGE;
    public static ForgeConfigSpec.ConfigValue<Double> MAGIC_QUIVER_VELOCITY;
    public static ForgeConfigSpec.ConfigValue<Double> MAGIC_QUIVER_AMMO;
    public static ForgeConfigSpec.ConfigValue<Double> MAGIC_QUIVER_LUCK_FACTOR;

    public static ForgeConfigSpec.ConfigValue<Double> ENVENOMED_QUIVER_DAMAGE;
    public static ForgeConfigSpec.ConfigValue<Double> ENVENOMED_QUIVER_VELOCITY;
    public static ForgeConfigSpec.ConfigValue<Integer> ENVENOMED_QUIVER_POISON_LEVEL;
    public static ForgeConfigSpec.ConfigValue<Integer> ENVENOMED_QUIVER_POISON_DURATION;
    public static ForgeConfigSpec.ConfigValue<Integer> ENVENOMED_QUIVER_WITHER_LEVEL;
    public static ForgeConfigSpec.ConfigValue<Integer> ENVENOMED_QUIVER_WITHER_DURATION;
    public static ForgeConfigSpec.ConfigValue<Double> ENVENOMED_QUIVER_AMMO;
    public static ForgeConfigSpec.ConfigValue<Double> ENVENOMED_QUIVER_LUCK_FACTOR;

    public static ForgeConfigSpec.ConfigValue<Double> MOLTEN_QUIVER_DAMAGE;
    public static ForgeConfigSpec.ConfigValue<Double>  MOLTEN_QUIVER_VELOCITY;
    public static ForgeConfigSpec.ConfigValue<Double> MOLTEN_QUIVER_ONFIRE_DAMAGE;
    public static ForgeConfigSpec.ConfigValue<Integer> MOLTEN_QUIVER_DURATION;
    public static ForgeConfigSpec.ConfigValue<Double> MOLTEN_QUIVER_AMMO;
    public static ForgeConfigSpec.ConfigValue<Double> MOLTEN_QUIVER_LUCK_FACTOR;

    public static ForgeConfigSpec.ConfigValue<Double> FROZEN_QUIVER_DAMAGE;
    public static ForgeConfigSpec.ConfigValue<Double> FROZEN_QUIVER_VELOCITY;
    public static ForgeConfigSpec.ConfigValue<Double> FROZEN_QUIVER_AMMO;
    public static ForgeConfigSpec.ConfigValue<Integer> FROZEN_QUIVER_MULTIPLIER;
    public static ForgeConfigSpec.ConfigValue<Double> FROZEN_QUIVER_LUCK_FACTOR;

    public static ForgeConfigSpec.ConfigValue<Double> WOODEN_HEADGEAR_ARMOR;
    public static ForgeConfigSpec.ConfigValue<Double> WOODEN_HEADGEAR_ARROW_DAMAGE_REDUCTION;
    public static ForgeConfigSpec.ConfigValue<Boolean> WOODEN_HEADGEAR_FIRST_AID_COMPAT;
    public static ForgeConfigSpec.ConfigValue<Double> WOODEN_HEADGEAR_HEADSHOT_DAMAGE_REDUCTION;

    public static ForgeConfigSpec.ConfigValue<Double> GOLDEN_HEADGEAR_ARMOR;
    public static ForgeConfigSpec.ConfigValue<Double> GOLDEN_HEADGEAR_ARROW_DAMAGE_REDUCTION;
    public static ForgeConfigSpec.ConfigValue<Boolean> GOLDEN_HEADGEAR_FIRST_AID_COMPAT;
    public static ForgeConfigSpec.ConfigValue<Double> GOLDEN_HEADGEAR_HEADSHOT_DAMAGE_REDUCTION;

    public static ForgeConfigSpec.ConfigValue<Double> NETHERITE_HEADGEAR_ARMOR;
    public static ForgeConfigSpec.ConfigValue<Double> NETHERITE_HEADGEAR_ARROW_DAMAGE_REDUCTION;
    public static ForgeConfigSpec.ConfigValue<Boolean> NETHERITE_HEADGEAR_FIRST_AID_COMPAT;
    public static ForgeConfigSpec.ConfigValue<Double> NETHERITE_HEADGEAR_HEADSHOT_DAMAGE_REDUCTION;

    public static ForgeConfigSpec.ConfigValue<Double> ENDER_DRAGON_CLAW_DAMAGE;
    public static ForgeConfigSpec.ConfigValue<Double> ENDER_DRAGON_CLAW_CHANCE;
    public static ForgeConfigSpec.ConfigValue<Double> ENDER_DRAGON_CLAW_LUCK_FACTOR;

    public static ForgeConfigSpec.ConfigValue<Integer> ENDERIAN_EYE_RADIUS;
    public static ForgeConfigSpec.ConfigValue<Double> ENDERIAN_EYE_COOLDOWN;

    public static ForgeConfigSpec.ConfigValue<Double> MECHANICAL_CLAW_DAMAGE;
    public static ForgeConfigSpec.ConfigValue<Double> MECHANICAL_CLAW_SPEED;
    public static ForgeConfigSpec.ConfigValue<Double> MECHANICAL_CLAW_BLEED_CHANCE;
    public static ForgeConfigSpec.ConfigValue<Integer> MECHANICAL_CLAW_BLEED_DAMAGE;
    public static ForgeConfigSpec.ConfigValue<Integer> MECHANICAL_CLAW_BLEED_DURATION;
    public static ForgeConfigSpec.ConfigValue<Double> BLEED_DAMAGE_RECEIVED;
    public static ForgeConfigSpec.ConfigValue<Double> MECHANICAL_CLAW_LUCK_FACTOR;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> BLEED_BLACKLISTED_ENTITIES;

    public static ForgeConfigSpec.ConfigValue<Double> ECHO_GLOVE_DAMAGE;
    public static ForgeConfigSpec.ConfigValue<Double> ECHO_GLOVE_ATTACK_SPEED;
    public static ForgeConfigSpec.ConfigValue<Double> ECHO_GLOVE_IGNORE_CHANCE;
    public static ForgeConfigSpec.ConfigValue<Double> ECHO_GLOVE_LUCK_FACTOR;

    public static ForgeConfigSpec.ConfigValue<Double> VENOM_STONE_DAMAGE;
    public static ForgeConfigSpec.ConfigValue<Double> VENOM_STONE_CHANCE;
    public static ForgeConfigSpec.ConfigValue<Integer> VENOM_STONE_LEVEL;
    public static ForgeConfigSpec.ConfigValue<Integer> VENOM_STONE_DURATION;
    public static ForgeConfigSpec.ConfigValue<Double> VENOM_STONE_DEADLY_CHANCE;
    public static ForgeConfigSpec.ConfigValue<Double> VENOM_STONE_LUCK_FACTOR;

    public static ForgeConfigSpec.ConfigValue<Double> DECAY_STONE_DAMAGE;
    public static ForgeConfigSpec.ConfigValue<Double> DECAY_STONE_CHANCE;
    public static ForgeConfigSpec.ConfigValue<Integer> DECAY_STONE_LEVEL;
    public static ForgeConfigSpec.ConfigValue<Integer> DECAY_STONE_DURATION;
    public static ForgeConfigSpec.ConfigValue<Integer> DECAY_STONE_HEAL_AMOUNT;
    public static ForgeConfigSpec.ConfigValue<Double> DECAY_STONE_LUCK_FACTOR;

    public static ForgeConfigSpec.ConfigValue<Double> FIRE_STONE_DAMAGE;
    public static ForgeConfigSpec.ConfigValue<Double> FIRE_STONE_CHANCE;
    public static ForgeConfigSpec.ConfigValue<Integer> FIRE_STONE_DURATION;
    public static ForgeConfigSpec.ConfigValue<Double> FIRE_STONE_LUCK_FACTOR;

    public static ForgeConfigSpec.ConfigValue<Double> ICE_STONE_DAMAGE;
    public static ForgeConfigSpec.ConfigValue<Double> ICE_STONE_CHANCE;
    public static ForgeConfigSpec.ConfigValue<Integer> ICE_STONE_DURATION;
    public static ForgeConfigSpec.ConfigValue<Double> ICE_STONE_ENCASED_CHANCE;
    public static ForgeConfigSpec.ConfigValue<Integer> ICE_STONE_ENCASED_DURATION;
    public static ForgeConfigSpec.ConfigValue<Boolean> ICE_STONE_COMPAT;
    public static ForgeConfigSpec.ConfigValue<Double> ICE_STONE_LUCK_FACTOR;

    public static ForgeConfigSpec.ConfigValue<Integer> RECALL_POTION_USE_TIME;
    public static ForgeConfigSpec.ConfigValue<Integer> RECALL_POTION_COOLDOWN;
    public static ForgeConfigSpec.ConfigValue<Boolean> RECALL_POTION_INTERDIMENSIONAL;
    public static ForgeConfigSpec.ConfigValue<Boolean> RECALL_POTION_GLOW;
    public static ForgeConfigSpec.ConfigValue<String> RECALL_POTION_INGREDIENT;

    public static ForgeConfigSpec.ConfigValue<Integer> WORMHOLE_POTION_USE_TIME;
    public static ForgeConfigSpec.ConfigValue<Integer> WORMHOLE_POTION_COOLDOWN;
    public static ForgeConfigSpec.ConfigValue<Boolean> WORMHOLE_POTION_INTERDIMENSIONAL;
    public static ForgeConfigSpec.ConfigValue<Boolean> WORMHOLE_POTION_GLOW;
    public static ForgeConfigSpec.ConfigValue<String> WORMHOLE_POTION_INGREDIENT;

    public static ForgeConfigSpec.ConfigValue<Integer> GRAVE_SCROLL_USE_TIME;
    public static ForgeConfigSpec.ConfigValue<Integer> GRAVE_SCROLL_COOLDOWN;
    public static ForgeConfigSpec.ConfigValue<Boolean> GRAVE_SCROLL_GLOW;
    public static ForgeConfigSpec.ConfigValue<Boolean> GRAVE_SCROLL_INTERDIMENSIONAL;

    public static ForgeConfigSpec.ConfigValue<Double> VANIR_MASK_DAMAGE_INCREASE;
    public static ForgeConfigSpec.ConfigValue<Double> VANIR_MASK_HEALTH_INCREASE;
    public static ForgeConfigSpec.ConfigValue<Double>  VANIR_MASK_SPEED_INCREASE;
    public static ForgeConfigSpec.ConfigValue<Double> VANIR_MASK_ARMOR_INCREASE;
    public static ForgeConfigSpec.ConfigValue<Double> VANIR_MASK_ARMOR_TOUGHNESS_INCREASE;

    public static ForgeConfigSpec.ConfigValue<Double> RUBY_RING_DAMAGE_INCREASE;
    public static ForgeConfigSpec.ConfigValue<Double> RUBY_RING_HEALTH_THRESHOLD;
    public static ForgeConfigSpec.ConfigValue<Double> RUBY_RING_HEALTH_CAP;

    public static ForgeConfigSpec.ConfigValue<Boolean> TAINTED_MIRROR_PLAYER_DAMAGE;

    public static ForgeConfigSpec.ConfigValue<Double> NATURES_MANTLE_SPEED_INCREASE;

    public static ForgeConfigSpec.ConfigValue<Double> ENDER_DRAGON_EYE_SUMMON_CHANCE;
    public static ForgeConfigSpec.ConfigValue<Double> ENDER_DRAGON_EYE_FOLLOW_DISTANCE;
    public static ForgeConfigSpec.ConfigValue<Double> ENDER_DRAGON_EYE_LUCK_FACTOR;
    public static ForgeConfigSpec.ConfigValue<Boolean> ENDER_DRAGON_EYE_COMPAT;
    public static ForgeConfigSpec.ConfigValue<Double> ENDERMAN_HEALTH_MULTIPLIER;
    public static ForgeConfigSpec.ConfigValue<Double> ENDERMAN_DAMAGE_MULTIPLIER;

    public static ForgeConfigSpec.ConfigValue<Double> LEATHER_TREADS_SPEED;

    public static ForgeConfigSpec.ConfigValue<Double> DUNE_TREADS_SPEED;
    public static ForgeConfigSpec.ConfigValue<Double> DUNE_TREADS_SAND_SPEED;

    public static ForgeConfigSpec.ConfigValue<Double> HIGH_JUMPERS_SPEED;

    public static ForgeConfigSpec.ConfigValue<Double> MECHANICAL_GEARS_SPEED;
    public static ForgeConfigSpec.ConfigValue<Double> MECHANICAL_GEARS_DODGE_CHANCE;
    public static ForgeConfigSpec.ConfigValue<Double> MECHANICAL_GEARS_LUCK_FACTOR;

    public static ForgeConfigSpec.ConfigValue<Double> BLAZING_TREADS_SPEED;

    public static ForgeConfigSpec.ConfigValue<Double> ENDERIAN_TREADS_SPEED;
    public static ForgeConfigSpec.ConfigValue<Double> ENDERIAN_TREADS_COOLDOWN;

    public static ForgeConfigSpec.ConfigValue<Double> SCULK_TREADS_SPEED;

    public static ForgeConfigSpec.ConfigValue<List<? extends String>> CURSED_DOLL_BLACKLIST_EFFECTS;

    public static ForgeConfigSpec.ConfigValue<Double> CURSED_DOLL_CHANCE;
    public static ForgeConfigSpec.ConfigValue<Integer> CURSED_DOLL_MAX_AMPLIFIER;
    public static ForgeConfigSpec.ConfigValue<Integer> CURSED_DOLL_MAX_DURATION;
    public static ForgeConfigSpec.ConfigValue<Double> CURSED_DOLL_LUCK_FACTOR;

    public static ForgeConfigSpec.ConfigValue<Integer> GUARDIAN_THORN_NECKLACE_ARMOR;
    public static ForgeConfigSpec.ConfigValue<Integer> GUARDIAN_THORN_NECKLACE_THORNS;
    public static ForgeConfigSpec.ConfigValue<Integer> GUARDIAN_THORN_NECKLACE_ARMOR_PIERCE;


    public static ForgeConfigSpec.ConfigValue<Integer> HOLY_MANTLE_COOLDOWN;
    public static ForgeConfigSpec.ConfigValue<Double> HOLY_MANTLE_ABSORPTION_CHANCE;
    public static ForgeConfigSpec.ConfigValue<Integer> HOLY_MANTLE_MAX_HEARTS;
    public static ForgeConfigSpec.ConfigValue<Double> HOLY_MANTLE_LUCK_FACTOR;

    public static ForgeConfigSpec.ConfigValue<Double> STEALTH_MANUAL_SNEAK_SPEED;

    public static ForgeConfigSpec.ConfigValue<Double> STORM_FANG_SPEED_INCREASE;
    public static ForgeConfigSpec.ConfigValue<Integer> STORM_FANG_SHOCK_DAMAGE;
    public static ForgeConfigSpec.ConfigValue<Integer> STORM_FANG_SHOCK_RADIUS;
    public static ForgeConfigSpec.ConfigValue<Integer> STORM_FANG_MANA_COST;
    public static ForgeConfigSpec.ConfigValue<Double> STORM_FANG_COOLDOWN;

    public static ForgeConfigSpec.ConfigValue<Double> RED_BELT_DODGE_DISTANCE;

    public static ForgeConfigSpec.ConfigValue<Double> ROGUE_BELT_DODGE_DISTANCE;
    public static ForgeConfigSpec.ConfigValue<Double> ROGUE_BELT_SNEAK_SPEED;

    public static ForgeConfigSpec.ConfigValue<Double> BROKEN_HEART_DAMAGE_INCREASE;

    public static ForgeConfigSpec.ConfigValue<Boolean> PHANTOM_THREAD_HEAD;
    public static ForgeConfigSpec.ConfigValue<Boolean> PHANTOM_THREAD_CHEST;
    public static ForgeConfigSpec.ConfigValue<Boolean> PHANTOM_THREAD_LEGS;
    public static ForgeConfigSpec.ConfigValue<Boolean> PHANTOM_THREAD_FEET;
    public static ForgeConfigSpec.ConfigValue<Boolean> PHANTOM_THREAD_GLOW;

    /*
    // Loot
    */
    public static ForgeConfigSpec.ConfigValue<Double> BEZOAR_DROP_CHANCE;

    public static ForgeConfigSpec.ConfigValue<Double> VITAMINS_DROP_CHANCE;

    public static ForgeConfigSpec.ConfigValue<Double> FAST_CLOCK_DROP_CHANCE;

    public static ForgeConfigSpec.ConfigValue<Double> DESERT_CHARM_DROP_CHANCE;

    public static ForgeConfigSpec.ConfigValue<Double> WITHER_SHARD_DROP_CHANCE;

    public static ForgeConfigSpec.ConfigValue<Double> SHULKER_HEART_DROP_CHANCE;

    public static ForgeConfigSpec.ConfigValue<Double> ENDERIAN_SCARF_DROP_CHANCE;

    public static ForgeConfigSpec.ConfigValue<Double> SHACKLE_DROP_CHANCE;

    public static ForgeConfigSpec.ConfigValue<Double> MAGIC_QUIVER_SKELETON_DROP_CHANCE;
    public static ForgeConfigSpec.ConfigValue<Double> MAGIC_QUIVER_PILLAGER_DROP_CHANCE;
    public static ForgeConfigSpec.ConfigValue<Double> MAGIC_QUIVER_TRADE_CHANCE;

    public static ForgeConfigSpec.ConfigValue<Double> ENDER_DRAGON_CLAW_DROP_CHANCE;

    public static ForgeConfigSpec.ConfigValue<Double> ENDER_DRAGON_EYE_DROP_CHANCE;

    public static ForgeConfigSpec.ConfigValue<Double> TAINTED_MIRROR_DROP_CHANCE;

    public static ForgeConfigSpec.ConfigValue<Double> CURSED_DOLL_DROP_CHANCE;

    public static ForgeConfigSpec.ConfigValue<Double> RED_BELT_DROP_CHANCE;

    public static ForgeConfigSpec.ConfigValue<Double> SCULK_LENS_DROP_CHANCE;

    public static ForgeConfigSpec.ConfigValue<Double> RECALL_POTION_DROP_CHANCE;

    public static ForgeConfigSpec.ConfigValue<Double> WORMHOLE_POTION_DROP_CHANCE;

    public static ForgeConfigSpec.ConfigValue<Double> COBALT_SHIELD_DROP_CHANCE;

    public static ForgeConfigSpec.ConfigValue<Double> MELODY_PLUSHIE_DROP_CHANCE;

    public static ForgeConfigSpec.ConfigValue<Double> DUNE_TREADS_DROP_CHANCE;

    public static ForgeConfigSpec.ConfigValue<Double> RUBY_RING_DROP_CHANCE;

    public static ForgeConfigSpec.ConfigValue<Double> ICE_CRYSTAL_DROP_CHANCE;

    public static ForgeConfigSpec.ConfigValue<Double> NATURES_MANTLE_DROP_CHANCE;

    public static ForgeConfigSpec.ConfigValue<Double> BROKEN_HEART_DROP_CHANCE;

    public static ForgeConfigSpec.ConfigValue<Double> STORM_FANG_DROP_CHANCE;

    public static ForgeConfigSpec.ConfigValue<Double> SHADOW_DUST_DUNGEON_DROP_CHANCE;
    public static ForgeConfigSpec.ConfigValue<Double> SHADOW_DUST_ANCIENT_CITY_DROP_CHANCE;

    public static ForgeConfigSpec.ConfigValue<Double> HOLY_MANTLE_TRADE_CHANCE;

    public static ForgeConfigSpec.ConfigValue<Double> STEALTH_MANUAL_TRADE_CHANCE;

    /*
    // General
    */
    public static ForgeConfigSpec.ConfigValue<Double> MAX_DAMAGE_REDUCTION;
    public static ForgeConfigSpec.ConfigValue<Double> MAX_FIRE_DAMAGE_REDUCTION;
    public static ForgeConfigSpec.ConfigValue<Double> MAX_DAMAGE_INCREASE;
    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_DEBUG;
    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_ENDER_TWEAKS;
    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_LOOT_TABLES;
    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_LUCK_FACTOR;
    public static ForgeConfigSpec.ConfigValue<Double> DEFAULT_MAX_MANA;
    public static ForgeConfigSpec.ConfigValue<Double> DEFAULT_MANA_REGEN;

    // Souls
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> ENVY_ATTRIBUTES;
    public static ForgeConfigSpec.ConfigValue<String> ENVY_EFFECT;
    public static ForgeConfigSpec.ConfigValue<Double> ENVY_MAGIC_DAMAGE;
    public static ForgeConfigSpec.ConfigValue<Double> ENVY_MAGIC_DAMAGE_CONVERSION;
    public static ForgeConfigSpec.ConfigValue<Double> ENVY_MANA_SHIELD_DAMAGE_MULTIPLIER;
    public static ForgeConfigSpec.ConfigValue<Double> ENVY_FLIGHT_MANA_COST;
    public static ForgeConfigSpec.ConfigValue<Double> ENVY_FLIGHT_START_THRESHOLD;
    public static ForgeConfigSpec.ConfigValue<Double> ENVY_MANA_SHIELD_START_THRESHOLD;
    public static ForgeConfigSpec.ConfigValue<Double> ENVY_MANA_SHIELD_COST;
    public static ForgeConfigSpec.ConfigValue<Double> ENVY_MANA_SHIELD_DIMINISHING_FACTOR;
    public static ForgeConfigSpec.ConfigValue<Double> ENVY_MANA_SHIELD_DIMINISHING_THRESHOLD;
    public static ForgeConfigSpec.ConfigValue<Double> ENVY_MANA_SHIELD_ARMOR_MULTIPLIER;
    public static ForgeConfigSpec.ConfigValue<Double> ENVY_MANA_SHIELD_ARMOR_TOUGHNESS_MULTIPLIER;
    public static ForgeConfigSpec.ConfigValue<Boolean> MANA_SHIELD_SOUNDS;

    public static ForgeConfigSpec.ConfigValue<Boolean> IRONS_SPELLBOOKS_MANA_COMPAT;
    public static ForgeConfigSpec.ConfigValue<Double> ENVY_SPELLBOOKS_MANA_SHIELD_DAMAGE_MULTIPLIER;
    public static ForgeConfigSpec.ConfigValue<Double> ENVY_SPELLBOOKS_FLIGHT_TIME;
    public static ForgeConfigSpec.ConfigValue<Double> ENVY_SPELLBOOKS_FLIGHT_START_COST;
    public static ForgeConfigSpec.ConfigValue<Double> ENVY_SPELLBOOKS_FLIGHT_MANA_OFFSET;
    public static ForgeConfigSpec.ConfigValue<Double> ENVY_SPELLBOOKS_MANA_SHIELD_START_THRESHOLD;
    public static ForgeConfigSpec.ConfigValue<Double> ENVY_SPELLBOOKS_MANA_SHIELD_COST;
    public static ForgeConfigSpec.ConfigValue<Double> ENVY_SPELLBOOKS_MANA_SHIELD_ARMOR_MULTIPLIER;
    public static ForgeConfigSpec.ConfigValue<Double> ENVY_SPELLBOOKS_MANA_SHIELD_ARMOR_TOUGHNESS_MULTIPLIER;
    public static ForgeConfigSpec.ConfigValue<Double> ENVY_SPELLBOOKS_MANA_SHIELD_MAX_MANA_SCALE;
    public static ForgeConfigSpec.ConfigValue<Double> ENVY_SPELLBOOKS_SPELL_COST_REDUCTION;
    public static ForgeConfigSpec.ConfigValue<Double> ENVY_FIRST_AID_MANA_SHIELD_DAMAGE_MULTIPLIER;

    public static ForgeConfigSpec.ConfigValue<Double> ENVY_EVOLVE_DAMAGE_1;
    public static ForgeConfigSpec.ConfigValue<Double> ENVY_EVOLVE_DAMAGE_2;
    public static ForgeConfigSpec.ConfigValue<Double> ENVY_EVOLVE_DAMAGE_3;
    public static ForgeConfigSpec.ConfigValue<Double> ENVY_EVOLVE_DAMAGE_4;


    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> WRATH_ATTRIBUTES;
    public static ForgeConfigSpec.ConfigValue<String> WRATH_EFFECT;
    public static ForgeConfigSpec.ConfigValue<Double> WRATH_STAGE_4_SINFUL_CHANCE;
    public static ForgeConfigSpec.ConfigValue<Integer> WRATH_STAGE_4_SINFUL_DURATION;
    public static ForgeConfigSpec.ConfigValue<Integer> WRATH_STAGE_4_SINFUL_MAX_LEVEL;
    public static ForgeConfigSpec.ConfigValue<Double> WRATH_SINFUL_CHANCE;
    public static ForgeConfigSpec.ConfigValue<Integer> WRATH_SINFUL_DURATION;
    public static ForgeConfigSpec.ConfigValue<Integer> WRATH_SINFUL_MAX_LEVEL;
    public static ForgeConfigSpec.ConfigValue<Double> WRATH_ANNIHILATION_DAMAGE_MULTIPLIER;
    public static ForgeConfigSpec.ConfigValue<Integer> WRATH_ANNIHILATION_MAX_STACKS;

    public static ForgeConfigSpec.ConfigValue<Double> WRATH_EVOLVE_DAMAGE_1;
    public static ForgeConfigSpec.ConfigValue<Double> WRATH_EVOLVE_DAMAGE_2;
    public static ForgeConfigSpec.ConfigValue<Double> WRATH_EVOLVE_DAMAGE_3;
    public static ForgeConfigSpec.ConfigValue<Double> WRATH_EVOLVE_DAMAGE_4;

    @SubscribeEvent
    static void onLoad(ModConfigEvent.Loading configEvent) {
        if (configEvent.getConfig().getFileName().equals(FILENAME)) {
            SoulAttributes.bakeConfig();
        }
    }

    public static boolean isItemDisabled(Item item) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(item);
        if (id == null) return false;

        ForgeConfigSpec.BooleanValue config = TOGGLE.get(id.toString());
        return config != null && !config.get();
    }

    static {
        /*
        // General Category
        */
        BUILDER.comment("General settings").push("General");
        MAX_DAMAGE_REDUCTION = BUILDER.comment("Global max damage reduction from artifacts in percentage").defineInRange("Max_Damage_Reduction", 1.0, 0.01, 1.0);
        MAX_FIRE_DAMAGE_REDUCTION = BUILDER.comment("Global max fire damage reduction from artifacts in percentage").defineInRange("Max_Fire_Damage_Reduction", 1.0, 0.01, 1.0);
        MAX_DAMAGE_INCREASE = BUILDER.comment("Global max damage increase from artifacts in percentage").defineInRange("Max_Damage_Increase", 2.0, 0.01, 1000.0);
        ENABLE_DEBUG = BUILDER.comment("Enable debugging").define("Debug", false);
        ENABLE_ENDER_TWEAKS = BUILDER.comment("Stop the endermen from being able to target or be hurt by the ender dragon/dragon breath").define("Ender_Tweaks", true);
        ENABLE_LOOT_TABLES = BUILDER.comment("Enables loot tables from this mod").define("Enable_Loot_Tables", true);
        ENABLE_LUCK_FACTOR = BUILDER.comment("Enables luck affecting artifact chanced based effects").define("Enable_Luck_Factor", true);
        BUILDER.comment("Mana Settings").push("Mana");
        DEFAULT_MAX_MANA = BUILDER.comment("Default maximum mana the player starts with").define("Default_Max_Mana", 100.0);
        DEFAULT_MANA_REGEN = BUILDER.comment("Amount of mana that regenerates every second").define("Default_Mana_Regen", 2.0);
        IRONS_SPELLBOOKS_MANA_COMPAT = BUILDER.comment("Enable using irons spellbooks mana system instead of this mods mana system").define("Spellbooks_Mana_System", true);
        BUILDER.pop();

        BUILDER.pop();

        /*
        // Artifact Toggles
        */
        BUILDER.comment("Toggle for which artifacts or items are enabled/disabled").push("Toggles");
        for (RegistryObject<Item> artifact : MAItems.getAllArtifacts()) {
            String itemID = Objects.requireNonNull(artifact.getId()).toString();
            String itemPath = artifact.getId().getPath();

            String displayName = Arrays.stream(itemPath.split("_")).map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1)).collect(Collectors.joining(" "));
            String configKey = Arrays.stream(itemPath.split("_")).map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1)).collect(Collectors.joining("_"));

            ForgeConfigSpec.BooleanValue configValue = BUILDER.comment("Enable " + displayName).define(configKey, true);
            TOGGLE.put(itemID, configValue);
        }
        BUILDER.pop();

        /*
        // Artifact Category
        */
        BUILDER.comment("Global artifact settings for all of their stats and abilities").push("Artifacts");

        BUILDER.push("Ankh_Charm");
        ANKH_CHARM_ADDITIONAL_EFFECTS = BUILDER.comment("List of additional effects the ankh charm grants immunity to (e.g., minecraft:glowing, minecraft:slow_falling etc)").defineList("Additional_Effects", List.of("moreartifacts:virulent"), s -> s instanceof String);
        BUILDER.pop();

        BUILDER.push("Ankh_Shield");
        ANKH_SHIELD_FIRE_DAMAGE_REDUCTION = BUILDER.comment("Fire damage reduction in percentage").define("Fire_Damage_Reduction", 0.50);
        ANKH_SHIELD_ADDITIONAL_EFFECTS = BUILDER.comment("List of additional effects the ankh shield grants immunity to (e.g., minecraft:glowing, minecraft:slow_falling etc)").defineList("Additional_Effects", List.of("moreartifacts:virulent"), s -> s instanceof String);
        BUILDER.pop();

        BUILDER.push("Blazing_Treads");
        BLAZING_TREADS_SPEED = BUILDER.comment("Speed increase gained in percentage").define("Speed_Increase", 0.10);
        BUILDER.pop();

        BUILDER.push("Broken_Heart");
        BROKEN_HEART_DAMAGE_INCREASE = BUILDER.comment("Damage increase for each broken heart the player has").define("Damage_Increase", 1.025);
        BUILDER.pop();

        BUILDER.push("Cursed_Doll");
        CURSED_DOLL_BLACKLIST_EFFECTS = BUILDER.comment("List of additional effects to blacklist being transferred  (e.g., minecraft:poison, minecraft:wither etc)").defineList("Blacklisted_Effects", List.of(), s -> s instanceof String);
        CURSED_DOLL_CHANCE = BUILDER.comment("Curse enemy chance in percentage").define("Chance", 0.07);
        CURSED_DOLL_MAX_AMPLIFIER = BUILDER.comment("Max level of an effect that can be transferred").define("Amplifier", 2);
        CURSED_DOLL_MAX_DURATION = BUILDER.comment("Max duration of an effect that can be transferred in seconds").define("Duration", 60);
        CURSED_DOLL_LUCK_FACTOR = BUILDER.comment("Percentage chance increase added to the base chance per luck attribute").defineInRange("Luck_Factor", 0.01, 0.0, 1.0);
        BUILDER.pop();

        BUILDER.push("Decay_Amulet");
        DECAY_AMULET_WITHER_CHANCE = BUILDER.comment("Chance to apply wither").define("Wither_Chance", 0.40);
        DECAY_AMULET_WITHER_LEVEL = BUILDER.comment("Level of wither inflicted").defineInRange("Wither_Level", 1, 1, 5);
        DECAY_AMULET_WITHER_DURATION = BUILDER.comment("Duration of the wither effect in seconds").define("Duration", 5);
        DECAY_AMULET_LUCK_FACTOR = BUILDER.comment("Percentage chance increase added to the base chance per luck attribute").defineInRange("Luck_Factor", 0.05, 0.0, 1.0);
        BUILDER.pop();

        BUILDER.push("Decay_Stone");
        DECAY_STONE_DAMAGE = BUILDER.comment("Increased damage dealt in percentage").define("Damage_Dealt", 1.20);
        DECAY_STONE_CHANCE = BUILDER.comment("Chance to inflict wither").define("Chance", 0.25);
        DECAY_STONE_LEVEL = BUILDER.comment("Level of the wither effect").defineInRange("Wither_Level", 3, 1, 5);
        DECAY_STONE_DURATION = BUILDER.comment("Duration of the wither effect in seconds").define("Duration", 3);
        DECAY_STONE_HEAL_AMOUNT = BUILDER.comment("Amount of hearts healed by the decay stone").define("Hearts", 1);
        DECAY_STONE_LUCK_FACTOR = BUILDER.comment("Percentage chance increase added to the base chance per luck attribute").defineInRange("Luck_Factor", 0.025,0.0, 1.0);
        BUILDER.pop();

        BUILDER.push("Dune_Treads");
        DUNE_TREADS_SPEED = BUILDER.comment("Speed increase gained in percentage").define("Speed_Increase", 0.05);
        DUNE_TREADS_SAND_SPEED = BUILDER.comment("Speed increase when on sand gained in percentage").define("Sand_Speed_Increase", 0.10);
        BUILDER.pop();

        BUILDER.push("Echo_Glove");
        ECHO_GLOVE_DAMAGE = BUILDER.comment("Damage increased in percentage").define("Damage_Increase", 0.05);
        ECHO_GLOVE_ATTACK_SPEED = BUILDER.comment("Attack speed increased in percentage").define("Attack_Speed_Increase", 0.25);
        ECHO_GLOVE_IGNORE_CHANCE = BUILDER.comment("Chance to ignore invulnerability frames on hit").define("Ignore_Chance", 0.10);
        ECHO_GLOVE_LUCK_FACTOR = BUILDER.comment("Percentage chance increase added to the base chance per luck attribute").defineInRange("Luck_Factor", 0.02, 0.0, 1.0);
        BUILDER.pop();

        BUILDER.push("Ender_Dragon_Claw");
        ENDER_DRAGON_CLAW_DAMAGE = BUILDER.comment("Increased damage dealt in percentage").define("Damage_Dealt", 1.50);
        ENDER_DRAGON_CLAW_CHANCE = BUILDER.comment("Chance to increase damage dealt").define("Chance", 0.10);
        ENDER_DRAGON_CLAW_LUCK_FACTOR = BUILDER.comment("Percentage chance increase added to the base chance per luck attribute").defineInRange("Luck_Factor", 0.01, 0.0, 1.0);
        BUILDER.pop();

        BUILDER.push("Ender_Dragon_Eye");
        ENDER_DRAGON_EYE_SUMMON_CHANCE = BUILDER.comment("Chance to summon endermen to fight for you when hit in percentage").define("Summon_Chance", 0.05);
        ENDER_DRAGON_EYE_FOLLOW_DISTANCE = BUILDER.comment("Distance at which endermen can follow you from in blocks").define("Follow_Distance", 20.0);
        ENDER_DRAGON_EYE_LUCK_FACTOR = BUILDER.comment("Percentage chance increase added to the base chance per luck attribute").defineInRange("Luck_Factor", 0.01, 0.0, 1.0);
        ENDER_DRAGON_EYE_COMPAT = BUILDER.comment("Enable compat to disable endermen static from enhanced visuals").define("EnhancedVisuals_Compat", true);
        ENDERMAN_HEALTH_MULTIPLIER = BUILDER.comment("Health multiplier of summoned enderman").define("Enderman_Health_Multiplier", 1.5);
        ENDERMAN_DAMAGE_MULTIPLIER = BUILDER.comment("Damage multiplier of summoned enderman").define("Enderman_Damage_Multiplier", 1.5);
        BUILDER.pop();

        BUILDER.push("Enderian_Eye");
        ENDERIAN_EYE_RADIUS = BUILDER.comment("Max teleportation radius").define("Radius", 25);
        ENDERIAN_EYE_COOLDOWN = BUILDER.comment("Cooldown for teleporting").define("Cooldown", 5.0);
        BUILDER.pop();

        BUILDER.push("Enderian_Scarf");
        ENDERIAN_DAMAGE_REDUCTION = BUILDER.comment("Damage reduction in percentage").define("Damage_Reduction", 0.08);
        BUILDER.pop();

        BUILDER.push("Enderian_Treads");
        ENDERIAN_TREADS_SPEED = BUILDER.comment("Speed increase gained in percentage").define("Speed_Increase", 0.20);
        ENDERIAN_TREADS_COOLDOWN = BUILDER.comment("Cooldown for blinking in seconds").define("Cooldown", 3.0);
        BUILDER.pop();

        BUILDER.push("Envenomed_Quiver");
        ENVENOMED_QUIVER_DAMAGE = BUILDER.comment("Increased arrow damage dealt in percentage").define("Arrow_Damage", 1.15);
        ENVENOMED_QUIVER_VELOCITY = BUILDER.comment("Increased arrow velocity in percentage").define("Arrow_Velocity", 0.10);
        ENVENOMED_QUIVER_POISON_LEVEL = BUILDER.comment("Level of poison inflicted").defineInRange("Poison_Level", 1, 1, 5);
        ENVENOMED_QUIVER_POISON_DURATION = BUILDER.comment("Duration of the poison effect in seconds").define("Poison_Duration", 5);
        ENVENOMED_QUIVER_WITHER_LEVEL = BUILDER.comment("Level of poison inflicted").defineInRange("Wither_Level", 1, 1, 5);
        ENVENOMED_QUIVER_WITHER_DURATION = BUILDER.comment("Duration of the wither effect in seconds").define("Wither_Duration", 5);
        ENVENOMED_QUIVER_AMMO = BUILDER.comment("Chance to not consume arrows in percentage").define("Save_Arrow_Chance", 0.20);
        ENVENOMED_QUIVER_LUCK_FACTOR = BUILDER.comment("Percentage chance increase added to the base chance per luck attribute").defineInRange("Luck_Factor", 0.02, 0, 1.0);
        BUILDER.pop();

        BUILDER.push("Fire_Stone");
        FIRE_STONE_DAMAGE = BUILDER.comment("Increased damage dealt in percentage").define("Damage_Dealt", 1.30);
        FIRE_STONE_CHANCE = BUILDER.comment("Chance to set enemies ablaze").define("Chance", 0.25);
        FIRE_STONE_DURATION = BUILDER.comment("Duration of enemies on fire in seconds").define("Duration", 3);
        FIRE_STONE_LUCK_FACTOR = BUILDER.comment("Percentage chance increase added to the base chance per luck attribute").defineInRange("Luck_Factor", 0.025,0.0, 1.0);
        BUILDER.pop();

        BUILDER.push("Frost_Gauntlet");
        FROST_GAUNTLET_DAMAGE = BUILDER.comment("Attack Damage increase").define("Attack_Damage", 2);
        FROST_GAUNTLET_ATTACK_SPEED = BUILDER.comment("Attack Speed increase").define("Attack_Speed", 0.0);
        FROSTED_WOUNDS_DAMAGE = BUILDER.comment("Damage that frosted wounds deals per second").define("Frosted_Wounds_Damage", 2);
        FROSTED_WOUNDS_DURATION = BUILDER.comment("Duration of frosted wounds in seconds").define("Frosted_Wounds_Duration", 10);
        FROSTED_WOUNDS_DAMAGE_RECEIVED = BUILDER.comment("Increased damage dealt to targets inflicted with frosted wounds in percentage").define("Damage_Received", 0.25);
        BUILDER.pop();

        BUILDER.push("Frosted_Shield");
        FROSTED_SHIELD_DAMAGE_REDUCTION = BUILDER.comment("Damage reduction given when below 50% of max health in percentage").define("Damage_Reduction", 0.25);
        BUILDER.pop();

        BUILDER.push("Frozen_Quiver");
        FROZEN_QUIVER_DAMAGE = BUILDER.comment("Increased arrow damage dealt in percentage").define("Arrow_Damage", 1.05);
        FROZEN_QUIVER_VELOCITY = BUILDER.comment("Increased arrow velocity in percentage").define("Arrow_Velocity", 0.10);
        FROZEN_QUIVER_AMMO = BUILDER.comment("Chance to not consume arrows in percentage").define("Save_Arrow_Chance", 0.40);
        FROZEN_QUIVER_MULTIPLIER = BUILDER.comment("The number that gets multiplied with damage dealt").define("Multiplier", 10);
        FROZEN_QUIVER_LUCK_FACTOR = BUILDER.comment("Percentage chance increase added to the base chance per luck attribute").defineInRange("Luck_Factor", 0.02, 0, 1.0);
        BUILDER.pop();

        BUILDER.push("Gilded_Scarf");
        GILDED_DAMAGE_REDUCTION = BUILDER.comment("Damage reduction in percentage").define("Damage_Reduction", 0.12);
        GILDED_DAMAGE_DEALT = BUILDER.comment("Increased damage dealt in percentage").define("Damage_Dealt", 1.10);
        BUILDER.pop();

        BUILDER.push("Golden_Headgear");
        GOLDEN_HEADGEAR_ARMOR = BUILDER.comment("Armor increase").define("Armor", 2.0);
        GOLDEN_HEADGEAR_ARROW_DAMAGE_REDUCTION = BUILDER.comment("Arrow damage reduction in percentage").define("Damage_Reduction", 0.10);
        GOLDEN_HEADGEAR_FIRST_AID_COMPAT = BUILDER.comment("Enable compat to enable headshot damage reduction").define("FirstAid_Compat", true);
        GOLDEN_HEADGEAR_HEADSHOT_DAMAGE_REDUCTION = BUILDER.comment("Headshot damage reduction in percentage").define("Headshot_Damage_Reduction", 0.08);
        BUILDER.pop();

        BUILDER.push("Grave_Scroll");
        GRAVE_SCROLL_USE_TIME = BUILDER.comment("Grave scroll use time in ticks").define("Use_Time", 64);
        GRAVE_SCROLL_COOLDOWN = BUILDER.comment("Grave scroll cooldown in seconds").define("Cooldown", 0);
        GRAVE_SCROLL_GLOW = BUILDER.comment("Does grave scroll have enchantment glow").define("Glow", false);
        GRAVE_SCROLL_INTERDIMENSIONAL = BUILDER.comment("Can grave scrolls work from other dimensions").define("Interdimensional", true);
        BUILDER.pop();

        BUILDER.push("Guardian_Thorn_Necklace");
        GUARDIAN_THORN_NECKLACE_ARMOR = BUILDER.comment("Armor increase").define("Armor", 1);
        GUARDIAN_THORN_NECKLACE_ARMOR_PIERCE = BUILDER.comment("Amount of armor points that are ignored from armor piercing").define("Armor_Piercing", 5);
        GUARDIAN_THORN_NECKLACE_THORNS = BUILDER.comment("Thorn damage dealt when hit").define("Thorns_Damage", 2);
        BUILDER.pop();

        BUILDER.push("Hero_Shield");
        IGNORE_DAMAGE_CHANCE = BUILDER.comment("Amount of hits taken until damage is ignored").define("Hits", 5);
        EXPLOSION_DAMAGE_REDUCTION = BUILDER.comment("Explosion damage reduction in percentage").define("Explosion_Damage_Reduction", 0.75);
        HERO_SHIELD_RES_LEVEL = BUILDER.comment("Level of resistance hero shield provides").define("Level", 1);
        HERO_SHIELD_ENHANCED_VISUALS_COMPAT = BUILDER.comment("Enable compat to disable explosion overlay from enhanced visuals").define("EnhancedVisuals_Compat", true);
        HERO_SHIELD_FIRST_AID_COMPAT = BUILDER.comment("Enable compat to add a chance to ignore headshots from first aid").define("FirstAid_Compat", true);
        FIRST_AID_IGNORE_CHANCE = BUILDER.comment("Chance to ignore headshots in percentage").define("Ignore_Chance", 0.10);
        BUILDER.pop();

        BUILDER.push("High_Jumpers");
        HIGH_JUMPERS_SPEED = BUILDER.comment("Speed increase gained in percentage").define("Speed_Increase", 0.10);
        BUILDER.pop();

        BUILDER.push("Holy_Mantle");
        HOLY_MANTLE_COOLDOWN = BUILDER.comment("Cooldown of holy mantle damage shield in seconds").define("Cooldown", 8);
        HOLY_MANTLE_ABSORPTION_CHANCE = BUILDER.comment("Chance  to gain absorption hearts upon blocking damage in percentage").define("Absorption_Chance", 0.25);
        HOLY_MANTLE_MAX_HEARTS = BUILDER.comment("Maximum absorption hearts able to be obtained by holy mantle").define("Max_Hearts", 3);
        HOLY_MANTLE_LUCK_FACTOR = BUILDER.comment("Percentage chance increase added to the base chance per luck attribute").define("Luck_Factor", 0.05);
        BUILDER.pop();

        BUILDER.push("Ice_Stone");
        ICE_STONE_DAMAGE = BUILDER.comment("Increased damage dealt in percentage").define("Damage_Dealt", 1.10);
        ICE_STONE_CHANCE = BUILDER.comment("Chance to freeze enemies").define("Chance", 0.25);
        ICE_STONE_DURATION = BUILDER.comment("Duration of enemies frozen in seconds").define("Duration", 8);
        ICE_STONE_COMPAT= BUILDER.comment("Enable compat to use ice and fire's freezing mechanic").define("IceandFire_Compat", true);
        ICE_STONE_ENCASED_CHANCE= BUILDER.comment("Chance to encase enemies in ice (iceandfire)").define("Encase_Chance", 0.10);
        ICE_STONE_ENCASED_DURATION = BUILDER.comment("Duration of how long enemies stay encased in ice for (iceandfire)").define("Encased_Duration", 5);
        ICE_STONE_LUCK_FACTOR = BUILDER.comment("Percentage chance increase added to the base chance per luck attribute").defineInRange("Luck_Factor", 0.025,0.0, 1.0);
        BUILDER.pop();

        BUILDER.push("Leather_Treads");
        LEATHER_TREADS_SPEED = BUILDER.comment("Speed increase gained in percentage").define("Speed_Increase", 0.05);
        BUILDER.pop();

        BUILDER.push("Lucky_Emerald_Ring");
        EMERALD_RING_DAMAGE = BUILDER.comment("Increased damage dealt against illagers in percentage").define("Damage_Dealt", 1.25);
        EMERALD_RING_EMERALDS = BUILDER.comment("Chance for on hit to drop an emerald").define("Emerald_Chance", 0.05);
        EMERALD_RING_LUCK_FACTOR = BUILDER.comment("Percentage chance increase added to the base chance per luck attribute").defineInRange("Luck_Factor", 0.01, 0.0, 1.0);
        EMERALD_RING_LUCK = BUILDER.comment("Amount of increased luck").define("Luck_Increase", 1);
        BUILDER.pop();

        BUILDER.push("Magic_Quiver");
        MAGIC_QUIVER_DAMAGE = BUILDER.comment("Increased arrow damage dealt in percentage").define("Arrow_Damage", 1.10);
        MAGIC_QUIVER_VELOCITY = BUILDER.comment("Increased arrow velocity in percentage").define("Arrow_Velocity", 0.10);
        MAGIC_QUIVER_AMMO = BUILDER.comment("Chance to not consume arrows in percentage").define("Save_Arrow_Chance", 0.20);
        MAGIC_QUIVER_LUCK_FACTOR = BUILDER.comment("Percentage chance increase added to the base chance per luck attribute").defineInRange("Luck_Factor", 0.02, 0, 1.0);
        BUILDER.pop();

        BUILDER.push("Mechanical_Claw");
        MECHANICAL_CLAW_DAMAGE = BUILDER.comment("Damage increased in percentage").define("Damage_Increase", 0.15);
        MECHANICAL_CLAW_SPEED = BUILDER.comment("Speed increased in percentage").define("Speed_Increase", 0.10);
        MECHANICAL_CLAW_BLEED_CHANCE = BUILDER.comment("Chance to inflict bleed onto hit enemies").define("Bleed_Chance", 0.25);
        MECHANICAL_CLAW_BLEED_DAMAGE = BUILDER.comment("Damage that bleed deals per second").define("Bleed_Damage", 1);
        MECHANICAL_CLAW_BLEED_DURATION = BUILDER.comment("Duration of bleed in seconds").define("Bleed_Duration", 5);
        BLEED_DAMAGE_RECEIVED = BUILDER.comment("Increased damage dealt to targets inflicted with bleed in percentage").define("Damage_Received",  0.1);
        MECHANICAL_CLAW_LUCK_FACTOR = BUILDER.comment("Percentage chance increase added to the base chance per luck attribute").defineInRange("Luck_Factor", 0.05, 0.0, 1.0);
        BLEED_BLACKLISTED_ENTITIES = BUILDER.comment("Blacklist for entities that cannot be affected by bleed").defineList("Blacklisted_Entities",List.of(), s -> s instanceof String);
        BUILDER.pop();

        BUILDER.push("Mechanical_Gears");
        MECHANICAL_GEARS_SPEED = BUILDER.comment("Speed increase gained in percentage").define("Speed_Increase", 0.02);
        MECHANICAL_GEARS_DODGE_CHANCE = BUILDER.comment("Chance to dodge attacks in percentage").define("Dodge_Chance", 0.05);
        MECHANICAL_GEARS_LUCK_FACTOR = BUILDER.comment("Percentage chance increase added to the base chance per luck attribute").defineInRange("Luck_Factor", 0.01, 0.0, 1.0);
        BUILDER.pop();

        BUILDER.push("Mechanical_Glove");
        MECHANICAL_GLOVE_DAMAGE = BUILDER.comment("Attack Damage increase").define("Attack_Damage", 1);
        MECHANICAL_GLOVE_SPEED = BUILDER.comment("Attack Speed increase in percentage").define("Attack_Speed", 0.1);
        BUILDER.pop();

        BUILDER.push("Melody_Plushie");
        PLUSHIE_HEALTH = BUILDER.comment("Max Health Increase percentage").define("Max_Health", 0.2);
        PLUSHIE_HEALTH_BOOST_LEVEL = BUILDER.comment("Level of health boost from waking up").define("Health_Boost_Level", 2);
        PLUSHIE_DURATION = BUILDER.comment("Duration of health boost in seconds").define("Duration", 240);
        PLUSHIE_REGEN_LEVEL = BUILDER.comment("Level regeneration melody plushie provides").define("Regen_Level", 1);
        BUILDER.pop();

        BUILDER.push("Molten_Quiver");
        MOLTEN_QUIVER_DAMAGE = BUILDER.comment("Increased arrow damage dealt in percentage").define("Arrow_Damage", 1.15);
        MOLTEN_QUIVER_VELOCITY = BUILDER.comment("Increased arrow velocity in percentage").define("Arrow_Velocity", 0.10);
        MOLTEN_QUIVER_ONFIRE_DAMAGE = BUILDER.comment("Increased on fire bonus arrow damage in percentage").define("On_Fire_Bonus", 1.05);
        MOLTEN_QUIVER_DURATION = BUILDER.comment("Duration of enemies on fire in seconds").define("Fire_Duration", 5);
        MOLTEN_QUIVER_AMMO = BUILDER.comment("Chance to not consume arrows in percentage").define("Save_Arrow_Chance", 0.20);
        MOLTEN_QUIVER_LUCK_FACTOR = BUILDER.comment("Percentage chance increase added to the base chance per luck attribute").defineInRange("Luck_Factor", 0.02, 0, 1.0);
        BUILDER.pop();

        BUILDER.push("Natures_Mantle");
        NATURES_MANTLE_SPEED_INCREASE = BUILDER.comment("Increased speed gained in percentage").define("Speed_Increase", 0.10);
        BUILDER.pop();

        BUILDER.push("Necroplasm_Amulet");
        NECROPLASM_AMULET_HEAL_CHANCE = BUILDER.comment("Chance to heal").define("Heal_Chance", 0.50);
        NECROPLASM_AMULET_HEAL_AMOUNT = BUILDER.comment("Amount of health gained from attacking").define("Heal_Amount", 2.0);
        NECROPLASM_AMULET_POISON_CHANCE = BUILDER.comment("Chance to apply poison").define("Poison_Chance", 0.60);
        NECROPLASM_AMULET_POISON_LEVEL = BUILDER.comment("Level of poison inflicted").defineInRange("Poison_Level", 1, 1, 5 );
        NECROPLASM_AMULET_POISON_DURATION = BUILDER.comment("Duration of the poison effect in seconds").define("Poison_Duration", 5);
        NECROPLASM_AMULET_WITHER_CHANCE = BUILDER.comment("Chance to apply wither").define("Wither_Chance", 0.60);
        NECROPLASM_AMULET_WITHER_LEVEL = BUILDER.comment("Level of wither inflicted").defineInRange("Wither_Level", 1, 1, 5);
        NECROPLASM_AMULET_WITHER_DURATION = BUILDER.comment("Duration of the wither effect in seconds").define("Wither_Duration", 5);
        NECROPLASM_AMULET_LUCK_FACTOR = BUILDER.comment("Percentage chance increase added to the base chance per luck attribute").defineInRange("Luck_Factor", 0.05, 0.0, 1.0);
        BUILDER.pop();

        BUILDER.push("Netherite_Headgear");
        NETHERITE_HEADGEAR_ARMOR = BUILDER.comment("Armor increase").define("Armor", 3.0);
        NETHERITE_HEADGEAR_ARROW_DAMAGE_REDUCTION = BUILDER.comment("Arrow damage reduction in percentage").define("Damage_Reduction", 0.15);
        NETHERITE_HEADGEAR_FIRST_AID_COMPAT = BUILDER.comment("Enable compat to enable headshot damage reduction").define("FirstAid_Compat", true);
        NETHERITE_HEADGEAR_HEADSHOT_DAMAGE_REDUCTION = BUILDER.comment("Headshot damage reduction in percentage").define("Headshot_Damage_Reduction", 0.12);
        BUILDER.pop();

        BUILDER.push("Obsidian_Shield");
        OBSIDIAN_SHIELD_FIRE_DAMAGE_REDUCTION = BUILDER.comment("Fire damage reduction in percentage").define("Fire_Damage_Reduction", 0.50);
        BUILDER.pop();

        BUILDER.push("Obsidian_Skull");
        SKULL_FIRE_DAMAGE_REDUCTION = BUILDER.comment("Fire damage reduction in percentage").define("Fire_Damage_Reduction", 0.50);
        BUILDER.pop();

        BUILDER.push("Phantom_Thread");
        PHANTOM_THREAD_HEAD = BUILDER.comment("Can phantom thread be applied to head slot items").define("Head", true);
        PHANTOM_THREAD_CHEST = BUILDER.comment("Can phantom thread be applied to chest slot items").define("Chest", true);
        PHANTOM_THREAD_LEGS = BUILDER.comment("Can phantom thread be applied to leg slot items").define("Legs", true);
        PHANTOM_THREAD_FEET = BUILDER.comment("Can phantom thread be applied to feet slot items").define("Feet", true);
        PHANTOM_THREAD_GLOW = BUILDER.comment("Does phantom thread have enchantment glow").define("Glow", true);
        BUILDER.pop();

        BUILDER.push("Recall_Potion");
        RECALL_POTION_USE_TIME = BUILDER.comment("Recall potion use time in ticks").define("Use_Time", 32);
        RECALL_POTION_COOLDOWN = BUILDER.comment("Recall potion cooldown in seconds").define("Cooldown", 0);
        RECALL_POTION_INTERDIMENSIONAL = BUILDER.comment("Can recall potion work from other dimensions").define("Interdimensional", false);
        RECALL_POTION_GLOW = BUILDER.comment("Does recall potion have enchantment glow").define("Glow", false);
        RECALL_POTION_INGREDIENT = BUILDER.comment("Main ingredient used to brew recall potions").define("Ingredient", "minecraft:ender_pearl");
        BUILDER.pop();

        BUILDER.push("Ruby_Ring");
        RUBY_RING_DAMAGE_INCREASE = BUILDER.comment("Damage increased for every health threshold in percentage").define("Damage_Increase", 0.05);
        RUBY_RING_HEALTH_THRESHOLD = BUILDER.comment("Health threshold for each damage increase").define("Health_Threshold", 20.0);
        RUBY_RING_HEALTH_CAP = BUILDER.comment("Max health cap for the damage increase").define("Health_Cap", 60.0);
        BUILDER.pop();

        BUILDER.push("Sculk_Treads");
        SCULK_TREADS_SPEED = BUILDER.comment("Speed increase gained in percentage").define("Speed_Increase", 0.25);
        BUILDER.pop();

        BUILDER.push("Shackle");
        SHACKLE_ARMOR = BUILDER.comment("Armor increase").define("Armor", 1);
        BUILDER.pop();

        BUILDER.push("Shadow_Aegis");
        SHADOW_AEGIS_IGNORE_DAMAGE_CHANCE = BUILDER.comment("Amount of hits taken until damage is ignored").define("Hits", 5);
        SHADOW_AEGIS_EXPLOSION_DAMAGE_REDUCTION = BUILDER.comment("Explosion damage reduction in percentage").define("Explosion_Damage_Reduction", 0.80);
        SHADOW_AEGIS_RES_LEVEL = BUILDER.comment("Level of resistance shadow aegis provides").define("Level", 2);
        SHADOW_AEGIS_ENHANCED_VISUALS_COMPAT = BUILDER.comment("Enable compat to disable explosion overlay from enhanced visuals").define("EnhancedVisuals_Compat", true);
        SHADOW_AEGIS_FIRST_AID_COMPAT = BUILDER.comment("Enable compat to add a chance to ignore headshots from first aid").define("FirstAid_Compat", true);
        SHADOW_AEGIS_FIRST_AID_IGNORE_CHANCE = BUILDER.comment("Chance to ignore headshots in percentage").define("Ignore_Chance", 0.15);
        BUILDER.pop();

        BUILDER.push("Spectre_Amulet");
        SPECTRE_AMULET_HEAL_CHANCE = BUILDER.comment("Chance to heal").define("Heal_Chance", 0.40);
        SPECTRE_AMULET_HEAL_AMOUNT = BUILDER.comment("Amount of health gained from attacking").define("Heal_Amount", 2.0);
        SPECTRE_AMULET_LUCK_FACTOR= BUILDER.comment("Percentage chance increase added to the base chance per luck attribute").defineInRange("Luck_Factor", 0.05, 0.0, 1.0);
        BUILDER.pop();

        BUILDER.push("Stealth_Manual");
        STEALTH_MANUAL_SNEAK_SPEED = BUILDER.comment("Sneak speed increase in percentage").define("Sneak_Speed", 0.50);
        BUILDER.pop();

        BUILDER.push("Storm_Fang");
        STORM_FANG_SPEED_INCREASE = BUILDER.comment("Speed increase in percentage").define("Speed_Increase", 0.25);
        STORM_FANG_SHOCK_DAMAGE = BUILDER.comment("Shock damage dealt from the storm shock ability").define("Shock_Damage", 8);
        STORM_FANG_SHOCK_RADIUS = BUILDER.comment("Maximum radius of the storm shock ability").define("Shock_Radius", 10);
        STORM_FANG_MANA_COST = BUILDER.comment("Mana cost of the storm shock ability").define("Mana_Cost", 25);
        STORM_FANG_COOLDOWN = BUILDER.comment("Storm shock ability cooldown in seconds").define("Cooldown", 8.0);
        BUILDER.pop();

        BUILDER.push("Red_Belt");
        RED_BELT_DODGE_DISTANCE = BUILDER.comment("Dodge distance increase in percentage").define("Dodge_Distance", 1.25);
        BUILDER.pop();

        BUILDER.push("Rogue_Belt");
        ROGUE_BELT_DODGE_DISTANCE = BUILDER.comment("Dodge distance increase in percentage").define("Dodge_Distance", 1.25);
        ROGUE_BELT_SNEAK_SPEED = BUILDER.comment("Sneak speed increase in percentage").define("Dodge_Distance", 0.50);
        BUILDER.pop();

        BUILDER.push("Tainted_Mirror");
        TAINTED_MIRROR_PLAYER_DAMAGE = BUILDER.comment("Makes players take only half of the damage they deal by the mirror instead of full damage").define("Reduced_Player_Damage", true);
        BUILDER.pop();

        BUILDER.push("True_Enderian_Scarf");
        TRUE_ENDERIAN_DAMAGE_REDUCTION = BUILDER.comment("Damage reduction in percentage").define("Damage_Reduction", 0.10);
        TRUE_ENDERIAN_EVADE = BUILDER.comment("Chance to evade an attack").define("Evade_Chance", 0.10);
        TRUE_ENDERIAN_REACH = BUILDER.comment("Amount of reach gain").define("Reach_Gain", 1.0);
        TRUE_ENDERIAN_LUCK_FACTOR = BUILDER.comment("Percentage chance increase added to the base chance per luck attribute").defineInRange("Luck_Factor", 0.01, 0.0, 1.0);
        TRUE_ENDERIAN_COMPAT = BUILDER.comment("Enable compat to disable endermen static from enhanced visuals").define("EnhancedVisuals_Compat", true);
        BUILDER.pop();

        BUILDER.push("Vanir_Mask");
        VANIR_MASK_DAMAGE_INCREASE = BUILDER.comment("Increased damage dealt to hit enemies").define("Damage_Increase", 2.0);
        VANIR_MASK_HEALTH_INCREASE = BUILDER.comment("Increased max health gained in percentage").define("Health_Increase", 0.10);
        VANIR_MASK_SPEED_INCREASE = BUILDER.comment("Increased speed gained in percentage").define("Speed_Increase", 0.10);
        VANIR_MASK_ARMOR_INCREASE = BUILDER.comment("Increased armor gained in percentage").define("Armor_Increase", 0.10);
        VANIR_MASK_ARMOR_TOUGHNESS_INCREASE = BUILDER.comment("Increased armor toughness gained in percentage").define("Armor_Toughness_Increase", 0.10);
        BUILDER.pop();

        BUILDER.push("Venom_Amulet");
        VENOM_AMULET_POISON_CHANCE = BUILDER.comment("Chance to apply poison").define("Poison_Chance", 0.40);
        VENOM_AMULET_POISON_LEVEL = BUILDER.comment("Level of poison inflicted").defineInRange("Poison_Level", 1, 1, 5);
        VENOM_AMULET_POISON_DURATION = BUILDER.comment("Duration of the poison effect in seconds").define("Duration", 5);
        VENOM_AMULET_LUCK_FACTOR = BUILDER.comment("Percentage chance increase added to the base chance per luck attribute").defineInRange("Luck_Factor", 0.05,0.0, 1.0);
        BUILDER.pop();

        BUILDER.push("Venom_Stone");
        VENOM_STONE_DAMAGE = BUILDER.comment("Increased damage dealt in percentage").define("Damage_Dealt", 1.20);
        VENOM_STONE_CHANCE = BUILDER.comment("Chance to inflict poison").define("Chance", 0.25);
        VENOM_STONE_LEVEL = BUILDER.comment("Level of the poison effect").defineInRange("Poison_Level", 2, 1, 5);
        VENOM_STONE_DURATION = BUILDER.comment("Duration of the poison effect in seconds").define("Duration", 5);
        VENOM_STONE_DEADLY_CHANCE = BUILDER.comment("Chance to convert poison into a more deadly poison").define("Deadly_Chance", 0.15);
        VENOM_STONE_LUCK_FACTOR = BUILDER.comment("Percentage chance increase added to the base chance per luck attribute").defineInRange("Luck_Factor", 0.025,0.0, 1.0);
        BUILDER.pop();

        BUILDER.push("Wooden_Headgear");
        WOODEN_HEADGEAR_ARMOR = BUILDER.comment("Armor increase").define("Armor", 1.0);
        WOODEN_HEADGEAR_ARROW_DAMAGE_REDUCTION = BUILDER.comment("Arrow damage reduction in percentage").define("Damage_Reduction", 0.05);
        WOODEN_HEADGEAR_FIRST_AID_COMPAT = BUILDER.comment("Enable compat to enable headshot damage reduction").define("FirstAid_Compat", true);
        WOODEN_HEADGEAR_HEADSHOT_DAMAGE_REDUCTION = BUILDER.comment("Headshot damage reduction in percentage").define("Headshot_Damage_Reduction", 0.04);
        BUILDER.pop();

        BUILDER.push("Wormhole_Potion");
        WORMHOLE_POTION_USE_TIME = BUILDER.comment("Wormhole potion use time in ticks").define("Use_Time", 32);
        WORMHOLE_POTION_COOLDOWN = BUILDER.comment("Wormhole potion cooldown in seconds").define("Cooldown", 30);
        WORMHOLE_POTION_INTERDIMENSIONAL = BUILDER.comment("Can wormhole potion work from other dimensions").define("Interdimensional", true);
        WORMHOLE_POTION_GLOW = BUILDER.comment("Does wormhole potion have enchantment glow").define("Glow", false);
        WORMHOLE_POTION_INGREDIENT = BUILDER.comment("Main ingredient used to brew wormhole potions").define("Ingredient", "minecraft:ender_pearl");
        BUILDER.pop();

        /*
        // Artifact Category End
        */
        BUILDER.pop();

        /*
        // Soul Category
        */
        BUILDER.comment("Global soul settings for all of their stats and abilities").push("Souls");
        BUILDER.push("Envy");
        ENVY_EFFECT = BUILDER.comment("Passive effect applied by this soul").define("Passive_Effect", "minecraft:invisibility");
        ENVY_MAGIC_DAMAGE = BUILDER.comment("Amount of magic damage gained per stage, this config is unused if apothecary is installed").define("Magic_Damage", 0.10);
        ENVY_MAGIC_DAMAGE_CONVERSION = BUILDER.comment("Amount of damage that is converted into magic damage per stage").define("Magic_Damage_Conversion", 0.10);
        ENVY_MANA_SHIELD_DAMAGE_MULTIPLIER = BUILDER.comment("Multiplier that applies to the damage the mana shield takes, based on the damage dealt to it").define("Mana_Shield_Damage_Multiplier", 3.0);
        ENVY_FLIGHT_MANA_COST = BUILDER.comment("Mana cost per second of using flight").define("Flight_Mana_Cost", 4.0);
        ENVY_FLIGHT_START_THRESHOLD = BUILDER.comment("Mana required to start flying, any number under this value wont allow flight").define("Flight_Start_Threshold", 10.0);
        ENVY_MANA_SHIELD_START_THRESHOLD = BUILDER.comment("Mana required to enable soul mana shield, any number under this value wont enable the shield").define("Mana_Shield_Start_Cost", 50.0);
        ENVY_MANA_SHIELD_COST = BUILDER.comment("Mana cost for enabling the soul mana shield, this amount of mana is taken away from the player upon enabling").define("Mana_Shield_Mana_Cost", 50.0);
        ENVY_MANA_SHIELD_DIMINISHING_FACTOR = BUILDER.comment("Diminishing returns factor for mana shield damage absorption, this number is raised to the power of the damage dealt").define("Diminishing_Returns_Factor", 0.75);
        ENVY_MANA_SHIELD_DIMINISHING_THRESHOLD = BUILDER.comment("The amount of incoming damage needed to be taken before the diminishing factor is active").define("Diminishing_Returns_Threshold", 10.0);
        ENVY_MANA_SHIELD_ARMOR_MULTIPLIER = BUILDER.comment("Every armor point adds to the total damage multiplier for the mana shield").define("Armor_Multiplier", 0.025);
        ENVY_MANA_SHIELD_ARMOR_TOUGHNESS_MULTIPLIER = BUILDER.comment("Every armor toughness point adds to the total damage multiplier for the mana shield").define("Armor_Toughness_Multiplier", 0.05);
        MANA_SHIELD_SOUNDS = BUILDER.comment("Enable mana shield sound effects").define("Mana_Shield_Sounds", true);
        BUILDER.push("Evolve");
        ENVY_EVOLVE_DAMAGE_1 = BUILDER.comment("Damage required to evolve from stage 1 -> 2").define("Stage2", 2000.0);
        ENVY_EVOLVE_DAMAGE_2 = BUILDER.comment("Damage required to evolve from stage 2 -> 3").define("Stage3", 5000.0);
        ENVY_EVOLVE_DAMAGE_3 = BUILDER.comment("Damage required to evolve from stage 3 -> 4").define("Stage4", 25000.0);
        ENVY_EVOLVE_DAMAGE_4 = BUILDER.comment("Damage required to evolve from stage 4 -> 5").define("Stage5", 50000.0);
        BUILDER.pop();
        BUILDER.push("Irons_Spellbooks");
        ENVY_SPELLBOOKS_MANA_SHIELD_DAMAGE_MULTIPLIER = BUILDER.comment("Multiplier that applies to the damage the mana shield takes, based on the damage dealt to it").define("Mana_Shield_Damage_Multiplier", 5.0);
        ENVY_SPELLBOOKS_FLIGHT_TIME = BUILDER.comment("Total time the player can fly until their mana runs out in seconds").define("Flight_Time", 40.0);
        ENVY_SPELLBOOKS_FLIGHT_START_COST = BUILDER.comment("Mana required to start flying, any number under this value wont allow flight").define("Flight_Start_Threshold", 50.0);
        ENVY_SPELLBOOKS_FLIGHT_MANA_OFFSET = BUILDER.comment("Mana to take away every tick on top of the flight time").define("Flight_Mana_Offset", 0.06);
        ENVY_SPELLBOOKS_MANA_SHIELD_START_THRESHOLD = BUILDER.comment("Mana required to enable soul mana shield, any number under this value wont enable the shield").define("Mana_Shield_Start_Threshold", 250.0);
        ENVY_SPELLBOOKS_MANA_SHIELD_COST = BUILDER.comment("Mana cost for enabling the soul mana shield, this amount of mana is taken away from the player upon enabling").define("Mana_Shield_Mana_Cost", 200.0);
        ENVY_SPELLBOOKS_MANA_SHIELD_ARMOR_MULTIPLIER = BUILDER.comment("Every armor point adds to the total damage multiplier for the mana shield").define("Armor_Multiplier", 0.1);
        ENVY_SPELLBOOKS_MANA_SHIELD_ARMOR_TOUGHNESS_MULTIPLIER = BUILDER.comment("Every armor toughness point adds to the total damage multiplier for the mana shield").define("Armor_Toughness_Multiplier", 0.2);
        ENVY_SPELLBOOKS_MANA_SHIELD_MAX_MANA_SCALE = BUILDER.comment("The diminishing factor for max mana, this number is raised to the power of every 100 max mana (100 = 1)").define("Max_Mana_Scale", 0.25);
        ENVY_SPELLBOOKS_SPELL_COST_REDUCTION = BUILDER.comment("Reduces spell mana costs by this amount in percentage").define("Spell_Cost_Reduction", 0.25);
        BUILDER.pop();
        BUILDER.push("First_Aid");
        ENVY_FIRST_AID_MANA_SHIELD_DAMAGE_MULTIPLIER = BUILDER.comment("Number that is added to the multiplier when first aid is installed, e.g... multiplier = 3, (this) = 2, final = 5").define("Mana_Shield_Added_Damage_Multiplier", 2.0);
        BUILDER.pop();
        ENVY_ATTRIBUTES = BUILDER.comment(
                "Soul Attributes",
                "Format: \"attribute;base;(per stage) increment;operation\"",
                "Operations: ADDITION, MULTIPLY_BASE, MULTIPLY_TOTAL"
        ).defineList("envy_attributes", List.of(
                "apothecary:magic_damage;0.20;0.075;MULTIPLY_BASE",
                "minecraft:generic.movement_speed;0.05;0.05;MULTIPLY_BASE",
                "apothecary:jump_height;0.0;0.05;MULTIPLY_BASE",
                "minecraft:generic.attack_damage;-0.05;-0.05;MULTIPLY_BASE",
                "minecraft:generic.armor;-0.10;-0.10;MULTIPLY_BASE",
                "minecraft:generic.knockback_resistance;-0.30;-0.05;MULTIPLY_BASE"
        ), o -> o instanceof String);
        BUILDER.pop();

        BUILDER.push("Wrath");
        WRATH_EFFECT = BUILDER.comment("Passive effect applied by this soul").define("Passive_Effect", "minecraft:strength");
        WRATH_SINFUL_CHANCE = BUILDER.comment("Chance every hit to gain the sinful effect").define("Sinful_Chance", 0.30);
        WRATH_SINFUL_DURATION = BUILDER.comment("Duration of the sinful effect gained").define("Sinful_Duration", 110);
        WRATH_SINFUL_MAX_LEVEL = BUILDER.comment("Max level the sinful effect can reach").define("Sinful_Max_Level", 5);
        WRATH_ANNIHILATION_DAMAGE_MULTIPLIER = BUILDER.comment("Amount of damage percentage added per sinful stack").define("Annihilation_Damage_Multiplier", 0.2);
        WRATH_ANNIHILATION_MAX_STACKS = BUILDER.comment("Max amount of annihilation stacks the player can achieve").define("Annihilation_Max_Stacks", 5);
        BUILDER.push("Stage4");
        WRATH_STAGE_4_SINFUL_CHANCE = BUILDER.comment("Chance every hit to gain the sinful effect").define("Sinful_Chance", 0.15);
        WRATH_STAGE_4_SINFUL_DURATION = BUILDER.comment("Duration of the sinful effect gained").define("Sinful_Duration", 110);
        WRATH_STAGE_4_SINFUL_MAX_LEVEL = BUILDER.comment("Max level the sinful effect can reach").define("Sinful_Max_Level", 3);
        BUILDER.pop();
        BUILDER.push("Evolve");
        WRATH_EVOLVE_DAMAGE_1 = BUILDER.comment("Damage required to evolve from stage 1 -> 2").define("Stage2", 2000.0);
        WRATH_EVOLVE_DAMAGE_2 = BUILDER.comment("Damage required to evolve from stage 2 -> 3").define("Stage3", 5000.0);
        WRATH_EVOLVE_DAMAGE_3 = BUILDER.comment("Damage required to evolve from stage 3 -> 4").define("Stage4", 25000.0);
        WRATH_EVOLVE_DAMAGE_4 = BUILDER.comment("Damage required to evolve from stage 4 -> 5").define("Stage5", 50000.0);
        BUILDER.pop();
        WRATH_ATTRIBUTES = BUILDER.comment(
                "Soul Attributes",
                "Format: \"attribute;base;(per stage) increment;operation\"",
                "Operations: ADDITION, MULTIPLY_BASE, MULTIPLY_TOTAL"
        ).defineList("wrath_attributes", List.of(
                "minecraft:generic.attack_damage;0.10;0.10;MULTIPLY_BASE",
                "minecraft:generic.attack_speed;0.03;0.03;MULTIPLY_BASE",
                "minecraft:generic.knockback_resistance;0.0;0.25;ADDITION",
                "minecraft:generic.armor;-0.05;-0.05;MULTIPLY_BASE",
                "minecraft:generic.armor_toughness;-0.05;-0.05;MULTIPLY_BASE",
                "apothecary:magic_shielding;-1.0;-1.0;ADDITION"
        ), o -> o instanceof String);
        BUILDER.pop();

        /*
        // Soul Category End
        */
        BUILDER.pop();

        /*
        // Loot Category
        */
        BUILDER.comment("Chances for artifacts to appear in loot tables").push("Loot");

        BUILDER.push("Bezoar");
        BEZOAR_DROP_CHANCE = BUILDER.comment("Bezoar drop chance from cave spiders in percentage").define("Drop_Chance", 0.05);
        BUILDER.pop();

        BUILDER.push("Vitamins");
        VITAMINS_DROP_CHANCE = BUILDER.comment("Vitamins drop chance from elder guardians in percentage").define("Drop_Chance", 0.40);
        BUILDER.pop();

        BUILDER.push("Fast_Clock");
        FAST_CLOCK_DROP_CHANCE = BUILDER.comment("Fast Clock drop chance from strays in percentage").define("Drop_Chance", 0.02);
        BUILDER.pop();

        BUILDER.push("Desert_Charm");
        DESERT_CHARM_DROP_CHANCE = BUILDER.comment("Desert Charm drop chance from husks in percentage").define("Drop_Chance", 0.02);
        BUILDER.pop();

        BUILDER.push("Wither_Shard");
        WITHER_SHARD_DROP_CHANCE = BUILDER.comment("Wither Shard drop chance from the wither in percentage").define("Drop_Chance", 0.25);
        BUILDER.pop();

        BUILDER.push("Shulker_Heart");
        SHULKER_HEART_DROP_CHANCE = BUILDER.comment("Shulker Heart drop chance from shulkers in percentage").define("Drop_Chance", 0.10);
        BUILDER.pop();

        BUILDER.push("Enderian_Scarf");
        ENDERIAN_SCARF_DROP_CHANCE = BUILDER.comment("Enderian Scarf drop chance from endermen in percentage").define("Drop_Chance", 0.01);
        BUILDER.pop();

        BUILDER.push("Shackle");
        SHACKLE_DROP_CHANCE = BUILDER.comment("Shackle drop chance from zombies in percentage").define("Drop_Chance", 0.01);
        BUILDER.pop();

        BUILDER.push("Magic_Quiver");
        MAGIC_QUIVER_SKELETON_DROP_CHANCE = BUILDER.comment("Magic Quiver drop chance from skeletons in percentage").define("Skeleton_Drop_Chance", 0.01);
        MAGIC_QUIVER_PILLAGER_DROP_CHANCE = BUILDER.comment("Magic Quiver drop chance from pillagers in percentage").define("Pillager_Drop_Chance", 0.04);
        MAGIC_QUIVER_TRADE_CHANCE = BUILDER.comment("Magic Quiver trade chance from fletcher villagers in percentage").define("Trade_Chance", 0.25);
        BUILDER.pop();

        BUILDER.push("Ender_Dragon_Claw");
        ENDER_DRAGON_CLAW_DROP_CHANCE = BUILDER.comment("Ender Dragon Claw drop chance from the ender dragon in percentage").define("Drop_Chance", 0.25);
        BUILDER.pop();

        BUILDER.push("Ender_Dragon_Eye");
        ENDER_DRAGON_EYE_DROP_CHANCE = BUILDER.comment("Ender Dragon Eye drop chance from the ender dragon in percentage").define("Drop_Chance", 0.10);
        BUILDER.pop();

        BUILDER.push("Tainted_Mirror");
        TAINTED_MIRROR_DROP_CHANCE = BUILDER.comment("Tainted Mirror drop chance from the warden in percentage").define("Drop_Chance", 0.10);
        BUILDER.pop();

        BUILDER.push("Cursed_Doll");
        CURSED_DOLL_DROP_CHANCE = BUILDER.comment("Cursed Doll drop chance from witches in percentage").define("Drop_Chance", 0.02);
        BUILDER.pop();

        BUILDER.push("Red_Belt");
        RED_BELT_DROP_CHANCE = BUILDER.comment("Red Belt drop chance from piglin brutes in percentage").define("Drop_Chance", 0.05);
        BUILDER.pop();

        BUILDER.push("Melody_Plushie");
        MELODY_PLUSHIE_DROP_CHANCE = BUILDER.comment("Melody Plushie drop chance from cat morning gifts in percentage").define("Drop_Chance", 0.01);
        BUILDER.pop();

        BUILDER.push("Dune_Treads");
        DUNE_TREADS_DROP_CHANCE = BUILDER.comment("Dune Treads loot chance from desert pyramids in percentage").define("Loot_Chance", 0.15);
        BUILDER.pop();

        BUILDER.push("Ruby_Ring");
        RUBY_RING_DROP_CHANCE = BUILDER.comment("Ruby Ring loot chance from bastions in percentage").define("Loot_Chance", 0.05);
        BUILDER.pop();

        BUILDER.push("Ice_Crystal");
        ICE_CRYSTAL_DROP_CHANCE = BUILDER.comment("Ice Crystal loot chance from igloos in percentage").define("Loot_Chance", 1.0);
        BUILDER.pop();

        BUILDER.push("Natures_Mantle");
        NATURES_MANTLE_DROP_CHANCE = BUILDER.comment("Natures Mantle loot chance from jungle temples in percentage").define("Loot_Chance", 0.20);
        BUILDER.pop();

        BUILDER.push("Broken_Heart");
        BROKEN_HEART_DROP_CHANCE = BUILDER.comment("Broken Heart loot chance from simple dungeons in percentage").define("Loot_Chance", 0.05);
        BUILDER.pop();

        BUILDER.push("Storm_Fang");
        STORM_FANG_DROP_CHANCE = BUILDER.comment("Storm Fang drop chance from charged creepers in percentage").define("Drop_Chance", 0.10);
        BUILDER.pop();

        BUILDER.push("Shadow_Dust");
        SHADOW_DUST_DUNGEON_DROP_CHANCE = BUILDER.comment("Shadow Dust loot chance from simple dungeons in percentage").define("Dungeon_Loot_Chance", 0.05);
        SHADOW_DUST_ANCIENT_CITY_DROP_CHANCE = BUILDER.comment("Shadow Dust loot chance from ancient cities in percentage").define("Ancient_City_Loot_Chance", 0.05);
        BUILDER.pop();

        BUILDER.push("Holy_Mantle");
        HOLY_MANTLE_TRADE_CHANCE = BUILDER.comment("Holy Mantle trade chance from max level cleric villagers in percentage").define("Trade_Chance", 0.05);
        BUILDER.pop();

        BUILDER.push("Stealth_Manual");
        STEALTH_MANUAL_TRADE_CHANCE = BUILDER.comment("Stealth Manual trade chance from max level cartographer villagers in percentage").define("Trade_Chance", 0.25);
        BUILDER.pop();

        BUILDER.push("Sculk_Lens");
        SCULK_LENS_DROP_CHANCE = BUILDER.comment("Sculk Lens loot chance from ancient cities in percentage").define("Loot_Chance", 0.05);
        BUILDER.pop();

        BUILDER.push("Recall_Potion");
        RECALL_POTION_DROP_CHANCE = BUILDER.comment("Recall Potion loot chance from simple dungeons in percentage").define("Loot_Chance", 0.25);
        BUILDER.pop();

        BUILDER.push("Wormhole_Potion");
        WORMHOLE_POTION_DROP_CHANCE = BUILDER.comment("Wormhole Potion loot chance from simple dungeons in percentage").define("Loot_Chance", 0.25);
        BUILDER.pop();

        BUILDER.push("Cobalt_Shield");
        COBALT_SHIELD_DROP_CHANCE = BUILDER.comment("Cobalt Shield loot chance from simple dungeon in percentage").define("Loot_Chance", 0.10);
        BUILDER.pop();

        /*
        // Loot Category End
        */
        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}