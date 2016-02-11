package net.tropicraft.registry;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.tropicraft.Info;
import net.tropicraft.Names;
import net.tropicraft.Tropicraft;
import net.tropicraft.block.BlockChunkOHead;
import net.tropicraft.block.BlockTropicraftStairs;

import com.google.common.collect.ImmutableSet;

public class BlockRegistry {
	
	public static Block chunk;
	public static Block chunkStairs;
	
	/**
	 * Register blocks in preInit
	 */
	public static void init() {
		chunk = registerBlock(new BlockChunkOHead(), Names.BLOCK_CHUNK_O_HEAD);
		chunkStairs = registerBlock(new BlockTropicraftStairs(chunk.getDefaultState()), Names.BLOCK_CHUNK_O_HEAD_STAIRS);
	}
	
	/**
	 * Register a block and return it for initialization
	 * @param block Block instance
	 * @param name Block name
	 * @return block instance
	 */
	private static Block registerBlock(Block block, String name) {
		block.setUnlocalizedName(getNamePrefixed(name));
		block.setCreativeTab(CreativeTabs.tabBlock);
		
		GameRegistry.registerBlock(block, ItemBlock.class, name);
		registerBlockVariant(block, name, 0);
		
		return block;
	}
	
	public static void registerBlockVariant(Block block, String stateName, int stateMeta) {
        Item item = Item.getItemFromBlock(block);
        Tropicraft.proxy.registerItemVariantModel(item, stateName, stateMeta);
    }
	
	public static ResourceLocation getResource(String name) {
    	return new ResourceLocation(Tropicraft.modID, name);
    }
    
	/**
	 * For getting proper resource names
	 */
    public static String getNamePrefixed(String name) {
    	return Info.MODID + ":" + name;
    }
}
