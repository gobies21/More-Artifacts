package net.gobies.moreartifacts.client.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.math.Axis;
import net.gobies.moreartifacts.MoreArtifacts;
import net.gobies.moreartifacts.config.CommonConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class AnnihilationStackOverlay {

    private static final ResourceLocation ANNIHILATION_STACK = new ResourceLocation(MoreArtifacts.MOD_ID, "textures/gui/annihilation_stack.png");

    private static final int ICON_SIZE = 8;
    private static final float BASE_RADIUS = 12.0F;
    private static final float RADIUS_PER_STACK = 1.0F;
    private static final float ORBIT_SPEED = 36.0F;

    private static int annihilationStacks;
    private static float orbitAngle;
    private static long lastFrame;

    public static void setStacks(int stacks) {
        annihilationStacks = Mth.clamp(stacks, 0, CommonConfig.WRATH_ANNIHILATION_MAX_STACKS.get());
    }

    public static boolean isReady() {
        return annihilationStacks > 0;
    }

    private static void updateOrbit() {
        long now = System.nanoTime();

        if (lastFrame != 0L) {
            float deltaSeconds = (now - lastFrame) / 1_000_000_000.0F;

            deltaSeconds = Mth.clamp(deltaSeconds, 0.0F, 0.05F);

            orbitAngle = (orbitAngle + ORBIT_SPEED * deltaSeconds) % 360.0F;
        }

        lastFrame = now;
    }

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Post event) {
        if (event.getOverlay() != VanillaGuiOverlay.CROSSHAIR.type()) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.options.hideGui || mc.level == null) return;
        int stacks = annihilationStacks;
        if (stacks <= 0) {
            return;
        }

        updateOrbit();
        GuiGraphics graphics = event.getGuiGraphics();

        float centerX = event.getWindow().getGuiScaledWidth() / 2.0F;
        float centerY = event.getWindow().getGuiScaledHeight() / 2.0F;
        float iconRadius = BASE_RADIUS + (stacks - 1) * RADIUS_PER_STACK;

        RenderSystem.enableBlend();
        if (!mc.options.getCameraType().isFirstPerson()) return;
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        graphics.pose().pushPose();
        graphics.pose().setIdentity();

        for (int i = 0; i < stacks; i++) {
            float angle = -90.0F + (360.0F * i / stacks) + orbitAngle;
            double radians = Math.toRadians(angle);

            float x = centerX + (float) Math.cos(radians) * iconRadius;
            float y = centerY + (float) Math.sin(radians) * iconRadius;

            graphics.pose().pushPose();
            graphics.pose().translate(x, y, 0.0F);
            graphics.pose().mulPose(Axis.ZP.rotationDegrees(angle + 45.0F));

            graphics.blit(ANNIHILATION_STACK, -ICON_SIZE / 2, -ICON_SIZE / 2, 0, 0, ICON_SIZE, ICON_SIZE, 9, 9);
            graphics.pose().popPose();
        }
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
        graphics.pose().popPose();
    }
}