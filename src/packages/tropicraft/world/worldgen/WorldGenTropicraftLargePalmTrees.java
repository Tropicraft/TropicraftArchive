package tropicraft.world.worldgen;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import tropicraft.blocks.BlockTropicraftLog;
import tropicraft.blocks.TropicraftBlocks;

import java.util.Random;

public class WorldGenTropicraftLargePalmTrees extends WorldGenerator
{

	private int woodID =  TropicraftBlocks.treeWood.blockID;
	private int leafID = TropicraftBlocks.tropicsLeaves.blockID;
	
	public WorldGenTropicraftLargePalmTrees()
	{
		super();
	}

	public WorldGenTropicraftLargePalmTrees(boolean flag)
	{
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k)
	{
		int b = random.nextInt(2);
		byte height = (byte)(random.nextInt(4) + 7);
		boolean flag = true;
		if(j < 1 || j + height + 1 > 128)
		{
			return false;
		}
		for(int l = j; l <= j + 1 + height; l++)
		{
			byte byte1 = 1;
			if(l == j)
			{
				byte1 = 0;
			}
			if(l >= (j + 1 + height) - 2)
			{
				byte1 = 2;
			}
			for(int j1 = i - byte1; j1 <= i + byte1 && flag; j1++)
			{
				for(int k1 = k - byte1; k1 <= k + byte1 && flag; k1++)
				{
					if(l >= 0 && l < 128)
					{
						int l1 = world.getBlockId(j1, l, k1);
						if(l1 != 0 && l1 != TropicraftBlocks.tropicsLeaves.blockID)
						{
							flag = false;
						}
					} else
					{
						flag = false;
					}
				}

			}

		}

		if(!flag)
		{
			return false;
		}
		int i1 = world.getBlockId(i, j - 1, k);
		if(i1 != Block.sand.blockID || j >= 128 - height - 1)
		{
			int ground = world.getHeightValue(i, k);
			i1 = world.getBlockId(i, ground - 1, k);
			if(i1 != Block.sand.blockID || j >= 128 - height - 1)
			{
				return false;
			}
			j = ground;
		}
		for(int y = 0; y <= height; y++)
		{
			setBlockAndMetadata(world, i, (j + y), k, woodID, 0);
		}
		setBlockAndMetadata(world, i + 0, j + height + 1, k + -7, leafID, 0);
		setBlockAndMetadata(world, i + -1, j + height + 1, k + -6, leafID, 0);
		setBlockAndMetadata(world, i + 1, j + height + 1, k + -6, leafID, 0);
		setBlockAndMetadata(world, i + -5, j + height + 1, k + -5, leafID, 0);
		setBlockAndMetadata(world, i + 5, j + height + 1, k + -5, leafID, 0);
		setBlockAndMetadata(world, i + -6, j + height + 1, k + -1, leafID, 0);
		setBlockAndMetadata(world, i + 0, j + height + 1, k + -1, woodID, 0);
		setBlockAndMetadata(world, i + 6, j + height + 1, k + -1, leafID, 0);
		setBlockAndMetadata(world, i + -7, j + height + 1, k + 0, leafID, 0);
		setBlockAndMetadata(world, i + -1, j + height + 1, k + 0, woodID, 0);
		setBlockAndMetadata(world, i + 0, j + height + 1, k + 0, woodID, 0);
		setBlockAndMetadata(world, i + 1, j + height + 1, k + 0, woodID, 0);
		setBlockAndMetadata(world, i + 7, j + height + 1, k + 0, leafID, 0);
		setBlockAndMetadata(world, i + -6, j + height + 1, k + 1, leafID, 0);
		setBlockAndMetadata(world, i + 0, j + height + 1, k + 1, woodID, 0);
		setBlockAndMetadata(world, i + 6, j + height + 1, k + 1, leafID, 0);
		setBlockAndMetadata(world, i + -5, j + height + 1, k + 5, leafID, 0);
		setBlockAndMetadata(world, i + 5, j + height + 1, k + 5, leafID, 0);
		setBlockAndMetadata(world, i + -1, j + height + 1, k + 6, leafID, 0);
		setBlockAndMetadata(world, i + 1, j + height + 1, k + 6, leafID, 0);
		setBlockAndMetadata(world, i + 0, j + height + 1, k + 7, leafID, 0);
		setBlockAndMetadata(world, i + 0, j + height + 2, k + -6, leafID, 0);
		setBlockAndMetadata(world, i + -4, j + height + 2, k + -5, leafID, 0);
		setBlockAndMetadata(world, i + -1, j + height + 2, k + -5, leafID, 0);
		setBlockAndMetadata(world, i + 1, j + height + 2, k + -5, leafID, 0);
		setBlockAndMetadata(world, i + 4, j + height + 2, k + -5, leafID, 0);
		setBlockAndMetadata(world, i + -5, j + height + 2, k + -4, leafID, 0);
		setBlockAndMetadata(world, i + -3, j + height + 2, k + -4, leafID, 0);
		setBlockAndMetadata(world, i + -1, j + height + 2, k + -4, leafID, 0);
		setBlockAndMetadata(world, i + 1, j + height + 2, k + -4, leafID, 0);
		setBlockAndMetadata(world, i + 3, j + height + 2, k + -4, leafID, 0);
		setBlockAndMetadata(world, i + 5, j + height + 2, k + -4, leafID, 0);
		setBlockAndMetadata(world, i + -4, j + height + 2, k + -3, leafID, 0);
		setBlockAndMetadata(world, i + -2, j + height + 2, k + -3, leafID, 0);
		setBlockAndMetadata(world, i + -1, j + height + 2, k + -3, leafID, 0);
		setBlockAndMetadata(world, i + 1, j + height + 2, k + -3, leafID, 0);
		setBlockAndMetadata(world, i + 2, j + height + 2, k + -3, leafID, 0);
		setBlockAndMetadata(world, i + 4, j + height + 2, k + -3, leafID, 0);
		setBlockAndMetadata(world, i + -3, j + height + 2, k + -2, leafID, 0);
		setBlockAndMetadata(world, i + -1, j + height + 2, k + -2, leafID, 0);
		setBlockAndMetadata(world, i + 1, j + height + 2, k + -2, leafID, 0);
		setBlockAndMetadata(world, i + 3, j + height + 2, k + -2, leafID, 0);
		setBlockAndMetadata(world, i + -5, j + height + 2, k + -1, leafID, 0);
		setBlockAndMetadata(world, i + -4, j + height + 2, k + -1, leafID, 0);
		setBlockAndMetadata(world, i + -3, j + height + 2, k + -1, leafID, 0);
		setBlockAndMetadata(world, i + -2, j + height + 2, k + -1, leafID, 0);
		setBlockAndMetadata(world, i + -1, j + height + 2, k + -1, leafID, 0);
		setBlockAndMetadata(world, i + 0, j + height + 2, k + -1, leafID, 0);
		setBlockAndMetadata(world, i + 1, j + height + 2, k + -1, leafID, 0);
		setBlockAndMetadata(world, i + 2, j + height + 2, k + -1, leafID, 0);
		setBlockAndMetadata(world, i + 3, j + height + 2, k + -1, leafID, 0);
		setBlockAndMetadata(world, i + 4, j + height + 2, k + -1, leafID, 0);
		setBlockAndMetadata(world, i + 5, j + height + 2, k + -1, leafID, 0);
		setBlockAndMetadata(world, i + -6, j + height + 2, k + 0, leafID, 0);
		setBlockAndMetadata(world, i + -1, j + height + 2, k + 0, leafID, 0);
		setBlockAndMetadata(world, i + 0, j + height + 2, k + 0, leafID, 0);
		setBlockAndMetadata(world, i + 1, j + height + 2, k + 0, leafID, 0);
		setBlockAndMetadata(world, i + 6, j + height + 2, k + 0, leafID, 0);
		setBlockAndMetadata(world, i + -5, j + height + 2, k + 1, leafID, 0);
		setBlockAndMetadata(world, i + -4, j + height + 2, k + 1, leafID, 0);
		setBlockAndMetadata(world, i + -3, j + height + 2, k + 1, leafID, 0);
		setBlockAndMetadata(world, i + -2, j + height + 2, k + 1, leafID, 0);
		setBlockAndMetadata(world, i + -1, j + height + 2, k + 1, leafID, 0);
		setBlockAndMetadata(world, i + 0, j + height + 2, k + 1, leafID, 0);
		setBlockAndMetadata(world, i + 1, j + height + 2, k + 1, leafID, 0);
		setBlockAndMetadata(world, i + 2, j + height + 2, k + 1, leafID, 0);
		setBlockAndMetadata(world, i + 3, j + height + 2, k + 1, leafID, 0);
		setBlockAndMetadata(world, i + 4, j + height + 2, k + 1, leafID, 0);
		setBlockAndMetadata(world, i + 5, j + height + 2, k + 1, leafID, 0);
		setBlockAndMetadata(world, i + -3, j + height + 2, k + 2, leafID, 0);
		setBlockAndMetadata(world, i + -1, j + height + 2, k + 2, leafID, 0);
		setBlockAndMetadata(world, i + 1, j + height + 2, k + 2, leafID, 0);
		setBlockAndMetadata(world, i + 3, j + height + 2, k + 2, leafID, 0);
		setBlockAndMetadata(world, i + -4, j + height + 2, k + 3, leafID, 0);
		setBlockAndMetadata(world, i + -2, j + height + 2, k + 3, leafID, 0);
		setBlockAndMetadata(world, i + -1, j + height + 2, k + 3, leafID, 0);
		setBlockAndMetadata(world, i + 1, j + height + 2, k + 3, leafID, 0);
		setBlockAndMetadata(world, i + 2, j + height + 2, k + 3, leafID, 0);
		setBlockAndMetadata(world, i + 4, j + height + 2, k + 3, leafID, 0);
		setBlockAndMetadata(world, i + -5, j + height + 2, k + 4, leafID, 0);
		setBlockAndMetadata(world, i + -3, j + height + 2, k + 4, leafID, 0);
		setBlockAndMetadata(world, i + -1, j + height + 2, k + 4, leafID, 0);
		setBlockAndMetadata(world, i + 1, j + height + 2, k + 4, leafID, 0);
		setBlockAndMetadata(world, i + 3, j + height + 2, k + 4, leafID, 0);
		setBlockAndMetadata(world, i + 5, j + height + 2, k + 4, leafID, 0);
		setBlockAndMetadata(world, i + -4, j + height + 2, k + 5, leafID, 0);
		setBlockAndMetadata(world, i + -1, j + height + 2, k + 5, leafID, 0);
		setBlockAndMetadata(world, i + 1, j + height + 2, k + 5, leafID, 0);
		setBlockAndMetadata(world, i + 4, j + height + 2, k + 5, leafID, 0);
		setBlockAndMetadata(world, i + 0, j + height + 2, k + 6, leafID, 0);
		setBlockAndMetadata(world, i + 0, j + height + 3, k + -5, leafID, 0);
		setBlockAndMetadata(world, i + -4, j + height + 3, k + -4, leafID, 0);
		setBlockAndMetadata(world, i + 0, j + height + 3, k + -4, leafID, 0);
		setBlockAndMetadata(world, i + 4, j + height + 3, k + -4, leafID, 0);
		setBlockAndMetadata(world, i + -3, j + height + 3, k + -3, leafID, 0);
		setBlockAndMetadata(world, i + 0, j + height + 3, k + -3, leafID, 0);
		setBlockAndMetadata(world, i + 3, j + height + 3, k + -3, leafID, 0);
		setBlockAndMetadata(world, i + -2, j + height + 3, k + -2, leafID, 0);
		setBlockAndMetadata(world, i + 0, j + height + 3, k + -2, leafID, 0);
		setBlockAndMetadata(world, i + 2, j + height + 3, k + -2, leafID, 0);
		setBlockAndMetadata(world, i + -1, j + height + 3, k + -1, leafID, 0);
		setBlockAndMetadata(world, i + 0, j + height + 3, k + -1, leafID, 0);
		setBlockAndMetadata(world, i + 1, j + height + 3, k + -1, leafID, 0);
		setBlockAndMetadata(world, i + -5, j + height + 3, k + 0, leafID, 0);
		setBlockAndMetadata(world, i + -4, j + height + 3, k + 0, leafID, 0);
		setBlockAndMetadata(world, i + -3, j + height + 3, k + 0, leafID, 0);
		setBlockAndMetadata(world, i + -2, j + height + 3, k + 0, leafID, 0);
		setBlockAndMetadata(world, i + -1, j + height + 3, k + 0, leafID, 0);
		setBlockAndMetadata(world, i + 1, j + height + 3, k + 0, leafID, 0);
		setBlockAndMetadata(world, i + 2, j + height + 3, k + 0, leafID, 0);
		setBlockAndMetadata(world, i + 3, j + height + 3, k + 0, leafID, 0);
		setBlockAndMetadata(world, i + 4, j + height + 3, k + 0, leafID, 0);
		setBlockAndMetadata(world, i + 5, j + height + 3, k + 0, leafID, 0);
		setBlockAndMetadata(world, i + -1, j + height + 3, k + 1, leafID, 0);
		setBlockAndMetadata(world, i + 0, j + height + 3, k + 1, leafID, 0);
		setBlockAndMetadata(world, i + 1, j + height + 3, k + 1, leafID, 0);
		setBlockAndMetadata(world, i + -2, j + height + 3, k + 2, leafID, 0);
		setBlockAndMetadata(world, i + 0, j + height + 3, k + 2, leafID, 0);
		setBlockAndMetadata(world, i + 2, j + height + 3, k + 2, leafID, 0);
		setBlockAndMetadata(world, i + -3, j + height + 3, k + 3, leafID, 0);
		setBlockAndMetadata(world, i + 0, j + height + 3, k + 3, leafID, 0);
		setBlockAndMetadata(world, i + 3, j + height + 3, k + 3, leafID, 0);
		setBlockAndMetadata(world, i + -4, j + height + 3, k + 4, leafID, 0);
		setBlockAndMetadata(world, i + 0, j + height + 3, k + 4, leafID, 0);
		setBlockAndMetadata(world, i + 4, j + height + 3, k + 4, leafID, 0);
		setBlockAndMetadata(world, i + 0, j + height + 3, k + 5, leafID, 0);
		for(int y = 0; y <= height; y++)
		{
			BlockTropicraftLog.spawnCoconuts(world, i, (j + y), k, random, 2);
		}
		return true;
	}
	protected Random rand;



	@Override
	/**
	 * Sets the block in the world, notifying neighbors if enabled.
	 */
	protected void setBlockAndMetadata(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
		par1World.setBlock(par2, par3, par4, par5, par6, 3);
	}

}