package net.gobies.moreartifacts.util;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class CurioHandler {

    public static boolean isCurioEquipped(LivingEntity entity, Item targetItem) {
        return CuriosApi.getCuriosInventory(entity).resolve()
                .flatMap(curios -> curios.findFirstCurio(stack ->
                        stack.getItem() == targetItem)).isPresent();
    }

    public static int getCurioCount(LivingEntity entity, Item item) {
        AtomicInteger count = new AtomicInteger();
        CuriosApi.getCuriosInventory(entity).ifPresent(handler -> handler.getCurios().forEach((identifier, stacksHandler) -> {
            IDynamicStackHandler stackHandler = stacksHandler.getStacks();
            for (int i = 0; i < stackHandler.getSlots(); i++) {
                if (stackHandler.getStackInSlot(i).getItem() == item) {
                    count.getAndIncrement();
                }
            }
        }));
        return count.get();
    }

    public static ItemStack getEquippedCurio(LivingEntity entity, Item item) {
        Optional<SlotResult> slotResultOptional = CuriosApi.getCuriosInventory(entity).resolve()
                .flatMap(curios -> curios.findFirstCurio(stack -> stack.getItem() == item));
        if (slotResultOptional.isPresent()) {
            SlotResult slotResult = slotResultOptional.get();
            return slotResult.stack(); // get equipped stack
        }
        return ItemStack.EMPTY;
    }
}