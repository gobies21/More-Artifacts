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

public class SculkShadesModel<T extends Entity> extends EntityModel<T> {
    private final ModelPart Sculk_Shades;
    private final ModelPart bb_main;

    public SculkShadesModel(ModelPart root) {
        this.Sculk_Shades = root.getChild("Sculk_Shades");
        this.bb_main = root.getChild("bb_main");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Sculk_Shades = partdefinition.addOrReplaceChild("Sculk_Shades", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 0).addBox(-4.5F, -5.0F, -5.0F, 0.5F, 0.5F, 4.0F, new CubeDeformation(0.001F))
                .texOffs(0, 5).addBox(4.0F, -5.0F, -5.0F, 0.5F, 0.5F, 4.0F, new CubeDeformation(0.001F))
                .texOffs(0, 10).addBox(1.5F, -5.0F, -5.0F, 2.0F, 2.0F, 0.5F, new CubeDeformation(0.001F))
                .texOffs(10, 0).addBox(-3.5F, -5.0F, -5.0F, 2.0F, 2.0F, 0.5F, new CubeDeformation(0.001F))
                .texOffs(10, 3).addBox(1.0F, -5.0F, -5.0F, 0.5F, 2.0F, 0.5F, new CubeDeformation(0.001F))
                .texOffs(10, 9).addBox(-1.0F, -5.0F, -5.0F, 0.5F, 1.0F, 0.5F, new CubeDeformation(0.001F))
                .texOffs(10, 11).addBox(0.5F, -5.0F, -5.0F, 0.5F, 1.0F, 0.5F, new CubeDeformation(0.001F))
                .texOffs(6, 10).addBox(-0.5F, -5.0F, -5.0F, 1.0F, 1.0F, 0.5F, new CubeDeformation(0.001F))
                .texOffs(10, 13).addBox(-4.0F, -5.0F, -5.0F, 0.5F, 0.5F, 0.5F, new CubeDeformation(0.001F))
                .texOffs(14, 3).addBox(3.5F, -5.0F, -5.0F, 0.5F, 0.5F, 0.5F, new CubeDeformation(0.001F))
                .texOffs(10, 6).addBox(-1.5F, -5.0F, -5.0F, 0.5F, 2.0F, 0.5F, new CubeDeformation(0.001F))
                .texOffs(4, 14).addBox(0.5F, -4.0F, -5.0F, 0.5F, 0.5F, 0.5F, new CubeDeformation(0.001F))
                .texOffs(14, 5).addBox(-1.0F, -4.0F, -5.0F, 0.5F, 0.5F, 0.5F, new CubeDeformation(0.001F))
                .texOffs(6, 12).addBox(-4.0F, -4.5F, -5.0F, 0.5F, 1.0F, 0.5F, new CubeDeformation(0.001F))
                .texOffs(0, 13).addBox(3.5F, -4.5F, -5.0F, 0.5F, 1.0F, 0.5F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(@NotNull Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        ICurioRenderer.followHeadRotations((LivingEntity) entity, bb_main);
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Sculk_Shades.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}