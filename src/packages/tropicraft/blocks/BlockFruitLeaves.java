package tropicraft.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tropicraft.ModInfo;
import tropicraft.creative.TropiCreativeTabs;
import tropicraft.items.TropicraftItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFruitLeaves extends BlockLeaves {

	@SideOnly(Side.CLIENT)
	private Icon[][] images;
	
	/** Which dimension to access images from in the 2D Icon array. Dim 0 = fast, 1 = fancy */
	@SideOnly(Side.CLIENT)
	private int graphicsLevelIndex;
	
	private String[] displayNames, imageNames;
	
	public BlockFruitLeaves(int id, String[] displayNames, String[] imageNames) {
		super(id);
		this.displayNames = displayNames;
		this.imageNames = imageNames;
		Block.setBurnProperties(id, 30, 60);
		this.disableStats();
		setCreativeTab(TropiCreativeTabs.tabBlock);
	}
	
	@Override
	public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int getBlockColor() {
        return 0xffffff;
    }

    @Override
    public int getRenderColor(int i) {
        return 0xffffff;
    }

    @Override
    public int colorMultiplier(IBlockAccess iblockaccess, int i, int j, int k) {
        return 0xffffff;
    }

    @Override
    public int quantityDropped(Random random) {
        return random.nextInt(3) != 0 ? 0 : 1;
    }

    @Override
    public int idDropped(int metadata, Random random, int j) {
        int treeType = metadata & 3;
        if (random.nextFloat() < 0.8F) {
            if (treeType == 0) {
                return TropicraftItems.lemon.itemID;
            } else if (treeType == 1) {
                return TropicraftItems.lime.itemID;
            } else if (treeType == 2) {
                return TropicraftItems.orange.itemID;
            } else {
                return TropicraftItems.grapefruit.itemID;
            }
        }
        return TropicraftBlocks.saplings.blockID;
    }

    public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7) {
        if (!par1World.isRemote) {
            if (par1World.rand.nextInt(3) == 0) {
            	int id = this.idDropped(par5, par1World.rand, par7);
            	if(id == TropicraftBlocks.saplings.blockID) {
            		this.dropBlockAsItem_do(par1World, par2, par3, par4, new ItemStack(id, 1, this.damageDropped(par5) + 1));
            	} else {
            		this.dropBlockAsItem_do(par1World, par2, par3, par4, new ItemStack(id, 1, 0));
            	}
            }
        }
    }
    
    private boolean isLeafBlockID(int i) {
        Block b = Block.blocksList[i];
        if (b != null && b.blockMaterial == Material.leaves) {
            return true;
        }
        return false;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    /**
     * Pass true to draw this block using fancy graphics, or false for fast graphics.
     */
    public void setGraphicsLevel(boolean par1) {
    	super.setGraphicsLevel(par1);
        this.graphicsLevel = par1;
        this.graphicsLevelIndex = par1 ? 0 : 1;
    }
    
    @SideOnly(Side.CLIENT)
    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getBlockTextureFromSideAndMetadata(int side, int meta) {
    	this.setGraphicsLevel(Minecraft.getMinecraft().gameSettings.fancyGraphics);
    	return this.images[this.graphicsLevelIndex][meta & 3];
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getIcon(int par1, int par2) {
        return getBlockTextureFromSideAndMetadata(par1, par2);
    }
    
    @Override
    public void registerIcons(IconRegister iconRegister) {
    	images = new Icon[2][displayNames.length];
    	for (int i = 0; i < 2; i++) {
    		for (int j = 0; j < displayNames.length; j++) {
    			images[i][j] = iconRegister.registerIcon(ModInfo.ICONLOCATION + "leaf_" + imageNames[j] + ((i == 1) ? "_fast" : ""));
    		}
    	}
    }
    
    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {

    }
    
    @Override
    public void beginLeavesDecay(World world, int x, int y, int z)
    {
    	
    }


}
