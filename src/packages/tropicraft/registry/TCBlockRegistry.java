package tropicraft.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;
import tropicraft.ModInfo;
import tropicraft.blocks.TropicraftBlocks;
import tropicraft.items.TropicraftItems;

public class TCBlockRegistry {	
	
	public static final Fluid TROPICS_WATER = new Fluid("tropics_water").setBlockID(TropicraftBlocks.tropicsWaterStationary.blockID).setUnlocalizedName(TropicraftBlocks.tropicsWaterStationary.getUnlocalizedName());

	public static void init() {
		oreDictInit();
		liquidDictInit();
	}
	
	private static void oreDictInit() {
		registerOre("ores", new ItemStack(TropicraftBlocks.tropiOres, 1, OreDictionary.WILDCARD_VALUE));
		registerOre("bambooBundle", TropicraftBlocks.bambooBundle);
		registerOre("coral", new ItemStack(TropicraftBlocks.coral, 1, OreDictionary.WILDCARD_VALUE));
		registerOre("chunk_o_head", TropicraftBlocks.chunkOHead);
		registerOre("fruit_leaves", new ItemStack(TropicraftBlocks.fruitLeaves, 1, OreDictionary.WILDCARD_VALUE));
		registerOre("flowers", new ItemStack(TropicraftBlocks.tropicsFlowers, 1, OreDictionary.WILDCARD_VALUE));
		registerOre("saplings", new ItemStack(TropicraftBlocks.saplings, 1, OreDictionary.WILDCARD_VALUE));
		registerOre("logs", new ItemStack(TropicraftBlocks.treeWood, 1, OreDictionary.WILDCARD_VALUE));
		registerOre("purified_sand", TropicraftBlocks.purifiedSand);
	}
	
	private static void registerOre(String name, ItemStack stack) {
		OreDictionary.registerOre(ModInfo.MODID + name, stack);
	}
	
	private static void registerOre(String name, Block block) {
		OreDictionary.registerOre(ModInfo.MODID + name, block);
	}
	
	private static void liquidDictInit() {
		FluidRegistry.registerFluid(TROPICS_WATER);
		FluidContainerRegistry.registerFluidContainer(TROPICS_WATER, new ItemStack(TropicraftItems.bucketTropicsWater), new ItemStack(Item.bucketEmpty));
	}

}
