package net.gobies.moreartifacts.util;

import net.gobies.moreartifacts.Config;
import net.gobies.moreartifacts.event.ClientEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Map;

public class Teleport {
    private static final Map<Entity, Long> cooldownMap = new HashMap<>();

    // NEEDS REFINING BADLY
    public static Vec3 solveTeleportDestination(Level level, LivingEntity entity, BlockPos blockPos, Vec3 vec3) {
        // define the start and end points of the raycast based on the entity's direction
        Vec3 start = entity.getEyePosition(1f);
        Vec3 end = start.add(entity.getViewVector(1f).scale(Config.ENDERIAN_EYE_RADIUS.get()));

        // perform the first raycast to find a potential ledge within 3 blocks above the entity's position
        HitResult hitResult = level.clip(new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity));
        Vec3 hitVec = hitResult.getLocation();
        double hitY = hitVec.y;

        // check if the block above the hit position is air
        BlockPos targetPos = new BlockPos((int) hitVec.x, (int) hitY, (int) hitVec.z);
        boolean isAir = level.getBlockState(targetPos.above()).isAir();

        // check if there is a line of sight to the hit position
        boolean los = level.clip(new ClipContext(start, hitVec, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity)).getType() == HitResult.Type.BLOCK;

        // if the block above the hit position is air and there is a line of sight, teleport the entity
        if (isAir && !los && Math.abs(hitY - entity.getY()) <= 3) {
            return new Vec3(hitVec.x, hitY + 0.001, hitVec.z);
        } else {
            // Otherwise, find a suitable position below the hit position that is not inside a block
            Vec3 belowVec = new Vec3(hitVec.x, hitY - entity.getBbHeight(), hitVec.z);
            HitResult belowResult = level.clip(new ClipContext(hitVec, belowVec, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity));
            Vec3 belowLocation = belowResult.getLocation();
            return belowLocation.add(0, 0.001, 0);
        }
    }

    // method to handle the teleportation
    public static void teleportPlayer(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null) return;

        // define the start and end points of the raycast
        Vec3 start = entity.getEyePosition(1f);
        Vec3 end = start.add(entity.getViewVector(1f).scale(Config.ENDERIAN_EYE_RADIUS.get()));

        // solve teleport destination
        Vec3 targetPosition = solveTeleportDestination((Level) world, (LivingEntity) entity, new BlockPos((int) x, (int) y, (int) z), end);
        long currentTime = System.currentTimeMillis();
        long lastTeleportTime = cooldownMap.getOrDefault(entity, 0L);

        if (currentTime - lastTeleportTime >= 1000 * Config.ENDERIAN_EYE_COOLDOWN.get()) {

            // teleport the entity to the hit position
            entity.teleportTo(targetPosition.x, targetPosition.y, targetPosition.z);

            // if the entity is a ServerPlayer, update its connection to reflect the new position
            if (entity instanceof ServerPlayer serverPlayer) {
                serverPlayer.connection.teleport(targetPosition.x, targetPosition.y, targetPosition.z, entity.getYRot(), entity.getXRot());
                cooldownMap.put(entity, currentTime);
                if (world instanceof Level level) {
                    if (!level.isClientSide()) {
                        level.playSound(null, BlockPos.containing(targetPosition), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
                        serverPlayer.playSound(SoundEvents.ENDERMAN_TELEPORT, 2.0F, 1.0F);
                    }
                }
            }
        }
    }
}
