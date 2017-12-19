package tropicraft.blocks;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import tropicraft.ModInfo;
import tropicraft.creative.TropiCreativeTabs;
import tropicraft.items.TropicraftItems;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockBuildingBlock extends BlockTropicalWood {

	//NOTE: add custom Icons here and then register them in the registerIcons method, then EDIT getBlockTextureFromSideAndMetadata below to determine which id should be used with which metadata
	private Icon thatchBundle;
	private Icon palmPlanks;
	private Icon eudialyte, zircon, azurite, zirconium;

	public BlockBuildingBlock(int id) {
		super(id);
		setCreativeTab(TropiCreativeTabs.tabBlock);
	}

	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	public Icon getIcon(int side, int meta)
	{
		switch (meta) {
		case 0:
			return this.thatchBundle;
		case 1:
			return this.palmPlanks;
		case 2:
			return this.eudialyte;
		case 3:
			return this.zircon;
		case 4:
			return this.azurite;
		case 5:
			return this.zirconium;
		default:
			return this.blockIcon;
		}
	}
	
	@Override
	public float getBlockHardness(World par1World, int par2, int par3, int par4) {
		int meta = par1World.getBlockMetadata(par2, par3, par4);
		switch(meta) {
			case 0:
				this.setResistance(0.1F);
				return 0.1F;
			case 1:
				this.setResistance(2.0F);
				return 2.0F;
			case 2:
				this.setResistance(5F);
				return 2.1F;
			case 3:
				this.setResistance(3F);
				return 3.2F;
			case 4:
				this.setResistance(3F);
				return 4.3F;
			case 5:
				this.setResistance(3F);
				return 4.3F;
		}
		this.setResistance(3F);
		return 0F;
	}
	
	@Override
    public float getPlayerRelativeBlockHardness(EntityPlayer entityplayer, World par2World, int par3, int par4, int par5)
    {
    	//unsure of what the boolean here does, may need to change at a later date
		int meta = par2World.getBlockMetadata(par3, par4, par5);
		if(meta < 2) {
	        return entityplayer.getCurrentPlayerStrVsBlock(Block.wood, false, par2World.getBlockMetadata(par3, par4, par5)) / this.getBlockHardness(par2World, par3, par4, par5) / 30F;
		} else {
	        return entityplayer.getCurrentPlayerStrVsBlock(Block.stone, false, par2World.getBlockMetadata(par3, par4, par5)) / this.getBlockHardness(par2World, par3, par4, par5) / 30F;
		}
    }
	
    @Override
    public int damageDropped(int i) {
        return i;
    }

	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	public void getSubBlocks(int par1, CreativeTabs creativeTabs, List list)  {
		for (int meta = 0; meta < 6; meta++) {
			list.add(new ItemStack(par1, 1, meta));
		}
	}
	
    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving, ItemStack par6ItemStack) {
    	super.onBlockPlacedBy(par1World, par2, par3, par4, par5EntityLiving, par6ItemStack);
    }

	/**
	 * Register all Icons used in this block
	 */
	@Override
	public void registerIcons(IconRegister iconRegistry) {
		this.blockIcon = iconRegistry.registerIcon(ModInfo.ICONLOCATION + getImageName());
		this.thatchBundle = iconRegistry.registerIcon(ModInfo.ICONLOCATION + "thatch_side");
		this.palmPlanks = iconRegistry.registerIcon(ModInfo.ICONLOCATION + "palmPlanks");
		this.eudialyte = iconRegistry.registerIcon(ModInfo.ICONLOCATION + "block_eudialyte");
		this.zircon = iconRegistry.registerIcon(ModInfo.ICONLOCATION + "block_zircon");
		this.azurite = iconRegistry.registerIcon(ModInfo.ICONLOCATION + "block_azurite");
		this.zirconium = iconRegistry.registerIcon(ModInfo.ICONLOCATION + "block_zirconium");
	}

	@Override
	public String getImageName() {
		return "thatch_side";
	}

}
