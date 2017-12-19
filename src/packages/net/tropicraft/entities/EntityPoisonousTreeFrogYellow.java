package net.tropicraft.entities;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.tropicraft.mods.TropicraftMod;

import java.util.List;
import java.util.Random;

public class EntityPoisonousTreeFrogYellow extends EntityAnimal implements IMob
{

    public EntityPoisonousTreeFrogYellow(World world)
    {
        super(world);
        texture = "/tropicalmod/treefrogyellow.png";
        setSize(0.9F, 1.3F);
        health = 4;
        savedhealth = health;
        onWall = false;
        random = new Random();
        side = -1;
    }

    public boolean getCanSpawnHere()
    {
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(boundingBox.minY);
        int k = MathHelper.floor_double(posZ);
        return worldObj.getBlockId(i, j - 1, k) == Block.grass.blockID && worldObj.getFullBlockLightValue(i, j, k) > 8 && super.getCanSpawnHere();
    }

    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        savedhealth = health;
    /*    if(onWall && System.currentTimeMillis() >= timer)
        {
            onWall = false;
            motionX = savedX;
            motionY = -0.35699998688697815D;
            motionZ = savedZ;
        }
        if(onWall)
        {
            timer = System.currentTimeMillis() + 50000L;
            savedX = motionX;
            savedY = motionY;
            savedZ = motionZ;
            motionX = 0.0D;
            motionY = 0.0D;
            motionZ = 0.0D;
        }
        if(isCollidedHorizontally && !onWall && worldObj.getBlockId((int)posX, (int)(posY - 1.5D), (int)posZ) == 0 && rand.nextInt(4) > 2)
        {
            if(worldObj.getBlockId((int)posX - 1, (int)posY, (int)posZ) == 0)
            {
                side = 1;
            } else
            if(worldObj.getBlockId((int)posX, (int)posY, (int)posZ - 1) == 0)
            {
                side = 2;
            } else
            if(worldObj.getBlockId((int)posX + 1, (int)posY, (int)posZ) == 0)
            {
                side = 3;
            } else
            if(worldObj.getBlockId((int)posX, (int)posY, (int)posZ + 1) == 0)
            {
                side = 4;
            }
            onWall = true;
            timer = System.currentTimeMillis() + 5000L;
        }
        */
        if(newPosRotationIncrements > 0)
        {
            double d = posX + (newPosX - posX) / (double)newPosRotationIncrements;
            double d1 = posY + (newPosY - posY) / (double)newPosRotationIncrements;
            double d2 = posZ + (newPosZ - posZ) / (double)newPosRotationIncrements;
            double d3;
            for(d3 = newRotationYaw - (double)rotationYaw; d3 < -180D; d3 += 360D) { }
            for(; d3 >= 180D; d3 -= 360D) { }
            rotationYaw += d3 / (double)newPosRotationIncrements;
            rotationPitch += (newRotationPitch - (double)rotationPitch) / (double)newPosRotationIncrements;
            newPosRotationIncrements--;
            setPosition(d, d1, d2);
            setRotation(rotationYaw, rotationPitch);
            List list1 = worldObj.getCollidingBoundingBoxes(this, boundingBox.getOffsetBoundingBox(0.03125D, 0.0D, 0.03125D));
            if(list1.size() > 0)
            {
                double d4 = 0.0D;
                for(int j = 0; j < list1.size(); j++)
                {
                    AxisAlignedBB axisalignedbb = (AxisAlignedBB)list1.get(j);
                    if(axisalignedbb.maxY > d4)
                    {
                        d4 = axisalignedbb.maxY;
                    }
                }

                d1 += d4 - boundingBox.minY;
                setPosition(d, d1, d2);
            }
        }
        if(isMovementBlocked())
        {
            isJumping = false;
            moveStrafing = 0.0F;
            moveForward = 0.0F;
            randomYawVelocity = 0.0F;
        } else
        if(!worldObj.isRemote)
        {
            updateEntityActionState();
        }
        boolean flag = isInWater();
        boolean flag1 = handleLavaMovement();
        if(isJumping)
        {
            if(flag)
            {
                motionY += 0.039999999105930328D;
            } else
            if(flag1)
            {
                motionY += 0.039999999105930328D;
            } else
            if(onGround)
            {
                jump();
            }
        }
        moveStrafing *= 0.7F;
        moveForward *= 0.7F;
        randomYawVelocity *= 0.8F;
        moveEntityWithHeading(moveStrafing, moveForward);
        List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));
        if(list != null && list.size() > 0)
        {
            for(int i = 0; i < list.size(); i++)
            {
                Entity entity = (Entity)list.get(i);
                if(entity.canBePushed())
                {
                    entity.applyEntityCollision(this);
                }
            }

        }
    }

    public void jump()
    {
        motionY = 0.69699998688697817D;
    }

	@Override
	protected Entity findPlayerToAttack() {
		if (worldObj.difficultySetting > 0) {
			EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this,
					4D);
			if (entityplayer != null && canEntityBeSeen(entityplayer)
					&& worldObj.difficultySetting > 0
					&& !entityplayer.capabilities.isCreativeMode) {
				return entityplayer;
			} else {
				return null;
			}
		} else
			return null;
	}

    protected void attackEntity(Entity entity, float f)
    {
        if(f < 4F && worldObj.difficultySetting != 0)
        {
            double d = entity.posX - posX;
            double d1 = entity.posZ - posZ;
            if(attackTime == 0 && !worldObj.isRemote && worldObj.difficultySetting != 0)
            {
                EntityPoisonBlot entitypoisonblot = new EntityPoisonBlot(worldObj, this);
                entitypoisonblot.posY += 1.3999999761581421D;
                double d2 = (entity.posY + (double)entity.getEyeHeight()) - 0.20000000298023224D - entitypoisonblot.posY;
                float f1 = MathHelper.sqrt_double(d * d + d1 * d1) * 0.2F;
                worldObj.playSoundAtEntity(this, "frogspit", 1.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.8F));
                worldObj.spawnEntityInWorld(entitypoisonblot);
                entitypoisonblot.setArrowHeading(d, d2 + (double)f1, d1, 0.6F, 12F);
                attackTime = 50;
            }
            rotationYaw = (float)((Math.atan2(d1, d) * 180D) / 3.1415927410125732D) - 90F;
            hasAttacked = true;
        }
    }


    protected void fall(float f)
    {
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
    }

    protected String getLivingSound()
    {
        return null;
    }

    protected String getHurtSound()
    {
        return null;
    }

    protected String getDeathSound()
    {
        return null;
    }

    protected float getSoundVolume()
    {
        return 0.4F;
    }

	/**
	 * Called when the mob's health reaches 0.
	 */
	public void onDeath(DamageSource par1DamageSource)
	{
		super.onDeath(par1DamageSource);
		if(!worldObj.isRemote) {
			this.dropItem(TropicraftMod.poisonSkin.shiftedIndex, 1);
		}
	}

    public int savedhealth;
    public boolean onWall;
    public long timer;
    public double savedX;
    public double savedY;
    public double savedZ;
    public Random random;
    public int side;
	
    public EntityAgeable createChild(EntityAgeable par1EntityAgeable)
    {
        return null;
    }

	@Override
	public int getMaxHealth() {
		// TODO Auto-generated method stub
		return 4;
	}
}
