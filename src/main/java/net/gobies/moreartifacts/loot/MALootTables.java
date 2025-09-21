package net.gobies.moreartifacts.loot;

import net.gobies.moreartifacts.MoreArtifacts;
import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.util.LootTableHandler;
import net.gobies.moreartifacts.util.MAUtils;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MoreArtifacts.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MALootTables {

    static float lootingValue = MAUtils.lootingValues();

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onLootTableLoad(LootTableLoadEvent event) {
        LootTableHandler.addLootWithLooting(event, "entities/cave_spider", MAItems.Bezoar.get(), 1, 1, CommonConfig.BEZOAR_DROP_CHANCE.get().floatValue(), lootingValue);
        LootTableHandler.addLootWithLooting(event, "entities/elder_guardian", MAItems.Vitamins.get(), 1, 1, CommonConfig.VITAMINS_DROP_CHANCE.get().floatValue(), lootingValue * 5);
        LootTableHandler.addLootWithLooting(event, "entities/stray", MAItems.FastClock.get(), 1, 1, CommonConfig.FAST_CLOCK_DROP_CHANCE.get().floatValue(), lootingValue);
        LootTableHandler.addLootWithLooting(event, "entities/husk", MAItems.DesertCharm.get(), 1, 1, CommonConfig.DESERT_CHARM_DROP_CHANCE.get().floatValue(), lootingValue);
        LootTableHandler.addLootWithLooting(event, "entities/wither", MAItems.WitherShard.get(), 1, 1, CommonConfig.WITHER_SHARD_DROP_CHANCE.get().floatValue(), lootingValue * 5);
        LootTableHandler.addLootWithLooting(event, "entities/shulker", MAItems.ShulkerHeart.get(), 1, 1, CommonConfig.SHULKER_HEART_DROP_CHANCE.get().floatValue(), lootingValue);
        LootTableHandler.addLootWithLooting(event, "entities/enderman", MAItems.EnderianScarf.get(), 1, 1, CommonConfig.ENDERIAN_SCARF_DROP_CHANCE.get().floatValue(), lootingValue);
        LootTableHandler.addLootWithLooting(event, "entities/zombie", MAItems.Shackle.get(), 1, 1, CommonConfig.SHACKLE_DROP_CHANCE.get().floatValue(), lootingValue);
        LootTableHandler.addLootWithLooting(event, "entities/skeleton", MAItems.MagicQuiver.get(), 1, 1, CommonConfig.MAGIC_QUIVER_SKELETON_DROP_CHANCE.get().floatValue(), lootingValue);
        LootTableHandler.addLootWithLooting(event, "entities/pillager", MAItems.MagicQuiver.get(), 1, 1, CommonConfig.MAGIC_QUIVER_PILLAGER_DROP_CHANCE.get().floatValue(), lootingValue);
        LootTableHandler.addLootWithLooting(event, "entities/ender_dragon", MAItems.EnderDragonClaw.get(), 1, 1, CommonConfig.ENDER_DRAGON_CLAW_DROP_CHANCE.get().floatValue(), lootingValue * 5);

        LootTableHandler.addLoot(event, "chests/ancient_city", MAItems.SculkLens.get(), 1, 1, CommonConfig.SCULK_LENS_DROP_CHANCE.get().floatValue());
        LootTableHandler.addLoot(event, "chests/simple_dungeon", MAItems.CobaltShield.get(), 1, 1, CommonConfig.COBALT_SHIELD_DROP_CHANCE.get().floatValue());
        LootTableHandler.addLoot(event, "chests/simple_dungeon", MAItems.RecallPotion.get(), 1, 3, CommonConfig.RECALL_POTION_DROP_CHANCE.get().floatValue());

        LootTableHandler.addLoot(event, "gameplay/cat_morning_gift", MAItems.MelodyPlushie.get(), 1, 1, CommonConfig.MELODY_PLUSHIE_DROP_CHANCE.get().floatValue());

        LootTableHandler.addLoot(event, "chests/desert_pyramid", MAItems.DuneTreads.get(), 1, 1, 0.15F);

    }
}