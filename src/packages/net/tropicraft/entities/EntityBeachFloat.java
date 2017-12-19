package net.tropicraft.entities;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.tropicraft.mods.TropicraftMod;

import java.util.List;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityBeachFloat extends Entity {

	public EntityBeachFloat(World world) {
		super(world);
		boatCurrentDamage = 0;
		boatTimeSinceHit = 0;
		boatRockDirection = 1;
		preventEntitySpawning = true;
		setSize(2F, 0.5F);
		yOffset = height / 1F;
		isonfloat = false;
	}

	protected boolean canTriggerWalking() {
		return false;
	}

	protected void entityInit() {
		dataWatcher.addObject(16, new Integer(0));
	}

	public void setColor(int col) {
		dataWatcher.updateObject(16, Integer.valueOf(col));
	}

	public int getColor() {
		return dataWatcher.getWatchableObjectInt(16);
	}

	protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		nbttagcompound.setInteger("Color", Integer.valueOf(getColor()));
	}

	protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		setColor(Integer.valueOf(nbttagcompound.getInteger("Color")));
	}

	public AxisAlignedBB getCollisionBox(Entity entity) {
		/*
		 * if(mod_BeachFloatHandler.playerFloatMap.containsKey(entity)){ return
		 * null; }
		 */
		if (this.ridingEntity != null)
			return null;

		return entity.boundingBox;
	}

	public AxisAlignedBB getBoundingBox() {
		return boundingBox;
	}

	public boolean canBePushed() {
		return !isonfloat;
	}

	public EntityBeachFloat(World world, double d, double d1, double d2,
			float f1, int type) {
		this(world);
		setPosition(d, d1 + (double) yOffset, d2);
		motionX = 0.0D;
		motionY = 0.0D;
		motionZ = 0.0D;
		prevPosX = d;
		prevPosY = d1;
		prevPosZ = d2;
		rotationYaw = f1;
		setColor(type);
	}

	public double getMountedYOffset() {
		return (double) height * 0.0D - .25D;// alter the second value to adjust
		// the sit height
	}

	public boolean attackEntityFrom(DamageSource damagesource, int i) {
		if (worldObj.isRemote || isDead) {
			return true;
		}
		boatRockDirection = -boatRockDirection;
		boatTimeSinceHit = 10;
		boatCurrentDamage += i * 10;
		setBeenAttacked();
		if (boatCurrentDamage > 40) {
			if (riddenByEntity != null) {
				riddenByEntity.mountEntity(this);
			}

			entityDropItem(new ItemStack(TropicraftMod.beachFloat, 1,
					getColor()), 0.0F);

			setDead();
		}
		return true;
	}

	@SideOnly(Side.CLIENT)
	public void performHurtAnimation() {
		boatRockDirection = -boatRockDirection;
		boatTimeSinceHit = 10;
		boatCurrentDamage += boatCurrentDamage * 10;
	}

	public boolean canBeCollidedWith() {
		return !isDead;
	}

	@SideOnly(Side.CLIENT)
	public void setPositionAndRotation2(double d, double d1, double d2,
			float f, float f1, int i) {
		boatX = d;
		boatY = d1;
		boatZ = d2;
		boatYaw = f;
		boatPitch = f1;
		field_9394_d = i + 4;
		motionX = velocityX;
		motionY = velocityY;
		motionZ = velocityZ;
	}

	protected boolean canDespawn() {
		return true;
	}

	@SideOnly(Side.CLIENT)
	public void setVelocity(double d, double d1, double d2) {
		velocityX = motionX = d;
		velocityY = motionY = d1;
		velocityZ = motionZ = d2;
	}

	public void onUpdate() {
		super.onUpdate();
		// setVelocity(motionX, -0.05D, motionZ);
		if (boatTimeSinceHit > 0) {
			boatTimeSinceHit--;
		}
		if (boatCurrentDamage > 0) {
			boatCurrentDamage--;
		}
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		int i = 5;
		double d = 0.0D;
		for (int j = 0; j < i; j++) {
			double d5 = (boundingBox.minY + ((boundingBox.maxY - boundingBox.minY) * (double) (j + 0))
					/ (double) i) - 0.125D;
			double d9 = (boundingBox.minY + ((boundingBox.maxY - boundingBox.minY) * (double) (j + 1))
					/ (double) i) - 0.125D;
			AxisAlignedBB axisalignedbb = AxisAlignedBB.getAABBPool()
					.addOrModifyAABBInPool(boundingBox.minX, d5,
							boundingBox.minZ, boundingBox.maxX, d9,
							boundingBox.maxZ);
			if (worldObj.isAABBInMaterial(axisalignedbb, Material.water)) {
				d += 1.0D / (double) i;
			}
		}

		if (worldObj.isRemote) {
			if (field_9394_d > 0) {
				double d1 = posX + (boatX - posX) / (double) field_9394_d;
				double d6 = posY + (boatY - posY) / (double) field_9394_d;
				double d10 = posZ + (boatZ - posZ) / (double) field_9394_d;
				double d14;
				for (d14 = boatYaw - (double) rotationYaw; d14 < -180D; d14 += 360D) {
				}
				for (; d14 >= 180D; d14 -= 360D) {
				}
				rotationYaw += d14 / (double) field_9394_d;
				rotationPitch += (boatPitch - (double) rotationPitch)
						/ (double) field_9394_d;
				field_9394_d--;
				setPosition(d1, d6, d10);
				setRotation(rotationYaw, rotationPitch);
			} else {
				/*
				 * double d2 = posX + motionX; double d7 = posY + motionY;
				 * double d11 = posZ + motionZ; setPosition(d2, d7, d11); if
				 * (onGround) { motionX *= 0.5D; motionY *= 0.5D; motionZ *=
				 * 0.5D; } motionX *= 0.99000000953674316D; motionY *=
				 * 0.94999998807907104D; motionZ *= 0.99000000953674316D;
				 */
			}
			return;
		}
		if (d < 1.0D) {
			double d3 = d * 2D - 1.0D;
			motionY += 0.039999999105930328D * d3;
		} else {
			if (motionY < 0.0D) {
				motionY /= 2D;
			}
			motionY += 0.0070000002160668373D;
		}
		// if(riddenByEntity != null) this allows the rider to control the chair
		// {
		// motionX += riddenByEntity.motionX * 0.20000000000000001D;
		// motionZ += riddenByEntity.motionZ * 0.20000000000000001D;
		// }
		double d4 = 0.40000000000000002D;
		if (motionX < -d4) {
			motionX = -d4;
		}
		if (motionX > d4) {
			motionX = d4;
		}
		if (motionZ < -d4) {
			motionZ = -d4;
		}
		if (motionZ > d4) {
			motionZ = d4;
		}
		if (onGround) {
			motionX *= 0.5D;
			motionY *= 0.5D;
			motionZ *= 0.5D;
		}
		moveEntity(motionX, motionY, motionZ);
		double d8 = Math.sqrt(motionX * motionX + motionZ * motionZ);
		if (d8 > 0.14999999999999999D) {
			double d12 = Math
					.cos(((double) rotationYaw * 3.1415926535897931D) / 180D);
			double d15 = Math
					.sin(((double) rotationYaw * 3.1415926535897931D) / 180D);
			for (int i1 = 0; (double) i1 < 1.0D + d8 * 60D; i1++) {
				double d18 = rand.nextFloat() * 2.0F - 1.0F;
				double d20 = (double) (rand.nextInt(2) * 2 - 1) * 0.69999999999999996D;
				if (rand.nextBoolean()) {
					double d21 = (posX - d12 * d18 * 0.80000000000000004D)
							+ d15 * d20;
					double d23 = posZ - d15 * d18 * 0.80000000000000004D - d12
							* d20;
					worldObj.spawnParticle("splash", d21, posY - 0.125D, d23,
							motionX, motionY, motionZ);
				} else {
					double d22 = posX + d12 + d15 * d18 * 0.69999999999999996D;
					double d24 = (posZ + d15) - d12 * d18
							* 0.69999999999999996D;
					worldObj.spawnParticle("splash", d22, posY - 0.125D, d24,
							motionX, motionY, motionZ);
				}
			}

		}
		if ((rand.nextInt(100) == 0)) {
			motionX += .03D;
		}
		if ((rand.nextInt(100) == 0)) {
			motionX -= .03D;
		}
		if ((rand.nextInt(100) == 0)) {
			motionZ -= .03D;
		}
		if ((rand.nextInt(100) == 0)) {
			motionZ += .03D;
		}

		{
			motionX *= 0.94999998807907104D;
			motionY *= 0.94999998807907104D;
			motionZ *= 0.94999998807907104D;
		}

		rotationPitch = 0.0F;
		double d13 = rotationYaw;
		double d16 = prevPosX - posX;
		double d17 = prevPosZ - posZ;
		if (d16 * d16 + d17 * d17 > 0.001D) {
			d13 = (float) ((Math.atan2(d17, d16) * 180D) / 3.1415926535897931D);
		}
		double d19;
		for (d19 = d13 - (double) rotationYaw; d19 >= 180D; d19 -= 360D) {
		}
		for (; d19 < -180D; d19 += 360D) {
		}
		if (d19 > 20D) {
			d19 = 20D;
		}
		if (d19 < -20D) {
			d19 = -20D;
		}
		rotationYaw += d19;
		setRotation(rotationYaw, rotationPitch);
		List list = worldObj.getEntitiesWithinAABBExcludingEntity(this,
				boundingBox.expand(0.20000000298023224D, 0.0D,
						0.20000000298023224D));
		if (list != null && list.size() > 0) {
			for (int j1 = 0; j1 < list.size(); j1++) {
				Entity entity = (Entity) list.get(j1);
				if (entity != riddenByEntity && entity.canBePushed()
						&& (entity instanceof EntityBeachFloat)) {
					entity.applyEntityCollision(this);
				}
			}

		}
		for (int k1 = 0; k1 < 4; k1++) {
			int l1 = MathHelper.floor_double(posX + ((double) (k1 % 2) - 0.5D)
					* 0.80000000000000004D);
			int i2 = MathHelper.floor_double(posY);
			int j2 = MathHelper.floor_double(posZ + ((double) (k1 / 2) - 0.5D)
					* 0.80000000000000004D);
			if (worldObj.getBlockId(l1, i2, j2) == Block.snow.blockID) {
				worldObj.setBlockWithNotify(l1, i2, j2, 0);
			}
		}

		if (riddenByEntity != null && riddenByEntity.isDead) {
			riddenByEntity = null;
		}
	}

	public void updateRiderPosition() {
		if (riddenByEntity == null) {
			setOnFloat(false);
			// System.out.println("no longer on float");
			return;
		} else {
			double d = Math
					.cos(((double) rotationYaw * 3.1415926535897931D) / 180D) * 0.0D;
			double d1 = Math
					.sin(((double) rotationYaw * 3.1415926535897931D) / 180D) * 0.0D;
			riddenByEntity.setPosition(posX + d, posY + getMountedYOffset()
					+ riddenByEntity.getYOffset(), posZ + d1);
			return;
		}
	}

	@SideOnly(Side.CLIENT)
	public float getShadowSize() {
		return 0.0F;
	}

	public boolean interact(EntityPlayer entityplayer) {

		if (isonfloat) {
			if (!worldObj.isRemote) {
				entityplayer.mountEntity(this);
			}
			if (worldObj.isRemote) {				
				setOnFloat(false);
				FMLClientHandler.instance().getClient().gameSettings.thirdPersonView = 0;
			}		
			return true;

		} else {
			if (!worldObj.isRemote) {				
				entityplayer.mountEntity(this);				
			}
			if (worldObj.isRemote) {				
				FMLClientHandler.instance().getClient().gameSettings.thirdPersonView = 1;
				setOnFloat(true);
			}
			return false;
		}

	}

	public void setOnFloat(boolean boo) {
		isonfloat = boo;
	}

	public boolean isOnFloat() {
		return isonfloat;
	}

	public int boatCurrentDamage;
	public int boatTimeSinceHit;
	public int boatRockDirection;
	private int field_9394_d;
	private double boatX;
	private double boatY;
	private double boatZ;
	private double boatYaw;
	private double boatPitch;
	@SideOnly(Side.CLIENT)
	private double velocityX;
	@SideOnly(Side.CLIENT)
	private double velocityY;
	@SideOnly(Side.CLIENT)
	private double velocityZ;
	public boolean isonfloat;

}
