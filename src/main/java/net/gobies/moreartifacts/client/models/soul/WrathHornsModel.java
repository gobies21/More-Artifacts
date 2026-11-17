package net.gobies.moreartifacts.client.models.soul;

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
import net.minecraft.client.player.AbstractClientPlayer;
import org.jetbrains.annotations.NotNull;

public class WrathHornsModel extends EntityModel<AbstractClientPlayer> {
    private final ModelPart Left_Horn;
    private final ModelPart Right_Horn;

    public WrathHornsModel(ModelPart root) {
        this.Left_Horn = root.getChild("Left_Horn");
        this.Right_Horn = root.getChild("Right_Horn");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Wrath_Left_Horn = partdefinition.addOrReplaceChild("Left_Horn", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        Wrath_Left_Horn.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(12, 9).addBox(-1.0F, -1.05F, 0.15F, 0.5F, 1.15F, 0.55F, new CubeDeformation(0.0001F)), PartPose.offsetAndRotation(5.0F, -35.75F, -0.325F, 0.0F, 0.0F, -0.5672F));
        Wrath_Left_Horn.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(6, 8).addBox(-1.0F, -1.05F, 0.05F, 0.6F, 1.15F, 0.65F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(5.25F, -34.5F, -0.25F, 0.0F, 0.0F, -0.2182F));
        Wrath_Left_Horn.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(8, 3).addBox(-1.35F, -1.05F, -0.45F, 1.05F, 1.15F, 1.2F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(5.25F, -33.25F, 0.0F, 0.0F, 0.0F, 0.0873F));
        Wrath_Left_Horn.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 8).addBox(-1.6F, -1.25F, -0.6F, 1.35F, 1.25F, 1.5F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(5.0F, -32.0F, 0.0F, 0.0F, 0.0F, 0.2182F));
        Wrath_Left_Horn.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 0).addBox(-0.25F, -3.0F, 1.0F, 1.75F, 1.75F, 1.75F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(2.25F, -30.0F, -1.75F, 0.0F, 0.0F, 0.48F));

        PartDefinition Wrath_Right_Horn = partdefinition.addOrReplaceChild("Right_Horn", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, 3.1416F, 0.0F));
        Wrath_Right_Horn.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(10, 9).addBox(-1.0F, -1.05F, 0.15F, 0.5F, 1.15F, 0.55F, new CubeDeformation(0.0001F)), PartPose.offsetAndRotation(5.0F, -35.75F, -0.325F, 0.0F, 0.0F, -0.5672F));
        Wrath_Right_Horn.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(6, 10).addBox(-1.0F, -1.05F, 0.05F, 0.6F, 1.15F, 0.65F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(5.25F, -34.5F, -0.25F, 0.0F, 0.0F, -0.2182F));
        Wrath_Right_Horn.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(8, 6).addBox(-1.35F, -1.05F, -0.45F, 1.05F, 1.15F, 1.2F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(5.25F, -33.25F, 0.0F, 0.0F, 0.0F, 0.0873F));
        Wrath_Right_Horn.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(8, 0).addBox(-1.6F, -1.25F, -0.6F, 1.35F, 1.25F, 1.5F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(5.0F, -32.0F, 0.0F, 0.0F, 0.0F, 0.2182F));
        Wrath_Right_Horn.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(0, 4).addBox(-0.25F, -3.0F, 1.0F, 1.75F, 1.75F, 1.75F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(2.25F, -30.0F, -1.75F, 0.0F, 0.0F, 0.48F));

        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    @Override
    public void setupAnim(@NotNull AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Left_Horn.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        Right_Horn.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}