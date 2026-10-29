package net.gobies.moreartifacts.util;

import net.gobies.moreartifacts.compat.spellbooks.SpellbooksCompat;
import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.network.ManaSyncPacket;
import net.gobies.moreartifacts.network.PacketHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class ManaUtil {
    private static final String MANA_KEY = "SoulMana";
    public static final float DEFAULT_MAX_MANA = 200.0f;

    public static float getMana(Player player) {
        if (useIronsMana()) {
            return SpellbooksCompat.getMana(player);
        }

        CompoundTag data = player.getPersistentData();

        if (!data.contains(MANA_KEY)) {
            data.putFloat(MANA_KEY, DEFAULT_MAX_MANA);
            return DEFAULT_MAX_MANA;
        }
        return data.getFloat(MANA_KEY);
    }

    public static void setMana(Player player, float amount) {
        if (useIronsMana()) {
            float maxMana = SpellbooksCompat.getMaxMana(player);
            float mana = Math.max(0.0f, Math.min(amount, maxMana));
            SpellbooksCompat.setMana(player, mana);
            return;
        }

        float mana = Math.max(0.0f, Math.min(amount, DEFAULT_MAX_MANA));
        player.getPersistentData().putFloat(MANA_KEY, mana);

        if (player instanceof ServerPlayer serverPlayer) {
            PacketHandler.sendToClient(new ManaSyncPacket(mana), serverPlayer);
        }
    }

    private static boolean useIronsMana() {
        return ModLoadedUtil.isIronsSpellbooksLoaded() && CommonConfig.IRONS_SPELLBOOKS_MANA_COMPAT.get();
    }
}