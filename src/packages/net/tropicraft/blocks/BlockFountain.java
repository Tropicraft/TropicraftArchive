package net.tropicraft.blocks;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.tropicraft.blocks.liquids.LAPI;
import net.tropicraft.mods.TropicraftMod;

public class BlockFountain extends Block {

	/**
	 * Array of hex values of all the colors used for the fountain blocks
	 */
	private static final int[] waterColors = new int[] {0xffffff, 0xD7793A, 0xB859C0, 0x7793CC, 0xffff00, 0x00FF40, 0xFFB6C1, 0x696969,
		0xC0C0C0, 0x2F728C, 0x9932CC, 0x0000ff, 0x8B4513, 0x006400, 0xff0000, 0x000000};

	public BlockFountain(int i) {
		super(i, Material.rock);
		this.setCreativeTab(TropicraftMod.tabBlock);
		this.setTextureFile("/tropicalmod/fountain_sheet.png");
	}

	/**
	 * Called when the block is placed in the world.
	 * <Cojo> so for all "flowing" blocks i would set the metadata to current metadata | 0x2
	 */
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving)
	{
		int var6 = MathHelper.floor_double((double)(par5EntityLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

		byte meta = 0x0;

		if (var6 == 0)
		{
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 0x0);
			meta = 0x0;
		}

		if (var6 == 1)
		{
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 0x1);
			meta = 0x1;
		}

		if (var6 == 2)
		{
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 0x0);
			meta = 0x0;
		}

		if (var6 == 3)
		{
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 0x1);
			meta = 0x1;
		}

		int y = 1;

		boolean shouldGenFountain = false;

		while (par1World.isAirBlock(par2, par3 - y, par4)) {

			int id = par1World.getBlockId(par2, par3 - y - 1, par4);
			
			if (!par1World.isAirBlock(par2, par3 - y - 1, par4) && (id == TropicraftMod.waterMovingTropics.blockID || id == TropicraftMod.waterStillTropics.blockID)) {
				shouldGenFountain = true;
			}

			y++;

		}

		y = 1;

		if (shouldGenFountain) {
			while (par1World.isAirBlock(par2, par3 - y, par4)) {
				par1World.setBlockAndMetadataWithNotify(par2, par3 - y, par4, blockID, meta | 0x2);

				y++;

			}
		}
	}

	@SideOnly(Side.CLIENT)

	/**
	 * Returns which pass should this block be rendered on. 0 for solids and 1 for alpha
	 */
	public int getRenderBlockPass()
	{
		return 1;
	}

	@Override
	public int getRenderType() {
		return TropicraftMod.fountainRenderId;
	} 

	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess world, int i, int j, int k) {
		int color = 0;//waterColors[3];

		if (world.getBlockId(i, j + 1, k) == this.blockID) {
			//		System.out.println("returning higher block");
			return Block.blocksList[world.getBlockId(i, j + 1, k)].colorMultiplier(world, i, j + 1, k);
		} else
			if (world.getBlockId(i, j + 1, k) == Block.cloth.blockID) {
				int damage = world.getBlockMetadata(i, j + 1, k);

				//set color based on color of wool block above this block

				if (damage >= 0 && damage <= 15) {
					color = waterColors[damage];
				}
			}

		//	System.out.println("Returning color: " + color);

		return color;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}


}
