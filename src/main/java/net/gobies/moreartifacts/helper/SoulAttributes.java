package net.gobies.moreartifacts.helper;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.gobies.moreartifacts.MoreArtifacts;
import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.util.SoulUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SoulAttributes {

    private static final String ENVY = "envy_holder:";
    private static final String WRATH = "wrath_holder";

    public record AttributeHolder(Attribute attribute, String attributeId, UUID uuid, double base, double increment, AttributeModifier.Operation operation) {}

    public static List<AttributeHolder> ENVY_ATTRIBUTES = new ArrayList<>();
    public static List<AttributeHolder> WRATH_ATTRIBUTES = new ArrayList<>();

    public static void bakeConfig() {
        ENVY_ATTRIBUTES = parse(CommonConfig.ENVY_ATTRIBUTES.get(), ENVY);
        WRATH_ATTRIBUTES = parse(CommonConfig.WRATH_ATTRIBUTES.get(), WRATH);
    }

    private static List<AttributeHolder> parse(List<? extends String> entries, String prefix) {
        List<AttributeHolder> holderList = new ArrayList<>();
        if (entries == null) return holderList;

        for (String entry : entries) {
            String[] tokens = entry.split(";");
            if (tokens.length < 4) continue;

            String attributeId = tokens[0].trim();
            ResourceLocation location = ResourceLocation.tryParse(attributeId);
            if (location == null) continue;

            Attribute attribute = ForgeRegistries.ATTRIBUTES.getValue(location);
            if (attribute == null) continue;

            try {
                double base = Double.parseDouble(tokens[1].trim());
                double increment = Double.parseDouble(tokens[2].trim());
                AttributeModifier.Operation operation = AttributeModifier.Operation.valueOf(tokens[3].trim().toUpperCase());

                UUID uuid = UUID.nameUUIDFromBytes((prefix + attributeId).getBytes(StandardCharsets.UTF_8));

                holderList.add(new AttributeHolder(attribute, attributeId, uuid, base, increment, operation));
            } catch (IllegalArgumentException e) {
                MoreArtifacts.LOGGER.error("Failed to parse soul config attributes");
            }
        }
        return holderList;
    }

    private static void processConfig(List<AttributeHolder> attributeList, AttributeProcessor processor) {
        if (attributeList == null) return;

        for (AttributeHolder holder : attributeList) {
            double value = holder.base + (4.0 * holder.increment);

            processor.accept(holder.attribute, holder.uuid, holder.attributeId, value, holder.operation);
        }
    }

    private static void removeConfigAttributes(Player player, List<AttributeHolder> attributeList) {
        if (attributeList == null) return;
        for (AttributeHolder holder : attributeList) {
            AttributeInstance instance = player.getAttribute(holder.attribute);
            if (instance != null) {
                instance.removeModifier(holder.uuid);
            }
        }
    }

    public static Multimap<Attribute, AttributeModifier> getCurioModifiers(UUID uuid, ItemStack stack, List<AttributeHolder> attributeHolder, String name) {
        Multimap<Attribute, AttributeModifier> modifiers = LinkedHashMultimap.create();
        if (attributeHolder == null || attributeHolder.isEmpty()) return modifiers;

        int soulStage = SoulUtil.getSoulStage(stack);
        double factor = (soulStage - 1);
        String uuidString = uuid.toString();

        for (AttributeHolder holder : attributeHolder) {
            double finalValue = holder.base + (factor * holder.increment);

            UUID curioUuid = UUID.nameUUIDFromBytes((name + uuidString + ":" + holder.attributeId).getBytes(StandardCharsets.UTF_8));

            modifiers.put(holder.attribute, new AttributeModifier(curioUuid, "Curio Soul Bonus: " + holder.attributeId, finalValue, holder.operation));
        }

        return modifiers;
    }

    public static void manageEnvyAttributes(Player player) {
        processConfig(ENVY_ATTRIBUTES, (attribute, uuid, name, value, operation) -> {
            AttributeInstance instance = player.getAttribute(attribute);
            if (instance != null) {
                AttributeModifier existing = instance.getModifier(uuid);
                if (existing == null || existing.getAmount() != value || existing.getOperation() != operation) {
                    instance.removeModifier(uuid);
                    instance.addPermanentModifier(new AttributeModifier(uuid, "Envy: " + name, value, operation));
                }
            }
        });
    }

    public static void manageWrathAttributes(Player player) {
        processConfig(WRATH_ATTRIBUTES, (attribute, uuid, name, value, operation) -> {
            AttributeInstance instance = player.getAttribute(attribute);
            if (instance != null) {
                AttributeModifier existing = instance.getModifier(uuid);
                if (existing == null || existing.getAmount() != value || existing.getOperation() != operation) {
                    instance.removeModifier(uuid);
                    instance.addPermanentModifier(new AttributeModifier(uuid, "Wrath: " + name, value, operation));
                }
            }
        });
    }

    public static void removeEnvyAttributes(ServerPlayer serverPlayer) {
        removeConfigAttributes(serverPlayer, ENVY_ATTRIBUTES);

        if (serverPlayer.hasEffect(MobEffects.INVISIBILITY)) {
            serverPlayer.removeEffect(MobEffects.INVISIBILITY);
        }
    }

    public static void removeWrathAttributes(ServerPlayer serverPlayer) {
        removeConfigAttributes(serverPlayer, WRATH_ATTRIBUTES);

        if (serverPlayer.hasEffect(MobEffects.DAMAGE_BOOST)) {
            serverPlayer.removeEffect(MobEffects.DAMAGE_BOOST);
        }
    }

    @FunctionalInterface
    private interface AttributeProcessor {
        void accept(Attribute attribute, UUID uuid, String name, double value, AttributeModifier.Operation operation);
    }
}