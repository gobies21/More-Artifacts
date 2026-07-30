package net.gobies.moreartifacts.init;

import net.gobies.moreartifacts.MoreArtifacts;
import net.gobies.moreartifacts.recipe.PhantomThreadRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MARecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS;
    public static final RegistryObject<SimpleCraftingRecipeSerializer<PhantomThreadRecipe>> PHANTOM_THREAD_RECIPE;

    public static void register(IEventBus eventBus) {
        RECIPE_SERIALIZERS.register(eventBus);
    }

    static {
        RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MoreArtifacts.MOD_ID);
        PHANTOM_THREAD_RECIPE = RECIPE_SERIALIZERS.register("phantom_thread_crafting", () -> new SimpleCraftingRecipeSerializer<>(PhantomThreadRecipe::new));
    }
}
