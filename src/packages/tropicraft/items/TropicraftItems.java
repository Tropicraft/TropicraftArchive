package tropicraft.items;

import static tropicraft.ModIds.ITEM_BAMBOODOOR_ID;
import static tropicraft.ModIds.ITEM_BUCKETTROPICS_ID;
import static tropicraft.ModIds.ITEM_EUDIALYTEAXE_ID;
import static tropicraft.ModIds.ITEM_EUDIALYTEHOE_ID;
import static tropicraft.ModIds.ITEM_EUDIALYTEPICKAXE_ID;
import static tropicraft.ModIds.ITEM_EUDIALYTESHOVEL_ID;
import static tropicraft.ModIds.ITEM_EUDIALYTESWORD_ID;
import static tropicraft.ModIds.ITEM_FISHBUCKET_ID;
import static tropicraft.ModIds.ITEM_FISHINGNET_ID;
import static tropicraft.ModIds.ITEM_FISHINGROD_ID;
import static tropicraft.ModIds.ITEM_SCALEBOOTS_ID;
import static tropicraft.ModIds.ITEM_SCALECHESTPLATE_ID;
import static tropicraft.ModIds.ITEM_SCALEHELM_ID;
import static tropicraft.ModIds.ITEM_SCALELEGGINGS_ID;
import static tropicraft.ModIds.ITEM_ZIRCONAXE_ID;
import static tropicraft.ModIds.ITEM_ZIRCONHOE_ID;
import static tropicraft.ModIds.ITEM_ZIRCONPICKAXE_ID;
import static tropicraft.ModIds.ITEM_ZIRCONSHOVEL_ID;
import static tropicraft.ModIds.ITEM_ZIRCONSWORD_ID;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import tropicraft.EnumToolMaterialTropics;
import tropicraft.ModIds;
import tropicraft.ModInfo;
import tropicraft.blocks.TropicraftBlocks;
import tropicraft.creative.TropiCreativeTabs;
import tropicraft.entities.items.EntityTCItemFrame;
import tropicraft.entities.passive.water.EntityTropicalFish;
import tropicraft.entities.passive.water.StarfishType;
import tropicraft.fishing.ItemRod;
import tropicraft.items.ashen.ItemAshenMask;
import tropicraft.items.koa.ItemStaffFireball;
import tropicraft.items.koa.ItemStaffIceball;
import tropicraft.items.koa.ItemStaffOfTaming;
import tropicraft.items.koa.ItemTropicraftLeafballNew;
import CoroAI.entity.ItemTropicalFishingRod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class TropicraftItems {

	public static Item bambooChute, bambooStick;

	public static Item coconut, coconutChunk, coconutBomb;

	public static Item tallFlower;

	public static Item frogLeg, cookedFrogLeg;

	public static Item poisonFrogSkin;

	public static Item pineappleCubes;

	public static Item freshMarlin, searedMarlin;

	/** Supports multiple damage values for tons of pearl types in one id if we want! */
	public static Item pearl;

	public static Item fertilizer;

	/** Currently, in order of metadata, eudialyte, zircon, azurite, zirconium */
	public static Item oreDrops;

	public static Item scale;

	public static Item shells;

	/* Fruits */
	public static Item lemon, lime, orange, grapefruit;

	public static Item waterWand;

	public static Item recordTradeWinds, recordEasternIsles;

	public static Item dagger;
	public static Item leafBall;
	public static ItemStaffFireball staffFire;
	public static ItemStaffIceball staffIce;
	public static ItemStaffOfTaming staffTaming;
	
	public static Item fishingRodTropical;

	public static Item swordEudialyte, swordZircon, spearBamboo;
	public static Item shovelEudialyte, shovelZircon;
	public static Item pickaxeEudialyte, pickaxeZircon;
	public static Item axeEudialyte, axeZircon;
	public static Item hoeEudialyte, hoeZircon;

	public static Item bambooDoor;

	//armor
	public static Item scaleBoots, scaleHelm, scaleLeggings, scaleChestplate;
	public static Item fireBoots, fireHelm, fireLeggings, fireChestplate;

	public static Item fishingNet;

	public static Item fishBucket, bucketTropicsWater;

	public static Item curare, dart, blowGun;

	public static Item flippers, snorkel;

	public static Item flowerPot;
	public static Item tikiTorch;
	public static Item bambooMugEmpty, cocktail;
	public static Item coffeeBean;

	public static Item snareTrap;

	public static Item seaUrchinRoe;
	
    public static Item starfish;

	public static Item recordLowTide;

	public static Item chair, umbrella;

	public static Item koaFrame, tropiFrame;
	
	public static Item recordTheTribe;
	
	public static Item portalEnchanter;
	
	public static Item encTropica;
	
	public static Item mobEggs;
	
//	public static Item enchantWand;
	
//	public static Item nigelJournal;
//	public static Item journalPage;
	
	public static Item ashenMasks;
	
	public static Item rodOld, rodGood, rodSuper;
	
	public static Item recordBuriedTreasure;
	
	public static Item recordSummering;
	
	/*Test items Make sure to remove*/
	//public static Item entityThrower;
	/*Hue*/

	public static void init() {
		bambooChute = new ItemBambooChute(ModIds.ITEM_BAMBOO_ID, TropiCreativeTabs.tabBlock).setUnlocalizedName("bambooChuteItem");
		coconut = new ItemCoconut(ModIds.ITEM_COCONUT_ID, TropiCreativeTabs.tabFood, 0).setUnlocalizedName("coconutItem");
		coconutBomb = new ItemCoconut(ModIds.ITEM_COCONUT_BOMB_ID, TropiCreativeTabs.tabCombat, 1).setUnlocalizedName("coconutBomb");
		tallFlower = new ItemTallFlower(ModIds.ITEM_TALLPLANT_ID).setUnlocalizedName("tallFlowerItem");
		frogLeg = new ItemTropicraftImpl(ModIds.ITEM_FROGLEG_ID, "frogleg").setUnlocalizedName("frogLeg");
		cookedFrogLeg = new ItemTropicraftFood(ModIds.ITEM_COOKEDFROGLEG_ID, 3, false, "cookedfrogleg").setUnlocalizedName("cookedFrogLeg");
		poisonFrogSkin = new ItemTropicraftImpl(ModIds.ITEM_POISONFROGSKIN_ID, "poisonfrogskin").setUnlocalizedName("poisonFrogSkin");
		coconutChunk = (new ItemTropicraftFood(ModIds.ITEM_COCONUTCHUNK_ID, 1, 0.1F, false, "coconutchunk")).setUnlocalizedName("coconutChunk");
		pineappleCubes = (new ItemTropicraftFood(ModIds.ITEM_PINEAPPLECUBE_ID, 1, 0.1F, false, "pineapplecubes")).setUnlocalizedName("Pineapple Cubes");
		freshMarlin = new ItemTropicraftFood(ModIds.ITEM_FRESHMARLIN_ID, 4, 0.2F, false, "freshmarlin").setUnlocalizedName("freshMarlin");
		searedMarlin = new ItemTropicraftFood(ModIds.ITEM_SEAREDMARLIN_ID, 5, 0.2F, false, "searedmarlin").setUnlocalizedName("searedMarlin");
		bambooStick = new ItemTropicraftImpl(ModIds.ITEM_BAMBOOSTICK_ID, "bamboostick", TropiCreativeTabs.tabMaterials).setUnlocalizedName("bambooStick");
		pearl = new ItemTropicraftMulti(ModIds.ITEM_PEARL_ID, new String[]{"Solo Pearl", "Black Pearl"}, new String[]{"whitepearl", "blackpearl"}).setUnlocalizedName("pearl").setCreativeTab(TropiCreativeTabs.tabDecorations);
		fertilizer = new ItemFertilizer(ModIds.ITEM_FERTILIZER_ID, "fertilizer").setUnlocalizedName("fertilizer");
		oreDrops = new ItemTropicraftMulti(ModIds.ITEM_OREITEM_ID, getOreDropDisplayNames(), new String[]{"eudialyte", "zircon", "azurite", "zirconium", "smelted_zircon"}).setUnlocalizedName("oreDrops").setCreativeTab(TropiCreativeTabs.tabMaterials);
		scale = new ItemTropicraftImpl(ModIds.ITEM_SCALE_ID, "scale", TropiCreativeTabs.tabMaterials).setUnlocalizedName("iggyScale");
		shells = new ItemShell(ModIds.ITEM_SHELLS_ID, getShellDisplayNames(), getShellImageNames()).setUnlocalizedName("shells");
		lemon = new ItemTropicraftFood(ModIds.ITEM_LEMON_ID, 2, 0.2F, false, "lemon").setUnlocalizedName("lemon");
		lime = new ItemTropicraftFood(ModIds.ITEM_LIME_ID, 2, 0.2F, false, "lime").setUnlocalizedName("lime");
		orange = new ItemTropicraftFood(ModIds.ITEM_ORANGE_ID, 2, 0.2F, false, "orange").setUnlocalizedName("orange");
		grapefruit = new ItemTropicraftFood(ModIds.ITEM_GRAPEFRUIT_ID, 2, 0.2F, false, "grapefruit").setUnlocalizedName("grapefruit");
		waterWand = new ItemWaterWand(ModIds.ITEM_WATERWAND_ID, "waterwand").setUnlocalizedName("waterwand");
		recordTradeWinds = new ItemTropicraftMusicDisk(ModIds.ITEM_TRADEWINDS_ID, "Trade Winds", "tradewinds", "Frox").setUnlocalizedName("Trade Winds");
		recordEasternIsles = new ItemTropicraftMusicDisk(ModIds.ITEM_EASTERNISLES_ID, "Eastern Isles", "easternisles", "Frox").setUnlocalizedName("Eastern Isles");
		dagger = (new ItemDagger(ModIds.ITEM_DAGGER_ID, "dagger", EnumToolMaterialTropics.ZIRCON)).setUnlocalizedName("dagger");
		fishingRodTropical = (new ItemTropicalFishingRod(ITEM_FISHINGROD_ID)).setUnlocalizedName(ModInfo.ICONLOCATION + "FishingRodTropical");
		leafBall = (new ItemTropicraftLeafballNew(ModIds.ITEM_LEAFBALL_ID)).setUnlocalizedName(ModInfo.ICONLOCATION + "leaf_green").setCreativeTab(TropiCreativeTabs.tabCombat);
		staffFire = (ItemStaffFireball) (new ItemStaffFireball(ModIds.ITEM_STAFFFIRE_ID)).setUnlocalizedName(ModInfo.ICONLOCATION + "staff_fire").setCreativeTab(TropiCreativeTabs.tabCombat);
		//staffIce = (ItemStaffIceball) (new ItemStaffIceball(ModIds.ITEM_STAFFICE_ID)).setUnlocalizedName(ModInfo.ICONLOCATION + "staff_ice").setCreativeTab(TropiCreativeTabs.tabCombat);
		staffTaming = (ItemStaffOfTaming) (new ItemStaffOfTaming(ModIds.ITEM_STAFFTAMING_ID)).setUnlocalizedName(ModInfo.ICONLOCATION + "staff_taming").setCreativeTab(TropiCreativeTabs.tabCombat);

		swordEudialyte = (new ItemTropicsSword(ITEM_EUDIALYTESWORD_ID, "eudialyte", EnumToolMaterialTropics.EUDIALYTE)).setUnlocalizedName("aEudialyte");
		shovelEudialyte = (new ItemTropicsSpade(ITEM_EUDIALYTESHOVEL_ID, "eudialyte", EnumToolMaterialTropics.EUDIALYTE)).setUnlocalizedName("bEudialyte");
		pickaxeEudialyte = (new ItemTropicsPickaxe(ITEM_EUDIALYTEPICKAXE_ID, "eudialyte", EnumToolMaterialTropics.EUDIALYTE)).setUnlocalizedName("cEudialyte");
		axeEudialyte = (new ItemTropicsAxe(ITEM_EUDIALYTEAXE_ID, "eudialyte", EnumToolMaterialTropics.EUDIALYTE)).setUnlocalizedName("dEudialyte");
		hoeEudialyte = (new ItemTropicsHoe(ITEM_EUDIALYTEHOE_ID, "eudialyte", EnumToolMaterialTropics.EUDIALYTE)).setUnlocalizedName("eEudialyte");

		swordZircon = (new ItemTropicsSword(ITEM_ZIRCONSWORD_ID, "zircon", EnumToolMaterialTropics.ZIRCON)).setUnlocalizedName("aZircon");
		shovelZircon = (new ItemTropicsSpade(ITEM_ZIRCONSHOVEL_ID, "zircon", EnumToolMaterialTropics.ZIRCON)).setUnlocalizedName("bZircon");
		pickaxeZircon = (new ItemTropicsPickaxe(ITEM_ZIRCONPICKAXE_ID, "zircon", EnumToolMaterialTropics.ZIRCON)).setUnlocalizedName("cZircon");
		axeZircon = (new ItemTropicsAxe(ITEM_ZIRCONAXE_ID, "zircon", EnumToolMaterialTropics.ZIRCON)).setUnlocalizedName("dZircon");
		hoeZircon = (new ItemTropicsHoe(ITEM_ZIRCONHOE_ID, "zircon", EnumToolMaterialTropics.ZIRCON)).setUnlocalizedName("eZircon");

		spearBamboo = (new ItemTropicsSword(ModIds.ITEM_BAMBOOSPEAR_ID, "bambooSpear", EnumToolMaterialTropics.BAMBOO)).setUnlocalizedName(ModInfo.ICONLOCATION + "bambooSpear");
		
		bambooDoor =  new ItemBambooDoor(ITEM_BAMBOODOOR_ID, "bamboodoor").setUnlocalizedName("bambooDoor");

		scaleBoots = (new ItemTropicraftArmorScale(ITEM_SCALEBOOTS_ID, 0, 3)).setUnlocalizedName(ModInfo.ICONLOCATION + "scale_boots");
		scaleHelm = (new ItemTropicraftArmorScale(ITEM_SCALEHELM_ID, 0, 0)).setUnlocalizedName(ModInfo.ICONLOCATION + "scale_helm");
		scaleLeggings = (new ItemTropicraftArmorScale(ITEM_SCALELEGGINGS_ID, 0, 2)).setUnlocalizedName(ModInfo.ICONLOCATION + "scale_leggings");
		scaleChestplate = (new ItemTropicraftArmorScale(ITEM_SCALECHESTPLATE_ID, 0, 1)).setUnlocalizedName(ModInfo.ICONLOCATION + "scale_chestplate");
		
		fireBoots = (new ItemTropicraftArmorFire(ModIds.ITEM_FIREBOOTS_ID, 0, 3)).setUnlocalizedName(ModInfo.ICONLOCATION + "fire_boots");
		fireHelm = (new ItemTropicraftArmorFire(ModIds.ITEM_FIREHELM_ID, 0, 0)).setUnlocalizedName(ModInfo.ICONLOCATION + "fire_helm");
		fireLeggings = (new ItemTropicraftArmorFire(ModIds.ITEM_FIRELEGGINGS_ID, 0, 2)).setUnlocalizedName(ModInfo.ICONLOCATION + "fire_leggings");
		fireChestplate = (new ItemTropicraftArmorFire(ModIds.ITEM_FIRECHESTPLATE_ID, 0, 1)).setUnlocalizedName(ModInfo.ICONLOCATION + "fire_chestplate");

		fishingNet = new ItemTropicraftImpl(ITEM_FISHINGNET_ID, "fishingnet", TropiCreativeTabs.tabTools).setUnlocalizedName("Fishing Net");

		fishBucket = new ItemFishBucket(ITEM_FISHBUCKET_ID).setUnlocalizedName("Fish Bucket"); 
		bucketTropicsWater = (new ItemTropicraftBucket(ITEM_BUCKETTROPICS_ID, TropicraftBlocks.tropicsWaterFlowing.blockID)).setUnlocalizedName("Tropics Bucket").setContainerItem(Item.bucketEmpty);

		curare = new ItemCurare(ModIds.ITEM_CURARE_ID).setUnlocalizedName("Curare");
		dart = new ItemDart(ModIds.ITEM_DART_ID).setUnlocalizedName("Dart");
		blowGun = new ItemBlowGun(ModIds.ITEM_BLOWGUN_ID).setUnlocalizedName("Blow Gun");

		flowerPot = new ItemFlowerPot(ModIds.ITEM_FLOWERPOT_ID, TropicraftBlocks.flowerPot).setUnlocalizedName("Flower Pot Tropicraft Item");

		tikiTorch = new ItemTikiTorch(ModIds.ITEM_TIKITORCH_ID, "tikitorch").setUnlocalizedName("Tiki Torch Item");

		snareTrap = new ItemSnareTrap(ModIds.ITEM_SNARETRAP_ID, "snaretrap").setUnlocalizedName("snareTrap");
		seaUrchinRoe = new ItemTropicraftFood(ModIds.ITEM_SEAURCHINROE_ID, 3, false, "seaurchinroe").setUnlocalizedName("seaUrchinRoe");

		snorkel = new ItemWaterGear(ModIds.ITEM_SNORKEL_ID, 0, 0, "snorkel").setUnlocalizedName("Snorkel");
		flippers = new ItemWaterGear(ModIds.ITEM_FLIPPERS_ID, 0, 3, "flippers").setUnlocalizedName("Flippers");
		

		bambooMugEmpty = new ItemTropicraftImpl(ModIds.ITEM_BAMBOOMUGEMPTY_ID, "bamboomugempty", TropiCreativeTabs.tabFood).setUnlocalizedName("bambooMugEmpty");
		cocktail = new ItemCocktail(ModIds.ITEM_COCKTAIL_ID, "cocktail", TropiCreativeTabs.tabFood).setUnlocalizedName("cocktail");
		coffeeBean = new ItemCoffeeBean(ModIds.ITEM_COFFEEBEAN_ID, "coffeebean", TropiCreativeTabs.tabFood).setUnlocalizedName("coffeeBean");

		recordLowTide = new ItemTropicraftMusicDisk(ModIds.ITEM_LOWTIDE_ID, "Low Tide", "lowtide", "Punchaface").setUnlocalizedName("Low Tide Disc");

		chair = new ItemChair(ModIds.ITEM_CHAIR_ID, getChairImageNames(), getChairDisplayNames()).setUnlocalizedName("Tropibeach Chair");
		umbrella = new ItemUmbrella(ModIds.ITEM_UMBRELLA_ID, getChairImageNames(), getUmbrellaDisplayNames()).setUnlocalizedName("Tropibeach Umbrella");

		tropiFrame = (new ItemTCItemFrame(ModIds.ITEM_TROPIFRAME_ID, EntityTCItemFrame.class, true)).setUnlocalizedName("tropiFrame");
		koaFrame = (new ItemTCItemFrame(ModIds.ITEM_KOAFRAME_ID, EntityTCItemFrame.class, false)).setUnlocalizedName("tropiFrame2");

		starfish = new ItemStarfish(ModIds.ITEM_STARFISH_ID).setCreativeTab(TropiCreativeTabs.tabDecorations);
		
		recordTheTribe = new ItemTropicraftMusicDisk(ModIds.ITEM_THETRIBE_ID, "The Tribe", "thetribe", "Emile van Krieken").setUnlocalizedName("The Tribe Disc");
		
		portalEnchanter = new ItemPortalEnchanter(ModIds.ITEM_PORTAL_ENCHANTER_ID, false).setUnlocalizedName("Portal Enchanter_");
		
		encTropica = new ItemTropBook(ModIds.ITEM_ENCYCLOPEDIA_ID, "encTropica").setUnlocalizedName("Encyclopedia Tropica_");
		
		mobEggs = new ItemMobEgg(ModIds.ITEM_MOBEGG_ID, getMobEggNames()).setUnlocalizedName("Mob Eggs Tropicraft");
		
	//	enchantWand = new ItemEnchantWand(ModIds.ITEM_ENCHANT_WAND).setUnlocalizedName("Enchant Wand");
		
	//	nigelJournal = new ItemNigelJournal(ModIds.ITEM_JOURNAL_ID, "nigeljournal").setUnlocalizedName("Nigel Journal_");
	//	journalPage = new ItemJournalPage(ModIds.ITEM_JOURNAL_PAGE_ID, "journalpage").setUnlocalizedName("Nigel Journal Page_");
		
		ashenMasks = new ItemAshenMask(ModIds.ITEM_ASHENMASK_ID, getMaskDisplayNames(), getMaskImageNames()).setUnlocalizedName("ashenMasks");
		
		rodOld = new ItemRod(ModIds.ITEM_OLDROD).setType(ItemRod.TYPE_OLD).setUnlocalizedName("Old Rod");
		rodGood = new ItemRod(ModIds.ITEM_GOODROD).setType(ItemRod.TYPE_GOOD).setUnlocalizedName("Good Rod");
		rodSuper = new ItemRod(ModIds.ITEM_SUPERROD).setType(ItemRod.TYPE_SUPER).setUnlocalizedName("Super Rod");
		
		recordBuriedTreasure = new ItemTropicraftMusicDisk(ModIds.ITEM_BURIEDTREASURE_ID, "Buried Treasure", "buriedtreasure", "Punchaface").setUnlocalizedName("Buried Treasure Disc");
		
		recordSummering = new ItemTropicraftMusicDisk(ModIds.ITEM_SUMMERING_ID, "Summering", "summering", "Billy Christiansen").setUnlocalizedName("Summering Disc");
		
		/*Test items please remove*/
		//entityThrower = new ItemEntityThrower(14341).setUnlocalizedName("Entity Thrower").setCreativeTab(TropiCreativeTabs.tabTools);
		//registerItem(entityThrower, "Entity Thrower");
		/*End of test items*/
		
		registerItem(recordBuriedTreasure, "Buried Treasure");
//		registerItem(journalPage, "Journal Page");
//		registerItem(nigelJournal, "Nigel's Journal");
		//registerItem(enchantWand, "Wand O' Enchanting");
		registerItem(mobEggs, "Mob Eggs");
		registerItem(encTropica, "Encyclopedia Tropica");		
		registerItem(portalEnchanter, "Tropics Portal Enchanter");
		
		registerItem(recordTheTribe, "The Tribe");
		
		registerItem(starfish, "Starfish", false);
		
		for (int i = 0; i < StarfishType.values().length; i++) {
			LanguageRegistry.addName(new ItemStack(starfish, 1, i), StarfishType.values()[i].getDisplayName());
		}
		
		registerItem(seaUrchinRoe, "Sea Urchin Roe");
		registerItem(tikiTorch, "Tiki Torch");
		registerItem(flowerPot, "Flower Pot");
		registerItem(bucketTropicsWater, "Bucket O' Tropics Water");
		registerItem(fishBucket, "Fish Bucket");
		for (int i = 0; i < EntityTropicalFish.names.length; i++) {
			LanguageRegistry.addName(new ItemStack(fishBucket, 1, i), EntityTropicalFish.names[i]);
		}

		for (int i = 0; i < ItemCurare.effectNames.length; i++) {
			LanguageRegistry.addName(new ItemStack(curare, 1, i), "Curare");
		}
		
		registerItem(tropiFrame, "Bamboo Item Frame");
		registerItem(koaFrame, "Koa Item Frame");
		registerItem(chair, "Beach Chair");
		registerItem(umbrella, "Beach Umbrella");
		registerItem(recordLowTide, "Low Tide");
		registerItem(coffeeBean, "Coffee Bean", false);
		registerItem(curare, "Curare");	
		registerItem(snorkel, "Snorkel");
		registerItem(flippers, "Flippers");
		registerItem(dart, "Dart");
		registerItem(blowGun, "Blow Gun");
		registerItem(fishingNet, "Fishing Net");
		registerItem(scaleBoots, "Scale Boots");
		registerItem(scaleHelm, "Scale Helmet");
		registerItem(scaleLeggings, "Scale Leggings");
		registerItem(scaleChestplate, "Scale Chestplate");
		registerItem(fireBoots, "Fire Boots");
		registerItem(fireHelm, "Fire Helmet");
		registerItem(fireLeggings, "Fire Leggings");
		registerItem(fireChestplate, "Fire Chestplate");
		registerItem(bambooDoor, "Bamboo Door");
		registerItem(swordEudialyte, "Eudialyte Sword");
		registerItem(shovelEudialyte, "Eudialyte Shovel");
		registerItem(pickaxeEudialyte, "Eudialyte Pickaxe");
		registerItem(axeEudialyte, "Eudialyte Axe");
		registerItem(hoeEudialyte, "Eudialyte Hoe");
		registerItem(swordZircon, "Zircon Spear");
		registerItem(shovelZircon, "Zircon Shovel");
		registerItem(pickaxeZircon, "Zircon Pickaxe");
		registerItem(axeZircon, "Zircon Axe");
		registerItem(hoeZircon, "Zircon Hoe");
		registerItem(dagger, "Dagger");
		registerItem(recordEasternIsles, "Eastern Isles");
		registerItem(recordTradeWinds, "Trade Winds");
		registerItem(waterWand, "Water Wand");
		registerItem(grapefruit, "Grapefruit");
		registerItem(orange, "Orange");
		registerItem(lime, "Lime");
		registerItem(lemon, "Lemon");
		registerItem(shells, "Shells", false);
		addNames(shells, getShellDisplayNames());
		registerItem(scale, "Iguana Scale");
		registerItem(oreDrops, "oreItems", false);
		addNames(oreDrops, getOreDropDisplayNames());
		registerItem(fertilizer, "Fertilizer");
		registerItem(pearl, "Pearl", false);
		LanguageRegistry.addName(new ItemStack(pearl, 1, 0), "Solo Pearl");
		LanguageRegistry.addName(new ItemStack(pearl, 1, 1), "Black Pearl");
		registerItem(bambooStick, "Bamboo Stick");
		registerItem(freshMarlin, "Fresh Marlin");
		registerItem(searedMarlin, "Seared Marlin");
		registerItem(pineappleCubes, "Pineapple Cubes");
		registerItem(coconutChunk, "Coconut Chunk");
		registerItem(poisonFrogSkin, "Poison Frog Skin");
		registerItem(cookedFrogLeg, "Cooked Frog Leg");
		registerItem(frogLeg, "Frog Leg");
		registerItem(tallFlower, "Pineapple", false);
		LanguageRegistry.addName(new ItemStack(tallFlower, 1, 9), "Pineapple");
		LanguageRegistry.addName(new ItemStack(tallFlower, 1, 15), "Iris");
		registerItem(bambooChute, "Bamboo Chute");
		registerItem(coconut, "Coconut");
		registerItem(coconutBomb, "Coconut Grenade");
		registerItem(bambooMugEmpty, "Empty Bamboo Mug");
		registerItem(cocktail, "Cocktail");
		registerItem(snareTrap, "Snare Trap");
		addNames(coffeeBean, new String[] {"Raw Coffee Beans", "Roasted Coffee Beans", "Coffee Berries"});
		registerItem(spearBamboo, "Bamboo Spear");
		registerItem(leafBall, "Leaf Ball");
		registerItem(staffFire, "Fire Staff");
		//registerItem(staffIce, "Ice Staff");
		registerItem(staffTaming, "Taming Staff");
		registerItem(ashenMasks, "Ashen Masks", false);
		addNames(ashenMasks, getMaskDisplayNames());
		registerItem(rodOld, "Old Rod");
		registerItem(rodGood, "Good Rod");
		registerItem(rodSuper, "Super Rod");
	}

	private static String[] getMobEggNames() {
		return new String[] {"iguana", "starfish", "greenfrog", "redfrog", "yellowfrog", "bluefrog", 
				"eih", "marlin", "fish", "ashen", "turtle", "mow", "monkey", "koa", "tropicreeper",
				"tropiskelly", "eagleray", "failgull", "seaurchin"};
	}
	
	public static String[] getUmbrellaDisplayNames() {
		return new String[] {"Blue Umbrella", "Red Umbrella", "Yellow Umbrella", "Purple Umbrella", "Green Umbrella"};
	}

	public static String[] getChairImageNames() {
		return new String[] {"blue", "red", "yellow", "purple", "green"};
	}
	
	public static String[] getChairDisplayNames() {
		return new String[] {"Blue Chair", "Red Chair", "Yellow Chair", "Purple Chair", "Green Chair"};
	}

	public static String[] getOreDropDisplayNames() {
		return new String[] {"Eudialyte Shard", "Zircon Crystal", "Azurite", "Zirconium", "Smelted Zircon"};
	}

	private static void addNames(Item item, String[] displayNames) {
		for (int i = 0; i < displayNames.length; i++) {
			LanguageRegistry.addName(new ItemStack(item, 1, i), displayNames[i]);
		}		
	}

	public static String[] getFruitDisplayNames() {
		return new String[]{"Lemon", "Lime", "Orange", "Grapefruit"};
	}

	public static String[] getFruitImageNames() {
		return new String[]{"lemon", "lime", "orange", "grapefruit"};
	}

	public static String[] getShellDisplayNames() {
		return new String[]{"Solonox Shell", "Frox Conch", "Pab Shell", "Rube Nautilus", "Starfish Shell", "Turtle Shell"};
	}

	public static String[] getShellImageNames() {
		return new String[]{"shell_solo", "shell_frox", "shell_pab", "shell_rube", "shell_starfish", "shell_turtle"};
	}
	
	public static String[] getMaskDisplayNames() {
		return new String[] {"Square Zord", "Horn Monkey", "Oblongatron", "Headinator", "Square Horn", "Screw Attack", "The Brain", "Bat Boy", "Ashen Mask", "Ashen Mask", "Ashen Mask", "Ashen Mask", "Ashen Mask"};
	}
	
	public static String[] getMaskImageNames() {
		String[] strArr = new String[13];
		for (int i = 0; i < strArr.length; i++) strArr[i] = "mask_" + i;
		return strArr;
	}

	private static void registerItem(Item item, String name) {
		GameRegistry.registerItem(item, name);
		LanguageRegistry.addName(item, name);
	}

	private static void registerItem(Item item, String name, boolean addName) {
		GameRegistry.registerItem(item, name);
		if (addName)
			LanguageRegistry.addName(item, name);
	}

}
