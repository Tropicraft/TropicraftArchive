package net.tropicraft.entities;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.tropicraft.mods.TropicraftMod;

import java.util.List;
import java.util.Random;

public class EntityVMonkey extends EntityAnimal {

	public EntityVMonkey(World world) {
		super(world);
		texture = "/tropicalmod/monkeytext.png";
		setSize(.5F, .8F);
		drinksum = 0;
		setDrinking(false);
		drunkIt = 0;
		direction = true;
		drinkTimer = 0;
		attackTimer = 0;
		drunktime = 0;
		target = null;
	}

	@Override
	public boolean isAIEnabled() {
		return false;
	}

	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(16, Byte.valueOf((byte) 0));
		dataWatcher.addObject(17, new Integer(attackTimer));
		dataWatcher.addObject(18, new Integer(drinkTimer));
		dataWatcher.addObject(19, new Integer(drinksum));
		dataWatcher.addObject(20, new Integer(drunktime));
		dataWatcher.addObject(21, "");
	}
	
	@Override
	protected void updateEntityActionState() {
		super.updateEntityActionState();
		if (!hasAttacked && !hasPath() && getFriend() != ""
				&& ridingEntity == null) {

			EntityPlayer entityplayer = worldObj
					.getPlayerEntityByName(getFriend());
			if (entityplayer != null) {
				float f = entityplayer.getDistanceToEntity(this);
				if (f > 5F) {
					getPathOrWalkableBlock(entityplayer, f);
				}
			}
		}
		
	/*	EntityPlayer player = worldObj.getClosestPlayerToEntity(this, 32D);
		
		if(player != null) {
			this.mountEntity(player);
		} */
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		List list = worldObj.getEntitiesWithinAABBExcludingEntity(this,
				boundingBox.expand(16D, 16D, 16D));
		for (int j = 0; j < list.size(); j++) {

			Entity entity1 = (Entity) list.get(j);
			if(entity1 != null) {
				if (entity1 instanceof EntityPlayer
						&& getDistanceToEntity(entity1) < 10 && !isDrinking()
						&& attackTimer == 0) {
					ItemStack itemstack = ((EntityPlayer) entity1).inventory
							.getCurrentItem();
					if (itemstack != null
							&& itemstack.itemID == TropicraftMod.bambooMugFull.shiftedIndex) {
						target = (EntityPlayer) entity1;
						attackTimer = 1;
						entityToAttack = (EntityPlayer) entity1;
						// System.out.println(target.username);

					}
				}
				if (entity1 instanceof EntityItem
						&& getDistanceToEntity(entity1) < 5 && !isDrinking()) {
					if (((EntityItem) entity1).func_92014_d().itemID == TropicraftMod.bambooMugFull.shiftedIndex) {
						entityToAttack = entity1;
						attackTimer = 0;
						PathEntity pathentity = worldObj.getPathEntityToEntity(
								this, entity1, 16F, false, false, false, false);
						setPathToEntity(pathentity);
						setAngry(false);
						if (this.getDistanceToEntity(entity1) < .5F) {
							entity1.setDead();

							setDrinking(true);

						}
					}
				}
				if (getFriend() != ""
						&& entity1 instanceof EntityChair
						&& worldObj != null
						&& worldObj.getPlayerEntityByName(getFriend()) != null
						&& worldObj.getPlayerEntityByName(getFriend()).ridingEntity != null
						&& worldObj.getPlayerEntityByName(getFriend()).ridingEntity instanceof EntityChair
						&& worldObj.getPlayerEntityByName(getFriend()).getDistanceToEntity(entity1) < 5 
						&& !isDrinking()) 
				{
					if (worldObj.getPlayerEntityByName(getFriend()).ridingEntity == entity1) {
						j++;
					} else {
						PathEntity pathentity = worldObj.getPathEntityToEntity(
								this, entity1, 16F, false, false, false, false);
						setPathToEntity(pathentity);
					}
				}
				if (entity1 instanceof EntityChair && !isDrinking()
						&& ridingEntity == null
						&& this.getDistanceToEntity(entity1) < 1.5F
						&& entityToAttack == null) {
					if (getFriend() == "" && worldObj.rand.nextInt(150) == 0) {
						this.mountEntity(entity1);
						break;
					}
					if (getFriend() != "") {
						this.mountEntity(entity1);

					}
				}

				if (isAngry()) {
					if (entity1 instanceof EntityChair
							|| entity1 instanceof EntityUmbrella
							&& getDistanceToEntity(entity1) < 5) {
						entityToAttack = entity1;
						break;
					} else {
						entityToAttack = worldObj.getClosestPlayerToEntity(this,
								16D);
					}
				}
			}
		}
		if (worldObj.isRemote && worldObj.rand.nextInt(600) == 0 && this.isRiding()
				&& getFriend() == "" || entityToAttack != null) {
			ridingEntity = null;

		}
		if (getFriend() != ""
				&& this.isRiding()
				&& worldObj.getPlayerEntityByName(getFriend()) != null
				&& worldObj.getPlayerEntityByName(getFriend()).ridingEntity == null) {
			ridingEntity = null;
		}

		if (drunktime > 0 && !isDrinking()) {
			drunktime--;
		}
		if (isOnLadder()) {
			isSitting = false;
			isClimbing = true;

		}
		if (drunktime > 6000 && worldObj.rand.nextInt(600) == 0 && !isAngry()) {
			attackTimer = 1;
			// moveSpeed = .3F;
		}

		if (drunktime == 0 && drinksum > 0) {
			if (worldObj.rand.nextInt(5000) == 0) {
				drinksum--;
				if (drinksum < 5) {
					setFriend("");
				}
			}
		}
		if (inWater) {
			motionY += .0022;
		}
		if(worldObj.isRemote)
			sit();

		// System.out.println(attackTimer);
		// System.out.println(getFriend());

	}

	public void onLivingUpdate() {

		super.onLivingUpdate();
		drinking();
		interested();
		if (drunktime > 1500) {
			weave();
		}

		// System.out.println(drunktime);
		// System.out.println(drinksum);

	}

	protected boolean isMovementCeased() {
		return isDrinking();
	}

	public void interested() {

		if (attackTimer >= 1) {
			attackTimer++;
			if (attackTimer == 150) {

				setAngry(true);
			}
			if (attackTimer >= 200) {
				setAngry(false);
				attackTimer = 0;
				entityToAttack = null;

			}
		}

	}

	public void drinking() {
		if (isDrinking()) {

			isJumping = false;
			attackTimer = 0;
			isSitting = true;
			drinkTimer++;
			if (drinkTimer <= 150) {
				holding = new ItemStack(TropicraftMod.bambooMugFull);
			}
			if (drinkTimer > 150) {
				holding = new ItemStack(TropicraftMod.bambooMugEmpty);
			}
			if (drinkTimer > 200) {
				drunktime += 1000;
				if(!worldObj.isRemote)
					this.dropItem(TropicraftMod.bambooMugEmpty.shiftedIndex, 1);
				setDrinking(false);
				holding = null;
				drinkTimer = 0;
				drinksum++;
				if (drinksum > 5 && getFriend() == "" && target != null) {
					setFriend(target.username);
				}

			}

		}

	}

	protected void weave() {

		drunkIt += .25F;
		if (drunkIt % 4 == 0) {
			moveStrafing = 20F * MathHelper.cos((float) Math.pow(drunkIt, 2));
		} else {
			moveStrafing = .5F * MathHelper.cos(drunkIt);
		}

	}

	protected boolean sit() {

		if ((posX == prevPosX && !inWater && !isJumping || isDrinking()
				|| isRiding())) {
			return isSitting = true;
		} else {
			return isSitting = false;
		}

	}

	public float getBlockPathWeight(int i, int j, int k) {
		if (worldObj.isMaterialInBB(boundingBox.expand(16D, 16D, 16D),
				Material.wood)) {
			return 100F;
		} else {
			return worldObj.getLightBrightness(i, j, k) - 0.5F;
		}
	}

	// public boolean getCanSpawnHere()
	// {
	// System.out.println("spawning monkey?");
	// return true;
	// }
	//
	public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		super.writeEntityToNBT(nbttagcompound);
		nbttagcompound.setShort("AttackTimer", (short) attackTimer);
		nbttagcompound.setShort("DrinkSum", (short) drinksum);
		nbttagcompound.setShort("DrunkTime", (short) drunktime);
		nbttagcompound.setBoolean("Sitting", isDrinking());
		nbttagcompound.setBoolean("Angry", isAngry());
		if (getFriend() == null) {
			nbttagcompound.setString("Friend", "");
		} else {
			nbttagcompound.setString("Friend", getFriend());
		}

	}

	public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		super.readEntityFromNBT(nbttagcompound);
		attackTimer = nbttagcompound.getShort("AttackTimer");
		drunktime = nbttagcompound.getShort("DrunkTime");
		drinksum = nbttagcompound.getShort("DrinkSum");
		setDrinking(nbttagcompound.getBoolean("Sitting"));
		setAngry(nbttagcompound.getBoolean("Angry"));
		String s = nbttagcompound.getString("Friend");
		if (s.length() > 0) {
			setFriend(s);
		}

	}

	public boolean isDrinking() {
		return (dataWatcher.getWatchableObjectByte(16) & 4) != 0;
	}

	public void setDrinking(boolean flag) {
		byte byte0 = dataWatcher.getWatchableObjectByte(16);
		if (flag) {
			dataWatcher.updateObject(16, Byte.valueOf((byte) (byte0 | 4)));
		} else {
			dataWatcher.updateObject(16, Byte.valueOf((byte) (byte0 & -5)));
		}
	}

	public boolean isAngry() {
		return (dataWatcher.getWatchableObjectByte(16) & 2) != 0;
	}

	public void setAngry(boolean flag) {
		byte byte0 = dataWatcher.getWatchableObjectByte(16);
		if (flag) {
			dataWatcher.updateObject(16, Byte.valueOf((byte) (byte0 | 2)));
		} else {
			dataWatcher.updateObject(16, Byte.valueOf((byte) (byte0 & -3)));
		}
	}

	public String getFriend() {
		return dataWatcher.getWatchableObjectString(21);
	}

	public void setFriend(String s) {
		dataWatcher.updateObject(21, s);
	}

	public boolean isOnLadder() {
		if (isCollidedHorizontally
				&& worldObj.isAABBInMaterial(boundingBox, Material.wood)) {

			return true;
		} else {
			isClimbing = false;
			return false;
		}
	}

	protected void attackEntity(Entity entity, float f) {
		if (isAngry() && !inWater && f > 0F && f < 6F
				&& worldObj.rand.nextInt(10) == 0) {
			double j = 0;
			double d = entity.posX - posX;
			double d1 = entity.posZ - posZ;
			float f1 = MathHelper.sqrt_double(d * d + d1 * d1);
			if (drunktime < 1500) {
				j = .75D;
			}
			if (drunktime > 1500 && worldObj.rand.nextInt(2) == 0) {
				j = .30D;
			}
			if (onGround) {
				motionX = (d / (double) f1) * j * 0.80000001192092896D
						+ motionX * 0.20000000298023224D;
				motionZ = (d1 / (double) f1) * j * 0.80000001192092896D
						+ motionZ * 0.20000000298023224D;
				motionY = 0.23D;
			}
			if ((double) f < 1.5D && entity.boundingBox.maxY > boundingBox.minY
					&& entity.boundingBox.minY < boundingBox.maxY) {
				attackTime = 120;

				if (entity instanceof EntityChair
						|| entity instanceof EntityUmbrella) {
					entity.attackEntityFrom(DamageSource.causeMobDamage(this),
							4);
				}

				if (entity instanceof EntityPlayer) {
					entity.attackEntityFrom(DamageSource.causeMobDamage(this),
							1);
					ItemStack itemstack = ((EntityPlayer) entity).inventory
							.getCurrentItem();
					if (itemstack != null
							&& itemstack.itemID == TropicraftMod.bambooMugFull.shiftedIndex) {
						((EntityPlayer) entity).dropOneItem(true);

						setAngry(false);
						attackTimer = 0;
					}
				} else {
					entity.attackEntityFrom(DamageSource.causeMobDamage(this),
							2);

				}
			}

		}
	}

	private void getPathOrWalkableBlock(Entity entity, float f) {
		PathEntity pathentity = worldObj.getPathEntityToEntity(this, entity,
				16F, false, false, false, false);
		if (pathentity == null && f > 12F) {
			int i = MathHelper.floor_double(entity.posX) - 2;
			int j = MathHelper.floor_double(entity.posZ) - 2;
			int k = MathHelper.floor_double(entity.boundingBox.minY);
			for (int l = 0; l <= 4; l++) {
				for (int i1 = 0; i1 <= 4; i1++) {
					if ((l < 1 || i1 < 1 || l > 3 || i1 > 3)
							&& worldObj.isBlockNormalCube(i + l, k - 1, j + i1)
							&& !worldObj.isBlockNormalCube(i + l, k, j + i1)
							&& !worldObj
							.isBlockNormalCube(i + l, k + 1, j + i1)) {
						setLocationAndAngles((float) (i + l) + 0.5F, k,
								(float) (j + i1) + 0.5F, rotationYaw,
								rotationPitch);
						return;
					}
				}

			}

		} else {
			setPathToEntity(pathentity);
		}
	}

	protected String getLivingSound() {
		if (isDrinking()) {
			return "monkeydrinking";
		}
		if (drunktime >= 1500 && !isAngry()) {
			return "monkeyhiccup";
		}
		if (isAngry()) {
			return "monkeyangry";
		} else {
			return "monkeyliving";
		}
	}

	protected String getHurtSound() {
		return "monkeyhurt";
	}

	protected String getDeathSound() {
		return null;
	}

	protected float getSoundVolume() {
		return 0.4F;
	}

	protected int getDropItemId() {
		return -1;
	}

	public ItemStack getHeldItem() {
		super.getHeldItem();
		return holding;
	}

	public String getTexture() {

		if (isAngry()) {
			return "/tropicalmod/monkeyAngrytext.png";
		} else {
			return super.getTexture();
		}
	}

	public EntityPlayer target;
	public Entity furnitureToAttack;
	public int drunktime;
	public boolean direction;
	public float drunkIt;
	public int drinksum;
	public int drinkTimer;
	public int attackTimer;
	public ItemStack holding;
	public boolean isSitting;
	protected Random rand;
	public boolean isClimbing;

	public EntityAnimal spawnBabyAnimal(EntityAgeable var1) {
		return new EntityVMonkey(worldObj);
	}

	@Override
	public int getMaxHealth() {
		return 10;
	}

	@Override
	public EntityAgeable func_90011_a(EntityAgeable var1) {
		return spawnBabyAnimal(var1);
	}

}
