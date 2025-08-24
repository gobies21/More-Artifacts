package net.gobies.moreartifacts.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class NecroplasmAmuletModel<T extends Entity> extends EntityModel<T> {
    private final ModelPart necroplasm_amulet;

    public NecroplasmAmuletModel(ModelPart root) {
        this.necroplasm_amulet = root.getChild("necroplasm_amulet");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition spectre_amulet = partdefinition.addOrReplaceChild("necroplasm_amulet", CubeListBuilder.create().texOffs(6, 0).addBox(-2.5F, -24.0F, 0.0F, 0.5F, 0.0F, 1.0F, new CubeDeformation(0.01F))
                .texOffs(6, 1).addBox(1.95F, -24.0F, 0.0F, 0.5F, 0.0F, 1.0F, new CubeDeformation(0.01F))
                .texOffs(0, 2).addBox(-3.0F, -24.0F, -2.0F, 0.5F, 0.0F, 2.0F, new CubeDeformation(0.01F))
                .texOffs(0, 0).addBox(2.5F, -24.0F, -2.0F, 0.5F, 0.0F, 2.0F, new CubeDeformation(0.01F))
                .texOffs(0, 7).addBox(-3.0F, -24.0F, -2.0F, 0.5F, 0.5F, 0.0F, new CubeDeformation(0.01F))
                .texOffs(4, 7).addBox(-2.0F, -22.5F, -2.0F, 0.5F, 0.5F, 0.0F, new CubeDeformation(0.01F))
                .texOffs(0, 8).addBox(-1.5F, -22.0F, -2.0F, 0.5F, 0.5F, 0.0F, new CubeDeformation(0.01F))
                .texOffs(0, 4).addBox(-1.0F, -21.45F, -2.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.01F))
                .texOffs(6, 7).addBox(1.5F, -22.5F, -2.0F, 0.5F, 0.5F, 0.0F, new CubeDeformation(0.01F))
                .texOffs(2, 8).addBox(1.0F, -22.0F, -2.0F, 0.5F, 0.5F, 0.0F, new CubeDeformation(0.01F))
                .texOffs(5, 2).addBox(1.0F, -20.0F, -2.0F, 0.5F, 0.5F, 0.0F, new CubeDeformation(0.01F))
                .texOffs(4, 2).addBox(-1.5F, -20.0F, -2.0F, 0.5F, 0.5F, 0.0F, new CubeDeformation(0.01F))
                .texOffs(4, 1).addBox(-0.5F, -19.0F, -2.0F, 1.0F, 0.5F, 0.0F, new CubeDeformation(0.011F))
                .texOffs(4, 3).addBox(-1.0F, -19.5F, -2.0F, 2.0F, 0.5F, 0.0F, new CubeDeformation(0.011F))
                .texOffs(2, 7).addBox(2.5F, -24.0F, -2.0F, 0.5F, 0.5F, 0.0F, new CubeDeformation(0.01F))
                .texOffs(4, 6).addBox(2.0F, -23.5F, -2.0F, 0.5F, 1.0F, 0.0F, new CubeDeformation(0.01F))
                .texOffs(6, 6).addBox(-2.5F, -23.5F, -2.0F, 0.5F, 1.0F, 0.0F, new CubeDeformation(0.01F))
                .texOffs(0, 0).addBox(-2.0F, -24.0F, 1.0F, 0.5F, 0.0F, 0.5F, new CubeDeformation(0.01F))
                .texOffs(0, 0).addBox(-1.5F, -24.0F, 1.5F, 3.0F, 0.0F, 0.5F, new CubeDeformation(0.01F))
                .texOffs(0, 0).addBox(1.5F, -24.0F, 1.0F, 0.5F, 0.0F, 0.5F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r1 = spectre_amulet.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(5, 5).addBox(-0.5F, -0.5F, 0.5F, 1.0F, 0.5F, 0.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(1.5F, -20.45F, -2.5F, 0.0F, 0.0F, -1.5708F));

        PartDefinition cube_r2 = spectre_amulet.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(5, 4).addBox(-0.5F, -0.5F, 0.5F, 1.0F, 0.5F, 0.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-1.0F, -20.45F, -2.5F, 0.0F, 0.0F, -1.5708F));

        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    @Override
    public void setupAnim(@NotNull Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        necroplasm_amulet.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}