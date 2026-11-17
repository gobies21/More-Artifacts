package net.gobies.moreartifacts.client.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.gobies.moreartifacts.MoreArtifacts;
import net.gobies.moreartifacts.client.renderer.gui.HeartRenderer;
import net.gobies.moreartifacts.config.ClientConfig;
import net.gobies.moreartifacts.init.MAEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class VirulentHeartOverlay {

    private static final ResourceLocation PURPLE_HEARTS = new ResourceLocation(MoreArtifacts.MOD_ID, "textures/gui/purple_hearts.png");
    private static final ResourceLocation PURPLE_HEARTS_HARDCORE = new ResourceLocation(MoreArtifacts.MOD_ID, "textures/gui/purple_hearts_hardcore.png");

    private static final HeartRenderer renderer = new HeartRenderer(512);

    private static int lastHealth = -1;
    private static long blinkTime = 0;

    private static final int HEART_SIZE = 9;
    private static final int TEXTURE_SIZE = 18;

    public static final IGuiOverlay OVERLAY = (gui, guiGraphics, partialTick, width, height) -> {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null || !player.hasEffect(MAEffects.Virulent.get())) {
            lastHealth = -1;
            blinkTime = 0;
            return;
        }

        if (renderer.shouldSkipRendering(mc, gui, () -> ClientConfig.VIRULENT_HEART_OVERLAY.get())) return;

        int ticks = gui.getGuiTicks();
        int health = Mth.ceil(player.getHealth());

        if (lastHealth == -1) {
            lastHealth = health;
        }

        if (health < lastHealth && player.invulnerableTime > 0) {
            blinkTime = ticks + 20L;
        } else if (health > lastHealth && player.invulnerableTime > 0) {
            blinkTime = ticks + 10L;
        }

        lastHealth = health;

        boolean isFlashing = blinkTime > ticks && (blinkTime - ticks) / 3L % 2L == 1L;
        AttributeInstance maxHealthAttr = player.getAttribute(Attributes.MAX_HEALTH);

        if (maxHealthAttr == null) {
            return;
        }

        float maxHealth = (float) maxHealthAttr.getValue();
        int absorption = Mth.ceil(player.getAbsorptionAmount());

        int totalRows = Mth.ceil((maxHealth + absorption) / 2.0F / 10.0F);
        int rowHeight = Math.max(10 - (totalRows - 2), 3);

        int left = width / 2 - 91;
        int top = height - 39;

        boolean isLowHealth = player.getHealth() + player.getAbsorptionAmount() <= 4.0F;
        int regen = -1;
        if (player.hasEffect(MobEffects.REGENERATION)) {
            regen = ticks % Mth.ceil(maxHealth + 5.0F);
        }

        int maxHearts = Mth.ceil((maxHealth + absorption) / 2.0F);
        ResourceLocation texture = mc.level != null && mc.level.getLevelData().isHardcore() ? PURPLE_HEARTS_HARDCORE : PURPLE_HEARTS;

        prepareHearts(health, maxHearts, left, top, rowHeight, regen, ticks, isLowHealth, isFlashing, player);
        guiGraphics.flush();
        renderOverlay(guiGraphics.pose().last().pose(), texture, maxHearts);
    };

    private static void prepareHearts(int health, int maxHearts, int left, int top, int rowHeight, int regen, int ticks, boolean isLowHealth, boolean isFlashing, Player player) {
        renderer.prepare(maxHearts);

        for (int i = maxHearts - 1; i >= 0; --i) {
            int x = left + (i % 10) * 8;
            int y = top - (i / 10) * rowHeight;

            int healthIndex = i * 2;
            if (healthIndex >= health) {
                continue;
            }

            if (isLowHealth) {
                renderer.random.setSeed((long) ticks * 312871L);

                int brokenHearts = renderer.getBrokenHearts(player);
                int absorptionHearts = Mth.ceil(player.getAbsorptionAmount() / 2.0F);
                int totalContainers = maxHearts + brokenHearts + absorptionHearts;

                int shakeOffset = 0;

                for (int h = totalContainers - 1; h >= i; --h) {
                    shakeOffset = renderer.random.nextInt(2);
                }

                y += shakeOffset;
            }

            if (regen >= 0 && regen == i) {
                y -= 2;
            }

            int uOffset = healthIndex + 1 == health ? 9 : 0;
            int vOffset = isFlashing ? 9 : 0;

            renderer.setHeart(i, x, y, uOffset, vOffset);
        }
    }

    private static void renderOverlay(Matrix4f matrix, ResourceLocation resourceLocation, int maxHearts) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderTexture(0, resourceLocation);

        renderHearts(matrix, maxHearts);

        RenderSystem.disableBlend();
    }

    private static void renderHearts(Matrix4f matrix, int maxHearts) {
        int visibleHearts = 0;

        for (int i = 0; i < maxHearts; i++) {
            if (renderer.isVisible(i)) {
                visibleHearts++;
            }
        }

        if (visibleHearts == 0) {
            return;
        }

        BufferBuilder builder = Tesselator.getInstance().getBuilder();

        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);

        for (int i = 0; i < maxHearts; i++) {
            if (!renderer.isVisible(i)) {
                continue;
            }

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

        BufferUploader.drawWithShader(builder.end());
    }
}