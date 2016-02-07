package net.tropicraft;


import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.tropicraft.proxy.CommonProxy;

@Mod(modid = "tropicraft", name="tropicraft", version="v0.1")
public class Tropitest {
	
	@Mod.Instance( value = "tropicraft" )
	public static Tropitest instance;
	public static String modID = "tropicraft";
    
	@SidedProxy(clientSide = "net.tropicraft.proxy.ClientProxy", serverSide = "net.tropicraft.proxy.CommonProxy")
    public static CommonProxy proxy;
	
	@Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
		
    }
    
	@Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
		proxy.init();
    }
    
    @Mod.EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
    	
    }
    
    @Mod.EventHandler
    public void serverStart(FMLServerStartedEvent event) {
    	
    }
    
    @Mod.EventHandler
    public void serverStop(FMLServerStoppedEvent event) {
    	
    }

}
