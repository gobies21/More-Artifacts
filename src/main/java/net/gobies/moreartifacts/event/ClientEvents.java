package net.gobies.moreartifacts.event;

import net.gobies.moreartifacts.MoreArtifacts;
import net.gobies.moreartifacts.client.overlay.BrokenHeartOverlay;
import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.init.MAParticles;
import net.gobies.moreartifacts.network.InvisibilityBindMessage;
import net.gobies.moreartifacts.network.NightVisionBindMessage;
import net.gobies.moreartifacts.network.PacketHandler;
import net.gobies.moreartifacts.particle.BloodParticle;
import net.gobies.moreartifacts.util.CurioHandler;
import net.gobies.moreartifacts.network.TeleportBindMessage;
import net.gobies.moreartifacts.util.MAUtils;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = MoreArtifacts.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvents {

    @SubscribeEvent
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(MAParticles.BLOOD.get(), BloodParticle.Factory::new);
    }

    public static final KeyMapping EYE_TELEPORT_KEY = new KeyMapping("key.moreartifacts.eye.teleport", GLFW.GLFW_KEY_C, "key.moreartifacts.category") {
        private boolean isDownOld = false;
        private long lastPressTime = 0;
        @Override
        public void setDown(boolean isDown) {
            super.setDown(isDown);
            Player player = Minecraft.getInstance().player;
            if (player == null) return;

            long currentTime = System.currentTimeMillis();
            if (CurioHandler.isCurioEquipped(player, MAItems.EnderianEye.get())) {
                if (isDownOld != isDown && isDown && (currentTime - lastPressTime) > 1000 * CommonConfig.ENDERIAN_EYE_COOLDOWN.get()) {
                    PacketHandler.PACKET_HANDLER.sendToServer(new TeleportBindMessage(0, 0));
                    TeleportBindMessage.pressAction(Minecraft.getInstance().player, 0);
                    lastPressTime = currentTime;
                }
            }
            isDownOld = isDown;
        }
    };
    public static final KeyMapping DRAGON_EYE_KEY = new KeyMapping("key.moreartifacts.dragon.eye", GLFW.GLFW_KEY_C, "key.moreartifacts.category") {
        private boolean isDownOld = false;
        @SuppressWarnings("InstantiationOfUtilityClass")
        @Override
        public void setDown(boolean isDown) {
            super.setDown(isDown);
            Player player = Minecraft.getInstance().player;
            if (player == null) return;
            if (!CurioHandler.isCurioEquipped(player, MAItems.DragonEye.get())) return;

            if (isDown && !isDownOld) {
                PacketHandler.PACKET_HANDLER.sendToServer(new NightVisionBindMessage());
                MAUtils.logDebug("Dragon Eye key pressed; toggle packet sent");
            }
            isDownOld = isDown;
        }
    };

    public static final KeyMapping SOUL_PASSIVE_KEY = new KeyMapping("key.moreartifacts.soul_passive", GLFW.GLFW_KEY_Y, "key.moreartifacts.category") {
        private boolean isDownOld = false;
        @SuppressWarnings("InstantiationOfUtilityClass")
        @Override
        public void setDown(boolean isDown) {
            super.setDown(isDown);
            Player player = Minecraft.getInstance().player;
            if (player == null) return;
            if (!CurioHandler.isCurioEquipped(player, MAItems.ShadowSoul.get())) return;

            if (isDown && !isDownOld) {
                PacketHandler.PACKET_HANDLER.sendToServer(new InvisibilityBindMessage());
                MAUtils.logDebug("Shadow Soul key pressed; toggle packet sent");
            }
            isDownOld = isDown;
        }
    };

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(EYE_TELEPORT_KEY);
        event.register(DRAGON_EYE_KEY);
        event.register(SOUL_PASSIVE_KEY);
    }

    @Mod.EventBusSubscriber({Dist.CLIENT})
    public static class KeyEventListener {
        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {
            if (event.phase != TickEvent.Phase.END) return;
            if (Minecraft.getInstance().screen == null) {
                EYE_TELEPORT_KEY.consumeClick();
                DRAGON_EYE_KEY.consumeClick();
                SOUL_PASSIVE_KEY.consumeClick();
            }
        }
    }

    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAbove(VanillaGuiOverlay.PLAYER_HEALTH.id(), "broken_heart_overlay", BrokenHeartOverlay.OVERLAY);
    }
}
