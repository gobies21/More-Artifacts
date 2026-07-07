package net.gobies.moreartifacts.event;

import net.gobies.moreartifacts.MoreArtifacts;
import net.gobies.moreartifacts.network.ManageRequests;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MoreArtifacts.MOD_ID)
public class WormholeEvent {
    @SubscribeEvent
    public static void onCommandExecuted(CommandEvent event) {
        String command = event.getParseResults().getReader().getString();
        if (command.equals("/wormhole_accept") || command.equals("/wormhole_deny")) {
            event.setCanceled(true);

            if (event.getParseResults().getContext().getSource().getEntity() instanceof ServerPlayer player) {
                boolean accepted = command.equals("/wormhole_accept");
                ManageRequests.handleResponse(player, accepted);
            }
        }
    }
}
