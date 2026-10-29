package net.gobies.moreartifacts.network;

import net.gobies.moreartifacts.client.overlay.ManaHudOverlay;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;

public record ManaSyncPacket(float mana) {

    public ManaSyncPacket(FriendlyByteBuf buf) {
        this(buf.readFloat());
    }

    public static void encode(ManaSyncPacket msg, FriendlyByteBuf buf) {
        buf.writeFloat(msg.mana);
    }

    public static void handle(ManaSyncPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> ManaHudOverlay.mana = msg.mana);
        ctx.get().setPacketHandled(true);
    }
}