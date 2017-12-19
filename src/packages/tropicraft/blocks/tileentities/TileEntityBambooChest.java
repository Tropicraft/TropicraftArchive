package tropicraft.blocks.tileentities;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityChest;
import tropicraft.blocks.BlockBambooChest;

public class TileEntityBambooChest extends TileEntityChest {

	/** Is this chest unbreakble (Koa chest) */
    private boolean unbreakable = false;

    public TileEntityBambooChest() {
        super();
    }

    @Override
    public String getInvName() {
        return "Bamboo chest";
    }
	
    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        unbreakable = nbttagcompound.getBoolean("unbreakable");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setBoolean("unbreakable", unbreakable);
    }
    
    /**
     * Performs the check for adjacent chests to determine if this chest is double or not.
     */
    @Override
    public void checkForAdjacentChests()
    {
        if (!this.adjacentChestChecked)
        {
            this.adjacentChestChecked = true;
            this.adjacentChestZNeg = null;
            this.adjacentChestXPos = null;
            this.adjacentChestXNeg = null;
            this.adjacentChestZPosition = null;

            if (this.func_94044_a(this.xCoord - 1, this.yCoord, this.zCoord))
            {
                this.adjacentChestXNeg = (TileEntityBambooChest)this.worldObj.getBlockTileEntity(this.xCoord - 1, this.yCoord, this.zCoord);
            }

            if (this.func_94044_a(this.xCoord + 1, this.yCoord, this.zCoord))
            {
                this.adjacentChestXPos = (TileEntityBambooChest)this.worldObj.getBlockTileEntity(this.xCoord + 1, this.yCoord, this.zCoord);
            }

            if (this.func_94044_a(this.xCoord, this.yCoord, this.zCoord - 1))
            {
                this.adjacentChestZNeg = (TileEntityBambooChest)this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord - 1);
            }

            if (this.func_94044_a(this.xCoord, this.yCoord, this.zCoord + 1))
            {
                this.adjacentChestZPosition = (TileEntityBambooChest)this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord + 1);
            }

            if (this.adjacentChestZNeg != null)
            {
                this.adjacentChestZNeg.updateContainingBlockInfo();
            }

            if (this.adjacentChestZPosition != null)
            {
                this.adjacentChestZPosition.updateContainingBlockInfo();
            }

            if (this.adjacentChestXPos != null)
            {
                this.adjacentChestXPos.updateContainingBlockInfo();
            }

            if (this.adjacentChestXNeg != null)
            {
                this.adjacentChestXNeg.updateContainingBlockInfo();
            }
        }
    }
    
    private void func_90009_a(TileEntityBambooChest par1TileEntityChest, int par2)
    {
        if (par1TileEntityChest.isInvalid())
        {
            this.adjacentChestChecked = false;
        }
        else if (this.adjacentChestChecked)
        {
            switch (par2)
            {
                case 0:
                    if (this.adjacentChestZPosition != par1TileEntityChest)
                    {
                        this.adjacentChestChecked = false;
                    }

                    break;
                case 1:
                    if (this.adjacentChestXNeg != par1TileEntityChest)
                    {
                        this.adjacentChestChecked = false;
                    }

                    break;
                case 2:
                    if (this.adjacentChestZNeg != par1TileEntityChest)
                    {
                        this.adjacentChestChecked = false;
                    }

                    break;
                case 3:
                    if (this.adjacentChestXPos != par1TileEntityChest)
                    {
                        this.adjacentChestChecked = false;
                    }
            }
        }
    }
    
    private boolean func_94044_a(int par1, int par2, int par3)
    {
        Block block = Block.blocksList[this.worldObj.getBlockId(par1, par2, par3)];
        return block != null && block instanceof BlockBambooChest ? ((BlockBambooChest)block).isTrapped == this.func_98041_l() : false;
    }
    
    /**
     * 
     * @return Returns if this chest is unbreakable
     */
    public boolean isUnbreakable() {
        return unbreakable;
    }
    
    /**
     * Sets whether this chest is unbreakable or not
     * @param flag Value to set the unbreakable flag to
     */
    public void setIsUnbreakable(boolean flag) {
        unbreakable = flag;
    }
}
