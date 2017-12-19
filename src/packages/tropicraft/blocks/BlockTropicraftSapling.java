package tropicraft.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import tropicraft.creative.TropiCreativeTabs;
import tropicraft.items.TropicraftItems;
import tropicraft.world.worldgen.WorldGenBentRainforestTree;
import tropicraft.world.worldgen.WorldGenMedRTree;
import tropicraft.world.worldgen.WorldGenTallRTree;
import tropicraft.world.worldgen.WorldGenTualang;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTropicraftSapling extends BlockTropicraftMulti {

	public BlockTropicraftSapling(int id, Material material, String prefix, String[] displayNames, String[] imageNames) {
		super(id, material, prefix, displayNames, imageNames);
		setTickRandomly(true);
		disableStats();
		setCreativeTab(TropiCreativeTabs.tabBlock);
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
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase living, ItemStack stack) {
		if (!canBlockStay(world, i, j, k, world.getBlockMetadata(i, j, k))) {
			dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
			world.setBlockToAir(i, j, k);
		}
	}

	protected boolean canThisPlantGrowOnThisBlockID(int meta, int i) {
		if (meta == 0) {
			return i == Block.sand.blockID || i == TropicraftBlocks.purifiedSand.blockID;
		} else {//if (meta < 5) {
			return i == Block.grass.blockID || i == Block.dirt.blockID;
		}
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
				world.setBlockToAir(i, j, k);
			}

			if (world.getBlockLightValue(i, j + 1 , k) >= 9 && random.nextInt(30) == 0) {
				int l = world.getBlockMetadata(i, j, k);
				growTree(world, i, j, k, world.rand);
			}
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int par5) {
		if (!canBlockStay(world, i, j, k, world.getBlockMetadata(i, j, k))) {
			dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
			world.setBlockToAir(i, j, k);
		}
	}

	public void growTree(World world, int i, int j, int k, Random random) {
		
		int type = world.getBlockMetadata(i, j, k);
		WorldGenerator gen = null;

		if (type == 0) {
			int b = random.nextInt(3);
			if (b == 0) {
				gen = new tropicraft.world.worldgen.WorldGenTropicraftLargePalmTrees(true);
			} else if (b == 1) {
				gen = new tropicraft.world.worldgen.WorldGenTropicraftCurvedPalm(true);
			} else if (b == 2) {
				gen = new tropicraft.world.worldgen.WorldGenTropicraftNormalPalms(true);
			}
		} else if (type < 5) {
			gen = new tropicraft.world.worldgen.WorldGenTropicraftFruitTrees(true, type - 1);
		} else if (type == 5) {
			gen = randomRainforestTreeGen(world);
		}

		if (gen != null) {
			world.setBlock(i, j, k, 0);
			if (!gen.generate(world, random, i, j, k)) {
				world.setBlock(i, j, k, blockID, type, 3);
			}
		}
	}


	private WorldGenerator randomRainforestTreeGen(World world) {
		Random rand = new Random();
		int type = rand.nextInt(4);
		
		switch(type) {
		case 0:
			return new WorldGenTallRTree(world, rand, true, false);
		case 1:
			return new WorldGenMedRTree(world, rand, true, false);
		case 2:
			return new WorldGenBentRainforestTree(world, rand, false);
		case 3:
			return new WorldGenTualang(world, rand, 18, 9, false);
		default:
			return new WorldGenTualang(world, rand, 25, 10, false);
		}
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
		for (int i = 0; i < 6; i++) {
			par3List.add(new ItemStack(par1, 1, i));
		}
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int doop, float ddd, float eee, float fff) {

		ItemStack itemstack = entityplayer.inventory.getCurrentItem();
		if (!world.isRemote && itemstack != null && ((itemstack.getItem() == Item.dyePowder && itemstack.getItemDamage() == 15) || (itemstack.getItem() == TropicraftItems.fertilizer)))//change this to change what makes them grow
		{
			itemstack.stackSize--;
			growTree(world, i, j, k, world.rand);
			return true;
		}
		return false;
	}

	@Override
	public Icon getIcon(int i, int j) {
		return images[j];
	}

}
