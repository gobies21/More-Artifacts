package net.gobies.moreartifacts.mixin;

import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.util.CurioHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin {

    @Inject(
            method = "makeStuckInBlock",
            at = @At("HEAD"),
            cancellable = true
    )
    private void cobwebImmunity(BlockState state, Vec3 motionFactor, CallbackInfo ci) {
        Player player = (Player) (Object) this;
        if (CurioHandler.isCurioEquipped(player, MAItems.StealthManual.get())) {
            if (state.is(Blocks.COBWEB)) {
                ci.cancel();
            }
        }
    }
}