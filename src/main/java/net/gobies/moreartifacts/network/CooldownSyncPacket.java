package net.gobies.moreartifacts.network;

import net.gobies.moreartifacts.util.CooldownHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record CooldownSyncPacket(String artifactId) {

    public CooldownSyncPacket(FriendlyByteBuf buffer) {
        this(buffer.readUtf());
    }

    public static void encode(CooldownSyncPacket msg, FriendlyByteBuf buffer) {
        buffer.writeUtf(msg.artifactId());
    }

    public static void handle(CooldownSyncPacket msg, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context context = ctxSupplier.get();
        context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientHandler.process(msg.artifactId())));
        context.setPacketHandled(true);
    }

    private static class ClientHandler {
        private static void process(String id) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.getSingleplayerServer() != null) return;

            Player player = mc.player;
            if (player != null) {
                CooldownHandler.updateCooldown(player, id);
            }
        }
    }
}