package net.gobies.moreartifacts.init;

import net.gobies.moreartifacts.item.artifacts.DragonEyeItem;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class MAProperties {
    public static void addItemProperties() {
        makeShield(MAItems.CobaltShield.get());
        makeShield(MAItems.ObsidianShield.get());
        makeShield(MAItems.AnkhShield.get());
    }

    private static void makeShield(Item item) {
        ItemProperties.register(item, new ResourceLocation("blocking"), (p_174575_, p_174576_, p_174577_, p_174578_) ->
                p_174577_ != null && p_174577_.isUsingItem() && p_174577_.getUseItem() == p_174575_ ? 1.0F : 0.0F);
    }

    public static void registerItemProperties() {
        ResourceLocation fire = new ResourceLocation("moreartifacts", "fire");
        ResourceLocation ice = new ResourceLocation("moreartifacts", "ice");
        ResourceLocation lightning = new ResourceLocation("moreartifacts", "lightning");

        ItemProperties.register(MAItems.DragonEye.get(), fire, (stack, world, entity, seed) -> DragonEyeItem.isFireDragonEye(stack) ? 1.0f : 0.0f);
        ItemProperties.register(MAItems.DragonEye.get(), ice, (stack, world, entity, seed) -> DragonEyeItem.isIceDragonEye(stack) ? 1.0f : 0.0f);
        ItemProperties.register(MAItems.DragonEye.get(), lightning, (stack, world, entity, seed) -> DragonEyeItem.isLightningDragonEye(stack) ? 1.0f : 0.0f);
    }
}




