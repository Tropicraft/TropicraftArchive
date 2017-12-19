package net.tropicraft.blocks;

import static net.minecraftforge.common.ForgeDirection.DOWN;

import net.minecraft.block.BlockChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.tropicraft.blocks.tileentities.TileEntityKoaChest;
import net.tropicraft.mods.TropicraftMod;

public class BlockKoaChest extends BlockChest {

    public BlockKoaChest(int blockID) {
        super(blockID);
        this.setTextureFile("/tropicalmod/tropiterrain.png");
        setRequiresSelfNotify();
        this.setCreativeTab(TropicraftMod.tabDecorations);
    }
    
    @Override
    public String getTextureFile() {
    	return "/tropicalmod/tropiterrain.png";
    }
    
    @Override
    public int getRenderType() {
    	return TropicraftMod.bambooChestModelId;
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
    	TileEntityKoaChest te = new TileEntityKoaChest();
    	//te.spawnKoa(world);
        return te;
    }

    @Override
    public void onBlockAdded(World world, int i, int j, int k) {
        super.onBlockAdded(world, i, j, k);
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(int i, int j) {
        return blockIndexInTexture;
    }

    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int doop, float f1, float f2, float f3) {
        Object obj = (TileEntityKoaChest) world.getBlockTileEntity(i, j, k);
        if (obj == null) {
            return true;
        }
        if (world.isBlockSolidOnSide(i, j + 1, k, DOWN)) {
            return true;
        }
        if (world.getBlockId(i - 1, j, k) == blockID && world.isBlockSolidOnSide(i - 1, j + 1, k, DOWN)) {
            return true;
        }
        if (world.getBlockId(i + 1, j, k) == blockID && world.isBlockSolidOnSide(i + 1, j + 1, k, DOWN)) {
            return true;
        }
        if (world.getBlockId(i, j, k - 1) == blockID && world.isBlockSolidOnSide(i, j + 1, k - 1, DOWN)) {
            return true;
        }
        if (world.getBlockId(i, j, k + 1) == blockID && world.isBlockSolidOnSide(i, j + 1, k + 1, DOWN)) {
            return true;
        }
        if (world.getBlockId(i - 1, j, k) == blockID) {
            obj = new InventoryLargeChest("Large koa chest", (TileEntityKoaChest) world.getBlockTileEntity(i - 1, j, k), ((IInventory) (obj)));
        }
        if (world.getBlockId(i + 1, j, k) == blockID) {
            obj = new InventoryLargeChest("Large koa chest", ((IInventory) (obj)), (TileEntityKoaChest) world.getBlockTileEntity(i + 1, j, k));
        }
        if (world.getBlockId(i, j, k - 1) == blockID) {
            obj = new InventoryLargeChest("Large koa chest", (TileEntityKoaChest) world.getBlockTileEntity(i, j, k - 1), ((IInventory) (obj)));
        }
        if (world.getBlockId(i, j, k + 1) == blockID) {
            obj = new InventoryLargeChest("Large koa chest", ((IInventory) (obj)), (TileEntityKoaChest) world.getBlockTileEntity(i, j, k + 1));
        }
        if (world.isRemote) {
            return true;
        } else {
            entityplayer.displayGUIChest(((IInventory) (obj)));
            return true;
        }
    }
  
    /**
     * Gets the hardness of block at the given coordinates in the given world, relative to the ability of the given
     * EntityPlayer.
     */
    @Override
    public float getPlayerRelativeBlockHardness(EntityPlayer player, World world, int i, int j, int k)
    {
    	TileEntityKoaChest tile = (TileEntityKoaChest) world.getBlockTileEntity(i, j, k);
        if (tile != null && tile.isUnbreakable()) {
            return 0.0F;
        }
        return super.getPlayerRelativeBlockHardness(player, world, i, j, k);
    }
    
    
}
