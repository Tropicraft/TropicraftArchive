package tropicraft.world.chunk;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.feature.WorldGenClay;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenReed;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenerator;
import tropicraft.blocks.TropicraftBlocks;
import tropicraft.config.TropicraftConfig;
import tropicraft.world.biomes.BiomeGenTropicraft;
import tropicraft.world.mapgen.MapGenKoaVillage;
import tropicraft.world.mapgen.MapGenTropicsCaves;
import tropicraft.world.mapgen.MapGenUndergroundGrove;
import tropicraft.world.mapgen.MapGenVolcano;
import tropicraft.world.structures.KoaVillageGenerator;
import tropicraft.world.worldgen.WorldGenBamboo;
import tropicraft.world.worldgen.WorldGenBentRainforestTree;
import tropicraft.world.worldgen.WorldGenCoffeePlant;
import tropicraft.world.worldgen.WorldGenEIH;
import tropicraft.world.worldgen.WorldGenForestAltarRuin;
import tropicraft.world.worldgen.WorldGenHomeTree;
import tropicraft.world.worldgen.WorldGenSunkenShip;
import tropicraft.world.worldgen.WorldGenTCUndergrowth;
import tropicraft.world.worldgen.WorldGenTallFlower;
import tropicraft.world.worldgen.WorldGenTallRTree;
import tropicraft.world.worldgen.WorldGenTropiSkellyDung;
import tropicraft.world.worldgen.WorldGenTropicraftCoral;
import tropicraft.world.worldgen.WorldGenTropicraftCurvedPalm;
import tropicraft.world.worldgen.WorldGenTropicraftFlowers;
import tropicraft.world.worldgen.WorldGenTropicraftFruitTrees;
import tropicraft.world.worldgen.WorldGenTropicraftLargePalmTrees;
import tropicraft.world.worldgen.WorldGenTropicraftNormalPalms;
import tropicraft.world.worldgen.WorldGenTropicsTreasure;
import tropicraft.world.worldgen.WorldGenTualang;
import tropicraft.world.worldgen.WorldGenUndergroundFruitTree;
import tropicraft.world.worldgen.WorldGenUpTree;
import tropicraft.world.worldgen.WorldGenWaterfall;

public class ChunkProviderTropicraft implements IChunkProvider { //NOTE: THIS WILL MOST LIKELY BE COMPLETELY REDONE
    
    private static final int HOME_TREE_RARITY = 350;
    
	private World worldObj;
	private long seed;
	protected Random rand;
    private BiomeGenBase[] biomesForGeneration;
    private MapGenUndergroundGrove groveGen;
    private MapGenTropicsCaves caveGenerator;
	private MapGenKoaVillage villageGenerator;
	private MapGenVolcano volcanoGen;
    
    private NoiseGeneratorOctaves noiseGen1;
    private NoiseGeneratorOctaves noiseGen2;
    private NoiseGeneratorOctaves noiseGen3;
    private NoiseGeneratorOctaves noiseGen4;
    private NoiseGeneratorOctaves noiseGen5;
    
	private WorldGenerator eudialyteGen;
	private WorldGenerator zirconGen;
	private WorldGenerator azuriteGen;
	private WorldGenerator ironGen;
	private WorldGenerator coalGen;
	private WorldGenerator lapisGen;
	
	public ChunkProviderTropicraft(World worldObj, long seed, boolean par4) {
		this.worldObj = worldObj;
		this.rand = new Random(seed);
		
        this.noiseGen1 = new NoiseGeneratorOctaves(this.rand, 16);
        this.noiseGen2 = new NoiseGeneratorOctaves(this.rand, 16);
        this.noiseGen3 = new NoiseGeneratorOctaves(this.rand, 8);
        this.noiseGen4 = new NoiseGeneratorOctaves(this.rand, 10);
        this.noiseGen5 = new NoiseGeneratorOctaves(this.rand, 16);
        
        caveGenerator = new MapGenTropicsCaves();
        villageGenerator = new MapGenKoaVillage();
        volcanoGen = new MapGenVolcano(worldObj, true);
        groveGen = new MapGenUndergroundGrove(worldObj);
        
		coalGen = new WorldGenMinable(Block.oreCoal.blockID, 16);
		lapisGen = new WorldGenMinable(Block.oreLapis.blockID, 6);
		ironGen = new WorldGenMinable(Block.oreIron.blockID, 8);
		eudialyteGen = new WorldGenMinable(TropicraftBlocks.tropiOres.blockID, 0, 6, Block.stone.blockID);
		zirconGen = new WorldGenMinable(TropicraftBlocks.tropiOres.blockID, 1, 4, Block.stone.blockID);
		azuriteGen = new WorldGenMinable(TropicraftBlocks.tropiOres.blockID, 2, 2, Block.stone.blockID);
        
		this.seed = seed;

	    
	    
	}

	public Chunk provideChunk(int x, int z)
    {
        this.rand.setSeed((long)x * 341873128712L + (long)z * 132897987541L);
        short[] blocks = new short[32768];
        byte[] metas = new byte[32768];
        this.generateTerrain(x, z, blocks, metas);
        this.biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, x * 16, z * 16, 16, 16);
        this.replaceBlocksForBiome(x, z, blocks, metas, this.biomesForGeneration);
        this.volcanoGen.generate(x, z, blocks, metas);
        this.groveGen.generate(x, z, blocks, metas);
        this.caveGenerator.generate(this, this.worldObj, x, z, blocks);
        this.villageGenerator.generate(this, worldObj, x, z, null);

        Chunk chunk = new Chunk(this.worldObj, blocks, metas, x, z);
        byte[] abyte1 = chunk.getBiomeArray();

        for (int k = 0; k < abyte1.length; ++k)
        {
            abyte1[k] = (byte)this.biomesForGeneration[k].biomeID;
        }

        chunk.generateSkylightMap();
        return chunk;
    }
	
	private void generateTerrain(int x, int z, short[] blocks, byte[] metas)
	{
		byte chunkSizeGenXZ = 4;
        byte chunkSizeGenY = 16;
        byte midHeight = 63;
        int k = chunkSizeGenXZ + 1;
        byte b3 = 17;
        int l = chunkSizeGenXZ + 1;
        this.biomesForGeneration = this.worldObj.getWorldChunkManager().getBiomesForGeneration(this.biomesForGeneration, x * 4 - 2, z * 4 - 2, k + 5, l + 5);
        double[] noiseArray = null;
        noiseArray = this.initializeNoiseField(noiseArray, x * chunkSizeGenXZ, 0, z * chunkSizeGenXZ, k, b3, l);

        for (int i1 = 0; i1 < chunkSizeGenXZ; ++i1)
        {
            for (int j1 = 0; j1 < chunkSizeGenXZ; ++j1)
            {
                for (int k1 = 0; k1 < chunkSizeGenY; ++k1)
                {
                    double d0 = 0.125D;
                    double d1 = noiseArray[((i1 + 0) * l + j1 + 0) * b3 + k1 + 0];
                    double d2 = noiseArray[((i1 + 0) * l + j1 + 1) * b3 + k1 + 0];
                    double d3 = noiseArray[((i1 + 1) * l + j1 + 0) * b3 + k1 + 0];
                    double d4 = noiseArray[((i1 + 1) * l + j1 + 1) * b3 + k1 + 0];
                    double d5 = (noiseArray[((i1 + 0) * l + j1 + 0) * b3 + k1 + 1] - d1) * d0;
                    double d6 = (noiseArray[((i1 + 0) * l + j1 + 1) * b3 + k1 + 1] - d2) * d0;
                    double d7 = (noiseArray[((i1 + 1) * l + j1 + 0) * b3 + k1 + 1] - d3) * d0;
                    double d8 = (noiseArray[((i1 + 1) * l + j1 + 1) * b3 + k1 + 1] - d4) * d0;

                    for (int l1 = 0; l1 < 8; ++l1)
                    {
                        double d9 = 0.25D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;

                        for (int i2 = 0; i2 < 4; ++i2)
                        {
                            double d14 = 0.25D;
                            double d15 = (d11 - d10) * d14;
                            double d16 = d10 - d15;

                            for (int k2 = 0; k2 < 4; ++k2)
                            {
                            	int index = (k1 * 8 + l1) << 8 | (j1 * 4 + k2) << 4 | (i1 * 4 + i2);
                                if ((d16 += d15) > 0.0D)
                                {
                                    blocks[index] = (short)Block.stone.blockID;
                                }
                                else if (k1 * 8 + l1 < midHeight)
                                {
                                    blocks[index] = (short)TropicraftBlocks.tropicsWaterStationary.blockID;
                                }
                                else
                                {
                                    blocks[index] = 0;
                                }
                            }

                            d10 += d12;
                            d11 += d13;
                        }

                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }
                }
            }
        }
	}
	
	private float[] parabolicField;
	
	private double[] initializeNoiseField(double[] par1ArrayOfDouble, int par2, int par3, int par4, int par5, int par6, int par7)
    {
        if (par1ArrayOfDouble == null)
        {
            par1ArrayOfDouble = new double[par5 * par6 * par7];
        }

        if (this.parabolicField == null)
        {
            this.parabolicField = new float[25];

            for (int k1 = -2; k1 <= 2; ++k1)
            {
                for (int l1 = -2; l1 <= 2; ++l1)
                {
                    float f = 10.0F / MathHelper.sqrt_float((float)(k1 * k1 + l1 * l1) + 0.2F);
                    this.parabolicField[k1 + 2 + (l1 + 2) * 5] = f;
                }
            }
        }

        double d0 = 684.412D;
        double d1 = 684.412D;
        double[] noise1 = null;
        double[] noise2 = null;
        double[] noise3 = null;
        double[] noise4 = null;
        double[] noise5 = null;
        noise1 = this.noiseGen1.generateNoiseOctaves(noise1, par2, par3, par4, par5, par6, par7, d0, d1, d0);
        noise2 = this.noiseGen2.generateNoiseOctaves(noise2, par2, par3, par4, par5, par6, par7, d0, d1, d0);
        noise3 = this.noiseGen3.generateNoiseOctaves(noise3, par2, par3, par4, par5, par6, par7, d0 / 80.0D, d1 / 160.0D, d0 / 80.0D);
        noise4 = this.noiseGen4.generateNoiseOctaves(noise4, par2, par4, par5, par7, 1.121D, 1.121D, 0.5D);
        noise5 = this.noiseGen5.generateNoiseOctaves(noise5, par2, par4, par5, par7, 200.0D, 200.0D, 0.5D);
        boolean flag = false;
        boolean flag1 = false;
        int i2 = 0;
        int j2 = 0;

        for (int k2 = 0; k2 < par5; ++k2)
        {
            for (int l2 = 0; l2 < par7; ++l2)
            {
                float f1 = 0.0F;
                float f2 = 0.0F;
                float f3 = 0.0F;
                byte b0 = 2;
                BiomeGenBase biomegenbase = this.biomesForGeneration[k2 + 2 + (l2 + 2) * (par5 + 5)];

                for (int i3 = -b0; i3 <= b0; ++i3)
                {
                    for (int j3 = -b0; j3 <= b0; ++j3)
                    {
                        BiomeGenBase biomegenbase1 = this.biomesForGeneration[k2 + i3 + 2 + (l2 + j3 + 2) * (par5 + 5)];
                        float f4 = this.parabolicField[i3 + 2 + (j3 + 2) * 5] / (biomegenbase1.minHeight + 2.0F);

                        if (biomegenbase1.minHeight > biomegenbase.minHeight)
                        {
                            f4 /= 2.0F;
                        }

                        f1 += biomegenbase1.maxHeight * f4;
                        f2 += biomegenbase1.minHeight * f4;
                        f3 += f4;
                    }
                }

                f1 /= f3;
                f2 /= f3;
                f1 = f1 * 0.9F + 0.1F;
                f2 = (f2 * 4.0F - 1.0F) / 8.0F;
                double d2 = noise5[j2] / 8000.0D;

                if (d2 < 0.0D)
                {
                    d2 = -d2 * 0.3D;
                }

                d2 = d2 * 3.0D - 2.0D;

                if (d2 < 0.0D)
                {
                    d2 /= 2.0D;

                    if (d2 < -1.0D)
                    {
                        d2 = -1.0D;
                    }

                    d2 /= 1.4D;
                    d2 /= 2.0D;
                }
                else
                {
                    if (d2 > 1.0D)
                    {
                        d2 = 1.0D;
                    }

                    d2 /= 8.0D;
                }

                ++j2;

                for (int k3 = 0; k3 < par6; ++k3)
                {
                    double d3 = (double)f2;
                    double d4 = (double)f1;
                    d3 += d2 * 0.2D;
                    d3 = d3 * (double)par6 / 16.0D;
                    double d5 = (double)par6 / 2.0D + d3 * 4.0D;
                    double d6 = 0.0D;
                    double d7 = ((double)k3 - d5) * 12.0D * 128.0D / 128.0D / d4;

                    if (d7 < 0.0D)
                    {
                        d7 *= 4.0D;
                    }

                    double d8 = noise1[i2] / 512.0D;
                    double d9 = noise2[i2] / 512.0D;
                    double d10 = (noise3[i2] / 10.0D + 1.0D) / 2.0D;

                    if (d10 < 0.0D)
                    {
                        d6 = d8;
                    }
                    else if (d10 > 1.0D)
                    {
                        d6 = d9;
                    }
                    else
                    {
                        d6 = d8 + (d9 - d8) * d10;
                    }

                    d6 -= d7;

                    if (k3 > par6 - 4)
                    {
                        double d11 = (double)((float)(k3 - (par6 - 4)) / 3.0F);
                        d6 = d6 * (1.0D - d11) + -10.0D * d11;
                    }

                    par1ArrayOfDouble[i2] = d6;
                    ++i2;
                }
            }
        }

        return par1ArrayOfDouble;
    }
	
	public void replaceBlocksForBiome(int x, int z, short[] blocks, byte[] metas, BiomeGenBase[] biomes)
    {
        int a = -1;

		boolean flag = false;

		int k = (int)63;
		double d = 0.03125D;
		for(int l = 0; l < 16; l++)
		{
			for(int i1 = 0; i1 < 16; i1++)
			{
				BiomeGenTropicraft biome = (BiomeGenTropicraft)biomes[i1 + l * 16];
				short top = biome.topBlock;
				short filler = biome.fillerBlock;

				short btop = (short) biome.sandID;
				short bfiller = (short) btop;

				for(int l1 = 128 - 1; l1 >= 0; l1--)
				{
					int i2 = l1 << 8 | l << 4 | i1;

					short block = blocks[i2];

					if(l1 <= 0)
					{
						blocks[i2] = (short)Block.bedrock.blockID;
						continue;
					}

					if(block == 0 || block == (TropicraftBlocks.tropicsWaterStationary.blockID))
					{
						a = 0;
						continue;
					}

					if(a >= 0 && a < 5)
					{
						short blockUsed = (short) Block.stone.blockID;
						if(a == 0 && l1 < 63 + 3)
						{
							flag = true;
						}
						if(flag)
						{
							if(a < 5) {
								blockUsed = btop;
							}
						}
						else
						{
							if(top != Block.sand.blockID)
							{
								if(a == 0)
								{
									blockUsed = top;
								}
								else if(a < 5)
								{
									blockUsed = filler;
								}
							}
						}
						blocks[i2] = blockUsed;
						a++;
						continue;
					}

					flag = false;
					a = -1;

				}

				a = -1;

			}

		}
    }
	
    public void populate(IChunkProvider par1IChunkProvider, int i, int j)
    {
        BlockSand.fallInstantly = true;
        int cx = i * 16;
		int cz = j * 16;
		BiomeGenTropicraft biome = (BiomeGenTropicraft) worldObj.getWorldChunkManager().getBiomeGenAt(cx, cz);
		rand.setSeed(worldObj.getSeed());
		long l1 = (rand.nextLong() / 2L) * 2L + 1L;
		long l2 = (rand.nextLong() / 2L) * 2L + 1L;
		rand.setSeed((long)i * l1 + (long)j * l2 ^ worldObj.getSeed());

		//boolean flag = villageGenerator.generateStructuresInChunk(worldObj, rand, i, j);
		
		boolean flag = villageGenerator.generateStructuresInChunk(worldObj, rand, i, j);

		if (TropicraftConfig.genVolcanoes) {
			ChunkCoordinates volcanoCoords = volcanoGen.getVolcanoNear(worldObj, i, j);
			if(volcanoCoords != null) {
				//worldObj.setBlock(volcanoCoords.posX, 1, volcanoCoords.posZ, TropicraftBlocks.volcanoHelper.blockID); TODO
			}	
		}	

		int x;
		int z;
		generateOres(cx,cz);     

		new WorldGenTallGrass(Block.tallGrass.blockID, 1).generate(worldObj, rand, cx + 6 + rand.nextInt(4), rand.nextInt(15) + 64, cz + 6 + rand.nextInt(4));

		if(this.groveGen.isActive) {
			for(int r = 0; r < 4; r++) {
				x = rand.nextInt(16) + cx + 8;
				z = rand.nextInt(16) + cz + 8;
				new WorldGenUndergroundFruitTree(true, rand.nextInt(3)).generate(worldObj, rand, x, groveGen.getHeightAt(x, z), z);
			}
		}
		
		if(isRainforestBiome(biome))
		{     			
			if(rand.nextInt(HOME_TREE_RARITY) == 0)
			{
				x = rand.nextInt(16) + cx + 8;
				z = rand.nextInt(16) + cz + 8;
				new WorldGenHomeTree(worldObj, rand).generate(x, 0, z);
			}		

			//		for(int a = 0; a < 5; a++)
				//			new WorldGenTallGrass(Block.tallGrass.blockID, 1).generate(worldObj, rand, cx + 6 + rand.nextInt(4), rand.nextInt(15) + 64, cz + 6 + rand.nextInt(4));   
			if(rand.nextInt(70) == 0) {
				x = rand.nextInt(16) + cx + 8;
				z = rand.nextInt(16) + cz + 8;
				new WorldGenForestAltarRuin(worldObj, rand).generate(x, getTerrainHeightAt(x, z), z);
			}

			if(rand.nextInt(2) == 0)
			{
				for(int r = 0; r < 6; r++){
					x = rand.nextInt(16) + cx + 8;
					z = rand.nextInt(16) + cz + 8;
					new WorldGenTallRTree(worldObj, rand, true).generate(x, getTerrainHeightAt(x, z), z);		
				}
			}

			if(rand.nextBoolean())
			{
				x = rand.nextInt(16) + cx + 8;
				z = rand.nextInt(16) + cz + 8;
				new WorldGenBentRainforestTree(worldObj, rand).generate(x, getTerrainHeightAt(x, z), z);
			}

			if(rand.nextInt(2) == 0)
			{
				for(int r = 0; r < 2; r++){
					x = rand.nextInt(16) + cx + 8;
					z = rand.nextInt(16) + cz + 8;
					new WorldGenUpTree(worldObj, rand).generate(x, getTerrainHeightAt(x, z), z);		
				}
			}

			for(int a = 0; a < 4; a++)
			{
				x = rand.nextInt(16) + cx + 8;
				z = rand.nextInt(16) + cz + 8;
				new WorldGenTualang(worldObj, rand, 18, 9).generate(x, getTerrainHeightAt(x, z), z);
			}

			for(int a = 0; a < 4; a++)
			{
				x = rand.nextInt(16) + cx + 8;
				z = rand.nextInt(16) + cz + 8;
				new WorldGenTualang(worldObj, rand, 25, 10).generate(x, getTerrainHeightAt(x, z), z);
			}		
			
			// This used to be 25
			for(int aaa = 0; aaa < 20; aaa++)
			{
				x = rand.nextInt(16) + cx + 8;
				z = rand.nextInt(16) + cz + 8;
				(new WorldGenTCUndergrowth(3, 0)).generate(worldObj, rand, x, getTerrainHeightAt(x,z), z);
			}
			
			for (int a = 0; a < 3; a++) {
				x = rand.nextInt(8) + cx + 4;
				z = rand.nextInt(8) + cz + 4;
				new WorldGenCoffeePlant().generate(worldObj, rand, x, getTerrainHeightAt(x,z), z);
			}	

			//		for(int a = 0 ; a < 30; a++)
			//			(new WorldGenTCUndergrowth(mod_tropicraft.tropicLeaves.blockID, true)).generate(worldObj, rand, cx + 6 + rand.nextInt(4), rand.nextInt(15) + 64, cz + 6 + rand.nextInt(4));

		}

		//for(int c = 0; c < biome.biomeDecorator.treesPerChunk; c++){
		//	WorldGenerator var5 = biome.getRandomWorldGenForTrees(rand);
		//	var5.setScale(1.0D, 1.0D, 1.0D);
		//	var5.generate(worldObj, rand, cx, this.worldObj.getHeightValue(cx, cz), cz);
		//}

		if(rand.nextBoolean()) {
			// Generate the same type of tree in a 4x4 chunk area
			int treeType = new Random((long)(i >> 2) << 32 | (long)(j >> 2)).nextInt(4);
			for(int a = 0; a < 2; a++)
			{
				x = rand.nextInt(16) + cx + 8;
				z = rand.nextInt(16) + cz + 8;
				new WorldGenTropicraftFruitTrees(true, treeType).generate(worldObj, rand, x, getTerrainHeightAt(x, z), z);
			}
		}

		if(rand.nextInt(25) == 0)
		{
			int x1 = cx + rand.nextInt(16) + 8;
			int y1 = rand.nextInt(56);
			int z1 = cz + rand.nextInt(16) + 8;
			new WorldGenTropiSkellyDung(worldObj, rand).generate(x1, y1, z1);
		}

		if(rand.nextInt(200) == 0) {
			x = rand.nextInt(16) + cx + 8;
			z = rand.nextInt(16) + cz + 8;
			new WorldGenSunkenShip(worldObj, rand).generate(x, 0, z);
		}
		
		for (int j4 = 0; j4 < 10; j4++)
		{
			int j9 = cx + rand.nextInt(16) + 8;
			int k13 = rand.nextInt(128);
			int l16 = cz + rand.nextInt(16) + 8;
			(new WorldGenReed()).generate(worldObj, rand, j9, k13, l16);
		}

		if(biome == BiomeGenTropicraft.tropics || biome == BiomeGenTropicraft.rainforestPlains) {
			if(rand.nextInt(320) == 0)
			{
				x = rand.nextInt(16) + cx + 8;
				z = rand.nextInt(16) + cz + 8;
				new WorldGenTropicsTreasure().generate(worldObj, rand, x, getTerrainHeightAt(x, z), z);
			}
		}

		int k = cx + rand.nextInt(16) + 8;
		int l = rand.nextInt(62) + 64;
		int i1 = cz + rand.nextInt(16) + 8;

		if (rand.nextInt(27) == 0) {
			(new WorldGenEIH()).generate(worldObj, rand, k, l, i1);
		}		

		if(rand.nextInt(3) == 0 && (biome == BiomeGenTropicraft.tropicsOcean || biome == BiomeGenTropicraft.ocean))
		{
			new WorldGenTropicraftCoral().generate(worldObj, rand, cx + 6 + rand.nextInt(4), 64, cz + 6 + rand.nextInt(4));
		}

		for (int a = 0; a < 10; a++) {
			l = rand.nextInt(62) + 64;
			(new WorldGenTropicraftFlowers(TropicraftBlocks.tropicsFlowers.blockID, isRainforestBiome(biome))).generate(worldObj, rand, k, l, i1);
		}
		
		l = rand.nextInt(62) + 64;
		(new WorldGenTallFlower(7, 9)).generate(worldObj, rand, k, l, i1);

		l = rand.nextInt(62) + 64;
		(new WorldGenTallFlower(8, 15)).generate(worldObj, rand, k, l, i1);

		l = rand.nextInt(62) + 64;
		(new WorldGenBamboo()).generate(worldObj, rand, k, l, i1);

		if(rand.nextBoolean())
		{
			x = rand.nextInt(16) + cx + 8;
			z = rand.nextInt(16) + cz + 8;
			new WorldGenClay(rand.nextInt(9) + 12).generate(worldObj, rand, x, rand.nextInt(16) + 56, z);
		}

		// Used to be 38 before 1.6
		for (int a = 0; a < 10; a++) {
			l = rand.nextInt(62) + 64;

			if (rand.nextInt(5) == 0) {
				(new WorldGenTropicraftLargePalmTrees()).generate(worldObj, rand, k, l, i1);
			} else if (rand.nextInt(5) == 1 || rand.nextInt(5) == 2) {
				(new WorldGenTropicraftCurvedPalm()).generate(worldObj, rand, k, l, i1);
			} else {
				(new WorldGenTropicraftNormalPalms()).generate(worldObj, rand, k, l, i1);
			}
		}
		
		for(int a = 0; a < 25; a++) {
			int x1 = cx + rand.nextInt(16) + 8;
			int y1 = rand.nextInt(100) + 30;
			int z1 = cz + rand.nextInt(16) + 8;
			new WorldGenWaterfall(worldObj, rand).generate(x1, y1, z1);
		}

	//TODO just removed also for 1.6	worldObj.getChunkFromChunkCoords(i, j).updateSkylight();

		SpawnerAnimals.performWorldGenSpawning(worldObj, biome, cx + 8, cz + 8, 16, 16, rand);

        BlockSand.fallInstantly = false;
    }

	private boolean isRainforestBiome(BiomeGenBase biome)
	{
		return BiomeGenTropicraft.rainforestBiomes.contains(biome);
	}
	
	/**
	 * 
	 * @param x
	 * @param z
	 * @return
	 */
	int getTerrainHeightAt(int x, int z)
	{
		for(int y = 128; y > 0; y--)
		{
			int id = worldObj.getBlockId(x, y, z);
			if(id != 0 && id != Block.tallGrass.blockID && id != Block.snow.blockID && id != Block.leaves.blockID && id != Block.wood.blockID)
			{
				return y + 1;
			}
		}
		return 0;
	}
	
	protected void generateOres(int x, int z)
	{
		genStandardOre1(19, coalGen, 0, 128, x, z);
		genStandardOre1(10, ironGen, 0, 128 / 2,x,z);
		genStandardOre1(15, zirconGen, 0, 128 / 4, x, z);
		genStandardOre1(20, eudialyteGen, 0, 128 / 2,x,z);
		genStandardOre1(10, azuriteGen, 0, 128, x, z);
		genStandardOre2(1, lapisGen, 128 / 8, 128 / 8,x,z);
	}

	protected void genStandardOre1(int i, WorldGenerator worldgenerator, int j, int k, int x, int z)
	{
		for (int l = 0; l < i; l++)
		{
			int i1 = x + rand.nextInt(16);
			int j1 = rand.nextInt(k - j) + j;
			int k1 = z + rand.nextInt(16);
			worldgenerator.generate(worldObj, rand, i1, j1, k1);
		}
	}

	protected void genStandardOre2(int i, WorldGenerator worldgenerator, int j, int k,int x, int z)
	{
		for (int l = 0; l < i; l++)
		{
			int i1 = x + rand.nextInt(16);
			int j1 = rand.nextInt(k) + rand.nextInt(k) + (j - k);
			int k1 = z + rand.nextInt(16);
			worldgenerator.generate(worldObj, rand, i1, j1, k1);
		}
	}
	
    public String makeString()
    {
        return "TropiLevelSource";
    }

	@Override
	public boolean chunkExists(int x, int z) {
		return true;
	}

	@Override
	public Chunk loadChunk(int x, int z) {
		return this.provideChunk(x, z);
	}

	@Override
	public boolean saveChunks(boolean flag, IProgressUpdate iprogressupdate) {
		return true;
	}

	@Override
	public boolean unloadQueuedChunks() {
		return false;
	}

	@Override
	public boolean canSave() {
		return true;
	}

	@Override
	public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4) {
		//if (!par1EnumCreatureType.equals(EnumCreatureType.waterCreature)) System.out.println("getPossibleCreatures: " + par1EnumCreatureType);
		BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(par2, par4);
        return biomegenbase == null ? null : biomegenbase.getSpawnableList(par1EnumCreatureType);
	}

	@Override
	public ChunkPosition findClosestStructure(World world, String s, int i, int j, int k) {
		return null;
	}

	@Override
	public int getLoadedChunkCount() {
		return 0;
	}

	@Override
	public void recreateStructures(int i, int j) {
		
	}

	@Override

	/**
	 * Save extra data not associated with any Chunk.  Not saved during autosave, only during world unload.  Currently
	 * unimplemented.
	 */
	public void saveExtraData() { }
	
}
