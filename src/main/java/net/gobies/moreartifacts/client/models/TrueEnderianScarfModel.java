package net.gobies.moreartifacts.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.gobies.moreartifacts.client.ClientUtil;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class TrueEnderianScarfModel<T extends Entity> extends EntityModel<T> {
    public final ModelPart TrueEnderianScarf;
    public final ModelPart ScarfBase;

    public TrueEnderianScarfModel(ModelPart root) {
        this.TrueEnderianScarf = root.getChild("TrueEnderianScarf");
        this.ScarfBase = root.getChild("ScarfBase");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition ScarfBase = partdefinition.addOrReplaceChild("ScarfBase", CubeListBuilder.create().texOffs(0, 2).addBox(3.0F, -1.0F, -4.5F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.09F))
                .texOffs(0, 13).addBox(-5.0F, -1.0F, -4.5F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.09F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        ScarfBase.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(22, 2).addBox(-0.5F, -1.0F, -4.5F, 3.0F, 2.0F, 8.0F, new CubeDeformation(0.001F))
                .texOffs(22, 12).addBox(-0.5F, -1.0F, -4.5F, 3.0F, 2.0F, 8.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.5F, 0.0F, 2.0F, 0.0F, -1.5708F, 0.0F));

        ScarfBase.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(22, 2).addBox(-0.5F, -1.0F, -4.5F, 3.0F, 2.0F, 8.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.5F, 0.0F, -4.0F, 0.0F, -1.5708F, 0.0F));

        partdefinition.addOrReplaceChild("TrueEnderianScarf", CubeListBuilder.create().texOffs(3, 32).addBox(-4.5F, -1.0F, -3.0F, 9.0F, 2.0F, 6.0F, new CubeDeformation(0.2F))
                .texOffs(0, 0).addBox(-4.0F, -1.0F, 3.0F, 3.0F, 8.0F, 0.5F, new CubeDeformation(0.19F))
                .texOffs(0, 9).addBox(-4.0F, 7.4F, 3.0F, 3.0F, 1.0F, 0.5F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(@NotNull Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        ICurioRenderer.followHeadRotations((LivingEntity) entity, ScarfBase);
        ClientUtil.followBodyRotations((LivingEntity) entity, TrueEnderianScarf);
        if (((LivingEntity) entity).hasItemInSlot(EquipmentSlot.CHEST)) {
            this.TrueEnderianScarf.y += 0.5f;
        }
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        TrueEnderianScarf.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        ScarfBase.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}