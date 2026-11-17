package net.gobies.moreartifacts.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.gobies.moreartifacts.client.overlay.AnnihilationStackOverlay;
import net.gobies.moreartifacts.config.ClientConfig;
import net.gobies.moreartifacts.util.DodgeProperties;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin {

    @Unique
    private static final int ANNIHILATION_TINT = 0xFF560000;

    @Unique
    private static float moreArtifacts$currentAlpha;

    @Unique
    private static long moreArtifacts$lastUpdated = -1L;

    @Unique
    private static final ResourceLocation GUI_ICONS = new ResourceLocation("textures/gui/icons.png");

    @Inject(
            method = "renderExperienceBar",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/profiling/ProfilerFiller;push(Ljava/lang/String;)V",
                    ordinal = 1
            )
    )
    private void renderDodgeOverlay(GuiGraphics graphics, int x, CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (!ClientConfig.DODGE_OVERLAY.get() || player == null) return;

        ItemStack dodgeItem = DodgeProperties.getDodgeItem(player);
        if (dodgeItem.isEmpty() || !player.getCooldowns().isOnCooldown(dodgeItem.getItem())) return;

        float percent = player.getCooldowns().getCooldownPercent(dodgeItem.getItem(), mc.getFrameTime());

        moreArtifacts$renderCooldownBar(graphics, x, mc.getWindow().getGuiScaledHeight() - 29, 1.0F - percent);
    }

    @Unique
    private static void moreArtifacts$renderCooldownBar(GuiGraphics graphics, int x, int y, float chargeRate) {
        graphics.blit(GUI_ICONS, x, y, 0, 84, 182, 5);

        if (chargeRate > 0.0F) {
            int width = Math.min((int) (chargeRate * 182.0F), 182);
            graphics.blit(GUI_ICONS, x, y, 0, 89, width, 5);
        }
    }

    @Inject(
            method = "renderCrosshair",
            at = @At("HEAD")
    )
    private void startColorCrosshair(GuiGraphics graphics, CallbackInfo ci) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null || minecraft.level == null) {
            moreArtifacts$currentAlpha = 0.0F;
            moreArtifacts$lastUpdated = -1L;
            return;
        }

        long time = System.nanoTime();

        float deltaTime = moreArtifacts$lastUpdated < 0L ? 0.0F : Math.min((time - moreArtifacts$lastUpdated) / 1_000_000_000.0F, 0.05F);

        moreArtifacts$lastUpdated = time;

        float targetAlpha = moreArtifacts$isReady() ? 1.0F : 0.0F;
        moreArtifacts$currentAlpha = Mth.approach(moreArtifacts$currentAlpha, targetAlpha, 2.5F * deltaTime);

        if (moreArtifacts$currentAlpha <= 0.0F) return;

        float alpha = moreArtifacts$currentAlpha;

        float red = ((ANNIHILATION_TINT >> 16) & 0xFF) / 255.0F;
        float green = ((ANNIHILATION_TINT >> 8) & 0xFF) / 255.0F;
        float blue = (ANNIHILATION_TINT & 0xFF) / 255.0F;

        red = 1.0F + (red - 1.0F) * alpha;
        green = 1.0F + (green - 1.0F) * alpha;
        blue = 1.0F + (blue - 1.0F) * alpha;

        RenderSystem.setShaderColor(red, green, blue, 1.0F);
    }

    @Unique
    private static boolean moreArtifacts$isReady() {
        return AnnihilationStackOverlay.isReady();
    }
}