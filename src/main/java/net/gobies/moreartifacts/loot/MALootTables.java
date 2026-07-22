package net.gobies.moreartifacts.loot;

import net.gobies.moreartifacts.MoreArtifacts;
import net.gobies.moreartifacts.config.CommonConfig;
import net.gobies.moreartifacts.init.MAItems;
import net.gobies.moreartifacts.util.LootTableHandler;
import net.gobies.moreartifacts.util.MAUtils;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = MoreArtifacts.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MALootTables {

    static float lootingValue = MAUtils.lootingValues();

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onLootTableLoad(LootTableLoadEvent event) {
        if (CommonConfig.ENABLE_LOOT_TABLES.get()) {
            if (!CommonConfig.isItemDisabled(MAItems.Bezoar.get())) {
                LootTableHandler.addLootWithLooting(event, "entities/cave_spider", MAItems.Bezoar.get(), 1, 1, CommonConfig.BEZOAR_DROP_CHANCE.get().floatValue(), lootingValue);
            }
            if (!CommonConfig.isItemDisabled(MAItems.Vitamins.get())) {
                LootTableHandler.addLootWithLooting(event, "entities/elder_guardian", MAItems.Vitamins.get(), 1, 1, CommonConfig.VITAMINS_DROP_CHANCE.get().floatValue(), lootingValue * 5);
            }
            if (!CommonConfig.isItemDisabled(MAItems.FastClock.get())) {
                LootTableHandler.addLootWithLooting(event, "entities/stray", MAItems.FastClock.get(), 1, 1, CommonConfig.FAST_CLOCK_DROP_CHANCE.get().floatValue(), lootingValue);
            }
            if (!CommonConfig.isItemDisabled(MAItems.DesertCharm.get())) {
                LootTableHandler.addLootWithLooting(event, "entities/husk", MAItems.DesertCharm.get(), 1, 1, CommonConfig.DESERT_CHARM_DROP_CHANCE.get().floatValue(), lootingValue);
            }
            if (!CommonConfig.isItemDisabled(MAItems.WitherShard.get())) {
                LootTableHandler.addLootWithLooting(event, "entities/wither", MAItems.WitherShard.get(), 1, 1, CommonConfig.WITHER_SHARD_DROP_CHANCE.get().floatValue(), lootingValue * 5);
            }
            if (!CommonConfig.isItemDisabled(MAItems.ShulkerHeart.get())) {
                LootTableHandler.addLootWithLooting(event, "entities/shulker", MAItems.ShulkerHeart.get(), 1, 1, CommonConfig.SHULKER_HEART_DROP_CHANCE.get().floatValue(), lootingValue);
            }
            if (!CommonConfig.isItemDisabled(MAItems.EnderianScarf.get())) {
                LootTableHandler.addLootWithLooting(event, "entities/enderman", MAItems.EnderianScarf.get(), 1, 1, CommonConfig.ENDERIAN_SCARF_DROP_CHANCE.get().floatValue(), lootingValue);
            }
            if (!CommonConfig.isItemDisabled(MAItems.Shackle.get())) {
                LootTableHandler.addLootWithLooting(event, "entities/zombie", MAItems.Shackle.get(), 1, 1, CommonConfig.SHACKLE_DROP_CHANCE.get().floatValue(), lootingValue);
            }
            if (!CommonConfig.isItemDisabled(MAItems.MagicQuiver.get())) {
                LootTableHandler.addLootWithLooting(event, "entities/skeleton", MAItems.MagicQuiver.get(), 1, 1, CommonConfig.MAGIC_QUIVER_SKELETON_DROP_CHANCE.get().floatValue(), lootingValue);
                LootTableHandler.addLootWithLooting(event, "entities/pillager", MAItems.MagicQuiver.get(), 1, 1, CommonConfig.MAGIC_QUIVER_PILLAGER_DROP_CHANCE.get().floatValue(), lootingValue);
            }
            if (!CommonConfig.isItemDisabled(MAItems.EnderDragonClaw.get())) {
                LootTableHandler.addLootWithLooting(event, "entities/ender_dragon", MAItems.EnderDragonClaw.get(), 1, 1, CommonConfig.ENDER_DRAGON_CLAW_DROP_CHANCE.get().floatValue(), lootingValue * 5);
            }
            if (!CommonConfig.isItemDisabled(MAItems.DragonEye.get())) {
                LootTableHandler.addLootWithLooting(event, "entities/ender_dragon", MAItems.DragonEye.get(), 1, 1, 0.05F, lootingValue);
            }
            if (!CommonConfig.isItemDisabled(MAItems.TaintedMirror.get())) {
                LootTableHandler.addLootWithLooting(event, "entities/warden", MAItems.TaintedMirror.get(), 1, 1, 0.10F, lootingValue);
            }
            if (!CommonConfig.isItemDisabled(MAItems.CursedDoll.get())) {
                LootTableHandler.addLootWithLooting(event, "entities/witch", MAItems.CursedDoll.get(), 1, 1, 0.02F, lootingValue);
            }

            if (!CommonConfig.isItemDisabled(MAItems.SculkLens.get())) {
                LootTableHandler.addLoot(event, "chests/ancient_city", MAItems.SculkLens.get(), 1, 1, CommonConfig.SCULK_LENS_DROP_CHANCE.get().floatValue());
            }
            if (!CommonConfig.isItemDisabled(MAItems.CobaltShield.get())) {
                LootTableHandler.addLoot(event, "chests/simple_dungeon", MAItems.CobaltShield.get(), 1, 1, CommonConfig.COBALT_SHIELD_DROP_CHANCE.get().floatValue());
            }
            if (!CommonConfig.isItemDisabled(MAItems.RecallPotion.get())) {
                LootTableHandler.addLoot(event, "chests/simple_dungeon", MAItems.RecallPotion.get(), 1, 3, CommonConfig.RECALL_POTION_DROP_CHANCE.get().floatValue());
            }
            if (!CommonConfig.isItemDisabled(MAItems.WormholePotion.get())) {
                LootTableHandler.addLoot(event, "chests/simple_dungeon", MAItems.WormholePotion.get(), 1, 3, 0.10F);
            }
            if (!CommonConfig.isItemDisabled(MAItems.MelodyPlushie.get())) {
                LootTableHandler.addLoot(event, "gameplay/cat_morning_gift", MAItems.MelodyPlushie.get(), 1, 1, CommonConfig.MELODY_PLUSHIE_DROP_CHANCE.get().floatValue());
            }
            if (!CommonConfig.isItemDisabled(MAItems.DuneTreads.get())) {
                LootTableHandler.addLoot(event, "chests/desert_pyramid", MAItems.DuneTreads.get(), 1, 1, 0.15F);
            }
            if (!CommonConfig.isItemDisabled(MAItems.RubyRing.get())) {
                LootTableHandler.addLoot(event, "chests/bastion_treasure", MAItems.RubyRing.get(), 1, 1, 0.10F);
                LootTableHandler.addLoot(event, "chests/bastion_other", MAItems.RubyRing.get(), 1, 1, 0.05F);
            }
            if (!CommonConfig.isItemDisabled(MAItems.IceCrystal.get())) {
                LootTableHandler.addLoot(event, "chests/igloo_chest", MAItems.IceCrystal.get(), 1, 1, 1.0F);
            }
            if (!CommonConfig.isItemDisabled(MAItems.NaturesMantle.get())) {
                LootTableHandler.addLoot(event, "chests/jungle_temple", MAItems.NaturesMantle.get(), 1, 1, 0.20F);
            }
        }
    }

    @SubscribeEvent
    public static void onTrade(VillagerTradesEvent event) {
        if (!CommonConfig.isItemDisabled(MAItems.MagicQuiver.get())) {
            if (event.getType().equals(VillagerProfession.FLETCHER)) {
                List<VillagerTrades.ItemListing> trades = event.getTrades().get(3);

                trades.add((entity, random) -> {
                    if (random.nextFloat() < CommonConfig.MAGIC_QUIVER_TRADE_CHANCE.get()) {
                        return new MerchantOffer(
                                new ItemStack(Items.EMERALD, 24),
                                new ItemStack(MAItems.MagicQuiver.get(), 1),
                                1,
                                10,
                                0.05f
                        );
                    }
                    return null;
                });
            }
        }
        if (!CommonConfig.isItemDisabled(MAItems.HolyMantle.get())) {
            if (event.getType().equals(VillagerProfession.CLERIC)) {
                List<VillagerTrades.ItemListing> trades = event.getTrades().get(5);

                trades.add((entity, random) -> {
                    if (random.nextFloat() < CommonConfig.HOLY_MANTLE_TRADE_CHANCE.get()) {
                        return new MerchantOffer(
                                new ItemStack(Items.EMERALD, 64),
                                new ItemStack(Items.EMERALD, 64),
                                new ItemStack(MAItems.HolyMantle.get(), 1),
                                1,
                                50,
                                0.05f
                        );
                    }
                    return null;
                });
            }
        }
        if (!CommonConfig.isItemDisabled(MAItems.StealthManual.get())) {
            if (event.getType().equals(VillagerProfession.CARTOGRAPHER)) {
                List<VillagerTrades.ItemListing> trades = event.getTrades().get(5);

                trades.add((entity, random) -> {
                    if (random.nextFloat() < 0.40) {
                        return new MerchantOffer(
                                new ItemStack(Items.EMERALD, 30),
                                new ItemStack(MAItems.StealthManual.get(), 1),
                                1,
                                30,
                                0.25f
                        );
                    }
                    return null;
                });
            }
        }
    }
}