package net.gobies.moreartifacts.network;

import net.gobies.moreartifacts.client.screen.WormholeScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public record OpenWormholeScreenPacket(List<String> players) {

    public OpenWormholeScreenPacket(FriendlyByteBuf buffer) {
        this((List<String>) buffer.readCollection(ArrayList::new, FriendlyByteBuf::readUtf));
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeCollection(this.players, FriendlyByteBuf::writeUtf);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientHandler.process(this.players)));
        context.setPacketHandled(true);
    }

    private static class ClientHandler {
        private static void process(List<String> players) {
            Minecraft.getInstance().setScreen(new WormholeScreen(players));
        }
    }
}