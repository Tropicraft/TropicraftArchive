package net.tropicraft.items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.tropicraft.blocks.BlockTropicraftSapling;
import net.tropicraft.mods.TropicraftMod;

public class ItemTropicalMeal extends ItemTropicraft {

    public ItemTropicalMeal(int i) {
        super(i);
        setHasSubtypes(true);
        setMaxDamage(0);
        this.setCreativeTab(TropicraftMod.tabMaterials);
    }

    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float par8, float par9, float par10) {
        int i1 = world.getBlockId(i, j, k);
        if (i1 == TropicraftMod.saplings.blockID) {
            if (!world.isRemote) {
                ((BlockTropicraftSapling) TropicraftMod.saplings).growTree(world, i, j, k, world.rand);
                itemstack.stackSize--;
            }
            return true;
        }
        if (i1 == Block.crops.blockID) {
            if (!world.isRemote) {
                ((BlockCrops) Block.crops).fertilize(world, i, j, k);
                itemstack.stackSize--;
            }
            return true;
        }
        if (i1 == TropicraftMod.pineappleFlower.blockID)
        {
        	if(!world.isRemote) {
        		world.setBlockAndMetadataWithNotify(i, j + 1, k, TropicraftMod.pineappleFlower.blockID, 0);
        		itemstack.stackSize--;
        	}
        	
        	return true;
        }
        if (i1 == Block.grass.blockID) {
            if (!world.isRemote) {
                itemstack.stackSize--;
                label0:
                for (int j1 = 0; j1 < 128; j1++) {
                    int k1 = i;
                    int l1 = j + 1;
                    int i2 = k;
                    for (int j2 = 0; j2 < j1 / 16; j2++) {
                        k1 += itemRand.nextInt(3) - 1;
                        l1 += ((itemRand.nextInt(3) - 1) * itemRand.nextInt(3)) / 2;
                        i2 += itemRand.nextInt(3) - 1;
                        if (world.getBlockId(k1, l1 - 1, i2) != Block.grass.blockID || world.isBlockNormalCube(k1, l1, i2)) {
                            continue label0;
                        }
                    }

                    if (world.getBlockId(k1, l1, i2) != 0) {
                        continue;
                    }
                    if (itemRand.nextInt(3) != 0) {
                        world.setBlockAndMetadataWithNotify(k1, l1, i2, Block.tallGrass.blockID, 1);
                        continue;
                    }
                    if (itemRand.nextInt(7) != 0) {
                        world.setBlockWithNotify(k1, l1, i2, Block.plantYellow.blockID);
                    } else {
                        world.setBlockWithNotify(k1, l1, i2, Block.plantRed.blockID);

                    }
                    if (itemRand.nextInt(9) == 0) {
                        world.setBlockAndMetadataWithNotify(k1, l1, i2, TropicraftMod.flowerCollection1.blockID, 0);
                        continue;
                    }
                    if (itemRand.nextInt(9) == 0) {
                        world.setBlockAndMetadataWithNotify(k1, l1, i2, TropicraftMod.flowerCollection1.blockID, itemRand.nextInt(7));
                        continue;
                    }
                    if (itemRand.nextInt(9) == 0) {
                        world.setBlockAndMetadataWithNotify(k1, l1, i2, TropicraftMod.flowerCollection1.blockID, itemRand.nextInt(7));
                        continue;
                    }
                    if (itemRand.nextInt(9) == 0) {
                        world.setBlockAndMetadataWithNotify(k1, l1, i2, TropicraftMod.pineappleFlower.blockID, 1);
                        continue;
                    } else if (itemRand.nextInt(8) == 0) {
                        world.setBlockAndMetadataWithNotify(k1, l1, i2, TropicraftMod.irisFlower.blockID, 1);
                        continue;
                    }

                }

            }
            return true;
        }
        return false;
    }
}
