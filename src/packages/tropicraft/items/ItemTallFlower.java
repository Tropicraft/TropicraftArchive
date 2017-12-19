package tropicraft.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCloth;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import tropicraft.ModInfo;
import tropicraft.blocks.TropicraftBlocks;
import tropicraft.creative.TropiCreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemTallFlower extends ItemTropicraft {

	@SideOnly(Side.CLIENT)
	private Icon iris;

	private static final String[] flowerNames = new String[]{"Pineapple", "Iris"};

	public ItemTallFlower(int id) {
		super(id);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		setCreativeTab(TropiCreativeTabs.tabDecorations);
	}	

	/**
	 * Returns the metadata of the block which this Item (ItemBlock) can place
	 */
	public int getMetadata(int par1)
	{
		return par1;
	}

	@SideOnly(Side.CLIENT)

	/**
	 * Gets an icon index based on an item's damage value
	 */
	public Icon getIconFromDamage(int par1)
	{
		int j = MathHelper.clamp_int(par1, 0, 15);
		return j == 9 ? this.itemIcon : this.iris;
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float par8, float par9, float par10) {

		int spawnDamage = 0;
		int spawnID = TropicraftBlocks.tallFlower.blockID;
		//<newthead> 0-6 = pineapple bottom growth, 9 = pineapple top, 8 = iris bottom, 15 = iris top
		if (itemstack != null) {
			spawnDamage = itemstack.getItemDamage() == 9 ? 0 : itemstack.getItemDamage() == 15 ? 8 : itemstack.getItemDamage();
		}
		if (world.getBlockId(i, j, k) == Block.snow.blockID) {
			l = 0;
		} else {
			if (l == 0) {
				j--;
			}
			if (l == 1) {
				j++;
			}
			if (l == 2) {
				k--;
			}
			if (l == 3) {
				k++;
			}
			if (l == 4) {
				i--;
			}
			if (l == 5) {
				i++;
			}
		}
		if (itemstack.stackSize == 0) {
			return false;
		}
		if (world.canPlaceEntityOnSide(spawnID, i, j, k, false, l, (Entity)null, itemstack)) {
			Block block = Block.blocksList[spawnID];
			if (world.setBlock(i, j, k, spawnID, spawnDamage, 3)) {
				block.onBlockPlaced(world, i, j, k, spawnDamage, par8, par9, par10, 0);
				block.onBlockPlacedBy(world, i, j, k, entityplayer, itemstack);
				world.playSoundEffect((float) i + 0.5F, (float) j + 0.5F, (float) k + 0.5F, block.stepSound.stepSoundName, (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
				itemstack.stackSize--;
			}
		}
		return true;
	}


	@SideOnly(Side.CLIENT)

	/**
	 * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
	 */
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		par3List.add(new ItemStack(par1, 1, 9));
		par3List.add(new ItemStack(par1, 1, 15));
	}

	/**
	 * Register all Icons used in this item
	 */
	@Override
	public void registerIcons(IconRegister iconRegistry) {
		this.itemIcon = iconRegistry.registerIcon(ModInfo.ICONLOCATION + "pineapple");
		this.iris = iconRegistry.registerIcon(ModInfo.ICONLOCATION + "iris");
	}

	@Override
	public String getImageName() {
		return "pineapple";
	}
	
    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
     * different names based on their damage or NBT.
     */
	@Override
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        return "tallFlowerItem" + "." + flowerNames[par1ItemStack.getItemDamage() == 9 ? 0 : 1];
    }
}
