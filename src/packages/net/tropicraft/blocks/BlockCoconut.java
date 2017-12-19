package net.tropicraft.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.tropicraft.items.ItemTropicsSword;
import net.tropicraft.mods.TropicraftMod;

import java.util.Random;

public class BlockCoconut extends TropicraftBlock {

	public BlockCoconut(int i) {
		super(i, -1, Material.ground);
		destroy = false;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		float f = 0.0625F;
		return AxisAlignedBB.getBoundingBox((float) i + f, j, (float) k + f, (float) (i + 1) - f, (float) (j + 1) - f, (float) (k + 1) - f);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean isBlockSolid(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
		return false;
	}

	@Override
	public int getRenderType() {
		return 1;
	}

	@Override
	public int quantityDropped(Random random) {
		if (destroy) {
			if (random.nextInt(7) == 0) {
				return 1;
			}
			if (random.nextInt(6) == 0) {
				return 2;
			}
			if (random.nextInt(5) == 0) {
				return 3;
			} else {
				return random.nextInt(4) != 0 ? 5 : 4;
			}
		} else {
			return 1;
		}
	}

	@Override
	public int idDropped(int i, Random random, int j) {
		
		if (destroy) {
			return TropicraftMod.coconutChunk.shiftedIndex;
		} else {
			return TropicraftMod.coconutItem.shiftedIndex;
		}
	}

	protected void func_cojo(World world, int i, int j, int k) {
		if(world.isRemote) return;

		destroy = true;
		//dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
		//world.setBlockWithNotify(i, j, k, 0);
		//destroy = false;
	}

	@Override
	public int getBlockTextureFromSide(int i) {
		return TropicraftMod.coconut.blockIndexInTexture;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l) {
		return true;
	}

	@Override
	public void onBlockClicked(World world, int i, int j, int k, EntityPlayer entityplayer) {
		ItemStack itemstack = entityplayer.inventory.getStackInSlot(entityplayer.inventory.currentItem);
		destroy = false;
		if (itemstack != null && (itemstack.getItem() instanceof ItemSword ||
				itemstack.getItem() instanceof ItemTropicsSword ||
				itemstack.getItem().getItemUseAction(itemstack) == EnumAction.block)) {
			func_cojo(world, i, j, k);
		}
	}	

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
		super.onNeighborBlockChange(world, i, j, k, l);
		if (world.getBlockId(i, j + 1, k) == 0) {
			dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
			world.setBlockWithNotify(i, j, k, 0);
		}
	}

	public boolean destroy;
}
