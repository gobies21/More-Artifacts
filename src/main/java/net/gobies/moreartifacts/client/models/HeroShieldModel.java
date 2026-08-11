package net.gobies.moreartifacts.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.gobies.moreartifacts.client.ClientUtil;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class HeroShieldModel<T extends Entity> extends EntityModel<T> {
    private final ModelPart HeroShield;

    public HeroShieldModel(ModelPart root) {
        this.HeroShield = root.getChild("HeroShield");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("HeroShield", CubeListBuilder.create().texOffs(0, 0).addBox(4.5F, 2.5F, -5.75F, 2.0F, 6.0F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(12, 12).addBox(4.5F, 2.0F, -5.75F, 2.0F, 0.5F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(6, 0).addBox(4.0F, 2.0F, -5.75F, 0.5F, 6.0F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(0, 7).addBox(6.5F, 2.0F, -5.75F, 0.5F, 6.0F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(4, 7).addBox(3.5F, 1.5F, -5.75F, 0.5F, 6.0F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(8, 7).addBox(7.0F, 1.5F, -5.75F, 0.5F, 6.0F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(2.5F, 1.5F, -5.75F, 1.0F, 5.0F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(14, 0).addBox(3.0F, 6.5F, -5.75F, 0.5F, 0.5F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(14, 2).addBox(7.5F, 6.5F, -5.75F, 0.5F, 0.5F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(12, 6).addBox(7.5F, 1.5F, -5.75F, 1.0F, 5.0F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(12, 13).addBox(2.0F, 2.0F, -5.75F, 0.5F, 3.0F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(0, 14).addBox(8.5F, 2.0F, -5.75F, 0.5F, 3.0F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(4, 14).addBox(2.0F, 5.0F, -5.75F, 0.5F, 0.5F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(14, 4).addBox(8.5F, 5.0F, -5.75F, 0.5F, 0.5F, 0.5F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(@NotNull Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        ClientUtil.followBodyRotations((LivingEntity) entity, HeroShield);
        if (((LivingEntity) entity).hasItemInSlot(EquipmentSlot.CHEST)) {
            this.HeroShield.z += 0.8f;
        }
        this.HeroShield.xScale = 0.4f;
        this.HeroShield.yScale = 0.4f;
        this.HeroShield.zScale = 0.4f;
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        HeroShield.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}