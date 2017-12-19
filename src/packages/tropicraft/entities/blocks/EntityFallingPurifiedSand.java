package tropicraft.entities.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityFallingPurifiedSand extends Entity implements IEntityAdditionalSpawnData
{

    public int blockID;
    public int metadata;
    /** How long the block has been falling for. */
    public int fallTime = 0;

    public EntityFallingPurifiedSand(World world)
    {
        super(world);
        this.preventEntitySpawning = true;
        this.setSize(0.98F, 0.98F);
        this.yOffset = this.height / 2.0F;
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
    }

    public EntityFallingPurifiedSand(World world, double i, double j, double k, int blockID, int metadata)
    {
        super(world);
        this.blockID = blockID;
        this.metadata = metadata;
        this.preventEntitySpawning = true;
        this.setSize(0.98F, 0.98F);
        this.yOffset = this.height / 2.0F;
        this.setPosition(i, j, k);
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
        this.prevPosX = i;
        this.prevPosY = j;
        this.prevPosZ = k;
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    @Override
    protected boolean canTriggerWalking()
    {
        return false;
    }

    @Override
    protected void entityInit()
    {
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    @Override
    public boolean canBeCollidedWith()
    {
        return !this.isDead;
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onUpdate()
    {
        if(this.blockID == 0) {
            this.setDead();
        } else {
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            ++this.fallTime;
            this.motionY -= 0.03999999910593033D;
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.9800000190734863D;
            this.motionY *= 0.9800000190734863D;
            this.motionZ *= 0.9800000190734863D;
            int var1 = MathHelper.floor_double(this.posX);
            int var2 = MathHelper.floor_double(this.posY);
            int var3 = MathHelper.floor_double(this.posZ);

            if(this.fallTime == 1 && this.worldObj.getBlockId(var1, var2, var3) == this.blockID) {
                this.worldObj.setBlockToAir(var1, var2, var3);
            } else if(!this.worldObj.isRemote && this.fallTime == 1) {
                this.setDead();
            }

            if(this.onGround) {
                this.motionX *= 0.699999988079071D;
                this.motionZ *= 0.699999988079071D;
                this.motionY *= -0.5D;

                if(this.worldObj.getBlockId(var1, var2, var3) != Block.pistonMoving.blockID) {
                    this.setDead();

                    if((!this.worldObj.canPlaceEntityOnSide(this.blockID, var1, var2, var3, true, 1, (Entity)null, new ItemStack(Block.blocksList[blockID])) || BlockSand.canFallBelow(this.worldObj, var1, var2 - 1, var3) || !this.worldObj.setBlock(var1, var2, var3, this.blockID, this.metadata, 3)) && !this.worldObj.isRemote) {
                        this.entityDropItem(new ItemStack(blockID, 1, metadata), 0);
                    }
                }
            } else if(this.fallTime > 100 && !this.worldObj.isRemote && (var2 < 1 || var2 > 256) || this.fallTime > 600) {
                this.entityDropItem(new ItemStack(blockID, 1, metadata), 0);
                this.setDead();
            }
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    @Override
    protected void writeEntityToNBT(NBTTagCompound data)
    {
        data.setInteger("blockID", this.blockID);
        data.setInteger("metadata", this.metadata);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    protected void readEntityFromNBT(NBTTagCompound data)
    {
        this.blockID = data.getInteger("blockID");
        this.metadata = data.getInteger("metadata");
    }

    @Override
    public float getShadowSize()
    {
        return 0.0F;
    }

    public World getWorld()
    {
        return this.worldObj;
    }

    @Override
    public void writeSpawnData(ByteArrayDataOutput data)
    {
        data.writeInt(blockID);
        data.writeInt(metadata);
    }

    @Override
    public void readSpawnData(ByteArrayDataInput data)
    {
        blockID = data.readInt();
        metadata = data.readInt();
    }
}