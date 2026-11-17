package net.gobies.moreartifacts.mixin.neat;

import net.gobies.moreartifacts.helper.SoulType;
import net.gobies.moreartifacts.util.SoulUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.*;
import vazkii.neat.HealthBarRenderer;

@Mixin(HealthBarRenderer.class)
public class HealthBarRendererMixin {

    @Invoker("getColor")
    private static int getColor(LivingEntity entity, boolean colorByType, boolean boss) {
        throw new AssertionError();
    }

    @Redirect(
            method = "hookRender",
            at = @At(
                    value = "INVOKE",
                    target = "Lvazkii/neat/HealthBarRenderer;getColor(Lnet/minecraft/world/entity/LivingEntity;ZZ)I"
            ),
            remap = false
    )
    private static int changeBarColor(LivingEntity entity, boolean colorByType, boolean boss) {
        if (entity instanceof Player player) {
            boolean manaShieldActive = SoulUtil.hasActiveSoul(player, SoulType.ENVY);
            if (manaShieldActive) {
                return 0xFFA133FF;
            }
        }
        return getColor(entity, colorByType, boss);
    }
}