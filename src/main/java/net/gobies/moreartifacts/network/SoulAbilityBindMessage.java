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
public class SoulAbilityBindMessage {

    public SoulAbilityBindMessage() {}

    public SoulAbilityBindMessage(FriendlyByteBuf buffer) {}

    public static void buffer(SoulAbilityBindMessage message, FriendlyByteBuf buffer) {}

    public static void handle(SoulAbilityBindMessage message, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) return;

            CompoundTag persistentData = player.getPersistentData();
            String playerSoul = persistentData.getString(SoulUtil.SOUL_KEY);

            if (!playerSoul.isEmpty() && !SoulType.UNKNOWN.key.equals(playerSoul)) {
                String toggleKey = SoulUtil.ABILITY_TOGGLE;
                persistentData.putBoolean(toggleKey, !persistentData.getBoolean(toggleKey));
            }

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