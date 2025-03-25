package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.Config;
import net.gobies.moreartifacts.init.MoreArtifactsKeybinds;
import net.gobies.moreartifacts.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;
import org.lwjgl.glfw.GLFW;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;


@Mod.EventBusSubscriber
public class EnderianEyeItem extends Item implements ICurioItem {
    public EnderianEyeItem(Properties properties) {
        super(new Properties().stacksTo(1).rarity(Rarity.RARE));

    }

    private static final int MAX_RADIUS = 25;
    private static final float COOLDOWN_SECONDS = 5.0F;
    private static final String TELEPORT_KEY = "KEY_TELEPORT"; // You should define this in your KeyMappings class
    private static final AtomicBoolean canTeleport = new AtomicBoolean(true);

    static {
        MinecraftForge.EVENT_BUS.register(EnderianEyeItem.class);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("§6Grants the ability to teleport where you are looking"));
        pTooltipComponents.add(Component.literal("§6Max radius of §325 §6blocks with a §35.0 §6second cooldown"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }


    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (event.getKey() != GLFW.GLFW_KEY_T) return;
        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        // Check if EnderianEye is in a curio slot
        CuriosApi.getCuriosHelper().findEquippedCurio(ModItems.EnderianEye.get(), player).ifPresent((slot) -> {
            if (canTeleport.get()) {
                // Teleport logic
                teleportPlayer(player);

                // Set cooldown
                //canTeleport.set(false);
                player.getCooldowns().addCooldown(ModItems.EnderianEye.get(), (int) (COOLDOWN_SECONDS * 20));
            }
        });
    }

    private static void teleportPlayer(Player player) {
        // Get the look vector and calculate the target position
        Vec3 lookVec = player.getLookAngle();
        BlockPos targetPos = new BlockPos((int) (player.getX() + lookVec.x * EnderianEyeItem.MAX_RADIUS), (int) (player.getY() + lookVec.y * EnderianEyeItem.MAX_RADIUS), (int) (player.getZ() + lookVec.z * EnderianEyeItem.MAX_RADIUS));

// Check if the target position is within the world boundaries
        if (player.level().hasChunkAt(targetPos)) {
            // Check for collision with blocks
            if (player.level().noCollision(player, player.getBoundingBox().move(targetPos.getX() - player.getX(), targetPos.getY() - player.getY(), targetPos.getZ() - player.getZ()))) {
                // Teleport player to the target position
                player.teleportTo(targetPos.getX(), targetPos.getY(), targetPos.getZ());
            }
        }
    }
}


