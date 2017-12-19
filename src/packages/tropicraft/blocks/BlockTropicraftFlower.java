package tropicraft.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import tropicraft.ModInfo;
import tropicraft.creative.TropiCreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTropicraftFlower extends BlockTropicraft {

	/**
	 * File names of all the flowers
	 */
	private String[] imageNames;
	
	/**
	 * Icon array representing all of the flowers registered to this class
	 */
	private Icon[] images;
	
	public BlockTropicraftFlower(int id, String[] imageNamesNew) {
		super(id, Material.plants);
        float f = 0.2F;
        setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 4.939F, 0.5F + f);
        this.setTickRandomly(true);
		images = new Icon[imageNamesNew.length];
		this.imageNames = new String[imageNamesNew.length];
		System.arraycopy(imageNamesNew, 0, this.imageNames, 0, imageNamesNew.length);
		setCreativeTab(TropiCreativeTabs.tabDecorations);
	}

    @Override
    public int damageDropped(int i) {
        return i;
    }

    @Override
    public Icon getIcon(int side, int meta) {
        return images[meta];
    }
    
    @Override
    public boolean canPlaceBlockAt(World world, int i, int j, int k) {
        return super.canPlaceBlockAt(world, i, j, k) && canThisPlantGrowOnThisBlockID(world.getBlockId(i, j - 1, k));
    }

    private boolean canThisPlantGrowOnThisBlockID(int i) {
        return i == Block.grass.blockID || i == Block.dirt.blockID || i == Block.tilledField.blockID;
    }
    
    @Override
    public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
        super.onNeighborBlockChange(world, i, j, k, l);
        checkFlowerChange(world, i, j, k);
    }

    @Override
    public void updateTick(World world, int i, int j, int k, Random random) {
        checkFlowerChange(world, i, j, k);
    }

    protected final void checkFlowerChange(World world, int i, int j, int k) {
        if (!canBlockStay(world, i, j, k)) {
            dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
            world.setBlockToAir(i, j, k);
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
    public int getRenderType() {
        return 1;
    }
    
    @Override
    public boolean canBlockStay(World world, int i, int j, int k) {
        return (world.getFullBlockLightValue(i, j, k) >= 8 || world.canBlockSeeTheSky(i, j, k)) && canThisPlantGrowOnThisBlockID(world.getBlockId(i, j - 1, k));
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
        return null;
    }
    
    @Override
    @SideOnly(Side.CLIENT)

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    public void getSubBlocks(int id, CreativeTabs creativeTab, List creativeList)
    {
        for (int meta = 0; meta < images.length; meta++)
        {
            creativeList.add(new ItemStack(id, 1, meta));
        }
    }
	
	/**
	 * Register all Icons used in this block
	 */
	@Override
	public void registerIcons(IconRegister iconRegistry) {
		for (int meta = 0; meta < this.imageNames.length; meta++) {
			images[meta] = iconRegistry.registerIcon(ModInfo.ICONLOCATION + getImageName() + meta);
		}
	}
	
	@Override
	public String getImageName() {
		return "flower_";
	}

}
