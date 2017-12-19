package tropicraft.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tropicraft.ModInfo;
import tropicraft.creative.TropiCreativeTabs;
import tropicraft.entities.passive.water.EntityTropicalFish;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTropicsCoral extends BlockTropicraft {

	/**
	 * File names of all the flowers
	 */
	public String[] imageNames;
	
	/**
	 * Icon array representing all of the flowers registered to this class
	 */
	private Icon[] images;
	
	/** Brightness value of coral blocks during the day */
    private static final float dayBrightness = 0.3F;
    
    /** Brightness value of coral blocks during the night */
    private static final float nightBrightness = 0.6F;
	
	public BlockTropicsCoral(int id, String[] imageNamesNew, Material material) {
		super(id, material);
		images = new Icon[imageNamesNew.length];
		this.imageNames = new String[imageNamesNew.length];
		System.arraycopy(imageNamesNew, 0, this.imageNames, 0, imageNamesNew.length);
		setTickRandomly(true);
		setCreativeTab(TropiCreativeTabs.tabDecorations);
	}
	
    @Override
    public Icon getIcon(int i, int j) {
        return images[j & 7];
    }
    
    @Override
    public int damageDropped(int i) {
        return i & 7;
    }
    
    @Override
    public boolean canPlaceBlockAt(World world, int i, int j, int k) {
        return super.canPlaceBlockAt(world, i, j, k) && canThisPlantGrowOnThisBlockID(world.getBlockId(i, j - 1, k)) && world.getBlockMaterial(i, j, k) == Material.water
                && world.getBlockMaterial(i, j + 1, k) == Material.water;
    }

    protected boolean canThisPlantGrowOnThisBlockID(int i) {
        return i == Block.grass.blockID || i == Block.dirt.blockID || i == Block.sand.blockID || i == TropicraftBlocks.purifiedSand.blockID;
    }
    
    @Override
    public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
        super.onNeighborBlockChange(world, i, j, k, l);
        checkFlowerChange(world, i, j, k);
    }

    @Override
    public void updateTick(World world, int i, int j, int k, Random random) {
        checkFlowerChange(world, i, j, k);
        if(!world.isRemote)
        generateTropicalFish(world,i,j,k,random);
    }
    
    
    public void generateTropicalFish(World w, int i, int j, int k, Random r){
    	int rate = 12;
    	double maxDist = 16D;
    	if(r.nextInt(rate) == 0)
    	{
    		EntityPlayer p = w.getClosestPlayer(i, j, k, maxDist);
    		if(p != null && !p.isDead){
	    		EntityTropicalFish fish = new EntityTropicalFish(w);
	    		fish.setPosition(i, j+1, k);
	    		w.spawnEntityInWorld(fish);
	    		//System.out.println("Spawned a fish!");
    		}
    	}
    }

    protected final void checkFlowerChange(World world, int i, int j, int k) {
        if (!canBlockStay(world, i, j, k)) {
            dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k) & 7, 0);
            world.setBlockToAir(i, j, k);
        } else {
            // Checks if world day/night does not match coral day/night type
            int meta = world.getBlockMetadata(i, j, k);
            if (world.isDaytime() != isDayCoral(meta)) {
                int newMetadata = (meta & 7) | (isDayCoral(meta) ? 8 : 0);
                world.setBlock(i, j, k, this.blockID, newMetadata, 3);
            }
        }
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z) {
        if (isDayCoral(world.getBlockMetadata(x, y, z))) {
            return (int) (15.0F * dayBrightness);
        } else {
            return (int) (15.0F * nightBrightness);
        }
    }

    private boolean isDayCoral(int metadata) {
        return (metadata & 8) == 0;
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
        return (world.getBlockMaterial(i, j, k) == Material.water && world.getBlockMaterial(i, j + 1, k) == Material.water) && canThisPlantGrowOnThisBlockID(world.getBlockId(i, j - 1, k));
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
	@Override
	public void registerIcons(IconRegister iconRegistry) {
		for (int meta = 0; meta < this.imageNames.length; meta++) {
			images[meta] = iconRegistry.registerIcon(ModInfo.ICONLOCATION + getImageName() + meta);
		}
	}

	@Override
	public String getImageName() {
		return "coral_";
	}
}
