package net.gobies.moreartifacts.util;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.stream.IntStream;

public class CurioHandler {

    public static boolean isCurioEquipped(LivingEntity entity, Item targetItem) {
        return CuriosApi.getCuriosInventory(entity).resolve()
                .flatMap(curios -> curios.findFirstCurio(stack ->
                        stack.getItem() == targetItem)).isPresent();
    }

    public static int getEquippedCuriosCount(LivingEntity entity, @NotNull Item targetItem, @Nullable Item optionalItem) {
        return CuriosApi.getCuriosInventory(entity).resolve()
                .map(handler -> (int) handler.getCurios().values().stream().flatMap(curio -> {
                    int slots = curio.getSlots();
                    return IntStream.range(0, slots).mapToObj(slotIndex -> curio.getStacks().getStackInSlot(slotIndex)).limit(slots);
                }).filter(itemStack -> {
                    Item stackItem = itemStack.getItem();
                    return stackItem == targetItem || (optionalItem != null && stackItem == optionalItem);
                }).count()).orElse(0);
    }
}