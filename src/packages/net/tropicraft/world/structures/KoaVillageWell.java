package net.tropicraft.world.structures;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.tropicraft.mods.TropicraftMod;

public class KoaVillageWell extends KoaVillageComponent {
    
    private static final int bridgeBlockID = TropicraftMod.thatchBlock.blockID;
    
    private Block thatchBlock = TropicraftMod.thatchBlock;
    private Block bambooFence = TropicraftMod.bambooFence;
    private Block thatchStair = TropicraftMod.thatchStairs;
    private Block thatchSlab = TropicraftMod.tropicalSingleSlab;
    private Block tikiTorch = TropicraftMod.tikiTorch;

    public KoaVillageWell(KoaVillage village, int x, int y, int z) {
        this.village = village;
        //generateZeWell(world, i, j, k, i2, j2, k2, dir);
        homeX = x;
        homeY = y;
        homeZ = z;
        //spawn(world, homeX, homeY, homeZ);
    }
    
    @Override
    public boolean canFitInWorld(World world) {
        
        if (world.getBlockId(homeX + 1, homeY, homeZ) == bridgeBlockID || world.getBlockId(homeX - 1, homeY, homeZ) == bridgeBlockID) {
            if (world.getBlockId(homeX + 1, homeY, homeZ) == bridgeBlockID) {
                for (int x1 = -4; x1 < 1; x1++) {
                    for (int z1 = -1; z1 < 4; z1++) {
                        //if (world.getHeightValue(x1, z1) > homeY - 1) {
                        if (world.getBlockId(homeX + x1, homeY, homeZ + z1) != 0) {
                            return false;
                        }
                    }
                }
                genX = homeX;
                genY = homeY;
                genZ = homeZ;
                genDirection = 2;
                return true;
            } else if (world.getBlockId(homeX - 1, homeY, homeZ) == bridgeBlockID) {
                for (int x1 = -1; x1 < 4; x1++) {
                    for (int z1 = -1; z1 < 4; z1++) {
                        //if (world.getHeightValue(x1, z1) > homeY - 1) {
                        if (world.getBlockId(homeX + x1, homeY, homeZ + z1) != 0) {
                            return false;
                        }
                    }
                }
                genX = homeX;
                genY = homeY;
                genZ = homeZ;
                genDirection = 1;
                return true;
            }
        } else if (world.getBlockId(homeX, homeY, homeZ + 1) == bridgeBlockID || world.getBlockId(homeX, homeY, homeZ - 1) == bridgeBlockID) {
            if (world.getBlockId(homeX, homeY, homeZ + 1) == bridgeBlockID) {
                for (int x1 = -5; x1 < 0; x1++) {
                    for (int z1 = -4; z1 < -1; z1++) {
                        //if (world.getHeightValue(x1, z1) > homeY - 1) {
                        if (world.getBlockId(homeX + x1, homeY, homeZ + z1) != 0) {
                            return false;
                        }
                    }
                }
                
                genX = homeX;
                genY = homeY;
                genZ = homeZ;
                genDirection = 3;
                return true;
                
            } else if (world.getBlockId(homeX, homeY, homeZ - 1) == bridgeBlockID) {
                for (int x1 = -5; x1 < 0; x1++) {
                    for (int z1 = -1; z1 < 4; z1++) {
                        //if (world.getHeightValue(x1, z1) > homeY - 1) {
                        if (world.getBlockId(homeX + x1, homeY, homeZ + z1) != 0) {
                            return false;
                        }
                    }
                }
                genX = homeX;
                genY = homeY;
                genZ = homeZ;
                genDirection = 4;
                return true;
            }
        }
        
        return false;
    }

    @Override
    public void spawn(World world) {
        //	EntityKoaMember ekm = village.h.get(0);
		/*
         * EntityKoaMan ekm;//
         *
         * try { ekm = (EntityKoaMan)EntityKoaMan.class.getConstructor(new
         * Class[]{World.class}).newInstance(new Object[]{world});
         * ekm.setLocationAndAngles(i, j, k, 0, 0);
         * world.spawnEntityInWorld(ekm); } catch (Exception var32) {
         * var32.printStackTrace();
          }
         */
        //= new EntityKoaMan(world);
        //	EntityKoaMember ekm = (EntityKoaMember) village.entityList.get(0);
//		ekm.setLocationAndAngles(i, j, k, 0F, 0F);
        //	ekm.occupation = EnumKoaOccupation.mFISHERMAN;
        //	ekm.occupationState = EnumKoaOccupationState.IDLE;
//		world.spawnEntityInWorld(ekm);
    }

    //private void generateZeWell(World world, int i, int j, int k, int i2, int j2, int k2, int dir) {
    @Override
    public void generate(World world) {
        
        if (genDirection == 1) {
            genWellOne(world, genX, genY, genZ);
        } else if (genDirection == 2) {
            genWellTwo(world, genX, genY, genZ);
        } else if (genDirection == 3) {
            genWellThree(world, genX, genY, genZ);
        } else if (genDirection == 4) {
            genWellFour(world, genX, genY, genZ);
        }

//		spawn(world, homeX, homeY, homeZ);

        /*
        if (world.getBlockId(i2 + 1, j2, k2) == bridgeBlockID || world.getBlockId(i2 - 1, j2, k2) == bridgeBlockID) {
            if (world.getBlockId(i2 + 1, j2, k2) == bridgeBlockID) {
                for (int x = -4; x < 1; x++) {
                    for (int z = -1; z < 4; z++) {
                        if (world.getBlockId(i2 + x, j2, k2 + z) != 0) {
                            //					return;
                        }
                    }
                }

                genWellTwo(world, i2, j2, k2);				//i - 4 >> i + 1 // k - 1 >> k + 3
            } else if (world.getBlockId(i2 - 1, j2, k2) == bridgeBlockID) {
                for (int x = -1; x < 4; x++) {
                    for (int z = -1; z < 4; z++) {
                        if (world.getBlockId(i2 + x, j2, k2 + z) != 0) {
                            //				return;
                        }
                    }
                }

                genWellOne(world, i2, j2, k2);		//i - 1 ->> i + 4 // k - 1 --> k + 3
            }

        } else if (world.getBlockId(i2, j2, k2 + 1) == bridgeBlockID || world.getBlockId(i2, j2, k2 - 1) == bridgeBlockID) {
            if (world.getBlockId(i2, j2, k2 + 1) == bridgeBlockID) {
                for (int x = -5; x < 0; x++) {
                    for (int z = -4; z < -1; z++) {
                        if (world.getBlockId(i2 + x, j2, k2 + z) != 0) {
                            //				return;
                        }
                    }
                }
                genWellThree(world, i2, j2, k2);			//i - 5 >> i // k - 4 >> k - 1
            } else if (world.getBlockId(i2, j2, k2 - 1) == bridgeBlockID) {
                for (int x = -5; x < 0; x++) {
                    for (int z = -1; z < 4; z++) {
                        if (world.getBlockId(i2 + x, j2, k2 + z) != 0) {
                            //					return;
                        }
                    }
                }
                genWellFour(world, i2, j2, k2);			//i - 5 >> i // k - 1 >> k + 4
            }
        }
        * 
        */
    }

    private void genWellOne(World world, int x, int y, int z) {

        for (int b = 0; b < 3; b++) {
            place(world, x, y, z + b, thatchBlock);			//this is the entrance steps
        }

        for (int a = 0; a < 4; a++) {
            for (int b = 0; b < 5; b++) {
                place(world, x + a + 1, y, z + b - 1, thatchBlock);		//this is the base
                if ((a == 0 || a == 3) && (b == 0 || b == 4)) {
                    placeDownTilGround(world, x + a + 1, y - 1, z + b - 1, bambooFence);  // posts under base corners
                }
            }
        }

        place(world, x - 1, y + 1, z - 1, bambooFence);			//posts on the dock/bridge
        place(world, x - 1, y + 1, z + 3, bambooFence);
        place(world, x - 1, y + 2, z - 1, tikiTorch, 0);			//posts on the dock/bridge
        place(world, x - 1, y + 2, z + 3, tikiTorch, 0);

        //placeDownTilGround(world, i - 1, j - 1, k - 1, b2);
        //placeDownTilGround(world, i - 1, j - 1, k + 3, b2);

        for (int a = 0; a < 3; a++) {
            place(world, x + 1, y + 1 + a, z - 1, bambooFence);
            place(world, x + 1, y + 1 + a, z + 3, bambooFence);
            place(world, x + 4, y + 1 + a, z - 1, bambooFence);		//these are the posts
            place(world, x + 4, y + 1 + a, z + 3, bambooFence);

            if (a == 2) {
                place(world, x + 1, y + 1 + a, z - 1, thatchSlab, 1);
                place(world, x + 1, y + 1 + a, z + 3, thatchSlab, 1);
                place(world, x + 4, y + 1 + a, z - 1, thatchSlab, 1);		//these are the posts
                place(world, x + 4, y + 1 + a, z + 3, thatchSlab, 1);
            }
        }

        int j3 = y + 3;
        int k1 = z - 1;
        int k2 = z + 3;

        placeWithMetadata(world, x + 2, j3, k1, thatchStair, 2);	//left stairs
        placeWithMetadata(world, x + 3, j3, k1, thatchStair, 2);	// ^

        placeWithMetadata(world, x + 2, j3, k2, thatchStair, 3);		//right stairs
        placeWithMetadata(world, x + 3, j3, k2, thatchStair, 3);		// ^

        for (int d = 0; d < 3; d++) {
            placeWithMetadata(world, x + 4, j3, z + d, thatchStair, 1);	//back stairs

            place(world, x + 2, j3, z + d, thatchBlock);
            place(world, x + 3, j3, z + d, thatchBlock);
            place(world, x + 1, j3, z + d, thatchBlock);				//this and the last 2 lines are just the roof blocks			
        }

        placeWithMetadata(world, x + 1, j3, z, thatchStair, 0);		//front stairs
        placeWithMetadata(world, x + 1, j3, z + 1, thatchStair, 0);	// ^
        placeWithMetadata(world, x + 1, j3, z + 2, thatchStair, 0);	// ^

        place(world, x + 2, j3 + 1, z + 1, thatchSlab, 1);				//top center block

        //try
        place(world, x + 4, y, z + 1, 0);
        place(world, x + 3, y, z + 1, 0);
        
        place(world, x + 2, y, z + 1, 0);			//air pockets for fishin or something
        place(world, x + 1, y, z + 1, 0);
        placeWithMetadata(world, x + 1, y, z + 1, Block.ladder, 5);
        place(world, x, y - 1, z + 1, thatchBlock);
        placeWithMetadata(world, x + 1, y - 1, z + 1, Block.ladder, 5);
        //	for stairs, the metadata = the directions: 3= right, 2 = faces left, 0 faces toward base, 1 faces away from base
    }

    private void genWellTwo(World world, int x, int y, int z) {

        for (int b = 0; b < 3; b++) {
            place(world, x, y, z + b, thatchBlock);			//this is the entrance steps
        }

        for (int a = 0; a < 4; a++) {
            for (int b = 0; b < 5; b++) {
                place(world, x - a - 1, y, z + b - 1, thatchBlock);		//this is the base
                if ((a == 0 || a == 3) && (b == 0 || b == 4)) {
                    placeDownTilGround(world, x - a - 1, y - 1, z + b - 1, bambooFence);  // posts under base corners
                }
            }
        }

        place(world, x + 1, y + 1, z - 1, bambooFence);			//posts on the dock/bridge
        place(world, x + 1, y + 1, z + 3, bambooFence);
        place(world, x + 1, y + 2, z - 1, tikiTorch, 0);			//posts on the dock/bridge
        place(world, x + 1, y + 2, z + 3, tikiTorch, 0);


        //placeDownTilGround(world, i + 1, j - 1, k - 1, b2);
        //placeDownTilGround(world, i + 1, j - 1, k + 3, b2);

        for (int a = 0; a < 3; a++) {
            place(world, x - 1, y + 1 + a, z - 1, bambooFence);
            place(world, x - 1, y + 1 + a, z + 3, bambooFence);
            place(world, x - 4, y + 1 + a, z - 1, bambooFence);		//these are the posts
            place(world, x - 4, y + 1 + a, z + 3, bambooFence);

            if (a == 2) {
                place(world, x - 1, y + 1 + a, z - 1, thatchSlab, 1);
                place(world, x - 1, y + 1 + a, z + 3, thatchSlab, 1);
                place(world, x - 4, y + 1 + a, z - 1, thatchSlab, 1);		//these are the posts
                place(world, x - 4, y + 1 + a, z + 3, thatchSlab, 1);
            }
        }

        int j3 = y + 3;
        int k1 = z - 1;
        int k2 = z + 3;

        placeWithMetadata(world, x - 2, j3, k1, thatchStair, 2);	//left stairs
        placeWithMetadata(world, x - 3, j3, k1, thatchStair, 2);	// ^

        placeWithMetadata(world, x - 2, j3, k2, thatchStair, 3);		//right stairs
        placeWithMetadata(world, x - 3, j3, k2, thatchStair, 3);		// ^

        for (int d = 0; d < 3; d++) {
            placeWithMetadata(world, x - 4, j3, z + d, thatchStair, 0);	//back stairs

            place(world, x - 2, j3, z + d, thatchBlock);
            place(world, x - 3, j3, z + d, thatchBlock);
            place(world, x - 1, j3, z + d, thatchBlock);				//this and the last 2 lines are just the roof blocks			
        }

        placeWithMetadata(world, x - 1, j3, z, thatchStair, 1);		//front stairs
        placeWithMetadata(world, x - 1, j3, z + 1, thatchStair, 1);	// ^
        placeWithMetadata(world, x - 1, j3, z + 2, thatchStair, 1);	// ^

        place(world, x - 2, j3 + 1, z + 1, thatchSlab, 1);				//top center block

      //try
        place(world, x - 4, y, z + 1, 0);
        place(world, x - 3, y, z + 1, 0);
        place(world, x - 2, y, z + 1, 0);			//air pockets for fishin or something
        place(world, x - 1, y, z + 1, 0);
        placeWithMetadata(world, x - 1, y, z + 1, Block.ladder, 4);
        place(world, x, y - 1, z + 1, thatchBlock);
        placeWithMetadata(world, x - 1, y - 1, z + 1, Block.ladder, 4);
        //	for stairs, the metadata = the directions: 3= right, 2 = faces left, 0 faces toward base, 1 faces away from base
    }

    private void genWellThree(World world, int x, int y, int z) {

        for (int b = 0; b < 3; b++) {
            place(world, x - b - 2, y, z, thatchBlock);			//this is the entrance steps
        }

        for (int a = 0; a < 5; a++) {
            for (int b = 0; b < 4; b++) {
                place(world, x - a - 1, y, z - b - 1, thatchBlock);		//this is the base
                if ((a == 0 || a == 4) && (b == 0 || b == 3)) {
                    placeDownTilGround(world, x - a - 1, y - 1, z - b - 1, bambooFence);  // posts under base corners
                }
            }
        }

        place(world, x - 1, y + 1, z + 1, bambooFence);			//posts on the dock/bridge
        place(world, x - 5, y + 1, z + 1, bambooFence);

        place(world, x - 1, y + 2, z + 1, tikiTorch, 0);			//posts on the dock/bridge
        place(world, x - 5, y + 2, z + 1, tikiTorch, 0);

        //placeDownTilGround(world, i - 1, j - 1, k + 1, b2);
        //placeDownTilGround(world, i - 5, j - 1, k + 1, b2);

        for (int a = 0; a < 3; a++) {
            place(world, x - 1, y + 1 + a, z - 1, bambooFence);
            place(world, x - 5, y + 1 + a, z - 4, bambooFence);
            place(world, x - 5, y + 1 + a, z - 1, bambooFence);		//these are the posts
            place(world, x - 1, y + 1 + a, z - 4, bambooFence);

            if (a == 2) {
                place(world, x - 1, y + 1 + a, z - 1, thatchSlab, 1);
                place(world, x - 5, y + 1 + a, z - 1, thatchSlab, 1);
                place(world, x - 1, y + 1 + a, z - 4, thatchSlab, 1);		//these are the slabs
                place(world, x - 5, y + 1 + a, z - 4, thatchSlab, 1);
            }
        }

        int j3 = y + 3;
        int k1 = z - 2;
        int k2 = z - 3;

        placeWithMetadata(world, x - 1, j3, k1, thatchStair, 1);	//left stairs
        placeWithMetadata(world, x - 5, j3, k1, thatchStair, 0);	// ^

        placeWithMetadata(world, x - 1, j3, k2, thatchStair, 1);		//right stairs
        placeWithMetadata(world, x - 5, j3, k2, thatchStair, 0);		// ^

        for (int d = 0; d < 3; d++) {
            placeWithMetadata(world, x - 2 - d, j3, z - 4, thatchStair, 2);	//back stairs

            place(world, x - 2, j3, z - 3 + d, thatchBlock);
            place(world, x - 3, j3, z - 3 + d, thatchBlock);
            place(world, x - 4, j3, z - 3 + d, thatchBlock);				//this and the last 2 lines are just the roof blocks			
        }

        placeWithMetadata(world, x - 2, j3, z - 3 + 2, thatchStair, 3);		//front stairs
        placeWithMetadata(world, x - 3, j3, z - 3 + 2, thatchStair, 3);	// ^
        placeWithMetadata(world, x - 4, j3, z - 3 + 2, thatchStair, 3);	// ^

        place(world, x - 3, j3 + 1, z - 2, thatchSlab, 1);				//top center block

        //try
        place(world, x - 3, y, z - 4, 0);
        place(world, x - 3, y, z - 3, 0);
        place(world, x - 3, y, z - 2, 0);			//air pockets for fishin or something
        place(world, x - 3, y, z - 1, 0);
        placeWithMetadata(world, x - 3, y, z - 1, Block.ladder, 2);
        place(world, x - 3, y - 1, z, thatchBlock);
        placeWithMetadata(world, x - 3, y - 1, z - 1, Block.ladder, 2);
        //	for stairs, the metadata = the directions: 3= right, 2 = faces left, 0 faces toward base, 1 faces away from base

    }

    private void genWellFour(World world, int x, int y, int z) {

        for (int b = 0; b < 3; b++) {
            place(world, x - b - 2, y, z, thatchBlock);			//this is the entrance steps
        }

        for (int a = 0; a < 5; a++) {
            for (int b = 0; b < 4; b++) {
                place(world, x - a - 1, y, z + b + 1, thatchBlock);		//this is the base
                if ((a == 0 || a == 4) && (b == 0 || b == 3)) {
                    placeDownTilGround(world, x - a - 1, y - 1, z + b + 1, bambooFence);  // posts under base corners
                }
            }
        }

        place(world, x - 1, y + 1, z - 1, bambooFence);			//posts on the dock/bridge
        place(world, x - 5, y + 1, z - 1, bambooFence);
        place(world, x - 1, y + 2, z - 1, tikiTorch, 0);			//posts on the dock/bridge
        place(world, x - 5, y + 2, z - 1, tikiTorch, 0);

        //placeDownTilGround(world, i - 1, j - 1, k - 1, b2);
        //placeDownTilGround(world, i - 5, j - 1, k - 1, b2);

        for (int a = 0; a < 3; a++) {
            place(world, x - 1, y + 1 + a, z + 1, bambooFence);
            place(world, x - 5, y + 1 + a, z + 4, bambooFence);
            place(world, x - 5, y + 1 + a, z + 1, bambooFence);		//these are the posts
            place(world, x - 1, y + 1 + a, z + 4, bambooFence);

            if (a == 2) {
                place(world, x - 1, y + 1 + a, z + 1, thatchSlab, 1);
                place(world, x - 5, y + 1 + a, z + 1, thatchSlab, 1);
                place(world, x - 1, y + 1 + a, z + 4, thatchSlab, 1);		//these are the slabs
                place(world, x - 5, y + 1 + a, z + 4, thatchSlab, 1);
            }
        }

        int j3 = y + 3;
        int k1 = z + 2;
        int k2 = z + 3;

        placeWithMetadata(world, x - 1, j3, k1, thatchStair, 1);	//left stairs
        placeWithMetadata(world, x - 5, j3, k1, thatchStair, 0);	// ^

        placeWithMetadata(world, x - 1, j3, k2, thatchStair, 1);		//right stairs
        placeWithMetadata(world, x - 5, j3, k2, thatchStair, 0);		// ^		

        for (int d = 0; d < 3; d++) {
            placeWithMetadata(world, x - 2 - d, j3, z + 4, thatchStair, 3);	//back stairs

            place(world, x - 2, j3, z + 3 - d, thatchBlock);
            place(world, x - 3, j3, z + 3 - d, thatchBlock);
            place(world, x - 4, j3, z + 3 - d, thatchBlock);				//this and the last 2 lines are just the roof blocks			
        }

        placeWithMetadata(world, x - 2, j3, z + 3 - 2, thatchStair, 2);		//front stairs
        placeWithMetadata(world, x - 3, j3, z + 3 - 2, thatchStair, 2);	// ^
        placeWithMetadata(world, x - 4, j3, z + 3 - 2, thatchStair, 2);	// ^

        place(world, x - 3, j3 + 1, z + 2, thatchSlab, 1);				//top center block

        //try
        place(world, x - 3, y, z + 4, 0);
        place(world, x - 3, y, z + 3, 0);
        place(world, x - 3, y, z + 2, 0);			//air pockets for fishin or something
        place(world, x - 3, y, z + 1, 0);
        placeWithMetadata(world, x - 3, y, z + 1, Block.ladder, 3);
        place(world, x - 3, y - 1, z, thatchBlock);
        placeWithMetadata(world, x - 3, y - 1, z + 1, Block.ladder, 3);
        //	for stairs, the metadata = the directions: 3= right, 2 = faces left, 0 faces toward base, 1 faces away from base
    }
}