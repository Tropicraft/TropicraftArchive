package tropicraft.world.worldgen;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

import tropicraft.blocks.TropicraftBlocks;

public class WorldGenUndergroundFruitTree extends WorldGenerator {

	int treeType;

	public WorldGenUndergroundFruitTree(boolean flag, int i) {
		super(flag);
		treeType = i;
	}

    @Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		// 	System.out.println("damage: " + damage);
		int l = random.nextInt(3) + 4;
		//        int damage = random.nextInt(5);
		boolean flag = true;
		if (j < 1 || j + l + 1 > 40) {
			return false;
		}
		
		for(int x = i - 3; x < i + 3; x++) {
			for(int y = j; y < j + l + 2; y++) {
				for(int z = k - 3; z < k + 3; z++) {
					int block = world.getBlockId(x, y, z);
					if(block != 0 && block != TropicraftBlocks.fruitLeaves.blockID && block != TropicraftBlocks.tropicsLeaves.blockID) {
						return  false;
					}
				}
			}
		}

		if (!flag) {
			return false;
		}
		int j1 = world.getBlockId(i, j - 1, k);
		if (j1 != Block.stone.blockID) {
			return false;
		}

		world.setBlock(i, j - 1, k, Block.dirt.blockID);
		world.setBlock(i + 1, j - 1, k, Block.dirt.blockID);
		world.setBlock(i - 1, j - 1, k, Block.dirt.blockID);
		world.setBlock(i, j - 1, k + 1, Block.dirt.blockID);
		world.setBlock(i, j - 1, k - 1, Block.dirt.blockID);

		for (int k1 = (j - 3) + l; k1 <= j + l; k1++) {
			int j2 = k1 - (j + l);
			int i3 = 1 - j2 / 2;
			for (int k3 = i - i3; k3 <= i + i3; k3++) {
				int l3 = k3 - i;
				for (int i4 = k - i3; i4 <= k + i3; i4++) {
					int j4 = i4 - k;
					if ((Math.abs(l3) != i3 || Math.abs(j4) != i3 || random.nextInt(2) != 0 && j2 != 0) && !Block.opaqueCubeLookup[world.getBlockId(k3, k1, i4)]) {
						if (random.nextBoolean()) {
							// Set fruit-bearing leaves here
							setBlockAndMetadata(world, k3, k1, i4, TropicraftBlocks.fruitLeaves.blockID, treeType);
						} else {
							// Set plain fruit tree leaves here
							setBlockAndMetadata(world, k3, k1, i4, TropicraftBlocks.tropicsLeaves.blockID, 1);
						}
					}
				}
			}
		}

		for (int l1 = 0; l1 < l; l1++) {
			int k2 = world.getBlockId(i, j + l1, k);
			if (k2 == 0 || k2 == Block.leaves.blockID || k2 == TropicraftBlocks.tropicsLeaves.blockID || k2 == TropicraftBlocks.fruitLeaves.blockID) {
				setBlockAndMetadata(world, i, j + l1, k, Block.wood.blockID, 0);
			}
		}

		return true;
	}
}