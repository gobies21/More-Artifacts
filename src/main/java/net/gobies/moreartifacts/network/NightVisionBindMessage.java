package net.gobies.moreartifacts.network;

import net.gobies.moreartifacts.init.MAItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class NightVisionBindMessage {

    public NightVisionBindMessage() {}

    public NightVisionBindMessage(FriendlyByteBuf buffer) {}

    public static void buffer(NightVisionBindMessage message, FriendlyByteBuf buffer) {}

    public static void handle(NightVisionBindMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) return;

            CuriosApi.getCuriosInventory(player).ifPresent(handler -> {
                handler.getCurios().forEach((identifier, stacksHandler) -> {
                    IDynamicStackHandler stackHandler = stacksHandler.getStacks();
                    for (int slot = 0; slot < stackHandler.getSlots(); slot++) {
                        ItemStack stack = stackHandler.getStackInSlot(slot);
                        if (stack.getItem() == MAItems.DragonEye.get()) {
                            CompoundTag tag = stack.getOrCreateTag();
                            tag.putBoolean("NightVision", !tag.getBoolean("NightVision"));
                        }
                    }
                });
            });
        });
        context.setPacketHandled(true);
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        PacketHandler.addNetworkMessage(NightVisionBindMessage.class, NightVisionBindMessage::buffer, NightVisionBindMessage::new, NightVisionBindMessage::handle);
    }
}