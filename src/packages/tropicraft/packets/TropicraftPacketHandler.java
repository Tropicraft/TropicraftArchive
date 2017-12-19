package tropicraft.packets;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import tropicraft.TickHandlerClient;
import tropicraft.Tropicraft;
import tropicraft.ai.WorldDirector;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TropicraftPacketHandler implements IPacketHandler {

	public TropicraftPacketHandler() {
	}

	@SideOnly(Side.CLIENT)
	public World getClientWorld() {
		return Minecraft.getMinecraft().theWorld;
	}
	
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		if (packet.channel.equals("SnareTrap")) {
			DataInputStream dis = new DataInputStream(new ByteArrayInputStream(packet.data));

			try {
				boolean clearFirst = dis.readBoolean();
				boolean adding = dis.readBoolean();
				short length = dis.readShort();
				
				Set<String> upsideDownUsernames = Tropicraft.proxy.getUpsideDownUsernames();
				
				if (clearFirst) {
					upsideDownUsernames.clear();
				}
				
				for (int i = 0; i < length; ++i) {
					String username = dis.readUTF();
					if (adding) {
						upsideDownUsernames.add(username);
					} else {
						upsideDownUsernames.remove(username);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if ("QuestData".equals(packet.channel)) {
			DataInputStream dis = new DataInputStream(new ByteArrayInputStream(packet.data));
			try {
				
				NBTTagCompound nbt = Packet.readNBTTagCompound(dis);
				
				TickHandlerClient.playerQuests.syncFromServer(nbt);
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else if ("PagesData".equals(packet.channel)) {
			DataInputStream dis = new DataInputStream(new ByteArrayInputStream(packet.data));
			try {
				
				TickHandlerClient.pagesCollected = Packet.readNBTTagCompound(dis);
				
				//System.out.println(TickHandlerClient.pagesCollected);
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		
/*
		if (packet.channel.equals("Volcano")) {
			ByteArrayDataInput dat = ByteStreams.newDataInput(packet.data);
			int x = dat.readInt();
			int y = dat.readInt();
			int z = dat.readInt();
			
			int intData[] = new int[4];
			boolean booleanData[] = new boolean[4];
			
			for (int i = 0; i < intData.length; i++) {
				intData[i] = dat.readInt();
			}
			
			for (int b = 0; b < booleanData.length; b++) {
				booleanData[b] = dat.readBoolean();
			}
			
			World world = TropicraftMod.proxy.getClientWorld();
			TileEntity te = world.getBlockTileEntity(x, y, z);
			
			if (te instanceof TileEntityVolcano) {
				TileEntityVolcano tv = (TileEntityVolcano) te;
				tv.handlePacketData(intData, booleanData);
			}
		 *///TODO
	}

	
	/**
	 * Static method used in TileEntityVolcano.getDescriptionPacket to replace the generic tile entity packet
	 * @param volcano Tile entity being affected
	 * @return custom packet 250
	 *//*
	public static Packet getPacket(TileEntityVolcano volcano) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(144);
		DataOutputStream dos = new DataOutputStream(bos);
		int x = volcano.xCoord;
		int y = volcano.yCoord;
		int z = volcano.zCoord;
		int[] intData = volcano.getIntData();
		boolean[] booleanData = volcano.getBooleanData();
		try {
			dos.writeInt(x);
			dos.writeInt(y);
			dos.writeInt(z);
			
			for(int i : intData)
				dos.writeInt(i);
			
			for(boolean b : booleanData)
				dos.writeBoolean(b);

		} catch (IOException e) {
			e.printStackTrace();
		}
		Packet250CustomPayload pkt = new Packet250CustomPayload();
		pkt.channel = "Volcano";
		pkt.data = bos.toByteArray();
		pkt.length = bos.size();
		pkt.isChunkDataPacket = true;
		return pkt;
	}*/

	public static Packet getSnareTrapPacket(boolean clearFirst, boolean adding, Set<String> usernames) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		
		try {
			dos.writeBoolean(clearFirst);
			dos.writeBoolean(adding);
			dos.writeShort(usernames.size());
			for (String username: usernames) {
				dos.writeUTF(username);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Packet250CustomPayload pkt = new Packet250CustomPayload();
		pkt.channel = "SnareTrap";
		pkt.data = bos.toByteArray();
		pkt.length = bos.size();
		pkt.isChunkDataPacket = true;
		return pkt;
	}
	
	public static void syncPagesData(String username) {
		Tropicraft.dbg("syncing pagesData to " + username);
		if (MinecraftServer.getServer() != null)
        {
			NBTTagCompound nbt = WorldDirector.getPlayerNBT(username).getCompoundTag("pagesData");
			
			if (nbt != null) {
	            try
	            {
	            	byte[] data = CompressedStreamTools.compress(nbt);
	            	//System.out.println("packet byte count: " + data.length);
	            	
	            	ByteArrayOutputStream bos = new ByteArrayOutputStream((Byte.SIZE * data.length) + Short.SIZE);
	                DataOutputStream dos = new DataOutputStream(bos);
	
	                writeNBTTagCompound(nbt, dos, data);
	                
		            Packet250CustomPayload pkt = new Packet250CustomPayload();
		            pkt.channel = "PagesData";
		            pkt.data = bos.toByteArray();
		            pkt.length = bos.size();
		            MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(username).playerNetServerHandler.sendPacketToPlayer(pkt);
	            }
	            catch (Exception ex)
	            {
	                ex.printStackTrace();
	            }
			}
        }
	}
	
	protected static void writeNBTTagCompound(NBTTagCompound par0NBTTagCompound, DataOutputStream par1DataOutputStream, byte[] data) throws IOException
    {
        if (par0NBTTagCompound == null)
        {
            par1DataOutputStream.writeShort(-1);
        }
        else
        {
            //byte[] var2 = CompressedStreamTools.compress(par0NBTTagCompound);
            par1DataOutputStream.writeShort((short)data.length);
            par1DataOutputStream.write(data);
        }
    }
}
