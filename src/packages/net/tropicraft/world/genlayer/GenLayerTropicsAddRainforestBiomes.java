package net.tropicraft.world.genlayer;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import net.tropicraft.world.biomes.BiomeGenTropicraft;

public class GenLayerTropicsAddRainforestBiomes extends GenLayerTropicraft{
	
	int[] biomes = new int[] { BiomeGenTropicraft.rainforestPlains.biomeID, BiomeGenTropicraft.rainforestMountains.biomeID, BiomeGenTropicraft.rainforestHills.biomeID }; 
	
	public GenLayerTropicsAddRainforestBiomes(long l, GenLayer p) {
		super(l);
		parent = p;
	}
	
	@Override
	public int[] getInts(int i, int j, int k, int l) {
		int ai[] = parent.getInts(i, j, k, l);
	    int ai1[] = IntCache.getIntCache(k * l);
	    for(int i1 = 0; i1 < l; i1++)
	    {
	        for(int j1 = 0; j1 < k; j1++)
	        {
	            this.initChunkSeed(j1 + i, i1 + j);
	            int k1 = ai[j1 + i1 * k];
	            if(k1 == BiomeGenTropicraft.rainforestPlains.biomeID)
	            {
	                ai1[j1 + i1 * k] = biomes[nextInt(biomes.length)];
	            } 
	            else
	            {
	                ai1[j1 + i1 * k] = k1;
	            }
	            
	        }
	    }
	    return ai1;
	}
}
