package tropicraft.registry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.liquids.LiquidContainerData;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.oredict.OreDictionary;
import tropicraft.blocks.TropicraftBlocks;
import tropicraft.items.TropicraftItems;

public class TCBlockRegistry {

	public static void init() {
		oreDictInit();
		liquidDictInit();
	}
	
	private static void oreDictInit() {
		OreDictionary.registerOre("ore_tc", new ItemStack(TropicraftBlocks.tropiOres, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("tc_bambooBundle", TropicraftBlocks.bambooBundle);
		OreDictionary.registerOre("tc_coral", new ItemStack(TropicraftBlocks.coral, 1, OreDictionary.WILDCARD_VALUE));
	}
	
	private static void liquidDictInit() {
		LiquidContainerRegistry.registerLiquid(new LiquidContainerData(new LiquidStack(TropicraftBlocks.tropicsWaterStationary, LiquidContainerRegistry.BUCKET_VOLUME), new ItemStack(TropicraftItems.bucketTropicsWater), new ItemStack(Item.bucketEmpty)));
		LiquidDictionary.getOrCreateLiquid("Tropics Water", new LiquidStack(TropicraftBlocks.tropicsWaterStationary, LiquidContainerRegistry.BUCKET_VOLUME));
	}

}
