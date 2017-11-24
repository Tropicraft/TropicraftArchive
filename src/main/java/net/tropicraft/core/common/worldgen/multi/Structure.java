package net.tropicraft.core.common.worldgen.multi;

import java.util.Collection;

import net.minecraft.world.chunk.Chunk;

public interface Structure {
    /**
     * The maximum number of starter candidates for the structure. As with many things, this
     * is a "use in the best way for you" scenario. If you have a high number of potential
     * starter candidates, you use more memory and validation takes longer the larger
     * your structure is. If you have a low number of potential starter candidates, you're
     * banking that the one potential location you have stored will eventually be valid.
     *
     * @return Maximum number of starter candidates to track, or -1 if you don't care
     */
    default public int getMaxStarterCandidates() {
        return -1;
    }
    
    public Collection<Chunk> getStarterCandidates();

    /**
     * Get a collection of all the potential build candidate chunks who have a base of
     * startCandidate
     * @param startCandidate
     * @return
     */
    public Collection<Chunk> getBuilderCandidates(Chunk startCandidate);
    
    public int maxPerChunk();
    
    /**
     * Can this structure make use of the provided chunk?
     * @param chunk Chunk being generated
     * @return Whether this chunk fits into the candidates of this structure's starter candidates
     */
    public boolean canUse(Chunk chunk);
}
