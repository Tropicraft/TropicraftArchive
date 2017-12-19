package tropicraft.world.structures;

import net.minecraft.block.Block;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import tropicraft.blocks.TropicraftBlocks;

public class KoaVillageGenerator {

    private static final List<Integer> waterBlockIDs = Arrays.asList(Block.waterMoving.blockID, Block.waterStill.blockID, TropicraftBlocks.tropicsWaterFlowing.blockID, TropicraftBlocks.tropicsWaterStationary.blockID);
    private static final List<Integer> sandBlockIDs = Arrays.asList(Block.sand.blockID, TropicraftBlocks.purifiedSand.blockID);
    public static LinkedList<KoaBridgePiece> bridgePieceList;
    
    public KoaVillage village;

    public KoaVillageGenerator(World world) {
        village = new KoaVillage(world);
        bridgePieceList = new LinkedList<KoaBridgePiece>();
    }

    private void generateSexy(World world, Random random, int i, int j, int k, int dir) {
        int x = i;
        int y = j - 1;
        int z = k;
        Block thatchStair = TropicraftBlocks.thatchStairs;

        for (int a = 0; a < 6; a++) {
            
            int xOffset = random.nextInt(8) - random.nextInt(8);
            int zOffset = random.nextInt(8) - random.nextInt(8);
            int yLoc = y + 6;
            while (yLoc > y - 4 && world.getBlockId(x + xOffset, yLoc, z + zOffset) == 0) {
                yLoc--;
            }
            
            if (sandBlockIDs.contains(world.getBlockId(x + xOffset, yLoc, z + zOffset)) &&
                    world.getBlockId(x + xOffset, yLoc + 1, z + zOffset) == 0 && 
                    world.getBlockId(x + xOffset, yLoc + 2, z + zOffset) == 0 &&
                    world.getBlockId(x + xOffset, yLoc + 3, z + zOffset) == 0) {
                placeWithMetadata(world, x + xOffset, yLoc + 1, z + zOffset, TropicraftBlocks.tikiTorch, 1, false);
                placeWithMetadata(world, x + xOffset, yLoc + 2, z + zOffset, TropicraftBlocks.tikiTorch, 1, false);
                placeWithMetadata(world, x + xOffset, yLoc + 3, z + zOffset, TropicraftBlocks.tikiTorch, 0, false);
            }
        }

        switch (dir) {
            case 0:	//generate in -x
                placeWithMetadata(world, i - 1, j, k, thatchStair, 0);
                placeWithMetadata(world, i - 1, j, k + 1, thatchStair, 0);
                placeWithMetadata(world, i - 1, j, k - 1, thatchStair, 0);

                if (world.getBlockId(i - 2, j - 1, k) == 0 || world.getBlockId(i - 2, j - 1, k - 1) == 0 || world.getBlockId(i - 2, j - 1, k + 1) == 0) {
                    placeWithMetadata(world, i - 2, j - 1, k, thatchStair, 0);
                    placeWithMetadata(world, i - 2, j - 1, k + 1, thatchStair, 0);
                    placeWithMetadata(world, i - 2, j - 1, k - 1, thatchStair, 0);

                }

                break;
            case 1:	//generate in + x
                placeWithMetadata(world, i + 1, j, k, thatchStair, 1);
                placeWithMetadata(world, i + 1, j, k + 1, thatchStair, 1);
                placeWithMetadata(world, i + 1, j, k - 1, thatchStair, 1);

                if (world.getBlockId(i + 2, j - 1, k) == 0 || world.getBlockId(i + 2, j - 1, k - 1) == 0 || world.getBlockId(i + 2, j - 1, k + 1) == 0) {
                    placeWithMetadata(world, i + 2, j - 1, k, thatchStair, 1);
                    placeWithMetadata(world, i + 2, j - 1, k + 1, thatchStair, 1);
                    placeWithMetadata(world, i + 2, j - 1, k - 1, thatchStair, 1);

                }

                break;
            case 2:		//generate in -z

                placeWithMetadata(world, i, j, k - 1, thatchStair, 2);
                placeWithMetadata(world, i + 1, j, k - 1, thatchStair, 2);
                placeWithMetadata(world, i - 1, j, k - 1, thatchStair, 2);

                if (world.getBlockId(i, j - 1, k - 2) == 0 || world.getBlockId(i - 1, j - 1, k - 2) == 0 || world.getBlockId(i + 1, j - 1, k - 2) == 0) {
                    placeWithMetadata(world, i, j - 1, k - 2, thatchStair, 2);
                    placeWithMetadata(world, i - 1, j - 1, k - 2, thatchStair, 2);
                    placeWithMetadata(world, i + 1, j - 1, k - 2, thatchStair, 2);

                }

                break;
            case 3:		//generate in +z

                placeWithMetadata(world, i, j, k + 1, thatchStair, 3);
                placeWithMetadata(world, i + 1, j, k + 1, thatchStair, 3);
                placeWithMetadata(world, i - 1, j, k + 1, thatchStair, 3);

                if (world.getBlockId(i, j - 1, k - 2) == 0 || world.getBlockId(i - 1, j - 1, k - 2) == 0 || world.getBlockId(i + 1, j - 1, k - 2) == 0) {
                    placeWithMetadata(world, i, j - 1, k + 2, thatchStair, 3);
                    placeWithMetadata(world, i - 1, j - 1, k + 2, thatchStair, 3);
                    placeWithMetadata(world, i + 1, j - 1, k + 2, thatchStair, 3);

                }

                break;
            default:

                break;
        }
    }
    
    protected void placeWithMetadata(World world, int i, int j, int k, Block block, int meta) {
        placeWithMetadata(world, i, j, k, block, meta, true);
    }

    protected void placeWithMetadata(World world, int i, int j, int k, Block block, int meta, boolean notify) {
        if (notify) {
            world.setBlock(i, j, k, block.blockID, meta, 3);
        } else {
            world.setBlock(i, j, k, block.blockID, meta, 3);
        }
    }

    private boolean generateBridges(World world, Random random, int x, int y, int z, int dir, int length) {

        if (canSimulateBridge(world, x, y, z, dir, length)) {
            if (length > 50) {
                length = 43;		//do more fancy math
            }

            if (length > 40) {
                length -= 3;
            }

            if (length > 25) {
                length -= 4;
            }

            if (length < 25) {
                return false;
            }

            KoaVillageManager.villageMap.put(this.village.id, this.village);
            generateSexy(world, random, x, y, z, dir);

            length -= 5;

            generateBridge(world, random, x, y, z, dir, length);
            generateHuts(world, random);

            int maxAdditionalBridges = random.nextInt(2) + 2;
            if (dir == 0) {
                x += length + 1;
            } else if (dir == 1) {
                x -= length + 1;
            } else if (dir == 2) {
                z += length + 1;
            } else {
                z -= length + 1;
            }
            
            
            for (int segment = 0; segment < maxAdditionalBridges; segment++) {

                int[] pdirs = new int[2];		//possible new directions
                pdirs[0] = 0;
                pdirs[1] = 0;

                if (dir == 0 || dir == 1) {
                    pdirs[0] = 2;
                    pdirs[1] = 3;
                } else if (dir == 2 || dir == 3) {
                    pdirs[0] = 0;
                    pdirs[1] = 1;
                }

                int maxLength = -1;
                int newDir = -1;

                if (getMaxDistance(world, x, y, z, pdirs[0]) > maxLength) {
                    maxLength = getMaxDistance(world, x, y, z, pdirs[0]);
                    newDir = pdirs[0];
                }

                if (getMaxDistance(world, x, y, z, pdirs[1]) > maxLength) {
                    maxLength = getMaxDistance(world, x, y, z, pdirs[1]);
                    newDir = pdirs[1];
                }
                
                if (newDir == 0) {
                    x -= 1;
                } else if (newDir == 1) {
                    x += 1;
                } else if (newDir == 2) {
                    z -= 1;
                } else {
                    z += 1;
                }

                length = maxLength;

                if (length > 50) {
                    length = 43;		//do more fancy math
                }

                if (length > 30) {
                    length -= 8;
                }
                if (length > 15) {
                    length -= 4;
                }

                if (length < 10) {
                    continue;
                }

                length -= 3;

                if (canSimulateBridge(world, x, y, z, newDir, length)) {
                    generateBridge(world, random, x, y, z, newDir, length);
                    generateHuts(world, random);
                    dir = newDir;
                    if (dir == 0) {
                        x += length + 1;
                    } else if (dir == 1) {
                        x -= length + 1;
                    } else if (dir == 2) {
                        z += length + 1;
                    } else {
                        z -= length + 1;
                    }
                }
            }
            
            return true;

        } else {
            
            return false;
        }
    }

    private void generateHuts(World world, Random random) {

        for (KoaBridgePiece kbp : bridgePieceList) {
            if (kbp.canBuildHere) {
                kbp.canBuildHere = false;
                // float is the percentage of structures that will be wells
                if (random.nextFloat() < 0.4F) {
                    //KoaVillageWell kvm = (new KoaVillageWell(village, world, kbp.x, kbp.y, kbp.z, coorsOfAdjacentAir(world, kbp.x, kbp.y, kbp.z)[0], coorsOfAdjacentAir(world, kbp.x, kbp.y, kbp.z)[1], coorsOfAdjacentAir(world, kbp.x, kbp.y, kbp.z)[2], coorsOfAdjacentAir(world, kbp.x, kbp.y, kbp.z)[3]));
                    KoaVillageWell component = new KoaVillageWell(village,
                            coordsOfAdjacentAir(world, kbp.x, kbp.y, kbp.z)[0],
                            coordsOfAdjacentAir(world, kbp.x, kbp.y, kbp.z)[1],
                            coordsOfAdjacentAir(world, kbp.x, kbp.y, kbp.z)[2]);
                    if (component.canFitInWorld(world)) {
                        component.generate(world);
                    }
                } else {
                	if (/*true*/random.nextBoolean() && !village.hasPlacedTrader) {
                		KoaVillageShop component = new KoaVillageShop(village, 
                                coordsOfAdjacentAir(world, kbp.x, kbp.y, kbp.z)[0],
                                coordsOfAdjacentAir(world, kbp.x, kbp.y, kbp.z)[1],
                                coordsOfAdjacentAir(world, kbp.x, kbp.y, kbp.z)[2]);
                		
                        if (component.canFitInWorld(world)) {
                        	village.hasPlacedTrader = true;
                            component.generateSchematic(world);
                        }
                	} else {
                		KoaVillageHut component = new KoaVillageHut(village,
                        coordsOfAdjacentAir(world, kbp.x, kbp.y, kbp.z)[0],
                        coordsOfAdjacentAir(world, kbp.x, kbp.y, kbp.z)[1],
                        coordsOfAdjacentAir(world, kbp.x, kbp.y, kbp.z)[2]);
                		
                        if (component.canFitInWorld(world)) {
                            component.generate(world);
                            //lol
                            //component.generateSchematic(world);
                        }
                	}
                }
            }
        }

        for (ChunkCoordinates ksp : this.village.spawnPoints) {
            place(world, ksp.posX, ksp.posY, ksp.posZ, TropicraftBlocks.koaChest);
        }
    }


    private int[] coordsOfAdjacentAir(World world, int i, int j, int k) {
        int[] asdf = new int[4];

        if (world.getBlockId(i + 1, j, k) == 0) {
            asdf[0] = i + 1;
            asdf[1] = j;
            asdf[2] = k;
            asdf[3] = i + 1;
        } else if (world.getBlockId(i - 1, j, k) == 0) {
            asdf[0] = i - 1;
            asdf[1] = j;
            asdf[2] = k;
            asdf[3] = i - 1;
        } else if (world.getBlockId(i + 1, j, k + 1) == 0) {
            asdf[0] = i + 1;
            asdf[1] = j;
            asdf[2] = k + 1;
            asdf[3] = 0;
        } else if (world.getBlockId(i - 1, j, k - 1) == 0) {
            asdf[0] = i - 1;
            asdf[1] = j;
            asdf[2] = k - 1;
            asdf[3] = 0;
        } else if (world.getBlockId(i, j, k + 1) == 0) {
            asdf[0] = i;
            asdf[1] = j;
            asdf[2] = k + 1;
            asdf[3] = k + 1;
        } else if (world.getBlockId(i, j, k - 1) == 0) {
            asdf[0] = i;
            asdf[1] = j;
            asdf[2] = k - 1;
            asdf[3] = k - 1;
        } else if (world.getBlockId(i - 1, j, k + 1) == 0) {
            asdf[0] = i - 1;
            asdf[1] = j;
            asdf[2] = k + 1;
            asdf[3] = 0;
        } else if (world.getBlockId(i + 1, j, k - 1) == 0) {
            asdf[0] = i + 1;
            asdf[1] = j;
            asdf[2] = k - 1;
            asdf[3] = 0;
        }
        return asdf;
    }

    private void generateBridge(World world, Random random, int i, int j, int k, int dir, int length) {
        Block bridgebl = TropicraftBlocks.tropicsBuildingBlock;

        switch (dir) {
            case 0:
                for (int a = 0; a < length; a++) {
                    genBridgePiece(world, i + a, j, k, bridgebl);
                    genBridgePiece(world, i + a, j, k + 1, bridgebl, a, 0, length);
                    genBridgePiece(world, i + a, j, k - 1, bridgebl, a, 1, length);
                }
                break;
            case 1:
                for (int a = 0; a < length; a++) {
                    genBridgePiece(world, i - a, j, k, bridgebl);
                    genBridgePiece(world, i - a, j, k + 1, bridgebl, a, 0, length);
                    genBridgePiece(world, i - a, j, k - 1, bridgebl, a, 1, length);
                }
                break;
            case 2:
                for (int a = 0; a < length; a++) {
                    genBridgePiece(world, i, j, k + a, bridgebl);
                    genBridgePiece(world, i + 1, j, k + a, bridgebl, a, 0, length);
                    genBridgePiece(world, i - 1, j, k + a, bridgebl, a, 1, length);
                }
                break;
            case 3:
                for (int a = 0; a < length; a++) {
                    genBridgePiece(world, i, j, k - a, bridgebl);
                    genBridgePiece(world, i + 1, j, k - a, bridgebl, a, 0, length);
                    genBridgePiece(world, i - 1, j, k - a, bridgebl, a, 1, length);
                }
                break;
            default:
                break;
        }
        generateHuts(world, random);
    }

    private void genBridgePiece(World world, int i, int j, int k, Block block, int count, int decide, int totalLength) {
        if (decide == 0) {
            place(world, i, j, k, block);
            int r = world.rand.nextInt(1) + 14;
            bridgePieceList.add(new KoaBridgePiece(i, j, k, count % r == 0 && hasAdjacentAir(world, i, j, k) && count > 2 && count < totalLength - 4));
        } else {
            place(world, i, j, k, block);
            int r = world.rand.nextInt(1) + 11;
            bridgePieceList.add(new KoaBridgePiece(i, j, k, count % r == 0 && hasAdjacentAir(world, i, j, k) && count > 2 && count < totalLength - 4));
        }
    }

    private boolean hasAdjacentAir(World world, int i, int j, int k) {

        return world.getBlockId(i + 1, j, k) == 0 || world.getBlockId(i - 1, j, k) == 0 || world.getBlockId(i, j, k - 1) == 0
                || world.getBlockId(i, j, k + 1) == 0 || world.getBlockId(i + 1, j, k + 1) == 0 || world.getBlockId(i + 1, j, k - 1) == 0
                || world.getBlockId(i - 1, j, k - 1) == 0 || world.getBlockId(i - 1, j, k + 1) == 0;
    }

    private void genBridgePiece(World world, int i, int j, int k, Block block) {
        place(world, i, j, k, block);
        bridgePieceList.add(new KoaBridgePiece(i, j, k, false));
    }

    private boolean canSimulateBridge(World world, int i, int j, int k, int dir, int length) {
        switch (dir) {
            case 0:
                for (int a = 0; a < length; a++) {
                    if (world.getBlockId(i + a, j, k) != 0 ||
                            world.getBlockId(i + a, j, k + 1) != 0 ||
                            world.getBlockId(i + a, j, k - 1) != 0) {
                        return false;
                    }
                }
                break;
            case 1:
                for (int a = 0; a < length; a++) {
                    if (world.getBlockId(i - a, j, k) != 0 ||
                            world.getBlockId(i - a, j, k + 1) != 0 ||
                            world.getBlockId(i - a, j, k - 1) != 0) {
                        return false;
                    }
                }
                break;
            case 2:
                for (int a = 0; a < length; a++) {
                    if (world.getBlockId(i, j, k + a) != 0 ||
                            world.getBlockId(i + 1, j, k + a) != 0 ||
                            world.getBlockId(i - 1, j, k + a) != 0) {
                        return false;
                    }
                }
                break;
            case 3:
                for (int a = 0; a < length; a++) {
                    if (world.getBlockId(i, j, k - a) != 0 ||
                            world.getBlockId(i + 1, j, k - a) != 0 ||
                            world.getBlockId(i - 1, j, k - a) != 0) {
                        return false;
                    }
                }
                break;
            default:
                break;
        }
        
        return true;
    }
    
    private void place(World world, int i, int j, int k, Block block) {
        place(world, i, j, k, block, true);
    }

    private void place(World world, int i, int j, int k, Block block, boolean notify) {
        if (notify) {
            world.setBlock(i, j, k, block.blockID);
        } else {
            world.setBlock(i, j, k, block.blockID);
        }
    }

    public boolean generate(World world, Random random, int i, int j, int k) {
        int genDirection = canGenerateStart(world, random, i, j - 1, k);
        return generate(world, random, i, j, k, genDirection);
    }

    public boolean generate(World world, Random random, int i, int j, int k, int direction) {

        if (direction != -1 && sandBlockIDs.contains(world.getBlockId(i, j - 1, k))) {

        	//System.out.println("aw yis " + i + " " + j + " " + k);
        	
            int idir = direction;
            int dist = getMaxDistance(world, i, j + 1, k, idir);

            boolean generated = generateBridges(world, random, i, j + 1, k, idir, dist);
            if (generated) {
                village.spawnEntities(world);
            }
            return generated;
        }

        return false;
    }

    private int canGenerateStart(World world, Random random, int i, int j, int k) {

        int dir = -1;

        boolean[] adjacentWater = new boolean[4];

        adjacentWater[0] = waterBlockIDs.contains(world.getBlockId(i + 1, j, k));
        adjacentWater[1] = waterBlockIDs.contains(world.getBlockId(i - 1, j, k));
        adjacentWater[2] = waterBlockIDs.contains(world.getBlockId(i, j, k + 1));
        adjacentWater[3] = waterBlockIDs.contains(world.getBlockId(i, j, k - 1));

        int numTrue = 0;

        for (int b = 0; b < 4; b++) {
            if (adjacentWater[b]) {
                numTrue++;
                dir = b;
            }
        }

        if (numTrue > 1) {
            return -1;
        } else if (numTrue == 0) {
            return -1;
        } else {
            return dir;
        }
    }

    private int getMaxDistance(World world, int i, int j, int k, int direction) {
        int max = 0;
        int id = TropicraftBlocks.tropicsBuildingBlock.blockID;

        switch (direction) {
            case 0:
                for (int a = 1; a < 80; a++) {
                    if (!waterBlockIDs.contains(world.getBlockId(i + a, j - 2, k)) && (world.getBlockId(i + a, j, k) == 0 || world.getBlockId(i + a, j, k) == id)) {
                        return a;
                    } else {
                        max++;
                    }
                }

                break;
            case 1:
                for (int a = 1; a < 80; a++) {
                    if (!waterBlockIDs.contains(world.getBlockId(i - a, j - 2, k)) && (world.getBlockId(i - a, j, k) == 0 || world.getBlockId(i - a, j, k) == id)) {
                        return a;
                    } else {
                        max++;
                    }
                }
                break;
            case 2:
                for (int a = 1; a < 80; a++) {
                    if (!waterBlockIDs.contains(world.getBlockId(i, j - 2, k + a)) && (world.getBlockId(i, j, k + a) == 0 || world.getBlockId(i, j, k + a) == id)) {
                        return a;
                    } else {
                        max++;
                    }
                }
                break;
            case 3:
                for (int a = 1; a < 80; a++) {
                    if (!waterBlockIDs.contains(world.getBlockId(i, j - 2, k - a)) && (world.getBlockId(i, j, k - a) == 0 || world.getBlockId(i, j, k - a) == id)) {
                        return a;
                    } else {
                        max++;
                    }
                }
                break;
            default:

                break;
        }

        if (max >= 79) {
            return 50;
        } else {
            return -1;
        }
    }

}