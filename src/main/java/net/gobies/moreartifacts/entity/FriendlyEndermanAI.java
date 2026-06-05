package net.gobies.moreartifacts.entity;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

public class FriendlyEndermanAI {
    private final EnderMan enderMan;
    private final Player player;
    private final PathNavigation navigation;
    private final float stopDistance = 4.0F;
    private final double speedModifier = 1.0D;

    public FriendlyEndermanAI(EnderMan enderMan, Player player) {
        this.enderMan = enderMan;
        this.player = player;
        this.navigation = enderMan.getNavigation();
        if (enderMan.getPathfindingMalus(BlockPathTypes.WATER) != 8.0F) {
            enderMan.setPathfindingMalus(BlockPathTypes.WATER, 8.0F);
        }
    }

    public void updateEndermanAI() {
        LivingEntity attacker = player.getLastHurtByMob();
        LivingEntity attackTarget = player.getLastHurtMob();
        LivingEntity enderAttacker = enderMan.getLastHurtByMob();

        // Increase ticks that endermen update to improve performance
        boolean shouldUpdatePath = (enderMan.tickCount + enderMan.getId()) % 5 == 0;

        if (attackTarget != null && !(attackTarget instanceof EnderMan)) {
            enderMan.setTarget(attackTarget);
            if (shouldUpdatePath) {
                if (navigation.isDone() || enderMan.distanceToSqr(attackTarget) > 4.0) {
                    // Move towards the attack target
                    navigation.moveTo(attackTarget.getX(), attackTarget.getY(), attackTarget.getZ(), speedModifier);
                }
            }
        } else if (attacker != null && !(attacker instanceof EnderMan) && !(attacker instanceof EnderDragon)) {
            defendPlayer(shouldUpdatePath);
        } else if (enderAttacker != null && enderAttacker != player && !(enderAttacker instanceof EnderDragon)) {
            enderMan.setTarget(enderAttacker);
        } else {
            followPlayer(shouldUpdatePath);
        }
    }

    private void defendPlayer(boolean shouldUpdatePath) {
        LivingEntity attacker = player.getLastHurtByMob();
        if (attacker == null || attacker.isDeadOrDying()) {
            enderMan.setTarget(null);
            return;
        }
        // Move towards the attacker
        enderMan.setTarget(attacker);
        if (shouldUpdatePath) {
            if (navigation.isDone() || enderMan.distanceToSqr(attacker) > 4.0) {
                navigation.moveTo(attacker.getX(), attacker.getY(), attacker.getZ(), speedModifier);
            }
        }
    }

    private void followPlayer(boolean shouldUpdatePath) {
        if (enderMan.getTarget() == player || (enderMan.getTarget() != null && enderMan.getTarget().isDeadOrDying())) {
            enderMan.setTarget(null);
        }
        double distanceToPlayer = enderMan.distanceToSqr(player);

        if (distanceToPlayer < (stopDistance * stopDistance)) {
            if (!navigation.isDone()) {
                navigation.stop();
            }
        } else if (shouldUpdatePath) {
            if (navigation.isStuck()) {
                navigation.recomputePath();
            }

            // Adjust Y positions to smoothen pathfinding
            if (navigation.isDone() || distanceToPlayer > 25.0) {
                float targetY = (float) player.getY();
                if (enderMan.getY() > player.getY()) {
                    targetY += 1.0F;
                } else if (enderMan.getY() < player.getY()) {
                    targetY -= 1.0F;
                }
                // Move to the players position
                navigation.moveTo(player.getX(), targetY, player.getZ(), speedModifier);
            }
        }
        // Make the enderman look at the player
        enderMan.getLookControl().setLookAt(player, 10.0F, (float) enderMan.getMaxHeadXRot());
    }

    public void revertEndermanAI() {
        enderMan.setTarget(null);
        enderMan.setPersistentAngerTarget(null);
        navigation.stop();
    }
}