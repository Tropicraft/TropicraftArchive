package tropicraft.volleyball;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;

public class TileEntityCourtMaster extends TileEntity {
	
	public Court court;
	
	public boolean firstTimeSync = true;
	public boolean hasLoadedNBT = false;
	
	public TileEntityCourtMaster() {
		System.out.println("woot!");
	}
	
	/* Should be called on first time placement by player or command */
	public void onFirstPlacement(Court parCourt) {
		court = parCourt;
		hasLoadedNBT = true;
	}
	
	@Override
	public void updateEntity() {
		// TODO Auto-generated method stub
		super.updateEntity();
		if (!worldObj.isRemote) {
			if (hasLoadedNBT && firstTimeSync) {
				firstTimeSync = false;
				
				syncData();
			}
		}
		//System.out.println(worldObj.isRemote);
	}
	
	public void syncData() {
		System.out.println("syncing TE court!");
		MinecraftServer.getServer().getConfigurationManager().sendPacketToAllPlayersInDimension(getDescriptionPacket(), worldObj.provider.dimensionId);
	}
	
    /**
     * Writes a tile entity to NBT.
     */
	@Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        System.out.println("write court");
        nbt.setDouble("minX", court.minX);
        nbt.setDouble("maxX", court.maxX);
        nbt.setDouble("courtY", court.y);
        nbt.setDouble("minZ", court.minZ);
        nbt.setDouble("maxZ", court.maxZ);
        nbt.setDouble("xLength", court.xLength);
        nbt.setDouble("zLength", court.zLength);
        court.saveGame(nbt);
    }

    /**
     * Reads a tile entity from NBT.
     */
	@Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        hasLoadedNBT = true;
        if (this.court == null) {
        	court = new Court();
        	System.out.println("read court");
        	court.minX = nbt.getDouble("minX");
        	court.maxX = nbt.getDouble("maxX");
        	court.y = nbt.getDouble("courtY");
        	court.minZ = nbt.getDouble("minZ");
        	court.maxZ = nbt.getDouble("maxZ");
        	court.xLength = nbt.getDouble("xLength");
        	court.zLength = nbt.getDouble("zLength");
        	court.loadGame(nbt);
        }
    }
	
    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared()
    {
        return 65536.0D;
    }
    
    @Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
    	//Strait up make a packet from the regular nbt write out and send that to client
    	this.readFromNBT(pkt.customParam1);
    }
    
    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound var1 = new NBTTagCompound();
        this.writeToNBT(var1);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 0, var1);
    }
}
