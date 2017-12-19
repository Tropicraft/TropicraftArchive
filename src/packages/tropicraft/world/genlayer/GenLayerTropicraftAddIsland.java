package tropicraft.world.genlayer;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import tropicraft.world.biomes.BiomeGenTropicraft;

public class GenLayerTropicraftAddIsland extends GenLayerTropicraft {
	
	private int oceanID = BiomeGenTropicraft.tropicsOcean.biomeID;
	private int landID = BiomeGenTropicraft.tropics.biomeID;
	private int num;
	
	public GenLayerTropicraftAddIsland(long i, GenLayer parent, int j, int landID) {
		super(i);
		this.parent = parent;
		this.num = j;
		this.landID = landID;
	}

	@Override
	public int[] getInts(int i, int j, int k, int l) {
		int i1 = i - 1;
        int j1 = j - 1;
        int k1 = k + 2;
        int l1 = l + 2;
        int[] aint = this.parent.getInts(i1, j1, k1, l1);
        int[] aint1 = IntCache.getIntCache(k * l);

        for (int i2 = 0; i2 < l; ++i2)
        {
            for (int j2 = 0; j2 < k; ++j2)
            {
                int k3 = aint[j2 + 1 + (i2 + 1) * k1];
                this.initChunkSeed((long)(j2 + i), (long)(i2 + j));

                if(this.nextInt(num) == 0)
                {
                    aint1[j2 + i2 * k] = landID;
                }
                else
                {
                    aint1[j2 + i2 * k] = k3;
                }
            }
        }

        return aint1;
	}
}
