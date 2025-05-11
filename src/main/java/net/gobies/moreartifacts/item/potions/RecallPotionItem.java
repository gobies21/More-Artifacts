package net.gobies.moreartifacts.item.potions;

import net.gobies.moreartifacts.Config;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;

public class RecallPotionItem extends Item {
    public RecallPotionItem(Properties properties) {
        super(properties.stacksTo(16).rarity(Rarity.RARE));
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return Config.RECALL_POTION_USE_TIME.get();
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return Config.RECALL_POTION_GLOW.get();
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.getItem() instanceof RecallPotionItem) {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(itemStack);
        }
        return InteractionResultHolder.pass(itemStack);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity entityLiving) {
        if (!level.isClientSide && entityLiving instanceof ServerPlayer serverPlayer) {
            boolean interDimensional = Config.RECALL_POTION_INTERDIMENSIONAL.get();
            ResourceKey<Level> currentDimension = level.dimension();
            ResourceKey<Level> respawnDimension = serverPlayer.getRespawnDimension();
            ServerLevel serverWorld = interDimensional ? serverPlayer.server.getLevel(respawnDimension) : (currentDimension == respawnDimension ? Objects.requireNonNull(level.getServer()).getLevel(currentDimension) : null);
            if (serverWorld != null) {
                    try {
                        Optional<Vec3> respawnLocation = Player.findRespawnPositionAndUseSpawnBlock(serverWorld, Objects.requireNonNull(serverPlayer.getRespawnPosition()), serverPlayer.getRespawnAngle(), false, false);
                        if (respawnLocation.isPresent()) {
                            Vec3 respawnVec = respawnLocation.get();

                            serverPlayer.teleportTo(serverWorld, respawnVec.x, respawnVec.y, respawnVec.z, serverPlayer.getYRot(), serverPlayer.getXRot());
                            serverWorld.playSound(null, respawnVec.x, respawnVec.y, respawnVec.z, SoundEvents.ENDERMAN_TELEPORT, serverPlayer.getSoundSource(), 1.0F, 1.0F);
                            level.playSound((Player) null, respawnVec.x, respawnVec.y, respawnVec.z, SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
                            level.playSound((Player) null, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
                            serverPlayer.getCooldowns().addCooldown(stack.getItem(), 20 * Config.RECALL_POTION_COOLDOWN.get()); // 20 ticks = 1 second
                            if (!serverPlayer.isCreative()) {
                                if (stack.getCount() == 1) {
                                    serverPlayer.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.GLASS_BOTTLE, 1));
                                } else {
                                    stack.shrink(1);
                                    serverPlayer.addItem(new ItemStack(Items.GLASS_BOTTLE, 1));
                                }
                            }
                        } else {
                            // no respawn point
                            serverPlayer.displayClientMessage(Component.translatable("moreartifacts.recall.potion.respawn"), true);
                        }
                    } catch (Exception e) {
                        // error finding spawn with no spawn point
                        serverPlayer.displayClientMessage(Component.translatable("moreartifacts.recall.potion.respawn"), true);
                    }
                } else {
                    // no dimension teleport
                    serverPlayer.displayClientMessage(Component.translatable("moreartifacts.recall.potion.dimension"), true);
                }
            }
        return stack;
    }
}


