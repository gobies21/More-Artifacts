package net.gobies.moreartifacts.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.gobies.moreartifacts.config.ClientConfig;
import net.gobies.moreartifacts.client.models.ObsidianShieldModel;
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

public class ObsidianShieldRenderer implements ICurioRenderer {
    private static final ResourceLocation TEXTURE = new ResourceLocation("moreartifacts", "textures/models/obsidian_shield.png");
    private final ObsidianShieldModel<LivingEntity> model;

    public ObsidianShieldRenderer() {
        this.model = new ObsidianShieldModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(MAModelLayer.OBSIDIAN_SHIELD));
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack poseStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource bufferSource, int light, float limbSwing, float limbSwingAmount, float partialTicks, float tickDelta, float headYaw, float headPitch) {
        if (!ClientConfig.ENABLE_ARTIFACT_MODELS.get()) return;
        LivingEntity entity = slotContext.entity();
        poseStack.pushPose();
        poseStack.scale(1.2f, 1.1f, 1.2f);
        poseStack.translate(0.0, 0.0, 0.01);
        if (entity.hasItemInSlot(EquipmentSlot.CHEST)) poseStack.translate(0.0, 0.0, 0.025);
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