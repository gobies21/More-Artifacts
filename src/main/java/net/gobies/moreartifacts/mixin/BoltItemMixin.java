package net.gobies.moreartifacts.mixin;

import com.oblivioussp.spartanweaponry.item.BoltItem;
import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.util.CurioHandler;
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
        boolean saveArrow = false;
        if (CurioHandler.isCurioEquipped(player, MAItems.MagicQuiver.get())) {
            if (player.getRandom().nextFloat() < CommonConfig.MAGIC_QUIVER_AMMO.get()) {
                saveArrow = true;
            }
        }
        if (CurioHandler.isCurioEquipped(player, MAItems.EnvenomedQuiver.get())) {
            if (player.getRandom().nextFloat() < CommonConfig.ENVENOMED_QUIVER_AMMO.get()) {
                saveArrow = true;
            }
        }
        if (CurioHandler.isCurioEquipped(player, MAItems.MoltenQuiver.get())) {
            if (player.getRandom().nextFloat() < CommonConfig.MOLTEN_QUIVER_AMMO.get()) {
                saveArrow = true;
            }
        }
        cir.setReturnValue(saveArrow);
    }
}
