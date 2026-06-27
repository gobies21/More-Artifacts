package net.gobies.moreartifacts.util;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

public class CurioHandler {

    public static boolean isCurioEquipped(LivingEntity entity, Item targetItem) {
        return CuriosApi.getCuriosInventory(entity)
                .map(inv -> inv.findFirstCurio(stack -> stack.is(targetItem)).isPresent())
                .orElse(false);
    }

    public static int getCurioCount(LivingEntity entity, Item item) {
        var lazyOptional = CuriosApi.getCuriosInventory(entity);
        if (!lazyOptional.isPresent()) {
            return 0;
        }

        ICuriosItemHandler handler = lazyOptional.orElseThrow(IllegalStateException::new);
        int count = 0;

        for (ICurioStacksHandler stacksHandler : handler.getCurios().values()) {
            IDynamicStackHandler stackHandler = stacksHandler.getStacks();
            for (int i = 0; i < stackHandler.getSlots(); i++) {
                ItemStack stack = stackHandler.getStackInSlot(i);
                if (!stack.isEmpty() && stack.is(item)) {
                    count++;
                }
            }
        }
        return count;
    }

    public static ItemStack getEquippedCurio(LivingEntity entity, Item item) {
        var lazyOptional = CuriosApi.getCuriosInventory(entity);
        if (!lazyOptional.isPresent()) {
            return ItemStack.EMPTY;
        }

        ICuriosItemHandler handler = lazyOptional.orElseThrow(IllegalStateException::new);

        for (ICurioStacksHandler stacksHandler : handler.getCurios().values()) {
            IDynamicStackHandler stackHandler = stacksHandler.getStacks();
            for (int i = 0; i < stackHandler.getSlots(); i++) {
                ItemStack stack = stackHandler.getStackInSlot(i);
                if (!stack.isEmpty() && stack.is(item)) {
                    return stack;
                }
            }
        }
        return ItemStack.EMPTY;
    }
}