package net.tropicraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.tropicraft.mods.TropicraftMod;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFlowerIris extends TropicraftBlock {

    public BlockFlowerIris(int i, int j) {
        super(i, Material.plants);
        setTickRandomly(true);
        float f = 0.3F;
        setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
    }
    
    @SideOnly(Side.CLIENT)

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
//        par3List.add(new ItemStack(par1, 1, 0));		
        par3List.add(new ItemStack(par1, 1, 1));		
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(int side, int metadata) {
        if (metadata == 0) {
            return this.blockIndexInTexture;
        }
        return this.blockIndexInTexture + 1;
    }

    @Override
    public boolean canPlaceBlockAt(World world, int i, int j, int k) {
        if (j > world.getHeight() - 2) {
            return false;
        }
        if (!world.isAirBlock(i, j + 1, k)) {
            return false;
        }
        return super.canPlaceBlockAt(world, i, j, k) && canThisPlantGrowOnThisBlockID(world.getBlockId(i, j - 1, k));
    }

    protected boolean canThisPlantGrowOnThisBlockID(int i) {
        return i == Block.grass.blockID || i == Block.dirt.blockID || i == Block.tilledField.blockID;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving doop) {
        if (world.getBlockMetadata(x, y, z) == 1 && world.getBlockId(x, y + 1, z) == 0) {
            world.setBlockAndMetadata(x, y + 1, z, this.blockID, 0);
        }
    }

    @Override
    public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
        if (!canBlockStay(world, i, j, k)) {
            dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
            world.setBlockWithNotify(i, j, k, 0);
        }
        super.onNeighborBlockChange(world, i, j, k, l);
    }

    @Override //used to be onBlockRemoval
    public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
        while (world.getBlockId(x, --y, z) == this.blockID) {
            dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
            world.setBlockWithNotify(x, y, z, 0);
        }
    }

    @Override
    public boolean canBlockStay(World world, int i, int j, int k) {
        boolean belowCheck = false;
        if (isUpperPlant(world, i, j, k)) {
            belowCheck = isLowerPlant(world, i, j - 1, k);
        } else {
            belowCheck = canThisPlantGrowOnThisBlockID(world.getBlockId(i, j - 1, k));
        }
        return belowCheck && (world.getFullBlockLightValue(i, j, k) >= 8 || world.canBlockSeeTheSky(i, j, k));
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
        return null;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return 1;
    }

    @Override
    public int idDropped(int metadata, Random random, int j) {
        if (metadata == 0) {
            return TropicraftMod.irisItem.shiftedIndex;
        }
        return 0;
    } 

    private boolean isUpperPlant(World w, int i, int j, int k) {
        return (w.getBlockId(i, j, k) == this.blockID && w.getBlockMetadata(i, j, k) == 0);
    }

    private boolean isLowerPlant(World w, int i, int j, int k) {
        return (w.getBlockId(i, j, k) == this.blockID && w.getBlockMetadata(i, j, k) == 1);
    }

	@Override
	@SideOnly(Side.CLIENT)
	public int idPicked(World par1World, int par2, int par3, int par4) {
		return TropicraftMod.irisItem.shiftedIndex;
	}
}
