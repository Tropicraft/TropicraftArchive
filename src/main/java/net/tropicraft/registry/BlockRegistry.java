package net.tropicraft.registry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.tropicraft.Names;
import net.tropicraft.Tropicraft;
import net.tropicraft.block.BlockChunkOHead;
import net.tropicraft.block.BlockTropicraftOre;
import net.tropicraft.block.BlockTropicraftOreBlock;
import net.tropicraft.block.BlockTropicraftStairs;
import net.tropicraft.enums.TropicraftOres;
import net.tropicraft.item.ItemBlockTropicraft;

public class BlockRegistry extends TropicraftRegistry {
	
	public static Block chunk;
	public static Block chunkStairs;
	
	// Ores
	public static Block oreAzurite, oreEudialyte, oreZircon;
	public static Block oreBlock;
	
	/**
	 * Register blocks in preInit
	 */
	public static void init() {
		chunk = registerBlock(new BlockChunkOHead(), Names.BLOCK_CHUNK_O_HEAD);
		chunkStairs = registerBlock(new BlockTropicraftStairs(chunk.getDefaultState()), Names.BLOCK_CHUNK_O_HEAD_STAIRS);
		oreAzurite = registerBlock(new BlockTropicraftOre(), "oreAzurite");
		oreEudialyte = registerBlock(new BlockTropicraftOre(), "oreEudialyte");
		oreZircon = registerBlock(new BlockTropicraftOre(), "oreZircon");
		oreBlock = registerMultiBlock(new BlockTropicraftOreBlock(), "oreBlock", new String[]{"blockOreAzurite", "blockOreEudialyte", "blockOreZircon"});
	}
	
	private static Block registerBlock(Block block, String name) {
		return registerBlock(block, name, false);
	}
	
	/**
	 * Register a block and return it for initialization
	 * @param block Block instance
	 * @param name Block name
	 * @return block instance
	 */
	private static Block registerBlock(Block block, String name, boolean hasVariants) {
		block.setUnlocalizedName(getNamePrefixed(name));
		block.setCreativeTab(CreativeTabs.tabBlock);
		
		GameRegistry.registerBlock(block, ItemBlock.class, name);
		
		if (hasVariants) {
			int count = 0;
	        for (TropicraftOres variant : TropicraftOres.values()) {
	            registerBlockVariant(block, variant.getName(), count++);
	        }
		} else {
			registerBlockVariant(block, name, 0);
		}
		
		return block;
	}
	
	/**
	 * Register a block with metadata
	 * @param block Block being registered
	 * @param name Name of the image prefix
	 * @param names Names of the images
	 */
	private static Block registerMultiBlock(Block block, String name, String[] names) {
		List<String> namesList = new ArrayList<String>();
		Collections.addAll(namesList, names);
		Class<? extends ItemBlock> clazz;
		clazz = ItemBlockTropicraft.class;
		GameRegistry.registerBlock(block, clazz, name, namesList);
		block.setUnlocalizedName(getNamePrefixed(name));
		int count = 0;
        for (TropicraftOres variant : TropicraftOres.values()) {
            registerBlockVariant(block, variant.getName(), count++);
        }
        block.setCreativeTab(CreativeTabs.tabBlock);
        
        return block;
	}

	
	public static void registerBlockVariant(Block block, String stateName, int stateMeta) {
        Item item = Item.getItemFromBlock(block);
        Tropicraft.proxy.registerItemVariantModel(item, stateName, stateMeta);
    }
}
