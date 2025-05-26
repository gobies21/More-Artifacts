package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.Config;
import net.gobies.moreartifacts.item.MAItems;
import net.gobies.moreartifacts.util.CurioHandler;
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
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import javax.annotation.Nullable;
import java.util.List;

public class DecayStoneItem extends Item implements ICurioItem {
    public DecayStoneItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.RARE));
    }

        static {
            MinecraftForge.EVENT_BUS.register(DecayStoneItem.class);
        }

        @Override
        public void curioTick(SlotContext slotContext, ItemStack stack) {
            if (slotContext.entity() instanceof Player player) {
                if (player.hasEffect(MobEffects.WITHER)) {
                    player.removeEffect(MobEffects.WITHER);
                }
            }
        }

        @SubscribeEvent
        public static void onMobEffectApplicable(MobEffectEvent.Applicable event) {
            if (event.getEntity() instanceof Player player) {
                event.getEffectInstance();
                if (event.getEffectInstance().getEffect() == MobEffects.WITHER) {
                    if (CurioHandler.isCurioEquipped(player, MAItems.DecayStone.get())) {
                        event.setResult(MobEffectEvent.Result.DENY);
                    }
                }
            }
        }

        @SubscribeEvent
        public static void onLivingHurt(LivingHurtEvent event) {
            if (event.getSource().getEntity() instanceof Player attacker) {
                if (CurioHandler.isCurioEquipped(attacker, MAItems.DecayStone.get())) {
                    RandomSource random = attacker.getRandom();
                    LivingEntity target = event.getEntity();
                    if (random.nextFloat() < Config.DECAY_STONE_CHANCE.get()) {
                        target.addEffect(new net.minecraft.world.effect.MobEffectInstance(MobEffects.WITHER, 20 * Config.DECAY_STONE_DURATION.get(), Config.DECAY_STONE_LEVEL.get() - 1));
                    }
                    if (target.hasEffect(MobEffects.WITHER)) {
                        event.setAmount((float) (event.getAmount() * Config.DECAY_STONE_DAMAGE.get()));
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
            pTooltipComponents.add(Component.literal("§6Grants immunity to Wither"));
            pTooltipComponents.add(Component.literal(String.format("§3%.1f%% §6Chance to inflict wither §3%d §6for §3%d §6seconds", (Config.DECAY_STONE_CHANCE.get() * 100), (Config.DECAY_STONE_LEVEL.get()), (Config.DECAY_STONE_DURATION.get()))));
            pTooltipComponents.add(Component.literal(String.format("§6Deal §3%.1f%% §6increased damage to targets inflicted with wither", (Config.DECAY_STONE_DAMAGE.get() - 1) * 100)));
            super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        }
    }








