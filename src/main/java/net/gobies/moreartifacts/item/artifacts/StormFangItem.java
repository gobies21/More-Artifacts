package net.gobies.moreartifacts.item.artifacts;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.gobies.moreartifacts.compat.apothecary.ApothecaryCompat;
import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.event.ClientEvents;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.init.MASounds;
import net.gobies.moreartifacts.mixin.CreeperAccessor;
import net.gobies.moreartifacts.network.PacketHandler;
import net.gobies.moreartifacts.network.StormShockPacket;
import net.gobies.moreartifacts.util.*;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class StormFangItem extends Item implements ICurioItem {
    public StormFangItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.RARE));
    }

    private static final UUID SPEED = UUID.fromString("0a9d922f-0548-4ee6-868c-8b33be589c1d");

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = LinkedHashMultimap.create();
        if (ModLoadedUtil.isBetterStealthLoaded()) {
            modifiers.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, String.valueOf(SPEED), CommonConfig.STORM_FANG_SPEED_INCREASE.get(), AttributeModifier.Operation.MULTIPLY_BASE));
        }
        return modifiers;
    }

    public static void triggerStormShock(ServerPlayer player) {
        if (!(player.level() instanceof ServerLevel level)) return;
        if (CurioHandler.isCurioEquipped(player, MAItems.StormFang.get())) {
            String artifactId = "storm_fang";
            float cooldown = CommonConfig.STORM_FANG_COOLDOWN.get().floatValue();
            if (!CooldownHandler.isReady(player, artifactId, cooldown)) return;
            float currentMana = ManaUtil.getMana(player);
            float manaCost = CommonConfig.STORM_FANG_MANA_COST.get();

            int shockRadius = CommonConfig.STORM_FANG_SHOCK_RADIUS.get();
            int shockDamage = CommonConfig.STORM_FANG_SHOCK_DAMAGE.get();

            List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(shockRadius), entity -> entity != player && entity.isAlive() && player.hasLineOfSight(entity) && (!(entity instanceof TamableAnimal tameable) || !tameable.isTame())).stream().limit(5).toList();

            if (targets.isEmpty()) return;

            DamageSource magicDamage = new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MAGIC), player, player);
            if (currentMana >= manaCost) {
                CooldownHandler.updateCooldown(player, artifactId);
                ManaUtil.setManaCost(player, manaCost);
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), MASounds.STORM_SHOCK.get(), SoundSource.PLAYERS, 0.8F, MAUtils.getRandomPitchRange());

                for (LivingEntity target : targets) {
                    target.hurt(magicDamage, shockDamage);
                    if (target instanceof Creeper creeper) {
                        creeper.getEntityData().set(CreeperAccessor.getPoweredData(), true);
                    }
                    if (ModLoadedUtil.isApothecaryLoaded()) {
                        target.addEffect(new MobEffectInstance(ApothecaryCompat.shocked(), 50, 1, false, true));
                    }
                    PacketHandler.sendToTrackingAndSelf(new StormShockPacket(player.getId(), target.getId()), player);
                }
            } else {
                player.displayClientMessage(Component.translatable("client.moreartifacts.mana").withStyle(ChatFormatting.RED), true);
            }
        }
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        int manaCost = CommonConfig.STORM_FANG_MANA_COST.get();
        int shockRadius = CommonConfig.STORM_FANG_SHOCK_RADIUS.get();
        int shockDamage = CommonConfig.STORM_FANG_SHOCK_DAMAGE.get();
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.storm_fang").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.storm_fang_ability").withStyle(ChatFormatting.GOLD).append(Component.literal(ClientEvents.STORM_SHOCK_KEY.getTranslatedKeyMessage().getString()).withStyle(ChatFormatting.YELLOW)).append(Component.translatable("tooltip.moreartifacts.toggle").withStyle(ChatFormatting.WHITE)).append(Component.literal(" (" + manaCost + " MP)").withStyle(ChatFormatting.AQUA)));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.storm_fang_stats", shockDamage, shockRadius).withStyle(ChatFormatting.BLUE));
        if (ModLoadedUtil.isLycanitesMobsLoaded()) {
            if (Screen.hasControlDown()) {
                pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.lycanites_paralysis").withStyle(ChatFormatting.GRAY));
            } else {
                pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.hold.ctrl"));
            }
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}










