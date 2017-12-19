package tropicraft.entities.passive.water;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import tropicraft.items.TropicraftItems;

public class EntitySeaTurtle extends EntityAmphibian {

    public EntitySeaTurtle(World world) {
        super(world);
        setSize(0.3f, 0.3f);
        this.experienceValue = 5;

    }
    public EntitySeaTurtle(World world, float i ){
        super(world, i);
        setSize(0.3f, 0.3f);
        this.experienceValue = 5;
    }

    @Override
    public boolean canDespawn()
    {
        return true;
    }

    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        if(onGround && getAmphibianAge() >= 1 && returnToLand){

            if(worldObj.getBlockId((int)(this.posX), (int)(this.posY) - 1 , (int)(this.posZ)) == Block.sand.blockID){
                if(rand.nextInt(600) == 0){
                    EntityTurtleEgg entityEgg = new EntityTurtleEgg(worldObj);
                    double d3 = this.posX;
                    double d4 = this.posY;
                    double d5 = this.posZ;
                    entityEgg.setLocationAndAngles(d3, d4, d5, 0.0F, 0.0F);
                    worldObj.spawnEntityInWorld(entityEgg);
                    returnToLand = false;
                }

            }

        }
    }

    public double getMountedYOffset()
    {
        return (double)height * 0.75D - 1F + 0.7F;
    }

    protected void entityInit()
    {
        super.entityInit();
        dataWatcher.addObject(30, Byte.valueOf((byte)0));
    }

    public boolean getSaddled()
    {
        return (dataWatcher.getWatchableObjectByte(30) & 1) != 0;
    }

    public void setSaddled(boolean flag)
    {
        if (flag)
        {
            dataWatcher.updateObject(30, Byte.valueOf((byte)1));
        }
        else
        {
            dataWatcher.updateObject(30, Byte.valueOf((byte)0));
        }
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("Saddle", getSaddled());
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        setSaddled(nbttagcompound.getBoolean("Saddle"));
    }

    public boolean interact(EntityPlayer entityplayer)
    {
        if (!super.interact(entityplayer))
        {
            if (getAmphibianAge() >= 1 && !getSaddled() && !worldObj.isRemote && (riddenByEntity == null || riddenByEntity == entityplayer))
            {
                entityplayer.mountEntity(this);
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return true;
        }
    }
    
    @Override
	public void onDeath(DamageSource d) {
		super.onDeath(d);
		if (!this.worldObj.isRemote) {
			this.entityDropItem(new ItemStack(TropicraftItems.shells.itemID, 1, 5), 0);
		}
	}
    
    @Override
	protected int getDropItemId() {
		return TropicraftItems.shells.itemID;
	};

}