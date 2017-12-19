package net.tropicraft.world.structures;

import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureStart;

import java.util.Random;

public class StructureKoaVillageStart extends StructureStart {
	    
    public StructureKoaVillageStart(World world, Random random, int chunkX, int chunkZ) {
        
        ComponentKoaVillage componentstartpiece = new ComponentKoaVillage(world.getWorldChunkManager(), random, (chunkX << 4) + 2, (chunkZ << 4) + 2, world);
        components.add(componentstartpiece);
        //System.err.println("GENERATING VILLAGE BISH" + "  " + ((chunkX << 4) + 2) + "   " +  ((chunkZ << 4) + 2));
         
       
        // Keep creating/adding components here if necessary...
        
        // Must update the bounding box to fit around all of the components we've added
        updateBoundingBox();
        
    }
    
    @Override
    public void generateStructure(World world, Random random,
    		StructureBoundingBox structureboundingbox) {
    	ComponentKoaVillage village = (ComponentKoaVillage)components.get(0);
    	if (!village.hasBeenGenerated()) {
    		super.generateStructure(world, random, structureboundingbox);
    	}
    }
    
}