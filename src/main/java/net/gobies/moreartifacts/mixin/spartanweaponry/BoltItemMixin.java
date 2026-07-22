package net.gobies.moreartifacts.mixin.spartanweaponry;

import com.oblivioussp.spartanweaponry.item.BoltItem;
import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.util.CurioHandler;
import net.gobies.moreartifacts.util.LuckHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoltItem.class)
public class BoltItemMixin {
    @Inject(
            method = "isInfinite",
            at = @At("HEAD"),
            remap = false,
            cancellable = true
    )
    private void quiverSaveBolt(ItemStack stack, ItemStack crossbow, Player player, CallbackInfoReturnable<Boolean> cir) {
        if (CurioHandler.isCurioEquipped(player, MAItems.MagicQuiver.get())) {
            double saveChance = CommonConfig.MAGIC_QUIVER_AMMO.get();
            if (LuckHelper.roll(player, saveChance, CommonConfig.MAGIC_QUIVER_LUCK_FACTOR.get())) {
                cir.setReturnValue(true);
                return;
            }
        }
        if (CurioHandler.isCurioEquipped(player, MAItems.EnvenomedQuiver.get())) {
            double saveChance = CommonConfig.ENVENOMED_QUIVER_AMMO.get();
            if (LuckHelper.roll(player, saveChance, CommonConfig.ENVENOMED_QUIVER_LUCK_FACTOR.get())) {
                cir.setReturnValue(true);
                return;
            }
        }
        if (CurioHandler.isCurioEquipped(player, MAItems.MoltenQuiver.get())) {
            double saveChance = CommonConfig.MOLTEN_QUIVER_AMMO.get();
            if (LuckHelper.roll(player, saveChance, CommonConfig.MOLTEN_QUIVER_LUCK_FACTOR.get())) {
                cir.setReturnValue(true);
                return;
            }
        }
        if (CurioHandler.isCurioEquipped(player, MAItems.FrozenQuiver.get())) {
            double saveChance = CommonConfig.FROZEN_QUIVER_AMMO.get();
            if (LuckHelper.roll(player, saveChance, CommonConfig.FROZEN_QUIVER_LUCK_FACTOR.get())) {
                cir.setReturnValue(true);
            }
        }
    }
}
