package net.gobies.moreartifacts.network;

import net.gobies.moreartifacts.util.SoulUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record SoulSyncPacket(CompoundTag data) {

    public SoulSyncPacket(FriendlyByteBuf buf) {
        this(buf.readNbt());
    }

    public static void encode(SoulSyncPacket msg, FriendlyByteBuf buf) {
        buf.writeNbt(msg.data);
    }

    public static void handle(SoulSyncPacket msg, Supplier<NetworkEvent.Context> ctxStr) {
        NetworkEvent.Context context = ctxStr.get();
        context.enqueueWork(() -> {
            String soulType = msg.data.getString(SoulUtil.SOUL_KEY);

            SoulClientData.setActiveSoul(soulType);

            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null) {
                CompoundTag clientData = mc.player.getPersistentData();
                clientData.putString(SoulUtil.SOUL_KEY, soulType);
                if (msg.data.contains("SoulAbilityToggle")) {
                    clientData.putBoolean("SoulAbilityToggle", msg.data.getBoolean("SoulAbilityToggle"));
                }
            }

            if (mc.screen instanceof AbstractContainerScreen<?> containerScreen) {
                containerScreen.init(mc, containerScreen.width, containerScreen.height);
            }
        });
        context.setPacketHandled(true);
    }

    public static class SoulClientData {
        private static String activeSoul = "";

        public static void setActiveSoul(String soul) {
            activeSoul = soul;
        }

        public static String getActiveSoul() {
            return activeSoul;
        }
    }
}