package tropicraft.world.structures;

import java.io.File;

import build.BuildServerTicks;
import build.world.BuildJob;
import tropicraft.ai.WorldDirector;
import tropicraft.blocks.TropicraftBlocks;
import tropicraft.world.schematic.CustomGenKoaHut;
import net.minecraft.block.Block;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

public class KoaVillageHut extends KoaVillageComponent {
    /*
     * int direction, and it's meanings
     *            ^ 0
     *            |
     *            |
     *            |
     * 3 <----------------> 1
     *            |
     *            |
     *            |
     *            v 2
     */

    private static final int bridgeBlockID = TropicraftBlocks.tropicsBuildingBlock.blockID;
    
    private Block thatchBlock = TropicraftBlocks.tropicsBuildingBlock;
    private Block bambooFence = TropicraftBlocks.tropicsFence;
    private Block palmWood = TropicraftBlocks.treeWood;
    private Block bambooBlock = TropicraftBlocks.bambooBundle;
    private Block thatchSlab = TropicraftBlocks.tropicsSingleSlab;
    private Block thatchStair = TropicraftBlocks.thatchStairs;
    private Block tikiTorch = TropicraftBlocks.tikiTorch;

    public KoaVillageHut(KoaVillage village, int x, int y, int z) {
        this.village = village;
        homeX = x;
        homeY = y;
        homeZ = z;
    }
    
    @Override
    public boolean canFitInWorld(World world) {
        
        if (world.getBlockId(homeX + 1, homeY, homeZ) == bridgeBlockID) {
            for (int x = -7; x < 1; x++) {
                for (int z = -4; z < 6; z++) {
                    if (world.getBlockId(homeX + x, homeY, homeZ + z) != 0) {
                        return false;
                    }
                }
            }
            genX = homeX;
            genY = homeY;
            genZ = homeZ;
            genDirection = 2;
            
        } else if (world.getBlockId(homeX - 1, homeY, homeZ) == bridgeBlockID) {
            for (int x = 0; x < 8; x++) {
                for (int z = -4; z < 6; z++) {
                    if (world.getBlockId(homeX + x, homeY, homeZ + z) != 0) {
                        return false;
                    }
                }
            }

            genX = homeX;
            genY = homeY;
            genZ = homeZ;
            genDirection = 1;
            
        } else if (world.getBlockId(homeX, homeY, homeZ + 1) == bridgeBlockID) {
            for (int x = -5; x < 4; x++) {
                for (int z = -6; z < 1; z++) {
                    if (world.getBlockId(homeX + x, homeY, homeZ + z) != 0) {
                        return false;
                    }
                }
            }

            genX = homeX;
            genY = homeY;
            genZ = homeZ;
            genDirection = 3;

        } else if (world.getBlockId(homeX, homeY, homeZ - 1) == bridgeBlockID) {
            for (int x = -5; x < 4; x++) {
                for (int z = 0; z < 6; z++) {
                    if (world.getBlockId(homeX + x, homeY, homeZ + z) != 0) {
                        return false;
                    }
                }
            }
            genX = homeX;
            genY = homeY;
            genZ = homeZ;
            genDirection = 4;
        }
        
        return true;
    }

    @Override
    public void spawn(World world) {
    
    }
    
    public void generateSchematic(World world) {
		if (world.isRemote) return;
		
		int x = homeX;
		int y = homeY;
		int z = homeZ;
		
		//adjust his starting 1 to my starting 0
		genDirection--;
		
		//notes
		//keep in mind wrong side and centering throws that off
		//1 is right
		//0 is wrong
		
		//System.out.println("genDirection: " + genDirection + ", " + x + ", " + y + ", " + z);
		
		if (genDirection == 0) {
			genDirection = 2;
		} else if (genDirection == 2) {
			genDirection = 3;
		} else if (genDirection == 3) {
			genDirection = 1;
		} else if (genDirection == 1) {
			genDirection = 0;
		}
		
		//System.out.println("fixed? genDirection: " + genDirection + ", " + x + ", " + y + ", " + z);
				
		int buildID = BuildServerTicks.buildMan.activeBuilds.size();
		String str = "TradeHut";
    	BuildJob bj = new BuildJob(buildID, homeX, homeY, homeZ, WorldDirector.getSaveFolderPath() + "TCSchematics" + File.separator + str);
		bj.build.dim = world.provider.dimensionId;
		bj.useFirstPass = false; //skip air setting pass
		bj.useRotationBuild = true;
		bj.build.newFormat = true;
		bj.customGenCallback = new CustomGenKoaHut();
		
		//schematic orientation expectations:
		//minX,minZ -> maxX,minZ is the front face
		//this is what would be adjusted for schematics that do not use the above rule 
		int offset = 0;
		bj.setDirection(genDirection);
		//bj.rotation = (genDirection * 90) + 180 + offset;
		
		//System.out.println(bj.rotation);
		
		BuildServerTicks.buildMan.addBuild(bj);
	}
    
    //private void generateZeHut(World world, int i, int j, int k, int i2, int j2, int k2) {
    @Override
    public void generate(World world) {
        
        if (genDirection == 1) {
            genHutOne(world, genX, genY, genZ);
        } else if (genDirection == 2) {
            genHutTwo(world, genX, genY, genZ);
        } else if (genDirection == 3) {
            genHutThree(world, genX, genY, genZ);
        } else if (genDirection == 4) {
            genHutFour(world, genX, genY, genZ);
        }
        /*
        int id = TropicraftBlocks.thatchBl.blockID;
        int iDiff = i2 - i;
        int kDiff = k2 - k;

//			spawn(world, homeX, homeY, homeZ);

        if (world.getBlockId(i2 + 1, j2, k2) == id || world.getBlockId(i2 - 1, j2, k2) == id) {
            if (world.getBlockId(i2 + 1, j2, k2) == id) {
                for (int x = -7; x < 1; x++) {
                    for (int z = -4; z < 6; z++) {
                        if (world.getBlockId(i2 + x, j2, k2 + z) != 0) {
                            //		return;
                        }
                    }
                }

                genHutTwo(world, i2, j2, k2);				// i - 7 --> i + 1 // k - 4 ==> k + 5
            } else if (world.getBlockId(i2 - 1, j2, k2) == id) {
                for (int x = 0; x < 8; x++) {
                    for (int z = -4; z < 6; z++) {
                        if (world.getBlockId(i2 + x, j2, k2 + z) != 0) {
                            //		return;
                        }
                    }
                }

                genHutOne(world, i2, j2, k2);		//correct // i - 1 --> i + 7 // k - 4 ==> k + 5
            }

        } else if (world.getBlockId(i2, j2, k2 + 1) == id || world.getBlockId(i2, j2, k2 - 1) == id) {
            if (world.getBlockId(i2, j2, k2 + 1) == id) {
                for (int x = -5; x < 4; x++) {
                    for (int z = -6; z < 1; z++) {
                        if (world.getBlockId(i2 + x, j2, k2 + z) != 0) {
                            //		return;
                        }
                    }
                }

                genHutThree(world, i2, j2, k2); // i - 5 --> i + 3 // k - 6 --> k + 1
            } else if (world.getBlockId(i2, j2, k2 - 1) == id) {
                for (int x = -5; x < 4; x++) {
                    for (int z = 0; z < 6; z++) {
                        if (world.getBlockId(i2 + x, j2, k2 + z) != 0) {
                            //		return;
                        }
                    }
                }
                genHutFour(world, i2, j2, k2);  // i - 5 --> i + 3 // k - 1 --> k + 6
            }
        }
        * 
        */
    }

    private void genHutOne(World world, int x, int y, int z) {
        boolean setSpawn = false;
        for (int a = 0; a < 2; a++) {
            for (int b = 0; b < 2; b++) {
                place(world, x + a, y, z + b, thatchBlock);			//this is the entrance steps
            }
        }

        for (int a = 0; a < 5; a++) {
            for (int b = 0; b < 8; b++) {
                if (a == 2 && b == 2) {
                    this.village.spawnPoints.add(new ChunkCoordinates(x + a + 2, y + 1, z + b - 3));
                }

                place(world, x + a + 2, y, z + b - 3, thatchBlock);		//this is the base
                
                if ((a == 0 || a == 4) && (b == 0 || b == 7)) {
                    placeDownTilGround(world, x + a + 2, y - 1, z + b - 3, bambooFence);  // posts under base corners
                }
            }
        }

        for (int a = 0; a < 3; a++) {
            place(world, x + 3 + a, y + 1, z - 3, bambooBlock);			//walls //side walls these top two be
            place(world, x + 3 + a, y + 1, z + 4, bambooBlock);
            place(world, x + 3 + a, y + 3, z - 3, bambooBlock);			//walls //side walls these top two be
            place(world, x + 3 + a, y + 3, z + 4, bambooBlock);
            place(world, x + 3 + a, y + 4, z - 3, palmWood);			//walls //side walls these top two be
            place(world, x + 3 + a, y + 4, z + 4, palmWood);
            place(world, x + 3 + a, y + 5, z - 3, thatchBlock);			//walls //side walls these top two be
            place(world, x + 3 + a, y + 5, z + 4, thatchBlock);

            place(world, x + 3 + a, y + 5, z - 4, thatchSlab, 1);			//shingles sides 1
            place(world, x + 3 + a, y + 5, z + 5, thatchSlab, 1);

            place(world, x + 3 + a, y + 6, z - 2, thatchSlab, 1);		//up another level of shinglez
            place(world, x + 3 + a, y + 6, z + 3, thatchSlab, 1);
        }

        placeWithMetadata(world, x + 4, y + 6, z - 2, thatchStair, 2);			//some stairs
        placeWithMetadata(world, x + 4, y + 6, z + 3, thatchStair, 3);			//some stairs

        for (int a = 0; a < 4; a++) {
            placeWithMetadata(world, x + 5, y + 6, z - 2 + a + 1, thatchStair, 1);			//some side stairs
            placeWithMetadata(world, x + 3, y + 6, z - 2 + a + 1, thatchStair, 0);			//some MORE side stairs
        }

        place(world, x + 4, y + 7, z + 2, thatchSlab, 1);		//rooftop slabs :>
        place(world, x + 4, y + 7, z - 1, thatchSlab, 1);		//rooftop slabs :>
        place(world, x + 4, y + 7, z + 1, thatchSlab, 1);		//rooftop slabs :>
        place(world, x + 4, y + 7, z, thatchSlab, 1);			//rooftop slabs :>

        for (int a = 0; a < 7; a++) {
            place(world, x + 6, y + 1, z - 3 + a, bambooBlock);		//back wall bottom
            place(world, x + 6, y + 3, z - 3 + a, bambooBlock);		//back wall bottom
            place(world, x + 2, y + 3, z - 3 + a, bambooBlock);		//front top wall
            place(world, x + 6, y + 4, z - 3 + a, palmWood);		//back wall bottom
            place(world, x + 2, y + 4, z - 3 + a, palmWood);		//front top wall
            place(world, x + 6, y + 5, z - 3 + a, thatchBlock);		//back wall bottom
            place(world, x + 2, y + 5, z - 3 + a, thatchBlock);		//front top wall

            if (a != 3 && a != 4) {
                place(world, x + 2, y + 1, z - 3 + a, bambooBlock);		//front "wall" bottom
            }
        }

        place(world, x - 1, y + 1, z - 1, bambooFence);			//posts on the dock/bridge
        place(world, x - 1, y + 1, z + 2, bambooFence);
        place(world, x - 1, y + 2, z - 1, tikiTorch, 0);			//posts on the dock/bridge
        place(world, x - 1, y + 2, z + 2, tikiTorch, 0);

        place(world, x + 2, y + 2, z - 1, palmWood);			//palm wood
        place(world, x + 2, y + 2, z + 2, palmWood);

        //placeDownTilGround(world, i - 1, j - 1, k - 1, bambooFence);
        //placeDownTilGround(world, i - 1, j - 1, k + 2, bambooFence);

        for (int a = 0; a < 4; a++) {
            place(world, x + 2, y + 1 + a, z - 3, palmWood);
            place(world, x + 2, y + 1 + a, z + 4, palmWood);
            place(world, x + 6, y + 1 + a, z - 3, palmWood);		//these are the posts
            place(world, x + 6, y + 1 + a, z + 4, palmWood);
            if (a == 3) {
                place(world, x + 2, y + 2 + a, z - 3, thatchBlock);
                place(world, x + 2, y + 2 + a, z + 4, thatchBlock);
                place(world, x + 6, y + 2 + a, z - 3, thatchBlock);		//thatching ish
                place(world, x + 6, y + 2 + a, z + 4, thatchBlock);
                place(world, x + 2, y + 2 + a, z - 4, thatchSlab, 1);
                place(world, x + 2, y + 2 + a, z + 5, thatchSlab, 1);
                place(world, x + 6, y + 2 + a, z - 4, thatchSlab, 1);		//thatching ish
                place(world, x + 6, y + 2 + a, z + 5, thatchSlab, 1);
            }
        }

        for (int a = 0; a < 10; a++) {
            place(world, x + 1, y + 5, z - 4 + a, thatchSlab, 1);
            place(world, x + 7, y + 5, z - 4 + a, thatchSlab, 1);	//front and back first row of roof slabs...this will take quite a long time <_<
        }

    }

    private void genHutTwo(World world, int x, int y, int z) {
        for (int a = 0; a < 2; a++) {
            for (int b = 0; b < 2; b++) {
                place(world, x - a, y, z + b, thatchBlock);			//this is the entrance steps
            }
        }

        for (int a = 0; a < 5; a++) {
            for (int b = 0; b < 8; b++) {
                if (a == 2 && b == 2) {
                    this.village.spawnPoints.add(new ChunkCoordinates(x - a - 2, y + 1, z + b - 3));
                }

                place(world, x - a - 2, y, z + b - 3, thatchBlock);		//this is the base
                if ((a == 0 || a == 4) && (b == 0 || b == 7)) {
                    placeDownTilGround(world, x - a - 2, y - 1, z + b - 3, bambooFence); // posts under base corners
                }
            }
        }

        for (int a = 0; a < 3; a++) {
            place(world, x - 3 - a, y + 1, z - 3, bambooBlock);			//walls //side walls these top two be
            place(world, x - 3 - a, y + 1, z + 4, bambooBlock);
            place(world, x - 3 - a, y + 3, z - 3, bambooBlock);			//walls //side walls these top two be
            place(world, x - 3 - a, y + 3, z + 4, bambooBlock);
            place(world, x - 3 - a, y + 4, z - 3, palmWood);			//walls //side walls these top two be
            place(world, x - 3 - a, y + 4, z + 4, palmWood);
            place(world, x - 3 - a, y + 5, z - 3, thatchBlock);			//walls //side walls these top two be
            place(world, x - 3 - a, y + 5, z + 4, thatchBlock);

            place(world, x - 3 - a, y + 5, z - 4, thatchSlab, 1);			//shingles sides 1
            place(world, x - 3 - a, y + 5, z + 5, thatchSlab, 1);

            place(world, x - 3 - a, y + 6, z - 2, thatchSlab, 1);		//up another level of shinglez
            place(world, x - 3 - a, y + 6, z + 3, thatchSlab, 1);
        }

        placeWithMetadata(world, x - 4, y + 6, z - 2, thatchStair, 2);			//some stairs
        placeWithMetadata(world, x - 4, y + 6, z + 3, thatchStair, 3);			//some stairs

        for (int a = 0; a < 4; a++) {
            placeWithMetadata(world, x - 5, y + 6, z - 2 + a + 1, thatchStair, 0);			//some side stairs
            placeWithMetadata(world, x - 3, y + 6, z - 2 + a + 1, thatchStair, 1);			//some MORE side stairs
        }

        place(world, x - 4, y + 7, z + 2, thatchSlab, 1);		//rooftop slabs :>
        place(world, x - 4, y + 7, z - 1, thatchSlab, 1);		//rooftop slabs :>
        place(world, x - 4, y + 7, z + 1, thatchSlab, 1);		//rooftop slabs :>
        place(world, x - 4, y + 7, z, thatchSlab, 1);			//rooftop slabs :>

        for (int a = 0; a < 7; a++) {
            place(world, x - 6, y + 1, z - 3 + a, bambooBlock);		//back wall bottom
            place(world, x - 6, y + 3, z - 3 + a, bambooBlock);		//back wall bottom
            place(world, x - 2, y + 3, z - 3 + a, bambooBlock);		//front top wall
            place(world, x - 6, y + 4, z - 3 + a, palmWood);		//back wall bottom
            place(world, x - 2, y + 4, z - 3 + a, palmWood);		//front top wall
            place(world, x - 6, y + 5, z - 3 + a, thatchBlock);		//back wall bottom
            place(world, x - 2, y + 5, z - 3 + a, thatchBlock);		//front top wall

            if (a != 3 && a != 4) {
                place(world, x - 2, y + 1, z - 3 + a, bambooBlock);		//front "wall" bottom
            }
        }

        place(world, x + 1, y + 1, z - 1, bambooFence);			//posts on the dock/bridge
        place(world, x + 1, y + 1, z + 2, bambooFence);
        place(world, x + 1, y + 2, z - 1, tikiTorch, 0);			//posts on the dock/bridge
        place(world, x + 1, y + 2, z + 2, tikiTorch, 0);

        place(world, x - 2, y + 2, z - 1, palmWood);			//palm wood
        place(world, x - 2, y + 2, z + 2, palmWood);

        //placeDownTilGround(world, x + 1, y - 1, z - 1, bambooFence);
        //placeDownTilGround(world, x + 1, y - 1, z + 2, bambooFence);

        for (int a = 0; a < 4; a++) {
            place(world, x - 2, y + 1 + a, z - 3, palmWood);
            place(world, x - 2, y + 1 + a, z + 4, palmWood);
            place(world, x - 6, y + 1 + a, z - 3, palmWood);		//these are the posts
            place(world, x - 6, y + 1 + a, z + 4, palmWood);
            if (a == 3) {
                place(world, x - 2, y + 2 + a, z - 3, thatchBlock);
                place(world, x - 2, y + 2 + a, z + 4, thatchBlock);
                place(world, x - 6, y + 2 + a, z - 3, thatchBlock);		//thatching ish
                place(world, x - 6, y + 2 + a, z + 4, thatchBlock);
                place(world, x - 2, y + 2 + a, z - 4, thatchSlab, 1);
                place(world, x - 2, y + 2 + a, z + 5, thatchSlab, 1);
                place(world, x - 6, y + 2 + a, z - 4, thatchSlab, 1);		//thatching ish
                place(world, x - 6, y + 2 + a, z + 5, thatchSlab, 1);
            }
        }

        for (int a = 0; a < 10; a++) {
            place(world, x - 1, y + 5, z - 4 + a, thatchSlab, 1);
            place(world, x - 7, y + 5, z - 4 + a, thatchSlab, 1);	//front and back first row of roof slabs...this will take quite a long time <_<
        }

    }

    private void genHutThree(World world, int x, int y, int z) {
        for (int a = 0; a < 2; a++) {
            for (int b = 0; b < 2; b++) {
                place(world, x - b, y, z - a, thatchBlock);			//this is the entrance steps
            }
        }

        place(world, x - 2, y + 1, z + 1, bambooFence);
        place(world, x + 1, y + 1, z + 1, bambooFence);		//posts
        place(world, x - 2, y + 2, z + 1, tikiTorch, 0);		//torches
        place(world, x + 1, y + 2, z + 1, tikiTorch, 0);
        //placeDownTilGround(world, x - 2, y - 1, z + 1, bambooFence);
        //placeDownTilGround(world, x + 1, y - 1, z + 1, bambooFence);		//postststst

        for (int a = 0; a < 8; a++) {
            for (int b = 0; b < 5; b++) {
                if (a == 2 && b == 2) {
                    this.village.spawnPoints.add(new ChunkCoordinates(x - a + 3, y + 1, z - b - 2));
                }

                place(world, x - a + 3, y, z - b - 2, thatchBlock);		//this is the base		//x r side to side
                if ((a == 0 || a == 7) && (b == 0 || b == 4)) {
                    placeDownTilGround(world, x - a + 3, y - 1, z - b - 2, bambooFence); // posts under base corners
                }
            }
        }

        for (int a = 0; a < 3; a++) //side wall stuffs
        {
            place(world, x - 4, y + 1, z - a - 3, bambooBlock);
            place(world, x + 3, y + 1, z - a - 3, bambooBlock);
            place(world, x - 4, y + 3, z - a - 3, bambooBlock);
            place(world, x + 3, y + 3, z - a - 3, bambooBlock);
            place(world, x - 4, y + 4, z - a - 3, palmWood);
            place(world, x + 3, y + 4, z - a - 3, palmWood);

            place(world, x + 3, y + 5, z - a - 3, thatchBlock);
            place(world, x - 4, y + 5, z - a - 3, thatchBlock);
        }

        for (int a = 0; a < 6; a++) {
            place(world, x - 3 + a, y + 1, z - 6, bambooBlock);
            place(world, x - 3 + a, y + 3, z - 6, bambooBlock);			//these 3 are back walls
            place(world, x - 3 + a, y + 4, z - 6, palmWood);
            place(world, x - 3 + a, y + 5, z - 6, thatchBlock);


            if (a != 2 && a != 3) {
                place(world, x - 3 + a, y + 1, z - 2, bambooBlock);
            }
            place(world, x - 3 + a, y + 3, z - 2, bambooBlock);			//these 3 are front walls
            place(world, x - 3 + a, y + 4, z - 2, palmWood);
            place(world, x - 3 + a, y + 5, z - 2, thatchBlock);
        }

        place(world, x - 3 + 1, y + 2, z - 2, palmWood);
        place(world, x - 3 + 4, y + 2, z - 2, palmWood);


        for (int a = 0; a < 5; a++) {
            if (a < 4) {
                place(world, x - 4, y + 1 + a, z - 2, palmWood);
                place(world, x + 3, y + 1 + a, z - 2, palmWood);
                place(world, x - 4, y + 1 + a, z - 6, palmWood);
                place(world, x + 3, y + 1 + a, z - 6, palmWood);
            } else if (a == 4) {
                place(world, x - 4, y + 1 + a, z - 2, thatchBlock);
                place(world, x + 3, y + 1 + a, z - 2, thatchBlock);
                place(world, x - 4, y + 1 + a, z - 6, thatchBlock);
                place(world, x + 3, y + 1 + a, z - 6, thatchBlock);
            }
        }

        for (int a = 0; a < 7; a++) {
            place(world, x - 5, y + 5, z - 1 - a, thatchSlab, 1);
            place(world, x + 4, y + 5, z - 1 - a, thatchSlab, 1);
        }

        for (int a = 0; a < 8; a++) {
            place(world, x - 4 + a, y + 5, z - 1, thatchSlab, 1);
            place(world, x - 4 + a, y + 5, z - 7, thatchSlab, 1);	//outer rim roof slabbers
        }

        placeWithMetadata(world, x - 3, y + 6, z - 4, thatchStair, 0);
        placeWithMetadata(world, x + 2, y + 6, z - 4, thatchStair, 1);

        for (int a = 0; a < 6; a++) {
            if (a == 0 || a == 5) {
                place(world, x - 3 + a, y + 6, z - 3, thatchSlab, 1);
                place(world, x - 3 + a, y + 6, z - 5, thatchSlab, 1);
            } else {
                placeWithMetadata(world, x - 3 + a, y + 6, z - 3, thatchStair, 3);
                placeWithMetadata(world, x - 3 + a, y + 6, z - 5, thatchStair, 2);
            }
        }

        for (int a = 0; a < 4; a++) {
            place(world, x - 2 + a, y + 7, z - 4, thatchSlab, 1);
        }


    }

    private void genHutFour(World world, int x, int y, int z) {
        for (int a = 0; a < 2; a++) {
            for (int b = 0; b < 2; b++) {
                place(world, x - b, y, z + a, thatchBlock);			//this is the entrance steps
            }
        }

        place(world, x - 2, y + 1, z - 1, bambooFence);
        place(world, x + 1, y + 1, z - 1, bambooFence);		//posts
        place(world, x - 2, y + 2, z - 1, tikiTorch, 0);		//torches
        place(world, x + 1, y + 2, z - 1, tikiTorch, 0);
        //placeDownTilGround(world, x - 2, y - 1, z - 1, bambooFence);
        //placeDownTilGround(world, x + 1, y - 1, z - 1, bambooFence);		//postststst

        for (int a = 0; a < 8; a++) {
            for (int b = 0; b < 5; b++) {
                if (a == 2 && b == 2) {
                    this.village.spawnPoints.add(new ChunkCoordinates(x - a + 3, y + 1, z + b + 2));
                }

                place(world, x - a + 3, y, z + b + 2, thatchBlock);		//this is the base		//x r side to side
                if ((a == 0 || a == 7) && (b == 0 || b == 4)) {
                    placeDownTilGround(world, x - a + 3, y - 1, z + b + 2, bambooFence); // posts under base corners
                }
            }
        }

        for (int a = 0; a < 3; a++) //side wall stuffs
        {
            place(world, x - 4, y + 1, z + a + 3, bambooBlock);
            place(world, x + 3, y + 1, z + a + 3, bambooBlock);
            place(world, x - 4, y + 3, z + a + 3, bambooBlock);
            place(world, x + 3, y + 3, z + a + 3, bambooBlock);
            place(world, x - 4, y + 4, z + a + 3, palmWood);
            place(world, x + 3, y + 4, z + a + 3, palmWood);

            place(world, x + 3, y + 5, z + a + 3, thatchBlock);
            place(world, x - 4, y + 5, z + a + 3, thatchBlock);
        }

        for (int a = 0; a < 6; a++) {
            place(world, x - 3 + a, y + 1, z + 6, bambooBlock);
            place(world, x - 3 + a, y + 3, z + 6, bambooBlock);			//these 3 are back walls
            place(world, x - 3 + a, y + 4, z + 6, palmWood);
            place(world, x - 3 + a, y + 5, z + 6, thatchBlock);


            if (a != 2 && a != 3) {
                place(world, x - 3 + a, y + 1, z + 2, bambooBlock);
            }
            place(world, x - 3 + a, y + 3, z + 2, bambooBlock);			//these 3 are front walls
            place(world, x - 3 + a, y + 4, z + 2, palmWood);
            place(world, x - 3 + a, y + 5, z + 2, thatchBlock);
        }

        place(world, x - 3 + 1, y + 2, z + 2, palmWood);
        place(world, x - 3 + 4, y + 2, z + 2, palmWood);


        for (int a = 0; a < 5; a++) {
            if (a < 4) {
                place(world, x - 4, y + 1 + a, z + 2, palmWood);
                place(world, x + 3, y + 1 + a, z + 2, palmWood);
                place(world, x - 4, y + 1 + a, z + 6, palmWood);
                place(world, x + 3, y + 1 + a, z + 6, palmWood);
            } else if (a == 4) {
                place(world, x - 4, y + 1 + a, z + 2, thatchBlock);
                place(world, x + 3, y + 1 + a, z + 2, thatchBlock);
                place(world, x - 4, y + 1 + a, z + 6, thatchBlock);
                place(world, x + 3, y + 1 + a, z + 6, thatchBlock);
            }
        }

        for (int a = 0; a < 7; a++) {
            place(world, x - 5, y + 5, z + 1 + a, thatchSlab, 1);
            place(world, x + 4, y + 5, z + 1 + a, thatchSlab, 1);
        }

        for (int a = 0; a < 8; a++) {
            place(world, x - 4 + a, y + 5, z + 1, thatchSlab, 1);
            place(world, x - 4 + a, y + 5, z + 7, thatchSlab, 1);	//outer rim roof slabbers
        }

        placeWithMetadata(world, x - 3, y + 6, z + 4, thatchStair, 0);
        placeWithMetadata(world, x + 2, y + 6, z + 4, thatchStair, 1);

        for (int a = 0; a < 6; a++) {
            if (a == 0 || a == 5) {
                place(world, x - 3 + a, y + 6, z + 3, thatchSlab, 1);
                place(world, x - 3 + a, y + 6, z + 5, thatchSlab, 1);
            } else {
                placeWithMetadata(world, x - 3 + a, y + 6, z + 3, thatchStair, 2);
                placeWithMetadata(world, x - 3 + a, y + 6, z + 5, thatchStair, 3);
            }
        }

        for (int a = 0; a < 4; a++) {
            place(world, x - 2 + a, y + 7, z + 4, thatchSlab, 1);
        }

    }
}
