package net.gobies.moreartifacts.client.screen;

import net.gobies.moreartifacts.network.PacketHandler;
import net.gobies.moreartifacts.network.SelectWormholeTargetPacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WormholeScreen extends Screen {
    private final List<String> players;

    public WormholeScreen(List<String> players) {
        super(Component.translatable("gui.moreartifacts.wormhole.title"));
        this.players = players;
    }

    @Override
    protected void init() {
        super.init();

        final int buttonWidth = 200;
        final int buttonHeight = 20;
        final int spacing = 24;
        final int startX = (this.width - buttonWidth) / 2;
        final int startY = 40;

        int maxButtons = Math.min(this.players.size(), (this.height - startY - 20) / spacing);

        for (int i = 0; i < maxButtons; i++) {
            final String targetName = this.players.get(i);
            this.addRenderableWidget(Button.builder(Component.literal(targetName), button -> {
                PacketHandler.sendToServer(new SelectWormholeTargetPacket(targetName));
                this.onClose();
            }).bounds(startX, startY + (i * spacing), buttonWidth, buttonHeight).build());
        }
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics);
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 15, 0xFFFFFF);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}