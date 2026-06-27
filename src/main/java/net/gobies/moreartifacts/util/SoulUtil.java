package net.gobies.moreartifacts.util;

import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.network.SoulSyncHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Set;
import java.util.UUID;

public class SoulUtil {

    private static final String STAGE = "SoulStage";
    public static final String KILLS = "KillsToUpgrade";

    public static final String SOUL_KEY = "Soul";
    public static final String SHADOW = "Shadow";
    public static final String BLOOD = "Blood";
    public static final String MATRIX = "Matrix";

    public static final Set<Item> SOUL_STONES = Set.of(
            MAItems.ShadowSoul.get(),
            MAItems.BloodSoul.get()
    );

    public static ItemStack isSoulEquipped(Player player) {
        for (Item soulStone : SOUL_STONES) {
            ItemStack soul = CurioHandler.getEquippedCurio(player, soulStone);
            if (!soul.isEmpty()) {
                return soul;
            }
        }
        return ItemStack.EMPTY;
    }

    public static int getSoulStage(ItemStack stack) {
        if (!SOUL_STONES.contains(stack.getItem())) return 0;

        CompoundTag nbt = stack.getOrCreateTag();
        if (!nbt.contains(STAGE)) {
            nbt.putInt(STAGE, 1);
        }
        return nbt.getInt(STAGE);
    }

    public static void evolveSoul(ItemStack stack, Player player) {
        if (!SOUL_STONES.contains(stack.getItem())) return;
        CompoundTag nbt = stack.getOrCreateTag();
        int currentLevel = getSoulStage(stack);

        if (currentLevel < 4) {
            nbt.putInt(STAGE, currentLevel + 1);
        } else if (currentLevel == 4) {
            String soulType = getSoul(stack.getItem());
            player.getPersistentData().putString(SOUL_KEY, soulType);
            if (player instanceof ServerPlayer serverPlayer) {
                SoulSyncHelper.syncSouls(serverPlayer);
            }
            stack.shrink(1);
        }
    }

    public static String getSoul(Item item) {
        if (item == MAItems.ShadowSoul.get()) return SHADOW;
        if (item == MAItems.BloodSoul.get()) return BLOOD;
        return "Unknown";
    }

    public static void removeShadowAttributes(ServerPlayer serverPlayer) {
        CompoundTag persistentData = serverPlayer.getPersistentData();
        persistentData.remove("ShadowSoulInvisToggle");
        UUID SHADOW_SPEED = UUID.fromString("6a4fa28a-7e3e-4d43-bc97-9e48b813b19b");
        UUID SHADOW_ARMOR = UUID.fromString("7f86dbdf-dc8a-4467-89fb-c3491ea9642b");

        var speedAttr = serverPlayer.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speedAttr != null) speedAttr.removeModifier(SHADOW_SPEED);

        var armorAttr = serverPlayer.getAttribute(Attributes.ARMOR);
        if (armorAttr != null) armorAttr.removeModifier(SHADOW_ARMOR);

        if (serverPlayer.hasEffect(net.minecraft.world.effect.MobEffects.INVISIBILITY)) {
            serverPlayer.removeEffect(net.minecraft.world.effect.MobEffects.INVISIBILITY);
        }
    }

    public static void removeBloodAttributes(ServerPlayer serverPlayer) {
        UUID BLOOD_MAX_HEALTH = UUID.fromString("b223ce63-e4c2-4b95-a63c-85c166e41c81");
        UUID BLOOD_ATTACK_SPEED = UUID.fromString("39d7cfe1-2854-4609-9bb0-0ffebcb029c1");
        UUID BLOOD_ARMOR = UUID.fromString("47f4de22-72a7-42a3-b8ed-874e49747e9f");
        UUID BLOOD_ARMOR_TOUGHNESS = UUID.fromString("26a47eb3-bf85-4417-af75-287cf04f77f3");

        var healthAttr = serverPlayer.getAttribute(Attributes.MAX_HEALTH);
        if (healthAttr != null) healthAttr.removeModifier(BLOOD_MAX_HEALTH);

        var attackSpeedAttr = serverPlayer.getAttribute(Attributes.ATTACK_SPEED);
        if (attackSpeedAttr != null) attackSpeedAttr.removeModifier(BLOOD_ATTACK_SPEED);

        var armorAttr = serverPlayer.getAttribute(Attributes.ARMOR);
        if (armorAttr != null) armorAttr.removeModifier(BLOOD_ARMOR);

        var armorToughnessAttr = serverPlayer.getAttribute(Attributes.ARMOR_TOUGHNESS);
        if (armorToughnessAttr != null) armorToughnessAttr.removeModifier(BLOOD_ARMOR_TOUGHNESS);
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
}
