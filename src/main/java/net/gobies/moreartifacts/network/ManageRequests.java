package net.gobies.moreartifacts.network;

import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.util.MAUtils;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ManageRequests {
    private static final Map<UUID, UUID> activeRequests = new ConcurrentHashMap<>();

    public static void createRequest(ServerPlayer requester, ServerPlayer target) {
        if (requester == null || target == null) return;

        activeRequests.put(target.getUUID(), requester.getUUID());

        HoverEvent acceptHover = new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.literal("Click to accept teleport"));
        HoverEvent denyHover = new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.literal("Click to deny teleport"));

        Component acceptButton = Component.literal("[ACCEPT]")
                .withStyle(Style.EMPTY.withColor(0x00FF00).withBold(true)
                        .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wormhole_accept"))
                        .withHoverEvent(acceptHover));

        Component denyButton = Component.literal("[DENY]")
                .withStyle(Style.EMPTY.withColor(0xFF0000).withBold(true)
                        .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wormhole_deny"))
                        .withHoverEvent(denyHover));

        Component message = Component.literal(requester.getGameProfile().getName() + " wants to teleport to you! ")
                .append(acceptButton)
                .append(" ")
                .append(denyButton);

        target.sendSystemMessage(message);
        requester.sendSystemMessage(Component.literal("Teleport request sent to " + target.getGameProfile().getName()));
    }

    public static void handleResponse(ServerPlayer target, boolean accepted) {
        if (target == null || target.level().isClientSide()) return;

        UUID requesterUuid = activeRequests.remove(target.getUUID());
        if (requesterUuid == null) {
            target.sendSystemMessage(Component.literal("You have no pending requests"));
            return;
        }

        ServerPlayer requester = target.server.getPlayerList().getPlayer(requesterUuid);
        if (requester == null) {
            target.sendSystemMessage(Component.literal("The requesting player is offline"));
            return;
        }

        String targetName = target.getGameProfile().getName();
        String requesterName = requester.getGameProfile().getName();
        ServerLevel requesterLevel = requester.serverLevel();
        ServerLevel targetLevel = target.serverLevel();
        Vec3 currentVec = requester.position();
        Vec3 targetVec = target.position();

        boolean interDimensional = CommonConfig.WORMHOLE_POTION_INTERDIMENSIONAL.get();
        ResourceKey<Level> requesterDimension = requesterLevel.dimension();
        ResourceKey<Level> targetDimension = targetLevel.dimension();

        if (accepted) {
            if (interDimensional || requesterDimension.equals(targetDimension)) {
                MAUtils.spawnPortalParticles(requesterLevel, currentVec);
                requester.teleportTo(target.serverLevel(), target.getX(), target.getY(), target.getZ(), target.getYRot(), target.getXRot());
                requesterLevel.playSound(null, requester.getX(), requester.getY(), requester.getZ(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
                targetLevel.playSound(null, target.getX(), target.getY(), target.getZ(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
                MAUtils.spawnPortalParticles(requesterLevel, targetVec);
                requester.sendSystemMessage(Component.literal("Teleport request accepted! Teleporting..."));
                target.sendSystemMessage(Component.literal("You accepted " + requesterName + "'s teleport request"));
            } else {
                requester.sendSystemMessage(Component.literal("Wormholes cannot go through dimensions"));
                target.sendSystemMessage(Component.literal("Wormholes cannot go through dimensions"));
            }
        } else {
            requester.sendSystemMessage(Component.literal(targetName + " denied your request"));
            target.sendSystemMessage(Component.literal("Request denied"));
        }
    }

    public static void clearMaps(UUID playerUuid) {
        activeRequests.remove(playerUuid);
        activeRequests.values().removeIf(uuid -> uuid.equals(playerUuid));
    }
}