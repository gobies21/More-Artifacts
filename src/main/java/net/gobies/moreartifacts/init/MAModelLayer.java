package net.gobies.moreartifacts.init;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.gobies.moreartifacts.client.models.SunglassesModel;

@OnlyIn(Dist.CLIENT)
public class MAModelLayer {
        public static final ModelLayerLocation SUNGLASSES = createLocation("sunglasses", "main");

        public static void registers(EntityRenderersEvent.RegisterLayerDefinitions event) {
                event.registerLayerDefinition(SUNGLASSES, () -> SunglassesModel.createBodyLayer(new CubeDeformation(0.3F)));
        }

        private static ModelLayerLocation createLocation(String model, String layer) {
                return new ModelLayerLocation(new ResourceLocation("moreartifacts", model), layer);
        }
}