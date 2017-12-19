package net.tropicraft.mods;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHalfSlab;
import net.minecraft.block.EnumMobType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemColored;
import net.minecraft.item.ItemReed;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.src.ModLoader;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.liquids.LiquidContainerData;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;
import net.tropicraft.DartSettingsHandler;
import net.tropicraft.EnumToolMaterialTropics;
import net.tropicraft.TCEntityRegistry;
import net.tropicraft.TropicraftChestItemRenderer;
import net.tropicraft.blocks.BlockBamboo;
import net.tropicraft.blocks.BlockBambooChest;
import net.tropicraft.blocks.BlockBambooFence;
import net.tropicraft.blocks.BlockBambooMug;
import net.tropicraft.blocks.BlockCoconut;
import net.tropicraft.blocks.BlockCoffeePlant;
import net.tropicraft.blocks.BlockCurareBowl;
import net.tropicraft.blocks.BlockEIHMixer;
import net.tropicraft.blocks.BlockFlowerIris;
import net.tropicraft.blocks.BlockFlowerPineapple;
import net.tropicraft.blocks.BlockFlowerPotTC;
import net.tropicraft.blocks.BlockFountain;
import net.tropicraft.blocks.BlockFruitLeaves;
import net.tropicraft.blocks.BlockKoaChest;
import net.tropicraft.blocks.BlockLemonSqueezer;
import net.tropicraft.blocks.BlockPortalWall;
import net.tropicraft.blocks.BlockPurchasePlate;
import net.tropicraft.blocks.BlockPurifiedSand;
import net.tropicraft.blocks.BlockSifter;
import net.tropicraft.blocks.BlockTempLavaFlowing;
import net.tropicraft.blocks.BlockTikiTorch;
import net.tropicraft.blocks.BlockTropicalBundle;
import net.tropicraft.blocks.BlockTropicalDoor;
import net.tropicraft.blocks.BlockTropicalStairs;
import net.tropicraft.blocks.BlockTropicalStep;
import net.tropicraft.blocks.BlockTropicalStone;
import net.tropicraft.blocks.BlockTropicalWood;
import net.tropicraft.blocks.BlockTropicraftCoral;
import net.tropicraft.blocks.BlockTropicraftFenceGate;
import net.tropicraft.blocks.BlockTropicraftFlower;
import net.tropicraft.blocks.BlockTropicraftLeaf;
import net.tropicraft.blocks.BlockTropicraftLog;
import net.tropicraft.blocks.BlockTropicraftOre;
import net.tropicraft.blocks.BlockTropicraftPortal;
import net.tropicraft.blocks.BlockTropicraftSapling;
import net.tropicraft.blocks.BlockVolcano;
import net.tropicraft.blocks.BlockWallShell;
import net.tropicraft.blocks.liquids.BlockFlowingLAPI;
import net.tropicraft.blocks.liquids.BlockStationaryLAPI;
import net.tropicraft.blocks.liquids.LAPI;
import net.tropicraft.blocks.tileentities.TileEntityBambooChest;
import net.tropicraft.blocks.tileentities.TileEntityBambooMug;
import net.tropicraft.blocks.tileentities.TileEntityCurareBowl;
import net.tropicraft.blocks.tileentities.TileEntityEIHMixer;
import net.tropicraft.blocks.tileentities.TileEntityFlowerPot;
import net.tropicraft.blocks.tileentities.TileEntityKoaChest;
import net.tropicraft.blocks.tileentities.TileEntityLemonSqueezer;
import net.tropicraft.blocks.tileentities.TileEntityPurchasePlate;
import net.tropicraft.blocks.tileentities.TileEntitySifter;
import net.tropicraft.blocks.tileentities.TileEntityVolcano;
import net.tropicraft.blocks.tileentities.TileEntityWallShell;
import net.tropicraft.crafting.CraftingTropicraft;
import net.tropicraft.creative.TropiCreativeTabs.CreativeTabBlockTC;
import net.tropicraft.creative.TropiCreativeTabs.CreativeTabCombatTC;
import net.tropicraft.creative.TropiCreativeTabs.CreativeTabDecoTC;
import net.tropicraft.creative.TropiCreativeTabs.CreativeTabFoodTC;
import net.tropicraft.creative.TropiCreativeTabs.CreativeTabFroxTC;
import net.tropicraft.creative.TropiCreativeTabs.CreativeTabMaterialsTC;
import net.tropicraft.creative.TropiCreativeTabs.CreativeTabMiscTC;
import net.tropicraft.creative.TropiCreativeTabs.CreativeTabToolsTC;
import net.tropicraft.encyclopedia.Encyclopedia;
import net.tropicraft.entities.EntityHangingTropicraft;
import net.tropicraft.entities.EntityTropicalFish;
import net.tropicraft.events.TropicraftEventHooks;
import net.tropicraft.items.ITropicraftArmor;
import net.tropicraft.items.ItemAshenMask;
import net.tropicraft.items.ItemBamboo;
import net.tropicraft.items.ItemBambooSpear;
import net.tropicraft.items.ItemBeachFloat;
import net.tropicraft.items.ItemBlowGun;
import net.tropicraft.items.ItemChair;
import net.tropicraft.items.ItemCocktail;
import net.tropicraft.items.ItemCoconut;
import net.tropicraft.items.ItemCoffeeBean;
import net.tropicraft.items.ItemCurare;
import net.tropicraft.items.ItemFishBucket;
import net.tropicraft.items.ItemFruitJuice;
import net.tropicraft.items.ItemHangingTropicraftEntity;
import net.tropicraft.items.ItemIris;
import net.tropicraft.items.ItemMobEgg;
import net.tropicraft.items.ItemNigelJournal;
import net.tropicraft.items.ItemPinaColada;
import net.tropicraft.items.ItemPineapple;
import net.tropicraft.items.ItemShell;
import net.tropicraft.items.ItemSnareTrap;
import net.tropicraft.items.ItemSponge;
import net.tropicraft.items.ItemTikiItem;
import net.tropicraft.items.ItemTropBook;
import net.tropicraft.items.ItemTropicalDoor;
import net.tropicraft.items.ItemTropicalMeal;
import net.tropicraft.items.ItemTropicraft;
import net.tropicraft.items.ItemTropicraftArmor;
import net.tropicraft.items.ItemTropicraftBucket;
import net.tropicraft.items.ItemTropicraftDart;
import net.tropicraft.items.ItemTropicraftFood;
import net.tropicraft.items.ItemTropicraftLeafball;
import net.tropicraft.items.ItemTropicraftRecord;
import net.tropicraft.items.ItemTropicsAxe;
import net.tropicraft.items.ItemTropicsDagger;
import net.tropicraft.items.ItemTropicsHoe;
import net.tropicraft.items.ItemTropicsPickaxe;
import net.tropicraft.items.ItemTropicsPortalStarter;
import net.tropicraft.items.ItemTropicsSpade;
import net.tropicraft.items.ItemTropicsSword;
import net.tropicraft.items.ItemUmbrella;
import net.tropicraft.items.ItemWaterGear;
import net.tropicraft.items.ItemZircon;
import net.tropicraft.koa.ValuedItems;
import net.tropicraft.packets.TropicraftConnectionHandler;
import net.tropicraft.packets.TropicraftPacketHandler;
import net.tropicraft.proxies.CommonProxy;
import net.tropicraft.world.TCWorldGenerator;
import net.tropicraft.world.WorldProviderTropics;
import net.tropicraft.world.teleporter.TeleporterTropics;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import CoroAI.entity.ItemTropicalFishingRod;
import cpw.mods.fml.client.registry.RenderingRegistry;
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
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@NetworkMod(channels = { "CoroAI_Inv", "Volcano", "SnareTrap", "QuestData"}, clientSideRequired = true, serverSideRequired = false, packetHandler = TropicraftPacketHandler.class, connectionHandler = TropicraftConnectionHandler.class)
@Mod(modid = "TropicraftMod", name="Tropicraft", version="v4.2.2 for MC v1.4.6")
public class TropicraftMod {
	/*BLOCK IDS*/
	private int bambooId;
	private int bambooBlockId;
	private int bambooFenceBlockId;
	private int bambooFenceGateBlockId;
	private int coconutBlockId;
	private int tikiTorchId;
	private int bambooStairId;
	private int oreEudialyteId;
	private int oreZirconId;
	private int oreAzuriteId;
	private int coralId1;
	private int flowerId1;
	private int thatchBlockId;
	private int thatchStairsId;
	private int tropicalWoodId;
	private int palmStairsId;
	private int palmPlanksId;
	private int tropicLeavesId;
	private int chunkBlockId;
	private int chunkStairsId;
	private int portalId;
	private int portalWallId;
	private int saplingsId;
	private int fruitLeavesId;
	private int pineappleId;
	private int irisId;
	private int sifterId;
	private int stationaryWaterId;
	private int flowingWaterId;
	private int tempLavaId;
	private int volcanoHelperId;
	private int singleSlabId;
	private int doubleSlabId;
	private int bambooChestId;
	private int purifiedSandId;
	private int koaChestId;
	private int wallShellId;
	private int bambooDoorId;
	private int lemonSqueezerId;
	private int eihMixerId;
	private int bambooMugId;
	private int flowerPotId;
	private int coffeePlantId;
	private int fountainId;
	private int fountainFlowId;
	private int fountainStationaryId;
	private int curareBowlId;	private int tradeBlockId;	/*END BLOCK IDS*/

	/* START BLOCKS */
	public static Block bamboo;
	public static Block bambooBlock;
	public static Block bambooFence;
	public static Block bambooFenceGate;
	public static Block bambooStairs;
	public static Block coconut;
	public static BlockTikiTorch tikiTorch;
	public static Block oreEudialyte;
	public static Block oreZircon;
	public static Block oreAzurite;
	public static Block coralCollection1;
	public static Block flowerCollection1;
	public static Block thatchBlock;
	public static Block thatchStairs;
	public static Block tropicalWood;
	public static Block palmStairs;
	public static Block palmPlanks;
	public static Block tropicLeaves;
	public static Block chunkBlock;
	public static Block chunkStairs;    
	public static Block tropicraftPortal;
	public static Block portalWall;
	public static Block saplings;
	public static BlockFruitLeaves fruitTreeLeaves;
	public static Block irisFlower;
	public static Block pineappleFlower;
	public static Block sifter;
	public static Block waterStillTropics;
	public static Block waterMovingTropics;
	public static Block tempLavaMoving;
	public static Block volcanoHelper;
	public static BlockHalfSlab tropicalSingleSlab;
	public static BlockHalfSlab tropicalDoubleSlab;
	public static Block bambooChest;
	public static Block purifiedSand;
	public static Block koaChest;
	public static Block wallShell;
	public static Block bambooDoor;
	public static Block lemonSqueezer;
	public static Block eihMixer;
	public static Block bambooMug;
	public static Block flowerPot;
	public static Block coffeePlant;
	public static Block fountain;
	public static Block curareBowl;
	public static Block tradeBlock;
	/* END BLOCKS */

	/* START ITEMS */
	public static Item bambooItem;
	public static Item coconutItem;
	public static Item irisItem;
	public static Item pineappleItem;
	public static Item tikiItem;
	public static Item fertilizer;
	public static Item froxTradeWinds;
	public static Item froxEasternIsles;
	public static Item bambooMugFull;
	public static Item bambooMugEmpty;
	public static Item swordEudialyte;
	public static Item shovelEudialyte;
	public static Item pickaxeEudialyte;
	public static Item axeEudialyte;
	public static Item hoeEudialyte;
	public static Item swordZircon;
	public static Item shovelZircon;
	public static Item pickaxeZircon;
	public static Item axeZircon;
	public static Item hoeZircon;
	public static Item eudialyteItem;
	public static Item zirconItem;
	public static Item azuriteItem;
	public static Item pearlItem;
	public static Item blackPearlItem;
	public static Item frogLeg;
	public static Item cookedFrogLeg;
	public static Item chair;
	public static Item umbrella;
	public static Item coconutBomb;
	public static Item scale;
	public static Item turtleShell;
	public static Item shellCommon1;
	public static Item shellCommon2;
	public static Item shellCommon3;
	public static Item shellRare1;
	public static Item shellStarfish;
	public static Item beachFloat;
	public static Item marlinRaw;
	public static Item marlinCooked;
	public static Item bambooSpear;
	public static Item poisonSkin;
	public static Item paraDart;
	public static Item ashenMask;
	public static Item blowGun;
	public static Item scaleBoots;
	public static Item scaleHelm;
	public static Item scaleLeggings;
	public static Item scaleChestplate;
	public static Item fishingNet;
	public static Item fishBucket;
	public static Item bucketTropicsWater;
	public static Item lemon;
	public static Item lime;
	public static Item orange;
	public static Item grapefruit;
	public static Item pineappleCube;
	public static Item coconutChunk;
	//public static Item seaweed;
	//public static Item nori;
	public static Item leafBall;
	public static Item mobEgg;
	public static Item encTropica;
	public static Item snorkel;
	public static Item flippers;
	public static Item fishingRodTropical;
	public static Item dagger;
	public static Item sponge;
	public static Item bambooDoorItem;
	public static Item tropicsPortalEnchanter;
	public static Item snareTrap;
	public static Item lemonJuice;
	public static Item limeJuice;
	public static Item orangeJuice;
	public static Item grapefruitJuice;
	public static Item cocktail;
	public static Item flowerPotItem;
	public static ItemCoffeeBean coffeeBean;
	public static Item nigelsJournal;
	public static Item tropiFrame;
	public static Item koaFrame;
	public static Item curare;
	public static Item zirconium;
	public static Item bambooStick;
	/* END ITEMS */

	/*ITEM IDS*/
	private int bambooItemId;
	private int coconutItemId;
	private int irisItemId;
	private int pineappleItemId;
	private int tikiItemId;
	private int fertilizerId;
	private int tradeWindsId;
	private int easternIslesId;
	private int bambooMugFullId;
	private int bambooMugEmptyId;
	private int[] eudialyteToolsIds = new int[5];
	private String[] eudialyteToolsNames = new String[]{"swordEudialyte", "shovelEudialyte", "pickaxeEudialyte", "axeEudialyte", "hoeEudialyte"};
	private int[] zirconToolsIds = new int[5];
	private String[] zirconToolsNames = new String[]{"swordZircon", "shovelZircon", "pickaxeZircon", "axeZircon", "hoeZircon"};
	private int eudialyteItemId;
	private int zirconItemId;
	private int azuriteItemId;
	private int pearlItemId;
	private int blackPearlItemId;
	private int frogLegId;
	private int cookedFrogLegId;
	private int chairId;
	private int umbrellaId;
	private int coconutBombId;
	private int scaleId;
	private int turtleShellId;
	private int[] shellIds = new int[5];
	private String[] shellIdNames = new String[]{"solonoxShell", "froxConch", "pabShell", "rubeNautilus", "starfishShell"};
	private int beachFloatId;
	private int rawMarlinId;
	private int cookedMarlinId;
	private int bambooSpearId;
	private int poisonSkinId;
	private int paraDartId;
	private int ashenMaskId;
	private int blowGunId;
	private int[] scaleArmorIds = new int[4];
	private String[] scaleArmorNames = new String[]{"scaleBoots", "scaleHelm", "scaleLeggings", "scaleChestplate"};
	private int fishingNetId;
	private int fishBucketId;
	private int bucketTropicsWaterId;
	private int[] fruitIds = new int[4];
	private String[] fruitNames = new String[]{"lemon", "lime", "orange", "grapefruit"};
	private int pineappleCubeId;
	private int coconutChunkId;
	//private int seaweedId;
	//private int noriId;
	private int leafBallId;
	private int mobEggId;
	private int encyclopediaId;
	private int snorkelId;
	private int flippersId;
	private int fishingRodId;
	private int daggerId;
	private int spongeItemId;
	private int bambooDoorItemId;
	private int portalStarterId;
	private int snareTrapId;
	private int lemonJuiceId;
	private int limeJuiceId;
	private int orangeJuiceId;
	private int grapefruitJuiceId;
	private int cocktailId;
	private int flowerPotItemId;
	private int coffeeBeanId;
	private int curareId;
	private int nigelsJournalId;
	private int itemFrameId;
	private int koaFrameId;
	private int zirconiumId;
	private int bambooStickId;
	/*END ITEM IDS*/

	/*General properties*/
	public static boolean generateVolcanoes;
	public static boolean canOtherModsGenerateInRealm;
	public static boolean keepTropicraftDimensionLoaded;
	public static boolean shouldVolcanoesErupt;
	public static boolean isTropicraftInDebugMode;
	public static boolean comeSailAway;

	/**
	 * Boolean used in TCWorldGenerator to determine if tropicraft palm trees, bamboo, and stuff, can generate
	 * in the main world
	 */
	public static boolean generateTropicalStuffInMainWorld;
	/*End general properties*/

	/**dimensionId of the tropicraft realm*/
	public static int tropicsDimensionID = -127;

	/**blockIndexInTexture for the bottom of palm wood*/
	public static final int palmWoodBottom = 18;
	
	/**Used for LAPI, determines an id used by LAPI*/
	public int tropicwaterID;
	
	/** Starting block id for tropicraft blocks */
	private static int currentBlockId = 200;
	
	/** Starting item id for tropicraft items*/
	private static int currentItemId = 14258;

	/**Render id for tiki torches*/
	public static int tikiTorchRenderId;
	
	/**Render id for lemon squeezers*/
	public static int lemonSqueezerRenderId;
	
	/**Render id for EIH Mixers*/
	public static int eihMixerRenderId;
	
	/**Render id for flower pots*/
	public static int flowerPotRenderId;
	
	/**Render id for fountains*/
	public static int fountainRenderId;
	
	/**Render id for curare bowls*/
	public static int curareBowlRenderId;
	
	/**Render id for coffee plants*/
	public static int coffeePlantRenderId;
	

	/** For use in preInit ONLY */
	public Configuration preInitConfig;

	/** Used for client-side stuff on servers, etc */
	@SidedProxy(clientSide = "net.tropicraft.proxies.ClientProxy", serverSide = "net.tropicraft.proxies.CommonProxy")
	public static CommonProxy proxy;

	/**Special render mode id for bamboo chests*/
	public static int bambooChestModelId;

	private static String soundZipPath = "/resources/";

	public static DartSettingsHandler dartSettings;

	public static TropicraftMod instance;

	public static Encyclopedia encyclopedia;

	public static final CreativeTabs tabBlock = new CreativeTabBlockTC("buildingBlocks");
	public static final CreativeTabs tabFood = new CreativeTabFoodTC("food");
	public static final CreativeTabs tabTools = new CreativeTabToolsTC("tools");
	public static final CreativeTabs tabCombat = new CreativeTabCombatTC("combat");
	public static final CreativeTabs tabDecorations = new CreativeTabDecoTC("decorations");
	public static final CreativeTabs tabMaterials = new CreativeTabMaterialsTC("materials");
	public static final CreativeTabs tabFrox = new CreativeTabFroxTC("frox");
	public static final CreativeTabs tabMisc = new CreativeTabMiscTC("misc");
	
	public static final List<ITropicraftArmor> armorList = new ArrayList<ITropicraftArmor>();

	@PreInit
	public void preInit(FMLPreInitializationEvent event) {

		preInitConfig = new Configuration(event.getSuggestedConfigurationFile());

		try {
			preInitConfig.load();
			tikiTorchRenderId = RenderingRegistry.getNextAvailableRenderId();
			lemonSqueezerRenderId = RenderingRegistry.getNextAvailableRenderId();
			eihMixerRenderId = RenderingRegistry.getNextAvailableRenderId();
			flowerPotRenderId = RenderingRegistry.getNextAvailableRenderId();
			fountainRenderId = RenderingRegistry.getNextAvailableRenderId();
			curareBowlRenderId = RenderingRegistry.getNextAvailableRenderId();
			coffeePlantRenderId = RenderingRegistry.getNextAvailableRenderId();

			setBlockIds();
			setItemIds();
			setGeneralSettings();
			addMobNames();
			proxy.rpapi();
			proxy.loadSounds();
		} catch (Exception e) {
			FMLLog.log(Level.SEVERE, e, "Tropicraft has a problem loading it's configuration");
		} finally {
			preInitConfig.save();
		}
	}

	@Init
	public void load(FMLInitializationEvent event) {
		instance = this;

		proxy.init();

		proxy.preEncyclopediaInit();

		registerTileEntities();
		
		bambooChestModelId = RenderingRegistry.getNextAvailableRenderId();

		tropicwaterID = proxy.getUniqueTropicraftLiquidID();
		proxy.registerTileEntitySpecialRenderer();		//put all tile entity render mappings in ClientProxy.registerTileEntitySpecialRenderer		
		RenderingRegistry.registerBlockHandler(new TropicraftChestItemRenderer());

		initBlocks();
		initItems();

		tropicalWood.blockIndexInTexture = 17;
		chunkBlock.blockIndexInTexture = 21;		
		
		LAPI.setColorValues(tropicwaterID, 0x00FFE6);

		initAndNameItemBlocks();

		registerBlocks();
		registerItems();
		
		ValuedItems.initItems();
		
		LiquidContainerRegistry.registerLiquid(new LiquidContainerData(new LiquidStack(this.waterStillTropics, LiquidContainerRegistry.BUCKET_VOLUME), new ItemStack(this.bucketTropicsWater), new ItemStack(Item.bucketEmpty)));
		LiquidDictionary.getOrCreateLiquid("Tropics Water", new LiquidStack(waterStillTropics, LiquidContainerRegistry.BUCKET_VOLUME));

		GameRegistry.registerWorldGenerator(new TCWorldGenerator());

		proxy.registerRenderInformation();

		proxy.initRenderRegistry();
		TCEntityRegistry.init(this);

		proxy.encyclopediaInit();

		CraftingTropicraft.addRecipes();    //adds recipes, mixer, curare, and crafting

		DimensionManager.registerProviderType(tropicsDimensionID, WorldProviderTropics.class, keepTropicraftDimensionLoaded);
		DimensionManager.registerDimension(tropicsDimensionID, tropicsDimensionID);	
		
		registerTropicraftArmorsForTicking();

		MinecraftForge.EVENT_BUS.register(new TropicraftEventHooks());
	}
	
	private void registerTropicraftArmorsForTicking() {
		armorList.add((ITropicraftArmor) snorkel);
		armorList.add((ITropicraftArmor) flippers);
	}

	private void setGeneralSettings()
	{
		generateVolcanoes = this.preInitConfig.get("general", "Generate_Volcanoes", true).getBoolean(true);
		canOtherModsGenerateInRealm = this.preInitConfig.get("general", "Can_other_mods_generate_in_realm", false).getBoolean(false);
		keepTropicraftDimensionLoaded = this.preInitConfig.get("general", "Keep_tropicraft_dimension_loaded", true).getBoolean(true);
		generateTropicalStuffInMainWorld = this.preInitConfig.get("general", "Generate_tropicraft_in_main_world", true).getBoolean(true);
		shouldVolcanoesErupt = this.preInitConfig.get("general", "Should Volcanoes Erupt", false).getBoolean(false);
		isTropicraftInDebugMode = this.preInitConfig.get("general", "Is_Tropicraft_In_Debug_Mode", false).getBoolean(false);
		comeSailAway = this.preInitConfig.get("general", "Come Sail Away?", false).getBoolean(true);
	}

	/**
	 * Add the names of all mobs
	 */
	private void addMobNames() {
		addMobName("Iguana");
		addMobName("Beach Chair");
		addMobName("Beach Umbrella");
		addMobName("EIH", "Easter Island Head");
		addMobName("Sea Turtle");
		addMobName("Turtle Egg", "Sea Turtle Egg");
		addMobName("Wall Shell");
		addMobName("Beach Float");
		addMobName("Purified Sand");
		addMobName("TropiCreeper");
		addMobName("Marlin");
		addMobName("MOW", "Man 'O War");
		addMobName("Starfish");
		addMobName("VervetMonkey", "Vervet Monkey");
		addMobName("TropiSkelly");
		addMobName("Tree Frog", "Green Tree Frog");
		addMobName("Red Tree Frog");
		addMobName("Blue Tree Frog");
		addMobName("Yellow Tree Frog");
		addMobName("Lost Mask");
		addMobName("Dart");
		addMobName("Ashen");
		addMobName("Ashen Hunter");
		addMobName("Tropical Fish");
		addMobName("Seaweed");
		addMobName("Leaf Ball");
		addMobName("Coconut Bomb");
		addMobName("Koa Man");
		addMobName("EntityTropicalFishHook", "Tropical Fish Hook");
	}

	/**
	 * Used for outputting to the console in the dev environment only. Can be changed in the config under "Is_Tropicraft_In_Debug_Mode".
	 * 
	 * @param classOut The class the output is from, will be useful for finding where console output is coming from, to prevent spammy console!
	 * @param output what would normally be called by System.out.println(output)
	 */
	public static void debugOut(Class classOut, String output) {
		if (TropicraftMod.isTropicraftInDebugMode)
			System.out.println(String.format("[%s]: " + output, classOut.getName()));
	}

	/**
	 * Convenience method for adding a name for when an entity dies <br><br>
	 * LanguageRegistry.instance().addStringLocalization("entity.RedDragon.name", "en_US", "Red Dragon");<br>
	 * 
	 * @param name: entity.name.name and name will both be the same
	 */
	private void addMobName(String name) {
		LanguageRegistry.instance().addStringLocalization("entity.TropicraftMod." + name + ".name", "en_US", name);
	}

	/**
	 * Convenience method for adding a name for when an entity dies <br><br>
	 * LanguageRegistry.instance().addStringLocalization("entity.RedDragon.name", "en_US", "Red Dragon");<br>
	 * 
	 * @param longName ex: entity.RedDragon.name
	 * @param shortName ex: Red Dragon
	 */
	private void addMobName(String longName, String shortName) {
		LanguageRegistry.instance().addStringLocalization("entity.TropicraftMod." + longName + ".name", "en_US", shortName);
	}

	/**
	 * Hello, tropicraft devs. For each item you make, please make a itemId int for configuration purposes, and set said int = makeItemId("identifiernamehere");
	 * 
	 * DO NOT CHANGE THE ORDER OF THE ONES ALREADY HERE OR THEY WILL HAVE TO ALSO BE CHANGED IN THE CONFIG
	 */
	private void setItemIds() {
		bambooItemId = makeItemId("bambooItem");
		coconutItemId = makeItemId("coconutItem");
		irisItemId = makeItemId("irisItem");
		pineappleItemId = makeItemId("pineappleItem");
		tikiItemId = makeItemId("tikiItem");
		fertilizerId = makeItemId("fertilizer");
		tradeWindsId = makeItemId("tradeWindsRecord");
		easternIslesId = makeItemId("easternIslesRecord");
		bambooMugFullId = makeItemId("bambooMugFull");
		bambooMugEmptyId = makeItemId("bambooMugEmpty");

		for( int i = 0 ; i < 5 ; i++ ) {		
			eudialyteToolsIds[i] = makeItemId(eudialyteToolsNames[i]);
			zirconToolsIds[i] = makeItemId(zirconToolsNames[i]);
		}

		eudialyteItemId = makeItemId("eudialyteItem");
		zirconItemId = makeItemId("zirconItem");
		azuriteItemId = makeItemId("azuriteItem");
		pearlItemId = makeItemId("pearlItem");
		blackPearlItemId = makeItemId("blackPearlItem");
		frogLegId = makeItemId("frogLeg");
		cookedFrogLegId = makeItemId("cookedFrogLeg");
		chairId = makeItemId("chair");
		umbrellaId = makeItemId("umbrella");
		coconutBombId = makeItemId("coconutBomb");
		scaleId = makeItemId("scale");
		turtleShellId = makeItemId("turtleShell");
		makeItemIds(5, shellIds, shellIdNames);
		beachFloatId = makeItemId("beachFloat");
		rawMarlinId = makeItemId("rawMarlin");
		cookedMarlinId = makeItemId("cookedMarlin");
		bambooSpearId = makeItemId("bambooSpear");
		poisonSkinId = makeItemId("poisonSkin");
		paraDartId = makeItemId("paraDart");
		ashenMaskId = makeItemId("ashenMask");
		blowGunId = makeItemId("blowGun");
		makeItemIds(4, scaleArmorIds, scaleArmorNames);
		fishingNetId = makeItemId("fishingNet");
		fishBucketId = makeItemId("fishBucket");
		bucketTropicsWaterId = makeItemId("bucketTropicsWater");
		makeItemIds(4, fruitIds, fruitNames);
		pineappleCubeId = makeItemId("pineappleCube");
		coconutChunkId = makeItemId("coconutChunk");
		leafBallId = makeItemId("leafBall");
		mobEggId = makeItemId("mobEgg");
		encyclopediaId = makeItemId("encyclopedia");
		snorkelId = makeItemId("snorkel");
		flippersId = makeItemId("flippers");
		fishingRodId = makeItemId("fishingRod");
		daggerId = makeItemId("dagger");
		spongeItemId = makeItemId("sponge");
		bambooDoorItemId = makeItemId("bambooDoor");
		portalStarterId = makeItemId("portalStarter");
		//	seaweedId = makeItemId("seaweed");
		//	noriId = makeItemId("nori");
		snareTrapId = makeItemId("snareTrap");
		lemonJuiceId = makeItemId("lemonJuice");
		limeJuiceId = makeItemId("limeJuice");
		orangeJuiceId = makeItemId("orangeJuice");
		grapefruitJuiceId = makeItemId("grapefruitJuice");
		cocktailId = makeItemId("cocktail");
		flowerPotItemId = makeItemId("flowerPotItem");
		coffeeBeanId = makeItemId("coffeeBean");
		curareId = makeItemId("curare");
		nigelsJournalId = makeItemId("nigelsJournal");
		itemFrameId = makeItemId("tropicsItemFrame");
		koaFrameId = makeItemId("koaItemFrame");
		zirconiumId = makeItemId("zirconium");
		bambooStickId = makeItemId("bambooStick");
	}

	/**
	 * Initialize all items here
	 */
	private void initItems() {
		bambooItem = (new ItemBamboo(bambooItemId, bamboo)).setItemName("Bamboo");
		coconutItem = (new ItemCoconut(coconutItemId, coconut, "normal")).setItemName("Coconut");
		coconutBomb = (new ItemCoconut(coconutBombId, coconut, "bomb")).setItemName("Coconut Bomb");
		irisItem = (new ItemIris(irisItemId, irisFlower)).setItemName("Iris");
		pineappleItem = (new ItemPineapple(pineappleItemId, pineappleFlower)).setItemName("Pineapple");
		tikiItem = (new ItemTikiItem(tikiItemId)).setItemName("Tiki pole");
		fertilizer = (ItemTropicalMeal) (new ItemTropicalMeal(fertilizerId)).setItemName("Tropical Meal");
		froxTradeWinds = (new ItemTropicraftRecord(tradeWindsId, "Trade Winds")).setItemName("recordFrox");
		froxEasternIsles = (new ItemTropicraftRecord(easternIslesId, "Eastern Isles")).setItemName("recordFrox2");
		bambooMugEmpty = (new ItemTropicraft(bambooMugEmptyId)).setItemName("Empty Bamboo Mug").setCreativeTab(TropicraftMod.tabFood);
		bambooMugFull = (ItemTropicraftFood) ((ItemTropicraftFood) (new ItemPinaColada(bambooMugFullId, 4)).setItemName("Pi\361a Colada")).setPotionEffect(Potion.confusion.id, 3 * 2 + 2, 0, 1.0F);

		swordEudialyte = (new ItemTropicsSword(eudialyteToolsIds[0], EnumToolMaterialTropics.EUDIALYTE)).setItemName("aEudialyte");
		shovelEudialyte = (new ItemTropicsSpade(eudialyteToolsIds[1], EnumToolMaterialTropics.EUDIALYTE)).setItemName("bEudialyte");
		pickaxeEudialyte = (new ItemTropicsPickaxe(eudialyteToolsIds[2], EnumToolMaterialTropics.EUDIALYTE)).setItemName("cEudialyte");
		axeEudialyte = (new ItemTropicsAxe(eudialyteToolsIds[3], EnumToolMaterialTropics.EUDIALYTE)).setItemName("dEudialyte");
		hoeEudialyte = (new ItemTropicsHoe(eudialyteToolsIds[4], EnumToolMaterialTropics.EUDIALYTE)).setItemName("eEudialyte").setCreativeTab(TropicraftMod.tabTools);

		swordZircon = (new ItemTropicsSword(zirconToolsIds[0], EnumToolMaterialTropics.ZIRCON)).setItemName("aZircon");
		shovelZircon = (new ItemTropicsSpade(zirconToolsIds[1], EnumToolMaterialTropics.ZIRCON)).setItemName("bZircon");
		pickaxeZircon = (new ItemTropicsPickaxe(zirconToolsIds[2], EnumToolMaterialTropics.ZIRCON)).setItemName("cZircon");
		axeZircon = (new ItemTropicsAxe(zirconToolsIds[3], EnumToolMaterialTropics.ZIRCON)).setItemName("dZircon");
		hoeZircon = (new ItemTropicsHoe(zirconToolsIds[4], EnumToolMaterialTropics.ZIRCON)).setItemName("eZircon").setCreativeTab(TropicraftMod.tabTools);

		eudialyteItem = (new ItemTropicraft(eudialyteItemId)).setItemName("Eudialyte Item");
		zirconItem = (new ItemZircon(zirconItemId)).setItemName("Zircon Item");
		azuriteItem = new ItemTropicraft(azuriteItemId).setItemName("Azurite");

		pearlItem = new ItemTropicraft(pearlItemId).setItemName("Solo Pearl");
		blackPearlItem = new ItemTropicraft(blackPearlItemId).setItemName("Black Pearl");

		frogLeg = (new ItemTropicraft(frogLegId)).setItemName("Frog Leg");
		cookedFrogLeg = (new ItemTropicraftFood(cookedFrogLegId, 3, false)).setItemName("Cooked Frog Leg");

		chair = (new ItemChair(chairId)).setItemName("chair");
		umbrella = (new ItemUmbrella(umbrellaId)).setItemName("umbrella");

		scale = (new ItemTropicraft(scaleId)).setItemName("Scale");
		turtleShell = new ItemShell(turtleShellId).setItemName("Turtle Shell");

		shellCommon1 = (new ItemShell(shellIds[0])).setItemName("Solonox Shell");
		shellCommon2 = (new ItemShell(shellIds[1])).setItemName("Frox Conch");
		shellCommon3 = (new ItemShell(shellIds[2])).setItemName("Pab Shell");
		shellRare1 = (new ItemShell(shellIds[3])).setItemName("Rube Nautilus");
		shellStarfish = (new ItemShell(shellIds[4])).setItemName("Starfish Shell");

		beachFloat = (new ItemBeachFloat(beachFloatId)).setItemName("Beach Float");

		marlinRaw = new ItemTropicraftFood(rawMarlinId, 4, 0.2F, false).setItemName("Raw Marlin").setMaxStackSize(64);
		marlinCooked = new ItemTropicraftFood(cookedMarlinId, 5, 0.2F, false).setItemName("Seared Marlin").setMaxStackSize(64);

		bambooSpear = new ItemBambooSpear(bambooSpearId).setItemName("Bamboo Spear");
		poisonSkin = (new ItemTropicraft(poisonSkinId)).setItemName("Poison Skin");

		paraDart = new ItemTropicraftDart(paraDartId).setItemName("Paralyzing Dart").setCreativeTab(TropicraftMod.tabCombat);
		blowGun = new ItemBlowGun(blowGunId).setItemName("Blow Gun");
		ashenMask = (new ItemAshenMask(ashenMaskId, proxy.getArmorNumber("mask"), 0)).setItemName("AshenMask");

		scaleBoots = (new ItemTropicraftArmor(scaleArmorIds[0], proxy.getArmorNumber("scale"), 3)).setItemName("Scale Boots");
		scaleHelm = (new ItemTropicraftArmor(scaleArmorIds[1], proxy.getArmorNumber("scale"), 0)).setItemName("Scale Helm");
		scaleLeggings = (new ItemTropicraftArmor(scaleArmorIds[2], proxy.getArmorNumber("scale"), 2)).setItemName("Scale Leggings");
		scaleChestplate = (new ItemTropicraftArmor(scaleArmorIds[3], proxy.getArmorNumber("scale"), 1)).setItemName("Scale Chestplate");

		fishingNet = new ItemTropicraft(fishingNetId).setItemName("Fishing Net");
		fishBucket = new ItemFishBucket(fishBucketId).setItemName("Fish Bucket");        
		bucketTropicsWater = (new ItemTropicraftBucket(bucketTropicsWaterId, waterMovingTropics.blockID)).setIconCoord(11, 4).setItemName("Tropics Bucket").setContainerItem(Item.bucketEmpty);

		lemon = (new ItemTropicraftFood(fruitIds[0], 2, 0.2F, false)).setItemName("Lemon");
		lime = (new ItemTropicraftFood(fruitIds[1], 2, 0.2F, false)).setItemName("Lime");
		orange = (new ItemTropicraftFood(fruitIds[2], 3, 0.2F, false)).setItemName("Orange");
		grapefruit = (new ItemTropicraftFood(fruitIds[3], 4, 0.2F, false)).setItemName("Grapefruit");
		pineappleCube = (new ItemTropicraftFood(pineappleCubeId, 1, 0.1F, false)).setItemName("Pineapple Cubes");
		coconutChunk = (new ItemTropicraftFood(coconutChunkId, 1, 0.1F, false)).setItemName("coconut chunk");
		//seaweed = new ItemSeaweed(seaweedId).setItemName("Seaweed");
		//nori = new ItemTropicraftFood(noriId, 1, 0.2F, false).setItemName("Nori");        
		leafBall = (new ItemTropicraftLeafball(leafBallId)).setItemName("Leafball");

		mobEgg = new ItemMobEgg(mobEggId).setItemName("mobEgg");

		encTropica = new ItemTropBook(encyclopediaId, encyclopedia).setItemName("Encyclopedia Tropica");

		snorkel = new ItemWaterGear(snorkelId, proxy.getArmorNumber("WaterGear"), 0).setItemName("Snorkel");
		flippers = new ItemWaterGear(flippersId, proxy.getArmorNumber("WaterGear"), 3).setItemName("Flippers");

		fishingRodTropical = (new ItemTropicalFishingRod(fishingRodId)).setIconCoord(5, 4);

		dagger = (new ItemTropicsDagger(daggerId, EnumToolMaterialTropics.ZIRCON)).setItemName("dagger");
		sponge = (new ItemSponge(spongeItemId).setItemName("sponge"));
		bambooDoorItem = new ItemTropicalDoor(bambooDoorItemId, Material.wood).setItemName("bambooDoor");
		tropicsPortalEnchanter = (new ItemTropicsPortalStarter(portalStarterId, false)).setItemName("TRP");
		snareTrap = new ItemSnareTrap(snareTrapId).setItemName("Snare Trap");
		lemonJuice = new ItemFruitJuice(lemonJuiceId, 2, 0.2f).setItemName("Lemon Juice");
		limeJuice = new ItemFruitJuice(limeJuiceId, 2, 0.2f).setItemName("Lime Juice");
		orangeJuice = new ItemFruitJuice(orangeJuiceId, 3, 0.2f).setItemName("Orange Juice");
		grapefruitJuice = new ItemFruitJuice(grapefruitJuiceId, 4, 0.2f).setItemName("Grapefruit Juice");
		cocktail = new ItemCocktail(cocktailId).setItemName("Cocktail");
		flowerPotItem = (new ItemReed(flowerPotItemId, TropicraftMod.flowerPot)).setItemName("flowerPot").setCreativeTab(TropicraftMod.tabDecorations);
		coffeeBean = (ItemCoffeeBean) new ItemCoffeeBean(coffeeBeanId).setItemName("coffeeBean");
		curare = new ItemCurare(curareId).setItemName("curare");
		nigelsJournal = new ItemNigelJournal(nigelsJournalId, encyclopedia).setItemName("Nigel's Journal");
		tropiFrame = (new ItemHangingTropicraftEntity(itemFrameId, EntityHangingTropicraft.class, true)).setItemName("tropiFrame").setIconCoord(14, 12);
		koaFrame = (new ItemHangingTropicraftEntity(koaFrameId, EntityHangingTropicraft.class, false)).setItemName("tropiFrame2").setIconCoord(14, 12);
		zirconium = (new ItemTropicraft(zirconiumId)).setItemName("Zirconium");
		bambooStick = (new ItemTropicraft(bambooStickId)).setItemName("bambooStick");
	}

	/**
	 * Set names and iconIndexes of items here
	 */
	private void registerItems() {
		registerItem(bambooItem, "Bamboo", 0);		
		registerItem(irisItem, "Iris", 1);
		registerItem(pineappleItem, "Pineapple", 2);
		registerItem(bambooMugEmpty, "Empty Bamboo Mug", 3);
		registerItem(bambooMugFull, "Pi\321a Colada", 4);
		registerItem(frogLeg, "Frog Leg", 7);
		registerItem(cookedFrogLeg, "Cooked Frog Leg", 8);
		registerItem(coconutItem, "Coconut", 10);
		registerItem(fertilizer, "Fertilizer", 23);
		registerItem(tikiItem, "Tiki Torch Pole", 24);
		registerItem(swordZircon, "Zircon Spear", 27);
		registerItem(shovelZircon, "Zircon Shovel", 28);
		registerItem(pickaxeZircon, "Zircon Pickaxe", 29);
		registerItem(axeZircon, "Zircon Axe", 30);
		registerItem(hoeZircon, "Zircon Hoe", 31);
		registerItem(pearlItem, "Solo Pearl", 40);
		registerItem(blackPearlItem, "Black Pearl", 41);
		registerItem(swordEudialyte, "Eudialyte Sword", 43);
		registerItem(shovelEudialyte, "Eudialyte Shovel", 44);
		registerItem(pickaxeEudialyte, "Eudialyte Pickaxe", 45);
		registerItem(axeEudialyte, "Eudialyte Axe", 46);
		registerItem(hoeEudialyte, "Eudialyte Hoe", 47);
		registerItem(eudialyteItem, "Eudialyte Shard", 49);
		registerItem(zirconItem, "Zircon Crystal", 50);
		registerItem(froxTradeWinds, "Trade Winds", 80);
		registerItem(froxEasternIsles, "Eastern Isles", 83);
		registerItem(azuriteItem, "Azurite", 97);
		registerItem(chair, "Beach Chair", 11);
		registerItem(coconutBomb, "Coconut Bomb", 54);
		registerItem(scale, "Scale", 17);
		registerItem(turtleShell, "Turtle Shell", 42);
		registerItem(shellCommon1, "Solonox Shell", 18);
		registerItem(shellCommon2, "Frox Conch", 19);
		registerItem(shellCommon3, "Pab Shell", 20);
		registerItem(shellRare1, "Rube Nautilus", 21);
		registerItem(shellStarfish, "Starfish Shell", 22);
		registerItem(beachFloat, "Beach Float", 98);
		registerItem(marlinRaw, "Fresh Marlin", 81);
		registerItem(marlinCooked, "Seared Marlin", 82);
		registerItem(bambooSpear, "Bamboo Spear", 96);    
		registerItem(poisonSkin, "Poison Frog Skin", 9);
		registerItem(paraDart, "Paralyzing Dart", 57);
		registerItem(blowGun, "Blow Gun", 33);
		registerItem(ashenMask, "Ashen Mask", 224);
		registerItem(leafBall, "Leaf Ball", 55);
		registerItem(pineappleCube, "Pineapple Cube", 52);
		registerItem(coconutChunk, "Coconut Chunk", 6);
		registerItem(snorkel, "Snorkel", 38);
		registerItem(flippers, "Flippers", 39);
		registerItem(sponge, "Water Wand", 84);


		for(int i = 0 ; i < ItemAshenMask.maskTypeNames.length ; i ++) {
			LanguageRegistry.addName(new ItemStack(ashenMask, 1, i), ItemAshenMask.maskTypeNames[i]);
		}

		registerItem(scaleHelm, "Scale Helmet", 16);
		registerItem(scaleChestplate, "Scale Chestplate", 32);
		registerItem(scaleLeggings, "Scale Leggings", 48);
		registerItem(scaleBoots, "Scale Boots", 64);
		//	registerItem(seaweed, "Seaweed", 70);
		//	registerItem(nori, "Nori", 71);
		registerItem(fishingNet, "Fishing Net", 36);
		registerItem(fishBucket, "Fish Bucket", 35);
		registerItem(bucketTropicsWater, "Bucket O' Tropics Water", 104);
		registerItem(lemon, "Lemon", 65);
		registerItem(lime, "Lime", 66);
		registerItem(orange, "Orange", 67);
		registerItem(grapefruit, "Grapefruit", 68);
		registerItem(encTropica, "Encyclopedia Tropica", 13);
		registerItem(dagger, "Dagger", 51);
		registerItem(bambooDoorItem, "Bamboo Door", 56);
		registerItem(tropicsPortalEnchanter, "Tropics Portal Enchanter", 103);
		registerItem(snareTrap, "Snare Trap", 105);
		registerItem(lemonJuice, "Lemon Juice", 160);
		registerItem(limeJuice, "Lime Juice", 161);
		registerItem(orangeJuice, "Orange Juice", 162);
		registerItem(grapefruitJuice, "Grapefruit Juice", 163);
		registerItem(cocktail, "Cocktail");
		registerItem(flowerPotItem, "Flower Pot", 60);
		registerItem(curare, "Curare", 59);
		
		for (int i = 0; i < ItemCurare.effectNames.length; i++) {
			LanguageRegistry.addName(new ItemStack(curare, 1, i), "Curare");
		}
		
		registerItem(coffeeBean, "Coffee Beans", 176);
		registerItem(tropiFrame, "Tropicraft Item Frame", 85);
		registerItem(koaFrame, "Koa Item Frame", 85);
		registerItem(zirconium, "Zirconium", 86);
		registerItem(bambooStick, "Bamboo Stick", 88);		
		
		LanguageRegistry.addName(new ItemStack(zirconItem, 1, 0), "Zircon Crystal");
		LanguageRegistry.addName(new ItemStack(zirconItem, 1, 1), "Smelted Zircon");
		
		LanguageRegistry.addName(new ItemStack(coffeeBean, 1, 0), "Raw Coffee Beans");
		LanguageRegistry.addName(new ItemStack(coffeeBean, 1, 1), "Roasted Coffee Beans");
		LanguageRegistry.addName(new ItemStack(coffeeBean, 1, 2), "Coffee Berries");

		for(int i = 0 ; i < 5; i ++) {
			LanguageRegistry.addName(new ItemStack(chair, 1, i), "Beach Chair");
			LanguageRegistry.addName(new ItemStack(umbrella, 1, i), "Beach Umbrella");
			LanguageRegistry.addName(new ItemStack(beachFloat, 1, i), "Beach Float");
		}

		for (int i = 0; i < ItemMobEgg.names.length; i++) {
			LanguageRegistry.addName(new ItemStack(mobEgg, 1, i), ItemMobEgg.names[i]);
		}

		mobEgg.setIconIndex(144);
	}

	/**
	 * Fill item ids with names, used for items with metadata usually
	 * @param length length of how many to add
	 * @param array ids to use
	 * @param idNames names to use
	 */
	private void makeItemIds(int length, int[] array, String[] idNames) {
		for(int i = 0 ; i < length ; i ++) {
			array[i] = makeItemId(idNames[i]);
		}
	}

	//derp

	/**
	 * Hello, tropicraft devs. For each block you make, please make a blockId int for configuration purposes, and set said int = makeBlockId("identifiernamehere");
	 * 
	 * DO NOT CHANGE THE ORDER OF THE ONES ALREADY HERE OR THEY WILL HAVE TO ALSO BE CHANGED IN THE CONFIG
	 */
	private void setBlockIds() {
		bambooId = makeBlockId("bamboo");
		bambooBlockId = makeBlockId("bambooBlock");
		bambooFenceBlockId = makeBlockId("bambooFence");
		bambooFenceGateBlockId = makeBlockId("bambooFenceGate");		
		bambooStairId = makeBlockId("bambooStair");
		coconutBlockId = makeBlockId("coconut");
		tikiTorchId = makeBlockId("tikiTorch");
		oreEudialyteId = makeBlockId("oreEudialyte");
		oreZirconId = makeBlockId("oreZircon");
		oreAzuriteId = makeBlockId("oreAzurite");
		coralId1 = makeBlockId("coral1");
		flowerId1 = makeBlockId("flower1");
		thatchBlockId = makeBlockId("thatchBlock");
		thatchStairsId = makeBlockId("thatchStairs");
		tropicalWoodId = makeBlockId("tropicalWood");
		palmStairsId = makeBlockId("palmStairs");
		palmPlanksId = makeBlockId("palmPlanks");
		tropicLeavesId = makeBlockId("tropicLeaves");
		chunkBlockId = makeBlockId("chunkOHead");
		chunkStairsId = makeBlockId("chunkStairs");
		portalId = makeBlockId("portal");
		portalWallId = makeBlockId("portalWall");
		saplingsId = makeBlockId("saplings");
		fruitLeavesId = makeBlockId("fruitLeaves");
		irisId = makeBlockId("iris");
		pineappleId = makeBlockId("pineapple");
		sifterId = makeBlockId("sifter");
		stationaryWaterId = makeBlockId("stationaryWaterTropics");
		flowingWaterId = makeBlockId("flowingWaterTropics");
		tempLavaId = makeBlockId("tempLava");
		volcanoHelperId = makeBlockId("volcanoHelper");
		singleSlabId = makeBlockId("tropicalSlabSingle");
		doubleSlabId = makeBlockId("tropicalSlabDouble");
		bambooChestId = makeBlockId("bambooChest");
		purifiedSandId = makeBlockId("pufifiedSand");
		koaChestId = makeBlockId("koaChest");
		wallShellId = makeBlockId("walLShell");
		bambooDoorId = makeBlockId("bambooDoor");
		lemonSqueezerId = makeBlockId("lemonSqueezer");
		eihMixerId = makeBlockId("eihMixer");
		bambooMugId = makeBlockId("bambooMug");
		flowerPotId = makeBlockId("flowerPot");
		coffeePlantId = makeBlockId("coffeePlant");
		fountainId = makeBlockId("fountain");
		fountainFlowId = makeBlockId("fountainFlow");
		fountainStationaryId = makeBlockId("fountainStationary");
		curareBowlId = makeBlockId("curareBowl");
		tradeBlockId = makeBlockId("tradeBlock");	}

	/**
	 * Register all blocks here
	 */
	private void registerBlocks() {
		registerBlock(bamboo, "Bamboo", 2);
		registerBlock(oreAzurite, "Azurite Ore", 3);
		registerBlock(oreZircon, "Zircon Ore", 4);
		registerBlock(oreEudialyte, "Eudialyte Ore", 5);
		registerBlock(sifter, "Sifter", 8);
		registerBlock(coconut, "Coconut", 20);
		registerBlock(chunkStairs, "Chunk O' Stairs", 21);
		registerBlock(thatchBlock, "Thatch Bundle", 22);
		registerBlock(thatchStairs, "Thatch Stairs", 22);
		registerBlock(bambooBlock, "Bamboo Bundle", 23);
		registerBlock(bambooFence, "Bamboo Fence", 23);
		registerBlock(bambooFenceGate, "Bamboo Fence Gate", 23);
		registerBlock(bambooStairs, "Bamboo Stairs", 23);
		registerBlock(tikiTorch, "Tiki Torch", 24);
		registerBlock(pineappleFlower, "Pineapple", 26);
		registerBlock(irisFlower, "Iris", 27);
		registerBlock(palmPlanks, "Palm Planks", 80);
		registerBlock(palmStairs, "Palm Stairs", 80);
		registerBlock(bambooChest, "Bamboo Chest", 112);	
		registerBlock(tempLavaMoving, "Temp Lava");
		registerBlock(tropicraftPortal, "Tropics Portal", proxy.getUniqueTextureLoc());
		registerBlock(portalWall, "Tropics Portal Wall", Block.sandStone.blockIndexInTexture);
		LAPI.registerBlock(waterStillTropics, "Still Tropics Water");
		LAPI.registerBlock(waterMovingTropics, "Flowing Tropics Water");
		LAPI.registerLiquidPair(waterStillTropics, waterMovingTropics);
		registerBlock(volcanoHelper, "Volcano Helper");	
		registerBlock(purifiedSand, "Purified Sand", 1);
		registerBlock(koaChest, "Koa Chest", 112);
		registerBlock(wallShell, "Wall Shell");
		registerBlock(bambooDoor, "Tropical Door", 129);
		registerBlock(lemonSqueezer, "Lemon Squeezer");
		registerBlock(eihMixer, "Drink Mixer");
		registerBlock(bambooMug, "Bamboo Mug");
		registerBlock(flowerPot, "Flower Pot", 60);
		registerBlock(coffeePlant, "Coffee Plant", 176);
		registerBlock(fountain, "Water Fountain", 0);	
		registerBlock(curareBowl, "Curare Bowl", 61);
		registerBlock(tradeBlock, "Trade Block", thatchBlockId);	}

	/**
	 * All blocks that use metadata should be initialized and named here
	 */
	private void initAndNameItemBlocks() {

		Item.itemsList[tropicalSingleSlab.blockID] = (new ItemSlab(tropicalSingleSlab.blockID - 256, tropicalSingleSlab, tropicalDoubleSlab, false)).setItemName("tropicalSlab");
		Item.itemsList[tropicalDoubleSlab.blockID] = (new ItemSlab(tropicalDoubleSlab.blockID - 256, tropicalSingleSlab, tropicalDoubleSlab, true)).setItemName("tropicalSlab");

		for (int i = 0; i < 4; i++) {
			LanguageRegistry.addName(new ItemStack(Item.itemsList[tropicalSingleSlab.blockID], 1, i), getSlabNames()[i] + " Slab");
		}		

		Item.itemsList[coralCollection1.blockID] = ((new ItemColored(coralCollection1.blockID - 256, true)).setBlockNames(new String[]{
				"coral1", "coral2", "coral3", "coral4", "coral5", "coral6"
		}));

		for (int i = 0; i < 6; i++) {
			LanguageRegistry.addName(new ItemStack(Item.itemsList[coralCollection1.blockID], 1, i), "Coral");
		}

		Item.itemsList[flowerCollection1.blockID] = ((new ItemColored(flowerCollection1.blockID - 256, true)).setBlockNames(new String[]{
				"flower1", "flower2", "flower3", "flower4", "flower5", "flower6", "flower7", "flower8", "flower9", "flower10",
				"flower11", "flower12", "flower13", "flower14", "flower15"
		}));

		for (int i = 0; i < ((BlockTropicraftFlower)flowerCollection1).names.length; i++) {
			LanguageRegistry.addName(new ItemStack(Item.itemsList[flowerCollection1.blockID], 1, i), ((BlockTropicraftFlower)flowerCollection1).names[i]);
		}

		Item.itemsList[tropicLeaves.blockID] = ((new ItemColored(tropicLeaves.blockID - 256, true)).setBlockNames(new String[]{
				"palmtree", "fruittree", "hometree", "rainforest"
		}));

		for (int i = 0; i < ((BlockTropicraftLeaf)tropicLeaves).names.length; i++) {
			LanguageRegistry.addName(new ItemStack(Item.itemsList[tropicLeaves.blockID], 1, i), ((BlockTropicraftLeaf)tropicLeaves).names[i]);
		}

		Item.itemsList[tropicalWood.blockID] = ((new ItemColored(tropicalWood.blockID - 256, true)).setBlockNames(new String[]{"palm", "mahogany"}));

		LanguageRegistry.addName(new ItemStack(Item.itemsList[tropicalWood.blockID], 1, 0), "Palm Wood");
		LanguageRegistry.addName(new ItemStack(Item.itemsList[tropicalWood.blockID], 1, 1), "Mahogany Log");

		Item.itemsList[chunkBlock.blockID] = (new ItemColored(chunkBlock.blockID - 256, true)).setBlockNames(new String[]{"chunk"});

		LanguageRegistry.addName(new ItemStack(Item.itemsList[chunkBlock.blockID], 1, 0), "Chunk O' Head");

		Item.itemsList[saplings.blockID] = ((new ItemColored(saplings.blockID - 256, true)).setBlockNames(new String[]{
				"palm", "grapefruit", "lemon", "orange", "lime"
		}));		

		for (int i = 0; i < ((BlockTropicraftSapling)saplings).names.length; i++) {
			LanguageRegistry.addName(new ItemStack(Item.itemsList[saplings.blockID], 1, i), ((BlockTropicraftSapling)saplings).names[i]);
		}

		Item.itemsList[fruitTreeLeaves.blockID] = ((new ItemColored(fruitTreeLeaves.blockID - 256, true)).setBlockNames(new String[]{
				"grapefruit", "lemon", "orange", "lime"
		}));

		for (int i = 0; i < ((BlockFruitLeaves)fruitTreeLeaves).names.length; i++) {
			LanguageRegistry.addName(new ItemStack(Item.itemsList[fruitTreeLeaves.blockID], 1, i), ((BlockFruitLeaves)fruitTreeLeaves).names[i]);
		}

		for (int i = 0; i < EntityTropicalFish.names.length; i++) {
			LanguageRegistry.addName(new ItemStack(fishBucket, 1, i), EntityTropicalFish.names[i]);
		}
	}

	/**
	 * Initialize all blocks here
	 */
	private void initBlocks() {
		bamboo = new BlockBamboo(bambooId).setBlockName("Bamboo");
		bambooBlock = new BlockTropicalBundle(bambooBlockId).setHardness(1.0F).setResistance(0.1F).setBlockName("Bamboo block");
		bambooFence = new BlockBambooFence(bambooFenceBlockId).setBlockName("Bamboo fence");
		bambooFenceGate = new BlockTropicraftFenceGate(bambooFenceGateBlockId).setBlockName("Bamboo fence gate");
		coconut = new BlockCoconut(coconutBlockId).setBlockName("Coconut");
		tikiTorch = (BlockTikiTorch) new BlockTikiTorch(tikiTorchId).setBlockName("Tiki torch").setLightValue(.75F);
		oreEudialyte = (new BlockTropicraftOre(oreEudialyteId)).setHardness(2.1F).setResistance(5F).setStepSound(Block.soundStoneFootstep).setBlockName("oreEudialyte");
		oreZircon = (new BlockTropicraftOre(oreZirconId)).setHardness(3.2F).setResistance(3F).setStepSound(Block.soundStoneFootstep).setBlockName("oreZircon");
		oreAzurite = (new BlockTropicraftOre(oreAzuriteId)).setHardness(4.3F).setResistance(3F).setStepSound(Block.soundStoneFootstep).setBlockName("oreAzurite");
		coralCollection1 = (new BlockTropicraftCoral(coralId1, getCoralArray(), Material.water)).setLightValue(0.3F).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setBlockName("Tropicraft coral");
		flowerCollection1 = (new BlockTropicraftFlower(flowerId1, getFlowerArray(), getFlowerNames(), Material.plants)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setBlockName("Tropicraftflower");
		bambooStairs = new BlockTropicalStairs(bambooStairId, bambooBlock, 0).setBlockName("Bamboo Stairs");
		thatchBlock = (new BlockTropicalWood(thatchBlockId)).setHardness(0.1F).setResistance(0.1F).setBlockName("thatchBl");
		thatchStairs = (new BlockTropicalStairs(thatchStairsId, thatchBlock, 0)).setBlockName("thatchstair");
		tropicalWood = (new BlockTropicraftLog(tropicalWoodId, 0)).setHardness(2.0F).setBlockName("Tropical Wood").setStepSound(Block.soundWoodFootstep);
		palmPlanks = (new BlockTropicalWood(palmPlanksId)).setHardness(2.0F).setBlockName("PalmPlanks");
		palmStairs = (new BlockTropicalStairs(palmStairsId, palmPlanks, 0)).setBlockName("palmstair");
		tropicLeaves = (BlockTropicraftLeaf) (new BlockTropicraftLeaf(tropicLeavesId, getLeafArray(), getLeafNames())).setHardness(0.2F).setLightOpacity(1).setStepSound(Block.soundGrassFootstep).setBlockName("Tropicraft Leaves");
		chunkBlock = (new BlockTropicalStone(chunkBlockId)).setHardness(2.0F).setResistance(30F).setBlockName("headBblock");
		chunkStairs = (new BlockTropicalStairs(chunkStairsId, chunkBlock, 0)).setBlockName("chunkostair");
		tropicraftPortal = (new BlockTropicraftPortal(portalId, true)).setBlockName("portal");
		portalWall = (new BlockPortalWall(portalWallId)).setBlockName("portalWall").setStepSound(Block.soundStoneFootstep); 
		saplings = (new BlockTropicraftSapling(saplingsId, getSaplingArray(), getSaplingNames(), Material.plants)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setBlockName("Tropics Sapling");
		fruitTreeLeaves = (BlockFruitLeaves) (new BlockFruitLeaves(fruitLeavesId, 66, getFruitLeafNames())).setHardness(0.2F).setLightOpacity(1).setStepSound(Block.soundGrassFootstep).setBlockName("FruitLeaves");
		pineappleFlower = (BlockFlowerPineapple) (new BlockFlowerPineapple(pineappleId, 3)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setBlockName("Pineapple");
		irisFlower = (BlockFlowerIris) (new BlockFlowerIris(irisId, 3)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setBlockName("Iris");
		sifter = (new BlockSifter(sifterId, false)).setHardness(3.5F).setBlockName("sifter");
		waterMovingTropics = (new BlockFlowingLAPI(flowingWaterId, tropicwaterID, Material.water)).setHardness(100.0F).setLightOpacity(1).setBlockName("waterr").setCreativeTab(TropicraftMod.tabMisc);
		waterStillTropics = (new BlockStationaryLAPI(stationaryWaterId, tropicwaterID, Material.water)).setHardness(100.0F).setLightOpacity(1).setBlockName("waterrr").setCreativeTab(TropicraftMod.tabMisc);
		tempLavaMoving = new BlockTempLavaFlowing(tempLavaId).setBlockName("TLavaFlow");
		tempLavaMoving.blockIndexInTexture = Block.lavaMoving.blockIndexInTexture;
		volcanoHelper = new BlockVolcano(volcanoHelperId).setBlockName("Volcano");
		tropicalSingleSlab = (BlockHalfSlab) (new BlockTropicalStep(singleSlabId, false).setHardness(0.1F).setResistance(0.1F).setBlockName("tropicalSlab"));
		tropicalDoubleSlab = (BlockHalfSlab) (new BlockTropicalStep(doubleSlabId, true).setHardness(0.1F).setResistance(0.1F).setBlockName("tropicalSlab"));
		bambooChest = (new BlockBambooChest(bambooChestId)).setBlockName("bamboochest").setHardness(2.5F).setStepSound(Block.soundWoodFootstep);
		purifiedSand = (new BlockPurifiedSand(purifiedSandId, 0)).setHardness(0.5F).setStepSound(Block.soundSandFootstep).setBlockName("Purified Sand");
		koaChest = (new BlockKoaChest(koaChestId)).setBlockName("koachest").setHardness(2.5F).setStepSound(Block.soundWoodFootstep);
		wallShell = (new BlockWallShell(wallShellId)).setBlockName("wallshell").setHardness(0.0F).setResistance(0.0F).setStepSound(Block.soundGlassFootstep);
		bambooDoor = (new BlockTropicalDoor(bambooDoorId, Material.wood)).setBlockName("bambooDoor").setHardness(1.0F).setResistance(.5F).setRequiresSelfNotify();
		lemonSqueezer = new BlockLemonSqueezer(lemonSqueezerId).setBlockName("lemonSqueezer").setHardness(2.0f).setResistance(30.0f).setStepSound(Block.soundStoneFootstep).setRequiresSelfNotify();
		eihMixer = (new BlockEIHMixer(eihMixerId)).setBlockName("eihMixer").setHardness(2.0f).setResistance(30.0f).setStepSound(Block.soundStoneFootstep).setRequiresSelfNotify();
		bambooMug = new BlockBambooMug(bambooMugId).setBlockName("bambooMug").setHardness(0.0f).setStepSound(Block.soundPowderFootstep);
		flowerPot = (new BlockFlowerPotTC(flowerPotId)).setHardness(0.0F).setStepSound(Block.soundPowderFootstep).setBlockName("flowerPot");
		coffeePlant = new BlockCoffeePlant(coffeePlantId).setHardness(0.0f).setStepSound(Block.soundGrassFootstep).setBlockName("coffeePlant");
		fountain = (new BlockFountain(fountainId)).setHardness(10.0F).setBlockName("fountain").setStepSound(Block.soundStoneFootstep).setBlockName("fountain");
		curareBowl = (new BlockCurareBowl(curareBowlId)).setHardness(4F).setBlockName("curareBowl").setStepSound(Block.soundSandFootstep);
		tradeBlock = (new BlockPurchasePlate(tradeBlockId++, 65, EnumMobType.players, Material.circuits)).setBlockName("tradeBlock").setCreativeTab(TropicraftMod.tabMisc);	}


	/**
	 * Register all tile entities in this method, called during mod init
	 */
	private void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntitySifter.class, "Sifter");
		GameRegistry.registerTileEntity(TileEntityVolcano.class, "VolcanoHelper");
		GameRegistry.registerTileEntity(TileEntityBambooChest.class, "BambooChest");
		GameRegistry.registerTileEntity(TileEntityKoaChest.class, "KoaChest");
		GameRegistry.registerTileEntity(TileEntityWallShell.class, "Wall Shell");
		GameRegistry.registerTileEntity(TileEntityLemonSqueezer.class, "LemonSqueezer");
		GameRegistry.registerTileEntity(TileEntityEIHMixer.class, "EIHMixer");
		GameRegistry.registerTileEntity(TileEntityBambooMug.class, "BambooMug");
		GameRegistry.registerTileEntity(TileEntityFlowerPot.class, "FlowerPot");
		GameRegistry.registerTileEntity(TileEntityCurareBowl.class, "CurareBowl");
GameRegistry.registerTileEntity(TileEntityPurchasePlate.class, "TradeBlock");	}

	@PostInit
	public void modsLoaded(FMLPostInitializationEvent event) {

	}

	/**
	 * @return Texture indicies for coral
	 */
	private static int[] getCoralArray() {	//lol booyeah	//:D
		int[] textureIndices = new int[6];
		for (int i = 0; i < textureIndices.length; i++) {
			textureIndices[i] = (96 + i);
		}
		return textureIndices;
	}

	/**
	 * @return Texture indicies for flowers
	 */
	private static int[] getFlowerArray() {	//lol booyeah	//:D
		int[] textureIndices = new int[15];
		for (int i = 0; i < textureIndices.length; i++) {
			textureIndices[i] = (32 + i);
		}
		return textureIndices;
	}

	private static String[] getSlabNames() {
		return new String[] {
				"Bamboo",
				"Thatch",
				"Chunk O'",
				"Palm"
		};
	}

	/**
	 * 
	 * @return A string array of flower names for flowerCollection1
	 */
	private static String[] getFlowerNames() {
		return new String[]{
				"Commelina Diffusa",
				"Crocosmia",
				"Orchid",
				"Canna",
				"Anemone",
				"Orange Anthurium",
				"Red Anthurium",
				"Magic Mushroom",
				"Pathos",
				"Acai Vine",
				"Croton",
				"Dracaena",
				"Fern",
				"Foliage",
				"Bromeliad"
		};
	}

	public static int[] getSaplingArray() {
		int[] id = new int[5];
		for (int i = 0; i < id.length; i++) {
			id[i] = (48 + i);
		}
		return id;
	}

	public static String[] getSaplingNames() {
		return new String[]{
				"Palm Sapling",
				"Grapefruit Sapling",
				"Lemon Sapling",
				"Orange Sapling",
				"Lime Sapling"
		};
	}

	public static int[] getLeafArray() {

		// Texture array ordering is:
		//  [0] = palm leaf (fancy)
		//  [1] = fruit leaf (fancy)
		//  [2] = home tree leaf (fancy)
		//  [3] = jungle tree leaf (fancy)
		//  [4] = palm leaf (fast)
		//  [5] = fruit leaf (fast)
		//  [6] = home tree leaf (fast)
		//  [7] = jungle tree leaf (fast)
		int[] id = new int[8];
		id[0] = 16;
		id[1] = 65;
		id[2] = 76;
		id[3] = 65;
		id[4] = 64;
		id[5] = 70;
		id[6] = 76;
		id[7] = 70;
		return id;
	}

	public static String[] getLeafNames() {
		return new String[]{
				"Palm Leaves",
				"Fruit Tree Leaves",
				"Giant Kapok Leaves",
				"Rainforest Leaves"
		};
	}

	public static String[] getFruitLeafNames() {
		return new String[]{
				"Grapefruit Leaves",
				"Lemon Leaves",
				"Orange Leaves",
				"Lime Leaves"
		};
	}

	/**
	 * <b><i>HOW TO USE:</b></i> <br>
	 *  1. idName is (i'm pretty sure) how it will show up in the config file, a mapping of sorts, name it whatever you want this block to show up as <br>
	 *  2. set the id for the block you are making = this method (example: bambooBlockId = makeBlockId("bamboo");) <br>
	 *  3. make sure you do this in the following method: setBlockIds()<br>
	 *  4. ???<br>
	 *  5. <b>Profit!</b><br>
	 * @param idName String representation of the block in the config file i'm pretty sure
	 * @return new id for this block
	 */
	private int makeBlockId(String idName) {
		int id = id(true);
		return this.preInitConfig.get(Configuration.CATEGORY_BLOCK, idName, id).getInt(id);
	}

	/**
	 * <b><i>HOW TO USE:</b></i> <br>
	 *  1. idName is (i'm pretty sure) how it will show up in the config file, a mapping of sorts, name it whatever you want this item to show up as <br>
	 *  2. set the id for the item you are making = this method (example: bambooItemId = makeItemId("bambooItem");) <br>
	 *  3. make sure you do this in the following method: setItemIds()<br>
	 *  4. ???<br>
	 *  5. <b>Profit!</b><br>
	 * @param idName String representation of the item in the config file i'm pretty sure
	 * @return new id for this item
	 */
	private int makeItemId(String idName) {
		int id = id(false);
		return this.preInitConfig.get(Configuration.CATEGORY_ITEM, idName, id).getInt(id);
	}

	/**
	 * Registers block and sets name
	 * @param block Block to register
	 * @param blockName Name for this block as it shows up in game
	 */
	private void registerBlock(Block block, String blockName) {
		GameRegistry.registerBlock(block, blockName);
		LanguageRegistry.addName(block, blockName);
	}

	/**
	 * Registers block and sets name, maps to ItemBlock subclass
	 * @param block Block to register
	 * @param blockName Name for this block as it shows up in game
	 * @param itemClass Subclass of ItemBlock that is mapped to this class
	 */
	private void registerBlock(Block block, String blockName, Class<? extends ItemBlock> itemClass) {
		GameRegistry.registerBlock(block, itemClass, blockName);
		LanguageRegistry.addName(block, blockName);
	}

	/**
	 * Registers block, sets name, sets blockIndexInTexture
	 * @param block Block to register
	 * @param blockName Name for this block as it shows up in game
	 * @param blockIndexInTexture blockIndexInTexture to set for the block
	 */
	private void registerBlock(Block block, String blockName, int blockIndexInTexture) {
		block.blockIndexInTexture = blockIndexInTexture;
		registerBlock(block, blockName);
	}

	/**
	 * Registers block, sets name, sets blockIndexInTexture, maps to ItemBlock subclass
	 * @param block Block to register
	 * @param blockName Name for this block as it shows up in game
	 * @param blockIndexInTexture blockIndexInTexture to set for the block
	 * @param itemClass Subclass of ItemBlock that is mapped to this class
	 */
	private void registerBlock(Block block, String blockName, Class<? extends ItemBlock> itemClass, int blockIndexInTexture) {
		block.blockIndexInTexture = blockIndexInTexture;
		registerBlock(block, blockName, itemClass);
	}

	/**
	 * Names item
	 * @param toBeNamed Item to be named
	 * @param name Name for item
	 */
	private void registerItem(Item toBeNamed, String name) {
		LanguageRegistry.addName(toBeNamed, name);
	}

	/**
	 * Sets name and iconIndex for an item
	 * @param toBeNamed Item to be named/iconIndex set
	 * @param name Name for item
	 * @param iconIndex iconIndex to be set for the toBeNamed item
	 */
	private void registerItem(Item toBeNamed, String name, int iconIndex) {
		LanguageRegistry.addName(toBeNamed, name);
		toBeNamed.setIconIndex(iconIndex);
	}


	/**
	 * Increments the current block id counter
	 * 
	 * @Param type If type == true, it is a block, if type == false, it is an item
	 * @return the id of the next block to be added
	 */
	private int id(boolean type) {
		if(type)
			return ++currentBlockId;
		else
			return ++currentItemId;
	}

	/**
	 * Helper method used to teleport the player to the tropics (or back from the tropics)
	 * @param player Player being teleported
	 */
	public static void teleportPlayerToTropics(EntityPlayerMP player)
	{
		if (player.dimension == 0) {
			TeleporterTropics tropicsTeleporter = new TeleporterTropics(MinecraftServer.getServer().worldServerForDimension(tropicsDimensionID));
			ServerConfigurationManager scm = MinecraftServer.getServer().getConfigurationManager();
			scm.transferPlayerToDimension(player, tropicsDimensionID, tropicsTeleporter);
		} else 
			if (player.dimension == tropicsDimensionID) {
				TeleporterTropics tropicsTeleporter = new TeleporterTropics(MinecraftServer.getServer().worldServerForDimension(0));
				ServerConfigurationManager scm = MinecraftServer.getServer().getConfigurationManager();
				scm.transferPlayerToDimension(player, 0, tropicsTeleporter);
			} else {
				TeleporterTropics tropicsTeleporter = new TeleporterTropics(MinecraftServer.getServer().worldServerForDimension(player.dimension));
				ServerConfigurationManager scm = MinecraftServer.getServer().getConfigurationManager();
				scm.transferPlayerToDimension(player, tropicsDimensionID, tropicsTeleporter);
			}
	}

	/**
	 * Mystical Corosus method used to speed up loading chunks when entering the realm?
	 * @param ws WorldServer instance
	 */
	public void initialWorldChunkLoad(WorldServer ws)
	{

		MinecraftServer mcs = FMLCommonHandler.instance().getMinecraftServerInstance();

		short var1 = 196;
		long var2 = System.currentTimeMillis();
		//mcs.setUserMessage("menu.generatingTerrain");

		int var4 = -127;

		//for (int var4 = 0; var4 < 1; ++var4)
		//{
		mcs.logger.info("Preparing start region for level " + var4);
		WorldServer var5 = ws;//mcs.theWorldServer[var4];
		ChunkCoordinates var6 = var5.getSpawnPoint();

		for (int var7 = -var1; var7 <= var1 && mcs.isServerRunning(); var7 += 16)
		{
			for (int var8 = -var1; var8 <= var1 && mcs.isServerRunning(); var8 += 16)
			{
				long var9 = System.currentTimeMillis();

				if (var9 < var2)
				{
					var2 = var9;
				}

				if (var9 > var2 + 1000L)
				{
					int var11 = (var1 * 2 + 1) * (var1 * 2 + 1);
					int var12 = (var7 + var1) * (var1 * 2 + 1) + var8 + 1;
					//mcs.outputPercentRemaining("Preparing spawn area", var12 * 100 / var11);
					var2 = var9;
				}

				var5.theChunkProviderServer.loadChunk(var6.posX + var7 >> 4, var6.posZ + var8 >> 4);

				/*while (var5.updatingLighting() && mcs.isServerRunning())
				{
					;
				}*/
			}
		}
		//}

		//mcs.clearCurrentTask();
	}

	public static Object getPrivateValueBoth(Class var0, Object var1, String obf, String mcp) {
		try {
			return ModLoader.getPrivateValue(var0, var1, obf);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try{
			return ModLoader.getPrivateValue(var0, var1, mcp);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

	public static void setPrivateValueBoth(Class var0, Object var1, String obf, String mcp, Object var3) {
		try {
			ModLoader.setPrivateValue(var0, var1, obf, var3);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static boolean renderSpecials(EntityPlayer par1EntityPlayer,
			float par2) {
		rotatePlayer(par1EntityPlayer);
		return proxy.getTropiPlayerRenderer().renderSpecials(par1EntityPlayer, par2);
	}
	
	public static void rotatePlayer(EntityPlayer par1EntityPlayer) {
		proxy.getTropiPlayerRenderer().rotatePlayer(par1EntityPlayer);
	}
	
	public static void renderGameOverlay(float par1, boolean par2, int par3, int par4) {
		proxy.renderGameOverlay(par1, par2, par3, par4);
	}
	
	public static void registerTropicraftArmorForUpdate(ITropicraftArmor armorItem) {
		armorList.add(armorItem);
	}
}
