package tropicraft.blocks;

import tropicraft.creative.TropiCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Currently only used for chunk o' head, but may be adapted to other things in the future.
 * @author Cory
 *
 */
public class BlockTropicraftStone extends BlockTropicraft {
	
	public BlockTropicraftStone(int id, String imageName) {
		super(id, Material.rock);
		this.imageName = imageName;	//use imageName here to avoid making this class abstract, so that the imageName can get sent to the getImageName method automatically on construction
		setCreativeTab(TropiCreativeTabs.tabBlock);
	}
	
    @Override
    public float getPlayerRelativeBlockHardness(EntityPlayer entityplayer, World world, int par3, int par4, int par5) {
        return entityplayer.getCurrentPlayerStrVsBlock(Block.stone, false, 0) / blockHardness / 30F;
    }

	@Override
	public String getImageName() {
		return this.imageName;
	}
}
