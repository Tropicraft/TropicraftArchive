package tropicraft.world.mapgen;

import net.minecraft.block.Block;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import tropicraft.blocks.TropicraftBlocks;
import tropicraft.world.biomes.BiomeGenTropicraft;
import tropicraft.world.perlin.NoiseModule;
import tropicraft.world.perlin.generator.Billowed;
import tropicraft.world.perlin.generator.Gradient;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MapGenVolcano {
    
    protected HashMap coordMap = new HashMap();
    
    public static List<BiomeGenBase> volcanoSpawnBiomesLand = Arrays.asList(new BiomeGenBase[] {
        BiomeGenTropicraft.tropics
    });
    public static List<BiomeGenBase> volcanoSpawnBiomesOcean = Arrays.asList(new BiomeGenBase[] {
            BiomeGenTropicraft.tropicsOcean
        });
    
    private World worldObj;
    
    private boolean useArrays;
    
    private final int CHUNK_SIZE_X = 16;
    private final int CHUNK_SIZE_Z = 16;
    private final int CHUNK_SIZE_Y = 127;
    private final int MAX_RADIUS = 40;
    private final int MIN_RADIUS = 30;
    private final int LAND_STEEPNESS_MOD = 6;
    private final int OCEAN_STEEPNESS_MOD = 11;
    private final int CALDERA_CUTOFF = 105; //The Y level where if the height of the volcano would pass becomes the caldera
    private final int VOLCANO_TOP = 98; //The Y level cut off of the sides of the volcano
    private final int VOLCANO_CRUST = 95; //The Y level where the crust of the volcano generates
    private final int LAVA_LEVEL = 90; //The Y level where the top of the lava column is
    private final int CRUST_HOLE_CHANCE = 15; //1 / x chance a certain block of the crust will be missing
    
    public MapGenVolcano(World worldObj, boolean useArrays)
    {
    	this.worldObj = worldObj;
    	this.useArrays = useArrays;
    }
    
	public short[] generate(int i, int k, short[] blocks, byte[] metas)
	{
        ChunkCoordinates volcanoCoords = getVolcanoNear(worldObj, i, k);
        
        if (volcanoCoords == null) {
            return blocks;
        }
		int[] heightmap = new int[256];

		for(int x = 0; x < 16; x++)
		{
			for(int z = 0; z < 16; z++)
			{
				for(int y = 0; y < 127; y++)
				{
					int blockID = getBlock(x, y, z, blocks);
					if(blockID == 0 || blockID == TropicraftBlocks.tropicsWaterStationary.blockID)
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

        i *= CHUNK_SIZE_X;
        k *= CHUNK_SIZE_Z;

        int volcCenterX = volcanoCoords.posX;
        int volcCenterZ = volcanoCoords.posZ;
        int steepnessMod = volcanoCoords.posY == 1 ? LAND_STEEPNESS_MOD : OCEAN_STEEPNESS_MOD;
        
        long seed = (long)volcCenterX * 341873128712L + (long)volcCenterZ * 132897987541L + worldObj.getWorldInfo().getSeed() + (long)4291726;
        Random rand = new Random(seed);
        
        int radius = rand.nextInt(MAX_RADIUS - MIN_RADIUS) + MIN_RADIUS;
        
        NoiseModule volcNoise = new Billowed(seed, 1, 1);
        volcNoise.amplitude = 13;

        for(int x = 0; x < CHUNK_SIZE_X; x++)
        {
            for(int z = 0; z < CHUNK_SIZE_Z; z++)
            {
                float relativeX = ((x + i) - volcCenterX) / (float)radius;
                float relativeZ = ((z + k) - volcCenterZ) / (float)radius;
                
                float distanceSquared = relativeX * relativeX + relativeZ * relativeZ;
                
                float perlin = (float)volcNoise.getNoise(relativeX * 5F + 0.0001, relativeZ * 5F + 0.0001);
                
                float volcanoHeight = (steepnessMod / (distanceSquared + 0.00001F)) - 5 - perlin;
                
                int groundHeight = heightmap[x * 16 + z];
                if(distanceSquared < radius * radius)
                {
	                for(int y = CHUNK_SIZE_Y; y > 0; y--)
	                {
	                	if(volcanoHeight + groundHeight < CALDERA_CUTOFF)
	                	{
	                		if(volcanoHeight + groundHeight <= VOLCANO_TOP)
	                		{
	                			if(y <= volcanoHeight + groundHeight && y >= groundHeight)
		                		{
		                			placeBlock(x, y, z, TropicraftBlocks.chunkOHead.blockID, blocks);
		                		}
	                		}
	                		else
	                		{
	                			if(y <= VOLCANO_TOP)
	                			{
		                			placeBlock(x, y, z, TropicraftBlocks.chunkOHead.blockID, blocks);
	                			}
	                		}
	                	}
	                	else
	                	{
	                		if(y == VOLCANO_CRUST && rand.nextInt(CRUST_HOLE_CHANCE) != 0)
	                		{
	                			placeBlock(x, y, z, TropicraftBlocks.chunkOHead.blockID, blocks);
	                		}
	                		if(y <= LAVA_LEVEL)
	                		{
	                			placeBlock(x, y, z, Block.lavaStill.blockID, blocks);
	                		}
	                	}
	                }
                }
            }
        }
		
		return blocks;
	}
	
	public void generate(int i, int k)
	{              
        int volcCenterX = i;
        int volcCenterZ = k;
        int steepnessMod = worldObj.getBiomeGenForCoords(i, k) == BiomeGenTropicraft.tropics ? LAND_STEEPNESS_MOD : OCEAN_STEEPNESS_MOD;
        
        long seed = (long)volcCenterX * 341873128712L + (long)volcCenterZ * 132897987541L + worldObj.getWorldInfo().getSeed() + (long)4291726;
        Random rand = new Random(seed);
        
        int radius = rand.nextInt(MAX_RADIUS - MIN_RADIUS) + MIN_RADIUS;
        
		int[] heightmap = new int[(radius * 2) * (radius * 2)];
        
		//System.out.println(radius);

		for(int x = -radius; x < radius; x++)
		{
			for(int z = -radius; z < radius; z++)
			{
				for(int y = 127; y > 0; y--)
				{
					int blockID = getBlock(x + i, y, z + k, null);
					if(blockID == Block.sand.blockID || blockID == Block.dirt.blockID || blockID == Block.grass.blockID)
					{
						heightmap[(x + radius) * 16 + (z + radius)] = y;
						break;
					}
					if(y < 30)
					{ 
						heightmap[(x + radius) * 16 + (z + radius)] = y;
						break;
					}
				}
			}
		}
        
        NoiseModule volcNoise = new Billowed(seed, 1, 1);
        volcNoise.amplitude = 15;

        for(int x = -radius; x < radius; x++)
        {
            for(int z = -radius; z < radius; z++)
            {
                float relativeX = x / (float)radius;
                float relativeZ = z / (float)radius;
                
                float distanceSquared = relativeX * relativeX + relativeZ * relativeZ;
                
                float perlin = (float)volcNoise.getNoise(relativeX * 5F + 0.0001, relativeZ * 5F + 0.0001);
                
                float volcanoHeight = (steepnessMod / (distanceSquared + 0.00001F)) - 5 - perlin;
                
                int groundHeight = heightmap[(x + radius) * 16 + (z + radius)];
                
                if(distanceSquared < radius * radius)
                {
	                for(int y = CHUNK_SIZE_Y; y > 0; y--)
	                {
	                	if(volcanoHeight + groundHeight < CALDERA_CUTOFF)
	                	{
	                		if(volcanoHeight + groundHeight <= VOLCANO_TOP)
	                		{
	                			if(y <= volcanoHeight + groundHeight && y >= groundHeight)
		                		{
		                			placeBlock(x + i, y, z + k, TropicraftBlocks.chunkOHead.blockID, null);
		                		}
	                		}
	                		else
	                		{
	                			if(y <= VOLCANO_TOP)
	                			{
		                			placeBlock(x + i, y, z + k, TropicraftBlocks.chunkOHead.blockID, null);
	                			}
	                		}
	                	}
	                	else
	                	{
	                		if(y == VOLCANO_CRUST && rand.nextInt(CRUST_HOLE_CHANCE) != 0)
	                		{
	                			placeBlock(x + i, y, z + k, TropicraftBlocks.chunkOHead.blockID, null);
	                		}
	                		if(y <= LAVA_LEVEL)
	                		{
	                			placeBlock(x + i, y, z + k, Block.lavaStill.blockID, null);
	                		}
	                	}
	                }
                }
            }
        }
	}
	
	public void placeBlock(int x, int y, int z, int blockID, short[] blocks)
	{
		if(useArrays)
		{
			blocks[y << 8 | z << 4 | x] = (short)(blockID & 0xFFFF);
		}
		else
		{
			worldObj.setBlock(x, y, z, blockID);
		}
	}
	
	public int getBlock(int x, int y, int z, short[] blocks)
	{
		if(useArrays)
		{
			return blocks[y << 8 | z << 4 | x];
		}
		else
		{
			return worldObj.getBlockId(x, y, z);
		}
	}
	
    /**
     * Method to choose spawn locations for volcanos (borrowed from village gen)
     * Rarity is determined by the numChunks/offsetChunks vars (smaller numbers
     * mean more spawning)
     */
    protected int canGenVolcanoAtCoords(World worldObj, int i, int j)
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
            if(worldObj.getWorldChunkManager().areBiomesViable(oldi * 16 + 8, oldj * 16 + 8, 0, volcanoSpawnBiomesLand))
            {
            	return 1;
            }
            if(worldObj.getWorldChunkManager().areBiomesViable(oldi * 16 + 8, oldj * 16 + 8, 0, volcanoSpawnBiomesOcean))
            {
            	return 2;
            }
        }

        return 0;
    }

    /**
     * Returns the coordinates of a volcano if it should be spawned near
     * this chunk, otherwise returns null.
     * The posY of the returned object should be used as the volcano radius
     */
    public ChunkCoordinates getVolcanoNear(World worldObj, int i, int j) {
        //Check 2 chunks in each direction (volcanoes are never more than 3 chunks wide)
        int range = 3;
        for(int x = i - range; x <= i + range; x++) {
            for(int z = j - range; z <= j + range; z++) {
            	int biome = canGenVolcanoAtCoords(worldObj, x, z);
                if (biome != 0) {
                    return new ChunkCoordinates(x * 16 + 8, biome, z * 16 + 8);
                }
            }
        }
        
        return null;
    }
   
}