package net.gobies.moreartifacts.init;

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
        ItemProperties.register(item, new ResourceLocation("blocking"), (stack, client, entity, number)
                -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);
    }
}




