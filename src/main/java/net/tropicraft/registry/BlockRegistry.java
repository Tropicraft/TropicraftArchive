package net.tropicraft.registry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.tropicraft.Names;
import net.tropicraft.Tropicraft;
import net.tropicraft.block.BlockChunkOHead;
import net.tropicraft.block.BlockTropicraftLog;
import net.tropicraft.block.BlockTropicraftOre;
import net.tropicraft.block.BlockTropicraftOreBlock;
import net.tropicraft.block.BlockTropicraftStairs;
import net.tropicraft.block.BlockTropicsFlowers;
import net.tropicraft.block.ITropicraftBlock;
import net.tropicraft.enums.TropicraftOres;
import net.tropicraft.item.ItemBlockTropicraft;

import com.google.common.collect.ImmutableSet;

public class BlockRegistry extends TropicraftRegistry {
	
	public static Block chunk;
	public static Block chunkStairs;
	
	// Ores
	public static Block oreAzurite, oreEudialyte, oreZircon;
	public static Block oreBlock;
	
	public static Block flowers;
	public static Block logs;
	
	// TODO Shane wants Taro https://en.wikipedia.org/wiki/Taro
	
	/**
	 * Register blocks in preInit
	 */
	public static void init() {
		chunk = registerBlock(new BlockChunkOHead(), Names.BLOCK_CHUNK_O_HEAD);
		chunkStairs = registerBlock(new BlockTropicraftStairs(chunk.getDefaultState()), Names.BLOCK_CHUNK_O_HEAD_STAIRS);
		oreAzurite = registerBlock(new BlockTropicraftOre(), "oreAzurite");
		oreEudialyte = registerBlock(new BlockTropicraftOre(), "oreEudialyte");
		oreZircon = registerBlock(new BlockTropicraftOre(), "oreZircon");
		oreBlock = registerMultiBlock(new BlockTropicraftOreBlock(Names.BLOCK_ORE_NAMES), "oreblock", Names.BLOCK_ORE_NAMES);
		flowers = registerMultiBlock(new BlockTropicsFlowers(Names.FLOWER_NAMES), "flower", Names.FLOWER_NAMES);
		logs = registerMultiBlock(new BlockTropicraftLog(Names.LOG_NAMES), "log", Names.LOG_NAMES);
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
		
		GameRegistry.registerBlock(block, ItemBlock.class, name);
		block.setCreativeTab(CreativeTabRegistry.tropicraftTab);
		
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
		
		// get the preset blocks variants
        ImmutableSet<IBlockState> presets = getBlockPresets(block);
        ITropicraftBlock tcBlock = (ITropicraftBlock)block;
        
        if (presets.isEmpty())
        {
            // block has no sub-blocks to register
            registerBlockVariant(block, name, 0);
        }
        else
        {
            // register all the sub-blocks
            for (IBlockState state : presets)
            {
                String stateName = tcBlock.getStateName(state);
                int stateMeta = block.getMetaFromState(state);
                System.err.println("Registering " + name + " with stateName " + stateName + " and meta " + stateMeta);
                registerBlockVariant(block, name, stateMeta, stateName);
            }
        }

        block.setCreativeTab(CreativeTabRegistry.tropicraftTab);
        
        return block;
	}

	
	public static void registerBlockVariant(Block block, String stateName, int stateMeta) {
        Item item = Item.getItemFromBlock(block);
        Tropicraft.proxy.registerItemVariantModel(item, stateName, stateMeta);
    }
	
	/**
	 * Built especially for registering blocks with multiple variants
	 * @param block
	 * @param registryName
	 * @param stateMeta
	 * @param variantName
	 */
	public static void registerBlockVariant(Block block, String registryName, int stateMeta, String variantName) {
        Item item = Item.getItemFromBlock(block);
        Tropicraft.proxy.registerItemVariantModel(item, registryName, stateMeta, variantName);
    }
	
    // return all of the different 'preset' variants of a block
    // works by looping through all the different values of the properties specified in block.getProperties()
    // only works on blocks supporting ITropicraft - returns an empty set for vanilla blocks
	// Thanks to our friends at BoP for this awesome code
    public static ImmutableSet<IBlockState> getBlockPresets(Block block) {
        if (!(block instanceof ITropicraftBlock)) {return ImmutableSet.<IBlockState>of();}
        IBlockState defaultState = block.getDefaultState();
        if (defaultState == null) {defaultState = block.getBlockState().getBaseState();}
        return getStatesSet(defaultState, ((ITropicraftBlock)block).getProperties());        
    }    
    
    // returns a set of states, one for every possible combination of values from the provided properties
    public static ImmutableSet<IBlockState> getStatesSet(IBlockState baseState, IProperty... properties)
    {        
        Stack<IProperty> propStack = new Stack<IProperty>();
        List<IBlockState> states = new ArrayList<IBlockState>();
        for (IProperty prop : properties) {propStack.push(prop);}
        if (!propStack.isEmpty())
        {
            addStatesToList(baseState, states, propStack);
        }
        ImmutableSet<IBlockState> ret = ImmutableSet.copyOf(states);
        return ret;
    }
    
    // recursively add state values to a list
    private static void addStatesToList(IBlockState state, List<IBlockState> list, Stack<IProperty> stack)
    {    
        if (stack.empty())
        {
            list.add(state);
            return;
        }
        else
        {
            IProperty prop = stack.pop();        
            for (Object value : prop.getAllowedValues())
            {
                addStatesToList(state.withProperty(prop, (Comparable)value), list, stack);
            }
            stack.push(prop);
        }
    }
}
