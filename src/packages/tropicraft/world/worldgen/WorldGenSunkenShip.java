package tropicraft.world.worldgen;

import java.util.Random;

import tropicraft.blocks.TropicraftBlocks;
import tropicraft.blocks.tileentities.TileEntityBambooChest;
import tropicraft.items.TropicraftItems;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class WorldGenSunkenShip extends TCGenBase {

	private int x, z;
	
	private final int DIR;

	public WorldGenSunkenShip(World world, Random random) {
		super(world, random, true);
		DIR = this.rand.nextInt(4);
	}

	@Override
	public boolean generate(int i, int j, int k) {
		this.x = i;
		this.z = k;
		
		j = this.getTerrainHeightAt(i, k);
		
		if(this.worldObj.getBlockId(i, j + 4, k) != TropicraftBlocks.tropicsWaterStationary.blockID) { // Must be water 4 blocks above the sea floor
			return false;
		}
		
		j += 1; //Move the "origin" up
		
		final int length = this.rand.nextInt(25) + 25;
		
		int y = j;
		
		while(true) { //Acting y loop
			boolean hasGenned = false;
			
			int fib = 2;
			int lastFib = 1;
			int width = y - j;
			for(int x = 0; x < length; x++) {
				if(x == fib && x <= (length / 3D)) {
					width++;
					fib += lastFib;
					lastFib = fib - lastFib;
				}
				
				if(x > length - 3) {
					width--;
				}
				
				if(width >= 0) {
					for(int z = -width; z <= width; z++) {
						if(rand.nextInt(5) < 3) 
						{
							if(y == j || x == length - 1) {
								this.placeBlockWithDir(x, y, z, TropicraftBlocks.tropicsBuildingBlock.blockID, 1);
								if(z == -width || z == width || x == length - 1) {
									this.placeBlockWithDir(x, y + 1, z, TropicraftBlocks.tropicsBuildingBlock.blockID, 1);								
								}
								
								if(x == length / 2 && z == 0) {
									this.placeBlockWithDir(x, y + 1, z, TropicraftBlocks.tropicsBuildingBlock.blockID, 1);		
									this.placeBlockWithDir(x, y + 2, z, TropicraftBlocks.tropicsBuildingBlock.blockID, 1);		
									this.placeBlockWithDir(x, y + 3, z, TropicraftBlocks.tropicsBuildingBlock.blockID, 1);		
								}
							} else if(x == length / 2 && z == 0 && y == j - 2) {
								this.placeBlockWithDir(x, y, z, TropicraftBlocks.bambooChest.blockID, 0);	
								
								TileEntityBambooChest chest = (TileEntityBambooChest)this.getTEWithDir(x, y, z);
								
								if(chest != null) {
									chest.setInventorySlotContents(0, this.randLoot());
								}
							} else if(z == -width || z == width) {
								this.placeBlockWithDir(x, y, z, TropicraftBlocks.tropicsBuildingBlock.blockID, 1);						
							} else {
								this.placeBlockWithDir(x, y, z, 0, 0);	
							}
						}
					}
					hasGenned = true;
				}
			}
			
			if(!hasGenned) {
				break;
			}
			
			y--;
		}
		
		return false;
	}
	
	private void placeBlockWithDir(int i, int j, int k, int blockID, int meta) {
		switch(this.DIR) {
			case 2:
				this.worldObj.setBlock(this.x + i, j, this.z + k, blockID, meta, 3);
				return;
			case 0:
				this.worldObj.setBlock(this.x + k, j, this.z + i, blockID, meta, 3);
				return;
			case 3:
				this.worldObj.setBlock(this.x - i, j, this.z - k, blockID, meta, 3);
				return;
			case 1:
				this.worldObj.setBlock(this.x - k, j, this.z - i, blockID, meta, 3);
				return;
		}
	}
	
	private TileEntity getTEWithDir(int i, int j, int k) {
		switch(this.DIR) {
			case 2:
				return this.worldObj.getBlockTileEntity(this.x + i, j, this.z + k);
			case 0:
				return this.worldObj.getBlockTileEntity(this.x + k, j, this.z + i);
			case 3:
				return this.worldObj.getBlockTileEntity(this.x - i, j, this.z - k);
			case 1:
				return this.worldObj.getBlockTileEntity(this.x - k, j, this.z - i);
		}
		return null;
	}
	
	public ItemStack randLoot()
	{
		int picker = rand.nextInt(18);
		if(picker < 6)
		{
			return new ItemStack(TropicraftItems.bambooChute, rand.nextInt(20) + 1);
		}
		else if(picker < 8)
		{
			return new ItemStack(TropicraftItems.coconutBomb, rand.nextInt(3) + 1);
		}
		else if(picker < 10)
		{
			return new ItemStack(TropicraftItems.scale, rand.nextInt(3) + 1);
		}
		else if(picker < 12)
		{
			return new ItemStack(TropicraftBlocks.tropicsBuildingBlock, rand.nextInt(20) + 1);
		}
		else if(picker < 15)
		{
			return new ItemStack(TropicraftItems.cookedFrogLeg, rand.nextInt(4) + 1);
		}
		else if(picker == 14)
		{
			return new ItemStack(TropicraftItems.ashenMasks, 1, rand.nextInt(7));
		}
		else if(picker == 15)
		{
			return new ItemStack(TropicraftItems.recordTradeWinds, 1);
		}
		else if(picker == 16)
		{
			return new ItemStack(TropicraftItems.recordEasternIsles, 1);
		}
		else
		{
			return new ItemStack(TropicraftItems.blowGun, 1);
		}
	}
}
