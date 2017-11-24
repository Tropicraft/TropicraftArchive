package net.tropicraft.core.common.worldgen.multi.predicates;

import java.util.List;

import com.google.common.base.Predicate;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.chunk.Chunk;
import scala.actors.threadpool.Arrays;

public class BiomePredicate implements Predicate<Chunk> {

    private List<Biome> biomes;
    private BiomeProvider biomeProvider;

    public BiomePredicate(BiomeProvider provider, Biome... biomes) {
        this.biomes = Arrays.asList(biomes);
        this.biomeProvider = provider;
    }

    @Override
    public boolean apply(Chunk chunk) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                Biome b = chunk.getBiome(new BlockPos(x, 0, z), this.biomeProvider);
                if (b != null && this.biomes.contains(b)) {
                    return true;
                }
            }
        }
        
        return false;
    }

}
