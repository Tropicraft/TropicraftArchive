package net.tropicraft.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.tropicraft.blocks.tileentities.TileEntityBambooMug;
import net.tropicraft.blocks.tileentities.TileEntityEIHMixer;
import net.tropicraft.drinks.MixerRecipeRegistry;
import net.tropicraft.mods.TropicraftMod;

public class BlockEIHMixer extends BlockContainer {

	/**
	 * @param par1 block id
	 */
	public BlockEIHMixer(int par1) {
		super(par1, Material.rock);
		this.setCreativeTab(TropicraftMod.tabBlock);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityEIHMixer();
	}

	@Override
	public int getRenderType() {
		return TropicraftMod.eihMixerRenderId;
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
	 */
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public int getRenderBlockPass() {
		return 0;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int par6, float par7, float par8, float par9) {
		if (world.isRemote) {
			return true;
		}
		
		ItemStack stack = entityPlayer.getCurrentEquippedItem();

		TileEntityEIHMixer mixer = (TileEntityEIHMixer)world.getBlockTileEntity(x, y, z);
		
		if (mixer.isDoneMixing()) {
			mixer.retrieveResult();
			return true;
		}

		if (stack == null) {
			mixer.emptyMixer();
			return true;
		}	

		ItemStack ingredientStack = stack.copy();
		ingredientStack.stackSize = 1;

		if (/*MixerRecipeRegistry.getInstance().isRegisteredIngredient(ingredientStack) &&*/ mixer.addToMixer(ingredientStack)) {
			entityPlayer.inventory.decrStackSize(entityPlayer.inventory.currentItem, 1);
		}

		if (stack.itemID == TropicraftMod.bambooMugEmpty.shiftedIndex && mixer.canMix()) {
			mixer.startMixing();
			entityPlayer.inventory.decrStackSize(entityPlayer.inventory.currentItem, 1);
		}

		return true;    	
	}
	
    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving)
    {
        int var6 = MathHelper.floor_double((double)(par5EntityLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

		int meta = 0;
		if (var6 == 0) {
			meta = 2;
		} else if (var6 == 1) {
			meta = 5;
		} else if (var6 == 2) {
			meta = 3;
		} else if (var6 == 3) {
			meta = 4;
		}
		
		par1World.setBlockMetadataWithNotify(par2, par3, par4, meta);
    }
    
    public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
    	if (!world.isRemote) {
    		TileEntityEIHMixer te = (TileEntityEIHMixer) world.getBlockTileEntity(x, y, z);
    		if (te.isDoneMixing()) {
    			te.retrieveResult();
    		} else {
    			te.emptyMixer();
    		}
    	}
    	
    	super.breakBlock(world, x, y, z, par5, par6);
    }

}
