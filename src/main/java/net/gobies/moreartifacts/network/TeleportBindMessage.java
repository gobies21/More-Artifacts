package net.gobies.moreartifacts.network;

import net.gobies.moreartifacts.util.Teleport;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public class TeleportBindMessage {

    int type, pressedms;

    public TeleportBindMessage(int type, int pressedms) {
        this.type = type;
        this.pressedms = pressedms;
    }

    public TeleportBindMessage(FriendlyByteBuf buffer) {
        this.type = buffer.readInt();
        this.pressedms = buffer.readInt();
    }

    public static void buffer(TeleportBindMessage message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.type);
        buffer.writeInt(message.pressedms);
    }

    public static void handle(TeleportBindMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> pressAction(Objects.requireNonNull(context.getSender()), message.type));
        context.setPacketHandled(true);
    }

    public static void pressAction(Player player, int type) {
        Level world = player.level();
        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();

        if (!world.isLoaded(player.blockPosition()))
            return;

        if (type == 0) {
            Teleport.teleportPlayer(world, x, y, z, player);
        }
    }
}