package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.Config;
import net.gobies.moreartifacts.item.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;

import java.util.HashMap;
import java.util.Map;

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
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;

public class SpectreAmuletItem extends Item implements ICurioItem {
    public SpectreAmuletItem(Properties properties) {
        super(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
    }
    private static final Map<Player, Long> cooldownMap = new HashMap<>();

    static {
        MinecraftForge.EVENT_BUS.register(SpectreAmuletItem.class);
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player attacker) {
            CuriosApi.getCuriosHelper().findEquippedCurio(ModItems.SpectreAmulet.get(), attacker).ifPresent((slot) -> {
                if (event.getEntity() instanceof LivingEntity && !event.getEntity().isDeadOrDying()) {
                    RandomSource random = attacker.getRandom();
                    if (random.nextFloat() < Config.SPECTRE_AMULET_HEAL_CHANCE.get()) {
                        long currentTime = System.currentTimeMillis();
                        long lastHealTime = cooldownMap.getOrDefault(attacker, 0L);

                        if (currentTime - lastHealTime >= 1000) {
                            attacker.heal(Config.SPECTRE_AMULET_HEAL_AMOUNT.get().floatValue());
                            cooldownMap.put(attacker, currentTime);
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
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("§7Grants a chance to heal the player upon attacking entities"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}









