package net.tropicraft.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.tropicraft.mods.TropicraftMod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public abstract class EntityAshen extends EntityAnimal {
	public float bobber;
	public int bobberHelper;
	public int actionPicker;
	public Entity maskToTrack;
	public Entity itemToTrack;
	protected int[] dropItems = new int[]{TropicraftMod.poisonSkin.shiftedIndex, Item.bone.shiftedIndex,  Item.rottenFlesh.shiftedIndex};
	protected List items = Arrays.asList(TropicraftMod.ashenMask.shiftedIndex, TropicraftMod.poisonSkin.shiftedIndex, TropicraftMod.paraDart.shiftedIndex,
			TropicraftMod.blowGun.shiftedIndex, TropicraftMod.bambooSpear.shiftedIndex, Item.beefRaw.shiftedIndex, Item.porkRaw.shiftedIndex,
			Item.chickenRaw.shiftedIndex, Item.rottenFlesh);
	
	public EntityAshen(World world)
	{
		super(world);
		texture = "/tropicalmod/nativetext.png";
		setSize(0.5F, 1.3F);      
		setMaskType(new Random().nextInt(7));
		moveSpeed = 0.75f;
		actionPicker = 0;
	}

	public int getMaxHealth()
	{
		return 20;
	}
	@Override
	public void onLivingUpdate()
	{    	
		super.onLivingUpdate();		
		if(this.posX != this.lastTickPosX && this.posZ != this.lastTickPosZ || this.posY != this.lastTickPosY){
			bobberHelper();
		}
		if(getActionState() == 1 &&  fleeingTick <=40 && maskToTrack != null){
			if(this.getDistanceToEntity(maskToTrack) <2){
				this.isJumping = true;
			}
			if(this.getDistanceToEntity(maskToTrack) <= 1.0 && ((EntityLostMask)maskToTrack).getMode() == 1){
				this.isJumping = false;
				setMaskType(((EntityLostMask)maskToTrack).getColor());
				maskToTrack.setDead();
				setActionState(actionPicker);
				maskToTrack = null;
				fleeingTick = 0;
				moveSpeed = .75f;

			}
		} 
		if(entityToAttack == null && itemToTrack != null && this.getDistanceToEntity(itemToTrack) <= 1){
			itemToTrack.setDead();
			itemToTrack = null;

		}

	}
	
	public void setMaskType(int type) {
		this.dataWatcher.updateObject(16, new Integer(type));
	}
	
	public int getMaskType() {
		return this.dataWatcher.getWatchableObjectInt(16);
	}
	
	public void setActionState(int state) {
		this.dataWatcher.updateObject(17, new Integer(state));
	}
	
	public int getActionState() {
		return this.dataWatcher.getWatchableObjectInt(17);
	}
	
	@Override
	protected void entityInit()
	{
		super.entityInit();        
		dataWatcher.addObject(16, new Integer(0));
		dataWatcher.addObject(17, new Integer(0));       
	}
	@Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeEntityToNBT(nbttagcompound);
		nbttagcompound.setShort("MaskType", (short)getMaskType());
		nbttagcompound.setShort("ActionState", (short)getActionState());
	}
	@Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readEntityFromNBT(nbttagcompound);
		setMaskType(nbttagcompound.getShort("MaskType"));
		setActionState(nbttagcompound.getShort("ActionState"));
	}
	@Override
	public boolean attackEntityFrom(DamageSource damagesource, int i)
	{
		super.attackEntityFrom(damagesource, i);
		if(damagesource.getSourceOfDamage() instanceof EntityPlayer && getActionState() != 1 && i >= 7){	    		
			double d =damagesource.getEntity().posX - posX;
			double d1 = damagesource.getEntity().posZ - posZ;
			
			EntityLostMask mask = new EntityLostMask(worldObj, (double)this.posX, (double)this.posY + (double)this.getEyeHeight(), 
					(double)this.posZ, this.getMaskType());
			if (!worldObj.isRemote) {
				worldObj.spawnEntityInWorld(mask);
				mask.setMaskHeading(-d, .5, -d1, .5F, .5F);
				setActionState(1);				    		
				maskToTrack = mask;
				fleeingTick = 100;
			}

		}	    	
		return true;	       
	}
	public void applyEntityCollision(Entity entity)
	{
		super.applyEntityCollision(entity);
		if(entity instanceof EntityLostMask && entity.equals(maskToTrack) && getActionState() == 1 && fleeingTick <= 40){
			setMaskType(((EntityLostMask)maskToTrack).getColor());
			maskToTrack.setDead();
			setActionState(actionPicker);
			maskToTrack = null;
			fleeingTick = 0;
			moveSpeed = .75f;
		}       

	}
	protected void updateEntityActionState()
	{
		super.updateEntityActionState();
		
		if(getActionState() == 1 && fleeingTick<= 40){
			List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(16D, 16D, 16D));
			if(!list.contains(maskToTrack)){
				maskToTrack = null;
			}
			if(maskToTrack == null){
				Entity ent = getclosestMask();
				if(ent != null){
					maskToTrack = ent;
				}

			}
			else if (maskToTrack != null  && !hasPath()){
				moveSpeed= 1.00f;
				this.getPathOrWalkableBlock(maskToTrack, this.getDistanceToEntity(maskToTrack));
			}
		}
		else if( getActionState() != 1 && entityToAttack == null && !hasPath() && worldObj.rand.nextInt(5) == 1){
			Entity item = getClosestItem();
			if(item != null){
				itemToTrack = item;
				this.getPathOrWalkableBlock(itemToTrack, this.getDistanceToEntity(itemToTrack));
			}
		}
		else if(getActionState() != 1 && entityToAttack == null && !hasPath()){
			EntityPlayer thePlayer = worldObj.getClosestPlayerToEntity(this, 16D);
			if(thePlayer != null){	    				
				if(thePlayer.inventory.armorInventory[3] != null){ 
					if(	thePlayer.inventory.armorInventory[3].itemID == TropicraftMod.ashenMask.shiftedIndex){
						float f = thePlayer.getDistanceToEntity(this);
						if(f > 5F)
						{
							getPathOrWalkableBlock(thePlayer, f);
						}
					}

				}

			}
		}


	}   


	/**
	 * Checks if the entity's current position is a valid location to spawn this entity.
	 */
	public boolean getCanSpawnHere()
	{
		return this.worldObj.checkIfAABBIsClear(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).size() == 0 && !this.worldObj.isAnyLiquid(this.boundingBox);
	}


	protected EntityAnimal func_40145_a(EntityAnimal entityanimal)
	{

		return null;
	}

	public boolean hasMask(){
		return getActionState() != 1;
	}
	
	private void getPathOrWalkableBlock(Entity entity, float f)
	{
		PathEntity pathentity = worldObj.getPathEntityToEntity(this, entity, 16F, false, false, false, false);
		if(pathentity == null){
			return;
		}
		setPathToEntity(pathentity);

	}
	public Entity getclosestMask(){
		List list = worldObj.getEntitiesWithinAABB(net.tropicraft.entities.EntityLostMask.class, AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(posX, posY, posZ, posX + 1.0D, posY + 1.0D, posZ + 1.0D).expand(16D, 4D, 16D));
		if(list.isEmpty()){
			return null;
		}
		else{
			HashMap map = new HashMap();		  
			for( Object ent : list){		  			
				map.put(this.getDistanceToEntity((Entity)ent), ent);		  		
			}		  	
			List sortedKeys=new ArrayList(map.keySet());
			Collections.sort(sortedKeys);
			return (Entity)map.get(sortedKeys.get(0));
		}		  	
	}
	public Entity getClosestItem(){
		List list = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(posX, posY, posZ, posX + 1.0D, posY + 1.0D, posZ + 1.0D).expand(16D, 4D, 16D));
		if(list.isEmpty()){
			return null;
		}

		HashMap map = new HashMap();	
		for( Object item : list){
			if(items.contains(((EntityItem)item).func_92014_d().itemID)){
				map.put(this.getDistanceToEntity((Entity)item), item);
			}
		}
		if(map.isEmpty()){
			return null;
		}
		List sortedKeys=new ArrayList(map.keySet());
		Collections.sort(sortedKeys);
		return (Entity)map.get(sortedKeys.get(0));

	}
	public ItemStack[] getHeld(){

		if(getActionState() == 2){
			ItemStack[] itemstack ={new ItemStack(TropicraftMod.bambooSpear, 1), new ItemStack(TropicraftMod.blowGun, 1)};
			return itemstack;
		}
		else{
			return null;
		}

	}
	public void onDeath(DamageSource damagesource)
	{
		Entity entity = damagesource.getEntity();
		if(entity != null && hasMask()){		  		
			double d = entity.posX - posX;
			double d1 = entity.posZ - posZ;	    	
			EntityLostMask mask = new EntityLostMask(worldObj, (double)this.posX, (double)this.posY + (double)this.getEyeHeight(), 
					(double)this.posZ, this.getMaskType());		    
			if (!worldObj.isRemote) {
				worldObj.spawnEntityInWorld(mask);
				mask.setMaskHeading(-d, .5, -d1, .5F, .5F);
				setActionState(1);
			}
		}
		super.onDeath(damagesource);
	}
	protected void dropFewItems(boolean flag, int i)
	{
		for(i = 0; i < rand.nextInt(4); i ++ ){
			int j = dropItems[rand.nextInt(dropItems.length)];
			dropItem(j, 1);
		}

	}
	public void bobberHelper(){
		bobber = (float) (Math.sin(bobberHelper)*0.01F);
		if(bobberHelper >= 360){
			bobberHelper = 0;
		}
		else{

			bobberHelper++;
		}

	}
	public void GTFO(Entity entity, float distance){
		double var16 = this.posX - entity.posX;
		double var18 = this.posZ - entity.posZ;
		this.rotationYaw = (float)(Math.atan2(var18, var16) * 180.0D / 3.1415927410125732D) - 90.0F;	
		//now, using that angle, get a new relative position based on your entities coords, fyi center = this, in your case

		//offset angle
		float look = 0F;
		//distance, might wanna use 8 or something
		double dist = distance;		
		double gatherX = 0.0D;		
		double gatherZ = 0.0D;
		double gatherY = 16;
		int counter = 0;
		do{
			if(counter == 13){
				// System.out.println("No Path Found");
				return;
			}
			gatherX = this.posX + ((double)(-Math.sin((this.rotationYaw+look) / 180.0F * 3.1415927F) * Math.cos(this.rotationPitch / 180.0F * 3.1415927F)) * dist);		
			gatherZ = this.posZ + ((double)(Math.cos((this.rotationYaw+look) / 180.0F * 3.1415927F) * Math.cos(this.rotationPitch / 180.0F * 3.1415927F)) * dist);
			gatherY = worldObj.getHeightValue((int)gatherX, (int)gatherZ);
			look += 15F;
			counter ++;
		}while (Math.abs(gatherY - this.posY) > 14 );

		// System.out.println("Path Found at, X = " + (int)gatherX + " Y = " + (int)gatherY + " Z = " + (int)gatherZ);

		PathEntity pathentity = worldObj.getEntityPathToXYZ(this, (int)gatherX, (int)gatherY, (int)gatherZ, 16F, false, false, false, false);
		if(pathentity == null){
			return;
		}
		setPathToEntity(pathentity);
	}

}
