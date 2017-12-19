package net.tropicraft.world.structures;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.tropicraft.entities.EntityHangingTropicraft;
import net.tropicraft.entities.EntityKoaManly;
import net.tropicraft.mods.TropicraftMod;
import CoroAI.entity.EnumJob;

public class KoaVillageShop extends KoaVillageComponent {

	private static final int bridgeBlockID = TropicraftMod.thatchBlock.blockID;
	
    private Block thatchBlock = TropicraftMod.thatchBlock;
    private Block bambooFence = TropicraftMod.bambooFence;
    private Block palmWood = TropicraftMod.tropicalWood;
    private Block bambooBlock = TropicraftMod.bambooBlock;
    private Block thatchSlab = TropicraftMod.tropicalSingleSlab;
    private Block thatchStair = TropicraftMod.thatchStairs;
    private Block tikiTorch = TropicraftMod.tikiTorch;
	
	public KoaVillageShop(KoaVillage village, int x, int y, int z) {
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
	public void generate(World world) {
		if (world.isRemote) return;
		
		int x = homeX;
		int y = homeY;
		int z = homeZ;
		//this is the entrance steps
        for (int a = 0; a < 2; a++) {
            for (int b = 0; b < 2; b++) {
                place(world, x + a, y, z + b, thatchBlock);			
            }
        }		
        
        for (int a = 0; a < 5; a++) {
            for (int b = 0; b < 8; b++) {
                if (a == 2 && b == 2) {
                 //   this.village.spawnPoints.add(new ChunkCoordinates(x + a + 2, y + 1, z + b - 3));
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
            place(world, x + 6, y + 2, z - 3 + a, bambooBlock);		//back wall bottom - new for shops
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
        
        //shop row in middle of hut, perhaps do spawning here Corosus? 8>
        for (int a = 2; a < 6; a++) {
    	    place(world, x + 4, y + 1, z - 3 + a, bambooBlock);
    	    
    	    if (a == 4)	//replace trade block thingy here, note this is when a == 4
    	    	place(world, x + 4, y + 2, z - 3 + a, Block.pressurePlateStone);
    	    
    	    placeShopFrame(world, x + 6, y + 2, z - 3 + a);
        }

        place(world, x - 1, y + 1, z - 1, bambooFence);			//posts on the dock/bridge
        place(world, x - 1, y + 1, z + 2, bambooFence);
        place(world, x - 1, y + 2, z - 1, tikiTorch, 0);			//posts on the dock/bridge
        place(world, x - 1, y + 2, z + 2, tikiTorch, 0);
        
        place(world, x + 2, y + 2, z - 1, palmWood);			//palm wood
        place(world, x + 2, y + 2, z + 2, palmWood);
        
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

	private void placeShopFrame(World world, int i, int j, int k) {
		EntityHangingTropicraft var38;
		try {
		//	var38 = (EntityHangingTropicraft)EntityHangingTropicraft.class.getConstructor(new Class[]{World.class, int.class, int.class, int.class, int.class, boolean.class})
		//			.newInstance(new Object[]{world, i, j, k, 0, false});
			var38 = (EntityHangingTropicraft)EntityHangingTropicraft.class.getConstructor(new Class[]{World.class}).newInstance(new Object[]{world});
			var38.xPosition = i;
			var38.yPosition = j;
			var38.zPosition = k;
			var38.setLocationAndAngles(i, j, k, 0, 0);
			var38.setDirection(1);
			world.spawnEntityInWorld(var38);
		} catch (Exception var32) {
			var32.printStackTrace();
		}
		
	}

	@Override
	public void spawn(World world) {
		// TODO Auto-generated method stub
		
	}

}
