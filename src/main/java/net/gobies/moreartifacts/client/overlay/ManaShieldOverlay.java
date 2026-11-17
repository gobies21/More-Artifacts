package net.gobies.moreartifacts.client.overlay;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.gobies.moreartifacts.MoreArtifacts;
import net.gobies.moreartifacts.client.renderer.gui.HeartRenderer;
import net.gobies.moreartifacts.config.ClientConfig;
import net.gobies.moreartifacts.helper.SoulType;
import net.gobies.moreartifacts.util.SoulUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ManaShieldOverlay {

    private static final ResourceLocation MANA_SHIELD_HEART = new ResourceLocation(MoreArtifacts.MOD_ID, "textures/gui/soul_mana_shield_heart.png");
    private static final ResourceLocation MANA_SHIELD_HEART_HARDCORE = new ResourceLocation(MoreArtifacts.MOD_ID, "textures/gui/soul_mana_shield_heart_hardcore.png");

    private static final float glowRadius = 10.0F;
    private static final HeartRenderer renderer = new HeartRenderer(512);

    private static int lastHealth = -1;
    private static long blinkTime = 0;

    public static final IGuiOverlay OVERLAY = (gui, guiGraphics, partialTick, width, height) -> {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) {
            lastHealth = -1;
            blinkTime = 0;
            return;
        }

        if (renderer.shouldSkipRendering(mc, gui, () -> ClientConfig.MANA_SHIELD_HEART_OVERLAY.get())) return;

        boolean shieldActive = SoulUtil.getActive(player);
        boolean isEnvy = SoulUtil.hasSoul(player, SoulType.ENVY);

        if (!isEnvy || !shieldActive) {
            lastHealth = -1;
            blinkTime = 0;
            return;
        }

        int ticks = gui.getGuiTicks();
        int health = Mth.ceil(player.getHealth());

        if (lastHealth == -1) {
            lastHealth = health;
        }

        if (health < lastHealth && player.invulnerableTime > 0) {
            blinkTime = ticks + 20;
        } else if (health > lastHealth && player.invulnerableTime > 0) {
            blinkTime = ticks + 10;
        }

        lastHealth = health;

        boolean isFlashing = blinkTime > ticks && (blinkTime - ticks) / 3L % 2L == 1L;

        int left = width / 2 - 91;
        int top = height - 39;

        ResourceLocation texture = mc.level != null && mc.level.getLevelData().isHardcore() ? MANA_SHIELD_HEART_HARDCORE : MANA_SHIELD_HEART;

        var maxHealthAttr = player.getAttribute(Attributes.MAX_HEALTH);
        if (maxHealthAttr == null) {
            return;
        }

        float maxHealth = (float) maxHealthAttr.getValue();
        int absorption = Mth.ceil(player.getAbsorptionAmount());

        int maxHearts = Mth.ceil(maxHealth / 2.0F);
        if (maxHearts <= 0) {
            return;
        }

        int totalRows = Mth.ceil((maxHealth + absorption) / 2.0F / 10.0F);
        int rowHeight = Math.max(10 - (totalRows - 2), 3);

        boolean isLowHealth = player.getHealth() + player.getAbsorptionAmount() <= 4.0F;
        int regen = -1;
        if (player.hasEffect(MobEffects.REGENERATION)) {
            regen = ticks % Mth.ceil(maxHealth + 5.0F);
        }

        float fluidTicks = ticks + partialTick;
        float index = (fluidTicks * 0.4F) % maxHearts;

        prepareHearts(maxHearts, health, left, top, rowHeight, isLowHealth, regen, isFlashing, ticks, index, player);
        Matrix4f matrix = guiGraphics.pose().last().pose();
        renderOverlay(matrix, texture, maxHearts);
    };

    private static void prepareHearts(int maxHearts, int health, int left, int top, int rowHeight, boolean isLowHealth, int regen, boolean isFlashing, int ticks, float index, Player player) {
        renderer.prepare(maxHearts);

        float hearts = health / 2.0F;
        float glowIndex = index * (hearts / maxHearts);
        float radius = Math.max(1.5F, glowRadius * (1.0F + hearts / maxHearts) / 2.0F);

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

            boolean isHalfHeart = healthIndex + 1 == health;
            renderer.setHeart(i, x, y, isHalfHeart ? 9 : 0, isHalfHeart && isFlashing ? 9 : 0);

            float distance = Math.abs(i - glowIndex);

            if (distance > hearts / 2.0F) {
                distance = hearts - distance;
            }

            if (distance < radius) {
                float pulse = 1.0F - (distance / radius);
                float intensity = 0.3F + pulse * 0.6F;

                renderer.setGlow(i, intensity);
            }
        }
    }

    private static void renderOverlay(Matrix4f matrix, ResourceLocation resourceLocation, int maxHearts) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderTexture(0, resourceLocation);

        renderHearts(matrix, maxHearts, false);
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
        renderHearts(matrix, maxHearts, true);

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
    }

    private static void renderHearts(Matrix4f matrix, int maxHearts, boolean glow) {
        int count = 0;

        for (int i = maxHearts - 1; i >= 0; --i) {
            if (!renderer.isVisible(i)) continue;
            if (glow && renderer.getGlowIntensity(i) <= 0.0F) continue;

            count++;
        }

        if (count == 0) return;

        BufferBuilder buffer = Tesselator.getInstance().getBuilder();
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);

        for (int i = maxHearts - 1; i >= 0; --i) {
            if (!renderer.isVisible(i)) continue;

            float red = 1.0F;
            float green = 1.0F;
            float blue = 1.0F;
            float alpha = 1.0F;

            if (glow) {
                float intensity = renderer.getGlowIntensity(i);

                if (intensity <= 0.0F) continue;

                red = 0.9F * intensity;
                green = 0.4F * intensity;
                blue = intensity;
                alpha = intensity;
            }

            int x = renderer.getX(i);
            int y = renderer.getY(i);
            int uOffset = renderer.getU(i);
            int vOffset = renderer.getV(i);

            float x2 = x + 9.0F;
            float y2 = y + 9.0F;

            float u1 = uOffset / 18.0F;
            float v1 = vOffset / 18.0F;
            float u2 = (uOffset + 9) / 18.0F;
            float v2 = (vOffset + 9) / 18.0F;

            buffer.vertex(matrix, x, y, 0.0F).uv(u1, v1).color(red, green, blue, alpha).endVertex();
            buffer.vertex(matrix, x, y2, 0.0F).uv(u1, v2).color(red, green, blue, alpha).endVertex();
            buffer.vertex(matrix, x2, y2, 0.0F).uv(u2, v2).color(red, green, blue, alpha).endVertex();
            buffer.vertex(matrix, x2, y, 0.0F).uv(u2, v1).color(red, green, blue, alpha).endVertex();
        }

        BufferUploader.drawWithShader(buffer.end());
    }
}