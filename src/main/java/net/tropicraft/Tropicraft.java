package net.tropicraft;


import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.tropicraft.proxy.CommonProxy;
import net.tropicraft.registry.BlockRegistry;
import net.tropicraft.registry.EntityRegistry;
import net.tropicraft.registry.ItemRegistry;

@Mod(modid = Info.MODID, name=Info.NAME, version=Info.VERSION)
public class Tropicraft {
	
	@Mod.Instance( value = "tropicraft" )
	public static Tropicraft instance;
	public static String modID = "tropicraft";
    
	@SidedProxy(clientSide = "net.tropicraft.proxy.ClientProxy", serverSide = "net.tropicraft.proxy.CommonProxy")
    public static CommonProxy proxy;
	
	@Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
		BlockRegistry.preInit();
		ItemRegistry.init();
    }
    
	@Mod.EventHandler
    public void init(FMLInitializationEvent event) {
		BlockRegistry.init();
		EntityRegistry.init();
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
