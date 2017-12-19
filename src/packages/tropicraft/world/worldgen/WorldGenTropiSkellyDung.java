package tropicraft.world.worldgen;

import java.util.Random;

import tropicraft.blocks.TropicraftBlocks;
import tropicraft.blocks.tileentities.TileEntityBambooChest;
import tropicraft.items.TropicraftItems;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;

/*
 * int direction, and it's meanings
 *            ^ 4
 *            |
 *            |
 *            |
 * 7 <----------------> 5
 *            |
 *            |
 *            |
 *            v 6
 */

public class WorldGenTropiSkellyDung extends TCGenBase {

	public WorldGenTropiSkellyDung(World worldObj, Random rand)
	{
		super(worldObj, rand);
	}
	
	public boolean generate(int i, int j, int k)
	{
		int length = (rand.nextInt(3) + 4) * 2 + 1;
		int width = (rand.nextInt(2) + 3) * 2 + 1;
		int height = 4;
		int dir = rand.nextInt(4);
		if((dir % 2) == 0)
		{
			length = length ^ width;
			width = length ^ width;
			length = length ^ width;
		}
		
		int num = 0;
		for(int x = i; x <= i + length; x++)
		{
			for(int z = k; z <= k + width; z++)
			{
				for(int y = j; y <= j + height; y++)
				{
					if(worldObj.getBlockId(x, y, z) == 0)
					{
						num++;
					}
				}
			}
		}
		if(num < ((length * width * height) / 2))
		{
			return false;
		}
		
		for(int x = i; x <= i + length; x++)
		{
			for(int z = k; z <= k + width; z++)
			{
				for(int y = j; y <= j + height; y++)
				{
					if(x == i || x == i + length || z == k || z == k + width || y == j || y == j + height)
					{
						if(!worldObj.isAirBlock(x, y, z))
						{
							if(y == j || y == j + height)
							{
								worldObj.setBlock(x, y, z, TropicraftBlocks.tropicsBuildingBlock.blockID, 1, 3);
							}
							else if(y == j + 1)
							{
								worldObj.setBlock(x, y, z, TropicraftBlocks.tropiOres.blockID, 2, 3);
								if(x == i + (length / 2) || x == i + (length / 2) + 1 || z == k + (width / 2) || z == k + (width / 2) + 1)
								{
									int meta = 5;
									if(z == k || z == k + width)
									{
										meta = 9;
									}
									worldObj.setBlock(x, y, z, TropicraftBlocks.treeWood.blockID, meta, 3);
								}
							}
							else if(y == j + 2)
							{
								int meta = 5;
								if(z == k || z == k + width)
								{
									meta = 9;
								}
								worldObj.setBlock(x, y, z, TropicraftBlocks.treeWood.blockID, meta, 3);
							}
							else if(y == j + 3)
							{
								worldObj.setBlock(x, y, z, TropicraftBlocks.tropicsBuildingBlock.blockID, 1, 3);
								if(x == i + (length / 2) - 1 || x == i + (length / 2) + 2 || z == k + (width / 2) - 1 || z == k + (width / 2) + 2)
								{
									int meta = 5;
									if(z == k || z == k + width)
									{
										meta = 9;
									}
									worldObj.setBlock(x, y, z, TropicraftBlocks.treeWood.blockID, meta, 3);
								}
							}
						}
					}
					else
					{
						worldObj.setBlockToAir(x, y, z);
					}
				}
			}
		}
		int iMin = i + 1;
		int kMin = k + 1;
		int jMin = j + 1;
		int iMax = i + length - 1;
		int kMax = k + width - 1;
		switch(dir)
		{
			case 0:
				putSpawner(iMin, jMin, kMin);
				putSpawner(iMax, jMin, kMin);
				putChest(iMin + (length / 2), jMin, kMin, 3);
				putChest(iMin + (length / 2) - 1, jMin, kMin, 3);
			case 1:
				putSpawner(iMin, jMin, kMin);
				putSpawner(iMin, jMin, kMax);
				putChest(iMin, jMin, kMin + (width / 2), 5);
				putChest(iMin, jMin, kMin + (width / 2) - 1, 5);
				break;
			case 2:
				putSpawner(iMin, jMin, kMax);
				putSpawner(iMax, jMin, kMax);
				putChest(iMin + (length / 2), jMin, kMax, 2);
				putChest(iMin + (length / 2) - 1, jMin, kMax, 2);
				break;
			case 3:
				putSpawner(iMax, jMin, kMin);
				putSpawner(iMax, jMin, kMax);
				putChest(iMax, jMin, kMin + (width / 2), 4);
				putChest(iMax, jMin, kMin + (width / 2) - 1, 4);
				break;
		}
		return true;
	}
	
	public ItemStack randLoot()
	{
		int picker = rand.nextInt(18);
		if(picker < 6)
		{
			return new ItemStack(TropicraftItems.bambooChute, rand.nextInt(20) + 1);
		}
		else if(picker < 8)
		{
			return new ItemStack(TropicraftItems.coconutBomb, rand.nextInt(3) + 1);
		}
		else if(picker < 10)
		{
			return new ItemStack(TropicraftItems.scale, rand.nextInt(3) + 1);
		}
		else if(picker < 12)
		{
			return new ItemStack(TropicraftBlocks.tropicsBuildingBlock, rand.nextInt(20) + 1);
		}
		else if(picker < 15)
		{
			return new ItemStack(TropicraftItems.cookedFrogLeg, rand.nextInt(4) + 1);
		}
		else if(picker == 14)
		{
			return new ItemStack(TropicraftItems.ashenMasks, 1, rand.nextInt(7));
		}
		else if(picker == 15)
		{
			return new ItemStack(TropicraftItems.recordTradeWinds, 1);
		}
		else if(picker == 16)
		{
			return new ItemStack(TropicraftItems.recordEasternIsles, 1);
		}
		else
		{
			return new ItemStack(TropicraftItems.blowGun, 1);
		}
	}
	
	private void putSpawner(int x, int y, int z)
	{
		worldObj.setBlock(x, y, z, Block.mobSpawner.blockID);
		TileEntityMobSpawner spawner = (TileEntityMobSpawner) worldObj.getBlockTileEntity(x, y, z);
		spawner.getSpawnerLogic().setMobID("TropiSkeleton");
	}
	
	private void putChest(int x, int y, int z, int dir)
	{
		worldObj.setBlock(x, y, z, TropicraftBlocks.bambooChest.blockID, dir, 3);
		worldObj.setBlockMetadataWithNotify(x, y, z, dir, 3);
		TileEntityBambooChest chest = (TileEntityBambooChest) worldObj.getBlockTileEntity(x, y, z);
		int amount = rand.nextInt(4) + 2;
		for(int i = 0; i < amount; i++)
		{
			chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), randLoot());
		}
	}
	
}
