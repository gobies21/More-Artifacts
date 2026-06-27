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
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class HolyMantleOverlay {

    private static final ResourceLocation ICON = new ResourceLocation("moreartifacts", "textures/gui/holy_mantle.png");
    private static final String ARTIFACT_ID = "holy_mantle";

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Post event) {
        if (event.getOverlay() != VanillaGuiOverlay.HOTBAR.type()) return;
        if (!ClientConfig.HOLY_MANTLE_OVERLAY.get()) return;
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null || player.isCreative() || player.isSpectator()) return;

        if (!CurioHandler.isCurioEquipped(player, MAItems.HolyMantle.get())) return;

        double cooldown = CommonConfig.HOLY_MANTLE_COOLDOWN.get();
        if (CooldownHandler.isReady(player, ARTIFACT_ID, cooldown)) {
            GuiGraphics guiGraphics = event.getGuiGraphics();
            int screenWidth = event.getWindow().getGuiScaledWidth();
            int screenHeight = event.getWindow().getGuiScaledHeight();

            int x = screenWidth / 2 - 93;
            int y = screenHeight - 52;

            guiGraphics.blit(ICON, x - 6, y + 11, 8, 10, 0, 0, 11, 14, 11, 14);
        }
    }
}