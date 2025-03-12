package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.item.ModItems;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import java.util.List;
import net.minecraft.network.chat.Component;
import javax.annotation.Nullable;

public class VitaminsItem extends Item implements ICurioItem {
    public VitaminsItem(Properties properties) {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON));
        MinecraftForge.EVENT_BUS.register(this);
    }
    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        if (livingEntity instanceof Player player) {
            if (player.hasEffect(MobEffects.WEAKNESS));
            player.removeEffect(MobEffects.WEAKNESS);
            if (player.hasEffect(MobEffects.DIG_SLOWDOWN));
            player.removeEffect(MobEffects.DIG_SLOWDOWN);
        }
    }
    @SubscribeEvent
    public void onMobEffectApplicable(MobEffectEvent.Applicable event) {
        if (event.getEntity() instanceof Player player) {
            if (event.getEffectInstance() != null && event.getEffectInstance().getEffect() == MobEffects.WEAKNESS ||
                    event.getEffectInstance().getEffect() == MobEffects.DIG_SLOWDOWN) {
                CuriosApi.getCuriosHelper().findEquippedCurio(ModItems.Vitamins.get(), player).ifPresent((slot) -> {
                    event.setResult(MobEffectEvent.Result.DENY);
                });
            }
        }
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("§7Grants immunity to Weakness and Mining Fatigue"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
