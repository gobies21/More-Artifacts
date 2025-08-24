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

    public ClientConfig() {
    }


    @SubscribeEvent
    static void onLoad(ModConfigEvent.Loading configEvent) {
        if (configEvent.getConfig().getFileName().equals(FILENAME)) {
            enable_artifact_models = ENABLE_ARTIFACT_MODELS.get();
            enderian_eye_overlay = ENDERIAN_EYE_OVERLAY.get();
        }
    }

    static {
        BUILDER.push("General");
        ENABLE_ARTIFACT_MODELS = BUILDER.comment("Enable artifacts models displaying on the player").define("Enable_Artifact_Models", true);
        ENDERIAN_EYE_OVERLAY = BUILDER.comment("Enable the overlay for the enderian eye cooldown").define("Enderian_Eye_Overlay", true);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}