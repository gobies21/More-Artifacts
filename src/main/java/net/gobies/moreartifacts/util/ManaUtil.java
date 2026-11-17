package net.gobies.moreartifacts.util;

import net.gobies.moreartifacts.compat.spellbooks.SpellbooksCompat;
import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.helper.SoulType;
import net.gobies.moreartifacts.network.ManaSyncPacket;
import net.gobies.moreartifacts.network.PacketHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class ManaUtil {
    private static final String MANA_KEY = "MAMana";

    public static float getMaxMana(Player player) {
        if (useIronsMana()) return SpellbooksCompat.getMaxMana(player);
        return CommonConfig.DEFAULT_MAX_MANA.get().floatValue() + getMaxManaBonus(player);
    }

    private static float getMaxManaBonus(Player player) {
        float bonus = 0.0F;

        if (SoulUtil.hasSoul(player, SoulType.ENVY)) {
            bonus += 100.0F;
        }

        return bonus;
    }

    public static float getMana(Player player) {
        if (useIronsMana()) {
            return SpellbooksCompat.getMana(player);
        }
        CompoundTag data = player.getPersistentData();

        if (!data.contains(MANA_KEY)) {
            data.putFloat(MANA_KEY, getMaxMana(player));
            return getMaxMana(player);
        }
        return data.getFloat(MANA_KEY);
    }

    public static void setMana(Player player, float amount) {
        if (useIronsMana()) {
            float maxMana = SpellbooksCompat.getMaxMana(player);
            float mana = Math.max(0.0f, Math.min(amount, maxMana));
            SpellbooksCompat.setMana(player, mana);
            SpellbooksCompat.syncPlayerMana((int) mana);
            return;
        }

        float mana = Math.max(0.0f, Math.min(amount, getMaxMana(player)));
        player.getPersistentData().putFloat(MANA_KEY, mana);

        if (player instanceof ServerPlayer serverPlayer) {
            PacketHandler.sendToClient(new ManaSyncPacket(mana), serverPlayer);
        }
    }

    public static void setManaCost(Player player, float cost) {
        float mana = getMana(player);
        setMana(player, Math.max(0.0f, mana - cost));
    }

    public static float getManaRegen() {
        return CommonConfig.DEFAULT_MANA_REGEN.get().floatValue();
    }

    public static void tickManaRegen(Player player) {
        float mana = getMana(player);
        float manaRegen = getManaRegen();
        ManaUtil.setMana(player, mana + manaRegen);
    }

    private static boolean useIronsMana() {
        return ModLoadedUtil.isIronsSpellbooksLoaded() && CommonConfig.IRONS_SPELLBOOKS_MANA_COMPAT.get();
    }
}