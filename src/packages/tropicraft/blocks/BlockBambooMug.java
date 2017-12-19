package tropicraft.blocks;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import tropicraft.blocks.tileentities.TileEntityBambooMug;
import tropicraft.items.TropicraftItems;
import tropicraft.ModIds;
import tropicraft.ModInfo;
import tropicraft.Tropicraft;

public class BlockBambooMug extends BlockContainer {
	// edited classes: BlockBambooMug, TileEntityBambooMug, TileEntityBambooMugRenderer, ModelBabooMug
	// TropicraftMod, Drink*, ItemDrink, /tropicalmod/bamboomug.png /tropicalmod/tropiitems.png
	
	public BlockBambooMug(int i) {
		super(i, Material.plants);
        this.setBlockBounds(0.3f, 0.0f, 0.3f, 0.7f, 0.45f, 0.7f);
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileEntityBambooMug();
	}
	
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    @Override
    public boolean isOpaqueCube() {
    	return false;
    }
    
    @Override
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
        return super.canPlaceBlockAt(par1World, par2, par3, par4) && par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4);
    }
    
	@Override
	public int getRenderType() {
		return -1;
	}
	
	@Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		TileEntityBambooMug mug = (TileEntityBambooMug) world.getBlockTileEntity(x, y, z);
		
		if (mug.isEmpty()) {
			return new ItemStack(TropicraftItems.bambooMugEmpty);
		}
		
		return mug.cocktail.copy();
	}
	

    public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
    	if (!world.isRemote) {
    		TileEntityBambooMug mug = (TileEntityBambooMug) world.getBlockTileEntity(x, y, z);
    		if (!mug.isEmpty()) {
    			dropBlockAsItem_do(world, x, y, z, mug.cocktail.copy());
    		} else {
    			dropBlockAsItem_do(world, x, y, z, new ItemStack(TropicraftItems.bambooMugEmpty));
    		}
    	}
    	super.breakBlock(world, x, y, z, par5, par6);
    }

	@Override
	public int quantityDropped(Random par1Random) {
		return 0;
	}

	@Override
	public void registerIcons(IconRegister iconRegistry) {
		//this.blockIcon = iconRegistry.registerIcon(ModInfo.MODID + ":" + getImageName());
	}
	
	/**
	 * @return Get the image name for this block
	 */
	public String getImageName() {
		return "bamboomug";
	}
}
