package net.tropicraft.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.tropicraft.mods.TropicraftMod;

import java.util.Iterator;
import java.util.List;

public class EntityIguana extends EntityAnimal
{

    public EntityIguana(World world)
    {
        super(world);
        field_753_a = false;
        field_752_b = 0.0F;
        destPos = 0.0F;
        field_755_h = 1.0F;
        texture = "/tropicalmod/iggytexture.png";
        setSize(0.3F, 0.4F);
        health = 9;
        this.renderDistanceWeight = 5.0D;
    }

    protected float getSoundVolume()
    {
        return 0.4F;
    }

    protected void entityInit()
    {
        super.entityInit();
        dataWatcher.addObject(16, Byte.valueOf((byte)0));
        dataWatcher.addObject(17, "");
        dataWatcher.addObject(18, new Integer(health));
    }

    public void onLivingUpdate()
    {
        super.onLivingUpdate();
    }

    protected void fall(float f)
    {
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("Angry", isIguanaAngry());
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        setIguanaAngry(nbttagcompound.getBoolean("Angry"));
    }

    protected String getLivingSound()
    {
        return "iggyliving";
    }

    protected String getHurtSound()
    {
        return "iggyattack";
    }

    protected String getDeathSound()
    {
        return "iggydeath";
    }

    protected int getDropItemId()
    {
        return TropicraftMod.scale.shiftedIndex;
    }

    public boolean attackEntityFrom(DamageSource damagesource, int i)
    {
        Entity entity = damagesource.getEntity();
        if(entity != null && !(entity instanceof EntityPlayer) && !(entity instanceof EntityArrow))
        {
            i = (i + 1) / 2;
        }
        if(super.attackEntityFrom(DamageSource.generic, i))
        {
            if(!isIguanaAngry())
            {
                if(entity instanceof EntityPlayer)
                {
                    setIguanaAngry(true);
                    entityToAttack = entity;
                }
                if(entity instanceof EntityLiving)
                {
                    List list = worldObj.getEntitiesWithinAABB(net.tropicraft.entities.EntityIguana.class, AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(posX, posY, posZ, posX + 1.0D, posY + 1.0D, posZ + 1.0D).expand(16D, 4D, 16D));
                    Iterator iterator = list.iterator();
                    do
                    {
                        if(!iterator.hasNext())
                        {
                            break;
                        }
                        Entity entity1 = (Entity)iterator.next();
                        EntityIguana entityiguana = (EntityIguana)entity1;
                        if(entityiguana.entityToAttack == null)
                        {
                            entityiguana.entityToAttack = entity;
                            if(entity instanceof EntityPlayer)
                            {
                                entityiguana.setIguanaAngry(true);
                            }
                        }
                    } while(true);
                }
            } else
            if(entity != this && entity != null)
            {
                if(entity instanceof EntityPlayer)
                {
                    return true;
                }
                entityToAttack = entity;
            }
            return true;
        } else
        {
            return false;
        }
    }

    protected Entity findPlayerToAttack()
    {
        if(isIguanaAngry())
        {
            return worldObj.getClosestPlayerToEntity(this, 16D);
        } else
        {
            return null;
        }
    }

    protected void attackEntity(Entity entity, float f)
    {
        if(f > 2.0F && f < 6F && rand.nextInt(10) == 0)
        {
            if(onGround)
            {
                double d = entity.posX - posX;
                double d1 = entity.posZ - posZ;
                float f1 = MathHelper.sqrt_double(d * d + d1 * d1);
                motionX = (d / (double)f1) * 0.5D * 0.80000001192092896D + motionX * 0.20000000298023224D;
                motionZ = (d1 / (double)f1) * 0.5D * 0.80000001192092896D + motionZ * 0.20000000298023224D;
                motionY = 0.40000000596046448D;
            }
        } else
        if((double)f < 1.5D && entity.boundingBox.maxY > boundingBox.minY && entity.boundingBox.minY < boundingBox.maxY)
        {
            attackTime = 20;
            byte byte0 = 2;
            entity.attackEntityFrom(DamageSource.causeMobDamage(this), byte0);
        }
    }

    public boolean interact(EntityPlayer entityplayer)
    {
        if(!worldObj.isRemote)
        {
            setPathToEntity(null);
            worldObj.setEntityState(this, (byte)7);
        } else
        {
            worldObj.setEntityState(this, (byte)6);
        }
        return true;
    }

    public boolean isIguanaAngry()
    {
        return (dataWatcher.getWatchableObjectByte(16) & 2) != 0;
    }

    public void setIguanaAngry(boolean flag)
    {
        byte byte0 = dataWatcher.getWatchableObjectByte(16);
        if(flag)
        {
            dataWatcher.updateObject(16, Byte.valueOf((byte)(byte0 | 2)));
        } else
        {
            dataWatcher.updateObject(16, Byte.valueOf((byte)(byte0 & -3)));
        }
    }
    
    @Override
    public int getMaxSpawnedInChunk()
    {
    	return 4;
    }

    public boolean field_753_a;
    public float field_752_b;
    public float destPos;
    public float field_757_d;
    public float field_756_e;
    public float field_755_h;
	
	public EntityAnimal spawnBabyAnimal(EntityAgeable par1EntityAgeable) {
		
		return new EntityIguana(worldObj);
	}
	
    public EntityAgeable createChild(EntityAgeable par1EntityAgeable)
    {
        return this.spawnBabyAnimal(par1EntityAgeable);
    }

	@Override
	public int getMaxHealth() {
		
		return 9;
	}
}
