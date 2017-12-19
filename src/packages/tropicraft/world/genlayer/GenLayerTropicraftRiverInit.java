package tropicraft.world.genlayer;

import tropicraft.world.biomes.BiomeGenTropicraft;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerTropicraftRiverInit extends GenLayerTropicraft {

	private int oceanID = BiomeGenTropicraft.tropicsOcean.biomeID;
	private int landID = BiomeGenTropicraft.tropics.biomeID;
	
	public GenLayerTropicraftRiverInit(long par1) {
		super(par1);
	}
	
	public int[] getInts(int par1, int par2, int par3, int par4)
    {
        int[] aint1 = IntCache.getIntCache(par3 * par4);

        for (int i1 = 0; i1 < par4; ++i1)
        {
            for (int j1 = 0; j1 < par3; ++j1)
            {
                this.initChunkSeed((long)(j1 + par1), (long)(i1 + par2));
                aint1[j1 + i1 * par3] = this.nextInt(2) + 1;
            }
        }

        return aint1;
    }
	
}

