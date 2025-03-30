package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.Config;
import net.gobies.moreartifacts.item.ModItems;
import net.minecraft.network.chat.Component;
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
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;

public class EnvenomedQuiverItem extends Item implements ICurioItem {
    public EnvenomedQuiverItem(Properties properties) {
        super(new Properties().stacksTo(1).rarity(Rarity.RARE));
    }

    static {
        MinecraftForge.EVENT_BUS.register(EnvenomedQuiverItem.class);
    }
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player attacker) {
            CuriosApi.getCuriosHelper().findEquippedCurio(ModItems.EnvenomedQuiver.get(), attacker).ifPresent((slot) -> {
                if (event.getSource().is(DamageTypes.ARROW)) {
                    event.setAmount((float) (event.getAmount() * Config.ENVENOMED_QUIVER_DAMAGE.get()));
                    LivingEntity target = event.getEntity();
                    target.addEffect(new net.minecraft.world.effect.MobEffectInstance(MobEffects.POISON, 20 * Config.ENVENOMED_QUIVER_POISON_DURATION.get(), Config.ENVENOMED_QUIVER_POISON_LEVEL.get() - 1));
                    target.addEffect(new net.minecraft.world.effect.MobEffectInstance(MobEffects.WITHER, 20 * Config.ENVENOMED_QUIVER_WITHER_DURATION.get(), Config.ENVENOMED_QUIVER_WITHER_LEVEL.get() - 1));

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
        pTooltipComponents.add(Component.literal(String.format("§6Increases arrow damage by §3%.1f%%", (Config.ENVENOMED_QUIVER_DAMAGE.get() - 1) * 100)));
        pTooltipComponents.add(Component.literal("§6Arrows inflict poison and wither to enemies"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}








