package tropicraft.blocks;

import tropicraft.ModInfo;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;

public class BlockTropicraftImpl extends BlockTropicraft {

	/**
	 * Simple implementation of BlockTropicraft
	 * @param id Block id
	 * @param mat Material
	 * @param imageName name of the image to use for this Block
	 */
	public BlockTropicraftImpl(int id, Material mat, String imageName) {
		super(id, mat);
		this.imageName = imageName;
	}

	/**
	 * Registers all icons used in this block
	 * @param iconRegistry IconRegister instance used to register all icons for this block
	 */
	@Override
	public void registerIcons(IconRegister iconRegistry) {
		this.blockIcon = iconRegistry.registerIcon(ModInfo.MODID + ":" + getImageName());
	}

	/**
	 * @return Get the image name for this block
	 */
	@Override
	public String getImageName() {
		return this.imageName;
	}

}
