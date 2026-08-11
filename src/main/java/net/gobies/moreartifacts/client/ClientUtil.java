package net.gobies.moreartifacts.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;

public class ClientUtil {

    public static void followBodyRotations(LivingEntity livingEntity, ModelPart... renderers) {
        EntityRenderer<? super LivingEntity> render = Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(livingEntity);
        if (render instanceof LivingEntityRenderer<?, ?> livingRenderer && livingRenderer.getModel() instanceof HumanoidModel<?> humanoidModel) {
            for (ModelPart renderer : renderers) {
                renderer.copyFrom(humanoidModel.body);
            }
        }
    }
}
