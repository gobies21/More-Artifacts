package net.gobies.moreartifacts.network;

import net.gobies.moreartifacts.util.Teleport;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
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

    public static void handler(TeleportBindMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> pressAction(Objects.requireNonNull(context.getSender()), message.type, message.pressedms));
        context.setPacketHandled(true);
    }

    public static void pressAction(Player entity, int type, int pressedms) {
        Level world = entity.level();
        double x = entity.getX();
        double y = entity.getY();
        double z = entity.getZ();

        if (!world.isLoaded(entity.blockPosition()))
            return;

        if (type == 0) {
            Teleport.teleportPlayer(world, x, y, z, entity);
        }
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        PacketHandler.addNetworkMessage(TeleportBindMessage.class, TeleportBindMessage::buffer, TeleportBindMessage::new, TeleportBindMessage::handler);
    }
}