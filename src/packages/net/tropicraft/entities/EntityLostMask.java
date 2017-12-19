package net.tropicraft.entities;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.tropicraft.mods.TropicraftMod;

import java.util.List;

public class EntityLostMask extends Entity{
	
	public EntityLostMask(World world)
    {
        super(world);
        umbrellaCurrentDamage = 0;
        umbrellaTimeSinceHit = 0;
        umbrellaRockDirection = 1;
        preventEntitySpawning = true;
        rotatorX = 0;
        rotatorY = 0;
        rotatorZ = 0;   
        direction = 0;  
        setMode(0);
        //wideMasks = new int{2,3};
        switch(getMode()){
	        case 0: setSize(1.0F, .25F); break;  
	        case 1: setSize(.5F, .5F); break;
        }
    }
    
	 public EntityLostMask(World world, double d, double d1, double d2, int beachColor)
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
	        setMode(0);
	    }
	 public EntityLostMask(World world, int i, int j, int k, int l, int maskType)
	    {
	        this(world);
	        
	        xPosition = i;
	        yPosition = j;
	        zPosition = k;
	        setColor(maskType);
	        setMode(1);
	        wallRotation(l);
	        
	    }
   
    protected boolean canTriggerWalking()
    {
        return false;
    }

    public void setColor(int color) {
    	this.dataWatcher.updateObject(16, new Integer(color));
    }
    
    public int getColor() {
    	return this.dataWatcher.getWatchableObjectInt(16);
    }
    
    public void setMode(int mode) {
    	this.dataWatcher.updateObject(17, new Integer(mode));
    }
    
    public int getMode() {
    	return this.dataWatcher.getWatchableObjectInt(17);
    }
    
    
    protected void entityInit()
    {
    	dataWatcher.addObject(16, new Integer(0)); 
    	dataWatcher.addObject(17, new Integer(0));
    }
    
    protected void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
    	
    	nbttagcompound.setShort("Color", (short)getColor()); 
    	nbttagcompound.setShort("Mode", (short)getMode());
    	nbttagcompound.setByte("Dir", (byte)direction);       
        nbttagcompound.setInteger("TileX", xPosition);
        nbttagcompound.setInteger("TileY", yPosition);
        nbttagcompound.setInteger("TileZ", zPosition);
    }
    
    protected void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
    	setColor(nbttagcompound.getShort("Color"));
    	setMode(nbttagcompound.getShort("Mode"));
    	direction = nbttagcompound.getByte("Dir");
    	xPosition = nbttagcompound.getInteger("TileX");
        yPosition = nbttagcompound.getInteger("TileY");
        zPosition = nbttagcompound.getInteger("TileZ");
        if(getMode() == 1){
        	wallRotation(direction);
        }
    }
    public AxisAlignedBB getCollisionBox(Entity entity)
    {
        return entity.boundingBox;
    }

    public AxisAlignedBB getBoundingBox()
    {
        return boundingBox;
    }

    public boolean canBePushed()
    {
    	switch(getMode()){
	    	case 0 : return true;
	    	case 1 : return false;
    	}
        return true;
    }

   public int getDirection(){
	   if(this.rotationYaw == 0){
		   return 0;
	   }
	   if(this.rotationYaw == 90){
		   return 1;
	   }
	   if(this.rotationYaw == 180){
		   return 2;
	   }
	   else{
		   return 270;
	   }
   }

    public double getMountedYOffset()
    {
        return (double)height * 0.0D - 0D;//alter the second value to adjust the sit height
    }

    public boolean attackEntityFrom(DamageSource damagesource, int i)
    {        
    	if(!isDead && !worldObj.isRemote)
        {
    		setDead();
            setBeenAttacked();
            worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX, posY, posZ, 
            		new ItemStack(TropicraftMod.ashenMask, 1, getColor())));
        }
        return true;
    }

   

    public boolean canBeCollidedWith()
    {
        return true;
    }

    public void setPositionAndRotation2(double d, double d1, double d2, float f, 
            float f1, int i)
    {
        umbrellaX = d;
        umbrellaY = d1;
        umbrellaZ = d2;
        umbrellaYaw = f;
        umbrellaPitch = f1;
        field_9394_d = i + 4;
        motionX = velocityX;
        motionY = velocityY;
        motionZ = velocityZ;
    }
    protected boolean canDespawn()
    {
        return false;
    }
    public void setVelocity(double d, double d1, double d2)
    {
        velocityX = motionX = d;
        velocityY = motionY = d1;
        velocityZ = motionZ = d2;
    }
   
    public void onEntityUpdate()
    {    
    }
    public void onUpdate()
    {
        super.onUpdate();       
        if(getMode() == 0){
	        
	        prevPosX = posX;
	        prevPosY = posY;
	        prevPosZ = posZ;
	        int i = 5;
	        double d = 0.0D;
	        for(int j = 0; j < i; j++)
	        {
	            double d5 = (boundingBox.minY + ((boundingBox.maxY - boundingBox.minY) * (double)(j + 0)) / (double)i) - 0.125D;
	            double d9 = (boundingBox.minY + ((boundingBox.maxY - boundingBox.minY) * (double)(j + 1)) / (double)i) - 0.125D;
	            AxisAlignedBB axisalignedbb = AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(boundingBox.minX, d5, boundingBox.minZ, boundingBox.maxX, d9, boundingBox.maxZ);
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
	                double d2 = posX + motionX;
	                double d7 = posY + motionY;
	                double d11 = posZ + motionZ;
	                setPosition(d2, d7, d11);
	                if(onGround)
	                {
	                    motionX *= 0.85D;
	                    motionY *= 0.85D;
	                    motionZ *= 0.85D;
	                }
	                motionX *= .85D;
	                motionY *= .95D;
	                motionZ *= .85D;
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
	            motionX *= 0.75D;
	            motionY *= 0.75D;
	            motionZ *= 0.75D;
	        }
	        moveEntity(motionX, motionY, motionZ);       
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
	                if(entity != riddenByEntity && entity.canBePushed())
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
	                worldObj.setBlockWithNotify(l1, i2, j2, 0);
	            }
	        }
	
	        if(riddenByEntity != null && riddenByEntity.isDead)
	        {
	            riddenByEntity = null;
	        }	        
	        rotator();
        }
        else{
        	if(tickCounter1++ == 100 && !worldObj.isRemote && getMode() == 1)
	        {
	            tickCounter1 = 0;
	            if(!canStay())
	            {
	            	setDead();
	                worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX, posY, posZ, 
	                		new ItemStack(TropicraftMod.ashenMask, 1, getColor())));
	            }
	            
	        }
        }
    }

   

    

    public float getShadowSize()
    {
        return 0.0F;
    }
    public void setMaskHeading(double d, double d1, double d2, float f, 
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
    }
    public void rotator(){
    	if(!this.onGround && !this.inWater && getMode() == 0){    		
    			rotatorX+=10;
        		rotatorY+=20;
        		rotatorZ+=20;   				
    		}
    	
    	else{
    		
    			rotatorX = 270;
    			rotatorY = 0;
    			rotatorZ = 0;
    		}
    	
    }
    public void wallRotation(int i)
    {
    	//System.out.println("color " + color);
    	direction = i;
        prevRotationYaw = rotationYaw = i * 90;
        //System.out.println(i);
//        if(i == 1){
//        	 prevRotationYaw = rotationYaw = 180;
//        }
        float f = 16F;
        float f1 = 32F;
        float f2 = 16F;
        float f8 = 0.0125F;
        for(int a : wideMasks){
        	if(a == getColor()){       		
        		f = 32;
        		f1 = 16;
        		f2 = 32;
        		f8 = 0.52F;
        		break;
        	}
        }
        for(int a : shortMasks){
        	if( a == getColor()){
        		f = 16;
        		f1 = 16;
        		f2 = 16;
        	}
        }
        
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
        float f4 = (float)yPosition - 0.5F;
        float f5 = (float)zPosition + 0.5F;
        float f6 = 0.5125F;
        
        if(i == 0)
        {
            f5 -= f6;
        }
        if(i == 1)
        {
        	f6 = .5125F;
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
            f3 -= f8;
        }
        if(i == 1)
        {
            f5 += f8;
        }
        if(i == 2)
        {
            f3 += f8;
        }
        if(i == 3)
        {
            f5 -= f8;
        }
        f4 += func_411_c(32);
       // System.out.println("X = " + f3 + " Z = " + f5);
        setPosition(f3, f4, f5);
        float f7 = -0.00625F;;
        boundingBox.setBounds(f3 - f - f7, f4 - f1 - f7, f5 - f2 - f7, f3 + f + f7, f4 + f1 + f7, f5 + f2 + f7);
    }
    private float func_411_c(int i)
    {
        if(i == 32)
        {
            return 0.5F;
        }
        return i != 64 ? 0.0F : 0.5F;
    }
    public boolean canStay()
    {
        if(worldObj.getCollidingBoundingBoxes(this, boundingBox).size() > 0)
        {
        	//System.out.println("Collision");
            return false;
        }        
        int k = xPosition;
        int l = yPosition;
        int i1 = zPosition;
        if(direction == 0)
        {
            k = MathHelper.floor_double(posX);
        }
        if(direction == 1)
        {
            i1 = MathHelper.floor_double(posZ);
        }
        if(direction == 2)
        {
            k = MathHelper.floor_double(posX);
        }
        if(direction == 3)
        {
            i1 = MathHelper.floor_double(posZ);
        }
        l = MathHelper.floor_double(posY);
        for(int j1 = 0; j1 < 1; j1++)
        {
            for(int k1 = 0; k1 < 1; k1++)
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
                    return false;
                }
            }

        }

        List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox);
        for(int l1 = 0; l1 < list.size(); l1++)
        {
            if(list.get(l1) instanceof EntityLostMask)
            {
                return false;
            }
        }

        return true;
    }
    
    public static int[] wideMasks = {2,5};
    public static int[] shortMasks = {6,7};
    private int tickCounter1;
    public String name;
    public int direction;
    public int xPosition;
    public int yPosition;
    public int zPosition;
    public int rotatorX;
    public int rotatorY;
    public int rotatorZ;
    public int umbrellaCurrentDamage;
    public int umbrellaTimeSinceHit;
    public int umbrellaRockDirection;
    private int field_9394_d;
    private double umbrellaX;
    private double umbrellaY;
    private double umbrellaZ;
    private double umbrellaYaw;
    private double umbrellaPitch;
    private double velocityX;
    private double velocityY;
    private double velocityZ;

}



