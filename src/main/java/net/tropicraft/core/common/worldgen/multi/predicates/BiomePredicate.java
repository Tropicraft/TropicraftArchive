package net.tropicraft.core.common.worldgen.multi.predicates;

import com.google.common.base.Predicate;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.chunk.Chunk;

public class BiomePredicate implements Predicate<Chunk> {

    private Biome biome;
    private BiomeProvider biomeProvider;

    public BiomePredicate(Biome biome, BiomeProvider provider) {
        this.biome = biome;
        this.biomeProvider = provider;
    }

    @Override
    public boolean apply(Chunk chunk) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                Biome b = chunk.getBiome(new BlockPos(x, 0, z), this.biomeProvider);
                if (b != null && b == biome) {
                    return true;
                }
            }
        }
        
        return false;
    }

}
