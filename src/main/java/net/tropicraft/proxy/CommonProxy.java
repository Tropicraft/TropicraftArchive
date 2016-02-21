package net.tropicraft.proxy;


import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.tropicraft.Tropicraft;
import net.tropicraft.block.BlockVariant;

public class CommonProxy
{

//	public static Block blockBasicChunk = new BlockBasic();
	public static Block blockLeaf = new BlockVariant();
	
    public CommonProxy()
    {
    	
    }

    public void init()
    {
    //	addBlock(blockBasicChunk, "chunk");
    	addBlock(blockLeaf, "leaf");
    }
    
    public void addBlock(Block block, Class tEnt, String unlocalizedName) {
		addBlock(block, unlocalizedName);
		GameRegistry.registerTileEntity(tEnt, unlocalizedName);
	}
	
	public void addBlock(Block parBlock, String unlocalizedName) {
		//vanilla calls
		GameRegistry.registerBlock(parBlock, ItemBlock.class, unlocalizedName);
		
		//new 1.8 stuff
		parBlock.setUnlocalizedName(getNamePrefixed(unlocalizedName));
		
		parBlock.setCreativeTab(CreativeTabs.tabAllSearch);
		//LanguageRegistry.addName(parBlock, "BLARG");
	}
	
	public static ResourceLocation getResource(String name) {
    	return new ResourceLocation(Tropicraft.modID, name);
    }
    
    public static String getNamePrefixed(String name) {
    	return Tropicraft.modID + ":" + name;
    }
    
    public void registerItemVariantModel(Item item, String name, int metadata) {}
    public void registerItemVariantModel(Item item, String registryName, int metadata, String variantName) {}
}
