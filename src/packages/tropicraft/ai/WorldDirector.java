package tropicraft.ai;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import tropicraft.config.TropicraftConfig;
import tropicraft.entities.hostile.land.Skeleton;
import CoroAI.IPFCallback;
import CoroAI.PFCallbackItem;
import CoroAI.c_CoroAIUtil;
import CoroAI.componentAI.ICoroAI;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class WorldDirector implements IPFCallback {

	public static HashMap<Integer, ArrayList<ChunkCoordinates>> coordSurfaceCaves = new HashMap<Integer, ArrayList<ChunkCoordinates>>();
	public static HashMap<Integer, ArrayList<ChunkCoordinates>> coordCaves = new HashMap<Integer, ArrayList<ChunkCoordinates>>();
	public static HashMap<Integer, ArrayList<ChunkCoordinates>> coordInvasionSources = new HashMap<Integer, ArrayList<ChunkCoordinates>>();
	
	//Curse tracking
	public HashMap<String, ChunkCoordinates> lastCursePlayerLocations;
	
	//General
	public HashMap<String, ChunkCoordinates> lastScanPlayerLocations;
	
	//Persistant Player NBT
	public static HashMap<String, NBTTagCompound> playerNBT = new HashMap<String, NBTTagCompound>();
	
	//Threading
	public AreaScanner areaScanner;
	public boolean scanning;
	private int detectedIDTropicraft;
	
	public WorldDirector() {		
		lastScanPlayerLocations = new HashMap<String, ChunkCoordinates>();
	}
	
	public void onTick() {
		WorldServer[] worlds = DimensionManager.getWorlds();
    	for (int i = 0; i < worlds.length; i++) {
    		WorldServer world = worlds[i];
    		onTickWorld(world);
    	}
	}
	
	public static void resetDimData() {
		dbg("Resetting TropiWD Data");
		coordSurfaceCaves = new HashMap<Integer, ArrayList<ChunkCoordinates>>();
		coordCaves = new HashMap<Integer, ArrayList<ChunkCoordinates>>();
		coordInvasionSources = new HashMap<Integer, ArrayList<ChunkCoordinates>>();
	}
	
	public static void initDimData(World world) {
		initDimData(world.provider.dimensionId);
	}
	
	public static void initDimData(int dimID) {
		dbg("Initializing TropiWD Data for dim: " + dimID);
		coordSurfaceCaves.put(dimID, new ArrayList<ChunkCoordinates>());
		coordCaves.put(dimID, new ArrayList<ChunkCoordinates>());
		coordInvasionSources.put(dimID, new ArrayList<ChunkCoordinates>());
	}
	
	public static NBTTagCompound getPlayerNBT(String username) {
		if (!playerNBT.containsKey(username)) {
			tryLoadPlayerNBT(username);
		}
		return playerNBT.get(username);
	}
	
	public static void tryLoadPlayerNBT(String username) {
		//try read from hw/playerdata/player.dat
		//init with data, if fail, init default blank
		
		NBTTagCompound playerData = new NBTTagCompound();
		
		try {
			String fileURL = getWorldSaveFolderPath() + getWorldFolderName() + File.separator + "TCPlayerData" + File.separator + username + ".dat";
			
			if ((new File(fileURL)).exists()) {
				playerData = CompressedStreamTools.readCompressed(new FileInputStream(fileURL));
			}
		} catch (Exception ex) {
			dbg("no saved data found for " + username);
		}
		
		playerNBT.put(username, playerData);
	}
	
	public static void writeAllPlayerNBT() {
		dbg("writing out all player nbt");
		
		String fileURL = getWorldSaveFolderPath() + getWorldFolderName() + File.separator + "TCPlayerData";
		if (!new File(fileURL).exists()) new File(fileURL).mkdir();
		
		Iterator it = playerNBT.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        dbg(pairs.getKey() + " = " + pairs.getValue());
	        writePlayerNBT((String)pairs.getKey(), (NBTTagCompound)pairs.getValue());
	    }
	}
	
	public static void writePlayerNBT(String username, NBTTagCompound parData) {
		dbg("writing " + username);
		
		String fileURL = getWorldSaveFolderPath() + getWorldFolderName() + File.separator + "TCPlayerData" + File.separator + username + ".dat";
		
		try {
			FileOutputStream fos = new FileOutputStream(fileURL);
	    	CompressedStreamTools.writeCompressed(parData, fos);
	    	fos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			dbg("Error writing TropiWD player data for " + username);
		}
	}
	
	public void onTickWorld(World world) {
		/*lastWorld = world;
		if (world != lastWorld) {
			world = lastWorld;
			/reaScanner = ;
		}*/
		
		//IM COLD, HALP COMPUTER
		//if (true) while (true) dbg("WARMTH!");
		
		int dim = world.provider.dimensionId;
		
		if (coordSurfaceCaves.get(dim) == null) {
			initDimData(world);
		}
				
		if (world.provider.dimensionId == 0) {
			onTickOverworld(world);
		}
		
		if (world.provider.getDimensionName().equalsIgnoreCase("Tropics")) {
			detectedIDTropicraft = world.provider.dimensionId;
			onTickTropicraft(world);
		}
	}
	
	public static void dbg(Object obj) {
		//System.out.println(obj);
	}
	
	public boolean isCoordAndNearAreaNaturalBlocks(World parWorld, int x, int y, int z, int range) {
		if (isNaturalSurfaceBlock(parWorld.getBlockId(x, y, z)) && 
				isNaturalSurfaceBlock(parWorld.getBlockId(x+range, y, z)) && 
				isNaturalSurfaceBlock(parWorld.getBlockId(x-range, y, z)) &&
				isNaturalSurfaceBlock(parWorld.getBlockId(x, y, z+range)) &&
				isNaturalSurfaceBlock(parWorld.getBlockId(x, y, z-range))) {
			return true;
		}
		return false;
	}
	
	public boolean isNaturalSurfaceBlock(int id) {
		if (id == Block.snow.blockID || id == Block.grass.blockID || id == Block.dirt.blockID || id == Block.sand.blockID || id == Block.stone.blockID || id == Block.gravel.blockID || id == Block.tallGrass.blockID) {
			return true;
		}
		if (isLogOrLeafBlock(id)) return true;
		return false;
	}
	
	public boolean isLogOrLeafBlock(int id) {
		Block block = Block.blocksList[id];
		if (block == null) return false;
		if (block.blockMaterial == Material.leaves) return true;
		if (block.blockMaterial == Material.plants) return true;
		if (block.blockMaterial == Material.wood) return true;
		return false;
	}
	
	//placeholder
	public boolean isCursed(EntityPlayer entP) {
		return true;
	}
	
	public ArrayList<ChunkCoordinates> getUnusedInvasionSourcesInRange(ArrayList<ChunkCoordinates> collection, EntityPlayer entP, float range) {
		return getUnusedInvasionSourcesInRange(collection, new ChunkCoordinates((int)entP.posX, (int)entP.posY, (int)entP.posZ), entP.dimension, range);
	}
	
	public ArrayList<ChunkCoordinates> getUnusedInvasionSourcesInRange(ArrayList<ChunkCoordinates> collection, ChunkCoordinates parCoords, int dim, float range) {
		ArrayList<ChunkCoordinates> coordList = new ArrayList<ChunkCoordinates>();
		
		for (int i = 0; i < collection.size(); i++) {
			ChunkCoordinates coords = collection.get(i);
			
			if (isValid(dim, coords)) {
				if (c_CoroAIUtil.getDistanceXZ(parCoords, coords) < range/* && (ModConfigFields.invasionManyPerPortal || !isInUse(dim, coords))*/) {
					coordList.add(coords);
				}
			} else {
				collection.remove(coords);
			}
		}
		
		return coordList;
	}
	
	public boolean isValid(int dim, ChunkCoordinates coords) {
		return true;//getSourceType(dim, coords) != null;
	}
	
	public void onTickTropicraft(World world) {
		
		for (int i = 0; i < world.playerEntities.size(); i++) {
			EntityPlayer entP = (EntityPlayer)world.playerEntities.get(i);
			
			if (entP != null && !entP.isDead) {
				
				NBTTagCompound playerData = getPlayerNBT(entP.username);
				
				if (world.getWorldTime() % 100 == 0) {
					tryAreaScan(entP);
				}
				
				if (TropicraftConfig.eventMiniSpawns) {
				
					int cooldown = playerData.getInteger("spawnEventCooldown");
					
					if (cooldown <= 0) {
						if (!world.isDaytime() && this.coordSurfaceCaves.get(world.provider.dimensionId).size() > 0) {
							
							if (triggerEvent(world, new ChunkCoordinates((int)entP.posX, (int)entP.posY, (int)entP.posZ), this.coordSurfaceCaves.get(world.provider.dimensionId))) {
								cooldown = 22000;
							} else {
								//retry cooldown
								cooldown = 200;
							}
						}
						
						/*EntityLiving entInt = new Skeleton(world);
						entInt.setPosition(entP.posX, entP.posY, entP.posZ);
						world.spawnEntityInWorld(entInt);
						((ICoroAI)entInt).getAIAgent().spawnedOrNBTReloadedInit();*/
						
					} else {
						cooldown--;
					}
					
					playerData.setInteger("spawnEventCooldown", cooldown);
				}
				
				//if they stepped up from the catacombs area - unneeded, portal is on surface now
				//if (entP.posY > 7) {
				
			}
		}
	}
	
	public void onTickOverworld(World world) {
		
		for (int i = 0; i < world.playerEntities.size(); i++) {
			EntityPlayer entP = (EntityPlayer)world.playerEntities.get(i);
			
			if (entP != null && !entP.isDead) {
				
				NBTTagCompound playerData = getPlayerNBT(entP.username);
				
				
			}
		}
		
		
	}
	
	public float distanceTo(int x1, int y1, int z1, int x2, int y2, int z2)
    {
        float f = x2 - x1;
        float f1 = y2 - y1;
        float f2 = z2 - z1;
        return MathHelper.sqrt_float(f * f + f1 * f1 + f2 * f2);
    }
	
	public boolean tryAreaScan(EntityPlayer entP) {
		
		//TEMPORARY LIST CLEAR!!!!!
		//initDimData(entP.worldObj);
		//scanning = false;
		//if (!scanning) {
		
		if (entP.worldObj.isDaytime() && !scanning && (!lastScanPlayerLocations.containsKey(entP.username) || Math.sqrt(lastScanPlayerLocations.get(entP.username).getDistanceSquared((int)entP.posX, (int)entP.posY, (int)entP.posZ)) > 8)) {
			lastScanPlayerLocations.put(entP.username, new ChunkCoordinates((int)entP.posX, (int)entP.posY, (int)entP.posZ));
			dbg("Starting area scan on player: " + entP.username);
			new Thread(areaScanner = new AreaScanner(this, entP.worldObj, entP), "TC Area Scanner").start();
			
			scanning = true;
		}
		return false;
	}
	
	public synchronized void areaScanCompleteCallback() {
		coordSurfaceCaves.put(areaScanner.world.provider.dimensionId, areaScanner.tempSurfaceCaves);
		coordCaves.put(areaScanner.world.provider.dimensionId, areaScanner.tempCaves);
		scanning = false;
		dbg(areaScanner.world.provider.dimensionId + ": Area scan complete, surface caves: " + coordSurfaceCaves.get(areaScanner.world.provider.dimensionId).size() + ", caves: " + coordCaves.get(areaScanner.world.provider.dimensionId).size());
	}
	
	public boolean triggerEvent(World world, ChunkCoordinates curseCoord, ArrayList<ChunkCoordinates> coordList) {
		
		int dim = world.provider.dimensionId;
		
		ChunkCoordinates bestCave = null;
		float closest = 99999F;
		float minDist = 50F;
		
		Random rand = new Random(world.getWorldTime());
		
		int i = rand.nextInt(coordList.size());
		int tries = 0;
		
		while (closest > 160 && tries++ < 100) {
			i = rand.nextInt(coordList.size());
			if (world.getChunkProvider().chunkExists(coordList.get(i).posX / 16, coordList.get(i).posZ / 16)) {
				float dist = (float)Math.sqrt(coordList.get(i).getDistanceSquared((int)curseCoord.posX, (int)curseCoord.posY, (int)curseCoord.posZ));
				if (dist < closest && dist > minDist) {
					closest = dist;
					bestCave = coordList.get(i);
				}
			}
		}
		
		if (closest <= 160 && bestCave != null) {
			dbg(world.provider.dimensionId + ": Cave Spawn Event dist: " + Math.sqrt(curseCoord.getDistanceSquaredToChunkCoordinates(bestCave)) + " - " + bestCave.posX + ", " + bestCave.posY + ", " + bestCave.posZ);
			
			return spawnGroup(world, curseCoord, bestCave, 5);
			
			
		} else {
			dbg("failed to find close cave, closest: " + closest);
			return false;
		}
	}
	
	public boolean spawnGroup(World world, ChunkCoordinates attackCoord, ChunkCoordinates spawnCoord, int spawnCount) {
		
		
		
		//dbg(difficultyFactor);
		
		//TEMP!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		//if (true) return;
		
		int spawned = 0;
		int amountToSpawn = spawnCount;
		int tries = 0;
		
		String prefix = "TropicraftMod.";
		String mobToSpawnLeader = "TropiSkeleton";
		String mobToSpawn = "TropiSkeleton";
		
		EntityLiving leader = null;
		
		while (spawned < amountToSpawn && tries++ < 100) {
			Entity ent = EntityList.createEntityByName(prefix + (spawned < 1 ? mobToSpawnLeader : /*spawned < 4 ? "ClimberZombie" : */mobToSpawn), world);

			int range = 16;
			
            if (ent instanceof EntityLiving)
            {
            	EntityLiving ent2 = (EntityLiving)ent;
            	double var5 = (double)spawnCoord.posX + (world.rand.nextDouble() - world.rand.nextDouble()) * (double)range;
                double var7 = (double)(spawnCoord.posY + world.rand.nextInt(4) - 2);
                double var9 = (double)spawnCoord.posZ + (world.rand.nextDouble() - world.rand.nextDouble()) * (double)range;
                //EntityLiving var11 = var13 instanceof EntityLiving ? (EntityLiving)var13 : null;
                ent2.setLocationAndAngles(var5, var7, var9, ent2.worldObj.rand.nextFloat() * 360.0F, 0.0F);

                if (ent2.getCanSpawnHere())
                {
                	if (ent instanceof ICoroAI) {
                		((ICoroAI) ent).getAIAgent().spawnedOrNBTReloadedInit();
                		((ICoroAI) ent).getAIAgent().homeX = attackCoord.posX;
                		((ICoroAI) ent).getAIAgent().homeY = attackCoord.posY;
                		((ICoroAI) ent).getAIAgent().homeZ = attackCoord.posZ;
                		((ICoroAI) ent).getAIAgent().jobMan.getPrimaryJob().dontStrayFromHome = true;
                		((EntityLiving) ent).entityAge = -2000;
                	}
                	
                	
                	
                	world.spawnEntityInWorld(ent2);
                	spawned++;
                	tries = 0;
                }
            } else {
            	dbg("invalid entity, aborting");
            	return false;
            }
		}
		
		if (tries >= 100) {
			dbg("hit max tries, aborted");
			if (spawned > 0) {
				dbg("spawned " + spawned + " at least, returning true");
				return true;
			}
			return false;
		}
		
		dbg("spawned: " + spawned);
		return true;
		/*
		Random rand = new Random();
		
		int x = (int)entP.posX + rand.nextInt(32) - 16;
		int z = (int)entP.posZ + rand.nextInt(32) - 16;
		
		//EntityTestAI ent = new EntityTestAI(entP.worldObj);
		EntityZombie ent = new EntityZombie(entP.worldObj);
		
		ent.setPosition(x, entP.worldObj.getHeightValue(x, z) + 1, z);
		//ent.setPosition(coord.posX, coord.posY, coord.posZ);
		
		
		
		entP.worldObj.spawnEntityInWorld(ent);
		
		PFQueue.tryPath(ent, coord.posX, entP.worldObj.getHeightValue(coord.posX, coord.posZ), coord.posZ, 256F, 0);*/
	}
	
	
	
	
	
	
	public ArrayList<PFCallbackItem> queue = new ArrayList<PFCallbackItem>();
	public boolean waitingOnPF = false;

	@Override
	public void pfComplete(PFCallbackItem ci) {
		getQueue().add(ci);
	}

	@Override
	public void manageCallbackQueue() {
		ArrayList<PFCallbackItem> list = getQueue();
		
		try {
			for (int i = 0; i < list.size(); i++) {
				PFCallbackItem item = list.get(i);
				dbg("processing queue");
				waitingOnPF = false;
				
				float dist = distanceTo(item.pe.getFinalPathPoint().xCoord, item.pe.getFinalPathPoint().yCoord, item.pe.getFinalPathPoint().zCoord, item.pe.getPathPointFromIndex(0).xCoord, item.pe.getPathPointFromIndex(0).yCoord, item.pe.getPathPointFromIndex(0).zCoord);
				
				dbg(dist);
				
				if (dist < 7F) {
				//if (item.foundEnd) {
					dbg("found a pathable spot!");
					
					//cache the spot!!!!! - use while they're close enough, find others while using this one
					
					//off till needed
					//spawnHorde(DimensionManager.getWorld(detectedIDTropicraft), new ChunkCoordinates(item.pe.getFinalPathPoint().xCoord, item.pe.getFinalPathPoint().yCoord, item.pe.getFinalPathPoint().zCoord), new ChunkCoordinates(item.pe.getPathPointFromIndex(0).xCoord, item.pe.getPathPointFromIndex(0).yCoord, item.pe.getPathPointFromIndex(0).zCoord));
				}
				
			}
		} catch (Exception ex) {
			dbg("Crash in HW Callback PF manager");
			ex.printStackTrace();
		}
		
		list.clear();
	}

	@Override
	public ArrayList<PFCallbackItem> getQueue() {
		return queue;
	}
	
	public static String lastWorldFolder = "";
    
    public static String getWorldFolderName() {
		World world = DimensionManager.getWorld(0);
		
		if (world != null) {
			lastWorldFolder = ((WorldServer)world).getChunkSaveLocation().getName();
			return lastWorldFolder + File.separator;
		}
		
		return lastWorldFolder + File.separator;
	}
	
	public static String getSaveFolderPath() {
    	if (MinecraftServer.getServer() == null || MinecraftServer.getServer().isSinglePlayer()) {
    		return getClientSidePath() + File.separator;
    	} else {
    		return new File(".").getAbsolutePath() + File.separator;
    	}
    	
    }
	
	public static String getWorldSaveFolderPath() {
    	if (MinecraftServer.getServer() == null || MinecraftServer.getServer().isSinglePlayer()) {
    		return getClientSidePath() + File.separator + "saves" + File.separator;
    	} else {
    		return new File(".").getAbsolutePath() + File.separator;
    	}
    	
    }
    
    @SideOnly(Side.CLIENT)
	public static String getClientSidePath() {
		return FMLClientHandler.instance().getClient().getMinecraftDir().getPath();
	}
}
