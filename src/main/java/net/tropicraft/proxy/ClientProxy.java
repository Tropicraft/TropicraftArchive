package net.tropicraft.proxy;


import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
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
        
        
    }
    
    @Override
    public void addBlock(Block parBlock, String unlocalizedName) {
    	super.addBlock(parBlock, unlocalizedName);
    	
    	registerItemRender(Item.getItemFromBlock(parBlock), 0, new ModelResourceLocation(Tropicraft.modID + ":" + unlocalizedName, "inventory"));
    }
    
    public void registerItemRender(Item item, int meta, ModelResourceLocation location) {
    	Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta, location);
    }
}
