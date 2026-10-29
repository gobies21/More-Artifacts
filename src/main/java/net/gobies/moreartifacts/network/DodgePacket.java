package net.gobies.moreartifacts.network;

import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.util.CooldownHandler;
import net.gobies.moreartifacts.util.DodgeProperties;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;

public record DodgePacket(DodgeProperties.DodgeDirection direction) {

    public static void encode(DodgePacket msg, FriendlyByteBuf buffer) {
        buffer.writeEnum(msg.direction);
    }

    public static DodgePacket decode(FriendlyByteBuf buffer) {
        return new DodgePacket(buffer.readEnum(DodgeProperties.DodgeDirection.class));
    }

    public static void handle(DodgePacket msg, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) return;

            ItemStack stack = DodgeProperties.getDodgeItem(player);

            if (stack != null && !stack.isEmpty()) {
                double cooldown = DodgeProperties.getDodgeCooldown(stack);

                if (CooldownHandler.isReady(player, DodgeProperties.DODGE_COOLDOWN, cooldown)) {
                    boolean airDodge = CommonConfig.ENABLE_AIR_DODGE.get();
                    if (!airDodge && !player.onGround()) return;
                    double distance = DodgeProperties.getDistance(stack, player);
                    double lift = DodgeProperties.getLift(stack);
                    boolean blink = DodgeProperties.isBlink(stack);

                    DodgeProperties.executeDodge(player, msg.direction, distance, lift, blink);
                    CooldownHandler.updateCooldown(player, DodgeProperties.DODGE_COOLDOWN);

                    int cooldownTicks = (int) (cooldown * 20.0);
                    player.getCooldowns().addCooldown(stack.getItem(), cooldownTicks);
                }
            }
        });
        context.setPacketHandled(true);
    }
}