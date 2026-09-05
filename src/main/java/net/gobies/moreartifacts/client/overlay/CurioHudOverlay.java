package net.gobies.moreartifacts.client.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import net.gobies.moreartifacts.config.ClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class CurioHudOverlay {
    private static final ItemStack[] ITEM_BUFFER = new ItemStack[32];

    public static final IGuiOverlay CURIO_HUD = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        if (!ClientConfig.ENABLE_CURIO_HUD.get()) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.options.hideGui || mc.player == null) return;
        if (mc.player.isSpectator()) return;

        var lazyOptional = CuriosApi.getCuriosInventory(mc.player);
        if (!lazyOptional.isPresent()) return;

        ICuriosItemHandler handler = lazyOptional.orElseThrow(IllegalStateException::new);

        int maxAllowed = ClientConfig.MAX_CURIOS.get();
        int totalItemsToDraw = fillItemBuffer(handler, maxAllowed);
        if (totalItemsToDraw == 0) return;

        boolean isVertical = ClientConfig.RENDER_VERTICALLY.get();
        float scale = ClientConfig.HUD_SCALE.get().floatValue();

        int baseRightX = (screenWidth - 20) - ClientConfig.OFFSET_X.get();
        int baseY = (screenHeight - 20) - ClientConfig.OFFSET_Y.get();
        int spacing = ClientConfig.CURIO_SPACING.get();

        if (!isVertical) {
            int rowWidth = Math.round((totalItemsToDraw - 1) * spacing * scale);
            int leftX = baseRightX - rowWidth;
            int hotbar = screenWidth / 2 + 91;
            if (leftX < hotbar) return;
        }

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        guiGraphics.pose().pushPose();

        boolean flipOrder = ClientConfig.FLIP_RENDER_ORDER.get();

        for (int i = 0; i < totalItemsToDraw; i++) {
            ItemStack stack = ITEM_BUFFER[i];
            int positionIndex = flipOrder ? i : (totalItemsToDraw - 1 - i);
            int spacingOffset = Math.round(positionIndex * spacing * scale);

            int x = baseRightX;
            int y = baseY;

            if (isVertical) {
                y -= spacingOffset;
            } else {
                x -= spacingOffset;
            }

            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(x, y, 0);
            guiGraphics.pose().scale(scale, scale, 1.0F);

            renderItemIcon(guiGraphics, mc, stack);

            guiGraphics.pose().popPose();
        }

        guiGraphics.pose().popPose();
        RenderSystem.disableBlend();
    };

    private static int fillItemBuffer(ICuriosItemHandler handler, int maxCurios) {
        int count = 0;
        for (int i = 0; i < 32; i++) {
            ITEM_BUFFER[i] = ItemStack.EMPTY;
        }
        for (ICurioStacksHandler stacksHandler : handler.getCurios().values()) {
            IDynamicStackHandler stackHandler = stacksHandler.getStacks();
            for (int i = 0; i < stackHandler.getSlots(); i++) {
                if (count >= maxCurios || count >= 32) {
                    return count;
                }
                ItemStack stack = stackHandler.getStackInSlot(i);
                if (!stack.isEmpty()) {
                    ITEM_BUFFER[count] = stack;
                    count++;
                }
            }
        }
        return count;
    }

    // Needs performance/detection improvements
    private static void renderItemIcon(GuiGraphics guiGraphics, Minecraft mc, ItemStack stack) {
        BakedModel model = mc.getItemRenderer().getModel(stack, null, null, 0);
        boolean simpleSprite = (!model.isGui3d() && !model.isCustomRenderer() && !stack.hasFoil());

        if (simpleSprite) {
            TextureAtlasSprite sprite = model.getParticleIcon(ModelData.EMPTY);
            guiGraphics.blit(0, 0, 0, 16, 16, sprite);
        } else {
            guiGraphics.renderItem(stack, 0, 0);
        }
    }
}