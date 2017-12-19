package tropicraft.entities.items;

import java.util.Iterator;
import java.util.List;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import tropicraft.blocks.TropicraftBlocks;
import tropicraft.items.TropicraftItems;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityTCItemFrame extends EntityItemFrame implements IEntityAdditionalSpawnData {

	/**
	 * Chance for this item frame's item to drop from the frame.
	 */
	private float itemDropChance = 1.0F;

	public EntityTCItemFrame(World world) {
		super(world);
		setShouldDropContents(false);
	}

	public EntityTCItemFrame(World par1World, int par2, int par3, int par4, int par5, boolean shouldDropContents) {
		super(par1World, par2, par3, par4, par5);
		setShouldDropContents(shouldDropContents);
	}
	
	@Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
    }
	
	@Override
    public void dropItemStack() {

        if (!getShouldDropContents()) {
            this.entityDropItem(new ItemStack(TropicraftItems.koaFrame), 0.0F);    //drop only item frame
        } else {
            this.entityDropItem(new ItemStack(TropicraftItems.tropiFrame), 0.0F);    //drop only item frame

            ItemStack var1 = this.getDisplayedItem();

            if (var1 != null && this.rand.nextFloat() < this.itemDropChance)
            {
                var1 = var1.copy();
                var1.setItemFrame((EntityItemFrame)null);
                this.entityDropItem(var1, 0.0F);
            }
        }
    }
	
	 /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        if (this.getDisplayedItem() != null)
        {
            par1NBTTagCompound.setCompoundTag("Item", this.getDisplayedItem().writeToNBT(new NBTTagCompound()));
            par1NBTTagCompound.setByte("ItemRotation", (byte)this.getRotation());
            par1NBTTagCompound.setFloat("ItemDropChance", this.itemDropChance);
            par1NBTTagCompound.setBoolean("ShouldDropContents", (boolean)this.getShouldDropContents());
        }

        super.writeEntityToNBT(par1NBTTagCompound);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        NBTTagCompound var2 = par1NBTTagCompound.getCompoundTag("Item");

        if (var2 != null && !var2.hasNoTags())
        {
            this.setDisplayedItem(ItemStack.loadItemStackFromNBT(var2));
            this.setItemRotation(par1NBTTagCompound.getByte("ItemRotation"));
            this.setShouldDropContents(par1NBTTagCompound.getBoolean("ShouldDropContents"));

            if (par1NBTTagCompound.hasKey("ItemDropChance"))
            {
                this.itemDropChance = par1NBTTagCompound.getFloat("ItemDropChance");
            }
        }

        super.readEntityFromNBT(par1NBTTagCompound);
    }
    
    /**
     * Return the rotation of the item currently on this frame.
     */
    public boolean getShouldDropContents()
    {
        return this.getDataWatcher().getWatchableObjectByte(16) == 1;
    }


    public void setShouldDropContents(boolean par1)
    {
        this.dataWatcher.updateObject(16, par1 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0));
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
   //     System.err.println("i r here");
    }

    /**
     * checks to make sure painting can be placed there
     */
    public boolean onValidSurface()
    {
        if (!this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty())
        {
            System.err.println("invalid surface");
            return false;
        }
        else
        {
            int var1 = Math.max(1, this.func_82329_d() / 16);
            int var2 = Math.max(1, this.func_82330_g() / 16);
            int var3 = this.xPosition;
            int var4 = this.yPosition;
            int var5 = this.zPosition;

            if (this.hangingDirection == 2)
            {
                var3 = MathHelper.floor_double(this.posX - (double)((float)this.func_82329_d() / 32.0F));
            }

            if (this.hangingDirection == 1)
            {
                var5 = MathHelper.floor_double(this.posZ - (double)((float)this.func_82329_d() / 32.0F));
            }

            if (this.hangingDirection == 0)
            {
                var3 = MathHelper.floor_double(this.posX - (double)((float)this.func_82329_d() / 32.0F));
            }

            if (this.hangingDirection == 3)
            {
                var5 = MathHelper.floor_double(this.posZ - (double)((float)this.func_82329_d() / 32.0F));
            }

            var4 = MathHelper.floor_double(this.posY - (double)((float)this.func_82330_g() / 32.0F));

            for (int var6 = 0; var6 < var1; ++var6)
            {
                for (int var7 = 0; var7 < var2; ++var7)
                {
                    Material var8;
                    Block blawk;

                    if (this.hangingDirection != 2 && this.hangingDirection != 0)
                    {
                        var8 = this.worldObj.getBlockMaterial(this.xPosition, var4 + var7, var5 + var6);
                        blawk = Block.blocksList[this.worldObj.getBlockId(this.xPosition, var4 + var7, var5 + var6)];
                    }
                    else
                    {
                        var8 = this.worldObj.getBlockMaterial(var3 + var6, var4 + var7, this.zPosition);
                        blawk = Block.blocksList[this.worldObj.getBlockId(var3 + var6, var4 + var7, this.zPosition)];
                    }

                    if (!var8.isSolid() && blawk != null && !(blawk.blockID == TropicraftBlocks.bambooChute.blockID))
                    {
                        System.err.println("invalid surface2");
                        return false;
                    }
                }
            }

            List var9 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox);
            Iterator var10 = var9.iterator();
            Entity var11;

            do
            {
                if (!var10.hasNext())
                {
                    return true;
                }

                var11 = (Entity)var10.next();
            }
            while (!(var11 instanceof EntityHanging));


            System.err.println("invalid surface3");
            return false;
        }
    }

	@Override
	public void writeSpawnData(ByteArrayDataOutput data) {
		data.writeInt(xPosition);
		data.writeInt(yPosition);
		data.writeInt(zPosition);	
		data.writeByte(hangingDirection);
		data.writeBoolean(getShouldDropContents());
	}

	@Override
	public void readSpawnData(ByteArrayDataInput data) {
		xPosition = data.readInt();
		yPosition = data.readInt();
		zPosition = data.readInt();
		setDirection(data.readByte());
		setShouldDropContents(data.readBoolean());
	}
}
