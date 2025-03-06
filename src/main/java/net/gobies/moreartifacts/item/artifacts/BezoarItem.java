package net.gobies.moreartifacts.item.artifacts;

import net.gobies.moreartifacts.item.ModItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffects;

public class BezoarItem extends Item implements ICurioItem {
    public BezoarItem(Properties properties) {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON));
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("§7Grants immunity to Poison"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    @SubscribeEvent
    public static void BezoarPoisonImmunity(TickEvent.PlayerTickEvent event) {
            if (event.player instanceof Player) {
                if (event.phase == TickEvent.Phase.START) {
                    Player player = event.player;
                    if (player != null) {
                        Optional<SlotResult> stack0 = CuriosApi.getCuriosHelper().findFirstCurio(player, (Item) ModItems.Bezoar.get());
                        if (stack0.isPresent() && player.hasEffect(MobEffects.POISON)) {
                            player.hasEffect(MobEffects.POISON);
                        }
                    }
                }

            }
        }
    }