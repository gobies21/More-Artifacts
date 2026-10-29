package net.gobies.moreartifacts.init;

import net.gobies.moreartifacts.MoreArtifacts;
import net.gobies.moreartifacts.effect.Sinful;
import net.gobies.moreartifacts.effect.Virulent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MAEffects {
    public static final DeferredRegister<MobEffect> EFFECTS;
    public static final RegistryObject<MobEffect> Virulent;
    public static final RegistryObject<MobEffect> Sinful;

    public static void register (IEventBus eventBus) {
        EFFECTS.register(eventBus);
    }

    static {
        EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MoreArtifacts.MOD_ID);
        Virulent = EFFECTS.register("virulent", () -> new Virulent(MobEffectCategory.HARMFUL, 0xBF40BF));
        Sinful = EFFECTS.register("sinful", () -> new Sinful(MobEffectCategory.BENEFICIAL, 0x8A0303));
    }
}
