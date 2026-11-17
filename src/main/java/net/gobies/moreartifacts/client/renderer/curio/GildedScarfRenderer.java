package net.gobies.moreartifacts.client.renderer.curio;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.gobies.moreartifacts.MoreArtifacts;
import net.gobies.moreartifacts.client.models.curio.GildedScarfModel;
import net.gobies.moreartifacts.config.ClientConfig;
import net.gobies.moreartifacts.init.MAModelLayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class GildedScarfRenderer implements ICurioRenderer {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MoreArtifacts.MOD_ID, "textures/models/gilded_scarf.png");
    private final GildedScarfModel<LivingEntity> model;

    public GildedScarfRenderer() {
        this.model = new GildedScarfModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(MAModelLayer.GILDED_SCARF));
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack poseStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource bufferSource, int light, float limbSwing, float limbSwingAmount, float partialTicks, float tickDelta, float headYaw, float headPitch) {
        if (!ClientConfig.ENABLE_ARTIFACT_MODELS.get()) return;
        LivingEntity entity = slotContext.entity();
        @SuppressWarnings("redundant")
        PoseStack scarfBasePose = poseStack;
        scarfBasePose.pushPose();
        poseStack.pushPose();
        this.model.setupAnim(entity, limbSwing, limbSwingAmount, partialTicks, headYaw, headPitch);
        M specificModel = renderLayerParent.getModel();
        @SuppressWarnings("unchecked")
        T typedEntity = (T) entity;
        specificModel.prepareMobModel(typedEntity, limbSwing, limbSwingAmount, partialTicks);
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutout(TEXTURE));
        this.model.GildedScarf.render(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
        this.model.ScarfBase.render(scarfBasePose, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        scarfBasePose.popPose();

    }
}