package net.gobies.moreartifacts.datagen;

import net.gobies.moreartifacts.MoreArtifacts;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ItemModels extends ItemModelProvider {
    public ItemModels(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MoreArtifacts.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        //simpleItem(ModItems.Bezoar);
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(MoreArtifacts.MOD_ID,"item/" + item.getId().getPath()));
    }
}