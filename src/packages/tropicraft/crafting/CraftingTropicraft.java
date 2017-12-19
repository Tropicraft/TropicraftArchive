package tropicraft.crafting;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import tropicraft.Tropicraft;
import tropicraft.blocks.TropicraftBlocks;
import tropicraft.drinks.Drink;
import tropicraft.drinks.MixerRecipes;
import tropicraft.items.ItemCurare;
import tropicraft.items.TropicraftItems;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CraftingTropicraft {

	private CraftingTropicraft() {
	}

	/**
	 * Items that should be recognized by the encyclopedia are added here.
	 * The names given MUST match the page names in the encyclopedia text file,
	 * and duplicates here are ok - multiple items can be associated with 1 page.
	 * Ordering doesn't matter, as the page order is determined by the text file
	 * 
	 * Note: Items with metadata values must be added individually (use a loop
	 * if possible)
	 */
	public static void addItemsToEncyclopedia() {
		Tropicraft.encyclopedia.includeItem("acaivine", new ItemStack(TropicraftBlocks.tropicsFlowers, 1, 9));
		Tropicraft.encyclopedia.includeItem("anemone", new ItemStack(TropicraftBlocks.tropicsFlowers, 1, 4));
		Tropicraft.encyclopedia.includeItem("anthuriumo", new ItemStack(TropicraftBlocks.tropicsFlowers, 1, 5));
		Tropicraft.encyclopedia.includeItem("anthuriumr", new ItemStack(TropicraftBlocks.tropicsFlowers, 1, 6));

		/*TODO	for (int i = 0; i < ItemAshenMask.maskTypeNames.length; i++) {
			Tropicraft.encyclopedia.includeItem("ashenmask", new ItemStack(TropicraftItems.ashenMask, 1, i));
		}*/

		//TODO
		/*
		 * ashenmask.title = Ashen Mask
ashenmask.desc  = These masks are the source of an Ashen's identity. Without a mask, an Ashen is naked, fearful, and cowardly, while with one, they are perhaps the most agressive hunters in the Tropics. By wearing a mask, you are considered an Ashen. Masks can also be hung on walls for decoration. Be wary though, as a maskless Ashen will take the closest mask.
		 */

		Tropicraft.encyclopedia.includeItem("azurite", new ItemStack(TropicraftItems.oreDrops, 1, 2));
		Tropicraft.encyclopedia.includeItem("bamboo", new ItemStack(TropicraftItems.bambooChute));
		Tropicraft.encyclopedia.includeItem("bamboomug", new ItemStack(TropicraftItems.bambooMugEmpty));
		Tropicraft.encyclopedia.includeItem("bambooblock", new ItemStack(TropicraftBlocks.bambooBundle));
		Tropicraft.encyclopedia.includeItem("bamboochest", new ItemStack(TropicraftBlocks.bambooChest));
		Tropicraft.encyclopedia.includeItem("bamboodoor", new ItemStack(TropicraftItems.bambooDoor));
		Tropicraft.encyclopedia.includeItem("bamboospear", new ItemStack(TropicraftItems.spearBamboo));
		Tropicraft.encyclopedia.includeItem("bamboostick", new ItemStack(TropicraftItems.bambooStick));

		for (int i = 0; i < 5; i++) {
			Tropicraft.encyclopedia.includeItem("beachchair", new ItemStack(TropicraftItems.chair, 1, i));
			//TODO	Tropicraft.encyclopedia.includeItem("beachfloat", new ItemStack(TropicraftItems.beachFloat, 1, i));
			/*
			 * beachfloat.title = Beach Float
beachfloat.desc  = These uncontrollable floats allow the gentle currents of the tropics to move you. They come in five different colors.   
			 */
			Tropicraft.encyclopedia.includeItem("beachumbrella", new ItemStack(TropicraftItems.umbrella, 1, i));
		}
		
		Tropicraft.encyclopedia.includeItem("blackcoffee", MixerRecipes.getItemStack(Drink.blackCoffee));
		Tropicraft.encyclopedia.includeItem("blowgun", new ItemStack(TropicraftItems.blowGun));
		Tropicraft.encyclopedia.includeItem("bromeliad", new ItemStack(TropicraftBlocks.tropicsFlowers, 1, 14));
		Tropicraft.encyclopedia.includeItem("caipirinha", MixerRecipes.getItemStack(Drink.caipirinha));
		Tropicraft.encyclopedia.includeItem("canna", new ItemStack(TropicraftBlocks.tropicsFlowers, 1, 3));
		Tropicraft.encyclopedia.includeItem("chunkohead", new ItemStack(TropicraftBlocks.chunkOHead));
		Tropicraft.encyclopedia.includeItem("coconut", new ItemStack(TropicraftItems.coconut));
		Tropicraft.encyclopedia.includeItem("coconutchunks", new ItemStack(TropicraftItems.coconutChunk));
		Tropicraft.encyclopedia.includeItem("coconutbomb", new ItemStack(TropicraftItems.coconutBomb));
		Tropicraft.encyclopedia.includeItem("coffeebean", new ItemStack(TropicraftItems.coffeeBean));
		Tropicraft.encyclopedia.includeItem("commelina", new ItemStack(TropicraftBlocks.tropicsFlowers, 1, 0));

		for (int i = 0; i < ((tropicraft.blocks.BlockTropicsCoral)TropicraftBlocks.coral).imageNames.length; i++) {
			Tropicraft.encyclopedia.includeItem("coral", new ItemStack(TropicraftBlocks.coral, 1, i));
		}

		Tropicraft.encyclopedia.includeItem("crocosmia", new ItemStack(TropicraftBlocks.tropicsFlowers, 1, 1));
		Tropicraft.encyclopedia.includeItem("croton", new ItemStack(TropicraftBlocks.tropicsFlowers, 1, 10));
		Tropicraft.encyclopedia.includeItem("dagger", new ItemStack(TropicraftItems.dagger));
		Tropicraft.encyclopedia.includeItem("dracaena", new ItemStack(TropicraftBlocks.tropicsFlowers, 1, 11));
		Tropicraft.encyclopedia.includeItem("easternisles", new ItemStack(TropicraftItems.recordEasternIsles));
	//	Tropicraft.encyclopedia.includeItem("enchantwand", new ItemStack(TropicraftItems.enchantWand));
		Tropicraft.encyclopedia.includeItem("encyclopedia", new ItemStack(TropicraftItems.encTropica));
		Tropicraft.encyclopedia.includeItem("eudialyte", new ItemStack(TropicraftItems.oreDrops, 1, 0));        
		Tropicraft.encyclopedia.includeItem("fern", new ItemStack(TropicraftBlocks.tropicsFlowers, 1, 12));
		Tropicraft.encyclopedia.includeItem("fertilizer", new ItemStack(TropicraftItems.fertilizer));
		Tropicraft.encyclopedia.includeItem("fireboots", new ItemStack(TropicraftItems.fireBoots));
		Tropicraft.encyclopedia.includeItem("firechestplate", new ItemStack(TropicraftItems.fireChestplate));
		Tropicraft.encyclopedia.includeItem("firehelm", new ItemStack(TropicraftItems.fireHelm));
		Tropicraft.encyclopedia.includeItem("fireleggings", new ItemStack(TropicraftItems.fireLeggings));
		Tropicraft.encyclopedia.includeItem("firestaff", new ItemStack(TropicraftItems.staffFire));
		Tropicraft.encyclopedia.includeItem("fishbucket", new ItemStack(TropicraftItems.fishBucket));
		Tropicraft.encyclopedia.includeItem("fishingnet", new ItemStack(TropicraftItems.fishingNet));
		Tropicraft.encyclopedia.includeItem("flippers", new ItemStack(TropicraftItems.flippers));
		Tropicraft.encyclopedia.includeItem("flippers", new ItemStack(Item.leather));
		Tropicraft.encyclopedia.includeItem("flowerpot", new ItemStack(TropicraftItems.flowerPot));
		Tropicraft.encyclopedia.includeItem("froglegs", new ItemStack(TropicraftItems.frogLeg));
		Tropicraft.encyclopedia.includeItem("froglegscooked", new ItemStack(TropicraftItems.cookedFrogLeg));
		Tropicraft.encyclopedia.includeItem("frogskin", new ItemStack(TropicraftItems.poisonFrogSkin));
		Tropicraft.encyclopedia.includeItem("froxconch", new ItemStack(TropicraftItems.shells, 1, 1));
		Tropicraft.encyclopedia.includeItem("grapefruit", new ItemStack(TropicraftItems.grapefruit));
		Tropicraft.encyclopedia.includeItem("grapefruitsapling", new ItemStack(TropicraftBlocks.saplings, 1, 1));
		//Tropicraft.encyclopedia.includeItem("icestaff", new ItemStack(TropicraftItems.staffIce));
		Tropicraft.encyclopedia.includeItem("iggyscale", new ItemStack(TropicraftItems.scale));
		Tropicraft.encyclopedia.includeItem("iris", new ItemStack(TropicraftItems.tallFlower, 1, 15));
	//	Tropicraft.encyclopedia.includeItem("journalpage", new ItemStack(TropicraftItems.journalPage));
		Tropicraft.encyclopedia.includeItem("kapok", new ItemStack(TropicraftBlocks.tropicsLeaves, 1, 2));
		Tropicraft.encyclopedia.includeItem("koachest", new ItemStack(TropicraftBlocks.koaChest));
		Tropicraft.encyclopedia.includeItem("orchid", new ItemStack(TropicraftBlocks.tropicsFlowers, 1, 2));
		Tropicraft.encyclopedia.includeItem("leafball", new ItemStack(TropicraftItems.leafBall));
		Tropicraft.encyclopedia.includeItem("leather", new ItemStack(Item.leather));
		Tropicraft.encyclopedia.includeItem("lemon", new ItemStack(TropicraftItems.lemon));
		Tropicraft.encyclopedia.includeItem("lemonade", MixerRecipes.getItemStack(Drink.lemonade));
		Tropicraft.encyclopedia.includeItem("lemonsapling", new ItemStack(TropicraftBlocks.saplings, 1, 2));
		Tropicraft.encyclopedia.includeItem("lime", new ItemStack(TropicraftItems.lime));
		Tropicraft.encyclopedia.includeItem("limeade", MixerRecipes.getItemStack(Drink.limeade));
		Tropicraft.encyclopedia.includeItem("limesapling", new ItemStack(TropicraftBlocks.saplings, 1, 4));
		Tropicraft.encyclopedia.includeItem("lowtide", new ItemStack(TropicraftItems.recordLowTide));
		Tropicraft.encyclopedia.includeItem("magicmushroom", new ItemStack(TropicraftBlocks.tropicsFlowers, 1, 7));
		Tropicraft.encyclopedia.includeItem("mahogany", new ItemStack(TropicraftBlocks.treeWood, 1, 1));
		Tropicraft.encyclopedia.includeItem("marlinmeat", new ItemStack(TropicraftItems.freshMarlin));
		Tropicraft.encyclopedia.includeItem("marlincooked", new ItemStack(TropicraftItems.searedMarlin));
	//	Tropicraft.encyclopedia.includeItem("nigeljournal", new ItemStack(TropicraftItems.nigelJournal));
		Tropicraft.encyclopedia.includeItem("orange", new ItemStack(TropicraftItems.orange));
		Tropicraft.encyclopedia.includeItem("orangeade", MixerRecipes.getItemStack(Drink.orangeade));
		Tropicraft.encyclopedia.includeItem("orangesapling", new ItemStack(TropicraftBlocks.saplings, 1, 3));
		Tropicraft.encyclopedia.includeItem("pabshell", new ItemStack(TropicraftItems.shells, 1, 2));
		Tropicraft.encyclopedia.includeItem("palmplanks", new ItemStack(TropicraftBlocks.tropicsBuildingBlock, 1, 1));
		Tropicraft.encyclopedia.includeItem("palmwood", new ItemStack(TropicraftBlocks.treeWood, 1, 0));
		Tropicraft.encyclopedia.includeItem("palmsapling", new ItemStack(TropicraftBlocks.saplings, 1, 0));
		Tropicraft.encyclopedia.includeItem("paradart", new ItemStack(TropicraftItems.dart));
		Tropicraft.encyclopedia.includeItem("pathos", new ItemStack(TropicraftBlocks.tropicsFlowers, 1, 8));
		Tropicraft.encyclopedia.includeItem("pearlb", new ItemStack(TropicraftItems.pearl, 1, 1));
		Tropicraft.encyclopedia.includeItem("pearlw", new ItemStack(TropicraftItems.pearl, 1, 0));
		Tropicraft.encyclopedia.includeItem("pineapple", new ItemStack(TropicraftItems.tallFlower, 1, 9));
		Tropicraft.encyclopedia.includeItem("pineapplecubes", new ItemStack(TropicraftItems.pineappleCubes));
		Tropicraft.encyclopedia.includeItem("pinacolada", MixerRecipes.getItemStack(Drink.pinaColada));
		Tropicraft.encyclopedia.includeItem("portalstarter",  new ItemStack(TropicraftItems.portalEnchanter));
		Tropicraft.encyclopedia.includeItem("purifiedsand", new ItemStack(TropicraftBlocks.purifiedSand));
		Tropicraft.encyclopedia.includeItem("reeds", new ItemStack(Item.reed));
		Tropicraft.encyclopedia.includeItem("rubenautilus", new ItemStack(TropicraftItems.shells, 1, 3));
		Tropicraft.encyclopedia.includeItem("scaleboots", new ItemStack(TropicraftItems.scaleBoots));
		Tropicraft.encyclopedia.includeItem("scalechestplate", new ItemStack(TropicraftItems.scaleChestplate));
		Tropicraft.encyclopedia.includeItem("scalehelm", new ItemStack(TropicraftItems.scaleHelm));
		Tropicraft.encyclopedia.includeItem("scaleleggings", new ItemStack(TropicraftItems.scaleLeggings));
		Tropicraft.encyclopedia.includeItem("seaurchinroe", new ItemStack(TropicraftItems.seaUrchinRoe));
		Tropicraft.encyclopedia.includeItem("sifter", new ItemStack(TropicraftBlocks.sifter));
		Tropicraft.encyclopedia.includeItem("smeltedzircon", new ItemStack(TropicraftItems.oreDrops, 1, 4));
		Tropicraft.encyclopedia.includeItem("snaretrap", new ItemStack(TropicraftItems.snareTrap));
		Tropicraft.encyclopedia.includeItem("snorkel", new ItemStack(TropicraftItems.snorkel));
		Tropicraft.encyclopedia.includeItem("solonoxshell", new ItemStack(TropicraftItems.shells, 1, 0));
		Tropicraft.encyclopedia.includeItem("starfishshell", new ItemStack(TropicraftItems.shells, 1, 4));
		Tropicraft.encyclopedia.includeItem("tikitorch", new ItemStack(TropicraftItems.tikiTorch));
		Tropicraft.encyclopedia.includeItem("thatchblock", new ItemStack(TropicraftBlocks.tropicsBuildingBlock, 1, 0));
		Tropicraft.encyclopedia.includeItem("thetribe", new ItemStack(TropicraftItems.recordTheTribe));
		Tropicraft.encyclopedia.includeItem("tradewinds", new ItemStack(TropicraftItems.recordTradeWinds));
		Tropicraft.encyclopedia.includeItem("tropiframe", new ItemStack(TropicraftItems.tropiFrame));
		Tropicraft.encyclopedia.includeItem("turtleshell", new ItemStack(TropicraftItems.shells, 1, 5));
		Tropicraft.encyclopedia.includeItem("waterwand", new ItemStack(TropicraftItems.waterWand));
		Tropicraft.encyclopedia.includeItem("zircon", new ItemStack(TropicraftItems.oreDrops, 1, 1));
		Tropicraft.encyclopedia.includeItem("zirconium", new ItemStack(TropicraftItems.oreDrops, 1, 3));
	
		for (int i = 0; i < ItemCurare.effectNames.length; i++) {
			Tropicraft.encyclopedia.includeItem("curare", new ItemStack(TropicraftItems.curare, 1, i));
			Tropicraft.encyclopedia.includeItem("dart", new ItemStack(TropicraftItems.dart, 1, i));
			Tropicraft.encyclopedia.includeItem("blowgun", new ItemStack(TropicraftItems.blowGun, 1, i));
		}
	} 

	public static void addRecipes() {

		createRecipe(true, new ItemStack(TropicraftBlocks.tropicsBuildingBlock, 1, 0), new Object[]{
			"XX", "XX",
			'X', Item.reed
		});

		createRecipe(true, new ItemStack(TropicraftBlocks.bambooBundle, 1), new Object[]{
			"XX", "XX",
			'X', TropicraftItems.bambooChute
		});

		createRecipe(true, new ItemStack(TropicraftBlocks.thatchStairs, 1), new Object[]{//
			"X ", "XX",
			'X', Item.reed
		});

		createRecipe(true, new ItemStack(TropicraftBlocks.thatchStairs, 4), new Object[]{//
			"X ", "XX",
			'X', new ItemStack(TropicraftBlocks.tropicsBuildingBlock, 1, 0)
		});

		createRecipe(true, new ItemStack(TropicraftBlocks.bambooStairs, 1), new Object[]{//
			"X ", "XX",
			'X', TropicraftItems.bambooChute
		});

		createRecipe(true, new ItemStack(TropicraftBlocks.bambooStairs, 4), new Object[]{//
			"X ", "XX",
			'X', TropicraftBlocks.bambooBundle
		});

		createRecipe(true, new ItemStack(TropicraftBlocks.tropicsSingleSlab, 1, 1), new Object[]{//
			"XX",
			'X', Item.reed
		});

		createRecipe(true, new ItemStack(TropicraftBlocks.tropicsSingleSlab, 1, 1), new Object[]{//
			"X",
			'X', new ItemStack(TropicraftBlocks.tropicsBuildingBlock, 1, 0)
		});

		createRecipe(true, new ItemStack(TropicraftBlocks.tropicsSingleSlab, 1, 0), new Object[]{//
			"XX",
			'X', TropicraftItems.bambooChute
		});

		createRecipe(true, new ItemStack(TropicraftBlocks.tropicsSingleSlab, 2, 0), new Object[]{//
			"X",
			'X', TropicraftBlocks.bambooBundle
		});

		createRecipe(true, new ItemStack(TropicraftBlocks.tropicsBuildingBlock, 4, 1), new Object[]{
			"#",
			'#', new ItemStack(TropicraftBlocks.treeWood, 1, 0)
		});

		createRecipe(true, new ItemStack(TropicraftItems.bambooMugEmpty, 1), new Object[]{
			"X X", "X X", "XXX",
			'X', TropicraftItems.bambooChute
		});

		createRecipe(true, MixerRecipes.getItemStack(Drink.pinaColada), new Object[]{
			"X", "Y", "Z",
			'X', TropicraftItems.coconutChunk,
			'Y', new ItemStack(TropicraftItems.tallFlower, 1, 9),
			'Z', TropicraftItems.bambooMugEmpty
		});

		createRecipe(true, MixerRecipes.getItemStack(Drink.pinaColada), new Object[]{
			"Y", "X", "Z",
			'X', TropicraftItems.coconutChunk,
			'Y', new ItemStack(TropicraftItems.tallFlower, 1, 9),
			'Z', TropicraftItems.bambooMugEmpty
		});

		createRecipe(true, new ItemStack(TropicraftItems.scaleHelm, 1), new Object[]{
			"XXX", "X X",
			'X', TropicraftItems.scale
		});

		createRecipe(true, new ItemStack(TropicraftItems.scaleChestplate, 1), new Object[]{
			"X X", "XXX", "XXX",
			'X', TropicraftItems.scale
		});

		createRecipe(true, new ItemStack(TropicraftItems.scaleLeggings, 1), new Object[]{
			"XXX", "X X", "X X",
			'X', TropicraftItems.scale
		});

		createRecipe(true, new ItemStack(TropicraftItems.scaleBoots, 1), new Object[]{
			"X X", "X X",
			'X', TropicraftItems.scale
		});

		createRecipe(true, new ItemStack(TropicraftItems.coconutBomb, 1), new Object[]{
			" X ", "XYX", " X ",
			'X', Item.gunpowder,
			'Y', TropicraftItems.coconut
		});

		createRecipe(true, new ItemStack(TropicraftItems.tikiTorch, 2), new Object[]{
			"Y  ", " X ", "  X",
			'Y', Item.coal,
			'X', TropicraftItems.bambooStick
		});

		createRecipe(false, new ItemStack(TropicraftItems.tikiTorch, 2), new Object[]{
			"Y  ", " X ", "  X",
			'Y', new ItemStack(Item.coal, 1, 1),
			'X', TropicraftItems.bambooStick
		});

		createRecipe(true, new ItemStack(TropicraftBlocks.tropicsFence, 2), new Object[]{
			"XXX", "XXX",
			'X', TropicraftItems.bambooStick
		});

		createRecipe(true, new ItemStack(TropicraftBlocks.tropicsFenceGate, 1), new Object[]{
			"XIX", "XIX",
			'X', TropicraftItems.bambooStick,
			'I', TropicraftBlocks.bambooBundle
		});

		createRecipe(true, new ItemStack(TropicraftItems.spearBamboo, 1), new Object[]{
			"X ", " X",
			'X', TropicraftItems.bambooStick
		});

		// Blowgun recipe used to be here

		// List of wool colors to use for chair/float/umbrella
		int[] beachItemColors = new int[] {3, 14, 4, 2, 5};
		for (int i = 0; i < beachItemColors.length; i++) {

			createRecipe(true, new ItemStack(TropicraftItems.chair, 1, i), new Object[]{
				"XIX", "XIX", "XIX",
				'X', TropicraftItems.bambooStick,
				'I', new ItemStack(Block.cloth, 1, beachItemColors[i])
			});

			createRecipe(true, new ItemStack(TropicraftItems.umbrella, 1, i), new Object[]{
				"XXX", " I ", " I ",
				'X', new ItemStack(Block.cloth, 1, beachItemColors[i]),
				'I', TropicraftItems.bambooStick
			});

			/*createRecipe(true, new ItemStack(TropicraftItems.beachFloat, 1, i), new Object[]{
				"XXX", "III",
				'X', new ItemStack(Block.cloth, 1, beachItemColors[i]),
				'I', TropicraftItems.bambooItem
			});*/
		}

	//	createRecipe(true, new ItemStack(TropicraftItems.enchantWand), new Object[]{
	//		"XY ", "YXY", " YX",
	//		'X', new ItemStack(TropicraftItems.bambooStick),
	//		'Y', new ItemStack(TropicraftItems.oreDrops, 1, 3)
	//	});
		
		
		//TODO
		/*
		 * nigeljournal.title = Journal
nigeljournal.desc = A culmination of all of the notes I took while exploring the realm. Rumor has it finding all pages of my journal comes with a reward that is truly spectacular. Can you collect all of the pages?


journalpage.title = Journal Page
journalpage.desc = Every page is a day's worth of notes on what I explored in the Tropics that day. Rumor has it that collecting all of the pages from my journal comes with a truly spectacular result that will change the way you view the Tropics. Complete quests given by Koa Shaman to collect my journal pages. Best of luck to you, but be careful, you never know what is hiding behind that stalk of bamboo.


enchantwand.title = Wand o' Enchanting
enchantwand.desc = Make a 2 block wide, 2 block long, 1 block high square of tropics water, toss in an item you want enchanted, and toss in the necessary amount of azurite crystals. Right click this area with the Wand o' Enchanting, and voila, you have an awesome enchanted item!


		 */

		createRecipe(true, new ItemStack(TropicraftBlocks.tropicsSingleSlab, 2, 2), new Object[]{
			"X",
			'X', TropicraftBlocks.chunkOHead
		});

		createRecipe(true, new ItemStack(TropicraftBlocks.chunkStairs, 4), new Object[]{
			"X  ", "XX ", "XXX",
			'X', TropicraftBlocks.chunkOHead
		});

		createRecipe(true, new ItemStack(TropicraftBlocks.tropicsSingleSlab, 2, 3), new Object[]{
			"X",
			'X', new ItemStack(TropicraftBlocks.tropicsBuildingBlock, 1, 1)
		});

		createRecipe(true, new ItemStack(TropicraftBlocks.palmStairs, 4), new Object[]{
			"X  ", "XX ", "XXX",
			'X', new ItemStack(TropicraftBlocks.tropicsBuildingBlock, 1, 1)
		});

		createRecipe(true, new ItemStack(Block.workbench, 1), new Object[]{
			"II", "II",
			'I', new ItemStack(TropicraftBlocks.tropicsBuildingBlock, 1, 1)
		});

		createRecipe(true, new ItemStack(TropicraftItems.pearl, 1, 0), new Object[]{
			"I",
			'I', new ItemStack(TropicraftItems.shells, 1, 0)
		});

		createRecipe(true, new ItemStack(TropicraftItems.pearl, 1, 1), new Object[]{
			"I",
			'I', new ItemStack(TropicraftItems.shells, 1, 1)
		});

		createRecipe(true, new ItemStack(TropicraftItems.pickaxeZircon), new Object[]{
			"XXX", " I ", " I ",
			'X', new ItemStack(TropicraftItems.oreDrops, 1, 1),
			'I', TropicraftItems.bambooStick
		});

		createRecipe(true, new ItemStack(TropicraftItems.axeZircon), new Object[]{
			"XX", "XI ", " I",
			'X', new ItemStack(TropicraftItems.oreDrops, 1, 1),
			'I', TropicraftItems.bambooStick
		});

		createRecipe(true, new ItemStack(TropicraftItems.hoeZircon), new Object[]{
			"XX", " I", " I",
			'X', new ItemStack(TropicraftItems.oreDrops, 1, 1),
			'I', TropicraftItems.bambooStick
		});

		createRecipe(true, new ItemStack(TropicraftItems.swordZircon), new Object[]{
			"X", "X", "I",
			'X', new ItemStack(TropicraftItems.oreDrops, 1, 1),
			'I', TropicraftItems.bambooStick
		});

		createRecipe(true, new ItemStack(TropicraftItems.shovelZircon), new Object[]{
			"X", "I", "I",
			'X', new ItemStack(TropicraftItems.oreDrops, 1, 1),
			'I', TropicraftItems.bambooStick
		});

		createRecipe(true, new ItemStack(TropicraftItems.pickaxeEudialyte), new Object[]{
			"XXX", " I ", " I ",
			'X', new ItemStack(TropicraftItems.oreDrops, 1, 0),
			'I', TropicraftItems.bambooStick
		});

		createRecipe(true, new ItemStack(TropicraftItems.axeEudialyte), new Object[]{
			"XX", "XI", " I",
			'X', new ItemStack(TropicraftItems.oreDrops, 1, 0),
			'I', TropicraftItems.bambooStick
		});

		createRecipe(true, new ItemStack(TropicraftItems.hoeEudialyte), new Object[]{
			"XX", " I", " I",
			'X', new ItemStack(TropicraftItems.oreDrops, 1, 0),
			'I', TropicraftItems.bambooStick
		});

		createRecipe(true, new ItemStack(TropicraftItems.swordEudialyte), new Object[]{
			"X", "X", "I",
			'X', new ItemStack(TropicraftItems.oreDrops, 1, 0),
			'I', TropicraftItems.bambooStick
		});

		createRecipe(true, new ItemStack(TropicraftItems.shovelEudialyte), new Object[]{
			"X", "I", "I",
			'X', new ItemStack(TropicraftItems.oreDrops, 1, 0),
			'I', TropicraftItems.bambooStick
		});

		createRecipe(true, new ItemStack(TropicraftItems.pineappleCubes), new Object[]{
			"X",
			'X', new ItemStack(TropicraftItems.tallFlower, 1, 9)
		});

		createRecipe(true, new ItemStack(TropicraftItems.flippers), new Object[]{
			"XIX", "X X",
			'X', Item.leather,
			'I', new ItemStack(Item.dyePowder, 1, 4)
		});

		createRecipe(true, new ItemStack(TropicraftItems.snorkel), new Object[]{
			"X  ", "XII",
			'X', TropicraftItems.bambooChute,
			'I', Block.thinGlass
		});

		createRecipe(true, new ItemStack(TropicraftBlocks.sifter), new Object[]{
			"XXX", "XIX", "XXX",
			'X', Block.planks,
			'I', Block.thinGlass
		});
		 
		createRecipe(true, new ItemStack(TropicraftItems.dagger), new Object[]{
			"X", "X", "I",
			'X', TropicraftBlocks.chunkOHead,
			'I', new ItemStack(TropicraftBlocks.tropicsBuildingBlock, 1, 1)
		});

		createRecipe(true, new ItemStack(TropicraftItems.fishingNet), new Object[]{
			"  X", " XI", "XII",
			'X', TropicraftItems.bambooChute,
			'I', Item.silk
		});

		createRecipe(true, new ItemStack(TropicraftItems.fertilizer, 3), new Object[]{
			"XI",
			'X', new ItemStack(TropicraftBlocks.tropicsFlowers, 1, 7),
			'I', new ItemStack(TropicraftBlocks.tropicsFlowers, 1, 10)
		});

		//para dart recipes were here

		createRecipe(true, new ItemStack(Item.dyePowder, 4, 5), new Object[]{
			"X",
			'X', new ItemStack(TropicraftItems.tallFlower, 1, 15)
		});

		createRecipe(true, new ItemStack(Item.dyePowder, 2, 1), new Object[]{
			"X",
			'X', new ItemStack(TropicraftBlocks.tropicsFlowers, 1, 6)//r antherium
		});

		createRecipe(true, new ItemStack(Item.dyePowder, 2, 14), new Object[]{
			"X",
			'X', new ItemStack(TropicraftBlocks.tropicsFlowers, 1, 5)//o antherium
		});

		createRecipe(true, new ItemStack(Item.dyePowder, 2, 12), new Object[]{
			"X",
			'X', new ItemStack(TropicraftBlocks.tropicsFlowers, 1, 0)//fern
		});

		createRecipe(true, new ItemStack(Item.dyePowder, 2, 2), new Object[]{
			"X",
			'X', new ItemStack(TropicraftBlocks.tropicsFlowers, 1, 12)//c. diffusa
		});

		createRecipe(true, new ItemStack(Item.dyePowder, 2, 11), new Object[]{
			"X",
			'X', new ItemStack(TropicraftBlocks.tropicsFlowers, 1, 3) //canna
		});       

		createRecipe(true, new ItemStack(TropicraftBlocks.bambooChest, 1), new Object[]{
			"XXX", "X X", "XXX",
			'X', TropicraftItems.bambooChute
		});

		createRecipe(true, new ItemStack(Item.stick, 4), new Object[] {
			"#", "#",
			'#', new ItemStack(TropicraftBlocks.tropicsBuildingBlock, 1, 1)
		});        

		createRecipe(true, new ItemStack(Item.pickaxeWood), new Object[] {
			"XXX", " # ", " # ",
			'X', new ItemStack(TropicraftBlocks.tropicsBuildingBlock, 1, 1),
			'#', Item.stick
		});

		createRecipe(true, new ItemStack(Item.shovelWood), new Object[] {
			"X", "#", "#",
			'X', new ItemStack(TropicraftBlocks.tropicsBuildingBlock, 1, 1),
			'#', Item.stick
		});

		createRecipe(true, new ItemStack(Item.axeWood), new Object[] {
			"XX", "X#", " #",
			'X', new ItemStack(TropicraftBlocks.tropicsBuildingBlock, 1, 1),
			'#', Item.stick
		});

		createRecipe(true, new ItemStack(Item.hoeWood), new Object[] {
			"XX", " #", " #",
			'X', new ItemStack(TropicraftBlocks.tropicsBuildingBlock, 1, 1),
			'#', Item.stick
		});

		createRecipe(true, new ItemStack(Item.swordWood), new Object[] {
			"X", "X", "#",
			'X', new ItemStack(TropicraftBlocks.tropicsBuildingBlock, 1, 1),
			'#', Item.stick
		});

		createRecipe(true, new ItemStack(Block.planks, 4, 3), new Object[] {
			"#",
			'#', new ItemStack(TropicraftBlocks.treeWood, 1, 1)
		});

		createRecipe(true, new ItemStack(TropicraftItems.encTropica, 1), new Object[]{
			"###", "#$#", "###",
			'#', TropicraftItems.bambooChute,
			'$', Item.book
		}); 

		createRecipe(true, new ItemStack(Item.doorWood, 1), new Object[]{
			"XX", "XX", "XX",
			'X', new ItemStack(TropicraftBlocks.tropicsBuildingBlock, 1, 1)
		});

		createRecipe(true, new ItemStack(Block.trapdoor, 2), new Object[]{
			"XXX", "XXX",
			'X', new ItemStack(TropicraftBlocks.tropicsBuildingBlock, 1, 1)
		});
		createRecipe(true, new ItemStack(TropicraftItems.bambooDoor), new Object[]{
			"XX", "YY", "XX", 'X', TropicraftBlocks.bambooBundle, 
			'Y', new ItemStack(TropicraftBlocks.tropicsBuildingBlock, 1, 0)
		});
		createRecipe(true, new ItemStack(TropicraftItems.waterWand), new Object[]{
			"  X", " Y ", "Y  ", 'X', new ItemStack(TropicraftItems.oreDrops, 1, 2), 'Y', Item.ingotGold
		});
		createRecipe(true, new ItemStack(TropicraftItems.snareTrap), new Object[] {
			"  X", "XX ", "XX ", 'X', Item.silk
		});
		 
		createRecipe(true, new ItemStack(TropicraftItems.flowerPot), new Object[] {
			"# #", " # ", '#', TropicraftItems.bambooChute
		});

		createRecipe(true, new ItemStack(TropicraftItems.coffeeBean, 1, 0), new Object[] {
			"X", 'X', new ItemStack(TropicraftItems.coffeeBean, 0, 2)
		});

		createRecipe(true, new ItemStack(TropicraftItems.tropiFrame, 1), new Object[] {
			"###", "#X#", "###", '#', TropicraftItems.bambooChute, 'X', Item.leather
		});

		createRecipe(true, new ItemStack(TropicraftItems.portalEnchanter, 1), new Object[] {
			"%@#", "#@%", " @ ", '@', TropicraftItems.bambooStick, '#', 
			new ItemStack(TropicraftItems.oreDrops, 1, 2), 
			'%', new ItemStack(TropicraftItems.oreDrops, 1, 3)
		});

		createRecipe(true, new ItemStack(TropicraftItems.portalEnchanter, 1), new Object[] {
			"#@%", "%@#", " @ ", '@', TropicraftItems.bambooStick, 
			'#', new ItemStack(TropicraftItems.oreDrops, 1, 2), 
			'%', new ItemStack(TropicraftItems.oreDrops, 1, 3)
		});

		createRecipe(true, new ItemStack(TropicraftItems.bambooStick, 4), 
				new Object[] {"#", "#", '#', TropicraftItems.bambooChute});

		createRecipe(true, new ItemStack(TropicraftItems.oreDrops, 1, 3), new Object[] {
			" # ", "#$#", " # ", 
			'#', Item.diamond,
			'$', new ItemStack(TropicraftItems.oreDrops, 1, 4)  //smelted zircon
		});
		
		createRecipe(true, new ItemStack(TropicraftBlocks.koaChest, 1), new Object[] {
			"###", "#X#", "###", '#', new ItemStack(TropicraftItems.oreDrops, 1, 3), 'X', TropicraftBlocks.bambooChest
		});

		createRecipe(true, new ItemStack(TropicraftBlocks.eihMixer), new Object[] {
			"XXX", "X X", "XXX", 'X', TropicraftBlocks.chunkOHead
		});
		
		createRecipe(true, new ItemStack(TropicraftBlocks.curareBowl), new Object[] {
			"X X", " X ", 'X', TropicraftBlocks.chunkOHead
		});
		
		createOreBlockRecipe(2, 0); //eudialyte
		createOreBlockRecipe(3, 1); //zircon
		createOreBlockRecipe(4, 2); //azurite
		createOreBlockRecipe(5, 3); //zirconium

		CurareRecipes.addRecipes(true);
		CurareRecipes.addCurareMixerRecipes();

		// Shapeless recipes go here //
		GameRegistry.addShapelessRecipe(
				new ItemStack(TropicraftItems.fertilizer, 3),
				new Object[]{
					new ItemStack(TropicraftBlocks.tropicsFlowers, 1, 7),
					new ItemStack(TropicraftBlocks.tropicsFlowers, 1, 10)
				});

		GameRegistry.addShapelessRecipe(
				MixerRecipes.getItemStack(Drink.pinaColada),
				new Object[]{
					TropicraftItems.coconutChunk,
					new ItemStack(TropicraftItems.tallFlower, 1, 9),
					TropicraftItems.bambooMugEmpty
				});


		// Smelting recipes go here //
		GameRegistry.addSmelting(TropicraftItems.frogLeg.itemID, new ItemStack(TropicraftItems.cookedFrogLeg), 3);
		GameRegistry.addSmelting(TropicraftBlocks.purifiedSand.blockID, new ItemStack(Block.glass), 4);
		GameRegistry.addSmelting(TropicraftItems.freshMarlin.itemID, new ItemStack(TropicraftItems.searedMarlin), 6);
		GameRegistry.addSmelting((TropicraftBlocks.treeWood.blockID), new ItemStack(Item.coal, 1, 1), 3); // metadata 1 = charcoal
		FurnaceRecipes.smelting().addSmelting(TropicraftItems.coffeeBean.itemID, 0, new ItemStack(TropicraftItems.coffeeBean, 1, 1), 0.15f);
		//zircon -> smelted zircon
		FurnaceRecipes.smelting().addSmelting(TropicraftItems.oreDrops.itemID, 1, new ItemStack(TropicraftItems.oreDrops, 1, 4), 3F);

		// Custom fuel burn times!
		GameRegistry.registerFuelHandler(new FuelHandler());
		
/*	TODO	for(int i = 0; i < 2; i++){
			ItemStack fuelItem = new ItemStack(TropicraftItems.shells,1);
			Item rodItem = null;
			switch(i){
			case 0:
				rodItem = TropicraftItems.rodOld;
				break;
			case 1:
				rodItem = TropicraftItems.rodGood;
				fuelItem = new ItemStack(TropicraftItems.lemon,1,0);
				break;
			case 2:
				rodItem = TropicraftItems.rodSuper;
				fuelItem = new ItemStack(TropicraftItems.oreDrops,1,3);
				break;
			}
		createRecipe(false, new ItemStack(rodItem, 1), new Object[] {
			"  #", " #X", "# F", '#', TropicraftItems.bambooStick, 'X', Item.silk, 'F', fuelItem
		});
		}*/
	}

	private static void createOreBlockRecipe(int i, int j) {
		createRecipe(true, new ItemStack(TropicraftBlocks.tropicsBuildingBlock, 1, i), new Object[] {
			"%%%", "%%%", "%%%",
			'%', new ItemStack(TropicraftItems.oreDrops, 1, j)
		});
		
	}

	@SideOnly(Side.CLIENT)
	public static void addToEncyclopedia(ItemStack itemstack, Object obj[]) {
		Tropicraft.encyclopedia.includeRecipe(itemstack, obj);
	}

	public static void createRecipe(boolean addToEncyclopedia, ItemStack itemstack, Object obj[]) {
		if (addToEncyclopedia && FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			addToEncyclopedia(itemstack, obj);
		}
		GameRegistry.addRecipe(itemstack, obj);
	}

	public static void createRecipe(boolean isServer, boolean addToEncyclopedia, ItemStack itemstack, Object obj[]) {
		if (addToEncyclopedia && FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			addToEncyclopedia(itemstack, obj);
		}
		GameRegistry.addRecipe(itemstack, obj);
	}

}