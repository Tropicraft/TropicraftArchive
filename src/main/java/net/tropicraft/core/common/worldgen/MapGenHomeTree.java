package net.tropicraft.core.common.worldgen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockVine;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenBase;
import net.tropicraft.Info;
import net.tropicraft.core.common.biome.BiomeGenTropicraft;
import net.tropicraft.core.common.block.BlockTropicraftLeaves;
import net.tropicraft.core.common.block.BlockTropicraftLog;
import net.tropicraft.core.common.block.tileentity.TileEntityBambooChest;
import net.tropicraft.core.common.enums.TropicraftLeaves;
import net.tropicraft.core.common.enums.TropicraftLogs;
import net.tropicraft.core.registry.BlockRegistry;
import net.tropicraft.core.registry.ItemRegistry;

public class MapGenHomeTree extends MapGenBase {

	private class BranchNode 
	{
		public int x1, y1, z1;
		public int x2, y2, z2;

		public BranchNode(int i, int j, int k, int x, int y, int z)
		{
			x1 = i;
			y1 = j;
			z1 = k;
			x2 = x;
			y2 = y;
			z2 = z;
		}
	}
	
	@FunctionalInterface
	private interface Action {
		
		void act(TileEntity te, Random rand);
	}
	
	private static class TileEntityAction {
		private final BlockPos pos;
		private final Action action;

		public TileEntityAction(BlockPos pos, Action action) {
			this.pos = pos;
			this.action = action;
		}

		public BlockPos getPos() {
			return pos;
		}

		
		public Action getAction() {
			return action;
		}		
	}
	
	private static class SpawnerAction implements Action {

		private final String entityName;
		
		public SpawnerAction(String entityName) {
			this.entityName = entityName;
		}

		@Override
		public void act(TileEntity te, Random rand) {
			((TileEntityMobSpawner)te).getSpawnerBaseLogic().setEntityName(this.entityName);
		}
	}
	
	private Action chestLootAction = (te, rand) -> {
		TileEntityBambooChest chest = (TileEntityBambooChest) te;
		int treasure = rand.nextInt(6) + 4;
		for (int x = 0; x < treasure; x++) {
			chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), randLoot(rand));
		}
	};

	private static final int HOME_TREE_RARITY = 80;
	
	private static final Long2ObjectMap<List<TileEntityAction>> actionCache = new Long2ObjectOpenHashMap<>(1024);

	private final IBlockState wood = BlockRegistry.logs.getDefaultState().withProperty(BlockTropicraftLog.VARIANT, TropicraftLogs.MAHOGANY);
	private final IBlockState leaves = BlockRegistry.leaves.getDefaultState().withProperty(BlockTropicraftLeaves.VARIANT, TropicraftLeaves.KAPOK);

	private ArrayList<BranchNode> branchList = new ArrayList<BranchNode>();
	private int trunkRadius;
	
	@Override
	protected void recursiveGenerate(World worldIn, int cx, int cz, int p_180701_4_, int p_180701_5_, ChunkPrimer primer) {
		
		int bx = rand.nextInt(16) + (cx << 4) + 8;
		int by = 127;
		int bz = rand.nextInt(16) + (cz << 4) + 8;
		
		int rx = bx & 15;
		int rz = bz & 15;

		if(worldIn.getBiome(new BlockPos(bx, by, bz)) != BiomeGenTropicraft.tropics || rand.nextInt(HOME_TREE_RARITY) > 0) {
			return;
		}
		

		trunkRadius = rand.nextInt(3) + 7;

		for(int i = rx - trunkRadius; i < rx + trunkRadius && i < 16 && i >= 0; i++) {
			for(int j = rx - trunkRadius; j < rx + trunkRadius && j < 16 && j >= 0; j++) {
				int tHeight = primer.findGroundBlockIdx(i, j);
				if(tHeight < by) {
					by = tHeight;
				}
			}
		}

		/*		BiomeGenBase currentBiome = worldObj.getWorldChunkManager().getBiomeGenAt(i, k);
		if(!(currentBiome instanceof BiomeGenRainforest)) {
			return false;
		}*/

		int height = rand.nextInt(20) + 30;

		if (height + by + 12 > 255) {
			return;
		}

		System.err.println("HOME TREE INCOMING!" + " " + bx + " " + by + " " + bz);

		int[] top = generateTrunk(bx, by, bz, height, primer);

		generateBranches(top[0], top[1], height + by, primer);
	}

	public int[] generateTrunk(int x, int y, int z, int height, ChunkPrimer primer) {
		int bn = 0;
		int chanceToDisplace = 0;
		int xDisplace = rand.nextBoolean() ? 1 : -1;
		int zDisplace = rand.nextBoolean() ? 1 : -1;

		int trunkX = x;
		int trunkZ = z;

		for (int trunkY = y; trunkY < height + y; trunkY++) {
			chanceToDisplace++;
			genCircle(trunkX, trunkY, trunkZ, trunkRadius, trunkRadius - 3, wood, false, primer);
			if(trunkY == height + trunkY - 1 || ((trunkY - trunkY) % 6 == 0)) {
				genCircle(trunkX, trunkY, trunkZ, trunkRadius, 2, wood, false, primer);
				placeBlock(trunkX + 1, trunkY, trunkZ + 1, wood, false, primer);
				placeBlock(trunkX + 1, trunkY, trunkZ - 1, wood, false, primer);
				placeBlock(trunkX - 1, trunkY, trunkZ + 1, wood, false, primer);
				placeBlock(trunkX - 1, trunkY, trunkZ - 1, wood, false, primer);
				double angle = rand.nextDouble() * 3.141592D * 2D;
				if (rand.nextInt(3) == 0) {
					double length = rand.nextDouble() * trunkRadius - 4;
					int spawnerX = trunkX - 4 + rand.nextInt(9);
					int spawnerZ = trunkZ - 4 + rand.nextInt(9);
					primer.setBlockState(spawnerX, trunkY + 1, spawnerZ, Blocks.MOB_SPAWNER.getDefaultState());
					StringBuilder sb = new StringBuilder(String.format("%s.", Info.MODID));
					sb.append(rand.nextBoolean() ? "AshenHunter" : "Iguana");
					BlockPos spawnerPos = new BlockPos(spawnerX, trunkY + 1, spawnerZ);
					addAction(spawnerPos, new SpawnerAction(sb.toString()));
				}
			}
			IBlockState vineState = Blocks.VINE.getDefaultState().withProperty(BlockVine.SOUTH, Boolean.valueOf(true));
			placeBlock(trunkX, trunkY, trunkZ, wood, false, primer);
			placeBlock(trunkX + 1, trunkY, trunkZ, vineState, false, primer);
			placeBlock(trunkX - 1, trunkY, trunkZ, vineState, false, primer);
			placeBlock(trunkX, trunkY, trunkZ + 1, vineState, false, primer);
			placeBlock(trunkX, trunkY, trunkZ - 1, vineState, false, primer);
			if (rand.nextInt(6) == 0); {
				if (trunkY >= trunkY + height - 6) {
					int branches = rand.nextInt(4) + 2;
					for (int i = 0; i < branches; i++) {
						int branchLength = rand.nextInt(10) + 15;
						int branchX1 = trunkX;
						int branchZ1 = trunkZ;
						double angle = rand.nextDouble() * 3.141592D * 2D;
						branchX1 = (int)((trunkRadius * Math.sin(angle)) + branchX1);
						branchZ1 = (int)((trunkRadius * Math.cos(angle)) + branchZ1); 
						int branchX2 = (int)((branchLength * Math.sin(angle)) + branchX1);
						int branchZ2 = (int)((branchLength * Math.cos(angle)) + branchZ1); 
						int branchY2 = rand.nextInt(4) + 4;
						branchList.add(new BranchNode(branchX1, trunkY, branchZ1, branchX2, trunkY + branchY2, branchZ2));
						bn++;
					}
				}
			}
			if (rand.nextInt(6) + 4 <= chanceToDisplace && chanceToDisplace * 9 > trunkY) {
				if (rand.nextBoolean()) {
					trunkX += xDisplace;
					if (rand.nextBoolean()) {
						trunkZ += zDisplace;
					}
				} else if (rand.nextBoolean()) {
					trunkZ += zDisplace;
					if (rand.nextBoolean()) {
						trunkZ += xDisplace;
					}
				}
				chanceToDisplace = 0;
			}
			placeBlock(trunkX, trunkY, trunkZ, BlockRegistry.logs.getDefaultState(), false, primer);
		}
		BlockPos trunkPos = new BlockPos(trunkX - 1, height + y, trunkZ - 1);
		primer.setBlockState(trunkPos.getX(), trunkPos.getY(), trunkPos.getZ(), BlockRegistry.bambooChest.getDefaultState());
		addAction(trunkPos, chestLootAction);
		return new int[] {trunkX, trunkZ};
	}

	public void generateBranches(int topX, int topZ, int height, ChunkPrimer primer) {
		for(int x = 0; x < branchList.size(); x++) {
			BranchNode bnode = branchList.get(x);
			int lSize = 3;
			if(!TCGenBase.checkBlockLine(world, new int[] { bnode.x1, bnode.y1, bnode.z1 }, new int[] {bnode.x2, bnode.y2, bnode.z2 }, TCGenBase.STANDARD_ALLOWED_BLOCKS) 
					&& !TCGenBase.checkBlockLine(world, new int[] { bnode.x1 + 1, bnode.y1, bnode.z1 }, new int[] {bnode.x2 + 1, bnode.y2, bnode.z2 }, TCGenBase.STANDARD_ALLOWED_BLOCKS) 
					&& !TCGenBase.checkBlockLine(world, new int[] { bnode.x1 - 1, bnode.y1, bnode.z1 }, new int[] {bnode.x2 - 1, bnode.y2, bnode.z2 }, TCGenBase.STANDARD_ALLOWED_BLOCKS) 
					&& !TCGenBase.checkBlockLine(world, new int[] { bnode.x1, bnode.y1, bnode.z1 + 1 }, new int[] {bnode.x2, bnode.y2, bnode.z2 + 1 }, TCGenBase.STANDARD_ALLOWED_BLOCKS) 
					&& !TCGenBase.checkBlockLine(world, new int[] { bnode.x1, bnode.y1, bnode.z1 - 1 }, new int[] {bnode.x2, bnode.y2, bnode.z2 - 1 }, TCGenBase.STANDARD_ALLOWED_BLOCKS) 
					&& !TCGenBase.checkBlockLine(world, new int[] { bnode.x1, bnode.y1 - 1, bnode.z1 }, new int[] {bnode.x2, bnode.y2 - 1, bnode.z2 }, TCGenBase.STANDARD_ALLOWED_BLOCKS)) {
				continue;
			}
			placeBlockLine(new int[] { bnode.x1, bnode.y1, bnode.z1 }, new int[] {bnode.x2, bnode.y2, bnode.z2 }, wood, primer);
			placeBlockLine(new int[] { bnode.x1 + 1, bnode.y1, bnode.z1 }, new int[] {bnode.x2 + 1, bnode.y2, bnode.z2 }, wood, primer);
			placeBlockLine(new int[] { bnode.x1 - 1, bnode.y1, bnode.z1 }, new int[] {bnode.x2 - 1, bnode.y2, bnode.z2 }, wood, primer);
			placeBlockLine(new int[] { bnode.x1, bnode.y1, bnode.z1 + 1 }, new int[] {bnode.x2, bnode.y2, bnode.z2 + 1 }, wood, primer);
			placeBlockLine(new int[] { bnode.x1, bnode.y1, bnode.z1 - 1 }, new int[] {bnode.x2, bnode.y2, bnode.z2 - 1 }, wood, primer);
			placeBlockLine(new int[] { bnode.x1, bnode.y1 - 1, bnode.z1 }, new int[] {bnode.x2, bnode.y2 - 1, bnode.z2 }, wood, primer);
			if(bnode.y2 + 1 <= height) {
				placeBlockLine(new int[] { bnode.x1, bnode.y1 + 1, bnode.z1 }, new int[] {bnode.x2, bnode.y2 + 1, bnode.z2 }, wood, primer);
			}
			genLeafCircle(bnode.x2, bnode.y2 + 2, bnode.z2, lSize + 9, 0, leaves, true, primer);
			genLeafCircle(bnode.x2, bnode.y2 + 1, bnode.z2, lSize + 10, 0, leaves, true, primer);
			genLeafCircle(bnode.x2, bnode.y2, bnode.z2, lSize + 6, 0, leaves, true, primer);
			genLeafCircle(bnode.x2, bnode.y2 - 1, bnode.z2, lSize + 5, lSize + 3, leaves, true, primer);

		}

		int topBranches = rand.nextInt(6) + 6;
		/*for(int x = 0; x < topBranches; x++)
		{
			int branchLength = rand.nextInt(10) + 15;
			int baseDistance = rand.nextInt(trunkRadius - 3);
			int branchX1 = topX;
			int branchZ1 = topZ;
			double angle = rand.nextDouble() * 3.141592D * 2D;
			branchX1 = (int)((baseDistance * Math.sin(angle)) + branchX1);
			branchZ1 = (int)((baseDistance * Math.cos(angle)) + branchZ1); 
			int branchX2 = (int)((branchLength * Math.sin(angle)) + branchX1);
			int branchZ2 = (int)((branchLength * Math.cos(angle)) + branchZ1); 
			int branchHeight = height + rand.nextInt(4);
			int leafRadius = rand.nextInt(5) - 3;
			genTopBranch(branchX1, height, branchZ1, branchX1, height + rand.nextInt(4) + 4, branchZ1, topX, topZ);
			genLeafCircle(branchX1, branchHeight - 1, branchZ1, leafRadius + 5, leafRadius + 3, leafID, leafMeta, true);
			genLeafCircle(branchX1, branchHeight, branchZ1, leafRadius + 6, 0, leafID, leafMeta, true);
			genLeafCircle(branchX1, branchHeight + 1, branchZ1, leafRadius + 10, 0, leafID, leafMeta, true);
			genLeafCircle(branchX1, branchHeight + 2, branchZ1, leafRadius + 9, 0, leafID, leafMeta, true);
		}*/
	}

	public boolean genTopBranch(int i, int j, int k, int sX, int sY, int sZ, int topX, int topZ, ChunkPrimer primer) {
		ArrayList<Block> allowedBlocks = new ArrayList<>(TCGenBase.STANDARD_ALLOWED_BLOCKS);
		allowedBlocks.add(wood.getBlock());
		allowedBlocks.add(leaves.getBlock());
		allowedBlocks.add(Blocks.VINE);
		int branchSize = rand.nextInt(2) + 4;
		ArrayList<int[]> lines = new ArrayList<int[]>();
		for (int x = i - branchSize; x < i + branchSize; x++) {
			for (int z = k - branchSize; z < k + branchSize; z++) {
				if ((x - i) * (x - i) + (z - k) * (z - k) < branchSize * branchSize && (x - topX) * (x - topX) + (z - topZ) * (z - topZ) < trunkRadius * trunkRadius) {
					if (!TCGenBase.checkBlockLine(world, new int[] { x, j, z }, new int[] {  sX + (i - x), sY, sZ + (k - z) }, allowedBlocks)) {
						return false;
					}
				}
			}
		}
		for (int x = i - branchSize; x < i + branchSize; x++) {
			for (int z = k - branchSize; z < k + branchSize; z++) {
				if ((x - i) * (x - i) + (z - k) * (z - k) < branchSize * branchSize && (x - topX) * (x - topX) + (z - topZ) * (z - topZ) < trunkRadius * trunkRadius) {
					placeBlockLine(new int[] { x, j, z }, new int[] {  sX + (i - x), sY, sZ + (k - z) }, wood, primer);
				}
			}
		}
		return true;
	}

	public void genLeafCircle(int x, int y, int z, int outerRadius, int innerRadius, IBlockState state, boolean vines, ChunkPrimer primer) {
		int outerRadiusSquared = outerRadius * outerRadius;
		int innerRadiusSquared = innerRadius * innerRadius;

		for (int i = -outerRadius + x; i < outerRadius + x; i++) {
			for (int k = -outerRadius + z; k < outerRadius + z; k++) {
				double d = (x - i) * (x - i) + (z - k) * (z - k);
				if (d <= outerRadiusSquared && d >= innerRadiusSquared) {
					BlockPos pos = new BlockPos(i, y, k);
					if (world.isAirBlock(pos) || world.getBlockState(pos).getBlock() == state.getBlock()) {
						placeBlock(i, y, k, state, false, primer);
					}

					if (rand.nextInt(20) == 0 && vines) {
						genVines(i, y - 1, k, primer);
					}
				}
			}
		}
	} 

	public void genVines(int i, int j, int k, ChunkPrimer primer) {
		int length = rand.nextInt(15) + 8;
		int dir = rand.nextInt(4);
		IProperty direction = null;
		if (dir == 0) direction = BlockVine.NORTH;
		if (dir == 1) direction = BlockVine.SOUTH;
		if (dir == 2) direction = BlockVine.EAST;
		if (dir == 3) direction = BlockVine.WEST;

		for (int y = j; y > j - length; y--) {
			if (world.isAirBlock(new BlockPos(i, y, k))) {
				placeBlock(i, y, k, Blocks.VINE.getDefaultState().withProperty(direction, Boolean.valueOf(true)), false, primer);
			}
			else break;
		}
	}

	public boolean placeBlock(int i, int j, int k, IBlockState state, boolean force, ChunkPrimer primer) {
		Block bID = primer.getBlockState(i, j, k).getBlock();
		if(force || bID == Blocks.WATER || bID == Blocks.FLOWING_WATER || bID == BlockRegistry.tropicsWater 
				|| bID == Blocks.AIR) {
			primer.setBlockState(i, j, k, state);
			return true;
		}
		return false;
	}

	public boolean genCircle(int i, int j, int k, double outerRadius, double innerRadius, IBlockState state, boolean solid, ChunkPrimer primer) {
		boolean hasGenned = false;
		double outerRadiusSquared = outerRadius * outerRadius;
		double innerRadiusSquared = innerRadius * innerRadius;
		for (int x = (int)-outerRadius + i; x < (int)outerRadius + i; x++) {
			for (int z = (int)-outerRadius + k; z < (int)outerRadius + k; z++) {
				double d = (x - i) * (x - i) + (z - k) * (z - k);
				if (d <= outerRadiusSquared && d >= innerRadiusSquared) {
					Block bID = primer.getBlockState(x, j, z).getBlock();
					if ((bID == Blocks.AIR || bID == Blocks.WATER || bID == Blocks.FLOWING_WATER) 
							|| bID == BlockRegistry.tropicsWater 
							|| /*bID == TCBlockRegistry.tropicsWaterFlowing ||*/ solid)
					{
						if (placeBlock(x, j, z, state, solid, primer)) {
							hasGenned = true;
						}
					}
				}
			}
		}
		return hasGenned;
	}

	public ArrayList<int[]> placeBlockLine(int ai[], int ai1[], IBlockState state, ChunkPrimer primer)
	{
		ArrayList<int[]> places = new ArrayList<int[]>();
		int ai2[] = {
				0, 0, 0
		};
		byte byte0 = 0;
		int j = 0;
		for(; byte0 < 3; byte0++)
		{
			ai2[byte0] = ai1[byte0] - ai[byte0];
			if(Math.abs(ai2[byte0]) > Math.abs(ai2[j]))
			{
				j = byte0;
			}
		}

		if(ai2[j] == 0)
		{
			return null;
		}
		byte byte1 = TCGenBase.otherCoordPairs[j];
		byte byte2 = TCGenBase.otherCoordPairs[j + 3];
		byte byte3;
		if(ai2[j] > 0)
		{
			byte3 = 1;
		} else
		{
			byte3 = -1;
		}
		double d = (double)ai2[byte1] / (double)ai2[j];
		double d1 = (double)ai2[byte2] / (double)ai2[j];
		int ai3[] = {
				0, 0, 0
		};
		int k = 0;
		for(int l = ai2[j] + byte3; k != l; k += byte3)
		{
			ai3[j] = MathHelper.floor(ai[j] + k + 0.5D);
			ai3[byte1] = MathHelper.floor(ai[byte1] + k * d + 0.5D);
			ai3[byte2] = MathHelper.floor(ai[byte2] + k * d1 + 0.5D);
			placeBlock(ai3[0], ai3[1], ai3[2], state, true, primer);
			places.add(new int[] { ai3[0], ai3[1], ai3[2] });
		}
		return places;
	}

	public ArrayList<int[]> checkAndPlaceBlockLine(int ai[], int ai1[], IBlockState state, List a, ChunkPrimer primer)
	{
		ArrayList<int[]> places = new ArrayList<int[]>();
		int ai2[] = {
				0, 0, 0
		};
		byte byte0 = 0;
		int j = 0;
		for (; byte0 < 3; byte0++) {
			ai2[byte0] = ai1[byte0] - ai[byte0];
			if (Math.abs(ai2[byte0]) > Math.abs(ai2[j])) {
				j = byte0;
			}
		}

		if(ai2[j] == 0) {
			return null;
		}

		byte byte1 = TCGenBase.otherCoordPairs[j];
		byte byte2 = TCGenBase.otherCoordPairs[j + 3];
		byte byte3;
		if(ai2[j] > 0) {
			byte3 = 1;
		} else {
			byte3 = -1;
		}
		double d = (double)ai2[byte1] / (double)ai2[j];
		double d1 = (double)ai2[byte2] / (double)ai2[j];
		int ai3[] = {
				0, 0, 0
		};
		int k = 0;

		for (int l = ai2[j] + byte3; k != l; k += byte3) {
			ai3[j] = MathHelper.floor(ai[j] + k + 0.5D);
			ai3[byte1] = MathHelper.floor(ai[byte1] + k * d + 0.5D);
			ai3[byte2] = MathHelper.floor(ai[byte2] + k * d1 + 0.5D);
			Block bId = primer.getBlockState(ai3[0], ai3[1], ai3[2]).getBlock();
			if (!a.contains(bId)) {
				return null;
			}
		}

		for (int l = ai2[j] + byte3; k != l; k += byte3) {
			ai3[j] = MathHelper.floor(ai[j] + k + 0.5D);
			ai3[byte1] = MathHelper.floor(ai[byte1] + k * d + 0.5D);
			ai3[byte2] = MathHelper.floor(ai[byte2] + k * d1 + 0.5D);
			placeBlock(ai3[0], ai3[1], ai3[2], state, true, primer);
			places.add(new int[] { ai3[0], ai3[1], ai3[2] });
		}
		return places;
	}
	
	private void addAction(int x, int y, int z, Action action) {
		addAction(new BlockPos(x, y, z), action);
	}
	
	private void addAction(BlockPos pos, Action action) {
		actionCache.computeIfAbsent(ChunkPos.asLong(pos.getX() << 4, pos.getZ() << 4), l -> new ArrayList<>()).add(new TileEntityAction(pos, action));
	}
	
	public void applyActions(World world, int cx, int cz) {
		List<TileEntityAction> actionsForChunk = actionCache.get(ChunkPos.asLong(cx, cz));
		if (actionsForChunk != null) {
			for (TileEntityAction action : actionsForChunk) {
				TileEntity te = world.getTileEntity(action.getPos());
				if (te != null) {
					action.getAction().act(te, world.rand);
				} else {
					throw new IllegalStateException("Missing TE at " + action.getPos() + "!");
				}
			}
		}
	}

	private static ItemStack randLoot(Random rand) {
		int picker = rand.nextInt(18);
		if(picker < 6)
		{
			return new ItemStack(ItemRegistry.bambooShoot, rand.nextInt(20) + 1);
		}
//		else if(picker < 8)
//		{
//			return new ItemStack(ItemRegistry.coconutBomb, rand.nextInt(3) + 1);
//		}
		else if(picker < 10)
		{
			return new ItemStack(ItemRegistry.scale, rand.nextInt(3) + 1);
		}
		else if(picker < 14)
		{
			return new ItemStack(ItemRegistry.cookedFrogLeg, rand.nextInt(4) + 1);
		}
		/*		else if(picker == 14)
		{
			return new ItemStack(TCItemRegistry.ashenMasks, 1, rand.nextInt(7));
		}*/
		else if(picker <= 15)
		{
			return new ItemStack(ItemRegistry.recordTradeWinds, 1);
		}
		else if(picker == 16)
		{
			return new ItemStack(ItemRegistry.recordEasternIsles, 1);
		}
		else
		{
			return new ItemStack(ItemRegistry.azurite, 1, 3);
		}
	}
}