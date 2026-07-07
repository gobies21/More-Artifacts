package net.gobies.moreartifacts.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record SelectWormholeTargetPacket(String targetName) {

    public SelectWormholeTargetPacket(FriendlyByteBuf buf) {
        this(buf.readUtf());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeUtf(this.targetName);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer requester = ctx.get().getSender();
            if (requester != null) {
                ServerPlayer target = requester.server.getPlayerList().getPlayerByName(this.targetName);
                if (target != null) {
                    ManageRequests.createRequest(requester, target);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}