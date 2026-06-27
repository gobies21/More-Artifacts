package net.gobies.moreartifacts.mixin;

import net.minecraft.world.entity.monster.EnderMan;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderMan.class)
public abstract class EnderManMixin {
    /*
    @Inject(
            method = "registerGoals",
            at = @At("TAIL")
    )
    private void injectFriendlyGoal(CallbackInfo ci) {
        EnderMan enderMan = (EnderMan) (Object) this;
        enderMan.goalSelector.addGoal(3, new FriendlyEndermanGoal(enderMan));
    }

     */

}