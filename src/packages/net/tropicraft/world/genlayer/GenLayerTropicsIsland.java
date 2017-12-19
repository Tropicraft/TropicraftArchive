package net.tropicraft.world.genlayer;

import net.minecraft.world.gen.layer.IntCache;

public class GenLayerTropicsIsland extends GenLayerTropicraft {
	
	public GenLayerTropicsIsland(long l)
	{
		super(l);
	}
	
    @Override
	public int[] getInts(int i, int j, int k, int l)
    {
        int ai[] = IntCache.getIntCache(k * l);
        for(int i1 = 0; i1 < l; i1++)
        {
            for(int j1 = 0; j1 < k; j1++)
            {
                super.initChunkSeed(i + j1, j + i1);
                ai[j1 + i1 * k] = nextInt(10) != 0 ? 60 : 61;
            }

        }

        if(i > -k && i <= 0 && j > -l && j <= 0)
        {
            ai[-i + -j * k] = 61;
        }
        
        return ai;
    }
	
}
