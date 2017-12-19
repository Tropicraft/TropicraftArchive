package tropicraft.blocks;
import tropicraft.ModInfo;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;

public abstract class BlockTropicraft extends Block {
	
	/**
	 * Name of the image to be loaded for this block, used in BlockTropicraftImpl
	 */
	public String imageName;
	
	/**
	 * Simple constructor that most Tropicraft blocks go through upon construction
	 * @param material Material used in this block
	 * @param id block id
	 */
	public BlockTropicraft(int id, Material material) {
		super(id, material);
	}
	
	/**
	 * Simple constructor that most Tropicraft blocks go through upon construction
	 * @param material Material used in this block
	 * @param id block id
	 * @param tab tab to display this block on in Creative Mode
	 */
	public BlockTropicraft(int id, Material material, CreativeTabs tab) {
		super(id, material);
		this.setCreativeTab(tab);
	}

	/**
	 * Register all Icons used in this block
	 */
	@Override
	public void registerIcons(IconRegister iconRegistry) {
		this.blockIcon = iconRegistry.registerIcon(ModInfo.ICONLOCATION + getImageName());
	}
	
	/**
	 * Returns the file name of the image icon
	 * @return File name of image icon for this Block
	 */
	public abstract String getImageName();
}
