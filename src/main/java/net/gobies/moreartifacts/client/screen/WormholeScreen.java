package net.gobies.moreartifacts.client.screen;

import net.gobies.moreartifacts.network.PacketHandler;
import net.gobies.moreartifacts.network.SelectWormholeTargetPacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class WormholeScreen extends Screen {
    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 20;
    private static final int NAME_SPACING = 24;
    private static final int START_Y = 40;
    private static final int SCROLL_SPEED = 16;

    private static final int VIEW_BOUNDS_TOP = 35;
    private static final int VIEW_BOUNDS_OFFSET_BOTTOM = 15;
    private static final int TRACK_PADDING = 5;
    private static final int TRACK_WIDTH = 4;

    private static final int TEXT_COLOR = 0xFFFFFF;
    private static final int BAR_COLOR = 0xFF808080;
    private static final int TRACK_COLOR = 0x33FFFFFF;

    private final List<String> players;
    private final List<Button> playerButtons = new ArrayList<>();

    private double scrollAmount = 0;
    private int maxScroll = 0;
    private boolean isDraggingScrollbar = false;

    private int scrollbarX;
    private int trackHeight;
    private int barHeight;

    public WormholeScreen(List<String> players) {
        super(Component.translatable("gui.moreartifacts.wormhole.title"));
        this.players = players;
    }

    @Override
    protected void init() {
        super.init();
        this.playerButtons.clear();
        this.scrollAmount = 0;
        this.isDraggingScrollbar = false;

        int startX = (this.width - BUTTON_WIDTH) / 2;

        for (int i = 0; i < this.players.size(); i++) {
            String targetName = this.players.get(i);
            Button button = Button.builder(Component.literal(targetName), b -> {
                PacketHandler.sendToServer(new SelectWormholeTargetPacket(targetName));
                this.onClose();
            }).bounds(startX, START_Y + (i * NAME_SPACING), BUTTON_WIDTH, BUTTON_HEIGHT).build();

            this.playerButtons.add(button);
            this.addRenderableWidget(button);
        }

        int totalHeight = this.players.size() * NAME_SPACING;
        int viewHeight = this.height - START_Y - 20;
        this.maxScroll = Math.max(0, totalHeight - viewHeight);

        this.scrollbarX = (this.width / 2) + (BUTTON_WIDTH / 2) + 5;
        int viewBoundsBottom = this.height - VIEW_BOUNDS_OFFSET_BOTTOM;
        this.trackHeight = viewBoundsBottom - VIEW_BOUNDS_TOP - (TRACK_PADDING * 2);
        this.barHeight = Math.max(20, (this.trackHeight * this.trackHeight) / Math.max(1, this.players.size() * NAME_SPACING));
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics);

        int viewBoundsBottom = this.height - VIEW_BOUNDS_OFFSET_BOTTOM;
        int viewBoundsWidth = Math.max(1, this.width);
        guiGraphics.enableScissor(0, VIEW_BOUNDS_TOP, viewBoundsWidth, viewBoundsBottom);

        for (int i = 0; i < this.playerButtons.size(); i++) {
            Button button = this.playerButtons.get(i);
            int currentY = (int) (START_Y + (i * NAME_SPACING) - this.scrollAmount);
            button.setY(currentY);
            button.visible = (currentY >= VIEW_BOUNDS_TOP - BUTTON_HEIGHT && currentY <= viewBoundsBottom);
        }

        super.render(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.disableScissor();

        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 15, TEXT_COLOR);

        if (this.maxScroll > 0) {
            int trackTop = VIEW_BOUNDS_TOP + TRACK_PADDING;
            int barY = trackTop + (int) ((this.scrollAmount / this.maxScroll) * (this.trackHeight - this.barHeight));

            guiGraphics.fill(this.scrollbarX, trackTop, this.scrollbarX + TRACK_WIDTH, viewBoundsBottom - TRACK_PADDING, TRACK_COLOR);
            guiGraphics.fill(this.scrollbarX, barY, this.scrollbarX + TRACK_WIDTH, barY + this.barHeight, BAR_COLOR);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.maxScroll > 0 && button == 0) {
            int viewBoundsBottom = this.height - VIEW_BOUNDS_OFFSET_BOTTOM;
            int trackTop = VIEW_BOUNDS_TOP + TRACK_PADDING;
            int trackBottom = viewBoundsBottom - TRACK_PADDING;
            int barY = trackTop + (int) ((this.scrollAmount / this.maxScroll) * (this.trackHeight - this.barHeight));

            if (mouseX >= this.scrollbarX && mouseX <= this.scrollbarX + TRACK_WIDTH && mouseY >= trackTop && mouseY <= trackBottom) {
                this.isDraggingScrollbar = true;
                if (mouseY < barY || mouseY > barY + this.barHeight) {
                    updateScrollFromMouse(mouseY);
                }
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (this.maxScroll > 0 && this.isDraggingScrollbar && button == 0) {
            updateScrollFromMouse(mouseY);
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) {
            this.isDraggingScrollbar = false;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        this.scrollAmount = Mth.clamp(this.scrollAmount - (delta * SCROLL_SPEED), 0, this.maxScroll);
        return true;
    }

    private void updateScrollFromMouse(double mouseY) {
        int trackTop = VIEW_BOUNDS_TOP + TRACK_PADDING;
        float progress = (float) (mouseY - trackTop - (this.barHeight / 2.0F)) / (float) (this.trackHeight - this.barHeight);
        this.scrollAmount = Mth.clamp(progress * this.maxScroll, 0, this.maxScroll);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}