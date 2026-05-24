package net.gobies.moreartifacts.client.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import net.gobies.moreartifacts.compat.firstaid.FirstAidCompat;
import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.util.BrokenHeartSystem;
import net.gobies.moreartifacts.util.ModLoadedUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;
import java.util.Random;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class BrokenHeartOverlay {

    // TODO: First aid compat for broken hearts overlay in their custom gui
    // TODO: Fix desync on ghost max health when gaining broken hearts
    // TODO: Fix broken heart regeneration effect
    private static final ResourceLocation HEART = new ResourceLocation("moreartifacts:textures/gui/broken_heart.png");

    public static final IGuiOverlay OVERLAY = (forgeGui, guiGraphics, partialTicks, screenWidth, screenHeight) -> {
        Minecraft mc = forgeGui.getMinecraft();
        if (shouldSkipRendering(mc, forgeGui)) {
            return;
        }
        Player player = mc.player;
        AttributeModifier modifier = BrokenHeartSystem.getBrokenHeartModifier(Objects.requireNonNull(player));
        if (modifier == null) {
            return;
        }
        int brokenHearts = (int) (Math.abs(modifier.getAmount()) * 0.5F);
        if (brokenHearts <= 0) {
            return;
        }
        mc.getProfiler().push("broken_hearts");
        int leftBase = screenWidth / 2 - 91;
        int ticks = mc.gui.getGuiTicks();
        boolean shakeRow = player.getHealth() < 5.0F;
        float maxHealth = player.getMaxHealth();
        int absorptionAmount = Mth.ceil(player.getAbsorptionAmount());

        float brokenHealthAmount = (float) Math.abs(modifier.getAmount());

        float defaultMaxHealth = maxHealth + brokenHealthAmount;
        float defaultHealthMax = Math.max(defaultMaxHealth, Math.max(player.getHealth(), maxHealth));
        int healthRows = Mth.ceil((defaultHealthMax + (float) absorptionAmount) * 0.05F);
        int rowHeight = Math.max(10 - (healthRows - 2), 3);

        int totalHeartContainers = Mth.ceil(defaultMaxHealth * 0.5F);
        int currentMaxContainers = totalHeartContainers - brokenHearts;
        int vanillaHeight = healthRows * rowHeight;
        if (rowHeight != 10) {
            vanillaHeight += (10 - rowHeight);
        }
        int leftHeight = forgeGui.leftHeight - vanillaHeight;
        int topY = screenHeight - leftHeight;

        int[] computedVanillaShakes = new int[totalHeartContainers];
        Random random = new Random(ticks * 312871);
        boolean isLowHealth = (player.getHealth() + player.getAbsorptionAmount()) <= 4.0F;
        for (int i1 = totalHeartContainers - 1; i1 >= 0; --i1) {
            if (isLowHealth) {
                computedVanillaShakes[i1] = random.nextInt(2);
            }
        }

        RenderSystem.enableBlend();
        for (int i = 0; i < brokenHearts; i++) {
            int containerIndex = currentMaxContainers + i;
            if (containerIndex >= totalHeartContainers || containerIndex < 0) {
                break;
            }

            int vanillaShakeOffset = computedVanillaShakes[containerIndex];
            int slotX = containerIndex % 10;
            int rowY = containerIndex / 10;
            int x = leftBase + (slotX * 8);
            int y = topY - (rowY * rowHeight);

            if (shakeRow) {
                y += vanillaShakeOffset;
            }

            guiGraphics.blit(HEART, x, y, 0, 0, 9, 9, 9, 9);
        }
        RenderSystem.disableBlend();
        mc.getProfiler().pop();
    };

    private static boolean shouldSkipRendering(Minecraft mc, ForgeGui forgeGui) {
        if (mc.options.hideGui || !forgeGui.shouldDrawSurvivalElements()) {
            return true;
        }
        if (!CommonConfig.BROKEN_HEART_OVERLAY.get()) {
            return true;
        }
        Player player = mc.player;
        if (player == null) {
            return true;
        }
        if (ModLoadedUtil.isFirstAidLoaded()) {
            if (!FirstAidCompat.isVanillaHealthBarActive()) {
                return true;
            }
        }
        return player.isCreative() || player.isSpectator();
    }
}