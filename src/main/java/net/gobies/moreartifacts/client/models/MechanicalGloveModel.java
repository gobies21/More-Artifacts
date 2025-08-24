package net.gobies.moreartifacts.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class MechanicalGloveModel<T extends Entity> extends EntityModel<T> {
    private final ModelPart mechanical_glove;

    public MechanicalGloveModel(ModelPart root) {
        this.mechanical_glove = root.getChild("mechanical_glove");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition mechanical_glove = partdefinition.addOrReplaceChild("mechanical_glove", CubeListBuilder.create().texOffs(0, 10).addBox(4.0F, -16.0F, 2.0F, 3.0F, 4.0F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(10, 5).addBox(4.0F, -16.0F, -2.5F, 3.0F, 4.0F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(4.0F, -16.0F, -2.5F, 3.0F, 4.0F, 0.5F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r1 = mechanical_glove.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 5).addBox(-0.5F, -4.0F, 0.0F, 4.0F, 4.0F, 0.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -12.0F, -1.5F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r2 = mechanical_glove.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -4.0F, 0.0F, 4.0F, 4.0F, 0.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.5F, -12.0F, -1.5F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r3 = mechanical_glove.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(8, 10).addBox(-0.5F, -4.0F, 0.0F, 3.0F, 4.0F, 0.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, -12.0F, -2.0F, -1.5708F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(@NotNull Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.mechanical_glove.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * limbSwingAmount;
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        mechanical_glove.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}