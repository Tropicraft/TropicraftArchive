package tropicraft.entities.projectiles;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import tropicraft.Tropicraft;
import tropicraft.entities.hostile.land.EIH;
import tropicraft.items.TropicraftItems;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityDart extends Entity {

  private static final short MAX_HIT_TIME = 10 * 20;	//number of seconds desired * number of ticks per second = number of ticks total 8)
	GuiIngame gui;
	Minecraft mc;
	public GameSettings gamesettings;
	public double sinc;
	public int k;
	private float herp;
	private float moveSpeedAct;
	private int xTile;
	private int yTile;
	private int zTile;
	private int inTile;
	private int inData;
	public float Fov;
	private boolean inGround;
	public boolean doesDartBelongToPlayer;
	public int dartShake;
	public EntityLivingBase owner;
	private int ticksInGround;
	private int ticksInAir;
	public float prevYaw;
	public float prevPitch;
	public double prevPosX;
	public double prevPosY;
	public double prevPosZ;
	private boolean hasRidden;
	public EntityAITasks prevTasks = null;

	public EntityDart(World world) {
		super(world);
		prevTasks = null;
		xTile = -1;
		yTile = -1;
		zTile = -1;
		inTile = 0;
		inData = 0;
		inGround = false;
		doesDartBelongToPlayer = false;
		dartShake = 0;
		ticksInAir = 0;
		setSize(0.125F, 0.125F);
		herp = -20F;

	}

	public EntityDart(World world, double d, double d1, double d2) {
		super(world);
		prevTasks = null;
		xTile = -1;
		yTile = -1;
		zTile = -1;
		inTile = 0;
		inData = 0;
		inGround = false;
		doesDartBelongToPlayer = false;
		dartShake = 0;
		ticksInAir = 0;
		setSize(0.5F, 0.5F);
		setPosition(d, d1, d2);
		yOffset = 0.0F;
	}

	public EntityDart(World world, EntityLivingBase EntityLivingBase, float f, int damage) {
		super(world);
		prevTasks = null;
		xTile = -1;
		yTile = -1;
		zTile = -1;
		inTile = 0;
		inData = 0;
		inGround = false;
		doesDartBelongToPlayer = false;
		dartShake = 0;
		ticksInAir = 0;
		owner = EntityLivingBase;
		doesDartBelongToPlayer = EntityLivingBase instanceof EntityPlayer;
		setSize(0.5F, 0.5F);
		setLocationAndAngles(EntityLivingBase.posX, EntityLivingBase.posY + (double) EntityLivingBase.getEyeHeight(), EntityLivingBase.posZ, EntityLivingBase.rotationYaw, EntityLivingBase.rotationPitch);
		posX -= MathHelper.cos((rotationYaw / 180F) * 3.141593F) * 0.16F;
		posY -= 0.10000000149011612D;
		posZ -= MathHelper.sin((rotationYaw / 180F) * 3.141593F) * 0.16F;
		setPosition(posX, posY, posZ);
		yOffset = 0.0F;
		motionX = -MathHelper.sin((rotationYaw / 180F) * 3.141593F)	* MathHelper.cos((rotationPitch / 180F) * 3.141593F);
		motionZ = MathHelper.cos((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F);
		motionY = -MathHelper.sin((rotationPitch / 180F) * 3.141593F);
		setArrowHeading(motionX, motionY, motionZ, f * 1.75F, 1.0F);
		Fov = Tropicraft.instance.proxy.getFOV();
		sinc = 0.0D;
		k = 0;
		hasRidden = false;
		setDamage(damage);
		setHitTimer((short) MAX_HIT_TIME);
		setIsHit(false);
	}

	@Override
	protected void entityInit() {
		prevTasks = null;
		dataWatcher.addObject(16, new Integer(0));
		dataWatcher.addObject(17, new Short(MAX_HIT_TIME));
		dataWatcher.addObject(18, new Byte((byte) 0));
	}

	public void setIsHit(boolean set) {
		dataWatcher.updateObject(18, new Byte((byte) (set ? 1 : 0)));
	}

	public boolean getIsHit() {
		return dataWatcher.getWatchableObjectByte(18) == 1;
	}

	public void setHitTimer(short hitTime) {
		dataWatcher.updateObject(17, new Short(hitTime));
	}

	public int getHitTimer() {
		return dataWatcher.getWatchableObjectShort(17);
	}

	public void setDamage(int damage) {
		this.dataWatcher.updateObject(16, new Integer(damage));
	}

	public int getDamage() {
		return this.dataWatcher.getWatchableObjectInt(16);
	}

	public void setArrowHeading(double d, double d1, double d2, float f,
			float f1) {
		float f2 = MathHelper.sqrt_double(d * d + d1 * d1 + d2 * d2);
		d /= f2;
		d1 /= f2;
		d2 /= f2;
		d += rand.nextGaussian() * .0074999998323619366D * (double) f1;
		d1 += rand.nextGaussian() * 0.0074999998323619366D * (double) f1;
		d2 += rand.nextGaussian() * 0.0074999998323619366D * (double) f1;
		d *= f;
		d1 *= f;
		d2 *= f;
		motionX = d;
		motionY = d1;
		motionZ = d2;
		float f3 = MathHelper.sqrt_double(d * d + d2 * d2);
		prevRotationYaw = rotationYaw = (float) ((Math.atan2(d, d2) * 180D) / 3.1415927410125732D);
		prevRotationPitch = rotationPitch = (float) ((Math.atan2(d1, f3) * 180D) / 3.1415927410125732D);
		ticksInGround = 0;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if (!worldObj.isRemote) {
			if (getIsHit() && getHitTimer() > 0) {
				//			System.out.println("hat " + getHitTimer());
				setHitTimer((short) (getHitTimer() - 1));
			} 
			if (getIsHit() && getHitTimer() <= 0) {
				setIsHit(false);
				setDead();
			}
		}

		if (prevRotationPitch == 0.0F && prevRotationYaw == 0.0F) {
			float f = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
			prevRotationYaw = rotationYaw = (float) ((Math.atan2(motionX, motionZ) * 180D) / 3.1415927410125732D);
			prevRotationPitch = rotationPitch = (float) ((Math.atan2(motionY, f) * 180D) / 3.1415927410125732D);
		}

		int i = worldObj.getBlockId(xTile, yTile, zTile);
		if (i > 0) {
			Block.blocksList[i].setBlockBoundsBasedOnState(worldObj, xTile, yTile, zTile);
			AxisAlignedBB axisalignedbb = Block.blocksList[i].getCollisionBoundingBoxFromPool(worldObj, xTile, yTile, zTile);
			if (axisalignedbb != null && axisalignedbb.isVecInside(Vec3.createVectorHelper(posX, posY, posZ))) {
				inGround = true;
			}
		}

		if (dartShake > 0) {
			dartShake--;
		}

		if (inGround) {
			int j = worldObj.getBlockId(xTile, yTile, zTile);
			int k = worldObj.getBlockMetadata(xTile, yTile, zTile);
			if (j != inTile || k != inData) {
				inGround = false;
				motionX *= rand.nextFloat() * 0.2F;
				motionY *= rand.nextFloat() * 0.2F;
				motionZ *= rand.nextFloat() * 0.2F;
				ticksInGround = 0;
				ticksInAir = 0;
				return;
			}
			ticksInGround++;
			if (ticksInGround == 1200) {
				setDead();
			}
			return;
		}
		ticksInAir++;
		Vec3 vec3d = Vec3.createVectorHelper(posX, posY, posZ);
		Vec3 vec3d1 = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ);
		MovingObjectPosition movingobjectposition = worldObj.rayTraceBlocks_do_do(vec3d, vec3d1, false, true);
		vec3d = Vec3.createVectorHelper(posX, posY, posZ);
		vec3d1 = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ);

		if (movingobjectposition != null) {
			vec3d1 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
		}

		Entity entity = null;
		List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.addCoord(motionX, motionY, motionZ).expand(0.5D, 0.5D, 0.5D));

		if (worldObj.isRemote) 
			list = null;

		double d = 0.0D;
		//weird way of testing if client side ;D
		if (list != null) {
			for (int l = 0; l < list.size(); l++) {
				Entity entity1 = (Entity) list.get(l);
				if (!entity1.canBeCollidedWith() || entity1 == owner || ticksInAir < 2) {
					continue;
				}
				float f4 = 0.3F;
				AxisAlignedBB axisalignedbb1 = entity1.boundingBox.expand(f4, f4, f4);
				MovingObjectPosition movingobjectposition1 = axisalignedbb1.calculateIntercept(vec3d, vec3d1);

				if (movingobjectposition1 == null) {
					continue;
				}
				double d1 = vec3d.distanceTo(movingobjectposition1.hitVec);
				if (d1 < d || d == 0.0D) {
					entity = entity1;
					d = d1;
				}
			}
		}

		if (entity != null) {
			movingobjectposition = new MovingObjectPosition(entity);
		}
		if (movingobjectposition != null) {
			if (movingobjectposition.entityHit != null) {
				if (!(movingobjectposition.entityHit instanceof EntityLivingBase)) {
					if (owner instanceof EntityPlayer) {
						movingobjectposition.entityHit.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) owner), 1);
					} else {
						movingobjectposition.entityHit.attackEntityFrom(DamageSource.causeMobDamage(owner), 1);
					}
					
					this.setDead();
					return;
				}

				//makes the dart bounce off EIH and Enderman
				if (movingobjectposition.entityHit instanceof EIH || movingobjectposition.entityHit instanceof EntityEnderman) { 
					motionX *= -.01D;
					motionY *= .5D;
					motionZ *= -.01D;
					rotationYaw += 180F;
					prevRotationYaw += 180F;
					ticksInAir = 0;
					return;
				}

				//prevents endless paralysis
				if (entity.riddenByEntity instanceof EntityDart) { 
					motionX *= 0D;
					//motionY *= 0D;
					motionZ *= 0D;
					rotationYaw += 180F;
					prevRotationYaw += 180F;
					ticksInAir = 0;
					return;
				}
				if (movingobjectposition.entityHit instanceof EntityPlayer && !((EntityPlayer) movingobjectposition.entityHit).capabilities.isCreativeMode) {
					mountEntity(entity);
					herp = -20;
				}

				//mounts entity on hit
				if (movingobjectposition.entityHit.attackEntityFrom(DamageSource.causeMobDamage(owner), 0)) {
					k = 0;
					if (entity instanceof EntityLivingBase) {
						moveSpeedAct = EntityDartHelper.getEntityMoveSpeed((EntityLivingBase) entity);
					}

					mountEntity(entity);
					movingobjectposition.entityHit.velocityChanged = false;

					worldObj.playSoundAtEntity(this, Tropicraft.modID + ":" + "darthit", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));

				} else //send arrow back if enemy mob is cooling down
				{
					motionX *= 0D;
					//motionY *= 0D;
					motionZ *= 0D;
					rotationYaw += 180F;
					prevRotationYaw += 180F;
					ticksInAir = 0;
				}
			} else { // how far it sticks in
				xTile = movingobjectposition.blockX;
				yTile = movingobjectposition.blockY;
				zTile = movingobjectposition.blockZ;
				inTile = worldObj.getBlockId(xTile, yTile, zTile);
				inData = worldObj.getBlockMetadata(xTile, yTile, zTile);
				motionX = (float) (movingobjectposition.hitVec.xCoord - posX);
				//motionY = (float) (movingobjectposition.hitVec.yCoord - posY);
				motionZ = (float) (movingobjectposition.hitVec.zCoord - posZ);
				float f1 = MathHelper.sqrt_double(motionX * motionX + motionY
						* motionY + motionZ * motionZ);
				posX -= (motionX / (double) f1) * 0.05000000074505806D;
				//posY -= (motionY / (double) f1) * 0.05000000074505806D;
				posZ -= (motionZ / (double) f1) * 0.05000000074505806D;
				worldObj.playSoundAtEntity(this, Tropicraft.modID + ":" + "darthit", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
				inGround = true;
				dartShake = 7; // sets time arrow vibrates
			}
		}

		//applies status effect
		if (getDamage() == 0) {
			if (((worldObj.isRemote && ridingEntity instanceof EntityPlayer) || !worldObj.isRemote) && ((this.ridingEntity != null && ridingEntity.shouldRiderSit()) || this.getFlag(2))) { 
				if (prevTasks == null && ridingEntity instanceof EntityLiving) {
					prevTasks = EntityDartHelper.getEntityAITasks((EntityLiving) ridingEntity);
				}

				if (k < 135) {
					paralyze(ridingEntity);
				} else {

					if (ridingEntity instanceof EntityLiving) {
						EntityDartHelper.setEntityAITasks((EntityLiving) ridingEntity, prevTasks);
						EntityDartHelper.setEntityMoveSpeed((EntityLiving) ridingEntity, moveSpeedAct);
					}

					if (ridingEntity instanceof EntityPlayer) {
						if(worldObj.isRemote && ((EntityPlayer)ridingEntity).username.equals(getClientUsername())) {
							//Tropicraft.instance.proxy.setFOV(Fov);
						}
					}
					setDead();
				}
				k++;
			}
			//end big if above

			//set fov, only if damage == 0 (paralysis)
			if (hasRidden && ridingEntity == null) {
				try {
					if(worldObj.isRemote && ((EntityPlayer)ridingEntity).username.equals(getClientUsername())) {
						//Tropicraft.instance.proxy.setFOV(Fov);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				hasRidden = false;
			}
		} else {
			if (!worldObj.isRemote && ridingEntity != null && ridingEntity instanceof EntityLivingBase && this.getFlag(2) && !(((EntityLivingBase)ridingEntity).getActivePotionEffects().size() > 0) && !getIsHit() && getHitTimer() > 0) {
				setIsHit(true);
				setHitTimer(MAX_HIT_TIME);
				//dummy item for easy array lookups
				int[] potions = new int[]{Potion.blindness.id, Potion.poison.id, Potion.moveSlowdown.id, Potion.harm.id, Potion.confusion.id, Potion.hunger.id, Potion.weakness.id};
				((EntityLivingBase) ridingEntity).addPotionEffect(new PotionEffect(potions[getDamage()], MAX_HIT_TIME, 1));

			//	ridingEntity.setHasActivePotion(true);
			}
		}

		posX += motionX;
		posY += motionY;
		posZ += motionZ;

		float f2 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
		rotationYaw = (float) ((Math.atan2(motionX, motionZ) * 180D) / 3.1415927410125732D);
		for (rotationPitch = (float) ((Math.atan2(motionY, f2) * 180D) / 3.1415927410125732D); rotationPitch - prevRotationPitch < -180F; prevRotationPitch -= 360F) {}
		for (; rotationPitch - prevRotationPitch >= 180F; prevRotationPitch += 360F) {}
		for (; rotationYaw - prevRotationYaw < -180F; prevRotationYaw -= 360F) {}
		for (; rotationYaw - prevRotationYaw >= 180F; prevRotationYaw += 360F) {}
		rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch)	* 0.2F;
		rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
		float f3 = 0.99F;
		float f5 = 0.03F;

		if (isInWater()) {
			for (int i1 = 0; i1 < 4; i1++) {
				float f6 = 0.25F;
				worldObj.spawnParticle("bubble", posX - motionX * (double) f6, posY - motionY * (double) f6, posZ - motionZ * (double) f6, motionX, motionY, motionZ);
			}

			f3 = 0.8F;
		}

		motionX *= f3;
		motionY *= f3;
		motionZ *= f3;
		motionY -= f5;
		setPosition(posX, posY, posZ);

	}

	@SideOnly(Side.CLIENT)
	public String getClientUsername() {
		if (mc == null) 
			mc = FMLClientHandler.instance().getClient();

		if (mc.thePlayer != null) 
			return mc.thePlayer.username;

		return "";
	}

	public static int sign(double f) {
		if (f != f) {
			throw new IllegalArgumentException("NaN");
		}
		if (f == 0) {
			return 0;
		}
		f *= Double.POSITIVE_INFINITY;
		if (f == Double.POSITIVE_INFINITY) {
			return +1;
		}
		if (f == Double.NEGATIVE_INFINITY) {
			return -1;
		}

		// this should never be reached, but I've been wrong before...
		throw new IllegalArgumentException("Unfathomed double");
	}

	public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		nbttagcompound.setShort("xTile", (short) xTile);
		nbttagcompound.setShort("yTile", (short) yTile);
		nbttagcompound.setShort("zTile", (short) zTile);
		nbttagcompound.setByte("inTile", (byte) inTile);
		nbttagcompound.setByte("inData", (byte) inData);
		nbttagcompound.setByte("shake", (byte) dartShake);
		nbttagcompound.setByte("inGround", (byte) (inGround ? 1 : 0));
		nbttagcompound.setBoolean("player", doesDartBelongToPlayer);
		nbttagcompound.setInteger("damage", getDamage());
		nbttagcompound.setBoolean("hit", getIsHit());
		nbttagcompound.setShort("hitTime", (short) getHitTimer());
	}

	public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		xTile = nbttagcompound.getShort("xTile");
		yTile = nbttagcompound.getShort("yTile");
		zTile = nbttagcompound.getShort("zTile");
		inTile = nbttagcompound.getByte("inTile") & 0xff;
		inData = nbttagcompound.getByte("inData") & 0xff;
		dartShake = nbttagcompound.getByte("shake") & 0xff;
		inGround = nbttagcompound.getByte("inGround") == 1;
		doesDartBelongToPlayer = nbttagcompound.getBoolean("player");
		setDamage(nbttagcompound.getInteger("damage"));
		setIsHit(nbttagcompound.getBoolean("hit"));
		setHitTimer(nbttagcompound.getShort("hitTime"));
	}

	public void onCollideWithPlayer(EntityPlayer entityplayer) {
		if (worldObj.isRemote) {
			return;
		}

		if (inGround && doesDartBelongToPlayer && dartShake <= 0 && entityplayer.inventory.addItemStackToInventory(new ItemStack(TropicraftItems.dart, 1))) {
			worldObj.playSoundAtEntity(this, "random.pop", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
			entityplayer.onItemPickup(this, 1);
			setDead();
		}
	}

	public float getShadowSize() {
		return 0.0F;
	}

	public void setVelocity1(double par1, double par3, double par5) {
		this.motionX = par1;
		this.motionY = par3;
		this.motionZ = par5;
	}

	public void paralyze(Entity entity) { // contains status effect		
		if (getDamage() == 0) {
			if (entity instanceof EntityLivingBase) {

				if (entity instanceof EntityCreeper) {
					EntityDartHelper.setCreeperIgnitionTime((EntityCreeper) entity,
							0);
				}

				entity.rotationYaw = entity.prevRotationYaw;
				if (entity instanceof EntityCreature) {
					EntityDartHelper.setEntityToAttack((EntityCreature) entity,
							null);
				}

				EntityDartHelper.setEntityAttackTime((EntityLivingBase) entity, 60);
				EntityDartHelper.setEntityMoveSpeed((EntityLivingBase) entity, 0);
				EntityDartHelper.setIsEntityJumping((EntityLivingBase) entity, false);

				if (entity.onGround) {
					entity.motionX = 0;
					//entity.motionY = 0;
					entity.motionZ = 0;
					entity.posX = ((EntityLivingBase) entity).lastTickPosX;
					entity.posY = ((EntityLivingBase) entity).lastTickPosY;
					entity.posZ = ((EntityLivingBase) entity).lastTickPosZ;
					entity.rotationPitch = entity.prevRotationPitch;
					entity.rotationYaw = entity.prevRotationYaw;
				} else {
					entity.motionX = 0;
					//entity.motionY = -.5D;
					entity.motionZ = 0;
					entity.posX = ((EntityLivingBase) entity).lastTickPosX;
					entity.posZ = ((EntityLivingBase) entity).lastTickPosZ;
					entity.rotationPitch = entity.prevRotationPitch;
					entity.rotationYaw = entity.prevRotationYaw;

					if ((int) entity.posY > worldObj.getHeightValue(
							(int) entity.posX, (int) entity.posY)) {
						//entity.motionY = -0.5D;
					}
				}

				EntityDartHelper.setEntityAITasks((EntityLivingBase) entity, null);
			}
			if (entity instanceof EntityPlayer) {
				hasRidden = true;
				if(worldObj.isRemote && ((EntityPlayer)ridingEntity).username.equals(getClientUsername())) {
					trollFOV((EntityPlayer)entity);
				}

			}
		} 	
	}

	protected boolean canTriggerWalking() {
		return false;
	}

	/**
	 * Troll...fov...
	 * @param entity Player to be trolled
	 */
	public void trollFOV(EntityPlayer entity) {

		sinc = (100D - (double) k) * .125D;
		float trollov = 0F;
		if (k <= 126) {
			trollov = Fov + (float) ((Math.sin(sinc)) / sinc);
		}
		if (k == 100) {
			trollov = Fov + 1;
		}
		if (k > 125) {
			if (herp == -20F && entity.worldObj.isRemote) {
				herp = (Tropicraft.instance.proxy.getFOV() - Fov) / 10;
			}
			if (entity.worldObj.isRemote) {
				trollov = Tropicraft.instance.proxy.getFOV() - herp;
			}
		}

		//if (entity.worldObj.isRemote)
			//Tropicraft.instance.proxy.setFOV(trollov);

	}

	public void mountEntity(Entity entity) {
		super.mountEntity(entity);
	}

	private void trollRender(int k, int i, int j) {
		float f = 1.0F / (float) k * .9F;
		GL11.glDisable(3008 /*
		 * GL_ALPHA_TEST
		 */);
		GL11.glDisable(2929 /*
		 * GL_DEPTH_TEST
		 */);
		GL11.glDepthMask(false);
		GL11.glBlendFunc(770, 771);
		GL11.glColor4f(1.0F, .5F, 1.0F, 1.0F);

	}
}