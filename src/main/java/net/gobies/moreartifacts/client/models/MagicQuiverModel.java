package net.gobies.moreartifacts.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class MagicQuiverModel<T extends Entity> extends EntityModel<T> {
    private final ModelPart magic_quiver;

    public MagicQuiverModel(ModelPart root) {
        this.magic_quiver = root.getChild("magic_quiver");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition magic_quiver = partdefinition.addOrReplaceChild("magic_quiver", CubeListBuilder.create(), PartPose.offsetAndRotation(0.5F, 19.5F, -1.75F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r1 = magic_quiver.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(9, 3).addBox(-0.4F, -0.25F, 0.25F, 0.15F, 0.25F, 0.15F, new CubeDeformation(0.035F)), PartPose.offsetAndRotation(5.3F, -11.5F, -2.9F, 1.1287F, 0.6724F, 0.921F));

        PartDefinition cube_r2 = magic_quiver.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(9, 4).addBox(-0.4F, -0.5F, 0.25F, 0.15F, 0.5F, 0.15F, new CubeDeformation(0.03F)), PartPose.offsetAndRotation(5.3F, -11.15F, -2.55F, 1.1287F, 0.6724F, 0.921F));

        PartDefinition cube_r3 = magic_quiver.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(9, 3).addBox(-0.4F, -0.25F, 0.25F, 0.15F, 0.25F, 0.15F, new CubeDeformation(0.035F)), PartPose.offsetAndRotation(5.95F, -11.5F, -2.975F, 0.8189F, -0.2532F, -0.2618F));

        PartDefinition cube_r4 = magic_quiver.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(9, 4).addBox(-0.4F, -0.5F, 0.25F, 0.15F, 0.5F, 0.15F, new CubeDeformation(0.03F)), PartPose.offsetAndRotation(5.95F, -11.15F, -2.625F, 0.8189F, -0.2532F, -0.2618F));

        PartDefinition cube_r5 = magic_quiver.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(9, 3).addBox(-0.4F, -0.25F, 0.25F, 0.15F, 0.25F, 0.15F, new CubeDeformation(0.035F)), PartPose.offsetAndRotation(5.825F, -11.35F, -3.175F, 0.8522F, -0.3499F, -0.3736F));

        PartDefinition cube_r6 = magic_quiver.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(9, 4).addBox(-0.4F, -0.5F, 0.25F, 0.15F, 0.5F, 0.15F, new CubeDeformation(0.03F)), PartPose.offsetAndRotation(5.825F, -11.0F, -2.825F, 0.8522F, -0.3499F, -0.3736F));

        PartDefinition cube_r7 = magic_quiver.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(9, 3).addBox(-0.4F, -0.25F, 0.25F, 0.15F, 0.25F, 0.15F, new CubeDeformation(0.035F)), PartPose.offsetAndRotation(5.3F, -11.375F, -3.075F, 0.8249F, 0.274F, 0.2849F));

        PartDefinition cube_r8 = magic_quiver.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(9, 4).addBox(-0.4F, -0.5F, 0.25F, 0.15F, 0.5F, 0.15F, new CubeDeformation(0.03F)), PartPose.offsetAndRotation(5.3F, -11.025F, -2.9F, 0.8165F, -0.2443F, -0.2519F));

        PartDefinition cube_r9 = magic_quiver.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(9, 3).addBox(-0.4F, -0.25F, 0.25F, 0.15F, 0.25F, 0.15F, new CubeDeformation(0.035F)), PartPose.offsetAndRotation(5.3F, -11.375F, -3.25F, 0.8165F, -0.2443F, -0.2519F));

        PartDefinition cube_r10 = magic_quiver.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(9, 4).addBox(-0.4F, -0.5F, 0.25F, 0.15F, 0.5F, 0.15F, new CubeDeformation(0.03F)), PartPose.offsetAndRotation(5.3F, -11.025F, -2.725F, 0.8249F, 0.274F, 0.2849F));

        PartDefinition cube_r11 = magic_quiver.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(9, 3).addBox(-0.4F, -0.25F, 0.25F, 0.15F, 0.25F, 0.15F, new CubeDeformation(0.035F)), PartPose.offsetAndRotation(5.05F, -11.5F, -2.9F, 0.8249F, 0.274F, 0.2849F));

        PartDefinition cube_r12 = magic_quiver.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(9, 4).addBox(-0.4F, -0.5F, 0.25F, 0.15F, 0.5F, 0.15F, new CubeDeformation(0.03F)), PartPose.offsetAndRotation(5.05F, -11.15F, -2.55F, 0.8249F, 0.274F, 0.2849F));

        PartDefinition cube_r13 = magic_quiver.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(4, 9).addBox(-0.5F, -0.5F, 0.0F, 0.5F, 0.5F, 0.5F, new CubeDeformation(0.3004F)).texOffs(0, 7).addBox(0.0F, -0.5F, 0.0F, 1.0F, 0.5F, 1.0F, new CubeDeformation(0.3002F)), PartPose.offsetAndRotation(4.95F, -10.3F, -2.65F, 0.6981F, 0.0F, 0.0F));

        PartDefinition cube_r14 = magic_quiver.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(8, 7).addBox(-1.0F, -0.5F, 0.0F, 1.0F, 0.5F, 0.5F, new CubeDeformation(0.3001F)), PartPose.offsetAndRotation(4.95F, -11.25F, -1.525F, 1.5708F, -0.8727F, -1.5708F));

        PartDefinition cube_r15 = magic_quiver.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(0, 9).addBox(-1.0F, -0.5F, 0.0F, 1.0F, 0.5F, 0.5F, new CubeDeformation(0.3002F)), PartPose.offsetAndRotation(5.95F, -10.925F, -1.9F, 0.6981F, 0.0F, 0.0F));

        PartDefinition cube_r16 = magic_quiver.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -2.0F, 0.0F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(4.7F, -10.3F, -3.0F, -0.8727F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    @Override
    public void setupAnim(@NotNull Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        magic_quiver.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}