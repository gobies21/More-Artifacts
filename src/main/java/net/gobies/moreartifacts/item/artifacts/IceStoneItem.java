package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.Config;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.util.CurioHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;

public class IceStoneItem extends Item implements ICurioItem {
    public IceStoneItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.RARE));
    }

    static {
        MinecraftForge.EVENT_BUS.register(IceStoneItem.class);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            if (player.isFreezing()) {
                player.setTicksFrozen(-1);
            }
        }
    }

    @SubscribeEvent
    public static void onEntityAttacked(LivingAttackEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (CurioHandler.isCurioEquipped(player, MAItems.IceStone.get())) {
                if (event.getSource().is(DamageTypes.FREEZE)) {
                    event.setCanceled(true);

                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player attacker) {
            if (CurioHandler.isCurioEquipped(attacker, MAItems.IceStone.get())) {
                RandomSource random = attacker.getRandom();
                LivingEntity target = event.getEntity();
                if (random.nextFloat() < Config.ICE_STONE_CHANCE.get()) {
                    target.setTicksFrozen(100 * Config.ICE_STONE_DURATION.get());
                    target.addEffect(new net.minecraft.world.effect.MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * Config.ICE_STONE_DURATION.get(), 0));
                }
                if (target.hasEffect(MobEffects.MOVEMENT_SLOWDOWN) || (target.isFreezing())) {
                    event.setAmount((float) (event.getAmount() * Config.ICE_STONE_DAMAGE.get()));
                }
            }
        }
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        double iceChance = Config.ICE_STONE_CHANCE.get() * 100;
        int iceDuration = Config.ICE_STONE_DURATION.get();
        double increasedDamage = (Config.ICE_STONE_DAMAGE.get() - 1) * 100;
        double encaseChance = (Config.ICE_STONE_ENCASED_CHANCE.get() * 100);
        int encaseDuration = Config.ICE_STONE_ENCASED_DURATION.get();
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.ice_stone.immunity").withStyle(ChatFormatting.AQUA));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.ice_stone.freeze", String.format("%.1f", iceChance), iceDuration).withStyle(ChatFormatting.DARK_AQUA));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.ice_stone.increased_damage", String.format("%.1f",  increasedDamage)).withStyle(ChatFormatting.DARK_AQUA));
        if (ModList.get().isLoaded("iceandfire") && (Config.ICE_STONE_COMPAT.get())) {
            pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.hold.ctrl"));
            if (Screen.hasControlDown()) {
                pTooltipComponents.remove(4);
                pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.ice_stone.encased", String.format("%.1f", encaseChance), encaseDuration).withStyle(ChatFormatting.DARK_AQUA));
            }
            super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        }
    }
}








