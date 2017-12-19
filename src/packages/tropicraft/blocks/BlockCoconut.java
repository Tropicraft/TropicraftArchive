package tropicraft.blocks;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tropicraft.enchanting.EnchantmentManager;
import tropicraft.items.ItemTropicsSword;
import tropicraft.items.TropicraftItems;

public class BlockCoconut extends BlockTropicraft {

	/**
	 * Should the user receive coconut chunks when they break this block?
	 */
	private boolean shouldReceiveChunks = false;
	
	/** Number of coconut chunks to boost the yield by when sliced with enchanted sword */
	private int boostAmount = 0;
	
	public BlockCoconut(int id, Material material, CreativeTabs tab) {
		super(id, material, tab);
		
		float f = 0.225F;

		setBlockBounds(0.5F - f, f, 0.5F - f, 0.5F + f, 1.0F - f, 0.5F + f);	
	}
	
    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }
	
    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    private byte getValidSides(IBlockAccess world, int i, int j, int k) {
        byte sides = 0;
        if (canAttachTo(world.getBlockId(i, j, k + 1))) {
            sides |= 1;
        }
        if (canAttachTo(world.getBlockId(i, j, k - 1))) {
            sides |= 4;
        }
        if (canAttachTo(world.getBlockId(i + 1, j, k))) {
            sides |= 8;
        }
        if (canAttachTo(world.getBlockId(i - 1, j, k))) {
            sides |= 2;
        }
        if (canAttachTo(world.getBlockId(i, j + 1, k))) {
            sides |= 16;
        }
 
        return sides;
    }
    
    private boolean canAttachTo(int i) {
        if (i == 0) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Adds all intersecting collision boxes to a list. (Be sure to only add boxes to the list if they intersect the
     * mask.) Parameters: World, X, Y, Z, mask, list, colliding entity
     */
    public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
    {
    	float f = 0.225F;
    	setBlockBounds(0.5F - f, f, 0.5F - f, 0.5F + f, 1.0F - f, 0.5F + f);	
        super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
    }
    
	@Override
	public int getRenderType() {
		return 1;
	}
	
	/**
	 * Number of items dropped upon block destruction
	 */
	@Override
	public int quantityDropped(Random random) {
		
		if (shouldReceiveChunks) {
			
			if (random.nextInt(7) == 0) {
				return 1 + boostAmount;
			}
			if (random.nextInt(6) == 0) {
				return 2 + boostAmount;
			}
			if (random.nextInt(5) == 0) {
				return 3 + boostAmount;
			} else {
				return boostAmount + (random.nextInt(4) != 0 ? 5 : 4);
			}
		} else {
			return 1;
		}
	}	
	
	protected void dropBlockAsItem_do(World par1World, int par2, int par3, int par4, ItemStack par5ItemStack)
    {
        if (!par1World.isRemote && par1World.getGameRules().getGameRuleBooleanValue("doTileDrops"))
        {
            float f = 0.7F;
            double d0 = (double)(par1World.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            double d1 = (double)(par1World.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            double d2 = (double)(par1World.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            EntityItem entityitem = new EntityItem(par1World, (double)par2 + d0, (double)par3 + d1, (double)par4 + d2, par5ItemStack);
            entityitem.delayBeforeCanPickup = 10;
            par1World.spawnEntityInWorld(entityitem);
            boostAmount = 0;
            shouldReceiveChunks = false;
        }
    }
	
	@Override
	public int idDropped(int i, Random random, int j) {
		
		if (shouldReceiveChunks) {
			return TropicraftItems.coconutChunk.itemID;
		} else {
			return TropicraftItems.coconut.itemID;
		}
	}
	
    /**
     * Called when a player removes a block.  This is responsible for
     * actually destroying the block, and the block is intact at time of call.
     * This is called regardless of whether the player can harvest the block or
     * not.
     *
     * Return true if the block is actually destroyed.
     *
     * Note: When used in multiplayer, this is called on both client and
     * server sides!
     *
     * @param world The current world
     * @param player The player damaging the block, may be null
     * @param x X Position
     * @param y Y position
     * @param z Z position
     * @return True if the block is actually destroyed.
     */
	@Override
    public boolean removeBlockByPlayer(World world, EntityPlayer player, int x, int y, int z)
    {
		if (world.isRemote) return false;
		
		ItemStack itemstack = player.inventory.getStackInSlot(player.inventory.currentItem);
		
		if (itemstack != null && (itemstack.getItem() instanceof ItemSword ||
				itemstack.getItem() instanceof ItemTropicsSword ||
				itemstack.getItem().getItemUseAction(itemstack) == EnumAction.block)) {
			shouldReceiveChunks = true;
			
			int l = EnchantmentHelper.getEnchantmentLevel(EnchantmentManager.fruitNinja.effectId, itemstack);
			
			if (l > 0) {
				boostAmount = quantityDropped(new Random());
			}
		}
		
        return world.setBlockToAir(x, y, z);
    }

	@Override
	public String getImageName() {
		return "coconut";
	}

	@Override
    @SideOnly(Side.CLIENT)
	public int idPicked(World par1World, int par2, int par3, int par4) {
		return TropicraftItems.coconut.itemID;
	}

}
