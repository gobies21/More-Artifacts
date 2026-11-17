package net.gobies.moreartifacts.event;

import net.gobies.moreartifacts.MoreArtifacts;
import net.gobies.moreartifacts.client.overlay.*;
import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.init.MAParticles;
import net.gobies.moreartifacts.network.*;
import net.gobies.moreartifacts.particle.*;
import net.gobies.moreartifacts.util.CurioHandler;
import net.gobies.moreartifacts.util.MAUtils;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
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
        event.registerSpriteSet(MAParticles.ANNIHILATION_SLASH.get(), AnnihilationSlashParticle.Factory::new);
        event.registerSpriteSet(MAParticles.MANA_SHIELD_DAMAGE.get(), ManaShieldDamageParticle.Factory::new);
        event.registerSpriteSet(MAParticles.MANA_SHIELD_BREAK.get(), ManaShieldBreakParticle.Factory::new);
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
                    PacketHandler.sendToServer(new TeleportBindMessage(0, 0));
                    MAUtils.logDebug("Teleport key pressed; packet sent");
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
                PacketHandler.sendToServer(new NightVisionBindMessage());
                MAUtils.logDebug("Dragon eye key pressed; toggle packet sent");
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

            if (isDown && !isDownOld) {
                PacketHandler.sendToServer(new SoulPassiveBindMessage());
                MAUtils.logDebug("Soul passive key pressed; toggle packet sent");
            }
            isDownOld = isDown;
        }
    };
    public static final KeyMapping SOUL_ACTIVE_KEY = new KeyMapping("key.moreartifacts.soul_active", GLFW.GLFW_KEY_X, "key.moreartifacts.category") {
        private boolean isDownOld = false;

        @SuppressWarnings("InstantiationOfUtilityClass")
        @Override
        public void setDown(boolean isDown) {
            super.setDown(isDown);
            Player player = Minecraft.getInstance().player;
            if (player == null) return;

            if (isDown && !isDownOld) {
                PacketHandler.sendToServer(new SoulAbilityBindMessage());
                MAUtils.logDebug("Soul active key pressed; toggle packet sent");
            }
            isDownOld = isDown;
        }
    };

    public static final KeyMapping STORM_SHOCK_KEY = new KeyMapping("key.moreartifacts.storm_shock", GLFW.GLFW_KEY_V, "key.moreartifacts.category") {
        private boolean isDownOld = false;

        @SuppressWarnings("InstantiationOfUtilityClass")
        @Override
        public void setDown(boolean isDown) {
            super.setDown(isDown);
            Player player = Minecraft.getInstance().player;
            if (player == null) return;

            if (isDown && !isDownOld) {
                PacketHandler.sendToServer(new StormShockBindMessage());
                MAUtils.logDebug("Storm shock key pressed; packet sent");
            }
            isDownOld = isDown;
        }
    };


    public static final KeyMapping DODGE_KEY = new KeyMapping("key.moreartifacts.dodge", GLFW.GLFW_KEY_LEFT_ALT, "key.moreartifacts.category");

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(EYE_TELEPORT_KEY);
        event.register(DRAGON_EYE_KEY);
        event.register(SOUL_PASSIVE_KEY);
        event.register(SOUL_ACTIVE_KEY);
        event.register(STORM_SHOCK_KEY);
        event.register(DODGE_KEY);
    }

    @Mod.EventBusSubscriber(Dist.CLIENT)
    public static class KeyEventListener {
        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {
            if (event.phase != TickEvent.Phase.END) return;
            if (Minecraft.getInstance().screen == null) {
                EYE_TELEPORT_KEY.consumeClick();
                DRAGON_EYE_KEY.consumeClick();
                SOUL_PASSIVE_KEY.consumeClick();
                SOUL_ACTIVE_KEY.consumeClick();
                STORM_SHOCK_KEY.consumeClick();
                DODGE_KEY.consumeClick();
            }
        }
    }

    @Mod.EventBusSubscriber(Dist.CLIENT)
    public static class ClientSync {
        @SubscribeEvent
        public static void onClientLoggingOut(ClientPlayerNetworkEvent.LoggingOut event) {
            SoulSyncPacket.SoulClientData.clearAll();
        }
    }

    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAbove(VanillaGuiOverlay.PLAYER_HEALTH.id(), "virulent_heart", VirulentHeartOverlay.OVERLAY);
        event.registerAbove(VanillaGuiOverlay.PLAYER_HEALTH.id(), "frosted_heart", FrostedHeartOverlay.OVERLAY);
        event.registerAbove(new ResourceLocation(MoreArtifacts.MOD_ID, "virulent_heart"), "mana_shield_heart", ManaShieldOverlay.OVERLAY);
        event.registerAbove(new ResourceLocation(MoreArtifacts.MOD_ID, "mana_shield_heart"), "broken_heart", BrokenHeartOverlay.OVERLAY);
        event.registerAbove(VanillaGuiOverlay.HOTBAR.id(), "curios_hud", CurioHudOverlay.CURIO_HUD);
    }
}
