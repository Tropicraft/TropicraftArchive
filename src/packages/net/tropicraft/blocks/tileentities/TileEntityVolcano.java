package net.tropicraft.blocks.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.tropicraft.VolcanoEvent;
import net.tropicraft.packets.TropicraftPacketHandler;

import java.util.Random;

public class TileEntityVolcano extends TileEntity {

	public int timeUntil = 6000;
	public boolean erupting = false;
	public boolean dormant = false;
	public VolcanoEvent event;
	public Random rand;
	
	//for packets
	public int[] intData;

	public boolean[] booleanData;
	
	public TileEntityVolcano()
	{
		intData = new int[4];
		booleanData = new boolean[4];
	}
	
	@Override
	public void updateEntity()
	{	
		if(!dormant)
		{
			if(event != null && event.worldObj == null && !worldObj.isRemote)
			{
				event.setWorld(worldObj);
			}
			
			if(!erupting)
				timeUntil--;
			
			if(!erupting && timeUntil < 0/* && new Random().nextInt(600) == 0*/ && !VolcanoEvent.erupting)
			{
				event = new VolcanoEvent(worldObj, xCoord, zCoord);
				erupting = true;
			}
			
			if(erupting)
			{
			    //System.out.println(xCoord + ", " + zCoord);
				if(!event.update())
				{
					if(new Random().nextInt(3) == 0)
					{
						dormant = true;
					}
					else
					{
						erupting = false;
						event = null;
						timeUntil = new Random().nextInt(3000) + 5000;
					}
				}
			}
		}
	}
	
	//packet method
	public void handlePacketData(int[] intData, boolean[] booleanData) {
		if (intData.length != 4 || booleanData.length != 4) {
			throw new IllegalArgumentException("Volcano data is incorrect size!");
		}
		
		erupting = booleanData[0];
		
		if (erupting) {
			event = new VolcanoEvent(xCoord, zCoord);
			event.falling = booleanData[1];
			event.lavaLevel = intData[0];
			event.radius = intData[1];
			event.time = intData[2];
			event.erupted = booleanData[2];
		}
		dormant = booleanData[3];
		timeUntil = intData[3];
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		erupting = nbttagcompound.getBoolean("erupting");
		if(erupting)
		{
			event = new VolcanoEvent(xCoord, zCoord);
			event.falling = nbttagcompound.getBoolean("falling");
			event.lavaLevel = nbttagcompound.getInteger("lavalevel");
			event.radius = nbttagcompound.getInteger("radius");
			event.time = nbttagcompound.getInteger("time");
			event.erupted = nbttagcompound.getBoolean("erupted");
		}
		timeUntil = nbttagcompound.getInteger("timeuntil");
		dormant = nbttagcompound.getBoolean("dormant");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setBoolean("erupting", erupting);
		booleanData[0] = nbttagcompound.getBoolean("erupting");
		nbttagcompound.setBoolean("dormant", dormant);
		booleanData[3] = nbttagcompound.getBoolean("dormant");
		if(event != null)
		{
			nbttagcompound.setBoolean("falling", event.falling);
			
			nbttagcompound.setBoolean("erupted", event.erupted);
	
			nbttagcompound.setInteger("lavalevel", event.lavaLevel);

			nbttagcompound.setInteger("radius", event.radius);

			nbttagcompound.setInteger("time", event.time);
		
		}
		else
		{
			nbttagcompound.setBoolean("falling", false);
			nbttagcompound.setBoolean("erupted", false);
			nbttagcompound.setInteger("lavalevel", 0);
			nbttagcompound.setInteger("radius", 0);
			nbttagcompound.setInteger("time", 0);
		}
		
		booleanData[1] = nbttagcompound.getBoolean("falling");
		booleanData[2] = nbttagcompound.getBoolean("erupted");
		intData[0] = nbttagcompound.getInteger("lavalevel");
		intData[1] = nbttagcompound.getInteger("radius");
		intData[2] = nbttagcompound.getInteger("time");
		
		nbttagcompound.setInteger("timeuntil", timeUntil);
		
		intData[3] = nbttagcompound.getInteger("timeuntil");
	}
	
    @Override
    public Packet getDescriptionPacket()
    {
       return TropicraftPacketHandler.getPacket(this);
    }
    
	
	public int[] getIntData() {
		return intData;
	}

	public boolean[] getBooleanData() {
		return booleanData;
	}
}
