package net.gobies.moreartifacts.client.overlay;

import net.gobies.moreartifacts.Config;
import net.gobies.moreartifacts.event.ClientEvents;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.util.CurioHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;

public class EnderianEyeOverlay {

    private static final ResourceLocation TELEPORT_BAR_LOCATION = new ResourceLocation("moreartifacts:textures/gui/teleport_bar.png");
    private static final ResourceLocation FILL_BAR_LOCATION = new ResourceLocation("moreartifacts:textures/gui/fill_bar.png");

    private static final Map<Player, Long> cooldownMap = new HashMap<>();

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onRenderGameOverlay(RenderGuiEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        LivingEntity entity = (LivingEntity) mc.getCameraEntity();
        if (Config.ENDERIAN_EYE_OVERLAY.get()) {
            if (entity instanceof Player player) {
                if (CurioHandler.isCurioEquipped(player, MAItems.EnderianEye.get())) {
                    double cooldown = Config.ENDERIAN_EYE_COOLDOWN.get();
                    GuiGraphics guiGraphics = event.getGuiGraphics();

                    long currentTime = System.currentTimeMillis();
                    long lastTeleportTime = cooldownMap.getOrDefault(player, 0L);
                    double cooldownTimer = (currentTime - lastTeleportTime) / 1000.0;

                    if (ClientEvents.TELEPORT_KEY.consumeClick() && cooldownTimer >= cooldown) {
                        cooldownMap.put(player, currentTime);
                        cooldownTimer = 0;
                    }

                    if (cooldownTimer < cooldown && mc.options.getCameraType().isFirstPerson()) {
                        int width = event.getWindow().getGuiScaledWidth();
                        int height = event.getWindow().getGuiScaledHeight();
                        int barWidth = (int) (cooldownTimer / cooldown * 14);
                        int barHeight = 4;
                        int x = width - 328;
                        int y = height - 175;

                        guiGraphics.blit(TELEPORT_BAR_LOCATION, x, y, 0, 8, 15, barHeight, 15, 3);

                        int middleBarHeight = 1;
                        int middleBarStartY = y + (barHeight - middleBarHeight) / 2;

                        guiGraphics.blit(FILL_BAR_LOCATION, x + 14 - barWidth, middleBarStartY, 0, 0, barWidth, middleBarHeight, 15, 1);
                    }
                }
            }
        }
    }
}
