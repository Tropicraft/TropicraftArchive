package net.tropicraft.blocks.liquids;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@NetworkMod(channels = { "LAPI" }, clientSideRequired = true, serverSideRequired = false, packetHandler = LAPIPacketHandler.class)
@Mod(modid = "LAPI", name="LAPI", version="for MC v1.3.2")
public class LAPI 
{
	private static ArrayList<Block> LAPIBlocks = new ArrayList<Block>();			//these are added to LAPIworld for placing blocks inside these liquids
	public static Map<Integer, Integer>colorMap = new HashMap<Integer, Integer>();
	private static int counter = 0;
	public static int nextID = -1;
	/**unused*/
	public static boolean showBlocksInCreativeMode = true;
	public static Map<Integer, Integer> terrainIDMap = new HashMap<Integer, Integer>();
	private static List<Integer> availableStationaryIDList = new ArrayList<Integer>();
	private static List<Integer> availableFlowingIDList = new ArrayList<Integer>();
	
	private static Map<Integer, Integer> liquidPairs = new HashMap<Integer, Integer>();
	
	/** Used for client-side stuff on servers, etc */
	@SidedProxy(clientSide = "net.tropicraft.blocks.liquids.LAPIClientProxy", serverSide = "net.tropicraft.blocks.liquids.LAPICommonProxy")
	public static LAPICommonProxy proxy;

	private static boolean hasInit = false;

	public LAPI()
	{

	}
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		Configuration preInitConfig = new Configuration(event.getSuggestedConfigurationFile());

		try {
			preInitConfig.load();
			int c = 0;

			for(int a = 0; a < 8; a++)
			{
				for(int b = 0; b < 5; b++)
				{
					availableStationaryIDList.add((Integer)(b*3 + 32*a));
					availableFlowingIDList.add((Integer)(b*3 + 32*a + 1));
					System.out.println("[LAPI] Placing " + (b*3 + 32*a) + " at " + c + " for stationary liquids");
					System.out.println("[LAPI] Placing " + (b*3 + 32*a + 1) + " at " + c + " for moving liquids");
					c++;
				}
			}
		} catch (Exception e) {
			FMLLog.log(Level.SEVERE, e, "LAPI has a problem loading it's configuration");
		} finally {
			preInitConfig.save();
		}
	}

	@Init
	public static void init(FMLInitializationEvent event)
	{
		//[00:24] <Risugami> but the idea of the blend class would be to have that class call two texture fx for updating and them blend together both their arrays into one and use it
		
		//[20:29] <ZeuX> if it doesn't crash the first time you run it you you did something wrong

		

	}

	@PostInit
	public void modsLoaded(FMLPostInitializationEvent event) {

	}
	
	public static final void setColorValues(int liquidID, int hexColor)
	{
		colorMap.put((Integer)liquidID, hexColor);
		System.out.println("[LAPI] Placing liquidID " + liquidID + " with hex color " + hexColor + " into LAPI.colorMap");
	}
	
	public static int getColorValue(int liquidID)
	{
		try
		{
			return colorMap.get((Integer)liquidID);
		}
		catch(NullPointerException npe)
		{
			System.out.println("No values in the LAPI Color Map!");
			return 0xffffff;
		}
	}

	public static int getUniqueLiquidID()
	{
		return proxy.getUniqueLiquidID();
	}

	public static int getUniqueStationaryID()
	{
		int a = ((Integer)availableStationaryIDList.get(nextID)).intValue();
		return a;
	}

	public static int getUniqueFlowingID()
	{
		int a = ((Integer)availableFlowingIDList.get(nextID)).intValue();
		return a;
	}

	public static int getTextureIDFromLiquidID(int liquidID, boolean isAnimatedLiquid) {

		if(!isAnimatedLiquid) 
		{
			return liquidID;
		}
		
		return (liquidID + 1);

	}

	public static void registerBlock(Block block, String blockName)
	{
		GameRegistry.registerBlock(block, blockName);
		LanguageRegistry.addName(block, blockName);
		LAPIBlocks.add(block);
	}

	public static boolean isWater(World world, int i, int j, int k)
	{
		return (world.getBlockId(i,j,k) == Block.waterMoving.blockID) || (world.getBlockId(i,j,k) == Block.waterStill.blockID);
	}

	public static ArrayList<Block> getBlockList()
	{
		return LAPIBlocks;
	}

	public static void registerLiquidPair(Block block1, Block block2) {
		try {
			liquidPairs.put(block1.blockID, block2.blockID);
			liquidPairs.put(block2.blockID, block1.blockID);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static int getPairedBlockID(Block block) {
		return getPairedBlockID(block.blockID);
	}
	
	public static int getPairedBlockID(int blockID) {
		return liquidPairs.get(blockID).intValue();
	}
}
