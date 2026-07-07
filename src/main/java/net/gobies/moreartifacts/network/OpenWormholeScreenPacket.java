package net.gobies.moreartifacts.network;

import net.gobies.moreartifacts.client.screen.WormholeScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
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
        ctx.get().enqueueWork(() -> Minecraft.getInstance().setScreen(new WormholeScreen(this.players)));
        ctx.get().setPacketHandled(true);
    }
}