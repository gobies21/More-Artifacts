package net.gobies.moreartifacts.client.overlay;

import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.util.ManaUtil;
import net.gobies.moreartifacts.util.ModLoadedUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ManaHudOverlay {

    public static float mana = 200.0f;

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Post event) {
        if (ModLoadedUtil.isIronsSpellbooksLoaded() && CommonConfig.IRONS_SPELLBOOKS_MANA_COMPAT.get()) return;
        if (event.getOverlay().id() != VanillaGuiOverlay.HOTBAR.id()) return;

        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null || player.isCreative() || player.isSpectator() || mc.options.hideGui) return;

        float maxMana = ManaUtil.getMana(player);

        if (mana >= maxMana) return;

        GuiGraphics graphics = event.getGuiGraphics();
        Font font = mc.font;

        int x = 4;
        int y = event.getWindow().getGuiScaledHeight() - 15;
        int width = 55;
        int height = 11;

        int filledWidth = 0;
        if (maxMana > 0f) {
            filledWidth = (int) (width * (mana / maxMana));
        }

        graphics.fill(x - 1, y - 1, x + width + 1, y + height + 1, 0xFF000000);
        graphics.fill(x, y, x + width, y + height, 0xAA222222);

        if (filledWidth > 0) {
            graphics.fill(x, y, x + filledWidth, y + height, 0xFF4169E1);
        }

        String text = String.format("%.0f/%.0f", mana, maxMana);
        int textX = x + (width - font.width(text)) / 2;
        int textY = y + (height - 6) / 2;

        graphics.drawString(font, text, textX, textY, 0xFFFFFFFF, true);
    }
}