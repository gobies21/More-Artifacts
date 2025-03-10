package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.Config;
import net.gobies.moreartifacts.item.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;

public class LuckyEmeraldRingItem extends Item implements ICurioItem {
    public LuckyEmeraldRingItem(Properties properties) {
        super(new Properties().stacksTo(1).rarity(Rarity.EPIC));
    }

    static {
        MinecraftForge.EVENT_BUS.register(LuckyEmeraldRingItem.class);
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player player && event.getEntity() != null) {
            LivingEntity target = event.getEntity();
            if (target.getMobType() == MobType.ILLAGER) {
                CuriosApi.getCuriosHelper().findEquippedCurio(ModItems.LuckyEmeraldRing.get(), player).ifPresent((slot) -> {
                    event.setAmount((float)(event.getAmount() * Config.EMERALD_RING_DAMAGE.get()));
                });
            }
        if (target instanceof LivingEntity) {
            CuriosApi.getCuriosHelper().findEquippedCurio(ModItems.LuckyEmeraldRing.get(), player).ifPresent((slot) -> {
            if (player.level().random.nextFloat() < Config.EMERALD_RING_EMERALDS.get()) {
                target.spawnAtLocation(net.minecraft.world.item.Items.EMERALD, 1);
            }
        });
    }
        }
    }
@Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal(String.format("§6Hitting enemies has a §3%.1f%% §6chance to drop emeralds", Config.EMERALD_RING_EMERALDS.get() * 100)));
        pTooltipComponents.add(Component.literal(String.format("§3%.1f%% §6Increased damage dealt against illagers", (Config.EMERALD_RING_DAMAGE.get() - 1) * 100)));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}










