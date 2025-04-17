package net.gobies.moreartifacts.init;

import net.gobies.moreartifacts.item.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

public class MoreArtifactsBrewing {
    public MoreArtifactsBrewing() {
    }

    public static void register(IEventBus eventBus) {
        eventBus.addListener(MoreArtifactsBrewing::registerBrewingRecipes);
    }

    private static void registerBrewingRecipes(RegisterEvent event) {
        event.register(ForgeRegistries.Keys.POTIONS, (helper) -> BrewingRecipeRegistry.addRecipe(
                Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)),
                Ingredient.of(Items.ENDER_EYE),
                (ModItems.RecallPotion.get()).getDefaultInstance()));
    }
}
