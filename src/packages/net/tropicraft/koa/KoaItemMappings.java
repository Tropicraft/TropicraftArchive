package net.tropicraft.koa;

import static net.tropicraft.mods.TropicraftMod.ashenMask;
import static net.tropicraft.mods.TropicraftMod.axeEudialyte;
import static net.tropicraft.mods.TropicraftMod.axeZircon;
import static net.tropicraft.mods.TropicraftMod.bamboo;
import static net.tropicraft.mods.TropicraftMod.bambooBlock;
import static net.tropicraft.mods.TropicraftMod.bambooFence;
import static net.tropicraft.mods.TropicraftMod.bambooFenceGate;
import static net.tropicraft.mods.TropicraftMod.bambooItem;
import static net.tropicraft.mods.TropicraftMod.bambooMugEmpty;
import static net.tropicraft.mods.TropicraftMod.bambooMugFull;
import static net.tropicraft.mods.TropicraftMod.bambooSpear;
import static net.tropicraft.mods.TropicraftMod.bambooStairs;
import static net.tropicraft.mods.TropicraftMod.beachFloat;
import static net.tropicraft.mods.TropicraftMod.blackPearlItem;
import static net.tropicraft.mods.TropicraftMod.blowGun;
import static net.tropicraft.mods.TropicraftMod.bucketTropicsWater;
import static net.tropicraft.mods.TropicraftMod.chair;
import static net.tropicraft.mods.TropicraftMod.coconutBomb;
import static net.tropicraft.mods.TropicraftMod.coconutChunk;
import static net.tropicraft.mods.TropicraftMod.coconutItem;
import static net.tropicraft.mods.TropicraftMod.cookedFrogLeg;
import static net.tropicraft.mods.TropicraftMod.eudialyteItem;
import static net.tropicraft.mods.TropicraftMod.fertilizer;
import static net.tropicraft.mods.TropicraftMod.fishBucket;
import static net.tropicraft.mods.TropicraftMod.fishingNet;
import static net.tropicraft.mods.TropicraftMod.fishingRodTropical;
import static net.tropicraft.mods.TropicraftMod.flippers;
import static net.tropicraft.mods.TropicraftMod.frogLeg;
import static net.tropicraft.mods.TropicraftMod.froxEasternIsles;
import static net.tropicraft.mods.TropicraftMod.froxTradeWinds;
import static net.tropicraft.mods.TropicraftMod.grapefruit;
import static net.tropicraft.mods.TropicraftMod.hoeEudialyte;
import static net.tropicraft.mods.TropicraftMod.hoeZircon;
import static net.tropicraft.mods.TropicraftMod.irisItem;
import static net.tropicraft.mods.TropicraftMod.leafBall;
import static net.tropicraft.mods.TropicraftMod.lemon;
import static net.tropicraft.mods.TropicraftMod.lime;
import static net.tropicraft.mods.TropicraftMod.marlinCooked;
import static net.tropicraft.mods.TropicraftMod.marlinRaw;
import static net.tropicraft.mods.TropicraftMod.orange;
import static net.tropicraft.mods.TropicraftMod.paraDart;
import static net.tropicraft.mods.TropicraftMod.pearlItem;
import static net.tropicraft.mods.TropicraftMod.pickaxeEudialyte;
import static net.tropicraft.mods.TropicraftMod.pickaxeZircon;
import static net.tropicraft.mods.TropicraftMod.pineappleCube;
import static net.tropicraft.mods.TropicraftMod.pineappleItem;
import static net.tropicraft.mods.TropicraftMod.poisonSkin;
import static net.tropicraft.mods.TropicraftMod.scale;
import static net.tropicraft.mods.TropicraftMod.scaleBoots;
import static net.tropicraft.mods.TropicraftMod.scaleChestplate;
import static net.tropicraft.mods.TropicraftMod.scaleHelm;
import static net.tropicraft.mods.TropicraftMod.scaleLeggings;
import static net.tropicraft.mods.TropicraftMod.shellCommon1;
import static net.tropicraft.mods.TropicraftMod.shellCommon2;
import static net.tropicraft.mods.TropicraftMod.shellCommon3;
import static net.tropicraft.mods.TropicraftMod.shellRare1;
import static net.tropicraft.mods.TropicraftMod.shellStarfish;
import static net.tropicraft.mods.TropicraftMod.shovelEudialyte;
import static net.tropicraft.mods.TropicraftMod.shovelZircon;
import static net.tropicraft.mods.TropicraftMod.snorkel;
import static net.tropicraft.mods.TropicraftMod.swordEudialyte;
import static net.tropicraft.mods.TropicraftMod.swordZircon;
import static net.tropicraft.mods.TropicraftMod.tikiItem;
import static net.tropicraft.mods.TropicraftMod.turtleShell;
import static net.tropicraft.mods.TropicraftMod.umbrella;
import static net.tropicraft.mods.TropicraftMod.zirconItem;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

/**
 * The basic idea of this class is to provide mappings of items and blocks to a value from 1-100, representing its worth in 
 * whatever unit is used for trading with Koa.
 * @author Cojomax99
 *
 */

@Deprecated
public class KoaItemMappings {

	private static HashMap<ItemStack, Integer> itemstackToValueMap = new HashMap<ItemStack, Integer> ();
	
	public KoaItemMappings() {
		
	}
	
	public static void init() {
		addMappings();
	}

	private static void addMappings() {
		//start eudialyte
		addMapping(axeEudialyte, 15);
		addMapping(pickaxeEudialyte, 15);
		addMapping(shovelEudialyte, 15);
		addMapping(hoeEudialyte, 15);
		addMapping(swordEudialyte, 15);
		addMapping(eudialyteItem, 5);
		//end eudialyte
		
		//start zircon
		addMapping(axeZircon, 35);
		addMapping(pickaxeZircon, 35);
		addMapping(shovelZircon, 35);
		addMapping(hoeZircon, 35);
		addMapping(swordZircon, 35);
		addMapping(zirconItem, 12);
		//end zircon
		
		addMapping(bamboo, 0);		//should be able to be traded
		addMapping(bambooFence, 7);
		addMapping(bambooFenceGate, 5);
		addMapping(bambooStairs, 8);
		addMapping(bambooBlock, 10);
		
		addMapping(bambooItem, 2);
		addMapping(coconutItem, 2);
		addMapping(irisItem, 2);
		addMapping(pineappleItem, 2);
		addMapping(tikiItem, 2);
		addMapping(fertilizer, 3);
		addMapping(froxTradeWinds, 60);
		addMapping(froxEasternIsles, 60);
		addMapping(bambooMugFull, 10);
		addMapping(bambooMugEmpty, 3);
		addMapping(pearlItem, 50);
		addMapping(blackPearlItem, 55);
		addMapping(frogLeg, 4);
		addMapping(cookedFrogLeg, 7);
		addMapping(chair, 20);
		addMapping(umbrella, 20);
		addMapping(coconutBomb, 40);
		addMapping(scale, 12);
		addMapping(turtleShell, 17);
		addMapping(shellCommon1, 8);
		addMapping(shellCommon2, 8);
		addMapping(shellCommon3, 8);
		addMapping(shellRare1, 20);
		addMapping(shellStarfish, 14);
		addMapping(beachFloat, 20);
		addMapping(marlinRaw, 10);
		addMapping(marlinCooked, 16);
		addMapping(bambooSpear, 9);
		addMapping(poisonSkin, 17);
		addMapping(paraDart, 24);
		addMapping(ashenMask, 45);
		addMapping(blowGun, 10);
		addMapping(scaleBoots, 30);
		addMapping(scaleHelm, 31);
		addMapping(scaleLeggings, 36);
		addMapping(scaleChestplate, 39);
		addMapping(fishingNet, 16);
		addMapping(fishBucket, 30);
		addMapping(bucketTropicsWater, 4);
		addMapping(lemon, 4);
		addMapping(lime, 4);
		addMapping(orange, 4);
		addMapping(grapefruit, 4);
		addMapping(pineappleCube, 4);
		addMapping(coconutChunk, 4);
		//addMapping(seaweed, 6);
		//addMapping(nori, 10);
		addMapping(leafBall, 15);
		addMapping(snorkel, 30);
		addMapping(flippers, 30);
		addMapping(fishingRodTropical, 22);
	}
	
	public static void replaceMapping(ItemStack stack, int i) {
		if(itemstackToValueMap.containsKey(new ItemStack(stack.getItem(), 1))) {
			itemstackToValueMap.remove(stack);
			addMapping(stack.getItem(), i);
		}
	}
	
	private static void addMapping(Item item, int i) {
		addMapping(stack(item), i);
	}
	
	private static void addMapping(Block block, int i) {
		addMapping(stack(block), i);
	}
	
	private static void addMapping(ItemStack stack, int i) {
		itemstackToValueMap.put(stack, new Integer(i));		
	}

	private static ItemStack stack(Item item) {
		return new ItemStack(item, 1);
	}
	
	private static ItemStack stack(Block block) {
		return new ItemStack(block, 1);
	}

}
