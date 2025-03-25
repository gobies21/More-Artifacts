package net.gobies.moreartifacts.datagen;

import net.gobies.moreartifacts.MoreArtifacts;
import net.gobies.moreartifacts.item.ModItems;
import net.gobies.moreartifacts.loot.AddItemModifier;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithLootingCondition;
import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.common.loot.LootTableIdCondition;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class GlobalLootModifiers extends GlobalLootModifierProvider {
    public GlobalLootModifiers(PackOutput output) {
        super(output, MoreArtifacts.MOD_ID);
    }

//chances are broken
    //tripleing the lower ones for now (also tripleing some looting chances) these chances are to broken
    @Override
    protected void start() {
        add("cave_spider_bezoar", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/cave_spider")).build(),
                LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.15f, 0.01f).build()}, ModItems.Bezoar.get()));

        add("elder_guardian_vitamins", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/elder_guardian")).build(),
                LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.50f, 0.15f).build()}, ModItems.Vitamins.get()));

        add("stray_fast_clock", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/stray")).build(),
                LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.09f, 0.01f).build()}, ModItems.FastClock.get()));

        add("husk_desert_charm", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/husk")).build(),
                LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.09f, 0.01f).build()}, ModItems.DesertCharm.get()));

        add("wither_wither_shard", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/wither")).build(),
                LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.50f, 0.15f).build()}, ModItems.WitherShard.get()));

        add("shulker_shulker_heart", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/shulker")).build(),
                LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.45f, 0.01f).build()}, ModItems.ShulkerHeart.get()));

        add("enderman_enderian_scarf", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/enderman")).build(),
                LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.06f, 0.01f).build()}, ModItems.EnderianScarf.get()));

        add("zombie_shackle", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/zombie")).build(),
                LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.03f, 0.01f).build()}, ModItems.Shackle.get()));

        add("skeleton_magic_quiver", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/skeleton")).build(),
                LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.03f, 0.01f).build()}, ModItems.MagicQuiver.get()));

        add("pillager_magic_quiver", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/pillager")).build(),
                LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.12f, 0.01f).build()}, ModItems.MagicQuiver.get()));

        add("ancient_city_sculk_lens", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/ancient_city")).build(),
                LootItemRandomChanceCondition.randomChance(0.20f).build()}, ModItems.SculkLens.get()));

        add("cat_gift_melody_plushie", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("gameplay/cat_morning_gift")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()}, ModItems.MelodyPlushie.get()));

        add("dungeon_cobalt_shield", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/simple_dungeon")).build(),
                LootItemRandomChanceCondition.randomChance(0.20f).build()}, ModItems.CobaltShield.get()));

        add("ender_dragon_dragon_claw", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/ender_dragon")).build(),
                LootItemRandomChanceCondition.randomChance(0.75f).build()}, ModItems.EnderDragonClaw.get()));

    }
}
