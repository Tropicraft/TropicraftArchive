package tropicraft.world.worldgen;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

import tropicraft.blocks.TropicraftBlocks;

public class WorldGenEIH extends WorldGenerator
{

    public WorldGenEIH() {
    }

    @Override
    /**
     * Generate the EIH statue with cool eyes and lava inside!
     * @param world World instance
     * @param random Random instance
     * @param i x coordinate
     * @param j y coordinate
     * @param k z coordinate
     */
    public boolean generate(World world, Random random, int i, int j, int k) {
        byte byte0 = 5;
        int l = i;
        int i1 = j;
        int j1 = k;
        
        if(j < 1 || j + byte0 + 1 > 128) {
            return false;
        }
        
        if(j >= 128 - byte0 - 1) {
            return false;
        }
        
        if((world.getBlockId(l, i1 - 1, j1) == Block.dirt.blockID || world.getBlockId(l, i1 - 1, j1) == Block.grass.blockID) 
        		&& world.getBlockId(l, i1, j1) == 0) {
            i1++;
            world.setBlock(l + 0, i1 + 0, j1 + 2, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + 0, i1 + 0, j1 + 3, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + 0, i1 + 0, j1 + 4, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -1, i1 + 0, j1 + 4, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -1, i1 + 0, j1 + 1, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -1, i1 + 0, j1 + 3, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -1, i1 + 1, j1 + 4, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + 0, i1 + 1, j1 + 4, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -1, i1 + 1, j1 + 3, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + 0, i1 + 1, j1 + 3, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + 0, i1 + 1, j1 + 2, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -1, i1 + 1, j1 + 1, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -1, i1 + 1, j1 + 0, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + 0, i1 + 2, j1 + 3, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -1, i1 + 2, j1 + 3, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + 0, i1 + 2, j1 + 2, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -1, i1 + 2, j1 + 0, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -1, i1 + 3, j1 + 3, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + 0, i1 + 3, j1 + 2, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + 0, i1 + 3, j1 + 1, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + 0, i1 + 3, j1 + 0, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + 0, i1 + 4, j1 + 2, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -1, i1 + 3, j1 + -1, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + 0, i1 + 3, j1 + -1, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -1, i1 + 2, j1 + -1, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + 0, i1 + 4, j1 + -1, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -1, i1 + 4, j1 + 2, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -1, i1 + 4, j1 + -1, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -1, i1 + 5, j1 + -1, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + 0, i1 + 5, j1 + -1, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -1, i1 + 5, j1 + 1, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -1, i1 + 5, j1 + 2, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -1, i1 + 3, j1 + 4, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -1, i1 + 4, j1 + 3, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + 0, i1 + 6, j1 + -1, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + 0, i1 + 6, j1 + 0, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -1, i1 + 6, j1 + -1, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -1, i1 + 6, j1 + 0, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -1, i1 + 6, j1 + 1, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + 1, i1 + 5, j1 + 0, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + 1, i1 + 5, j1 + 1, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + 1, i1 + 4, j1 + 1, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + 1, i1 + 4, j1 + 0, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + 0, i1 + 2, j1 + 1, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + 0, i1 + 2, j1 + 0, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + 0, i1 + 1, j1 + 0, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + 0, i1 + 0, j1 + 0, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -1, i1 + 0, j1 + 0, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + 0, i1 + 6, j1 + 1, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + 0, i1 + 5, j1 + 0, Block.lavaMoving.blockID);
            world.setBlock(l + -1, i1 + 4, j1 + 0, Block.lavaMoving.blockID);
            world.setBlock(l + -1, i1 + 5, j1 + 0, Block.lavaMoving.blockID);
            world.setBlock(l + -1, i1 + 3, j1 + 0, Block.lavaMoving.blockID);
            world.setBlock(l + -1, i1 + 4, j1 + 1, Block.lavaMoving.blockID);
            world.setBlock(l + -1, i1 + 3, j1 + 1, Block.lavaMoving.blockID);
            world.setBlock(l + -1, i1 + 2, j1 + 1, Block.lavaMoving.blockID);
            world.setBlock(l + -1, i1 + 3, j1 + 2, Block.lavaMoving.blockID);
            world.setBlock(l + -1, i1 + 2, j1 + 2, Block.lavaMoving.blockID);
            world.setBlock(l + -1, i1 + 1, j1 + 2, Block.lavaMoving.blockID);
            world.setBlock(l + -2, i1 + 3, j1 + 4, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -2, i1 + 3, j1 + 3, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -2, i1 + 2, j1 + 3, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -2, i1 + 1, j1 + 3, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -2, i1 + 1, j1 + 4, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -2, i1 + 0, j1 + 4, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -2, i1 + 0, j1 + 3, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -2, i1 + 0, j1 + 1, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -2, i1 + 0, j1 + 0, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -2, i1 + 1, j1 + 1, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -2, i1 + 1, j1 + 0, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -2, i1 + 2, j1 + 0, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -2, i1 + 2, j1 + -1, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -2, i1 + 3, j1 + -1, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -2, i1 + 4, j1 + -1, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -2, i1 + 5, j1 + -1, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -2, i1 + 6, j1 + -1, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -2, i1 + 6, j1 + 1, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -2, i1 + 6, j1 + 0, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -2, i1 + 5, j1 + 2, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -2, i1 + 5, j1 + 1, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -2, i1 + 4, j1 + 2, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -2, i1 + 4, j1 + 3, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -2, i1 + 5, j1 + 0, Block.lavaMoving.blockID);
            world.setBlock(l + -2, i1 + 4, j1 + 0, Block.lavaMoving.blockID);
            world.setBlock(l + -2, i1 + 3, j1 + 0, Block.lavaMoving.blockID);
            world.setBlock(l + -2, i1 + 4, j1 + 1, Block.lavaMoving.blockID);
            world.setBlock(l + -2, i1 + 3, j1 + 1, Block.lavaMoving.blockID);
            world.setBlock(l + -2, i1 + 2, j1 + 1, Block.lavaMoving.blockID);
            world.setBlock(l + -2, i1 + 3, j1 + 2, Block.lavaMoving.blockID);
            world.setBlock(l + -2, i1 + 2, j1 + 2, Block.lavaMoving.blockID);
            world.setBlock(l + -2, i1 + 1, j1 + 2, Block.lavaMoving.blockID);
            world.setBlock(l + -3, i1 + 0, j1 + 0, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -3, i1 + 0, j1 + 2, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -3, i1 + 0, j1 + 3, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -3, i1 + 0, j1 + 4, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -3, i1 + 1, j1 + 4, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -3, i1 + 1, j1 + 3, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -3, i1 + 2, j1 + 3, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -3, i1 + 1, j1 + 0, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -3, i1 + 1, j1 + 2, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -3, i1 + 2, j1 + 2, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -3, i1 + 2, j1 + 1, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -3, i1 + 2, j1 + 0, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -3, i1 + 3, j1 + 2, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -3, i1 + 4, j1 + 2, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -3, i1 + 3, j1 + 1, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -3, i1 + 3, j1 + 0, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -3, i1 + 3, j1 + -1, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -3, i1 + 4, j1 + -1, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -3, i1 + 5, j1 + -1, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -3, i1 + 6, j1 + -1, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -3, i1 + 6, j1 + 0, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -3, i1 + 6, j1 + 1, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -4, i1 + 5, j1 + 0, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -4, i1 + 4, j1 + 0, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -4, i1 + 4, j1 + 1, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + 0, i1 + 4, j1 + 0, Block.lavaMoving.blockID);
            world.setBlock(l + 0, i1 + 4, j1 + 1, Block.lavaMoving.blockID);
            world.setBlock(l + -3, i1 + 4, j1 + 0, Block.lavaMoving.blockID);
            world.setBlock(l + -3, i1 + 4, j1 + 1, Block.lavaMoving.blockID);
            world.setBlock(l + -3, i1 + 5, j1 + 0, Block.lavaMoving.blockID);
            world.setBlock(l + -4, i1 + 5, j1 + 1, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -2, i1 + 1, j1 + -1, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -1, i1 + 1, j1 + -1, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -2, i1 + 0, j1 + -1, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -1, i1 + 0, j1 + -1, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -3, i1 + -1, j1 + 0, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -2, i1 + -1, j1 + 0, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -1, i1 + -1, j1 + 0, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + 0, i1 + -1, j1 + 0, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -2, i1 + -1, j1 + 1, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -1, i1 + -1, j1 + 1, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -3, i1 + -1, j1 + 2, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + 0, i1 + -1, j1 + 2, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -2, i1 + -1, j1 + 3, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -1, i1 + -1, j1 + 3, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -3, i1 + -2, j1 + 2, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -2, i1 + -2, j1 + 3, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -1, i1 + -2, j1 + 3, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + 0, i1 + -2, j1 + 2, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -1, i1 + -2, j1 + 1, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -2, i1 + -2, j1 + 1, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -3, i1 + -2, j1 + 0, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -2, i1 + -2, j1 + 0, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -1, i1 + -2, j1 + 0, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + 0, i1 + -2, j1 + 0, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + 0, i1 + -3, j1 + 2, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -1, i1 + -3, j1 + 3, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -1, i1 + 0, j1 + 2, Block.lavaMoving.blockID);
            world.setBlock(l + -2, i1 + 0, j1 + 2, Block.lavaMoving.blockID);
            world.setBlock(l + -1, i1 + -1, j1 + 2, Block.lavaMoving.blockID);
            world.setBlock(l + -2, i1 + -1, j1 + 2, Block.lavaMoving.blockID);
            world.setBlock(l + -2, i1 + -2, j1 + 2, Block.lavaMoving.blockID);
            world.setBlock(l + -1, i1 + -2, j1 + 2, Block.lavaMoving.blockID);
            world.setBlock(l + -2, i1 + -3, j1 + 3, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -1, i1 + -3, j1 + 2, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -2, i1 + -3, j1 + 2, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -3, i1 + -3, j1 + 2, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -2, i1 + -3, j1 + 1, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -1, i1 + -3, j1 + 1, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -3, i1 + -3, j1 + 0, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -2, i1 + -3, j1 + 0, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + -1, i1 + -3, j1 + 0, TropicraftBlocks.chunkOHead.blockID);
            world.setBlock(l + 0, i1 + -3, j1 + 0, TropicraftBlocks.chunkOHead.blockID);
            
            int k1 = random.nextInt(7);
            // coords of the first eye
            int eye1X = l, eye1Y = i1 + 5, eye1Z = j1 + 1;
            // coords of the second eye
            int eye2X = l - 3, eye2Y = i1 + 5, eye2Z = j1 + 1;
            
            // Place eyes
            placeEye(world, eye1X, eye1Y, eye1Z, k1, random);
            placeEye(world, eye2X, eye2Y, eye2Z, k1, random);
        }
        return true;
    }
    
    /**
     * Place an eye on the head
     * @param world World instance
     * @param x xCoord
     * @param y yCoord
     * @param z zCoord
     * @param eye_rand Randomized int value that determines which block the eye will be
     * @param rand Random object
     */
    private void placeEye(World world, int x, int y, int z, int eye_rand, Random rand) {
    	int id;
    	int meta = 0;
    	switch (eye_rand) {
    	case 0:	// Fall through to case 5
    	case 5:
    		id = Block.glowStone.blockID;
    		break;
    	case 1:
    		id = Block.obsidian.blockID;
    		break;
    	case 2:
    		id = Block.blockDiamond.blockID;
    		break;
    	case 3:
    		id = Block.blockIron.blockID;
    		break;
    	case 4:
    		id = Block.blockGold.blockID;
    		break;
    	case 6:
    		id = TropicraftBlocks.tropiOres.blockID;
    		meta = rand.nextInt(3);
    		break;
    	default:	// Should never get called, if so, redstone in tropics :o
    		id = Block.blockRedstone.blockID;
    		break;
    	}
        
    	world.setBlock(x, y, z, id, meta, 3);
    }   
}
