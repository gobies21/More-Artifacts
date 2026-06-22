package net.gobies.moreartifacts.network;

import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.util.SoulUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class InvisibilityBindMessage {

    public InvisibilityBindMessage() {}

    public InvisibilityBindMessage(FriendlyByteBuf buffer) {}

    public static void buffer(InvisibilityBindMessage message, FriendlyByteBuf buffer) {}

    public static void handle(InvisibilityBindMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) return;

            CuriosApi.getCuriosInventory(player).ifPresent(handler -> handler.getCurios().forEach((identifier, stacksHandler) -> {
                IDynamicStackHandler stackHandler = stacksHandler.getStacks();
                for (int slot = 0; slot < stackHandler.getSlots(); slot++) {
                    ItemStack stack = stackHandler.getStackInSlot(slot);
                    int stage = SoulUtil.getSoulStage(stack);
                    if (stack.getItem() == MAItems.ShadowSoul.get() && stage >= 3) {
                        CompoundTag tag = stack.getOrCreateTag();
                        tag.putBoolean("Invisibility", !tag.getBoolean("Invisibility"));
                    }
                }
            }));
        });
        context.setPacketHandled(true);
    }
}