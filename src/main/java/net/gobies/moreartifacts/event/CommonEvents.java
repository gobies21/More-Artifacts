package net.gobies.moreartifacts.event;

import net.gobies.moreartifacts.network.InvisibilityBindMessage;
import net.gobies.moreartifacts.network.NightVisionBindMessage;
import net.gobies.moreartifacts.network.PacketHandler;
import net.gobies.moreartifacts.network.TeleportBindMessage;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonEvents {
    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        PacketHandler.addNetworkMessage(TeleportBindMessage.class, TeleportBindMessage::buffer, TeleportBindMessage::new, TeleportBindMessage::handle);
        PacketHandler.addNetworkMessage(InvisibilityBindMessage.class, InvisibilityBindMessage::buffer, InvisibilityBindMessage::new, InvisibilityBindMessage::handle);
        PacketHandler.addNetworkMessage(NightVisionBindMessage.class, NightVisionBindMessage::buffer, NightVisionBindMessage::new, NightVisionBindMessage::handle);
    }
}
