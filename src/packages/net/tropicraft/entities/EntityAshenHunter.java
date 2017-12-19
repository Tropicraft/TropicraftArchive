package net.tropicraft.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class EntityAshenHunter extends EntityAshen {			
	public Entity target;
	boolean hasGTFO;
	int lostSight;
	public EntityAshenHunter(World world) {
		super(world);
		setActionState(2);
		actionPicker = 2;
		hasGTFO = false;
		lostSight = 0;

	}
	@Override
	protected void entityInit()
	{		 
		super.entityInit();  

	}
	public int getAttackStrength(){
		switch(worldObj.difficultySetting){
		case 1: return 1;
		case 2: return 2;
		case 3: return 3;
		default: return 0;
		}

	}
	@Override
	public void onLivingUpdate()
	{ 
		super.onLivingUpdate();

		if((worldObj.difficultySetting == 0) && getActionState() != 1){// || worldObj.getWorldInfo().getGameType() == EnumGameType.CREATIVE){// || ModLoader.getMinecraftInstance().playerController.isInCreativeMode()
			setActionState(0);
		}
		else if (getActionState() != 1){
			setActionState(actionPicker);
		}

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
	@Override
	protected String getLivingSound()
	{
		if(entityToAttack != null){
			return "ashenLaugh";
		}
		return null;
	}
	@Override
	protected void updateEntityActionState(){ 
		super.updateEntityActionState();

		attackHandler();  	
		if(target != null){
			if(target.isDead){			
				target = null;
				moveSpeed = .75F;
			}

			else if(!canEntityBeSeen(target)){
				lostSight++;
				if(lostSight == 200){
					lostSight = 0;
					target = null;
					entityToAttack = null;
					moveSpeed = .75F;
				}
			}
			else{
				lostSight = 0;
			}
		}
		if(entityToAttack != null){
			//System.out.println("EntityToAttack = " + entityToAttack);
			if(entityToAttack instanceof EntityPlayer) {
				if(((EntityPlayer)entityToAttack).capabilities.isCreativeMode) {
					setActionState(0);
				}
			}
		}				
	}
	public void attackHandler(){
		if(getActionState() == 2 && worldObj.difficultySetting != 0){
			if(entityToAttack == null && target == null){
				target = findStuffToHunt();				
				moveSpeed = 1.25F;
			}

			if(target != null && this.getDistanceToEntity(target) < 6 && !(target.riddenByEntity instanceof EntityDart)
					&& !hasPath()){
				if(entityToAttack != null){
					entityToAttack = null;
				}
				GTFO(target, 8F);
				moveSpeed = 1.25F;
			}			
			if(target != null && this.getDistanceToEntity(target) >= 8/* && 
					!(target.riddenByEntity instanceof EntityDart && !hasPath()*/){
				moveSpeed = 1.25F;
				if(getDistanceToEntity(target) <=10F){
					moveSpeed = 0.1F;
				}
				double d = target.posX - posX;
				double d1 = target.posZ - posZ;
				rotationYaw = (float)((Math.atan2(d1, d) * 180D) / 3.1415927410125732D) - 90F;
				if(attackTime == 0 && !worldObj.isRemote)
				{
					EntityDart entitydart = new EntityDart(worldObj, this, 1.0F, 0);
				//	System.out.println("shooting ze dart");
					double d2 = (target.posY + (double)target.getEyeHeight()) - 0.69999998807907104D - entitydart.posY;
					float f1 = MathHelper.sqrt_double(d * d + d1 * d1) * 0.2F;
					worldObj.playSoundAtEntity(this, "random.bow", 1.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.8F));
					worldObj.spawnEntityInWorld(entitydart);
					entitydart.setArrowHeading(d, d2 + (double)f1, d1, 1.6F, 12F); 
					attackTime = 60;
				}	            
				hasAttacked = true;
			}
			if(target != null && target.riddenByEntity instanceof EntityDart){
				entityToAttack = target;
				moveSpeed = 1.25F;
			}
		}
		else if(target != null){
			entityToAttack = null;
			target = null;
			moveSpeed = .75F;
		}

	}
	protected void attackEntity(Entity entity, float f){
		if(entity.riddenByEntity instanceof EntityDart){
			if( f < 2.0F){// && entity.boundingBox.maxY > boundingBox.minY && entity.boundingBox.minY < boundingBox.maxY){
				attackTime = 30;
				byte byte0 = (byte)getAttackStrength();
				entity.attackEntityFrom(DamageSource.causeMobDamage(this), byte0);
			}
		}	

	}

	public Entity findStuffToHunt(){		
		//if (!worldObj.isRemote) return null;		//if client return null -- COJO NEW
		EntityPlayer player = worldObj.getClosestPlayerToEntity(this, 16D);
		
		if(player != null && player.capabilities.isCreativeMode)
			return null;
		
		if(player != null && this.canEntityBeSeen(player)){
			if(player.inventory.armorInventory[3] == null){
				return player;
			}
		}
		List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(16D, 16D, 16D));
		if(list.isEmpty()){			 
			return null;
		}
		HashMap map = new HashMap();		
		for( Object ent : list){
			if(ent instanceof EntityPig || ent instanceof EntityChicken || ent instanceof EntityCow || ent instanceof EntityKoaManly){
				if(this.canEntityBeSeen((Entity)ent)){
					map.put(this.getDistanceToEntity((Entity)ent), ent);
				}				 
			}
		}
		if(map.isEmpty()){
			return null;
		}
		else{
			List sortedKeys=new ArrayList(map.keySet());
			Collections.sort(sortedKeys);			 
			return (Entity)map.get(sortedKeys.get(0));
		}
	}

    /**
     * This function is used when two same-species animals in 'love mode' breed to generate the new baby animal.
     */
    public EntityAshenHunter spawnBabyAnimal(EntityAgeable par1EntityAgeable)
    {
        return new EntityAshenHunter(this.worldObj);
    }

    public EntityAgeable createChild(EntityAgeable par1EntityAgeable)
    {
        return this.spawnBabyAnimal(par1EntityAgeable);
    }
}
