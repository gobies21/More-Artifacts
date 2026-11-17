package net.gobies.moreartifacts.mixin.spellbooks;

import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import net.gobies.moreartifacts.compat.apothecary.ApothecaryCompat;
import net.gobies.moreartifacts.helper.SoulType;
import net.gobies.moreartifacts.util.SoulUtil;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MagicManager.class)
public class MagicManagerMixin {

    @Inject(
            method = "regenPlayerMana",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private void regenPlayerMana(ServerPlayer serverPlayer, MagicData playerMagicData, CallbackInfoReturnable<Boolean> cir) {
        if (SoulUtil.hasSoul(serverPlayer, SoulType.ENVY) && serverPlayer.getAbilities().mayfly && serverPlayer.getAbilities().flying && !serverPlayer.isCreative() && !serverPlayer.hasEffect(ApothecaryCompat.flight())) {
            cir.setReturnValue(false);
        }
    }
}
