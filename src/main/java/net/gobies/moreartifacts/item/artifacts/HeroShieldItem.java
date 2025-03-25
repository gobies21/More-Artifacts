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
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import javax.annotation.Nullable;
import net.minecraftforge.common.MinecraftForge;
import java.util.List;

public class HeroShieldItem extends Item implements ICurioItem {
    public static int hitCount = 0;
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
        if (event.getEntity() instanceof Player player && event.getSource().getEntity() != null) {
            CuriosApi.getCuriosHelper().findEquippedCurio(ModItems.HeroShield.get(), player).ifPresent((slot) -> {
                hitCount++;
                if (hitCount % Config.IGNORE_DAMAGE_CHANCE.get() == 0) {
                    event.setCanceled(true);
                    player.displayClientMessage(Component.literal("§6Ow!"), true);
                    player.level().playSound(null, player.blockPosition(), SoundEvents.ANVIL_LAND, SoundSource.PLAYERS, 0.6f, 1.1f);
                    hitCount = 0;
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
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("§6Grants Resistance"));
        pTooltipComponents.add(Component.literal(String.format("§6Every §3%d §6hits taken ignore the attack §3" + hitCount +"§3/%d", Config.IGNORE_DAMAGE_CHANCE.get(), Config.IGNORE_DAMAGE_CHANCE.get())));
        pTooltipComponents.add(Component.literal(String.format("§6Reduces explosion damage taken by §3%.1f%%", (1.0 - Config.EXPLOSION_DAMAGE_TAKEN.get()) * 100)));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}








