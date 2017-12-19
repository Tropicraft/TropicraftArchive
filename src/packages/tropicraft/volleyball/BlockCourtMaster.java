package tropicraft.volleyball;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tropicraft.ModInfo;

public class BlockCourtMaster extends BlockContainer implements ITileEntityProvider {

	private String imageName;

	public BlockCourtMaster(int id, String name) {
		super(id, Material.rock);
		imageName = name;
	}

	/**
	 * Called upon block activation (right click on the block.)
	 */
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		if (!world.isRemote) {

			if (world.getBlockTileEntity(x, y, z) instanceof TileEntityCourtMaster) {
				TileEntityCourtMaster cm = (TileEntityCourtMaster)world.getBlockTileEntity(x, y, z);

				cm.court.onClick(par5EntityPlayer);

				return true;
			}
		}

		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityCourtMaster();
	}

	/**
	 * Called when the block is placed in the world.
	 */
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemstack) {
		if (!world.isRemote) {
			world.setBlockTileEntity(x, y, z, this.createNewTileEntity(world));
		}
	}

	/**
	 * Registers all icons used in this block
	 * @param iconRegistry IconRegister instance used to register all icons for this block
	 */
	@Override
	public void registerIcons(IconRegister iconRegistry) {
		this.blockIcon = iconRegistry.registerIcon(ModInfo.MODID + ":" + imageName);
	}

}
