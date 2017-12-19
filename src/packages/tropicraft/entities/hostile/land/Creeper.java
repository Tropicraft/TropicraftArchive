package tropicraft.entities.hostile.land;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import tropicraft.blocks.TropicraftBlocks;
import tropicraft.entities.hostile.EntityLand;
import tropicraft.items.TropicraftItems;
import CoroAI.componentAI.AIAgent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Creeper extends EntityLand implements IMob {

	public AIAgent agent;
	
	public Creeper(World par1World) {
		super(par1World);
		
		texture = "/mods/TropicraftMod/textures/entities/creeper.png";
		if (agent == null) agent = new AIAgent(this, false);
		
		agent.moveLeadTicks = 0;
		agent.shouldAvoid = false;
		
		agent.setMoveSpeed(0.28F);
	}
	
	public boolean getCanSpawnHere()
    {
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(this.boundingBox.minY);
        int k = MathHelper.floor_double(this.posZ);
        return this.worldObj.getBlockId(i, j - 1, k) != 0 && this.worldObj.getFullBlockLightValue(i, j, k) > 8 && super.getCanSpawnHere();
    }
    
    @Override
	public void attackMelee(Entity ent, float dist) {
		setCreeperState(1);
	}
	
	//CREEPER CODE COPY START
	
	/**
     * Time when this creeper was last in an active state (Messed up code here, probably causes creeper animation to go
     * weird)
     */
    private int lastActiveTime;

    /**
     * The amount of time since the creeper was close enough to the player to ignite
     */
    private int timeSinceIgnited;
    private int fuseTime = 30;

    /** Explosion radius for this creeper. */
    private int explosionRadius = 3;

    /**
     * Returns true if the newer Entity AI code should be run
     */
    public boolean isAIEnabled()
    {
        return true;
    }

    public int func_82143_as()
    {
        return this.getAttackTarget() == null ? 3 : 3 + (this.health - 1);
    }

    /**
     * Called when the mob is falling. Calculates and applies fall damage.
     */
    protected void fall(float par1)
    {
        super.fall(par1);
        this.timeSinceIgnited = (int)((float)this.timeSinceIgnited + par1 * 1.5F);

        if (this.timeSinceIgnited > this.fuseTime - 5)
        {
            this.timeSinceIgnited = this.fuseTime - 5;
        }
    }

    public int getMaxHealth()
    {
        return 20;
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

        par1NBTTagCompound.setShort("Fuse", (short)this.fuseTime);
        par1NBTTagCompound.setByte("ExplosionRadius", (byte)this.explosionRadius);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.dataWatcher.updateObject(17, Byte.valueOf((byte)(par1NBTTagCompound.getBoolean("powered") ? 1 : 0)));

        if (par1NBTTagCompound.hasKey("Fuse"))
        {
            this.fuseTime = par1NBTTagCompound.getShort("Fuse");
        }

        if (par1NBTTagCompound.hasKey("ExplosionRadius"))
        {
            this.explosionRadius = par1NBTTagCompound.getByte("ExplosionRadius");
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        if (this.isEntityAlive())
        {
            this.lastActiveTime = this.timeSinceIgnited;
            int i = this.getCreeperState();

            if (i > 0 && this.timeSinceIgnited == 0)
            {
                this.playSound("random.fuse", 1.0F, 0.5F);
            }

            this.timeSinceIgnited += i;

            if (this.timeSinceIgnited < 0)
            {
                this.timeSinceIgnited = 0;
            }

            if (this.timeSinceIgnited >= this.fuseTime)
            {
                this.timeSinceIgnited = this.fuseTime;

                if (!this.worldObj.isRemote)
                {
                    boolean flag = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
                    
                    onDeathBySelf();

                    this.setDead();
                }
            }
        }

        super.onUpdate();
    }
    
    /**
	 * Called when the mob's health reaches 0.
	 */
    @Override
	public void onDeath(DamageSource par1DamageSource)
	{
		super.onDeath(par1DamageSource);

		if (par1DamageSource.getEntity() instanceof EntitySkeleton)
		{
			this.dropItem(TropicraftItems.recordEasternIsles.itemID - rand.nextInt(1), 1);
		} else
			if(par1DamageSource.getEntity() instanceof EntityPlayer)
			{
				this.dropItem(TropicraftItems.coconutBomb.itemID, rand.nextInt(3) + 1);
				int y = worldObj.getHeightValue((int)posX, (int)posZ);
				int xo = rand.nextInt(3) + 4;
				int zo = xo + rand.nextInt(3) - (new Random()).nextInt(3);
				for(int x = (int)posX - xo; x < (int)posX + xo; x++)
				{
					for(int z = (int)posZ - zo; z < (int)posZ + zo; z++)
					{
						y = worldObj.getHeightValue(x, z);
						
						if(worldObj.getBlockMaterial(x, y-1, z) != Material.water && worldObj.getBlockId(x,y-1, z) != 0)
						worldObj.setBlock(x, y, z, TropicraftBlocks.tropicsFlowers.blockID, rand.nextInt(7), 3);
					}
				}
			}
	}

    public void onDeathBySelf() {

        this.dropItem(TropicraftItems.coconutBomb.itemID, rand.nextInt(3) + 1);
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
                if (rand.nextInt(10) < 6 && worldObj.getBlockId(x, y, z) != TropicraftBlocks.bambooBundle.blockID && worldObj.getBlockId(x, y, z) != TropicraftBlocks.coconut.blockID) {
                    int flowerType = rand.nextInt(7);
                    if (TropicraftBlocks.tropicsFlowers.canPlaceBlockAt(worldObj, x, y, z)) {
                        worldObj.setBlock(x, y, z, TropicraftBlocks.tropicsFlowers.blockID, flowerType, 3);
                    } else {
                        this.entityDropItem(new ItemStack(TropicraftBlocks.tropicsFlowers.blockID, 1, flowerType), 0.5F);
                    }
                } else if (rand.nextInt(10) < 7 && worldObj.getBlockId(x, y, z) != TropicraftBlocks.bambooBundle.blockID && worldObj.getBlockId(x, y, z) != TropicraftBlocks.coconut.blockID && worldObj.getBlockId(x, y - 1, z) == Block.grass.blockID) {
                    worldObj.setBlock(x, y, z, Block.tallGrass.blockID, 1, 3);
                } else if (rand.nextInt(10) < 8 && (worldObj.getBlockId(x, y - 1, z) != TropicraftBlocks.bambooBundle.blockID && worldObj.getBlockId(x, y - 1, z) != 0)) {
                    
                    int palmTreeType = rand.nextInt(3);
                    WorldGenerator gen = null;
                    /*if (palmTreeType == 0) {
                        gen = new WorldGenTropicraftLargePalmTrees(true);
                    } else if (palmTreeType == 1) {
                        gen = new WorldGenTropicraftCurvedPalm(true);
                    } else if (palmTreeType == 2) {
                        gen = new WorldGenTropicraftNormalPalms(true);
                    }
                    gen.generate(worldObj, rand, x, y, z);*/

                } else {
                    //do nothing :D
                }

            }
        }

    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "mob.creeper.say";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "mob.creeper.death";
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

    @SideOnly(Side.CLIENT)

    /**
     * Params: (Float)Render tick. Returns the intensity of the creeper's flash when it is ignited.
     */
    public float getCreeperFlashIntensity(float par1)
    {
        return ((float)this.lastActiveTime + (float)(this.timeSinceIgnited - this.lastActiveTime) * par1) / (float)(this.fuseTime - 2);
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getDropItemId()
    {
        return Item.gunpowder.itemID;
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
    
    //CREEPER CODE COPY END

	@Override
	public void updateAITasks() {
		super.updateAITasks();
		//agent.updateAITasks();
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		
        this.dataWatcher.addObject(16, Byte.valueOf((byte) - 1));
        this.dataWatcher.addObject(17, Byte.valueOf((byte)0));
    }

}
