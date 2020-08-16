package net.tropicraft.core.common.dimension.config;

import java.util.function.Supplier;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.LazyLoadBase;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.tropicraft.core.common.block.BlockTropicraftSand;
import net.tropicraft.core.common.block.TropicraftBlocks;

public class TropicsBuilderConfigs {
    private static final LazyLoadBase<BlockState> PURIFIED_SAND = new LazyLoadBase<>(() -> TropicraftBlocks.PURIFIED_SAND.get().getDefaultState());
    private static final LazyLoadBase<BlockState> UNDERWATER_PURIFIED_SAND = new LazyLoadBase<>(() -> PURIFIED_SAND.getValue().with(BlockTropicraftSand.UNDERWATER, true));

    public static final Supplier<SurfaceBuilderConfig> PURIFIED_SAND_CONFIG = () -> new SurfaceBuilderConfig(PURIFIED_SAND.getValue(), PURIFIED_SAND.getValue(), UNDERWATER_PURIFIED_SAND.getValue());
    public static final Supplier<SurfaceBuilderConfig> UNDERWATER_PURIFIED_SAND_CONFIG = () -> new SurfaceBuilderConfig(UNDERWATER_PURIFIED_SAND.getValue(), UNDERWATER_PURIFIED_SAND.getValue(), UNDERWATER_PURIFIED_SAND.getValue());

    public static final Supplier<SurfaceBuilderConfig> TROPICS_CONFIG = () -> new SurfaceBuilderConfig(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.STONE.getDefaultState());
}
