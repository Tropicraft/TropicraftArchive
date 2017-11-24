package net.tropicraft.core.common.worldgen.multi;

import java.util.Collection;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.world.chunk.Chunk;

public abstract class StructureBase implements Structure {
    
    private Long2ObjectMap<Chunk> id2ChunkMap = null;
    
    protected void init(int initialSize) {
        id2ChunkMap = new Long2ObjectOpenHashMap<Chunk>(initialSize);
    }
    
    public abstract boolean isPotentialCandidate(Chunk chunk);
    public abstract boolean isComplete();
    public abstract boolean generate(Chunk chunk);
    public abstract boolean isMarkedValid(Chunk chunk);
    public abstract void markValid(Chunk chunk);
    public void addCandidate(Chunk starterCandidate, Chunk pieceCandidate) {
        
    }

    /**
     * Return a Collection of all Chunks that are part of the structure so far
     */
    protected Collection<Chunk> getChunks() {
        return this.id2ChunkMap.values();
    }
    
/*
 * if (nearVillageGen(chunkpos)) {
    markVillageChunkGenerated(chunkpos); // checks if this chunk is valid, if it is, marks it as generated, otherwise discards this village attempt
    if (getVillageNear(chunkpos).isComplete()) {
       getVillageNear(chunkpos).generate(chunkpos);
    }
}

right, you'd check every chunk also for if it's a valid initial position for a village

and add that to a set of candidates
so you keep this set of candidates and check that any future chunks are (A) in range of this candidate (B) are water chunks(edited)
once the candidate knows that all chunks it will use are valid and generated, it places the blocks without worry

right
there could potentially be a lot of candidates
considering a candidate is just "am I near a beach"
but that should be fine

a bottleneck I can think of is that we'd need a fast way to lookup candidates that are nearby the current position
as the edges of generation becomes larger and larger it will become a huge waste of time iterating candidates that are very far away
enough to worry about I'm not sure
maybe that's something to worry about if it's actually a performance problem
and not now :smiley:
 */
}

/*
 * yeah so you have a class for the structure def
in there you have a list of structure chunks
which are expressed as x,z offsets in whole number chunks from the start chunk
(including the start chunk itself - with a 0,0 pos)
or maybe the start chunk is separate I dunno
but then you only care about mapping to world coords after you start checking predicates
when you find a suitable location for the starter chunk, you check the rotation and use that to calculate what all the extra chunks' coords actually are


right, except you don't even need a register function
make a chunk candidate class and just have lists that you can add to
kinda like how mob spawning works
so then you have your abstract structure def class that takes the two lists as parameters in the constructor
so impls will then super that with their set of lists
or heck, don't even need to make it abstract. if people want to use it directly why stop them
*/
