package net.tropicraft.proxy;


import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.tropicraft.Tropicraft;
@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
    
    public ClientProxy()
    {
        
    }

    @Override
    public void init()
    {
        super.init();
        
        registerItemRender(Item.getItemFromBlock(CommonProxy.blockLeaf), 0, "leaf", "inventory_palm");
        registerItemRender(Item.getItemFromBlock(CommonProxy.blockLeaf), 1, "leaf", "inventory_kapok");
    }
    
    @Override
    public void addBlock(Block parBlock, String unlocalizedName) {
    	super.addBlock(parBlock, unlocalizedName);
    	
    	registerItemRender(Item.getItemFromBlock(parBlock), 0, unlocalizedName);
    }
    
    public void registerItemRender(Item item, int meta, String unlocalizedName) {
    	//ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(Tropicraft.modID + ":" + unlocalizedName, "inventory"));
    	Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta, new ModelResourceLocation(Tropicraft.modID + ":" + unlocalizedName, "inventory"));
    }
    
    public void registerItemRender(Item item, int meta, String unlocalizedName, String location) {
    	//ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(Tropicraft.modID + ":" + unlocalizedName, "inventory"));
    	Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta, new ModelResourceLocation(Tropicraft.modID + ":" + unlocalizedName, location));
    }
}
