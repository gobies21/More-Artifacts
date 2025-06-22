package net.gobies.moreartifacts.loot;

import net.gobies.moreartifacts.Config;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.util.LootTableHandler;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "moreartifacts", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MALootTables {

    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event) {
        LootTableHandler.addLootToEntity(event, "entities/cave_spider", MAItems.Bezoar.get(), 1, 1, Config.BEZOAR_DROP_CHANCE.get().floatValue(), 0.01F);
        LootTableHandler.addLootToEntity(event, "entities/elder_guardian", MAItems.Vitamins.get(), 1, 1, Config.VITAMINS_DROP_CHANCE.get().floatValue(), 0.05F);
        LootTableHandler.addLootToEntity(event, "entities/stray", MAItems.FastClock.get(), 1, 1, Config.FAST_CLOCK_DROP_CHANCE.get().floatValue(), 0.01F);
        LootTableHandler.addLootToEntity(event, "entities/husk", MAItems.DesertCharm.get(), 1, 1, Config.DESERT_CHARM_DROP_CHANCE.get().floatValue(), 0.01F);
        LootTableHandler.addLootToEntity(event, "entities/wither", MAItems.WitherShard.get(), 1, 1, Config.WITHER_SHARD_DROP_CHANCE.get().floatValue(), 0.05F);
        LootTableHandler.addLootToEntity(event, "entities/shulker", MAItems.ShulkerHeart.get(), 1, 1, Config.SHULKER_HEART_DROP_CHANCE.get().floatValue(), 0.01F);
        LootTableHandler.addLootToEntity(event, "entities/enderman", MAItems.EnderianScarf.get(), 1, 1, Config.ENDERIAN_SCARF_DROP_CHANCE.get().floatValue(), 0.01F);
        LootTableHandler.addLootToEntity(event, "entities/zombie", MAItems.Shackle.get(), 1, 1, Config.SHACKLE_DROP_CHANCE.get().floatValue(), 0.01F);
        LootTableHandler.addLootToEntity(event, "entities/skeleton", MAItems.MagicQuiver.get(), 1, 1, Config.MAGIC_QUIVER_SKELETON_DROP_CHANCE.get().floatValue(), 0.01F);
        LootTableHandler.addLootToEntity(event, "entities/pillager", MAItems.MagicQuiver.get(), 1, 1, Config.MAGIC_QUIVER_PILLAGER_DROP_CHANCE.get().floatValue(), 0.01F);
        LootTableHandler.addLootToEntity(event, "entities/ender_dragon", MAItems.EnderDragonClaw.get(), 1, 1, Config.ENDER_DRAGON_CLAW_DROP_CHANCE.get().floatValue(), 0.05F);

        LootTableHandler.addLootToEntity(event, "chests/ancient_city", MAItems.SculkLens.get(), 1, 1, Config.SCULK_LENS_DROP_CHANCE.get().floatValue(), 0.00F);
        LootTableHandler.addLootToEntity(event, "chests/simple_dungeon", MAItems.CobaltShield.get(), 1, 1, Config.COBALT_SHIELD_DROP_CHANCE.get().floatValue(), 0.00F);
        LootTableHandler.addLootToEntity(event, "chests/simple_dungeon", MAItems.RecallPotion.get(), 1, 3, Config.RECALL_POTION_DROP_CHANCE.get().floatValue(), 0.00F);

        LootTableHandler.addLootToEntity(event, "gameplay/cat_morning_gift", MAItems.MelodyPlushie.get(), 1, 1, Config.MELODY_PLUSHIE_DROP_CHANCE.get().floatValue(), 0.00F);



    }
}
