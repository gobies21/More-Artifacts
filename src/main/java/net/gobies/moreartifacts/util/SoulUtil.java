package net.gobies.moreartifacts.util;

import net.gobies.moreartifacts.helper.SoulType;
import net.gobies.moreartifacts.init.MAEffects;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.helper.SoulSyncHelper;
import net.gobies.moreartifacts.network.SoulSyncPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class SoulUtil {

    public static final String SOUL_KEY = "Soul";
    private static final String STAGE = "SoulStage";
    public static final String SoulDamage = "SoulDamage";

    public static final String PASSIVE_TOGGLE = "SoulPassiveToggle";
    public static final String ABILITY_TOGGLE = "SoulAbilityToggle";
    public static final Set<UUID> activePassive = new HashSet<>();

    public static final Set<Item> SOULS = Set.of(
            MAItems.EnvySoul.get(),
            MAItems.WrathSoul.get()
    );

    public static ItemStack isSoulEquipped(Player player) {
        for (Item soulStone : SOULS) {
            ItemStack soul = CurioHandler.getEquippedCurio(player, soulStone);
            if (!soul.isEmpty()) {
                return soul;
            }
        }
        return ItemStack.EMPTY;
    }

    public static int getSoulStage(ItemStack stack) {
        if (!SOULS.contains(stack.getItem())) return 0;

        CompoundTag nbt = stack.getOrCreateTag();
        if (!nbt.contains(STAGE)) {
            nbt.putInt(STAGE, 1);
        }
        return nbt.getInt(STAGE);
    }

    public static boolean hasSoul(Player player, SoulType soulType) {
        if (soulType == SoulType.UNKNOWN) return false;
        return soulType.key.equals(player.getPersistentData().getString(SoulUtil.SOUL_KEY));
    }

    public static boolean getPassive(Player player) {
        CompoundTag persistentData = player.getPersistentData();
        return persistentData.getBoolean(SoulUtil.PASSIVE_TOGGLE);
    }

    public static void setPassive(Player player, boolean passiveToggle) {
        CompoundTag persistentData = player.getPersistentData();
        persistentData.putBoolean(SoulUtil.PASSIVE_TOGGLE, passiveToggle);
    }

    public static boolean getActive(Player player) {
        CompoundTag persistentData = player.getPersistentData();
        return persistentData.getBoolean(SoulUtil.ABILITY_TOGGLE);
    }

    public static void setActive(Player player, boolean abilityToggle) {
        CompoundTag persistentData = player.getPersistentData();
        persistentData.putBoolean(SoulUtil.ABILITY_TOGGLE, abilityToggle);
    }

    public static boolean hasActiveSoul(Player player, SoulType soul) {
        if (player.level().isClientSide) {
            return SoulSyncPacket.SoulClientData.hasActiveSoul(player.getUUID(), soul);
        }
        return hasSoul(player, soul) && getActive(player);
    }

    public static void evolveSoul(ItemStack stack, Player player) {
        if (!SOULS.contains(stack.getItem())) return;
        CompoundTag nbt = stack.getOrCreateTag();
        int currentLevel = getSoulStage(stack);

        if (currentLevel < 4) {
            nbt.putInt(STAGE, currentLevel + 1);
        } else if (currentLevel == 4) {
            String soulType = getSoul(stack.getItem());
            player.getPersistentData().putString(SOUL_KEY, soulType);
            if (player instanceof ServerPlayer serverPlayer) {
                SoulSyncHelper.syncSouls(serverPlayer);
                MAUtils.awardAdvancement(serverPlayer, "soul_max");
                LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(serverPlayer.level());
                if (lightning != null) {
                    lightning.moveTo(serverPlayer.position());
                    serverPlayer.level().addFreshEntity(lightning);
                }
            }
            stack.shrink(1);
        }
    }

    public static String getSoul(Item item) {
        if (item == MAItems.EnvySoul.get()) return SoulType.ENVY.key;
        if (item == MAItems.WrathSoul.get()) return SoulType.WRATH.key;
        return SoulType.UNKNOWN.key;
    }

    public static void imbueSoul(ItemStack stack, ServerPlayer player) {
        if (getSoulStage(stack) == 5) {
            String soulType = getSoul(stack.getItem());
            player.getPersistentData().putString(SOUL_KEY, soulType);
            SoulSyncHelper.syncSouls(player);
            stack.shrink(1);
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.WARDEN_HEARTBEAT, SoundSource.PLAYERS, 1.5F, 0.8F);
        }
    }

    public static void applySinful(Player player, int duration, int maxLevel) {
        MobEffectInstance activeEffect = player.getEffect(MAEffects.Sinful.get());
        int amplifier = 0;

        if (activeEffect != null) {
            amplifier = activeEffect.getAmplifier();

            if (amplifier < (maxLevel - 1)) {
                amplifier++;
            }
        }

        player.addEffect(new MobEffectInstance(MAEffects.Sinful.get(), duration, amplifier, false, false, true));
    }
}
