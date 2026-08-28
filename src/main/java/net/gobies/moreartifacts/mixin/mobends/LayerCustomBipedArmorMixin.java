package net.gobies.moreartifacts.mixin.mobends;

import com.mojang.blaze3d.vertex.PoseStack;
import goblinbob.mobends.core.data.EntityData;
import goblinbob.mobends.standard.client.renderer.entity.layers.LayerCustomBipedArmor;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LayerCustomBipedArmor.class)
public class LayerCustomBipedArmorMixin {

    @Inject(
            method = "renderArmorPiece",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private void onRenderArmorPiece(PoseStack poseStack, MultiBufferSource bufferSource, LivingEntity entity, EquipmentSlot slot, int packedLight, boolean hasBendsAnimation, EntityData<?> entityData, CallbackInfo ci) {
        ItemStack armorStack = entity.getItemBySlot(slot);
        if (armorStack.isEmpty()) return;
        CompoundTag tag = armorStack.getTag();
        if (tag != null && tag.getBoolean("Invisible")) {
            ci.cancel();
        }
    }
}