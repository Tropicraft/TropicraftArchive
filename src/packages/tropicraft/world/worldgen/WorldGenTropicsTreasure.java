package tropicraft.world.worldgen;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.storage.MapData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tropicraft.blocks.TropicraftBlocks;
import tropicraft.items.TropicraftItems;

public class WorldGenTropicsTreasure extends WorldGenerator {
    
    private static final List<Item> treasureList = new ArrayList<Item>();
    
    private static final List<Integer> sandBlocks = new ArrayList<Integer>();

    public WorldGenTropicsTreasure() {
    }

    @Override
    public boolean generate(World world, Random random, int i, int j, int k) {

        int depth = random.nextInt(2) + 2;
        
tryagain:
        for (int tries = 0; tries < 10; tries++) {
            
            int x = (i + random.nextInt(8)) - random.nextInt(8);
            int z = (k + random.nextInt(8)) - random.nextInt(8);
            j = getTerrainHeightAt(world, x, z) - 1;
            int y = j;
        
            for (; y > j - depth; y--) {
                if (!sandBlocks.contains(world.getBlockId(x, y, z))) {
                    continue tryagain;
                }
            }
            
            int sandArea = 3;
            for (int surroundZ = z - sandArea; surroundZ <= z + sandArea; surroundZ++) {
                for (int surroundX = x - sandArea; surroundX <= x + sandArea; surroundX++) {
                    if (!sandBlocks.contains(world.getBlockId(surroundX, j, surroundZ))) {
                        continue tryagain;
                    }
                }
            }
            
            //System.err.println("Generating Treasure chest at: " + i + " " + y + " " + k);
            
            for (int surroundZ = z - sandArea; surroundZ <= z + sandArea; surroundZ++) {
                for (int surroundX = x - sandArea; surroundX <= x + sandArea; surroundX++) {
                    if (random.nextFloat() < 0.2f) {
                        world.setBlock(surroundX, j, surroundZ, TropicraftBlocks.purifiedSand.blockID);
                    }
                }
            }
            
            world.setBlock(x, y, z, TropicraftBlocks.bambooChest.blockID);
            
            TileEntityChest tileentitychest = (TileEntityChest) world.getBlockTileEntity(x, y, z);
            if (tileentitychest == null) {
                return false;
            }

            boolean hasAddedMap = false;
            for (int e = 0; e < 8; e++) {
                ItemStack itemstack = e == 0 ? new ItemStack(TropicraftItems.recordBuriedTreasure) : pickCheckLootItem(world, random, x, y, z);
                if (itemstack != null && (itemstack.getItem() != Item.map || !hasAddedMap)) {
                    if (itemstack.getItem() == Item.map) {
                        hasAddedMap = true;
                        initializeMap(world, itemstack, x, y, z);
                    }
                    tileentitychest.setInventorySlotContents(random.nextInt(tileentitychest.getSizeInventory()), itemstack);
                }
            }

            return true;
        }
        return true;
    }
    
    int getTerrainHeightAt(World world, int x, int z) {
        for (int y = 128; y > 0; y--) {
            int id = world.getBlockId(x, y, z);
            if (id != 0 && id != Block.tallGrass.blockID && id != Block.snow.blockID && id != Block.leaves.blockID && id != Block.wood.blockID) {
                return y + 1;
            }
        }
        return 0;
    }

    private ItemStack pickCheckLootItem(World world, Random random, int x, int y, int z) {

        Item loot = treasureList.get(random.nextInt(treasureList.size()));
        
        if (loot == Item.ingotIron) {
            return new ItemStack(loot, random.nextInt(36) + 1);
        } else if (loot == Item.ingotGold) {
            return new ItemStack(loot, random.nextInt(46) + 1);
        } else if (loot == Item.diamond) {
            return new ItemStack(loot, random.nextInt(24) + 6);
        } else if (loot == TropicraftItems.recordTradeWinds) {
            if (random.nextFloat() >= 0.5f) {
                return null;
            }
            return new ItemStack(loot);
        } else if (loot == Item.arrow) {
            return new ItemStack(loot, random.nextInt(45) + 1);
        } else if (loot == Item.goldNugget) {
            return new ItemStack(loot, random.nextInt(40) + 1);
        } else if (loot == TropicraftItems.oreDrops) {
        	return new ItemStack(loot, random.nextInt(10) + 5, random.nextInt(5));
        } else if (loot == TropicraftItems.shells) {
        	return new ItemStack(loot, random.nextInt(4) + 2, random.nextInt(6));
        }
        
        return new ItemStack(loot);
        
    }
    
    /**
     * Initialize the map for the realm
     * @param world World object
     * @param mapItem Map object
     * @param x x coordinate
     * @param y y coordinate
     * @param z z coordinate
     */
    private void initializeMap(World world, ItemStack mapItem, int x, int y, int z) {
        mapItem.setItemDamage(world.getUniqueDataId("map"));
        String mapName = "map_" + mapItem.getItemDamage();
        MapData data = new MapData(mapName);
        world.setItemData(mapName, data);
        data.xCenter = x;
        data.zCenter = z;
        data.scale = 3;
    //    data.dimension = (byte)world.worldProvider.worldType;
        data.markDirty();
    }
    
    static {
        // TODO
    	//treasureList.add(TropicraftMod.tropicsPortalEnchanter);
    	//treasureList.add(mod_tropicraft.PageOfJournal);
    	
        treasureList.add(Item.ingotIron);
        treasureList.add(Item.ingotGold);
        treasureList.add(Item.diamond);
        treasureList.add(TropicraftItems.oreDrops);
        
        treasureList.add(TropicraftItems.shells);
        
        treasureList.add(TropicraftItems.scaleHelm);
        treasureList.add(TropicraftItems.scaleBoots);
        treasureList.add(TropicraftItems.scaleChestplate);
        treasureList.add(TropicraftItems.scaleLeggings);
        
        treasureList.add(Item.appleGold);
        treasureList.add(Item.arrow);
        
        treasureList.add(TropicraftItems.swordEudialyte);
        treasureList.add(TropicraftItems.swordZircon);
        
        treasureList.add(Item.goldNugget);
        treasureList.add(Item.map);
        treasureList.add(Item.spiderEye);
        
        treasureList.add(TropicraftItems.recordTradeWinds);
        treasureList.add(TropicraftItems.recordEasternIsles);
        treasureList.add(TropicraftItems.recordBuriedTreasure);
        treasureList.add(TropicraftItems.recordLowTide);
        treasureList.add(TropicraftItems.recordSummering);
        treasureList.add(TropicraftItems.recordTheTribe);
        
        sandBlocks.add(Block.sand.blockID);
        sandBlocks.add(Block.sandStone.blockID);
        sandBlocks.add(TropicraftBlocks.purifiedSand.blockID);        
    }
}
