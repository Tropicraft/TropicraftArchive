package net.tropicraft.core.common.dimension.config;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.tropicraft.core.common.block.TropicraftBlocks;

public class TropicsBuilderConfigs {
    private static final BlockState PURIFIED_SAND = TropicraftBlocks.PURIFIED_SAND.getDefaultState();

    public static final SurfaceBuilderConfig PURIFIED_SAND_CONFIG = new SurfaceBuilderConfig(PURIFIED_SAND, PURIFIED_SAND, PURIFIED_SAND);

    public static final SurfaceBuilderConfig TROPICS_CONFIG = new SurfaceBuilderConfig(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.STONE.getDefaultState());
}