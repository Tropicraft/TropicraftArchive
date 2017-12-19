package net.tropicraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.tropicraft.mods.TropicraftMod;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTikiTorch extends TropicraftBlock {

	public BlockTikiTorch(int i) {
		super(i, Material.wood);
		setTickRandomly(true);
		this.setCreativeTab(null);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		float w = 0.1F;
		float top = world.getBlockMetadata(x, y, z) == 0 ? 0.625F : 1.0F;
		setBlockBounds(0.5F - w, 0.0F, 0.5F - w, 0.5F + w, top, 0.5F + w);
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		if (!world.isRemote)
			return canPlaceTikiTorchOn(world, x, y - 1, z);
		else
			return false;
	}

	@Override
	public int idDropped(int metadata, Random random, int j) {
		if (metadata == 0) {
			return TropicraftMod.tikiItem.shiftedIndex;
		}
		return 0;
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
		return TropicraftMod.tikiTorchRenderId;
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int metadata) {
		if (metadata != 0) {
			return blockIndexInTexture + 1;
		}
		return blockIndexInTexture;
	}

	private boolean canPlaceTikiTorchOn(World world, int x, int y, int z) {
		if (world.isBlockNormalCube(x, y, z)) {
			return true;
		} else {
			int l = world.getBlockId(x, y, z);
			if (l == 0) {
				return false;
			}

			if (l == this.blockID && world.getBlockMetadata(x, y, z) == 1) {
				return true;
			}
			//return l == Block.fence.blockID || l == Block.netherFence.blockID;
			Block b = Block.blocksList[l];
			if (l != Block.glass.blockID && !(b instanceof BlockFence))
			{
				if (Block.blocksList[l] != null && Block.blocksList[l] instanceof BlockStairs)
				{
					int meta = world.getBlockMetadata(x, y, z);

					if ((4 & meta) != 0)
					{
						return true;
					}
				}

				return false;
			}
			else
			{
				return true;
			}
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int oldblockID) {
		if (!world.isRemote && !canPlaceTikiTorchOn(world, x, y - 1, z)) {
			dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
			world.setBlockWithNotify(x, y, z, 0);
		}
		super.onNeighborBlockChange(world, x, y, z, oldblockID);
	}


	public void onBlockRemoval(World world, int x, int y, int z) {
		if (!world.isRemote)
			while (world.getBlockId(x, --y, z) == this.blockID) {
				dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
				world.setBlockWithNotify(x, y, z, 0);
			}
	}

	@Override
	public void onBlockDestroyedByExplosion(World world, int x, int y, int z) {
		onBlockRemoval(world, x, y, z);
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int l) {
		onBlockRemoval(world, x, y, z);
	}

	@Override
	public void randomDisplayTick(World world, int x, int y, int z, Random random) {
		int l = world.getBlockMetadata(x, y, z);
		if (l == 0) {
			double d = (float) x + 0.5F;
			double d1 = (float) y + 0.7F;
			double d2 = (float) z + 0.5F;

			world.spawnParticle("smoke", d, d1, d2, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("flame", d, d1, d2, 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		int l = world.getBlockMetadata(x, y, z);
		if (l == 0) {
			return super.getLightValue(world, x, y, z);
		} else {
			return 0;
		}
	}


	@SideOnly(Side.CLIENT)

	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		par3List.add(new ItemStack(par1, 1, 0));		
		par3List.add(new ItemStack(par1, 1, 1));		
	}
	
    @Override
    @SideOnly(Side.CLIENT)
    public int idPicked(World par1World, int par2, int par3, int par4) {
    	return TropicraftMod.tikiItem.shiftedIndex;
    }
}