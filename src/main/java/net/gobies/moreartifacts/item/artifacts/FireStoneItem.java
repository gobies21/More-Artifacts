package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.Config;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.util.CurioHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageTypes;
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
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import javax.annotation.Nullable;
import java.util.List;

public class FireStoneItem extends Item implements ICurioItem {
    public FireStoneItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.RARE).fireResistant());
    }

    static {
        MinecraftForge.EVENT_BUS.register(FireStoneItem.class);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            if (player.isOnFire()) {
                player.extinguishFire();
            }
        }
    }

    @SubscribeEvent
    public static void onEntityAttacked(LivingAttackEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (CurioHandler.isCurioEquipped(player, MAItems.FireStone.get())) {
                if (event.getSource().is(DamageTypes.ON_FIRE) || (event.getSource().is(DamageTypes.IN_FIRE) || (event.getSource().is(DamageTypes.HOT_FLOOR)))) {
                    event.setCanceled(true);

                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player attacker) {
            if (CurioHandler.isCurioEquipped(attacker, MAItems.FireStone.get())) {
                RandomSource random = attacker.getRandom();
                LivingEntity target = event.getEntity();
                if (random.nextFloat() < Config.FIRE_STONE_CHANCE.get()) {
                    target.setSecondsOnFire(Config.FIRE_STONE_DURATION.get());
                }
                if (target.isOnFire()) {
                    event.setAmount((float) (event.getAmount() * Config.FIRE_STONE_DAMAGE.get()));
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
        pTooltipComponents.add(Component.literal("§cGrants immunity to Fire"));
        pTooltipComponents.add(Component.literal(String.format("§3%.1f%% §cChance to set enemies ablaze for §3%d §cseconds", (Config.FIRE_STONE_CHANCE.get() * 100), (Config.FIRE_STONE_DURATION.get()))));
        pTooltipComponents.add(Component.literal(String.format("§cDeal §3%.1f%% §cincreased damage to targets on fire", (Config.FIRE_STONE_DAMAGE.get() - 1) * 100)));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}








