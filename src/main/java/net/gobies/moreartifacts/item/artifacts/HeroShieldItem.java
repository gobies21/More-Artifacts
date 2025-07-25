package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.util.ModLoadedUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import javax.annotation.Nullable;
import net.minecraftforge.common.MinecraftForge;
import java.util.List;

public class HeroShieldItem extends Item implements ICurioItem {
    public HeroShieldItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.EPIC).fireResistant());
    }

    static {
        MinecraftForge.EVENT_BUS.register(HeroShieldItem.class);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            if (!player.hasEffect(MobEffects.DAMAGE_RESISTANCE)) {
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, -1, CommonConfig.HERO_SHIELD_RES_LEVEL.get() - 1, false, false));
            }
        }
    }

    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            player.removeEffect(MobEffects.DAMAGE_RESISTANCE);
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player player && event.getSource().getEntity() != null) {
            CuriosApi.getCuriosInventory(player).ifPresent(handler -> handler.findFirstCurio(stack -> stack.getItem() instanceof HeroShieldItem).ifPresent(slotResult -> {
                ItemStack stack = slotResult.stack();
                if (stack.getItem() instanceof HeroShieldItem) {
                    ItemStack pStack = slotResult.stack();
                    CompoundTag tag = pStack.getOrCreateTag();
                    int hitCount = tag.getInt("HitCount");
                    hitCount++;
                    tag.putInt("HitCount", hitCount);
                    if (hitCount % CommonConfig.IGNORE_DAMAGE_CHANCE.get() == 0 && !event.isCanceled()) {
                        event.setCanceled(true);
                        player.displayClientMessage(Component.translatable("tooltip.moreartifacts.hero_shield.damage_text").withStyle(ChatFormatting.GOLD), true);
                        player.level().playSound(null, player.blockPosition(), SoundEvents.ANVIL_LAND, SoundSource.PLAYERS, 0.3f, 1.1f);
                        tag.putInt("HitCount", 0);
                    }
                    if (event.getSource().is(DamageTypes.EXPLOSION) || event.getSource().is(DamageTypes.PLAYER_EXPLOSION)) {
                        event.setAmount((float) (event.getAmount() * CommonConfig.EXPLOSION_DAMAGE_TAKEN.get()));
                    }
                }
            }));
        }
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        CompoundTag tag = pStack.getTag();
        int hitCount = tag != null ? tag.getInt("HitCount") : 0;
        int ignoreDamage = CommonConfig.IGNORE_DAMAGE_CHANCE.get();
        double explosionDamage = (1.0 - CommonConfig.EXPLOSION_DAMAGE_TAKEN.get()) * 100;
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.hero_shield.resistance").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.hero_shield.damage_ignore", ignoreDamage, hitCount, ignoreDamage).withStyle(ChatFormatting.DARK_AQUA));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.hero_shield.explosion_damage", String.format("%.1f", explosionDamage)).withStyle(ChatFormatting.DARK_AQUA));
        if (ModLoadedUtil.isEnhancedVisualsLoaded() && CommonConfig.HERO_SHIELD_COMPAT.get()) {
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.hold.ctrl"));
            if (Screen.hasControlDown()) {
                pTooltipComponents.remove(4);
                pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.hero_shield.enhanced_visuals").withStyle(ChatFormatting.GRAY));
            }
            super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        }
    }
}




