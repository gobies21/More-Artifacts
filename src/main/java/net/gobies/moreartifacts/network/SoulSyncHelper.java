package net.gobies.moreartifacts.network;

import net.gobies.moreartifacts.MoreArtifacts;
import net.gobies.moreartifacts.util.SoulUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MoreArtifacts.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SoulSyncHelper {

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) syncSouls(player);
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) syncSouls(player);
    }

    @SubscribeEvent
    public static void onPlayerDimensionChange(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) syncSouls(player);
    }

    public static void syncSouls(ServerPlayer player) {
        CompoundTag persistent = player.getPersistentData();
        String currentSoul = persistent.getString(SoulUtil.SOUL_KEY);

        CompoundTag tag = new CompoundTag();
        tag.putString(SoulUtil.SOUL_KEY, currentSoul);

        if (persistent.contains("SoulAbilityToggle")) {
            tag.putBoolean("SoulAbilityToggle", persistent.getBoolean("SoulAbilityToggle"));
        }

        PacketHandler.sendToClient(new SoulSyncPacket(tag), player);
    }
}