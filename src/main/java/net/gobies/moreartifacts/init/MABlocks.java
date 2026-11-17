package net.gobies.moreartifacts.init;

import net.gobies.moreartifacts.MoreArtifacts;
import net.gobies.moreartifacts.block.NectarBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MABlocks {
    public static final DeferredRegister<Block> BLOCKS;
    public static final RegistryObject<Block> Nectar;
    public static final RegistryObject<Block> PottedNectar;

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

    static {
        BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MoreArtifacts.MOD_ID);
        Nectar = BLOCKS.register("nectar", () -> new NectarBlock(BlockBehaviour.Properties.copy(Blocks.ALLIUM)));
        PottedNectar = BLOCKS.register("potted_nectar", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, Nectar, BlockBehaviour.Properties.copy(Blocks.POTTED_ALLIUM)));

    }
}