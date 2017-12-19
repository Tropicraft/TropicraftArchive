package tropicraft.blocks;

import java.util.List;
import java.util.Random;

import tropicraft.creative.TropiCreativeTabs;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTropicraftLog extends BlockTropicraftMulti {

	public BlockTropicraftLog(int id, Material material, String prefix, String[] displayNames, String[] imageNames) {
		super(id, material, prefix, displayNames, imageNames);
		setTickRandomly(true);
		Block.setBurnProperties(id, 5, 5);
		this.disableStats();
		setCreativeTab(TropiCreativeTabs.tabBlock);
		//TODO: this.setBlockBounds(0.19f, 0.0f, 0.19f, 0.81f, 1f, 0.81f);
	}
	
    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
	@Override
    public boolean isOpaqueCube()
    {
        return true;
    }
	
    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
	@Override
    public boolean renderAsNormalBlock()
    {
        return true;
    }

	@Override
	public int quantityDropped(Random random) {
		return 1;
	}

	@Override
	public int idDropped(int i, Random random, int j) {
		return TropicraftBlocks.treeWood.blockID;
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l) {
		super.harvestBlock(world, entityplayer, i, j, k, l);
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
		if (world.getBlockMetadata(i, j, k) == 0) {
			spawnCoconuts(world, i, j, k, random, 20);
		}
	}

	public static void spawnCoconuts(World world, int i, int j, int k, Random random, int chance) {
		
        if ((world.getBlockId(i, j + 1, k) == TropicraftBlocks.tropicsLeaves.blockID && world.getBlockMetadata(i, j + 1, k) == 0) || (world.getBlockId(i, j + 2, k) == TropicraftBlocks.tropicsLeaves.blockID
                && world.getBlockMetadata(i, j + 2, k) == 0)) {
            if (world.isAirBlock(i + 1, j, k) && random.nextInt(chance) == 0) {
                world.setBlock(i + 1, j, k, TropicraftBlocks.coconut.blockID);
                world.setBlockMetadataWithNotify(i, j, k, 0, 2);

            }
            if (world.isAirBlock(i - 1, j, k) && random.nextInt(chance) == 0) {
                world.setBlock(i - 1, j, k, TropicraftBlocks.coconut.blockID);
                world.setBlockMetadataWithNotify(i, j, k, 0, 2);


            }
            if (world.isAirBlock(i, j, k - 1) && random.nextInt(chance) == 0) {
                world.setBlock(i, j, k - 1, TropicraftBlocks.coconut.blockID);
                world.setBlockMetadataWithNotify(i, j, k, 0, 2);


            }
            if (world.isAirBlock(i, j, k + 1) && random.nextInt(chance) == 0) {
                world.setBlock(i, j, k + 1, TropicraftBlocks.coconut.blockID);
                world.setBlockMetadataWithNotify(i, j, k, 0, 2);
            }

            if (world.isAirBlock(i, j - 1, k) && random.nextInt(chance) == 0) {
                world.setBlock(i, j - 1, k, TropicraftBlocks.coconut.blockID);
                world.setBlockMetadataWithNotify(i, j, k, 0, 2);
            }
        } 

	}

	@Override
	public int damageDropped(int i) {
		return i & 1;
	}

	/**
	 * returns a number between 0 and 3
	 */
	public static int limitToValidMetadata(int par0)
	{
		return par0 & 1;
	}

	@Override
	public float getPlayerRelativeBlockHardness(EntityPlayer entityplayer, World world, int par3, int par4, int par5) {
		if (blockHardness < 0.0F) {
			return 0.0F;
		}
		if (!entityplayer.canHarvestBlock(this)) {
			return 1.0F / blockHardness / 100F;
		} else {
			return entityplayer.getCurrentPlayerStrVsBlock(Block.wood, true, world.getBlockMetadata(par3, par4, par5)) / blockHardness / 30F;
		}
	}

	@Override
	public Icon getIcon(int i, int j) {

		if (j == 0) {
			if (i == 0 || i == 1) {
				return images[1];	//palm top
			} else {
				return images[0];	//palm side
			}
		}
		
		if (j == 8) {
			if (i == 2 || i == 3) {
				return images[1];	//palm top
			} else {
				return images[0];	//palm side
			}
		}
		
		if (j == 4) {
			if (i == 4 || i == 5) {
				return images[1];	//palm top
			} else {
				return images[0];	//palm side
			}
		}
		
		if (j == 1) {
			if (i == 0 || i == 1) {
				return images[3];
			} else {
				return images[2];
			}
		}
		
		if (j == 5) {
			if (i == 4 || i == 5) {
				return images[3];
			} else {
				return images[2];
			}
		}
		
		if (j == 9) {
        	if (i == 3 || i == 2) {
        		return images[3];
        	} else {
        		return images[2];
        	}
		}

		return images[1];
	}

	/**
	 * ejects contained items into the world, and notifies neighbours of an update, as appropriate
	 * NOTE: This used to be onBlockRemoval - I have yet to find a suitable replacement
	 */
	@Override
	public void breakBlock(World world, int i, int j, int k, int lol, int meh) {
			byte byte0 = 4;
		int l = byte0 + 1;
		if (world.checkChunksExist(i - l, j - l, k - l, i + l, j + l, k + l)) {
			for (int i1 = -byte0; i1 <= byte0; i1++) {
				for (int j1 = -byte0; j1 <= byte0; j1++) {
					for (int k1 = -byte0; k1 <= byte0; k1++) {
						int l1 = world.getBlockId(i + i1, j + j1, k + k1);
						if (l1 != TropicraftBlocks.tropicsLeaves.blockID) {
							continue;
						}
						int i2 = world.getBlockMetadata(i + i1, j + j1, k + k1);
						if ((i2 & 8) == 0) {
							world.setBlockMetadataWithNotify(i + i1, j + j1, k + k1, i2 | 8, 3);
						}
					}

				}

			}

		}
	}

	@SideOnly(Side.CLIENT)

	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		par3List.add(new ItemStack(par1, 1, 0));
		par3List.add(new ItemStack(par1, 1, 1));
	}

	@Override
	public boolean canSustainLeaves(World world, int x, int y, int z)
	{
		return true;
	}

	@Override
	public boolean isWood(World world, int x, int y, int z)
	{
		return true;
	}
	/**
	 * Called when the block is placed in the world.
	 */
	@Override
	public int getRenderType()
	{
		return 31;
	}
	
	@Override
	public boolean isBlockNormalCube(World world, int i, int j, int k) {
		return true;
	}

    /**
     * Called when a block is placed using its ItemBlock. Args: World, X, Y, Z, side, hitX, hitY, hitZ, block metadata
     */
    public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9)
    {
        int j1 = par9 & 3;
        byte b0 = 0;

        switch (par5)
        {
            case 0:
            case 1:
                b0 = 0;
                break;
            case 2:
            case 3:
                b0 = 8;
                break;
            case 4:
            case 5:
                b0 = 4;
        }

        return j1 | b0;
    }

	@Override
	/**
	 * Returns an item stack containing a single instance of the current block type. 'i' is the block's subtype/damage
	 * and is ignored for blocks which do not support subtypes. Blocks which cannot be harvested should return null.
	 */
	protected ItemStack createStackedBlock(int par1)
	{
		return new ItemStack(this.blockID, 1, limitToValidMetadata(par1));
	}

}
