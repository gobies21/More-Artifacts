package net.gobies.moreartifacts.config;

import net.gobies.moreartifacts.MoreArtifacts;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = MoreArtifacts.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientConfig {
    private static final String FILENAME = "moreartifacts-client.toml";

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_ARTIFACT_MODELS;
    public static boolean enable_artifact_models;
    public static ForgeConfigSpec.ConfigValue<Boolean> ENDERIAN_EYE_OVERLAY;
    public static boolean enderian_eye_overlay;
    public static ForgeConfigSpec.ConfigValue<Boolean> BROKEN_HEART_OVERLAY;
    public static boolean broken_heart_overlay;
    public static ForgeConfigSpec.ConfigValue<Boolean> HOLY_MANTLE_OVERLAY;
    public static boolean holy_mantle_overlay;
    public static ForgeConfigSpec.ConfigValue<Boolean> DOUBLE_TAP_DODGE;
    public static boolean double_tap_dodge;
    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_CURIO_HUD;
    public static boolean enable_curio_hud;
    public static ForgeConfigSpec.ConfigValue<Boolean> RENDER_VERTICALLY;
    public static boolean render_vertically;
    public static ForgeConfigSpec.ConfigValue<Boolean> FLIP_RENDER_ORDER;
    public static boolean flip_render_order;
    public static ForgeConfigSpec.ConfigValue<Integer> MAX_CURIOS;
    public static int max_curios;
    public static ForgeConfigSpec.ConfigValue<Integer> OFFSET_X;
    public static int offset_x;
    public static ForgeConfigSpec.ConfigValue<Integer> OFFSET_Y;
    public static int offset_y;
    public static ForgeConfigSpec.ConfigValue<Integer> CURIO_SPACING;
    public static int curio_spacing;
    public static ForgeConfigSpec.ConfigValue<Double> HUD_SCALE;
    public static float hud_scale;

    @SubscribeEvent
    static void onLoad(ModConfigEvent.Loading configEvent) {
        if (configEvent.getConfig().getFileName().equals(FILENAME)) {
            enable_artifact_models = ENABLE_ARTIFACT_MODELS.get();
            enderian_eye_overlay = ENDERIAN_EYE_OVERLAY.get();
            broken_heart_overlay = BROKEN_HEART_OVERLAY.get();
            holy_mantle_overlay = HOLY_MANTLE_OVERLAY.get();
            double_tap_dodge = DOUBLE_TAP_DODGE.get();
            enable_curio_hud = ENABLE_CURIO_HUD.get();
            render_vertically = RENDER_VERTICALLY.get();
            flip_render_order = FLIP_RENDER_ORDER.get();
            max_curios = MAX_CURIOS.get();
            offset_x = OFFSET_X.get();
            offset_y = OFFSET_Y.get();
            curio_spacing = CURIO_SPACING.get();
            hud_scale = HUD_SCALE.get().floatValue();
        }
    }

    static {
        BUILDER.comment("General client settings").push("General");
        ENABLE_ARTIFACT_MODELS = BUILDER.comment("Enable artifacts models displaying on the player").define("Enable_Artifact_Models", true);
        ENDERIAN_EYE_OVERLAY = BUILDER.comment("Enable the overlay for the enderian eye cooldown").define("Enderian_Eye_Overlay", true);
        BROKEN_HEART_OVERLAY = BUILDER.comment("Enable the broken heart overlay rendering").define("Broken_Heart_Overlay", true);
        HOLY_MANTLE_OVERLAY = BUILDER.comment("Enable the holy mantle overlay rendering").define("Holy_Mantle_Overlay", false);
        DOUBLE_TAP_DODGE = BUILDER.comment("Enable double tapping movement keys to dodge").define("Double_Tap_Dodge", false);
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