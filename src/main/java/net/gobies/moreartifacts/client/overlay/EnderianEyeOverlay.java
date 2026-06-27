package net.gobies.moreartifacts.client.overlay;

import net.gobies.moreartifacts.config.ClientConfig;
import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.util.CooldownHandler;
import net.gobies.moreartifacts.util.CurioHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class EnderianEyeOverlay {

    private static final ResourceLocation TELEPORT_BAR_LOCATION = new ResourceLocation("moreartifacts:textures/gui/teleport_bar.png");
    private static final ResourceLocation FILL_BAR_LOCATION = new ResourceLocation("moreartifacts:textures/gui/fill_bar.png");

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Post event) {
        if (!ClientConfig.ENDERIAN_EYE_OVERLAY.get()) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || !mc.options.getCameraType().isFirstPerson()) return;

        Player player = mc.player;
        if (CurioHandler.isCurioEquipped(player, MAItems.EnderianEye.get())) {
            String artifactId = "enderian_eye";
            double cooldown = CommonConfig.ENDERIAN_EYE_COOLDOWN.get();
            double cooldownTimer = CooldownHandler.getCooldown(player, artifactId);

            if (cooldownTimer > 0 && cooldownTimer < cooldown) {
                GuiGraphics guiGraphics = event.getGuiGraphics();
                int width = event.getWindow().getGuiScaledWidth();
                int height = event.getWindow().getGuiScaledHeight();

                int barWidth = (int) (cooldownTimer / cooldown * 14);
                int barHeight = 4;
                int x = (int) (width * 0.5 - 7.8);
                int y = (int) (height * 0.5) + 5;

                guiGraphics.blit(TELEPORT_BAR_LOCATION, x, y, 0, 8, 15, barHeight, 15, 3);
                int middleBarHeight = 1;
                int middleBarStartY = y + (barHeight - middleBarHeight) / 2;
                guiGraphics.blit(FILL_BAR_LOCATION, x + 14 - barWidth, middleBarStartY, 0, 0, barWidth, middleBarHeight, 15, 1);
            }
        }
    }
}