package net.tropicraft.packets;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.tropicraft.ClientTickHandler;
import net.tropicraft.blocks.tileentities.TileEntityVolcano;
import net.tropicraft.mods.TropicraftMod;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Set;

import CoroAI.entity.c_EnhAI;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class TropicraftPacketHandler implements IPacketHandler {

	public TropicraftPacketHandler() {
	}

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {

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
		} else if ("CoroAI_Inv".equals(packet.channel)) {
			DataInputStream dis = new DataInputStream(new ByteArrayInputStream(packet.data));
			try {
				int entID = dis.readInt();
				ItemStack is = Packet.readItemStack(dis);

				Entity entity = TropicraftMod.proxy.getEntByID(entID);
				if (entity instanceof c_EnhAI) {
					((c_EnhAI) entity).inventory.mainInventory[0] = is;
					((c_EnhAI) entity).setCurrentSlot(0);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else if (packet.channel.equals("SnareTrap")) {
			DataInputStream dis = new DataInputStream(new ByteArrayInputStream(packet.data));
			try {
				boolean clearFirst = dis.readBoolean();
				boolean adding = dis.readBoolean();
				short length = dis.readShort();
				
				Set<String> upsideDownUsernames = TropicraftMod.proxy.getUpsideDownUsernames();
				
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
				
				ClientTickHandler.playerQuests.syncFromServer(nbt);
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	
	/**
	 * Static method used in TileEntityVolcano.getDescriptionPacket to replace the generic tile entity packet
	 * @param volcano Tile entity being affected
	 * @return custom packet 250
	 */
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
	}

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

}
