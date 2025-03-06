package net.gobies.moreartifacts.item;

import net.gobies.moreartifacts.MoreArtifacts;
import net.gobies.moreartifacts.item.artifacts.BezoarItem;
import net.gobies.moreartifacts.item.artifacts.VitaminsItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item>ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MoreArtifacts.MOD_ID);

    public static final RegistryObject<Item> Bezoar = ITEMS.register("bezoar",
            () -> new BezoarItem(new Item.Properties()));
    public static final RegistryObject<Item> Vitamins = ITEMS.register("vitamins",
            () -> new VitaminsItem(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
