package net.gobies.moreartifacts.client.overlay;

import net.gobies.moreartifacts.config.ClientConfig;
import net.gobies.moreartifacts.util.DodgeProperties;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class DodgeOverlay {

    private static final ResourceLocation GUI_ICONS = new ResourceLocation("textures/gui/icons.png");

    @SubscribeEvent
    public static void onRenderXPBar(RenderGuiOverlayEvent.Pre event) {
        if (!ClientConfig.DODGE_OVERLAY.get()) return;
        if (!event.getOverlay().id().equals(VanillaGuiOverlay.EXPERIENCE_BAR.id())) return;

        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) return;

        ItemStack dodgeItem = DodgeProperties.getDodgeItem(player);
        if (dodgeItem.isEmpty() || !player.getCooldowns().isOnCooldown(dodgeItem.getItem())) return;

        event.setCanceled(true);

        int width = event.getWindow().getGuiScaledWidth();
        int height = event.getWindow().getGuiScaledHeight();

        float percent = player.getCooldowns().getCooldownPercent(dodgeItem.getItem(), event.getPartialTick());
        renderCooldownBar(event.getGuiGraphics(), width / 2 - 91, height - 29, 1.0F - percent);

        if ((!player.isCreative() && !player.isSpectator()) && player.experienceLevel > 0) {
            renderExperienceNumber(event.getGuiGraphics(), mc.font, player.experienceLevel, width, height);
        }
    }

    private static void renderCooldownBar(GuiGraphics graphics, int x, int y, float chargeRate) {
        graphics.blit(GUI_ICONS, x, y, 0, 84, 182, 5);

        if (chargeRate > 0.0F) {
            int width = Math.min((int) (chargeRate * 182.0F), 182);
            graphics.blit(GUI_ICONS, x, y, 0, 89, width, 5);
        }
    }

    private static void renderExperienceNumber(GuiGraphics graphics, Font font, int level, int screenWidth, int screenHeight) {
        String number = String.valueOf(level);
        int textX = (screenWidth - font.width(number)) / 2;
        int textY = screenHeight - 35;

        var buffer = graphics.bufferSource();
        var matrix = graphics.pose().last().pose();

        draw(font, number, textX + 1, textY, 0, matrix, buffer);
        draw(font, number, textX - 1, textY, 0, matrix, buffer);
        draw(font, number, textX, textY + 1, 0, matrix, buffer);
        draw(font, number, textX, textY - 1, 0, matrix, buffer);

        draw(font, number, textX, textY, 8453920, matrix, buffer);
    }

    private static void draw(Font font, String text, int x, int y, int color, Matrix4f matrix, MultiBufferSource buffer) {
        font.drawInBatch(text, x, y, color, false, matrix, buffer, Font.DisplayMode.NORMAL, 0, 15728880);
    }
}