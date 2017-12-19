package net.tropicraft.blocks.tileentities;

import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityChest;
import net.tropicraft.mods.TropicraftMod;

public class TileEntityBambooChest extends TileEntityChest
    implements IInventory
{
    
    private boolean unbreakable = false;

    public TileEntityBambooChest()
    {
        super();
    }

    public String getInvName()
    {
        return "Bamboo chest";
    }

    public void checkForAdjacentChests()
    {
        if (adjacentChestChecked)
        {
            return;
        }
        adjacentChestChecked = true;
        adjacentChestZNeg = null;
        adjacentChestXPos = null;
        adjacentChestXNeg = null;
        adjacentChestZPosition = null;
        if (worldObj.getBlockId(xCoord - 1, yCoord, zCoord) == TropicraftMod.bambooChest.blockID)
        {
            adjacentChestXNeg = (TileEntityBambooChest)worldObj.getBlockTileEntity(xCoord - 1, yCoord, zCoord);
        }
        if (worldObj.getBlockId(xCoord + 1, yCoord, zCoord) == TropicraftMod.bambooChest.blockID)
        {
            adjacentChestXPos = (TileEntityBambooChest)worldObj.getBlockTileEntity(xCoord + 1, yCoord, zCoord);
        }
        if (worldObj.getBlockId(xCoord, yCoord, zCoord - 1) == TropicraftMod.bambooChest.blockID)
        {
            adjacentChestZNeg = (TileEntityBambooChest)worldObj.getBlockTileEntity(xCoord, yCoord, zCoord - 1);
        }
        if (worldObj.getBlockId(xCoord, yCoord, zCoord + 1) == TropicraftMod.bambooChest.blockID)
        {
            adjacentChestZPosition = (TileEntityBambooChest)worldObj.getBlockTileEntity(xCoord, yCoord, zCoord + 1);
        }
        if (adjacentChestZNeg != null)
        {
            adjacentChestZNeg.updateContainingBlockInfo();
        }
        if (adjacentChestZPosition != null)
        {
            adjacentChestZPosition.updateContainingBlockInfo();
        }
        if (adjacentChestXPos != null)
        {
            adjacentChestXPos.updateContainingBlockInfo();
        }
        if (adjacentChestXNeg != null)
        {
            adjacentChestXNeg.updateContainingBlockInfo();
        }
    }
    
    public boolean isUnbreakable() {
        return unbreakable;
    }
    
    public void setIsUnbreakable(boolean flag) {
        unbreakable = flag;
    }
    
    @Override
    public void openChest()
    {
        ++this.numUsingPlayers;
        this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, TropicraftMod.bambooChest.blockID, 1, this.numUsingPlayers);
    }

    @Override
    public void closeChest()
    {
        --this.numUsingPlayers;
        this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, TropicraftMod.bambooChest.blockID, 1, this.numUsingPlayers);
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
    
    
}
