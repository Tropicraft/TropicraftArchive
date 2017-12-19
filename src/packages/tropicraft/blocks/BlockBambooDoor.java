package tropicraft.blocks;

import java.util.Random;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.IconFlipped;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tropicraft.ModInfo;
import tropicraft.creative.TropiCreativeTabs;
import tropicraft.items.TropicraftItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBambooDoor extends BlockDoor {

	@SideOnly(Side.CLIENT)
	private Icon[] images;

	private static final String[] imageNames = new String[] {"bamboodoor_bottom", "bamboodoor_top"};

	public BlockBambooDoor(int id) {
		super(id, Material.wood);
		this.disableStats();
		//setCreativeTab(TropiCreativeTabs.tabBlock);
	}
	
	@Override
	public int idDropped(int par1, Random par2Random, int par3)
    {		
        return TropicraftItems.bambooDoor.itemID;
    }

	@SideOnly(Side.CLIENT)
	@Override

	/**
	 * When this method is called, your block should register all the icons it needs with the given IconRegister. This
	 * is the only chance you get to register icons.
	 */
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.images = new Icon[imageNames.length * 2];

		for (int i = 0; i < imageNames.length; ++i)
		{
			this.images[i] = par1IconRegister.registerIcon(ModInfo.ICONLOCATION + imageNames[i]);
			this.images[i + imageNames.length] = new IconFlipped(this.images[i], true, false);
		}
	}    

	@Override
	@SideOnly(Side.CLIENT)
	public int idPicked(World par1World, int par2, int par3, int par4) {
		return TropicraftItems.bambooDoor.itemID;
	}

	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		if (par5 != 1 && par5 != 0)
		{
			int i1 = this.getFullMetadata(par1IBlockAccess, par2, par3, par4);
			int j1 = i1 & 3;
			boolean flag = (i1 & 4) != 0;
			boolean flag1 = false;
			boolean flag2 = (i1 & 8) != 0;

			if (flag)
			{
				if (j1 == 0 && par5 == 2)
				{
					flag1 = !flag1;
				}
				else if (j1 == 1 && par5 == 5)
				{
					flag1 = !flag1;
				}
				else if (j1 == 2 && par5 == 3)
				{
					flag1 = !flag1;
				}
				else if (j1 == 3 && par5 == 4)
				{
					flag1 = !flag1;
				}
			}
			else
			{
				if (j1 == 0 && par5 == 5)
				{
					flag1 = !flag1;
				}
				else if (j1 == 1 && par5 == 3)
				{
					flag1 = !flag1;
				}
				else if (j1 == 2 && par5 == 4)
				{
					flag1 = !flag1;
				}
				else if (j1 == 3 && par5 == 2)
				{
					flag1 = !flag1;
				}

				if ((i1 & 16) != 0)
				{
					flag1 = !flag1;
				}
			}

			return this.images[0 + (flag1 ? imageNames.length : 0) + (flag2 ? 1 : 0)];
		}
		else
		{
			return this.images[0];
		}
	}

    @SideOnly(Side.CLIENT)
    @Override

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getIcon(int par1, int par2)
    {
        return this.images[0];
    }

}
