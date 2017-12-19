package build;

import java.io.File;

import modconfig.ConfigMod;
import net.minecraft.block.Block;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import build.config.BuildConfig;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@NetworkMod(channels = { "Build_Command" }, clientSideRequired = true, serverSideRequired = true, packetHandler = BuildPacketHandler.class)
@Mod(modid = "BuildMod", name = "Build Mod", version = "v1.0")

public class BuildMod
{

    @SidedProxy(clientSide = "build.BuildClientProxy", serverSide = "build.BuildCommonProxy")
    public static BuildCommonProxy proxy;
    
    public Configuration preInitConfig;
    
    public ItemEditTool itemEditTool; 
    
    @PreInit
    public void preInit(FMLPreInitializationEvent event)
    {
    	ConfigMod.addConfigFile(event, "bm", new BuildConfig());
    }
    
    @Init
    public void load(FMLInitializationEvent event)
    {
        proxy.init(this);
        proxy.registerRenderInformation();
        
        MinecraftForge.EVENT_BUS.register(new BuildEventHandler());
    }

    @PostInit
    public void modsLoaded(FMLPostInitializationEvent event)
    {
    	/*System.out.println("TEST OUTPUT OF UNLOCALIZED NAMES! GO!");
        for (int i = 0; i < Block.blocksList.length; i++) {
        	Block block = Block.blocksList[i];
        	if (block != null) {
        		System.out.println("ID: " + i + " - " + block.getUnlocalizedName());
        	}
        }*/
    }
    
    public static String lastWorldFolder = "";
    
    public static String getWorldFolderName() {
		World world = DimensionManager.getWorld(0);
		
		if (world != null) {
			lastWorldFolder = ((WorldServer)world).getChunkSaveLocation().getName();
			return lastWorldFolder + File.separator;
		}
		
		return lastWorldFolder + File.separator;
	}
	
	public static String getSaveFolderPath() {
    	if (MinecraftServer.getServer() == null || MinecraftServer.getServer().isSinglePlayer()) {
    		return getClientSidePath() + File.separator;
    	} else {
    		return new File(".").getAbsolutePath() + File.separator;
    	}
    	
    }
	
	public static String getWorldSaveFolderPath() {
    	if (MinecraftServer.getServer() == null || MinecraftServer.getServer().isSinglePlayer()) {
    		return getClientSidePath() + File.separator + "saves" + File.separator;
    	} else {
    		return new File(".").getAbsolutePath() + File.separator;
    	}
    	
    }
    
    @SideOnly(Side.CLIENT)
	public static String getClientSidePath() {
		return FMLClientHandler.instance().getClient().getMinecraftDir().getPath();
	}

	public static void dbg(String string) {
		System.out.println(string);
	}
    
}


