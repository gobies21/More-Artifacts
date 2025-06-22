package net.gobies.moreartifacts.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithLootingCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.event.LootTableLoadEvent;

public class LootTableHandler {

    public static void addLootToEntity(LootTableLoadEvent event, String entity, Item item, int minCount, int maxCount, float baseDropChance, float lootingBoostChance) {
        ResourceLocation lootTable = event.getName();
        ResourceLocation entityLocation = new ResourceLocation(entity);

        if (lootTable.equals(entityLocation)) {
            LootPool.Builder poolBuilder = LootPool.lootPool()
                    .add(LootItem.lootTableItem(item)
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(minCount, maxCount)))
                            .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(baseDropChance, lootingBoostChance)));

            event.getTable().addPool(poolBuilder.build());
        }
    }
}