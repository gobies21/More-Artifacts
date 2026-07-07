package net.gobies.moreartifacts.network;

import net.gobies.moreartifacts.MoreArtifacts;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class PacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(MoreArtifacts.MOD_ID), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
    private static int id = 0;

    public static void registerMessages() {
        addNetworkMessage(TeleportBindMessage.class, TeleportBindMessage::buffer, TeleportBindMessage::new, TeleportBindMessage::handle);
        addNetworkMessage(InvisibilityBindMessage.class, InvisibilityBindMessage::buffer, InvisibilityBindMessage::new, InvisibilityBindMessage::handle);
        addNetworkMessage(NightVisionBindMessage.class, NightVisionBindMessage::buffer, NightVisionBindMessage::new, NightVisionBindMessage::handle);
        addNetworkMessage(OpenWormholeScreenPacket.class, OpenWormholeScreenPacket::encode, OpenWormholeScreenPacket::new, OpenWormholeScreenPacket::handle);
        addNetworkMessage(SelectWormholeTargetPacket.class, SelectWormholeTargetPacket::encode, SelectWormholeTargetPacket::new, SelectWormholeTargetPacket::handle);
        addNetworkMessage(SoulSyncPacket.class, SoulSyncPacket::encode, SoulSyncPacket::new, SoulSyncPacket::handle);
        addNetworkMessage(CooldownSyncPacket.class, CooldownSyncPacket::encode, CooldownSyncPacket::new, CooldownSyncPacket::handle);
    }

    public static <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
        INSTANCE.registerMessage(id, messageType, encoder, decoder, messageConsumer);
        id++;
    }

    public static void sendToClient(Object message, ServerPlayer player) {
        if (!player.level().isClientSide()) {
            INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
        }
    }

    public static void sendToServer(Object message) {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            INSTANCE.sendToServer(message);
        }
    }
}