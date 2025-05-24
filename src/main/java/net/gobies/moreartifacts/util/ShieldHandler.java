package net.gobies.moreartifacts.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class ShieldHandler {

    public static boolean isShieldEquipped(Player player, Item targetItem) {
        Item mainHandItem = player.getMainHandItem().getItem();
        Item offHandItem = player.getOffhandItem().getItem();

        return mainHandItem == targetItem || offHandItem == targetItem;
    }
}