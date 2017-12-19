package tropicraft.items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import tropicraft.blocks.BlockTropicraftSapling;
import tropicraft.blocks.TropicraftBlocks;
import tropicraft.creative.TropiCreativeTabs;
import tropicraft.enchanting.EnchantmentManager;

public class ItemFertilizer extends ItemTropicraftImpl {

	public ItemFertilizer(int id, String imgName) {
		super(id, imgName);
		setCreativeTab(TropiCreativeTabs.tabMaterials);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float par8, float par9, float par10) {
		int i1 = world.getBlockId(i, j, k);
		int meta = world.getBlockMetadata(i, j, k);
		 if (i1 == TropicraftBlocks.saplings.blockID) {
            if (!world.isRemote) {
                ((BlockTropicraftSapling) TropicraftBlocks.saplings).growTree(world, i, j, k, world.rand);
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
		if (i1 == TropicraftBlocks.tallFlower.blockID && meta <= 7)
		{
			if(!world.isRemote) {
				world.setBlock(i, j + 1, k, TropicraftBlocks.tallFlower.blockID, 9 /* pineapple */, 3);
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
						if (itemRand.nextInt(2) != 0) {
							world.setBlock(k1, l1, i2, Block.tallGrass.blockID, 1, 3);
							continue;
						}
						if (itemRand.nextInt(7) == 0) {
							world.setBlock(k1, l1, i2, Block.plantYellow.blockID);
						} else 
							if (itemRand.nextInt(7) == 1) {
								world.setBlock(k1, l1, i2, Block.plantRed.blockID);
							}

						if (itemRand.nextInt(9) == 0) {
							world.setBlock(k1, l1, i2, TropicraftBlocks.tropicsFlowers.blockID, 0, 3);
							continue;
						}
						if (itemRand.nextInt(9) == 0) {
							world.setBlock(k1, l1, i2, TropicraftBlocks.tropicsFlowers.blockID, itemRand.nextInt(7), 3);
							continue;
						}
						if (itemRand.nextInt(9) == 0) {
							world.setBlock(k1, l1, i2, TropicraftBlocks.tropicsFlowers.blockID, itemRand.nextInt(7), 3);
							continue;
						}
						if (itemRand.nextInt(9) == 0) {	//pineapple
							world.setBlock(k1, l1, i2, TropicraftBlocks.tallFlower.blockID, 0, 3);
							continue;
						} else if (itemRand.nextInt(8) == 0) {		//iris
							world.setBlock(k1, l1, i2, TropicraftBlocks.tallFlower.blockID, 8, 3);
							continue;
						}

					}

			}
			return true;
		}
		return false;
	}

}
