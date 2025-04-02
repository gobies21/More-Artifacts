package net.gobies.moreartifacts.item.potions;

import net.gobies.moreartifacts.Config;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
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
        return 32;
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
            ServerLevel serverWorld = serverPlayer.server.getLevel(serverPlayer.getRespawnDimension());
            boolean interDimensional = Config.RECALL_POTION_INTERDIMENSIONAL.get();
            if (serverWorld != null && (interDimensional || serverWorld.dimension() == Level.OVERWORLD)) {
                if (level.dimension() == Level.OVERWORLD || interDimensional) {
                    Optional<Vec3> respawnLocation = Player.findRespawnPositionAndUseSpawnBlock(serverWorld, Objects.requireNonNull(serverPlayer.getRespawnPosition()), serverPlayer.getRespawnAngle(), false, false);
                    if (respawnLocation.isPresent()) {
                        Vec3 respawnVec = respawnLocation.get();

                        serverPlayer.teleportTo(serverWorld, respawnVec.x, respawnVec.y, respawnVec.z, serverPlayer.getYRot(), serverPlayer.getXRot());
                        serverWorld.playSound(null, respawnVec.x, respawnVec.y, respawnVec.z, SoundEvents.ENDERMAN_TELEPORT, serverPlayer.getSoundSource(), 1.0F, 1.0F);
                        serverPlayer.playSound(SoundEvents.ENDERMAN_TELEPORT, 2.0F, 1.0F);
                        serverPlayer.getCooldowns().addCooldown(stack.getItem(), 20 * Config.RECALL_POTION_COOLDOWN.get()); // 20 ticks = 1 second
                        if (!serverPlayer.isCreative()) {
                            stack.shrink(1);
                            serverPlayer.addItem(new ItemStack(Items.GLASS_BOTTLE, 1));
                        }
                    } else {
                        serverPlayer.displayClientMessage(Component.translatable("moreartifacts.recall.potion.respawn"), true);
                    }
                } else {
                    serverPlayer.displayClientMessage(Component.translatable("moreartifacts.recall.potion.dimension"), true);
                }
            }
        }
        return stack;
    }
}


