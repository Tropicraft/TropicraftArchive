package tropicraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public abstract class BlockTropicalWood extends BlockTropicraft {

	public BlockTropicalWood(int id) {
		super(id, Material.wood);
		Block.setBurnProperties(id, 5, 5);
	}

	public BlockTropicalWood(int id, CreativeTabs tab) {
		super(id, Material.wood, tab);
		Block.setBurnProperties(id, 5, 5);
	}
	
    /**
     * Gets the hardness of block at the given coordinates in the given world, relative to the ability of the given
     * EntityPlayer
     */
    @Override
    public float getPlayerRelativeBlockHardness(EntityPlayer entityplayer, World par2World, int par3, int par4, int par5)
    {
    	//unsure of what the boolean here does, may need to change at a later date
        return entityplayer.getCurrentPlayerStrVsBlock(Block.wood, false, par2World.getBlockMetadata(par3, par4, par5)) / blockHardness / 30F;
    }
}
