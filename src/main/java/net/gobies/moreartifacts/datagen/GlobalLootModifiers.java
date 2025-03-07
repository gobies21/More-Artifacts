package net.gobies.moreartifacts.datagen;

import net.gobies.moreartifacts.MoreArtifacts;
import net.gobies.moreartifacts.item.ModItems;
import net.gobies.moreartifacts.loot.AddItemModifier;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

public class GlobalLootModifiers extends GlobalLootModifierProvider {
    public GlobalLootModifiers(PackOutput output) {
        super(output, MoreArtifacts.MOD_ID);
    }

    @Override
    protected void start() {
        add("cave_spider_bezoar", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/cave_spider")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()}, ModItems.Bezoar.get()));
        add("elder_guardian_vitamins", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/elder_guardian")).build(),
                LootItemRandomChanceCondition.randomChance(0.50f).build()}, ModItems.Vitamins.get()));
        add("stray_fast_clock", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/stray")).build(),
                LootItemRandomChanceCondition.randomChance(0.04f).build()}, ModItems.FastClock.get()));
        add("husk_desert_charm", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/husk")).build(),
                LootItemRandomChanceCondition.randomChance(0.04f).build()}, ModItems.DesertCharm.get()));
        add("wither_wither_shard", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/wither")).build(),
                LootItemRandomChanceCondition.randomChance(0.50f).build()}, ModItems.WitherShard.get()));
        add("ancient_city_sculk_lens", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/ancient_city")).build(),
                LootItemRandomChanceCondition.randomChance(0.10f).build()}, ModItems.SculkLens.get()));
        add("cat_gift_melody_plushie", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("gameplay/cat_morning_gift")).build(),
                LootItemRandomChanceCondition.randomChance(0.8f).build()}, ModItems.MelodyPlushie.get()));
        add("dungeon_cobalt_shield", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/simple_dungeon")).build(),
                LootItemRandomChanceCondition.randomChance(0.20f).build()}, ModItems.CobaltShield.get()));
        add("shulker_shulker_heart", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/shulker")).build(),
                LootItemRandomChanceCondition.randomChance(0.20f).build()}, ModItems.ShulkerHeart.get()));
        add("enderman_enderian_scarf", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/enderman")).build(),
                LootItemRandomChanceCondition.randomChance(0.02f).build()}, ModItems.EnderianScarf.get()));
        add("zombie_shackle", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/zombie")).build(),
                LootItemRandomChanceCondition.randomChance(0.01f).build()}, ModItems.Shackle.get()));
        add("skeleton_magic_quiver", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/skeleton")).build(),
                LootItemRandomChanceCondition.randomChance(0.01f).build()}, ModItems.MagicQuiver.get()));
        add("pillager_magic_quiver", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/pillager")).build(),
                LootItemRandomChanceCondition.randomChance(0.04f).build()}, ModItems.MagicQuiver.get()));
    }
}

