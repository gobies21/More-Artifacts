package net.gobies.moreartifacts.helper;

import net.gobies.moreartifacts.MoreArtifacts;
import net.gobies.moreartifacts.config.CommonConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collection;
import java.util.List;

@Mod.EventBusSubscriber(modid = MoreArtifacts.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ItemDisabler {

    @SubscribeEvent
    public static void onRecipesUpdated(RecipesUpdatedEvent event) {
        var level = Minecraft.getInstance().level;
        if (level == null) return;

        RecipeManager recipeManager = event.getRecipeManager();
        RegistryAccess registryAccess = level.registryAccess();
        Collection<Recipe<?>> recipes = recipeManager.getRecipes();

        List<Recipe<?>> filteredRecipes = recipes.stream()
                .filter(recipe -> {
                    Item outputItem = recipe.getResultItem(registryAccess).getItem();
                    return !CommonConfig.isItemDisabled(outputItem);
                }).toList();

        if (filteredRecipes.size() != recipes.size()) {
            recipeManager.replaceRecipes(filteredRecipes);
        }
    }

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        Item item = event.getItemStack().getItem();
        if (CommonConfig.isItemDisabled(item)) {
            event.getToolTip().removeAll(event.getToolTip());
            event.getToolTip().add(Component.literal("This item is disabled").withStyle(ChatFormatting.RED));
        }
    }
}
