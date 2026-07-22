package net.gobies.moreartifacts.helper;

import net.gobies.moreartifacts.MoreArtifacts;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@Mod.EventBusSubscriber(modid = MoreArtifacts.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LifecycleManager {

    // Helper class to clear maps
    private static final List<Consumer<UUID>> CLEANUP_HOOKS = new ArrayList<>();

    public static void registerCleanupHook(Consumer<UUID> hook) {
        CLEANUP_HOOKS.add(hook);
    }

    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        executeCleanup(event.getEntity().getUUID());
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.isWasDeath() || !event.getOriginal().level().isClientSide()) {
            executeCleanup(event.getOriginal().getUUID());
        }
    }

    @SubscribeEvent
    public static void onEntityLeaveLevel(EntityLeaveLevelEvent event) {
        if (event.getLevel().isClientSide()) return;
        Entity entity = event.getEntity();
        if (!(entity instanceof Player)) {
            executeCleanup(entity.getUUID());
        }
    }

    private static void executeCleanup(UUID uuid) {
        for (Consumer<UUID> hook : CLEANUP_HOOKS) {
            try {
                hook.accept(uuid);
            } catch (Exception e) {
                MoreArtifacts.LOGGER.error("Cleanup hook failed", e);
            }
        }
    }
}