package net.gobies.moreartifacts.item.artifacts;

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

import static net.gobies.moreartifacts.init.MoreArtifactsCurioHandler.isCurioEquipped;

public class AnkhCharmItem extends Item implements ICurioItem {
    public AnkhCharmItem(Properties properties) {
        super(new Properties().stacksTo(1).rarity(Rarity.EPIC));
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            player.removeEffect(MobEffects.POISON);
            player.removeEffect(MobEffects.WITHER);
            player.removeEffect(MobEffects.HUNGER);
            player.removeEffect(MobEffects.CONFUSION);
            player.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
            player.removeEffect(MobEffects.LEVITATION);
            player.removeEffect(MobEffects.DIG_SLOWDOWN);
            player.removeEffect(MobEffects.WEAKNESS);
            player.removeEffect(MobEffects.BLINDNESS);
            player.removeEffect(MobEffects.DARKNESS);
        }
    }


    @SubscribeEvent
    public void onMobEffectApplicable(MobEffectEvent.Applicable event) {
        if (event.getEntity() instanceof Player player) {
            event.getEffectInstance();
            if (isCurioEquipped(player, ModItems.AnkhCharm.get())) {
                event.setResult(MobEffectEvent.Result.DENY);
            }
        }
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("§6Grants immunity to most debuffs"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}



