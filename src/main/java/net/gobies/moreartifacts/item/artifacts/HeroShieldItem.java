package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.Config;
import net.gobies.moreartifacts.item.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import javax.annotation.Nullable;
import net.minecraftforge.common.MinecraftForge;
import java.util.List;

public class HeroShieldItem extends Item implements ICurioItem {
    public HeroShieldItem(Properties properties) {
        super(new Properties().stacksTo(1).rarity(Rarity.EPIC));
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        if (livingEntity instanceof Player player) {
            if (!player.hasEffect(MobEffects.DAMAGE_RESISTANCE)) {
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, -1, 0, false, false));
            }
        }
    }
    public void onUnequip(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        if (livingEntity instanceof Player player) {
            player.removeEffect(MobEffects.DAMAGE_RESISTANCE);
        }

    }
    static {
        MinecraftForge.EVENT_BUS.register(HeroShieldItem.class);
    }
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player player) {
            CuriosApi.getCuriosHelper().findEquippedCurio(ModItems.HeroShield.get(), player).ifPresent((slot) -> {
                RandomSource random = player.getRandom();
                if (random.nextFloat() < Config.IGNORE_DAMAGE_CHANCE.get()) {
                    event.setCanceled(true);
                    player.displayClientMessage(Component.literal("§6Ow!"), true);
                    player.level().playSound(null, player.blockPosition(), SoundEvents.ANVIL_LAND, SoundSource.PLAYERS, 0.6f, 1.1f);
                }
                if (event.getSource().is(DamageTypes.EXPLOSION) || event.getSource().is(DamageTypes.PLAYER_EXPLOSION)) {
                    event.setAmount((float) (event.getAmount() * Config.EXPLOSION_DAMAGE_TAKEN.get()));
                }
            });
        }
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("§6Grants Resistance"));
        pTooltipComponents.add(Component.literal(String.format("§3%.1f%% §6Chance to ignore damage", Config.IGNORE_DAMAGE_CHANCE.get() * 100)));
        pTooltipComponents.add(Component.literal(String.format("§6Reduces explosion damage taken by §3%.1f%%", (1.0 - Config.EXPLOSION_DAMAGE_TAKEN.get()) * 100)));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}








