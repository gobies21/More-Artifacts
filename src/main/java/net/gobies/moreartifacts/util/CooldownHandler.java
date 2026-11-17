package net.gobies.moreartifacts.util;

import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownHandler {
    // Per artifact cooldowns
    private static final Map<UUID, Map<String, Long>> cooldownMap = new HashMap<>();

    public static double getCooldown(Player player, String artifactId) {
        long currentTime = player.level().getGameTime();
        Map<String, Long> playerCooldowns = cooldownMap.getOrDefault(player.getUUID(), new HashMap<>());
        long lastTime = playerCooldowns.getOrDefault(artifactId, 0L);
        return (currentTime - lastTime) / 20.0;
    }

    // Update cooldown
    public static void updateCooldown(Player player, String artifactId) {
        long time = player.level().getGameTime();
        cooldownMap.computeIfAbsent(player.getUUID(), uuid -> new HashMap<>()).put(artifactId, time);
    }

    // Check ready
    public static boolean isReady(Player player, String artifactId, double cooldown) {
        long currentTime = player.level().getGameTime();
        Map<String, Long> playerCooldowns = cooldownMap.getOrDefault(player.getUUID(), new HashMap<>());
        long lastTime = playerCooldowns.getOrDefault(artifactId, 0L);
        return (currentTime - lastTime) >= (cooldown * 20);
    }

    public static void clearMaps(UUID uuid) {
        cooldownMap.remove(uuid);
    }
}