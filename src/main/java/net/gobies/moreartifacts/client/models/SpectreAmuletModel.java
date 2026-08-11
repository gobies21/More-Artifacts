package net.gobies.moreartifacts.client.models;

import net.gobies.moreartifacts.client.ClientUtil;
import net.minecraft.client.model.geom.ModelPart;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class SpectreAmuletModel<T extends Entity> extends EntityModel<T> {
    private final ModelPart SpectreAmulet;

    public SpectreAmuletModel(ModelPart root) {
        this.SpectreAmulet = root.getChild("SpectreAmulet");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition SpectreAmulet = partdefinition.addOrReplaceChild("SpectreAmulet", CubeListBuilder.create().texOffs(6, 0).addBox(-2.5F, 0.0F, -0.1F, 0.5F, 0.0F, 1.0F, new CubeDeformation(0.01F))
                .texOffs(6, 1).addBox(1.95F, 0.0F, -0.1F, 0.5F, 0.0F, 1.0F, new CubeDeformation(0.01F))
                .texOffs(0, 2).addBox(-3.0F, 0.0F, -2.1F, 0.5F, 0.0F, 2.0F, new CubeDeformation(0.01F))
                .texOffs(0, 0).addBox(2.5F, 0.0F, -2.1F, 0.5F, 0.0F, 2.0F, new CubeDeformation(0.01F))
                .texOffs(0, 7).addBox(-3.0F, 0.0F, -2.1F, 0.5F, 0.5F, 0.0F, new CubeDeformation(0.01F))
                .texOffs(4, 7).addBox(-2.0F, 1.5F, -2.1F, 0.5F, 0.5F, 0.0F, new CubeDeformation(0.01F))
                .texOffs(0, 8).addBox(-1.5F, 2.0F, -2.1F, 0.5F, 0.5F, 0.0F, new CubeDeformation(0.01F))
                .texOffs(0, 4).addBox(-1.0F, 2.55F, -2.1F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.01F))
                .texOffs(6, 7).addBox(1.5F, 1.5F, -2.1F, 0.5F, 0.5F, 0.0F, new CubeDeformation(0.01F))
                .texOffs(2, 8).addBox(1.0F, 2.0F, -2.1F, 0.5F, 0.5F, 0.0F, new CubeDeformation(0.01F))
                .texOffs(5, 2).addBox(1.0F, 4.0F, -2.1F, 0.5F, 1.0F, 0.0F, new CubeDeformation(0.01F))
                .texOffs(4, 2).addBox(-1.5F, 4.0F, -2.1F, 0.5F, 1.0F, 0.0F, new CubeDeformation(0.01F))
                .texOffs(6, 2).addBox(-1.0F, 5.0F, -2.1F, 2.0F, 0.5F, 0.0F, new CubeDeformation(0.011F))
                .texOffs(4, 3).addBox(-1.0F, 4.5F, -2.1F, 2.0F, 0.5F, 0.0F, new CubeDeformation(0.011F))
                .texOffs(2, 7).addBox(2.5F, 0.0F, -2.1F, 0.5F, 0.5F, 0.0F, new CubeDeformation(0.01F))
                .texOffs(4, 6).addBox(2.0F, 0.5F, -2.1F, 0.5F, 1.0F, 0.0F, new CubeDeformation(0.01F))
                .texOffs(6, 6).addBox(-2.5F, 0.5F, -2.1F, 0.5F, 1.0F, 0.0F, new CubeDeformation(0.01F))
                .texOffs(0, 0).addBox(-2.0F, 0.0F, 0.9F, 0.5F, 0.0F, 0.5F, new CubeDeformation(0.01F))
                .texOffs(0, 0).addBox(-1.5F, 0.0F, 1.4F, 3.0F, 0.0F, 0.5F, new CubeDeformation(0.01F))
                .texOffs(0, 0).addBox(1.5F, 0.0F, 0.9F, 0.5F, 0.0F, 0.5F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        SpectreAmulet.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(5, 5).addBox(-0.5F, -0.5F, 0.5F, 1.0F, 0.5F, 0.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(1.5F, 3.55F, -2.6F, 0.0F, 0.0F, -1.5708F));

        SpectreAmulet.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(5, 4).addBox(-0.5F, -0.5F, 0.5F, 1.0F, 0.5F, 0.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-1.0F, 3.55F, -2.6F, 0.0F, 0.0F, -1.5708F));

        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    @Override
    public void setupAnim(@NotNull Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        ClientUtil.followBodyRotations((LivingEntity) entity, SpectreAmulet);
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        SpectreAmulet.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}