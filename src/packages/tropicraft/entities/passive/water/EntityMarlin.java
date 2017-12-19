package tropicraft.entities.passive.water;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import tropicraft.ModInfo;
import tropicraft.entities.EntityTropicraftWaterMob;
import tropicraft.items.TropicraftItems;

public class EntityMarlin extends EntityTropicraftWaterMob {

	public EntityMarlin(World world) {
		super(world);
		hyperness = 30;
		fickleness = 150;
		horFactor = .3F;
		climbFactor = .225F;
		heightRange = new int[] { 58, 32 };
		texture = ModInfo.TEXTURE_ENTITY_LOC + "marlin.png";
		if(world.isRemote){
			if(rand.nextInt(70) == 0)
			texture = ModInfo.TEXTURE_ENTITY_LOC + "marlin2.png";
		}
		setSize(1.4f, 0.95f);
		this.isCatchable = true;
		this.fishingMaxLookDist = 40D;
		this.fishingInterestOdds = 6;
	}

	@Override
	public int getMaxHealth() {
		return 20;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound n) {
		n.setString("texture", texture);
		super.writeEntityToNBT(n);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound n) {
		texture = n.getString("texture");
		super.readEntityFromNBT(n);
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
				entityDropItem(new ItemStack(TropicraftItems.freshMarlin), 0.0F);
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
		return super.isInWater();
		//return worldObj.handleMaterialAcceleration(boundingBox.expand(0.0D, -0.06D, 0.0D), Material.water, this);
	}

	@Override
	public void applyEntityCollision(Entity entity) {
		super.applyEntityCollision(entity);
		if (isSurfacing) {
			//entity.attackEntityFrom(DamageSource.causeMobDamage(this), attackStrength());
		}
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
	}

	@Override
	protected void updateEntityActionState() {
		super.updateEntityActionState();
		if (isInWater() && worldObj.rand.nextInt(500) == 0 && !isSurfacing
				&& Math.floor(posY) >= 60D) {
			//System.out.println("Aye");
			isSurfacing = true;
			motionX *= 1.5F;
			motionZ *= 1.5F;
			addVelocity(0, .75D, 0);
			surfaceTick = 20;
			reachedTarget = false;
			return;
		}
	}


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
	
	@Override
	protected int getDropItemId() {
		return TropicraftItems.freshMarlin.itemID;
	};

}