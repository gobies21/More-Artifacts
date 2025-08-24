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


public class MelodyPlushieModel<T extends Entity> extends EntityModel<T> {
	private final ModelPart my_melody;

	public MelodyPlushieModel(ModelPart root) {
		this.my_melody = root.getChild("my_melody");
	}

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition my_melody = partdefinition.addOrReplaceChild("my_melody", CubeListBuilder.create().texOffs(0, 6).addBox(-1.5F, -10.0F, -0.75F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.001F))
                .texOffs(0, 0).addBox(-2.0F, -13.0F, -1.25F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(8, 10).addBox(-1.65F, -13.75F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(0.65F, -13.75F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 12).addBox(-1.65F, -14.25F, -0.25F, 1.0F, 0.5F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(8, 12).addBox(0.65F, -14.25F, -0.25F, 1.0F, 0.5F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(11, 0).addBox(0.15F, -14.25F, -0.25F, 0.5F, 0.5F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 14).addBox(-0.65F, -14.25F, -0.25F, 0.5F, 0.5F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r1 = my_melody.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(12, 12).addBox(-1.0F, -0.5F, 0.0F, 1.0F, 0.5F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.15F, -13.75F, -0.25F, 0.0F, 0.0F, -1.5708F));

        PartDefinition cube_r2 = my_melody.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(12, 10).addBox(-1.0F, -0.5F, 0.0F, 1.0F, 0.5F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.65F, -13.75F, -0.25F, 0.0F, 0.0F, -1.5708F));

        PartDefinition cube_r3 = my_melody.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 12).addBox(-1.0F, -0.5F, 0.0F, 1.0F, 0.5F, 1.0F, new CubeDeformation(0.0001F)), PartPose.offsetAndRotation(1.5F, -10.0F, -0.25F, 0.0F, 0.0F, -2.3562F));

        PartDefinition cube_r4 = my_melody.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(10, 8).addBox(-1.0F, -0.5F, 0.0F, 1.0F, 0.5F, 1.0F, new CubeDeformation(0.0001F)), PartPose.offsetAndRotation(-1.25F, -9.75F, -0.25F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r5 = my_melody.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(10, 6).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0001F)), PartPose.offsetAndRotation(1.0F, -8.0F, -1.0F, 0.0F, 2.618F, 0.0F));

        PartDefinition cube_r6 = my_melody.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(4, 10).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0001F)), PartPose.offsetAndRotation(-1.0F, -8.0F, -1.0F, 0.0F, 0.5236F, 0.0F));

        return LayerDefinition.create(meshdefinition, 16, 16);
    }

	@Override
	public void setupAnim(@NotNull Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        ICurioRenderer.followHeadRotations((LivingEntity) entity, my_melody);
	}

	@Override
	public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		my_melody.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}