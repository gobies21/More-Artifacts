package net.gobies.moreartifacts.compat.firstaid;

import ichttt.mods.firstaid.FirstAid;
import ichttt.mods.firstaid.FirstAidConfig;
import ichttt.mods.firstaid.api.damagesystem.AbstractDamageablePart;
import ichttt.mods.firstaid.api.event.FirstAidLivingDamageEvent;
import ichttt.mods.firstaid.common.network.MessageUpdatePart;
import ichttt.mods.firstaid.common.util.CommonUtils;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.util.BrokenHeartSystem;
import net.gobies.moreartifacts.util.CurioHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;

public class FirstAidCompat {

    public static void loadCompat() {
        MinecraftForge.EVENT_BUS.register(new FirstAidCompat());
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onFirstAidLivingDamage(FirstAidLivingDamageEvent event) {
        if (event.isCanceled()) return;
        Player player = event.getEntity();
        if (player == null || player.level().isClientSide() || !(player instanceof ServerPlayer serverPlayer)) {
            return;
        }

        // Totem check, will try to make it more global in the future
        if (player.getMainHandItem().is(Items.TOTEM_OF_UNDYING) || player.getOffhandItem().is(Items.TOTEM_OF_UNDYING)) return;
        if (event.getUndistributedDamage() > 1000) return;
        if (CurioHandler.isCurioEquipped(serverPlayer, MAItems.BrokenHeart.get())) {
            boolean dead = false;
            List<AbstractDamageablePart> fatalParts = new ArrayList<>();

            for (AbstractDamageablePart part : event.getAfterDamage()) {
                if (part.canCauseDeath && part.currentHealth <= 0) {
                    if (part.getMaxHealth() >= 2) {
                        fatalParts.add(part);
                    } else {
                        dead = true;
                    }
                }
            }

            if (!dead && !fatalParts.isEmpty()) {
                AttributeInstance maxHealth = serverPlayer.getAttribute(Attributes.MAX_HEALTH);
                if (maxHealth == null) {
                    return;
                }

                AttributeModifier activeModifier = maxHealth.getModifier(BrokenHeartSystem.BROKEN_HEART_HEALTH_UUID);
                double brokenHeartReduction = activeModifier != null ? activeModifier.getAmount() : 0.0;
                double currentMaxHealth = maxHealth.getValue();
                double originalMaxHealth = currentMaxHealth - brokenHeartReduction;
                double healthToBreak = (originalMaxHealth * 0.2) + (double) (fatalParts.size() * 2) + event.getUndistributedDamage();

                if (healthToBreak > currentMaxHealth - 2.0) {
                    return;
                }

                // Prevent first aid from killing player
                event.setCanceled(true);
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.IRON_GOLEM_DAMAGE, SoundSource.PLAYERS, 1.0F, 0.8F);

                int heartsToBreak = Math.max(1, (int) Math.ceil(healthToBreak / 2.0F));
                int maxHeartContainers = Mth.ceil((float) originalMaxHealth / 2.0F) - 1;
                int currentBrokenHearts = BrokenHeartSystem.getBrokenHearts(serverPlayer);
                int availableContainersLeft = maxHeartContainers - currentBrokenHearts;

                if (heartsToBreak > availableContainersLeft) {
                    heartsToBreak = availableContainersLeft;
                }
                BrokenHeartSystem.addBrokenHearts(serverPlayer, heartsToBreak);

                float adjustedMaxLimbHealth = serverPlayer.getMaxHealth();
                for (AbstractDamageablePart part : event.getAfterDamage()) {
                    if (fatalParts.contains(part)) {
                        part.currentHealth = part.getMaxHealth();
                        MessageUpdatePart updatePacket = new MessageUpdatePart(part);
                        FirstAid.NETWORKING.send(PacketDistributor.PLAYER.with(() -> serverPlayer), updatePacket);
                    }
                }
                serverPlayer.setHealth(adjustedMaxLimbHealth);
            }
        }
    }
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onPlayerWake(PlayerWakeUpEvent event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) {
            return;
        }
        if (player instanceof ServerPlayer serverPlayer) {
            if (CurioHandler.isCurioEquipped(serverPlayer, MAItems.BrokenHeart.get())) {
                BrokenHeartSystem.clearBrokenHearts(serverPlayer);
                CommonUtils.getOptionalDamageModel(serverPlayer).ifPresent(damageModel -> {
                    try {
                        damageModel.runScaleLogic(serverPlayer);
                    } catch (NoSuchMethodError | NoClassDefFoundError e) {
                        AttributeInstance attribute = serverPlayer.getAttribute(Attributes.MAX_HEALTH);
                        if (attribute != null) {
                            attribute.setBaseValue(attribute.getBaseValue());
                        }
                    }
                    CommonUtils.healPlayerByPercentage(1.0, damageModel, serverPlayer);
                    for (AbstractDamageablePart part : damageModel) {
                        MessageUpdatePart updatePacket = new MessageUpdatePart(part);
                        FirstAid.NETWORKING.send(PacketDistributor.PLAYER.with(() -> serverPlayer), updatePacket);
                    }
                });
            }
        }
    }
    public static boolean isVanillaHealthBarActive() {
        if (FirstAidConfig.CLIENT.vanillaHealthBarMode.get().equals(FirstAidConfig.Client.VanillaHealthbarMode.NORMAL)) {
            return true;
        }
        if (FirstAidConfig.CLIENT.vanillaHealthBarMode.get().equals(FirstAidConfig.Client.VanillaHealthbarMode.HIGHLIGHT_CRITICAL_PATH)) {
            return true;
        }
        FirstAidConfig.CLIENT.vanillaHealthBarMode.get();
        return false;
    }
}