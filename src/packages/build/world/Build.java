package build.world;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import build.SchematicData;
import cpw.mods.fml.common.FMLCommonHandler;

public class Build {

	public static int blockIDHighestVanilla = 158; //figure out an elegant non loop crazy way to determine this automatically
	
	//public int id = 0;
	public String file = "";

	//Basic placement data
	/*public int startX = 0;
	public int startY = 0;
	public int startZ = 0;
	
	public int sizeX = 0;
	public int sizeY = 0;
	public int sizeZ = 0;*/
	
	//visual data
	public int curTick = 0;
	public int maxTicks = 0;
	
	//world data
	public NBTTagCompound levelData = null;
	public NBTTagList tileEntities;
	public NBTTagList entities;
	public int build_blockIDArr[][][];
	public int build_blockMetaArr[][][];
	public boolean build_blockPlaced[][][];
	
	public HashMap<Integer, Integer> blockMappingNameToID = new HashMap<Integer, Integer>();
	
	//REFACTOR THESE NAMES TO THE ABOVE COMMENTED OUT ONES ONCE COMPILING
	public int map_sizeX = 0;
	public int map_sizeY = 0;
	public int map_sizeZ = 0;
	public int map_coord_minX = 0;
	public int map_coord_minY = 0;
	public int map_coord_minZ = 0;
	public int map_surfaceOffset = 0;
	
	public int dim = 0;
	
	//the new format
	//- was to use integers instead of bytes, but that even wont be needed if unlocalized names are used (though use integer for saving out 
	public boolean newFormat = false;
	
	public boolean backwardsCompatibleOldRotate = false;
	
	public Build(int x, int y, int z, String parFile, boolean noLoad) {
		//id = parID;
		file = parFile;
		
		map_coord_minX = x;
		map_coord_minY = y;
		map_coord_minZ = z;
		
		if (!noLoad) readNBT(file);
	}
	
	public Build(int x, int y, int z, String parFile) {
		this(x, y, z, parFile, false);
	}
	
	public void load() {
		
	}
	
	public void start() {
		
	}
	
	public void updateTick() {
		
	}
	
	public void setCornerPosition(int x, int y, int z) {
		map_coord_minX = x;
		map_coord_minY = y;
		map_coord_minZ = z;
	}
	
	public void recalculateLevelSize(int x1, int y1, int z1, int x2, int y2, int z2) {
		recalculateLevelSize(x1, y1, z1, x2, y2, z2, false);
	}
	
	public void recalculateLevelSize(int x1, int y1, int z1, int x2, int y2, int z2, boolean sizeUp) {
		
		map_sizeX = 0;
		map_sizeY = 0;
		map_sizeZ = 0;
		map_coord_minX = x1;
		map_coord_minY = y1;
		map_coord_minZ = z1;
		
		if (x1 > x2) map_coord_minX = x2;
		if (y1 > y2) map_coord_minY = y2;
		if (z1 > z2) map_coord_minZ = z2;
		
		if (x1 - x2 >= 0) {
			map_sizeX = x1 - x2;
		} else {
			map_sizeX = x2 - x1;
		}
		
		if (y1 - y2 >= 0) {
			map_sizeY = y1 - y2;
		} else {
			map_sizeY = y2 - y1;
		}
		
		if (z1 - z2 >= 0) {
			map_sizeZ = z1 - z2;
		} else {
			map_sizeZ = z2 - z1;
		}
		
		//map_coord_minY--;
		//map_sizeY+=2;
		if (sizeUp) {
			map_sizeX++;
			map_sizeY++;
			map_sizeZ++;
		}
		
		//System.out.println("Size: " + map_sizeX + "," + map_sizeY + "," + map_sizeZ);
		//System.out.println("map_coord_min: " + map_coord_minX + "," + map_coord_minY + "," + map_coord_minZ);
		
	}
	
	public void readNBT(String level) {
		
		
		
		levelData = null;
		
		try {
			
	    	NBTTagCompound nbttagcompound = CompressedStreamTools.readCompressed(new FileInputStream(level + ".schematic"));
	    	
	    	levelData = nbttagcompound;
	    	
	    	//only override newFormat if schematic actually tells it to, this also assumes the outside setting is right context...
	    	if (nbttagcompound.hasKey("newFormat")) {
	    		newFormat = nbttagcompound.getBoolean("newFormat");
	    	}
	    	
	    	if (newFormat) {
		    	NBTTagCompound nbtMapping = nbttagcompound.getCompoundTag("blockTranslationMap");
		    	this.blockMappingNameToID = genBlockIDSchemToNewIDMap(nbtMapping);
	    	}
	    	
			tileEntities = nbttagcompound.getTagList("TileEntities");
			entities = nbttagcompound.getTagList("Entities");
			
			
			
			int sizeX = map_sizeX = nbttagcompound.getShort("Width");
			int sizeZ = map_sizeZ = nbttagcompound.getShort("Length");
			int sizeY = map_sizeY = nbttagcompound.getShort("Height");
			
			map_surfaceOffset = nbttagcompound.getShort("surfaceOffset");
			
			int bPosX = 0;
			int bPosY = 0;
			int bPosZ = 0;
			
			build_blockIDArr = new int
            [sizeX]
            [sizeY]
            [sizeZ];
			
			build_blockMetaArr = new int
            [sizeX]
            [sizeY]
            [sizeZ];
			
			build_blockPlaced = new boolean
            [sizeX]
            [sizeY]
            [sizeZ];
			
			if (newFormat) {
				int metadata[] = nbttagcompound.getIntArray("Data");
				int blockids[] = nbttagcompound.getIntArray("Blocks");
				
				for (int xx = 0; xx < sizeX; xx++) {
					for (int yy = 0; yy < sizeY; yy++) {
						for (int zz = 0; zz < sizeZ; zz++) {
							int index = yy * sizeX * sizeZ + zz * sizeX + xx;
							build_blockIDArr[xx][yy][zz] = blockids[index];
							build_blockMetaArr[xx][yy][zz] = metadata[index];
							
							//time to translate using mapping!
							int a = build_blockIDArr[xx][yy][zz];
							if (a > blockIDHighestVanilla) {
								//protect against javas autocasting primitves, incase of missing entry
								if (this.blockMappingNameToID.containsKey(a)) {
									Integer bb = this.blockMappingNameToID.get(a);
									int b = Integer.valueOf(this.blockMappingNameToID.get(a));
									//System.out.println("tra: " + a + " -> " + b);
									build_blockIDArr[xx][yy][zz] = b;
								}
							}
							
							build_blockPlaced[xx][yy][zz] = false;
						}
					}
				}
			} else {
				byte metadata[] = nbttagcompound.getByteArray("Data");
				byte blockids[] = nbttagcompound.getByteArray("Blocks");
				
				for (int xx = 0; xx < sizeX; xx++) {
					for (int yy = 0; yy < sizeY; yy++) {
						for (int zz = 0; zz < sizeZ; zz++) {
							int index = yy * sizeX * sizeZ + zz * sizeX + xx;
							build_blockIDArr[xx][yy][zz] = blockids[index];
							build_blockMetaArr[xx][yy][zz] = metadata[index];
							build_blockPlaced[xx][yy][zz] = false;
							
						}
					}
				}
			}
			
			
			
			file = level;
			//ZCGame.instance.mapMan.curLevel = level;
			
		} catch (Exception ex) {
			//notification off until generic build copy paste interface is supported for server
			ex.printStackTrace();
		}
		
	}
	
	public void resetData() {
		build_blockIDArr = new int
        [map_sizeX]
        [map_sizeY]
        [map_sizeZ];
		
		build_blockMetaArr = new int
        [map_sizeX]
        [map_sizeY]
        [map_sizeZ];
		
		build_blockPlaced = new boolean
        [map_sizeX]
        [map_sizeY]
        [map_sizeZ];
	}
	
	public void scanLevelToData() {
		scanLevelToData(FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(dim));
	}
	
	public void scanLevelToData(World worldObj) {
		
		resetData();
		
		//if (false) map_surfaceOffset = map_coord_minY - ZCGame.ZCWorldHeight;
		
		for (int xx = 0; xx < map_sizeX; xx++) {
			for (int yy = 0; yy < map_sizeY; yy++) {
				for (int zz = 0; zz < map_sizeZ; zz++) {
					int index = yy * map_sizeX * map_sizeZ + zz * map_sizeX + xx;
					
					World worldRef = worldObj;
					
					build_blockIDArr[xx][yy][zz] = worldRef.getBlockId(map_coord_minX+xx, map_coord_minY+yy, map_coord_minZ+zz);
					build_blockMetaArr[xx][yy][zz] = worldRef.getBlockMetadata(map_coord_minX+xx, map_coord_minY+yy, map_coord_minZ+zz);
					build_blockPlaced[xx][yy][zz] = false;
					
					//System.out.println("build_blockIDArr[xx][yy][zz]: " + build_blockIDArr[xx][yy][zz]);
					
					//check for tile entity to write out!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
					
				}
			}
		}
	}
	
	public void scanWriteNBT() {
		scanLevelToData();
		writeNBT();
	}
	
	public void writeNBT() {
		//scans over level area and updates all data needed
		
		try {
			if (levelData == null) {
				System.out.println("New NBT Data Object");
				levelData = new NBTTagCompound();
			}
			
			//Init both formats cause java is bitchy
			int metadataInt[] = new int[map_sizeX * map_sizeY * map_sizeZ];
			int blockidsInt[] = new int[map_sizeX * map_sizeY * map_sizeZ];
			byte metadataByte[] = new byte[map_sizeX * map_sizeY * map_sizeZ];
			byte blockidsByte[] = new byte[map_sizeX * map_sizeY * map_sizeZ];
			
			NBTTagList var16 = new NBTTagList();
			
			for (int xx = 0; xx < map_sizeX; xx++) {
				for (int yy = 0; yy < map_sizeY; yy++) {
					for (int zz = 0; zz < map_sizeZ; zz++) {
						int index = yy * map_sizeX * map_sizeZ + zz * map_sizeX + xx;
						
						if (newFormat) {
							blockidsInt[index] = build_blockIDArr[xx][yy][zz];
							metadataInt[index] = build_blockMetaArr[xx][yy][zz];
						} else {
							blockidsByte[index] = (byte)build_blockIDArr[xx][yy][zz];
							metadataByte[index] = (byte)build_blockMetaArr[xx][yy][zz];
						}
						
						World worldRef = FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(dim);
						
						TileEntity tEnt = worldRef.getBlockTileEntity(map_coord_minX+xx,map_coord_minY+yy,map_coord_minZ+zz);
						if (tEnt != null) {
							NBTTagCompound var10 = new NBTTagCompound();
							
							if (tEnt instanceof SchematicData) {
								((SchematicData)tEnt).writeToNBT(var10, this);
							} else {
								tEnt.writeToNBT(var10);
							}
							
							//adjust coords to be relative to the schematic file
							var10.setBoolean("coordsSetRelative", true);
							var10.setInteger("x", xx);
							var10.setInteger("y", yy);
							var10.setInteger("z", zz);
							
							var16.appendTag(var10);
						}
						//check for tile entity to write out!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
						
					}
				}
			}
	
	        levelData.setTag("TileEntities", var16);
			
			//somehow get all entities within bounds
			//loadedentity list bounds check
			
	        if (newFormat) {
	        	levelData.setIntArray("Blocks", blockidsInt);
				levelData.setIntArray("Data", metadataInt);
	        } else {
				levelData.setByteArray("Blocks", blockidsByte);
				levelData.setByteArray("Data", metadataByte);
	        }
	        levelData.setBoolean("newFormat", newFormat);
	        levelData.setCompoundTag("blockTranslationMap", genBlockIDToNameMap());
			
			levelData.setShort("Width", (short)map_sizeX);
			levelData.setShort("Height", (short)map_sizeY);
			levelData.setShort("Length", (short)map_sizeZ);
			
			levelData.setShort("surfaceOffset", (short)map_surfaceOffset);
			
			saveLevelData(file);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	//lets try: still write out using existing block ids, but this map does those ids -> unlocalizedname
	//then on printing, do schematic id -> unlocalizedname -> lookup the new id
	//for write out
	public NBTTagCompound genBlockIDToNameMap() {
		NBTTagCompound nbt = new NBTTagCompound();
		
		for (int i = 0; i < Block.blocksList.length; i++) {
        	Block block = Block.blocksList[i];
        	if (block != null) {
        		if (i > blockIDHighestVanilla && !block.getUnlocalizedName().equals("tile.ForgeFiller")) {
        			//set both lookup methods, or not, since you have to iterate it, make it easier
        			//nbt.setString("" + i, block.getUnlocalizedName());
        			nbt.setInteger(block.getUnlocalizedName(), i);
        		}
        	}
        }

		return nbt;
	}
	
	//for read in, more complicated
	public HashMap<Integer, Integer> genBlockIDSchemToNewIDMap(NBTTagCompound parMappingNBT) {
		HashMap<String, Integer> swapMap = new HashMap<String, Integer>();
		HashMap<Integer, Integer> finalMap = new HashMap<Integer, Integer>();
		try {
			
			//first, generate the swapMap for the running mc block name -> id
			for (int i = 0; i < Block.blocksList.length; i++) {
	        	Block block = Block.blocksList[i];
	        	if (block != null) {
	        		if (i > blockIDHighestVanilla && !block.getUnlocalizedName().equals("tile.ForgeFiller")) {
	        			swapMap.put(block.getUnlocalizedName(), i);
	        		}
	        	}
			}
			
			Collection playerDataCl = parMappingNBT.getTags();
			Iterator it = playerDataCl.iterator();
			
			while (it.hasNext()) {
				NBTTagInt tag = (NBTTagInt)it.next();
				if (swapMap.get(tag.getName()) != null) {
					finalMap.put(tag.data, swapMap.get(tag.getName()));
				} else {
					System.out.println("missing a mapping in this schematic for: " + tag.data);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return finalMap;
	}
	
	public void saveLevelData(String level) {
		
		try {
			
			if (levelData != null) {
				
				FileOutputStream fos = new FileOutputStream(level + ".schematic");
				
		    	CompressedStreamTools.writeCompressed(levelData, fos);
		    	
		    	fos.close();
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	public boolean fillBuildData(Build parBuild) {
		
		FileInputStream fis = null;
		
    	try {
	    	InputStream is = new FileInputStream(parBuild.file);
			
	    	fis = new FileInputStream(parBuild.file);
	    	
	    	NBTTagCompound nbttagcompound = CompressedStreamTools.readCompressed(fis);
	    	
			byte metadata[] = nbttagcompound.getByteArray("Data");
			byte blockids[] = nbttagcompound.getByteArray("Blocks");
			
			
			
			if (fis != null) {
    			fis.close();
    		}
			
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	} finally {
    		
    		
    	}
    	return true;
    }
}
