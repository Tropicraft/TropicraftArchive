package tropicraft.world.worldgen;

import java.util.Random;

import tropicraft.blocks.BlockFlowerPot;
import tropicraft.blocks.TropicraftBlocks;
import tropicraft.blocks.tileentities.TileEntityBambooChest;
import tropicraft.blocks.tileentities.TileEntityFlowerPot;
import tropicraft.items.TropicraftItems;
import tropicraft.world.biomes.BiomeGenTropicraft;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class WorldGenForestAltarRuin extends TCGenBase {

	private int x, z;
	
	private final int DIR;
	
	public WorldGenForestAltarRuin(World world, Random random) {
		super(world, random, true);
		DIR = this.rand.nextInt(4);
	}

	@Override
	public boolean generate(int i, int j, int k) {
		if(this.worldObj.getBiomeGenForCoords(i, k) != BiomeGenTropicraft.rainforestPlains && this.worldObj.getBiomeGenForCoords(i, k) != BiomeGenTropicraft.rainforestHills) {
			return false;
		}
		
		x = i;
		z = k;
		
		final int width = (rand.nextInt(2) + 3) * 2;
		final int length = rand.nextInt(6) + 10;
		final int halfWidth = width / 2;

		boolean hasGennedTunnel = false;
		
		for(int z = 0; z < length; z++) {
			for(int x = 0; x < width; x++) {
				if(this.getTerrainHeightWithDir(x, z) < j) {
					j = this.getTerrainHeightWithDir(x, z);
				}
			}
		}
		
		for(int z = 0; z < length; z++) {
			for(int x = 0; x < width; x++) {
				for(int y = 0; y < 4; y++) {
					this.placeBlockWithDir(x, y + j, z, 0, 0);
				}
				int y = j;
				this.placeBlockWithDir(x, y, z, TropicraftBlocks.tropicsBuildingBlock.blockID, 1);
				
				if(z == 0) {
					if(x == 0 || x == width - 1) {
						this.placeBlockWithDir(x, y + 1, z, TropicraftBlocks.tropicsFence.blockID, 0);
						this.placeBlockWithDir(x, y + 2, z, TropicraftBlocks.tropicsFence.blockID, 0);
					} else {
						this.placeBlockWithDir(x, y + 1, z, TropicraftBlocks.tropicsSingleSlab.blockID, 2);
						this.placeBlockWithDir(x, y + 2, z, TropicraftBlocks.tropicsFence.blockID, 0);
					}
				} else if(z == 1) {
					if(x == 0 || x == width - 1) {
						this.placeBlockWithDir(x, y + 1, z, TropicraftBlocks.tropicsFence.blockID, 0);
					} else if(x == 1 || x == width - 2) {
						this.placeBlockWithDir(x, y + 1, z, TropicraftBlocks.chunkOHead.blockID, 0);
						this.placeBlockWithDir(x, y + 2, z, TropicraftBlocks.flowerPot.blockID, 0);
						
						TileEntityFlowerPot pot = (TileEntityFlowerPot)this.getTEWithDir(x, y + 2, z);
						if(pot != null) {
							pot.setDamage((short) (this.rand.nextInt(13) + 1));
						}
					} else if(x == halfWidth - 1 || x == halfWidth) {
						this.placeBlockWithDir(x, y, z, Block.netherrack.blockID, 0);
						this.placeBlockWithDir(x, y + 1, z, Block.fire.blockID, 0);
					} else {
						this.placeBlockWithDir(x, y + 1, z, TropicraftBlocks.tropicsSingleSlab.blockID, 2);
					}
				} else if(z == 2) {
					if(x == 0 || x == width - 1) {
						this.placeBlockWithDir(x, y + 1, z, TropicraftBlocks.tropicsFence.blockID, 0);
					} else {
						this.placeBlockWithDir(x, y + 1, z, TropicraftBlocks.tropicsSingleSlab.blockID, 2);
					}
				} else if(z % 2 == 1) {
					if(x == 0 || x == width - 1) {
						this.placeBlockWithDir(x, y + 1, z, TropicraftBlocks.tropicsFence.blockID, 0);
					}
				} else {
					if(x == 0 || x == width - 1) {
						this.placeBlockWithDir(x, y + 1, z, TropicraftBlocks.tikiTorch.blockID, 1);
						this.placeBlockWithDir(x, y + 2, z, TropicraftBlocks.tikiTorch.blockID, 1);
						this.placeBlockWithDir(x, y + 3, z, TropicraftBlocks.tikiTorch.blockID, 0);
					} else if(x != halfWidth - 1 && x != halfWidth) {
						this.placeBlockWithDir(x, y + 1, z, TropicraftBlocks.palmStairs.blockID, this.DIR);
						if(!hasGennedTunnel) {
							generateTunnel(x, y, z);
							hasGennedTunnel = true;
						}
					}
				}
			}
		}
		
		return false;
	}
	
	private void generateTunnel(int i, int j, int k) {
		int depth = rand.nextInt(5) + 8;
		for(int y = 0; y < depth; y++)
		{
			this.placeBlockWithDir(i, j - y, k, 0, 0);
		}
		
		j -= depth;
		
		int length = rand.nextInt(20) + 30;
		int dir = rand.nextInt(4);
		
		for(int x = 0; x < length; x++) {
			switch(dir) {
				case 0:
					i++;
					break;
				case 1:
					k++;
					break;
				case 2:
					i--;
					break;
				case 3:
					k--;
					break;
			}
			if(rand.nextInt(3) == 0) {
				j += rand.nextInt(3) - 1;
			}
			
			this.placeBlockWithDir(i, j, k, 0, 0);
			this.placeBlockWithDir(i, j + 1, k, 0, 0);
			this.placeBlockWithDir(i, j + 2, k, 0, 0);
			
			if(rand.nextInt(5) == 0) {
				dir = rand.nextInt(4);
			}
		}
		
		this.placeBlockWithDir(i, j, k, TropicraftBlocks.bambooChest.blockID, 0);
		
		TileEntityBambooChest chest = (TileEntityBambooChest)this.getTEWithDir(i, j, k);
		
		if(chest != null) {
			chest.setInventorySlotContents(0, this.randLoot());
		}
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
	
	private int getTerrainHeightWithDir(int i, int k) {
		switch(this.DIR) {
			case 2:
				return this.getTerrainHeightAt(this.x + i, this.z + k);
			case 0:
				return this.getTerrainHeightAt(this.x + k, this.z + i);
			case 3:
				return this.getTerrainHeightAt(this.x - i, this.z - k);
			case 1:
				return this.getTerrainHeightAt(this.x - k, this.z - i);
		}
		return 64;
	}
	
}
