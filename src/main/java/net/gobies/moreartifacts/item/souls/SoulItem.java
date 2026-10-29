package net.gobies.moreartifacts.item.souls;

import net.gobies.moreartifacts.util.SoulUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class SoulItem extends Item implements ICurioItem {
    public SoulItem(Properties properties) {
        super(properties.stacksTo(1).fireResistant());
    }

    public static final Map<UUID, Map<MobEffect, ItemStack>> appliedEffect = new HashMap<>();

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

    // Limit souls to 1
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            String soul = player.getPersistentData().getString(SoulUtil.SOUL_KEY);
            if (!soul.isEmpty() && !"Unknown".equals(soul)) {
                return false;
            }
            ItemStack soulEquipped = SoulUtil.isSoulEquipped(player);
            if (!soulEquipped.isEmpty()) {
                return soulEquipped == stack;
            }
        }
        return true;
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (slotContext.entity() instanceof ServerPlayer player) {
            SoulUtil.imbueSoul(stack, player);
        }
    }


    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) {
        return super.getName(stack).copy().withStyle(ChatFormatting.GOLD);
    }

    public static void clearMaps(UUID uuid) {
        appliedEffect.remove(uuid);
    }

    // TODO: Soul of the Matrix
}
