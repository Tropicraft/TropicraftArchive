package net.tropicraft.core.common.worldgen.multi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Predicate;

import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.Chunk;

/**
 * A StructureDefinition is used to lay out what sort of @{Structure} should be generated
 * @author cojomax99
 *
 */
public abstract class StructureDefinition {

    protected Map<ChunkPos, List<Predicate<Chunk>>> candidates = new HashMap<>();
    
    public abstract List<Predicate<Chunk>> starterChunkConditions();
    
    /**
     * Register all candidate positions in this function. It will get called
     * during initialization.
     */
    public abstract void registerCandidatePositions();
    
    protected void addCandidate(ChunkPos pos, List<Predicate<Chunk>> predicates) {
        candidates.put(pos, predicates);
    }
    
    /**
     * Checks the provided chunk against all the predicates required for this structure
     * definition to be deemed a valid starter chunk
     * @param chunk
     * @return Whether the chunk is a valid starter chunk for this structure definition
     */
    public boolean isValidStarterChunk(Chunk chunk) {
        for (Predicate<Chunk> predicate : starterChunkConditions()) {
            if (predicate.apply(chunk)) {
                return true;
            }
        }
        
        return false;
    }
    
}
