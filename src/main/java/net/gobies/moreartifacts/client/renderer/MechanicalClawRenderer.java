package net.gobies.moreartifacts.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.gobies.moreartifacts.client.models.MechanicalClawModel;
import net.gobies.moreartifacts.config.ClientConfig;
import net.gobies.moreartifacts.init.MAModelLayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
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

public class MechanicalClawRenderer implements ICurioRenderer {
    private static final ResourceLocation TEXTURE = new ResourceLocation("moreartifacts", "textures/models/mechanical_claw.png");
    private final MechanicalClawModel<LivingEntity> model;

    public MechanicalClawRenderer() {
        this.model = new MechanicalClawModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(MAModelLayer.MECHANICAL_CLAW));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack poseStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource bufferSource, int light, float limbSwing, float limbSwingAmount, float partialTicks, float tickDelta, float headYaw, float headPitch) {
        if (!ClientConfig.ENABLE_ARTIFACT_MODELS.get()) return;
        LivingEntity entity = slotContext.entity();
        poseStack.pushPose();
        HumanoidModel<T> parentModel = (HumanoidModel<T>) renderLayerParent.getModel();
        parentModel.copyPropertiesTo((HumanoidModel<T>) this.model);
        this.model.setupAnim(entity, limbSwing, limbSwingAmount, tickDelta, headYaw, headPitch);
        ModelPart arm = slotContext.index() == 0 ? this.model.rightArm : this.model.leftArm;
        if (slotContext.index() == 1) {
            poseStack.translate(0.075, 0.0, 0.0);
        }
        arm.translateAndRotate(poseStack);
        VertexConsumer buffer = ItemRenderer.getFoilBuffer(bufferSource, RenderType.entityCutout(TEXTURE), false, stack.hasFoil());
        this.model.mechanical_claw.render(poseStack, buffer, light, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
        poseStack.popPose();
    }
}