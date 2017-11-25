package net.tropicraft.core.common.worldgen.multi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class StructureMaster {
    /**
     * Collection of active {@code StructureDefinition} objects to be checked against
     * during Chunk population. Key is an arbitrary, unique, user-chosen key for
     * later lookups.
     */
    private static Map<String, StructureDefinition> structureDefinitions = new LinkedHashMap<String, StructureDefinition>(4);

    private static Map<ChunkPos, List<Structure>> structures = new LinkedHashMap<>();
    
    public static boolean registerStructureDefinition(String name, StructureDefinition definition) {
        if (structureDefinitions.containsKey(name)) {
            System.err.println("Structure Definition already registered with name " + name + "! Ignoring.");
            return false;
        } else {
            structureDefinitions.put(name, definition);
            System.out.println("Registering structure definition with name " + name);
            return true;
        }
    }

    // MAJOR TODO - track world changes and reload if the world loaded is the same
    // as a world previously stored. Use WorldEvent.Unload?
    @SubscribeEvent
    public static void init(WorldEvent.Load event) {
        if (!(event.getWorld() instanceof WorldServer)) return;
        for (StructureDefinition def : structureDefinitions.values()) {
            if (((WorldServer)event.getWorld()).provider.getDimension() == def.getDimensionID()) {
                def.registerCandidatePositions(event.getWorld());
            }
        }
    }

    private static Structure startNew(StructureDefinition definition, Chunk chunk) {
        Structure structure = new StructureBase(definition);
        //System.out.println("Found valid starter chunk: (" + chunk.x + ", " + chunk.z + ")");
        
        // If we already have a structure originating from this chunk, add it to the list
        if (structures.containsKey(chunk.getPos())) {
            // Check and see if this chunk already has a structure of the given definition
            // originating from this chunk
            if (!chunkContainsStructureOfType(chunk.getPos(), definition)) {
                structures.get(chunk.getPos()).add(structure);
                structure.addStarterCandidate(chunk);
            }
        } else { // Otherwise, create a new list for structures originating from this chunk
            ArrayList<Structure> st = new ArrayList<Structure>();
            st.add(structure);
            structures.put(chunk.getPos(), st);
            structure.addStarterCandidate(chunk);
        }
        return structure;
    }
    
    private static boolean chunkContainsStructureOfType(ChunkPos pos, StructureDefinition def) {
        List<Structure> structuresInChunk = structures.get(pos);
        for (Structure structure : structuresInChunk) {
            if (structure.getDefinition().equals(def)) {
                return true;
            }
        }
        
        return false;
    }

    /**
     * Gets a list of all structures with starter candidates in valid range of the
     * given Chunk. Valid range is determined by the structures themselves.
     * @param chunk Chunk being populated
     * @return List of structures within range
     */
    public static List<Structure> getStructuresInRange(Chunk chunk) {
        int range = 2;
        List<Structure> foundStructures = new ArrayList<Structure>();
        ChunkPos pos = chunk.getPos();
        for (int i = -range; i < range; i++) {
            for (int j = -range; j < range; j++) {
                ChunkPos pos2 = new ChunkPos(pos.x + i, pos.z + j);
                if (structures.containsKey(pos2)) {
                    foundStructures.addAll(structures.get(pos2));
                }
            }
        }

        return foundStructures;
    }

    public static void onChunkGeneration(Chunk chunk) {
        Iterator<StructureDefinition> it = structureDefinitions.values().iterator();
        
        //System.out.println("onChunkGeneration");
        
        while (it.hasNext()) {
            StructureDefinition def = it.next();
            
            // Don't worry about this structure definition if it's not meant to generate
            // in the same dimension as this chunk
            if (def.getDimensionID() != chunk.getWorld().provider.getDimension())
                continue;
            
            if (def.isValidStarterChunk(chunk)) {
                startNew(def, chunk);
            }
        }
        
        // Find all currently in-progress structures within range of the newly loaded chunk
        Iterator<Structure> it2 = getStructuresInRange(chunk).iterator();

        while (it2.hasNext()) {
            Structure structure = it2.next();
            
            // Don't worry about this structure definition if it's not meant to generate
            // in the same dimension as this chunk
            if (structure.getDimensionID() != chunk.getWorld().provider.getDimension())
                continue;
            
            Long2ObjectMap<Chunk> startChunks = structure.findValidStarterChunks(chunk);
            for (Chunk startChunk : startChunks.values()) {
                boolean couldAdd = structure.addCandidate(startChunk, chunk);

                if (couldAdd) {
                    if (structure.isComplete(startChunk)) {
                        boolean didGenerate = structure.generate(startChunk, StructureBase.TEMP_DIR);
                        if (didGenerate) {
                            // In theory, remove the structure from being reconstructed
                            structures.get(startChunk.getPos()).remove(structure);
                        }
                    }
                }
                
            }
        }
    }
}
