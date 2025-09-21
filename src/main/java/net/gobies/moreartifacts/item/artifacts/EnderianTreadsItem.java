package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.util.CurioHandler;
import net.gobies.moreartifacts.util.MAUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class EnderianTreadsItem extends Item implements ICurioItem {
    public EnderianTreadsItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.EPIC));
    }

    static {
        MinecraftForge.EVENT_BUS.register(EnderianTreadsItem.class);
    }


    private static final UUID SPEED = UUID.randomUUID();
    private static final Map<UUID, Long> cooldownMap = new HashMap<>();


    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            MAUtils.addAttributes(player, Attributes.MOVEMENT_SPEED, CommonConfig.ENDERIAN_TREADS_SPEED.get(), AttributeModifier.Operation.MULTIPLY_BASE, String.valueOf(SPEED));
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            MAUtils.removeAttributes(player, Attributes.MOVEMENT_SPEED, String.valueOf(SPEED));
        }
    }

    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            if (CurioHandler.isCurioEquipped(player, MAItems.EnderianTreads.get())) {
                if (player.getHealth() - event.getAmount() <= 0) {
                    if (canUseAbility(player)) {
                        teleportPlayer(player);
                        setCooldown(player);
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

    private static boolean canUseAbility(Player player) {
        Long lastUsed = cooldownMap.get(player.getUUID());
        long currentTime = System.currentTimeMillis();
        long cooldownDuration = (long) (1000L * CommonConfig.ENDERIAN_TREADS_COOLDOWN.get());

        if (lastUsed == null) {
            MAUtils.logDebug("Teleported: {}" + player.getUUID());
            return true;
        }

        long timeSinceLastUse = currentTime - lastUsed;

        return timeSinceLastUse >= cooldownDuration;
    }


    private static void teleportPlayer(ServerPlayer player) {
        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();

        for (int i = 0; i < 20; i++) {
            double offsetX = (Math.random() * 7) + 10;
            double offsetY = (Math.random() * 7) - 3; // Random offset between -3 and 3
            double offsetZ = (Math.random() * 7) + 10;

            int offsetXInt = (int) Math.round(offsetX);
            int offsetYInt = (int) Math.round(offsetY);
            int offsetZInt = (int) Math.round(offsetZ);

            BlockPos targetPos = player.blockPosition().offset(offsetXInt, offsetYInt, offsetZInt);
            BlockPos targetPosBelow = targetPos.below();

            if (!player.level().getBlockState(targetPosBelow).getBlock().equals(Blocks.AIR) &&
                    player.level().getBlockState(targetPos).getBlock().equals(Blocks.AIR)) {
                player.teleportTo(x + offsetX, y + offsetY, z + offsetZ);
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 2.0F, 1.0F);
                player.displayClientMessage(Component.translatable("message.moreartifacts.enderian_treads.evade").withStyle(ChatFormatting.LIGHT_PURPLE), true);
                player.getCooldowns().addCooldown(MAItems.EnderianTreads.get(), (int) (20 * CommonConfig.ENDERIAN_TREADS_COOLDOWN.get()));
                break;
            }
        }
    }

    private static void setCooldown(Player player) {
        cooldownMap.put(player.getUUID(), System.currentTimeMillis());
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        double movementSpeed = CommonConfig.ENDERIAN_TREADS_SPEED.get() * 100;
        double evadeCooldown = CommonConfig.ENDERIAN_TREADS_COOLDOWN.get();
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.enderian_treads.speed", String.format("%.1f", movementSpeed)).withStyle(ChatFormatting.DARK_AQUA));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.enderian_treads.evade").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.enderian_treads.cooldown", evadeCooldown).withStyle(ChatFormatting.DARK_AQUA));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
