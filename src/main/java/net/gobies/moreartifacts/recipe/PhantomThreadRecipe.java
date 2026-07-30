package net.gobies.moreartifacts.recipe;

import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.init.MARecipes;
import net.gobies.moreartifacts.item.misc.PhantomThreadItem;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class PhantomThreadRecipe extends CustomRecipe {
    public PhantomThreadRecipe(ResourceLocation id, CraftingBookCategory category) {
        super(id, category);
    }

    private boolean isSlotEnabled(ArmorItem armorItem) {
        boolean HEAD = CommonConfig.PHANTOM_THREAD_HEAD.get();
        boolean CHEST = CommonConfig.PHANTOM_THREAD_CHEST.get();
        boolean LEGS = CommonConfig.PHANTOM_THREAD_LEGS.get();
        boolean FEET = CommonConfig.PHANTOM_THREAD_FEET.get();
        EquipmentSlot slot = armorItem.getType().getSlot();
        return switch (slot) {
            case HEAD -> HEAD;
            case CHEST -> CHEST;
            case LEGS -> LEGS;
            case FEET -> FEET;
            default -> false;
        };
    }


    @Override
    public boolean matches(CraftingContainer container, @NotNull Level level) {
        int armorCount = 0;
        int threadCount = 0;

        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof ArmorItem armorItem) {
                    if (!isSlotEnabled(armorItem)) return false;
                    armorCount++;
                } else if (stack.getItem() instanceof PhantomThreadItem) {
                    threadCount++;
                } else {
                    return false;
                }
            }
        }
        return armorCount == 1 && threadCount == 1;
    }

    @Override
    public @NotNull ItemStack assemble(CraftingContainer container, @NotNull RegistryAccess registryAccess) {
        ItemStack targetArmor = ItemStack.EMPTY;

        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (!stack.isEmpty() && stack.getItem() instanceof ArmorItem) {
                targetArmor = stack.copy();
                break;
            }
        }

        if (!targetArmor.isEmpty()) {
            boolean isCurrentlyInvisible = targetArmor.hasTag() && Objects.requireNonNull(targetArmor.getTag()).getBoolean("Invisible");

            if (isCurrentlyInvisible) {
                targetArmor.getTag().remove("Invisible");
                if (targetArmor.getTag().isEmpty()) {
                    targetArmor.setTag(null);
                }
            } else {
                targetArmor.getOrCreateTag().putBoolean("Invisible", true);
            }
        }
        return targetArmor;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return MARecipes.PHANTOM_THREAD_RECIPE.get();
    }
}