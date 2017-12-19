package net.tropicraft.blocks;

import static net.minecraftforge.common.ForgeDirection.EAST;
import static net.minecraftforge.common.ForgeDirection.NORTH;
import static net.minecraftforge.common.ForgeDirection.SOUTH;
import static net.minecraftforge.common.ForgeDirection.WEST;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.tropicraft.blocks.tileentities.TileEntityWallShell;
import net.tropicraft.mods.TropicraftMod;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockWallShell extends BlockContainer {

	public BlockWallShell(int par1) {
		super(par1, Material.glass);
	}
	
	public boolean canPlaceBlockAt(World world, int i, int j, int k)
	{
		super.canPlaceBlockAt(world, i, j, k);
		 return (world.isBlockSolidOnSide(i - 1, j, k, EAST,  true)) ||
	              (world.isBlockSolidOnSide(i + 1, j, k, WEST,  true)) ||
	              (world.isBlockSolidOnSide(i, j, k - 1, SOUTH, true)) ||
	              (world.isBlockSolidOnSide(i, j, k + 1, NORTH, true));
		//return true;
	}
	
	@Override
	public void onBlockAdded(World world, int i, int j, int k)
	{
		super.onBlockAdded(world, i, j, k);
	}
	
	 /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return null;
    }
    
    @SideOnly(Side.CLIENT)
    /**
     * Returns the bounding box of the wired rectangular prism to render.
     */
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        return super.getSelectedBoundingBoxFromPool(par1World, par2, par3, par4);
    }
    
    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
            int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            float sixteenth = 0.0625F;
            if (var5 == 0)
            {
                this.setBlockBounds(1.0F - sixteenth, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            }

            if (var5 == 1)
            {
                this.setBlockBounds(0.0F, 0.0F, 1.0F - sixteenth, 1.0F, 1.0F, 1.0F);
            }

            if (var5 == 2)
            {
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.0F + sixteenth, 1.0F, 1.0F);
            }

            if (var5 == 3)
            {
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F + sixteenth);
            }
    } 
    
    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return -1;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return true;
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World par1World)
    {
    	return new TileEntityWallShell();
    }
    
    public int quantityDropped(Random par1Random)
    {
        return 0;
    }
    
    @Override
    public int idDropped(int i, Random rand, int j)
    {
    	return 0;
    }
    
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
    	TileEntityWallShell west = (TileEntityWallShell)par1World.getBlockTileEntity(par2, par3, par4);
    	Random rand = new Random();
        float var15 = 0.05F;
        float var10 = rand.nextFloat() * 0.8F + 0.1F;
        float var11 = rand.nextFloat() * 0.8F + 0.1F;
        float var12 = rand.nextFloat() * 0.8F + 0.1F;
    	EntityItem var14 = new EntityItem(par1World, (double)((float)par2 + var10), (double)((float)par3 + var11), (double)((float)par4 + var12), new ItemStack(west.type, 1, 0));
        var14.motionX = (double)((float)rand.nextGaussian() * var15);
        var14.motionY = (double)((float)rand.nextGaussian() * var15 + 0.2F);
        var14.motionZ = (double)((float)rand.nextGaussian() * var15);
        par1World.spawnEntityInWorld(var14);
    	super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        boolean var6 = false;
        int var7 = par1World.getBlockMetadata(par2, par3, par4);
        var6 = true;

        if (var7 == 0 && par1World.isBlockOpaqueCube(par2 + 1, par3, par4))
        {
            var6 = false;
        }

        if (var7 == 1 && par1World.isBlockOpaqueCube(par2, par3, par4 + 1))
        {
            var6 = false;
        }

        if (var7 == 2 && par1World.isBlockOpaqueCube(par2 - 1, par3, par4))
        {
            var6 = false;
        }

        if (var7 == 3 && par1World.isBlockOpaqueCube(par2, par3, par4 - 1))
        {
            var6 = false;
        }

        if (var6)
        {
        	TileEntityWallShell te = (TileEntityWallShell)par1World.getBlockTileEntity(par2, par3, par4);
            dropShell(par1World, par2, par3, par4, new ItemStack(Item.itemsList[te.type]));
            par1World.setBlockWithNotify(par2, par3, par4, 0);
        }

        super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
    }
    
    private void dropShell(World worldObj, int i, int j, int k, ItemStack stack)
    {
    	if (!worldObj.isRemote)
        {
            float var6 = 0.7F;
            double var7 = (double)(worldObj.rand.nextFloat() * var6) + (double)(1.0F - var6) * 0.5D;
            double var9 = (double)(worldObj.rand.nextFloat() * var6) + (double)(1.0F - var6) * 0.5D;
            double var11 = (double)(worldObj.rand.nextFloat() * var6) + (double)(1.0F - var6) * 0.5D;
            EntityItem var13 = new EntityItem(worldObj, (double)i + var7, (double)j + var9, (double)k + var11, stack);
            var13.delayBeforeCanPickup = 10;
            worldObj.spawnEntityInWorld(var13);
        }
    }

    @SideOnly(Side.CLIENT)

    /**
     * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
     */
    public int idPicked(World par1World, int par2, int par3, int par4)
    {
    	return TropicraftMod.shellCommon1.shiftedIndex;
    }

}
