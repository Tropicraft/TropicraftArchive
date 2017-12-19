package net.tropicraft.crafting;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.tropicraft.FuelHandler;
import net.tropicraft.blocks.BlockTropicraftCoral;
import net.tropicraft.items.ItemAshenMask;
import net.tropicraft.mods.TropicraftMod;

import cpw.mods.fml.common.registry.GameRegistry;

public class CraftingTropicraft {

	private CraftingTropicraft() {
	}

	/*
	 * Items that should be recognized by the encyclopedia are added here.
	 * The names given MUST match the page names in the encyclopedia text file,
	 * and duplicates here are ok - multiple items can be associated with 1 page.
	 * Ordering doesn't matter, as the page order is determined by the text file
	 * 
	 * Note: Items with metadata values must be added individually (use a loop
	 * if possible)
	 */


	public static void addItemsToEncyclopedia() {
		TropicraftMod.encyclopedia.includeItem("encyclopedia", new ItemStack(TropicraftMod.encTropica));
		TropicraftMod.encyclopedia.includeItem("acaivine", new ItemStack(TropicraftMod.flowerCollection1, 1, 9));
		TropicraftMod.encyclopedia.includeItem("anemone", new ItemStack(TropicraftMod.flowerCollection1, 1, 4));
		TropicraftMod.encyclopedia.includeItem("anthuriumo", new ItemStack(TropicraftMod.flowerCollection1, 1, 5));
		TropicraftMod.encyclopedia.includeItem("anthuriumr", new ItemStack(TropicraftMod.flowerCollection1, 1, 6));

		for (int i = 0; i < ItemAshenMask.maskTypeNames.length; i++) {
			TropicraftMod.encyclopedia.includeItem("ashenmask", new ItemStack(TropicraftMod.ashenMask, 1, i));
		}

		TropicraftMod.encyclopedia.includeItem("azurite", new ItemStack(TropicraftMod.azuriteItem));
		TropicraftMod.encyclopedia.includeItem("bamboo", new ItemStack(TropicraftMod.bambooItem));
		TropicraftMod.encyclopedia.includeItem("bamboomug", new ItemStack(TropicraftMod.bambooMugEmpty));
		TropicraftMod.encyclopedia.includeItem("bambooblock", new ItemStack(TropicraftMod.bambooBlock));
		TropicraftMod.encyclopedia.includeItem("bamboochest", new ItemStack(TropicraftMod.bambooChest));
		TropicraftMod.encyclopedia.includeItem("bamboospear", new ItemStack(TropicraftMod.bambooSpear));

		for (int i = 0; i < 5; i++) {
			TropicraftMod.encyclopedia.includeItem("beachchair", new ItemStack(TropicraftMod.chair, 1, i));
			TropicraftMod.encyclopedia.includeItem("beachfloat", new ItemStack(TropicraftMod.beachFloat, 1, i));
			TropicraftMod.encyclopedia.includeItem("beachumbrella", new ItemStack(TropicraftMod.umbrella, 1, i));
		}

		TropicraftMod.encyclopedia.includeItem("blowgun", new ItemStack(TropicraftMod.blowGun));
		TropicraftMod.encyclopedia.includeItem("bromeliad", new ItemStack(TropicraftMod.flowerCollection1, 1, 14));
		TropicraftMod.encyclopedia.includeItem("canna", new ItemStack(TropicraftMod.flowerCollection1, 1, 3));
		TropicraftMod.encyclopedia.includeItem("coconut", new ItemStack(TropicraftMod.coconutItem));
		TropicraftMod.encyclopedia.includeItem("coconutchunks", new ItemStack(TropicraftMod.coconutChunk));
		TropicraftMod.encyclopedia.includeItem("coconutbomb", new ItemStack(TropicraftMod.coconutBomb));
		TropicraftMod.encyclopedia.includeItem("commelina", new ItemStack(TropicraftMod.flowerCollection1, 1, 0));

		for (int i = 0; i < ((BlockTropicraftCoral)TropicraftMod.coralCollection1).textures.length; i++) {
			TropicraftMod.encyclopedia.includeItem("coral", new ItemStack(TropicraftMod.coralCollection1, 1, i));
		}

		TropicraftMod.encyclopedia.includeItem("chunkohead", new ItemStack(TropicraftMod.chunkBlock));
		TropicraftMod.encyclopedia.includeItem("crocosmia", new ItemStack(TropicraftMod.flowerCollection1, 1, 1));
		TropicraftMod.encyclopedia.includeItem("croton", new ItemStack(TropicraftMod.flowerCollection1, 1, 10));
		TropicraftMod.encyclopedia.includeItem("dagger", new ItemStack(TropicraftMod.dagger));
		TropicraftMod.encyclopedia.includeItem("dracaena", new ItemStack(TropicraftMod.flowerCollection1, 1, 11));
		TropicraftMod.encyclopedia.includeItem("easternisles", new ItemStack(TropicraftMod.froxEasternIsles));
		TropicraftMod.encyclopedia.includeItem("eudialyte", new ItemStack(TropicraftMod.eudialyteItem));        
		TropicraftMod.encyclopedia.includeItem("fern", new ItemStack(TropicraftMod.flowerCollection1, 1, 12));
		TropicraftMod.encyclopedia.includeItem("fertilizer", new ItemStack(TropicraftMod.fertilizer));
		TropicraftMod.encyclopedia.includeItem("fishbucket", new ItemStack(TropicraftMod.fishBucket));
		TropicraftMod.encyclopedia.includeItem("fishingnet", new ItemStack(TropicraftMod.fishingNet));
		TropicraftMod.encyclopedia.includeItem("froglegs", new ItemStack(TropicraftMod.frogLeg));
		TropicraftMod.encyclopedia.includeItem("froglegscooked", new ItemStack(TropicraftMod.cookedFrogLeg));
		TropicraftMod.encyclopedia.includeItem("froxconch", new ItemStack(TropicraftMod.shellCommon1));
		TropicraftMod.encyclopedia.includeItem("flippers", new ItemStack(TropicraftMod.flippers));
		TropicraftMod.encyclopedia.includeItem("flippers", new ItemStack(Item.leather));
		TropicraftMod.encyclopedia.includeItem("grapefruit", new ItemStack(TropicraftMod.grapefruit));
		TropicraftMod.encyclopedia.includeItem("grapefruitsapling", new ItemStack(TropicraftMod.saplings, 1, 1));
		TropicraftMod.encyclopedia.includeItem("iris", new ItemStack(TropicraftMod.irisItem));
		TropicraftMod.encyclopedia.includeItem("kapok", new ItemStack(TropicraftMod.tropicLeaves, 1, 2));
		TropicraftMod.encyclopedia.includeItem("orchid", new ItemStack(TropicraftMod.flowerCollection1, 1, 2));
		TropicraftMod.encyclopedia.includeItem("leafball", new ItemStack(TropicraftMod.leafBall));
		TropicraftMod.encyclopedia.includeItem("leather", new ItemStack(Item.leather));
		TropicraftMod.encyclopedia.includeItem("lemon", new ItemStack(TropicraftMod.lemon));
		TropicraftMod.encyclopedia.includeItem("lemonsapling", new ItemStack(TropicraftMod.saplings, 1, 2));
		TropicraftMod.encyclopedia.includeItem("lime", new ItemStack(TropicraftMod.lime));
		TropicraftMod.encyclopedia.includeItem("limesapling", new ItemStack(TropicraftMod.saplings, 1, 4));
		TropicraftMod.encyclopedia.includeItem("magicmushroom", new ItemStack(TropicraftMod.flowerCollection1, 1, 7));
		TropicraftMod.encyclopedia.includeItem("mahogany", new ItemStack(TropicraftMod.tropicalWood, 1, 1));
		TropicraftMod.encyclopedia.includeItem("marlinmeat", new ItemStack(TropicraftMod.marlinRaw));
		TropicraftMod.encyclopedia.includeItem("marlincooked", new ItemStack(TropicraftMod.marlinCooked));
		TropicraftMod.encyclopedia.includeItem("orange", new ItemStack(TropicraftMod.orange));
		TropicraftMod.encyclopedia.includeItem("orangesapling", new ItemStack(TropicraftMod.saplings, 1, 3));
		TropicraftMod.encyclopedia.includeItem("pabshell", new ItemStack(TropicraftMod.shellCommon3));
		TropicraftMod.encyclopedia.includeItem("palmplanks", new ItemStack(TropicraftMod.palmPlanks));
		TropicraftMod.encyclopedia.includeItem("palmwood", new ItemStack(TropicraftMod.tropicalWood, 1, 0));
		TropicraftMod.encyclopedia.includeItem("palmsapling", new ItemStack(TropicraftMod.saplings, 1, 0));
		TropicraftMod.encyclopedia.includeItem("paradart", new ItemStack(TropicraftMod.paraDart));
		TropicraftMod.encyclopedia.includeItem("pathos", new ItemStack(TropicraftMod.flowerCollection1, 1, 8));
		TropicraftMod.encyclopedia.includeItem("pearlb", new ItemStack(TropicraftMod.blackPearlItem));
		TropicraftMod.encyclopedia.includeItem("pearlw", new ItemStack(TropicraftMod.pearlItem));
		TropicraftMod.encyclopedia.includeItem("pineapple", new ItemStack(TropicraftMod.pineappleItem));
		TropicraftMod.encyclopedia.includeItem("pineapplecubes", new ItemStack(TropicraftMod.pineappleCube));
		TropicraftMod.encyclopedia.includeItem("pinacolada", new ItemStack(TropicraftMod.bambooMugFull));
		TropicraftMod.encyclopedia.includeItem("frogskin", new ItemStack(TropicraftMod.poisonSkin));
		TropicraftMod.encyclopedia.includeItem("purifiedsand", new ItemStack(TropicraftMod.purifiedSand));
		TropicraftMod.encyclopedia.includeItem("reeds", new ItemStack(Item.reed));
		TropicraftMod.encyclopedia.includeItem("rubenautilus", new ItemStack(TropicraftMod.shellRare1));
		TropicraftMod.encyclopedia.includeItem("iggyscale", new ItemStack(TropicraftMod.scale));
		TropicraftMod.encyclopedia.includeItem("sifter", new ItemStack(TropicraftMod.sifter));
		TropicraftMod.encyclopedia.includeItem("snorkel", new ItemStack(TropicraftMod.snorkel));
		TropicraftMod.encyclopedia.includeItem("solonoxshell", new ItemStack(TropicraftMod.shellCommon1));
		TropicraftMod.encyclopedia.includeItem("starfishshell", new ItemStack(TropicraftMod.shellStarfish));
		TropicraftMod.encyclopedia.includeItem("tikitorch", new ItemStack(TropicraftMod.tikiItem));
		TropicraftMod.encyclopedia.includeItem("thatchblock", new ItemStack(TropicraftMod.thatchBlock));
		TropicraftMod.encyclopedia.includeItem("tradewinds", new ItemStack(TropicraftMod.froxTradeWinds));
		TropicraftMod.encyclopedia.includeItem("turtleshell", new ItemStack(TropicraftMod.turtleShell));
		TropicraftMod.encyclopedia.includeItem("zircon", new ItemStack(TropicraftMod.zirconItem)); 
		TropicraftMod.encyclopedia.includeItem("bamboodoor", new ItemStack(TropicraftMod.bambooDoorItem));
		TropicraftMod.encyclopedia.includeItem("waterwand", new ItemStack(TropicraftMod.sponge));
		TropicraftMod.encyclopedia.includeItem("snaretrap", new ItemStack(TropicraftMod.snareTrap));
		TropicraftMod.encyclopedia.includeItem("flowerpot", new ItemStack(TropicraftMod.flowerPotItem));
		TropicraftMod.encyclopedia.includeItem("tropiframe", new ItemStack(TropicraftMod.tropiFrame));
		TropicraftMod.encyclopedia.includeItem("portalstarter",  new ItemStack(TropicraftMod.tropicsPortalEnchanter));
		TropicraftMod.encyclopedia.includeItem("bamboostick", new ItemStack(TropicraftMod.bambooStick));
	} 


	public static void addRecipes() {

		createRecipe(true, new ItemStack(TropicraftMod.thatchBlock, 1), new Object[]{
			"XX", "XX",
			'X', Item.reed
		});

		createRecipe(true, new ItemStack(TropicraftMod.bambooBlock, 1), new Object[]{
			"XX", "XX",
			'X', TropicraftMod.bambooItem
		});

		createRecipe(true, new ItemStack(TropicraftMod.thatchStairs, 1), new Object[]{//
			"X ", "XX",
			'X', Item.reed
		});

		createRecipe(true, new ItemStack(TropicraftMod.thatchStairs, 4), new Object[]{//
			"X ", "XX",
			'X', TropicraftMod.thatchBlock
		});

		createRecipe(true, new ItemStack(TropicraftMod.bambooStairs, 1), new Object[]{//
			"X ", "XX",
			'X', TropicraftMod.bambooItem
		});

		createRecipe(true, new ItemStack(TropicraftMod.bambooStairs, 4), new Object[]{//
			"X ", "XX",
			'X', TropicraftMod.bambooBlock
		});

		createRecipe(true, new ItemStack(TropicraftMod.tropicalSingleSlab, 1, 1), new Object[]{//
			"XX",
			'X', Item.reed
		});

		createRecipe(true, new ItemStack(TropicraftMod.tropicalSingleSlab, 1, 1), new Object[]{//
			"X",
			'X', TropicraftMod.thatchBlock
		});

		createRecipe(true, new ItemStack(TropicraftMod.tropicalSingleSlab, 1, 0), new Object[]{//
			"XX",
			'X', TropicraftMod.bambooItem
		});

		createRecipe(true, new ItemStack(TropicraftMod.tropicalSingleSlab, 2, 0), new Object[]{//
			"X",
			'X', TropicraftMod.bambooBlock
		});

		createRecipe(true, new ItemStack(TropicraftMod.palmPlanks, 4), new Object[]{
			"#",
			'#', new ItemStack(TropicraftMod.tropicalWood, 1, 0)
		});

		createRecipe(true, new ItemStack(TropicraftMod.bambooMugEmpty, 1), new Object[]{
			"X X", "X X", "XXX",
			'X', TropicraftMod.bambooItem
		});

		createRecipe(true, new ItemStack(TropicraftMod.bambooMugFull, 1), new Object[]{
			"X", "Y", "Z",
			'X', TropicraftMod.coconutChunk,
			'Y', TropicraftMod.pineappleItem,
			'Z', TropicraftMod.bambooMugEmpty
		});

		createRecipe(true, new ItemStack(TropicraftMod.bambooMugFull, 1), new Object[]{
			"Y", "X", "Z",
			'X', TropicraftMod.coconutChunk,
			'Y', TropicraftMod.pineappleItem,
			'Z', TropicraftMod.bambooMugEmpty
		});

		createRecipe(true, new ItemStack(TropicraftMod.scaleHelm, 1), new Object[]{
			"XXX", "X X",
			'X', TropicraftMod.scale
		});

		createRecipe(true, new ItemStack(TropicraftMod.scaleChestplate, 1), new Object[]{
			"X X", "XXX", "XXX",
			'X', TropicraftMod.scale
		});

		createRecipe(true, new ItemStack(TropicraftMod.scaleLeggings, 1), new Object[]{
			"XXX", "X X", "X X",
			'X', TropicraftMod.scale
		});

		createRecipe(true, new ItemStack(TropicraftMod.scaleBoots, 1), new Object[]{
			"X X", "X X",
			'X', TropicraftMod.scale
		});

		createRecipe(true, new ItemStack(TropicraftMod.coconutBomb, 1), new Object[]{
			" X ", "XYX", " X ",
			'X', Item.gunpowder,
			'Y', TropicraftMod.coconutItem
		});

		createRecipe(true, new ItemStack(TropicraftMod.tikiItem, 2), new Object[]{
			"Y  ", " X ", "  X",
			'Y', Item.coal,
			'X', TropicraftMod.bambooItem
		});

		createRecipe(false, new ItemStack(TropicraftMod.tikiItem, 2), new Object[]{
			"Y  ", " X ", "  X",
			'Y', new ItemStack(Item.coal, 1, 1),
			'X', TropicraftMod.bambooItem
		});

		createRecipe(true, new ItemStack(TropicraftMod.bambooFence, 2), new Object[]{
			"XXX", "XXX",
			'X', TropicraftMod.bambooStick
		});

		createRecipe(true, new ItemStack(TropicraftMod.bambooFenceGate, 1), new Object[]{
			"XIX", "XIX",
			'X', TropicraftMod.bambooStick,
			'I', TropicraftMod.bambooBlock
		});

		createRecipe(true, new ItemStack(TropicraftMod.bambooSpear, 1), new Object[]{
			"X ", " X",
			'X', TropicraftMod.bambooStick
		});

		// Blowgun recipe used to be here

		// List of wool colors to use for chair/float/umbrella
		int[] beachItemColors = new int[] {3, 14, 4, 2, 5};
		for (int i = 0; i < beachItemColors.length; i++) {

			createRecipe(true, new ItemStack(TropicraftMod.chair, 1, i), new Object[]{
				"XIX", "XIX", "XIX",
				'X', TropicraftMod.bambooItem,
				'I', new ItemStack(Block.cloth, 1, beachItemColors[i])
			});

			createRecipe(true, new ItemStack(TropicraftMod.umbrella, 1, i), new Object[]{
				"XXX", " I ", " I ",
				'X', new ItemStack(Block.cloth, 1, beachItemColors[i]),
				'I', TropicraftMod.bambooItem
			});

			createRecipe(true, new ItemStack(TropicraftMod.beachFloat, 1, i), new Object[]{
				"XXX", "III",
				'X', new ItemStack(Block.cloth, 1, beachItemColors[i]),
				'I', TropicraftMod.bambooItem
			});
		}


		createRecipe(true, new ItemStack(TropicraftMod.tropicalSingleSlab, 2, 2), new Object[]{
			"X",
			'X', TropicraftMod.chunkBlock
		});

		createRecipe(true, new ItemStack(TropicraftMod.chunkStairs, 4), new Object[]{
			"X  ", "XX ", "XXX",
			'X', TropicraftMod.chunkBlock
		});

		createRecipe(true, new ItemStack(TropicraftMod.tropicalSingleSlab, 2, 3), new Object[]{
			"X",
			'X', TropicraftMod.palmPlanks
		});

		createRecipe(true, new ItemStack(TropicraftMod.palmStairs, 4), new Object[]{
			"X  ", "XX ", "XXX",
			'X', TropicraftMod.palmPlanks
		});

		createRecipe(true, new ItemStack(Block.workbench, 1), new Object[]{
			"II", "II",
			'I', TropicraftMod.palmPlanks
		});

		createRecipe(true, new ItemStack(TropicraftMod.pearlItem, 1), new Object[]{
			"I",
			'I', new ItemStack(TropicraftMod.shellCommon1, 1, 0)
		});

		createRecipe(true, new ItemStack(TropicraftMod.blackPearlItem, 1), new Object[]{
			"I",
			'I', new ItemStack(TropicraftMod.shellCommon2, 1, 0)
		});

		createRecipe(true, new ItemStack(TropicraftMod.pickaxeZircon), new Object[]{
			"XXX", " I ", " I ",
			'X', TropicraftMod.zirconItem,
			'I', TropicraftMod.bambooStick
		});

		createRecipe(true, new ItemStack(TropicraftMod.axeZircon), new Object[]{
			"XX", "XI ", " I",
			'X', TropicraftMod.zirconItem,
			'I', TropicraftMod.bambooStick
		});

		createRecipe(true, new ItemStack(TropicraftMod.hoeZircon), new Object[]{
			"XX", " I", " I",
			'X', TropicraftMod.zirconItem,
			'I', TropicraftMod.bambooStick
		});

		createRecipe(true, new ItemStack(TropicraftMod.swordZircon), new Object[]{
			"X", "X", "I",
			'X', TropicraftMod.zirconItem,
			'I', TropicraftMod.bambooStick
		});

		createRecipe(true, new ItemStack(TropicraftMod.shovelZircon), new Object[]{
			"X", "I", "I",
			'X', TropicraftMod.zirconItem,
			'I', TropicraftMod.bambooStick
		});

		createRecipe(true, new ItemStack(TropicraftMod.pickaxeEudialyte), new Object[]{
			"XXX", " I ", " I ",
			'X', TropicraftMod.eudialyteItem,
			'I', TropicraftMod.bambooStick
		});

		createRecipe(true, new ItemStack(TropicraftMod.axeEudialyte), new Object[]{
			"XX", "XI", " I",
			'X', TropicraftMod.eudialyteItem,
			'I', TropicraftMod.bambooStick
		});

		createRecipe(true, new ItemStack(TropicraftMod.hoeEudialyte), new Object[]{
			"XX", " I", " I",
			'X', TropicraftMod.eudialyteItem,
			'I', TropicraftMod.bambooStick
		});

		createRecipe(true, new ItemStack(TropicraftMod.swordEudialyte), new Object[]{
			"X", "X", "I",
			'X', TropicraftMod.eudialyteItem,
			'I', TropicraftMod.bambooStick
		});

		createRecipe(true, new ItemStack(TropicraftMod.shovelEudialyte), new Object[]{
			"X", "I", "I",
			'X', TropicraftMod.eudialyteItem,
			'I', TropicraftMod.bambooStick
		});

		createRecipe(true, new ItemStack(TropicraftMod.pineappleCube), new Object[]{
			"X",
			'X', TropicraftMod.pineappleItem
		});

		createRecipe(true, new ItemStack(TropicraftMod.flippers), new Object[]{
			"XIX", "X X",
			'X', Item.leather,
			'I', new ItemStack(Item.dyePowder, 1, 4)
		});

		createRecipe(true, new ItemStack(TropicraftMod.snorkel), new Object[]{
			"X  ", "XII",
			'X', TropicraftMod.bambooItem,
			'I', Block.thinGlass
		});

		createRecipe(true, new ItemStack(TropicraftMod.sifter), new Object[]{
			"XXX", "XIX", "XXX",
			'X', Block.planks,
			'I', Block.thinGlass
		});

		createRecipe(true, new ItemStack(TropicraftMod.dagger), new Object[]{
			"X", "X", "I",
			'X', TropicraftMod.chunkBlock,
			'I', TropicraftMod.palmPlanks
		});

		createRecipe(true, new ItemStack(TropicraftMod.fishingNet), new Object[]{
			"  X", " XI", "XII",
			'X', TropicraftMod.bambooItem,
			'I', Item.silk
		});

		createRecipe(true, new ItemStack(TropicraftMod.fertilizer, 3), new Object[]{
			"XI",
			'X', new ItemStack(TropicraftMod.flowerCollection1, 1, 7),
			'I', new ItemStack(TropicraftMod.flowerCollection1, 1, 10)
		});

		//para dart recipes were here

		createRecipe(true, new ItemStack(Item.dyePowder, 4, 5), new Object[]{
			"X",
			'X', TropicraftMod.irisItem
		});

		createRecipe(true, new ItemStack(Item.dyePowder, 2, 1), new Object[]{
			"X",
			'X', new ItemStack(TropicraftMod.flowerCollection1, 1, 6)//r antherium
		});

		createRecipe(true, new ItemStack(Item.dyePowder, 2, 14), new Object[]{
			"X",
			'X', new ItemStack(TropicraftMod.flowerCollection1, 1, 5)//o antherium
		});

		createRecipe(true, new ItemStack(Item.dyePowder, 2, 12), new Object[]{
			"X",
			'X', new ItemStack(TropicraftMod.flowerCollection1, 1, 0)//fern
		});

		createRecipe(true, new ItemStack(Item.dyePowder, 2, 2), new Object[]{
			"X",
			'X', new ItemStack(TropicraftMod.flowerCollection1, 1, 12)//c. diffusa
		});

		createRecipe(true, new ItemStack(Item.dyePowder, 2, 11), new Object[]{
			"X",
			'X', new ItemStack(TropicraftMod.flowerCollection1, 1, 3) //canna
		});       

		createRecipe(true, new ItemStack(TropicraftMod.bambooChest, 1), new Object[]{
			"XXX", "X X", "XXX",
			'X', TropicraftMod.bambooItem
		});

		createRecipe(true, new ItemStack(Item.stick, 4), new Object[] {
			"#", "#",
			'#', TropicraftMod.palmPlanks
		});        

		createRecipe(true, new ItemStack(Item.pickaxeWood), new Object[] {
			"XXX", " # ", " # ",
			'X', TropicraftMod.palmPlanks,
			'#', Item.stick
		});

		createRecipe(true, new ItemStack(Item.shovelWood), new Object[] {
			"X", "#", "#",
			'X', TropicraftMod.palmPlanks,
			'#', Item.stick
		});

		createRecipe(true, new ItemStack(Item.axeWood), new Object[] {
			"XX", "X#", " #",
			'X', TropicraftMod.palmPlanks,
			'#', Item.stick
		});

		createRecipe(true, new ItemStack(Item.hoeWood), new Object[] {
			"XX", " #", " #",
			'X', TropicraftMod.palmPlanks,
			'#', Item.stick
		});

		createRecipe(true, new ItemStack(Item.swordWood), new Object[] {
			"X", "X", "#",
			'X', TropicraftMod.palmPlanks,
			'#', Item.stick
		});

		createRecipe(true, new ItemStack(Block.planks, 4, 3), new Object[] {
			"#",
			'#', new ItemStack(TropicraftMod.tropicalWood, 1, 1)
		});

		createRecipe(true, new ItemStack(TropicraftMod.encTropica, 1), new Object[]{
			"###", "#$#", "###",
			'#', TropicraftMod.bambooItem,
			'$', Item.book
		}); 


		createRecipe(true, new ItemStack(Item.doorWood, 1), new Object[]{
			"XX", "XX", "XX",
			'X', TropicraftMod.palmPlanks
		});

		createRecipe(true, new ItemStack(Block.trapdoor, 2), new Object[]{
			"XXX", "XXX",
			'X', TropicraftMod.palmPlanks
		});
		createRecipe(true, new ItemStack(TropicraftMod.bambooDoorItem), new Object[]{
			"XX", "YY", "XX", 'X', TropicraftMod.bambooBlock, 'Y', TropicraftMod.thatchBlock
		});
		createRecipe(true, new ItemStack(TropicraftMod.sponge), new Object[]{
			"  X", " Y ", "Y  ", 'X', TropicraftMod.azuriteItem, 'Y', Item.ingotGold
		});
		createRecipe(true, new ItemStack(TropicraftMod.snareTrap), new Object[] {
			"  X", "XX ", "XX ", 'X', Item.silk
		});
		createRecipe(true, new ItemStack(TropicraftMod.lemonSqueezer), new Object[] {
			"XXX", "X X", "XXX", 'X', TropicraftMod.chunkBlock
		});

		createRecipe(true, new ItemStack(TropicraftMod.flowerPotItem), new Object[] {
			"# #", " # ", '#', TropicraftMod.bambooItem
		});

		createRecipe(true, new ItemStack(TropicraftMod.coffeeBean, 1, 0), new Object[] {
			"X", 'X', new ItemStack(TropicraftMod.coffeeBean, 0, 2)
		});

		createRecipe(true, new ItemStack(TropicraftMod.tropiFrame, 1), new Object[] {
			"###", "#X#", "###", '#', TropicraftMod.bambooItem, 'X', Item.leather
		});

		createRecipe(true, new ItemStack(TropicraftMod.tropicsPortalEnchanter, 1), new Object[] {
			"%@#", "#@%", " @ ", '@', TropicraftMod.bambooStick, '#', TropicraftMod.azuriteItem, '%', TropicraftMod.zirconium
		});
		
		createRecipe(true, new ItemStack(TropicraftMod.tropicsPortalEnchanter, 1), new Object[] {
			"#@%", "%@#", " @ ", '@', TropicraftMod.bambooStick, '#', TropicraftMod.azuriteItem, '%', TropicraftMod.zirconium
		});
		
		createRecipe(true, new ItemStack(TropicraftMod.bambooStick, 4), new Object[] {"#", "#", '#', TropicraftMod.bambooItem});
		
		createRecipe(true, new ItemStack(TropicraftMod.zirconium), new Object[] {
			" # ", "#$#", " # ", '#', new ItemStack(TropicraftMod.zirconItem, 1, 1), '$', TropicraftMod.eudialyteItem
		});
		
		CurareRecipes.addRecipes();
		CurareRecipes.addCurareMixerRecipes();
		MixerRecipes.addMixerRecipes();


		// Shapeless recipes go here //
		GameRegistry.addShapelessRecipe(
				new ItemStack(TropicraftMod.fertilizer, 3),
				new Object[]{
					new ItemStack(TropicraftMod.flowerCollection1, 1, 7),
					new ItemStack(TropicraftMod.flowerCollection1, 1, 10)
				});

		GameRegistry.addShapelessRecipe(
				new ItemStack(TropicraftMod.bambooMugFull),
				new Object[]{
					TropicraftMod.coconutChunk,
					TropicraftMod.pineappleItem,
					TropicraftMod.bambooMugEmpty
				});


		// Smelting recipes go here //
		GameRegistry.addSmelting(TropicraftMod.frogLeg.shiftedIndex, new ItemStack(TropicraftMod.cookedFrogLeg), 3);
		GameRegistry.addSmelting(TropicraftMod.purifiedSand.blockID, new ItemStack(Block.glass), 4);
		GameRegistry.addSmelting(TropicraftMod.marlinRaw.shiftedIndex, new ItemStack(TropicraftMod.marlinCooked), 6);
		GameRegistry.addSmelting((TropicraftMod.tropicalWood.blockID), new ItemStack(Item.coal, 1, 1), 3); // metadata 1 = charcoal
		FurnaceRecipes.smelting().addSmelting(TropicraftMod.coffeeBean.shiftedIndex, 0, new ItemStack(TropicraftMod.coffeeBean, 1, 1), 0.15f);
		FurnaceRecipes.smelting().addSmelting(TropicraftMod.zirconItem.shiftedIndex, 0, new ItemStack(TropicraftMod.zirconItem, 1, 1), 3);

		// Custom fuel burn times!
		GameRegistry.registerFuelHandler(new FuelHandler());
	}



	public static void createRecipe(boolean addToEncyclopedia, ItemStack itemstack, Object obj[]) {
		if (addToEncyclopedia) {
			TropicraftMod.encyclopedia.includeRecipe(itemstack, obj);
		}
		GameRegistry.addRecipe(itemstack, obj);
	}

}