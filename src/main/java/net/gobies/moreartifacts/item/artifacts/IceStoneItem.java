package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.Config;
import net.gobies.moreartifacts.item.ModItems;
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
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;

public class IceStoneItem extends Item implements ICurioItem {
    public IceStoneItem(Properties properties) {
        super(new Properties().stacksTo(1).rarity(Rarity.RARE));
    }

        static {
        MinecraftForge.EVENT_BUS.register(IceStoneItem.class);
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        if (livingEntity instanceof Player player) {
            if (player.isFreezing()) {
                player.setTicksFrozen(-1);
            }
        }
    }

    @SubscribeEvent
    public static void onEntityAttacked(LivingAttackEvent event) {
        if (event.getEntity() instanceof Player player) {
            CuriosApi.getCuriosHelper().findEquippedCurio(ModItems.IceStone.get(), player).ifPresent((slot) -> {
                if (event.getSource().is(DamageTypes.FREEZE)) {
                    event.setCanceled(true);

                }
            });
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player attacker) {
            CuriosApi.getCuriosHelper().findEquippedCurio(ModItems.IceStone.get(), attacker).ifPresent((slot) -> {
                RandomSource random = attacker.getRandom();
                LivingEntity target = event.getEntity();
                if (random.nextFloat() < Config.ICE_STONE_CHANCE.get()) {
                    target.setTicksFrozen(100 * Config.ICE_STONE_DURATION.get());
                    target.addEffect(new net.minecraft.world.effect.MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * Config.ICE_STONE_DURATION.get(), 0));
                }
                if (target.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
                    event.setAmount((float) (event.getAmount() * Config.ICE_STONE_DAMAGE.get()));
                }

            });
        }
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("§bGrants immunity to Freezing"));
        pTooltipComponents.add(Component.literal(String.format("§3%.1f%% §bChance to freeze enemies for §3%d §bseconds", (Config.ICE_STONE_CHANCE.get() * 100), (Config.ICE_STONE_DURATION.get()))));
        pTooltipComponents.add(Component.literal(String.format("§bDeal §3%.1f%% §bincreased damage to freezing targets", (Config.ICE_STONE_DAMAGE.get() - 1) * 100)));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}








