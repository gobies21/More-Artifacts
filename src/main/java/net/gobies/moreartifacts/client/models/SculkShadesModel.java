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
    private final ModelPart SculkShades;

    public SculkShadesModel(ModelPart root) {
        this.SculkShades = root.getChild("SculkShades");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("SculkShades", CubeListBuilder.create().texOffs(0, 4).addBox(-4.33F, -6.025F, -5.85F, 4.435F, 0.735F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(8, 13).addBox(0.07F, -6.025F, -5.85F, 4.435F, 0.735F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(15, 9).addBox(-5.05F, -6.025F, -5.85F, 0.735F, 3.205F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(15, 11).addBox(-1.375F, -5.305F, -5.85F, 0.735F, 2.485F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(7, 6).addBox(0.83F, -5.305F, -5.85F, 0.735F, 2.485F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(10, 3).addBox(1.54F, -5.305F, -5.75F, 2.96F, 2.485F, 0.3F, new CubeDeformation(-0.01F))
                .texOffs(0, 15).addBox(-4.315F, -5.305F, -5.75F, 2.985F, 2.485F, 0.3F, new CubeDeformation(-0.01F))
                .texOffs(17, 0).addBox(4.505F, -6.025F, -5.85F, 0.735F, 3.205F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(7, 10).addBox(1.52F, -2.82F, -5.85F, 2.985F, 0.735F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(10, 7).addBox(-4.315F, -2.82F, -5.85F, 2.985F, 0.735F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(0, 6).addBox(5.24F, -6.025F, -5.85F, 0.5F, 0.735F, 5.205F, new CubeDeformation(0.0F))
                .texOffs(0, 6).addBox(-5.55F, -6.025F, -5.85F, 0.5F, 0.735F, 5.205F, new CubeDeformation(0.0F))
                .texOffs(6, 8).addBox(-0.65F, -5.35F, -5.85F, 1.5F, 1.0F, 0.5F, new CubeDeformation(0.0001F)), PartPose.offset(0.0F, 16.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        ICurioRenderer.followHeadRotations((LivingEntity) entity, SculkShades);
        this.SculkShades.xScale = 0.8f;
        this.SculkShades.yScale = 0.8f;
        this.SculkShades.zScale = 0.8f;
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        SculkShades.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}