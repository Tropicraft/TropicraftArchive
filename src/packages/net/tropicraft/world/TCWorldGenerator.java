package net.tropicraft.world;

import net.minecraft.src.c_CoroWeatherUtil;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeCacheBlock;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;
import net.tropicraft.mods.TropicraftMod;
import net.tropicraft.world.biomes.BiomeGenTropicraft;
import net.tropicraft.world.worldgen.WorldGenBamboo;
import net.tropicraft.world.worldgen.WorldGenEIH;
import net.tropicraft.world.worldgen.WorldGenTallFlower;
import net.tropicraft.world.worldgen.WorldGenTropicraftCurvedPalm;
import net.tropicraft.world.worldgen.WorldGenTropicraftFlowers;
import net.tropicraft.world.worldgen.WorldGenTropicraftLargePalmTrees;
import net.tropicraft.world.worldgen.WorldGenTropicraftNormalPalms;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.IWorldGenerator;

public class TCWorldGenerator implements IWorldGenerator {

	private static final List<BiomeGenBase> rainBiomes = Arrays.asList(BiomeGenBase.desert, BiomeGenBase.frozenOcean, BiomeGenBase.frozenRiver, BiomeGenBase.taiga);

	public TCWorldGenerator() {
	}

	@Override
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

		//convert to block coords rather than chunk coords
		chunkX *= 16;
		chunkZ *= 16;

		if (world.provider.dimensionId == 0 && world.provider.terrainType == world.provider.terrainType.DEFAULT) {
			int k = chunkX + random.nextInt(16) + 8;
			int l = random.nextInt(62) + 64;
			int i1 = chunkZ + random.nextInt(16) + 8;
			if (TropicraftMod.generateTropicalStuffInMainWorld) {

				for (int j3 = 0; j3 < 10; j3++) {
					l = random.nextInt(62) + 64;
					(new WorldGenTropicraftFlowers(TropicraftMod.flowerCollection1.blockID, false)).generate(world, random, k, l, i1);
				}

				//	(new WorldGenTallFlower(TropicraftMod.irisFlower.blockID)).generate(world, random, k, l, i1);

				l = random.nextInt(62) + 64;

				if (random.nextInt(27) == 0) {
					(new WorldGenEIH()).generate(world, random, k, l, i1);
				}
			}

			//This is a fix to workaround the broken biomecache, always returns ocean biome for some reason, possible forge issue
			BiomeCache biomeCache = (BiomeCache)c_CoroWeatherUtil.getPrivateValueBoth(WorldChunkManager.class, world.getWorldChunkManager(), "f", "biomeCache");
			BiomeCacheBlock var5 = new BiomeCacheBlock(biomeCache, k, i1);
			BiomeGenBase biome = var5.getBiomeGenAt(k, i1);

			// Generate necessary items in the main world no matter what
			// (for making pina colada and beach chair)
			//if (biome == BiomeGenBase.desert || biome == BiomeGenBase.beach)
			{
				//			System.out.println("ya is desert or beach" + k + " " + i1);
				for (int j3 = 0; j3 < 8; j3++) {
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

//			if (biome == BiomeGenBase.plains
//					|| biome == BiomeGenBase.forest
//					|| biome == BiomeGenBase.extremeHills) {
				l = random.nextInt(62) + 64;
				(new WorldGenTallFlower(TropicraftMod.pineappleFlower.blockID)).generate(world, random, k, l, i1);
		//	}
//
		//	if (biome == BiomeGenBase.jungle || biome == BiomeGenBase.jungleHills
		//			|| biome == BiomeGenBase.plains) {
				l = random.nextInt(62) + 64;
				(new WorldGenBamboo()).generate(world, random, k, l, i1);
		//	}

		}

	}

	/**
	 * Is the biome at the given coordinates in the given world a rainforest biome?
	 * @param world World to be checked for rainforest biome
	 * @param k chunkX
	 * @param i1 chunkZ
	 * @return Whether the biome at the given coordinates is a rainforest biome in the given world
	 */
	private boolean isRainforest(World world, int k, int i1) {
		return world.getWorldChunkManager().getBiomeGenAt(k, i1) == BiomeGenTropicraft.rainforestHills
				|| world.getWorldChunkManager().getBiomeGenAt(k, i1) == BiomeGenTropicraft.rainforestMountains
				|| world.getWorldChunkManager().getBiomeGenAt(k, i1) == BiomeGenTropicraft.rainforestPlains;
	}

}
