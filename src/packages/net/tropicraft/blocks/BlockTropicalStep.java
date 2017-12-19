package net.tropicraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHalfSlab;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.tropicraft.mods.TropicraftMod;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTropicalStep extends BlockHalfSlab {

	public static Block[] modelBlocks = new Block[]{
		TropicraftMod.bambooBlock,
		TropicraftMod.thatchBlock,
		TropicraftMod.chunkBlock,
		TropicraftMod.palmPlanks};
	public static String[] blockNames = new String[]{"bamboo", "thatch", "headB", "palm"};

	/** The list of the types of step blocks. */
	public static final String[] blockStepTypes = new String[] {"bamboo", "thatch", "chunk", "palm"};

	public BlockTropicalStep(int i, boolean j) {
		super(i, j, Material.rock);
		this.setCreativeTab(TropicraftMod.tabBlock);
		this.setTextureFile("/tropicalmod/tropiterrain.png");
		this.setLightOpacity(0);
	}

	@Override
	public int getFlammability(IBlockAccess world, int x, int y, int z, int metadata, ForgeDirection face) {
		int type = metadata & 7;
		if (type < modelBlocks.length) {
			return modelBlocks[type].getFlammability(world, x, y, z, metadata, face);
		} else {
			return super.getFlammability(world, x, y, z, metadata, face);
		}
	}

	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) {
		int type = j & 7;
		if (type < modelBlocks.length) {

			switch(type) {
			case 0:
				return TropicraftMod.bambooBlock.blockIndexInTexture;
			case 1:
				return TropicraftMod.thatchBlock.blockIndexInTexture;
			case 2:
				return TropicraftMod.chunkBlock.blockIndexInTexture;
			case 3:
				return TropicraftMod.palmPlanks.blockIndexInTexture;
			default:
				return 0;
			}
		}
		else
			return 0;
	}

	/**
	 * Returns the block texture based on the side being looked at.  Args: side
	 */
	public int getBlockTextureFromSide(int par1)
	{
		return this.getBlockTextureFromSideAndMetadata(par1, 0);
	}

	@Override
	public int idDropped(int i, Random random, int j) {
		return TropicraftMod.tropicalSingleSlab.blockID;
	}

	@Override
	protected ItemStack createStackedBlock(int i) {
		return new ItemStack(TropicraftMod.tropicalSingleSlab.blockID, 2, i & 7);
	}
	
	/**
	 * Gets the hardness of block at the given coordinates in the given world, relative to the ability of the given
	 * EntityPlayer.
	 */
	@Override
	public float getPlayerRelativeBlockHardness(EntityPlayer par1EntityPlayer, World par2World, int j, int k, int l)
	{
		int type = par2World.getBlockMetadata(j, k, l) & 7;
		
		if (type < modelBlocks.length) {
			return modelBlocks[type].getPlayerRelativeBlockHardness(par1EntityPlayer, par2World, j, k, l);
		} else {
			return super.getPlayerRelativeBlockHardness(par1EntityPlayer, par2World, j, k, l);
		}
	}

	@Override
	public boolean canHarvestBlock(EntityPlayer player, int meta) {

		int type = meta & 7;
		if (type < modelBlocks.length) {
			return modelBlocks[type].canHarvestBlock(player, meta);
		} else {
			return super.canHarvestBlock(player, meta);
		}
	}

	@SideOnly(Side.CLIENT)

	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		if (par1 != Block.stoneDoubleSlab.blockID && par1 != TropicraftMod.tropicalDoubleSlab.blockID)
		{
			for (int var4 = 0; var4 < 4; ++var4)
			{
				par3List.add(new ItemStack(par1, 1, var4));
			}
		}
	}

	/**
	 * Returns the slab block name with step type.
	 */
	@Override
	public String getFullSlabName(int par1)
	{
		if (par1 < 0 || par1 >= blockStepTypes.length)
		{
			par1 = 0;
		}

		return super.getBlockName() + "." + blockStepTypes[par1];
	}


}
