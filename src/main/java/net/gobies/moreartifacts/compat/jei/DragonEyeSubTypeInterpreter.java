package net.gobies.moreartifacts.compat.jei;

import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.gobies.moreartifacts.item.artifacts.DragonEyeItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DragonEyeSubTypeInterpreter implements IIngredientSubtypeInterpreter<ItemStack> {

    public static final DragonEyeSubTypeInterpreter INSTANCE = new DragonEyeSubTypeInterpreter();

    @Override
    public @NotNull String apply(@NotNull ItemStack ingredient, @NotNull UidContext context) {
        CompoundTag nbt = ingredient.getTag();
        if (nbt != null) {
            List<String> dragonEyes = new ArrayList<>();
            if (DragonEyeItem.isFireDragonEye(ingredient)) {
                dragonEyes.add("Fire");
            }
            if (DragonEyeItem.isIceDragonEye(ingredient)) {
                dragonEyes.add("Ice");
            }
            if (DragonEyeItem.isLightningDragonEye(ingredient)) {
                dragonEyes.add("Lightning");
            }
            if (!dragonEyes.isEmpty()) {
                return dragonEyes.get(0);
            }
        }
        return "default";
    }
}
