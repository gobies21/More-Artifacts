package net.gobies.moreartifacts.compat.coldsweat;

import com.momosoftworks.coldsweat.api.event.common.temperautre.TempModifierEvent;
import net.gobies.moreartifacts.helper.SoulType;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.item.artifacts.DragonEyeItem;
import net.gobies.moreartifacts.util.CurioHandler;
import net.gobies.moreartifacts.util.SoulUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ColdSweatCompat {

    public static void loadCompat() {
        MinecraftForge.EVENT_BUS.register(new ColdSweatCompat());
    }

    @SubscribeEvent
    public void onTempModifierCalculate(TempModifierEvent.Calculate.Post event) {
        if (!(event.getEntity() instanceof Player player)) return;
        boolean coldImmune = SoulUtil.hasSoul(player, SoulType.ENVY);
        boolean heatImmune = SoulUtil.hasSoul(player, SoulType.WRATH);

        ItemStack stack = CurioHandler.getEquippedCurio(player, MAItems.DragonEye.get());
        if (!stack.isEmpty()) {
            if (DragonEyeItem.isEnderDragonEye(stack) || DragonEyeItem.isIceDragonEye(stack)) {
                coldImmune = true;
            }

            if (DragonEyeItem.isFireDragonEye(stack)) {
                heatImmune = true;
            }
        }

        double temperature = event.getTemperature();
        if (coldImmune && temperature < 0.0) {
            event.setTemperature(0.0);
        }

        if (heatImmune && temperature > 0.0) {
            event.setTemperature(0.0);
        }
    }
}