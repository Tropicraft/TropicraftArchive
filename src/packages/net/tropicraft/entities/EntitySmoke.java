package net.tropicraft.entities;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntitySmoke extends Entity {

	public float smokeSize = 0.8F;
	public float smokeAlpha = 0.5F;
	
	public EntitySmoke(World world) {
		super(world);
		ignoreFrustumCheck = true;
	}
	
	public EntitySmoke(World world, double x, double y, double z) {
		super(world);
		posX = x;
		posY = y;
		posZ = z;
		ignoreFrustumCheck = true;
	}

	@Override
	public void onUpdate()
	{		
		if(ticksExisted % 120 == 0)
		{
			smokeSize += 0.05F;
		}
		posY += 0.1;
		
		setPosition(posX, posY, posZ);
		
		if(posY > 128)
		{
			smokeAlpha = (float) ((148 - posY) / 40);
		}
		
		if(posY == 148)
		{
			setDead();
		}
	}
	
	@Override
	protected void entityInit() {
		
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		
	}

}
