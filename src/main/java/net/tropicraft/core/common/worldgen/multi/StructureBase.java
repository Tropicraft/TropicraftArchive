package net.tropicraft.core.common.worldgen.multi;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.Chunk;

public class StructureBase implements Structure {
    
    private StructureDefinition definition;
    
    /** Temporary single direction used for now */
    public static final EnumFacing TEMP_DIR = EnumFacing.EAST;
    
    private Map<ChunkPos, Map<EnumFacing, Long2ObjectMap<Chunk>>> starter2CandidatesMap;
    
    // TODO figure out a way to not use this
    private Long2ObjectMap<Chunk> starterCandidates;

    public StructureBase(StructureDefinition definition) {
        this(definition, 16);
    }
    
    public StructureBase(StructureDefinition definition, int initialSize) {
        this.definition = definition;
        starter2CandidatesMap = new HashMap<>();
        starterCandidates = new Long2ObjectOpenHashMap<Chunk>();
    }

    public boolean addCandidate(Chunk starterCandidate, Chunk pieceCandidate) {       
        if (starter2CandidatesMap.containsKey(starterCandidate.getPos())) {
            Map<EnumFacing, Long2ObjectMap<Chunk>> dirChunks = starter2CandidatesMap.get(starterCandidate.getPos());
            Long2ObjectMap<Chunk> chunks = dirChunks.get(TEMP_DIR);
            chunks.put(Long.valueOf(ChunkPos.asLong(pieceCandidate.x, pieceCandidate.z)), pieceCandidate);
            //starterCandidates.put(Long.valueOf(ChunkPos.asLong(pieceCandidate.x, pieceCandidate.z)), pieceCandidate);
            return true;
        } else {
            Long2ObjectMap<Chunk> chunks = new Long2ObjectOpenHashMap<Chunk>();
            chunks.put(Long.valueOf(ChunkPos.asLong(pieceCandidate.x, pieceCandidate.z)), pieceCandidate);
            Map<EnumFacing, Long2ObjectMap<Chunk>> map1 = new HashMap<>();
            map1.put(TEMP_DIR, chunks);
            starter2CandidatesMap.put(starterCandidate.getPos(), map1);
            //starterCandidates.put(Long.valueOf(ChunkPos.asLong(starterCandidate.x, starterCandidate.z)), starterCandidate);
            return false;
        }
    }

    public boolean isComplete(Chunk startChunk) {
        Map<EnumFacing, Long2ObjectMap<Chunk>> dirChunks = starter2CandidatesMap.get(startChunk.getPos());
        Long2ObjectMap<Chunk> chunks = dirChunks.get(TEMP_DIR);
        
        Set<ChunkOffset> defOffsets = definition.getChunkOffsets();
        ChunkPos startPos = startChunk.getPos();
        
        int chunksMatched = 0;
        
        for (Chunk c : chunks.values()) {
            ChunkPos pos = c.getPos();
            for (ChunkOffset offset : defOffsets) {
                ChunkPos offsetPos = offset.getPosOffset(startPos);
                if (offsetPos.equals(pos)) {
                    chunksMatched++;
                    break;
                }
            }
        }
        
        return chunksMatched == chunks.size();
    }
    
    public Long2ObjectMap<Chunk> findValidStarterChunks(Chunk pieceCandidate) {
        // List of all valid starter chunks found
        Long2ObjectMap<Chunk> validStartChunks = new Long2ObjectOpenHashMap<Chunk>();
        for (Chunk c : this.getStarterCandidates()) {
            ChunkPos startPos = c.getPos();
            ChunkPos piecePos = pieceCandidate.getPos();
            // TODO add iteration around direction here too
            Set<ChunkOffset> offsets = definition.getChunkOffsets();
            // Iterate through all the offsets from the start pos of this definition
            for (ChunkOffset offset : offsets) {
                // If the start position + offset = piece candidate position sent in
                // then we know this piece fits with this start chunk potentially
                if (offset.getPosOffset(startPos).equals(piecePos)) {
                    validStartChunks.put(Long.valueOf(ChunkPos.asLong(c.x, c.z)), c);
                }
            }
        }

        return validStartChunks;
    }

    @Override
    public Collection<Chunk> getStarterCandidates() {
        return starterCandidates.values();
    }

    @Override
    public Collection<Chunk> getBuilderCandidates(Chunk startCandidate) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int maxPerChunk() {
        // TODO Unused
        return 0;
    }

    @Override
    public boolean canUse(Chunk chunk) {
        return false;
    }

    @Override
    public boolean generate(Chunk startChunk, EnumFacing direction) {
        System.out.println("Trying to generate in chunk" + startChunk.x + " " + startChunk.z);
        return definition.generate(startChunk, direction);
    }

    @Override
    public boolean addStarterCandidate(Chunk starterCandidate) {
        Long2ObjectMap<Chunk> chunks = new Long2ObjectOpenHashMap<Chunk>();
        Map<EnumFacing, Long2ObjectMap<Chunk>> map1 = new HashMap<>();
        map1.put(TEMP_DIR, chunks);
        starter2CandidatesMap.put(starterCandidate.getPos(), map1);
        starterCandidates.put(ChunkPos.asLong(starterCandidate.x, starterCandidate.z), starterCandidate);
        return true;
    }
    
    @Override
    public boolean equals(Object o) {
        StructureBase other = (StructureBase)o;
        return false;
    }

    @Override
    public StructureDefinition getDefinition() {
        return this.definition;
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


/**
chunk loaded -> 
iterate through definitions -> 
iterate through starter candidate chunks -> 
iterate through each direction -> 
see if chunk fits in each rotated direction of structure definition -> 
if chunk fits, check if it's valid -> 
if it's valid, add the chunk as a piece candidate for the starter candidate chunk -> 
if all required pieces are met for the starter chunk, generate.
*/