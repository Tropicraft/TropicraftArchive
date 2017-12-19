package tropicraft.blocks.tileentities;

import cpw.mods.fml.common.network.PacketDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class TileEntityBambooMug extends TileEntity {
	public ItemStack cocktail;
	
	@Override
	public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
		super.readFromNBT(par1nbtTagCompound);
		if (par1nbtTagCompound.hasKey("Cocktail")) {
			this.cocktail = ItemStack.loadItemStackFromNBT(par1nbtTagCompound.getCompoundTag("Cocktail"));
		} else {
			this.cocktail = null;
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound par1nbtTagCompound) {
		super.writeToNBT(par1nbtTagCompound);
		if (this.cocktail != null) {
			NBTTagCompound nbt = new NBTTagCompound();
			this.cocktail.writeToNBT(nbt);
			par1nbtTagCompound.setCompoundTag("Cocktail", nbt);
		}
	}
	
	public void sync() {
		PacketDispatcher.sendPacketToAllInDimension(getDescriptionPacket(), worldObj.provider.dimensionId);
	}

	@Override
	public Packet getDescriptionPacket() {
        NBTTagCompound var1 = new NBTTagCompound();
        this.writeToNBT(var1);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, var1);
	}

	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
    	this.readFromNBT(pkt.customParam1);
	}
	
	public boolean isEmpty() {
		return this.cocktail == null;
	}

	public void setCocktail(ItemStack cocktail) {
		this.cocktail = cocktail;
		this.sync();
	}
	
	public int getMetadata() {
		return worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
	}
}
