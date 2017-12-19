package tropicraft.blocks;

import java.util.Random;

import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import tropicraft.creative.TropiCreativeTabs;
import tropicraft.entities.blocks.EntityFallingPurifiedSand;

public class BlockPurifiedSand extends BlockTropicraft {

	 /** Do blocks fall instantly to where they stop or do they fall over time */
    public static boolean fallInstantly = false;
	
	public BlockPurifiedSand(int id) {
		super(id, Material.sand);
		setCreativeTab(TropiCreativeTabs.tabBlock);
	}
	
	/**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate(par1World));
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate(par1World));
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (!par1World.isRemote)
        {
            this.tryToFall(par1World, par2, par3, par4);
        }
    }
    
    /**
     * If there is space to fall below will start this block falling
     */
    private void tryToFall(World par1World, int par2, int par3, int par4)
    {
        if (BlockSand.canFallBelow(par1World, par2, par3 - 1, par4) && par3 >= 0)
        {
            byte var8 = 32;

            if (!fallInstantly && par1World.checkChunksExist(par2 - var8, par3 - var8, par4 - var8, par2 + var8, par3 + var8, par4 + var8))
            {
                if (!par1World.isRemote)
                {
                	EntityFallingPurifiedSand var9 = new EntityFallingPurifiedSand(par1World, (double)((float)par2 + 0.5F), (double)((float)par3 + 0.5F), (double)((float)par4 + 0.5F), this.blockID, 0);
               		par1World.spawnEntityInWorld(var9);
                }
            }
            else
            {
                par1World.setBlockToAir(par2, par3, par4);

                while (BlockSand.canFallBelow(par1World, par2, par3 - 1, par4) && par3 > 0)
                {
                    --par3;
                }

                if (par3 > 0)
                {
                    par1World.setBlock(par2, par3, par4, this.blockID);
                }
            }
        }
    }
    
    /**
     * How many world ticks before ticking
     */
    @Override
    public int tickRate(World world)
    {
        return 3;
    }

	@Override
	public String getImageName() {
		return "purifiedsand";
	}

}
