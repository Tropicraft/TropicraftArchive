package tropicraft.ai;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import CoroUtil.ChunkCoordinatesSize;
import CoroUtil.DimensionChunkCache;
import CoroUtil.pathfinding.PFQueue;
import CoroUtil.pathfinding.PathEntityEx;

public class AreaScanner implements Runnable {

	public World world;
	public WorldDirector worldDirector;
	public EntityPlayer player;
	
	public ArrayList<ChunkCoordinates> tempSurfaceCaves = new ArrayList();
	public ArrayList<ChunkCoordinates> tempCaves = new ArrayList();
	
	public ArrayList<Integer> groundBlocks = new ArrayList<Integer>();
	
	public boolean pfToPlayer = false;
	
	public AreaScanner(WorldDirector wd, World parWorld, EntityPlayer parPlayer) {
		world = parWorld;
		worldDirector = wd;
		player = parPlayer;
		groundBlocks.add(Block.stone.blockID);
		groundBlocks.add(Block.dirt.blockID);
	}
	
	@Override
	public void run() {

		try {
			tempSurfaceCaves = (ArrayList<ChunkCoordinates>) worldDirector.coordSurfaceCaves.get(world.provider.dimensionId).clone();
			tempCaves = (ArrayList<ChunkCoordinates>) worldDirector.coordCaves.get(world.provider.dimensionId).clone();
			
			if (tempSurfaceCaves == null) tempSurfaceCaves = new ArrayList();
			if (tempCaves == null) tempCaves = new ArrayList();
			
			//TEMP DEBUG!
			//tempSurfaceCaves.clear();
			//tempCaves.clear();
			
			findGoodSurfaceSpot();
			//areaScan();
			worldDirector.areaScanCompleteCallback();
			//Thread.sleep(1000);
		} catch (Exception ex) {
			//worldDirector.dbg("Area scanner aborted due to exception.");
			worldDirector.areaScanCompleteCallback();
			ex.printStackTrace();
		}
		
	}
	
	//Thread safe functions
    public int getBlockId(int x, int y, int z)
    {
        try
        {
            if (!world.checkChunksExist(x, 0, z , x, 128, z)) return 10;
            return world.getBlockId(x, y, z);
        }
        catch (Exception ex)
        {
            return 10;
        }
    }
    
    public int getBlockMetadata(int x, int y, int z)
    {
        if (!world.checkChunksExist(x, 0, z , x, 128, z)) return 0;
        return world.getBlockMetadata(x, y, z);
    }
    
    public float getLightBrightness(int x, int y, int z)
    {
        if (!world.checkChunksExist(x, 0, z , x, 128, z)) return 0;
        return world.getLightBrightness(x, y, z);
    }
    
    public int getHeightValue(int x, int z)
    {
        if (!world.checkChunksExist(x, 0, z , x, 128, z)) return 0;
        return world.getHeightValue(x, z);
    }
    
    public float distanceTo(int x1, int y1, int z1, int x2, int y2, int z2)
    {
        float f = x2 - x1;
        float f1 = y2 - y1;
        float f2 = z2 - z1;
        return MathHelper.sqrt_float(f * f + f1 * f1 + f2 * f2);
    }
    
    public void findGoodSurfaceSpot() {
    	boolean foundSafeSpot = false;
		ChunkCoordinates dest = null;
		
		int randX = -1;
		int randZ = -1;
		
		int maxDistance = 200;
		int minDistance = 50;
		
		for (int tries = 0; !foundSafeSpot && tries < 100; tries++) {
			
			randX = (int)player.posX - (maxDistance/2) + player.worldObj.rand.nextInt(maxDistance);
			randZ = (int)player.posZ - (maxDistance/2) + player.worldObj.rand.nextInt(maxDistance);
			
			//dbg("trying " + randX + " - " + randZ);
			
			//if far away enough
			if (player.getDistance(randX, player.posY, randZ) > minDistance) {
				int tryY = player.worldObj.getHeightValue(randX, randZ) - 1;
				
				int id = world.getBlockId(randX, tryY, randZ);

				//dbg("first id: " + id);
				
				//if area is natural blocks
				if (worldDirector.isCoordAndNearAreaNaturalBlocks(world, randX, tryY, randZ, 8)) {
					while (worldDirector.isLogOrLeafBlock(id)) {
						tryY -= 2;
						id = world.getBlockId(randX, tryY, randZ);
					}
					worldDirector.dbg("Found TC spawn site! " + randX + " - " + tryY + " - " + randZ);
					foundSafeSpot = true;
					dest = new ChunkCoordinates(randX, tryY, randZ);
				}
			} else {
				//dbg("too close");
			}
		}
		
		if (foundSafeSpot) {
			ChunkCoordinates spawn = new ChunkCoordinates((int)player.posX - (maxDistance/2) + player.worldObj.rand.nextInt(maxDistance), 500, (int)player.posZ - (maxDistance/2) + player.worldObj.rand.nextInt(maxDistance));
			//ChunkCoordinates dest = new ChunkCoordinates(randX, , randZ);
			tempSurfaceCaves.add(dest);
			//eventMeteorite(world, spawn, dest, invasion);
		} else {
			worldDirector.dbg("couldnt find a safe TC spawn spot D:");
		}
    }
    
    //Threaded function - iterates over all players
    public void areaScan()
    {
    	//System.out.println("started area scan, pf to player? " + pfToPlayer);
    	
    	
    	if (pfToPlayer) {
    		tempCaves.clear();
    	}
    	
    	if (PFQueue.lastCacheUpdate < System.currentTimeMillis()) {
    		PFQueue.lastCacheUpdate = System.currentTimeMillis() + 10000;
    		DimensionChunkCache.updateAllWorldCache();
    	}
    	
    	PFQueue pathfinder = new PFQueue((IBlockAccess)DimensionChunkCache.dimCacheLookup.get(world.provider.dimensionId), false);
    	//pathfinder.worldMap = (IBlockAccess)DimensionChunkCache.dimCacheLookup.get(world.provider.dimensionId);
    	
    	//for (int i = 0; i < world.playerEntities.size(); i++) {
    		
    		//EntityPlayer player = (EntityPlayer)world.playerEntities.get(i);
    	
    		int size = 512;
    		
    		if (pfToPlayer) size = 96;
    		
            int hsize = size / 2;
            int curX = (int)player.posX;
            int curY = (int)player.posY;
            int curZ = (int)player.posZ;
            
            //int seaLevel = 65; //whats the method that gets this?!
            
            int firstY = 100;
            int lastY = 40;
            
            int yDir = -3;
            
    		int horizontalStep = 5;
    		int distBetweenLocations = 50;
            
    		if (pfToPlayer) {
    			firstY = 0;
    			lastY = 90;
    			yDir = 3;
    		}
    		
    		for (int xx = curX - hsize; xx < curX + hsize; xx += horizontalStep)
            {
                for (int yy = firstY; (pfToPlayer && yy < lastY) || (!pfToPlayer && yy > lastY); yy += yDir)
                {
                    for (int zz = curZ - hsize; zz < curZ + hsize; zz += horizontalStep)
                    {
                        int id = getBlockId(xx, yy, zz);
                        
                        if (id == 0) {
                        	//System.out.println("step 1");
                        	boolean proxFail = false;
                        	boolean proxFail2 = false;
            				for (int j = 0; j < tempSurfaceCaves.size(); j++) {
                    			if (Math.sqrt(tempSurfaceCaves.get(j).getDistanceSquared(xx, yy, zz)) < distBetweenLocations) {
                    				proxFail2 = true;
                    				break;
                    			}
                    		}
            				
            				for (int j = 0; j < tempCaves.size(); j++) {
                    			if (Math.sqrt(tempCaves.get(j).getDistanceSquared(xx, yy, zz)) < distBetweenLocations) {
                    				proxFail2 = true;
                    				break;
                    			}
                    		}
            				
            				if (!proxFail && !proxFail2) {
            					
            					float blockLight = getLightBrightness(xx,  yy, zz);
            					//System.out.println("blockLight " + blockLight);
            					if (pfToPlayer || blockLight <= 0.3) {
	            					int downScan = 0;
	            					for (downScan = 0; downScan < 30; downScan++) {
	            						int testID = getBlockId(xx, yy-downScan, zz);
	            						
	            						if (testID != 0 && groundBlocks.contains(testID)) {
	            							//System.out.println("step 3 - " + downScan);
	            							break;
	            						}
	            					}
	            					
	            					if (downScan < 30 && downScan != 0) {
	            						
	            						//AxisAlignedBB aabb = ;
	            						PathEntityEx pe = null;
	            						ChunkCoordinates coordsDest = null;
	            						ChunkCoordinates coordsSource = null;
	            						if (pfToPlayer) {
	            							coordsDest = new ChunkCoordinates((int)player.posX, (int)player.posY, (int)player.posZ);
	            						} else {
	            							coordsDest = new ChunkCoordinates(xx, getHeightValue(xx, zz), zz);
	            						}

	            						coordsSource = new ChunkCoordinates(xx, yy-downScan+1, zz);
	            						
	            						//temp test
	            						//coordsSource = new ChunkCoordinates((int)player.posX + 10, (int)player.posY, (int)player.posZ);
	            						
	            						//System.out.println("starting pathfind: " + coordsSource.posX + ", " + coordsSource.posY + ", " + coordsSource.posZ + " to " + coordsDest.posX + ", " + coordsDest.posY + ", " + coordsDest.posZ);
	            						
	            						pe = pathfinder.createPathTo(new ChunkCoordinatesSize(coordsSource.posX, coordsSource.posY, coordsSource.posZ, world.provider.dimensionId, 128, 2)/*.getAABBPool().addOrModifyAABBInPool(xx, yy-downScan+1, zz, xx, yy-downScan+1, zz).expand(1D, 2D, 1D)*/, coordsDest.posX, coordsDest.posY, coordsDest.posZ, 256, 0);
	            						
	            						//pe = pathfinder.createPathTo(new ChunkCoordinatesSize(coords.posX + 10, coords.posY, coords.posZ, world.provider.dimensionId, 1, 2), coords.posX, coords.posY, coords.posZ, 256, 0);
	            						
	            						if (pe != null) {
	            							//System.out.println("path not null! - " + pe.pathLength);
	            							
	            							double dist = distanceTo(pe.points[pe.pathLength-1].xCoord, pe.points[pe.pathLength-1].yCoord, pe.points[pe.pathLength-1].zCoord, coordsDest.posX, coordsDest.posY, coordsDest.posZ);
	            							
	            							//System.out.println("pf result dist: " + dist);
	            							
	            							if (dist < 5F) {
	            								//System.out.println("found near surface cave, location: " + xx + ", " + yy + ", " + zz);
	            								if (!proxFail2) {
	            									//System.out.println("adding");
	            									tempSurfaceCaves.add(new ChunkCoordinates(xx, yy-downScan, zz));
	            								}
			            						
	            							} else {
	            								if (!proxFail) tempCaves.add(new ChunkCoordinates(xx, yy-downScan, zz));
	            							}
	            						} else {
	            							if (!proxFail) tempCaves.add(new ChunkCoordinates(xx, yy-downScan, zz));
	            						}
	            						
	            						
	            					}
            					}
            					
            					
            					//System.out.println("add waterfall");
            				}
                        } else {
                        	
                        }
                    }
                }
            }
    	//}
    }

}
