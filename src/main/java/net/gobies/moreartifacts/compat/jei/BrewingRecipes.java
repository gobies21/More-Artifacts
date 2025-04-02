package net.gobies.moreartifacts.compat.jei;

import mezz.jei.api.JeiPlugin;
import net.gobies.moreartifacts.item.ModItems;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;

import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.recipe.vanilla.IVanillaRecipeFactory;
import mezz.jei.api.recipe.vanilla.IJeiBrewingRecipe;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.IModPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.ArrayList;

@JeiPlugin
public class BrewingRecipes implements IModPlugin {
    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return new ResourceLocation("more_artifacts:brewing_recipes");
    }
//fix deprecated and need to test without jei installed
    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        IVanillaRecipeFactory factory = registration.getVanillaRecipeFactory();
        List<IJeiBrewingRecipe> brewingRecipes = new ArrayList<>();
        ItemStack potion = new ItemStack(Items.POTION);
        List<ItemStack> ingredientStack = new ArrayList<>();
        ingredientStack.add(new ItemStack(Items.ENDER_PEARL));
        PotionUtils.setPotion(potion, Potions.AWKWARD);
        brewingRecipes.add(factory.createBrewingRecipe(List.copyOf(ingredientStack), potion.copy(), new ItemStack(ModItems.RecallPotion.get())));
        ingredientStack.clear();
        registration.addRecipes(RecipeTypes.BREWING, brewingRecipes);
    }
}

