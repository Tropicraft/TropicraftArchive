package net.tropicraft.blocks.tileentities;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.tileentity.TileEntity;
import net.tropicraft.mods.TropicraftMod;

import java.util.Random;

import cpw.mods.fml.common.FMLCommonHandler;

public class TileEntitySifter extends TileEntity
{
    public int sifterSiftTime;
    private Random rand;
    public boolean sifting;
    private final ItemStack[] possibleCommonSifts = new ItemStack[]{new ItemStack(TropicraftMod.shellCommon1, 1), new ItemStack(TropicraftMod.shellCommon2, 1),
    		new ItemStack(TropicraftMod.shellCommon3, 1 ), new ItemStack(TropicraftMod.shellStarfish, 1), 
    		new ItemStack(TropicraftMod.purifiedSand, 1)};
    
    private final ItemStack[] possibleRareSifts = new ItemStack[]{new ItemStack(TropicraftMod.shellRare1, 1), new ItemStack(Item.glassBottle, 1), new ItemStack(Item.shovelWood, 1),
    		new ItemStack(Item.bucketEmpty, 1), new ItemStack(Item.goldNugget, 1), new ItemStack(TropicraftMod.shellCommon1, 1, 1)};
    
    public double yaw;
    public double yaw2 = 0.0D;
    
    public TileEntitySifter()
    {
        sifterSiftTime = 240;
        rand = new Random();
    }

    public String getInvName()
    {
        return "Sifter";
    }

    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);    
        sifting = nbttagcompound.getBoolean("sifting");
        if (nbttagcompound.hasKey("siftTime")) {
        	sifterSiftTime = nbttagcompound.getInteger("siftTime");
        }
    }

    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setBoolean("sifting", sifting);
        nbttagcompound.setInteger("siftTime", sifterSiftTime);
    }

    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public void updateEntity()
    {
        boolean flag = sifterSiftTime > 0;
        
        if(sifterSiftTime > 0 && isSifting())
        {
            sifterSiftTime--;
       //     System.out.println("Sifter time: " + sifterSiftTime);
        }
        if(worldObj.isRemote) {
            this.yaw2 = this.yaw % 360.0D;
            this.yaw += 4.545454502105713D;
        }
        
        if(!flag && sifting && !worldObj.isRemote)		//spawn teh itemz!
        {
        	double x = this.xCoord + worldObj.rand.nextDouble()*1.4;
        	double y = this.yCoord + worldObj.rand.nextDouble()*1.4;
        	double z = this.zCoord + worldObj.rand.nextDouble()*1.4;
        	
        	for(int i = 0; i < 2; i++)
        	{
        		EntityItem e = new EntityItem(worldObj, x, y, z, getCommonItem());	//spawn common item
        		spawn(e, x, y, z);
        	}
        	
        	if(rand.nextBoolean() && (new Random()).nextInt(10) == 7 && (rand.nextInt(10) == 7))
        	{
        		EntityItem e = new EntityItem(worldObj, x, y, z, getRareItem());	//spawn rare item
        		spawn(e, x, y, z);
        	}
        	else
        	{
         		EntityItem e = new EntityItem(worldObj, x, y, z, getCommonItem());	//spawn common item
        		spawn(e, x, y, z);
        	}
        	
        	sifterSiftTime = 240;
        	this.setSifting(false);
        } else if (!flag && sifting) {
        	sifterSiftTime = 240;
        	this.setSifting(false);
        }
    }

    
    private ItemStack getCommonItem()
    {
    	int l = rand.nextInt(possibleCommonSifts.length);
    	switch(l){
    	case 0: return new ItemStack(TropicraftMod.shellCommon1, 1);
    	case 1: return new ItemStack(TropicraftMod.shellCommon2, 1);
    	case 2: return new ItemStack(TropicraftMod.shellCommon3, 1 ); 
    	case 3: return new ItemStack(TropicraftMod.shellStarfish, 1);
    	case 4: return new ItemStack(TropicraftMod.purifiedSand, 1);
    	default: return new ItemStack(TropicraftMod.purifiedSand, 1);
    	}
    	
    }
    
    private ItemStack getRareItem()
    {
    	
    	if(rand.nextBoolean())
    		if(rand.nextBoolean())
    			if(rand.nextBoolean())
    				if(worldObj.rand.nextBoolean())
    					   	if(rand.nextBoolean())
    					   		if(rand.nextBoolean())
    					   				return new ItemStack(TropicraftMod.shellCommon1, 1, 1);
    					   		else
    					   			return new ItemStack(Item.goldNugget, 1);
    					   		else
    					   	return new ItemStack(Item.bucketEmpty, 1);
    					else
    				return new ItemStack(Item.shovelWood, 1);
    			else
    		return new ItemStack(Item.glassBottle, 1);
    	else
    return new ItemStack(TropicraftMod.shellRare1, 1);
    	else
    		return new ItemStack(TropicraftMod.shellRare1, 1);
    }
    
    private void spawn(EntityItem entity, double i, double j, double k)
    {
    	entity.setLocationAndAngles(i, j, k, 0, 0);
    	worldObj.spawnEntityInWorld(entity);
    }
    
    public void setSifting(boolean shouldSift) {
    	this.sifting = shouldSift;
    	if (!this.worldObj.isRemote) {
    		sendToAllInOurWorld(this.getDescriptionPacket());
    	}
    }
    
    private void sendToAllInOurWorld(Packet pkt) {
    	ServerConfigurationManager scm = FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager();
    	
    	for (Object obj: scm.playerEntityList) {
    		EntityPlayerMP player = (EntityPlayerMP) obj;
    		if (player.worldObj == this.worldObj) {
    			player.playerNetServerHandler.sendPacketToPlayer(pkt);
    		}
    	}
    }
   
    public boolean isSifting() 
    {    	
		return sifting;
	}

	public boolean isUseableByPlayer(EntityPlayer entityplayer)
    {
        if(worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this)
        {
            return false;
        }
        return entityplayer.getDistanceSq((double)xCoord + 0.5D, (double)yCoord + 0.5D, (double)zCoord + 0.5D) <= 64D;
    }

    public void openChest()
    {
    }

    public void closeChest()
    {
    }
    
    @Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
    	this.readFromNBT(pkt.customParam1);
	}

	/**
     * 
     * signs and mobSpawners use this to send text and meta-data
     */
    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound var1 = new NBTTagCompound();
        this.writeToNBT(var1);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, var1);
    }
}
