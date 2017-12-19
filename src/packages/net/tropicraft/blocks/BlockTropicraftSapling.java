package net.tropicraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.tropicraft.mods.TropicraftMod;
import net.tropicraft.world.worldgen.WorldGenTropicraftCurvedPalm;
import net.tropicraft.world.worldgen.WorldGenTropicraftFruitTrees;
import net.tropicraft.world.worldgen.WorldGenTropicraftLargePalmTrees;
import net.tropicraft.world.worldgen.WorldGenTropicraftNormalPalms;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTropicraftSapling extends TropicraftBlock {

	public int[] textures; //:D
	public String[] names;

	enum sexy {COROSUS, COJO, RUBE, FISHTACO}; //8D
	protected Random rand;
	protected Random grow;

	public BlockTropicraftSapling(int i, int[] j, String[] s, Material material) {
		super(i, material);
		textures = j;
		names = s;
		setTickRandomly(true);
		setRequiresSelfNotify();
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		return null;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving living) {
		if (!canBlockStay(world, i, j, k, world.getBlockMetadata(i, j, k))) {
			dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
			world.setBlockWithNotify(i, j, k, 0);
		}
	}

	protected boolean canThisPlantGrowOnThisBlockID(int meta, int i) {
		if (meta == 0) {
			return i == Block.sand.blockID || i == TropicraftMod.purifiedSand.blockID;
		} else if (meta < 5) {
			return i == Block.grass.blockID || i == Block.dirt.blockID;
		}
		return false;
	}

	public boolean canBlockStay(World world, int i, int j, int k, int meta) {
		return (world.getFullBlockLightValue(i, j, k) >= 8 || world.canBlockSeeTheSky(i, j, k)) && canThisPlantGrowOnThisBlockID(meta, world.getBlockId(i, j - 1, k));
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {

		if (!world.isRemote) {
			super.updateTick(world, i, j, k, random);
			if (!canBlockStay(world, i, j, k, world.getBlockMetadata(i, j, k))) {
				dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
				world.setBlockWithNotify(i, j, k, 0);
			}

			if (world.getBlockLightValue(i, j + 1 , k) >= 9 && random.nextInt(30) == 0) {
				int l = world.getBlockMetadata(i, j, k);
				//            if ((l & 8) == 0) {
					//                world.setBlockMetadataWithNotify(i, j, k, l | 8);
					//            } else {

						growTree(world, i, j, k, world.rand);
						//}
			}
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int par5) {
		if (!canBlockStay(world, i, j, k, world.getBlockMetadata(i, j, k))) {
			dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
			world.setBlockWithNotify(i, j, k, 0);
		}
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) {
		if (j < textures.length) {
			return textures[j];
		} else {
			return 0;
		}
	}

	public void growTree(World world, int i, int j, int k, Random random) {

		int type = world.getBlockMetadata(i, j, k);
		WorldGenerator gen = null;

		if (type == 0) {
			int b = random.nextInt(3);
			if (b == 0) {
				gen = new WorldGenTropicraftLargePalmTrees(true);
			} else if (b == 1) {
				gen = new WorldGenTropicraftCurvedPalm(true);
			} else if (b == 2) {
				gen = new WorldGenTropicraftNormalPalms(true);
			}
		} else if (type < 5) {
			gen = new WorldGenTropicraftFruitTrees(true, type - 1);
		}

		if (gen != null) {
			world.setBlock(i, j, k, 0);
			if (!gen.generate(world, random, i, j, k)) {
				world.setBlockAndMetadata(i, j, k, blockID, type);
			}
		}
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int doop, float ddd, float eee, float fff) {

		ItemStack itemstack = entityplayer.inventory.getCurrentItem();
		if (!world.isRemote && itemstack != null && itemstack.getItem() == Item.dyePowder && itemstack.getItemDamage() == 15)//change this to change what makes them grow
		{
			itemstack.stackSize--;
			growTree(world, i, j, k, world.rand);
			return true;
		}
		return false;
	}

	@Override
	public int getRenderType() {
		return 1;
	}

	@Override
	public int damageDropped(int metadata) {
		return metadata;
	}

	@SideOnly(Side.CLIENT)

	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		par3List.add(new ItemStack(par1, 1, 0));		//palm
		par3List.add(new ItemStack(par1, 1, 1));		//these next 4 are fruit saplings
		par3List.add(new ItemStack(par1, 1, 2));
		par3List.add(new ItemStack(par1, 1, 3));
		par3List.add(new ItemStack(par1, 1, 4));
	}

}
