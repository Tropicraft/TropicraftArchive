package net.tropicraft.world;

import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import net.tropicraft.world.biomes.BiomeGenTropicraft;
import net.tropicraft.world.genlayer.GenLayerTropicraft;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldChunkManagerTropics extends WorldChunkManager
{

    private GenLayer field_34903_b;
    private GenLayer biomeIndexLayer;
    private GenLayer temperatureLayer;
    private GenLayer rainfallLayer;
    private BiomeCache biomeCache;
    private ArrayList<BiomeGenBase> biomesToSpawnIn;
    public BiomeGenBase field_4195_d[];
    public float field_40541_b[];

    protected WorldChunkManagerTropics()
    {
        biomeCache = new BiomeCache(this);
        biomesToSpawnIn = new ArrayList<BiomeGenBase>();
        biomesToSpawnIn.add(BiomeGenTropicraft.tropics);
    }

    public WorldChunkManagerTropics(World world)
    {
        this();
        GenLayer agenlayer[] = GenLayerTropicraft.getGenLayers(world.getSeed());
        field_34903_b = agenlayer[0];
        biomeIndexLayer = agenlayer[1];
        temperatureLayer = agenlayer[2];
        rainfallLayer = agenlayer[3];
    }

    @Override
    public List getBiomesToSpawnIn()
    {
        return biomesToSpawnIn;
    }

    public BiomeGenBase getBiomeGenAtChunkCoord(ChunkCoordIntPair chunkcoordintpair)
    {
        return getBiomeGenAt(chunkcoordintpair.chunkXPos << 4, chunkcoordintpair.chunkZPos << 4);
    }

    @Override
    public BiomeGenBase getBiomeGenAt(int i, int j)
    {
        return biomeCache.getBiomeGenAt(i, j);
    }

    /*public float getRainfall(int i, int j)
    {
        return biomeCache.getRainfall(i, j);
    }*/

    /**
     * Returns a list of rainfall values for the specified blocks. Args: listToReuse, x, z, width, length.
     */
    @Override
    public float[] getRainfall(float[] par1ArrayOfFloat, int par2, int par3, int par4, int par5)
    {
        IntCache.resetIntCache();

        if (par1ArrayOfFloat == null || par1ArrayOfFloat.length < par4 * par5)
        {
            par1ArrayOfFloat = new float[par4 * par5];
        }

        int[] var6 = this.biomeIndexLayer.getInts(par2, par3, par4, par5);

        for (int var7 = 0; var7 < par4 * par5; ++var7)
        {
        	if (var6[var7] < 0) {
        		System.out.println("-1 ERROR IN getRainfall()");
        		continue; //Coro bandaid fix #1 for weird -1 index crash
        	}
        	
            float var8 = (float)BiomeGenBase.biomeList[var6[var7]].getIntRainfall() / 65536.0F;

            if (var8 > 1.0F)
            {
                var8 = 1.0F;
            }

            par1ArrayOfFloat[var7] = var8;
        }

        return par1ArrayOfFloat;
    }
    
    /**
     * Return an adjusted version of a given temperature based on the y height
     */
    @Override
    public float getTemperatureAtHeight(float par1, int par2)
    {
        return par1;
    }

  /*  public float[] initTemperatureCache(int i, int j, int k, int l)
    {
        temperatureCache = getTemperatures(temperatureCache, i, j, k, l);
        return temperatureCache;
    }
    */
    /**
     * Returns a list of temperatures to use for the specified blocks.  Args: listToReuse, x, y, width, length
     */
    @Override
    public float[] getTemperatures(float[] par1ArrayOfFloat, int par2, int par3, int par4, int par5)
    {
        IntCache.resetIntCache();

        if (par1ArrayOfFloat == null || par1ArrayOfFloat.length < par4 * par5)
        {
            par1ArrayOfFloat = new float[par4 * par5];
        }

        int[] var6 = this.biomeIndexLayer.getInts(par2, par3, par4, par5);

        for (int var7 = 0; var7 < par4 * par5; ++var7)
        {
        	if (var6[var7] < 0) {
        		System.out.println("-1 ERROR IN getTemperatures()");
        		continue; //Coro bandaid fix #2 for weird -1 index crash
        	}
        	
            float var8 = (float)BiomeGenBase.biomeList[var6[var7]].getIntTemperature() / 65536.0F;

            if (var8 > 1.0F)
            {
                var8 = 1.0F;
            }

            par1ArrayOfFloat[var7] = var8;
        }

        return par1ArrayOfFloat;
    }

    @Override
    public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase abiomegenbase[], int i, int j, int k, int l)
    {
        IntCache.resetIntCache();
        if(abiomegenbase == null || abiomegenbase.length < k * l)
        {
            abiomegenbase = new BiomeGenTropicraft[k * l];
        }
        int ai[] = field_34903_b.getInts(i, j, k, l);
        for(int i1 = 0; i1 < k * l; i1++)
        {
            abiomegenbase[i1] = BiomeGenTropicraft.tropiBiomeList[ai[i1]];
        }

        return abiomegenbase;
    }

    public BiomeGenBase[] loadRendererData(int i, int j, int k, int l)
    {
        if(k == 16 && l == 16 && (i & 0xf) == 0 && (j & 0xf) == 0)
        {
            return biomeCache.getCachedBiomes(i, j);
        } else
        {
            field_4195_d = loadBlockGeneratorData(field_4195_d, i, j, k, l);
            return field_4195_d;
        }
    }

    @Override
    public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase abiomegenbase[], int i, int j, int k, int l)
    {
        return getBiomeGenAt(abiomegenbase, i, j, k, l, true);
    }

    @Override
    public BiomeGenBase[] getBiomeGenAt(BiomeGenBase abiomegenbase[], int i, int j, int k, int l, boolean flag)
    {
        IntCache.resetIntCache();
        if(abiomegenbase == null || abiomegenbase.length < k * l)
        {
            abiomegenbase = new BiomeGenTropicraft[k * l];
        }
        if(flag && k == 16 && l == 16 && (i & 0xf) == 0 && (j & 0xf) == 0)
        {
            BiomeGenBase abiomegenbase1[] = biomeCache.getCachedBiomes(i, j);
            BiomeGenTropicraft biomeTropics[] = new BiomeGenTropicraft[abiomegenbase.length];
            for(int a = 0; a < abiomegenbase1.length; a++)
           	{
            	biomeTropics[a] = (BiomeGenTropicraft)abiomegenbase1[a];
       		}
            System.arraycopy(abiomegenbase1, 0, abiomegenbase, 0, k * l);
            return abiomegenbase;
        }
        int ai[] = biomeIndexLayer.getInts(i, j, k, l);
        for(int i1 = 0; i1 < k * l; i1++)
        {
        	if (ai[i1] < 0)
            {
                System.out.println("-1 ERROR IN getBiomeGenAt()");
                continue; //Coro bandaid fix #2 for weird -1 index crash
            }
            abiomegenbase[i1] = BiomeGenTropicraft.tropiBiomeList[ai[i1]];
        }

        return abiomegenbase;
    }

    @Override
    public boolean areBiomesViable(int i, int j, int k, List list)
    {
        int l = i - k >> 2;
        int i1 = j - k >> 2;
        int j1 = i + k >> 2;
        int k1 = j + k >> 2;
        int l1 = (j1 - l) + 1;
        int i2 = (k1 - i1) + 1;
        int ai[] = field_34903_b.getInts(l, i1, l1, i2);
        for(int j2 = 0; j2 < l1 * i2; j2++)
        {
            BiomeGenBase biomegenbase = BiomeGenTropicraft.tropiBiomeList[ai[j2]];
            if(!list.contains(biomegenbase))
            {
                return false;
            }
        }

        return true;
    }

    @Override
    public ChunkPosition findBiomePosition(int i, int j, int k, List list, Random random)
    {
        int l = i - k >> 2;
        int i1 = j - k >> 2;
        int j1 = i + k >> 2;
        int k1 = j + k >> 2;
        int l1 = (j1 - l) + 1;
        int i2 = (k1 - i1) + 1;
        int ai[] = field_34903_b.getInts(l, i1, l1, i2);
        ChunkPosition chunkposition = null;
        int j2 = 0;
        for(int k2 = 0; k2 < ai.length; k2++)
        {
            int l2 = l + k2 % l1 << 2;
            int i3 = i1 + k2 / l1 << 2;
            BiomeGenBase biomegenbase = BiomeGenTropicraft.tropiBiomeList[ai[k2]];
            if(list.contains(biomegenbase) && (chunkposition == null || random.nextInt(j2 + 1) == 0))
            {
                chunkposition = new ChunkPosition(l2, 0, i3);
                j2++;
            }
        }

        return chunkposition;
    }

    @Override
    public void cleanupCache()
    {
        biomeCache.cleanupCache();
    }
}/*
getRainfallresetIntCachegetIntsgetTemperatureresetIntCachegetIntsresetIntCachegetIntsgetCachedBiomesresetIntCachegetCachedBiomesgetIntsgetIntsgetIntscleanupCache*/