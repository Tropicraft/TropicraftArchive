package tropicraft.world.teleporter;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import tropicraft.blocks.TropicraftBlocks;
import tropicraft.blocks.tileentities.TileEntityBambooChest;
import tropicraft.items.TropicraftItems;
import tropicraft.world.WorldProviderTropicraft;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class TeleporterTropics extends Teleporter {

    private int portalBlockID = TropicraftBlocks.portal.blockID;
    private int portalWallID = TropicraftBlocks.portalWall.blockID;
    private static final List<Integer> validBlockIDs = Arrays.asList(Block.sand.blockID, TropicraftBlocks.purifiedSand.blockID, Block.grass.blockID, Block.dirt.blockID);
	
    WorldServer world;
	
	public TeleporterTropics(WorldServer world) {
		super(world);
		this.world = world;
	}
	
	@Override
    public void placeInPortal(Entity entity, double d, double d2, double d3, float f)
    {
        if (!placeInExistingPortal(entity, d, d2, d3, f))
        {
        	makePortal(entity);
            placeInExistingPortal(entity, d, d2, d3, f);
        }
    }
    
    @Override
    public boolean placeInExistingPortal(Entity entity, double d, double d2, double d3, float f)
    {
        int searchArea = 256;
        double closestPortal = -1D;
        int foundX = 0;
        int foundY = 0;
        int foundZ = 0;
        int entityX = MathHelper.floor_double(entity.posX);
        int entityZ = MathHelper.floor_double(entity.posZ);

        entityX = 512 * Math.round(entityX / 512); 
        entityZ = 512 * Math.round(entityZ / 512); 
        
        for (int x = entityX - searchArea; x <= entityX + searchArea; x++)
        {
            double distX = ((double)x + 0.5D) - entity.posX;
            for (int z = entityZ - searchArea; z <= entityZ + searchArea; z++)
            {
                double distZ = ((double)z + 0.5D) - entity.posZ;
                for (int y = world.getHeight() - 1; y >= 0; y--)
                {
                    if (world.getBlockId(x, y, z) != portalBlockID)
                    {
                        continue;
                    }
                    for (; world.getBlockId(x, y - 1, z) == portalBlockID; y--) { }
                    double distY = ((double)y + 0.5D) - entity.posY;
                    double distance = distX * distX + distY * distY + distZ * distZ;
                    if (closestPortal < 0.0D || distance < closestPortal)
                    {
                        closestPortal = distance;
                        foundX = x;
                        foundY = y;
                        foundZ = z;
                    }
                }
            }
        }

        if (closestPortal >= 0.0D)
        {
            int x = foundX;
            int y = foundY;
            int z = foundZ;
            double newLocX = (double)x + 0.5D;
            double newLocY = (double)y + 0.5D;
            double newLocZ = (double)z + 0.5D;
            if (world.getBlockId(x - 1, y, z) == portalBlockID)
            {
                newLocX -= 0.5D;
            }
            if (world.getBlockId(x + 1, y, z) == portalBlockID)
            {
                newLocX += 0.5D;
            }
            if (world.getBlockId(x, y, z - 1) == portalBlockID)
            {
                newLocZ -= 0.5D;
            }
            if (world.getBlockId(x, y, z + 1) == portalBlockID)
            {
                newLocZ += 0.5D;
            }          
            entity.setLocationAndAngles(newLocX, newLocY + 2, newLocZ, entity.rotationYaw, 0.0F);
            int worldSpawnX = MathHelper.floor_double(newLocX) + ((new Random()).nextBoolean() ? 3 : -3);
            int worldSpawnZ = MathHelper.floor_double(newLocZ) + ((new Random()).nextBoolean() ? 3 : -3);
            int worldSpawnY = world.getHeightValue(worldSpawnX, worldSpawnZ) + 3;
            world.getWorldInfo().setSpawnPosition(worldSpawnX, worldSpawnY, worldSpawnZ);
            entity.motionX = entity.motionY = entity.motionZ = 0.0D;
            
            // If the player is entering the tropics, spawn an Encyclopedia Tropica
            // in the spawn portal chest (if they don't already have one AND one isn't
            // already in the chest)
            if (entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entity;
                if (world.provider instanceof WorldProviderTropicraft) {
                    if (!player.inventory.hasItem(TropicraftItems.encTropica.itemID)) {
                        
                        // Search for the spawn chest
                        TileEntityBambooChest chest = null;
                        int chestX = MathHelper.floor_double(newLocX);
                        int chestZ = MathHelper.floor_double(newLocZ);
                        chestSearch:
                        for (int searchX = -3; searchX < 4; searchX++) {
                            for (int searchZ = -3; searchZ < 4; searchZ++) {
                                for (int searchY = -3; searchY < 4; searchY++) {
                                    if (world.getBlockId(chestX + searchX, worldSpawnY + searchY, chestZ + searchZ) == TropicraftBlocks.bambooChest.blockID) {
                                        chest = (TileEntityBambooChest)world.getBlockTileEntity(chestX + searchX, worldSpawnY + searchY, chestZ + searchZ);
                                        if (chest != null && chest.isUnbreakable()) {
                                            break chestSearch;
                                        }
                                    }
                                }
                            }
                        }
                        
                        // Make sure chest doesn't have the encyclopedia
                        if (chest!= null && chest.isUnbreakable()) {
                            boolean hasEncyclopedia = false;
                            for (int inv = 0; inv < chest.getSizeInventory(); inv++) {
                                ItemStack stack = chest.getStackInSlot(inv);
                                if (stack != null && stack.getItem() == TropicraftItems.encTropica) {
                                    hasEncyclopedia = true;
                                }
                            }

                            // Give out a new encyclopedia
                            if (!hasEncyclopedia) {
                                for (int inv = 0; inv < chest.getSizeInventory(); inv++) {
                                    ItemStack stack = chest.getStackInSlot(inv);
                                    if (stack == null) {
                                        chest.setInventorySlotContents(inv, new ItemStack(TropicraftItems.encTropica, 1));
                                        break;
                                    }
                                }
                            }
                        } 
                    }
                }
            }
            
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public boolean makePortal(Entity entity)
    {
        int searchArea = 32;
        double closestSpot = -1D;
        int entityX = MathHelper.floor_double(entity.posX);
        int entityY = MathHelper.floor_double(entity.posY);
        int entityZ = MathHelper.floor_double(entity.posZ);
        int foundX = entityX;
        int foundY = entityY;
        int foundZ = entityZ;
        
        for (int x = entityX - searchArea; x <= entityX + searchArea; x++) {
            double distX = ((double)x + 0.5D) - entity.posX;
nextCoords:
            for (int z = entityZ - searchArea; z <= entityZ + searchArea; z++) {
                double distZ = ((double)z + 0.5D) - entity.posZ;
                
                // Find topmost solid block at this x,z location
                int y = world.getHeight() - 1;
                for (; y >= 63 - 1 && (world.getBlockId(x, y, z) == 0 || 
                        !Block.blocksList[world.getBlockId(x, y, z)].blockMaterial.isSolid()); y--);
                // Only generate portal between sea level and sea level + 20
                if (y > 63 + 20 || y < 63) {
                    continue;
                }
                
                if (validBlockIDs.contains(world.getBlockId(x, y, z))) {
                    for (int xOffset = -2; xOffset <= 2; xOffset++) {
                        for (int zOffset = -2; zOffset <= 2; zOffset++) {
                            int otherY = world.getHeight() - 1;
                            for (; otherY >= 63 && (world.getBlockId(x + xOffset, otherY, z + zOffset) == 0 || 
                                    !Block.blocksList[world.getBlockId(x + xOffset, otherY, z + zOffset)].blockMaterial.isSolid()); otherY--);
                            if (Math.abs(y - otherY) >= 3) {
                                continue nextCoords;
                            }
                            
                            if (!validBlockIDs.contains(world.getBlockId(x + xOffset, otherY, z + zOffset))) {
                                continue nextCoords;
                            }
                        }
                    }
                    
                    double distY = ((double)y + 0.5D) - entity.posY;
                    double distance = distX * distX + distY * distY + distZ * distZ;
                    if (closestSpot < 0.0D || distance < closestSpot)
                    {
                        closestSpot = distance;
                        foundX = x;
                        foundY = y;
                        foundZ = z;
                        
                    }
                }
            }
        }
        
        // If we can't find a spot (e.g. we're in the middle of the ocean),
        // just put the portal at sea level
        if(closestSpot < 0.0D) {
            Random r = new Random();
            foundX += r.nextInt(16) - 8;
            foundZ += r.nextInt(16) - 8;
            foundY = 63;
        }
        
        buildTeleporterAt(foundX, foundY, foundZ, entity);

        return true;
    }
    
    public void buildTeleporterAt(int x, int y, int z, Entity entity) {        
        y = y < 9 ? 9 : y;

        for (int yOffset = 4; yOffset >= -7; yOffset--) {
            for (int zOffset = -2; zOffset <= 2; zOffset++) {
                for (int xOffset = -2; xOffset <= 2; xOffset++) {
                    int blockX = x + xOffset;
                    int blockY = y + yOffset;
                    int blockZ = z + zOffset;



                    if (yOffset == -7) {
                        // Set bottom of portal to be solid
                        world.setBlock(blockX, blockY, blockZ, portalWallID);
                    } else if (yOffset > 0) {
                        // Set 4 blocks above portal to air
                        world.setBlock(blockX, blockY, blockZ, 0);
                    } else {
                        boolean isWall = xOffset == -2 || xOffset == 2 || zOffset == -2 || zOffset == 2;
                        if (isWall) {
                            // Set walls around portal
                            world.setBlock(blockX, blockY, blockZ, portalWallID);
                        } else {
                            // Set inside of portal
                            int metadata = yOffset == -6 ? 8 : 0;
                        //    System.out.printf("Metadoota %d yOffset %d\n", metadata, yOffset);
                            world.setBlock(blockX, blockY, blockZ, portalBlockID, metadata, 3);
                        }
                    }

                    boolean isCorner = (xOffset == -2 || xOffset == 2) && (zOffset == -2 || zOffset == 2);
                    if (yOffset == 0 && isCorner) { 
                    	System.out.println("I found waldo!" + blockX + " " + blockY + " " + blockZ + " Dimension=" + world.provider.dimensionId);
                        world.setBlock(blockX, blockY + 1, blockZ, TropicraftBlocks.tikiTorch.blockID, 1, 3);
                        world.setBlock(blockX, blockY + 2, blockZ, TropicraftBlocks.tikiTorch.blockID, 1, 3);
                        world.setBlock(blockX, blockY + 3, blockZ, TropicraftBlocks.tikiTorch.blockID, 0, 3);
                    }

                }
            }
        }

        // Add an unbreakable chest to place encyclopedia in
        // NOTE: using instanceof instead of world.getWorldInfo().getDimension()
        // because getWorldInfo() may not be set/correct yet
        if (world.provider instanceof WorldProviderTropicraft) {
            world.setBlock(x + 2, y + 1, z, TropicraftBlocks.bambooChest.blockID, 1, 3);
            TileEntityBambooChest tile = (TileEntityBambooChest)world.getBlockTileEntity(x + 2, y + 1, z);
            if (tile != null) {
                tile.setIsUnbreakable(true);
            }
        }

        for (int yOffset = 5; yOffset >= -7; yOffset--) {
            for (int zOffset = -2; zOffset <= 2; zOffset++) {
                for (int xOffset = -2; xOffset <= 2; xOffset++) {
                    int blockX = x + xOffset;
                    int blockY = y + yOffset;
                    int blockZ = z + zOffset;
                    world.notifyBlocksOfNeighborChange(blockX, blockY, blockZ, world.getBlockId(blockX, blockY, blockZ));
                }
            }
        }
            
    }

}
