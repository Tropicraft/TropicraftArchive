package tropicraft.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHalfSlab;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import tropicraft.ModInfo;
import tropicraft.creative.TropiCreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTropicalStep extends BlockHalfSlab {
	public static Block[] modelBlocks = new Block[]{
		TropicraftBlocks.bambooBundle,
		TropicraftBlocks.tropicsBuildingBlock,
		TropicraftBlocks.chunkOHead,
		TropicraftBlocks.tropicsBuildingBlock};
	
	public static String[] blockNames = new String[]{"bamboo", "thatch", "chunk", "palm"};
	
	/** The list of the types of step blocks. */
	public static final String[] blockStepTypes = new String[] {"bamboo", "thatch", "chunk", "palm"};
	
	private Icon bamboo;
	private Icon thatch;
	private Icon chunk;
	private Icon palm;
	
	public BlockTropicalStep(int i, boolean j) {
		super(i, j, Material.rock);
		this.setCreativeTab(TropiCreativeTabs.tabBlock);
		this.setLightOpacity(0);
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    /**
     * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
     */
    public int idPicked(World par1World, int par2, int par3, int par4)
    {
        return TropicraftBlocks.tropicsSingleSlab.blockID;
    }
	
	/**
	 * Get the flammability of this block
	 */
	@Override
	public int getFlammability(IBlockAccess world, int x, int y, int z, int metadata, ForgeDirection face) {
		int type = metadata & 7;
		if (type < modelBlocks.length) {
			return modelBlocks[type].getFlammability(world, x, y, z, metadata, face);
		} else {
			return super.getFlammability(world, x, y, z, metadata, face);
		}
	}
	
	@Override
	public Icon getIcon(int side, int metadata) {
		int type = metadata & 7;
		if (type < modelBlocks.length) {

			switch(type) {
			case 0:
				return bamboo;
			case 1:
				return thatch;
			case 2:
				return chunk;
			case 3:
				return palm;
			default:
				return blockIcon;
			}
		}
		else
			return this.blockIcon;
	}
	

	@Override
	public int idDropped(int i, Random random, int j) {
		return TropicraftBlocks.tropicsSingleSlab.blockID;
	}

	@Override
	protected ItemStack createStackedBlock(int i) {
		return new ItemStack(TropicraftBlocks.tropicsSingleSlab.blockID, 2, i & 7);
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
	
	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		if (par1 != Block.stoneDoubleSlab.blockID && par1 != TropicraftBlocks.tropicsDoubleSlab.blockID)
		{
			for (int var4 = 0; var4 < 4; ++var4)
			{
				par3List.add(new ItemStack(par1, 1, var4));
			}
		}
	}

	/**
	 * Register all Icons used in this block
	 */
	@Override
	public void registerIcons(IconRegister iconRegistry) {
		this.blockIcon = iconRegistry.registerIcon(ModInfo.ICONLOCATION + "bambooBundle_side");
		this.bamboo = iconRegistry.registerIcon(ModInfo.ICONLOCATION + "bambooBundle_side");
		this.thatch = iconRegistry.registerIcon(ModInfo.ICONLOCATION + "thatch_side");
		this.chunk = iconRegistry.registerIcon(ModInfo.ICONLOCATION + "chunk");
		this.palm = iconRegistry.registerIcon(ModInfo.ICONLOCATION + "palmPlanks");
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

		return super.getUnlocalizedName() + "." + blockStepTypes[par1];
	}
}
