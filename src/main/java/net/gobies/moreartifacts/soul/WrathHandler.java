package net.gobies.moreartifacts.soul;

import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.effect.MAStatusEffects;
import net.gobies.moreartifacts.helper.SoulAttributes;
import net.gobies.moreartifacts.helper.SoulSyncHelper;
import net.gobies.moreartifacts.helper.SoulType;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.init.MAParticles;
import net.gobies.moreartifacts.init.MASounds;
import net.gobies.moreartifacts.network.AnnihilationStackSyncPacket;
import net.gobies.moreartifacts.network.PacketHandler;
import net.gobies.moreartifacts.util.CurioHandler;
import net.gobies.moreartifacts.util.LuckHelper;
import net.gobies.moreartifacts.util.MAUtils;
import net.gobies.moreartifacts.util.SoulUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WrathHandler extends SoulUtil {

    public static final Map<UUID, Float> annihilation = new HashMap<>();

    public static MobEffect getEffect() {
        return ForgeRegistries.MOB_EFFECTS.getValue(ResourceLocation.tryParse(CommonConfig.WRATH_EFFECT.get()));
    }

    public static void tickWrathSoul(Player player, CompoundTag persistentData) {
        UUID playerId = player.getUUID();
        if (player.tickCount % 5 == 0) {
            if (player.tickCount % 10 == 0) {
                SoulAttributes.manageWrathAttributes(player);
            }

            if (!persistentData.contains(SoulUtil.PASSIVE_TOGGLE)) {
                SoulUtil.setActive(player, true);
            }

            boolean abilityActive = SoulUtil.getPassive(player);
            boolean isActive = activePassive.contains(playerId);
            MobEffectInstance currentEffect = player.getEffect(getEffect());

            if (abilityActive) {
                if (currentEffect == null || !isActive) {
                    player.addEffect(new MobEffectInstance(getEffect(), 210, 0, true, false));
                    activePassive.add(playerId);
                } else if (currentEffect.getDuration() <= 110) {
                    player.addEffect(new MobEffectInstance(getEffect(), 210, 0, true, false));
                }
            } else if (isActive) {
                if (currentEffect != null) {
                    player.removeEffect(getEffect());
                }
                activePassive.remove(playerId);
            }
            MAUtils.removeEffect(player, MobEffects.WEAKNESS, MobEffects.DIG_SLOWDOWN);
        }
    }

    public static void handleSinful(DamageSource source) {
        if (source.getEntity() instanceof ServerPlayer player) {
            if (source.isIndirect()) return;
            if (player.getAttackStrengthScale(1.0F) >= 0.8F) {
                float stage4Chance = CommonConfig.WRATH_STAGE_4_SINFUL_CHANCE.get().floatValue();
                int stage4Duration = CommonConfig.WRATH_STAGE_4_SINFUL_DURATION.get();
                int stage4MaxLevel = CommonConfig.WRATH_STAGE_4_SINFUL_MAX_LEVEL.get();
                ItemStack wrathSoul = CurioHandler.getEquippedCurio(player, MAItems.WrathSoul.get());
                if (wrathSoul != null && SoulUtil.getSoulStage(wrathSoul) == 4) {
                    if (LuckHelper.roll(player, stage4Chance, 0.01)) {
                        SoulUtil.applySinful(player, stage4Duration, stage4MaxLevel);
                    }
                } else if (SoulUtil.hasSoul(player, SoulType.WRATH)) {
                    float chance = CommonConfig.WRATH_SINFUL_CHANCE.get().floatValue();
                    int duration = CommonConfig.WRATH_SINFUL_DURATION.get();
                    int maxLevel = CommonConfig.WRATH_SINFUL_MAX_LEVEL.get();
                    if (LuckHelper.roll(player, chance, 0.01)) {
                        SoulUtil.applySinful(player, duration, maxLevel);
                    }
                }
            }
        }
    }

    public static void handleAnnihilation(LivingEntity target, DamageSource source, LivingHurtEvent event) {
        if (source.getEntity() instanceof ServerPlayer player) {
            if (source.isIndirect()) return;
            if (source.getDirectEntity() != player) return;
            Float multiplier = annihilation.remove(player.getUUID());

            if (multiplier == null) return;

            PacketHandler.sendToClient(new AnnihilationStackSyncPacket(0), player);
            if (!SoulUtil.hasSoul(player, SoulType.WRATH)) {
                SoulUtil.setActive(player, false);
                SoulSyncHelper.syncSouls(player);
                return;
            }
            UUID targetId = target.getUUID();

            event.setAmount(event.getAmount() * multiplier);
            if (!MAUtils.bleedImmune(target)) {
                MAStatusEffects.bleeding().put(targetId, (int) ((multiplier * 10.0F) * 10));
            }
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), MASounds.ANNIHILATION_HIT.get(), SoundSource.PLAYERS, 1.2F, MAUtils.getRandomPitch());
            if (target.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(
                        MAParticles.ANNIHILATION_SLASH.get(),
                        target.getX() + (target.getRandom().nextDouble() - 0.5D) * 0.6D,
                        target.getY() + target.getBbHeight() * 0.8D,
                        target.getZ() + (target.getRandom().nextDouble() - 0.5D) * 0.6D,
                        1,
                        0.0D, 0.0D, 0.0D,
                        0.0D
                );
            }
            SoulUtil.setActive(player, false);
            SoulSyncHelper.syncSouls(player);
        }
    }
}
