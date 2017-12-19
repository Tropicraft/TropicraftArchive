package net.tropicraft.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.tropicraft.entities.EntityLostMask;
import net.tropicraft.mods.TropicraftMod;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemAshenMask extends ItemArmor {

	public int type;
	public static String maskTypeNames[] = {
		"Square Zord",
		"Horn Monkey",
		"Oblongatron",
		"Headinator",
		"Square Horn",
		"Screw Attack",
		"The Brain",
		"Bat Boy",
		"Ashen Mask",
		"Ashen Mask",
		"Ashen Mask",
		"Ashen Mask",
		"Ashen Mask"
	};

	//public final int armorType;
	//public final int damageReduceAmount;
	//public final int renderIndex;
	//private final EnumArmorMaterial material;
	public ItemAshenMask(int i, int j, int k) {
		super(i, EnumArmorMaterial.CHAIN, j, k);
		type = 0;
		//material = EnumArmorMaterial.CLOTH;
		//armorType = k;
		//renderIndex = j;
		//damageReduceAmount = material.getDamageReductionAmount(k);
		setMaxDamage(0);
		maxStackSize = 1;
		setHasSubtypes(true);
		this.setCreativeTab(TropicraftMod.tabMisc);
		this.setTextureFile("/tropicalmod/tropiitems.png");
	}
	
    @Override
    @SideOnly(Side.CLIENT)

    /**
     * Gets an icon index based on an item's damage value and the given render pass
     */
    public int getIconFromDamageForRenderPass(int par1, int par2)
    {
        return this.getIconFromDamage(par1);
    }
	
    @SideOnly(Side.CLIENT)

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    @Override
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int var4 = 0; var4 < maskTypeNames.length; ++var4)
        {
            par3List.add(new ItemStack(par1, 1, var4));
        }
    }

	@Override
	public String getItemNameIS(ItemStack itemstack) {
		return getItemName() + "." + itemstack.getItemDamage();
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float par8, float par9, float par10) {
		byte byte0 = 0;
		//System.out.println(l);
		switch (l) {
		case 0:
			return false;
		case 1:
			return false;
		case 3:
			byte0 = 2;
			break;
		case 4:
			byte0 = 1;
			break;
		case 5:
			byte0 = 3;
			break;
		default:
			break;
		}
		if (!entityplayer.canPlayerEdit(i, j, k, l, itemstack)) {
			return false;
		}
		EntityLostMask entitymask = new EntityLostMask(world, i, j, k, byte0, itemstack.getItemDamage());
		if (entitymask.canStay()) {
			if (!world.isRemote) {
				world.spawnEntityInWorld(entitymask);
			}
			if (!entitymask.isDead) {
				itemstack.stackSize--;
			}
		}
		return true;
	}

	@Override
	public int getIconFromDamage(int i) {
		return iconIndex + i;
	}


}
