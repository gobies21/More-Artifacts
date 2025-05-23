package net.gobies.moreartifacts.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.gobies.moreartifacts.MoreArtifacts;
import net.gobies.moreartifacts.client.models.SunglassesModel;
import net.gobies.moreartifacts.init.MAModelLayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class SunglassesRenderer implements ICurioRenderer {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MoreArtifacts.MOD_ID, "textures/models/sunglasses.png");

    private final SunglassesModel model;

    public SunglassesRenderer() {
        this.model = new SunglassesModel(Minecraft.getInstance().getEntityModels().bakeLayer(MAModelLayer.SUNGLASSES));
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack poseStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource buffer, int packedLight, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ICurioRenderer.followHeadRotations(slotContext.entity(), model.head);
        VertexConsumer consumer = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(getCuriosTexture()), false, stack.hasFoil());
        this.model.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    public ResourceLocation getCuriosTexture() {
        return TEXTURE;
    }
}
