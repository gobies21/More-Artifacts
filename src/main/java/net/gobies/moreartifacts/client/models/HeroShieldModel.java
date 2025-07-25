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

public class HeroShieldModel<T extends Entity> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    private final ModelPart Hero_Shield;

    public HeroShieldModel(ModelPart root) {
        this.Hero_Shield = root.getChild("Hero_Shield");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Hero_Shield = partdefinition.addOrReplaceChild("Hero_Shield", CubeListBuilder.create().texOffs(0, 0).addBox(5.0F, -20.0F, -7.0F, 2.0F, 6.0F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(12, 12).addBox(5.0F, -20.5F, -7.0F, 2.0F, 0.5F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(6, 0).addBox(4.5F, -20.5F, -7.0F, 0.5F, 6.0F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(0, 7).addBox(7.0F, -20.5F, -7.0F, 0.5F, 6.0F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(4, 7).addBox(4.0F, -21.0F, -7.0F, 0.5F, 6.0F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(8, 7).addBox(7.5F, -21.0F, -7.0F, 0.5F, 6.0F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(3.0F, -21.0F, -7.0F, 1.0F, 5.0F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(14, 0).addBox(3.5F, -16.0F, -7.0F, 0.5F, 0.5F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(14, 2).addBox(8.0F, -16.0F, -7.0F, 0.5F, 0.5F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(12, 6).addBox(8.0F, -21.0F, -7.0F, 1.0F, 5.0F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(12, 13).addBox(2.5F, -20.5F, -7.0F, 0.5F, 3.0F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(0, 14).addBox(9.0F, -20.5F, -7.0F, 0.5F, 3.0F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(4, 14).addBox(2.5F, -17.5F, -7.0F, 0.5F, 0.5F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(14, 4).addBox(9.0F, -17.5F, -7.0F, 0.5F, 0.5F, 0.5F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(@NotNull Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Hero_Shield.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}