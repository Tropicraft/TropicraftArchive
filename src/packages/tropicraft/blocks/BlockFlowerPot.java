package tropicraft.blocks;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tropicraft.ModInfo;
import tropicraft.ModRenderIds;
import tropicraft.blocks.tileentities.TileEntityFlowerPot;
import tropicraft.items.TropicraftItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFlowerPot extends BlockContainer implements ITileEntityProvider {

	public BlockFlowerPot(int id) {
		super(id, Material.circuits);
		this.setBlockBoundsForItemRender();
		this.disableStats();
	}

	/**
	 * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
	 * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
	 */
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType()
	{
		return ModRenderIds.flowerPotRenderId;
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
	 */
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	/**
	 * Sets the block's bounds for rendering it as an item
	 */
	@Override
	public void setBlockBoundsForItemRender()
	{
		float var1 = 0.375F;
		float var2 = var1 / 2.0F;
		this.setBlockBounds(0.5F - var2, 0.0F, 0.5F - var2, 0.5F + var2, var1, 0.5F + var2);
	}

	/**
	 * Called upon block activation (right click on the block.)
	 */
	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		ItemStack var10 = par5EntityPlayer.inventory.getCurrentItem();

		if (var10 == null)
		{
			return false;
		}
        else if (par1World.getBlockMetadata(par2, par3, par4) != 0)
        {
            return false;
        }
		else
		{
			int var11 = getMetaForPlant(var10);

			if (var11 > 0)
			{
				TileEntityFlowerPot pot = (TileEntityFlowerPot) par1World.getBlockTileEntity(par2, par3, par4);

				pot.setDamage((short) var11);
				
				par1World.setBlockMetadataWithNotify(par2, par3, par4, var11, 2);

				par1World.markBlockForUpdate(par2, par3, par4);

				if (!par5EntityPlayer.capabilities.isCreativeMode && --var10.stackSize <= 0)
				{
					par5EntityPlayer.inventory.setInventorySlotContents(par5EntityPlayer.inventory.currentItem, (ItemStack)null);
				}

				return true;
			}
			else
			{
				return false;
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
	 */
	public int idPicked(World par1World, int par2, int par3, int par4)
	{
		ItemStack var5 = getPlantForMeta(par1World.getBlockMetadata(par2, par3, par4));
		return var5 == null ? TropicraftItems.flowerPot.itemID : var5.itemID;
	}

	/**
	 * Get the block's damage value (for use with pick block).
	 */
	@Override
	public int getDamageValue(World par1World, int par2, int par3, int par4)
	{
		ItemStack var5 = getPlantForMeta(par1World.getBlockMetadata(par2, par3, par4));
		return var5 == null ? TropicraftItems.flowerPot.itemID : var5.getItemDamage();
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityFlowerPot();
	}

	@Override
    @SideOnly(Side.CLIENT)

    /**
     * Returns true only if block is flowerPot
     */
    public boolean isFlowerPot()
    {
        return true;
    }

	/**
	 * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
	 */
	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		return super.canPlaceBlockAt(par1World, par2, par3, par4) && par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4);
	}

	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
	 * their own) Args: x, y, z, neighbor blockID
	 */
	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		if (!par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4))
		{
			this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
            par1World.setBlockToAir(par2, par3, par4);
		}
	}

	/**
	 * Drops the block items with a specified chance of dropping the specified items
	 */
	@Override
	public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
	{
		super.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5, par6, par7);
		System.out.println("meh" + par5);

		if (par5 > 0)
		{
			ItemStack var8 = getPlantForMeta(par5);
			System.out.println("meh2");
			if (var8 != null)
			{
	
				this.dropBlockAsItem_do(par1World, par2, par3, par4, var8);
			}
		}
	}

	/**
	 * Returns the ID of the items to drop on destruction.
	 */
	@Override
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return TropicraftItems.flowerPot.itemID;
	}

	/**
	 * Return the item associated with the specified flower pot metadata value.
	 */
	public static ItemStack getPlantForMeta(int par0)
	{
		switch (par0)
		{
		case 1:
			return new ItemStack(TropicraftBlocks.tropicsFlowers, 1, 0);
		case 2:
			return new ItemStack(TropicraftBlocks.tropicsFlowers, 1, 1);
		case 3:
			return new ItemStack(TropicraftBlocks.tropicsFlowers, 1, 2);
		case 4:
			return new ItemStack(TropicraftBlocks.tropicsFlowers, 1, 3);
		case 5:
			return new ItemStack(TropicraftBlocks.tropicsFlowers, 1, 4);
		case 6:
			return new ItemStack(TropicraftBlocks.tropicsFlowers, 1, 5);
		case 7:
			return new ItemStack(TropicraftBlocks.tropicsFlowers, 1, 6);
		case 8:
			return new ItemStack(TropicraftBlocks.tropicsFlowers, 1, 7);
		case 9:
			return new ItemStack(TropicraftBlocks.tropicsFlowers, 1, 8);
		case 10:
			return new ItemStack(TropicraftBlocks.tropicsFlowers, 1, 9);
		case 11:
			return new ItemStack(TropicraftBlocks.tropicsFlowers, 1, 10);
		case 12:
			return new ItemStack(TropicraftBlocks.tropicsFlowers, 1, 11);
		case 13:
			return new ItemStack(TropicraftBlocks.tropicsFlowers, 1, 12);
		case 14:
			return new ItemStack(TropicraftBlocks.tropicsFlowers, 1, 13);
		case 15:
			return new ItemStack(TropicraftBlocks.tropicsFlowers, 1, 14);
		case 16:
			return new ItemStack(TropicraftBlocks.tropicsFlowers, 1, 15);
		case 17:
			return new ItemStack(TropicraftItems.tallFlower, 1, 9);
		case 18:
			return new ItemStack(TropicraftBlocks.tallFlower, 1, 15);
		case 19:
			return new ItemStack(TropicraftBlocks.saplings, 1, 0);
		case 20:
			return new ItemStack(TropicraftBlocks.saplings, 1, 1);
		case 21:
			return new ItemStack(TropicraftBlocks.saplings, 1, 2);
		case 22:
			return new ItemStack(TropicraftBlocks.saplings, 1, 3);
		case 23:
			return new ItemStack(TropicraftBlocks.saplings, 1, 4);
		default:
			return null;
		}
	}

	/**
	 * Return the flower pot metadata value associated with the specified item.
	 */
	public static int getMetaForPlant(ItemStack par0ItemStack)
	{
		int var1 = par0ItemStack.getItem().itemID;
		int damage = par0ItemStack.getItemDamage() + 1;

		if (var1 == TropicraftBlocks.tropicsFlowers.blockID) {
			return damage;
		} else
			if (var1 == TropicraftItems.tallFlower.itemID) {
				return damage == 9 ? 17 : 18;
			} else
				if (var1 == TropicraftBlocks.saplings.blockID) {
					return 19 + damage - 1;
				}

		return 0;
	}
	
	@Override
	public void registerIcons(IconRegister registry) {
		this.blockIcon = registry.registerIcon(ModInfo.ICONLOCATION + "flowerpot");
	}

}
