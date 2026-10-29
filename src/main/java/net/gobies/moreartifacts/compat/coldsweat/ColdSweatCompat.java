package net.gobies.moreartifacts.compat.coldsweat;

import com.momosoftworks.coldsweat.api.event.common.temperautre.TemperatureChangedEvent;
import com.momosoftworks.coldsweat.config.ConfigSettings;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.item.artifacts.DragonEyeItem;
import net.gobies.moreartifacts.util.CurioHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ColdSweatCompat {

    public static void loadCompat() {
        MinecraftForge.EVENT_BUS.register(new ColdSweatCompat());
    }

    @SubscribeEvent
    public void onTempChange(TemperatureChangedEvent event) {
        if (event.getEntity() instanceof Player player) {
            ItemStack stack = CurioHandler.getEquippedCurio(player, MAItems.DragonEye.get());
            float minTemp = ConfigSettings.MIN_TEMP.get().floatValue();
            float maxTemp = ConfigSettings.MAX_TEMP.get().floatValue();
            if (!stack.isEmpty()) {
                if (DragonEyeItem.isEnderDragonEye(stack) || DragonEyeItem.isIceDragonEye(stack)) {
                    if (event.getTemperature() < minTemp) {
                        event.setTemperature(minTemp);
                    }
                }
                if (DragonEyeItem.isFireDragonEye(stack)) {
                    if (event.getTemperature() < maxTemp) {
                        event.setTemperature(maxTemp);
                    }
                }
            }
        }
    }
}