package net.gobies.moreartifacts.network;

import net.gobies.moreartifacts.helper.SoulType;
import net.gobies.moreartifacts.util.MAUtils;
import net.gobies.moreartifacts.util.SoulUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

public record SoulSyncPacket(UUID playerId, CompoundTag data, boolean active) {

    public SoulSyncPacket(FriendlyByteBuf buffer) {
        this(buffer.readUUID(), buffer.readNbt(), buffer.readBoolean());
    }

    public static void encode(SoulSyncPacket msg, FriendlyByteBuf buffer) {
        buffer.writeUUID(msg.playerId());
        buffer.writeNbt(msg.data());
        buffer.writeBoolean(msg.active());
    }

    public static void handle(SoulSyncPacket msg, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            String soulType = msg.data.getString(SoulUtil.SOUL_KEY);

            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null && mc.player.getUUID().equals(msg.playerId)) {
                SoulClientData.setActiveSoul(soulType);
                CompoundTag clientData = mc.player.getPersistentData();
                clientData.putString(SoulUtil.SOUL_KEY, soulType);
                if (msg.data.contains(SoulUtil.PASSIVE_TOGGLE)) {
                    clientData.putBoolean(SoulUtil.PASSIVE_TOGGLE, msg.data.getBoolean(SoulUtil.PASSIVE_TOGGLE));
                }
                if (msg.data.contains(SoulUtil.ABILITY_TOGGLE)) {
                    clientData.putBoolean(SoulUtil.ABILITY_TOGGLE, msg.data.getBoolean(SoulUtil.ABILITY_TOGGLE));
                }
            }

            SoulClientData.setPlayerSoul(msg.playerId, soulType, msg.active);
            if (mc.screen instanceof AbstractContainerScreen<?> containerScreen) {
                containerScreen.init(mc, containerScreen.width, containerScreen.height);
            }
        });
        context.setPacketHandled(true);
    }

    public static class SoulClientData {
        private static String activeSoul;

        private static final Map<UUID, String> SOULS = new HashMap<>();
        private static final Map<UUID, Boolean> ACTIVE = new HashMap<>();

        public static void setActiveSoul(String soul) {
            activeSoul = soul;
        }

        public static String getActiveSoul() {
            return activeSoul;
        }

        public static void setPlayerSoul(UUID uuid, String soul, boolean active) {
            SOULS.put(uuid, soul);
            ACTIVE.put(uuid, active);

            MAUtils.logDebug("Soul Client Data for: {UUID: " + uuid + ", Soul: " + soul + ", Active: " + active + "}");
        }

        public static boolean hasSoul(UUID uuid, SoulType soul) {
            return soul.key.equals(SOULS.get(uuid));
        }

        public static boolean hasActiveSoul(UUID uuid, SoulType soul) {
            return soul.key.equals(SOULS.get(uuid)) && ACTIVE.getOrDefault(uuid, false);
        }

        public static void clearMaps(UUID uuid) {
            SOULS.remove(uuid);
            ACTIVE.remove(uuid);
        }

        public static void clearAll() {
            SOULS.clear();
            ACTIVE.clear();
            activeSoul = null;
        }
    }
}