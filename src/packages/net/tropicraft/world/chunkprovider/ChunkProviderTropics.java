package net.tropicraft.world.chunkprovider;

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
import net.tropicraft.mods.TropicraftMod;
import net.tropicraft.world.biomes.BiomeGenTropicraft;
import net.tropicraft.world.mapgen.MapGenKoaVillage;
import net.tropicraft.world.mapgen.MapGenTropicsCaves;
import net.tropicraft.world.mapgen.MapGenVolcano;
import net.tropicraft.world.worldgen.WorldGenBamboo;
import net.tropicraft.world.worldgen.WorldGenBentRainforestTree;
import net.tropicraft.world.worldgen.WorldGenCoffeePlant;
import net.tropicraft.world.worldgen.WorldGenEIH;
import net.tropicraft.world.worldgen.WorldGenHomeTree;
import net.tropicraft.world.worldgen.WorldGenMedRTree;
import net.tropicraft.world.worldgen.WorldGenTCUndergrowth;
import net.tropicraft.world.worldgen.WorldGenTallFlower;
import net.tropicraft.world.worldgen.WorldGenTallRTree;
import net.tropicraft.world.worldgen.WorldGenTropicraftCoral;
import net.tropicraft.world.worldgen.WorldGenTropicraftCurvedPalm;
import net.tropicraft.world.worldgen.WorldGenTropicraftFlowers;
import net.tropicraft.world.worldgen.WorldGenTropicraftFruitTrees;
import net.tropicraft.world.worldgen.WorldGenTropicraftLargePalmTrees;
import net.tropicraft.world.worldgen.WorldGenTropicraftNormalPalms;
import net.tropicraft.world.worldgen.WorldGenTropicsTreasure;
import net.tropicraft.world.worldgen.WorldGenTualang;

import java.util.List;
import java.util.Random;

public class ChunkProviderTropics implements IChunkProvider
{
	private Random rand;
	private MapGenKoaVillage villageGenerator;
	private NoiseGeneratorOctaves noiseGen1;
	private NoiseGeneratorOctaves noiseGen2;
	private NoiseGeneratorOctaves noiseGen3;
	private NoiseGeneratorOctaves noiseGen4;
	public NoiseGeneratorOctaves noiseGen5;
	public NoiseGeneratorOctaves noiseGen6;
	protected WorldGenerator eudialyteGen;
	protected WorldGenerator zirconGen;
	protected WorldGenerator azuriteGen;
	protected WorldGenerator ironGen;
	protected WorldGenerator coalGen;
	protected WorldGenerator lapisGen;
	public MapGenTropicsCaves cavegen;
	public MapGenVolcano volcanoGen;
	private World worldObj;
	private final boolean mapFeaturesEnabled;
	private double noiseArray[];
	public static int water;
	final int heightShift = 7;
	final int xShift = 11;
	
	/**
	 * The random chance (1/this number) that a home tree will generate
	 */
	private final int HOME_TREE_RARITY = 350;		

	private BiomeGenTropicraft biomesForGeneration[];

	double noise3[];
	double noise1[];
	double noise2[];
	double noise5[];
	double noise6[];
	float field_35388_l[];
	int unusedIntArray32x32[][];

	public ChunkProviderTropics(World world, long l, boolean flag)
	{
		unusedIntArray32x32 = new int[32][32];
		villageGenerator = new MapGenKoaVillage();
		water = TropicraftMod.waterStillTropics.blockID;
		cavegen = new MapGenTropicsCaves();
		volcanoGen = new MapGenVolcano();
		worldObj = world;
		mapFeaturesEnabled = flag;
		rand = new Random(l);
		noiseGen1 = new NoiseGeneratorOctaves(rand, 16);
		noiseGen2 = new NoiseGeneratorOctaves(rand, 16);
		noiseGen3 = new NoiseGeneratorOctaves(rand, 8);
		noiseGen4 = new NoiseGeneratorOctaves(rand, 4);
		noiseGen5 = new NoiseGeneratorOctaves(rand, 10);
		noiseGen6 = new NoiseGeneratorOctaves(rand, 16);
		coalGen = new WorldGenMinable(Block.oreCoal.blockID, 16);
		lapisGen = new WorldGenMinable(Block.oreLapis.blockID, 6);
		ironGen = new WorldGenMinable(Block.oreIron.blockID, 8);
		eudialyteGen = new WorldGenMinable(TropicraftMod.oreEudialyte.blockID, 6);
		zirconGen = new WorldGenMinable(TropicraftMod.oreZircon.blockID, 4);
		//System.out.println("Initialized");
		azuriteGen = new WorldGenMinable(TropicraftMod.oreAzurite.blockID, 2);
	}

	public void generateTerrain(int par1, int par2, byte[] par3ArrayOfByte)
	{
		water = TropicraftMod.waterStillTropics.blockID;
		//	System.out.println("GENC");
		byte var4 = 4;
		byte var5 = 16;
		byte var6 = 63;
		int var7 = var4 + 1;
		byte var8 = 17;
		int var9 = var4 + 1;
		this.biomesForGeneration = (BiomeGenTropicraft[])this.worldObj.getWorldChunkManager().getBiomesForGeneration(this.biomesForGeneration, par1 * 4 - 2, par2 * 4 - 2, var7 + 5, var9 + 5);
		this.noiseArray = this.initializeNoiseField(this.noiseArray, par1 * var4, 0, par2 * var4, var7, var8, var9);

		for (int var10 = 0; var10 < var4; ++var10)
		{
			for (int var11 = 0; var11 < var4; ++var11)
			{
				for (int var12 = 0; var12 < var5; ++var12)
				{
					double var13 = 0.125D;
					double var15 = this.noiseArray[((var10 + 0) * var9 + var11 + 0) * var8 + var12 + 0];
					double var17 = this.noiseArray[((var10 + 0) * var9 + var11 + 1) * var8 + var12 + 0];
					double var19 = this.noiseArray[((var10 + 1) * var9 + var11 + 0) * var8 + var12 + 0];
					double var21 = this.noiseArray[((var10 + 1) * var9 + var11 + 1) * var8 + var12 + 0];
					double var23 = (this.noiseArray[((var10 + 0) * var9 + var11 + 0) * var8 + var12 + 1] - var15) * var13;
					double var25 = (this.noiseArray[((var10 + 0) * var9 + var11 + 1) * var8 + var12 + 1] - var17) * var13;
					double var27 = (this.noiseArray[((var10 + 1) * var9 + var11 + 0) * var8 + var12 + 1] - var19) * var13;
					double var29 = (this.noiseArray[((var10 + 1) * var9 + var11 + 1) * var8 + var12 + 1] - var21) * var13;

					for (int var31 = 0; var31 < 8; ++var31)
					{
						double var32 = 0.25D;
						double var34 = var15;
						double var36 = var17;
						double var38 = (var19 - var15) * var32;
						double var40 = (var21 - var17) * var32;

						for (int var42 = 0; var42 < 4; ++var42)
						{
							int var43 = var42 + var10 * 4 << 11 | 0 + var11 * 4 << 7 | var12 * 8 + var31;
							short var44 = 128;
							var43 -= var44;
							double var45 = 0.25D;
							double var49 = (var36 - var34) * var45;
							double var47 = var34 - var49;

							for (int var51 = 0; var51 < 4; ++var51)
							{
								if ((var47 += var49) > 0.0D)
								{
									par3ArrayOfByte[var43 += var44] = (byte)Block.stone.blockID;
								}
								else if (var12 * 8 + var31 < var6)
								{
									par3ArrayOfByte[var43 += var44] = (byte)(TropicraftMod.waterStillTropics.blockID & 0xff);
								}
								else
								{
									par3ArrayOfByte[var43 += var44] = 0;
								}
							}

							var34 += var38;
							var36 += var40;
						}

						var15 += var23;
						var17 += var25;
						var19 += var27;
						var21 += var29;
					}
				}
			}
		}
	}

	/*
	 * 
	 * This is from the old ChunkProviderTropics, in case there are any more discrepancies
	 * 
	 * public void generateTerrain(int i, int j, byte abyte0[])
	{
		water = Block.waterStill.blockID;
		water = mod_tropicraft.waterStillTropics.blockID;
		//	water = mod_tropicraft.bambooBl.blockID;
		byte byte0 = 4;
		int k = worldObj.worldHeight / 8;
		int l = worldObj.seaLevel;
		int i1 = byte0 + 1;
		int j1 = worldObj.worldHeight / 8 + 1;
		int k1 = byte0 + 1;
		biomesForGeneration = (BiomeGenTropicraft[])worldObj.getWorldChunkManager().getBiomesForGeneration(biomesForGeneration, i * 4 - 2, j * 4 - 2, i1 + 5, k1 + 5);
		field_4180_q = initializeNoiseField(field_4180_q, i * byte0, 0, j * byte0, i1, j1, k1);
		for(int l1 = 0; l1 < byte0; l1++)
		{
			for(int i2 = 0; i2 < byte0; i2++)
			{
				for(int j2 = 0; j2 < k; j2++)
				{
					double d = 0.125D;
					double d1 = field_4180_q[((l1 + 0) * k1 + (i2 + 0)) * j1 + (j2 + 0)];
					double d2 = field_4180_q[((l1 + 0) * k1 + (i2 + 1)) * j1 + (j2 + 0)];
					double d3 = field_4180_q[((l1 + 1) * k1 + (i2 + 0)) * j1 + (j2 + 0)];
					double d4 = field_4180_q[((l1 + 1) * k1 + (i2 + 1)) * j1 + (j2 + 0)];
					double d5 = (field_4180_q[((l1 + 0) * k1 + (i2 + 0)) * j1 + (j2 + 1)] - d1) * d;
					double d6 = (field_4180_q[((l1 + 0) * k1 + (i2 + 1)) * j1 + (j2 + 1)] - d2) * d;
					double d7 = (field_4180_q[((l1 + 1) * k1 + (i2 + 0)) * j1 + (j2 + 1)] - d3) * d;
					double d8 = (field_4180_q[((l1 + 1) * k1 + (i2 + 1)) * j1 + (j2 + 1)] - d4) * d;
					for(int k2 = 0; k2 < 8; k2++)
					{
						double d9 = 0.25D;
						double d10 = d1;
						double d11 = d2;
						double d12 = (d3 - d1) * d9;
						double d13 = (d4 - d2) * d9;
						for(int l2 = 0; l2 < 4; l2++)
						{
							int i3 = l2 + l1 * 4 << worldObj.xShift | 0 + i2 * 4 << worldObj.heightShift | j2 * 8 + k2;
							int j3 = 1 << worldObj.heightShift;
							i3 -= j3;
							double d14 = 0.25D;
							double d15 = d10;
							double d16 = (d11 - d10) * d14;
							d15 -= d16;
							for(int k3 = 0; k3 < 4; k3++)
							{
								if((d15 += d16) > 0.0D)
								{
									abyte0[i3 += j3] = (byte)Block.stone.blockID;
									continue;
								}
								if(j2 * 8 + k2 < l)
								{
									abyte0[i3 += j3] = (byte)(mod_tropicraft.waterStillTropics.blockID & 0xff);
								} else
								{
									abyte0[i3 += j3] = 0;
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
	 */


	public void replaceBlocksForBiome(int i, int j, byte abyte0[], BiomeGenTropicraft abiomegenbase[])
	{
		int a = -1;
		water = TropicraftMod.waterStillTropics.blockID;

		boolean flag = false;

		int k = (int)63;
		double d = 0.03125D;
		for(int l = 0; l < 16; l++)
		{
			for(int i1 = 0; i1 < 16; i1++)
			{

				BiomeGenTropicraft biome = abiomegenbase[i1 + l * 16];
				int k1 = -1;
				byte top = biome.topBlock;
				byte filler = biome.fillerBlock;

				byte btop = biome.beachTopBlock;
				byte bfiller = biome.beachFillerBlock;
				boolean bdrop = biome.beachLayerLowered;

				int sandDepth = rand.nextInt(2) + 2;

				for(int l1 = 128 - 1; l1 >= 0; l1--)
				{
					int i2 = (i1 * 16 + l) * 128 + l1;

					byte block = abyte0[i2];

					if(l1 <= 0)
					{
						abyte0[i2] = (byte)Block.bedrock.blockID;
						continue;
					}

					if(block == 0 || block == (byte)(TropicraftMod.waterStillTropics.blockID & 0xff))
					{
						a = 0;
						continue;
					}

					if(a >= 0 && a < 5)
					{
						byte blockUsed = (byte)Block.stone.blockID;
						if(a == 0 && l1 < 63 + 3)
						{
							flag = true;
						}
						if(flag)
						{
							if(btop != Block.sand.blockID)
							{
								if(a == 0)
								{
									blockUsed = btop;
								}
								else if(a < 5)
								{
									blockUsed = bfiller;
								}
							}
							else
							{
								if(a < sandDepth)
								{
									blockUsed = btop;
								}
								else if(a < 5)
								{
									blockUsed = bfiller;
								}
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
							else
							{
								if(a < sandDepth)
								{
									blockUsed = top;
								}
								else if(a < 5)
								{
									blockUsed = filler;
								}
							}
						}
						abyte0[i2] = blockUsed;
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

	@Override
	public Chunk loadChunk(int i, int j)
	{
		return provideChunk(i, j);
	}

	@Override
	public Chunk provideChunk(int i, int j)
	{
		rand.setSeed((long)i * 0x4f9939f508L + (long)j * 0x1ef1565bd5L);
		byte abyte0[] = new byte[32768];
		generateTerrain(i, j, abyte0);
		biomesForGeneration = (BiomeGenTropicraft[])worldObj.getWorldChunkManager().loadBlockGeneratorData(biomesForGeneration, i * 16, j * 16, 16, 16);
		replaceBlocksForBiome(i, j, abyte0, biomesForGeneration);
		if (TropicraftMod.generateVolcanoes) {
			volcanoGen.generate(worldObj, i, j, abyte0);
		}
		cavegen.generate(this, worldObj, i, j, abyte0);
		villageGenerator.generate(this, worldObj, i, j, abyte0);
		Chunk chunk = new Chunk(worldObj, abyte0, i, j);
		chunk.generateSkylightMap();
		//chunk.
		return chunk;
	}

	private double[] initializeNoiseField(double[] par1ArrayOfDouble, int par2, int par3, int par4, int par5, int par6, int par7)
	{
		if (par1ArrayOfDouble == null)
		{
			par1ArrayOfDouble = new double[par5 * par6 * par7];
		}

		if (this.field_35388_l == null)
		{
			this.field_35388_l = new float[25];

			for (int var8 = -2; var8 <= 2; ++var8)
			{
				for (int var9 = -2; var9 <= 2; ++var9)
				{
					float var10 = 10.0F / MathHelper.sqrt_float((float)(var8 * var8 + var9 * var9) + 0.2F);
					this.field_35388_l[var8 + 2 + (var9 + 2) * 5] = var10;
				}
			}
		}

		double var44 = 684.412D;
		double var45 = 684.412D;
		this.noise5 = this.noiseGen5.generateNoiseOctaves(this.noise5, par2, par4, par5, par7, 1.121D, 1.121D, 0.5D);
		this.noise6 = this.noiseGen6.generateNoiseOctaves(this.noise6, par2, par4, par5, par7, 200.0D, 200.0D, 0.5D);
		this.noise3 = this.noiseGen3.generateNoiseOctaves(this.noise3, par2, par3, par4, par5, par6, par7, var44 / 80, var45 / 30, var44 / 80);
		this.noise1 = this.noiseGen1.generateNoiseOctaves(this.noise1, par2, par3, par4, par5, par6, par7, var44, var45 / 20, var44);
		this.noise2 = this.noiseGen2.generateNoiseOctaves(this.noise2, par2, par3, par4, par5, par6, par7, var44, var45, var44);
		boolean var43 = false;
		boolean var42 = false;
		int var12 = 0;
		int var13 = 0;

		for (int var14 = 0; var14 < par5; ++var14)
		{
			for (int var15 = 0; var15 < par7; ++var15)
			{
				float var16 = 0.0F;
				float var17 = 0.0F;
				float var18 = 0.0F;
				byte var19 = 2;
				BiomeGenBase var20 = this.biomesForGeneration[var14 + 2 + (var15 + 2) * (par5 + 5)];

				for (int var21 = -var19; var21 <= var19; ++var21)
				{
					for (int var22 = -var19; var22 <= var19; ++var22)
					{
						BiomeGenBase var23 = this.biomesForGeneration[var14 + var21 + 2 + (var15 + var22 + 2) * (par5 + 5)];
						float var24 = this.field_35388_l[var21 + 2 + (var22 + 2) * 5] / (var23.minHeight + 2.0F);

						if (var23.minHeight > var20.minHeight)
						{
							var24 /= 2.0F;
						}

						var16 += var23.maxHeight * var24;
						var17 += var23.minHeight * var24;
						var18 += var24;
					}
				}

				var16 /= var18;
				var17 /= var18;
				var16 = var16 * 0.9F + 0.1F;
				var17 = (var17 * 4.0F - 1.0F) / 8.0F;
				double var47 = this.noise6[var13] / 8000.0D;

				if (var47 < 0.0D)
				{
					var47 = -var47 * 0.3D;
				}

				var47 = var47 * 3.0D - 2.0D;

				if (var47 < 0.0D)
				{
					var47 /= 2.0D;

					if (var47 < -1.0D)
					{
						var47 = -1.0D;
					}

					var47 /= 1.4D;
					var47 /= 2.0D;
				}
				else
				{
					if (var47 > 1.0D)
					{
						var47 = 1.0D;
					}

					var47 /= 8.0D;
				}

				++var13;

				for (int var46 = 0; var46 < par6; ++var46)
				{
					double var48 = (double)var17;
					double var26 = (double)var16;
					var48 += var47 * 0.2D;
					var48 = var48 * (double)par6 / 16.0D;
					double var28 = (double)par6 / 2.0D + var48 * 4.0D;
					double var30 = 0.0D;
					double var32 = ((double)var46 - var28) * 12.0D * 128.0D / 128.0D / var26;

					if (var32 < 0.0D)
					{
						var32 *= 4.0D;
					}

					double var34 = this.noise1[var12] / 512.0D;
					double var36 = this.noise2[var12] / 512.0D;
					double var38 = (this.noise3[var12] / 10.0D + 1.0D) / 2.0D;

					if (var38 < 0.0D)
					{
						var30 = var34;
					}
					else if (var38 > 1.0D)
					{
						var30 = var36;
					}
					else
					{
						var30 = var34 + (var36 - var34) * var38;
					}

					var30 -= var32;

					if (var46 > par6 - 4)
					{
						double var40 = (double)((float)(var46 - (par6 - 4)) / 3.0F);
						var30 = var30 * (1.0D - var40) + -10.0D * var40;
					}

					par1ArrayOfDouble[var12] = var30;
					++var12;
				}
			}
		}

		return par1ArrayOfDouble;
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
			int i1 = x +rand.nextInt(16);
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

	@Override
	public boolean chunkExists(int i, int j)
	{
		return true;
	}

	private boolean isRainforestBiome(BiomeGenBase biome)
	{
		return biome == BiomeGenTropicraft.rainforestHills || biome == BiomeGenTropicraft.rainforestMountains || biome == BiomeGenTropicraft.rainforestPlains;
	}

	@Override
	public void populate(IChunkProvider ichunkprovider, int i, int j)
	{
		BlockSand.fallInstantly = true;
		int cx = i * 16;
		int cz = j * 16;
		BiomeGenTropicraft biome = (BiomeGenTropicraft) worldObj.getWorldChunkManager().getBiomeGenAt(cx, cz);
		rand.setSeed(worldObj.getSeed());
		long l1 = (rand.nextLong() / 2L) * 2L + 1L;
		long l2 = (rand.nextLong() / 2L) * 2L + 1L;
		rand.setSeed((long)i * l1 + (long)j * l2 ^ worldObj.getSeed());

		boolean flag = villageGenerator.generateStructuresInChunk(worldObj, rand, i, j);

		cavegen.generateWater(worldObj, i, j);

		if (TropicraftMod.generateVolcanoes) {
			ChunkCoordinates volcanoCoords = volcanoGen.getVolcanoNear(worldObj, i, j);
			if(volcanoCoords != null) {
				worldObj.setBlock(volcanoCoords.posX, 1, volcanoCoords.posZ, TropicraftMod.volcanoHelper.blockID);
			}	
		}	

		int x;
		int z;
		generateOres(cx,cz);     

		new WorldGenTallGrass(Block.tallGrass.blockID, 1).generate(worldObj, rand, cx + 6 + rand.nextInt(4), rand.nextInt(15) + 64, cz + 6 + rand.nextInt(4));

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


			if(rand.nextInt(2) == 0)
			{
				for(int r = 0; r < 6; r++){
					x = rand.nextInt(16) + cx + 8;
					z = rand.nextInt(16) + cz + 8;
					new WorldGenTallRTree(worldObj, rand, true).generate(x, getTerrainHeightAt(x, z), z);		
				}
			}


			for(int r = 0 ; r < 4; r ++){
				x = rand.nextInt(16) + cx + 8;
				z = rand.nextInt(16) + cz + 8;
				new WorldGenMedRTree(worldObj, rand, true).generate(x, getTerrainHeightAt(x, z), z);		
			}

			if(rand.nextBoolean())
			{
				x = rand.nextInt(16) + cx + 8;
				z = rand.nextInt(16) + cz + 8;
				new WorldGenBentRainforestTree(worldObj, rand).generate(x, getTerrainHeightAt(x, z), z);
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
			
			for(int aaa = 0; aaa < 25; aaa++)
			{
				x = rand.nextInt(16) + cx + 8;
				z = rand.nextInt(16) + cz + 8;
				(new WorldGenTCUndergrowth(3, 0)).generate(worldObj, rand, x, getTerrainHeightAt(x,z), z);
			}
			
			for (int a = 0; a < 1; a++) {
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


		for (int j4 = 0; j4 < 10; j4++)
		{
			int j9 = cx + rand.nextInt(16) + 8;
			int k13 = rand.nextInt(128);
			int l16 = cz + rand.nextInt(16) + 8;
			(new WorldGenReed()).generate(worldObj, rand, j9, k13, l16);
		}

		if(biome == BiomeGenTropicraft.tropics || biome == BiomeGenTropicraft.rainforestPlains)
			if(rand.nextInt(320) == 0)
			{
				x = rand.nextInt(16) + cx + 8;
				z = rand.nextInt(16) + cz + 8;
				new WorldGenTropicsTreasure().generate(worldObj, rand, x, getTerrainHeightAt(x, z), z);
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
			(new WorldGenTropicraftFlowers(TropicraftMod.flowerCollection1.blockID, isRainforestBiome(biome))).generate(worldObj, rand, k, l, i1);
		}

		l = rand.nextInt(62) + 64;
		(new WorldGenTallFlower(TropicraftMod.irisFlower.blockID)).generate(worldObj, rand, k, l, i1);

		l = rand.nextInt(62) + 64;
		(new WorldGenTallFlower(TropicraftMod.pineappleFlower.blockID)).generate(worldObj, rand, k, l, i1);

		l = rand.nextInt(62) + 64;
		(new WorldGenBamboo()).generate(worldObj, rand, k, l, i1);

		if(rand.nextBoolean())
		{
			x = rand.nextInt(16) + cx + 8;
			z = rand.nextInt(16) + cz + 8;
			new WorldGenClay(rand.nextInt(9) + 12).generate(worldObj, rand, x, rand.nextInt(16) + 56, z);
		}

		for (int a = 0; a < 38; a++) {
			l = rand.nextInt(62) + 64;

			if (rand.nextInt(5) == 0) {
				(new WorldGenTropicraftLargePalmTrees()).generate(worldObj, rand, k, l, i1);
			} else if (rand.nextInt(5) == 1 || rand.nextInt(5) == 2) {
				(new WorldGenTropicraftCurvedPalm()).generate(worldObj, rand, k, l, i1);
			} else {
				(new WorldGenTropicraftNormalPalms()).generate(worldObj, rand, k, l, i1);
			}
		}


		worldObj.getChunkFromChunkCoords(i, j).updateSkylight();

		SpawnerAnimals.performWorldGenSpawning(worldObj, biome, cx + 8, cz + 8, 16, 16, rand);
		BlockSand.fallInstantly = false;
	}

	@Override
	public boolean saveChunks(boolean flag, IProgressUpdate iprogressupdate)
	{
		return true;
	}

	@Override
	public boolean unload100OldestChunks()
	{
		return false;
	}

	@Override
	public boolean canSave()
	{
		return true;
	}

	@Override
	public String makeString()
	{
		return TropicraftMod.canOtherModsGenerateInRealm ? "RandomLevelSource" : "TropicsLevelSource";
	}

	/**
	 * Returns a list of creatures of the specified type that can spawn at the given location.
	 */
	@Override
	public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4)
	{
		BiomeGenBase var5 = this.worldObj.getBiomeGenForCoords(par2, par4);
		return var5 == null ? null : var5.getSpawnableList(par1EnumCreatureType);
	}

	@Override
	public ChunkPosition findClosestStructure(World world, String s, int i, int j, int k)
	{
		return null;
	}

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

	@Override
	public int getLoadedChunkCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void recreateStructures(int var1, int var2) {
		// TODO Auto-generated method stub
		
	}

}
