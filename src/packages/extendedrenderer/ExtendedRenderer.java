package extendedrenderer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.logging.Level;

import net.minecraft.block.Block;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.MinecraftForge;

import com.google.common.collect.Lists;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import extendedrenderer.render.RotatingEffectRenderer;

@Mod(modid = "ExtendedRenderer", name="Extended Renderer", version="v1.0")
public class ExtendedRenderer {
	
	@Mod.Instance( value = "ExtendedRenderer" )
	public static ExtendedRenderer instance;
    
    /** For use in preInit ONLY */
    public Configuration preInitConfig;
    
    @SidedProxy(clientSide = "extendedrenderer.ClientProxy", serverSide = "extendedrenderer.CommonProxy")
    public static CommonProxy proxy;

    @SideOnly(Side.CLIENT)
    public static RotatingEffectRenderer rotEffRenderer;
    
    @PreInit
    public void preInit(FMLPreInitializationEvent event)
    {
    	preInitConfig = new Configuration(event.getSuggestedConfigurationFile());
        try
        {
            preInitConfig.load();
            
            
        }
        catch (Exception e)
        {
            FMLLog.log(Level.SEVERE, e, "Hostile Worlds has a problem loading it's configuration");
        }
        finally
        {
            preInitConfig.save();
        }
    }
    
    @Init
    public void load(FMLInitializationEvent event)
    {
    	proxy.init(this);
    	MinecraftForge.EVENT_BUS.register(new EventHandler());
    }
    
    @PostInit
	public void postInit(FMLPostInitializationEvent event) {
    	proxy.postInit(this);
	}

    public ExtendedRenderer() {
    	
    }
    
    @Mod.ServerStarted
    public void serverStart(FMLServerStartedEvent event) {
    	
    	//proper command adding
    	//((ServerCommandManager) MinecraftServer.getServer().getCommandManager()).registerCommand(new commandAddOwner());
    }
    
    @Mod.ServerStopped
    public void serverStop(FMLServerStoppedEvent event) {
    	
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
	
	public static void dbg(Object obj) {
		if (true) System.out.println(obj);
	}
}
