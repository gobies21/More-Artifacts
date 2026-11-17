package net.gobies.moreartifacts.client.models.soul;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.player.AbstractClientPlayer;
import org.jetbrains.annotations.NotNull;

public class EnvyHoodModel extends EntityModel<AbstractClientPlayer> {
    private final ModelPart Hood;

    public EnvyHoodModel(ModelPart root) {
        this.Hood = root.getChild("Hood");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Hood = partdefinition.addOrReplaceChild("Hood", CubeListBuilder.create().texOffs(22, 0).addBox(4.0F, -32.0F, -4.75F, 0.5F, 8.0F, 9.0F, new CubeDeformation(0.02F))
                .texOffs(0, 36).addBox(-4.5F, -32.0F, -4.75F, 0.5F, 8.0F, 9.0F, new CubeDeformation(0.02F))
                .texOffs(20, 36).addBox(-5.0F, -30.5F, -4.75F, 0.5F, 4.0F, 9.5F, new CubeDeformation(0.02F))
                .texOffs(42, 0).addBox(4.5F, -30.5F, -4.75F, 0.5F, 4.0F, 9.5F, new CubeDeformation(0.02F))
                .texOffs(22, 17).addBox(-4.0F, -32.0F, -4.75F, 2.0F, 1.0F, 0.75F, new CubeDeformation(0.01F))
                .texOffs(34, 17).addBox(-4.0F, -31.0F, -4.75F, 1.0F, 1.25F, 0.75F, new CubeDeformation(0.01F))
                .texOffs(28, 17).addBox(2.0F, -32.0F, -4.75F, 2.0F, 1.0F, 0.75F, new CubeDeformation(0.01F))
                .texOffs(38, 17).addBox(3.0F, -31.0F, -4.75F, 1.0F, 1.25F, 0.75F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        Hood.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(20, 19).addBox(-0.5F, -6.0F, -4.75F, 0.5F, 7.0F, 9.5F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(2.5F, -33.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

        Hood.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -7.0F, -4.75F, 0.5F, 9.0F, 9.5F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(2.5F, -32.5F, 0.0F, 0.0F, 0.0F, -1.5708F));

        Hood.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 19).addBox(-0.5F, -7.0F, -4.75F, 1.0F, 8.0F, 9.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-0.25F, -25.0F, 4.25F, 0.0F, -1.5708F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Hood.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void setupAnim(@NotNull AbstractClientPlayer pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {

    }
}