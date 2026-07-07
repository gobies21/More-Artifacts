package net.gobies.moreartifacts.client.overlay;

import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.network.SoulSyncPacket;
import net.gobies.moreartifacts.util.SoulUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.client.gui.CuriosScreen;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class SoulOverlay {

    @SubscribeEvent
    public static void onRenderScreen(ScreenEvent.Render.Post event) {
        var screen = event.getScreen();
        if (!(screen instanceof AbstractContainerScreen<?> containerScreen)) return;

        boolean isVanillaInventory = containerScreen instanceof InventoryScreen;
        boolean isCuriosInventory = containerScreen instanceof CuriosScreen;

        if (!isVanillaInventory && !isCuriosInventory) return;

        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) return;

        String activeSoul = SoulSyncPacket.SoulClientData.getActiveSoul();
        if (activeSoul.isEmpty() || "Unknown".equals(activeSoul)) return;

        GuiGraphics guiGraphics = event.getGuiGraphics();

        int renderX = containerScreen.getGuiLeft() + 156 ;
        int renderY = containerScreen.getGuiTop() + 4;

        int mouseX = event.getMouseX();
        int mouseY = event.getMouseY();

        ItemStack activeSoulStack = ItemStack.EMPTY;

        if (SoulUtil.SHADOW.equals(activeSoul)) {
            activeSoulStack = new ItemStack(MAItems.ShadowSoul.get());
        } else if (SoulUtil.BLOOD.equals(activeSoul)) {
            activeSoulStack = new ItemStack(MAItems.BloodSoul.get());
        }

        if (!activeSoulStack.isEmpty()) {
            activeSoulStack.getOrCreateTag().putInt("SoulStage", 5);
            guiGraphics.renderItem(activeSoulStack, renderX, renderY);
            guiGraphics.renderItemDecorations(mc.font, activeSoulStack, renderX, renderY);
            if (mouseX >= renderX && mouseX < renderX + 16 && mouseY >= renderY && mouseY < renderY + 16) {
                List<Component> rawTooltip = activeSoulStack.getTooltipLines(player, TooltipFlag.Default.NORMAL);
                List<Component> filteredTooltip = new ArrayList<>();

                for (Component line : rawTooltip) {
                    String plainText = line.getString();

                    // Skip unnecessary tooltips
                    if (plainText.startsWith("Slot:") || plainText.contains("Slot: Body")) continue;
                    if (plainText.contains("When on") || plainText.contains("When worn")) {
                        filteredTooltip.add(Component.literal("§6When intertwined with soul:"));
                        continue;
                    }
                    if (plainText.contains("More Artifacts")) continue;

                    filteredTooltip.add(line);
                }

                guiGraphics.renderComponentTooltip(mc.font, filteredTooltip, mouseX, mouseY);
            }
        }
    }
}