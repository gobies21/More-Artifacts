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

public class CobaltShieldModel<T extends Entity> extends EntityModel<T> {
    private final ModelPart CobaltShield;

    public CobaltShieldModel(ModelPart root) {
        this.CobaltShield = root.getChild("Cobalt_Shield");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Cobalt_Shield = partdefinition.addOrReplaceChild("Cobalt_Shield", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -23.5F, 2.0F, 6.0F, 11.0F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(13, 11).addBox(-0.5F, -20.5F, 1.5F, 1.0F, 4.0F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(11, 14).addBox(-0.5F, -20.5F, 0.5F, 1.0F, 0.5F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(11, 12).addBox(-0.5F, -17.0F, 0.5F, 1.0F, 0.5F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    @Override
    public void setupAnim(@NotNull Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        CobaltShield.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}