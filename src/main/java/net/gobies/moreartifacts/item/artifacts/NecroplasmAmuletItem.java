package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.Config;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.util.CurioHandler;
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
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NecroplasmAmuletItem extends Item implements ICurioItem {
    public NecroplasmAmuletItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.RARE));
    }

    static {
        MinecraftForge.EVENT_BUS.register(NecroplasmAmuletItem.class);
    }

    private static final Map<Player, Long> cooldownMap = new HashMap<>();

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player attacker) {
            if (CurioHandler.isCurioEquipped(attacker, MAItems.NecroplasmAmulet.get())) {
                if (event.getEntity() instanceof LivingEntity && !event.getEntity().isDeadOrDying()) {
                    RandomSource random = attacker.getRandom();
                    if (random.nextFloat() < Config.NECROPLASM_AMULET_HEAL_CHANCE.get()) {
                        long currentTime = System.currentTimeMillis();
                        long lastHealTime = cooldownMap.getOrDefault(attacker, 0L);

                        if (currentTime - lastHealTime >= 1000) {
                            attacker.heal(Config.NECROPLASM_AMULET_HEAL_AMOUNT.get().floatValue());
                            cooldownMap.put(attacker, currentTime);
                        }
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public static void onPlayerAttacked(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (CurioHandler.isCurioEquipped(player, MAItems.NecroplasmAmulet.get())) {
                if (event.getSource().getEntity() instanceof LivingEntity attacker) {
                    if (player.getRandom().nextFloat() < Config.NECROPLASM_AMULET_POISON_CHANCE.get()) {
                        attacker.addEffect(new MobEffectInstance(MobEffects.POISON, 20 * Config.NECROPLASM_AMULET_POISON_DURATION.get(), Config.NECROPLASM_AMULET_POISON_LEVEL.get() - 1));
                        if (player.getRandom().nextFloat() < Config.NECROPLASM_AMULET_WITHER_CHANCE.get()) {
                            attacker.addEffect(new MobEffectInstance(MobEffects.WITHER, 20 * Config.NECROPLASM_AMULET_WITHER_DURATION.get(), Config.NECROPLASM_AMULET_WITHER_LEVEL.get() - 1));
                        }
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
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.necroplasm_amulet.heal").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.translatable("tooltip.moreartifacts.necroplasm_amulet.effect").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}








