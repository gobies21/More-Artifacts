package net.gobies.moreartifacts.mixin.mobends;

import com.mojang.blaze3d.vertex.PoseStack;
import goblinbob.mobends.standard.client.model.armor.ArmorRenderingFacade;
import goblinbob.mobends.standard.data.BipedEntityData;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ArmorRenderingFacade.class)
public class ArmorRenderingFacadeMixin {

    @Inject(
            method = "renderArmor(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ArmorItem;Lnet/minecraft/client/model/Model;Lgoblinbob/mobends/standard/data/BipedEntityData;Lnet/minecraft/resources/ResourceLocation;)Z",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private <T extends LivingEntity> void renderArmor(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, T entity, EquipmentSlot slot, ItemStack armorStack, ArmorItem armorItem, Model armorModel, BipedEntityData<?> entityData, ResourceLocation texture, CallbackInfoReturnable<Boolean> cir) {
        if (armorStack != null && !armorStack.isEmpty()) {
            CompoundTag tag = armorStack.getTag();
            if (tag != null && tag.getBoolean("Invisible")) {
                cir.setReturnValue(false);
            }
        }
    }
}