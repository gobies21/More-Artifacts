package net.gobies.moreartifacts.item.souls;

import net.gobies.moreartifacts.util.SoulUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;


public class SoulItem extends Item implements ICurioItem {
    public SoulItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull Rarity getRarity(@NotNull ItemStack pStack) {
        int level = SoulUtil.getSoulStage(pStack);
        return switch (level) {
            case 2, 3 -> Rarity.RARE;
            case 4, 5 -> Rarity.EPIC;
            default -> Rarity.UNCOMMON;
        };
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            ItemStack soul = SoulUtil.isSoulEquipped(player);
            if (!soul.isEmpty()) {
                return soul == stack;
            }
        }
        return true;
    }

    // TODO: Soul of the Matrix
}
