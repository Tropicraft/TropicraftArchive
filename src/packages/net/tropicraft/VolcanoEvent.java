package net.tropicraft;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.tropicraft.blocks.BlockTempLavaFlowing;
import net.tropicraft.entities.EntityLavaBall;
import net.tropicraft.entities.EntitySmoke;
import net.tropicraft.mods.TropicraftMod;

import java.util.Random;

public class VolcanoEvent {

	public static boolean erupting = false;

	public int xPos;
	public int zPos;
	public int lavaLevel;
	public int radius;
	public boolean falling;
	public Random rand;
	public World worldObj;
	public int time;
	//public Entity e;
	public boolean erupted;
	private boolean hasInitialized;

	public VolcanoEvent(World w, int i, int k)
	{
		xPos = i;
		zPos = k;
		radius = 0;
		lavaLevel = 79;
		worldObj = w;
		erupting = true;
		falling = false;
		erupted = false;
		BlockTempLavaFlowing.decay = false;
		rand = new Random();
		//	e = new EntityCreeper(w); //MR RUBE ADD YOUR OWN ENTITY
		//	e.setLocationAndAngles(i, 91, k, 0, 0);
		//	worldObj.joinEntityInSurroundings(e);
		hasInitialized = false;
		if (worldObj != null) {
			findRadius();
			getLavaLevel();
			hasInitialized = true;
		}
	}

	public VolcanoEvent(int i, int k)
	{
		xPos = i;
		zPos = k;
		radius = 0;
		lavaLevel = 79;
		erupting = true;
		falling = false;
		erupted = false;
		BlockTempLavaFlowing.decay = false;
		rand = new Random();
		//	e = new EntityCreeper(w); //MR RUBE ADD YOUR OWN ENTITY
		//	e.setLocationAndAngles(i, 91, k, 0, 0);
		//	worldObj.joinEntityInSurroundings(e);
		hasInitialized = false;
	}

	public void setWorld(World w)
	{
		worldObj = w;
		if (worldObj != null) {
			findRadius();
			getLavaLevel();
			hasInitialized = true;
		}
	}

	public boolean update()
	{
		if(!TropicraftMod.shouldVolcanoesErupt)
			return false;
		
		//System.out.println(worldObj.isRemote);
		if (worldObj != null && !hasInitialized) {
			findRadius();
			getLavaLevel();
			hasInitialized = true;
		} else if (worldObj == null) {
			return true;
		}
		time++;
		//if(e.isDead && !erupted)
		//{
		//	falling = true;
		//}
		if(time % (100) == 0 && !falling)
		{
			if(!worldObj.isRemote)
				spewSmoke();
		}
		if(time > 1200 && time % 300 == 0)
		{
			raiseFallLava();
		}

		if(!worldObj.isRemote) {

			if((time % (rand.nextInt(40) + 10) == 0) && time > 800 && !falling)
			{
				throwLavaFromCaldera((rand.nextDouble() * 0.5) + (lavaLevel > 90 ? 1.25 : 0.75));
			}
			if((time % (rand.nextInt(40) + 10) == 0) && !falling && lavaLevel > 90)
			{
				throwLavaFromCaldera((rand.nextDouble() * 0.5) + (lavaLevel > 90 ? 1.25 : 0.75));
			}
		}
		
		if(lavaLevel < 84 && falling)
		{
			BlockTempLavaFlowing.decay = true;
		}
		if(lavaLevel == 79 && falling && time % 150 == 0)
		{
			cleanUpFromEruption();
			erupting = false;
			return false;
		}
		return true;
	}

	public void cleanUpFromEruption()
	{
		for(int x = xPos - (radius * 2); x < xPos + (radius * 2); x++)
		{
			for(int z = zPos - (radius * 2); z < zPos + (radius * 2); z++)
			{
				for(int y = 64; y < 120; y++)
				{
					if(worldObj.getBlockId(x, y, z) == TropicraftMod.tempLavaMoving.blockID)
					{
						worldObj.setBlock(x, y, z, 0);
					}
				}
			}
		}
	}

	public void getLavaLevel()
	{
		for(int y = 75; y < 128; y++)
		{
			if(worldObj.getBlockId(xPos, y, zPos) != Block.lavaStill.blockID && worldObj.getBlockId(xPos, y, zPos) != TropicraftMod.tempLavaMoving.blockID)
			{
				lavaLevel = y - 1;
				break;
			}
		}
	}

	public void findRadius()
	{
		if(radius == 0)
		{
			for(int x = 0; x < 30; x++)
			{
				if(worldObj.getBlockId(x + xPos, 10, zPos) != Block.lavaStill.blockID)
				{
					radius = x;
				}
			}
		}
	}

	public void raiseFallLava()
	{		
		if(lavaLevel >= 95)
		{
			falling = true;
		}

		if(!falling)
		{
			lavaLevel++;
		}

		for(int x = xPos - radius; x < xPos + radius; x++)
		{
			for(int z = zPos - radius; z < zPos + radius; z++)
			{
				if(Math.sqrt(Math.pow(x - xPos, 2) + Math.pow(z - zPos, 2)) < radius + 3)
					if((worldObj.getBlockId(x, 0, z) == Block.lavaStill.blockID)){
						worldObj.setBlockWithNotify(x, lavaLevel, z, falling ? 0 : lavaLevel >= 95 ? TropicraftMod.tempLavaMoving.blockID : Block.lavaStill.blockID);
					}
			}
		}

		if(lavaLevel == 90)
		{
			if(!falling)
			{
				worldObj.playSoundEffect(xPos, 73, zPos, "random.explode", 1.0F, rand.nextFloat() / 4 + 0.825F);
				int balls = rand.nextInt(25) + 35;
				for(int i = 0; i < balls; i++)
				{
					throwLavaFromCaldera((rand.nextDouble() * 0.5) + 1.25);
				}
				erupted = true;
			}
			else
			{				
				xLoop:
					for(int x = xPos - radius; x < xPos + radius; x++)
					{
						boolean ready = false;
						for(int z = zPos - radius; z < zPos + radius; z++)
						{
							if((worldObj.getBlockId(x, 0, z) == Block.lavaStill.blockID && rand.nextInt(20) != 0)){
								worldObj.setBlockWithNotify(x, lavaLevel, z, TropicraftMod.chunkBlock.blockID);
							}
						}
					}
			}
		}

		if(falling)
		{
			lavaLevel--;
		}

	}

	public void spewSmoke()
	{
		System.out.println("Spewing smoke");
		int n = rand.nextInt(6) + 4;
		for(int i = 0; i < n; i++)
		{
			worldObj.spawnEntityInWorld(new EntitySmoke(worldObj, xPos + rand.nextInt(10) - 5, lavaLevel + rand.nextInt(4), zPos + rand.nextInt(10) - 5));
		}
	}

	public void throwLavaFromCaldera(double force)
	{
		double x = rand.nextInt(radius) - (radius / 2) + xPos;
		double z = rand.nextInt(radius) - (radius / 2) + zPos; 
		double motX = ((rand.nextDouble() / 2) + 0.5) * (rand.nextBoolean() ? -1 : 1) * force;
		double motZ = ((rand.nextDouble() / 2) + 0.5) * (rand.nextBoolean() ? -1 : 1) * force;
		throwLava(x, lavaLevel + 2, z, motX, 1.0, motZ);
	}

	public void throwLava(double i, double j, double k, double xMot, double yMot, double zMot)
	{
		worldObj.spawnEntityInWorld(new EntityLavaBall(worldObj, i, j, k, xMot, yMot, zMot));
	}

}
