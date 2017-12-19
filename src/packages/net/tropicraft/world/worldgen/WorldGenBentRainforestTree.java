package net.tropicraft.world.worldgen;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.tropicraft.mods.TropicraftMod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class WorldGenBentRainforestTree extends TCGenBase{

	public WorldGenBentRainforestTree(World world, Random random) {
		super(world, random);
	}

	@Override
	public boolean generate(int i, int j, int k) {

		if(worldObj.getBlockId(i, j - 1, k) != Block.dirt.blockID && worldObj.getBlockId(i, j - 1, k) != Block.grass.blockID)
		{
			return false;
		}		
		
		int height1 = rand.nextInt(4) + 4 + j;
		int height2 = rand.nextInt(6) + 6 + height1;
		
		double angle1 = randAngle();
		double angle2 = randAngle();
		
		int length1 = rand.nextInt(2) + 1;
		int length2 = rand.nextInt(6) + 4;
		
		int randOffsetX1 = (int) Math.round((length1 * Math.sin(angle1)) + i);
		int randOffsetZ1 = (int) Math.round((length1 * Math.cos(angle1)) + k); 

		int randOffsetX2 = (int) Math.round((length2 * Math.sin(angle2))) + randOffsetX1;
		int randOffsetZ2 = (int) Math.round((length2 * Math.cos(angle2))) + randOffsetZ1; 

		genCircle(i, j - 1, k, 1, 0, Block.dirt.blockID, 0, true);
		
		if(!checkBlockCircleLine(new int[] { randOffsetX1, height1, randOffsetZ1 }, new int[] { randOffsetX2, height2, randOffsetZ2 }, 1.3, 0, Arrays.asList(0, Block.tallGrass.blockID, Block.snow.blockID, TropicraftMod.tropicLeaves.blockID, Block.leaves.blockID)))
		{
			return false;
		}
		
		if(checkAndPlaceBlockCircleLine(new int[] { i, j, k }, new int[] { randOffsetX1, height1, randOffsetZ1 }, 1.3, 0, TropicraftMod.tropicalWood.blockID, 1,  Arrays.asList(0, Block.tallGrass.blockID, Block.snow.blockID, TropicraftMod.tropicLeaves.blockID, Block.leaves.blockID)) == null)
		{
			return false;
		}
		
		ArrayList<int[]> trunk = placeBlockCircleLine(new int[] { randOffsetX1, height1, randOffsetZ1 }, new int[] { randOffsetX2, height2, randOffsetZ2 }, 1.3, 0, TropicraftMod.tropicalWood.blockID, 1);
		
		//System.out.println("Started");
		
		for(int lVar = 0; lVar < trunk.size(); lVar++)
		{
			int[] block = trunk.get(lVar);
			if(rand.nextInt(3) == 0)
			{
				int branches = rand.nextInt(2) + 1;
				for(int lVar2 = 0; lVar2 < branches; lVar2++)
				{
					genBranch(block);
				}
			}
		}
		
		int[] top = trunk.get(trunk.size() - 1);
		
		genBranch(top);
		
		return true;
	}
	
	public boolean genBranch(int[] block)
	{
		double randAngle = randAngle();
		int branchLength = rand.nextInt(4) + 4;
		int branchHeight = rand.nextInt(3);
		int ax = (int) Math.round((branchLength * Math.sin(randAngle)) + block[0]);
		int az = (int) Math.round((branchLength * Math.cos(randAngle)) + block[2]); 
		if(checkAndPlaceBlockCircleLine(new int[] { ax, branchHeight + block[1], az}, new int[] { block[0], block[1], block[2] }, 1, 0, TropicraftMod.tropicalWood.blockID, 1, Arrays.asList(0, Block.tallGrass.blockID, Block.snow.blockID)) == null)
		genSphere(ax, branchHeight + block[1], az, rand.nextInt(2) + 2, TropicraftMod.tropicLeaves.blockID, 3);
		return true;
	}

}

