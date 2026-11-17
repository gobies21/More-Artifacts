package net.gobies.moreartifacts.event;

import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.helper.SoulType;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.network.ManaSyncPacket;
import net.gobies.moreartifacts.network.PacketHandler;
import net.gobies.moreartifacts.soul.EnvyHandler;
import net.gobies.moreartifacts.soul.WrathHandler;
import net.gobies.moreartifacts.util.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.*;

public class SoulEvents {

    // All soul functionality
    public static void register() {
        MinecraftForge.EVENT_BUS.register(new SoulEvents());
    }

    // Tick Souls
    @SubscribeEvent
    public void onSoulTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.level().isClientSide()) return;
        Player player = event.player;
        CompoundTag persistentData = player.getPersistentData();

        if (SoulUtil.hasSoul(player, SoulType.ENVY)) {
            EnvyHandler.tickEnvySoul(player, persistentData);
        } else if (SoulUtil.hasSoul(player, SoulType.WRATH)) {
            WrathHandler.tickWrathSoul(player, persistentData);
        }
    }


    // Handle immunities (global) make fully configurable with tooltips (eventually)
    @SubscribeEvent
    public void onMobEffectApplicable(MobEffectEvent.Applicable event) {
        if (event.getEntity() instanceof Player player) {
            if (SoulUtil.hasSoul(player, SoulType.ENVY)) {
                MAUtils.makeEffectImmune(event, MobEffects.BLINDNESS, MobEffects.DARKNESS, MobEffects.MOVEMENT_SLOWDOWN);
            } else if (SoulUtil.hasSoul(player, SoulType.WRATH)) {
                MAUtils.makeEffectImmune(event, MobEffects.WEAKNESS, MobEffects.DIG_SLOWDOWN);
            }
        }
    }

    // Magic conversion (envy) // Sinful/Annihilation (wrath)
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onLivingHurtPre(LivingHurtEvent event) {
        Entity entity = event.getSource().getEntity();
        LivingEntity target = event.getEntity();
        DamageSource source = event.getSource();
        if (entity instanceof LivingEntity livingEntity) {
            EnvyHandler.handleDamageConversion(livingEntity, target, source, event);
        }
        if (target.isAlive()) {
            WrathHandler.handleSinful(source);
        }
        WrathHandler.handleAnnihilation(target, source, event);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onLivingHurtPost(LivingHurtEvent event) {
        Entity entity = event.getSource().getEntity();
        DamageSource source = event.getSource();
        if (entity instanceof LivingEntity livingEntity) {
            EnvyHandler.handleMagicDamageIncrease(livingEntity, source, event);
        }
    }

    // Mana and soul mana flight function
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (event.phase != TickEvent.Phase.END || player.level().isClientSide() || player.isDeadOrDying()) return;
        if (player.isSpectator()) return;
        boolean spellbooksCompat = ModLoadedUtil.isIronsSpellbooksLoaded() && CommonConfig.IRONS_SPELLBOOKS_MANA_COMPAT.get();

        if (SoulUtil.hasSoul(player, SoulType.ENVY)) {
            EnvyHandler.handleFlight(player);
        }
        float currentMana = ManaUtil.getMana(player);

        // Global mana regen
        if (!spellbooksCompat) {
            if (Objects.requireNonNull(player.level().getServer()).getTickCount() % 20 == 0) {
                if (currentMana < ManaUtil.getMaxMana(player)) {
                    if (player.getAbilities().flying && SoulUtil.hasSoul(player, SoulType.ENVY) && !player.isCreative()) return;
                    ManaUtil.tickManaRegen(player);
                }
            }
        }
    }

    // Handle soul mana shielding (envy)
    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onPlayerDamage(LivingDamageEvent event) {
        if (event.isCanceled() || event.getAmount() <= 0.0f) return;
        if (!(event.getEntity() instanceof Player player) || player.level().isClientSide()) return;
        if (SoulUtil.hasSoul(player, SoulType.ENVY) && SoulUtil.getActive(player)) {
            EnvyHandler.handleManaShield(player, event);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onSoulDamage(LivingDamageEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            if (event.getAmount() < event.getEntity().getHealth()) return;
            ItemStack stack = ItemStack.EMPTY;
            for (var item : SoulUtil.SOULS) {
                if (CurioHandler.isCurioEquipped(player, item)) {
                    stack = CurioHandler.getEquippedCurio(player, item);
                    break;
                }
            }

            if (!stack.isEmpty()) {
                int currentStage = SoulUtil.getSoulStage(stack);

                if (currentStage >= 1 && currentStage <= 4) {
                    var nbt = stack.getOrCreateTag();

                    float damageDealt = nbt.getFloat("SoulDamage") + event.getEntity().getMaxHealth();
                    nbt.putFloat("SoulDamage", damageDealt);

                    float requiredDamage = getSoulDamage(stack, currentStage);

                    if (requiredDamage > 0f && damageDealt >= requiredDamage) {
                        SoulUtil.evolveSoul(stack, player);
                        nbt.putFloat("SoulDamage", 0f);
                    }
                }
            }
        }
    }

    private float getSoulDamage(ItemStack stack, int stage) {
        if (stack.is(MAItems.EnvySoul.get())) {
            return switch (stage) {
                case 1 -> CommonConfig.ENVY_EVOLVE_DAMAGE_1.get().floatValue();
                case 2 -> CommonConfig.ENVY_EVOLVE_DAMAGE_2.get().floatValue();
                case 3 -> CommonConfig.ENVY_EVOLVE_DAMAGE_3.get().floatValue();
                case 4 -> CommonConfig.ENVY_EVOLVE_DAMAGE_4.get().floatValue();
                default -> 0f;
            };
        } else if (stack.is(MAItems.WrathSoul.get())) {
            return switch (stage) {
                case 1 -> CommonConfig.WRATH_EVOLVE_DAMAGE_1.get().floatValue();
                case 2 -> CommonConfig.WRATH_EVOLVE_DAMAGE_2.get().floatValue();
                case 3 -> CommonConfig.WRATH_EVOLVE_DAMAGE_3.get().floatValue();
                case 4 -> CommonConfig.WRATH_EVOLVE_DAMAGE_4.get().floatValue();
                default -> 0f;
            };
        }
        return 0f;
    }

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            float currentMana = ManaUtil.getMana(serverPlayer);
            PacketHandler.sendToClient(new ManaSyncPacket(currentMana), serverPlayer);
        }
    }

    public static void clearMaps(UUID uuid) {
        SoulUtil.activePassive.remove(uuid);
        EnvyHandler.darkAreaCache.remove(uuid);
        WrathHandler.annihilation.remove(uuid);
    }
}