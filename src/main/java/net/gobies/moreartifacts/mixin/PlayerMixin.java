package net.gobies.moreartifacts.mixin;

import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.helper.SoulType;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.init.MASounds;
import net.gobies.moreartifacts.util.CurioHandler;
import net.gobies.moreartifacts.util.SoulUtil;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin {

    @Inject(
            method = "makeStuckInBlock",
            at = @At("HEAD"),
            cancellable = true
    )
    private void cobwebImmunity(BlockState state, Vec3 motionFactor, CallbackInfo ci) {
        Player player = (Player) (Object) this;
        if (CurioHandler.isCurioEquipped(player, MAItems.StealthManual.get()) || CurioHandler.isCurioEquipped(player, MAItems.RogueBelt.get())) {
            if (state.is(Blocks.COBWEB)) {
                ci.cancel();
            }
        }
    }

    @Inject(
            method = "getHurtSound",
            at = @At("HEAD"),
            cancellable = true
    )
    private void replaceHurtSound(DamageSource damageSource, CallbackInfoReturnable<@NotNull SoundEvent> cir) {
        if (!CommonConfig.MANA_SHIELD_SOUNDS.get()) return;
        Player player = (Player) (Object) this;
        boolean manaShieldActive = SoulUtil.hasActiveSoul(player, SoulType.ENVY);
        if (manaShieldActive) {
            cir.setReturnValue(MASounds.MANA_SHIELD_HURT.get());
        }
    }
}