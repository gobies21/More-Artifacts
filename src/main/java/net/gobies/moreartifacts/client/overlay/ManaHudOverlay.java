package net.gobies.moreartifacts.client.overlay;

import net.gobies.moreartifacts.MoreArtifacts;
import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.helper.SoulType;
import net.gobies.moreartifacts.util.ManaUtil;
import net.gobies.moreartifacts.util.ModLoadedUtil;
import net.gobies.moreartifacts.util.SoulUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ManaHudOverlay {

    public static float clientMana;

    public static void setClientMana(float mana) {
        clientMana = mana;
    }

    private static final ResourceLocation MANA_HUD = new ResourceLocation(MoreArtifacts.MOD_ID, "textures/gui/mana_hud.png");
    private static final ResourceLocation SOUL_MANA_HUD = new ResourceLocation(MoreArtifacts.MOD_ID, "textures/gui/soul_mana_hud.png");

    private static final float SCALE = 2.0F;
    private static final float TEXT_SCALE = 1.0F;

    private static final int BAR_X = 6;
    private static final int BAR_Y = 29;

    private static final int BAR_WIDTH = Math.round(34 * SCALE);
    private static final int BAR_HEIGHT = Math.round(12 * SCALE);

    private static final int FILL_X = 3;
    private static final int FILL_Y = 3;
    private static final int FILL_U = 3;
    private static final int FILL_V = 13;

    private static final int FILL_WIDTH = 28;
    private static final int FILL_HEIGHT = 6;

    private static final int TEXTURE_WIDTH = 34;
    private static final int TEXTURE_HEIGHT = 19;

    private static final int TEXT_COLOR = Objects.requireNonNullElse(ChatFormatting.AQUA.getColor(), 0xFFFFFF);
    private static final int SOUL_TEXT_COLOR = Objects.requireNonNullElse(ChatFormatting.LIGHT_PURPLE.getColor(), 0xFF55FF);

    private static int lastManaText = Integer.MIN_VALUE;
    private static int lastMaxManaText = Integer.MIN_VALUE;

    private static String textHolder = "";
    private static int textWidth;

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Post event) {
        if (event.getOverlay().id() != VanillaGuiOverlay.HOTBAR.id()) return;
        if (ModLoadedUtil.isIronsSpellbooksLoaded() && CommonConfig.IRONS_SPELLBOOKS_MANA_COMPAT.get()) return;
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (player == null || player.isSpectator() || mc.options.hideGui) return;
        float maxMana = ManaUtil.getMaxMana(player);
        if (maxMana <= 0.0F || clientMana >= maxMana) return;

        boolean isEnvy = SoulUtil.hasSoul(player, SoulType.ENVY);
        ResourceLocation manaTexture = isEnvy ? SOUL_MANA_HUD : MANA_HUD;
        int textColor = isEnvy ? SOUL_TEXT_COLOR : TEXT_COLOR;

        GuiGraphics graphics = event.getGuiGraphics();
        Font font = mc.font;

        int y = event.getWindow().getGuiScaledHeight() - BAR_Y;

        float manaPercent = Mth.clamp(clientMana / maxMana, 0.0F, 1.0F);

        int manaText = (int) clientMana;
        int maxManaText = (int) maxMana;

        if (manaText != lastManaText || maxManaText != lastMaxManaText) {
            textHolder = manaText + "/" + maxManaText;
            textWidth = font.width(textHolder);
            lastManaText = manaText;
            lastMaxManaText = maxManaText;
        }

        renderManaBar(graphics, manaTexture, y, manaPercent);
        graphics.pose().pushPose();
        graphics.pose().translate(BAR_X + BAR_WIDTH / 2.0F, y + BAR_HEIGHT / 2.0F, 0.0F);
        graphics.pose().scale(TEXT_SCALE, TEXT_SCALE, 1.0F);
        graphics.drawString(font, textHolder, -textWidth / 2, -font.lineHeight / 2, textColor, true);
        graphics.pose().popPose();
    }

    private static void renderManaBar(GuiGraphics graphics, ResourceLocation texture, int y, float manaPercent) {
        int fillX = BAR_X + Math.round(FILL_X * SCALE);
        int fillY = y + Math.round(FILL_Y * SCALE);

        graphics.blit(texture, BAR_X, y, BAR_WIDTH, BAR_HEIGHT, 0, 0, 34, 12, TEXTURE_WIDTH, TEXTURE_HEIGHT);

        int sourceWidth = Mth.clamp((int) Math.ceil(FILL_WIDTH * manaPercent), 0, FILL_WIDTH);

        int fillWidth = Math.round(sourceWidth * SCALE);
        int fillHeight = Math.round(FILL_HEIGHT * SCALE);

        if (sourceWidth > 0) {
            graphics.blit(texture, fillX, fillY, fillWidth, fillHeight, FILL_U, FILL_V, sourceWidth, FILL_HEIGHT, TEXTURE_WIDTH, TEXTURE_HEIGHT);
        }
    }
}