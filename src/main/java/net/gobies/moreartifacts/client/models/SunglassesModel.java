package net.gobies.moreartifacts.client.models;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class SunglassesModel extends HumanoidModel<LivingEntity> {
    public SunglassesModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer(CubeDeformation deformation) {
        MeshDefinition meshdefinition = HumanoidModel.createMesh(deformation, 0.0F);
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 1).addBox(-0.5F, -6.5F, 0.5F, 2.0F, 0.5F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.0F, -8.0F, 0.5F, 7.0F, 0.5F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 2).addBox(3.5F, -6.5F, 0.5F, 2.0F, 0.5F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(4, 2).addBox(-1.0F, -7.5F, 0.5F, 0.5F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(4, 4).addBox(1.5F, -7.0F, 0.5F, 0.5F, 0.5F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(4, 3).addBox(5.5F, -7.5F, 0.5F, 0.5F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(4, 1).addBox(2.0F, -7.5F, 0.5F, 1.0F, 0.5F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(2, 5).addBox(1.5F, -7.5F, 0.5F, 0.5F, 0.5F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(4, 5).addBox(1.0F, -7.5F, 0.5F, 0.5F, 0.5F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 6).addBox(0.5F, -7.5F, 0.5F, 0.5F, 0.5F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(6, 1).addBox(0.0F, -7.5F, 0.5F, 0.5F, 0.5F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(2, 6).addBox(-0.5F, -7.5F, 0.5F, 0.5F, 0.5F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(6, 2).addBox(-0.5F, -7.0F, 0.5F, 0.5F, 0.5F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(6, 3).addBox(0.0F, -7.0F, 0.5F, 0.5F, 0.5F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(4, 6).addBox(0.5F, -7.0F, 0.5F, 0.5F, 0.5F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(6, 5).addBox(3.0F, -7.0F, 0.5F, 0.5F, 0.5F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 7).addBox(3.5F, -7.0F, 0.5F, 0.5F, 0.5F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(4, 7).addBox(4.0F, -7.0F, 0.5F, 0.5F, 0.5F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 8).addBox(4.5F, -7.0F, 0.5F, 0.5F, 0.5F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(2, 8).addBox(5.0F, -7.0F, 0.5F, 0.5F, 0.5F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(8, 3).addBox(5.0F, -7.5F, 0.5F, 0.5F, 0.5F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(8, 4).addBox(4.5F, -7.5F, 0.5F, 0.5F, 0.5F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(6, 8).addBox(4.0F, -7.5F, 0.5F, 0.5F, 0.5F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(8, 7).addBox(3.5F, -7.5F, 0.5F, 0.5F, 0.5F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 9).addBox(3.0F, -7.5F, 0.5F, 0.5F, 0.5F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(6, 4).addBox(1.0F, -7.0F, 0.5F, 0.5F, 0.5F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r1 = head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 4).addBox(-0.5F, -0.5F, 0.5F, 2.0F, 0.5F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.5F, -7.5F, 2.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r2 = head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 3).addBox(-0.5F, -0.5F, 0.5F, 2.0F, 0.5F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -7.5F, 2.0F, 0.0F, 1.5708F, 0.0F));


        return LayerDefinition.create(meshdefinition, 16, 16);
    }


    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}