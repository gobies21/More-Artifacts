package net.gobies.moreartifacts.config;

import net.gobies.moreartifacts.MoreArtifacts;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import java.util.List;

@Mod.EventBusSubscriber(modid = MoreArtifacts.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonConfig {
    private static final String FILENAME = "moreartifacts-common.toml";

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    // Artifacts
    public static ForgeConfigSpec.ConfigValue<Double> SKULL_FIRE_DAMAGE_REDUCTION;
    public static float skull_fire_damage_reduction;

    public static ForgeConfigSpec.ConfigValue<Double> OBSIDIAN_SHIELD_FIRE_DAMAGE_REDUCTION;
    public static float obsidian_shield_fire_damage_reduction;

    public static ForgeConfigSpec.ConfigValue<List<? extends String>> ANKH_CHARM_ADDITIONAL_EFFECTS;
    public static List<? extends String> ankh_charm_additional_effects;

    public static ForgeConfigSpec.ConfigValue<Double> ANKH_SHIELD_FIRE_DAMAGE_REDUCTION;
    public static float ankh_shield_fire_damage_reduction;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> ANKH_SHIELD_ADDITIONAL_EFFECTS;
    public static List<? extends String> ankh_shield_additional_effects;

    public static ForgeConfigSpec.ConfigValue<Double> DECAY_AMULET_WITHER_CHANCE;
    public static float decay_amulet_wither_chance;
    public static ForgeConfigSpec.ConfigValue<Integer> DECAY_AMULET_WITHER_DURATION;
    public static int decay_amulet_wither_duration;
    public static ForgeConfigSpec.ConfigValue<Integer> DECAY_AMULET_WITHER_LEVEL;
    public static int decay_amulet_wither_level;

    public static ForgeConfigSpec.ConfigValue<Double> VENOM_AMULET_POISON_CHANCE;
    public static float venom_amulet_poison_chance;
    public static ForgeConfigSpec.ConfigValue<Integer> VENOM_AMULET_POISON_DURATION;
    public static int venom_amulet_poison_duration;
    public static ForgeConfigSpec.ConfigValue<Integer> VENOM_AMULET_POISON_LEVEL;
    public static int venom_amulet_poison_level;

    public static ForgeConfigSpec.ConfigValue<Double> SPECTRE_AMULET_HEAL_CHANCE;
    public static float spectre_amulet_heal_chance;
    public static ForgeConfigSpec.ConfigValue<Double> SPECTRE_AMULET_HEAL_AMOUNT;
    public static float spectre_amulet_heal_amount;

    public static ForgeConfigSpec.ConfigValue<Double> NECROPLASM_AMULET_HEAL_CHANCE;
    public static float necroplasm_amulet_heal_chance;
    public static ForgeConfigSpec.ConfigValue<Double> NECROPLASM_AMULET_HEAL_AMOUNT;
    public static float necroplasm_amulet_heal_amount;
    public static ForgeConfigSpec.ConfigValue<Double> NECROPLASM_AMULET_POISON_CHANCE;
    public static float necroplasm_amulet_poison_chance;
    public static ForgeConfigSpec.ConfigValue<Integer> NECROPLASM_AMULET_POISON_LEVEL;
    public static int necroplasm_amulet_poison_level;
    public static ForgeConfigSpec.ConfigValue<Integer> NECROPLASM_AMULET_POISON_DURATION;
    public static int necroplasm_amulet_poison_duration;
    public static ForgeConfigSpec.ConfigValue<Double> NECROPLASM_AMULET_WITHER_CHANCE;
    public static float necroplasm_amulet_wither_chance;
    public static ForgeConfigSpec.ConfigValue<Integer> NECROPLASM_AMULET_WITHER_LEVEL;
    public static int necroplasm_amulet_wither_level;
    public static ForgeConfigSpec.ConfigValue<Integer> NECROPLASM_AMULET_WITHER_DURATION;
    public static int necroplasm_amulet_wither_duration;

    public static ForgeConfigSpec.ConfigValue<Integer> MECHANICAL_GLOVE_DAMAGE;
    public static int mechanical_glove_damage;

    public static ForgeConfigSpec.ConfigValue<Integer> SHACKLE_ARMOR;
    public static int shackle_armor;

    public static ForgeConfigSpec.ConfigValue<Integer> IGNORE_DAMAGE_CHANCE;
    public static int ignore_damage_chance;
    public static ForgeConfigSpec.ConfigValue<Double> EXPLOSION_DAMAGE_REDUCTION;
    public static float explosion_damage_reduction;
    public static ForgeConfigSpec.ConfigValue<Integer> HERO_SHIELD_RES_LEVEL;
    public static int hero_shield_res_level;
    public static ForgeConfigSpec.ConfigValue<Boolean> HERO_SHIELD_COMPAT;
    public static boolean hero_shield_compat;

    public static ForgeConfigSpec.ConfigValue<Double> ENDERIAN_DAMAGE_REDUCTION;
    public static float enderian_damage_reduction;

    public static ForgeConfigSpec.ConfigValue<Double> TRUE_ENDERIAN_DAMAGE_REDUCTION;
    public static float true_enderian_damage_reduction;
    public static ForgeConfigSpec.ConfigValue<Double> TRUE_ENDERIAN_EVADE;
    public static float true_enderian_evade;
    public static ForgeConfigSpec.ConfigValue<Double> TRUE_ENDERIAN_REACH;
    public static float true_enderian_reach;
    public static ForgeConfigSpec.ConfigValue<Boolean> TRUE_ENDERIAN_COMPAT;
    public static boolean true_enderian_compat;

    public static ForgeConfigSpec.ConfigValue<Double> GILDED_DAMAGE_REDUCTION;
    public static float gilded_damage_reduction;
    public static ForgeConfigSpec.ConfigValue<Double> GILDED_DAMAGE_DEALT;
    public static float gilded_damage_dealt;

    public static ForgeConfigSpec.ConfigValue<Double> PLUSHIE_HEALTH;
    public static float plushie_health;
    public static ForgeConfigSpec.ConfigValue<Integer> PLUSHIE_HEALTH_BOOST_LEVEL;
    public static int plushie_health_boost_level;
    public static ForgeConfigSpec.ConfigValue<Integer> PLUSHIE_DURATION;
    public static int plushie_duration;
    public static ForgeConfigSpec.ConfigValue<Integer> PLUSHIE_REGEN_LEVEL;
    public static int plushie_regen_level;

    public static ForgeConfigSpec.ConfigValue<Double> EMERALD_RING_DAMAGE;
    public static float emerald_ring_damage;
    public static ForgeConfigSpec.ConfigValue<Double> EMERALD_RING_EMERALDS;
    public static float emerald_ring_emeralds;
    public static ForgeConfigSpec.ConfigValue<Integer> EMERALD_RING_LUCK;
    public static int emerald_ring_luck;

    public static ForgeConfigSpec.ConfigValue<Double> MAGIC_QUIVER_DAMAGE;
    public static float magic_quiver_damage;
    public static ForgeConfigSpec.ConfigValue<Double> MAGIC_QUIVER_AMMO;
    public static float magic_quiver_ammo;

    public static ForgeConfigSpec.ConfigValue<Double> ENVENOMED_QUIVER_DAMAGE;
    public static float envenomed_quiver_damage;
    public static ForgeConfigSpec.ConfigValue<Integer> ENVENOMED_QUIVER_POISON_LEVEL;
    public static int envenomed_quiver_poison_level;
    public static ForgeConfigSpec.ConfigValue<Integer> ENVENOMED_QUIVER_POISON_DURATION;
    public static int envenomed_quiver_poison_duration;
    public static ForgeConfigSpec.ConfigValue<Integer> ENVENOMED_QUIVER_WITHER_LEVEL;
    public static int envenomed_quiver_wither_level;
    public static ForgeConfigSpec.ConfigValue<Integer> ENVENOMED_QUIVER_WITHER_DURATION;
    public static int envenomed_quiver_wither_duration;
    public static ForgeConfigSpec.ConfigValue<Double> ENVENOMED_QUIVER_AMMO;
    public static float envenomed_quiver_ammo;

    public static ForgeConfigSpec.ConfigValue<Double> MOLTEN_QUIVER_DAMAGE;
    public static float molten_quiver_damage;
    public static ForgeConfigSpec.ConfigValue<Double> MOLTEN_QUIVER_ONFIRE_DAMAGE;
    public static float molten_quiver_onfire_damage;
    public static ForgeConfigSpec.ConfigValue<Integer> MOLTEN_QUIVER_DURATION;
    public static int molten_quiver_duration;
    public static ForgeConfigSpec.ConfigValue<Double> MOLTEN_QUIVER_AMMO;
    public static float molten_quiver_ammo;

    public static ForgeConfigSpec.ConfigValue<Double> WOODEN_HEADGEAR_ARMOR;
    public static float wooden_headgear_armor;
    public static ForgeConfigSpec.ConfigValue<Double> WOODEN_HEADGEAR_ARROW_DAMAGE_TAKEN;
    public static float wooden_headgear_arrow_damage_taken;

    public static ForgeConfigSpec.ConfigValue<Double> GOLDEN_HEADGEAR_ARMOR;
    public static float golden_headgear_armor;
    public static ForgeConfigSpec.ConfigValue<Double> GOLDEN_HEADGEAR_ARROW_DAMAGE_TAKEN;
    public static float golden_headgear_arrow_damage_taken;

    public static ForgeConfigSpec.ConfigValue<Double> NETHERITE_HEADGEAR_ARMOR;
    public static float netherite_headgear_armor;
    public static ForgeConfigSpec.ConfigValue<Double> NETHERITE_HEADGEAR_ARROW_DAMAGE_TAKEN;
    public static float netherite_headgear_arrow_damage_taken;

    public static ForgeConfigSpec.ConfigValue<Double> ENDER_DRAGON_CLAW_DAMAGE;
    public static float ender_dragon_claw_damage;
    public static ForgeConfigSpec.ConfigValue<Double> ENDER_DRAGON_CLAW_CHANCE;
    public static float ender_dragon_claw_chance;

    public static ForgeConfigSpec.ConfigValue<Integer> ENDERIAN_EYE_RADIUS;
    public static int enderian_eye_radius;
    public static ForgeConfigSpec.ConfigValue<Double> ENDERIAN_EYE_COOLDOWN;
    public static float enderian_eye_cooldown;

    public static ForgeConfigSpec.ConfigValue<Double> MECHANICAL_CLAW_DAMAGE;
    public static float mechanical_claw_damage;
    public static ForgeConfigSpec.ConfigValue<Double> MECHANICAL_CLAW_BLEED_CHANCE;
    public static float mechanical_claw_bleed_chance;
    public static ForgeConfigSpec.ConfigValue<Integer> MECHANICAL_CLAW_BLEED_DAMAGE;
    public static int mechanical_claw_bleed_damage;
    public static ForgeConfigSpec.ConfigValue<Integer> MECHANICAL_CLAW_BLEED_DURATION;
    public static int mechanical_claw_bleed_duration;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> BLEED_BLACKLISTED_ENTITIES;
    public static List<? extends String> bleed_blacklisted_entities;

    public static ForgeConfigSpec.ConfigValue<Double> ECHO_GLOVE_DAMAGE;
    public static float echo_glove_damage;
    public static ForgeConfigSpec.ConfigValue<Double> ECHO_GLOVE_ATTACK_SPEED;
    public static float echo_glove_attack_speed;
    public static ForgeConfigSpec.ConfigValue<Double> ECHO_GLOVE_IGNORE_CHANCE;
    public static float echo_glove_ignore_chance;

    public static ForgeConfigSpec.ConfigValue<Double> VENOM_STONE_DAMAGE;
    public static float venom_stone_damage;
    public static ForgeConfigSpec.ConfigValue<Double> VENOM_STONE_CHANCE;
    public static float venom_stone_chance;
    public static ForgeConfigSpec.ConfigValue<Integer> VENOM_STONE_LEVEL;
    public static int venom_stone_level;
    public static ForgeConfigSpec.ConfigValue<Integer> VENOM_STONE_DURATION;
    public static int venom_stone_duration;
    public static ForgeConfigSpec.ConfigValue<Double> VENOM_STONE_DEADLY_CHANCE;
    public static float venom_stone_deadly_chance;

    public static ForgeConfigSpec.ConfigValue<Double> DECAY_STONE_DAMAGE;
    public static float decay_stone_damage;
    public static ForgeConfigSpec.ConfigValue<Double> DECAY_STONE_CHANCE;
    public static float decay_stone_chance;
    public static ForgeConfigSpec.ConfigValue<Integer> DECAY_STONE_LEVEL;
    public static int decay_stone_level;
    public static ForgeConfigSpec.ConfigValue<Integer> DECAY_STONE_DURATION;
    public static int decay_stone_duration;
    public static ForgeConfigSpec.ConfigValue<Integer> DECAY_STONE_HEAL_AMOUNT;
    public static int decay_stone_heal_amount;

    public static ForgeConfigSpec.ConfigValue<Double> FIRE_STONE_DAMAGE;
    public static float fire_stone_damage;
    public static ForgeConfigSpec.ConfigValue<Double> FIRE_STONE_CHANCE;
    public static float fire_stone_chance;
    public static ForgeConfigSpec.ConfigValue<Integer> FIRE_STONE_DURATION;
    public static int fire_stone_duration;

    public static ForgeConfigSpec.ConfigValue<Double> ICE_STONE_DAMAGE;
    public static float ice_stone_damage;
    public static ForgeConfigSpec.ConfigValue<Double> ICE_STONE_CHANCE;
    public static float ice_stone_chance;
    public static ForgeConfigSpec.ConfigValue<Integer> ICE_STONE_DURATION;
    public static int ice_stone_duration;
    public static ForgeConfigSpec.ConfigValue<Double> ICE_STONE_ENCASED_CHANCE;
    public static float ice_stone_encased_chance;
    public static ForgeConfigSpec.ConfigValue<Integer> ICE_STONE_ENCASED_DURATION;
    public static int ice_stone_encased_duration;
    public static ForgeConfigSpec.ConfigValue<Boolean> ICE_STONE_COMPAT;
    public static boolean ice_stone_compat;

    public static ForgeConfigSpec.ConfigValue<Integer> RECALL_POTION_USE_TIME;
    public static int recall_potion_use_time;
    public static ForgeConfigSpec.ConfigValue<Integer> RECALL_POTION_COOLDOWN;
    public static int recall_potion_cooldown;
    public static ForgeConfigSpec.ConfigValue<Boolean> RECALL_POTION_INTERDIMENSIONAL;
    public static boolean recall_potion_interdimensional;
    public static ForgeConfigSpec.ConfigValue<Boolean> RECALL_POTION_GLOW;
    public static boolean recall_potion_glow;
    public static ForgeConfigSpec.ConfigValue<String> RECALL_POTION_INGREDIENT;
    public static String recall_potion_ingredient;

    public static ForgeConfigSpec.ConfigValue<Double> VANIR_MASK_DAMAGE_INCREASE;
    public static float vanir_mask_damage_increase;
    public static ForgeConfigSpec.ConfigValue<Double> VANIR_MASK_HEALTH_INCREASE;
    public static float vanir_mask_health_increase;
    public static ForgeConfigSpec.ConfigValue<Double>  VANIR_MASK_SPEED_INCREASE;
    public static float vanir_mask_speed_increase;
    public static ForgeConfigSpec.ConfigValue<Double> VANIR_MASK_ARMOR_INCREASE;
    public static float vanir_mask_armor_increase;
    public static ForgeConfigSpec.ConfigValue<Double> VANIR_MASK_ARMOR_TOUGHNESS_INCREASE;
    public static float vanir_mask_armor_toughness_increase;

    public static ForgeConfigSpec.ConfigValue<Double> RUBY_RING_DAMAGE_INCREASE;
    public static float ruby_ring_damage_increase;
    public static ForgeConfigSpec.ConfigValue<Double> RUBY_RING_HEALTH_THRESHOLD;
    public static float ruby_ring_health_threshold;
    public static ForgeConfigSpec.ConfigValue<Double> RUBY_RING_HEALTH_CAP;
    public static float ruby_ring_health_cap;

    public static ForgeConfigSpec.ConfigValue<Boolean> TAINTED_MIRROR_PLAYER_DAMAGE;
    public static boolean tainted_mirror_player_damage;

    public static ForgeConfigSpec.ConfigValue<Double> NATURES_MANTLE_SPEED_INCREASE;
    public static float natures_mantle_speed_increase;

    public static ForgeConfigSpec.ConfigValue<Double> ENDER_DRAGON_EYE_SUMMON_CHANCE;
    public static float ender_dragon_eye_summon_chance;
    public static ForgeConfigSpec.ConfigValue<Double> ENDER_DRAGON_EYE_FOLLOW_DISTANCE;
    public static float ender_dragon_eye_follow_distance;
    public static ForgeConfigSpec.ConfigValue<Boolean> ENDER_DRAGON_EYE_COMPAT;
    public static boolean ender_dragon_eye_compat;

    public static ForgeConfigSpec.ConfigValue<Double> LEATHER_TREADS_SPEED;
    public static float leather_treads_speed;

    public static ForgeConfigSpec.ConfigValue<Double> DUNE_TREADS_SPEED;
    public static float dune_treads_speed;
    public static ForgeConfigSpec.ConfigValue<Double> DUNE_TREADS_SAND_SPEED;
    public static float dune_treads_sand_speed;

    public static ForgeConfigSpec.ConfigValue<Double> HIGH_JUMPERS_SPEED;
    public static float high_jumpers_speed;

    public static ForgeConfigSpec.ConfigValue<Double> MECHANICAL_GEARS_SPEED;
    public static float mechanical_gears_speed;
    public static ForgeConfigSpec.ConfigValue<Double> MECHANICAL_GEARS_DODGE_CHANCE;
    public static float mechanical_gears_dodge_chance;

    public static ForgeConfigSpec.ConfigValue<Double> BLAZING_TREADS_SPEED;
    public static float blazing_treads_speed;

    public static ForgeConfigSpec.ConfigValue<Double> ENDERIAN_TREADS_SPEED;
    public static float enderian_treads_speed;
    public static ForgeConfigSpec.ConfigValue<Double> ENDERIAN_TREADS_COOLDOWN;
    public static float enderian_treads_cooldown;

    public static ForgeConfigSpec.ConfigValue<Double> SCULK_TREADS_SPEED;
    public static float sculk_treads_speed;

    // Loot
    public static ForgeConfigSpec.ConfigValue<Double> BEZOAR_DROP_CHANCE;
    public static float bezoar_drop_chance;

    public static ForgeConfigSpec.ConfigValue<Double> VITAMINS_DROP_CHANCE;
    public static float vitamins_drop_chance;

    public static ForgeConfigSpec.ConfigValue<Double> FAST_CLOCK_DROP_CHANCE;
    public static float fast_clock_drop_chance;

    public static ForgeConfigSpec.ConfigValue<Double> DESERT_CHARM_DROP_CHANCE;
    public static float desert_charm_drop_chance;

    public static ForgeConfigSpec.ConfigValue<Double> WITHER_SHARD_DROP_CHANCE;
    public static float wither_shard_drop_chance;

    public static ForgeConfigSpec.ConfigValue<Double> SHULKER_HEART_DROP_CHANCE;
    public static float shulker_heart_drop_chance;

    public static ForgeConfigSpec.ConfigValue<Double> ENDERIAN_SCARF_DROP_CHANCE;
    public static float enderian_scarf_drop_chance;

    public static ForgeConfigSpec.ConfigValue<Double> SHACKLE_DROP_CHANCE;
    public static float shackle_drop_chance;

    public static ForgeConfigSpec.ConfigValue<Double> MAGIC_QUIVER_SKELETON_DROP_CHANCE;
    public static float magic_quiver_skeleton_drop_chance;

    public static ForgeConfigSpec.ConfigValue<Double> MAGIC_QUIVER_PILLAGER_DROP_CHANCE;
    public static float magic_quiver_pillager_drop_chance;

    public static ForgeConfigSpec.ConfigValue<Double> ENDER_DRAGON_CLAW_DROP_CHANCE;
    public static float ender_dragon_claw_drop_chance;

    public static ForgeConfigSpec.ConfigValue<Double> SCULK_LENS_DROP_CHANCE;
    public static float sculk_lens_drop_chance;

    public static ForgeConfigSpec.ConfigValue<Double> RECALL_POTION_DROP_CHANCE;
    public static float recall_potion_drop_chance;

    public static ForgeConfigSpec.ConfigValue<Double> COBALT_SHIELD_DROP_CHANCE;
    public static float cobalt_shield_drop_chance;

    public static ForgeConfigSpec.ConfigValue<Double> MELODY_PLUSHIE_DROP_CHANCE;
    public static float melody_plushie_drop_chance;

    // General
    public static ForgeConfigSpec.ConfigValue<Double> MAX_DAMAGE_REDUCTION;
    public static float max_damage_reduction;
    public static ForgeConfigSpec.ConfigValue<Double> MAX_FIRE_DAMAGE_REDUCTION;
    public static float max_fire_damage_reduction;
    public static ForgeConfigSpec.ConfigValue<Double> MAX_DAMAGE_INCREASE;
    public static float max_damage_increase;
    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_DEBUG;
    public static boolean enable_debug;
    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_ENDER_TWEAKS;
    public static boolean enable_ender_tweaks;

    @SubscribeEvent
    static void onLoad(ModConfigEvent.Loading configEvent) {
        if (configEvent.getConfig().getFileName().equals(FILENAME)) {
            ignore_damage_chance = IGNORE_DAMAGE_CHANCE.get();
            explosion_damage_reduction = EXPLOSION_DAMAGE_REDUCTION.get().floatValue();
            hero_shield_res_level = HERO_SHIELD_RES_LEVEL.get();
            hero_shield_compat = HERO_SHIELD_COMPAT.get();
            skull_fire_damage_reduction = SKULL_FIRE_DAMAGE_REDUCTION.get().floatValue();
            obsidian_shield_fire_damage_reduction = ANKH_SHIELD_FIRE_DAMAGE_REDUCTION.get().floatValue();
            ankh_charm_additional_effects = ANKH_CHARM_ADDITIONAL_EFFECTS.get();
            ankh_shield_fire_damage_reduction = ANKH_SHIELD_FIRE_DAMAGE_REDUCTION.get().floatValue();
            ankh_shield_additional_effects = ANKH_SHIELD_ADDITIONAL_EFFECTS.get();
            decay_amulet_wither_chance = DECAY_AMULET_WITHER_CHANCE.get().floatValue();
            decay_amulet_wither_duration = DECAY_AMULET_WITHER_DURATION.get();
            decay_amulet_wither_level = DECAY_AMULET_WITHER_LEVEL.get();
            venom_amulet_poison_chance = VENOM_AMULET_POISON_CHANCE.get().floatValue();
            venom_amulet_poison_duration = VENOM_AMULET_POISON_DURATION.get();
            venom_amulet_poison_level = VENOM_AMULET_POISON_LEVEL.get();
            spectre_amulet_heal_chance = SPECTRE_AMULET_HEAL_CHANCE.get().floatValue();
            spectre_amulet_heal_amount = SPECTRE_AMULET_HEAL_AMOUNT.get().floatValue();
            necroplasm_amulet_heal_chance = NECROPLASM_AMULET_HEAL_CHANCE.get().floatValue();
            necroplasm_amulet_heal_amount = NECROPLASM_AMULET_HEAL_AMOUNT.get().floatValue();
            necroplasm_amulet_poison_chance = NECROPLASM_AMULET_POISON_CHANCE.get().floatValue();
            necroplasm_amulet_poison_level = NECROPLASM_AMULET_POISON_LEVEL.get();
            necroplasm_amulet_poison_duration = NECROPLASM_AMULET_POISON_DURATION.get();
            necroplasm_amulet_wither_chance = NECROPLASM_AMULET_WITHER_CHANCE.get().floatValue();
            necroplasm_amulet_wither_level = NECROPLASM_AMULET_WITHER_LEVEL.get();
            necroplasm_amulet_wither_duration = MECHANICAL_GLOVE_DAMAGE.get();
            mechanical_glove_damage = MECHANICAL_GLOVE_DAMAGE.get();
            shackle_armor = SHACKLE_ARMOR.get();
            plushie_health = PLUSHIE_HEALTH.get().floatValue();
            plushie_health_boost_level = PLUSHIE_HEALTH_BOOST_LEVEL.get();
            plushie_duration = PLUSHIE_DURATION.get();
            plushie_regen_level = PLUSHIE_REGEN_LEVEL.get();
            enderian_damage_reduction = ENDERIAN_DAMAGE_REDUCTION.get().floatValue();
            true_enderian_damage_reduction = TRUE_ENDERIAN_DAMAGE_REDUCTION.get().floatValue();
            true_enderian_evade = TRUE_ENDERIAN_EVADE.get().floatValue();
            true_enderian_reach = TRUE_ENDERIAN_REACH.get().floatValue();
            true_enderian_compat = TRUE_ENDERIAN_COMPAT.get();
            gilded_damage_reduction = GILDED_DAMAGE_REDUCTION.get().floatValue();
            gilded_damage_dealt = GILDED_DAMAGE_DEALT.get().floatValue();
            emerald_ring_damage = EMERALD_RING_DAMAGE.get().floatValue();
            emerald_ring_emeralds = EMERALD_RING_EMERALDS.get().floatValue();
            emerald_ring_luck = EMERALD_RING_LUCK.get();
            magic_quiver_damage = MAGIC_QUIVER_DAMAGE.get().floatValue();
            magic_quiver_ammo = MAGIC_QUIVER_AMMO.get().floatValue();
            envenomed_quiver_damage = ENVENOMED_QUIVER_DAMAGE.get().floatValue();
            envenomed_quiver_poison_level = ENVENOMED_QUIVER_POISON_LEVEL.get();
            envenomed_quiver_poison_duration = ENVENOMED_QUIVER_POISON_DURATION.get();
            envenomed_quiver_wither_level = ENVENOMED_QUIVER_WITHER_LEVEL.get();
            envenomed_quiver_wither_duration = ENVENOMED_QUIVER_WITHER_DURATION.get();
            envenomed_quiver_ammo = ENVENOMED_QUIVER_AMMO.get().floatValue();
            molten_quiver_damage = MOLTEN_QUIVER_DAMAGE.get().floatValue();
            molten_quiver_onfire_damage = MOLTEN_QUIVER_ONFIRE_DAMAGE.get().floatValue();
            molten_quiver_duration = MOLTEN_QUIVER_DURATION.get();
            molten_quiver_ammo = MOLTEN_QUIVER_AMMO.get().floatValue();
            wooden_headgear_armor = WOODEN_HEADGEAR_ARMOR.get().floatValue();
            wooden_headgear_arrow_damage_taken = WOODEN_HEADGEAR_ARROW_DAMAGE_TAKEN.get().floatValue();
            golden_headgear_armor = GOLDEN_HEADGEAR_ARMOR.get().floatValue();
            golden_headgear_arrow_damage_taken = GOLDEN_HEADGEAR_ARROW_DAMAGE_TAKEN.get().floatValue();
            netherite_headgear_armor = NETHERITE_HEADGEAR_ARMOR.get().floatValue();
            netherite_headgear_arrow_damage_taken = NETHERITE_HEADGEAR_ARROW_DAMAGE_TAKEN.get().floatValue();
            ender_dragon_claw_damage = ENDER_DRAGON_CLAW_DAMAGE.get().floatValue();
            ender_dragon_claw_chance = ENDER_DRAGON_CLAW_CHANCE.get().floatValue();
            enderian_eye_cooldown = ENDERIAN_EYE_COOLDOWN.get().floatValue();
            enderian_eye_radius = ENDERIAN_EYE_RADIUS.get();
            mechanical_claw_damage = MECHANICAL_CLAW_DAMAGE.get().floatValue();
            mechanical_claw_bleed_chance = MECHANICAL_CLAW_BLEED_CHANCE.get().floatValue();
            mechanical_claw_bleed_damage = MECHANICAL_CLAW_BLEED_DAMAGE.get();
            mechanical_claw_bleed_duration = MECHANICAL_CLAW_BLEED_DURATION.get();
            bleed_blacklisted_entities = BLEED_BLACKLISTED_ENTITIES.get();
            echo_glove_damage = ECHO_GLOVE_DAMAGE.get().floatValue();
            echo_glove_attack_speed = ECHO_GLOVE_ATTACK_SPEED.get().floatValue();
            echo_glove_ignore_chance = ECHO_GLOVE_ATTACK_SPEED.get().floatValue();
            venom_stone_damage = VENOM_STONE_DAMAGE.get().floatValue();
            venom_stone_chance = VENOM_STONE_CHANCE.get().floatValue();
            venom_stone_level = VENOM_STONE_LEVEL.get();
            venom_stone_duration = VENOM_STONE_DURATION.get();
            venom_stone_deadly_chance = VENOM_STONE_DEADLY_CHANCE.get().floatValue();
            decay_stone_damage = DECAY_STONE_DAMAGE.get().floatValue();
            decay_stone_chance = DECAY_STONE_CHANCE.get().floatValue();
            decay_stone_level = DECAY_STONE_LEVEL.get();
            decay_stone_duration = DECAY_STONE_DURATION.get();
            decay_stone_heal_amount = DECAY_STONE_HEAL_AMOUNT.get();
            fire_stone_damage = FIRE_STONE_DAMAGE.get().floatValue();
            fire_stone_chance = FIRE_STONE_CHANCE.get().floatValue();
            fire_stone_duration = FIRE_STONE_DURATION.get();
            ice_stone_damage = ICE_STONE_DAMAGE.get().floatValue();
            ice_stone_chance = ICE_STONE_CHANCE.get().floatValue();
            ice_stone_duration = ICE_STONE_DURATION.get();
            ice_stone_compat = ICE_STONE_COMPAT.get();
            ice_stone_encased_chance = ICE_STONE_ENCASED_CHANCE.get().floatValue();
            ice_stone_encased_duration = ICE_STONE_ENCASED_DURATION.get();
            recall_potion_use_time = RECALL_POTION_USE_TIME.get();
            recall_potion_cooldown = RECALL_POTION_COOLDOWN.get();
            recall_potion_interdimensional = RECALL_POTION_INTERDIMENSIONAL.get();
            recall_potion_glow = RECALL_POTION_GLOW.get();
            recall_potion_ingredient = RECALL_POTION_INGREDIENT.get();
            vanir_mask_damage_increase = VANIR_MASK_DAMAGE_INCREASE.get().floatValue();
            vanir_mask_health_increase = VANIR_MASK_HEALTH_INCREASE.get().floatValue();
            vanir_mask_speed_increase = VANIR_MASK_SPEED_INCREASE.get().floatValue();
            vanir_mask_armor_increase = VANIR_MASK_ARMOR_INCREASE.get().floatValue();
            vanir_mask_armor_toughness_increase = VANIR_MASK_ARMOR_TOUGHNESS_INCREASE.get().floatValue();
            ruby_ring_damage_increase = RUBY_RING_DAMAGE_INCREASE.get().floatValue();
            ruby_ring_health_threshold = RUBY_RING_HEALTH_THRESHOLD.get().floatValue();
            ruby_ring_health_cap = RUBY_RING_HEALTH_CAP.get().floatValue();
            tainted_mirror_player_damage = TAINTED_MIRROR_PLAYER_DAMAGE.get();
            natures_mantle_speed_increase = NATURES_MANTLE_SPEED_INCREASE.get().floatValue();
            ender_dragon_eye_summon_chance = ENDER_DRAGON_EYE_SUMMON_CHANCE.get().floatValue();
            ender_dragon_eye_follow_distance = ENDER_DRAGON_EYE_FOLLOW_DISTANCE.get().floatValue();
            ender_dragon_eye_compat = ENDER_DRAGON_EYE_COMPAT.get();
            leather_treads_speed = LEATHER_TREADS_SPEED.get().floatValue();
            dune_treads_speed = DUNE_TREADS_SPEED.get().floatValue();
            dune_treads_sand_speed = DUNE_TREADS_SAND_SPEED.get().floatValue();
            high_jumpers_speed = HIGH_JUMPERS_SPEED.get().floatValue();
            mechanical_gears_speed = MECHANICAL_GEARS_SPEED.get().floatValue();
            mechanical_gears_dodge_chance = MECHANICAL_GEARS_DODGE_CHANCE.get().floatValue();
            blazing_treads_speed = BLAZING_TREADS_SPEED.get().floatValue();
            enderian_treads_speed = ENDERIAN_TREADS_SPEED.get().floatValue();
            enderian_treads_cooldown = ENDERIAN_TREADS_COOLDOWN.get().floatValue();
            sculk_treads_speed = SCULK_TREADS_SPEED.get().floatValue();
            bezoar_drop_chance = BEZOAR_DROP_CHANCE.get().floatValue();
            vitamins_drop_chance = VITAMINS_DROP_CHANCE.get().floatValue();
            fast_clock_drop_chance = FAST_CLOCK_DROP_CHANCE.get().floatValue();
            desert_charm_drop_chance = DESERT_CHARM_DROP_CHANCE.get().floatValue();
            wither_shard_drop_chance = WITHER_SHARD_DROP_CHANCE.get().floatValue();
            shulker_heart_drop_chance = SHULKER_HEART_DROP_CHANCE.get().floatValue();
            enderian_scarf_drop_chance = ENDERIAN_SCARF_DROP_CHANCE.get().floatValue();
            shackle_drop_chance = SHACKLE_DROP_CHANCE.get().floatValue();
            magic_quiver_skeleton_drop_chance = MAGIC_QUIVER_SKELETON_DROP_CHANCE.get().floatValue();
            magic_quiver_pillager_drop_chance = MAGIC_QUIVER_PILLAGER_DROP_CHANCE.get().floatValue();
            ender_dragon_claw_drop_chance = ENDER_DRAGON_CLAW_DROP_CHANCE.get().floatValue();
            sculk_lens_drop_chance = SCULK_LENS_DROP_CHANCE.get().floatValue();
            recall_potion_drop_chance = RECALL_POTION_DROP_CHANCE.get().floatValue();
            cobalt_shield_drop_chance = COBALT_SHIELD_DROP_CHANCE.get().floatValue();
            melody_plushie_drop_chance = MELODY_PLUSHIE_DROP_CHANCE.get().floatValue();
            max_damage_reduction = MAX_DAMAGE_REDUCTION.get().floatValue();
            max_fire_damage_reduction = MAX_FIRE_DAMAGE_REDUCTION.get().floatValue();
            max_damage_increase = MAX_DAMAGE_INCREASE.get().floatValue();
            enable_debug = ENABLE_DEBUG.get();
            enable_ender_tweaks = ENABLE_ENDER_TWEAKS.get();
        }
    }

    static {

        // General Category
        BUILDER.push("General");
        MAX_DAMAGE_REDUCTION = BUILDER.comment("Global max damage reduction from artifacts in percentage").defineInRange("Max_Damage_Reduction", 1.0, 0.01, 1.0);
        MAX_FIRE_DAMAGE_REDUCTION = BUILDER.comment("Global max fire damage reduction from artifacts in percentage").defineInRange("Max_Fire_Damage_Reduction", 1.0, 0.01, 1.0);
        MAX_DAMAGE_INCREASE = BUILDER.comment("Global max damage increase from artifacts in percentage").defineInRange("Max_Damage_Increase", 2.0, 0.01, 2.0);
        ENABLE_DEBUG = BUILDER.comment("Enable debugging").define("Debug", false);
        ENABLE_ENDER_TWEAKS = BUILDER.comment("Stop the endermen from being able to target or be hurt by the ender dragon/dragon breath").define("Ender_Tweaks", true);
        BUILDER.pop();

        // Artifact Category
        BUILDER.push("Artifacts");
        //

        BUILDER.push("Hero_Shield");
        IGNORE_DAMAGE_CHANCE = BUILDER.comment("Amount of hits taken until damage is ignored").define("Hits", 5);
        EXPLOSION_DAMAGE_REDUCTION = BUILDER.comment("Explosion damage reduction in percentage").define("Explosion_Damage_Reduction", 0.75);
        HERO_SHIELD_RES_LEVEL = BUILDER.comment("Level of resistance hero shield provides").define("Level", 1);
        HERO_SHIELD_COMPAT = BUILDER.comment("Enable compat to disable explosion overlay from enhanced visuals").define("EnhancedVisuals_Compat", true);
        BUILDER.pop();

        BUILDER.push("Obsidian_Skull");
        SKULL_FIRE_DAMAGE_REDUCTION = BUILDER.comment("Fire damage reduction in percentage").define("Fire_Damage_Reduction", 0.50);
        BUILDER.pop();

        BUILDER.push("Obsidian_Shield");
        OBSIDIAN_SHIELD_FIRE_DAMAGE_REDUCTION = BUILDER.comment("Fire damage reduction in percentage").define("Fire_Damage_Reduction", 0.50);
        BUILDER.pop();

        BUILDER.push("Ankh_Charm");
        ANKH_CHARM_ADDITIONAL_EFFECTS = BUILDER.comment("List of additional effects the ankh charm grants immunity to (e.g., minecraft:glowing, minecraft:slow_falling etc)").defineList("Additional_Effects", List.of("moreartifacts:virulent"), s -> s instanceof String);
        BUILDER.pop();

        BUILDER.push("Ankh_Shield");
        ANKH_SHIELD_FIRE_DAMAGE_REDUCTION = BUILDER.comment("Fire damage reduction in percentage").define("Fire_Damage_Reduction", 0.50);
        ANKH_SHIELD_ADDITIONAL_EFFECTS = BUILDER.comment("List of additional effects the ankh shield grants immunity to (e.g., minecraft:glowing, minecraft:slow_falling etc)").defineList("Additional_Effects", List.of("moreartifacts:virulent"), s -> s instanceof String);
        BUILDER.pop();

        BUILDER.push("Venom_Amulet");
        VENOM_AMULET_POISON_CHANCE = BUILDER.comment("Chance to apply poison").define("Poison_Chance", 0.40);
        VENOM_AMULET_POISON_LEVEL = BUILDER.comment("Level of poison inflicted").defineInRange("Poison_Level", 1, 1, 5);
        VENOM_AMULET_POISON_DURATION = BUILDER.comment("Duration of the poison effect in seconds").define("Duration", 5);
        BUILDER.pop();

        BUILDER.push("Decay_Amulet");
        DECAY_AMULET_WITHER_CHANCE = BUILDER.comment("Chance to apply wither").define("Wither_Chance", 0.40);
        DECAY_AMULET_WITHER_LEVEL = BUILDER.comment("Level of wither inflicted").defineInRange("Wither_Level", 1, 1, 5);
        DECAY_AMULET_WITHER_DURATION = BUILDER.comment("Duration of the wither effect in seconds").define("Duration", 5);
        BUILDER.pop();

        BUILDER.push("Spectre_Amulet");
        SPECTRE_AMULET_HEAL_CHANCE = BUILDER.comment("Chance to heal").define("Heal_Chance", 0.40);
        SPECTRE_AMULET_HEAL_AMOUNT = BUILDER.comment("Amount of health gained from attacking").define("Heal_Amount", 2.0);
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
        BUILDER.pop();

        BUILDER.push("Mechanical_Glove");
        MECHANICAL_GLOVE_DAMAGE= BUILDER.comment("Attack Damage increase").define("Attack_Damage", 2);
        BUILDER.pop();

        BUILDER.push("Mechanical_Claw");
        MECHANICAL_CLAW_DAMAGE = BUILDER.comment("Damage increased in percentage").define("Damage_Increase", 0.15);
        MECHANICAL_CLAW_BLEED_CHANCE = BUILDER.comment("Chance to inflict bleed onto hit enemies").define("Bleed_Chance", 0.25);
        MECHANICAL_CLAW_BLEED_DAMAGE = BUILDER.comment("Damage that bleed deals per second").define("Bleed_Damage", 1);
        MECHANICAL_CLAW_BLEED_DURATION = BUILDER.comment("Duration of bleed in seconds").define("Bleed_Duration", 8);
        BLEED_BLACKLISTED_ENTITIES = BUILDER.comment("Blacklist for entities that cannot be affected by bleed").defineList("Blacklisted_Entities",List.of(), s -> s instanceof String);
        BUILDER.pop();

        BUILDER.push("Echo_Glove");
        ECHO_GLOVE_DAMAGE = BUILDER.comment("Damage increased in percentage").define("Damage_Increase", 0.05);
        ECHO_GLOVE_ATTACK_SPEED = BUILDER.comment("Attack speed increased in percentage").define("Attack_Speed_Increase", 0.15);
        ECHO_GLOVE_IGNORE_CHANCE = BUILDER.comment("Chance to ignore invulnerability frames on hit").define("Ignore_Chance", 0.10);
        BUILDER.pop();

        BUILDER.push("Shackle");
        SHACKLE_ARMOR = BUILDER.comment("Armor increase").define("Armor", 1);
        BUILDER.pop();

        BUILDER.push("Melody_Plushie");
        PLUSHIE_HEALTH = BUILDER.comment("Max Health Increase percentage").define("Max_Health", 0.2);
        PLUSHIE_HEALTH_BOOST_LEVEL = BUILDER.comment("Level of health boost from waking up").define("Health_Boost_Level", 2);
        PLUSHIE_DURATION = BUILDER.comment("Duration of health boost in seconds").define("Duration", 240);
        PLUSHIE_REGEN_LEVEL = BUILDER.comment("Level regeneration melody plushie provides").define("Regen_Level", 1);
        BUILDER.pop();

        BUILDER.push("Enderian_Scarf");
        ENDERIAN_DAMAGE_REDUCTION = BUILDER.comment("Damage reduction in percentage").define("Damage_Reduction", 0.08);
        BUILDER.pop();

        BUILDER.push("True_Enderian_Scarf");
        TRUE_ENDERIAN_DAMAGE_REDUCTION = BUILDER.comment("Damage reduction in percentage").define("Damage_Reduction", 0.10);
        TRUE_ENDERIAN_EVADE = BUILDER.comment("Chance to evade an attack").define("Evade_Chance", 0.10);
        TRUE_ENDERIAN_REACH = BUILDER.comment("Amount of reach gain").define("Reach_Gain", 1.0);
        TRUE_ENDERIAN_COMPAT = BUILDER.comment("Enable compat to disable endermen static from enhanced visuals").define("EnhancedVisuals_Compat", true);
        BUILDER.pop();

        BUILDER.push("Gilded_Scarf");
        GILDED_DAMAGE_REDUCTION = BUILDER.comment("Damage reduction in percentage").define("Damage_Reduction", 0.12);
        GILDED_DAMAGE_DEALT = BUILDER.comment("Increased damage dealt in percentage").define("Damage_Dealt", 1.10);
        BUILDER.pop();

        BUILDER.push("Lucky_Emerald_Ring");
        EMERALD_RING_DAMAGE = BUILDER.comment("Increased damage dealt against illagers in percentage").define("Damage_Dealt", 1.25);
        EMERALD_RING_EMERALDS = BUILDER.comment("Chance for on hit to drop an emerald").define("Emerald_Chance", 0.05);
        EMERALD_RING_LUCK = BUILDER.comment("Amount of increased luck").define("Luck_Increase", 1);
        BUILDER.pop();

        BUILDER.push("Magic_Quiver");
        MAGIC_QUIVER_DAMAGE = BUILDER.comment("Increased arrow damage dealt in percentage").define("Arrow_Damage", 1.10);
        MAGIC_QUIVER_AMMO = BUILDER.comment("Chance to not consume arrows in percentage").define("Save_Arrow_Chance", 0.20);
        BUILDER.pop();

        BUILDER.push("Envenomed_Quiver");
        ENVENOMED_QUIVER_DAMAGE = BUILDER.comment("Increased arrow damage dealt in percentage").define("Arrow_Damage", 1.15);
        ENVENOMED_QUIVER_POISON_LEVEL = BUILDER.comment("Level of poison inflicted").defineInRange("Poison_Level", 1, 1, 5);
        ENVENOMED_QUIVER_POISON_DURATION = BUILDER.comment("Duration of the poison effect in seconds").define("Poison_Duration", 5);
        ENVENOMED_QUIVER_WITHER_LEVEL = BUILDER.comment("Level of poison inflicted").defineInRange("Wither_Level", 1, 1, 5);
        ENVENOMED_QUIVER_WITHER_DURATION = BUILDER.comment("Duration of the wither effect in seconds").define("Wither_Duration", 5);
        ENVENOMED_QUIVER_AMMO = BUILDER.comment("Chance to not consume arrows in percentage").define("Save_Arrow_Chance", 0.20);
        BUILDER.pop();

        BUILDER.push("Molten_Quiver");
        MOLTEN_QUIVER_DAMAGE = BUILDER.comment("Increased arrow damage dealt in percentage").define("Arrow_Damage", 1.15);
        MOLTEN_QUIVER_ONFIRE_DAMAGE = BUILDER.comment("Increased on fire bonus arrow damage in percentage").define("On_Fire_Bonus", 1.05);
        MOLTEN_QUIVER_DURATION = BUILDER.comment("Duration of enemies on fire in seconds").define("Fire_Duration", 5);
        MOLTEN_QUIVER_AMMO = BUILDER.comment("Chance to not consume arrows in percentage").define("Save_Arrow_Chance", 0.20);
        BUILDER.pop();

        BUILDER.push("Wooden_Headgear");
        WOODEN_HEADGEAR_ARMOR = BUILDER.comment("Armor increase").define("Armor", 1.0);
        WOODEN_HEADGEAR_ARROW_DAMAGE_TAKEN = BUILDER.comment("Damage taken in percentage").define("Damage_Taken", 0.95);
        BUILDER.pop();

        BUILDER.push("Golden_Headgear");
        GOLDEN_HEADGEAR_ARMOR = BUILDER.comment("Armor increase").define("Armor", 2.0);
        GOLDEN_HEADGEAR_ARROW_DAMAGE_TAKEN = BUILDER.comment("Damage taken in percentage").define("Damage_Taken", 0.90);
        BUILDER.pop();

        BUILDER.push("Netherite_Headgear");
        NETHERITE_HEADGEAR_ARMOR = BUILDER.comment("Armor increase").define("Armor", 3.0);
        NETHERITE_HEADGEAR_ARROW_DAMAGE_TAKEN = BUILDER.comment("Damage taken in percentage").define("Damage_Taken", 0.85);
        BUILDER.pop();

        BUILDER.push("Ender_Dragon_Claw");
        ENDER_DRAGON_CLAW_DAMAGE = BUILDER.comment("Increased damage dealt in percentage").define("Damage_Dealt", 1.50);
        ENDER_DRAGON_CLAW_CHANCE = BUILDER.comment("Chance to increase damage dealt").define("Chance", 0.05);
        BUILDER.pop();

        BUILDER.push("Enderian_Eye");
        ENDERIAN_EYE_RADIUS = BUILDER.comment("Max teleportation radius").define("Radius", 25);
        ENDERIAN_EYE_COOLDOWN = BUILDER.comment("Cooldown for teleporting").define("Cooldown", 5.0);
        BUILDER.pop();

        BUILDER.push("Venom_Stone");
        VENOM_STONE_DAMAGE = BUILDER.comment("Increased damage dealt in percentage").define("Damage_Dealt", 1.20);
        VENOM_STONE_CHANCE = BUILDER.comment("Chance to inflict poison").define("Chance", 0.25);
        VENOM_STONE_LEVEL = BUILDER.comment("Level of the poison effect").defineInRange("Poison_Level", 2, 1, 5);
        VENOM_STONE_DURATION = BUILDER.comment("Duration of the poison effect in seconds").define("Duration", 5);
        VENOM_STONE_DEADLY_CHANCE = BUILDER.comment("Chance to convert poison into a more deadly poison").define("Deadly_Chance", 0.15);
        BUILDER.pop();

        BUILDER.push("Decay_Stone");
        DECAY_STONE_DAMAGE = BUILDER.comment("Increased damage dealt in percentage").define("Damage_Dealt", 1.20);
        DECAY_STONE_CHANCE = BUILDER.comment("Chance to inflict wither").define("Chance", 0.25);
        DECAY_STONE_LEVEL = BUILDER.comment("Level of the wither effect").defineInRange("Wither_Level", 3, 1, 5);
        DECAY_STONE_DURATION = BUILDER.comment("Duration of the wither effect in seconds").define("Duration", 3);
        DECAY_STONE_HEAL_AMOUNT = BUILDER.comment("Amount of hearts healed by the decay stone").define("Hearts", 1);
        BUILDER.pop();

        BUILDER.push("Fire_Stone");
        FIRE_STONE_DAMAGE = BUILDER.comment("Increased damage dealt in percentage").define("Damage_Dealt", 1.30);
        FIRE_STONE_CHANCE = BUILDER.comment("Chance to set enemies ablaze").define("Chance", 0.25);
        FIRE_STONE_DURATION = BUILDER.comment("Duration of enemies on fire in seconds").define("Duration", 3);
        BUILDER.pop();

        BUILDER.push("Ice_Stone");
        ICE_STONE_DAMAGE = BUILDER.comment("Increased damage dealt in percentage").define("Damage_Dealt", 1.10);
        ICE_STONE_CHANCE = BUILDER.comment("Chance to freeze enemies").define("Chance", 0.25);
        ICE_STONE_DURATION = BUILDER.comment("Duration of enemies frozen in seconds").define("Duration", 8);
        ICE_STONE_COMPAT= BUILDER.comment("Enable compat to use ice and fire's freezing mechanic").define("IceandFire_Compat", true);
        ICE_STONE_ENCASED_CHANCE= BUILDER.comment("Chance to encase enemies in ice (iceandfire)").define("Encase_Chance", 0.10);
        ICE_STONE_ENCASED_DURATION = BUILDER.comment("Duration of how long enemies stay encased in ice for (iceandfire)").define("Encased_Duration", 5);
        BUILDER.pop();

        BUILDER.push("Recall_Potion");
        RECALL_POTION_USE_TIME = BUILDER.comment("Recall potion use time in ticks").define("Use_Time", 32);
        RECALL_POTION_COOLDOWN = BUILDER.comment("Recall potion cooldown in seconds").define("Cooldown", 0);
        RECALL_POTION_INTERDIMENSIONAL = BUILDER.comment("Can recall potion work from other dimensions").define("Interdimensional", false);
        RECALL_POTION_GLOW = BUILDER.comment("Does recall potion have enchantment glow").define("Glow", false);
        RECALL_POTION_INGREDIENT = BUILDER.comment("Main ingredient used to brew recall potions").define("Ingredient", "minecraft:ender_pearl");
        BUILDER.pop();

        BUILDER.push("Vanir_Mask");
        VANIR_MASK_DAMAGE_INCREASE = BUILDER.comment("Increased damage dealt to hit enemies").define("Damage_Increase", 2.0);
        VANIR_MASK_HEALTH_INCREASE = BUILDER.comment("Increased max health gained in percentage").define("Health_Increase", 0.10);
        VANIR_MASK_SPEED_INCREASE = BUILDER.comment("Increased speed gained in percentage").define("Speed_Increase", 0.25);
        VANIR_MASK_ARMOR_INCREASE = BUILDER.comment("Increased armor gained in percentage").define("Armor_Increase", 0.25);
        VANIR_MASK_ARMOR_TOUGHNESS_INCREASE = BUILDER.comment("Increased armor toughness gained in percentage").define("Armor_Toughness_Increase", 0.25);
        BUILDER.pop();

        BUILDER.push("Ruby_Ring");
        RUBY_RING_DAMAGE_INCREASE = BUILDER.comment("Damage increased for every health threshold in percentage").define("Damage_Increase", 0.05);
        RUBY_RING_HEALTH_THRESHOLD = BUILDER.comment("Health threshold for each damage increase").define("Health_Threshold", 20.0);
        RUBY_RING_HEALTH_CAP = BUILDER.comment("Max health cap for the damage increase").define("Health_Cap", 60.0);
        BUILDER.pop();

        BUILDER.push("Tainted_Mirror");
        TAINTED_MIRROR_PLAYER_DAMAGE = BUILDER.comment("Makes players take only half of the damage they deal by the mirror instead of full damage").define("Reduced_Player_Damage", true);
        BUILDER.pop();

        BUILDER.push("Natures_Mantle");
        NATURES_MANTLE_SPEED_INCREASE = BUILDER.comment("Increased speed gained in percentage").define("Speed_Increase", 0.10);
        BUILDER.pop();

        BUILDER.push("Ender_Dragon_Eye");
        ENDER_DRAGON_EYE_SUMMON_CHANCE = BUILDER.comment("Chance to summon endermen to fight for you when hit in percentage").define("Summon_Chance", 0.01);
        ENDER_DRAGON_EYE_FOLLOW_DISTANCE = BUILDER.comment("Distance at which endermen can follow you from in blocks").define("Follow_Distance", 20.0);
        ENDER_DRAGON_EYE_COMPAT = BUILDER.comment("Enable compat to disable endermen static from enhanced visuals").define("EnhancedVisuals_Compat", true);
        BUILDER.pop();

        BUILDER.push("Leather_Treads");
        LEATHER_TREADS_SPEED = BUILDER.comment("Speed increase gained in percentage").define("Speed_Increase", 0.05);
        BUILDER.pop();

        BUILDER.push("Dune_Treads");
        DUNE_TREADS_SPEED = BUILDER.comment("Speed increase gained in percentage").define("Speed_Increase", 0.05);
        DUNE_TREADS_SAND_SPEED = BUILDER.comment("Speed increase when on sand gained in percentage").define("Sand_Speed_Increase", 0.10);
        BUILDER.pop();

        BUILDER.push("High_Jumpers");
        HIGH_JUMPERS_SPEED = BUILDER.comment("Speed increase gained in percentage").define("Speed_Increase", 0.10);
        BUILDER.pop();

        BUILDER.push("Mechanical_Gears");
        MECHANICAL_GEARS_SPEED = BUILDER.comment("Speed increase gained in percentage").define("Speed_Increase", 0.02);
        MECHANICAL_GEARS_DODGE_CHANCE = BUILDER.comment("Chance to dodge attacks in percentage").define("Dodge_Chance", 0.05);
        BUILDER.pop();

        BUILDER.push("Blazing_Treads");
        BLAZING_TREADS_SPEED = BUILDER.comment("Speed increase gained in percentage").define("Speed_Increase", 0.10);
        BUILDER.pop();

        BUILDER.push("Enderian_Treads");
        ENDERIAN_TREADS_SPEED = BUILDER.comment("Speed increase gained in percentage").define("Speed_Increase", 0.20);
        ENDERIAN_TREADS_COOLDOWN = BUILDER.comment("Cooldown for evading attacks in seconds").define("Cooldown", 60.0);
        BUILDER.pop();

        BUILDER.push("Sculk_Treads");
        SCULK_TREADS_SPEED = BUILDER.comment("Speed increase gained in percentage").define("Speed_Increase", 0.25);
        BUILDER.pop();

        // Artifact Category End
        BUILDER.pop();
        //

        // Loot Category
        BUILDER.push("Loot");
        //

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
        SHULKER_HEART_DROP_CHANCE = BUILDER.comment("Shulker Heart drop chance from shulkers in percentage").define("Drop_Chance", 0.15);
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
        BUILDER.pop();

        BUILDER.push("Ender_Dragon_Claw");
        ENDER_DRAGON_CLAW_DROP_CHANCE = BUILDER.comment("Ender Dragon Claw drop chance from the ender dragon in percentage").define("Drop_Chance", 0.25);
        BUILDER.pop();

        BUILDER.push("Melody_Plushie");
        MELODY_PLUSHIE_DROP_CHANCE = BUILDER.comment("Melody Plushie drop chance from cat morning gifts in percentage").define("Drop_Chance", 0.01);
        BUILDER.pop();

        BUILDER.push("Sculk_Lens");
        SCULK_LENS_DROP_CHANCE = BUILDER.comment("Sculk Lens loot chance from ancient cities in percentage").define("Loot_Chance", 0.05);
        BUILDER.pop();

        BUILDER.push("Recall_Potion");
        RECALL_POTION_DROP_CHANCE = BUILDER.comment("Recall Potion loot chance from simple dungeons in percentage").define("Loot_Chance", 0.25);
        BUILDER.pop();

        BUILDER.push("Cobalt_Shield");
        COBALT_SHIELD_DROP_CHANCE = BUILDER.comment("Cobalt Shield loot chance from simple dungeon in percentage").define("Loot_Chance", 0.10);
        BUILDER.pop();

        // Loot Category End
        BUILDER.pop();
        //

        SPEC = BUILDER.build();
    }
}