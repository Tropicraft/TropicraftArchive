package tropicraft.world.chunk;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

public class ChunkProviderTest extends ChunkProviderTropicraft {

	private static final int CHUNK_SIZE_X = 16;
	private static final int CHUNK_SIZE_Y = 128;
	private static final int CHUNK_SIZE_Z = 16;
	
	private static final int MID_HEIGHT = 64;
	
	private World worldObj;
	
	public ChunkProviderTest(World worldObj, long seed, boolean par4) {
		super(worldObj, seed, par4);
		
		this.worldObj = worldObj;
	}
	
	public void generateTerrain(int chunkX, int chunkZ, byte[] idArray)
    {
		BiomeGenBase[] biomes = null;
        biomes = this.worldObj.getWorldChunkManager().getBiomesForGeneration(biomes, chunkX * 4 - 2, chunkZ * 4 - 2, 10, 10);
		biomes = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(biomes, chunkX * CHUNK_SIZE_X, chunkZ * CHUNK_SIZE_X, CHUNK_SIZE_X, CHUNK_SIZE_Z);
		for(int x = 0; x < CHUNK_SIZE_X; x++)
		{
			for(int z = 0; z < CHUNK_SIZE_Z; z++)
			{
				double yDev = biomes[x * 16 + z].biomeID + 20;

				for(int y = 0; y < CHUNK_SIZE_Y; y++)
				{
					if(y < yDev)
					{
						idArray[this.getIndex(x, y, z)] = (byte)Block.stone.blockID;
					}
				}
			}
		}
    }   
	
	public Chunk provideChunk(int par1, int par2)
    {
        this.rand.setSeed((long)par1 * 341873128712L + (long)par2 * 132897987541L);
        
        byte[] abyte = new byte[32768];
        
        this.generateTerrain(par1, par2, abyte);

        Chunk chunk = new Chunk(this.worldObj, abyte, par1, par2);
        
        chunk.generateSkylightMap();
        return chunk;
    }
	
	@Override
	public void populate(IChunkProvider par1IChunkProvider, int x, int z)
	{
		
	}
	
	public int getIndex(int x, int y, int z)
	{
		return (z * 16 + x) * 128 + y;
	}

}
