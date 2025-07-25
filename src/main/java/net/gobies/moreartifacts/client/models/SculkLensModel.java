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
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class SculkLensModel<T extends Entity> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    private final ModelPart Sculk_Lens;

    public SculkLensModel(ModelPart root) {
        this.Sculk_Lens = root.getChild("Sculk_Lens");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Sculk_Lens = partdefinition.addOrReplaceChild("Sculk_Lens", CubeListBuilder.create().texOffs(4, 4).addBox(2.75F, -6.5F, -5.25F, 1.0F, 4.0F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(0, 6).addBox(0.75F, -6.5F, -5.25F, 1.0F, 4.0F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(1.75F, -7.0F, -5.25F, 1.0F, 5.0F, 0.5F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r1 = Sculk_Lens.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(4, 0).addBox(-1.5F, -0.5F, 0.5F, 3.0F, 0.5F, 0.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.25F, -4.5F, -5.75F, 0.0F, 0.0F, -1.5708F));

        PartDefinition cube_r2 = Sculk_Lens.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(4, 2).addBox(-1.5F, -0.5F, 0.5F, 3.0F, 0.5F, 0.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.75F, -4.5F, -5.75F, 0.0F, 0.0F, -1.5708F));

        PartDefinition cube_r3 = Sculk_Lens.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(8, 8).addBox(0.5F, -0.5F, 0.5F, 1.0F, 0.5F, 0.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.75F, -3.5F, -5.75F, 0.0F, 0.0F, -1.5708F));

        PartDefinition cube_r4 = Sculk_Lens.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(8, 4).addBox(0.5F, -0.5F, 0.5F, 1.0F, 0.5F, 0.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.25F, -3.5F, -5.75F, 0.0F, 0.0F, -1.5708F));

        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    @Override
    public void setupAnim(@NotNull Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        ICurioRenderer.followHeadRotations((LivingEntity) entity, Sculk_Lens);
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Sculk_Lens.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}