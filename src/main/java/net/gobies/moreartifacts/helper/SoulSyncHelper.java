package net.gobies.moreartifacts.helper;

import net.gobies.moreartifacts.MoreArtifacts;
import net.gobies.moreartifacts.network.PacketHandler;
import net.gobies.moreartifacts.network.SoulSyncPacket;
import net.gobies.moreartifacts.util.ManaUtil;
import net.gobies.moreartifacts.util.SoulUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MoreArtifacts.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SoulSyncHelper {

    // Sync server soul abilities and updates to client
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer joiningPlayer) {
            for (ServerPlayer player : joiningPlayer.serverLevel().players()) {
                syncSoulsToPlayer(player, joiningPlayer);

                if (player != joiningPlayer) {
                    syncSoulsToPlayer(joiningPlayer, player);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            syncSouls(player);
        }
    }

    @SubscribeEvent
    public static void onPlayerDimensionChange(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            syncSouls(player);
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        Player oldPlayer = event.getOriginal();
        Player newPlayer = event.getEntity();

        CompoundTag oldData = oldPlayer.getPersistentData();
        CompoundTag newData = newPlayer.getPersistentData();

        String activeSoul = oldData.getString(SoulUtil.SOUL_KEY);

        if (!activeSoul.isEmpty()) {
            newData.putString(SoulUtil.SOUL_KEY, activeSoul);

            if (SoulType.ENVY.key.equals(activeSoul)) {
                newData.putBoolean(SoulUtil.PASSIVE_TOGGLE, oldData.getBoolean(SoulUtil.PASSIVE_TOGGLE));
                SoulAttributes.manageEnvyAttributes(newPlayer);
                if (event.isWasDeath()) {
                    float maxMana = ManaUtil.getMana(newPlayer);
                    ManaUtil.setMana(newPlayer, maxMana);
                }
            } else if (SoulType.WRATH.key.equals(activeSoul)) {
                SoulAttributes.manageWrathAttributes(newPlayer);
            }
        }
    }

    public static void syncSouls(ServerPlayer player) {
        for (ServerPlayer serverPlayer : player.serverLevel().players()) {
            syncSoulsToPlayer(player, serverPlayer);
        }
    }

    private static void syncSoulsToPlayer(ServerPlayer source, ServerPlayer recipient) {
        PacketHandler.sendToClient(createSoulPacket(source), recipient);
    }

    private static SoulSyncPacket createSoulPacket(ServerPlayer player) {
        CompoundTag persistent = player.getPersistentData();
        CompoundTag tag = new CompoundTag();
        tag.putString(SoulUtil.SOUL_KEY, persistent.getString(SoulUtil.SOUL_KEY));
        if (persistent.contains(SoulUtil.PASSIVE_TOGGLE)) {
            tag.putBoolean(SoulUtil.PASSIVE_TOGGLE, persistent.getBoolean(SoulUtil.PASSIVE_TOGGLE));
        }
        if (persistent.contains(SoulUtil.ABILITY_TOGGLE)) {
            tag.putBoolean(SoulUtil.ABILITY_TOGGLE, persistent.getBoolean(SoulUtil.ABILITY_TOGGLE));
        }

        return new SoulSyncPacket(player.getUUID(), tag, SoulUtil.getActive(player));
    }
}