package net.tropicraft.entities;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.tropicraft.mods.TropicraftMod;

public class EntityMarlin extends EntityTropicraftWaterMob {

	public EntityMarlin(World world) {
		super(world);
		hyperness = 30;
		fickleness = 150;
		horFactor = .3F;
		climbFactor = .225F;
		heightRange = new int[] { 58, 32 };
		texture = "/tropicalmod/marlin.png";
		setSize(0.95F, 0.95F);
	}

	@Override
	public int getMaxHealth() {
		return 20;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		super.writeEntityToNBT(nbttagcompound);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		super.readEntityFromNBT(nbttagcompound);
	}

	@Override
	protected String getLivingSound() {
		return null;
	}

	@Override
	protected String getHurtSound() {
		return null;
	}

	@Override
	protected String getDeathSound() {
		return null;
	}

	@Override
	protected float getSoundVolume() {
		return 0.4F;
	}

	protected void dropFewItems(boolean flag) {
		int i = rand.nextInt(3) + 1;
		for (int j = 0; j < i; j++) {
			if(!worldObj.isRemote)
				entityDropItem(new ItemStack(TropicraftMod.marlinRaw), 0.0F);
		}

	}

	@Override
	public void onDeath(DamageSource damagesource) {
		super.onDeath(damagesource);
		dropFewItems(true);
	}

	@Override
	public boolean interact(EntityPlayer entityplayer) {
		return false;
	}

	@Override
	public boolean isInWater() {
		return worldObj.handleMaterialAcceleration(
				boundingBox.expand(0.0D, -0.60000002384185791D, 0.0D),
				Material.water, this);
	}

	@Override
	public void applyEntityCollision(Entity entity) {
		super.applyEntityCollision(entity);
		if (isSurfacing) {
			entity.attackEntityFrom(DamageSource.causeMobDamage(this),
					attackStrength());
		}
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();

		
	}

	@Override
	protected void updateEntityActionState() {
		super.updateEntityActionState();
		
		/*if (isInWater() && worldObj.rand.nextInt(500) == 0 && !isSurfacing
				&& Math.floor(posY) >= 60D) {

			isSurfacing = true;
			motionX *= 1.5F;
			motionZ *= 1.5F;
			addVelocity(0, .75D, 0);
			surfaceTick = 20;
			reachedTarget = false;
			return;
		}*/
	}

	public boolean isSurfacing;
	public boolean reachedTarget;
	public float important1;
	private float randomMotionSpeed;
	private float important2;
	private float randomMotionVecX;
	private float randomMotionVecY;
	private float randomMotionVecZ;
	public int targetHeight;
	public int surfaceTick;

	@Override
	protected Entity getTarget() {
		return null;
	}

	@Override
	protected int attackStrength() {
		switch (worldObj.difficultySetting) {
		case 1:
			return 3;
		case 2:
			return 5;
		case 3:
			return 7;
		default:
			return 0;
		}
	}

	@Override
	protected boolean canDespawn() {
		return true;
	}

}