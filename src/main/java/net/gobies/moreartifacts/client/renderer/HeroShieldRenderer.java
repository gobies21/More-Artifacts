package net.gobies.moreartifacts.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.gobies.moreartifacts.config.ClientConfig;
import net.gobies.moreartifacts.client.models.HeroShieldModel;
import net.gobies.moreartifacts.init.MAModelLayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class HeroShieldRenderer implements ICurioRenderer {
    private static final ResourceLocation TEXTURE = new ResourceLocation("moreartifacts", "textures/models/hero_shield.png");
    private final HeroShieldModel<LivingEntity> model;

    public HeroShieldRenderer() {
        this.model = new HeroShieldModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(MAModelLayer.HERO_SHIELD));
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack poseStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource bufferSource, int light, float limbSwing, float limbSwingAmount, float partialTicks, float tickDelta, float headYaw, float headPitch) {
        if (ClientConfig.ENABLE_ARTIFACT_MODELS.get()) {
            LivingEntity entity = slotContext.entity();
            poseStack.pushPose();
            poseStack.scale(0.4f, 0.4f, 0.4f);
            poseStack.translate(0.0, 0.0, 0.08);
            if (entity.hasItemInSlot(EquipmentSlot.CHEST)) poseStack.translate(0.0, 0.0, -0.135);
            if (entity.isCrouching()) poseStack.translate(0.0, 0.3, 0.0);
            ICurioRenderer.translateIfSneaking(poseStack, entity);
            ICurioRenderer.rotateIfSneaking(poseStack, entity);
            this.model.setupAnim(entity, limbSwing, limbSwingAmount, partialTicks, headYaw, headPitch);
            M specificModel = renderLayerParent.getModel();
            @SuppressWarnings("unchecked")
            T typedEntity = (T) entity;
            specificModel.prepareMobModel(typedEntity, limbSwing, limbSwingAmount, partialTicks);
            VertexConsumer vertexConsumer = ItemRenderer.getFoilBuffer(bufferSource, RenderType.entityCutout(TEXTURE), false, stack.hasFoil());
            this.model.renderToBuffer(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            poseStack.popPose();
        }
    }
}