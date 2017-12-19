package tropicraft.entities.projectiles;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityCoconutGrenade extends Entity
{

    public EntityCoconutGrenade(World world)
    {
        super(world);
        xTileCG = -1;
        yTileCG = -1;
        zTileCG = -1;
        inTileCG = 0;
        inGroundCG = false;
        shakeCG = 0;
        ticksInAirCG = 0;
        setSize(0.25F, 0.25F);
    }

    protected void entityInit()
    {
    }

    public boolean isInRangeToRenderDist(double d)
    {
        double d1 = boundingBox.getAverageEdgeLength() * 4D;
        d1 *= 64D;
        return d < d1 * d1;
    }

    public EntityCoconutGrenade(World world, EntityLivingBase entityliving)
    {
        super(world);
        xTileCG = -1;
        yTileCG = -1;
        zTileCG = -1;
        inTileCG = 0;
        inGroundCG = false;
        shakeCG = 0;
        ticksInAirCG = 0;
        shootingEntity = entityliving;
        setSize(0.25F, 0.25F);
        setLocationAndAngles(entityliving.posX, entityliving.posY + (double)entityliving.getEyeHeight(), entityliving.posZ, entityliving.rotationYaw, entityliving.rotationPitch);
        posX -= MathHelper.cos((rotationYaw / 180F) * 3.141593F) * 0.16F;
        posY -= 0.10000000149011612D;
        posZ -= MathHelper.sin((rotationYaw / 180F) * 3.141593F) * 0.16F;
        setPosition(posX, posY, posZ);
        yOffset = 0.0F;
        float f = 0.4F;
        motionX = -MathHelper.sin((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F) * f;
        motionZ = MathHelper.cos((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F) * f;
        motionY = -MathHelper.sin((rotationPitch / 180F) * 3.141593F) * f;
        setSnowballHeading(motionX, motionY, motionZ, 1.5F, 1.0F);
    }

    public EntityCoconutGrenade(World world, double d, double d1, double d2)
    {
        super(world);
        xTileCG = -1;
        yTileCG = -1;
        zTileCG = -1;
        inTileCG = 0;
        inGroundCG = false;
        shakeCG = 0;
        ticksInAirCG = 0;
        ticksInGroundCG = 0;
        setSize(0.25F, 0.25F);
        setPosition(d, d1, d2);
        yOffset = 0.0F;
    }

    public void setSnowballHeading(double d, double d1, double d2, float f, 
            float f1)
    {
        float f2 = MathHelper.sqrt_double(d * d + d1 * d1 + d2 * d2);
        d /= f2;
        d1 /= f2;
        d2 /= f2;
        d += rand.nextGaussian() * 0.0074999998323619366D * (double)f1;
        d1 += rand.nextGaussian() * 0.0074999998323619366D * (double)f1;
        d2 += rand.nextGaussian() * 0.0074999998323619366D * (double)f1;
        d *= f;
        d1 *= f;
        d2 *= f;
        motionX = d;
        motionY = d1;
        motionZ = d2;
        float f3 = MathHelper.sqrt_double(d * d + d2 * d2);
        prevRotationYaw = rotationYaw = (float)((Math.atan2(d, d2) * 180D) / 3.1415927410125732D);
        prevRotationPitch = rotationPitch = (float)((Math.atan2(d1, f3) * 180D) / 3.1415927410125732D);
        ticksInGroundCG = 0;
    }

    public void setVelocity(double d, double d1, double d2)
    {
        motionX = d;
        motionY = d1;
        motionZ = d2;
        if(prevRotationPitch == 0.0F && prevRotationYaw == 0.0F)
        {
            float f = MathHelper.sqrt_double(d * d + d2 * d2);
            prevRotationYaw = rotationYaw = (float)((Math.atan2(d, d2) * 180D) / 3.1415927410125732D);
            prevRotationPitch = rotationPitch = (float)((Math.atan2(d1, f) * 180D) / 3.1415927410125732D);
        }
    }

    public void onUpdate()
    {
        lastTickPosX = posX;
        lastTickPosY = posY;
        lastTickPosZ = posZ;
        super.onUpdate();
        if(shakeCG > 0)
        {
            shakeCG--;
        }
        if(inGroundCG)
        {
            int i = worldObj.getBlockId(xTileCG, yTileCG, zTileCG);
            if(i != inTileCG)
            {
                inGroundCG = false;
                motionX *= rand.nextFloat() * 0.152F / 3;
                motionY *= rand.nextFloat() * 0.212F;
                motionZ *= rand.nextFloat() * 0.152F / 3;
                ticksInGroundCG = 0;
                ticksInAirCG = 0;
            } else
            {
            	if (!this.worldObj.isRemote) worldObj.createExplosion(this, posX, posY, posZ, 2.4F, true);
                ticksInGroundCG++;
                setDead();
             //   if(ticksInGroundSnowball == 1200)
           //     {
                   
         //       }
                return;
            }
        } else
        {
            ticksInAirCG++;
        }
        Vec3 vec3d = Vec3.createVectorHelper(posX, posY, posZ);
        Vec3 vec3d1 = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ);
        MovingObjectPosition movingobjectposition = worldObj.clip(vec3d, vec3d1);
        vec3d = Vec3.createVectorHelper(posX, posY, posZ);
        vec3d1 = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ);
        if(movingobjectposition != null)
        {
            vec3d1 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
        }
        if(!worldObj.isRemote)
        {
            Entity entity = null;
            List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
            double d = 0.0D;
            for(int l = 0; l < list.size(); l++)
            {
                Entity entity1 = (Entity)list.get(l);
                if(!entity1.canBeCollidedWith() || entity1 == shootingEntity && ticksInAirCG < 5)
                {
                    continue;
                }
                float f4 = 0.3F;
                AxisAlignedBB axisalignedbb = entity1.boundingBox.expand(f4, f4, f4);
                MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(vec3d, vec3d1);
                if(movingobjectposition1 == null)
                {
                    continue;
                }
                double d1 = vec3d.distanceTo(movingobjectposition1.hitVec);
                if(d1 < d || d == 0.0D)
                {
                    entity = entity1;
                    d = d1;
                }
            }

            if(entity != null)
            {
                movingobjectposition = new MovingObjectPosition(entity);
            }
        }
        if(movingobjectposition != null)
        {
            if(movingobjectposition.entityHit != null)
            {
        //        if(!movingobjectposition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, shootingEntity), 0));
            	//if (!this.worldObj.isRemote) worldObj.createExplosion(this, posX, posY, posZ, 2.4F);
            }
            for(int j = 0; j < 8; j++)
            {
                worldObj.spawnParticle("snowballpoof", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
            }
            if (!this.worldObj.isRemote) worldObj.createExplosion(this, posX, posY, posZ, 2.4F, true);
            setDead();
        }
        posX += motionX;
        posY += motionY;
        posZ += motionZ;
        float f = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
        rotationYaw = (float)((Math.atan2(motionX, motionZ) * 180D) / 3.1415927410125732D);
        for(rotationPitch = (float)((Math.atan2(motionY, f) * 180D) / 3.1415927410125732D); rotationPitch - prevRotationPitch < -180F; prevRotationPitch -= 360F) { }
        for(; rotationPitch - prevRotationPitch >= 180F; prevRotationPitch += 360F) { }
        for(; rotationYaw - prevRotationYaw < -180F; prevRotationYaw -= 360F) { }
        for(; rotationYaw - prevRotationYaw >= 180F; prevRotationYaw += 360F) { }
        rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
        rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
        float f1 = 0.99F;
        float f2 = 0.03F;
        if(isInWater())
        {
            for(int k = 0; k < 4; k++)
            {
                float f3 = 0.25F;
                worldObj.spawnParticle("bubble", posX - motionX * (double)f3, posY - motionY * (double)f3, posZ - motionZ * (double)f3, motionX, motionY, motionZ);
            }
            if (!this.worldObj.isRemote) worldObj.createExplosion(this, posX, posY, posZ, 2.4F, true);
            f1 = 0.8F;
        }
        motionX *= f1;
        motionY *= f1;
        motionZ *= f1;
        motionY -= f2;
        setPosition(posX, posY, posZ);
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setShort("xTile", (short)xTileCG);
        nbttagcompound.setShort("yTile", (short)yTileCG);
        nbttagcompound.setShort("zTile", (short)zTileCG);
        nbttagcompound.setByte("inTile", (byte)inTileCG);
        nbttagcompound.setByte("shake", (byte)shakeCG);
        nbttagcompound.setByte("inGround", (byte)(inGroundCG ? 1 : 0));
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        xTileCG = nbttagcompound.getShort("xTile");
        yTileCG = nbttagcompound.getShort("yTile");
        zTileCG = nbttagcompound.getShort("zTile");
        inTileCG = nbttagcompound.getByte("inTile") & 0xff;
        shakeCG = nbttagcompound.getByte("shake") & 0xff;
        inGroundCG = nbttagcompound.getByte("inGround") == 1;
    }

    public void onCollideWithPlayer(EntityPlayer entityplayer)
    {
        if(inGroundCG && shootingEntity == entityplayer && shakeCG <= 0 && entityplayer.inventory.addItemStackToInventory(new ItemStack(Item.arrow, 1)))
        {
            worldObj.playSoundAtEntity(this, "random.pop", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
    //        worldObj.createExplosion(this, posX, posY, posZ, 3F);
            entityplayer.onItemPickup(this, 1);
            setDead();
        }
    }

    @SideOnly(Side.CLIENT)
    public float getShadowSize()
    {
        return 0.0F;
    }

    private int xTileCG;
    private int yTileCG;
    private int zTileCG;
    private int inTileCG;
    private boolean inGroundCG;
    public int shakeCG;
    private EntityLivingBase shootingEntity;
    private int ticksInGroundCG;
    private int ticksInAirCG;
}
