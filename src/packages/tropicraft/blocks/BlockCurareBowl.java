package tropicraft.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tropicraft.ModInfo;
import tropicraft.blocks.tileentities.TileEntityCurareBowl;
import tropicraft.creative.TropiCreativeTabs;

public class BlockCurareBowl extends BlockContainer {

	public BlockCurareBowl(int par1) {
		super(par1, Material.rock);
		this.setBlockBounds(0.3f, 0.0f, 0.3f, 0.7f, 0.45f, 0.7f);
		this.setCreativeTab(TropiCreativeTabs.tabBlock);
	}

	/**
	 * ejects contained items into the world, and notifies neighbours of an update, as appropriate
	 */
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
		TileEntityCurareBowl bowl = (TileEntityCurareBowl)par1World.getBlockTileEntity(par2, par3, par4);
		
		if (bowl != null && !par1World.isRemote) {
			if (!bowl.hasMetMaxNumClicks()) {
				for (int var8 = 0; var8 < bowl.getIngredients().length; ++var8) {
					ItemStack var9 = bowl.getIngredientList().get(var8);

					if (var9 != null)
					{
						float var10 = par1World.rand.nextFloat() * 0.8F + 0.1F;
						float var11 = par1World.rand.nextFloat() * 0.8F + 0.1F;
						EntityItem item = new EntityItem(par1World, par2 + var10, par3 + var10, par4 + var11, var9);
						
						par1World.spawnEntityInWorld(item);
					}
				}
			} else {
				bowl.dropResult();
			}
		}

		par1World.removeBlockTileEntity(par2, par3, par4);
		
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileEntityCurareBowl();
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
		return super.canPlaceBlockAt(par1World, par2, par3, par4) && par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4);
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int doop, float f1, float f2, float f3) {
		if (!world.isRemote && entityplayer.getCurrentEquippedItem() != null && entityplayer.getCurrentEquippedItem().getItem() != null) {
			TileEntityCurareBowl bowl = (TileEntityCurareBowl) world.getBlockTileEntity(i, j, k);

			if (entityplayer.getCurrentEquippedItem().getItem().itemID == Item.stick.itemID) {


				if (bowl.isBowlFull()) {
					bowl.incrementNumClicks();

					if (bowl.hasMetMaxNumClicks()) {
						bowl.resetClicks();
						bowl.dropResult();
					}
				}
			} else
				if (entityplayer.getCurrentEquippedItem().getItem().itemID == TropicraftBlocks.tropicsFlowers.blockID) {
					bowl.addIngredient(entityplayer.getCurrentEquippedItem());
					
					if (!entityplayer.capabilities.isCreativeMode)
						entityplayer.getCurrentEquippedItem().stackSize--;
				}
		}

		return true;
	}
	

	/**
	 * Registers all icons used in this block
	 * @param iconRegistry IconRegister instance used to register all icons for this block
	 */
	@Override
	public void registerIcons(IconRegister iconRegistry) {
		this.blockIcon = iconRegistry.registerIcon(ModInfo.ICONLOCATION + getImageName());
	}

	/**
	 * @return Get the image name for this block
	 */
	public String getImageName() {
		return "curarebowl";
	}
}
