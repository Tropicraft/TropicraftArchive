package net.tropicraft.core.common.worldgen.multi;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.world.chunk.Chunk;

public class StructureMaster {
    /**
     * Collection of active {@code StructureDefinition} objects to be checked against
     * during Chunk population. Key is an arbitrary, unique, user-chosen key for
     * later lookups.
     */
    private static Map<String, StructureDefinition> structureDefinitions = new LinkedHashMap<String, StructureDefinition>(4);

    public static boolean registerStructureDefinition(String name, StructureDefinition definition) {
        if (structureDefinitions.containsKey(name)) {
            System.err.println("Structure Definition already registered with name " + name + "! Ignoring.");
            return false;
        } else {
            structureDefinitions.put(name, definition);
            return true;
        }
    }

    public static void init() {
        for (StructureDefinition def : structureDefinitions.values()) {
            def.registerCandidatePositions();
        }
    }

    private static Structure startNew(Chunk chunk) {
        // TODO
        return null;
    }

    /**
     * Gets a list of all structures with starter candidates in valid range of the
     * given Chunk. Valid range is determined by the structures themselves.
     * @param chunk Chunk being populated
     * @return List of structures within range
     */
    public static List<Structure> getStructuresInRange(Chunk chunk) {
        // TODO
        return null;
    }

    public static void onChunkGeneration(Chunk chunk) {
        Iterator<StructureDefinition> it = structureDefinitions.values().iterator();
        
        while (it.hasNext()) {
            StructureDefinition def = it.next();
            
            if (def.isValidStarterChunk(chunk)) {
                startNew(chunk);
            }
        }
        
        Iterator<Structure> it2 = getStructuresInRange(chunk).iterator();
        
        while (it2.hasNext()) {
            Structure structure = it2.next();
            if (structure.canUse(chunk)) {
                
            }
        }
    }
}
