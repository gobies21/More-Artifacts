package net.gobies.moreartifacts.client.renderer.soul;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.gobies.moreartifacts.MoreArtifacts;
import net.gobies.moreartifacts.client.models.soul.EnvyHoodModel;
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
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class EnvyHoodRenderer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MoreArtifacts.MOD_ID, "textures/models/envy_hood.png");
    private final EnvyHoodModel model;

    public EnvyHoodRenderer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderer) {
        super(renderer);
        this.model = new EnvyHoodModel(Minecraft.getInstance().getEntityModels().bakeLayer(MAModelLayer.ENVY_HOOD));
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight, AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!SoulSyncPacket.SoulClientData.hasSoul(player.getUUID(), SoulType.ENVY)) return;
        ItemStack armorStack = player.getItemBySlot(EquipmentSlot.HEAD);
        CompoundTag tag = armorStack.getTag();
        if ((player.hasItemInSlot(EquipmentSlot.HEAD))) {
            if (tag != null && !tag.getBoolean("Invisible")) {
                return;
            }
        }

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