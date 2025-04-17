package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.Config;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
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
import java.util.Optional;

import static net.gobies.moreartifacts.event.ClientEvents.TELEPORT_KEY;

public class EnderianEyeItem extends Item implements ICurioItem {
    public EnderianEyeItem(Properties properties) {
        super(new Properties().stacksTo(1).rarity(Rarity.RARE));

    }

    static {
        MinecraftForge.EVENT_BUS.register(EnderianEyeItem.class);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("§6Grants the ability to teleport where you are looking"));
        pTooltipComponents.add(Component.literal(String.format("§6Max radius of §3%d §6blocks with a §3%.1f §6second cooldown", Config.ENDERIAN_EYE_RADIUS.get(), Config.ENDERIAN_EYE_COOLDOWN.get())));
        pTooltipComponents.add(Component.literal("§6Press ").append(Component.literal(TELEPORT_KEY.getTranslatedKeyMessage().getString()).withStyle(ChatFormatting.DARK_AQUA)).append(Component.literal("§6 to teleport")));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
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
}