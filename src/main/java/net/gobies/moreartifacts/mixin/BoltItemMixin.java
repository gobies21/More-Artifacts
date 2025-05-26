package net.gobies.moreartifacts.mixin;

import com.oblivioussp.spartanweaponry.item.HeavyCrossbowItem;
import net.gobies.moreartifacts.Config;
import net.gobies.moreartifacts.item.MAItems;
import net.gobies.moreartifacts.util.CurioHandler;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeavyCrossbowItem.class)
public class BoltItemMixin {
    @Inject(
            method = "releaseUsing",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;shrink(I)V"),
            remap = false,
            cancellable = true)

    private void quiverSaveBolt(ItemStack stack, Level levelIn, LivingEntity entityLiving, int timeLeft, CallbackInfo ci) {
        if (CurioHandler.isCurioEquipped(entityLiving, MAItems.MagicQuiver.get())) {
            if (entityLiving.getRandom().nextFloat() < Config.MAGIC_QUIVER_AMMO.get()) {
                ci.cancel();
            }
        }
        if (CurioHandler.isCurioEquipped(entityLiving, MAItems.EnvenomedQuiver.get())) {
            if (entityLiving.getRandom().nextFloat() < Config.ENVENOMED_QUIVER_AMMO.get()) {
                ci.cancel();
            }
        }
        if (CurioHandler.isCurioEquipped(entityLiving, MAItems.MoltenQuiver.get())) {
            if (entityLiving.getRandom().nextFloat() < Config.MOLTEN_QUIVER_AMMO.get()) {
                ci.cancel();
            }
        }
    }
}
