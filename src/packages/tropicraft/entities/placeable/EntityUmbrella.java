package tropicraft.entities.placeable;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import tropicraft.items.TropicraftItems;
import weather.wind.WindHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityUmbrella extends Entity implements WindHandler {

	public EntityUmbrella(World world)
	{
		super(world);
		umbrellaCurrentDamage = 0;
		umbrellaTimeSinceHit = 0;
		umbrellaRockDirection = 1;
		preventEntitySpawning = true;       
		ignoreFrustumCheck = true;
		setSize(.125F, 4F);
		//yOffset = 5F;

	}	
	
/*	@Override
	@SideOnly(Side.CLIENT)
    public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9)
    {
        this.setPosition(par1, par3, par5);
        this.setRotation(par7, par8);        
    }*/

	protected boolean canTriggerWalking()
	{
		return false;
	}

	protected void entityInit()
	{
		dataWatcher.addObject(COLOR, new Integer(0));
		if(worldObj.isRemote)
			setVelocity(motionX, -0.05D, motionZ);
	}

	protected void writeEntityToNBT(NBTTagCompound nbttagcompound)
	{
		nbttagcompound.setInteger("Color", Integer.valueOf(getColor()));
	}

	protected void readEntityFromNBT(NBTTagCompound nbttagcompound)
	{
		setColor(Integer.valueOf(nbttagcompound.getInteger("Color")));
	}
	
	public AxisAlignedBB getCollisionBox(Entity entity)
	{
		return entity.boundingBox;
	}

	public AxisAlignedBB getBoundingBox()
	{
		return boundingBox;
	}

	public EntityUmbrella(World world, double d, double d1, double d2, int beachColor)
	{
		this(world);
		setPosition(d, d1 + (double)yOffset, d2);
		motionX = 0.0D;
		motionY = 0.0D;
		motionZ = 0.0D;
		prevPosX = d;
		prevPosY = d1;
		prevPosZ = d2;
		setColor(beachColor);
	}

	public double getMountedYOffset()
	{
		return (double)height * 0.0D - 0D;//alter the second value to adjust the sit height
	}

	public boolean attackEntityFrom(DamageSource damagesource, int i)
	{
		if(worldObj.isRemote || isDead)
		{
			return true;
		}
		umbrellaRockDirection = -umbrellaRockDirection;
		umbrellaTimeSinceHit = 10;
		umbrellaCurrentDamage += i * 10;
		setBeenAttacked();
		if(umbrellaCurrentDamage > 40)
		{
			entityDropItem(new ItemStack(TropicraftItems.umbrella, 1, getColor()), 0.0F);

			setDead();
		}
		return true;
	}
	
	public void setColor(int col) {
		this.dataWatcher.updateObject(COLOR, Integer.valueOf(col));
	}

	public int getColor() {
		return this.dataWatcher.getWatchableObjectInt(COLOR);
	}

	public void performHurtAnimation()
	{
		umbrellaRockDirection = -umbrellaRockDirection;
		umbrellaTimeSinceHit = 10;
		umbrellaCurrentDamage += umbrellaCurrentDamage * 10;
	}


	@SideOnly(Side.CLIENT)
	public void setPositionAndRotation2(double d, double d1, double d2, float f, 
			float f1, int i)
	{
		umbrellaX = d;
		umbrellaY = d1;
		umbrellaZ = d2;
		umbrellaYaw = f;
		umbrellaPitch = f1;
		field_9394_d = i + 4;
		if(worldObj.isRemote) {
		motionX = velocityX;
		motionY = velocityY;
		motionZ = velocityZ;
		}
	}

	@SideOnly(Side.CLIENT)
	public void setVelocity(double d, double d1, double d2)
	{
		velocityX = motionX = d;
		velocityY = motionY = d1;
		velocityZ = motionZ = d2;
	}

	public void onUpdate()
	{
		super.onUpdate();
		
		if(umbrellaTimeSinceHit > 0)
		{
			umbrellaTimeSinceHit--;
		}
		if(umbrellaCurrentDamage > 0)
		{
			umbrellaCurrentDamage--;
		}
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		int i = 5;
		double d = 0.0D;
		for(int j = 0; j < i; j++)
		{
			double d5 = (boundingBox.minY + ((boundingBox.maxY - boundingBox.minY) * (double)(j + 0)) / (double)i) - 0.125D;
			double d9 = (boundingBox.minY + ((boundingBox.maxY - boundingBox.minY) * (double)(j + 1)) / (double)i) - 0.125D;
			AxisAlignedBB axisalignedbb = AxisAlignedBB.getAABBPool().getAABB(boundingBox.minX, d5, boundingBox.minZ, boundingBox.maxX, d9, boundingBox.maxZ);
			if(worldObj.isAABBInMaterial(axisalignedbb, Material.water))
			{
				d += 1.0D / (double)i;
			}
		}

		if(worldObj.isRemote)
		{
			if(field_9394_d > 0)
			{
				double d1 = posX + (umbrellaX - posX) / (double)field_9394_d;
				double d6 = posY + (umbrellaY - posY) / (double)field_9394_d;
				double d10 = posZ + (umbrellaZ - posZ) / (double)field_9394_d;
				double d14;
				for(d14 = umbrellaYaw - (double)rotationYaw; d14 < -180D; d14 += 360D) { }
				for(; d14 >= 180D; d14 -= 360D) { }
				rotationYaw += d14 / (double)field_9394_d;
				rotationPitch += (umbrellaPitch - (double)rotationPitch) / (double)field_9394_d;
				field_9394_d--;
				setPosition(d1, d6, d10);
				setRotation(rotationYaw, rotationPitch);
			} else
			{
		/*		double d2 = posX + motionX;
				double d7 = posY + motionY;
				double d11 = posZ + motionZ;
				setPosition(d2, d7, d11);
				if(onGround)
				{
					motionX *= 0.5D;
					motionY *= 0.5D;
					motionZ *= 0.5D;
				}
				motionX *= 0.99000000953674316D;
				motionY *= 0.94999998807907104D;
				motionZ *= 0.99000000953674316D; */
			}
			return;
		}
		if(d < 1.0D)
		{
			double d3 = d * 2D - 1.0D;
			motionY += 0.039999999105930328D * d3;
		} else
		{
			if(motionY < 0.0D)
			{
				motionY /= 2D;
			}
			motionY += 0.0070000002160668373D;
		}
		//if(riddenByEntity != null) this allows the rider to control the chair
		//{
			//motionX += riddenByEntity.motionX * 0.20000000000000001D;
			//motionZ += riddenByEntity.motionZ * 0.20000000000000001D;
			//}
		double d4 = 0.40000000000000002D;
		if(motionX < -d4)
		{
			motionX = -d4;
		}
		if(motionX > d4)
		{
			motionX = d4;
		}
		if(motionZ < -d4)
		{
			motionZ = -d4;
		}
		if(motionZ > d4)
		{
			motionZ = d4;
		}
		if(onGround)
		{
			motionX *= 0.5D;
			motionY *= 0.5D;
			motionZ *= 0.5D;
		}
		moveEntity(motionX, motionY, motionZ);
		double d8 = Math.sqrt(motionX * motionX + motionZ * motionZ);
		if(d8 > 0.14999999999999999D)
		{
			double d12 = Math.cos(((double)rotationYaw * 3.1415926535897931D) / 180D);
			double d15 = Math.sin(((double)rotationYaw * 3.1415926535897931D) / 180D);
			for(int i1 = 0; (double)i1 < 1.0D + d8 * 60D; i1++)
			{
				double d18 = rand.nextFloat() * 2.0F - 1.0F;
				double d20 = (double)(rand.nextInt(2) * 2 - 1) * 0.69999999999999996D;
				if(rand.nextBoolean())
				{
					double d21 = (posX - d12 * d18 * 0.80000000000000004D) + d15 * d20;
					double d23 = posZ - d15 * d18 * 0.80000000000000004D - d12 * d20;
					worldObj.spawnParticle("splash", d21, posY - 0.125D, d23, motionX, motionY, motionZ);
				} else
				{
					double d22 = posX + d12 + d15 * d18 * 0.69999999999999996D;
					double d24 = (posZ + d15) - d12 * d18 * 0.69999999999999996D;
					worldObj.spawnParticle("splash", d22, posY - 0.125D, d24, motionX, motionY, motionZ);
				}
			}

		}

		{
			motionX *= 0.1D;
			motionY *= 0.94999998807907104D;
			motionZ *= 0.1D;
		}
		rotationPitch = 0.0F;
		double d13 = rotationYaw;
		double d16 = prevPosX - posX;
		double d17 = prevPosZ - posZ;
		if(d16 * d16 + d17 * d17 > 0.001D)
		{
			d13 = (float)((Math.atan2(d17, d16) * 180D) / 3.1415926535897931D);
		}
		double d19;
		for(d19 = d13 - (double)rotationYaw; d19 >= 180D; d19 -= 360D) { }
		for(; d19 < -180D; d19 += 360D) { }
		if(d19 > 20D)
		{
			d19 = 20D;
		}
		if(d19 < -20D)
		{
			d19 = -20D;
		}
		rotationYaw += d19;
		setRotation(rotationYaw, rotationPitch);
		List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));
		if(list != null && list.size() > 0)
		{
			for(int j1 = 0; j1 < list.size(); j1++)
			{
				Entity entity = (Entity)list.get(j1);
				if(entity != riddenByEntity && entity.canBePushed() && (entity instanceof EntityChair))
				{
					entity.applyEntityCollision(this);
				}
			}

		}
		for(int k1 = 0; k1 < 4; k1++)
		{
			int l1 = MathHelper.floor_double(posX + ((double)(k1 % 2) - 0.5D) * 0.80000000000000004D);
			int i2 = MathHelper.floor_double(posY);
			int j2 = MathHelper.floor_double(posZ + ((double)(k1 / 2) - 0.5D) * 0.80000000000000004D);
			if(worldObj.getBlockId(l1, i2, j2) == Block.snow.blockID)
			{
				worldObj.setBlockToAir(l1, i2, j2);
			}
		}

		if(riddenByEntity != null && riddenByEntity.isDead)
		{
			riddenByEntity = null;
		}

	}   

	@Override
	public boolean canBeCollidedWith()
	{
		return true;
	}
	@Override
	public boolean canBePushed(){
		return false;
	}

	@SideOnly(Side.CLIENT)
	public float getShadowSize()
	{
		return 0.0F;
	}



	private final int COLOR = 17;
	public int umbrellaCurrentDamage;
	public int umbrellaTimeSinceHit;
	public int umbrellaRockDirection;
	private int field_9394_d;
	private double umbrellaX;
	private double umbrellaY;
	private double umbrellaZ;
	private double umbrellaYaw;
	private double umbrellaPitch;
	@SideOnly(Side.CLIENT)
	private double velocityX;
	@SideOnly(Side.CLIENT)
	private double velocityY;
	@SideOnly(Side.CLIENT)
	private double velocityZ;
	@Override
	public float getWindWeight() {
		// TODO Auto-generated method stub
		return 9999999;
	}

	@Override
	public int getParticleDecayExtra() {
		// TODO Auto-generated method stub
		return 0;
	}

}
