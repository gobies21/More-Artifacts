package net.gobies.moreartifacts.event;

import net.gobies.moreartifacts.compat.betterstealth.BetterStealthCompat;
import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.helper.SoulAttributes;
import net.gobies.moreartifacts.helper.SoulType;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.network.ManaSyncPacket;
import net.gobies.moreartifacts.network.PacketHandler;
import net.gobies.moreartifacts.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public class SoulEvents {

    public static void register() {
        MinecraftForge.EVENT_BUS.register(new SoulEvents());
    }

    private static final UUID SHADOW_STEALTH = UUID.fromString("0a6493b8-6ee4-436f-b2f5-b2230489cf00");

    private static final Set<UUID> activeAbility = new HashSet<>();
    private static final Set<UUID> darkAreaCache = new HashSet<>();

    @SubscribeEvent
    public void onSoulTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.level().isClientSide()) return;
        Player player = event.player;
        CompoundTag persistentData = player.getPersistentData();

        String activeSoul = persistentData.getString(SoulUtil.SOUL_KEY);

        if (SoulType.ENVY.key.equals(activeSoul)) {
            tickEnvySoul(player, persistentData);
        } else if (SoulType.WRATH.key.equals(activeSoul)) {
            tickWrathSoul(player, persistentData);
        }
    }

    private static void tickEnvySoul(Player player, CompoundTag persistentData) {
        UUID playerId = player.getUUID();
        boolean isDarkArea;

        if (player.tickCount % 5 == 0) {
            if (player.tickCount % 10 == 0) {
                SoulAttributes.manageEnvyAttributes(player);
            }

            BlockPos pos = player.blockPosition();
            int lightLevel = player.level().getMaxLocalRawBrightness(pos);
            isDarkArea = lightLevel <= 7;

            if (isDarkArea) {
                darkAreaCache.add(playerId);
            } else {
                darkAreaCache.remove(playerId);
            }

            if (ModLoadedUtil.isBetterStealthLoaded()) {
                updateEnvyStealthModifier(player, isDarkArea);
            }

            isDarkArea = darkAreaCache.contains(playerId);

            if (!persistentData.contains(SoulUtil.ABILITY_TOGGLE)) {
                persistentData.putBoolean(SoulUtil.ABILITY_TOGGLE, true);
            }

            boolean abilityActive = persistentData.getBoolean(SoulUtil.ABILITY_TOGGLE) && isDarkArea;
            boolean isActive = activeAbility.contains(playerId);
            MobEffectInstance currentEffect = player.getEffect(MobEffects.INVISIBILITY);

            if (abilityActive) {
                if (currentEffect == null || !isActive) {
                    player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 210, 0, true, false));
                    activeAbility.add(playerId);
                } else if (currentEffect.getDuration() <= 110) {
                    player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 210, 0, true, false));
                }
            } else if (isActive) {
                if (currentEffect != null) {
                    player.removeEffect(MobEffects.INVISIBILITY);
                }
                activeAbility.remove(playerId);
            }
            MAUtils.removeEffect(player, MobEffects.MOVEMENT_SLOWDOWN, MobEffects.BLINDNESS, MobEffects.DARKNESS);
        }
    }

    private static void updateEnvyStealthModifier(Player player, boolean isDarkArea) {
        if (isDarkArea) {
            MAUtils.addAttributes(player, BetterStealthCompat.stealthAttribute(), 0.5, AttributeModifier.Operation.ADDITION, String.valueOf(SHADOW_STEALTH));
        } else {
            MAUtils.removeAttributes(player, BetterStealthCompat.stealthAttribute(), String.valueOf(SHADOW_STEALTH));
        }
    }

    private void tickWrathSoul(Player player, CompoundTag persistentData) {
        UUID playerId = player.getUUID();
        if (player.tickCount % 5 == 0) {
            if (player.tickCount % 10 == 0) {
                SoulAttributes.manageWrathAttributes(player);
            }

            if (!persistentData.contains(SoulUtil.ABILITY_TOGGLE)) {
                persistentData.putBoolean(SoulUtil.ABILITY_TOGGLE, true);
            }

            boolean abilityActive = persistentData.getBoolean(SoulUtil.ABILITY_TOGGLE);
            boolean isActive = activeAbility.contains(playerId);
            MobEffectInstance currentEffect = player.getEffect(MobEffects.DAMAGE_BOOST);

            if (abilityActive) {
                if (currentEffect == null || !isActive) {
                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 210, 0, true, false));
                    activeAbility.add(playerId);
                } else if (currentEffect.getDuration() <= 110) {
                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 210, 0, true, false));
                }
            } else if (isActive) {
                if (currentEffect != null) {
                    player.removeEffect(MobEffects.DAMAGE_BOOST);
                }
                activeAbility.remove(playerId);
            }
            MAUtils.removeEffect(player, MobEffects.POISON, MobEffects.WITHER, MobEffects.DIG_SLOWDOWN);
        }
    }

    @SubscribeEvent
    public void onMobEffectApplicable(MobEffectEvent.Applicable event) {
        if (event.getEntity() instanceof Player player) {
            CompoundTag persistentData = player.getPersistentData();
            String activeSoul = persistentData.getString(SoulUtil.SOUL_KEY);
            if (SoulType.ENVY.key.equals(activeSoul)) {
                MAUtils.makeEffectImmune(event, MobEffects.MOVEMENT_SLOWDOWN, MobEffects.BLINDNESS, MobEffects.DARKNESS);
            } else if (SoulType.WRATH.key.equals(activeSoul)) {
                MAUtils.makeEffectImmune(event, MobEffects.WEAKNESS, MobEffects.DIG_SLOWDOWN);
            }
        }
    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        if (event.getEntity().level().isClientSide) return;
        if (event.getSource().getEntity() instanceof Player player) {
            if (event.getEntity() == player) return;
            ItemStack soul = SoulUtil.isSoulEquipped(player);
            CompoundTag persistentData = player.getPersistentData();
            String activeSoul = persistentData.getString(SoulUtil.SOUL_KEY);

            if (SoulUtil.getSoulStage(soul) == 4) {
                if (LuckHelper.roll(player, 0.25, 0.01)) {
                    SoulUtil.applySinful(player, 110, 4);
                }
            } else if (SoulType.WRATH.key.equals(activeSoul)) {
                if (LuckHelper.roll(player, 0.50, 0.01)) {
                    SoulUtil.applySinful(player, 110, 5);
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event) {
        Player oldPlayer = event.getOriginal();
        Player newPlayer = event.getEntity();

        CompoundTag oldData = oldPlayer.getPersistentData();
        CompoundTag newData = newPlayer.getPersistentData();

        String activeSoul = oldData.getString(SoulUtil.SOUL_KEY);

        if (!activeSoul.isEmpty()) {
            newData.putString(SoulUtil.SOUL_KEY, activeSoul);

            if (SoulType.ENVY.key.equals(activeSoul)) {
                newData.putBoolean(SoulUtil.ABILITY_TOGGLE, oldData.getBoolean(SoulUtil.ABILITY_TOGGLE));
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

    @SubscribeEvent
    public void onPlayerLoggedIn(net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            float currentMana = ManaUtil.getMana(serverPlayer);
            PacketHandler.sendToClient(new ManaSyncPacket(currentMana), serverPlayer);
        }
    }

    private boolean isConverted = false;

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onLivingHurt(LivingHurtEvent event) {
        if (isConverted) return;

        if (event.getSource().getEntity() instanceof Player player && !event.getSource().is(DamageTypes.MAGIC)) {
            if (event.getSource().getDirectEntity() != player) return;

            float ringConversionRate = 0f;
            float soulConversionRate = 0f;

            if (CurioHandler.isCurioEquipped(player, MAItems.EnvyRing.get())) {
                if (LuckHelper.roll(player, 0.25, 0.025)) {
                    ringConversionRate = 1.0f;
                }
            }

            if (CurioHandler.isCurioEquipped(player, MAItems.EnvySoul.get())) {
                ItemStack stack = CurioHandler.getEquippedCurio(player, MAItems.EnvySoul.get());
                int stage = SoulUtil.getSoulStage(stack);
                if (stage >= 1 && stage <= 4) {
                    soulConversionRate = stage * CommonConfig.ENVY_MAGIC_DAMAGE_CONVERSION.get().floatValue();
                }
            } else {
                String activeSoul = player.getPersistentData().getString(SoulUtil.SOUL_KEY);
                if (SoulType.ENVY.key.equals(activeSoul)) {
                    soulConversionRate = 5 * CommonConfig.ENVY_MAGIC_DAMAGE_CONVERSION.get().floatValue();
                }
            }

            float conversionRate = Math.max(ringConversionRate, soulConversionRate);

            if (conversionRate > 0f) {
                float magicDamage = event.getAmount() * conversionRate;
                float physicalDamage = event.getAmount() - magicDamage;
                event.setAmount(physicalDamage);

                var magicSource = event.getEntity().level().damageSources().indirectMagic(player, player);
                LivingHurtEvent magicEvent = new LivingHurtEvent(event.getEntity(), magicSource, magicDamage);

                isConverted = true;
                MinecraftForge.EVENT_BUS.post(magicEvent);
                isConverted = false;

                if (!magicEvent.isCanceled()) {
                    event.setAmount(physicalDamage + magicEvent.getAmount());
                }

                if (CurioHandler.isCurioEquipped(player, MAItems.EnvyRing.get())) {
                    SoulUtil.applySinful(player, 210, 2);
                }
            }
        }
    }

    private static final float REGEN_RATE = 0.05f;

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.level().isClientSide() || event.player.isDeadOrDying())
            return;

        Player player = event.player;
        if (player.isCreative() || player.isSpectator()) return;

        float currentMana = ManaUtil.getMana(player);
        float flightCost;
        if (ModLoadedUtil.isIronsSpellbooksLoaded() && CommonConfig.IRONS_SPELLBOOKS_MANA_COMPAT.get()) {
            var maxManaAttr = ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("irons_spellbooks", "max_mana"));

            float maxMana = ManaUtil.DEFAULT_MAX_MANA;
            if (maxManaAttr != null && player.getAttribute(maxManaAttr) != null) {
                maxMana = (float) player.getAttributeValue(maxManaAttr);
            }

            float flightTime = CommonConfig.ENVY_SPELLBOOKS_FLIGHT_TIME.get().floatValue(); // Seconds
            float manaCost = CommonConfig.ENVY_FLIGHT_MANA_COST.get().floatValue();
            float offset = manaCost * 0.05f;

            flightCost = (maxMana / (flightTime * 20.0f)) + offset;
        } else {
            float manaCost = CommonConfig.ENVY_FLIGHT_MANA_COST.get().floatValue();
            flightCost = manaCost * 0.05f;
        }

        String activeSoul = player.getPersistentData().getString(SoulUtil.SOUL_KEY);
        boolean canFly = SoulType.ENVY.key.equals(activeSoul);
        float startCost;
        if (!ModLoadedUtil.isIronsSpellbooksLoaded() || !CommonConfig.IRONS_SPELLBOOKS_MANA_COMPAT.get()) {
            startCost = CommonConfig.ENVY_START_COST.get().floatValue();
        } else {
            startCost = CommonConfig.ENVY_SPELLBOOKS_START_COST.get().floatValue();
        }
        float requiredMana = player.getAbilities().flying ? flightCost : startCost;

        if (canFly && currentMana >= requiredMana) {
            if (!player.getAbilities().mayfly) {
                player.getAbilities().mayfly = true;
                player.onUpdateAbilities();
            }
            if (player.getAbilities().flying) {
                ManaUtil.setMana(player, currentMana - flightCost);
            }
        } else {
            if (player.getAbilities().mayfly) {
                player.getAbilities().flying = false;
                player.getAbilities().mayfly = false;
                player.onUpdateAbilities();
            }
        }

        if (!ModLoadedUtil.isIronsSpellbooksLoaded() || !CommonConfig.IRONS_SPELLBOOKS_MANA_COMPAT.get()) {
            if (!player.getAbilities().flying && currentMana < ManaUtil.DEFAULT_MAX_MANA) {
                ManaUtil.setMana(player, ManaUtil.getMana(player) + REGEN_RATE);
            }
        }
    }

    public static void clearMaps(UUID uuid) {
        activeAbility.remove(uuid);
        darkAreaCache.remove(uuid);
    }
}