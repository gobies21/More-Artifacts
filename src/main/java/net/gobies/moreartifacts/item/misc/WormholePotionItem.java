package net.gobies.moreartifacts.item.misc;

import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.network.OpenWormholeScreenPacket;
import net.gobies.moreartifacts.network.PacketHandler;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class WormholePotionItem extends Item {
    public WormholePotionItem(Properties properties) {
        super(properties.stacksTo(16).rarity(Rarity.RARE));
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return CommonConfig.WORMHOLE_POTION_USE_TIME.get();
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return CommonConfig.WORMHOLE_POTION_GLOW.get();
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity entity) {
        if (entity instanceof ServerPlayer serverPlayer) {
            if (!serverPlayer.isCreative()) {
                if (stack.getCount() == 1) {
                    if (serverPlayer.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof WormholePotionItem) {
                        serverPlayer.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.GLASS_BOTTLE, 1));
                    } else {
                        serverPlayer.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.GLASS_BOTTLE, 1));
                    }
                } else {
                    stack.shrink(1);
                    serverPlayer.addItem(new ItemStack(Items.GLASS_BOTTLE, 1));
                }
            }

            serverPlayer.awardStat(Stats.ITEM_USED.get(this));
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);

            List<ServerPlayer> otherPlayers = serverPlayer.server.getPlayerList().getPlayers();
            List<String> playerNames = new ArrayList<>(otherPlayers.size());
            for (ServerPlayer onlinePlayer : otherPlayers) {
                if (!onlinePlayer.getUUID().equals(serverPlayer.getUUID())) {
                    playerNames.add(onlinePlayer.getGameProfile().getName());
                }
            }

            if (!playerNames.isEmpty()) {
                PacketHandler.sendToClient(new OpenWormholeScreenPacket(playerNames), serverPlayer);
                serverPlayer.getCooldowns().addCooldown(stack.getItem(), 20 * CommonConfig.WORMHOLE_POTION_COOLDOWN.get()); // 20 ticks = 1 second
            } else {
                serverPlayer.sendSystemMessage(Component.literal("No players available for teleport"));
            }
        }
        return stack;
    }
}


