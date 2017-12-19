package net.tropicraft.entities;

import net.minecraft.block.material.Material;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.tropicraft.mods.TropicraftMod;

public class EntityStarfish extends EntityWaterMob
{
    public float field_21089_a;
    public float field_21088_b;
    public float field_21087_c;
    public float field_21086_f;
    public float field_21085_g;
    public float field_21084_h;
    public float tentacleAngle;
    public float lastTentacleAngle;
    private float randomMotionSpeed;
    private float field_21080_l;
    private float field_21079_m;
    private float randomMotionVecX;
    private float randomMotionVecY;
    private float randomMotionVecZ;

    public EntityStarfish(World world)
    {
        super(world);
        field_21089_a = 0.0F;
        field_21088_b = 0.0F;
        field_21087_c = 0.0F;
        field_21086_f = 0.0F;
        field_21085_g = 0.0F;
        field_21084_h = 0.0F;
        tentacleAngle = 0.0F;
        lastTentacleAngle = 0.0F;
        randomMotionSpeed = 0.0F;
        field_21080_l = 0.0F;
        field_21079_m = 0.0F;
        randomMotionVecX = 0.0F;
        randomMotionVecY = 0.0F;
        randomMotionVecZ = 0.0F;
        texture = "/tropicalmod/Starfish.png";
        setSize(0.95F, 0.25F);
        field_21080_l = (1.0F / (rand.nextFloat() + 1.0F)) * 0.2F;
    }

    public int getMaxHealth()
    {
        return 10;
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
        return 0.0F;
    }

    public boolean interact(EntityPlayer entityplayer)
    {
        return super.interact(entityplayer);
    }

    public boolean isInWater()
    {
        return worldObj.getBlockMaterial(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ)) == Material.water;
    }

    public void onLivingUpdate()
    {
        super.onLivingUpdate();
                
        if (!worldObj.isRemote)
        {
            if (isInWater()) 
            {
                motionX = randomMotionVecX * randomMotionSpeed;
                motionY = randomMotionVecY * randomMotionSpeed;// * -0.01F;
                motionZ = randomMotionVecZ * randomMotionSpeed;
                this.moveEntity(0.0D, -0.025D, 0.0D);
            } else {
                setDead();
            }
        }
    }

    public void moveEntityWithHeading(float f, float f1)
    {
        moveEntity(motionX, motionY, motionZ);
    }

    protected void updateEntityActionState()
    {
        entityAge++;
        if (entityAge > 100)
        {
            randomMotionVecX = randomMotionVecY = randomMotionVecZ = 0.0F;
        }
        else if (rand.nextInt(50) == 0 || !inWater || randomMotionVecX == 0.0F && randomMotionVecY == 0.0F && randomMotionVecZ == 0.0F)
        {
            float f = rand.nextFloat() * 3.141593F * 2.0F;
            randomMotionVecX = MathHelper.cos(f) * 0.2F;
            randomMotionVecY = 0;
            randomMotionVecZ = MathHelper.sin(f) * 0.2F;
        }
        despawnEntity();
    }

    public boolean getCanSpawnHere()
    {
    	return posY > 45D && posY < 63 && super.getCanSpawnHere();
    }
    
    protected int getDropItemId()
    {
        return TropicraftMod.shellStarfish.shiftedIndex;
    }
}
