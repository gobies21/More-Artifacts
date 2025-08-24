package net.gobies.moreartifacts.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class GildedScarfModel<T extends Entity> extends EntityModel<T> {
    public final ModelPart scarf_base;
    public final ModelPart gilded_scarf;

    public GildedScarfModel(ModelPart root) {
        this.scarf_base = root.getChild("scarf_base");
        this.gilded_scarf = root.getChild("gilded_scarf");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition scarf_base = partdefinition.addOrReplaceChild("scarf_base", CubeListBuilder.create().texOffs(0, 2).addBox(3.0F, -1.0F, -4.5F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.09F))
                .texOffs(0, 13).addBox(-5.0F, -1.0F, -4.5F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.09F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r1 = scarf_base.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(22, 12).addBox(-0.5F, -1.0F, -4.5F, 3.0F, 2.0F, 8.0F, new CubeDeformation(0.1F))
                .texOffs(22, 2).addBox(-0.5F, -1.0F, -4.5F, 3.0F, 2.0F, 8.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-0.5F, 0.0F, 2.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r2 = scarf_base.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(22, 2).addBox(-0.5F, -1.0F, -4.5F, 3.0F, 2.0F, 8.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.5F, 0.0F, -4.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition enderian_scarf = partdefinition.addOrReplaceChild("gilded_scarf", CubeListBuilder.create().texOffs(3, 32).addBox(-4.5F, -24.0F, -3.0F, 9.0F, 2.0F, 6.0F, new CubeDeformation(0.2F))
                .texOffs(0, 0).addBox(-4.0F, -24.0F, 3.0F, 3.0F, 8.0F, 0.5F, new CubeDeformation(0.19F))
                .texOffs(0, 9).addBox(-4.0F, -15.6F, 3.0F, 3.0F, 1.0F, 0.5F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(@NotNull Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        ICurioRenderer.followHeadRotations((LivingEntity) entity, scarf_base);
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        scarf_base.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}