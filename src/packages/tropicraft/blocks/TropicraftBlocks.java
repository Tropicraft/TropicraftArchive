package tropicraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHalfSlab;
import net.minecraft.block.EnumMobType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemColored;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import tropicraft.ModIds;
import tropicraft.blocks.tileentities.TileEntityBambooChest;
import tropicraft.blocks.tileentities.TileEntityBambooMug;
import tropicraft.blocks.tileentities.TileEntityCurareBowl;
import tropicraft.blocks.tileentities.TileEntityEIHMixer;
import tropicraft.blocks.tileentities.TileEntityFirePit;
import tropicraft.blocks.tileentities.TileEntityFlowerPot;
import tropicraft.blocks.tileentities.TileEntityKoaChest;
import tropicraft.blocks.tileentities.TileEntityPurchasePlate;
import tropicraft.blocks.tileentities.TileEntitySifter;
import tropicraft.creative.TropiCreativeTabs;
import tropicraft.items.ItemBongoDrum;
import tropicraft.items.ItemBuildingBlock;
import tropicraft.items.ItemTallFlowerBlock;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class TropicraftBlocks {

	/**
	 * Bamboo chute block
	 */
	public static Block bambooChute;

	public static Block coconut;

	public static Block bambooBundle;

	public static Block chunkOHead;

	public static Block tallFlower;

	/**
	 * Hopefully can be up to 16 different types of fence :o
	 */
	public static Block tropicsFence;

	/**
	 * Hopefully can be up to 16 different types of fence gate :o
	 */
	public static Block tropicsFenceGate;

	/**
	 * Will be used to store all kinds of Tropics aesthetic and building blocks. <br>
	 * Metadata:<br>
	 * 0: Thatch block<br>
	 */
	public static Block tropicsBuildingBlock;

	/**
	 * Single slab of bamboo, thatch, palm, and chunk o head
	 */
	public static BlockHalfSlab tropicsSingleSlab;

	/**
	 * Double slab of bamboo, thatch, palm, and chunk o head
	 */
	public static BlockHalfSlab tropicsDoubleSlab;

	public static Block bambooStairs;

	public static Block chunkStairs;

	public static Block thatchStairs;

	public static Block palmStairs;

	/**
	 * The 16 one-block-height flowers
	 */
	public static Block tropicsFlowers;

	public static Block coral;

	public static Block tropicsWaterFlowing;

	public static Block tropicsWaterStationary;

	/** Ore blocks - Eudialyte, Zircon, Azurite */
	public static Block tropiOres;

	public static Block portal;

	/** Fruit tree leaves in order: Lemon, Lime, Orange, Grapefruit */
	public static Block fruitLeaves;

	public static Block saplings;

	public static Block sifter;

	public static Block purifiedSand;

	public static Block bambooDoor;

	public static Block treeWood;

	public static Block bambooChest;

	public static Block flowerPot;

	public static Block tikiTorch;

	public static Block coffeePlant;

	public static Block eihMixer;

	public static Block bambooMug;

	public static Block tropicsLeaves;

	public static Block portalWall;
	
	public static Block koaChest;
	
	public static Block tradeBlock;

	public static Block bongoDrum;
	
	public static Block curareBowl;
	
	public static Block particleBlock;
	
	private static final String[] slabNames = { "Bamboo", "Thatch", "Chunk O'", "Palm" };

	private static final String[] flowerNames = {"Commelina Diffusa", "Crocosmia", "Orchid", "Canna", "Anemone", "Orange Anthurium", "Red Anthurium", "Magic Mushroom", "Pathos", "Acai Vine",
		"Croton", "Dracaena", "Fern", "Foilage", "Bromeliad"};

	private static final String[] coralNames = {"Coral", "Coral", "Coral", "Coral", "Coral", "Coral"};

	public static void init() {
		bambooChute = new BlockBambooChute(ModIds.BLOCK_BAMBOO_CHUTE_ID, Material.plants).setUnlocalizedName("bambooChuteBlock").setCreativeTab(null).setHardness(0.0F).setStepSound(Block.soundGrassFootstep);
		coconut = new BlockCoconut(ModIds.BLOCK_COCONUT_ID, Material.ground, TropiCreativeTabs.tabBlock).setUnlocalizedName("coconutBlock");
		bambooBundle = new BlockBundle(ModIds.BLOCK_BAMBOO_BUNDLE_ID, "bambooBundle").setHardness(1.0F).setResistance(0.1F).setUnlocalizedName("bambooBundle");
		chunkOHead = new BlockTropicraftStone(ModIds.BLOCK_CHUNKOHEAD_ID, "chunk").setHardness(2.0F).setResistance(30F).setUnlocalizedName("chunkOHead");
		tallFlower = (new BlockTallFlower(ModIds.BLOCK_TALLFLOWER_ID)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("tallFlower");
		tropicsFence = new BlockTropicsFence(ModIds.BLOCK_TROPICSFENCE_ID, "bambooBundle_side").setHardness(0.25F).setUnlocalizedName("tropicsFence");
		tropicsFenceGate = new BlockTropicsFenceGate(ModIds.BLOCK_TROPICSFENCEGATE_ID).setHardness(0.25F).setUnlocalizedName("tropicsFenceGate");
		tropicsBuildingBlock = new BlockBuildingBlock(ModIds.BLOCK_BUILDINGBLOCK_ID).setUnlocalizedName("buildingBlock");
		tropicsSingleSlab = (BlockHalfSlab) (new BlockTropicalStep(ModIds.BLOCK_SINGLESLAB_ID, false).setHardness(0.1F).setResistance(0.1F).setUnlocalizedName("tropicsSlabSingle"));
		tropicsDoubleSlab = (BlockHalfSlab) (new BlockTropicalStep(ModIds.BLOCK_DOUBLESLAB_ID, true).setHardness(0.1F).setResistance(0.1F).setUnlocalizedName("tropicsSlabDouble"));
		bambooStairs = new BlockTropicraftStairs(ModIds.BLOCK_BAMBOOSTAIRS_ID, bambooBundle, 0, "bambooBundle_side").setHardness(0.25F).setResistance(0.1F).setUnlocalizedName("Bamboo Stairs");
		thatchStairs = new BlockTropicraftStairs(ModIds.BLOCK_THATCHSTAIRS_ID, tropicsBuildingBlock, 0, "thatch_side").setHardness(0.1F).setResistance(0.1F).setUnlocalizedName("Thatch Stairs");
		chunkStairs = new BlockTropicraftStairs(ModIds.BLOCK_CHUNKSTAIRS_ID, chunkOHead, 0, "chunk").setUnlocalizedName("Chunk Stairs");
		palmStairs = new BlockTropicraftStairs(ModIds.BLOCK_PALMSTAIRS_ID, tropicsBuildingBlock, 1, "palmPlanks").setHardness(1F).setResistance(1F).setUnlocalizedName("Palm Stairs");
		tropicsFlowers = (new BlockTropicraftFlower(ModIds.BLOCK_TROPICSFLOWERS_ID, flowerNames)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("Tropicraftflower");
		coral = (new BlockTropicsCoral(ModIds.BLOCK_CORAL_ID, coralNames, Material.water)).setLightValue(0.3F).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("tropicraftCoral");
		tropicsWaterFlowing = (BlockFluidTropics)(new BlockFlowingTropics(ModIds.BLOCK_TROPICSWATERFLOWING_ID, Material.water)).setHardness(100.0F).setLightOpacity(3).setUnlocalizedName("water");
		tropicsWaterStationary = (new BlockStationaryTropics(ModIds.BLOCK_TROPICSWATERSTATIONARY_ID, Material.water)).setHardness(100.0F).setLightOpacity(3).setUnlocalizedName("water");
		tropiOres = new BlockTropicraftOres(ModIds.BLOCK_TROPIORES_ID, getOreDisplayNames(), getOreImageNames()).setUnlocalizedName("blockOres");
		portal = new BlockTropicraftPortal(ModIds.BLOCK_PORTAL_ID).setUnlocalizedName("portal").setCreativeTab(null);
		fruitLeaves = new BlockFruitLeaves(ModIds.BLOCK_FRUITLEAVES_ID, getFruitLeavesDisplayNames(), getFruitLeavesImageNames()).setHardness(0.2F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("fruitLeaves");
		saplings = new BlockTropicraftSapling(ModIds.BLOCK_SAPLINGS_ID, Material.plants, "sapling_", getSaplingDisplayNames(), getSaplingImageNames()).setUnlocalizedName("saplings");
		purifiedSand = new BlockPurifiedSand(ModIds.BLOCK_PURIFIEDSAND_ID).setHardness(0.5F).setUnlocalizedName("purifiedSand");
		sifter = new BlockSifter(ModIds.BLOCK_SIFTER_ID).setHardness(3.5F).setUnlocalizedName("sifter");
		bambooDoor = (new BlockBambooDoor(ModIds.BLOCK_BAMBOODOOR_ID)).setHardness(1.0F).setResistance(.5F).setUnlocalizedName("Bamboo Door");
		treeWood = new BlockTropicraftLog(ModIds.BLOCK_TREEWOOD_ID, Material.wood, "", getLogDisplayNames(), getLogImageNames()).setUnlocalizedName("treeWood").setHardness(2.0F).setStepSound(Block.soundWoodFootstep);
		bambooChest = (new BlockBambooChest(ModIds.BLOCK_BAMBOOCHEST_ID)).setUnlocalizedName("bamboochest").setHardness(2.5F).setStepSound(Block.soundWoodFootstep);
		flowerPot = new BlockFlowerPot(ModIds.BLOCK_FLOWERPOT_ID).setUnlocalizedName("Flower Pot Tropicraft").setHardness(0.0F).setStepSound(Block.soundPowderFootstep);
		tikiTorch = new BlockTikiTorch(ModIds.BLOCK_TIKITORCH_ID).setUnlocalizedName("Tiki Torch Blawk").setLightValue(.75F);
		bambooMug = new BlockBambooMug(ModIds.BLOCK_BAMBOOMUG_ID).setUnlocalizedName("bambooMug").setHardness(0.0f).setStepSound(Block.soundPowderFootstep);
		coffeePlant = new BlockCoffeePlant(ModIds.BLOCK_COFFEEPLANT_ID).setUnlocalizedName("coffeePlant").setHardness(0.0f).setStepSound(Block.soundGrassFootstep);
		eihMixer = new BlockEIHMixer(ModIds.BLOCK_EIHMIXER_ID).setUnlocalizedName("eihMixer").setHardness(2.0f).setResistance(30.0f).setStepSound(Block.soundStoneFootstep).setCreativeTab(TropiCreativeTabs.tabFood);
		tropicsLeaves = new BlockTropicsLeaves(ModIds.BLOCK_RAINFORESTLEAVES_ID, getTropicsLeavesDisplayNames(), getTropicsLeavesImageNames()).setHardness(0.2F).setLightOpacity(1).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("Tropics Leaves");
		portalWall = new BlockPortalWall(ModIds.BLOCK_PORTALWALL_ID).setUnlocalizedName("portalWall").setStepSound(Block.soundStoneFootstep).setTextureName("sandstone");
		koaChest = (new BlockKoaChest(ModIds.BLOCK_KOACHEST_ID)).setUnlocalizedName("koaChest").setHardness(2.5F).setStepSound(Block.soundWoodFootstep);
		tradeBlock = (new BlockPurchasePlate(ModIds.BLOCK_TRADEPLATE_ID, EnumMobType.players, Material.circuits)).setCreativeTab(TropiCreativeTabs.tabBlock).setUnlocalizedName("tradeBlock");
		curareBowl = (new BlockCurareBowl(ModIds.BLOCK_CURAREBOWL_ID)).setHardness(4F).setUnlocalizedName("curareBowl").setStepSound(Block.soundSandFootstep);
		particleBlock = (new BlockParticleMaker(ModIds.BLOCK_PARTICLEBLOCK_ID)).setCreativeTab(TropiCreativeTabs.tabBlock).setLightValue(1F).setUnlocalizedName("firePit");
		
		registerBlock(curareBowl, "Curare Bowl");
		bongoDrum = new BlockBongoDrum(ModIds.BLOCK_BONGODRUMS_ID).setCreativeTab(TropiCreativeTabs.tabDecorations).setUnlocalizedName("bongoDrums").setHardness(0.0f).setStepSound(Block.soundWoodFootstep);
		
		GameRegistry.registerBlock(bongoDrum, ItemBongoDrum.class, "bongoDrum");
		for (int i = 0; i < getBongoDrumDisplayNames().length; i++) {
			LanguageRegistry.addName(new ItemStack(bongoDrum, 1, i), getBongoDrumDisplayNames()[i]);
		}

		registerBlock(portalWall, "Tropicraft Portal Wall");
		registerBlock(tikiTorch, "Tiki Torch Block");
		registerBlock(flowerPot, "Flower Pot Block");
		registerBlock(bambooChest, "Bamboo Chest");
		registerBlock(koaChest, "Koa Chest");
		registerBlock(tradeBlock, "Koa Trade Plate");
		registerMultiBlock(treeWood, getLogDisplayNames());
		registerBlock(bambooDoor, "Bamboo Door Block");
		registerBlock(purifiedSand, "Purified Sand");
		registerBlock(sifter, "Sifter");
		registerBlock(particleBlock, "Fire Pit");

		Item.itemsList[saplings.blockID] = ((new ItemColored(saplings.blockID - 256, true)).setBlockNames(getSaplingImageNames()));

		for (int i = 0; i < getSaplingDisplayNames().length; i++) {
			LanguageRegistry.addName(new ItemStack(Item.itemsList[saplings.blockID], 1, i), getSaplingDisplayNames()[i]);
		}

		Item.itemsList[tropicsLeaves.blockID] = ((new ItemColored(tropicsLeaves.blockID - 256, true)).setBlockNames(new String[]{
				"palmtree", "fruittree", "hometree", "rainforest"
		}));

		for (int i = 0; i < getTropicsLeavesDisplayNames().length; i++) {
			LanguageRegistry.addName(new ItemStack(Item.itemsList[tropicsLeaves.blockID], 1, i), getTropicsLeavesDisplayNames()[i]);
		}

		Item.itemsList[fruitLeaves.blockID] = ((new ItemColored(fruitLeaves.blockID - 256, true)).setBlockNames(getFruitLeavesImageNames()));

		for (int i = 0; i < getFruitLeavesDisplayNames().length; i++) {
			LanguageRegistry.addName(new ItemStack(Item.itemsList[fruitLeaves.blockID], 1, i), getFruitLeavesDisplayNames()[i]);
		}
		
		registerBlock(portal, "Tropics Portal");

		Item.itemsList[tropiOres.blockID] = ((new ItemColored(tropiOres.blockID - 256, true)).setBlockNames(getOreImageNames()));

		for (int i = 0; i < getOreDisplayNames().length; i++) {
			LanguageRegistry.addName(new ItemStack(Item.itemsList[tropiOres.blockID], 1, i), getOreDisplayNames()[i]);
		}

		registerBlock(tropicsWaterFlowing, "Flowing Tropics Water");
		registerBlock(tropicsWaterStationary, "Stationary Tropics Water");

		//LiquidContainerRegistry.registerLiquid(new LiquidContainerData(new LiquidStack(tropicsWaterStationary, LiquidContainerRegistry.BUCKET_VOLUME), new ItemStack(TropicraftItems.bucketTropicsWater), new ItemStack(Item.bucketEmpty)));
		//LiquidDictionary.getOrCreateLiquid("Tropics Water", new LiquidStack(tropicsWaterStationary, LiquidContainerRegistry.BUCKET_VOLUME));

		Item.itemsList[coral.blockID] = ((new ItemColored(coral.blockID - 256, true)).setBlockNames(new String[] {
				"coral1", "coral2", "coral3", "coral4", "coral5", "coral6"
		}));

		for (int i = 0; i < 6; i++) {
			LanguageRegistry.addName(new ItemStack(Item.itemsList[coral.blockID], 1, i), "Coral");
		}

		Item.itemsList[tropicsFlowers.blockID] = ((new ItemColored(tropicsFlowers.blockID - 256, true)).setBlockNames(flowerNames));

		for (int i = 0; i < flowerNames.length; i++) {
			LanguageRegistry.addName(new ItemStack(Item.itemsList[tropicsFlowers.blockID], 1, i), flowerNames[i]);
		}

		registerBlock(bambooStairs, "Bamboo Stairs");
		registerBlock(thatchStairs, "Thatch Stairs");
		registerBlock(chunkStairs, "Chunk O' Stairs");
		registerBlock(palmStairs, "Palm Plank Stairs");

		Item.itemsList[tropicsBuildingBlock.blockID] = (new ItemColored(tropicsBuildingBlock.blockID - 256, true)).setBlockNames(new String[]{"Thatch Bundle", "Palm Planks",
				"Eudialyte Block", "Zircon Block", "Azurite Block", "Zirconium Cube"});

		LanguageRegistry.addName(new ItemStack(tropicsBuildingBlock, 1, 0), "Thatch Bundle");
		LanguageRegistry.addName(new ItemStack(tropicsBuildingBlock, 1, 1), "Palm Planks");
		LanguageRegistry.addName(new ItemStack(tropicsBuildingBlock, 1, 2), "Eudialyte Block");
		LanguageRegistry.addName(new ItemStack(tropicsBuildingBlock, 1, 3), "Zircon Block");
		LanguageRegistry.addName(new ItemStack(tropicsBuildingBlock, 1, 4), "Azurite Block");
		LanguageRegistry.addName(new ItemStack(tropicsBuildingBlock, 1, 5), "Zirconium Block");

		Item.itemsList[tropicsSingleSlab.blockID] = (new ItemSlab(tropicsSingleSlab.blockID - 256, tropicsSingleSlab, tropicsDoubleSlab, false)).setUnlocalizedName("tropicsSlab");
		Item.itemsList[tropicsDoubleSlab.blockID] = (new ItemSlab(tropicsDoubleSlab.blockID - 256, tropicsSingleSlab, tropicsDoubleSlab, true)).setUnlocalizedName("tropicsSlab");

		for (int i = 0; i < 4; i++) {
			LanguageRegistry.addName(new ItemStack(Item.itemsList[tropicsSingleSlab.blockID], 1, i), slabNames[i] + " Slab");
		}		

		registerBlock(tropicsFenceGate, "Bamboo Fence Gate");
		registerBlock(tropicsFence, "Bamboo Fence");
		registerBlock(tallFlower, ItemTallFlowerBlock.class, "Pineapple block");
		LanguageRegistry.addName(new ItemStack(tallFlower, 1, 1), "Iris Block");
		registerBlock(chunkOHead, "Chunk O' Head");
		registerBlock(bambooBundle, "Bamboo Bundle");
		registerBlock(bambooChute, "Bamboo Chute Block");
		registerBlock(coconut, "Coconut Block");
		registerBlock(bambooMug, "Bamboo Mug");
		registerBlock(eihMixer, "Drink Mixer");

		registerTileEntities();
	}

	private static String[] getTropicsLeavesImageNames() {
		return new String[] {"palm", "leaf", "kapok", "leaf"};
	}

	private static String[] getTropicsLeavesDisplayNames() {
		return new String[] {"Palm Fronds", "Fruit Tree Leaves", "Giant Kapok Leaves", "Rainforest Leaves"};
	}

	private static String[] getLogDisplayNames() {
		return new String[] {"Palm Wood", "Mahogany Logs"};
	}

	private static void registerMultiBlock(Block block, String[] names) {
		Item.itemsList[block.blockID] = ((new ItemColored(block.blockID - 256, true)).setBlockNames(names));

		for (int i = 0; i < names.length; i++) {
			LanguageRegistry.addName(new ItemStack(Item.itemsList[block.blockID], 1, i), names[i]);
		}		
	}

	private static String[] getLogImageNames() {
		return new String[] {"palm_side", "palm_top", "mahogany_side", "mahogany_top"};
	}

	/**
	 * Register all tile entities here! Rendering registration is done in the ClientProxy
	 */
	private static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntitySifter.class, "Sifter");
		GameRegistry.registerTileEntity(TileEntityBambooMug.class, "BambooMug");
		GameRegistry.registerTileEntity(TileEntityEIHMixer.class, "EIHMixer");
		GameRegistry.registerTileEntity(TileEntityBambooChest.class, "BambooChest");
		GameRegistry.registerTileEntity(TileEntityFlowerPot.class, "FlowerPot");
		GameRegistry.registerTileEntity(TileEntityKoaChest.class, "KoaChest");
		GameRegistry.registerTileEntity(TileEntityPurchasePlate.class, "TradePlate");
		GameRegistry.registerTileEntity(TileEntityCurareBowl.class, "CurareBowl");
		GameRegistry.registerTileEntity(TileEntityFirePit.class, "FirePit");
	}

	private static String[] getSaplingImageNames() {
		return new String[] {"palm", "lemon", "lime", "orange", "grapefruit", "rainforest"};
	}

	private static String[] getSaplingDisplayNames() {
		return new String[] {"Palm Tree Sapling", "Lemon Tree Sapling", "Lime Tree Sapling", "Orange Tree Sapling", "Grapefruit Tree Sapling", "Mahogany Tree Sapling"};
	}

	private static String[] getFruitLeavesDisplayNames() {
		return new String[] {"Lemon Leaves", "Lime Leaves", "Orange Leaves", "Grapefruit Leaves"};
	}

	private static String[] getFruitLeavesImageNames() {
		return new String[] {"lemon", "lime", "orange", "grapefruit"};
	}

	private static String[] getOreImageNames() {
		return new String[] {"eudialyte", "zircon", "azurite"};
	}

	private static String[] getOreDisplayNames() {
		return new String[]{"Eudialyte Ore", "Zircon Ore", "Azurite Ore"};
	}
	
	private static String[] getBongoDrumDisplayNames() {
		return new String[] {"Small Bongo Drum", "Medium Bongo Drum", "Big Bongo Drum"};
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

	/**
	 * Register a block with the world, with the specified item class and block name
	 * @param block The block to register
	 * @param itemclass The item type to register with it
	 * @param name The mod-unique name to register it with
	 */
	private static void registerBlock(Block block, Class<? extends ItemBlock> itemclass, String name) {
		GameRegistry.registerBlock(block, itemclass, name);
		LanguageRegistry.addName(block, name);
	}

}
