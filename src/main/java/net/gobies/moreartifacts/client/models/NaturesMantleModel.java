package net.gobies.moreartifacts.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.gobies.moreartifacts.client.ClientUtil;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class NaturesMantleModel<T extends Entity> extends EntityModel<T> {
    private final ModelPart NaturesMantle;

    public NaturesMantleModel(ModelPart root) {
        this.NaturesMantle = root.getChild("NaturesMantle");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition NaturesMantle = partdefinition.addOrReplaceChild("NaturesMantle", CubeListBuilder.create().texOffs(0, 18).addBox(4.0F, -0.75F, -2.25F, 3.0F, 0.25F, 4.5F, new CubeDeformation(0.0F))
                .texOffs(16, 18).addBox(-7.0F, -0.75F, -2.25F, 3.0F, 0.25F, 4.5F, new CubeDeformation(0.0F))
                .texOffs(0, 11).addBox(-7.0F, 0.0F, -2.25F, 14.0F, 1.5F, 0.25F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-7.0F, -0.5F, 2.0F, 14.0F, 10.5F, 0.25F, new CubeDeformation(0.0F))
                .texOffs(0, 16).addBox(-4.5F, -0.5F, 2.25F, 9.0F, 0.25F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(22, 16).addBox(-7.0F, -0.5F, -2.25F, 3.5F, 0.5F, 0.25F, new CubeDeformation(0.0F))
                .texOffs(22, 17).addBox(3.5F, -0.5F, -2.25F, 3.5F, 0.5F, 0.25F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-3.5F, -0.3F, -2.25F, 7.0F, 0.3F, 0.25F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r1 = NaturesMantle.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(10, 23).addBox(6.25F, -0.75F, 0.25F, 1.75F, 0.5F, 3.25F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.75F, -0.25F, 10.25F, 1.5708F, 1.5708F, 0.0F));

        PartDefinition cube_r2 = NaturesMantle.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 23).addBox(6.25F, -0.75F, 0.25F, 1.75F, 0.5F, 3.25F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.75F, -0.25F, 10.25F, 1.5708F, 1.5708F, 0.0F));

        PartDefinition cube_r3 = NaturesMantle.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 13).addBox(-1.0F, -0.5F, 0.25F, 9.0F, 0.25F, 3.25F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, -0.25F, 4.5F, 1.5708F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(@NotNull Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        ClientUtil.followBodyRotations((LivingEntity) entity, NaturesMantle);
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        NaturesMantle.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}