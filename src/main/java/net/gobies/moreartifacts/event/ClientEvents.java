package net.gobies.moreartifacts.event;

import net.gobies.moreartifacts.MoreArtifacts;
import net.gobies.moreartifacts.init.MoreArtifactsKeybinds;
import net.gobies.moreartifacts.network.TeleportBindMessage;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;
import top.theillusivec4.curios.api.CuriosApi;
import static net.gobies.moreartifacts.item.ModItems.EnderianEye;

@Mod.EventBusSubscriber(modid = MoreArtifacts.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvents {
    public static final KeyMapping TELEPORT_KEY = new KeyMapping("key.moreartifacts.teleport", GLFW.GLFW_KEY_T, "key.categories.misc") {
        private boolean isDownOld = false;

        @Override
        public void setDown(boolean isDown) {
            super.setDown(isDown);
            Player player = Minecraft.getInstance().player;
            if (player == null) return;
            CuriosApi.getCuriosHelper().findEquippedCurio(EnderianEye.get(), player).ifPresent((slot) -> {
                if (isDownOld != isDown && isDown) {
                    MoreArtifacts.PACKET_HANDLER.sendToServer(new TeleportBindMessage(0, 0));
                    assert Minecraft.getInstance().player != null;
                    TeleportBindMessage.pressAction(Minecraft.getInstance().player, 0, 0);
                }
                isDownOld = isDown;
            });
        }
    };

        @SubscribeEvent
        public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
            event.register(TELEPORT_KEY);
        }

        @Mod.EventBusSubscriber({Dist.CLIENT})
        public static class KeyEventListener {
            @SubscribeEvent
            public static void onClientTick(TickEvent.ClientTickEvent event) {
                if (Minecraft.getInstance().screen == null) {
                    TELEPORT_KEY.consumeClick();
                }
            }
        }
    };
