package net.gobies.moreartifacts.mixin;

import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.util.CurioHandler;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.creative.enhancedvisuals.common.handler.ExplosionHandler;

@Mixin(ExplosionHandler.class)
public class ExplosionHandlerMixin {
    @Inject(
            method =
            "onExploded",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    public void onExplosion(Player player, Vec3 pos, float size, Entity source, CallbackInfo ci) {
        if (CurioHandler.isCurioEquipped(player, MAItems.HeroShield.get()) && CommonConfig.HERO_SHIELD_COMPAT.get()) {
            ci.cancel();
        }
    }
}
