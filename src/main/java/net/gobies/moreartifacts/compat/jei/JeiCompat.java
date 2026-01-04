package net.gobies.moreartifacts.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.gobies.moreartifacts.init.MAItems;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class JeiCompat implements IModPlugin {
    private final ResourceLocation PLUGIN_UID = new ResourceLocation("moreartifacts", "jei_plugin");

    public @NotNull ResourceLocation getPluginUid() {
        return PLUGIN_UID;
    }

    @Override
    public void registerItemSubtypes(@NotNull ISubtypeRegistration registration) {
        registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, MAItems.DragonEye.get(), DragonEyeSubTypeInterpreter.INSTANCE);
    }
}