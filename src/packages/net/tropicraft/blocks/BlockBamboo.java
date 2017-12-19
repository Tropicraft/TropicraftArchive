package net.tropicraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.tropicraft.mods.TropicraftMod;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBamboo extends TropicraftBlock {

    public BlockBamboo(int i) {
        super(i, Material.plants);
        float f = 0.375F;
        setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
        setTickRandomly(true);
    }

    @Override
    public void updateTick(World world, int i, int j, int k, Random random) {
        if (world.isAirBlock(i, j + 1, k)) {
            int l;
            for (l = 1; world.getBlockId(i, j - l, k) == blockID; l++) {
            }
            if (l < 10) {
                int i1 = world.getBlockMetadata(i, j, k);
                if (i1 == 8) {
                    world.setBlockWithNotify(i, j + 1, k, blockID);
                    world.setBlockMetadataWithNotify(i, j, k, 0);
                } else {
                    world.setBlockMetadataWithNotify(i, j, k, i1 + 1);
                }
            }
        }
    }

    @Override
    public boolean canPlaceBlockAt(World world, int i, int j, int k) {
        int l = world.getBlockId(i, j - 1, k);
        if (l == blockID) {
            return true;
        }
        if (l != Block.grass.blockID && l != Block.dirt.blockID) {
            return false;
        }
        if (world.getBlockId(i - 1, j - 1, k) == Block.dirt.blockID || world.getBlockId(i - 1, j - 1, k) == Block.grass.blockID) {
            return true;
        }
        if (world.getBlockId(i + 1, j - 1, k) == Block.dirt.blockID || world.getBlockId(i + 1, j - 1, k) == Block.grass.blockID) {
            return true;
        }
        if (world.getBlockId(i, j - 1, k - 1) == Block.dirt.blockID || world.getBlockId(i, j - 1, k - 1) == Block.grass.blockID) {
            return true;
        } else {
            return world.getBlockId(i, j - 1, k + 1) == Block.grass.blockID;
        }
    }

    @Override
    public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
        checkBlockCoordValid(world, i, j, k);
    }

    protected final void checkBlockCoordValid(World world, int i, int j, int k) {
        if (!canBlockStay(world, i, j, k)) {
            dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
            world.setBlockWithNotify(i, j, k, 0);
        }
    }

    @Override
    public boolean canBlockStay(World world, int i, int j, int k) {
        return canPlaceBlockAt(world, i, j, k);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
        return null;
    }

    @Override
    public int idDropped(int i, Random random, int j) {
        return TropicraftMod.bambooItem.shiftedIndex;
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
    @SideOnly(Side.CLIENT)
    public int idPicked(World par1World, int par2, int par3, int par4) {
    	return TropicraftMod.bambooItem.shiftedIndex;
    }
}
