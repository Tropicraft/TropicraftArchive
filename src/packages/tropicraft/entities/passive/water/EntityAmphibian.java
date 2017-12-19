package tropicraft.entities.passive.water;

import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeInstance;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import tropicraft.ModInfo;
import tropicraft.items.TropicraftItems;

public class EntityAmphibian extends EntityCreature {

	public boolean desireToReturn;
	public boolean returnToLand;
	public boolean reachedTarget;
	public float important1;
	protected float randomMotionSpeed;
	protected float important2;
	protected float randomMotionVecX;
	protected float randomMotionVecY;
	protected float randomMotionVecZ;
	public int targetHeight;
	protected int growthRate;
	protected int fickleness;
	protected float landSpeed;
	
	//new 1.6.2 stuff
	public static final UUID uuid = UUID.randomUUID();
	//public static AttributeModifier speedBoostFlee = (new AttributeModifier(uuid, "Speed boost flee", 0.45D, 0)).setSaved(false);
	public static AttributeModifier speedBoostReturnToLand = (new AttributeModifier(uuid, "Speed boost return to land", 0.25D, 0)).setSaved(false);
	public float moveSpeed;

	public EntityAmphibian(World world) {
		super(world);
		//texture = ModInfo.TEXTURE_ENTITY_LOC + "turtle2.png";
		important1 = 0.0F;
		randomMotionSpeed = 0.0F;
		important2 = 0.0F;
		randomMotionVecX = 0.0F;
		randomMotionVecY = 0.0F;
		randomMotionVecZ = 0.0F;
		important2 = (1.0F / (rand.nextFloat() + 1.0F)) * 0.2F;
		reachedTarget = false;
		returnToLand = false;
		desireToReturn = true;
		targetHeight = 61;
		setAmphibianAge(1.0f);
		growthRate = 12000;
		fickleness = 1200;
		landSpeed = .25F;

	}
	public EntityAmphibian(World world, float placeHolder){
		super(world);
		//texture = "/tropicalmod/turtle2.png";
		important1 = 0.0F;
		randomMotionSpeed = 0.0F;
		important2 = 0.0F;
		randomMotionVecX = 0.0F;
		randomMotionVecY = 0.0F;
		randomMotionVecZ = 0.0F;
		important2 = (1.0F / (rand.nextFloat() + 1.0F)) * 0.2F;
		reachedTarget = false;
		returnToLand = false;
		desireToReturn = true;
		targetHeight = 61;
		growthRate = 12000;
		fickleness = 1200;
		setAmphibianAge(placeHolder);
		landSpeed = .25F;
		setMoveSpeed(landSpeed);
	}
	
	
	
	public void setSpeedReturnToLandAdditive(float speed) {
		//fleeSpeed = speed;
		speedBoostReturnToLand = (new AttributeModifier(uuid, "Speed boost return to land", speed, 0)).setSaved(false);
	}
	
	public void setSpeedNormalBase(float var) {
		moveSpeed = var;
	}
	
	public void applyEntityAttributesMine() {
		//baseline movespeed
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(moveSpeed);
	}
	
	public void attrRemoveSpeeds() {
		AttributeInstance attributeinstance = getEntityAttribute(SharedMonsterAttributes.movementSpeed);
        attributeinstance.removeModifier(speedBoostReturnToLand);
	}

	public void attrSetReturnToLand() {
		attrRemoveSpeeds();
		AttributeInstance attributeinstance = getEntityAttribute(SharedMonsterAttributes.movementSpeed);
		attributeinstance.applyModifier(speedBoostReturnToLand);
	}
	
	public void attrSetSpeedNormal() {
		attrRemoveSpeeds();
	}

	public void setAmphibianAge(float age) {
		this.dataWatcher.updateObject(16, new Integer((int)(age*10000)));

	}

	public float getAmphibianAge() {
		return this.dataWatcher.getWatchableObjectInt(16)/10000f;
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		setSpeedReturnToLandAdditive(0.5F);
		setSpeedNormalBase(1F);
		applyEntityAttributesMine();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(10.0D);
	}
	
	@Override
	protected boolean isAIEnabled()
	{
		return false;
	}

	protected double getDistanceToBase(int i){
		if(i == 5){
			return i;
		}
		if(worldObj.getBlockId((int)posX, 62 - i, (int)posZ) == Block.waterStill.blockID){

			return getDistanceToBase(i + 1);
		}
		else{
			return i;
		}

	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(16, new Integer(10000));
		dataWatcher.addObject(23, Integer.valueOf(0));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeEntityToNBT(nbttagcompound);
		nbttagcompound.setFloat("Age", getAmphibianAge());

	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readEntityFromNBT(nbttagcompound);
		setAmphibianAge(nbttagcompound.getFloat("Age"));

	}
	@Override
	public void onDeath(DamageSource par1DamageSource){
		super.onDeath(par1DamageSource);
		this.entityDropItem(new ItemStack(TropicraftItems.shells.itemID, 1, 5), 1);
	}
	protected void getTargetHeight(){
		if(isWet()){
			int range = (int)(getDistanceToBase(0));
			if(range >1){
				targetHeight = (int)posY;
			}
			targetHeight =  63 - rand.nextInt(range + 1);
			if(targetHeight == 63){
				targetHeight--;
			}
			reachedTarget = false;
		}
	}

	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		if(ticksExisted % growthRate == 0){
			setAmphibianAge(getAmphibianAge() + .05f);
		}

		if(rand.nextInt(fickleness / 2) == 0 && returnToLand){
			returnToLand = false;
		}
		if(isInWater() && returnToLand){
			attrSetReturnToLand();
			//moveSpeed = 1.5F;
		}else                            //--Cojo - added 'else' here
		if(!isInWater()){
			attrSetSpeedNormal();
			//moveSpeed = landSpeed;
		}else                            //--Cojo - added 'else' here
		if(isInWater() && !returnToLand)
		{
			if(rand.nextInt(fickleness) == 0){
				returnToLand = true;
			}
			important1 += important2;
			if(prevRotationPitch == 0.0F && prevRotationYaw == 0.0F)
			{
				float f = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
				prevRenderYawOffset = renderYawOffset = (float)((Math.atan2(motionX, motionZ) * 180D) / 3.1415927410125732D);
				prevRotationPitch = rotationPitch = (float)((Math.atan2(motionY, f) * 180D) / 3.1415927410125732D);
			}
			float f3 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
			for(rotationPitch = (float)((Math.atan2(motionY, f3) * 180D) / 3.1415927410125732D); rotationPitch - prevRotationPitch < -180F; prevRotationPitch -= 360F) { }
			for(; rotationPitch - prevRotationPitch >= 180F; prevRotationPitch += 360F) { }
			rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;

			if(important1 > 6.283185F)
			{
				important1 -= 6.283185F;
				if(rand.nextInt(10) == 0)
				{
					important2 = (1.0F / (rand.nextFloat() + 1.0F)) * 0.2F;
				}
			}
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
			if(!worldObj.isRemote)
			{
				motionX = randomMotionVecX * randomMotionSpeed;
				motionY = randomMotionVecY * randomMotionSpeed;
				motionZ = randomMotionVecZ * randomMotionSpeed;
			}
			float f1 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
			renderYawOffset += ((-(float)Math.atan2(motionX, motionZ) * 180F) / 3.141593F - renderYawOffset) * 0.1F;
			rotationYaw = renderYawOffset;
		}
	}

	@Override
	public float getBlockPathWeight(int i, int j, int k)
	{
		int block = worldObj.getBlockId(i,j -1,k);
		if(returnToLand){
			switch(block){
			case 2: return 10F;
			case 12: return 20F;
			}
		}
		else if(block == Block.waterStill.blockID){
			return 20F;
		}
		return 0.0F;
	}

	@Override
	protected void updateEntityActionState()
	{
		if(returnToLand || (!returnToLand && !isInWater())){
			super.updateEntityActionState();
		}
		if(!returnToLand){
			if(rand.nextInt(70) == 0 || !inWater || randomMotionVecX == 0.0F && randomMotionVecY == 0.0F && randomMotionVecZ == 0.0F)
			{
				float f = rand.nextFloat() * 3.141593F * 2.0F;
				randomMotionVecX = MathHelper.cos(f) * 0.15F;
				randomMotionVecZ = MathHelper.sin(f) * 0.15F;

			}
			if(isInWater()){
				if(posY <= targetHeight + .15 && posY >= targetHeight - .15 || reachedTarget == true){
					reachedTarget = true;
					randomMotionVecY = 0;
					if(rand.nextInt(300) == 0){
						getTargetHeight();

					}
				}
				else if(posY > targetHeight && !reachedTarget){
					randomMotionVecY = -.15F;
				}
				else if(posY < targetHeight && !reachedTarget){
					randomMotionVecY  = .15F;
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
	public boolean getCanSpawnHere() {
		return !worldObj.checkBlockCollision(boundingBox);
	}

	@Override
	public int getTalkInterval()
	{
		return 120;
	}

	@Override
	protected boolean canDespawn()
	{
		return true;
	}

	@Override
	protected int getExperiencePoints(EntityPlayer entityplayer)
	{
		return 1 + worldObj.rand.nextInt(3);
	}
	
	public float getMoveSpeed() {
		return (float) this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
	}
	
	public void setMoveSpeed(float var) {
		//moveSpeed = var;
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(var);
		if (!worldObj.isRemote) {
			this.dataWatcher.updateObject(23, Integer.valueOf((int)(var * 1000)));
		}
	}

}