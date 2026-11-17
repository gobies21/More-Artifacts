package net.gobies.moreartifacts.mixin;

import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.util.CurioHandler;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {

    @Inject(
            method = "getSneakingSpeedBonus(Lnet/minecraft/world/entity/LivingEntity;)F",
            at = @At("RETURN"),
            cancellable = true
    )
    private static void adjustSneakSpeed(LivingEntity entity, CallbackInfoReturnable<Float> cir) {
        if (CurioHandler.isCurioEquipped(entity, MAItems.StealthManual.get())) {
            cir.setReturnValue(cir.getReturnValue() + CommonConfig.STEALTH_MANUAL_SNEAK_SPEED.get().floatValue());
        } else if (CurioHandler.isCurioEquipped(entity, MAItems.RogueBelt.get())) {
            cir.setReturnValue(cir.getReturnValue() + CommonConfig.ROGUE_BELT_SNEAK_SPEED.get().floatValue());
        }
    }
}