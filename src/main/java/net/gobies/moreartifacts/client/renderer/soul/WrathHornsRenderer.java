package net.gobies.moreartifacts.client.renderer.soul;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.gobies.moreartifacts.MoreArtifacts;
import net.gobies.moreartifacts.client.models.soul.WrathHornsModel;
import net.gobies.moreartifacts.helper.SoulType;
import net.gobies.moreartifacts.init.MAModelLayer;
import net.gobies.moreartifacts.network.SoulSyncPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class WrathHornsRenderer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MoreArtifacts.MOD_ID, "textures/models/wrath_horns.png");
    private final WrathHornsModel model;

    public WrathHornsRenderer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderer) {
        super(renderer);
        this.model = new WrathHornsModel(Minecraft.getInstance().getEntityModels().bakeLayer(MAModelLayer.WRATH_HORNS));
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight, AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!SoulSyncPacket.SoulClientData.hasSoul(player.getUUID(), SoulType.WRATH)) return;
        if (player.isInvisible()) return;
        PlayerModel<AbstractClientPlayer> playerModel = this.getParentModel();
        ModelPart head = playerModel.head;
        poseStack.pushPose();
        head.translateAndRotate(poseStack);
        this.model.setupAnim(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
        this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
    }
}