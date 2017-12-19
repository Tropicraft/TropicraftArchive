package net.tropicraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.tropicraft.mods.TropicraftMod;

import java.util.ArrayList;
import java.util.Random;

public class BlockFruitLeaves extends BlockLeaves {

    private int baseIndexInPNG;
    private int[] adjacentTreeBlocks;
    public String[] names;

    enum sexy {COROSUS, COJO, RUBE, FISHTACO};	//8D

    public BlockFruitLeaves(int i, int j, String[] s) {
        super(i, j);
        baseIndexInPNG = j;
        names = s;
        setTickRandomly(true);
        Block.setBurnProperties(i, 30, 60);
        this.setTextureFile("/tropicalmod/tropiterrain.png");
        setRequiresSelfNotify();
        this.setCreativeTab(TropicraftMod.tabBlock);
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

    private boolean isLeafBlockID(int i) {
        Block b = Block.blocksList[i];
        if (b != null && b.blockMaterial == Material.leaves) {
            return true;
        }
        return false;
    }

    private boolean isWoodBlockID(int i) {
        if (i == Block.wood.blockID || i == TropicraftMod.tropicalWood.blockID) {
            return true;
        }
        return false;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
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

    private void removeLeaves(World world, int x, int y, int z) {
        dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
        world.setBlockWithNotify(x, y, z, 0);
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
                return TropicraftMod.grapefruit.shiftedIndex;
            } else if (treeType == 1) {
                return TropicraftMod.lemon.shiftedIndex;
            } else if (treeType == 2) {
                return TropicraftMod.orange.shiftedIndex;
            } else {
                return TropicraftMod.lime.shiftedIndex;
            }
        }
        return TropicraftMod.saplings.blockID;
    }

    @Override
    public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

        int count = quantityDropped(metadata, fortune, world.rand);
        for (int i = 0; i < count; i++) {
            int id = idDropped(metadata, world.rand, 0);
            if (id > 0) {
                if (id == TropicraftMod.saplings.blockID) {
                    ret.add(new ItemStack(id, 1, damageDropped(metadata) + 1));
                } else {
                    ret.add(new ItemStack(id, 1, damageDropped(metadata)));
                }
            }
        }
        return ret;
    }

    @Override
    public void dropBlockAsItemWithChance(World world, int x, int y, int z, int metadata, float chance, int i1) {
        if (world.isRemote) {
            return;
        }

        ArrayList<ItemStack> items = getBlockDropped(world, x, y, z, metadata, i1);
        for (ItemStack item : items) {
            if (world.rand.nextFloat() > chance) {
                continue;
            }
            dropBlockAsItem_do(world, x, y, z, item);
        }
    }

    @Override
    public boolean isOpaqueCube() {
        return !graphicsLevel;
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(int side, int meta) {
        setGraphicsLevel(ModLoader.getMinecraftInstance().gameSettings.fancyGraphics);
        return blockIndexInTexture + (meta & 3);
    }

    @Override
    public void setGraphicsLevel(boolean flag) {
        graphicsLevel = flag;
        blockIndexInTexture = baseIndexInPNG + (flag ? 0 : 5);
    }
}