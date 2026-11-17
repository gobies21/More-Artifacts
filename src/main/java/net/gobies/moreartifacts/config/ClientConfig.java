package net.gobies.moreartifacts.config;

import net.gobies.moreartifacts.MoreArtifacts;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MoreArtifacts.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientConfig {
    private static final String FILENAME = "moreartifacts-client.toml";

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_ARTIFACT_MODELS;
    public static ForgeConfigSpec.ConfigValue<Boolean> ENDERIAN_EYE_OVERLAY;
    public static ForgeConfigSpec.ConfigValue<Boolean> VIRULENT_HEART_OVERLAY;
    public static ForgeConfigSpec.ConfigValue<Boolean> FROSTED_HEART_OVERLAY;
    public static ForgeConfigSpec.ConfigValue<Boolean> MANA_SHIELD_HEART_OVERLAY;
    public static ForgeConfigSpec.ConfigValue<Boolean> BROKEN_HEART_OVERLAY;

    public static ForgeConfigSpec.ConfigValue<Boolean> HOLY_MANTLE_OVERLAY;
    public static ForgeConfigSpec.ConfigValue<Boolean> DODGE_OVERLAY;
    public static ForgeConfigSpec.ConfigValue<Boolean> DOUBLE_TAP_DODGE;
    public static ForgeConfigSpec.ConfigValue<Boolean> SOUL_OVERLAY;
    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_CURIO_HUD;
    public static ForgeConfigSpec.ConfigValue<Boolean> RENDER_VERTICALLY;
    public static ForgeConfigSpec.ConfigValue<Boolean> FLIP_RENDER_ORDER;
    public static ForgeConfigSpec.ConfigValue<Integer> MAX_CURIOS;
    public static ForgeConfigSpec.ConfigValue<Integer> OFFSET_X;
    public static ForgeConfigSpec.ConfigValue<Integer> OFFSET_Y;
    public static ForgeConfigSpec.ConfigValue<Integer> CURIO_SPACING;
    public static ForgeConfigSpec.ConfigValue<Double> HUD_SCALE;

    static {
        BUILDER.comment("General client settings").push("General");
        ENABLE_ARTIFACT_MODELS = BUILDER.comment("Enable artifacts models displaying on the player").define("Enable_Artifact_Models", true);
        ENDERIAN_EYE_OVERLAY = BUILDER.comment("Enable the overlay for the enderian eye cooldown").define("Enderian_Eye_Overlay", true);
        VIRULENT_HEART_OVERLAY = BUILDER.comment("Enable the virulent heart overlay rendering").define("Virulent_Heart_Overlay", true);
        FROSTED_HEART_OVERLAY = BUILDER.comment("Enable the frosted heart overlay rendering").define("Frosted_Heart_Overlay", true);
        MANA_SHIELD_HEART_OVERLAY = BUILDER.comment("Enable the mana shield heart overlay rendering").define("Mana_Shield_Heart_Overlay", true);
        BROKEN_HEART_OVERLAY = BUILDER.comment("Enable the broken heart overlay rendering").define("Broken_Heart_Overlay", true);
        HOLY_MANTLE_OVERLAY = BUILDER.comment("Enable the holy mantle overlay rendering").define("Holy_Mantle_Overlay", true);
        DODGE_OVERLAY = BUILDER.comment("Enable the dodge overlay appearing in the experience bar").define("Dodge_Overlay", true);
        DOUBLE_TAP_DODGE = BUILDER.comment("Enable double tapping movement keys to dodge").define("Double_Tap_Dodge", false);
        SOUL_OVERLAY = BUILDER.comment("Enable the soul overlay rendering").define("Soul_Overlay", true);
        BUILDER.push("Curio_Hud");
        ENABLE_CURIO_HUD = BUILDER.comment("Enable curios rendering on the screen based on whats equipped (WIP)").define("Enable_Curio_Hud", false);
        RENDER_VERTICALLY = BUILDER.comment("Should curios render vertically instead of horizontally").define("Render_Vertically", false);
        FLIP_RENDER_ORDER = BUILDER.comment("Should the render order be flipped, so that head slot items render at the opposite side").define("Flip_Render_Order", false);
        MAX_CURIOS = BUILDER.comment("Max curios that can be rendered from this overlay").defineInRange("Max_Curios", 8, 1, 32);
        OFFSET_X = BUILDER.comment("X Offset for the overlay").define("Offset_X", 0);
        OFFSET_Y = BUILDER.comment("Y Offset for the overlay").define("Offset_Y", 0);
        CURIO_SPACING = BUILDER.comment("How many pixels are each curio separated by").define("Spacing", 18);
        HUD_SCALE = BUILDER.comment("scale of all the curio items rendered").define("Hud_Scale", 1.0);
        BUILDER.pop();

        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}