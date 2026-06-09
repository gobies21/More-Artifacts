package net.gobies.moreartifacts.util;

import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownHandler {
    // Per artifact cooldowns
    private static final Map<UUID, Map<String, Long>> cooldownMap = new HashMap<>();

    public static double getCooldownTimer(Player player, String artifactId) {
        long currentTime = System.currentTimeMillis();
        Map<String, Long> playerCooldowns = cooldownMap.getOrDefault(player.getUUID(), new HashMap<>());
        long lastTime = playerCooldowns.getOrDefault(artifactId, 0L);
        return (currentTime - lastTime) / 1000.0;
    }

    // Update cooldown
    public static void updateCooldown(Player player, String artifactId) {
        long time = System.currentTimeMillis();
        cooldownMap.computeIfAbsent(player.getUUID(), k -> new HashMap<>()).put(artifactId, time);
    }

    // Check ready
    public static boolean isReady(Player player, String artifactId, double cooldown) {
        long currentTime = System.currentTimeMillis();
        Map<String, Long> playerCooldowns = cooldownMap.getOrDefault(player.getUUID(), new HashMap<>());
        long lastTime = playerCooldowns.getOrDefault(artifactId, 0L);
        return (currentTime - lastTime) >= (cooldown * 1000);
    }
    public static void clearMaps(UUID uuid) {
        cooldownMap.remove(uuid);
    }
}