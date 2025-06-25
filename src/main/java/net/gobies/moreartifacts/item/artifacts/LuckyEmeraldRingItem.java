package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.Config;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.util.CurioHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;

public class LuckyEmeraldRingItem extends Item implements ICurioItem {
    public LuckyEmeraldRingItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.EPIC));
    }

    static {
        MinecraftForge.EVENT_BUS.register(LuckyEmeraldRingItem.class);
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player player && event.getEntity() != null) {
            LivingEntity target = event.getEntity();
            if (target.getMobType() == MobType.ILLAGER) {
                if (CurioHandler.isCurioEquipped(player, MAItems.LuckyEmeraldRing.get())) {
                    event.setAmount((float) (event.getAmount() * Config.EMERALD_RING_DAMAGE.get()));
                }
            }
            if (target instanceof LivingEntity) {
                if (CurioHandler.isCurioEquipped(player, MAItems.LuckyEmeraldRing.get())) {
                    if (player.level().random.nextFloat() < Config.EMERALD_RING_EMERALDS.get()) {
                        target.spawnAtLocation(Items.EMERALD, 1);
                        player.level().playSound(null, player.blockPosition(), SoundEvents.AMETHYST_BLOCK_BREAK, SoundSource.PLAYERS, 0.6f, 1.8f);
                    }
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
        double emeraldChance = Config.EMERALD_RING_EMERALDS.get() * 100;
        double damageIncrease = (Config.EMERALD_RING_DAMAGE.get() - 1) * 100;
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.lucky_emerald_ring.chance", String.format("%.1f", emeraldChance)).withStyle(ChatFormatting.DARK_AQUA));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.lucky_emerald_ring.damage", String.format("%.1f", damageIncrease)).withStyle(ChatFormatting.DARK_AQUA));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}










