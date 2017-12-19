package net.tropicraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.tropicraft.items.ItemTropicsSword;
import net.tropicraft.mods.TropicraftMod;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFlowerPineapple extends TropicraftBlock {

	public BlockFlowerPineapple(int i, int j) {
		super(i, Material.plants);
		setTickRandomly(true);
		float f = 0.3F;
		setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
	}

	/**
	 * Called upon block activation (right click on the block.)
	 */
	public boolean onBlockActivated(World par1World, int i, int j, int k, EntityPlayer player, int par6, float par7, float par8, float par9)
	{
		if(
				player.getCurrentEquippedItem() != null
				&& player.getCurrentEquippedItem().getItem() == Item.dyePowder 
				&& player.getCurrentEquippedItem().getItemDamage() == 15) {
			player.getCurrentEquippedItem().stackSize--;
			par1World.setBlockAndMetadataWithNotify(i, j + 1, k, TropicraftMod.pineappleFlower.blockID, 0);
			par1World.setBlockMetadataWithNotify(i, j, k, 1);
		}

		return false;
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int metadata) {
		if (metadata == 0) {
			return this.blockIndexInTexture;
		}
		return this.blockIndexInTexture + 2;
	}

	@Override
	public boolean canPlaceBlockAt(World world, int i, int j, int k) {
		return super.canPlaceBlockAt(world, i, j, k) && canThisPlantGrowOnThisBlockID(world.getBlockId(i, j - 1, k));
	}

	protected boolean canThisPlantGrowOnThisBlockID(int i) {
		return i == Block.grass.blockID || i == Block.dirt.blockID || i == Block.tilledField.blockID;
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
		super.onNeighborBlockChange(world, i, j, k, l);
		checkFlowerChange(world, i, j, k);
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {   
		checkFlowerChange(world, i, j, k);
		if (j > world.getHeight() - 2) {
			return;
		}
		if (world.getBlockId(i, j, k) == this.blockID && world.getBlockMetadata(i, j, k) != 0
				&& world.isAirBlock(i, j + 1, k)) {
			int growth = world.getBlockMetadata(i, j, k);
			if (growth >= 9) {
				world.setBlockAndMetadataWithNotify(i, j + 1, k, TropicraftMod.pineappleFlower.blockID, 0);
				world.setBlockMetadataWithNotify(i, j, k, 1);
			} else {
				world.setBlockMetadataWithNotify(i, j, k, growth + 1);
			}
		}
	}

	protected void checkFlowerChange(World world, int i, int j, int k) {
		if (!canBlockStay(world, i, j, k) && !world.isRemote) {
			dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
			world.setBlockWithNotify(i, j, k, 0);
		}
	}

	@Override
	public boolean canBlockStay(World world, int i, int j, int k) {
		boolean belowCheck = false;
		if (world.getBlockId(i, j, k) == blockID && world.getBlockMetadata(i, j, k) == 0) {
			belowCheck = (world.getBlockId(i, j - 1, k) == this.blockID && world.getBlockMetadata(i, j - 1, k) != 0);
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
			return TropicraftMod.pineappleItem.shiftedIndex;
		}
		return 0;
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityPlayer, int i, int j, int k, int meta) {

		if (meta == 0) {
			ItemStack itemstack = entityPlayer.inventory.getStackInSlot(entityPlayer.inventory.currentItem);

			// If pineapple is harvested with a sword, drop pineapple chunks
			if (itemstack != null && (itemstack.getItem() instanceof ItemSword ||
					itemstack.getItem() instanceof ItemTropicsSword ||
					itemstack.getItem().getItemUseAction(itemstack) == EnumAction.block)) {

				int count = world.rand.nextInt(4) != 0 ? 3 : 4;
				if (world.rand.nextInt(7) == 0) {
					count = 2;
				}else
					if (world.rand.nextInt(6) == 0) {
						count = 1;
					}else
						if (world.rand.nextInt(5) == 0) {
							count = 5;
						}

				ItemStack cubes;
				for (int c = 0; c < count; c++) {
					cubes = new ItemStack(TropicraftMod.pineappleCube.shiftedIndex, 1, 0);
					if(!world.isRemote)
						this.dropBlockAsItem_do(world, i, j, k, cubes);
				}

			} else {
				// Drop a whole pineapple
				if(!world.isRemote)
					dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
			}
		}
	} 

	@Override
	@SideOnly(Side.CLIENT)
	public int idPicked(World par1World, int par2, int par3, int par4) {
		return TropicraftMod.pineappleItem.shiftedIndex;
	}
}
