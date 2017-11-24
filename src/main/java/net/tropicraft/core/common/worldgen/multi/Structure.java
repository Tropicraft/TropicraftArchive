package net.tropicraft.core.common.worldgen.multi;

import java.util.Collection;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import net.minecraft.util.EnumFacing;
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
    
    public Long2ObjectMap<Chunk> findValidStarterChunks(Chunk pieceCandidate);
    
    public boolean addCandidate(Chunk starterCandidate, Chunk pieceCandidate);
    
    /**
     * When a chunk is loaded and deemed to be a valid starter candidate for this structure
     * this method is called to add the chunk to the structure's list of starter candidates
     * @param starterCandidate
     * @return
     */
    public boolean addStarterCandidate(Chunk starterCandidate);
    
    /**
     * Determines whether the structure starting from the given Chunk
     * is complete.
     * @param startChunk
     * @return
     */
    public boolean isComplete(Chunk startChunk);

    /**
     * Actually build the structure.
     * @param startChunk Chunk to start the structure gen from
     * @param direction Direction to generate in
     * @return Whether the build generated successfully
     */
    public boolean generate(Chunk startChunk, EnumFacing direction);
    
    /**
     * Ez getter for the definition associated with this structure
     * @return
     */
    public StructureDefinition getDefinition();
}
