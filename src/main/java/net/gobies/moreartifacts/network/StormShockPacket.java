package net.gobies.moreartifacts.network;

import net.gobies.moreartifacts.client.renderer.effect.StormShockRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record StormShockPacket(int sourceId, int targetId) {

    public StormShockPacket(FriendlyByteBuf buffer) {
        this(buffer.readInt(), buffer.readInt());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeInt(sourceId);
        buffer.writeInt(targetId);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            Minecraft mc = Minecraft.getInstance();
            if (mc.level == null) return;

            Entity source = mc.level.getEntity(sourceId);
            Entity target = mc.level.getEntity(targetId);
            if (source != null && target != null) {
                StormShockRenderer.spawn(source, target);
            }
        }));

        context.setPacketHandled(true);
    }
}