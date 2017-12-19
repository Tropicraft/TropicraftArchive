package modconfig.forge;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import modconfig.ConfigEntryInfo;
import modconfig.ConfigMod;
import modconfig.gui.GuiConfigEditor;
import net.minecraft.client.Minecraft;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MCPacketHandler implements IPacketHandler
{
    public MCPacketHandler()
    {
    }

    @SideOnly(Side.CLIENT)
	public World getClientWorld() {
		return Minecraft.getMinecraft().theWorld;
	}
    
    @SideOnly(Side.CLIENT)
	public void openConfigGui() {
    	Minecraft.getMinecraft().displayGuiScreen(new GuiConfigEditor());
    }
    
    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
    {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(packet.data));
        try {
	        if ("ModConfig".equals(packet.channel)) {
                
	        	int mode = dis.readInt();
	        	if (mode == 0) {
	                String modid = dis.readUTF();
	                int entryCount = dis.readInt();
	                int pos = 0;
	                ConfigMod.dbg("modconfig packet, size: " + entryCount);
	                //require a full resync of data, make sure other side sends it all
	                if (!GuiConfigEditor.clientMode || ConfigMod.configLookup.get(modid).configData.size() == 0) {
		                ConfigMod.configLookup.get(modid).configData.clear();
		                for (int i = 0; i < entryCount; i++) {
		                	String str1 = dis.readUTF();
		                	String str2 = dis.readUTF();
		                	String str3 = "";//dis.readUTF();
		                	ConfigMod.configLookup.get(modid).configData.add(new ConfigEntryInfo(pos++, str1, str2, str3));
		                }
	                }
	        	} else {
	        		openConfigGui();
	        	}
	        }
        } catch (Exception ex) {
        	//HostileWorlds.dbg("ERROR HANDLING HW PACKETS");
            ex.printStackTrace();
        }
    }
}
