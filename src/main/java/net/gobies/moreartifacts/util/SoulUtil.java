package net.gobies.moreartifacts.util;

import net.gobies.moreartifacts.init.MAItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Set;

public class SoulUtil {

    private static final String STAGE = "SoulStage";
    public static final String KILLS = "KillsToUpgrade";
    public static final int REQUIRED_KILLS = 100;

    public static final Set<Item> SOUL_STONES = Set.of(
            MAItems.ShadowSoul.get(),
            MAItems.BloodSoul.get()
    );

    public static ItemStack isSoulEquipped(Player player) {
        for (Item soulStone : SOUL_STONES) {
            ItemStack soul = CurioHandler.getEquippedCurio(player, soulStone);
            if (!soul.isEmpty()) {
                return soul;
            }
        }
        return ItemStack.EMPTY;
    }

    public static int getSoulStage(ItemStack stack) {
        if (!SOUL_STONES.contains(stack.getItem())) return 0;

        CompoundTag nbt = stack.getOrCreateTag();
        if (!nbt.contains(STAGE)) {
            nbt.putInt(STAGE, 1);
        }
        return nbt.getInt(STAGE);
    }

    public static void evolveSoul(ItemStack stack) {
        if (!SOUL_STONES.contains(stack.getItem())) return;
        CompoundTag nbt = stack.getOrCreateTag();
        int currentLevel = getSoulStage(stack);

        if (currentLevel < 5) {
            nbt.putInt(STAGE, currentLevel + 1);
        }
    }
}
