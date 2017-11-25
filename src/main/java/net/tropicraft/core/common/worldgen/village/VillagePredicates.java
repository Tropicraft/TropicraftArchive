package net.tropicraft.core.common.worldgen.village;

import com.google.common.base.Predicate;

import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.tropicraft.core.common.biome.BiomeTropicraft;
import net.tropicraft.core.common.worldgen.multi.predicates.BiomePredicate;

public class VillagePredicates {

    public static Predicate<Chunk> OCEAN_BIOME;
    public static Predicate<Chunk> BEACH_BIOME;
    
    public static void init(World world) {
        OCEAN_BIOME = new BiomePredicate(world.getBiomeProvider(), BiomeTropicraft.tropicsOcean);
        BEACH_BIOME = new BiomePredicate(world.getBiomeProvider(), BiomeTropicraft.tropicsBeach, BiomeTropicraft.tropics);
    }
}
