package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.Config;
import net.gobies.moreartifacts.item.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
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
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NecroplasmAmuletItem extends Item implements ICurioItem {
    public NecroplasmAmuletItem(Properties properties) {
        super(new Properties().stacksTo(1).rarity(Rarity.RARE));
    }
    private static final Map<Player, Long> cooldownMap = new HashMap<>();

    static {
        MinecraftForge.EVENT_BUS.register(NecroplasmAmuletItem.class);
    }
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player attacker) {
            CuriosApi.getCuriosHelper().findEquippedCurio(ModItems.NecroplasmAmulet.get(), attacker).ifPresent((slot) -> {
                if (event.getEntity() instanceof LivingEntity && !event.getEntity().isDeadOrDying()) {
                    RandomSource random = attacker.getRandom();
                    if (random.nextFloat() < Config.NECROPLASM_HEAL_CHANCE.get()) {
                        long currentTime = System.currentTimeMillis();
                        long lastHealTime = cooldownMap.getOrDefault(attacker, 0L);

                        if (currentTime - lastHealTime >= 1000) {
                            attacker.heal(1.0f);
                            cooldownMap.put(attacker, currentTime);
                        }
                    }
                }
            });
        }
    }
    @SubscribeEvent
    public static void onPlayerAttacked(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player player) {
            CuriosApi.getCuriosHelper().findEquippedCurio(ModItems.NecroplasmAmulet.get(), player).ifPresent(slot -> {
                if (event.getSource().getEntity() instanceof LivingEntity attacker) {
                    if (player.getRandom().nextFloat() < Config.NECROPLASM_POISON_CHANCE.get()) {
                        attacker.addEffect(new net.minecraft.world.effect.MobEffectInstance(MobEffects.POISON, 100, 0));
                        if (player.getRandom().nextFloat() < Config.NECROPLASM_WITHER_CHANCE.get()) {
                            attacker.addEffect(new net.minecraft.world.effect.MobEffectInstance(MobEffects.WITHER, 100, 0));
                        }
                    }
                }
            });
        }
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("§6Grants a chance to heal the player upon attacking entities"));
        pTooltipComponents.add(Component.literal("§6Has a chance to poison and wither attackers"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}








