package net.gobies.moreartifacts.block;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("deprecation")
public class NectarBlock extends FlowerBlock {
    public NectarBlock(Properties properties) {
        super(() -> MobEffects.REGENERATION, 15, properties
                .noCollission()
                .instabreak()
                .sound(SoundType.GRASS)
                .pushReaction(PushReaction.DESTROY)
                .offsetType(BlockBehaviour.OffsetType.XZ));
    }

    // Retain same quality when broken and picked up (reforgeable)
    public static final Map<BlockPos, String> QUALITY = new ConcurrentHashMap<>();

    @Override
    public void setPlacedBy(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable LivingEntity placer, @NotNull ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        CompoundTag tag = stack.getTag();
        if (!level.isClientSide() && tag != null && tag.contains("Quality")) {
            QUALITY.put(pos.immutable(), tag.getString("Quality"));
        }
    }


    @Override
    public @NotNull List<ItemStack> getDrops(@NotNull BlockState state, LootParams.Builder builder) {
        ItemStack dropStack = new ItemStack(this.asItem());
        BlockPos pos = BlockPos.containing(builder.getParameter(LootContextParams.ORIGIN));

        String savedQuality = QUALITY.remove(pos);
        if (savedQuality != null) {
            dropStack.getOrCreateTag().putString("Quality", savedQuality);
        }

        return List.of(dropStack);
    }
}