package net.gobies.moreartifacts.client;

import net.gobies.moreartifacts.init.MAModels;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public class ClientUtil {

    public static void followBodyRotations(LivingEntity livingEntity, ModelPart... renderers) {
        EntityRenderer<? super LivingEntity> render = Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(livingEntity);
        if (render instanceof LivingEntityRenderer<?, ?> livingRenderer && livingRenderer.getModel() instanceof HumanoidModel<?> humanoidModel) {
            for (ModelPart renderer : renderers) {
                renderer.copyFrom(humanoidModel.body);
            }
        }
    }

    public static void init(IEventBus modBus) {
        modBus.addListener(ClientUtil::addPlayerLayers);
    }

    private static void addPlayerLayers(EntityRenderersEvent.AddLayers event) {
        MAModels.addPlayerLayers(event);
    }
}
