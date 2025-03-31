package net.gobies.moreartifacts.init;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import top.theillusivec4.curios.api.CuriosApi;

public class MoreArtifactsCurioHandler {

    public MoreArtifactsCurioHandler() {
    }

    public static void register() {
    }

    public static boolean isCurioEquipped(LivingEntity entity, Item targetItem) {
        return CuriosApi.getCuriosInventory(entity).resolve()
                .flatMap(curios -> curios.findFirstCurio(stack ->
                        stack.getItem() == targetItem)).isPresent();
    }
}
