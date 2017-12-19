package modconfig;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import CoroAI.c_CoroAIUtil;

import modconfig.forge.CommandModConfig;
import modconfig.forge.MCPacketHandler;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@NetworkMod(channels = { "ModConfig" }, clientSideRequired = true, serverSideRequired = true, packetHandler = MCPacketHandler.class)
@Mod(modid = "ConfigMod", name="Extended Mod Config", version="v1.0", useMetadata=false)
public class ConfigMod {

	@Mod.Instance( value = "ConfigMod" )
	public static ConfigMod instance;
	
	//public static Class configFieldClass = ModConfigFields.class;
	//public static ModConfigFields configFieldInstance = new ModConfigFields(); //upgrade to class.getConstructor later 
	
	public static List<ModConfigData> configs = new ArrayList<ModConfigData>();
	public static List<ModConfigData> liveEditConfigs = new ArrayList<ModConfigData>();
	public static HashMap<String, ModConfigData> configLookup = new HashMap<String, ModConfigData>();
	
	
    public ConfigMod() {
    	instance = this;
    }
    
    @Mod.ServerStarted
    public void serverStart(FMLServerStartedEvent event) {
    	((ServerCommandManager)FMLCommonHandler.instance().getMinecraftServerInstance().getCommandManager()).registerCommand(new CommandModConfig());
    }
    
    public static Packet250CustomPayload getModConfigPacketMenu() {
    	ByteArrayOutputStream bos = new ByteArrayOutputStream(Integer.SIZE);
        DataOutputStream dos = new DataOutputStream(bos);

        try
        {
        	dos.writeInt(1);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        Packet250CustomPayload pkt = new Packet250CustomPayload();
        pkt.channel = "ModConfig";
        pkt.data = bos.toByteArray();
        pkt.length = bos.size();
        
        return pkt;
    }
    
    public static Packet250CustomPayload getModConfigPacket(String modid/*, int count, List entries*/) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(Integer.SIZE + Character.SIZE * 64 * configLookup.get(modid).configData.size());
        DataOutputStream dos = new DataOutputStream(bos);

        try
        {
        	dos.writeInt(0);
        	dos.writeUTF(modid);
        	populateData(modid);
        	dos.writeInt(configLookup.get(modid).configData.size());
        	for (int i = 0; i < configLookup.get(modid).configData.size(); i++) {
        		dos.writeUTF((String)configLookup.get(modid).configData.get(i).name);
        		dos.writeUTF(String.valueOf(configLookup.get(modid).configData.get(i).value));
        	}
            
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        Packet250CustomPayload pkt = new Packet250CustomPayload();
        pkt.channel = "ModConfig";
        pkt.data = bos.toByteArray();
        pkt.length = bos.size();
        
        return pkt;
	}
    
    public static void populateData(String modid) {
    	
    	configLookup.get(modid).configData.clear();
    	
    	ModConfigData data = configLookup.get(modid);
        
        if (data != null) {
        	//int pos = 0;
        	
        	processHashMap(modid, data.valsInteger);
        	processHashMap(modid, data.valsDouble);
        	processHashMap(modid, data.valsBoolean);
        	processHashMap(modid, data.valsString);
        } else {
        	System.out.println("error: cant find config data for gui");
        }
        
        //sort it here!
        Collections.sort(configLookup.get(modid).configData, new ConfigComparatorName());
        //configLookup.get(modid).configData.
    }
    
    public static void processHashMap(String modid, Map map) {
    	Iterator it = map.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        String name = (String)pairs.getKey();
	        Object val = pairs.getValue();
	        ConfigEntryInfo info = new ConfigEntryInfo(configLookup.get(modid).configData.size(), name, val);
	        configLookup.get(modid).configData.add(info);
	    }
    }
    
    /* Main Usage Methods Start */
    
    /* Main Inits */
    public static void addConfigFile(FMLPreInitializationEvent event, String modID, IConfigCategory configCat) {
    	addConfigFile(event, modID, configCat, true);
    }
    
    public static void addConfigFile(FMLPreInitializationEvent event, String modID, IConfigCategory configCat, boolean liveEdit) {
    	if (instance == null) init(event);
    	
    	ModConfigData configData = new ModConfigData(new File(getSaveFolderPath() + "config" + File.separator + configCat.getConfigFileName() + ".cfg"), modID, configCat.getClass(), configCat);
    	
    	configs.add(configData);
    	if (liveEdit) liveEditConfigs.add(configData);
    	configLookup.put(modID, configData);
    	
    	configData.initData();
    	configData.writeConfigFile(false);
    }
    
    /* Get Inner Field value */
    public static Object getField(String configID, String name) {
    	try { return c_CoroAIUtil.getPrivateValue(configLookup.get(configID).configClass, instance, name);
    	} catch (Exception ex) { ex.printStackTrace(); }
    	return null;
    }
    
    /* Update Config Field Entirely */
    public static boolean updateField(String configID, String name, Object obj) {
    	if (configLookup.get(configID).setFieldBasedOnType(name, obj)) {
        	//writeHashMapsToFile();
    		configLookup.get(configID).writeConfigFile(true);
        	return true;
    	}
    	return false;
    }
    
    /* Sync the HashMap data if an outside source modified one of the config fields */
    public static void updateHashMaps() {
    	/*Field[] fields = configLookup.get(configID).getDeclaredFields();
    	
    	for (int i = 0; i < fields.length; i++) {
    		Field field = fields[i];
    		String name = field.getName();
    		instance.processField(name);
    	}*/
    }
    
    /* Main Usage Methods End */
    
    private static void init(FMLPreInitializationEvent event) {
    	new ConfigMod();
    	//instance.saveFilePath = event.getSuggestedConfigurationFile();
    	//instance.initData();
    	//instance.writeConfigFiles(false);
    }
    
    public void initData() {
    	
    }
    
    public void writeConfigFiles(Boolean resetData) {
    	
    }
    
    public static String getSaveFolderPath() {
    	if (MinecraftServer.getServer() == null || MinecraftServer.getServer().isSinglePlayer()) {
    		return getClientSidePath() + File.separator;
    	} else {
    		return new File(".").getAbsolutePath() + File.separator;
    	}
    	
    }
    
    @SideOnly(Side.CLIENT)
	public static String getClientSidePath() {
		return FMLClientHandler.instance().getClient().getMinecraftDir().getPath();
	}
    
    public static void dbg(Object obj) {
		if (true) {
			System.out.println(obj);
			//MinecraftServer.getServer().getLogAgent().logInfo(String.valueOf(obj));
		}
	}
}
