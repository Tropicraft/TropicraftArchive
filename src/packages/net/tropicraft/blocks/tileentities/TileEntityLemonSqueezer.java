package net.tropicraft.blocks.tileentities;

import cpw.mods.fml.common.network.PacketDispatcher;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.tropicraft.blocks.BlockLemonSqueezer.FruitType;

public class TileEntityLemonSqueezer extends TileEntity {
	public static final int ticksToSqueeze = 4*20;
	public FruitType fruitType;
	public int ticks;

	public TileEntityLemonSqueezer() {
	}

	@Override
	public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
		super.readFromNBT(par1nbtTagCompound);
		this.ticks = par1nbtTagCompound.getInteger("SqueezeTicks");
		
		if (par1nbtTagCompound.hasKey("FruitType")) {
			this.fruitType = FruitType.values()[par1nbtTagCompound.getByte("FruitType")];
		} else {
			this.fruitType = null;
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound par1nbtTagCompound) {
		super.writeToNBT(par1nbtTagCompound);
		par1nbtTagCompound.setInteger("SqueezeTicks", this.ticks);
		
	 	if (this.fruitType != null) {
	 		par1nbtTagCompound.setByte("FruitType", (byte)fruitType.ordinal());
	 	}
 	}

	@Override
	public void updateEntity() {
		if (this.ticks < ticksToSqueeze) {
			ticks++;
		}
	}
	
	public void startSqueezing(FruitType type) {
		this.fruitType = type;
		this.ticks = 0;
		this.sync();
	}
	
	public void emptySqueezer() {
		this.fruitType = null;
		this.ticks = ticksToSqueeze;
		this.sync();
	}
	
	public boolean isSqueezing() {
		return this.fruitType != null;
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

	public boolean isDoneSqueezing() {
		return fruitType != null && ticks == ticksToSqueeze;
	}

}
