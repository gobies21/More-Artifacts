package net.gobies.moreartifacts.init;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class MAModelLayer {
        public static final ModelLayerLocation SUNGLASSES;
        public static final ModelLayerLocation SCULK_SHADES;
        public static final ModelLayerLocation SCULK_LENS;
        public static final ModelLayerLocation COBALT_SHIELD;
        public static final ModelLayerLocation OBSIDIAN_SHIELD;
        public static final ModelLayerLocation ANKH_SHIELD;
        public static final ModelLayerLocation HERO_SHIELD;

        static {
                SUNGLASSES = new ModelLayerLocation(new ResourceLocation("moreartifacts", "sunglasses"), "sunglasses");
                SCULK_SHADES = new ModelLayerLocation(new ResourceLocation("moreartifacts", "sculk_shades"), "sculk_shades");
                SCULK_LENS = new ModelLayerLocation(new ResourceLocation("moreartifacts", "sculk_lens"), "sculk_lens");
                COBALT_SHIELD = new ModelLayerLocation(new ResourceLocation("moreartifacts", "cobalt_shield"), "cobalt_shield");
                OBSIDIAN_SHIELD = new ModelLayerLocation(new ResourceLocation("moreartifacts", "obsidian_shield"), "obsidian_shield");
                ANKH_SHIELD = new ModelLayerLocation(new ResourceLocation("moreartifacts", "ankh_shield"), "ankh_shield");
                HERO_SHIELD = new ModelLayerLocation(new ResourceLocation("moreartifacts", "hero_shield"), "hero_shield");

        }
}