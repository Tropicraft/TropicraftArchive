package net.tropicraft.world.genlayer;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerTropicsExpand extends GenLayerTropicraft{

	public GenLayerTropicsExpand(long l, GenLayer p) {
		super(l);
		parent = p;
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
                int k2 = ai[j2 + 0 + (i2 + 0) * k1];
                int l2 = ai[j2 + 2 + (i2 + 0) * k1];
                int i3 = ai[j2 + 0 + (i2 + 2) * k1];
                int j3 = ai[j2 + 2 + (i2 + 2) * k1];
                int k3 = ai[j2 + 1 + (i2 + 1) * k1];
                initChunkSeed(j2 + i, i2 + j);
                if(k3 == 60 && (k2 != 60 || l2 != 60 || i3 != 60 || j3 != 60))
                {
                	ai1[j2 + i2 * k] = nextInt(6) + 60;
                }
                else
                {
                	ai1[j2 + i2 * k] = k3;
                }
                
            }
        }
        return ai1;
	}
	
	
}
