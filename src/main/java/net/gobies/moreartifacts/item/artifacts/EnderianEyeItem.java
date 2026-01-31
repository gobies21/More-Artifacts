package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.event.ClientEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
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
            spawnPortalParticles(serverPlayer, targetPosition);
        }
    }

    private static void spawnPortalParticles(ServerPlayer serverPlayer, Vec3 position) {
        if (position != null) {
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    for (int z = -1; z <= 1; z++) {
                        Vec3 particlePos = position.add(x * 0.3, y * 0.5, z * 0.3);
                        serverPlayer.connection.send(new ClientboundLevelParticlesPacket(ParticleTypes.PORTAL, true, particlePos.x, particlePos.y, particlePos.z, 0.1F, 0.1F, 0.1F, 0.1F, 10));
                    }
                }
            }
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
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.enderian_eye.radius.cooldown", radius, cooldown).withStyle(ChatFormatting.DARK_AQUA));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.press").withStyle(ChatFormatting.GOLD).append(Component.literal(ClientEvents.EYE_TELEPORT_KEY.getTranslatedKeyMessage().getString()).withStyle(ChatFormatting.YELLOW)).append(Component.translatable("tooltip.moreartifacts.teleport").withStyle(ChatFormatting.WHITE)));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}