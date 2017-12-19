package net.tropicraft.world.worldgen;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.storage.MapData;
import net.tropicraft.mods.TropicraftMod;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
                        world.setBlockWithNotify(surroundX, j, surroundZ, TropicraftMod.purifiedSand.blockID);
                    }
                }
            }
            
            world.setBlockWithNotify(x, y, z, TropicraftMod.bambooChest.blockID);
            
            TileEntityChest tileentitychest = (TileEntityChest) world.getBlockTileEntity(x, y, z);
            if (tileentitychest == null) {
                return false;
            }

            boolean hasAddedMap = false;
            for (int e = 0; e < 8; e++) {
                ItemStack itemstack = pickCheckLootItem(world, random, x, y, z);
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
        } else if (loot == TropicraftMod.froxTradeWinds) {
            if (random.nextFloat() >= 0.5f) {
                return null;
            }
            return new ItemStack(loot);
        } else if (loot == Item.arrow) {
            return new ItemStack(loot, random.nextInt(45) + 1);
        } else if (loot == Item.goldNugget) {
            return new ItemStack(loot, random.nextInt(40) + 1);
        }
        
        return new ItemStack(loot);
        
    }
    
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
        
    	//treasureList.add(TropicraftMod.tropicsPortalEnchanter);
        //treasureList.add(TropicraftMod.dagger);
        treasureList.add(Item.ingotIron);
        treasureList.add(Item.ingotGold);
        treasureList.add(Item.diamond);
        treasureList.add(TropicraftMod.froxTradeWinds);
        treasureList.add(TropicraftMod.scaleHelm);
        treasureList.add(TropicraftMod.scaleBoots);
        treasureList.add(Item.appleGold);
        treasureList.add(TropicraftMod.scaleChestplate);
        treasureList.add(Item.recordCat);
        treasureList.add(Item.arrow);
        treasureList.add(TropicraftMod.scaleLeggings);
        treasureList.add(TropicraftMod.swordEudialyte);
        treasureList.add(TropicraftMod.swordZircon);
        //treasureList.add(mod_tropicraft.PageOfJournal);
        treasureList.add(Item.goldNugget);
        treasureList.add(Item.map);
        treasureList.add(Item.spiderEye);
        treasureList.add(TropicraftMod.froxEasternIsles);
        
        sandBlocks.add(Block.sand.blockID);
        sandBlocks.add(Block.sandStone.blockID);
        sandBlocks.add(TropicraftMod.purifiedSand.blockID);
        
    }
}
