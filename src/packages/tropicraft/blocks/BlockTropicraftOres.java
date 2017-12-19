package tropicraft.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import tropicraft.ModInfo;
import tropicraft.creative.TropiCreativeTabs;
import tropicraft.items.TropicraftItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class BlockTropicraftOres extends BlockTropicraft {

	@SideOnly(Side.CLIENT)
	private Icon[] images;

	private String[] displayNames, imageNames;

	public BlockTropicraftOres(int id, String[] displayNames, String[] imageNames) {
		super(id, Material.rock);
		this.displayNames = displayNames;
		this.imageNames = imageNames;
		setCreativeTab(TropiCreativeTabs.tabBlock);
	}
	
    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    public int damageDropped(int meta)
    {
        return meta & 3;
    }

	
    @Override
    public int idDropped(int i, Random random, int j) {
        return TropicraftItems.oreDrops.itemID;
    }	
	
	public float getBlockHardness(World par1World, int par2, int par3, int par4) {
		int meta = par1World.getBlockMetadata(par2, par3, par4);
		
		if (meta == 0) {
			this.setResistance(5F);
			return 2.1F;
		} else
			if (meta == 1) {
				this.setResistance(3F);
				return 3.2F;
			} else
				if (meta == 2) {
					this.setResistance(3F);
					return 4.3F;
				}
		
		this.setResistance(3F);
		return 0F;
	}

	@Override
	public Icon getIcon(int i, int j) {
		return images[j & 7];
	}

	@Override
	public int quantityDropped(int meta, int fortune, Random random) {
		if (meta == 0) {		//eudialyte
			return 4 + random.nextInt(3);
		} else
			if (meta == 1) {		//zircon
				return 2 + random.nextInt(2);
			} else
				if (meta == 2) {		//azurite
					return 5 + random.nextInt(7);
				}
		
		return 1;
	}
	
	//TODO: not sure if need?
    @Override
    public int quantityDroppedWithBonus(int i, Random random) {
        if (i > 0 && blockID != idDropped(0, random, i)) {
            int j = random.nextInt(i + 2) - 1;
            if (j < 0) {
                j = 0;
            }
            return quantityDropped(random) * (j + 1);
        } else {
            return quantityDropped(random);
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
	public String getImageName() {
		return "ore_";
	}
}
