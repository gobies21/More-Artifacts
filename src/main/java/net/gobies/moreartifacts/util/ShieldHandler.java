package net.gobies.moreartifacts.util;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;

public class ShieldHandler {

    public static boolean isShieldEquipped(Player player, Item targetItem) {
        Item mainHandItem = player.getMainHandItem().getItem();
        Item offHandItem = player.getOffhandItem().getItem();

        return mainHandItem == targetItem || offHandItem == targetItem;
    }

    public static boolean isBlocking(Player player) {
        ItemStack mainHandStack = player.getMainHandItem();
        ItemStack offHandStack = player.getOffhandItem();

        boolean isMainHandBlocking = mainHandStack.getItem() instanceof ShieldItem
                && player.isUsingItem() && player.getUsedItemHand() == InteractionHand.MAIN_HAND;
        boolean isOffHandBlocking = offHandStack.getItem() instanceof ShieldItem
                && player.isUsingItem() && player.getUsedItemHand() == InteractionHand.OFF_HAND;

        return isMainHandBlocking || isOffHandBlocking;
    }
}
