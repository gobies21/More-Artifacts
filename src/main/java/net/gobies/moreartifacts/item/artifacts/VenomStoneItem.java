package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.Config;
import net.gobies.moreartifacts.util.CurioHandler;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.util.DamageManager;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
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
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import javax.annotation.Nullable;
import java.util.List;

public class VenomStoneItem extends Item implements ICurioItem {
    public VenomStoneItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.RARE));
    }

    static {
        MinecraftForge.EVENT_BUS.register(VenomStoneItem.class);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            if (player.hasEffect(MobEffects.POISON)) {
                player.removeEffect(MobEffects.POISON);
            }
        }
    }

    @SubscribeEvent
    public static void onMobEffectApplicable(MobEffectEvent.Applicable event) {
        if (event.getEntity() instanceof Player player) {
            event.getEffectInstance();
            if (event.getEffectInstance().getEffect() == MobEffects.POISON) {
                if (CurioHandler.isCurioEquipped(player, MAItems.VenomStone.get())) {
                    event.setResult(MobEffectEvent.Result.DENY);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player attacker) {
            if (CurioHandler.isCurioEquipped(attacker, MAItems.VenomStone.get())) {
                RandomSource random = attacker.getRandom();
                LivingEntity target = event.getEntity();
                if (random.nextFloat() < Config.VENOM_STONE_CHANCE.get()) {
                    target.addEffect(new MobEffectInstance(MobEffects.POISON, 20 * Config.VENOM_STONE_DURATION.get(), Config.VENOM_STONE_LEVEL.get() - 1));
                }
                if (target.hasEffect(MobEffects.POISON)) {
                    DamageManager.updateDamageIncrease(attacker, target, event);
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
        double poisonChance = Config.VENOM_STONE_CHANCE.get() * 100;
        int poisonLevel = Config.VENOM_STONE_LEVEL.get();
        int poisonDuration = Config.VENOM_STONE_DURATION.get();
        double increasedDamage = (Config.VENOM_STONE_DAMAGE.get() - 1) * 100;
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.venom_stone.immunity").withStyle(ChatFormatting.GREEN));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.venom_stone.inflict_poison", String.format("%.1f", poisonChance), poisonLevel, poisonDuration).withStyle(ChatFormatting.DARK_AQUA));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.venom_stone.increased_damage", String.format("%.1f",  increasedDamage)).withStyle(ChatFormatting.DARK_AQUA));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}










