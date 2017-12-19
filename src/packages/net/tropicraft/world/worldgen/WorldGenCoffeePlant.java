package net.tropicraft.world.worldgen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.ForgeDirection;
import net.tropicraft.mods.TropicraftMod;

public class WorldGenCoffeePlant extends WorldGenerator {
	private static final ForgeDirection[] cardinalDirections = {
		ForgeDirection.NORTH, ForgeDirection.EAST,
		ForgeDirection.SOUTH, ForgeDirection.WEST
	};
	
	@Override
	public boolean generate(World world, Random random, int x, int y, int z) {
		int nx = (x + random.nextInt(8)) - random.nextInt(8);
		int nz = (z + random.nextInt(8)) - random.nextInt(8);
		// we pass in the terrain height, so no random y variation desired
		int ny = y;
		
		if (!world.isAirBlock(nx, ny, nz) || world.getBlockId(nx, ny-1, nz) != Block.grass.blockID) {
			return false;
		}
		
		ForgeDirection viableDirection = ForgeDirection.UNKNOWN;
		
		// scan for existing water
		for (ForgeDirection dir: cardinalDirections) {
			int neighborx = nx+dir.offsetX;
			int neighborz = nz+dir.offsetZ;
			
			if (world.getBlockMaterial(neighborx, ny-1, neighborz) == Material.water) {
				viableDirection = dir;
				break;
			}
			
		}
		
		if (viableDirection == ForgeDirection.UNKNOWN) {
			// scan for places to put a water source block
			for (ForgeDirection dir: cardinalDirections) {
				int neighborx = nx+dir.offsetX;
				int neighborz = nz+dir.offsetZ;

				// isAirBlock call for ny-2 is to prevent a waterfall from spawning
				if (!world.isAirBlock(neighborx, ny, neighborz)
						|| world.getBlockId(neighborx, ny-1, neighborz) != Block.grass.blockID
						|| world.isAirBlock(neighborx, ny-2, neighborz)) {
					continue;
				}
				
				// check if the water block we'd place would be enclosed by grass (don't
				// want accidental waterfalls)
				boolean surrounded = true;
				
				for (ForgeDirection surroundingDir: cardinalDirections) {
					int surroundingx = neighborx+surroundingDir.offsetX;
					int surroundingz = neighborz+surroundingDir.offsetZ;
					
					if (!world.isAirBlock(surroundingx, ny, surroundingz)
						|| world.getBlockId(surroundingx, ny-1, surroundingz) != Block.grass.blockID) {
						surrounded = false;
						break;
					}
				}
				
				if (surrounded) {
					viableDirection = dir;
					break;
				}
			}
		}
		
		if (viableDirection == ForgeDirection.UNKNOWN) {
			return false;
		}
		
		world.setBlockAndMetadata(nx+viableDirection.offsetX, ny-1, nz+viableDirection.offsetZ, Block.waterStill.blockID, 0);
		world.setBlockAndMetadata(nx, ny-1, nz, Block.tilledField.blockID, 7);
		
		for (int i = 0; i < 3; ++i) {
			if (world.isAirBlock(nx, ny+i, nz)) {
				world.setBlockAndMetadata(nx, ny+i, nz, TropicraftMod.coffeePlant.blockID, 6);
			} else {
				break;
			}
		}
		
		return true;
	}

}
