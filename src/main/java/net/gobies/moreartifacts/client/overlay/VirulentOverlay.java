package net.gobies.moreartifacts.client.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import net.gobies.moreartifacts.MoreArtifacts;
import net.gobies.moreartifacts.init.MAEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class VirulentOverlay {

    private static final ResourceLocation PURPLE_HEARTS = new ResourceLocation(MoreArtifacts.MOD_ID, "textures/gui/purple_hearts.png");
    private static final ResourceLocation PURPLE_HEARTS_HARDCORE = new ResourceLocation(MoreArtifacts.MOD_ID, "textures/gui/purple_hearts_hardcore.png");
    private static final RandomSource random = RandomSource.create();

    private static int lastHealth = -1;
    private static long blinkTime = 0;

    public static final IGuiOverlay OVERLAY = (gui, guiGraphics, partialTick, width, height) -> {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (player == null || !player.hasEffect(MAEffects.Virulent.get())) {
            lastHealth = -1;
            blinkTime = 0;
            return;
        }

        int ticks = mc.gui.getGuiTicks();
        int health = Mth.ceil(player.getHealth());

        if (lastHealth == -1) {
            lastHealth = health;
        }

        if (health < lastHealth && player.invulnerableTime > 0) {
            blinkTime = ticks + 20;
        } else if (health > lastHealth && player.invulnerableTime > 0) {
            blinkTime = ticks + 10;
        }
        lastHealth = health;

        boolean isFlashing = blinkTime > (long) ticks && (blinkTime - (long) ticks) / 3L % 2L == 1L;

        AttributeInstance maxHealthAttr = player.getAttribute(Attributes.MAX_HEALTH);
        if (maxHealthAttr == null) return;

        float maxHealth = (float) maxHealthAttr.getValue();
        int absorption = Mth.ceil(player.getAbsorptionAmount());

        int totalRows = Mth.ceil((maxHealth + absorption) / 2.0F / 10.0F);
        int rowHeight = Math.max(10 - (totalRows - 2), 3);

        int left = width / 2 - 91;
        int top = height - 39;

        boolean isLowHealth = (player.getHealth() + player.getAbsorptionAmount()) <= 4.0F;
        int regen = -1;
        if (player.hasEffect(MobEffects.REGENERATION)) {
            regen = ticks % Mth.ceil(maxHealth + 5.0F);
        }

        int maxHearts = Mth.ceil((maxHealth + absorption) / 2.0F);

        ResourceLocation texture = mc.level != null && mc.level.getLevelData().isHardcore() ? PURPLE_HEARTS_HARDCORE : PURPLE_HEARTS;

        RenderSystem.enableBlend();
        RenderSystem.setShaderTexture(0, texture);

        for (int i = maxHearts - 1; i >= 0; --i) {
            int healthIndex = i * 2;

            if (healthIndex >= health) {
                continue;
            }

            int x = left + (i % 10) * 8;
            int y = top - (i / 10) * rowHeight;

            if (isLowHealth) {
                random.setSeed((long) ticks * 312871L);
                int shakeOffset = 0;
                for (int h = maxHearts - 1; h >= i; --h) {
                    shakeOffset = random.nextInt(2);
                }
                y += shakeOffset;
            }

            if (regen >= 0 && regen == i) {
                y -= 2;
            }

            int xOffset = (healthIndex + 1 == health) ? 9 : 0;
            int yOffset = isFlashing ? 9 : 0;

            guiGraphics.blit(texture, x, y, xOffset, yOffset, 9, 9, 18, 18);
        }

        RenderSystem.disableBlend();
    };
}