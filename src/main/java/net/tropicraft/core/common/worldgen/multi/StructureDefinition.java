package net.tropicraft.core.common.worldgen.multi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Predicate;

import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

/**
 * A StructureDefinition is used to lay out what sort of @{Structure} should be generated
 * @author cojomax99
 *
 */
public abstract class StructureDefinition {

    protected Map<ChunkOffset, Predicate<Chunk>[]> candidates = new HashMap<>();
    
    public abstract List<Predicate<Chunk>> starterChunkConditions();
    
    /**
     * Register all candidate positions in this function. It will get called
     * during world initialization.
     */
    public abstract void registerCandidatePositions(World world);
    
    protected void addCandidate(ChunkOffset offset, Predicate<Chunk>... predicates) {
        candidates.put(offset, predicates);
    }
    
    public Set<ChunkOffset> getChunkOffsets() {
        return candidates.keySet();
    }
    
    /**
     * Checks the provided chunk against all the predicates required for this structure
     * definition to be deemed a valid starter chunk
     * @param chunk
     * @return Whether the chunk is a valid starter chunk for this structure definition
     */
    public boolean isValidStarterChunk(Chunk chunk) {
        boolean allPredicatesMet = true;
        for (Predicate<Chunk> predicate : starterChunkConditions()) {
            if (!predicate.apply(chunk)) {
                allPredicatesMet = false;
            }
        }
        
        return allPredicatesMet;
    }
    
    public abstract boolean generate(Chunk startChunk, EnumFacing direction);
    
    public abstract int getDimensionID();
    
}
