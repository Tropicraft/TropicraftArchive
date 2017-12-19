package tropicraft;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import tropicraft.ai.WorldDirector;
import tropicraft.blocks.TropicraftBlocks;
import tropicraft.commands.CommandRegistry;
import tropicraft.config.ConfigHandler;
import tropicraft.config.TropicraftConfig;
import tropicraft.crafting.CraftingTropicraft;
import tropicraft.drinks.MixerRecipes;
import tropicraft.economy.ItemValueEntries;
import tropicraft.encyclopedia.Encyclopedia;
import tropicraft.items.TropicraftItems;
import tropicraft.packets.TropicraftConnectionHandler;
import tropicraft.packets.TropicraftPacketHandler;
import tropicraft.proxies.CommonProxy;
import tropicraft.questsystem.PlayerQuestManager;
import tropicraft.registry.TCBlockRegistry;
import tropicraft.registry.TCEntityRegistry;
import tropicraft.world.TCWorldGenerator;
import tropicraft.world.TropicraftWorldUtils;
import tropicraft.world.structures.ComponentKoaVillage;
import tropicraft.world.structures.StructureKoaVillageStart;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

//NOTE: Classes that need uncommenting:
/*
 * DONE TropicraftConnectionHandler
 * TropicraftPacketHandler
 * DONE CommandTeleportTropics
 * BlockCoconut (also, make it render along wall rather than float?)
 * DONE Fix BlockTallFlower for block removal and stuff
 * DONE BlockTropicsFence
 * DONE BlockTropicsFenceGate
 * DONE BlockTropicraftFlower
 * DONE BlockTropicsCoral - needs moar purified sand reference
 * DONE ItemFertilizer - saplings reference (actually possibly don't need, because added code to the sapling file to take care of it)
 * DONE ItemTropicsPickaxe
 * DONE BlockTropicraftPortal - actual teleport code
 * DONE BlockFruitLeaves - finish implementing once have saplings and wood in
 * DONE BlockTropicraftSapling - purified sand reference, growTree method
 * DONE BlockPurifiedSand - entity stuff
 * ValuedItems - requires redesign for itemdamage support (go for full itemstack & nbt support), item entries commented out for now
 * EntityKoaMemberNew - uncomment once trading is functional: job.jobTypes.put(EnumJob.TRADING, new JobTrade(job));
 * JobTrade - readd file once valued items functional and trade plate added
 * c_CoroAIUtil - undo temp disable on koaEnemy once it has a list to iterate over, koaEnemyWhitelist needs rebuilding, factor in package changes
 * RenderAshen - update isswinging logic check to ai datawatcher
 */

/**TODO:
 * 
 * 
 * Terrain gen in overworld as well as dimension
 * Make EIH mob spawnable by building a structure using Chunk o' Head blocks?
 * Re-order item ids to be in alphabetical order
 * Fix tools to work correctly on certain materials
 *
 */
@NetworkMod(channels = { "Volcano", "SnareTrap", "QuestData", "PagesData" }, clientSideRequired = true, serverSideRequired = false, packetHandler = TropicraftPacketHandler.class, connectionHandler = TropicraftConnectionHandler.class)
@Mod(modid = ModInfo.MODID, name = ModInfo.NAME, version = ModInfo.VERSION)
public class Tropicraft {

	@Instance(ModInfo.MODID)
	public static Tropicraft instance;

	public static String modID = ModInfo.MODID;

	/**
	 * Tropicraft configuration file
	 */
	public Configuration config;

	/**
	 * Array of users that can use coconut bombs, can differ between client and server as the server version will get called on the server and client on client.
	 */
	public static String[] COCONUT_BOMB_WHITELIST = null;

	/** Used for client-side stuff on servers, etc */
	@SidedProxy(clientSide = "tropicraft.proxies.ClientProxy", serverSide = "tropicraft.proxies.CommonProxy")
	public static CommonProxy proxy;

	/**
	 * List of all usernames that can use coconut bombs
	 */
	public static List<String> coconutBombWhitelistedUsers;

	@SideOnly(Side.CLIENT)
	public static Encyclopedia encyclopedia;

	public static Logger logger = Logger.getLogger("TropicraftMod");

	//	@SideOnly(Side.CLIENT)
	//	public static NigelJournal journal;

	@ForgeSubscribe
	public void tropicsWaterBucketDrop(FillBucketEvent fbe) {
		World world = fbe.world;

		if (!world.isRemote) {

			Block block = Block.blocksList[world.getBlockId(fbe.target.blockX, fbe.target.blockY, fbe.target.blockZ)];

			if (block != null && block.blockID == TropicraftBlocks.tropicsWaterStationary.blockID) {
				fbe.result = new ItemStack(TropicraftItems.bucketTropicsWater.itemID, 1, 0);
				world.setBlockToAir(fbe.target.blockX, fbe.target.blockY, fbe.target.blockZ);
			}

			if (fbe.result != null && fbe.result.getItem().itemID == TropicraftItems.bucketTropicsWater.itemID) {
				fbe.setResult(Result.ALLOW);
			}
		}
	}

	/**
	 * Initialize commands registry
	 * @param event Event invoked upon server start
	 */
	@EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		CommandRegistry.init(event);
	}

	@EventHandler
	public void serverStop(FMLServerStoppedEvent event) {
		PlayerQuestManager.i.saveAndUnload();
		WorldDirector.writeAllPlayerNBT();
	}

	public static void dbg(Object obj) {
		if (TropicraftConfig.debugConsole) {
			System.out.println(obj);
		}
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		try {
			ConfigHandler.initConfig(event);
			proxy.loadSounds();	//install sounds
			TropicraftZipFilesCopier.init();
		} catch (Exception e) {
			FMLLog.log(Level.SEVERE, e, "Tropicraft has a problem loading it's configuration");
		}
	}

	@EventHandler
	public void load(FMLInitializationEvent evt) {

		logger.setParent(FMLLog.getLogger());
		logger.info("Starting to load TropicraftMod!");

		TropicraftBlocks.init();
		TropicraftItems.init();

		// For overworld gen
		GameRegistry.registerWorldGenerator(new TCWorldGenerator());

		//register EIH mixer recipes
		MixerRecipes.addMixerRecipes();

		proxy.registerBooks();

		//Initialize economy data for trading, selling etc
		ItemValueEntries.initEconomy();

		//register entities
		TCEntityRegistry.init();

		TropicraftWorldUtils.initializeDimension();

		MapGenStructureIO.func_143031_a(ComponentKoaVillage.class, ModInfo.MODID + ":KoaVillageComponent");
		MapGenStructureIO.func_143034_b(StructureKoaVillageStart.class, ModInfo.MODID + ":KoaVillageStart");

		//register tick handlers
		proxy.initTickers();

		//register block render ids
		proxy.registerBlockRenderIds();

		//register renders for entities
		proxy.initTCRenderRegistry();

		//register renders for tile entities
		proxy.registerTESRs();

		//register renders for items
		proxy.registerItemRenderers();

		//register block render handlers
		proxy.registerBlockRenderHandlers();

		//init tropicraft enchantments
		//EnchantmentManager.init();

		CraftingTropicraft.addRecipes();

		//schedule this class for event callbacks
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(new QuestEventHooksTropicraft());

		logger.info("Finished loading TropicraftMod!");
	}

	@EventHandler
	public void modsLoaded(FMLPostInitializationEvent evt) {
		//Register blocks with ore/liquid dictionaries
		TCBlockRegistry.init();
	}
}
