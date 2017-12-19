package tropicraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.client.renderer.texture.IconRegister;
import tropicraft.ModInfo;
import tropicraft.creative.TropiCreativeTabs;

public class BlockTropicraftStairs extends BlockStairs {

	private String imageName;
	
	public BlockTropicraftStairs(int par1, Block par2Block, int par3, String imageName) {
		super(par1, par2Block, par3);
        this.setLightOpacity(0);
        disableStats();
        this.imageName = imageName;
        setCreativeTab(TropiCreativeTabs.tabBlock);
	}
	
	/**
	 * Register all Icons used in this block
	 */
	@Override
	public void registerIcons(IconRegister iconRegistry) {
		this.blockIcon = iconRegistry.registerIcon(ModInfo.ICONLOCATION + imageName);
	}

}
