package tropicraft.entities;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.server.FMLServerHandler;

import tropicraft.blocks.TropicraftBlocks;
import tropicraft.entities.hostile.land.tribes.koa.v3.EntityKoaBase;
import tropicraft.fishing.EntityHook;
import tropicraft.items.TropicraftItems;

import CoroAI.entity.EntityTropicalFishHook;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class EntityTropicraftWaterMob extends EntityWaterMob
{
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
	
	public boolean isCatchable;
	public int outOfWaterTick = 0;
	public double fishingMaxLookDist = 10D;
	public int fishingImmediateDispatchOdds = 10;
	public int fishingInterestOdds = 10;
	public int fishingBreakLineOdds = 500;
	public float fishingApproachSpeed = 1.4f;
	public float fishingEscapeSpeed = 2.4f;
	public static boolean fishingDebug = false;
	public List<String> fishingLog = new ArrayList<String>();
	
	public void fishDebug(String s){
		try{
		if(fishingDebug && !worldObj.isRemote){
			String out = "<"+this.getEntityString().split("\\.")[1]+this.entityId+">: "+s;
			if(fishingLog.contains(out)){
				//System.out.println(out);
				return;
			}
			net.minecraft.server.MinecraftServer.getServer().getConfigurationManager().sendChatMsg(out);
			fishingLog.add(out);
		}
		}catch(Exception e){
			
		}
	}
	
	
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
		isCatchable = false;
		this.fishingInterestOdds = 1000;
		this.fishingImmediateDispatchOdds = 5;
		this.renderYawOffset = this.rotationYaw;
		this.rotationYaw = 0;
		this.rotationPitch = 0;

	}
	
	
	
	@Override
	protected String getHurtSound()
	{
		// TODO Auto-generated method stub
		return "random.splash";
	}


	@Override
	protected String getDeathSound()
	{
		// TODO Auto-generated method stub
		return super.getDeathSound();
	}


	@Override
	public void entityInit(){
		this.dataWatcher.addObject(26, Integer.valueOf(-1));
		super.entityInit();
	}
	
	
	public int getHookID(){
		return this.dataWatcher.getWatchableObjectInt(26);
	}
	
	public void setHookID(int i){
		this.dataWatcher.updateObject(26, Integer.valueOf(i));
	}
	
	public boolean fishingIsInterested = false;
	
	public void onFishingUpdate(){
		// If we're hooked, check the validity of it
		if(this.getHookID() != -1){
			List<Entity> l = worldObj.loadedEntityList;
			for(Entity e : l){
				if(e.entityId == getHookID()){
					if(e.isDead){
						// invalid, so set un-hooked
						//System.out.println("Gross");
						setHookID(-1);
					}
					if(rand.nextInt(this.fishingBreakLineOdds) == 0){
						if(!worldObj.isRemote){
							//fishDebug("I managed to get your hook, see ya ;D");
							//e.setDead();
						}
					}
				}
			}
		}
		
		List<Entity> l = worldObj.loadedEntityList;
		for(Entity entity : l){
			
			// we're up against the player's fishing rod
			if(entity instanceof EntityHook && !worldObj.isRemote){
				EntityHook hook = (EntityHook)entity;
				if(hook.isDead){ 
					continue;
				}
				 if(hook.bobber == null){
					// this.isSurfacing = true;
					 // fish collision with bobber
					if(hook.boundingBox.expand(1f, 1f, 1f).intersectsWith(this.boundingBox) /*&& rand.nextInt(this.fishingImmediateDispatchOdds) == 0*/){
						hook.bobber = this;
						fishDebug("I'm hooked!");
						// hook fish to the bobber, also set the hook id for reference
						this.posX = hook.posX;
						this.posY = hook.posY;
						this.posZ = hook.posZ;
						this.setHookID(hook.entityId);
						continue;
					}else{
						if(this.getDistanceToEntity(hook) < fishingMaxLookDist && this.canEntityBeSeen(hook) && rand.nextInt(this.fishingInterestOdds) == 0)
						{
							if(!isInWater()){
								fishingIsInterested = false;
								return;
							}
							fishingIsInterested = true;
							fishDebug("Okay player, I want your hook!");
							this.reachedTarget = false;
							this.faceEntity(hook, 100f, 100f);
							motionX = -((fishingApproachSpeed / 10) * Math.sin(Math.toRadians(rotationYaw)));
							motionZ = ((fishingApproachSpeed / 10) * Math.cos(Math.toRadians(rotationYaw)));
						//	if(this.getDistanceToEntity(hook) < 4D)
							motionY = (hook.posY > this.posY + this.height ? 0.2f : -0.2F);
							//this.isSurfacing = true;
						}else{
							fishingIsInterested = false;
						}
					}
				 }
			}
			
			// we're up against a koa's fishing rod
			if(entity instanceof EntityTropicalFishHook && !worldObj.isRemote){
				EntityTropicalFishHook hook = (EntityTropicalFishHook)entity;
				if(hook.isDead){ 
					continue;
				}
				 if(hook.bobber == null){
					 // fish collision with bobber
					 if(rand.nextInt(this.fishingImmediateDispatchOdds) == 0){
						 fishDebug("I escaped the Koa's clutches!");
						 return;
					 }
					if(hook.boundingBox.expand(2d, 2d, 2d).intersectsWith(this.boundingBox)){
						hook.bobber = this;
						// hook fish to the bobber, also set the hook id for reference
						fishDebug("Hooked by a Koa!");
						this.setHookID(hook.entityId);
						continue;
					}else{
						if(this.getDistanceToEntity(hook) < fishingMaxLookDist && this.canEntityBeSeen(hook) && rand.nextInt(this.fishingInterestOdds) == 0)
						{
							if(!isInWater()){
								return;
							}
							fishDebug("I see a Koa hook worth going for!");
							this.reachedTarget = false;
							this.faceEntity(hook, 100f, 100f);
							motionX = -((fishingApproachSpeed / 10) * Math.sin(Math.toRadians(rotationYaw)));
							motionZ = ((fishingApproachSpeed / 10) * Math.cos(Math.toRadians(rotationYaw)));
							motionY = hook.posY > this.posY + this.height ? 0.2f : -0.2F;
							this.isSurfacing = true;
						}
					}
				 }
			}
		}
		
		// Resist once grabbed
		if(this.getHookID() != -1){
			l = worldObj.loadedEntityList;
			for(Entity e : l){
				if(e instanceof EntityHook){
					EntityHook hook = (EntityHook) e;
					if(e.entityId == getHookID() && hook.bobber != this){
						fishDebug("Taking this player's hook for a swim \\o/!");
						if(this.isInWater()){
							//motionX += -((fishingEscapeSpeed) * Math.sin(Math.toRadians(rotationYaw)));
							//motionZ += ((fishingEscapeSpeed) * Math.cos(Math.toRadians(rotationYaw)));
						}
						double y = hook.getVecToPlayer().yCoord;
						if(y > 0){
						//	hook.angler.faceEntity(this, 1f, 1f);
						}
						
						//if(!this.isCollidedVertically)
						//motionY = -0.05F;
					}
				}
				if(e instanceof EntityTropicalFishHook){
					EntityTropicalFishHook hook = (EntityTropicalFishHook) e;
					if(e.entityId == getHookID() && hook.bobber != null && hook.angler != null){
						this.faceEntity(hook.angler, 100f, 100f);
						hook.angler.faceEntity(this, 100f, 100f);
						fishDebug("D: KOA'S ARE GONNA KILL AND EAT ME!!!");
						motionX = -((fishingEscapeSpeed / 10) * Math.sin(Math.toRadians(rotationYaw)));
						motionZ = ((fishingEscapeSpeed / 10) * Math.cos(Math.toRadians(rotationYaw)));
						motionY = (hook.angler.posY+(hook.angler.height/2)) > this.posY + this.height ? 0.2f : -0.2F;
						hook.posX = this.posX;
						hook.posY = this.posY;
						hook.posZ = this.posZ;
						//hook.motionY = 0;
						if(this.getDistanceToEntity(hook.angler) < 3D){
							fishDebug("This is it for me, farewell...");
							hook.angler.swingItem();
							hook.angler.attackEntityAsMob(this);
						}
					}
				}
			}
		}
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
	public int getMaxHealth()
	{
		return 0;
	}

	@Override
	protected void playStepSound(int par1, int par2, int par3, int par4)
	{

	}

	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		
		
		if(this.isInWater()){
			this.outOfWaterTick = 0;
		}else{
			this.outOfWaterTick++;
		}
		
		if(worldObj.isRemote){
			return;
		}
		
		//targetHeight = this.worldObj.getHeightValue((int)this.posX, (int)this.posZ);

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
		//	motionY = 0;
			if(!isSurfacing)
			{
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
				if(!worldObj.isRemote && targetEntity == null && getHookID() == -1 && !fishingIsInterested)
				{
					//randomMotionSpeed = randomMotionSpeed;
					motionX = (randomMotionVecX * randomMotionSpeed);
					motionZ = (randomMotionVecZ * randomMotionSpeed);
				//	if(!isSurfacing){
					motionY = (randomMotionVecY * randomMotionSpeed);
				//	}
				//	else{
					//	motionY += 0.02f;
				//	}
				}
				if(targetEntity == null)
				{
					renderYawOffset += ((-(float)Math.atan2(motionX, motionZ) * 180F) / 3.141593F - renderYawOffset) * 0.1F;
					rotationYaw = renderYawOffset;
				}
			}
		} else
		{
			if(!worldObj.isRemote)
			{
				if(this.getHookID() == -1){
					motionX = 0;   
					motionZ = 0;
					motionY *= 0.98000001907348633D;
				}
				
			}
			if(surfaceTick == 0 || outOfWaterTick > 10){
				isSurfacing = false;
			}
			if(onGround && deathTime == 0 && outOfWaterTick > 60){            	
				motionY = .50;
				if(this.outOfWaterTick % 60 == 0){
				this.attackEntityFrom(DamageSource.drown, 1);
				}
				int d = 1;
				int e = 1;
				if(rand.nextInt(2) == 0){
					d = -1;
				}
				if(rand.nextInt(2) == 0){
					e = -1;
				}
				if(getHookID() == -1){
					motionZ += rand.nextFloat()*.20F *d;
					motionX += rand.nextFloat()*.20F*e;
				}
			}
			if(!isInWater() && this.outOfWaterTick > 10){
				motionY -= 0.4D;
				this.reachedTarget = true;
			}
		}
		try{
			if(this.isCatchable){
				this.onFishingUpdate();
			}
		}catch(ConcurrentModificationException e){
			// Yes java, i am well aware!
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
	public boolean attackEntityFrom(DamageSource s, int i){
		if(!worldObj.isRemote)
		motionY -= 0.2f;
		//System.out.println(s.damageType);
		return super.attackEntityFrom(s, i);
	}

	@Override
	public void moveEntityWithHeading(float f, float f1)
	{
		//super.moveEntityWithHeading(f, f1);
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
		if(worldObj.getBlockMaterial((int)Math.floor(posX), (int)posY + i, (int)Math.floor(posZ)) == Material.water){

			return getDistanceToSurface(i + 1);
		} 
		else{
			return i;
		}
	}
	
	@Override
	public boolean getCanSpawnHere(){
		
		
		return super.getCanSpawnHere();
	}

	protected boolean getTargetHeight(){
		if(isWet() && !isSurfacing){
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
	
	public void faceEntity(Entity par1Entity, float par2, float par3)
    {
        double d0 = par1Entity.posX - this.posX;
        double d1 = par1Entity.posZ - this.posZ;
        double d2;

        if (par1Entity instanceof EntityLiving)
        {
            EntityLiving entityliving = (EntityLiving)par1Entity;
            d2 = entityliving.posY + (double)entityliving.getEyeHeight() - (this.posY + (double)this.getEyeHeight());
        }
        else
        {
            d2 = (par1Entity.boundingBox.minY + par1Entity.boundingBox.maxY) / 2.0D - (this.posY + (double)this.getEyeHeight());
        }

        double d3 = (double)MathHelper.sqrt_double(d0 * d0 + d1 * d1);
        float f2 = (float)(Math.atan2(d1, d0) * 180.0D / Math.PI) - 90.0F;
        float f3 = (float)(-(Math.atan2(d2, d3) * 180.0D / Math.PI));
        this.rotationPitch = this.updateRotation(this.rotationPitch, f3, par3);
        this.renderYawOffset = this.updateRotation(this.renderYawOffset, f2, par2);
        this.rotationYaw = this.renderYawOffset;
    }
    
    private float updateRotation(float par1, float par2, float par3)
    {
        float f3 = MathHelper.wrapAngleTo180_float(par2 - par1);

        if (f3 > par3)
        {
            f3 = par3;
        }

        if (f3 < -par3)
        {
            f3 = -par3;
        }

        return par1 + f3;
    }

	@Override
	protected void updateEntityActionState()
	{
		++this.entityAge;
		if(this.entityAge % 60 == 0){
		//	this.entityAge-= 10;
		}
		//this.despawnEntity();
		if(targetEntity != null){
			if(targetEntity.isDead || !targetEntity.isInWater() || this.getDistanceToEntity(targetEntity) > 10){
				targetEntity = null;
			}
			else if(isInWater()){

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
		if(targetEntity == null && !worldObj.isRemote){
			targetEntity = getTarget();
			if(rand.nextInt(hyperness) == 0 || !isInWater() || randomMotionVecX == 0.0F && randomMotionVecY == 0.0F && randomMotionVecZ == 0.0F)
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
