package tropicraft.blocks.tileentities;

import java.util.Random;

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
import tropicraft.blocks.TropicraftBlocks;
import tropicraft.items.TropicraftItems;
import cpw.mods.fml.common.FMLCommonHandler;

public class TileEntitySifter extends TileEntity {

	private static final int SIFT_TIME = 20 * 30;	//change to moar later TODO:

	/** Is this machine currently sifting? */
	private boolean sifting;

	/** Current progress in sifting - -1 if not sifting */
	private int currentSiftTime;

	private Random rand;	

	public double yaw;
	public double yaw2 = 0.0D;

	public TileEntitySifter() {
		rand = new Random();
		currentSiftTime = SIFT_TIME;
	}

	private ItemStack getCommonItem() {
		int dmg = rand.nextInt(8);	//damage of shell

		switch (dmg) {
		case 0:
		case 1:
		case 2:
		case 4:
			return new ItemStack(TropicraftItems.shells, 1, dmg);
		default:
			return new ItemStack(TropicraftBlocks.purifiedSand, 1);
		}
	}

	private ItemStack getRareItem() {
		int dmg = rand.nextInt(6);

		switch (dmg) {
		case 0:
			return new ItemStack(TropicraftItems.shells, 1, 3);	//rube nautilus
		case 1:
			return new ItemStack(Item.goldNugget, 1);
		case 2:
			return new ItemStack(Item.bucketEmpty, 1);
		case 3:
			return new ItemStack(Item.shovelWood, 1);
		case 4:
			return new ItemStack(Item.glassBottle, 1);
		default:
			return new ItemStack(TropicraftItems.shells, 1, 3);	//rube nautilus
		}
	}

	public String getInvName() {
		return "Sifter";
	}

	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);    
		sifting = nbttagcompound.getBoolean("sifting");
		if (nbttagcompound.hasKey("siftTime")) {
			currentSiftTime = nbttagcompound.getInteger("siftTime");
		}
	}

	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setBoolean("sifting", sifting);
		nbttagcompound.setInteger("siftTime", currentSiftTime);
	}

	@Override
	public void updateEntity()
	{
		boolean flag = currentSiftTime > 0;

		if(currentSiftTime > 0 && getIsSifting())
		{
			currentSiftTime--;
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

			for(int i = 0; i < 5; i++)
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

			currentSiftTime = SIFT_TIME;
			this.setSifting(false);
		} else if (!flag && sifting) {
			currentSiftTime = SIFT_TIME;
			this.setSifting(false);
		}
	}

	private void spawn(EntityItem entity, double i, double j, double k)
	{
		if (worldObj.isRemote) return;
		entity.setLocationAndAngles(i, j, k, 0, 0);
		worldObj.spawnEntityInWorld(entity);
	}

	public boolean getIsSifting() {
		return this.sifting;
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

	private boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		if(worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this)
		{
			return false;
		}
		return entityplayer.getDistanceSq((double)xCoord + 0.5D, (double)yCoord + 0.5D, (double)zCoord + 0.5D) <= 64D;
	}

	public void openChest(){}
	public void closeChest(){}

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
