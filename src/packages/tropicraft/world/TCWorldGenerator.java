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
import CoroAI.c_CoroAIUtil;
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

		// convert to block coords rather than chunk coords
		chunkX *= 16;
		chunkZ *= 16;

		if (world.provider.dimensionId == 0 && world.provider.terrainType == world.provider.terrainType.DEFAULT) {
			int k = chunkX + random.nextInt(16) + 8;
			int l = random.nextInt(62) + 64;
			int i1 = chunkZ + random.nextInt(16) + 8;
			if (TropicraftConfig.genTropicraftFlowersInOverworld) {
				for (int j3 = 0; j3 < 10; j3++) {
					l = random.nextInt(62) + 64;
					(new WorldGenTropicraftFlowers(TropicraftBlocks.tropicsFlowers.blockID, false)).generate(world, random, k, l, i1);
				}	
			}

			if (random.nextInt(27) == 0 && TropicraftConfig.genTropicraftEIHInOverworld) {
				l = random.nextInt(62) + 64;
				(new WorldGenEIH()).generate(world, random, k, l, i1);
			}

			// This is a fix to workaround the broken biomecache, always returns ocean biome for some reason, possible forge issue
			BiomeCache biomeCache = (BiomeCache)c_CoroAIUtil.getPrivateValueSRGMCP(WorldChunkManager.class, world.getWorldChunkManager(), "field_76942_f", "biomeCache");
			BiomeCacheBlock var5 = new BiomeCacheBlock(biomeCache, k, i1);
			BiomeGenBase biome = var5.getBiomeGenAt(k, i1);

			// Generate necessary items in the main world no matter what
			// (for making pina colada and beach chair)
			for (int j3 = 0; j3 < 3; j3++) {
				l = random.nextInt(62) + 64;

				if (random.nextInt(5) == 0) {
					(new WorldGenTropicraftLargePalmTrees()).generate(world, random, k, l, i1);
				} else if (random.nextInt(5) < 3) {
					(new WorldGenTropicraftCurvedPalm()).generate(world, random, k, l, i1);
				} else {
					(new WorldGenTropicraftNormalPalms()).generate(world, random, k, l, i1);
				}
			}

			// pineapples
			l = random.nextInt(62) + 64;
			(new WorldGenTallFlower(7, 9)).generate(world, random, k, l, i1);

			// bamboo
			l = random.nextInt(62) + 64;
			(new WorldGenBamboo()).generate(world, random, k, l, i1);
		}

	}
}