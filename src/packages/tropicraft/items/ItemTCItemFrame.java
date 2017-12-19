package tropicraft.items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemHangingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import tropicraft.ModInfo;
import tropicraft.creative.TropiCreativeTabs;
import tropicraft.entities.items.EntityTCItemFrame;

public class ItemTCItemFrame extends ItemHangingEntity {

	private final Class hangingEntityClass;
	private boolean shouldDropContents;
	
	

	public ItemTCItemFrame(int par1, Class par2Class, boolean shouldDropContents) {
		super(par1, par2Class);
		this.hangingEntityClass = par2Class;
		this.setCreativeTab(TropiCreativeTabs.tabDecorations);
		this.shouldDropContents = shouldDropContents;        //koa use this
	}

	/**
	 * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
	 * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
	 */
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
	{
		if (par7 == 0)
		{
			return false;
		}
		else if (par7 == 1)
		{
			return false;
		}
		else
		{
			int i1 = Direction.facingToDirection[par7];
			EntityTCItemFrame entityhanging = this.createHangingEntity(par3World, par4, par5, par6, i1);

			if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack))
			{
				return false;
			}
			else
			{
				if (entityhanging != null && entityhanging.onValidSurface())
				{
					if (!par3World.isRemote)
					{
						par3World.spawnEntityInWorld(entityhanging);
					}

					--par1ItemStack.stackSize;
				}

				return true;
			}
		}
	}

	/**
	 * Create the hanging entity associated to this item.
	 */
	private EntityTCItemFrame createHangingEntity(World par1World, int par2, int par3, int par4, int par5)
	{
		return new EntityTCItemFrame(par1World, par2, par3, par4, par5, shouldDropContents);
	}
	
	@Override
	public void registerIcons(IconRegister iconRegistry) {
		this.itemIcon = iconRegistry.registerIcon(ModInfo.ICONLOCATION + "itemframe");
	}
}
