package net.gobies.moreartifacts.entity;

import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.item.artifacts.DragonEyeItem;
import net.gobies.moreartifacts.util.CurioHandler;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

import java.util.EnumSet;

public class FriendlyEndermanGoal extends Goal {
    private final EnderMan enderMan;
    private final PathNavigation navigation;
    private final float stopDistanceSq = 16.0F;
    private int pathUpdateDelay;
    private int attackInterval;
    private Player player;

    private final TargetingConditions targetConditions = TargetingConditions.forNonCombat()
            .range(CommonConfig.ENDER_DRAGON_EYE_FOLLOW_DISTANCE.get())
            .ignoreLineOfSight()
            .ignoreInvisibilityTesting()
            .selector(entity -> entity instanceof Player p && isOwner(p));

    public FriendlyEndermanGoal(EnderMan enderMan) {
        this.enderMan = enderMan;
        this.navigation = enderMan.getNavigation();
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        if (enderMan.getPathfindingMalus(BlockPathTypes.WATER) != 8.0F) {
            enderMan.setPathfindingMalus(BlockPathTypes.WATER, 8.0F);
        }
    }

    private static boolean isOwner(Player owner) {
        ItemStack stack = CurioHandler.getEquippedCurio(owner, MAItems.DragonEye.get());
        return !stack.isEmpty() && DragonEyeItem.isEnderDragonEye(stack);
    }

    @Override
    public boolean canUse() {
        if (this.player == null || !this.player.isAlive() || !this.targetConditions.test(enderMan, this.player)) {
            this.player = enderMan.level().getNearestPlayer(this.targetConditions, enderMan);
        }
        if (this.player == null) return false;
        if (enderMan.getTarget() == this.player) {
            enderMan.setTarget(null);
            enderMan.setPersistentAngerTarget(null);
            enderMan.setRemainingPersistentAngerTime(0);
        }
        return enderMan.distanceToSqr(this.player) > stopDistanceSq || getValidTarget() != null;
    }

    @Override
    public boolean canContinueToUse() {
        return this.player != null && this.player.isAlive() && this.targetConditions.test(enderMan, this.player);
    }

    @Override
    public void start() {
        this.pathUpdateDelay = 0;
        this.attackInterval = 0;
    }

    @Override
    public void stop() {
        enderMan.setTarget(null);
        enderMan.setPersistentAngerTarget(null);
        navigation.stop();
        this.player = null;
    }

    @Override
    public void tick() {
        if (this.player == null) return;

        LivingEntity combatTarget = getValidTarget();
        boolean shouldUpdatePath = --this.pathUpdateDelay <= 0;

        if (this.attackInterval > 0) this.attackInterval--;

        double speedModifier = 1.0D;
        if (combatTarget != null && combatTarget.isAlive() && combatTarget != this.player) {
            if (enderMan.getTarget() != combatTarget) enderMan.setTarget(combatTarget);
            enderMan.getLookControl().setLookAt(combatTarget, 30.0F, (float) enderMan.getMaxHeadXRot());

            double distanceSq = enderMan.distanceToSqr(combatTarget);

            if (shouldUpdatePath) {
                this.pathUpdateDelay = distanceSq < 36.0D ? 4 + enderMan.getRandom().nextInt(4) : 12 + enderMan.getRandom().nextInt(8);
                if (navigation.isDone() || distanceSq > 4.0D) {
                    navigation.moveTo(combatTarget, speedModifier);
                }
            }

            double reach = enderMan.getBbWidth() * 2.0F * enderMan.getBbWidth() * 2.0F + combatTarget.getBbWidth();
            if (distanceSq <= reach && this.attackInterval <= 0) {
                this.attackInterval = 20;
                enderMan.swing(InteractionHand.MAIN_HAND);
                enderMan.doHurtTarget(combatTarget);
            }
        } else {
            if (enderMan.getTarget() == this.player) enderMan.setTarget(null);
            enderMan.getLookControl().setLookAt(this.player, 30.0F, (float) enderMan.getMaxHeadXRot());

            double distSq = enderMan.distanceToSqr(this.player);

            if (distSq < stopDistanceSq) {
                if (!navigation.isDone()) navigation.stop();
            } else if (shouldUpdatePath) {
                this.pathUpdateDelay = distSq < 64.0D ? 12 + enderMan.getRandom().nextInt(4) : 24 + enderMan.getRandom().nextInt(8);
                if (navigation.isStuck()) navigation.recomputePath();
                if (navigation.isDone() || distSq > 25.0D) {
                    navigation.moveTo(this.player, speedModifier);
                }
            }
        }
    }

    private LivingEntity getValidTarget() {
        if (this.player == null) return null;

        LivingEntity target = this.player.getLastHurtMob();
        if (target != null && !(target instanceof EnderMan) && target.isAlive()) return target;

        target = this.player.getLastHurtByMob();
        if (target != null && !(target instanceof EnderMan) && !(target instanceof EnderDragon) && target.isAlive()) return target;

        target = enderMan.getLastHurtByMob();
        if (target != null && target != this.player && !(target instanceof EnderDragon) && target.isAlive()) return target;

        return null;
    }
}