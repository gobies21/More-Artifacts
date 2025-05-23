package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.Config;
import net.gobies.moreartifacts.init.MACurioHandler;
import net.gobies.moreartifacts.item.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
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

public class EnderDragonClawItem extends Item implements ICurioItem {
    public EnderDragonClawItem(Properties properties) {
        super(new Properties().stacksTo(1).rarity(Rarity.EPIC));
    }

        static {
        MinecraftForge.EVENT_BUS.register(EnderDragonClawItem.class);
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player attacker) {
            if (MACurioHandler.isCurioEquipped(attacker, ModItems.EnderDragonClaw.get())) {
                RandomSource random = attacker.getRandom();
                if (random.nextFloat() < Config.ENDER_DRAGON_CLAW_CHANCE.get()) {
                    event.setAmount((float) (event.getAmount() * Config.ENDER_DRAGON_CLAW_DAMAGE.get()));
                    attacker.level().playSound(null, attacker.blockPosition(), SoundEvents.ENDER_DRAGON_HURT, SoundSource.PLAYERS, 0.6f, 1.4f);
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
        pTooltipComponents.add(Component.literal(String.format("§3%.1f%% §6Chance to deal §3%.1f%% §6increased damage on hit", (Config.ENDER_DRAGON_CLAW_CHANCE.get() * 100), (Config.ENDER_DRAGON_CLAW_DAMAGE.get() - 1) * 100)));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}










