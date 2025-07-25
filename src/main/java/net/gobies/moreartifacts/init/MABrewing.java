package net.gobies.moreartifacts.init;

import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.util.TrueBrewingRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class MABrewing {

    public static void register() {
        BrewingRecipeRegistry.addRecipe(new TrueBrewingRecipe(
                Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)),
                Ingredient.of(Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(CommonConfig.RECALL_POTION_INGREDIENT.get())))),
                (MAItems.RecallPotion.get()).getDefaultInstance())
        );

    }
}
