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
    public static final RegistryObject<SoundEvent> STORM_SHOCK;
    public static final RegistryObject<SoundEvent> MANA_SHIELD_HURT;
    public static final RegistryObject<SoundEvent> MANA_SHIELD_BREAK;
    public static final RegistryObject<SoundEvent> MANA_SHIELD_ENABLED;
    public static final RegistryObject<SoundEvent> MANA_SHIELD_DISABLED;
    public static final RegistryObject<SoundEvent> ANNIHILATION_HIT;
    public static final RegistryObject<SoundEvent> ANNIHILATION_STACK;

    public static void register(IEventBus eventBus) {
        REGISTRY.register(eventBus);
    }

    static {
        REGISTRY = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MoreArtifacts.MOD_ID);
        CURSED = REGISTRY.register("cursed", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MoreArtifacts.MOD_ID, "cursed")));
        STORM_SHOCK = REGISTRY.register("storm_shock", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MoreArtifacts.MOD_ID, "storm_shock")));
        MANA_SHIELD_HURT = REGISTRY.register("mana_shield_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MoreArtifacts.MOD_ID, "mana_shield_hurt")));
        MANA_SHIELD_BREAK = REGISTRY.register("mana_shield_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MoreArtifacts.MOD_ID, "mana_shield_break")));
        MANA_SHIELD_ENABLED = REGISTRY.register("mana_shield_enabled", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MoreArtifacts.MOD_ID, "mana_shield_enabled")));
        MANA_SHIELD_DISABLED = REGISTRY.register("mana_shield_disabled", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MoreArtifacts.MOD_ID, "mana_shield_disabled")));
        ANNIHILATION_HIT = REGISTRY.register("annihilation_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MoreArtifacts.MOD_ID, "annihilation_hit")));
        ANNIHILATION_STACK = REGISTRY.register("annihilation_stack", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MoreArtifacts.MOD_ID, "annihilation_stack")));
    }
}