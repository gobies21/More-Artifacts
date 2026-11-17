package net.gobies.moreartifacts.client.renderer.gui;

import net.gobies.moreartifacts.compat.firstaid.FirstAidCompat;
import net.gobies.moreartifacts.util.BrokenHeartSystem;
import net.gobies.moreartifacts.util.ModLoadedUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.ForgeGui;

import java.util.Arrays;
import java.util.function.BooleanSupplier;

public class HeartRenderer {

    private int[] x;
    private int[] y;
    private int[] u;
    private int[] v;

    private float[] glowIntensity;
    private boolean[] visible;
    public static int healthRows;
    public final RandomSource random = RandomSource.create();

    public HeartRenderer(int maxHearts) {
        int max = Math.max(maxHearts, 1);

        x = new int[max];
        y = new int[max];
        u = new int[max];
        v = new int[max];

        glowIntensity = new float[max];
        visible = new boolean[max];
    }

    public void prepare(int size) {
        checkMaxHearts(size);

        Arrays.fill(visible, 0, size, false);
        Arrays.fill(glowIntensity, 0, size, 0.0F);
    }

    public void setHeart(int index, int x, int y, int u, int v) {
        checkMaxHearts(index + 1);

        this.x[index] = x;
        this.y[index] = y;
        this.u[index] = u;
        this.v[index] = v;

        visible[index] = true;
    }

    public void setGlow(int index, float intensity) {
        glowIntensity[index] = intensity;
    }

    public boolean isVisible(int index) {
        return visible[index];
    }

    public int getX(int index) {
        return x[index];
    }

    public int getY(int index) {
        return y[index];
    }

    public int getU(int index) {
        return u[index];
    }

    public int getV(int index) {
        return v[index];
    }

    public float getGlowIntensity(int index) {
        return glowIntensity[index];
    }

    private void checkMaxHearts(int size) {
        if (size <= x.length) {
            return;
        }

        int capacity = Math.max(size, x.length * 2);

        x = Arrays.copyOf(x, capacity);
        y = Arrays.copyOf(y, capacity);
        u = Arrays.copyOf(u, capacity);
        v = Arrays.copyOf(v, capacity);

        glowIntensity = Arrays.copyOf(glowIntensity, capacity);
        visible = Arrays.copyOf(visible, capacity);
    }

    public boolean shouldSkipRendering(Minecraft mc, ForgeGui forgeGui, BooleanSupplier config) {
        if (mc.options.hideGui || !forgeGui.shouldDrawSurvivalElements()) {
            return true;
        }
        if (!config.getAsBoolean()) {
            return true;
        }
        Player player = mc.player;
        if (player == null) {
            return true;
        }
        if (ModLoadedUtil.isFirstAidLoaded()) {
            if (!FirstAidCompat.isVanillaHealthBarActive()) {
                return true;
            }
        }
        return player.isCreative() || player.isSpectator();
    }


    // FIX BROKEN HEART REDUCING ACTIVE HEALTH ROWS, CANT RECREATE???
    // temp fix for broken hearts conflicting with other heart overlays, will use a more global fix in the future
    public int getBrokenHearts(Player player) {
        AttributeModifier modifier = BrokenHeartSystem.getBrokenHeartModifier(player);
        if (modifier == null) {
            return 0;
        }

        float brokenAmount = (float) Math.abs(modifier.getAmount());

        return (int) (brokenAmount * 0.5F);
    }
}