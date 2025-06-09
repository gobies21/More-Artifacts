package net.gobies.moreartifacts;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = MoreArtifacts.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static ForgeConfigSpec.ConfigValue<Double> SKULL_FIRE_DAMAGE_TAKEN;
    public static float skull_fire_damage_taken;

    public static ForgeConfigSpec.ConfigValue<Double> OBSIDIAN_SHIELD_FIRE_DAMAGE_TAKEN;
    public static float obsidian_shield_fire_damage_taken;

    public static ForgeConfigSpec.ConfigValue<Double> ANKH_SHIELD_FIRE_DAMAGE_TAKEN;
    public static float ankh_shield_fire_damage_taken;

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
    public static ForgeConfigSpec.ConfigValue<Double> EXPLOSION_DAMAGE_TAKEN;
    public static float explosion_damage_taken;
    public static ForgeConfigSpec.ConfigValue<Integer> HERO_SHIELD_RES_LEVEL;
    public static int hero_shield_res_level;

    public static ForgeConfigSpec.ConfigValue<Double> ENDERIAN_DAMAGE_TAKEN;
    public static float enderian_damage_taken;

    public static ForgeConfigSpec.ConfigValue<Double> TRUE_ENDERIAN_DAMAGE_TAKEN;
    public static float true_enderian_damage_taken;
    public static ForgeConfigSpec.ConfigValue<Double> TRUE_ENDERIAN_EVADE;
    public static float true_enderian_evade;
    public static ForgeConfigSpec.ConfigValue<Double> TRUE_ENDERIAN_REACH;
    public static float true_enderian_reach;
    public static ForgeConfigSpec.ConfigValue<Boolean> TRUE_ENDERIAN_COMPAT;
    public static boolean true_enderian_compat;

    public static ForgeConfigSpec.ConfigValue<Double> GILDED_DAMAGE_TAKEN;
    public static float gilded_damage_taken;
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

    public static ForgeConfigSpec.ConfigValue<Double> DECAY_STONE_DAMAGE;
    public static float decay_stone_damage;
    public static ForgeConfigSpec.ConfigValue<Double> DECAY_STONE_CHANCE;
    public static float decay_stone_chance;
    public static ForgeConfigSpec.ConfigValue<Integer> DECAY_STONE_LEVEL;
    public static int decay_stone_level;
    public static ForgeConfigSpec.ConfigValue<Integer> DECAY_STONE_DURATION;
    public static int decay_stone_duration;

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
    public static ForgeConfigSpec.ConfigValue<Double> VANIR_MASK_SPEED_INCREASE;
    public static float vanir_mask_speed_increase;
    public static ForgeConfigSpec.ConfigValue<Double> VANIR_MASK_ARMOR_INCREASE;
    public static float vanir_mask_armor_increase;
    public static ForgeConfigSpec.ConfigValue<Double> VANIR_MASK_ARMOR_TOUGHNESS_INCREASE;
    public static float vanir_mask_armor_toughness_increase;

    public Config() {
    }

    @SubscribeEvent
    static void onLoad(ModConfigEvent.Loading configEvent) {
        ignore_damage_chance = IGNORE_DAMAGE_CHANCE.get();
        explosion_damage_taken = (float) (EXPLOSION_DAMAGE_TAKEN.get() * (double) 1.0F);
        hero_shield_res_level = HERO_SHIELD_RES_LEVEL.get();
        skull_fire_damage_taken = (float) (SKULL_FIRE_DAMAGE_TAKEN.get() * (double) 1.0F);
        obsidian_shield_fire_damage_taken = (float) (ANKH_SHIELD_FIRE_DAMAGE_TAKEN.get() * (double) 1.0F);
        ankh_shield_fire_damage_taken = (float) (ANKH_SHIELD_FIRE_DAMAGE_TAKEN.get() * (double) 1.0F);
        decay_amulet_wither_chance = (float) (DECAY_AMULET_WITHER_CHANCE.get() * (double) 1.0F);
        decay_amulet_wither_duration = DECAY_AMULET_WITHER_DURATION.get();
        decay_amulet_wither_level = DECAY_AMULET_WITHER_LEVEL.get();
        venom_amulet_poison_chance = (float) (VENOM_AMULET_POISON_CHANCE.get() * (double) 1.0F);
        venom_amulet_poison_duration = VENOM_AMULET_POISON_DURATION.get();
        venom_amulet_poison_level = VENOM_AMULET_POISON_LEVEL.get();
        spectre_amulet_heal_chance = (float) (SPECTRE_AMULET_HEAL_CHANCE.get() * (double) 1.0F);
        spectre_amulet_heal_amount = (float) (SPECTRE_AMULET_HEAL_AMOUNT.get() * (double) 1.0F);
        necroplasm_amulet_heal_chance = (float) (NECROPLASM_AMULET_HEAL_CHANCE.get() * (double) 1.0F);
        necroplasm_amulet_heal_amount = (float) (NECROPLASM_AMULET_HEAL_AMOUNT.get() * (double) 1.0F);
        necroplasm_amulet_poison_chance = (float) (NECROPLASM_AMULET_POISON_CHANCE.get() * (double) 1.0F);
        necroplasm_amulet_poison_level = NECROPLASM_AMULET_POISON_LEVEL.get();
        necroplasm_amulet_poison_duration = NECROPLASM_AMULET_POISON_DURATION.get();
        necroplasm_amulet_wither_chance = (float) (NECROPLASM_AMULET_WITHER_CHANCE.get() * (double) 1.0F);
        necroplasm_amulet_wither_level = NECROPLASM_AMULET_WITHER_LEVEL.get();
        necroplasm_amulet_wither_duration = MECHANICAL_GLOVE_DAMAGE.get();
        mechanical_glove_damage = MECHANICAL_GLOVE_DAMAGE.get();
        shackle_armor = SHACKLE_ARMOR.get();
        plushie_health = (float) (PLUSHIE_HEALTH.get() * (double) 1.0F);
        plushie_health_boost_level = PLUSHIE_HEALTH_BOOST_LEVEL.get();
        plushie_duration = PLUSHIE_DURATION.get();
        plushie_regen_level = PLUSHIE_REGEN_LEVEL.get();
        enderian_damage_taken = (float) (ENDERIAN_DAMAGE_TAKEN.get() * (double) 1.0F);
        true_enderian_damage_taken = (float) (TRUE_ENDERIAN_DAMAGE_TAKEN.get() * (double) 1.0F);
        true_enderian_evade = (float) (TRUE_ENDERIAN_EVADE.get() * (double) 1.0F);
        true_enderian_reach = (float) (TRUE_ENDERIAN_REACH.get() * (double) 1.0F);
        true_enderian_compat = TRUE_ENDERIAN_COMPAT.get();
        gilded_damage_taken = (float) (GILDED_DAMAGE_TAKEN.get() * (double) 1.0F);
        gilded_damage_dealt = (float) (GILDED_DAMAGE_DEALT.get() * (double) 1.0F);
        emerald_ring_damage = (float) (EMERALD_RING_DAMAGE.get() * (double) 1.0F);
        emerald_ring_emeralds = (float) (EMERALD_RING_EMERALDS.get() * (double) 1.0F);
        magic_quiver_damage = (float) (MAGIC_QUIVER_DAMAGE.get() * (double) 1.0F);
        magic_quiver_ammo = (float) (MAGIC_QUIVER_AMMO.get() * (double) 1.0F);
        envenomed_quiver_damage = (float) (ENVENOMED_QUIVER_DAMAGE.get() * (double) 1.0F);
        envenomed_quiver_poison_level = ENVENOMED_QUIVER_POISON_LEVEL.get();
        envenomed_quiver_poison_duration = ENVENOMED_QUIVER_POISON_DURATION.get();
        envenomed_quiver_wither_level = ENVENOMED_QUIVER_WITHER_LEVEL.get();
        envenomed_quiver_wither_duration = ENVENOMED_QUIVER_WITHER_DURATION.get();
        envenomed_quiver_ammo = (float) (ENVENOMED_QUIVER_AMMO.get() * (double) 1.0F);
        molten_quiver_damage = (float) (MOLTEN_QUIVER_DAMAGE.get() * (double) 1.0F);
        molten_quiver_onfire_damage = (float) (MOLTEN_QUIVER_ONFIRE_DAMAGE.get() * (double) 1.0F);
        molten_quiver_duration = MOLTEN_QUIVER_DURATION.get();
        molten_quiver_ammo = (float) (MOLTEN_QUIVER_AMMO.get() * (double) 1.0F);
        wooden_headgear_armor = (float) ( WOODEN_HEADGEAR_ARMOR.get() * (double) 1.0F);
        wooden_headgear_arrow_damage_taken = (float) ( WOODEN_HEADGEAR_ARROW_DAMAGE_TAKEN.get() * (double) 1.0F);
        golden_headgear_armor = (float) (GOLDEN_HEADGEAR_ARMOR.get() * (double) 1.0F);
        golden_headgear_arrow_damage_taken = (float) (GOLDEN_HEADGEAR_ARROW_DAMAGE_TAKEN.get() * (double) 1.0F);
        netherite_headgear_armor = (float) (NETHERITE_HEADGEAR_ARMOR.get() * (double) 1.0F);
        netherite_headgear_arrow_damage_taken = (float) (NETHERITE_HEADGEAR_ARROW_DAMAGE_TAKEN.get() * (double) 1.0F);
        ender_dragon_claw_damage = (float) (ENDER_DRAGON_CLAW_DAMAGE.get() * (double) 1.0F);
        ender_dragon_claw_chance = (float) (ENDER_DRAGON_CLAW_CHANCE.get() * (double) 1.0F);
        enderian_eye_cooldown = (float) (ENDERIAN_EYE_COOLDOWN.get() * (double) 1.0F);
        enderian_eye_radius = ENDERIAN_EYE_RADIUS.get();
        mechanical_claw_damage = (float) (MECHANICAL_CLAW_DAMAGE.get() * (double) 1.0F);
        mechanical_claw_bleed_chance = (float) (MECHANICAL_CLAW_BLEED_CHANCE.get() * (double) 1.0F);
        mechanical_claw_bleed_damage = MECHANICAL_CLAW_BLEED_DAMAGE.get();
        mechanical_claw_bleed_duration = MECHANICAL_CLAW_BLEED_DURATION.get();
        echo_glove_damage = (float) (ECHO_GLOVE_DAMAGE.get() * (double) 1.0F);
        echo_glove_attack_speed = (float) (ECHO_GLOVE_ATTACK_SPEED.get() * (double) 1.0F);
        echo_glove_ignore_chance = (float) (ECHO_GLOVE_ATTACK_SPEED.get() * (double) 1.0F);
        venom_stone_damage = (float) (VENOM_STONE_DAMAGE.get() * (double) 1.0F);
        venom_stone_chance = (float) (VENOM_STONE_CHANCE.get() * (double) 1.0F);
        venom_stone_level = VENOM_STONE_LEVEL.get();
        venom_stone_duration = VENOM_STONE_DURATION.get();
        decay_stone_damage = (float) (DECAY_STONE_DAMAGE.get() * (double) 1.0F);
        decay_stone_chance = (float) (DECAY_STONE_CHANCE.get() * (double) 1.0F);
        decay_stone_level = DECAY_STONE_LEVEL.get();
        decay_stone_duration = DECAY_STONE_DURATION.get();
        fire_stone_damage = (float) (FIRE_STONE_DAMAGE.get() * (double) 1.0F);
        fire_stone_chance = (float) (FIRE_STONE_CHANCE.get() * (double) 1.0F);
        fire_stone_duration = FIRE_STONE_DURATION.get();
        ice_stone_damage = (float) (ICE_STONE_DAMAGE.get() * (double) 1.0F);
        ice_stone_chance = (float) (ICE_STONE_CHANCE.get() * (double) 1.0F);
        ice_stone_duration = ICE_STONE_DURATION.get();
        ice_stone_compat = ICE_STONE_COMPAT.get();
        ice_stone_encased_chance = (float) (ICE_STONE_ENCASED_CHANCE.get() * (double) 1.0F);
        ice_stone_encased_duration = ICE_STONE_ENCASED_DURATION.get();
        recall_potion_use_time = RECALL_POTION_USE_TIME.get();
        recall_potion_cooldown = RECALL_POTION_COOLDOWN.get();
        recall_potion_interdimensional = RECALL_POTION_INTERDIMENSIONAL.get();
        recall_potion_glow = RECALL_POTION_GLOW.get();
        recall_potion_ingredient = RECALL_POTION_INGREDIENT.get();
        vanir_mask_damage_increase = (float) (VANIR_MASK_DAMAGE_INCREASE.get() * (double) 1.0F);
        vanir_mask_health_increase = (float) (VANIR_MASK_HEALTH_INCREASE.get() * (double) 1.0F);
        vanir_mask_speed_increase = (float) (VANIR_MASK_SPEED_INCREASE.get() * (double) 1.0F);
        vanir_mask_armor_increase = (float) (VANIR_MASK_ARMOR_INCREASE.get() * (double) 1.0F);
        vanir_mask_armor_toughness_increase = (float) (VANIR_MASK_ARMOR_TOUGHNESS_INCREASE.get() * (double) 1.0F);

    }

    static {
        BUILDER.push("Hero_Shield");
        IGNORE_DAMAGE_CHANCE = BUILDER.comment("Amount of hits taken until damage is ignored").define("Hits", 5);
        EXPLOSION_DAMAGE_TAKEN = BUILDER.comment("Explosion damage taken in percentage").define("Explosion_Damage_Taken", 0.25);
        HERO_SHIELD_RES_LEVEL = BUILDER.comment("Level of resistance hero shield provides").define("Level", 1);
        BUILDER.pop();

        BUILDER.push("Obsidian_Skull");
        SKULL_FIRE_DAMAGE_TAKEN = BUILDER.comment("Fire damage taken in percentage").define("Fire_Damage_Taken", 0.50);
        BUILDER.pop();

        BUILDER.push("Obsidian_Shield");
        OBSIDIAN_SHIELD_FIRE_DAMAGE_TAKEN = BUILDER.comment("Fire damage taken in percentage").define("Fire_Damage_Taken", 0.50);
        BUILDER.pop();

        BUILDER.push("Ankh_Shield");
        ANKH_SHIELD_FIRE_DAMAGE_TAKEN = BUILDER.comment("Fire damage taken in percentage").define("Fire_Damage_Taken", 0.50);
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
        MECHANICAL_CLAW_BLEED_DURATION = BUILDER.comment("Duration of bleed in seconds").define("Bleed_Duration", 5);
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
        ENDERIAN_DAMAGE_TAKEN = BUILDER.comment("Damage taken in percentage").define("Damage_Taken", 0.92);
        BUILDER.pop();

        BUILDER.push("True_Enderian_Scarf");
        TRUE_ENDERIAN_DAMAGE_TAKEN = BUILDER.comment("Damage taken in percentage").define("Damage_Taken", 0.90);
        TRUE_ENDERIAN_EVADE = BUILDER.comment("Chance to evade an attack").define("Evade_Chance", 0.10);
        TRUE_ENDERIAN_REACH = BUILDER.comment("Amount of reach gain").define("Reach_Gain", 1.0);
        TRUE_ENDERIAN_COMPAT = BUILDER.comment("Enable compat to disable endermen static from enhanced visuals").define("EnhancedVisuals_Compat", true);
        BUILDER.pop();

        BUILDER.push("Gilded_Scarf");
        GILDED_DAMAGE_TAKEN = BUILDER.comment("Damage taken in percentage").define("Damage_Taken", 0.88);
        GILDED_DAMAGE_DEALT = BUILDER.comment("Increased damage dealt in percentage").define("Damage_Dealt", 1.10);
        BUILDER.pop();

        BUILDER.push("Lucky_Emerald_Ring");
        EMERALD_RING_DAMAGE = BUILDER.comment("Increased damage dealt against illagers in percentage").define("Damage_Dealt", 1.25);
        EMERALD_RING_EMERALDS = BUILDER.comment("Chance for on hit to drop an emerald").define("Emerald_Chance", 0.05);
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
        ENDER_DRAGON_CLAW_CHANCE = BUILDER.comment("Chance to increase damage dealt").define("Chance", 0.15);
        BUILDER.pop();

        BUILDER.push("Enderian_Eye");
        ENDERIAN_EYE_RADIUS = BUILDER.comment("Max teleportation radius").define("Radius", 25);
        ENDERIAN_EYE_COOLDOWN = BUILDER.comment("Cooldown for teleporting").define("Cooldown", 5.0);
        BUILDER.pop();

        BUILDER.push("Venom_Stone");
        VENOM_STONE_DAMAGE = BUILDER.comment("Increased damage dealt in percentage").define("Damage_Dealt", 1.20);
        VENOM_STONE_CHANCE = BUILDER.comment("Chance to inflict poison").define("Chance", 0.25);
        VENOM_STONE_LEVEL = BUILDER.comment("Level of the poison effect").defineInRange("Poison_Level", 2, 1, 5);
        VENOM_STONE_DURATION = BUILDER.comment("Duration of the poison effect in seconds").define("Duration", 3);
        BUILDER.pop();

        BUILDER.push("Decay_Stone");
        DECAY_STONE_DAMAGE = BUILDER.comment("Increased damage dealt in percentage").define("Damage_Dealt", 1.20);
        DECAY_STONE_CHANCE = BUILDER.comment("Chance to inflict wither").define("Chance", 0.25);
        DECAY_STONE_LEVEL = BUILDER.comment("Level of the wither effect").defineInRange("Wither_Level", 2, 1, 5);
        DECAY_STONE_DURATION = BUILDER.comment("Duration of the wither effect in seconds").define("Duration", 3);
        BUILDER.pop();

        BUILDER.push("Fire_Stone");
        FIRE_STONE_DAMAGE = BUILDER.comment("Increased damage dealt in percentage").define("Damage_Dealt", 1.30);
        FIRE_STONE_CHANCE = BUILDER.comment("Chance to set enemies ablaze").define("Chance", 0.25);
        FIRE_STONE_DURATION = BUILDER.comment("Duration of enemies on fire in seconds").define("Duration", 3);
        BUILDER.pop();

        BUILDER.push("Ice_Stone");
        ICE_STONE_DAMAGE = BUILDER.comment("Increased damage dealt in percentage").define("Damage_Dealt", 1.20);
        ICE_STONE_CHANCE = BUILDER.comment("Chance to freeze enemies").define("Chance", 0.25);
        ICE_STONE_DURATION = BUILDER.comment("Duration of enemies frozen in seconds").define("Duration", 6);
        ICE_STONE_COMPAT= BUILDER.comment("Enable compat to use ice and fire's freezing mechanic").define("IceandFire_Compat", true);
        ICE_STONE_ENCASED_CHANCE= BUILDER.comment("Chance to encase enemies in ice (iceandfire)").define("Encase_Chance", 0.10);
        ICE_STONE_ENCASED_DURATION = BUILDER.comment("Duration of how long enemies stay encased in ice for (iceandfire)").define("Encased_Duration", 5);
        BUILDER.pop();

        BUILDER.push("Recall_Potion");
        RECALL_POTION_USE_TIME = BUILDER.comment("Recall potion use time in ticks").define("Use_Time", 32);
        RECALL_POTION_COOLDOWN = BUILDER.comment("Recall potion cooldown in seconds").define("Cooldown", 0);
        RECALL_POTION_INTERDIMENSIONAL = BUILDER.comment("Can recall potion work from other dimensions").define("Interdimensional", false);
        RECALL_POTION_GLOW = BUILDER.comment("Does recall potion have enchantment glow").define("Glow", false);
        RECALL_POTION_INGREDIENT = BUILDER.comment("Main ingredient used to brew recall potions").define("Ingredient", "minecraft:ender_eye");
        BUILDER.pop();

        BUILDER.push("Vanir_Mask");
        VANIR_MASK_DAMAGE_INCREASE = BUILDER.comment("Increased damage dealt to hit enemies").define("Damage_Increase", 2.0);
        VANIR_MASK_HEALTH_INCREASE = BUILDER.comment("Increased max health gained in percentage").define("Health_Increase", 0.10);
        VANIR_MASK_SPEED_INCREASE = BUILDER.comment("Increased speed gained in percentage").define("Speed_Increase", 0.25);
        VANIR_MASK_ARMOR_INCREASE = BUILDER.comment("Increased armor gained in percentage").define("Armor_Increase", 0.25);
        VANIR_MASK_ARMOR_TOUGHNESS_INCREASE = BUILDER.comment("Increased armor toughness gained in percentage").define("Armor_Toughness_Increase", 0.25);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}