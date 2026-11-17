package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.event.ClientEvents;
import net.gobies.moreartifacts.util.MAUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import javax.annotation.Nullable;
import java.util.List;

public class EnderianEyeItem extends Item implements ICurioItem {
    public EnderianEyeItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.RARE));
    }

    public static void enderianEyeParticles(Player player, Vec3 targetPosition) {
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.teleportTo(targetPosition.x, targetPosition.y, targetPosition.z);
            MAUtils.spawnPortalParticles(serverPlayer, targetPosition);
        }
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        int radius = CommonConfig.ENDERIAN_EYE_RADIUS.get();
        double cooldown = CommonConfig.ENDERIAN_EYE_COOLDOWN.get();
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.enderian_eye.teleport").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.enderian_eye.radius.cooldown", radius, MAUtils.formatDecimal(cooldown)).withStyle(ChatFormatting.BLUE));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.press").withStyle(ChatFormatting.GOLD).append(Component.literal(ClientEvents.EYE_TELEPORT_KEY.getTranslatedKeyMessage().getString()).withStyle(ChatFormatting.YELLOW)).append(Component.translatable("tooltip.moreartifacts.teleport").withStyle(ChatFormatting.WHITE)));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}