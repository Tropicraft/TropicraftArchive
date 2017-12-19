package net.tropicraft.world.mapgen;

import net.minecraft.block.Block;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.tropicraft.mods.TropicraftMod;
import net.tropicraft.world.biomes.BiomeGenTropicraft;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MapGenVolcano {
    
    protected HashMap coordMap = new HashMap();
    
    public static List<BiomeGenBase> volcanoSpawnBiomes = Arrays.asList(new BiomeGenBase[] {
        BiomeGenTropicraft.rainforestPlains,
        BiomeGenTropicraft.tropics
    });
	
	public byte[] generate(World worldObj, int i, int k, byte[] bytes)
	{
		
        ChunkCoordinates volcanoCoords = getVolcanoNear(worldObj, i, k);
        
        if (volcanoCoords == null) {
            return bytes;
        }

		int[] heightmap = new int[256];
		
		for(int x = 0; x < 16; x++)
		{
			for(int z = 0; z < 16; z++)
			{
				for(int y = 0; y < 127; y++)
				{
					int index = (x * 16 + z) * 128 + y;
					if(bytes[index] == 0 || bytes[index] == Block.waterStill.blockID)
					{
						heightmap[x * 16 + z] = y;
						break;
					}
					if(y > 75)
					{ 
						heightmap[x * 16 + z] = y;
						break;
					}
				}
			}
		}

        Random rand = new Random(worldObj.getSeed() * ((i * 42) + (k * 453)));

        i *= 16;
        k *= 16;

        int volcCenterX = volcanoCoords.posX;
        int volcCenterZ = volcanoCoords.posZ;
        int radius = volcanoCoords.posY;

        for(int x = 0; x < 16; x++)
        {
            for(int z = 0; z < 16; z++)
            {
                int x2 = x + i;
                int z2 = z + k;

                double distance = Math.sqrt(Math.pow(x2 - volcCenterX, 2) + Math.pow(z2 - volcCenterZ, 2));

                int tY = heightmap[x * 16 + z];

                if(distance < radius)
                {
                    double d1 = radius - distance;
                    int yUp = (int)Math.pow(0.5 * d1, 2);
                    if(yUp + tY > 100)
                    {
                        for(int y = 0; y < 127; y++)
                        {
                            int index = (x * 16 + z) * 128 + y;
                            if(y == 90 && rand.nextInt(20) != 0)
                            {
                                bytes[index] = (byte)TropicraftMod.chunkBlock.blockID;
                            }
                            else if(y < 80)
                            {
                                bytes[index] = (byte)Block.lavaStill.blockID;
                            }
                            else
                            {
                                bytes[index] = 0;
                            }
                        }
                    }
                    else
                    {
                        if(tY + yUp > 95)
                            yUp = 95 - tY;
                        for(int y = 0; y < 127; y++)
                        {
                            int index = (x * 16 + z) * 128 + y;
                            if(y < tY + yUp)
                            {
                                bytes[index] = (byte)TropicraftMod.chunkBlock.blockID;
                            }
                        }
                    }
                }
            }
        }
		
		return bytes;
	}
	
    /**
     * Method to choose spawn locations for volcanos (borrowed from village gen)
     * Rarity is determined by the numChunks/offsetChunks vars (smaller numbers
     * mean more spawning)
     */
    protected boolean canGenVolcanoAtCoords(World worldObj, int i, int j)
    {
        byte numChunks = 32;
        byte offsetChunks = 8;
        int oldi = i;
        int oldj = j;

        if (i < 0)
        {
            i -= numChunks - 1;
        }

        if (j < 0)
        {
            j -= numChunks - 1;
        }

        int randX = i / numChunks;
        int randZ = j / numChunks;
        long seed = (long)randX * 341873128712L + (long)randZ * 132897987541L + worldObj.getWorldInfo().getSeed() + (long)4291726;
        Random rand = new Random(seed);
        randX *= numChunks;
        randZ *= numChunks;
        randX += rand.nextInt(numChunks - offsetChunks);
        randZ += rand.nextInt(numChunks - offsetChunks);

        if (oldi == randX && oldj == randZ)
        {
            return worldObj.getWorldChunkManager().areBiomesViable(oldi * 16 + 8, oldj * 16 + 8, 0, volcanoSpawnBiomes);
        }

        return false;
    }

    /**
     * Returns the coordinates of a volcano if it should be spawned near
     * this chunk, otherwise returns null.
     * The posY of the returned object should be used as the volcano radius
     */
    public ChunkCoordinates getVolcanoNear(World worldObj, int i, int j) {
        //Check 2 chunks in each direction (volcanoes are never more than 3 chunks wide)
        int range = 2;
        for(int x = i - range; x <= i + range; x++) {
            for(int z = j - range; z <= j + range; z++) {
                if (canGenVolcanoAtCoords(worldObj, x, z)) {
                    long seed = (long)x * 341873128712L + (long)z * 132897987541L + worldObj.getWorldInfo().getSeed() + (long)4291726;
                    Random rand = new Random(seed);
                    int radius = rand.nextInt(10) + 20;
                    return new ChunkCoordinates(x * 16 + 8, radius, z * 16 + 8);
                }
            }
        }
        
        return null;
    }
   
}