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
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import javax.annotation.Nullable;
import java.util.List;
import static net.gobies.moreartifacts.item.ModItems.VenomStone;

public class VenomStoneItem extends Item implements ICurioItem {
    public VenomStoneItem(Properties properties) {
        super(new Properties().stacksTo(1).rarity(Rarity.RARE));
    }

        static {
        MinecraftForge.EVENT_BUS.register(VenomStoneItem.class);
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        if (livingEntity instanceof Player player) {
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
                    CuriosApi.getCuriosHelper().findEquippedCurio(ModItems.VenomStone.get(), player).ifPresent((slot) -> {
                        event.setResult(MobEffectEvent.Result.DENY);
                    });
                }
            }
        }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player attacker) {
            CuriosApi.getCuriosHelper().findEquippedCurio(VenomStone.get(), attacker).ifPresent((slot) -> {
                RandomSource random = attacker.getRandom();
                LivingEntity target = event.getEntity();
                if (random.nextFloat() < Config.VENOM_STONE_CHANCE.get()) {
                    target.addEffect(new net.minecraft.world.effect.MobEffectInstance(MobEffects.POISON, 20 *Config.VENOM_STONE_DURATION.get(), Config.VENOM_STONE_LEVEL.get()));
                }
                if (target.hasEffect(MobEffects.POISON)) {
                    event.setAmount((float) (event.getAmount() * Config.VENOM_STONE_DAMAGE.get()));
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
        pTooltipComponents.add(Component.literal("§aGrants immunity to Poison"));
        pTooltipComponents.add(Component.literal(String.format("§3%.1f%% §aChance to inflict poison §3%d §afor §3%d §aseconds", (Config.VENOM_STONE_CHANCE.get() * 100), (Config.VENOM_STONE_LEVEL.get() + 1), (Config.VENOM_STONE_DURATION.get()))));
        pTooltipComponents.add(Component.literal(String.format("§aDeal §3%.1f%% §aincreased damage to targets inflicted with poison", (Config.VENOM_STONE_DAMAGE.get() - 1) * 100)));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}










