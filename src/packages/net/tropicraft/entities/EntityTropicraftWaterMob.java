package net.tropicraft.entities;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class EntityTropicraftWaterMob extends EntityCreature
{

	public EntityTropicraftWaterMob(World world)
    {
        super(world);
        important1 = 0.0F;       
        randomMotionSpeed = 0.0F;
        important2 = 0.0F;
        important2 = (1.0F / (rand.nextFloat() + 1.0F)) * 0.2F;
        randomMotionVecX = 0.0F;
        randomMotionVecY = 0.0F;
        randomMotionVecZ = 0.0F;        
        reachedTarget = true;
        targetHeight = 62;
        isSurfacing = false;
        surfaceTick = 0;
        hyperness = 30;
        fickleness = 150;
        horFactor = .1F;
        climbFactor = .1F;
        heightRange = new int[]{62, 32};
        
    }

    @Override
    public boolean canBreatheUnderwater()
    {
        return true;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
    }

    public void onCojoLivingUpdate()
    {
    	super.onLivingUpdate();
    }
    
    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        
        if (this.worldObj.isRemote) return;
               
	        important1 += important2;
	        if(prevRotationPitch == 0.0F && prevRotationYaw == 0.0f)
	        {
	            float f = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
	            prevRenderYawOffset = renderYawOffset = (float)((Math.atan2(motionX, motionZ) * 180D) / 3.1415927410125732D);
	            prevRotationPitch = rotationPitch = (float)((Math.atan2(motionY, f) * 180D) / 3.1415927410125732D);
	        }
	        float f3 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
	        for(rotationPitch = (float)((Math.atan2(motionY, f3) * 180D) / 3.1415927410125732D); rotationPitch - prevRotationPitch < -180F; prevRotationPitch -= 360F) { }
	        for(; rotationPitch - prevRotationPitch >= 180F; prevRotationPitch += 360F) { }
	        rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
        
        if(surfaceTick != 0){
        	surfaceTick--;
        }
        if(important1 > 6.283185F)
        {
            important1 -= 6.283185F;
            if(rand.nextInt(10) == 0)
            {
                important2 = (1.0F / (rand.nextFloat() + 1.0F)) * 0.2F;
            }
        }
        
	        if(isInWater())
	        {    
	        	if(!isSurfacing){
	            if(important1 < 3.141593F)
	            {
	                float f = important1 / 3.141593F;
	                if((double)f > 0.75D)
	                {
	                    randomMotionSpeed = 1.0F;
	                } 
	            } else
	            {
	                randomMotionSpeed = randomMotionSpeed * 0.95F;
	            }
	            if(!worldObj.isRemote && targetEntity == null)
	            {
	                motionX = randomMotionVecX * randomMotionSpeed;
	                motionY = randomMotionVecY * randomMotionSpeed;
	                motionZ = randomMotionVecZ * randomMotionSpeed;
	            }
	            if(targetEntity == null){
	            renderYawOffset += ((-(float)Math.atan2(motionX, motionZ) * 180F) / 3.141593F - renderYawOffset) * 0.1F;
	            rotationYaw = renderYawOffset;
	            }
	        	}
	        } else
	        {
	            if(!worldObj.isRemote)
	            {
	                motionX = 0.0D;               
	                motionY *= 0.98000001907348633D;
	                motionZ = 0.0D;
	            }
	            if(surfaceTick == 0){
	            	isSurfacing = false;
	            }
	            if(onGround && deathTime == 0){            	
	            	motionY += .50;
	            	this.attackEntityFrom(DamageSource.drown, 1);
	            	int d = 1;
	            	int e = 1;
	            	if(rand.nextInt(2) == 0){
	            		d = -1;
	            	}
	            	if(rand.nextInt(2) == 0){
	            		e = -1;
	            	}
	            	motionZ = rand.nextFloat()*.20F *d;
	            	motionX = rand.nextFloat()*.20F*e;
	            }
	           if(!inWater){
	        	   motionY -= 0.080000000000000002D;
	           }
	        }
        
    }
    
    @Override
    public void applyEntityCollision(Entity entity){
    	super.applyEntityCollision(entity);
    	if(targetEntity != null && entity.equals(targetEntity)){
    		if(attackStrength() != 0){
	    		targetEntity.attackEntityFrom(DamageSource.causeMobDamage(this), attackStrength());
	    		attackTime = 60;
    		}
    	}
    }
    
    @Override
    public void moveEntityWithHeading(float f, float f1)
    {
        moveEntity(motionX, motionY, motionZ);
    }
    
    protected double getDistanceToBase(int i, int height){    	
    	
    	if(worldObj.getBlockMaterial((int)posX, height - i, (int)posZ) == Material.water){
    		//System.out.println("I = " + i);
    		return getDistanceToBase(i + 1, height);
    	}    	
    	else{    		
    		return i;
    	}
		
    }
    
    protected double getDistanceToSurface(int i){
		if(worldObj.getBlockMaterial((int)posX, (int)posY + i, (int)posZ) == Material.water){
		    		
		    		return getDistanceToSurface(i + 1);
		    	} 
		else{
			return i;
		}
    }
    
    protected boolean getTargetHeight(){
    	if(isWet() && !isSurfacing ){
    		if(posY < 63){
		    	int depth = (int)(getDistanceToBase(0, 62));
		    	if(depth < 1){
		    		targetHeight = (int)posY;    		
		    		return false;
		    	}
		    	else if( depth < 63- heightRange[0]){
		    		targetHeight = 63 - rand.nextInt(depth + 1);
		    		if(targetHeight == 63){
		        		targetHeight--;
		        	}
		    		return false;
		    	}
		    	else{
		    		depth = depth -( 63 - heightRange[0]);		    		
		    		if(depth > heightRange[1]){
		    			depth = heightRange[1];
		    		}
		    		targetHeight = heightRange[0] - rand.nextInt(depth + 1);
		    		return false;
		    	} 
    		}
    		else{
    			//System.out.println("Finding Distance to Surface");
    			int height = (int)getDistanceToSurface(0);
    			height = (int)posY + height;
    			//System.out.println("Height = " + height + "PosY = " + posY);
    			int depth = (int)getDistanceToBase(0, height -1 );
    			//System.out.println("Depth = " + depth);
    			if(depth < 1){
		    		targetHeight = (int)posY;	    		
		    		return false;
		    	}
    			else{
    				int i1 = (int)(rand.nextInt(depth));
    				if(i1 == 0){
    					i1 = 1;
    				}
    				targetHeight =  height - i1;
    				//System.out.println("TargetHeight is " + targetHeight);
    				return false;
    			}
    		}
    	}
    	return true;
    }
    
    public void setAttackHeading(double d, double d1, double d2, float f, 
            float f1)
    {
        float f2 = MathHelper.sqrt_double(d * d + d1 * d1 + d2 * d2);
        d /= f2;
        d1 /= f2;
        d2 /= f2;     
        d *= f;
        d1 *= f;
        d2 *= f;
        motionX = d;
        motionY = d1;
        motionZ = d2;        
    }
    
    protected void attackEntity(Entity entity)
    {
	    	 double d = entity.posX - posX;
	         double d1 = entity.posZ - posZ;          
        	 faceEntity(targetEntity, 360F, 360F);
             double d2 = entity.posY  - posY;
             float f1 = MathHelper.sqrt_double(d * d + d1 * d1) * 0.2F;            
             setAttackHeading(d, d2, d1, horFactor, 12F);
             
                 
    }
    
    protected abstract Entity getTarget();
    protected abstract int attackStrength();
    
    @Override
    protected void updateEntityActionState()
    {
    	++this.entityAge;
    	this.despawnEntity();
    	if(targetEntity != null){
    		if(targetEntity.isDead || !targetEntity.isInWater() || this.getDistanceToEntity(targetEntity) > 10){
	    		targetEntity = null;
	    	}
    		else if(inWater){
    			
	    		if(targetEntity.posY > heightRange[0] || !targetEntity.isInWater()){
	        		targetEntity = null;
	        	}
	    		
	    		if(attackTime == 0 && targetEntity != null){
	    			attackEntity(targetEntity);
	    		}
	    		if(attackTime > 0){
	    			
	    			motionZ = Math.cos((renderYawOffset)/ 57.26)*horFactor;
	    			motionX = Math.sin((renderYawOffset)/ 57.26)*horFactor;
	    			motionY = 0;   			
	    		} 
    		}
    	}
    	if(targetEntity == null){
    		targetEntity = getTarget();
	         if(rand.nextInt(hyperness) == 0 || !inWater || randomMotionVecX == 0.0F && randomMotionVecY == 0.0F && randomMotionVecZ == 0.0F)
	         {
	             float f = rand.nextFloat() * 3.141593F * 2.0F;
	             randomMotionVecX = MathHelper.cos(f) * horFactor;            
	             randomMotionVecZ = MathHelper.sin(f) * horFactor;
	             
	         }
	         
	         if(!isSurfacing && isInWater()){
	        	 if(!reachedTarget){
	        		 targetHeightTick --;
	        	 }
	        	 if(targetHeightTick == 0){
	        		 targetHeightTick = 120;
	        		 reachedTarget = true;
	        	 }
	        	 if(posY <= targetHeight + .15 && posY >= targetHeight - .15 || reachedTarget == true){
	        		 reachedTarget = true;
	        		 targetHeightTick = 120;
	        		 randomMotionVecY = 0;
	        		 if(rand.nextInt(fickleness) == 0){        		 
	                	 reachedTarget = getTargetHeight();
	                	 
	        		 }
	        	 }
	        	 else if(posY > targetHeight && !reachedTarget){	        	 
		     		randomMotionVecY = -climbFactor;
		     	}
		         else if(posY < targetHeight && !reachedTarget){
		        	 randomMotionVecY  = climbFactor;
		         }	         
	         }
    	}
    	
    }
    public Entity targetEntity;
    protected float climbFactor;
    protected float horFactor;
    protected int hyperness;
    protected int fickleness;
    protected int[] heightRange;
    public boolean isSurfacing;
    public boolean reachedTarget;    
    public float important1;
    public float randomMotionSpeed;
    public float important2;
    protected float randomMotionVecX;
    protected float randomMotionVecY;
    protected float randomMotionVecZ;
    public int targetHeight;    
    public int surfaceTick;
    public int targetHeightTick = 120;
    @Override
    public boolean getCanSpawnHere()
    {
        return worldObj.checkIfAABBIsClear(boundingBox);
    }

    @Override
    public int getTalkInterval()
    {
        return 120;
    }
    
    @Override
    protected boolean canDespawn()
    {
        return false;
    }

    @Override
    protected int getExperiencePoints(EntityPlayer entityplayer)
    {
        return 1 + worldObj.rand.nextInt(3);
    }
}
