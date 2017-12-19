package tropicraft.world;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeCacheBlock;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;
import tropicraft.blocks.TropicraftBlocks;
import tropicraft.config.TropicraftConfig;
import tropicraft.world.biomes.BiomeGenTropicraft;
import tropicraft.world.worldgen.WorldGenBamboo;
import tropicraft.world.worldgen.WorldGenEIH;
import tropicraft.world.worldgen.WorldGenTallFlower;
import tropicraft.world.worldgen.WorldGenTropicraftCurvedPalm;
import tropicraft.world.worldgen.WorldGenTropicraftFlowers;
import tropicraft.world.worldgen.WorldGenTropicraftLargePalmTrees;
import tropicraft.world.worldgen.WorldGenTropicraftNormalPalms;
import CoroUtil.OldUtil;
import cpw.mods.fml.common.IWorldGenerator;

/**
 * Used by Tropicraft to generate Tropistuff in the overworld. Eventually may be expanded to having our own biomes? Currently just
 * generates tropithings in existing biomes. Possibly modded biomes as well as vanilla? Not sure!
 *
 */
public class TCWorldGenerator implements IWorldGenerator {

	public TCWorldGenerator() {
	}

	@Override
	/**
	 * Call the old generate method
	 */
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		generateSurface(world, random, chunkX, chunkZ);
	}

	/**
	 * The generation method used in the older versions of tropicraft to generate things in the
	 * main world
	 * @param world World to generate in
	 * @param random Random!!!
	 * @param chunkX chunkX
	 * @param chunkZ chunkZ
	 */
	public void generateSurface(World world, Random random, int chunkX, int chunkZ) {

		int cx = chunkX;
		int cz = chunkZ;
		
		if (TropicraftConfig.genTropicraftInOverworld) {
			// Convert to block coords rather than chunk coords
			chunkX *= 16;
			chunkZ *= 16;

			if (world.provider.dimensionId == 0 && world.provider.terrainType != world.provider.terrainType.FLAT) {
				int k = chunkX + random.nextInt(16) + 8;
				int l = random.nextInt(62) + 64;
				int i1 = chunkZ + random.nextInt(16) + 8;

				if (TropicraftConfig.genTropicraftFlowersInOverworld) {
					for (int j3 = 0; j3 < 10; j3++) {
						l = random.nextInt(62) + 64;
						(new WorldGenTropicraftFlowers(TropicraftBlocks.tropicsFlowers.blockID, false)).generate(world, random, k, l, i1);
					}	
				}

				if (TropicraftConfig.genTropicraftEIHInOverworld && random.nextInt(27) == 0) {
					l = random.nextInt(62) + 64;
					(new WorldGenEIH()).generate(world, random, k, l, i1);
				}

				//*********** HERE TO BOTTOM ARE THINGS NECESSARY TO GET TO THE TROPICS ***********//

				if (TropicraftConfig.genPalmsInOverworld) {
					BiomeGenBase biome = world.getWorldChunkManager().getBiomeGenAt(cx, cz);					

					if ((TropicraftConfig.genOverworldPalmsInBeachOnly && biome == BiomeGenBase.beach) || !TropicraftConfig.genOverworldPalmsInBeachOnly)
						if (TropicraftConfig.palmChanceOfGenInOverworld < 0 || random.nextFloat() < (float)(TropicraftConfig.palmChanceOfGenInOverworld / 100F)) {
							for (int j3 = 0; j3 < TropicraftConfig.palmPopulationFactorInOverworld; j3++) {
								l = random.nextInt(62) + 64;

								if (random.nextInt(5) == 0) {
									(new WorldGenTropicraftLargePalmTrees()).generate(world, random, k, l, i1);
								} else if (random.nextInt(5) < 3) {
									(new WorldGenTropicraftCurvedPalm()).generate(world, random, k, l, i1);
								} else {
									(new WorldGenTropicraftNormalPalms()).generate(world, random, k, l, i1);
								}
							}
						}
				}

				// Pineapples
				if (TropicraftConfig.genPineapplesInOverworld) {
					l = random.nextInt(62) + 64;
					(new WorldGenTallFlower(7, 9)).generate(world, random, k, l, i1);
				}

				// Bamboo
				if (TropicraftConfig.genBambooInOverworld && random.nextInt(3) == 0) {
					l = random.nextInt(62) + 64;
					(new WorldGenBamboo()).generate(world, random, k, l, i1);
				}
			}
		}
	}
}