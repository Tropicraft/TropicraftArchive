package tropicraft.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import tropicraft.ModInfo;
import tropicraft.enchanting.EnchantmentManager;
import tropicraft.items.ItemTropicsSword;
import tropicraft.items.TropicraftItems;

public class BlockTallFlower extends BlockTropicraft {

	private Icon pineapple; //texture for pineapple top of flower
	private Icon iris;	//texture for iris top of flower

	private boolean shouldReceiveChunks;

	public BlockTallFlower(int id) {
		super(id, Material.plants);
		setTickRandomly(true);
		float f = 0.3F;
		setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
	}

	/**
	 * Called upon block activation (right click on the block.)
	 */
	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer player, int par6, float par7, float par8, float par9)
	{
		if (world.isRemote) return false;

		if (world.getBlockMetadata(i, j, k) < 8 && world.isAirBlock(i, j + 1, k)) {			//checks if bone meal is in hand
			if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == Item.dyePowder && player.getCurrentEquippedItem().getItemDamage() == 15) {
				player.getCurrentEquippedItem().stackSize--;
				world.setBlock(i, j + 1, k, TropicraftBlocks.tallFlower.blockID, 9, 3);
				world.setBlockMetadataWithNotify(i, j, k, 7, 3);
			}
		}
		else
			if (world.getBlockMetadata(i, j, k) == 9) {	//pineapple
	/*			player.getCurrentEquippedItem().damageItem(1, player);
				dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
				world.setBlockToAir(i, j, k);
				world.setBlockMetadataWithNotify(i, j - 1, k, 0, 3);*/
			}

		return false;
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {   
		checkFlowerChange(world, i, j, k);
		if (j > world.getHeight() - 2) {
			return;
		}
		if (world.getBlockId(i, j, k) == this.blockID && ((world.getBlockMetadata(i, j, k)/* & 7*/) != 7) && world.isAirBlock(i, j + 1, k)) {
			int growth = world.getBlockMetadata(i, j, k);
			if (growth >= 6) {
				world.setBlock(i, j + 1, k, TropicraftBlocks.tallFlower.blockID, 9, 3);
				world.setBlockMetadataWithNotify(i, j, k, 7, 3);
			} else {
				world.setBlockMetadataWithNotify(i, j, k, growth + 1, 3);
			}
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

		if (world.getBlockId(x, y, z) == this.blockID && world.getBlockId(x, y + 1, z) == blockID && world.getBlockMetadata(x, y + 1, z) == 15) {
			dropBlockAsItem(world, x, y + 1, z, world.getBlockMetadata(x, y + 1, z), 0);
			world.setBlockToAir(x, y + 1, z);
			world.setBlockToAir(x, y, z);
		}
		else
			if (world.getBlockId(x, y, z) == this.blockID && world.getBlockId(x, y + 1, z) == blockID) {
				if (world.getBlockMetadata(x, y + 1, z) == 9) {
					dropBlockAsItem(world, x, y + 1, z, world.getBlockMetadata(x, y + 1, z), 0);
					world.setBlockToAir(x, y + 1, z);
					world.setBlockToAir(x, y, z);
				} else {
					world.setBlockToAir(x, y, z);
				}
			}
			else
				if (world.getBlockId(x, y, z) == this.blockID && world.getBlockId(x, y - 1, z) == blockID && world.getBlockMetadata(x, y, z) == 9) {
					if (!harvestBlock2(world, player, x, y, z, world.getBlockMetadata(x, y, z))) {
	//					dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
					}
					world.setBlockToAir(x, y, z);
					world.setBlockMetadataWithNotify(x, y - 1, z, 0, 3);
				}
				else
					if (world.getBlockId(x, y, z) == this.blockID && world.isAirBlock(x, y + 1, z) && world.getBlockMetadata(x, y, z) < 8) {	//only pineapple stem
						world.setBlockToAir(x, y, z);
					}
					else {
						while (world.getBlockId(x, --y, z) == this.blockID) {			//top iris block
							dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
							world.setBlockToAir(x, y, z);	
						}
					}

		return true;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving doop, ItemStack stack) {
		if (world.getBlockMetadata(x, y, z) == 8 && world.isAirBlock(x, y + 1, z)) {
			world.setBlock(x, y + 1, z, this.blockID, 15, 3);
		}
	}

	@Override
	public Icon getIcon(int side, int metadata) {
		if (metadata == 9) {
			return this.pineapple;
		} else
			if (metadata == 15) {
				return this.iris;
			}

		return this.blockIcon;
	}

	@Override
	public boolean canPlaceBlockAt(World world, int i, int j, int k) {
		return super.canPlaceBlockAt(world, i, j, k) && canThisPlantGrowOnThisBlockID(world.getBlockId(i, j - 1, k));
	}

	private boolean canThisPlantGrowOnThisBlockID(int i) {
		return i != 0 && (Block.blocksList[i].blockMaterial == Material.ground || Block.blocksList[i].blockMaterial == Material.grass);
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
		super.onNeighborBlockChange(world, i, j, k, l);
		checkFlowerChange(world, i, j, k);
	}

	protected void checkFlowerChange(World world, int i, int j, int k) {
		//System.out.println("being called, sadly");
		if (!world.isRemote && !canBlockStay(world, i, j, k)) {
			dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k) == 15 ? 15 : 0, 0);
			world.setBlockToAir(i, j, k);
		}
	}

	//@Override
	public boolean harvestBlock2(World world, EntityPlayer entityPlayer, int i, int j, int k, int meta) {

		if (meta == 9) {
			ItemStack itemstack = entityPlayer.inventory.getStackInSlot(entityPlayer.inventory.currentItem);

			// If pineapple is harvested with a sword, drop pineapple chunks
			if (itemstack != null && (itemstack.getItem() instanceof ItemSword ||
					itemstack.getItem() instanceof ItemTropicsSword ||
					itemstack.getItem().getItemUseAction(itemstack) == EnumAction.block)) {
				this.shouldReceiveChunks = true;

				int count = world.rand.nextInt(4) != 0 ? 3 : 4;
				if (world.rand.nextInt(7) == 0) {
					count = 2;
				}else
					if (world.rand.nextInt(6) == 0) {
						count = 1;
					}else
						if (world.rand.nextInt(5) == 0) {
							count = 5;
						}

				int l = EnchantmentHelper.getEnchantmentLevel(EnchantmentManager.fruitNinja.effectId, itemstack);

				if (l > 0) {
					count += new Random().nextInt(count) + 1;
				}

				ItemStack cubes;
				for (int c = 0; c < count; c++) {
					cubes = new ItemStack(TropicraftItems.pineappleCubes.itemID, 1, 0);
					System.out.println("yeah...");
					if(!world.isRemote)
						this.dropBlockAsItem_do(world, i, j, k, cubes);
				}

				return true;

			} else {
				// Drop a whole pineapple
				//			if(!world.isRemote)
				//				dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);

				return false;
			}
		}

		return false;
	} 

	@Override
	public boolean canBlockStay(World world, int i, int j, int k) {
		boolean belowCheck = false;

		if (world.getBlockId(i, j, k) == blockID && (world.getBlockMetadata(i, j, k) == 9 /*pineapple*/ || world.getBlockMetadata(i, j, k) == 15 /*iris*/ )) {
			belowCheck = (world.getBlockId(i, j - 1, k) == this.blockID && world.getBlockMetadata(i, j - 1, k) != 9 && world.getBlockMetadata(i, j - 1, k) != 15);
		} else {
			belowCheck = canThisPlantGrowOnThisBlockID(world.getBlockId(i, j - 1, k));
		}
		return belowCheck && (world.getFullBlockLightValue(i, j, k) >= 8 || world.canBlockSeeTheSky(i, j, k));
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		return null;
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
	public int idDropped(int meta, Random random, int j) {
		if (shouldReceiveChunks) {
			/*			shouldReceiveChunks = false;*/
			return TropicraftItems.pineappleCubes.itemID;
		}

		if ((meta == 9 || meta == 15)) {	//is not plant bottom
			return TropicraftItems.tallFlower.itemID;
		}

		return 0;
	}

	@Override
	public int damageDropped(int i) {
		if (shouldReceiveChunks) {
			shouldReceiveChunks = false;
			return 0;
		}
		else
			return i;
	}
	/*    
    private boolean isUpperPlant(World w, int i, int j, int k) {
        return (w.getBlockId(i, j, k) == this.blockID && w.getBlockMetadata(i, j, k) == 0);
    }

    private boolean isLowerPlant(World w, int i, int j, int k) {
        return (w.getBlockId(i, j, k) == this.blockID && w.getBlockMetadata(i, j, k) == 1);
    }*/

	/**
	 * Register all Icons used in this block
	 */
	@Override
	public void registerIcons(IconRegister iconRegistry) {
		this.blockIcon = iconRegistry.registerIcon(ModInfo.ICONLOCATION + getImageName());
		this.iris = iconRegistry.registerIcon(ModInfo.ICONLOCATION + "iris");
		this.pineapple = iconRegistry.registerIcon(ModInfo.ICONLOCATION + "pineapple");
	}

	@Override
	public String getImageName() {
		return "stem";
	}

}
