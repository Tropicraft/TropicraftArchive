package net.tropicraft.dungeons;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenRotate extends WorldGenerator {

	public AxisAlignedBB boundingBox;
	private static final int DISTANCE = 9;
	private int orientation;
	private int x, y, z;

	public WorldGenRotate(int orientation) {
		super(true);
		this.orientation = orientation;
	}

	@Override
	public boolean generate(World world, Random var2, int i, int j, int k) {		
		x = i;
		y = j;
		z = k;
		boundingBox = AxisAlignedBB.getBoundingBox(0, 0, 0, DISTANCE, DISTANCE, DISTANCE);
		int sandstone = Block.sandStone.blockID;
		int stair = Block.stairsSandStone.blockID;
		int walllength = 30;

		generateTower(world, i, j, k, 0, 0, 0);
		generateTower(world, i, j, k, (int)boundingBox.maxX + walllength, 0, 0);
		generateTower(world, i, j, k, 0, 0, (int)boundingBox.maxZ + walllength);
		generateTower(world, i, j, k, (int)boundingBox.maxX + walllength, 0, (int)boundingBox.maxZ + walllength);

		generateWall(world, i, j, k, 0, 0, 0);
		
		return true;
	}
	
	private boolean generateWall(World world, int i, int j, int k, int xx, int yy, int zz) {
		int sandstone = Block.sandStone.blockID;
		int stair = Block.stairsSandStone.blockID;
		int walllength = 30;
		
		//one way
		
		//long wall, base, smooth
		for (int b = 0; b < 2; b++)
			for (int a = (int)boundingBox.maxX ; a <= walllength + boundingBox.maxX; a++) {
				place(world, a, b, 2, sandstone, 2);
			}
		
		//long wall, upper-mid
		for (int b = 2; b < 12; b++)
			for (int a = (int)boundingBox.maxX - 1; a <= walllength + boundingBox.maxX; a++) {
				place(world, a, b, 3, sandstone, 0);
			}
		
		//long wall, top
		for (int b = 12; b < 14; b++)
			for (int a = (int)boundingBox.maxX - 1; a <= walllength + boundingBox.maxX; a++) {
				place(world, a, b, 3, sandstone, 0);
			}
		
		//another way
		
		zz = (int)boundingBox.maxZ + walllength;
		
		//long wall, base, smooth
		for (int b = 0; b < 2; b++)
			for (int a = (int)boundingBox.maxX ; a <= walllength + boundingBox.maxX; a++) {
				place(world, a, b, 6, sandstone, 2, 0, yy, zz);
			}
		
		//long wall, upper-mid
		for (int b = 2; b < 12; b++)
			for (int a = (int)boundingBox.maxX - 1; a <= walllength + boundingBox.maxX; a++) {
				place(world, a, b, 5, sandstone, 0, 0, yy, zz);
			}
		
		//long wall, top
		for (int b = 12; b < 14; b++)
			for (int a = (int)boundingBox.maxX - 1; a <= walllength + boundingBox.maxX; a++) {
				place(world, a, b, 5, sandstone, 0, 0, yy, zz);
			}
		
		///////////////////////
		
		zz = 0;
		
		//long wall, base, smooth
		for (int b = 0; b < 2; b++)
			for (int a = (int)boundingBox.maxZ ; a <= walllength + boundingBox.maxZ; a++) {
				place(world, 2, b, a, sandstone, 2, 0, yy, zz);
			}
		
		//long wall, upper-mid
		for (int b = 2; b < 12; b++)
			for (int a = (int)boundingBox.maxX - 1; a <= walllength + boundingBox.maxX; a++) {
				place(world, 3, b, a, sandstone, 0, 0, yy, zz);
			}
		
		//long wall, top
		for (int b = 12; b < 14; b++)
			for (int a = (int)boundingBox.maxX - 1; a <= walllength + boundingBox.maxX; a++) {
				place(world, 3, b, a, sandstone, 0, 0, yy, zz);
			}
		
		xx = (int)boundingBox.maxX + walllength;
		
		//AND THE LAST ONE
		//long wall, base, smooth
		for (int b = 0; b < 2; b++)
			for (int a = (int)boundingBox.maxZ ; a <= walllength + boundingBox.maxZ; a++) {
				place(world, 6, b, a, sandstone, 2, xx, yy, zz);
			}
		
		//long wall, upper-mid
		for (int b = 2; b < 12; b++)
			for (int a = (int)boundingBox.maxX - 1; a <= walllength + boundingBox.maxX; a++) {
				place(world, 5, b, a, sandstone, 0, xx, yy, zz);
			}
		
		//long wall, top
		for (int b = 12; b < 14; b++)
			for (int a = (int)boundingBox.maxX - 1; a <= walllength + boundingBox.maxX; a++) {
				place(world, 5, b, a, sandstone, 0, xx, yy, zz);
			}
		
		return true;
	}
	
	private boolean generateTower(World world, int i, int j, int k, int xx, int yy, int zz) {
		
		int sandstone = Block.sandStone.blockID;
		int stair = Block.stairsSandStone.blockID;
		
		//generate lowest outer wall
		for (int b = 0; b < 2; b++) {
			//front and back
			for (int ii = 0; ii < 9; ii++) {
				place(world, ii, b, 0, sandstone, 2, xx, yy, zz);
				place(world, ii, b, 8, sandstone, 2, xx, yy, zz);
			}
			//left and right
			for (int a = 0; a < 9; a++) {
				place(world, 0, b, a, sandstone, 2, xx, yy, zz);
				place(world, 8, b, a, sandstone, 2, xx, yy, zz);
			}
		}

		//generate inner tall wall
		for (int b = 2; b < 19; b++) {
			//front and back
			for (int ii = 1; ii < 8; ii++) {
				place(world, ii, b, 1, sandstone, 0, xx, yy, zz);
				place(world, ii, b, 7, sandstone, 0, xx, yy, zz);
			}
			//left and right
			for (int a = 1; a < 8; a++) {
				place(world, 1, b, a, sandstone, 0, xx, yy, zz);
				place(world, 7, b, a, sandstone, 0, xx, yy, zz);
			}
		}

		//level 14 stairs border
		//front and back
		for (int ii = 0; ii < 9; ii++) {
			place(world, ii, 15, 0, stair, 6, xx, yy, zz); //good
			place(world, ii, 15, 8, stair, 7, xx, yy, zz); //good
		}
		//left and right
		for (int a = 0; a < 9; a++) {
			place(world, 0, 15, a, stair, 12, xx, yy, zz);
			place(world, 8, 15, a, stair, 13, xx, yy, zz);
		}

		//level 15-16 smooth upper border
		//front and back
		for (int b = 0; b < 2; b++) {
			for (int ii = 0; ii < 9; ii++) {
				place(world, ii, 16 + b, 0, sandstone, 2, xx, yy, zz);
				place(world, ii, 16 + b, 8, sandstone, 2, xx, yy, zz);
			}
			//left and right
			for (int a = 0; a < 9; a++) {
				place(world, 0, 16 + b, a, sandstone, 2, xx, yy, zz);
				place(world, 8, 16 + b, a, sandstone, 2, xx, yy, zz);
			}
		}

		//level 17 stairs border
		//front and back
		for (int ii = -1; ii < 10; ii++) {
			place(world, ii, 17, -1, stair, 6, xx, yy, zz); //good
			place(world, ii, 17, 9, stair, 7, xx, yy, zz); //good
		}
		//left and right
		for (int a = -1; a < 10; a++) {
			place(world, -1, 17, a, stair, 12, xx, yy, zz);
			place(world, 9, 17, a, stair, 13, xx, yy, zz);
		}

		//level 18 smooth upper border
		//front and back
		for (int ii = -1; ii < 10; ii++) {
			place(world, ii, 18, -1, sandstone, 2, xx, yy, zz);
			place(world, ii, 18, 9, sandstone, 2, xx, yy, zz);
		}
		//left and right
		for (int a = -1; a < 10; a++) {
			place(world, -1, 18, a, sandstone, 2, xx, yy, zz);
			place(world, 9, 18, a, sandstone, 2, xx, yy, zz);
		}

		//level 19 thingies
		for (int a = 0; a < 11; a++) {
			//front and back
			if (a % 2 == 0) {
				place(world, a - 1, 19, -1, sandstone, 2, xx, yy, zz);
				place(world, a - 1, 19, 9, sandstone, 2, xx, yy, zz);
			}

			//left and right
			if (a % 2 == 0) {
				place(world, -1, 19, a - 1, sandstone, 2, xx, yy, zz);
				place(world, 9, 19, a - 1, sandstone, 2, xx, yy, zz);
			}
		}

		//level 20
		for (int a = 0; a < 11; a++) {
			//front and back
			place(world, a - 1, 20, -1, sandstone, 2, xx, yy, zz);
			place(world, a - 1, 20, 9, sandstone, 2, xx, yy, zz);
			//left and right
			place(world, -1, 20, a - 1, sandstone, 2, xx, yy, zz);
			place(world, 9, 20, a - 1, sandstone, 2, xx, yy, zz);			
		}
		
		return true;
	}

	private boolean place(World world, int i, int j, int k, int blockID, int meta) {
		switch (orientation) {
		case 0:
			world.setBlockAndMetadataWithNotify(x + i, y + j, z + k, blockID, meta);
			break;
			//90 degrees
		case 1:
			world.setBlockAndMetadata((int)(x + boundingBox.maxX) - k, y + j, z + i, blockID, meta);
			break;
			//180 degrees
		case 2:
			world.setBlockAndMetadata((int)(x + boundingBox.maxX) - i, y + j, (int)(boundingBox.maxZ + z) - k, blockID, meta);
			break;
			//270 degrees
		case 3:
			world.setBlockAndMetadata(x + k, y + j, (int)(boundingBox.maxZ + z) - i, blockID, meta);
			break;
		default:
			break;
		}	

		return true;
	}
	
	private boolean place(World world, int i, int j, int k, int blockID, int meta, int xx, int yy, int zz) {
		int x = this.x + xx;
		int y = this.y + yy;
		int z = this.z + zz;		
		
		switch (orientation) {
		case 0:
			world.setBlockAndMetadataWithNotify(x + i, y + j, z + k, blockID, meta);
			break;
			//90 degrees
		case 1:
			world.setBlockAndMetadata((int)(x + boundingBox.maxX) - k, y + j, z + i, blockID, meta);
			break;
			//180 degrees
		case 2:
			world.setBlockAndMetadata((int)(x + boundingBox.maxX) - i, y + j, (int)(boundingBox.maxZ + z) - k, blockID, meta);
			break;
			//270 degrees
		case 3:
			world.setBlockAndMetadata(x + k, y + j, (int)(boundingBox.maxZ + z) - i, blockID, meta);
			break;
		default:
			break;
		}	

		return true;
	}

}
