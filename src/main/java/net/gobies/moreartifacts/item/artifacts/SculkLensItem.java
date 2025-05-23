package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.init.MACurioHandler;
import net.gobies.moreartifacts.item.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;

public class SculkLensItem extends Item implements ICurioItem {
    public SculkLensItem(Properties properties) {
        super(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
        MinecraftForge.EVENT_BUS.register(this);
    }
    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            if (player.hasEffect(MobEffects.DARKNESS)) {
                player.removeEffect(MobEffects.DARKNESS);
            }
        }
    }
    @SubscribeEvent
    public void onMobEffectApplicable(MobEffectEvent.Applicable event) {
        if (event.getEntity() instanceof Player player) {
            event.getEffectInstance();
            if (event.getEffectInstance().getEffect() == MobEffects.DARKNESS) {
                if (MACurioHandler.isCurioEquipped(player, ModItems.SculkLens.get())) {
                    event.setResult(MobEffectEvent.Result.DENY);
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
        pTooltipComponents.add(Component.literal("§7Grants immunity to Darkness"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}








