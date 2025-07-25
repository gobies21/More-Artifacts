package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.util.CurioHandler;
import net.gobies.moreartifacts.init.MAItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;

public class EnvenomedQuiverItem extends Item implements ICurioItem {
    public EnvenomedQuiverItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.RARE));
    }

    static {
        MinecraftForge.EVENT_BUS.register(EnvenomedQuiverItem.class);
    }
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player attacker) {
            if (CurioHandler.isCurioEquipped(attacker, MAItems.EnvenomedQuiver.get())) {
                if (event.getSource().is(DamageTypes.ARROW)) {
                    LivingEntity target = event.getEntity();
                    target.addEffect(new MobEffectInstance(MobEffects.POISON, 20 * CommonConfig.ENVENOMED_QUIVER_POISON_DURATION.get(), CommonConfig.ENVENOMED_QUIVER_POISON_LEVEL.get() - 1));
                    target.addEffect(new MobEffectInstance(MobEffects.WITHER, 20 * CommonConfig.ENVENOMED_QUIVER_WITHER_DURATION.get(), CommonConfig.ENVENOMED_QUIVER_WITHER_LEVEL.get() - 1));
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
        double arrowDamage = (CommonConfig.ENVENOMED_QUIVER_DAMAGE.get() - 1) * 100;
        double arrowSave = (CommonConfig.ENVENOMED_QUIVER_AMMO.get() * 100);
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.envenomed_quiver.damage", String.format("%.1f", arrowDamage)).withStyle(ChatFormatting.DARK_AQUA));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.envenomed_quiver.save_arrow", String.format("%.1f", arrowSave)).withStyle(ChatFormatting.DARK_AQUA));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.envenomed_quiver.inflict").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}








