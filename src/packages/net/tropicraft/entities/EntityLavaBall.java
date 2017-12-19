package net.tropicraft.entities;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.src.ModLoader;
import net.minecraft.world.World;
import net.tropicraft.mods.TropicraftMod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityLavaBall extends Entity {

	public boolean setFire;
	public float size;
	public boolean held;
	public int lifeTimer;

	public EntityLavaBall(World world){
		super(world);
		setSize(1F, 1F);
		setFire = false;
		held = false;
		size = 1;
		lifeTimer = 0;

	}

	public EntityLavaBall(World world, double i, double j, double k, double motX, double motY, double motZ) {
		super(world);
		setSize(1F, 1F);
		setFire = false;
		setLocationAndAngles(i, j, k, 0, 0);
		motionX = motX;
		motionY = motY;
		motionZ = motZ;
		size = 1;
		held = false;
		lifeTimer = 0;

	}
	public EntityLavaBall(World world, float startSize){
		super(world);
		size = startSize;
		setSize(1F, 1F);
		setFire = false;
		held = true;
		lifeTimer = 0;

	}

	public boolean canBeCollidedWith(){
		return true;
	}
	public boolean canBePushed(){
		return true;
	}

	@SideOnly(Side.CLIENT)
	public void supahDrip() {
		ModLoader.getMinecraftInstance().effectRenderer.addEffect(new EntityFastDropletFX(worldObj, (float)(posX + rand.nextFloat() -.5F ),(float)( 
				posY   + rand.nextFloat() -.5F), 
				(float)(posZ + rand.nextFloat() - .5F)));
	}

	public void onUpdate(){
		super.onUpdate();

		//System.out.println("laba ball: " + posX + " " + posY + " " + posZ);
		
		if(lifeTimer < 500)
			lifeTimer++;
		else
			this.setDead();

		if(size < 1){
			size += .025;
		}
		if(onGround){
			motionZ *= .95;
			motionX *= .95;
		}

		motionY *= .99;
		if(!onGround){
			motionY -=.05F;
			if(worldObj.isRemote) {
				for(int i = 0; i < 5 + rand.nextInt(3) ; i++){
					supahDrip();
				}

			worldObj.spawnParticle("lava", (float)(posX + rand.nextFloat() -.5F ),(float)( 
					posY   + rand.nextFloat() -.5F), 
					(float)(posZ + rand.nextFloat() - .5F), 0.0D, 0.0D, 0.0D);
			
			}
		}
		if(isCollidedHorizontally){

			motionZ  = 0;
			motionX = 0;
		}


		if(worldObj.getBlockId((int)Math.floor(posX), (int)Math.floor(posY) -1, (int)Math.floor(posZ)) != 0 &&
				worldObj.getBlockMaterial((int)Math.floor(posX), (int)Math.floor(posY) - 1, (int)Math.floor(posZ)) != Material.lava && !held){
			if(setFire){
				worldObj.setBlockAndMetadataWithNotify((int)Math.floor(posX), (int)Math.floor(posY), (int)Math.floor(posZ), TropicraftMod.tempLavaMoving.blockID, 5);
				isDead = true;
			}
			if( !setFire){
				if(worldObj.getBlockId((int)Math.floor(posX) -1, (int)Math.floor(posY), (int)Math.floor(posZ)) == 0){
					worldObj.setBlockAndMetadataWithNotify((int)Math.floor(posX) -1, (int)Math.floor(posY), (int)Math.floor(posZ), TropicraftMod.tempLavaMoving.blockID, 4);
				}
				if(worldObj.getBlockId((int)Math.floor(posX) + 1, (int)Math.floor(posY), (int)Math.floor(posZ)) == 0){
					worldObj.setBlockAndMetadataWithNotify((int)Math.floor(posX) + 1, (int)Math.floor(posY), (int)Math.floor(posZ), TropicraftMod.tempLavaMoving.blockID, 4);
				}
				if(worldObj.getBlockId((int)Math.floor(posX), (int)Math.floor(posY), (int)Math.floor(posZ) - 1) == 0){
					worldObj.setBlockAndMetadataWithNotify((int)Math.floor(posX), (int)Math.floor(posY), (int)Math.floor(posZ) - 1, TropicraftMod.tempLavaMoving.blockID, 4);
				}
				if(worldObj.getBlockId((int)Math.floor(posX), (int)Math.floor(posY), (int)Math.floor(posZ) + 1) == 0){
					worldObj.setBlockAndMetadataWithNotify((int)Math.floor(posX), (int)Math.floor(posY), (int)Math.floor(posZ) + 1, TropicraftMod.tempLavaMoving.blockID, 4);
				}
				worldObj.setBlockAndMetadataWithNotify((int)Math.floor(posX), (int)Math.floor(posY), (int)Math.floor(posZ), TropicraftMod.tempLavaMoving.blockID, 4);
				setFire = true;

			}

		}


		this.moveEntity(motionX, motionY, motionZ);
	}
	@Override
	protected void entityInit() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		// TODO Auto-generated method stub

	}

}
