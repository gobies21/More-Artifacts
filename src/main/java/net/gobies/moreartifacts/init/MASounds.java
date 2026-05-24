package net.gobies.moreartifacts.init;

import net.gobies.moreartifacts.MoreArtifacts;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MASounds {


    public static final DeferredRegister<SoundEvent> REGISTRY;
    public static final RegistryObject<SoundEvent> CURSED;

    public static void register(IEventBus eventBus) {
        REGISTRY.register(eventBus);
    }

    static {
        REGISTRY = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MoreArtifacts.MOD_ID);
        CURSED = REGISTRY.register("cursed", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("moreartifacts", "cursed")));
    }
}