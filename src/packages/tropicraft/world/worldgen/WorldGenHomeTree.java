package tropicraft.world.worldgen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import tropicraft.ModInfo;
import tropicraft.blocks.TropicraftBlocks;
import tropicraft.items.TropicraftItems;
import tropicraft.world.biomes.BiomeGenTropicraft;

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
	
	private final int woodID = TropicraftBlocks.treeWood.blockID;
	private final int woodMeta = 1;
	private final int leafID = TropicraftBlocks.tropicsLeaves.blockID;
	private final int leafMeta = 6;
	
	private ArrayList<BranchNode> branchList = new ArrayList(); 
	private int trunkRadius;
	
	public WorldGenHomeTree(World world, Random random)
	{
		super(world, random);
	}
	
	@Override
	public boolean generate(int i, int j, int k) {
		j = 127;
		
		trunkRadius = rand.nextInt(3) + 7;
		
		for(int x = i - trunkRadius; x < i + trunkRadius; x++)
		{
			for(int z = k - trunkRadius; z < k + trunkRadius; z++)
			{
				int tHeight = getTerrainHeightAt(x, z);
				if(tHeight < j)
				{
					j = tHeight;
				}
			}
		}
		
		if(!BiomeGenTropicraft.rainforestBiomes.contains(worldObj.getWorldChunkManager().getBiomeGenAt(i, k)))
		{
			return false;
		}
		
		int height = rand.nextInt(20) + 30;
		
		if(height + j + 12 > 255)
		{
			return false;
		}
		
		int[] top = generateTrunk(i, j, k, height);
		
		generateBranches(top[0], top[1], height + j);
		return true;
	}
	
	public int[] generateTrunk(int i, int j, int k, int height)
	{
		int bn = 0;
		
		int chanceToDisplace = 0;
		int xDisplace = rand.nextBoolean() ? 1 : -1;
		int zDisplace = rand.nextBoolean() ? 1 : -1;
		
		int trunkX = i;
		int trunkZ = k;
		
		for(int y = j; y < height + j; y++)
		{
			chanceToDisplace++;
			genCircle(trunkX, y, trunkZ, trunkRadius, trunkRadius - 3, woodID, woodMeta, false);
			if(y == height + j - 1 || ((y - j) % 6 == 0))
			{
				genCircle(trunkX, y, trunkZ, trunkRadius, 2, woodID, woodMeta, false);
				placeBlock(trunkX + 1, y, trunkZ + 1, woodID, woodMeta, false);
				placeBlock(trunkX + 1, y, trunkZ - 1, woodID, woodMeta, false);
				placeBlock(trunkX - 1, y, trunkZ + 1, woodID, woodMeta, false);
				placeBlock(trunkX - 1, y, trunkZ - 1, woodID, woodMeta, false);
				double angle = rand.nextDouble() * 3.141592D * 2D;
				if(rand.nextInt(3) == 0)
				{
					double length = rand.nextDouble() * trunkRadius - 4;
					int spawnerX = trunkX - 4 + rand.nextInt(9);
					int spawnerZ = trunkZ - 4 + rand.nextInt(9);
					worldObj.setBlock(spawnerX, y + 1, spawnerZ, Block.mobSpawner.blockID);
					StringBuilder sb = new StringBuilder(String.format("%s.", ModInfo.MODID));
					sb.append(rand.nextBoolean() ? "AshenHunter" : "Iguana");
					TileEntityMobSpawner spawner = ((TileEntityMobSpawner)worldObj.getBlockTileEntity(spawnerX, y + 1, spawnerZ));
					if(spawner != null)
					{
						spawner.getSpawnerLogic().setMobID(sb.toString());
					}
				}
			}
			placeBlock(trunkX, y, trunkZ, woodID, woodMeta, false);
			placeBlock(trunkX + 1, y, trunkZ, Block.vine.blockID, 2, false);
			placeBlock(trunkX - 1, y, trunkZ, Block.vine.blockID, 2, false);
			placeBlock(trunkX, y, trunkZ + 1, Block.vine.blockID, 2, false);
			placeBlock(trunkX, y, trunkZ - 1, Block.vine.blockID, 2, false);
			if(rand.nextInt(6) == 0);
			{
				if(y >= j + height - 6)
				{
					int branches = rand.nextInt(4) + 2;
					for(int x = 0; x < branches; x++)
					{
						int branchLength = rand.nextInt(10) + 15;
						int branchX1 = trunkX;
						int branchZ1 = trunkZ;
						double angle = rand.nextDouble() * 3.141592D * 2D;
						branchX1 = (int)((trunkRadius * Math.sin(angle)) + branchX1);
						branchZ1 = (int)((trunkRadius * Math.cos(angle)) + branchZ1); 
						int branchX2 = (int)((branchLength * Math.sin(angle)) + branchX1);
						int branchZ2 = (int)((branchLength * Math.cos(angle)) + branchZ1); 
						int branchY2 = rand.nextInt(4) + 4;
						branchList.add(new BranchNode(branchX1, y, branchZ1, branchX2, y + branchY2, branchZ2));
						bn++;
					}
				}
			}
			if(rand.nextInt(6) + 4 <= chanceToDisplace && chanceToDisplace * 9 > y)
			{
				if(rand.nextBoolean())
				{
					trunkX += xDisplace;
					if(rand.nextBoolean())
					{
						trunkZ += zDisplace;
					}
				}
				else if(rand.nextBoolean())
				{
					trunkZ += zDisplace;
					if(rand.nextBoolean())
					{
						trunkZ += xDisplace;
					}
				}
				chanceToDisplace = 0;
			}
			placeBlock(trunkX, y, trunkZ, Block.wood.blockID, 0, false);
		}
		worldObj.setBlock(trunkX - 1, height + j, trunkZ - 1, TropicraftBlocks.bambooChest.blockID);
		TileEntityChest chest = (TileEntityChest)worldObj.getBlockTileEntity(trunkX - 1, height + j, trunkZ - 1);
		if(chest != null)
		{
			int treasure = rand.nextInt(6) + 4;
			for(int x = 0; x < treasure; x++)
			{
				chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), randLoot());
			}
		}
		return new int[] {trunkX, trunkZ};
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
				placeBlockLine(new int[] { bnode.x1, bnode.y1 + 1, bnode.z1 }, new int[] {bnode.x2, bnode.y2 + 1, bnode.z2 }, woodID, woodMeta);
			}
			genLeafCircle(bnode.x2, bnode.y2 - 1, bnode.z2, lSize + 5, lSize + 3, leafID, leafMeta, true);
			genLeafCircle(bnode.x2, bnode.y2, bnode.z2, lSize + 6, 0, leafID, leafMeta, true);
			genLeafCircle(bnode.x2, bnode.y2 + 1, bnode.z2, lSize + 10, 0, leafID, leafMeta, true);
			genLeafCircle(bnode.x2, bnode.y2 + 2, bnode.z2, lSize + 9, 0, leafID, leafMeta, true);
		}
		
		int topBranches = rand.nextInt(6) + 6;
		/*for(int x = 0; x < topBranches; x++)
		{
			int branchLength = rand.nextInt(10) + 15;
			int baseDistance = rand.nextInt(trunkRadius - 3);
			int branchX1 = topX;
			int branchZ1 = topZ;
			double angle = rand.nextDouble() * 3.141592D * 2D;
			branchX1 = (int)((baseDistance * Math.sin(angle)) + branchX1);
			branchZ1 = (int)((baseDistance * Math.cos(angle)) + branchZ1); 
			int branchX2 = (int)((branchLength * Math.sin(angle)) + branchX1);
			int branchZ2 = (int)((branchLength * Math.cos(angle)) + branchZ1); 
			int branchHeight = height + rand.nextInt(4);
			int leafRadius = rand.nextInt(5) - 3;
			genTopBranch(branchX1, height, branchZ1, branchX1, height + rand.nextInt(4) + 4, branchZ1, topX, topZ);
			genLeafCircle(branchX1, branchHeight - 1, branchZ1, leafRadius + 5, leafRadius + 3, leafID, leafMeta, true);
			genLeafCircle(branchX1, branchHeight, branchZ1, leafRadius + 6, 0, leafID, leafMeta, true);
			genLeafCircle(branchX1, branchHeight + 1, branchZ1, leafRadius + 10, 0, leafID, leafMeta, true);
			genLeafCircle(branchX1, branchHeight + 2, branchZ1, leafRadius + 9, 0, leafID, leafMeta, true);
		}*/
	}
	
	public boolean genTopBranch(int i, int j, int k, int sX, int sY, int sZ, int topX, int topZ)
	{
		ArrayList<Integer> allowedBlocks = new ArrayList(this.standardAllowedBlocks);
		allowedBlocks.add(woodID);
		allowedBlocks.add(leafID);
		allowedBlocks.add(Block.vine.blockID);
		int branchSize = rand.nextInt(2) + 4;
		ArrayList<int[]> lines = new ArrayList<int[]>();
		for(int x = i - branchSize; x < i + branchSize; x++)
		{
			for(int z = k - branchSize; z < k + branchSize; z++)
			{
				if((x - i) * (x - i) + (z - k) * (z - k) < branchSize * branchSize && (x - topX) * (x - topX) + (z - topZ) * (z - topZ) < trunkRadius * trunkRadius)
				{
					if(!checkBlockLine(new int[] { x, j, z }, new int[] {  sX + (i - x), sY, sZ + (k - z) }, allowedBlocks))
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
				if((x - i) * (x - i) + (z - k) * (z - k) < branchSize * branchSize && (x - topX) * (x - topX) + (z - topZ) * (z - topZ) < trunkRadius * trunkRadius)
				{
					placeBlockLine(new int[] { x, j, z }, new int[] {  sX + (i - x), sY, sZ + (k - z) }, woodID, woodMeta);
				}
			}
		}
		return true;
	}
	
	public void genLeafCircle(int x, int y, int z, int outerRadius, int innerRadius, int id, int meta, boolean vines)
	{
		int outerRadiusSquared = outerRadius * outerRadius;
		int innerRadiusSquared = innerRadius * innerRadius;
		for(int i = -outerRadius + x; i < outerRadius + x; i++)
		{
			for(int k = -outerRadius + z; k < outerRadius + z; k++)
			{
				double d = (x - i) * (x - i) + (z - k) * (z - k);
				if(d <= outerRadiusSquared && d >= innerRadiusSquared)
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
	
	public boolean placeBlock(int i, int j, int k, int id, int meta, boolean force)
	{
		int bID = worldObj.getBlockId(i, j, k);
		if(force || bID == Block.waterStill.blockID || bID == Block.waterMoving.blockID || bID == TropicraftBlocks.tropicsWaterStationary.blockID || bID == TropicraftBlocks.tropicsWaterFlowing.blockID || bID == 0)
		{
			if(meta == 0)				
			{
				return worldObj.setBlock(i, j, k, id);
			}
			return worldObj.setBlock(i, j, k, id, meta, 3);
		}
		return false;
	}
	
	@Override
	public boolean genCircle(int i, int j, int k, double outerRadius, double innerRadius, int id, int meta, boolean solid)
	{
		boolean hasGenned = false;
		double outerRadiusSquared = outerRadius * outerRadius;
		double innerRadiusSquared = innerRadius * innerRadius;
		for(int x = (int)-outerRadius + i; x < (int)outerRadius + i; x++)
		{
			for(int z = (int)-outerRadius + k; z < (int)outerRadius + k; z++)
			{
				double d = (x - i) * (x - i) + (z - k) * (z - k);
				if(d <= outerRadiusSquared && d >= innerRadiusSquared)
				{
					int bID = worldObj.getBlockId(x, j, z);
					if((bID == 0 || bID == Block.waterStill.blockID || bID == Block.waterMoving.blockID) || bID == TropicraftBlocks.tropicsWaterStationary.blockID || bID == TropicraftBlocks.tropicsWaterFlowing.blockID || solid)
					{
						if(placeBlock(x, j, z, id, meta, solid))
						{
							hasGenned = true;
						}
					}
				}
			}
		}
		return hasGenned;
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
            ai3[j] = MathHelper.floor_double(ai[j] + k + 0.5D);
            ai3[byte1] = MathHelper.floor_double(ai[byte1] + k * d + 0.5D);
            ai3[byte2] = MathHelper.floor_double(ai[byte2] + k * d1 + 0.5D);
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
            ai3[j] = MathHelper.floor_double(ai[j] + k + 0.5D);
            ai3[byte1] = MathHelper.floor_double(ai[byte1] + k * d + 0.5D);
            ai3[byte2] = MathHelper.floor_double(ai[byte2] + k * d1 + 0.5D);
			int bId = worldObj.getBlockId(ai3[0], ai3[1], ai3[2]);
			if(!a.contains(bId))
			{
				return null;
			}
        }
        for(int l = ai2[j] + byte3; k != l; k += byte3)
        {
            ai3[j] = MathHelper.floor_double(ai[j] + k + 0.5D);
            ai3[byte1] = MathHelper.floor_double(ai[byte1] + k * d + 0.5D);
            ai3[byte2] = MathHelper.floor_double(ai[byte2] + k * d1 + 0.5D);
            placeBlock(ai3[0], ai3[1], ai3[2], i, meta, true);
            places.add(new int[] { ai3[0], ai3[1], ai3[2] });
        }
        return places;
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
		else if(picker < 14)
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
			return new ItemStack(Item.diamond, 1);
		}
	}
}
