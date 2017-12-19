package tropicraft.world.worldgen;

import net.minecraft.block.Block;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Random;

public class WorldGenCanopyTreeTall extends WorldGenRainforest {

	int radius;

	public WorldGenCanopyTreeTall(int rad) {		//radius of leaves on top
		radius = rad;
	}

	@Override
	public boolean generate(World world, Random rand, int i, int j, int k) {

		rand = new Random();

		int height = rand.nextInt(15);

		if(height < 12)
		{
			height += 10 + Math.abs(rand.nextInt(5));
		}

		int id = Block.wood.blockID;
		//System.out.println("gen line");
		if(height + 5 > 127)
		{
			return false;
		}

		if(world.getBlockId(i, j - 1, k) != Block.dirt.blockID && world.getBlockId(i, j - 1, k) != Block.grass.blockID)
		{
			return false;
		}


	/*	for(int xx = 0; xx < 4; xx ++)
		{
			for(int zz = 0; zz < 4; zz ++)
			{
				place(world, i - 1 + xx, j + 2, k + 2 - zz, id);		//base square stump thing
			}
		}

		place(world, i - 3, j, k - 3, id);		//one leg
		place(world, i - 2, j + 1, k - 2, id);
		place(world, i - 1, j + 1, k - 1, id);

		place(world, i + 4, j, k + 4, id);		//another leg
		place(world, i + 3, j + 1, k + 3, id);
		place(world, i + 2, j + 1, k + 2, id);

		place(world, i - 3, j, k + 4, id);		//another leg
		place(world, i - 2, j + 1, k + 3, id);
		place(world, i - 1, j + 1, k + 2, id);

		place(world, i + 4, j, k - 3, id);		//another leg
		place(world, i + 3, j + 1, k - 2, id);
		place(world, i + 2, j + 1, k - 1, id);

		j += 2;*/

		for(int h = 0; h < height; h++)			//the trunk
		{
			place(world,   i,       j + h,    k,       id);		
			place(world,   i + 1,   j + h,    k,       id);	
			place(world,   i,       j + h,    k + 1,   id);	
			place(world,   i + 1,   j + h,    k + 1,   id);				
		}		

		int j1 = j + height - 1;


		for(int i1 = i - radius + 1; i1 < i + radius + 1; i1++)
		{
			for(int k1 = k - radius + 1; k1 < k + radius + 1; k1++)
			{
				place(world,    i1,    j1,    k1, Block.leaves.blockID);

				if(i1 > i - radius + 1&& k1 > k - radius + 1 && i1 < i + radius && k1 < k + radius)
				{
					place(world, i1, j1 + 1, k1, Block.leaves.blockID);
				}
			}
		}

		//generate branches

		genLine(world, i + 2,j + height - 5 - rand.nextInt(3), k, 3F, 5F, id, 0, true);

		genLine(world, i,j + height - 7 - rand.nextInt(3), k, 3F, 5F, id, 1, true);

		genLine(world, i , j + height - (int)(height/2) + rand.nextInt(4), k + 1, 4, 3, id, 2, true);

		if(rand.nextInt(2) == 0)
			genLine(world, i , j + height - (int)(height/2) - rand.nextInt(4), k - 1, 5, 4, id, 3, true);

		if(height > 18)
		{
			genLine(world, i + 2,j + height - 10 - rand.nextInt(3), k, 3F, 5F, id, 0, true);

			genLine(world, i,j + height - 7 - rand.nextInt(3), k, 3F, 5F, id, rand.nextInt(2) + 1, true);
		}
		
		for(int d = 0; d < 20; d++)
		{
			for (int e = height; e < height + j; e++)
			{
				if(rand.nextBoolean())
				{
					genVines(world, rand, i + d, e + j, k + d);
				}
			}
		}

		return true;
	}

	public void genLine(World world, int i, int j, int k, float rise, float run, int id, int dir, boolean shouldNodes)
	{
		//y = mx + b
		// x= (y - b)/m
		float slope = rise / run;
		ArrayList<ChunkCoordinates> nodes = new ArrayList<ChunkCoordinates>();

		switch(dir)
		{
		case 0:
			if(run > rise)
			{

				int[] ypoints = new int[(int) run];

				for(int r = 0; r < run; r ++)
				{
					ypoints[r] = (int)(r * slope);
					//System.out.println("adding point: " + ypoints[r]);
				}

				double y = 0;
				int prevY = j;

				for(int x = i; x < i + run; x++)
				{
					y = j + ypoints[x - i];
					//System.out.println("x: " + x);
					//System.out.println("y: " + y);
					if(x == i + run - 1)
					{
						nodes.add(new ChunkCoordinates(x, (int)y, k));
					}
					place(world,x,(int)y,k,id);
				}
			}
			else
				if(rise > run)
				{
					int[] xpoints = new int[(int) rise];

					for(int r = 0; r < rise; r ++)
					{
						xpoints[r] = (int)(r/slope);
						//System.out.println("adding point: " + xpoints[r]);
					}

					double x = 0;

					for(int y = j; y < j + rise; y++)
					{
						x = xpoints[y - j] + i;
						//System.out.println("x: " + x);
						//System.out.println("y: " + y);

						if(y == j + rise - 1)
						{
							nodes.add(new ChunkCoordinates((int) x, y, k));
						}

						place(world,(int) x,y,k,id);
					}
				}else		//rise == run
				{
					for(int x = i; x < run + i; x++)
					{
						place(world, x, j + (int)(x - i), k, id);
					}
				}
			break;
		case 1:
			if(run > rise)
			{

				int[] ypoints = new int[(int) run];

				for(int r = 0; r < run; r ++)
				{
					ypoints[r] = (int)(r * slope);
					//System.out.println("adding pointrun>rise: " + ypoints[r]);
				}

				double y = 0;
				int prevY = j;

				for(int x = i; x > i - run; x--)
				{
					y = j + ypoints[i - x];
					//System.out.println("x: " + x);
					//System.out.println("y: " + y);

					if(x == i - run + 1)
					{
						nodes.add(new ChunkCoordinates(x, (int)y, k));
					}
					place(world,x,(int)y,k,id);
				}
			}
			else
				if(rise > run)
				{
					int[] xpoints = new int[(int) rise];

					for(int r = 0; r < rise; r ++)
					{
						xpoints[r] = (int)(r/slope);
						//System.out.println("adding pointrise >run: " + xpoints[r]);
					}

					double x = 0;

					for(int y = j; y < j + rise; y++)
					{
						x = i - xpoints[y - j];
						//System.out.println("x: " + x);
						//System.out.println("y: " + y);

						if(y == j + rise - 1)
						{
							nodes.add(new ChunkCoordinates((int) x, y, k));
						}
						place(world,(int) x,y,k,id);
					}
				}else		//rise == run
				{
					for(int x = i; x < run + i; x++)
					{
						place(world, x, j + (int)(x - i), k, id);
					}
				}
			break;
		case 2:
			if(run > rise)
			{

				int[] ypoints = new int[(int) run];

				for(int r = 0; r < run; r ++)
				{
					ypoints[r] = (int)(r * slope);
					//System.out.println("adding point: " + ypoints[r]);
				}

				double y = 0;
				int prevY = j;

				for(int z = k; z < k + run; z++)
				{
					y = j + ypoints[z - k];
					//System.out.println("z: " + z);
					//System.out.println("y: " + y);

					if(z == k + run - 1)
					{
						nodes.add(new ChunkCoordinates(i, (int)y, z));
					}
					place(world,i,(int)y,z,id);
				}
			}
			else
				if(rise > run)
				{
					int[] zpoints = new int[(int) rise];

					for(int r = 0; r < rise; r ++)
					{
						zpoints[r] = (int)(r/slope);
						//System.out.println("adding point: " + zpoints[r]);
					}

					double z = 0;

					for(int y = j; y < j + rise; y++)
					{
						z = zpoints[y - j] + k;
						//System.out.println("z: " + z);
						//System.out.println("y: " + y);
						if(y == j + rise - 1)
						{
							nodes.add(new ChunkCoordinates(i, y, (int) z));
						}
						place(world,i,y,(int) z,id);
					}
				}else		//rise == run
				{
					for(int z = k; z < run + k; z++)
					{
						place(world, i, j + (int)(z - k), z, id);
					}
				}
			break;
		case 3:
			if(run > rise)
			{

				int[] ypoints = new int[(int) run];

				for(int r = 0; r < run; r ++)
				{
					ypoints[r] = (int)(r * slope);
					//System.out.println("adding point: " + ypoints[r]);
				}

				double y = 0;
				int prevY = j;

				for(int z = k; z > k - run; z--)
				{
					y = j + ypoints[k - z];
					//System.out.println("z: " + z);
					//System.out.println("y: " + y);
					if(z == k - run + 1)
					{
						nodes.add(new ChunkCoordinates(i, (int)y, z));
					}
					place(world,i,(int)y,z,id);
				}
			}
			else
				if(rise > run)
				{
					int[] zpoints = new int[(int) rise];

					for(int r = 0; r < rise; r ++)
					{
						zpoints[r] = (int)(r/slope);
						//System.out.println("adding point: " + zpoints[r]);
					}

					double z = 0;

					for(int y = j; y < j + rise; y++)
					{
						z = k - zpoints[y - j];
						//System.out.println("z: " + z);
						//System.out.println("y: " + y);
						if(y == j + rise - 1)
						{
							nodes.add(new ChunkCoordinates(i, y, (int) z));
						}
						place(world,i,y,(int) z,id);
					}
				}else		//rise == run
				{
					for(int z = k; z < run + k; z++)
					{
						place(world, i, j + (int)(z - k), z, id);
					}
				}
			break;
		default:
			break;


		}

		if(shouldNodes)
			genNodes(world, genBranches(world, nodes));

	}

	private ArrayList<ChunkCoordinates> genBranches(World world, ArrayList<ChunkCoordinates> nodes) {
		int id = Block.wood.blockID;
		Random rand = new Random();
		int rise, run, dir;
		ArrayList<ChunkCoordinates> newnodes = new ArrayList<ChunkCoordinates>();

		for(ChunkCoordinates node : nodes)
		{
			rise = rand.nextInt(5);
			run = rand.nextInt(6);
			dir = rand.nextInt(4);
			if(rise == run)
				run++;
			genLine(world, node.posX,node.posY, node.posZ, rise, run, id, dir, false);
			switch(dir)
			{
			case 0:
				newnodes.add(new ChunkCoordinates(node.posX + run, node.posY + rise, node.posZ));
				break;
			case 1:
				newnodes.add(new ChunkCoordinates(node.posX - run, node.posY + rise, node.posZ));
				break;
			case 2:
				newnodes.add(new ChunkCoordinates(node.posX, node.posY + rise, node.posZ + run));
				break;
			case 3:
				newnodes.add(new ChunkCoordinates(node.posX, node.posY + rise, node.posZ - run));
				break;
			default:
				break;
			}
		}

		return newnodes;

	}

	private void genNodes(World world, ArrayList<ChunkCoordinates> nodes) {
		int id = Block.leaves.blockID;
		for(ChunkCoordinates node : nodes)
		{			
			for(int i1 = node.posX - radius + 1; i1 < node.posX + radius + 1; i1++)
			{
				for(int k1 = node.posZ - radius + 1; k1 < node.posZ + radius + 1; k1++)
				{
					placeWithMetadata(world,    i1,    node.posY,    k1, id, 0 | 8);

					if(i1 > node.posX - radius + 1&& k1 > node.posZ - radius + 1 && i1 < node.posX + radius && k1 < node.posZ + radius)
					{
						placeWithMetadata(world, i1, node.posY + 1, k1, id, 0 | 8);
					}
		//			else
		//				if(world.isAirBlock(i1 + 1, node.posY, k1))
		//					placeWithMetadata(world, i1 + 1, node.posY, k1, Block.vine.blockID, 2);
				}
			}			
		}		
	}

}