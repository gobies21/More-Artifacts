package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.item.ModItems;
import net.minecraft.network.chat.Component;
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
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;

public class EnvenomedQuiverItem extends Item implements ICurioItem {
    public EnvenomedQuiverItem(Properties properties) {
        super(new Properties().stacksTo(1).rarity(Rarity.RARE));
    }

    static {
        MinecraftForge.EVENT_BUS.register(EnvenomedQuiverItem.class);
    }
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player attacker) {
            CuriosApi.getCuriosHelper().findEquippedCurio(ModItems.EnvenomedQuiver.get(), attacker).ifPresent((slot) -> {
                if (event.getSource().is(DamageTypes.ARROW)) {
                    event.setAmount(event.getAmount() * 1.10f);
                    LivingEntity target = event.getEntity();
                    target.addEffect(new net.minecraft.world.effect.MobEffectInstance(MobEffects.POISON, 100, 0));
                    target.addEffect(new net.minecraft.world.effect.MobEffectInstance(MobEffects.WITHER, 100, 0));

                }
            });
        }
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("§6Increases arrow damage by §310.0%"));
        pTooltipComponents.add(Component.literal("§6Arrows inflict poison and wither to entites"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}








