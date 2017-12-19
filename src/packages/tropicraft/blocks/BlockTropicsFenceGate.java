package tropicraft.blocks;

import tropicraft.ModInfo;
import tropicraft.creative.TropiCreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.Icon;

public class BlockTropicsFenceGate extends BlockFenceGate {

    public BlockTropicsFenceGate(int i) {
        super(i);
        Block.setBurnProperties(i, 5, 20);
        setCreativeTab(TropiCreativeTabs.tabDecorations);
    }
    
    @Override
    @SideOnly(Side.CLIENT)

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getIcon(int par1, int par2)
    {
        return this.blockIcon;
    }
    
    @Override
    @SideOnly(Side.CLIENT)

    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerIcons(IconRegister par1IconRegister) {
    	this.blockIcon = par1IconRegister.registerIcon(ModInfo.ICONLOCATION + "bambooBundle_side");
    }
}
