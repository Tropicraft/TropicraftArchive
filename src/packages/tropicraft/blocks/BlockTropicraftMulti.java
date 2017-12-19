package tropicraft.blocks;

import java.util.List;

import tropicraft.ModInfo;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTropicraftMulti extends BlockTropicraft {

	@SideOnly(Side.CLIENT)
	protected Icon[] images;
	
	private String[] imageNames, displayNames;
	
	//optional, for example would be "ore_" for the ores, "sapling_" for the saplings, etc
	private String imageNamePrefix;
	
	public BlockTropicraftMulti(int id, Material material, String prefix, String[] displayNames, String[] imageNames) {
		super(id, material);
		
		imageNamePrefix = "";
		
		if (prefix != null && !prefix.isEmpty())
			imageNamePrefix = prefix;
		
		this.imageNames = imageNames;
		this.displayNames = displayNames;
	}
	
	/**
	 * Register all Icons used in this block
	 */
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister iconRegistry) {
		this.blockIcon = iconRegistry.registerIcon(ModInfo.ICONLOCATION + getImageName() + imageNames[0]);

		images = new Icon[imageNames.length];

		for (int i = 0; i < imageNames.length; i++) {
			images[i] = iconRegistry.registerIcon(ModInfo.ICONLOCATION + getImageName() + imageNames[i]);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for (int var4 = 0; var4 < imageNames.length; ++var4)
		{
			par3List.add(new ItemStack(par1, 1, var4));
		}
	}

	@Override
	public String getImageName() {
		return imageNamePrefix;
	}

}
