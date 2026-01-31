package net.gobies.moreartifacts.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class MechanicalClawModel<T extends LivingEntity> extends HumanoidModel<T> {

    public final ModelPart mechanical_claw;

    public MechanicalClawModel(ModelPart root) {
        super(root);
        this.mechanical_claw = root.getChild("mechanical_claw");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition mechanical_claw = partdefinition.addOrReplaceChild("mechanical_claw", CubeListBuilder.create().texOffs(0, 0).addBox(1.1F, -19.5F, -2.6F, 0.4F, 5.5F, 4.95F, new CubeDeformation(0.015F))
                .texOffs(10, 0).addBox(-2.85F, -19.5F, -2.6F, 0.4F, 5.5F, 4.95F, new CubeDeformation(0.015F))
                .texOffs(20, 0).addBox(-2.875F, -19.6F, 0.825F, 4.15F, 0.85F, 1.725F, new CubeDeformation(0.001F))
                .texOffs(20, 3).addBox(-2.875F, -19.6F, -2.75F, 4.15F, 0.85F, 1.675F, new CubeDeformation(0.001F))
                .texOffs(8, 0).addBox(-2.1F, -13.375F, -1.0F, 0.5F, 1.5F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(8, 0).addBox(-0.875F, -13.375F, -1.0F, 0.5F, 1.5F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(8, 0).addBox(0.325F, -13.375F, -1.0F, 0.5F, 1.5F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(8, 0).addBox(0.325F, -11.25F, -0.25F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(8, 0).addBox(-0.875F, -11.25F, -0.25F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(8, 0).addBox(-2.075F, -11.25F, -0.25F, 0.5F, 0.5F, 1.5F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r1 = mechanical_claw.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(8, 0).addBox(-0.5F, -0.5F, 0.0F, 0.5F, 0.5F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(8, 0).addBox(0.7F, -0.5F, 0.0F, 0.5F, 0.5F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(8, 0).addBox(1.9F, -0.5F, 0.0F, 0.5F, 0.5F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.575F, -11.65F, -1.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r2 = mechanical_claw.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(8, 14).addBox(-2.8F, -0.85F, -1.4F, 5.3F, 0.85F, 1.7F, new CubeDeformation(0.0001F)), PartPose.offsetAndRotation(-2.725F, -18.75F, 0.05F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r3 = mechanical_claw.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(8, 11).addBox(-2.8F, -0.85F, 0.0F, 5.3F, 0.85F, 1.7F, new CubeDeformation(0.0001F)), PartPose.offsetAndRotation(1.675F, -18.75F, 0.05F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r4 = mechanical_claw.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 11).addBox(-0.5F, -5.5F, 0.6F, 0.4F, 5.5F, 3.6F, new CubeDeformation(0.0151F)), PartPose.offsetAndRotation(1.75F, -14.0F, 2.45F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r5 = mechanical_claw.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(8, 17).addBox(-0.5F, -4.75F, 0.6F, 0.4F, 4.75F, 3.6F, new CubeDeformation(0.0151F)), PartPose.offsetAndRotation(1.75F, -14.0F, -2.1F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r6 = mechanical_claw.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(16, 17).addBox(-1.0F, -4.25F, 0.0F, 4.15F, 4.75F, 0.5F, new CubeDeformation(0.12F)), PartPose.offsetAndRotation(-1.75F, -14.0F, -2.0F, -1.5708F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        ICurioRenderer.followBodyRotations(entity, (HumanoidModel<LivingEntity>) this);
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.mechanical_claw.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}