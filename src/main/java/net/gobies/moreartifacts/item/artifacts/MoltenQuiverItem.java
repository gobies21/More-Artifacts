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

public class MoltenQuiverItem extends Item implements ICurioItem {
    public MoltenQuiverItem(Properties properties) {
        super(new Properties().stacksTo(1).rarity(Rarity.RARE));
    }

    static {
        MinecraftForge.EVENT_BUS.register(MoltenQuiverItem.class);
    }
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player attacker) {
            CuriosApi.getCuriosHelper().findEquippedCurio(ModItems.MoltenQuiver.get(), attacker).ifPresent((slot) -> {
                if (event.getSource().is(DamageTypes.ARROW)) {
                    event.setAmount(event.getAmount() * 1.10f);
                    LivingEntity target = event.getEntity();
                    if (!target.isOnFire()) {
                        target.setSecondsOnFire(5);
                    }
                    if (target.isOnFire()) {
                        event.setAmount(event.getAmount() * 1.05f);
                    }

                }
            });
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("§6Increases arrow damage by §310.0%"));
        pTooltipComponents.add(Component.literal("§6Lights attacked entites on fire"));
        pTooltipComponents.add(Component.literal("§6Deals §35.0% §6increased damage to ignited entites"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}








