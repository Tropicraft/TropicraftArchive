package net.tropicraft.entities;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class EntityWallShell extends Entity {

	private int tickCounter1;
	public int direction;
	public int xPosition;
	public int yPosition;
	public int zPosition;

	public EntityWallShell(World world) {
		super(world);
		tickCounter1 = 0;
		direction = 0;
		yOffset = 0.0F;
		setSize(0.5F, 0.5F);
	}

	public EntityWallShell(World world, int i, int j, int k, int l, int itemID)
	{
		this(world);
		direction = l;
		xPosition = i;
		yPosition = j;
		zPosition = k;
		setColor(itemID);
		func_412_b(l);
	}
	protected void entityInit()
	{
		this.dataWatcher.addObject(16, new Integer(0));
	}
	public boolean canStay()
	{
		if(worldObj.getCollidingBoundingBoxes(this, boundingBox).size() > 0)
		{
			//  	System.out.println("Collision");
			return false;
		}
		int i = 1;
		int j = 1;
		int k = xPosition;
		int l = yPosition;
		int i1 = zPosition;
		if(direction == 0)
		{
			k = MathHelper.floor_double(posX - 0.5D);
		}
		if(direction == 1)
		{
			i1 = MathHelper.floor_double(posZ - 0.5D);
		}
		if(direction == 2)
		{
			k = MathHelper.floor_double(posX - 0.5D);
		}
		if(direction == 3)
		{
			i1 = MathHelper.floor_double(posZ - 0.5D);
		}
		l = MathHelper.floor_double(posY - 0.5D);
		for(int j1 = 0; j1 < i; j1++)
		{
			for(int k1 = 0; k1 < j; k1++)
			{
				Material material;
				if(direction == 0 || direction == 2)
				{
					material = worldObj.getBlockMaterial(k + j1, l + k1, zPosition);
				} else
				{
					material = worldObj.getBlockMaterial(xPosition, l + k1, i1 + j1);
				}
				if(!material.isSolid())
				{
					//           	System.out.println("Material isn't Solid");
					return false;
				}
			}

		}

		List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox);
		for(int l1 = 0; l1 < list.size(); l1++)
		{
			if(list.get(l1) instanceof EntityWallShell)
			{
				//      	System.out.println("Shell in bounding Box");
				return false;
			}
		}

		return true;
	}
	public boolean canBeCollidedWith()
	{
		return true;
	}
	public void onUpdate()
	{
		if(tickCounter1++ == 100 && !worldObj.isRemote)
		{

			tickCounter1 = 0;
			if(!canStay())
			{
				setDead();
				worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX, posY, posZ, new ItemStack(getColor(), 1, 0)));
			}
		}
	}
	public boolean attackEntityFrom(DamageSource damagesource, int i)
	{
		if(!isDead && !worldObj.isRemote)
		{
			setDead();
			setBeenAttacked();
			worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX, posY, posZ, new ItemStack(getColor(), 1, 0)));
		}
		return true;

	}
	public void func_412_b(int i)
	{
		direction = i;
		prevRotationYaw = rotationYaw = i * 90;
		float f = 16;
		float f1 = 16;
		float f2 = 16;
		if(i == 0 || i == 2)
		{
			f2 = 0.5F;
		} else
		{
			f = 0.5F;
		}
		f /= 32F;
		f1 /= 32F;
		f2 /= 32F;
		float f3 = (float)xPosition + 0.5F;
		float f4 = (float)yPosition + 0.5F;
		float f5 = (float)zPosition + 0.5F;
		float f6 = 0.5625F;
		if(i == 0)
		{
			f5 -= f6;
		}
		if(i == 1)
		{
			f3 -= f6;
		}
		if(i == 2)
		{
			f5 += f6;
		}
		if(i == 3)
		{
			f3 += f6;
		}
		if(i == 0)
		{
			f3 -= 0.0F;
		}
		if(i == 1)
		{
			f5 += 0.0F;
		}
		if(i == 2)
		{
			f3 += 0.0F;
		}
		if(i == 3)
		{
			f5 -= 0.0F;
		}
		f4 += 0.0F;
		setPosition(f3, f4, f5);
		float f7 = -0.00625F;
		boundingBox.setBounds(f3 - f - f7, f4 - f1 - f7, f5 - f2 - f7, f3 + f + f7, f4 + f1 + f7, f5 + f2 + f7);
	}
	
	public void setColor(int col) {
		this.dataWatcher.updateObject(16, Integer.valueOf(col));
	}
	
	public int getColor() {
		return this.dataWatcher.getWatchableObjectInt(16);
	}	
	
	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
	{
		nbttagcompound.setByte("Dir", (byte)direction);
		nbttagcompound.setInteger("TileX", xPosition);
		nbttagcompound.setInteger("TileY", yPosition);
		nbttagcompound.setInteger("TileZ", zPosition);
	}

	public void readEntityFromNBT(NBTTagCompound nbttagcompound)
	{
		direction = nbttagcompound.getByte("Dir");
		xPosition = nbttagcompound.getInteger("TileX");
		yPosition = nbttagcompound.getInteger("TileY");
		zPosition = nbttagcompound.getInteger("TileZ");	
		func_412_b(direction);
		System.out.println("Read from NBT");
	}
}
