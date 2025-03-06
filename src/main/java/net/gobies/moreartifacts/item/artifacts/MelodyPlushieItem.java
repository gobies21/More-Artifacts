package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.item.ModItems;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class MelodyPlushieItem extends Item implements ICurioItem {
    public MelodyPlushieItem(Properties properties) {
        super(new Properties().stacksTo(1).rarity(Rarity.EPIC));
    }

    //coming back to this (DOESNT WORK)

    @SubscribeEvent
    public static void onPlayerWakeUp(PlayerWakeUpEvent event) {
        Player player = event.getEntity();
        if (!player.level().isClientSide && player.isSleeping()) {
            CuriosApi.getCuriosHelper().findEquippedCurio(ModItems.MelodyPlushie.get(), player).ifPresent((slot) -> {
                player.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 4800, 1, false, true)); // Grant Health Boost II for 2 minutes
            });
        }
    }

    private static final UUID MAX_HEALTH = UUID.randomUUID();
    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        if (livingEntity instanceof Player player) {
            var attribute = player.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH);
            if (attribute != null) {
                if (attribute.getModifier(MAX_HEALTH) == null) {
                    attribute.addTransientModifier(
                            new AttributeModifier(MAX_HEALTH, "Max Health", 0.2, AttributeModifier.Operation.MULTIPLY_BASE));
                    if (!player.hasEffect(MobEffects.REGENERATION)) {
                        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, -1, 0, false, false));
                    }
                }
            }
        }
    }
            public void onUnequip(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
                if (livingEntity instanceof Player player) {
                    player.removeEffect(MobEffects.REGENERATION);
                    player.getAttribute(Attributes.MAX_HEALTH).removeModifier(MAX_HEALTH);
                }
                livingEntity.hurt(livingEntity.damageSources().generic(), 0.1F);
            }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("§3+20.0% §dMax Health"));
        pTooltipComponents.add(Component.literal("§dGrants Regeneration"));
        pTooltipComponents.add(Component.literal("§dIf equip when sleeping, grants Health Boost II"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    private class GENERIC {
    }
}








