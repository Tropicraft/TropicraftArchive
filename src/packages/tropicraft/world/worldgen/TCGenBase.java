package tropicraft.world.worldgen;

import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import tropicraft.blocks.TropicraftBlocks;

public abstract class TCGenBase extends WorldGenerator {
	
	World worldObj;
	
	Random rand;
	
	/**Used in placeBlockLine*/
	static final byte otherCoordPairs[] = {
        2, 0, 0, 1, 2, 1
    };
	
	/** Should this only generate in rainforests? */
	boolean isRainforestOnly;
	
	/**Blocks normally checked in the check methods*/
	List<Integer> standardAllowedBlocks = Arrays.asList(0, Block.leaves.blockID, Block.tallGrass.blockID, Block.snow.blockID);
	
	public TCGenBase(World world, Random random, boolean isRainforestOnly)
	{
		worldObj = world;
		rand = random;
		this.isRainforestOnly = isRainforestOnly;
	}
	
	public TCGenBase(World world, Random random)
	{
		worldObj = world;
		rand = random;
		this.isRainforestOnly = true;
	}
	
	/** Checks if the ID is that of a leaf block*/
	public boolean isLeafId(int id)
	{
		return id == Block.leaves.blockID || id == TropicraftBlocks.tropicsLeaves.blockID || id == TropicraftBlocks.fruitLeaves.blockID;
	}
	
	public abstract boolean generate(int i, int j, int k);
	
	/** Allows this class to work as a WorldGenerator object */
	@Override
	public boolean generate(World world, Random rand, int i, int j, int k)
	{
		worldObj = world;
		this.rand = rand;
		return generate(i, j, k);
	}
	
	/** 
	 * Generates a circle
	 * @param x The x coordinate of the center of the circle
 	 * @param y The y coordinate of the center of the circle
	 * @param z The z coordinate of the center of the circle
	 * @param outerRadius The radius of the circle's outside edge
	 * @param innerRadius The radius of the circle's inner edge, 0 for a full circle
	 * @param id The ID to generate with
	 * @param meta The metadata to generate with
	 * @param solid Whether it should place the block if another block is already occupying that space
	 */
	public boolean genCircle(int x, int y, int z, double outerRadius, double innerRadius, int id, int meta, boolean solid)
	{
		boolean hasGenned = false;
		for(int i = (int)(-outerRadius - 1) + x; i <= (int)(outerRadius + 1) + x; i++)
		{
			for(int k = (int)(-outerRadius - 1) + z; k <= (int)(outerRadius + 1) + z; k++)
			{
				double d = (i - x) * (i - x) + (k - z) * (k - z);
				if(d <= outerRadius * outerRadius && d >= innerRadius * innerRadius)
				{
					if(worldObj.isAirBlock(i, y, k) || solid)
					{
						if(worldObj.setBlock(i, y, k, id, meta, 3))
						{
							hasGenned = true;
						}
					}
				}
			}
		}
		return hasGenned;
	}
	
	/**
	 * Checks whether any blocks not specified in allowedBlockList exist in that circle
	 * @param x The x coordinate of the center of the circle
 	 * @param y The y coordinate of the center of the circle
	 * @param z The z coordinate of the center of the circle
	 * @param outerRadius The radius of the circle's outside edge
	 * @param innerRadius The radius of the circle's inner edge, 0 for a full circle
	 * @param allowedBlockList The blocks to exclude from the check
	 * @return Whether any blocks not specified in allowedBlockList exist in that circle
	 */
	public boolean checkCircle(int i, int j, int k, double outerRadius, double innerRadius, List allowedBlockList)
	{
		for(int x = (int)(-outerRadius - 2) + i; x < (int)(outerRadius + 2) + i; x++)
		{
			for(int z = (int)(-outerRadius - 2) + k; z < (int)(outerRadius + 2) + k; z++)
			{
				double d = (i - x) * (i - x) + (k - z) * (k - z);
				if(d <= outerRadius * outerRadius && d >= innerRadius * innerRadius)
				{
					int bId = worldObj.getBlockId(x, j, z);
					if(!allowedBlockList.contains(bId));
					{
						return false;
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * Checks whether any blocks not specified in allowedBlockList exist in that line
	 * @param ai One end of the line
	 * @param ai1 The other end of the line
	 * @param allowedBlockList The block to exclude from the check
	 * @return Whether any blocks not specified in allowedBlockList exist in that circle
	 */
	public boolean checkBlockLine(int ai[], int ai1[], List allowedBlockList)
    {
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
            return false;
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
			List standardAllowedBlocks = Arrays.asList(0, Block.leaves.blockID, Block.tallGrass.blockID, Block.snow.blockID);
			if(!allowedBlockList.contains(bId))
			{
				return false;
			}
        }
        return true;
    }
	
	/**
	 * Places a line from coords ai to coords ai1
	 * @param ai One end of the line
	 * @param ai1 The other end of the line
	 * @param i The block ID to place
	 * @param meta The block metadata to place
	 * @return The coords that blocks were placed on
	 */
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
            worldObj.setBlock(ai3[0], ai3[1], ai3[2], i, meta, 3);
            places.add(new int[] { ai3[0], ai3[1], ai3[2] });
        }
        return places;
    }
	
	/**
	 * Checks whether any blocks not specified in allowedBlockList exist in that line of circles
	 * @param ai One end of the line
	 * @param ai1 The other end of the line
	 * @param outerRadius The radius of the circle's outside edge
	 * @param innerRadius The radius of the circle's inner edge, 0 for a full circle
	 * @param allowedBlockList The block to exclude from the check
	 * @return
	 */
	public boolean checkBlockCircleLine(int ai[], int ai1[], double outerRadius, double innerRadius, List allowedBlockList)
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
            return false;
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
            if(!checkCircle(ai3[0], ai3[1], ai3[2], outerRadius, innerRadius, allowedBlockList))
           	{
            	return false;
      		}
        }
        return true;
    }
	
	/**
	 * Checks whether any blocks not specified in allowedBlockList exist in that line of circles and if not places the block circle line
	 * @param ai One end of the line
	 * @param ai1 The other end of the line
	 * @param outerRadius The radius of the circle's outside edge
	 * @param innerRadius The radius of the circle's inner edge, 0 for a full circle
	 * @param i The ID to generate the block circle line with
	 * @param meta The metadata to generate the block circle line with
	 * @param allowedBlockList The block to exclude from the check
	 * @return The coordinates where a circle was generated
	 */
	public ArrayList<int[]> checkAndPlaceBlockCircleLine(int ai[], int ai1[], double outerRadius, double innerRadius, int i, int meta, List allowedBlockList)
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
            if(!checkCircle(ai3[0], ai3[1], ai3[2], outerRadius, innerRadius, allowedBlockList))
            {
            	return null;
            }
        }
        for(int l = ai2[j] + byte3; k != l; k += byte3)
        {
            ai3[j] = MathHelper.floor_double((double)(ai[j] + k) + 0.5D);
            ai3[byte1] = MathHelper.floor_double((double)ai[byte1] + (double)k * d + 0.5D);
            ai3[byte2] = MathHelper.floor_double((double)ai[byte2] + (double)k * d1 + 0.5D);
            genCircle(ai3[0], ai3[1], ai3[2], outerRadius, innerRadius, i, meta, true);
            places.add(new int[] { ai3[0], ai3[1], ai3[2] });
        }
        return places;
    }
	
	/**
	 * Checks if any blocks not specified in allowedBlockList exist within the line and if not places the line
	 * @param ai One end of the line
	 * @param ai1 The other end of the line
	 * @param i The ID to generate the block circle line with
	 * @param meta The metadata to generate the block circle line with
	 * @param allowedBlockList The block to exclude from the check
	 * @return The coordinates where a block was placed
	 */
	public ArrayList<int[]> checkAndPlaceBlockLine(int ai[], int ai1[], int i, int meta, List allowedBlockList)
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
			if(!allowedBlockList.contains(bId))
			{
				return null;
			}
        }
        for(int l = ai2[j] + byte3; k != l; k += byte3)
        {
            ai3[j] = MathHelper.floor_double((double)(ai[j] + k) + 0.5D);
            ai3[byte1] = MathHelper.floor_double((double)ai[byte1] + (double)k * d + 0.5D);
            ai3[byte2] = MathHelper.floor_double((double)ai[byte2] + (double)k * d1 + 0.5D);
            worldObj.setBlock(ai3[0], ai3[1], ai3[2], i, meta, 3);
            places.add(new int[] { ai3[0], ai3[1], ai3[2] });
        }
        return places;
    }
	
	/**
	 * Places a block circle line
	 * @param ai One end of the line
	 * @param ai1 The other end of the line
	 * @param outerRadius The radius of the circle's outside edge
	 * @param innerRadius The radius of the circle's inner edge, 0 for a full circle
	 * @param i The ID to generate the block circle line with
	 * @param meta The metadata to generate the block circle line with
	 * @param allowedBlockList The block to exclude from the check
	 * @return The coordinates where a circle was generated
	 */
	public ArrayList<int[]> placeBlockCircleLine(int ai[], int ai1[], double distance, double distance2, int i, int meta)
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
            genCircle(ai3[0], ai3[1], ai3[2], distance, distance2, i, meta, true);
            places.add(new int[] { ai3[0], ai3[1], ai3[2] });
        }
        return places;
    }
	
	/**
	 * Generates a sphere at the specified coordinates
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @param z The z coordinate
	 * @param outerRadius The radius of the sphere's outside edge
	 * @param id The block ID to generate the sphere with
	 * @param meta The block metadata to generate the sphere with
	 */
	public void genSphere(int x, int y, int z, int outerRadius, int id, int meta)
	{
		for(int i = x - outerRadius; i < x + outerRadius; i++)
	    {
	         for(int j = y - outerRadius; j < y + outerRadius; j++)
	         {
	            for(int k = z - outerRadius; k < z + outerRadius; k++)
	            {
	            	if(worldObj.isAirBlock(i, j, k))
               	 	{
		                 int distance1 = (i - x) * (i - x) + (j - y) * (j - y) + (k - z) * (k - z);
	
		                 if(distance1 <= outerRadius)
		                 {
		                	 worldObj.setBlock(i, j, k, id, meta, 3);
		                 }
               	 	}
	            }
	         }
	    }
	}
	
	/**
	 * Gets the terrain height at the specified coordinates
	 * @param x The x coordinate
	 * @param z The z coordinate
	 * @return The terrain height at the specified coordinates
	 */
	public int getTerrainHeightAt(int x, int z)
	{
		for(int y = 256; y > 0; y--)
		{
			int id = worldObj.getBlockId(x, y, z);
			if(id == Block.dirt.blockID || id == Block.grass.blockID || id == Block.sand.blockID || id == Block.stone.blockID)
			{
				return y + 1;
			}
		}
		return 0;
	}
	
	/**
	 * Gets a random angle in radians
	 * @return A random angle in radians
	 */
	public double randAngle()
	{
		return rand.nextDouble() * 3.1415926535897931D * 2D;
	}
}
