package net.tropicraft.core.common.worldgen.village;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.google.common.base.Predicate;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.tropicraft.core.common.biome.decorators.BiomeDecoratorTropicsBeach;
import net.tropicraft.core.common.dimension.WorldProviderTropicraft;
import net.tropicraft.core.common.worldgen.multi.ChunkOffset;
import net.tropicraft.core.common.worldgen.multi.StructureDefinition;

public class StructureDefinitionVillage extends StructureDefinition {

    private List<Predicate<Chunk>> starterChunkConditions;

    public StructureDefinitionVillage() {
        starterChunkConditions = new LinkedList<>();
    }

    @Override
    public List<Predicate<Chunk>> starterChunkConditions() {
        return starterChunkConditions;
    }

    @Override
    public void registerCandidatePositions(World world) {
        // TODO prevent duplicates by checking dimension
        System.err.println("Registering Village predicates");
        // TODO Eventually move the custom predicates init stuff into a registry system that automagically
        // gets called like events. But that's for later, if ever.
        VillagePredicates.init(world);
        // TODO similar to above, create a predicate init method
        starterChunkConditions.add(VillagePredicates.BEACH_BIOME);
        
        //looks like villages are 4.75 chunks long and 5.375 chunks wide
        int z = 0;
        //for (int z = -2; z < 3; z++) {
            this.addCandidate(new ChunkOffset(1, z), VillagePredicates.OCEAN_BIOME);
//            this.addCandidate(new ChunkOffset(2, z), VillagePredicates.OCEAN_BIOME);
//            this.addCandidate(new ChunkOffset(3, z), VillagePredicates.OCEAN_BIOME);
//            this.addCandidate(new ChunkOffset(4, z), VillagePredicates.OCEAN_BIOME);
//            this.addCandidate(new ChunkOffset(5, z), VillagePredicates.OCEAN_BIOME);
            //this.addCandidate(new ChunkOffset(6, z), VillagePredicates.OCEAN_BIOME);
      //  }
    }

    @Override
    public boolean generate(Chunk startChunk, EnumFacing direction) {
        if (startChunk.getWorld() == null) {
            System.err.println("Could not generate structure def, world is null!");
            return false;
        }
        // TODO Add proper BlockPos logic here to select a valid place in the middle of the chunk if desired
        int x = startChunk.x << 4;
        int z = startChunk.z << 4;
        int y = startChunk.getHeight(new BlockPos(x, 0, z));
        //TownKoaVillageGenHelper.hookTryGenVillage(new BlockPos(x, y, z), startChunk.getWorld());
        return decorateForVillage(startChunk.getWorld(), startChunk.getWorld().rand, new BlockPos(x, y, z));
    }
    
    public static boolean decorateForVillage(World world, Random rand, BlockPos pos) {
        boolean success = false;
        for (int ii = 0; ii < 20 && !success; ii++) {
            int i = BiomeDecoratorTropicsBeach.randCoord(rand, pos.getX(), 16);
            int k = BiomeDecoratorTropicsBeach.randCoord(rand, pos.getZ(), 16);
            int y = world.getTopSolidOrLiquidBlock(new BlockPos(i, 0, k)).getY();
            if (y < WorldProviderTropicraft.MID_HEIGHT) y = WorldProviderTropicraft.MID_HEIGHT+1;
            success = TownKoaVillageGenHelper.hookTryGenVillage(new BlockPos(i, y, k), world);
        }
        
        return success;
    }

}
