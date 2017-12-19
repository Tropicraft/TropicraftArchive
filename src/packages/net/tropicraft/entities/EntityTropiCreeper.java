package net.tropicraft.entities;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.tropicraft.mods.TropicraftMod;
import net.tropicraft.world.worldgen.WorldGenTropicraftCurvedPalm;
import net.tropicraft.world.worldgen.WorldGenTropicraftLargePalmTrees;
import net.tropicraft.world.worldgen.WorldGenTropicraftNormalPalms;

import java.util.Random;

public class EntityTropiCreeper extends EntityMob
{
	/**
	 * The amount of time since the creeper was close enough to the player to ignite
	 */
	int timeSinceIgnited;

	/**
	 * Time when this creeper was last in an active state (Messed up code here, probably causes creeper animation to go
	 * weird)
	 */
	int lastActiveTime;

	public EntityTropiCreeper(World par1World)
	{
		super(par1World);
		this.texture = "/tropicalmod/mobs/creeper.png";
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAITropiCreeperSwell(this));
		this.tasks.addTask(4, new EntityAIAttackOnCollide(this, 0.25F, false));
		this.tasks.addTask(5, new EntityAIWander(this, 0.2F));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(6, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 16.0F, 0, true));
		this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
	}

	/**
	 * Returns true if the newer Entity AI code should be run
	 */
	public boolean isAIEnabled()
	{
		return true;
	}

	public int getMaxHealth()
	{
		return 20;
	}

	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(16, Byte.valueOf((byte) - 1));
		this.dataWatcher.addObject(17, Byte.valueOf((byte)0));
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);

		if (this.dataWatcher.getWatchableObjectByte(17) == 1)
		{
			par1NBTTagCompound.setBoolean("powered", true);
		}
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);
		this.dataWatcher.updateObject(17, Byte.valueOf((byte)(par1NBTTagCompound.getBoolean("powered") ? 1 : 0)));
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate()
	{
		if (this.isEntityAlive())
		{
			this.lastActiveTime = this.timeSinceIgnited;
			int var1 = this.getCreeperState();

			if (var1 > 0 && this.timeSinceIgnited == 0)
			{
				this.worldObj.playSoundAtEntity(this, "random.fuse", 1.0F, 0.5F);
			}

	//		System.out.println(timeSinceIgnited);

			this.timeSinceIgnited += var1;

			if (this.timeSinceIgnited < 0)
			{
				this.timeSinceIgnited = 0;
			}

			if (this.timeSinceIgnited >= 30)
			{
				this.timeSinceIgnited = 30;

				if (!this.worldObj.isRemote)
				{
					if (this.getPowered())
					{
						//         this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 6.0F);
						onDeathBySelf();
					}
					else
					{
						//        this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 3.0F);
						onDeathBySelf();
					}

					this.setDead();
				}
			}
		}

		super.onUpdate();
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	protected String getHurtSound()
	{
		return "mob.creeper";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	protected String getDeathSound()
	{
		return "mob.creeperdeath";
	}

	/**
	 * Called when the mob's health reaches 0.
	 */
	public void onDeath(DamageSource par1DamageSource)
	{
		super.onDeath(par1DamageSource);

		if (par1DamageSource.getEntity() instanceof EntitySkeleton)
		{
			this.dropItem(TropicraftMod.froxEasternIsles.shiftedIndex - rand.nextInt(1), 1);
		} else
			if(par1DamageSource.getEntity() instanceof EntityPlayer)
			{
				this.dropItem(TropicraftMod.coconutBomb.shiftedIndex, rand.nextInt(3) + 1);
				int y = worldObj.getHeightValue((int)posX, (int)posZ);
				int xo = rand.nextInt(3) + 4;
				int zo = xo + rand.nextInt(3) - (new Random()).nextInt(3);
				for(int x = (int)posX - xo; x < (int)posX + xo; x++)
				{
					for(int z = (int)posZ - zo; z < (int)posZ + zo; z++)
					{
						y = worldObj.getHeightValue(x, z);
						
						if(worldObj.getBlockMaterial(x, y-1, z) != Material.water && worldObj.getBlockId(x,y-1, z) != 0)
						worldObj.setBlockAndMetadata(x, y, z, TropicraftMod.flowerCollection1.blockID, rand.nextInt(7));
					}
				}
			}
	}

    public void onDeathBySelf() {

        this.dropItem(TropicraftMod.coconutBomb.shiftedIndex, rand.nextInt(3) + 1);
        int y = (int)posY + 3;
        int xo = rand.nextInt(3) + 2;
        int zo = rand.nextInt(3) + 2;
        for (int x = (int) posX - xo; x < (int) posX + xo; x++) {
            for (int z = (int) posZ - zo; z < (int) posZ + zo; z++) {
                if (rand.nextInt(3) == 0) {
                    continue;
                }
                for (y = (int) posY + 3; y > (int) posY - 3; y--) {
                    if (worldObj.isAirBlock(x, y, z) && worldObj.isBlockOpaqueCube(x, y - 1, z)) {
                        break;
                    }
                }
                if (y == (int) posY - 3) {
                    continue;
                }
                if (rand.nextInt(10) < 6 && worldObj.getBlockId(x, y, z) != TropicraftMod.tropicalWood.blockID && worldObj.getBlockId(x, y, z) != TropicraftMod.coconut.blockID) {
                    int flowerType = rand.nextInt(7);
                    if (TropicraftMod.flowerCollection1.canPlaceBlockAt(worldObj, x, y, z)) {
                        worldObj.setBlockAndMetadataWithNotify(x, y, z, TropicraftMod.flowerCollection1.blockID, flowerType);
                    } else {
                        this.entityDropItem(new ItemStack(TropicraftMod.flowerCollection1.blockID, 1, flowerType), 0.5F);
                    }
                } else if (rand.nextInt(10) < 7 && worldObj.getBlockId(x, y, z) != TropicraftMod.tropicalWood.blockID && worldObj.getBlockId(x, y, z) != TropicraftMod.coconut.blockID && worldObj.getBlockId(x, y - 1, z) == Block.grass.blockID) {
                    worldObj.setBlockAndMetadataWithNotify(x, y, z, Block.tallGrass.blockID, 1);
                } else if (rand.nextInt(10) < 8 && (worldObj.getBlockId(x, y - 1, z) != TropicraftMod.tropicLeaves.blockID && worldObj.getBlockId(x, y - 1, z) != 0)) {
                    
                    int palmTreeType = rand.nextInt(3);
                    WorldGenerator gen = null;
                    if (palmTreeType == 0) {
                        gen = new WorldGenTropicraftLargePalmTrees(true);
                    } else if (palmTreeType == 1) {
                        gen = new WorldGenTropicraftCurvedPalm(true);
                    } else if (palmTreeType == 2) {
                        gen = new WorldGenTropicraftNormalPalms(true);
                    }
                    gen.generate(worldObj, rand, x, y, z);

                } else {
                    //do nothing :D
                }

            }
        }

    }

	public boolean attackEntityAsMob(Entity par1Entity)
	{
		return true;
	}

	/**
	 * Returns true if the creeper is powered by a lightning bolt.
	 */
	 public boolean getPowered()
	{
		 return this.dataWatcher.getWatchableObjectByte(17) == 1;
	}

	 /**
	  * Connects the the creeper flashes to the creeper's color multiplier
	  */
	 public float setCreeperFlashTime(float par1)
	 {
		 return ((float)this.lastActiveTime + (float)(this.timeSinceIgnited - this.lastActiveTime) * par1) / 28.0F;
	 }

	 /**
	  * Returns the item ID for the item the mob drops on death.
	  */
	 protected int getDropItemId()
	 {
		 return TropicraftMod.coconutBomb.shiftedIndex;
	 }

	 /**
	  * Returns the current state of creeper, -1 is idle, 1 is 'in fuse'
	  */
	 public int getCreeperState()
	 {
		 return this.dataWatcher.getWatchableObjectByte(16);
	 }

	 /**
	  * Sets the state of creeper, -1 to idle and 1 to be 'in fuse'
	  */
	 public void setCreeperState(int par1)
	 {
		 this.dataWatcher.updateObject(16, Byte.valueOf((byte)par1));
	 }

	 /**
	  * Called when a lightning bolt hits the entity.
	  */
	 public void onStruckByLightning(EntityLightningBolt par1EntityLightningBolt)
	 {
		 super.onStruckByLightning(par1EntityLightningBolt);
		 this.dataWatcher.updateObject(17, Byte.valueOf((byte)1));
	 }
}
