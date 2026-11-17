package net.gobies.moreartifacts.network;

import net.gobies.moreartifacts.helper.SoulType;
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
public class SoulPassiveBindMessage {

    public SoulPassiveBindMessage() {}

    public SoulPassiveBindMessage(FriendlyByteBuf buffer) {}

    public static void buffer(SoulPassiveBindMessage message, FriendlyByteBuf buffer) {}

    public static void handle(SoulPassiveBindMessage message, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) return;

            String playerSoul = player.getPersistentData().getString(SoulUtil.SOUL_KEY);
            if (playerSoul.isEmpty() || SoulUtil.hasSoul(player, SoulType.UNKNOWN)) return;

            boolean passive = SoulUtil.getPassive(player);
            SoulUtil.setPassive(player, !passive);

            CuriosApi.getCuriosInventory(player).ifPresent(handler ->
                    handler.getCurios().forEach((identifier, stacksHandler) -> {
                        IDynamicStackHandler stackHandler = stacksHandler.getStacks();
                        for (int slot = 0; slot < stackHandler.getSlots(); slot++) {
                            ItemStack stack = stackHandler.getStackInSlot(slot);
                            int stage = SoulUtil.getSoulStage(stack);

                            if (SoulUtil.SOULS.contains(stack.getItem()) && stage >= 3) {
                                String soulType = SoulUtil.getSoul(stack.getItem());
                                CompoundTag tag = stack.getOrCreateTag();

                                tag.putBoolean(soulType, !tag.getBoolean(soulType));
                            }
                        }
                    })
            );
        });
        context.setPacketHandled(true);
    }
}