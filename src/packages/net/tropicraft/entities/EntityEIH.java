package net.tropicraft.entities;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.tropicraft.mods.TropicraftMod;

public class EntityEIH extends EntityAnimal {

	public EntityEIH(World world) {
		super(world);
		texture = "/tropicalmod/headtext.png";
		setSize(1.0F, 3.0F);
		moveSpeed = 0.5F;
		health = 40;
		isImmuneToFire = true;
		// isJumping = false;
		setImmobile(true);

	}

	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(16, Byte.valueOf((byte) 0));
	}

	public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		super.writeEntityToNBT(nbttagcompound);
		nbttagcompound.setBoolean("Sitting", isImmobile());
		nbttagcompound.setBoolean("Angry", isAngry());
		nbttagcompound.setBoolean("Awake", isAwake());

	}

	public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		super.readEntityFromNBT(nbttagcompound);
		setImmobile(nbttagcompound.getBoolean("Sitting"));
		setAngry(nbttagcompound.getBoolean("Angry"));
		setAwake(nbttagcompound.getBoolean("Awake"));

	}

	public void onLivingUpdate() {
		super.onLivingUpdate();
		// System.out.println("Entity to attack = " + this.entityToAttack);
		if (!isImmobile()) {
			prevYaw = rotationYaw;
			prevPitch = rotationPitch;
		}
		getPlayerToHurt();

		if (getEntityToAttack() != null && !hasPath() && !isAngry()) {
			Entity entity = getEntityToAttack();
			if (entity instanceof EntityPlayer) {
				if (!((EntityPlayer)entity).capabilities.isCreativeMode) {

					EntityPlayer entityplayer = (EntityPlayer) entity;

					if (getDistanceToEntity(entityplayer) < 10F) {

						// worldObj.playSoundAtEntity(entity, "headshort", 1.0F,
						// 1.0F);
						setAwake(true);
						ItemStack itemstack = entityplayer.inventory
								.getCurrentItem();
						if (itemstack != null) {
							if (isAwake()
									&& itemstack.itemID == TropicraftMod.chunkBlock.blockID) {
								setAngry(true);
								setImmobile(false);
							}

						}
					}
					if (getDistanceToEntity(entityplayer) < 3F
							&& worldObj.difficultySetting > 0) {
						// worldObj.playSoundAtEntity(entity, "headmed", 1.0F,
						// 1.0F);
						setAwake(false);
						setImmobile(false);
						setAngry(true);
					}

				}

				else {
					setImmobile(true);
					setAwake(false);
					setAngry(false);

					moveEntity(0D, -.1D, 0D);
					setRotation(prevYaw, prevPitch);

				}
			}
		}

		if (isImmobile()) {
			setRotation(prevYaw, prevPitch);
		} else {
			setAwake(false);
		}
	}
	

	
	@Override
	public String getTexture() {

		if (isAngry()) {
			return "/tropicalmod/headangrytext.png";
		}
		if (isAwake()) {
			return "/tropicalmod/headawaretext.png";
		} else {
			return super.getTexture();
		}
	}

	private void getPlayerToHurt() {
		if (entityToAttack == null) {
			entityToAttack = worldObj.getClosestPlayerToEntity(this, 10);
		} else if (getDistanceToEntity(entityToAttack) > 16) {
			entityToAttack = null;

		}
	}

	@Override
	protected Entity findPlayerToAttack() {

		if (isAngry()) {
			return worldObj.getClosestPlayerToEntity(this, 16D);
		}

		else {

			return null;
		}
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, int i) {
		if (damagesource.getSourceOfDamage() instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer) damagesource
					.getSourceOfDamage();

			ItemStack itemstack = entityplayer.inventory.getCurrentItem();
			if (itemstack != null) {
				if (itemstack.getItem().canHarvestBlock(Block.oreIron)) {
					super.attackEntityFrom(damagesource, i);
				} else {
					int b = rand.nextInt(1);
					if (b == 0) {
						worldObj.playSoundAtEntity(this, "headlaughing", 1.0F,
								1.2F / (rand.nextFloat() * 0.2F + 0.9F));
					}
					if (b == 1) {
						worldObj.playSoundAtEntity(this, "headlaughing2", 1.0F,
								1.2F / (rand.nextFloat() * 0.2F + 0.9F));
					}
				}
			}

		}

		setImmobile(false);
		setAngry(true);
		entityToAttack = damagesource.getSourceOfDamage();

		// if(getDistanceToEntity(entity) > 16D){
		// setImmobile(true);
		// setAngry(false);
		// isJumping = false;
		// setVelocity(0D, 0D,0D);
		// playerToAttack = null;
		// return false;
		// }
		return true;
	}

	protected void attackEntity(Entity entity, float f) {
		if (isAngry()) {

			if (f > 2.0F && f < 6F && rand.nextInt(10) == 0) {
				if (onGround) {
					double d = entity.posX - posX;
					double d1 = entity.posZ - posZ;
					float f1 = MathHelper.sqrt_double(d * d + d1 * d1);
					motionX = (d / (double) f1) * 0.75D * 0.80000001192092896D
							+ motionX * 0.20000000298023224D;
					motionZ = (d1 / (double) f1) * 0.75D * 0.80000001192092896D
							+ motionZ * 0.20000000298023224D;
					motionY = 0.40000000596046448D;
				}
			} else if ((double) f < 1.5D
					&& entity.boundingBox.maxY > boundingBox.minY
					&& entity.boundingBox.minY < boundingBox.maxY) {
				attackTime = 120;
				byte byte0 = 7;
				entity.attackEntityFrom(DamageSource.causeMobDamage(this),
						byte0);
			}
			if (getDistanceToEntity(entity) > 10D) {
				setImmobile(true);
				setAngry(false);
				isJumping = false;
			//	setVelocity(0D, 0D, 0D);
				this.motionX = 0D;
				this.motionY = 0D;
				this.motionZ = 0D;
				entityToAttack = null;

			}
		}
	}

	protected float getSoundVolume() {
		return 0.4F;
	}

	@Override
	protected int getDropItemId() {
		return TropicraftMod.chunkBlock.blockID;
	}

	@Override
	public boolean interact(EntityPlayer entityplayer) {
		// can use this for right click reactions
		return false;
	}

	@Override
	protected boolean isMovementCeased() {
		return isImmobile();
	}

	public boolean isImmobile() {
		// setRotation(0F, 0F);
		return (dataWatcher.getWatchableObjectByte(16) & 1) != 0;
	}

	public void setImmobile(boolean flag) {
		byte byte0 = dataWatcher.getWatchableObjectByte(16);
		if (flag) {
			dataWatcher.updateObject(16, Byte.valueOf((byte) (byte0 | 1)));
		} else {
			dataWatcher.updateObject(16, Byte.valueOf((byte) (byte0 & -2)));
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

	public boolean isAwake() {
		return (dataWatcher.getWatchableObjectByte(16) & 4) != 0;
	}

	public void setAwake(boolean flag) {
		byte byte0 = dataWatcher.getWatchableObjectByte(16);
		if (flag) {
			dataWatcher.updateObject(16, Byte.valueOf((byte) (byte0 | 4)));
		} else {
			dataWatcher.updateObject(16, Byte.valueOf((byte) (byte0 & -5)));
		}
	}

	public int getMaxSpawnedInChunk() {
		return 1;
	}

	protected String getHurtSound() {
		return "headpain";
	}

	protected String getDeathSound() {
		return "headdeath";
	}

	protected String getLivingSound() {
		if (isAngry()) {
			if (rand.nextInt(10) == 0) {
				return "headmed";
			} else {
				return null;
			}
		}
		if (isAwake()) {
			if (rand.nextInt(10) == 0) {
				return "headshort";
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public static float prevYaw;
	public static float prevPitch;

	public EntityAnimal spawnBabyAnimal(EntityAgeable par1EntityAgeable) {
		return new EntityEIH(worldObj);
	}
	
	@Override
    public EntityAgeable func_90011_a(EntityAgeable par1EntityAgeable)
    {
        return this.spawnBabyAnimal(par1EntityAgeable);
    }

	@Override
	public int getMaxHealth() {
		return 40;
	}
}
