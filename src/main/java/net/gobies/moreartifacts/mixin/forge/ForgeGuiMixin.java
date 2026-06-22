package net.gobies.moreartifacts.mixin.forge;

import net.gobies.moreartifacts.config.ClientConfig;
import net.gobies.moreartifacts.util.BrokenHeartSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ForgeGui.class)
public abstract class ForgeGuiMixin {

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
        if (ClientConfig.BROKEN_HEART_OVERLAY.get()) {
            Minecraft mc = Minecraft.getInstance();
            Player player = mc.player;
            if (player == null) return original;
            AttributeModifier modifier = BrokenHeartSystem.getBrokenHeartModifier(player);
            if (modifier == null) return original;

            float brokenHearts = (float) Math.abs(modifier.getAmount());
            float unscaled = original * 20.0F;
            float adjusted = unscaled + brokenHearts;
            return adjusted / 20.0F;
        }
        return original;
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