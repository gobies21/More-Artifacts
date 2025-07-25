package net.gobies.moreartifacts.mixin;

import com.oblivioussp.spartanweaponry.item.HeavyCrossbowItem;
import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.util.CurioHandler;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeavyCrossbowItem.class)
public class HeavyCrossbowItemMixin {
    @Inject(
            method = "releaseUsing",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;shrink(I)V"),
            cancellable = true
    )
    private void quiverSaveBolt(ItemStack stack, Level levelIn, LivingEntity entityLiving, int timeLeft, CallbackInfo ci) {
        if (CurioHandler.isCurioEquipped(entityLiving, MAItems.MagicQuiver.get())) {
            if (entityLiving.getRandom().nextFloat() < CommonConfig.MAGIC_QUIVER_AMMO.get()) {
                ci.cancel();
            }
        }
        if (CurioHandler.isCurioEquipped(entityLiving, MAItems.EnvenomedQuiver.get())) {
            if (entityLiving.getRandom().nextFloat() < CommonConfig.ENVENOMED_QUIVER_AMMO.get()) {
                ci.cancel();
            }
        }
        if (CurioHandler.isCurioEquipped(entityLiving, MAItems.MoltenQuiver.get())) {
            if (entityLiving.getRandom().nextFloat() < CommonConfig.MOLTEN_QUIVER_AMMO.get()) {
                ci.cancel();
            }
        }
    }
}
