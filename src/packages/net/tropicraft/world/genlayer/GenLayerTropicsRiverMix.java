package net.tropicraft.world.genlayer;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerTropicsRiverMix extends GenLayerTropicraft {

    private GenLayer parent;
    private GenLayer river;

	public GenLayerTropicsRiverMix(long l, GenLayer genlayer, GenLayer genlayer1) {
		super(l);
        parent = genlayer;
        river = genlayer1;
	}

    @Override
    public void initWorldGenSeed(long l)
    {
        parent.initWorldGenSeed(l);
        river.initWorldGenSeed(l);
        super.initWorldGenSeed(l);
    }

    @Override
    public int[] getInts(int i, int j, int k, int l)
    {
        int ai[] = parent.getInts(i, j, k, l);
        int ai1[] = river.getInts(i, j, k, l);
        int ai2[] = IntCache.getIntCache(k * l);
        for(int i1 = 0; i1 < k * l; i1++)
        {
            if(ai[i1] == 60)
            {
                ai2[i1] = ai[i1];
                continue;
            }
            if(ai1[i1] >= 0)
            {
                    ai2[i1] = ai1[i1];
            } else
            {
                ai2[i1] = ai[i1];
            }
        }

        return ai2;
    }
}
