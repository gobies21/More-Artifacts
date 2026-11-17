package net.gobies.moreartifacts.network;

import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.helper.SoulSyncHelper;
import net.gobies.moreartifacts.helper.SoulType;
import net.gobies.moreartifacts.init.MAEffects;
import net.gobies.moreartifacts.init.MASounds;
import net.gobies.moreartifacts.soul.WrathHandler;
import net.gobies.moreartifacts.util.MAUtils;
import net.gobies.moreartifacts.util.ManaUtil;
import net.gobies.moreartifacts.util.ModLoadedUtil;
import net.gobies.moreartifacts.util.SoulUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.network.NetworkEvent;

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

            String playerSoul = player.getPersistentData().getString(SoulUtil.SOUL_KEY);
            if (playerSoul.isEmpty() || SoulUtil.hasSoul(player, SoulType.UNKNOWN)) return;

            float currentMana = ManaUtil.getMana(player);
            boolean active = SoulUtil.getActive(player);
            boolean newState = !active;
            boolean isEnvy = SoulUtil.hasSoul(player, SoulType.ENVY);
            boolean isWrath = SoulUtil.hasSoul(player, SoulType.WRATH);
            boolean spellbooksCompat = ModLoadedUtil.isIronsSpellbooksLoaded() && CommonConfig.IRONS_SPELLBOOKS_MANA_COMPAT.get();

            if (isEnvy) {
                if (!active) {
                    float requiredMana = CommonConfig.ENVY_MANA_SHIELD_START_THRESHOLD.get().floatValue();
                    if (spellbooksCompat) {
                        requiredMana = CommonConfig.ENVY_SPELLBOOKS_MANA_SHIELD_START_THRESHOLD.get().floatValue();
                    }
                    if (currentMana <= requiredMana) {
                        player.displayClientMessage(Component.translatable("client.moreartifacts.mana").withStyle(ChatFormatting.RED), true);
                        return;
                    }
                }

                float manaCost = CommonConfig.ENVY_MANA_SHIELD_COST.get().floatValue();
                if (spellbooksCompat) {
                    manaCost = CommonConfig.ENVY_SPELLBOOKS_MANA_SHIELD_COST.get().floatValue();
                }

                if (currentMana >= manaCost && !active) {
                    SoulUtil.setActive(player, true);
                    ManaUtil.setMana(player, currentMana - manaCost);
                } else {
                    SoulUtil.setActive(player, newState);
                }

                if (CommonConfig.MANA_SHIELD_SOUNDS.get()) {
                    var sound = newState ? MASounds.MANA_SHIELD_ENABLED.get() : MASounds.MANA_SHIELD_DISABLED.get();
                    player.playNotifySound(sound, SoundSource.PLAYERS, 1.5F, MAUtils.getRandomPitch());
                }
            } else if (isWrath) {
                if (!WrathHandler.annihilation.containsKey(player.getUUID())) {
                    MobEffectInstance sinful = player.getEffect(MAEffects.Sinful.get());
                    if (sinful == null) {
                        player.displayClientMessage(Component.literal("§cYou need sinful to trigger annihilation"), true);
                        return;
                    }

                    float damageMultiplier = CommonConfig.WRATH_ANNIHILATION_DAMAGE_MULTIPLIER.get().floatValue();
                    int stacks = sinful.getAmplifier() + 1;
                    float finalMultiplier = Math.min(1.0F + (stacks * damageMultiplier), CommonConfig.WRATH_ANNIHILATION_MAX_STACKS.get());

                    WrathHandler.annihilation.put(player.getUUID(), finalMultiplier);
                    player.removeEffect(MAEffects.Sinful.get());

                    SoulUtil.setActive(player, true);
                    PacketHandler.sendToClient(new AnnihilationStackSyncPacket(stacks), player);
                    player.playNotifySound(MASounds.ANNIHILATION_STACK.get(), SoundSource.PLAYERS, 1.5F, MAUtils.getRandomPitch());
                }
            } else {
                SoulUtil.setActive(player, newState);
            }

            SoulSyncHelper.syncSouls(player);
        });
        context.setPacketHandled(true);
    }
}