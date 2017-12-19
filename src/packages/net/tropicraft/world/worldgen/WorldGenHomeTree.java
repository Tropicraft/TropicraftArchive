package net.tropicraft.world.worldgen;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.Direction;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.tropicraft.mods.TropicraftMod;
import net.tropicraft.world.biomes.BiomeGenTropicraft;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldGenHomeTree extends TCGenBase {

	private class BranchNode 
	{
		public int x1, y1, z1;
		public int x2, y2, z2;
		
		public BranchNode(int i, int j, int k, int x, int y, int z)
		{
			x1 = i;
			y1 = j;
			z1 = k;
			x2 = x;
			y2 = y;
			z2 = z;
		}
	}
	
	private final int woodID = TropicraftMod.tropicalWood.blockID;
	private final int woodMeta = 1;
	private final int leafID = TropicraftMod.tropicLeaves.blockID;
	private final int leafMeta = 6;
	
	private ArrayList<BranchNode> branchList = new ArrayList(); 
	private int trunkCirc;
	
	public WorldGenHomeTree(World world, Random random)
	{
		super(world, random);
	}
	
	@Override
	public boolean generate(int i, int j, int k) {
		j = getTerrainHeightAt(i, k);
		
		if(!BiomeGenTropicraft.rainforestBiomes.contains(worldObj.getWorldChunkManager().getBiomeGenAt(i, k)))
		{
			return false;
		}
		
		int height = rand.nextInt(20) + 30;
		
		if(height + j + 12 > 127)
		{
			return false;
		}
		
		trunkCirc = rand.nextInt(3) + 7;
		if(worldObj.getBlockId(i, j - 1, k) == Block.grass.blockID && worldObj.isAirBlock(i, j, k));
		int[] top = generateTrunk(i, j, k, trunkCirc, height);
		generateBranches(top[0], top[1], height + j);
		return false;
	}
	
	public int[] generateTrunk(int i, int j, int k, int circ, int height)
	{
		int bn = 0;
		
		int chanceToDisplace = 0;
		int lr = rand.nextBoolean() ? 1 : -1;
		int fb = rand.nextBoolean() ? 1 : -1;
		
		int tx = i;
		int tz = k;
		
		for(int y = j - 10; y < height + j; y++)
		{
			chanceToDisplace++;
			genCircle(tx, y, tz, circ, circ - 3, woodID, woodMeta, false);
			if(y == height + j - 1 || ((y - j) % 6 == 0))
			{
				genCircle(tx, y, tz, circ, 2, woodID, woodMeta, false);
				placeBlock(tx + 1, y, tz + 1, woodID, woodMeta, false);
				placeBlock(tx + 1, y, tz - 1, woodID, woodMeta, false);
				placeBlock(tx - 1, y, tz + 1, woodID, woodMeta, false);
				placeBlock(tx - 1, y, tz - 1, woodID, woodMeta, false);
				double angle = rand.nextDouble() * 3.141592D * 2D;
				if(rand.nextInt(3) == 0)
				{
					double length = rand.nextDouble() * circ - 4;
					int ax = (int) Math.round((length * Math.sin(angle)) + tx);
					int az = (int) Math.round((length * Math.cos(angle)) + tz); 
					worldObj.setBlock(ax, y + 1, az, Block.mobSpawner.blockID);
					String s = rand.nextBoolean() ? "AshenHunter" : "Iguana";
					TileEntityMobSpawner spawner = ((TileEntityMobSpawner)worldObj.getBlockTileEntity(ax, y + 1, az));
					if(spawner != null)
					{
						spawner.setMobID(s);
					}
				}
			}
			placeBlock(tx, y, tz, woodID, woodMeta, false);
			placeVine(tx + 1, y, tz);
			placeVine(tx - 1, y, tz);
			placeVine(tx, y, tz + 1);
			placeVine(tx, y, tz - 1);
			if(rand.nextInt(6) == 0);
			{
				if(y >= j + height - 6)
				{
					int branches = rand.nextInt(4) + 2;
					for(int b = 0; b < branches; b++)
					{
						int bl = rand.nextInt(10) + 15;
						int ax = tx;
						int az = tz;
						double angle = rand.nextDouble() * 3.141592D * 2D;
						int tax = (int) Math.round((circ * Math.sin(angle)) + ax);
						int taz = (int) Math.round((circ * Math.cos(angle)) + az); 
						ax = (int) Math.round((bl * Math.sin(angle)) + ax);
						az = (int) Math.round((bl * Math.cos(angle)) + az); 
						int ay = rand.nextInt(4) + 4;
						branchList.add(new BranchNode(tax, y, taz, ax, y + ay, az));
						bn++;
					}
				}
			}
			if(rand.nextInt(6) + 4 <= chanceToDisplace && chanceToDisplace * 9 > y)
			{
				if(rand.nextBoolean())
				{
					tx += lr;
					if(rand.nextBoolean())
					{
						tz += fb;
					}
				}
				else if(rand.nextBoolean())
				{
					tz += fb;
					if(rand.nextBoolean())
					{
						tz += lr;
					}
				}
				chanceToDisplace = 0;
			}
			placeBlock(tx, y, tz, Block.wood.blockID, 0, false);
		}
		worldObj.setBlock(tx - 1, height + j, tz - 1, TropicraftMod.bambooChest.blockID);
		TileEntityChest chest = (TileEntityChest)worldObj.getBlockTileEntity(tx - 1, height + j, tz - 1);
		if(chest != null)
		{
			int treasure = rand.nextInt(6) + 4;
			for(int lv = 0; lv < treasure; lv++)
			{
				chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), randLoot());
			}
		}
		return new int[] {tx, tz};
	}
	
	public void generateBranches(int topX, int topZ, int height)
	{
		for(int x = 0; x < branchList.size(); x++)
		{
			BranchNode bnode = branchList.get(x);
			int lSize = 3;
			if(!checkBlockLine(new int[] { bnode.x1, bnode.y1, bnode.z1 }, new int[] {bnode.x2, bnode.y2, bnode.z2 }, this.standardAllowedBlocks) && !checkBlockLine(new int[] { bnode.x1 + 1, bnode.y1, bnode.z1 }, new int[] {bnode.x2 + 1, bnode.y2, bnode.z2 }, this.standardAllowedBlocks) && !checkBlockLine(new int[] { bnode.x1 - 1, bnode.y1, bnode.z1 }, new int[] {bnode.x2 - 1, bnode.y2, bnode.z2 }, this.standardAllowedBlocks) && !checkBlockLine(new int[] { bnode.x1, bnode.y1, bnode.z1 + 1 }, new int[] {bnode.x2, bnode.y2, bnode.z2 + 1 }, this.standardAllowedBlocks) && !checkBlockLine(new int[] { bnode.x1, bnode.y1, bnode.z1 - 1 }, new int[] {bnode.x2, bnode.y2, bnode.z2 - 1 }, this.standardAllowedBlocks) && !checkBlockLine(new int[] { bnode.x1, bnode.y1 - 1, bnode.z1 }, new int[] {bnode.x2, bnode.y2 - 1, bnode.z2 }, this.standardAllowedBlocks))
			{
				continue;
			}
			placeBlockLine(new int[] { bnode.x1, bnode.y1, bnode.z1 }, new int[] {bnode.x2, bnode.y2, bnode.z2 }, woodID, woodMeta);
			placeBlockLine(new int[] { bnode.x1 + 1, bnode.y1, bnode.z1 }, new int[] {bnode.x2 + 1, bnode.y2, bnode.z2 }, woodID, woodMeta);
			placeBlockLine(new int[] { bnode.x1 - 1, bnode.y1, bnode.z1 }, new int[] {bnode.x2 - 1, bnode.y2, bnode.z2 }, woodID, woodMeta);
			placeBlockLine(new int[] { bnode.x1, bnode.y1, bnode.z1 + 1 }, new int[] {bnode.x2, bnode.y2, bnode.z2 + 1 }, woodID, woodMeta);
			placeBlockLine(new int[] { bnode.x1, bnode.y1, bnode.z1 - 1 }, new int[] {bnode.x2, bnode.y2, bnode.z2 - 1 }, woodID, woodMeta);
			placeBlockLine(new int[] { bnode.x1, bnode.y1 - 1, bnode.z1 }, new int[] {bnode.x2, bnode.y2 - 1, bnode.z2 }, woodID, woodMeta);
			if(bnode.y2 + 1 <= height)
			{
				placeBlockLine(new int[] { bnode.x1, bnode.y1 + 1, bnode.z1 }, new int[] {bnode.x2, bnode.y2 + 1, bnode.z2 }, TropicraftMod.tropicalWood.blockID, 1);
			}
			genLeafCircle(bnode.x2, bnode.y2 - 1, bnode.z2, lSize + 5, lSize + 3, leafID, leafMeta, true);
			genLeafCircle(bnode.x2, bnode.y2, bnode.z2, lSize + 6, 0, leafID, leafMeta, true);
			genLeafCircle(bnode.x2, bnode.y2 + 1, bnode.z2, lSize + 10, 0, leafID, leafMeta, true);
			genLeafCircle(bnode.x2, bnode.y2 + 2, bnode.z2, lSize + 9, 0, leafID, leafMeta, true);
		}
		
		int topBranches = rand.nextInt(6) + 6;
		for(int lVar = 0; lVar < topBranches; lVar++)
		{
			int bl = rand.nextInt(10) + 15;
			int ax = topX;
			int az = topZ;
			double angle = rand.nextDouble() * 3.141592D * 2D;
			ax = (int) Math.round((bl * Math.sin(angle)) + ax);
			az = (int) Math.round((bl * Math.cos(angle)) + az); 
			int branchHeight = height + rand.nextInt(4);
			int lSize = rand.nextInt(5) - 3;
			genTopBranch(ax, height, az, ax, height + rand.nextInt(4) + 4, az, topX, topZ);
			genLeafCircle(ax, branchHeight - 1, az, lSize + 5, lSize + 3, leafID, leafMeta, true);
			genLeafCircle(ax, branchHeight, az, lSize + 6, 0, leafID, leafMeta, true);
			genLeafCircle(ax, branchHeight + 1, az, lSize + 10, 0, leafID, leafMeta, true);
			genLeafCircle(ax, branchHeight + 2, az, lSize + 9, 0, leafID, leafMeta, true);
		}
	}
	
	public boolean canGenerate(int i, int j, int k, int height, int trunkCirc)
	{
		for(int x = i - trunkCirc + 5; x <= i + trunkCirc + 5; x++)
		{
			for(int z = k - trunkCirc + 5; z <= k + trunkCirc + 5; z++)
			{
				for(int y = getTerrainHeightAt(x, z); y < j + height; y++)
				{
					int bId = worldObj.getBlockId(x, y, z);
					if(!this.standardAllowedBlocks.contains(bId))
					{
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public boolean genTopBranch(int i, int j, int k, int sX, int sY, int sZ, int topX, int topZ)
	{
		int branchSize = rand.nextInt(2) + 4;
		ArrayList<int[]> lines = new ArrayList<int[]>();
		for(int x = i - branchSize; x < i + branchSize; x++)
		{
			for(int z = k - branchSize; z < k + branchSize; z++)
			{
				if((Math.sqrt(Math.pow(x - i, 2) + Math.pow(z - k, 2))) < branchSize && (Math.sqrt(Math.pow(x - topX, 2) + Math.pow(z - topZ, 2))) < trunkCirc)
				{
					if(!checkBlockLine(new int[] { x, j, z }, new int[] {  sX + (i - x), sY, sZ + (k - z) }, this.standardAllowedBlocks))
					{
						return false;
					}
				}
			}
		}
		for(int x = i - branchSize; x < i + branchSize; x++)
		{
			for(int z = k - branchSize; z < k + branchSize; z++)
			{
				if((Math.sqrt(Math.pow(x - i, 2) + Math.pow(z - k, 2))) < branchSize && (Math.sqrt(Math.pow(x - topX, 2) + Math.pow(z - topZ, 2))) < trunkCirc)
				{
					placeBlockLine(new int[] { x, j, z }, new int[] {  sX + (i - x), sY, sZ + (k - z) }, woodID, woodMeta);
				}
			}
		}
		return true;
	}
	
	public void genLeafCircle(int x, int y, int z, int outerRadius, int innerRadius, int id, int meta, boolean vines)
	{
		for(int i = (-outerRadius - 2) + x; i < (outerRadius + 2) + x; i++)
		{
			for(int k = (-outerRadius - 2) + z; k < (outerRadius + 2) + z; k++)
			{
				double d = (Math.sqrt(Math.pow(i - x, 2) + Math.pow(k - z, 2)));
				if(d <= outerRadius && d >= innerRadius)
				{
					if(worldObj.getBlockId(i, y, k) == 0 || worldObj.getBlockId(i, y, k) == id)
					{
                        placeBlock(i, y, k, id, meta, false);
					}
					if(rand.nextInt(20) == 0 && vines)
					{
						genVines(i, y - 1, k);
					}
				}
			}
		}
	} 

	public void genVines(int i, int j, int k)
	{
		int length = rand.nextInt(15) + 8;
		int dir = rand.nextInt(4);
		for(int y = j; y > j - length; y--)
		{
			if(worldObj.isAirBlock(i, y, k))
			{
				placeBlock(i, y, k, Block.vine.blockID, dir, false);
			}
			else break;
		}
	}
	
	public void placeBlock(int i, int j, int k, int id, int meta, boolean force)
	{
		if(worldObj.getBlockId(i, j, k) != id || worldObj.getBlockMetadata(i, j, k) != meta)
		{
			if(force || worldObj.getBlockId(i, j, k) == 0)
			{
				if(meta == 0)				
				{
					worldObj.setBlock(i, j, k, id);
					return;
				}
				worldObj.setBlockAndMetadata(i, j, k, id, meta);
			}
		}
	}
	
	
	@Override
	public void genCircle(int i, int j, int k, double distance, double distance2, int id, int meta, boolean solid)
	{
		for(int x = (int)(-distance - 2) + i; x < (int)(distance + 2) + i; x++)
		{
			for(int z = (int)(-distance - 2) + k; z < (int)(distance + 2) + k; z++)
			{
				double d = (Math.sqrt(Math.pow(x - i, 2) + Math.pow(z - k, 2)));
				if(d <= distance && d >= distance2)
				{
					int cid = worldObj.getBlockId(x, j, z);
					if((cid == 0 || cid == Block.waterStill.blockID || cid == Block.waterMoving.blockID) || solid)
					{
						placeBlock(x, j, z, id, meta, solid);
					}
				}
			}
		}
	}
	
	@Override
    public ArrayList<int[]> placeBlockLine(int ai[], int ai1[], int i, int meta)
    {
		ArrayList<int[]> places = new ArrayList<int[]>();
        int ai2[] = {
            0, 0, 0
        };
        byte byte0 = 0;
        int j = 0;
        for(; byte0 < 3; byte0++)
        {
            ai2[byte0] = ai1[byte0] - ai[byte0];
            if(Math.abs(ai2[byte0]) > Math.abs(ai2[j]))
            {
                j = byte0;
            }
        }

        if(ai2[j] == 0)
        {
            return null;
        }
        byte byte1 = otherCoordPairs[j];
        byte byte2 = otherCoordPairs[j + 3];
        byte byte3;
        if(ai2[j] > 0)
        {
            byte3 = 1;
        } else
        {
            byte3 = -1;
        }
        double d = (double)ai2[byte1] / (double)ai2[j];
        double d1 = (double)ai2[byte2] / (double)ai2[j];
        int ai3[] = {
            0, 0, 0
        };
        int k = 0;
        for(int l = ai2[j] + byte3; k != l; k += byte3)
        {
            ai3[j] = MathHelper.floor_double((double)(ai[j] + k) + 0.5D);
            ai3[byte1] = MathHelper.floor_double((double)ai[byte1] + (double)k * d + 0.5D);
            ai3[byte2] = MathHelper.floor_double((double)ai[byte2] + (double)k * d1 + 0.5D);
            placeBlock(ai3[0], ai3[1], ai3[2], i, meta, true);
            places.add(new int[] { ai3[0], ai3[1], ai3[2] });
        }
        return places;
    }
	
    @Override
	public ArrayList<int[]> checkAndPlaceBlockLine(int ai[], int ai1[], int i, int meta, List a)
    {
		ArrayList<int[]> places = new ArrayList<int[]>();
        int ai2[] = {
            0, 0, 0
        };
        byte byte0 = 0;
        int j = 0;
        for(; byte0 < 3; byte0++)
        {
            ai2[byte0] = ai1[byte0] - ai[byte0];
            if(Math.abs(ai2[byte0]) > Math.abs(ai2[j]))
            {
                j = byte0;
            }
        }

        if(ai2[j] == 0)
        {
            return null;
        }
        byte byte1 = otherCoordPairs[j];
        byte byte2 = otherCoordPairs[j + 3];
        byte byte3;
        if(ai2[j] > 0)
        {
            byte3 = 1;
        } else
        {
            byte3 = -1;
        }
        double d = (double)ai2[byte1] / (double)ai2[j];
        double d1 = (double)ai2[byte2] / (double)ai2[j];
        int ai3[] = {
            0, 0, 0
        };
        int k = 0;
        for(int l = ai2[j] + byte3; k != l; k += byte3)
        {
            ai3[j] = MathHelper.floor_double((double)(ai[j] + k) + 0.5D);
            ai3[byte1] = MathHelper.floor_double((double)ai[byte1] + (double)k * d + 0.5D);
            ai3[byte2] = MathHelper.floor_double((double)ai[byte2] + (double)k * d1 + 0.5D);
			int bId = worldObj.getBlockId(ai3[0], ai3[1], ai3[2]);
			if(!a.contains(bId))
			{
				return null;
			}
        }
        for(int l = ai2[j] + byte3; k != l; k += byte3)
        {
            ai3[j] = MathHelper.floor_double((double)(ai[j] + k) + 0.5D);
            ai3[byte1] = MathHelper.floor_double((double)ai[byte1] + (double)k * d + 0.5D);
            ai3[byte2] = MathHelper.floor_double((double)ai[byte2] + (double)k * d1 + 0.5D);
            placeBlock(ai3[0], ai3[1], ai3[2], i, meta, true);
            places.add(new int[] { ai3[0], ai3[1], ai3[2] });
        }
        return places;
    }
	
	public void placeVine(int i, int j, int k)
	{
		int m = 2;

        do
        {
            if (m > 5)
            {
            	return;
            }

            if (Block.vine.canPlaceBlockOnSide(worldObj, i, j, k, m) && worldObj.getBlockId(i, j, k) == 0)
            {
                worldObj.setBlockAndMetadata(i, j, k, Block.vine.blockID, 1 << Direction.vineGrowth[Facing.faceToSide[m]]);
                break;
            }

            m++;
        }
        while (true);
	}
	
	public ItemStack randLoot()
	{
		int picker = rand.nextInt(18);
		if(picker < 6)
		{
			return new ItemStack(TropicraftMod.bambooItem, rand.nextInt(20) + 1);
		}
		else if(picker < 8)
		{
			return new ItemStack(TropicraftMod.coconutBomb, rand.nextInt(3) + 1);
		}
		else if(picker < 10)
		{
			return new ItemStack(TropicraftMod.azuriteItem, rand.nextInt(3) + 1);
		}
		else if(picker < 12)
		{
			return new ItemStack(TropicraftMod.thatchBlock, rand.nextInt(20) + 1);
		}
		else if(picker < 14)
		{
			return new ItemStack(TropicraftMod.cookedFrogLeg, rand.nextInt(4) + 1);
		}
		else if(picker == 14)
		{
			return new ItemStack(TropicraftMod.ashenMask, 1, rand.nextInt(7));
		}
		else if(picker == 15)
		{
			return new ItemStack(TropicraftMod.froxTradeWinds, 1);
		}
		else if(picker == 16)
		{
			return new ItemStack(TropicraftMod.froxEasternIsles, 1);
		}
		else
		{
			return new ItemStack(Item.diamond, 1);
		}
	}
}
