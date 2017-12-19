package net.tropicraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.tropicraft.mods.TropicraftMod;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTropicraftLeaf extends BlockLeaves {

	enum sexy {COROSUS, COJO, RUBE, FISHTACO}; //8D
	public int[] textures;
	public String[] names;
	private int[] adjacentTreeBlocks;

	public BlockTropicraftLeaf(int i, int[] tex, String[] s) {
		super(i, tex[0]);
		textures = tex;
		names = s;
		setTickRandomly(true);
		Block.setBurnProperties(i, 30, 60);
		setRequiresSelfNotify();
		this.setCreativeTab(TropicraftMod.tabBlock);
		setTextureFile("/tropicalmod/tropiterrain.png");
	}
	
    /**
     * Used by getTopSolidOrLiquidBlock while placing biome decorations, villages, etc
     * Also used to determine if the player can spawn on this block.
     * 
     * @return False to disallow spawning
     */
    public boolean isBlockFoliage(World world, int x, int y, int z)
    {
        return true;
    }

	@SideOnly(Side.CLIENT)
	@Override
	public int getBlockColor() {
		return 0xffffff;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getRenderColor(int i) {
		int treeType = i & 3;
		if (treeType == 3) {
			return 0x48b518;
		} else {
			return 0xffffff;
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int colorMultiplier(IBlockAccess iblockaccess, int i, int j, int k) {
		int treeType = iblockaccess.getBlockMetadata(i, j, k) & 3;
		if (treeType == 3) {
			return 0x48b518;
		} else {
			return 0xffffff;
		}
	}

	public boolean isLeafBlockID(int i) {
		Block b = Block.blocksList[i];
		if (b != null && b.blockMaterial == Material.leaves) {
			return true;
		}
		return false;
	}

	public boolean isWoodBlockID(int i) {
		if (i == Block.wood.blockID || i == TropicraftMod.tropicalWood.blockID) {
			return true;
		}
		return false;
	}

	@Override
	//used to be onBlockRemoval :/
	public void breakBlock(World world, int x, int y, int z, int meh, int meh2) {
		int radius = 1;
		int area = radius + 1;
		if (world.checkChunksExist(x - area, y - area, z - area, x + area, y + area, z + area)) {
			for (int xOffset = -radius; xOffset <= radius; xOffset++) {
				for (int yOffset = -radius; yOffset <= radius; yOffset++) {
					for (int zOffset = -radius; zOffset <= radius; zOffset++) {

						int neighborID = world.getBlockId(x + xOffset, y + yOffset, z + zOffset);
						if (isLeafBlockID(neighborID)) {
							int neighborMetadata = world.getBlockMetadata(x + xOffset, y + yOffset, z + zOffset);
							world.setBlockMetadata(x + xOffset, y + yOffset, z + zOffset, neighborMetadata | 8);
						}
					}
				}
			}
		}
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random random) {
		if (world.isRemote) {
			return;
		}
		int metadata = world.getBlockMetadata(x, y, z);
		if ((metadata & 8) != 0 && (metadata & 4) == 0) {
			byte byte0 = 4;
			int i1 = byte0 + 1;
			byte byte1 = 32;
			int j1 = byte1 * byte1;
			int k1 = byte1 / 2;
			if (adjacentTreeBlocks == null) {
				adjacentTreeBlocks = new int[byte1 * byte1 * byte1];
			}
			if (world.checkChunksExist(x - i1, y - i1, z - i1, x + i1, y + i1, z + i1)) {
				for (int l1 = -byte0; l1 <= byte0; l1++) {
					for (int k2 = -byte0; k2 <= byte0; k2++) {
						for (int i3 = -byte0; i3 <= byte0; i3++) {
							int k3 = world.getBlockId(x + l1, y + k2, z + i3);
							if (isWoodBlockID(k3)) {
								adjacentTreeBlocks[(l1 + k1) * j1 + (k2 + k1) * byte1 + (i3 + k1)] = 0;
								continue;
							}
							if (isLeafBlockID(k3)) {
								adjacentTreeBlocks[(l1 + k1) * j1 + (k2 + k1) * byte1 + (i3 + k1)] = -2;
							} else {
								adjacentTreeBlocks[(l1 + k1) * j1 + (k2 + k1) * byte1 + (i3 + k1)] = -1;
							}
						}
					}
				}

				for (int i2 = 1; i2 <= 4; i2++) {
					for (int l2 = -byte0; l2 <= byte0; l2++) {
						for (int j3 = -byte0; j3 <= byte0; j3++) {
							for (int l3 = -byte0; l3 <= byte0; l3++) {
								if (adjacentTreeBlocks[(l2 + k1) * j1 + (j3 + k1) * byte1 + (l3 + k1)] != i2 - 1) {
									continue;
								}
								if (adjacentTreeBlocks[((l2 + k1) - 1) * j1 + (j3 + k1) * byte1 + (l3 + k1)] == -2) {
									adjacentTreeBlocks[((l2 + k1) - 1) * j1 + (j3 + k1) * byte1 + (l3 + k1)] = i2;
								}
								if (adjacentTreeBlocks[(l2 + k1 + 1) * j1 + (j3 + k1) * byte1 + (l3 + k1)] == -2) {
									adjacentTreeBlocks[(l2 + k1 + 1) * j1 + (j3 + k1) * byte1 + (l3 + k1)] = i2;
								}
								if (adjacentTreeBlocks[(l2 + k1) * j1 + ((j3 + k1) - 1) * byte1 + (l3 + k1)] == -2) {
									adjacentTreeBlocks[(l2 + k1) * j1 + ((j3 + k1) - 1) * byte1 + (l3 + k1)] = i2;
								}
								if (adjacentTreeBlocks[(l2 + k1) * j1 + (j3 + k1 + 1) * byte1 + (l3 + k1)] == -2) {
									adjacentTreeBlocks[(l2 + k1) * j1 + (j3 + k1 + 1) * byte1 + (l3 + k1)] = i2;
								}
								if (adjacentTreeBlocks[(l2 + k1) * j1 + (j3 + k1) * byte1 + ((l3 + k1) - 1)] == -2) {
									adjacentTreeBlocks[(l2 + k1) * j1 + (j3 + k1) * byte1 + ((l3 + k1) - 1)] = i2;
								}
								if (adjacentTreeBlocks[(l2 + k1) * j1 + (j3 + k1) * byte1 + (l3 + k1 + 1)] == -2) {
									adjacentTreeBlocks[(l2 + k1) * j1 + (j3 + k1) * byte1 + (l3 + k1 + 1)] = i2;
								}
							}
						}
					}
				}
			}
			int j2 = adjacentTreeBlocks[k1 * j1 + k1 * byte1 + k1];
			if (j2 >= 0) {
				world.setBlockMetadata(x, y, z, metadata & -9);
			} else {
				removeLeaves(world, x, y, z);
			}
		}
	}

	protected void removeLeaves(World world, int x, int y, int z) {
		//	System.out.println("leaf metadata: " + world.getBlockMetadata(x, y, z));
		this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
		world.setBlockWithNotify(x, y, z, 0);
	}

	@Override
	public int quantityDropped(Random random) {
		return random.nextInt(20) != 0 ? 0 : 1;
	}

	@Override
	public int idDropped(int metadata, Random random, int j) {
		int treeType = metadata & 3;
		if (treeType == 0) {
			return TropicraftMod.saplings.blockID;
		}

		return 0;
	}

	/**
	 * Drops the block items with a specified chance of dropping the specified
	 * items
	 */
	@Override
	public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7) {
		if (!par1World.isRemote) {
			if (par1World.rand.nextInt(20) == 0) {
				int var9 = this.idDropped(par5, par1World.rand, par7);
				//    System.out.println("Dropping damage: " + this.damageDropped(par5) + "  Dropping item id: " + var9 + " Resulting in: " + (new ItemStack(var9, 1, this.damageDropped(par5))).itemID);

				if (var9 > 0) {
					this.dropBlockAsItem_do(par1World, par2, par3, par4, new ItemStack(var9, 1, this.damageDropped(par5)));
				}
					//System.out.println("So, like, idDropped is derping and we dropped " + var9);
			}
		}
	}

	@Override
	public boolean isOpaqueCube() {
		return !graphicsLevel;
	}


	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int meta) {
		setGraphicsLevel(ModLoader.getMinecraftInstance().gameSettings.fancyGraphics);
		int treeType = meta & 3;
		if (!graphicsLevel) {
			treeType += textures.length / 2;
		}
		return textures[treeType];
	}

	/**
	 * Called when a leaf should start its decay process.
	 * 
	 * @param world The current world
	 * @param x X Position
	 * @param y Y Position
	 * @param z Z Position
	 */
	@Override
	public void beginLeavesDecay(World world, int x, int y, int z){
		world.setBlockMetadata(x, y, z, world.getBlockMetadata(x, y, z) | 8);
	}

	@Override
	public void setGraphicsLevel(boolean flag) {
		graphicsLevel = flag;
	}
}