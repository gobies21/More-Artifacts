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

    public static ForgeConfigSpec.ConfigValue<Double> DECAY_WITHER_CHANCE;
    public static float decay_wither_chance;

    public static ForgeConfigSpec.ConfigValue<Double> VENOM_POISON_CHANCE;
    public static float venom_poison_chance;

    public static ForgeConfigSpec.ConfigValue<Double> SPECTRE_HEAL_CHANCE;
    public static float spectre_heal_chance;

    public static ForgeConfigSpec.ConfigValue<Double> NECROPLASM_HEAL_CHANCE;
    public static float necroplasm_heal_chance;
    public static ForgeConfigSpec.ConfigValue<Double> NECROPLASM_WITHER_CHANCE;
    public static float necroplasm_wither_chance;
    public static ForgeConfigSpec.ConfigValue<Double> NECROPLASM_POISON_CHANCE;
    public static float necroplasm_poison_chance;

    public static ForgeConfigSpec.ConfigValue<Double> MECHANICAL_ATTACK;
    public static float mechanical_attack;

    public static ForgeConfigSpec.ConfigValue<Double> SHACKLE_ARMOR;
    public static float shackle_armor;

    public static ForgeConfigSpec.ConfigValue<Double> IGNORE_DAMAGE_CHANCE;
    public static float ignore_damage_chance;
    public static ForgeConfigSpec.ConfigValue<Double> EXPLOSION_DAMAGE_TAKEN;
    public static float explosion_damage_taken;

    public static ForgeConfigSpec.ConfigValue<Double> ENDERIAN_DAMAGE_TAKEN;
    public static float enderian_damage_taken;

    public static ForgeConfigSpec.ConfigValue<Double> TRUE_ENDERIAN_DAMAGE_TAKEN;
    public static float true_enderian_damage_taken;
    public static ForgeConfigSpec.ConfigValue<Double> TRUE_ENDERIAN_EVADE;
    public static float true_enderian_evade;
    public static ForgeConfigSpec.ConfigValue<Double> TRUE_ENDERIAN_REACH;
    public static float true_enderian_reach;

    public static ForgeConfigSpec.ConfigValue<Double> GILDED_DAMAGE_TAKEN;
    public static float gilded_damage_taken;
    public static ForgeConfigSpec.ConfigValue<Double> GILDED_DAMAGE_DEALT;
    public static float gilded_damage_dealt;

    public static ForgeConfigSpec.ConfigValue<Double> PLUSHIE_HEALTH;
    public static float plushie_health;

    public static ForgeConfigSpec.ConfigValue<Double> EMERALD_RING_DAMAGE;
    public static float emerald_ring_damage;
    public static ForgeConfigSpec.ConfigValue<Double> EMERALD_RING_EMERALDS;
    public static float emerald_ring_emeralds;

    public static ForgeConfigSpec.ConfigValue<Double> MAGIC_QUIVER_DAMAGE;
    public static float magic_quiver_damage;

    public static ForgeConfigSpec.ConfigValue<Double> ENVENOMED_QUIVER_DAMAGE;
    public static float envenomed_quiver_damage;

    public static ForgeConfigSpec.ConfigValue<Double> MOLTEN_QUIVER_DAMAGE;
    public static float molten_quiver_damage;
    public static ForgeConfigSpec.ConfigValue<Double> MOLTEN_QUIVER_ONFIRE_DAMAGE;
    public static float molten_quiver_onfire_damage;

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

    public Config() {
    }

    @SubscribeEvent
    static void onLoad(ModConfigEvent.Loading configEvent) {
        ignore_damage_chance = (float) ((Double) IGNORE_DAMAGE_CHANCE.get() * (double) 1.0F);
        explosion_damage_taken = (float) ((Double) EXPLOSION_DAMAGE_TAKEN.get() * (double) 1.0F);
        skull_fire_damage_taken = (float) ((Double) SKULL_FIRE_DAMAGE_TAKEN.get() * (double) 1.0F);
        obsidian_shield_fire_damage_taken = (float) ((Double) ANKH_SHIELD_FIRE_DAMAGE_TAKEN.get() * (double) 1.0F);
        ankh_shield_fire_damage_taken = (float) ((Double) ANKH_SHIELD_FIRE_DAMAGE_TAKEN.get() * (double) 1.0F);
        decay_wither_chance = (float) ((Double) DECAY_WITHER_CHANCE.get() * (double) 1.0F);
        venom_poison_chance = (float) ((Double) VENOM_POISON_CHANCE.get() * (double) 1.0F);
        spectre_heal_chance = (float) ((Double) SPECTRE_HEAL_CHANCE.get() * (double) 1.0F);
        necroplasm_heal_chance = (float) ((Double) NECROPLASM_HEAL_CHANCE.get() * (double) 1.0F);
        necroplasm_wither_chance = (float) ((Double) NECROPLASM_WITHER_CHANCE.get() * (double) 1.0F);
        necroplasm_poison_chance = (float) ((Double) NECROPLASM_POISON_CHANCE.get() * (double) 1.0F);
        mechanical_attack = (float) ((Double) MECHANICAL_ATTACK.get() * (double) 1.0F);
        shackle_armor = (float) ((Double) SHACKLE_ARMOR.get() * (double) 1.0F);
        plushie_health = (float) ((Double) PLUSHIE_HEALTH.get() * (double) 1.0F);
        enderian_damage_taken = (float) ((Double) ENDERIAN_DAMAGE_TAKEN.get() * (double) 1.0F);
        true_enderian_damage_taken = (float) ((Double) TRUE_ENDERIAN_DAMAGE_TAKEN.get() * (double) 1.0F);
        true_enderian_evade = (float) ((Double) TRUE_ENDERIAN_EVADE.get() * (double) 1.0F);
        true_enderian_reach = (float) ((Double) TRUE_ENDERIAN_REACH.get() * (double) 1.0F);
        gilded_damage_taken = (float) ((Double) GILDED_DAMAGE_TAKEN.get() * (double) 1.0F);
        gilded_damage_dealt = (float) ((Double) GILDED_DAMAGE_DEALT.get() * (double) 1.0F);
        emerald_ring_damage = (float) ((Double) EMERALD_RING_DAMAGE.get() * (double) 1.0F);
        emerald_ring_emeralds = (float) ((Double) EMERALD_RING_EMERALDS.get() * (double) 1.0F);
        magic_quiver_damage = (float) ((Double) MAGIC_QUIVER_DAMAGE.get() * (double) 1.0F);
        envenomed_quiver_damage = (float) ((Double) ENVENOMED_QUIVER_DAMAGE.get() * (double) 1.0F);
        molten_quiver_damage = (float) ((Double) MOLTEN_QUIVER_DAMAGE.get() * (double) 1.0F);
        molten_quiver_onfire_damage = (float) ((Double) MOLTEN_QUIVER_ONFIRE_DAMAGE.get() * (double) 1.0F);
        wooden_headgear_armor = (float) ((Double) WOODEN_HEADGEAR_ARMOR.get() * (double) 1.0F);
        wooden_headgear_arrow_damage_taken = (float) ((Double) WOODEN_HEADGEAR_ARROW_DAMAGE_TAKEN.get() * (double) 1.0F);
        golden_headgear_armor = (float) ((Double) GOLDEN_HEADGEAR_ARMOR.get() * (double) 1.0F);
        golden_headgear_arrow_damage_taken = (float) ((Double) GOLDEN_HEADGEAR_ARROW_DAMAGE_TAKEN.get() * (double) 1.0F);
        netherite_headgear_armor = (float) ((Double) NETHERITE_HEADGEAR_ARMOR.get() * (double) 1.0F);
        netherite_headgear_arrow_damage_taken = (float) ((Double) NETHERITE_HEADGEAR_ARROW_DAMAGE_TAKEN.get() * (double) 1.0F);

    }

    static {
        BUILDER.push("Hero Shield");
        IGNORE_DAMAGE_CHANCE = BUILDER.comment("Chance that hero shield will ignore damage").define("Chance", 0.10);
        EXPLOSION_DAMAGE_TAKEN = BUILDER.comment("Explosion damage taken in percentage").define("Explosion Damage Taken", 0.25);
        BUILDER.pop();

        BUILDER.push("Obsidian Skull");
        SKULL_FIRE_DAMAGE_TAKEN = BUILDER.comment("Fire damage taken in percentage").define("Fire Damage Taken", 0.50);
        BUILDER.pop();

        BUILDER.push("Obsidian Shield");
        OBSIDIAN_SHIELD_FIRE_DAMAGE_TAKEN = BUILDER.comment("Fire damage taken in percentage").define("Fire Damage Taken", 0.50);
        BUILDER.pop();

        BUILDER.push("Ankh Shield");
        ANKH_SHIELD_FIRE_DAMAGE_TAKEN = BUILDER.comment("Fire damage taken in percentage").define("Fire Damage Taken", 0.50);
        BUILDER.pop();

        BUILDER.push("Venom amulet");
        VENOM_POISON_CHANCE = BUILDER.comment("Chance to apply posion").define("Poison Chance", 0.40);
        BUILDER.pop();

        BUILDER.push("Decay Amulet");
        DECAY_WITHER_CHANCE = BUILDER.comment("Chance to apply wither").define("Wither Chance", 0.40);
        BUILDER.pop();

        BUILDER.push("Spectre Amulet");
        SPECTRE_HEAL_CHANCE = BUILDER.comment("Chance to heal").define("Heal Chance", 0.40);
        BUILDER.pop();

        BUILDER.push("Necroplasm Amulet");
        NECROPLASM_HEAL_CHANCE = BUILDER.comment("Chance to heal").define("Heal Chance", 0.50);
        NECROPLASM_POISON_CHANCE = BUILDER.comment("Chance to apply poison").define("Poison Chance", 0.60);
        NECROPLASM_WITHER_CHANCE = BUILDER.comment("Chance to apply wither").define("Wither Chance", 0.60);
        BUILDER.pop();

        BUILDER.push("Mechanical Glove");
        MECHANICAL_ATTACK = BUILDER.comment("Attack Damage increase").define("Attack Damage", 2.0);
        BUILDER.pop();

        BUILDER.push("Shackle");
        SHACKLE_ARMOR = BUILDER.comment("Armor increase").define("Armor", 1.0);
        BUILDER.pop();

        BUILDER.push("Melody Plushie");
        PLUSHIE_HEALTH = BUILDER.comment("Max Health Increase percentage").define("Max Health", 0.2);
        BUILDER.pop();

        BUILDER.push("Enderian Scarf");
        ENDERIAN_DAMAGE_TAKEN = BUILDER.comment("Damage taken in percentage").define("Damage Taken", 0.92);
        BUILDER.pop();

        BUILDER.push("True Enderian Scarf");
        TRUE_ENDERIAN_DAMAGE_TAKEN = BUILDER.comment("Damage taken in percentage").define("Damage Taken", 0.90);
        TRUE_ENDERIAN_EVADE = BUILDER.comment("Chance to evade an attack").define("Evade Chance", 0.1);
        TRUE_ENDERIAN_REACH = BUILDER.comment("Amount of reach gain").define("Reach gain", 1.0);
        BUILDER.pop();

        BUILDER.push("Gilded Scarf");
        GILDED_DAMAGE_TAKEN = BUILDER.comment("Damage taken in percentage").define("Damage Taken", 0.88);
        GILDED_DAMAGE_DEALT = BUILDER.comment("Increased damage dealt in percentage").define("Damage Dealt", 1.10);
        BUILDER.pop();

        BUILDER.push("Lucky Emerald Ring");
        EMERALD_RING_DAMAGE = BUILDER.comment("Increased damage dealt against illagers in percentage").define("Damage Dealt", 1.25);
        EMERALD_RING_EMERALDS = BUILDER.comment("Chance for on hit to drop an emerald").define("Emerald Chance", 0.05);
        BUILDER.pop();

        BUILDER.push("Magic Quiver");
        MAGIC_QUIVER_DAMAGE = BUILDER.comment("Increased arrow damage dealt in percentage").define("Arrow Damage", 1.08);
        BUILDER.pop();

        BUILDER.push("Envenomed Quiver");
        ENVENOMED_QUIVER_DAMAGE = BUILDER.comment("Increased arrow damage dealt in percentage").define("Arrow Damage", 1.10);
        BUILDER.pop();

        BUILDER.push("Molten Quiver");
        MOLTEN_QUIVER_DAMAGE = BUILDER.comment("Increased arrow damage dealt in percentage").define("Arrow Damage", 1.10);
        MOLTEN_QUIVER_ONFIRE_DAMAGE = BUILDER.comment("Increased on fire bonus arrow damage in percentage").define("On fire bonus", 1.05);
        BUILDER.pop();

        BUILDER.push("Wooden Headgear");
        WOODEN_HEADGEAR_ARMOR = BUILDER.comment("Armor increase").define("Armor", 1.0);
        WOODEN_HEADGEAR_ARROW_DAMAGE_TAKEN = BUILDER.comment("Damage taken in percentage").define("Damage taken", 0.95);
        BUILDER.pop();

        BUILDER.push("Golden Headgear");
        GOLDEN_HEADGEAR_ARMOR = BUILDER.comment("Armor increase").define("Armor", 2.0);
        GOLDEN_HEADGEAR_ARROW_DAMAGE_TAKEN = BUILDER.comment("Damage taken in percentage").define("Damage taken", 0.90);
        BUILDER.pop();

        BUILDER.push("Netherite Headgear");
        NETHERITE_HEADGEAR_ARMOR = BUILDER.comment("Armor increase").define("Armor", 3.0);
        NETHERITE_HEADGEAR_ARROW_DAMAGE_TAKEN = BUILDER.comment("Damage taken in percentage").define("Damage taken", 0.85);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
