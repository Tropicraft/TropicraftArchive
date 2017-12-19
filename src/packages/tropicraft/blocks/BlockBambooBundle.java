package tropicraft.blocks;

import net.minecraft.block.BlockPistonBase;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import tropicraft.ModInfo;
import tropicraft.entities.passive.water.EntityStarfish;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBambooBundle extends BlockTropicalWood {

	/**
	 * Icon for the top of the block. The side is the default blockIcon so there is no need for a field for it.
	 */
	private Icon top;

	public BlockBambooBundle(int id) {
		super(id);
	}
    
    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        super.onBlockAdded(par1World, par2, par3, par4);
        
/*        Failgull f = new Failgull(par1World);
        f.setLocationAndAngles(par2, par3 + 3, par4, 0, 0);
        par1World.spawnEntityInWorld(f);*/
    }

	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int i, int j)
	{
		if (j == 0) {
			if (i == 1|| i == 0) {
				return top;
			}
			return this.blockIcon;            
		}
		if (j == 8){        	
			if (i == 3 || i == 2) {
				return this.top;
			}            
			return this.blockIcon;
		}
		if(j == 4){
			if(i == 4 || i == 5){
				return this.top;
			}
			else{
				return this.blockIcon;
			}
		}
		else return this.blockIcon;
	}

	@Override
	public int getRenderType()
	{
		return 31;
	}

	/**
	 * Called when block is placed by the player
	 */
	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving, ItemStack stack)
	{
		int var6 = par1World.getBlockMetadata(par2, par3, par4) & 3;
		int var7 = BlockPistonBase.determineOrientation(par1World, par2, par3, par4, (EntityPlayer)par5EntityLiving);
		byte var8 = 0;

		switch (var7)
		{
		case 0:
		case 1:
			var8 = 0;
			break;
		case 2:
		case 3:
			var8 = 8;
			break;
		case 4:
		case 5:
			var8 = 4;
		}

		par1World.setBlockMetadataWithNotify(par2, par3, par4, var6 | var8, 3);
	}

	/**
	 * When this method is called, your block should register all the icons it needs with the given IconRegister. This
	 * is the only chance you get to register icons.
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.blockIcon = par1IconRegister.registerIcon(ModInfo.ICONLOCATION + getImageName() + "_side");
		this.top = par1IconRegister.registerIcon(ModInfo.ICONLOCATION + getImageName() + "_top");
	}

	/**
	 * In this case, the prefix to the icon names
	 */
	@Override
	public String getImageName() {
		return "bambooBundle";
	}

}
