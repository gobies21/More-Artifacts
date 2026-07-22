package net.gobies.moreartifacts.util;

import net.gobies.moreartifacts.MoreArtifacts;
import net.gobies.moreartifacts.init.MAItems;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = MoreArtifacts.MOD_ID)
public class BrokenHeartSystem {

    public static final UUID BROKEN_HEART_HEALTH_UUID = UUID.fromString("4640ad16-b37a-4af1-8473-28b572cf99ee");
    public static final String BROKEN_HEARTS = "BrokenHearts";

    public static int getBrokenHearts(Player player) {
        return player.getPersistentData().getInt(BROKEN_HEARTS);
    }

    public static AttributeModifier getBrokenHeartModifier(Player player) {
        AttributeInstance attribute = player.getAttribute(Attributes.MAX_HEALTH);
        if (attribute == null) {
            return null;
        }
        return attribute.getModifier(BrokenHeartSystem.BROKEN_HEART_HEALTH_UUID);
    }

    public static void addBrokenHearts(ServerPlayer player, int hearts) {
        int current = getBrokenHearts(player);
        int total = current + hearts;

        player.getPersistentData().putInt(BROKEN_HEARTS, total);

        applyHealthPenalty(player);
    }

    public static void removeBrokenHearts(ServerPlayer player, int hearts) {
        if (hearts <= 0) return;
        int current = getBrokenHearts(player);
        int total = Math.max(0, current - hearts);

        player.getPersistentData().putInt(BROKEN_HEARTS, total);

        applyHealthPenalty(player);
    }

    public static void clearBrokenHearts(ServerPlayer player) {
        if (getBrokenHearts(player) <= 0) return;

        player.getPersistentData().putInt(BROKEN_HEARTS, 0);
        AttributeInstance attribute = player.getAttribute(Attributes.MAX_HEALTH);
        if (attribute == null) {
            return;
        }

        if (attribute.getModifier(BROKEN_HEART_HEALTH_UUID) != null) {
            attribute.removeModifier(BROKEN_HEART_HEALTH_UUID);
        }

        player.setHealth(player.getMaxHealth());
    }

    public static void applyHealthPenalty(ServerPlayer player) {
        AttributeInstance attribute = player.getAttribute(Attributes.MAX_HEALTH);
        if (attribute == null) {
            return;
        }

        int brokenHearts = getBrokenHearts(player);
        float healthLoss = brokenHearts * 2.0F;

        AttributeModifier existing = attribute.getModifier(BROKEN_HEART_HEALTH_UUID);
        if (existing != null) {
            if (existing.getAmount() == -healthLoss) {
                return;
            }
            attribute.removeModifier(existing);
        }
        if (healthLoss > 0) {
            attribute.addTransientModifier(new AttributeModifier(BROKEN_HEART_HEALTH_UUID, "ma_broken_hearts", -healthLoss, AttributeModifier.Operation.ADDITION));
        }

        if (player.getHealth() > player.getMaxHealth()) {
            player.setHealth(player.getMaxHealth());
        }
    }

    @SubscribeEvent
    public static void onRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            clearBrokenHearts(player);
        }
    }

    @SubscribeEvent
    public static void onPlayerWake(PlayerWakeUpEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            if (CurioHandler.isCurioEquipped(player, MAItems.BrokenHeart.get())) {
                clearBrokenHearts(player);
                player.heal(player.getMaxHealth());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!(event.getEntity() instanceof ServerPlayer newPlayer)) return;
        Player oldPlayer = event.getOriginal();

        if (oldPlayer instanceof ServerPlayer) {
            int brokenHearts = getBrokenHearts(oldPlayer);
            newPlayer.getPersistentData().putInt(BROKEN_HEARTS, brokenHearts);

            if (!event.isWasDeath()) {
                applyHealthPenalty(newPlayer);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            applyHealthPenalty(player);
        }
    }
}