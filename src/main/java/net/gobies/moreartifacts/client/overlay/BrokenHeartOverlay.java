package net.gobies.moreartifacts.client.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.gobies.moreartifacts.client.renderer.gui.HeartRenderer;
import net.gobies.moreartifacts.config.ClientConfig;
import net.gobies.moreartifacts.util.BrokenHeartSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class BrokenHeartOverlay {

    private static final ResourceLocation BROKEN_HEART = new ResourceLocation("moreartifacts:textures/gui/broken_heart.png");
    private static final ResourceLocation BROKEN_HEART_HARDCORE = new ResourceLocation("moreartifacts:textures/gui/broken_heart_hardcore.png");

    private static final HeartRenderer renderer = new HeartRenderer(512);

    private static final int HEART_SIZE = 9;
    private static final int TEXTURE_SIZE = 9;

    public static final IGuiOverlay OVERLAY = (gui, guiGraphics, partialTicks, screenWidth, screenHeight) -> {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) return;

        if (renderer.shouldSkipRendering(mc, gui, () -> ClientConfig.BROKEN_HEART_OVERLAY.get())) return;

        AttributeModifier modifier = BrokenHeartSystem.getBrokenHeartModifier(player);
        if (modifier == null) {
            return;
        }

        float brokenAmount = (float) Math.abs(modifier.getAmount());
        int brokenHearts = (int) (brokenAmount * 0.5F);

        if (brokenHearts <= 0) {
            return;
        }

        mc.getProfiler().push("broken_hearts");

        int ticks = gui.getGuiTicks();

        float maxHealth = player.getMaxHealth();
        int absorption = Mth.ceil(player.getAbsorptionAmount());

        float defaultMaxHealth = maxHealth + brokenAmount;

        float defaultHealthMax = Math.max(defaultMaxHealth, Math.max(player.getHealth(), maxHealth));

        int healthRows = Mth.ceil((defaultHealthMax + absorption) * 0.05F);

        int rowHeight = Math.max(10 - (healthRows - 2), 3);

        ResourceLocation texture = mc.level != null && mc.level.getLevelData().isHardcore() ? BROKEN_HEART_HARDCORE : BROKEN_HEART;

        int totalHeartContainers = Mth.ceil(defaultMaxHealth * 0.5F);
        int currentMaxContainers = totalHeartContainers - brokenHearts;
        int topY = screenHeight - (gui.leftHeight - (healthRows * rowHeight + (rowHeight != 10 ? 10 - rowHeight : 0)));
        int leftBase = screenWidth / 2 - 91;

        boolean isLowHealth = player.getHealth() + player.getAbsorptionAmount() <= 4.0F;
        int regen = -1;
        if (player.hasEffect(MobEffects.REGENERATION)) {
            regen = ticks % Mth.ceil(maxHealth + 5.0F);
        }

        prepareHearts(brokenHearts, totalHeartContainers, currentMaxContainers, leftBase, topY, rowHeight, regen, ticks, isLowHealth);
        guiGraphics.flush();
        renderHearts(guiGraphics.pose().last().pose(), texture, totalHeartContainers);

        mc.getProfiler().pop();
    };

    private static void prepareHearts(int brokenHearts, int totalHeartContainers, int currentMaxContainers, int leftBase, int topY, int rowHeight, int regen, int ticks, boolean isLowHealth) {
        renderer.prepare(totalHeartContainers);

        for (int i = 0; i < brokenHearts; i++) {
            int containerIndex = currentMaxContainers + i;
            if (containerIndex >= totalHeartContainers || containerIndex < 0) {
                break;
            }

            int x = leftBase + (containerIndex % 10 * 8);
            int y = topY - (containerIndex / 10 * rowHeight);

            if (isLowHealth) {
                renderer.random.setSeed((long) ticks * 312871L);
                int shakeOffset = 0;
                for (int h = totalHeartContainers - 1; h >= containerIndex; --h) {
                    shakeOffset = renderer.random.nextInt(2);
                }
                y += shakeOffset;
            }

            if (regen >= 0 && regen == containerIndex) {
                y -= 2;
            }

            renderer.setHeart(containerIndex, x, y, 0, 0);
        }
    }

    private static void renderHearts(Matrix4f matrix, ResourceLocation resourceLocation, int totalHeartContainers) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderTexture(0, resourceLocation);

        BufferBuilder builder = Tesselator.getInstance().getBuilder();

        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);

        boolean hasHearts = false;

        for (int i = 0; i < totalHeartContainers; i++) {
            if (!renderer.isVisible(i)) {
                continue;
            }

            hasHearts = true;

            int x = renderer.getX(i);
            int y = renderer.getY(i);

            int u = renderer.getU(i);
            int v = renderer.getV(i);

            float minU = (float) u / TEXTURE_SIZE;
            float minV = (float) v / TEXTURE_SIZE;

            float maxU = (float) (u + HEART_SIZE) / TEXTURE_SIZE;
            float maxV = (float) (v + HEART_SIZE) / TEXTURE_SIZE;

            builder.vertex(matrix, x, y + HEART_SIZE, 0).uv(minU, maxV).color(255, 255, 255, 255).endVertex();
            builder.vertex(matrix, x + HEART_SIZE, y + HEART_SIZE, 0).uv(maxU, maxV).color(255, 255, 255, 255).endVertex();
            builder.vertex(matrix, x + HEART_SIZE, y, 0).uv(maxU, minV).color(255, 255, 255, 255).endVertex();
            builder.vertex(matrix, x, y, 0).uv(minU, minV).color(255, 255, 255, 255).endVertex();
        }

        if (hasHearts) {
            BufferUploader.drawWithShader(builder.end());
        }

        RenderSystem.disableBlend();
    }
}