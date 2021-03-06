package tropicraft.world.genlayer;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import tropicraft.world.biomes.BiomeGenTropicraft;

public class GenLayerTropicraftAddHills extends GenLayerTropicraft {
	
	public GenLayerTropicraftAddHills(long par1, GenLayer parent) {
		super(par1);
		this.parent = parent;
	}

	@Override
	public int[] getInts(int i, int j, int k, int l) {
		int[] aint = this.parent.getInts(i, j, k, l);
        int[] aint1 = IntCache.getIntCache(k * l);

        for (int i1 = 0; i1 < l; ++i1)
        {
            for (int j1 = 0; j1 < k; ++j1)
            {
                this.initChunkSeed((long)(j1 + i), (long)(i1 + j));
                int k1 = aint[j1 + i1 * k];

                if (k1 == BiomeGenTropicraft.rainforestPlains.biomeID)
                {
                    aint1[j1 + i1 * k] = k1 + nextInt(3);
                }
                else
                {
                    aint1[j1 + i1 * k] = k1;
                }
            }
        }

        return aint1;
	}
	
}
