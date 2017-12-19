package net.tropicraft.world.genlayer;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import net.tropicraft.world.biomes.BiomeGenTropicraft;

public class GenLayerTropicsAddBiomes extends GenLayerTropicraft{
	
	int[] biomes = new int[] {BiomeGenTropicraft.tropics.biomeID, BiomeGenTropicraft.rainforestPlains.biomeID};
	
	public GenLayerTropicsAddBiomes(long l, GenLayer p) {
		super(l);
		parent = p;
	}

    @Override
	public int[] getInts(int i, int j, int k, int l)
    {
    	//System.out.println(i + ", " + j + ", " + k + ", " + l);
        int ai[] = parent.getInts(i, j, k, l);
        int ai1[] = IntCache.getIntCache(k * l);
        for(int i2 = 0; i2 < l; i2++)
        {
            for(int j2 = 0; j2 < k; j2++)
            {
                initChunkSeed(j2 + i, i2 + j);
                int k2 = ai[j2 + i2 * k];
                if(k2 != 60)
                {
                    if(nextInt(3) == 0)
                    {
                    	ai1[j2 + i2 * k] = BiomeGenTropicraft.rainforestPlains.biomeID;
                    }
                    else
                    {
                    	ai1[j2 + i2 * k] = BiomeGenTropicraft.tropics.biomeID;
                    }
                } 
                else
                {
                	ai1[j2 + i2 * k] = BiomeGenTropicraft.tropicsOcean.biomeID;
                }
                
            }

        }

        return ai1;
    }
	
}
