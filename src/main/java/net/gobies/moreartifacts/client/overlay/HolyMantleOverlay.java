package net.gobies.moreartifacts.client.overlay;

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
public class HolyMantleOverlay {

    private static final ResourceLocation ICON = new ResourceLocation("moreartifacts", "textures/gui/holy_mantle.png");
    private static final String ARTIFACT_ID = "holy_mantle";

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) return;
        if (!CurioHandler.isCurioEquipped(player, MAItems.HolyMantle.get())) return;
        GuiGraphics guiGraphics = event.getGuiGraphics();
        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();

        int x = screenWidth / 2 - 93;
        int y = screenHeight - 52;

        double cooldown = CommonConfig.HOLY_MANTLE_COOLDOWN.get();
        boolean isReady = CooldownHandler.isReady(player, ARTIFACT_ID, cooldown);
        if (isReady && !player.isCreative() && !player.isSpectator()) {
            float scale = 0.50f;
            guiGraphics.pose().pushPose();

            guiGraphics.pose().translate(x + 6, y + 11.5, 0);
            guiGraphics.pose().scale(scale, scale, 1.0f);

            guiGraphics.blit(ICON, 0, 0, 0, 0, 19, 22, 19, 22);
            guiGraphics.pose().popPose();
        }
    }
}