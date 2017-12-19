package net.tropicraft.world.genlayer;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import net.tropicraft.world.biomes.BiomeGenTropicraft;

public class GenLayerTropicsBeach extends GenLayerTropicraft {

	public GenLayerTropicsBeach(long l, GenLayer g) {
		super(l);
		parent = g;
	}

    @Override
	public int[] getInts(int i, int j, int k, int l) {
        int i1 = i - 1;
        int j1 = j - 1;
        int k1 = k + 2;
        int l1 = l + 2;
        int ai[] = parent.getInts(i1, j1, k1, l1);
        int ai1[] = IntCache.getIntCache(k * l);
        for(int i2 = 0; i2 < l; i2++)
        {
            for(int j2 = 0; j2 < k; j2++)
            {
                int k3 = ai[j2 + 1 + (i2 + 1) * k1];
            	if(k3 == BiomeGenTropicraft.tropicsOcean.biomeID)
            	{
	                int k2 = ai[j2 + 0 + (i2 + 1) * k1];
	                int l2 = ai[j2 + 2 + (i2 + 1) * k1];
	                int i3 = ai[j2 + 1 + (i2 + 0) * k1];
	                int j3 = ai[j2 + 1 + (i2 + 2) * k1];
                    if(k2 != BiomeGenTropicraft.tropicsOcean.biomeID || l2 != BiomeGenTropicraft.tropicsOcean.biomeID || i3 != BiomeGenTropicraft.tropicsOcean.biomeID || j3 != BiomeGenTropicraft.tropicsOcean.biomeID)
                    {
                        ai1[j2 + i2 * k] = BiomeGenTropicraft.beach.biomeID;
                    } else
                    {
                        ai1[j2 + i2 * k] = k3;
                    }
                } else
                {
                    ai1[j2 + i2 * k] = k3;
                }
            }

        }

        return ai1;
	}

}
