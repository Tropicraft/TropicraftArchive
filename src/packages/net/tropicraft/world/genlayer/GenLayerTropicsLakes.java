package net.tropicraft.world.genlayer;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import net.tropicraft.world.biomes.BiomeGenTropicraft;

public class GenLayerTropicsLakes extends GenLayerTropicraft {
	
	 public GenLayerTropicsLakes(long l, GenLayer genlayer)
	 {
	    super(l);
	    parent = genlayer;
	 }

     @Override
	 public int[] getInts(int par1, int par2, int par3, int par4)
     {
        int var5 = par1 - 1;
        int var6 = par2 - 1;
        int var7 = par3 + 2;
        int var8 = par4 + 2;
        int[] var9 = this.parent.getInts(var5, var6, var7, var8);
        int[] var10 = IntCache.getIntCache(par3 * par4);

        for (int var11 = 0; var11 < par4; ++var11)
        {
            for (int var12 = 0; var12 < par3; ++var12)
            {
                int var13 = var9[var12 + 0 + (var11 + 0) * var7];
                int var14 = var9[var12 + 2 + (var11 + 0) * var7];
                int var15 = var9[var12 + 0 + (var11 + 2) * var7];
                int var16 = var9[var12 + 2 + (var11 + 2) * var7];
                int var17 = var9[var12 + 1 + (var11 + 1) * var7];
                this.initChunkSeed((long)(var12 + par1), (long)(var11 + par2));

                if (var17 != 60 && var13 != 60 && var14 != 60 && var15 != 60 && var16 != 60 && (this.nextInt(700) == 0 || this.nextInt(850) == 0))
                {
                    var10[var12 + var11 * par3] = BiomeGenTropicraft.lake.biomeID;
                }
                else
                {
                    var10[var12 + var11 * par3] = var17;
                }
            }
        }

        return var10;
     }
}