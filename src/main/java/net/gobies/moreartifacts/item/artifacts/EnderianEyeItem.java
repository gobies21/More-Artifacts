package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.Config;
import net.gobies.moreartifacts.event.ClientEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import javax.annotation.Nullable;
import java.util.List;

public class EnderianEyeItem extends Item implements ICurioItem {
    public EnderianEyeItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.RARE));
    }

    static {
        MinecraftForge.EVENT_BUS.register(EnderianEyeItem.class);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    public static void enderianEyeParticles(Player player, Vec3 targetPosition) {
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.teleportTo(targetPosition.x, targetPosition.y, targetPosition.z);
            spawnPortalParticles(serverPlayer.level(), targetPosition);
        }
    }

    private static void spawnPortalParticles(Level level, Vec3 position) {
        if (level instanceof ServerLevel serverLevel) {
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    for (int z = -1; z <= 1; z++) {
                        Vec3 particlePos = position.add(x * 0.3, y * 0.5, z * 0.3);
                        serverLevel.sendParticles(ParticleTypes.PORTAL, particlePos.x, particlePos.y, particlePos.z, 10, 0.1, 0.1, 0.1, 0.1);
                    }
                }
            }
        }
    }
    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        int radius = Config.ENDERIAN_EYE_RADIUS.get();
        double cooldown = Config.ENDERIAN_EYE_COOLDOWN.get();
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.enderian_eye.teleport").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.enderian_eye.radius.cooldown", radius, cooldown).withStyle(ChatFormatting.DARK_AQUA));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.press").withStyle(ChatFormatting.GOLD).append(Component.literal(ClientEvents.TELEPORT_KEY.getTranslatedKeyMessage().getString()).withStyle(ChatFormatting.DARK_AQUA)).append(Component.translatable("tooltip.moreartifacts.teleport").withStyle(ChatFormatting.GOLD)));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}