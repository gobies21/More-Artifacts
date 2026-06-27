package net.gobies.moreartifacts.event;

import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.init.MAEffects;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.init.MASounds;
import net.gobies.moreartifacts.item.artifacts.*;
import net.gobies.moreartifacts.network.CooldownSyncPacket;
import net.gobies.moreartifacts.network.PacketHandler;
import net.gobies.moreartifacts.util.*;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.*;

public class MAEvents {

    public static void register() {
        MinecraftForge.EVENT_BUS.register(new MAEvents());
    }

    @SubscribeEvent
    public void onMobEffectApplicable(MobEffectEvent.Applicable event) {
        LivingEntity livingEntity = event.getEntity();
        MobEffect effect = event.getEffectInstance().getEffect();
        if (event.getEntity() instanceof Player player) {
            if (CurioHandler.isCurioEquipped(player, MAItems.Bezoar.get())) {
                MAUtils.harmfulSpecificEffectImmune(event, MobEffects.POISON);
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.Vitamins.get())) {
                MAUtils.harmfulSpecificEffectImmune(event, MobEffects.WEAKNESS, MobEffects.DIG_SLOWDOWN);
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.Sunglasses.get())) {
                MAUtils.harmfulSpecificEffectImmune(event, MobEffects.BLINDNESS);
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.FastClock.get())) {
                MAUtils.harmfulSpecificEffectImmune(event, MobEffects.MOVEMENT_SLOWDOWN);
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.Nectar.get())) {
                MAUtils.harmfulSpecificEffectImmune(event, MobEffects.CONFUSION);
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.DesertCharm.get())) {
                MAUtils.harmfulSpecificEffectImmune(event, MobEffects.HUNGER);
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.SculkLens.get())) {
                MAUtils.harmfulSpecificEffectImmune(event, MobEffects.DARKNESS);
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.WitherShard.get())) {
                MAUtils.harmfulSpecificEffectImmune(event, MobEffects.WITHER);
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.ShulkerHeart.get())) {
                MAUtils.harmfulSpecificEffectImmune(event, MobEffects.LEVITATION);
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.PurificationCharm.get())) {
                MAUtils.harmfulSpecificEffectImmune(event, MobEffects.HUNGER, MobEffects.CONFUSION);
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.SculkShades.get())) {
                MAUtils.harmfulSpecificEffectImmune(event, MobEffects.BLINDNESS, MobEffects.DARKNESS);
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.WitheredBezoar.get())) {
                MAUtils.harmfulSpecificEffectImmune(event, MobEffects.POISON, MobEffects.WITHER);
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.ShulkedClock.get())) {
                MAUtils.harmfulSpecificEffectImmune(event, MobEffects.MOVEMENT_SLOWDOWN, MobEffects.LEVITATION);
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.AnkhCharm.get()) || ShieldHandler.isShieldEquipped(player, MAItems.AnkhCharm.get())) {
                MAUtils.harmfulEffectImmunity(event);
                AnkhCharmItem.additionalEffectImmunity(event);
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.AnkhShield.get()) || ShieldHandler.isShieldEquipped(player, MAItems.AnkhShield.get())) {
                MAUtils.harmfulEffectImmunity(event);
                AnkhShieldItem.additionalEffectImmunity(event);
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.VenomStone.get())) {
                MAUtils.harmfulSpecificEffectImmune(event, MobEffects.POISON);
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.DecayStone.get())) {
                MAUtils.harmfulSpecificEffectImmune(event, MobEffects.WITHER);
            }
        }
        if (livingEntity.hasEffect(MAEffects.Virulent.get())) {
            if (effect == MobEffects.POISON) {
                event.setResult(MobEffectEvent.Result.DENY);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (event.player.level().isClientSide()) return;
            Player player = event.player;
            if (player.tickCount % 5 != 0) return;

            boolean hasAnkhShield = ShieldHandler.isShieldEquipped(player, MAItems.AnkhShield.get());
            boolean hasAnkhCharm = ShieldHandler.isShieldEquipped(player, MAItems.AnkhCharm.get());

            if (hasAnkhShield) {
                MAUtils.removeHarmfulEffects(player);
                AnkhShieldItem.removeAdditionalEffects(player);
            }
            if (hasAnkhCharm) {
                MAUtils.removeHarmfulEffects(player);
                AnkhCharmItem.removeAdditionalEffects(player);
            }
            int fireTicks = player.getRemainingFireTicks();
            if (player.isOnFire() && fireTicks > 100) {
                if (CurioHandler.isCurioEquipped(player, MAItems.ObsidianSkull.get()) || CurioHandler.isCurioEquipped(player, MAItems.ObsidianShield.get()) || hasAnkhShield) {
                    player.setRemainingFireTicks(fireTicks / 2);
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onLivingAttack(LivingAttackEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (CurioHandler.isCurioEquipped(player, MAItems.IceStone.get()) || CurioHandler.isCurioEquipped(player, MAItems.IceCrystal.get()) || CurioHandler.isCurioEquipped(player, MAItems.FrostedShield.get()) || ShieldHandler.isShieldEquipped(player, MAItems.FrostedShield.get())) {
                MAUtils.makeFreezingImmune(event);
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.ObsidianSkull.get())) {
                MAUtils.makeBurningImmune(event);
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.ObsidianShield.get()) || ShieldHandler.isShieldEquipped(player, MAItems.ObsidianShield.get())) {
                MAUtils.makeBurningImmune(event);
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.AnkhShield.get()) || ShieldHandler.isShieldEquipped(player, MAItems.AnkhShield.get())) {
                MAUtils.makeBurningImmune(event);
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.FrostedShield.get()) || ShieldHandler.isShieldEquipped(player, MAItems.FrostedShield.get())) {
                MAUtils.makeFreezingImmune(event);
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.FireStone.get())) {
                MAUtils.makeBurningImmune(event);
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.BlazingTreads.get())) {
                MAUtils.makeBurningImmune(event);
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.MechanicalGears.get())) {
                if (LuckHelper.roll(player, CommonConfig.MECHANICAL_GEARS_DODGE_CHANCE.get(), CommonConfig.MECHANICAL_GEARS_LUCK_FACTOR.get()) && event.getSource().getEntity() != null) {
                    double x = player.getX();
                    double z = player.getZ();
                    double attackerX = event.getSource().getEntity().getX();
                    double attackerZ = event.getSource().getEntity().getZ();

                    double angle = Math.atan2(attackerZ - z, attackerX - x);
                    boolean side = player.getRandom().nextBoolean();
                    double deltaXZ = Math.cos(angle + (side ? (Math.PI / 2D) : (-Math.PI / 2D)));
                    Vec3 knockbackVector = new Vec3(deltaXZ, 0.3D, deltaXZ).normalize().scale(1.0D);

                    player.setDeltaMovement(knockbackVector);
                    player.hurtMarked = true;
                    event.setCanceled(true);
                }
            }
        }
        if (event.getEntity() instanceof Player player && event.getSource().getEntity() != null && !event.isCanceled()) {
            double evadeChance = CommonConfig.TRUE_ENDERIAN_EVADE.get();
            if (CurioHandler.isCurioEquipped(player, MAItems.TrueEnderianScarf.get())) {
                if (!player.isCreative() || !player.isSpectator()) {
                    if (LuckHelper.roll(player, evadeChance, CommonConfig.TRUE_ENDERIAN_LUCK_FACTOR.get())) {
                        Level level = player.level();
                        if (!(level instanceof ServerLevel)) return;
                        event.setCanceled(true);
                        LivingEntity livingEntity = event.getEntity();
                        double d0 = livingEntity.getX();
                        double d2 = livingEntity.getZ();
                        double maxRadius = 4d;
                        var entityRandom = livingEntity.getRandom();

                        for (int i = 0; i < 12; ++i) {
                            var minRadius = maxRadius / 2;
                            Vec3 vec = new Vec3((entityRandom.nextInt((int) minRadius, (int) maxRadius)), 0, 0);
                            int degrees = entityRandom.nextInt(360);
                            vec = vec.yRot(degrees * Mth.DEG_TO_RAD);

                            double x = d0 + vec.x;
                            if (level instanceof ServerLevel serverLevel) {
                                double y = Mth.clamp(livingEntity.getY() + (livingEntity.getRandom().nextInt((int) maxRadius) - maxRadius / 2), level.getMinBuildHeight(), (level.getMinBuildHeight() + serverLevel.getLogicalHeight() - 1));
                                double z = d2 + vec.z;

                                if (livingEntity.isPassenger()) livingEntity.stopRiding();

                                if (livingEntity.randomTeleport(x, y, z, true)) {
                                    if (event.getSource().getEntity() != null) {
                                        livingEntity.lookAt(EntityAnchorArgument.Anchor.EYES, event.getSource().getEntity().getEyePosition());
                                    }
                                    player.level().playSound(null, player.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, player.getSoundSource(), 1.0F, 1.0F);
                                    livingEntity.playSound(SoundEvents.ENDERMAN_TELEPORT, 2.0F, 1.0F);
                                    break;
                                }

                                if (maxRadius > 2) {
                                    maxRadius--;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onLivingKnockBack(LivingKnockBackEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof Player player) {
            if (ShieldHandler.isShieldEquipped(player, MAItems.CobaltShield.get())) {
                event.setCanceled(true);
            }
            if (ShieldHandler.isShieldEquipped(player, MAItems.ObsidianShield.get())) {
                event.setCanceled(true);
            }
            if (ShieldHandler.isShieldEquipped(player, MAItems.AnkhShield.get())) {
                event.setCanceled(true);
            }
            if (ShieldHandler.isShieldEquipped(player, MAItems.FrostedShield.get())) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerJump(LivingEvent.LivingJumpEvent event) {
        if (event.getEntity() instanceof Player player) {
            int ballonCount = CurioHandler.getCurioCount(player, MAItems.Balloon.get());
            for (int i = 0; i < ballonCount; i++) {
                player.setDeltaMovement(player.getDeltaMovement().add(0, 0.15, 0));
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.HighJumpers.get())) {
                player.setDeltaMovement(player.getDeltaMovement().add(0, 0.15, 0));
            }
        }
    }

    @SubscribeEvent
    public void onFallDamageReduction(LivingFallEvent event) {
        if (event.getEntity() instanceof Player player) {
            int balloonCount = CurioHandler.getCurioCount(player, MAItems.Balloon.get());
            for (int i = 0; i < balloonCount; i++) {
                event.setDistance(event.getDistance() * 0.8f);
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.HighJumpers.get())) {
                event.setDistance(event.getDistance() * 0.8f);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerWakeUp(PlayerWakeUpEvent event) {
        Player player = event.getEntity();
        if (!player.level().isClientSide && player.isSleeping()) {
            if (CurioHandler.isCurioEquipped(player, MAItems.MelodyPlushie.get())) {
                player.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 20 * CommonConfig.PLUSHIE_DURATION.get(), CommonConfig.PLUSHIE_HEALTH_BOOST_LEVEL.get() - 1, true, true));
            }
        }
    }

    @SubscribeEvent
    public void onAttack(LivingAttackEvent event) {
        LivingEntity target = event.getEntity();
        Entity attacker = event.getSource().getEntity();
        DamageSource source = event.getSource();
        if (target != null && attacker != null) {
            if (attacker instanceof LivingEntity) {
                if (CurioHandler.isCurioEquipped((LivingEntity) attacker, MAItems.DragonEye.get())) {
                    ItemStack stack = CurioHandler.getEquippedCurio((LivingEntity) attacker, MAItems.DragonEye.get());
                    if (stack != null && DragonEyeItem.isEnderDragonEye(stack)) {
                        if (target instanceof EnderMan) {
                            event.setCanceled(true);
                        }
                    }
                }
            }
        }
        if (target instanceof EnderMan) {
            if (attacker instanceof EnderDragon || source.is(DamageTypes.DRAGON_BREATH)) {
                event.setCanceled(CommonConfig.ENABLE_ENDER_TWEAKS.get());
            }
        }
        if (!event.isCanceled() && event.getSource().getEntity() instanceof LivingEntity player) {
            LivingEntity attackedEntity = event.getEntity();
            int gloveCount = CurioHandler.getCurioCount(player, MAItems.EchoGlove.get());
            for (int i = 0; i < gloveCount; i++) {
                double ignoreChance = CommonConfig.ECHO_GLOVE_IGNORE_CHANCE.get() * gloveCount;
                if (LuckHelper.roll(player, ignoreChance, CommonConfig.ECHO_GLOVE_LUCK_FACTOR.get())) {
                    // reduce the invincibility time by a fixed number of ticks
                    attackedEntity.invulnerableTime = Math.max(0, attackedEntity.invulnerableTime - 5);
                }
            }
        }
    }

    private static final String SUMMONED = "MA_Summoned";
    private static final Map<UUID, Long> cooldownMap = new HashMap<>();

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        LivingEntity livingEntity = event.getEntity();
        Entity sourceEntity = event.getSource().getEntity();
        DamageSource damageSource = event.getSource();
        if (event.getEntity() instanceof Player player && event.getSource().getEntity() != null) {
            CuriosApi.getCuriosInventory(player).ifPresent(handler -> handler.findFirstCurio(stack -> stack.getItem() instanceof HeroShieldItem).ifPresent(slotResult -> {
                ItemStack stack = slotResult.stack();
                if (stack.getItem() instanceof HeroShieldItem) {
                    ItemStack pStack = slotResult.stack();
                    CompoundTag tag = pStack.getOrCreateTag();
                    int hitCount = tag.getInt("HitCount");
                    Random random = new Random();
                    hitCount++;
                    tag.putInt("HitCount", hitCount);
                    if (hitCount % CommonConfig.IGNORE_DAMAGE_CHANCE.get() == 0 && !event.isCanceled()) {
                        event.setCanceled(true);
                        player.displayClientMessage(Component.translatable("message.moreartifacts.hero_shield.damage_text").withStyle(ChatFormatting.GOLD), true);
                        float randomPitch = 1.0f + random.nextFloat() * 0.2f;
                        player.level().playSound(null, player.blockPosition(), SoundEvents.ANVIL_LAND, SoundSource.PLAYERS, 0.3f, randomPitch);
                        tag.putInt("HitCount", 0);
                    }
                    if (event.getSource().is(DamageTypes.EXPLOSION) || event.getSource().is(DamageTypes.PLAYER_EXPLOSION)) {
                        event.setAmount((float) (event.getAmount() * (1 - CommonConfig.EXPLOSION_DAMAGE_REDUCTION.get())));
                    }
                }
            }));
        }
        if (CurioHandler.isCurioEquipped(livingEntity, MAItems.VenomAmulet.get())) {
            if (event.getSource().getEntity() instanceof LivingEntity attacker) {
                double venomChance = CommonConfig.VENOM_AMULET_POISON_CHANCE.get();
                if (livingEntity instanceof Player player && LuckHelper.roll(player, venomChance, CommonConfig.VENOM_AMULET_LUCK_FACTOR.get())) {
                    attacker.addEffect(new MobEffectInstance(MobEffects.POISON, 20 * CommonConfig.VENOM_AMULET_POISON_DURATION.get(), CommonConfig.VENOM_AMULET_POISON_LEVEL.get() - 1));
                }
            }
        }
        if (CurioHandler.isCurioEquipped(livingEntity, MAItems.DecayAmulet.get())) {
            if (event.getSource().getEntity() instanceof LivingEntity attacker) {
                double decayChance = CommonConfig.DECAY_AMULET_WITHER_CHANCE.get();
                if (livingEntity instanceof Player player && LuckHelper.roll(player, decayChance, 0.05F)) {
                    attacker.addEffect(new MobEffectInstance(MobEffects.WITHER, 20 * CommonConfig.DECAY_AMULET_WITHER_DURATION.get(), CommonConfig.DECAY_AMULET_WITHER_LEVEL.get() - 1));
                }
            }
        }
        if (sourceEntity instanceof Player attacker) {
            if (CurioHandler.isCurioEquipped(attacker, MAItems.SpectreAmulet.get())) {
                if (!livingEntity.isDeadOrDying()) {
                    double healChance = CommonConfig.SPECTRE_AMULET_HEAL_CHANCE.get();
                    if (LuckHelper.roll(attacker, healChance, CommonConfig.SPECTRE_AMULET_LUCK_FACTOR.get())) {
                        long currentTime = System.currentTimeMillis();
                        long lastHealTime = cooldownMap.getOrDefault(attacker.getUUID(), 0L);

                        if (currentTime - lastHealTime >= 1000) {
                            attacker.heal(CommonConfig.SPECTRE_AMULET_HEAL_AMOUNT.get().floatValue());
                            cooldownMap.put(attacker.getUUID(), currentTime);
                        }
                    }
                }
            }
        }
        if (sourceEntity instanceof Player attacker) {
            if (CurioHandler.isCurioEquipped(attacker, MAItems.NecroplasmAmulet.get())) {
                if (event.getEntity() instanceof LivingEntity && !event.getEntity().isDeadOrDying()) {
                    double healChance = CommonConfig.NECROPLASM_AMULET_HEAL_CHANCE.get();
                    if (LuckHelper.roll(attacker, healChance, CommonConfig.NECROPLASM_AMULET_LUCK_FACTOR.get())) {
                        long currentTime = System.currentTimeMillis();
                        long lastHealTime = cooldownMap.getOrDefault(attacker.getUUID(), 0L);
                        if (currentTime - lastHealTime >= 1000) {
                            attacker.heal(CommonConfig.NECROPLASM_AMULET_HEAL_AMOUNT.get().floatValue());
                            cooldownMap.put(attacker.getUUID(), currentTime);
                        }
                    }
                }
            }
        }
        if (CurioHandler.isCurioEquipped(livingEntity, MAItems.NecroplasmAmulet.get())) {
            if (event.getSource().getEntity() instanceof LivingEntity attacker) {
                double poisonChance = CommonConfig.NECROPLASM_AMULET_POISON_CHANCE.get();
                if (livingEntity instanceof Player player && LuckHelper.roll(player, poisonChance, 0.05F)) {
                    attacker.addEffect(new MobEffectInstance(MobEffects.POISON, 20 * CommonConfig.NECROPLASM_AMULET_POISON_DURATION.get(), CommonConfig.NECROPLASM_AMULET_POISON_LEVEL.get() - 1));
                }
                double witherChance = CommonConfig.NECROPLASM_AMULET_WITHER_CHANCE.get();
                if (livingEntity instanceof Player player && LuckHelper.roll(player, witherChance, 0.05F)) {
                    attacker.addEffect(new MobEffectInstance(MobEffects.WITHER, 20 * CommonConfig.NECROPLASM_AMULET_WITHER_DURATION.get(), CommonConfig.NECROPLASM_AMULET_WITHER_LEVEL.get() - 1));
                }
            }
        }
        if (sourceEntity instanceof Player player && event.getEntity() != null) {
            LivingEntity target = event.getEntity();
            if (CurioHandler.isCurioEquipped(player, MAItems.LuckyEmeraldRing.get())) {
                if (target instanceof LivingEntity) {
                    double emeraldChance = CommonConfig.EMERALD_RING_EMERALDS.get();
                    if (LuckHelper.roll(player, emeraldChance, CommonConfig.EMERALD_RING_LUCK_FACTOR.get())) {
                        target.spawnAtLocation(Items.EMERALD, 1);
                        player.level().playSound(null, player.blockPosition(), SoundEvents.AMETHYST_BLOCK_BREAK, SoundSource.PLAYERS, 1.0f, 1.8f);
                    }
                }
            }
        }
        if (CurioHandler.isCurioEquipped(livingEntity, MAItems.DragonEye.get())) {
            ItemStack stack = CurioHandler.getEquippedCurio(livingEntity, MAItems.DragonEye.get());
            if (stack != null && DragonEyeItem.isEnderDragonEye(stack)) {
                double summonChance = CommonConfig.ENDER_DRAGON_EYE_SUMMON_CHANCE.get();
                if (livingEntity instanceof Player player && LuckHelper.roll(player, summonChance, CommonConfig.ENDER_DRAGON_CLAW_LUCK_FACTOR.get())) {
                    Level level = livingEntity.level();
                    if (level instanceof ServerLevel serverLevel) {
                        RandomSource random = livingEntity.getRandom();
                        double offsetX = Mth.nextDouble(random, -1.0, 1.0);
                        double offsetZ = Mth.nextDouble(random, -1.0, 1.0);

                        double x = livingEntity.getX() + offsetX;
                        double z = livingEntity.getZ() + offsetZ;

                        EnderMan enderMan = new EnderMan(EntityType.ENDERMAN, serverLevel);
                        enderMan.addTag(SUMMONED);
                        float newMaxHealth = (float) (enderMan.getMaxHealth() * CommonConfig.ENDERMAN_HEALTH_MULTIPLIER.get());
                        float newDamage = (float) (enderMan.getAttributeBaseValue(Attributes.ATTACK_DAMAGE) * CommonConfig.ENDERMAN_DAMAGE_MULTIPLIER.get());
                        Objects.requireNonNull(enderMan.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(newMaxHealth);
                        Objects.requireNonNull(enderMan.getAttribute(Attributes.ATTACK_DAMAGE)).setBaseValue(newDamage);
                        enderMan.setHealth(newMaxHealth);
                        enderMan.setPos(x, livingEntity.getY(), z);
                        serverLevel.addFreshEntity(enderMan);
                    }
                }
            }
        }
        if (CurioHandler.isCurioEquipped(livingEntity, MAItems.GuardianThornNecklace.get()) && !event.isCanceled()) {
            if (sourceEntity instanceof LivingEntity attacker && damageSource.getEntity() != null) {
                DamageSource thornsDamage = damageSource.getEntity().damageSources().thorns(livingEntity);
                int damageReflect = CommonConfig.GUARDIAN_THORN_NECKLACE_THORNS.get();
                attacker.hurt(thornsDamage, damageReflect);
            }
        }
        if (!(sourceEntity instanceof LivingEntity attacker)) return;
        int cursedDollCount = CurioHandler.getCurioCount(attacker, MAItems.CursedDoll.get());
        SoundEvent cursed = ForgeRegistries.SOUND_EVENTS.getValue(MASounds.CURSED.get().getLocation());
        for (int i = 0; i < cursedDollCount; i++) {
            double curseChance = CommonConfig.CURSED_DOLL_CHANCE.get() * cursedDollCount;
            if (attacker instanceof Player player && LuckHelper.roll(player, curseChance, CommonConfig.CURSED_DOLL_LUCK_FACTOR.get())) {
                Level level = livingEntity.level();
                Collection<MobEffectInstance> activeEffects = attacker.getActiveEffects();
                List<MobEffectInstance> harmfulEffects = activeEffects.stream()
                        .filter(mobEffectInstance -> mobEffectInstance.getEffect().getCategory() == MobEffectCategory.HARMFUL)
                        .filter(effect -> !isBlacklisted(effect))
                        .toList();
                if (!harmfulEffects.isEmpty()) {
                    MobEffectInstance randomEffect = harmfulEffects.get(attacker.level().random.nextInt(harmfulEffects.size()));
                    attacker.removeEffect(randomEffect.getEffect());
                    int maxDuration = Math.min(randomEffect.getDuration(), 20 * CommonConfig.CURSED_DOLL_MAX_DURATION.get());
                    int maxAmplifier = Math.min(randomEffect.getAmplifier(), CommonConfig.CURSED_DOLL_MAX_AMPLIFIER.get() - 1);
                    MobEffectInstance copiedEffect = new MobEffectInstance(randomEffect.getEffect(), maxDuration, maxAmplifier, randomEffect.isAmbient(), randomEffect.isVisible(), randomEffect.showIcon());
                    level.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), Objects.requireNonNull(cursed), SoundSource.PLAYERS, 1.2F, 1.0F);
                    livingEntity.addEffect(copiedEffect);
                }

                int fireTicks = attacker.getRemainingFireTicks();
                if (fireTicks > 0) {
                    livingEntity.setRemainingFireTicks(fireTicks);
                    attacker.setRemainingFireTicks(0);
                    level.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), Objects.requireNonNull(cursed), SoundSource.PLAYERS, 1.2F, 1.0F);
                }

                int freezeTicks = attacker.getTicksFrozen();
                if (freezeTicks > 0) {
                    livingEntity.setTicksFrozen(freezeTicks);
                    attacker.setTicksFrozen(0);
                    level.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), Objects.requireNonNull(cursed), SoundSource.PLAYERS, 1.2F, 1.0F);
                }
            }
        }
        if (livingEntity instanceof Player player && CurioHandler.isCurioEquipped(player, MAItems.HolyMantle.get())) {
            Level level = livingEntity.level();
            double cooldown = CommonConfig.HOLY_MANTLE_COOLDOWN.get();
            String artifactId = "holy_mantle";
            if (CooldownHandler.isReady(player, artifactId, cooldown)) {
                event.setCanceled(true);
                CooldownHandler.updateCooldown(player, artifactId);
                if (player instanceof ServerPlayer serverPlayer && !Objects.requireNonNull(serverPlayer.getServer()).isSingleplayer()) {
                    PacketHandler.sendToClient(new CooldownSyncPacket(artifactId), serverPlayer);
                }
                level.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 1.2F, 1.0F);
                if (LuckHelper.roll(player, CommonConfig.HOLY_MANTLE_ABSORPTION_CHANCE.get(), CommonConfig.HOLY_MANTLE_LUCK_FACTOR.get())) {
                    float maxAbsorption = 2 * CommonConfig.HOLY_MANTLE_MAX_HEARTS.get();
                    float currentAbsorption = player.getAbsorptionAmount();
                    if (currentAbsorption < maxAbsorption) {
                        float newAbsorption = Math.min(currentAbsorption + 2, maxAbsorption);
                        player.setAbsorptionAmount(newAbsorption);
                    }
                }
            }
        }
    }

    private boolean isBlacklisted(MobEffectInstance instance) {
        ResourceLocation entityName = ForgeRegistries.MOB_EFFECTS.getKey(instance.getEffect());
        return entityName != null && CommonConfig.CURSED_DOLL_BLACKLIST_EFFECTS.get().contains(entityName.toString());
    }

    @SubscribeEvent
    public void onChangeTarget(LivingChangeTargetEvent event) {
        LivingEntity entity = event.getEntity();
        LivingEntity newTarget = event.getNewTarget();
        if (entity instanceof EnderMan && newTarget instanceof EnderDragon) {
            event.setCanceled(CommonConfig.ENABLE_ENDER_TWEAKS.get());
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onLivingDamage(LivingDamageEvent event) {
        LivingEntity entity = event.getEntity();
        if (!(entity instanceof ServerPlayer player)) return;
        if (event.isCanceled()) return;

        if (CurioHandler.isCurioEquipped(player, MAItems.EnderianTreads.get())) {
            if (player.getHealth() - event.getAmount() <= 0) {
                if (EnderianTreadsItem.canUseAbility(player)) {
                    EnderianTreadsItem.teleportPlayer(player);
                    EnderianTreadsItem.setCooldown(player);
                    event.setCanceled(true);
                }
            }
        }

        if (CurioHandler.isCurioEquipped(player, MAItems.BrokenHeart.get())) {
            if (ModLoadedUtil.isFirstAidLoaded()) {
                return;
            }

            // Totem check, will try to make it more global in the future
            if (player.getMainHandItem().is(Items.TOTEM_OF_UNDYING) || player.getOffhandItem().is(Items.TOTEM_OF_UNDYING)) return;
            DamageSource source = event.getSource();
            if (source.is(DamageTypes.FELL_OUT_OF_WORLD)) {
                return;
            }

            float amount = event.getAmount();
            float currentHealth = player.getHealth();
            float remainingHealth = currentHealth - amount;

            if (remainingHealth > 0) {
                return;
            }
            AttributeInstance attribute = player.getAttribute(Attributes.MAX_HEALTH);
            if (attribute == null) {
                return;
            }
            AttributeModifier modifier = attribute.getModifier(BrokenHeartSystem.BROKEN_HEART_HEALTH_UUID);

            double currentModifierValue = modifier != null ? Math.abs(modifier.getAmount()) : 0.0;
            float naturalMaxHealth = player.getMaxHealth() + (float) currentModifierValue;
            int maxHeartContainers = Mth.ceil(naturalMaxHealth / 2.0F) - 1;
            int currentBrokenHearts = BrokenHeartSystem.getBrokenHearts(player);

            if (currentBrokenHearts >= maxHeartContainers) {
                return;
            }
            // Prevent fatal damage
            event.setCanceled(true);

            // To prevent death from things like explosions
            player.getCombatTracker().recheckStatus();

            float totalDepletedHealth = currentHealth + Math.abs(remainingHealth);
            int heartsToBreak = Math.max(1, (int) Math.ceil(totalDepletedHealth / 2.0F));
            int availableContainersLeft = maxHeartContainers - currentBrokenHearts;

            if (heartsToBreak >= availableContainersLeft) {
                BrokenHeartSystem.addBrokenHearts(player, availableContainersLeft);
                player.setHealth(0.0F);
            } else {
                BrokenHeartSystem.addBrokenHearts(player, heartsToBreak);
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.IRON_GOLEM_DAMAGE, SoundSource.PLAYERS, 1.0F, 0.8F);
                player.setHealth(1.0F);
                player.invulnerableTime = 20;
                player.hurtDuration = 10;
                player.hurtTime = 10;
            }
        }
    }

    @SubscribeEvent
    public void onMobKill(LivingDeathEvent event) {
        if (!(event.getSource().getEntity() instanceof Player player)) return;
        ItemStack soul = SoulUtil.isSoulEquipped(player);
        if (soul.isEmpty()) return;

        if (player.getCommandSenderWorld().isClientSide()) return;
        if (event.getEntity() instanceof Enemy) {

            LivingEntity victim = event.getEntity();
            Level level = victim.getCommandSenderWorld();
            BlockPos pos = victim.blockPosition();
            int lightLevel = level.getMaxLocalRawBrightness(pos);

            // Evolve Soul of Shadows (stage 1-2-3)
            if (soul.is(MAItems.ShadowSoul.get())) {
                if (SoulUtil.getSoulStage(soul) == 1) {
                    if (lightLevel <= 7) {
                        CompoundTag nbt = soul.getOrCreateTag();
                        int currentKills = nbt.getInt(SoulUtil.KILLS) + 1;

                        if (currentKills >= 100) { // 100 kills 1-2
                            SoulUtil.evolveSoul(soul, player);
                            nbt.remove(SoulUtil.KILLS);
                            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.WARDEN_HEARTBEAT, SoundSource.PLAYERS, 1.0F, 0.8F);
                        } else {
                            nbt.putInt(SoulUtil.KILLS, currentKills);
                        }
                    }
                } else if (SoulUtil.getSoulStage(soul) == 2) {
                    if (lightLevel <= 7) {
                        CompoundTag nbt = soul.getOrCreateTag();
                        int currentKills = nbt.getInt(SoulUtil.KILLS) + 1;

                        if (currentKills >= 300) { // 300 kills 2-3
                            SoulUtil.evolveSoul(soul, player);
                            nbt.remove(SoulUtil.KILLS);
                            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.WARDEN_HEARTBEAT, SoundSource.PLAYERS, 1.0F, 0.8F);
                        } else {
                            nbt.putInt(SoulUtil.KILLS, currentKills);
                        }
                    }
                } else if (SoulUtil.getSoulStage(soul) == 3) {
                    CompoundTag nbt = soul.getOrCreateTag();
                    int currentKills = nbt.getInt(SoulUtil.KILLS) + 1;
                    ResourceLocation entityName = ForgeRegistries.ENTITY_TYPES.getKey(event.getEntity().getType());
                    if (entityName != null && "minecraft:wither".equals(entityName.toString())) {
                        if (currentKills >= 3) { // 3 wither kills
                            SoulUtil.evolveSoul(soul, player);
                            nbt.remove(SoulUtil.KILLS);
                            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.WARDEN_HEARTBEAT, SoundSource.PLAYERS, 1.0F, 0.8F);
                        } else {
                            nbt.putInt(SoulUtil.KILLS, currentKills);
                        }
                    }
                } else if (SoulUtil.getSoulStage(soul) == 4) {
                    CompoundTag nbt = soul.getOrCreateTag();
                    int currentKills = nbt.getInt(SoulUtil.KILLS) + 1;
                    ResourceLocation entityName = ForgeRegistries.ENTITY_TYPES.getKey(event.getEntity().getType());
                    if (entityName != null && "minecraft:ender_dragon".equals(entityName.toString())) {
                        if (currentKills >= 3) { // 3 dragon kills
                            SoulUtil.evolveSoul(soul, player);
                            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.WARDEN_HEARTBEAT, SoundSource.PLAYERS, 1.0F, 0.8F);
                        } else {
                            nbt.putInt(SoulUtil.KILLS, currentKills);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onEntityKill(LivingHurtEvent event) {
        if (!(event.getSource().getEntity() instanceof Player player)) return;
        ItemStack soul = SoulUtil.isSoulEquipped(player);
        if (soul.isEmpty()) return;

        if (player.getCommandSenderWorld().isClientSide()) return;
        if (event.getEntity() instanceof Enemy) {

            LivingEntity victim = event.getEntity();
            if (soul.is(MAItems.BloodSoul.get())) {
                if (SoulUtil.getSoulStage(soul) == 1) {
                    if (victim.getMaxHealth() <= event.getAmount()) {
                        CompoundTag nbt = soul.getOrCreateTag();
                        int currentKills = nbt.getInt(SoulUtil.KILLS) + 1;
                        if (currentKills >= 100) { // 100 kills 1-2
                            SoulUtil.evolveSoul(soul, player);
                            nbt.remove(SoulUtil.KILLS);
                            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.WARDEN_HEARTBEAT, SoundSource.PLAYERS, 1.0F, 0.8F);
                        } else {
                            nbt.putInt(SoulUtil.KILLS, currentKills);
                        }
                    }
                } else if (SoulUtil.getSoulStage(soul) == 2) {
                    if (victim.getMaxHealth() <= event.getAmount()) {
                        CompoundTag nbt = soul.getOrCreateTag();
                        int currentKills = nbt.getInt(SoulUtil.KILLS) + 1;
                        if (currentKills >= 300) { // 300 kills 2-3
                            SoulUtil.evolveSoul(soul, player);
                            nbt.remove(SoulUtil.KILLS);
                            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.WARDEN_HEARTBEAT, SoundSource.PLAYERS, 1.0F, 0.8F);
                        } else {
                            nbt.putInt(SoulUtil.KILLS, currentKills);
                        }
                    }
                } else if (SoulUtil.getSoulStage(soul) == 3) {
                    if (victim.getMaxHealth() <= event.getAmount()) {
                        CompoundTag nbt = soul.getOrCreateTag();
                        int currentKills = nbt.getInt(SoulUtil.KILLS) + 1;
                        if (currentKills >= 500) { // 500 kills 3-4
                            SoulUtil.evolveSoul(soul, player);
                            nbt.remove(SoulUtil.KILLS);
                            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.WARDEN_HEARTBEAT, SoundSource.PLAYERS, 1.0F, 0.8F);
                        } else {
                            nbt.putInt(SoulUtil.KILLS, currentKills);
                        }
                    }
                } else if (SoulUtil.getSoulStage(soul) == 4) {
                    if (victim.getMaxHealth() <= event.getAmount()) {
                        CompoundTag nbt = soul.getOrCreateTag();
                        int currentKills = nbt.getInt(SoulUtil.KILLS) + 1;
                        if (currentKills >= 1000) { // 1000 kills 4-5
                            SoulUtil.evolveSoul(soul, player);
                            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.WARDEN_HEARTBEAT, SoundSource.PLAYERS, 1.0F, 0.8F);
                            player.setHealth(player.getMaxHealth() * 0.75F);
                        } else {
                            nbt.putInt(SoulUtil.KILLS, currentKills);
                        }
                    }
                }
            }
        }
    }
    public static void clearMaps(UUID uuid) {
        cooldownMap.remove(uuid);
    }
}