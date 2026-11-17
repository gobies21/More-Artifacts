package net.gobies.moreartifacts.network;

import net.gobies.moreartifacts.item.artifacts.StormFangItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class StormShockBindMessage {

    public StormShockBindMessage() {}

    public StormShockBindMessage(FriendlyByteBuf buffer) {}

    public static void buffer(StormShockBindMessage message, FriendlyByteBuf buffer) {}

    public static void handle(StormShockBindMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();

        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();

            if (player != null) {
                StormFangItem.triggerStormShock(player);
            }
        });

        context.setPacketHandled(true);
    }
}