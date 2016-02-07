package net.tropicraft.proxy;


import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.LanguageRegistry;
import net.tropicraft.Tropitest;
import net.tropicraft.block.BlockBasic;

public class CommonProxy
{

	public static Block blockBasicChunk = new BlockBasic();
	
    public CommonProxy()
    {
    	
    }

    public void init()
    {
    	addBlock(blockBasicChunk, "chunk");
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
		
		//parBlock.setCreativeTab(tab);
		LanguageRegistry.addName(parBlock, "BLARG");
	}
	
	public static ResourceLocation getResource(String name) {
    	return new ResourceLocation(Tropitest.modID, name);
    }
    
    public static String getNamePrefixed(String name) {
    	return Tropitest.modID + "." + name;
    }
}
