package net.gobies.moreartifacts.network;

import net.gobies.moreartifacts.client.overlay.AnnihilationStackOverlay;
import net.gobies.moreartifacts.config.CommonConfig;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record AnnihilationStackSyncPacket(int stacks) {

    public AnnihilationStackSyncPacket(int stacks) {
        this.stacks = Math.max(0, Math.min(CommonConfig.WRATH_ANNIHILATION_MAX_STACKS.get(), stacks));
    }

    public AnnihilationStackSyncPacket(FriendlyByteBuf buffer) {
        this(buffer.readVarInt());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeVarInt(stacks);
    }

    public static void handle(AnnihilationStackSyncPacket message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> AnnihilationStackOverlay.setStacks(message.stacks)));
        context.setPacketHandled(true);
    }
}