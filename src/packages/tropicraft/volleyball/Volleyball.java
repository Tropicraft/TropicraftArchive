package tropicraft.volleyball;

import modconfig.ConfigMod;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import tropicraft.ModInfo;
import tropicraft.Tropicraft;
import tropicraft.packets.TropicraftConnectionHandler;
import tropicraft.packets.TropicraftPacketHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@NetworkMod(channels = { "Volcano", "SnareTrap", "QuestData" }, clientSideRequired = true, serverSideRequired = false, packetHandler = TropicraftPacketHandler.class, connectionHandler = TropicraftConnectionHandler.class)
@Mod(modid = "VolleyballMod", name = "Volleyball", version = ModInfo.VERSION)
public class Volleyball {

	@Instance(ModInfo.MODID)
	public static Tropicraft instance;	
	
	/** Used for client-side stuff on servers, etc */
	@SidedProxy(clientSide = "tropicraft.volleyball.VBClientProxy", serverSide = "tropicraft.volleyball.VBCommonProxy")
	public static VBCommonProxy proxy;
	
	public static Block courtMaster;
	
	public static Block post;
	
	public Volleyball() {
		
	}
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		ConfigMod.addConfigFile(event, "tcvbconfigids", new ModIds(), false);
	}
	
	@Init
	public void load(FMLInitializationEvent evt) {
		courtMaster = new BlockCourtMaster(ModIds.BLOCK_VOLLEYBALLCOURTMASTER_ID, "courtmaster").setUnlocalizedName("Court Master").setCreativeTab(CreativeTabs.tabDecorations);
		post = new BlockCourtPost(ModIds.BLOCK_VOLLEYBALLCOURTPOST_ID, "post").setUnlocalizedName("Volleyball Court Post").setCreativeTab(CreativeTabs.tabDecorations);
		
		registerBlock(post, "Volleyball Court Post");
		registerBlock(courtMaster, "Court Master");
		
		GameRegistry.registerTileEntity(TileEntityCourtMaster.class, "CourtMaster");
		
		proxy.registerTESRs();
	}
	
	/**
	 * Register a block with the specified mod specific name : overrides the standard type based name
	 * @param block The block to register
	 * @param name The mod-unique name to register it as
	 */
	private static void registerBlock(Block block, String name) {
		GameRegistry.registerBlock(block, name);
		LanguageRegistry.addName(block, name);
	}

	@PostInit
	public void modsLoaded(FMLPostInitializationEvent evt) {

	}

}
