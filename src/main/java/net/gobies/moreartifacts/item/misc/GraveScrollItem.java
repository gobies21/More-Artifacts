package net.gobies.moreartifacts.item.misc;

import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.util.MAUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class GraveScrollItem extends Item {
    public GraveScrollItem(Properties properties) {
        super(properties.stacksTo(64).rarity(Rarity.UNCOMMON));
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack pStack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack pStack) {
        return CommonConfig.GRAVE_SCROLL_USE_TIME.get();
    }

    @Override
    public boolean isFoil(@NotNull ItemStack pStack) {
        return CommonConfig.GRAVE_SCROLL_GLOW.get();
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity livingEntity) {
        if (!level.isClientSide() && livingEntity instanceof ServerPlayer serverPlayer) {
            serverPlayer.getLastDeathLocation().ifPresent(deathPosition -> {
                boolean interDimensional = CommonConfig.GRAVE_SCROLL_INTERDIMENSIONAL.get();
                ResourceKey<Level> currentDimension = level.dimension();
                ResourceKey<Level> deathDimension = deathPosition.dimension();

                if (interDimensional || currentDimension.equals(deathDimension)) {
                    ServerLevel targetLevel = serverPlayer.server.getLevel(deathPosition.dimension());

                    if (targetLevel != null) {
                        BlockPos currentPos = serverPlayer.blockPosition();
                        BlockPos deathPos = deathPosition.pos();

                        serverPlayer.awardStat(Stats.ITEM_USED.get(this));
                        CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);

                        MAUtils.spawnPortalParticles(serverPlayer, Vec3.atLowerCornerOf(currentPos));

                        serverPlayer.teleportTo(targetLevel, deathPos.getX(), deathPos.getY(), deathPos.getZ(), serverPlayer.getYRot(), serverPlayer.getXRot());
                        MAUtils.spawnPortalParticles(serverPlayer, Vec3.atLowerCornerOf(deathPos));
                        targetLevel.playSound(null, deathPos.getX(), deathPos.getY(), deathPos.getZ(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);

                        serverPlayer.invulnerableTime = 30;
                        if (!serverPlayer.getAbilities().instabuild) {
                            stack.shrink(1);
                            serverPlayer.getCooldowns().addCooldown(stack.getItem(), 20 * CommonConfig.GRAVE_SCROLL_COOLDOWN.get());
                        }
                    }
                } else {
                    serverPlayer.displayClientMessage(Component.translatable("tooltip.moreartifacts.grave_scroll.dimension"), true);
                }
            });
        }
        return stack;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.grave_scroll").withStyle(ChatFormatting.GRAY));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}










