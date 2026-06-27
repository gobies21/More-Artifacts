package net.gobies.moreartifacts.helper;

import net.gobies.moreartifacts.MoreArtifacts;
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

    private static void executeCleanup(UUID uuid) {
        for (Consumer<UUID> hook : CLEANUP_HOOKS) {
            hook.accept(uuid);
        }
    }
}