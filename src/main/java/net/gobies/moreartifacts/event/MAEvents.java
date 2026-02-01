package net.gobies.moreartifacts.event;

import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.init.MAEffects;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.item.artifacts.AnkhCharmItem;
import net.gobies.moreartifacts.item.artifacts.AnkhShieldItem;
import net.gobies.moreartifacts.item.artifacts.DragonEyeItem;
import net.gobies.moreartifacts.item.artifacts.EnderianTreadsItem;
import net.gobies.moreartifacts.util.CurioHandler;
import net.gobies.moreartifacts.util.MAUtils;
import net.gobies.moreartifacts.util.ShieldHandler;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

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
            Player player = event.player;
            if (ShieldHandler.isShieldEquipped(player, MAItems.AnkhShield.get())) {
                MAUtils.removeHarmfulEffects(player);
                AnkhShieldItem.removeAdditionalEffects(player);
            }
            if (ShieldHandler.isShieldEquipped(player, MAItems.AnkhCharm.get())) {
                MAUtils.removeHarmfulEffects(player);
                AnkhCharmItem.removeAdditionalEffects(player);
            }
        }
    }

    @SubscribeEvent
    public void onLivingAttack(LivingAttackEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (CurioHandler.isCurioEquipped(player, MAItems.IceStone.get()) || CurioHandler.isCurioEquipped(player, MAItems.IceCrystal.get())) {
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
            if (CurioHandler.isCurioEquipped(player, MAItems.FireStone.get())) {
                MAUtils.makeBurningImmune(event);
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.BlazingTreads.get())) {
                MAUtils.makeBurningImmune(event);
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.EnderianTreads.get())) {
                if (player.getHealth() - event.getAmount() <= 0) {
                    if (!player.level().isClientSide && EnderianTreadsItem.canUseAbility(player)) {
                        EnderianTreadsItem.teleportPlayer((ServerPlayer) player);
                        EnderianTreadsItem.setCooldown(player);
                        event.setCanceled(true);
                    }
                }
            }
            if (CurioHandler.isCurioEquipped(player, MAItems.MechanicalGears.get())) {
                if (player.getRandom().nextFloat() < CommonConfig.MECHANICAL_GEARS_DODGE_CHANCE.get() && event.getSource().getEntity() != null) {
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
            if (CurioHandler.isCurioEquipped(player, MAItems.TrueEnderianScarf.get())) {
                if (!player.isCreative() || !player.isSpectator()) {
                    RandomSource playerRandom = player.getRandom();
                    if (playerRandom.nextFloat() >= CommonConfig.TRUE_ENDERIAN_EVADE.get()) {
                        return;
                    }
                    Level level = player.level();
                    if (!(level instanceof ServerLevel)) {
                        return;
                    }
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

                            if (livingEntity.isPassenger()) {
                                livingEntity.stopRiding();
                            }

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
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        LivingEntity player = event.getEntity();
        if (CurioHandler.isCurioEquipped(player, MAItems.DragonEye.get())) {
            ItemStack stack = CurioHandler.getEquippedCurio(player, MAItems.DragonEye.get());
            if (stack != null && DragonEyeItem.isEnderDragonEye(stack)) {
                if (player.getRandom().nextFloat() < CommonConfig.ENDER_DRAGON_EYE_SUMMON_CHANCE.get()) {
                    Level level = player.level();
                    if (level instanceof ServerLevel serverLevel) {
                        RandomSource random = player.getRandom();
                        double offsetX = Mth.nextDouble(random, -1.0, 1.0);
                        double offsetZ = Mth.nextDouble(random, -1.0, 1.0);

                        double x = player.getX() + offsetX;
                        double z = player.getZ() + offsetZ;

                        EnderMan enderMan = new EnderMan(EntityType.ENDERMAN, serverLevel);
                        enderMan.setPos(x, player.getY(), z);
                        serverLevel.addFreshEntity(enderMan);
                        enderMan.addTag("MA_Summoned");
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onChangeTarget(LivingChangeTargetEvent event) {
        LivingEntity entity = event.getEntity();
        LivingEntity newTarget = event.getNewTarget();
        if (entity instanceof EnderMan && newTarget instanceof EnderDragon) {
            event.setCanceled(CommonConfig.ENABLE_ENDER_TWEAKS.get());
        }
    }
}