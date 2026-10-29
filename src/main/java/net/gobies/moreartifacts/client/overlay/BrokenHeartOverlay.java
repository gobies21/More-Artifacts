package net.gobies.moreartifacts.client.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import net.gobies.moreartifacts.compat.firstaid.FirstAidCompat;
import net.gobies.moreartifacts.config.ClientConfig;
import net.gobies.moreartifacts.util.BrokenHeartSystem;
import net.gobies.moreartifacts.util.ModLoadedUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class BrokenHeartOverlay {

    // TODO: First aid compat for broken hearts overlay in their custom gui
    private static final ResourceLocation BROKEN_HEART = new ResourceLocation("moreartifacts:textures/gui/broken_heart.png");
    private static final ResourceLocation BROKEN_HEART_HARDCORE = new ResourceLocation("moreartifacts:textures/gui/broken_heart_hardcore.png");
    private static final RandomSource random = RandomSource.create();

    public static final IGuiOverlay OVERLAY = (forgeGui, guiGraphics, partialTicks, screenWidth, screenHeight) -> {
        Minecraft mc = forgeGui.getMinecraft();
        Player player = mc.player;

        if (player == null || shouldSkipRendering(mc, forgeGui)) return;

        AttributeModifier modifier = BrokenHeartSystem.getBrokenHeartModifier(player);
        if (modifier == null) return;

        float brokenAmount = (float) Math.abs(modifier.getAmount());
        int brokenHearts = (int) (brokenAmount * 0.5F);
        if (brokenHearts <= 0) return;

        mc.getProfiler().push("broken_hearts");

        int ticks = mc.gui.getGuiTicks();
        float maxHealth = player.getMaxHealth();
        int absorption = Mth.ceil(player.getAbsorptionAmount());

        float defaultMaxHealth = maxHealth + brokenAmount;
        float defaultHealthMax = Math.max(defaultMaxHealth, Math.max(player.getHealth(), maxHealth));

        int healthRows = Mth.ceil((defaultHealthMax + absorption) * 0.05F);
        int rowHeight = Math.max(10 - (healthRows - 2), 3);

        ResourceLocation texture = mc.level != null && mc.level.getLevelData().isHardcore() ? BROKEN_HEART_HARDCORE : BROKEN_HEART;

        int totalHeartContainers = Mth.ceil(defaultMaxHealth * 0.5F);
        int currentMaxContainers = totalHeartContainers - brokenHearts;

        int topY = screenHeight - (forgeGui.leftHeight - (healthRows * rowHeight + (rowHeight != 10 ? 10 - rowHeight : 0)));
        int leftBase = screenWidth / 2 - 91;

        boolean isLowHealth = (player.getHealth() + player.getAbsorptionAmount()) <= 4.0F;
        int regen = -1;
        if (player.hasEffect(MobEffects.REGENERATION)) {
            regen = ticks % Mth.ceil(maxHealth + 5.0F);
        }

        RenderSystem.enableBlend();

        for (int i = 0; i < brokenHearts; i++) {
            int containerIndex = currentMaxContainers + i;
            if (containerIndex >= totalHeartContainers || containerIndex < 0) break;

            int x = leftBase + (containerIndex % 10 * 8);
            int y = topY - (containerIndex / 10 * rowHeight);

            if (isLowHealth) {
                random.setSeed((long) ticks * 312871L);
                int shakeOffset = 0;
                for (int h = totalHeartContainers - 1; h >= containerIndex; --h) {
                    shakeOffset = random.nextInt(2);
                }
                y += shakeOffset;
            }

            if (regen >= 0 && regen == containerIndex) {
                y -= 2;
            }

            guiGraphics.blit(texture, x, y, 0, 0, 9, 9, 9, 9);
        }

        RenderSystem.disableBlend();
        mc.getProfiler().pop();
    };

    private static boolean shouldSkipRendering(Minecraft mc, ForgeGui forgeGui) {
        if (mc.options.hideGui || !forgeGui.shouldDrawSurvivalElements()) {
            return true;
        }
        if (!ClientConfig.BROKEN_HEART_OVERLAY.get()) {
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