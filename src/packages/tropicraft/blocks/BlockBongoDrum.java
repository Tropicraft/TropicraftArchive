package tropicraft.blocks;

import java.util.List;

import tropicraft.ModInfo;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBongoDrum extends BlockTropicraftImpl {
	public static final float SMALL_DRUM_SIZE = 0.5f;
	public static final float MEDIUM_DRUM_SIZE = 0.6f;
	public static final float BIG_DRUM_SIZE = 0.7f;
	
	public static final float SMALL_DRUM_OFFSET = (1.0f - SMALL_DRUM_SIZE)/2.0f;
	public static final float MEDIUM_DRUM_OFFSET = (1.0f - MEDIUM_DRUM_SIZE)/2.0f;
	public static final float BIG_DRUM_OFFSET = (1.0f - BIG_DRUM_SIZE)/2.0f;
	public static final float DRUM_HEIGHT = 1.0f;
	
	@SideOnly(Side.CLIENT)
	private Icon topIcon;
	@SideOnly(Side.CLIENT)
	private Icon sideIcon;
	
	public BlockBongoDrum(int id) {
		super(id, Material.circuits, "bongodrum");
		setBlockBounds(SMALL_DRUM_OFFSET, 0.0f, SMALL_DRUM_OFFSET, 1-SMALL_DRUM_OFFSET, DRUM_HEIGHT, 1-SMALL_DRUM_OFFSET);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister iconRegistry) {
		topIcon = iconRegistry.registerIcon(ModInfo.MODID + ":" + getImageName() + "top");
		blockIcon = sideIcon = iconRegistry.registerIcon(ModInfo.MODID + ":" + getImageName() + "side");
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float offsetX, float offsetY, float offsetZ) {
		if (side != 1) {
			return false;
		}

		int meta = world.getBlockMetadata(x, y, z)&3;
		
		switch (meta) {
		case 2:
			playLowBongo(world, x, y, z);
			break;
		case 1:
			playMediumBongo(world, x, y, z);
			break;
		case 0:
		default:
			playHighBongo(world, x, y, z);
			break;
		}

		return true;
	}
	
    private void playHighBongo(World world, int x, int y, int z) {
    	world.playSoundEffect(x, y, z, "bongohigh", 1.0f, 1.0f);
	}
    
    private void playMediumBongo(World world, int x, int y, int z) {
    	world.playSoundEffect(x, y, z, "bongomedium", 1.0f, 1.0f);
    	
    }

    private void playLowBongo(World world, int x, int y, int z) {
    	world.playSoundEffect(x, y, z, "bongolow", 1.0f, 1.0f);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z) & 3;
		
		switch (meta) {
		case 2:
			setBlockBounds(BIG_DRUM_OFFSET, 0.0f, BIG_DRUM_OFFSET, 1-BIG_DRUM_OFFSET, DRUM_HEIGHT, 1-BIG_DRUM_OFFSET);
			break;
		case 1:
			setBlockBounds(MEDIUM_DRUM_OFFSET, 0.0f, MEDIUM_DRUM_OFFSET, 1-MEDIUM_DRUM_OFFSET, DRUM_HEIGHT, 1-MEDIUM_DRUM_OFFSET);
			break;
		case 0:
		default:
			setBlockBounds(SMALL_DRUM_OFFSET, 0.0f, SMALL_DRUM_OFFSET, 1-SMALL_DRUM_OFFSET, DRUM_HEIGHT, 1-SMALL_DRUM_OFFSET);
			break;
		}
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public void setBlockBoundsForItemRender() {
		setBlockBounds(BIG_DRUM_OFFSET, 0.0f, BIG_DRUM_OFFSET, 1-BIG_DRUM_OFFSET, DRUM_HEIGHT, 1-BIG_DRUM_OFFSET);
	}

	@Override
	public void getSubBlocks(int par1, CreativeTabs tab, List list) {
		list.add(new ItemStack(blockID, 1, 0));
		list.add(new ItemStack(blockID, 1, 1));
		list.add(new ItemStack(blockID, 1, 2));
	}

	@Override
	public Icon getIcon(int side, int meta) {
		if (side == 1) {
			return topIcon;
		} else {
			return sideIcon;
		}
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
		if (side == 0) {
			return false;
		}
		
		return super.shouldSideBeRendered(world, x, y, z, side);
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	@Override
	public int getLightOpacity(World world, int x, int y, int z) {
		return 255;
	}
	
    /**
     * Get the block's damage value (for use with pick block).
     */
    public int getDamageValue(World world, int x, int y, int z) {
    	int dmg = super.getDamageValue(world, x, y, z);
    	
    	if (dmg > 2) {
    		dmg = 0;
    	}
    	
    	return dmg;
    }

}
