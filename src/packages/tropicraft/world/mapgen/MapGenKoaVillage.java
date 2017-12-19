package tropicraft.world.mapgen;

import java.util.Arrays;
import java.util.List;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureMineshaftStart;
import net.minecraft.world.gen.structure.StructureStart;
import tropicraft.world.biomes.BiomeGenTropicraft;
import tropicraft.world.structures.StructureKoaVillageStart;

public class MapGenKoaVillage extends MapGenStructure {
    
    private static List<BiomeGenBase> koaSpawnBiomes;
    
    public static String structureComponentName = "KoaVillage";

    @Override
    protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
        
    	int spreadX = 32;
    	int spreadZ = 32;
    	
    	int searchSizeX = 16;
    	int searchSizeZ = 16;
    	
    	if (chunkX % spreadX == 0 && chunkZ % spreadZ == 0) {
    		int totalGoodBiomes = 0;
    		for (int xOffset = 0; xOffset < searchSizeX; xOffset++) {
    			for (int zOffset = 0; zOffset < searchSizeZ; zOffset++) {
    				if (worldObj.getWorldChunkManager().areBiomesViable((chunkX + xOffset) * 16 + 8, (chunkZ + zOffset) * 16 + 8, 0, koaSpawnBiomes)) {
    					totalGoodBiomes++;
    				}
    			}
    		}
    		
    		if (totalGoodBiomes > 16) {
    			return true;
    		}
    		
    	}
    	return false;
    	
    }

    @Override
    protected StructureStart getStructureStart(int i, int j) {
        
        return new StructureKoaVillageStart(worldObj, rand, i, j);
    }
    
    static
    {
        koaSpawnBiomes = Arrays.asList(new BiomeGenBase[]
                {
                    BiomeGenTropicraft.tropicsOcean, BiomeGenTropicraft.beach, BiomeGenTropicraft.tropics
                });
    }

	@Override
	public String func_143025_a() {
		return structureComponentName;
	}
}