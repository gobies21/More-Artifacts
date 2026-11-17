package net.gobies.moreartifacts.mixin.forge;

import net.gobies.moreartifacts.client.renderer.gui.HeartRenderer;
import net.gobies.moreartifacts.config.ClientConfig;
import net.gobies.moreartifacts.util.BrokenHeartSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ForgeGui.class)
public abstract class ForgeGuiMixin {

    @ModifyVariable(
            method = "renderHealth",
            at = @At(
                    value = "STORE",
                    ordinal = 0
            ),
            name = "healthMax",
            remap = false
    )
    private float syncMaxHealth(float healthMax) {
        if (ClientConfig.BROKEN_HEART_OVERLAY.get()) {
            Minecraft mc = Minecraft.getInstance();
            Player player = mc.player;
            if (player == null) return healthMax;

            AttributeModifier modifier = BrokenHeartSystem.getBrokenHeartModifier(player);
            if (modifier == null || modifier.getAmount() == 0) return healthMax;

            return player.getMaxHealth();
        }
        return healthMax;
    }

    @ModifyArg(
            method = "renderHealth",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/Mth;ceil(F)I",
                    ordinal = 2
            ),
            index = 0
    )
    private float modifyHealthRows(float original) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) {
            HeartRenderer.healthRows = 0;
            return original;
        }

        float adjusted = original;

        if (ClientConfig.BROKEN_HEART_OVERLAY.get()) {
            AttributeModifier modifier = BrokenHeartSystem.getBrokenHeartModifier(player);

            if (modifier != null) {
                float brokenHearts = (float) Math.abs(modifier.getAmount());
                adjusted = (original * 20.0F + brokenHearts) / 20.0F;
            }
        }

        HeartRenderer.healthRows = Mth.ceil(adjusted);

        return adjusted;
    }

    @ModifyArg(
            method = "renderHealth",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraftforge/client/gui/overlay/ForgeGui;renderHearts(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/world/entity/player/Player;IIIIFIIIZ)V"
            ),
            index = 6
    )
    private float modifyAbsorptionHearts(float original) {
        if (ClientConfig.BROKEN_HEART_OVERLAY.get()) {
            Minecraft mc = Minecraft.getInstance();
            Player player = mc.player;
            if (player == null) return original;

            AttributeModifier modifier = BrokenHeartSystem.getBrokenHeartModifier(player);
            if (modifier == null) return original;
            return original + (float) Math.abs(modifier.getAmount());
        }
        return original;
    }
}