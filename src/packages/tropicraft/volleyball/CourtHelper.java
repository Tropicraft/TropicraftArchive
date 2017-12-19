package tropicraft.volleyball;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import tropicraft.blocks.TropicraftBlocks;

public class CourtHelper {
	/** Max number of blocks to search outward to build a court */
	private static final int MAX_SEARCH_DIST = 29;

	/** Min length of the long side of the court */
	private static final int MIN_COURT_LONG_LENGTH = 9;

	/** Min length of the short side of the court */
	private static final int MIN_COURT_SHORT_LENGTH = 5;

	/** Ratio to base court size off of */
	private static final double COURT_LENGTH_RATIO = 19/9;
	
	/** Percentage of tolerance to check for court size */
	private static final int TOLERANCE = 10;

	/**
	 * Construct the actual court (place the court master block and draw the "chalk"
	 * @param world World instance
	 * @param postCoords Array of ChunkCoordinates representing the locations of the posts
	 * @return Court instance with min and max coordinate values set
	 */
	public static Court buildCourt(World world, ChunkCoordinates[] postCoords) {
		Court court = new Court();
		court.minX = postCoords[0].posX < postCoords[1].posX ? postCoords[0].posX : postCoords[1].posX;
		court.minZ = postCoords[1].posZ < postCoords[2].posZ ? postCoords[1].posZ : postCoords[2].posZ;
		court.maxX = postCoords[0].posX > postCoords[1].posX ? postCoords[0].posX : postCoords[1].posX;
		court.maxZ = postCoords[1].posZ > postCoords[2].posZ ? postCoords[1].posZ : postCoords[2].posZ;
		court.zLength = court.maxZ - court.minZ + 1;
		court.xLength = court.maxX - court.minX + 1;
		court.y = postCoords[0].posY;
		
		int posY = postCoords[0].posY;
		final int id = TropicraftBlocks.bambooBundle.blockID;
		final int id2 = TropicraftBlocks.purifiedSand.blockID;
		
		for (int i = (int)court.minX; i < court.maxX; i++) {
			world.setBlock(i, posY - 1, (int)court.minZ, id);
			world.setBlock(i, posY - 1, (int)court.maxZ, id);

		/*	if (i > (int)court.minX && i < court.maxX) {
				for (int k = (int) court.minZ + 1; k < court.maxZ; k++) {
					world.setBlock(i, posY - 1, k, id2);
				}
			}*/
		}

		for (int i = (int)court.minZ; i < court.maxZ; i++) {
			world.setBlock((int)court.minX, posY - 1, i, id);
			world.setBlock((int)court.maxX, posY - 1, i, id);
		}
		
		if (court.xLength > court.zLength) {
			world.setBlock((int)(court.minX + MathHelper.floor_double(court.xLength / 2)), (int)court.y, (int)court.minZ, Volleyball.post.blockID, 1, 3);
			world.setBlock((int)(court.minX + MathHelper.floor_double(court.xLength / 2)), (int)court.y + 1, (int)court.minZ, Volleyball.post.blockID, 1, 3);
			
			world.setBlock((int)(court.minX + MathHelper.floor_double(court.xLength / 2)), (int)court.y, (int)court.maxZ, Volleyball.post.blockID, 1, 3);
			world.setBlock((int)(court.minX + MathHelper.floor_double(court.xLength / 2)), (int)court.y + 1, (int)court.maxZ, Volleyball.post.blockID, 1, 3);
		} else {
			world.setBlock((int)(court.minX), (int)court.y, (int)(court.minZ + MathHelper.floor_double(court.zLength / 2)), Volleyball.post.blockID, 1, 3);
			world.setBlock((int)(court.minX), (int)court.y + 1, (int)(court.minZ + MathHelper.floor_double(court.zLength / 2)), Volleyball.post.blockID, 1, 3);
			
			world.setBlock((int)(court.maxX), (int)court.y, (int)(court.minZ + MathHelper.floor_double(court.zLength / 2)), Volleyball.post.blockID, 1, 3);
			world.setBlock((int)(court.maxX), (int)court.y + 1, (int)(court.minZ + MathHelper.floor_double(court.zLength / 2)), Volleyball.post.blockID, 1, 3);
		}
		 
		return court;
	}

	/**
	 * Called whenever a volleyball court post is placed. This method checks to see if the post forms a valid court or not
	 * @param world World instance
	 * @param x xCoord
	 * @param y yCoord
	 * @param z zCoord
	 * @return The four coordinates of the posts (x1, x2, z1, z2 in that order in the returned array) or null if the court is not valid
	 */
	public static ChunkCoordinates[] checkCourtForValidity(World world, int x, int y, int z) {

		int posXDist = findPost(world, x, y, z, ForgeDirection.EAST, 0);	//0 == closest, 1 == next closest, etc
		int negXDist = findPost(world, x, y, z, ForgeDirection.WEST, 0);
		int posZDist = findPost(world, x, y, z, ForgeDirection.SOUTH, 0);
		int negZDist = findPost(world, x, y, z, ForgeDirection.NORTH, 0);

		int closestCount = 0;

		while (!(posXDist == -1 && negXDist == -1) && !(posZDist == -1 && negZDist == -1)) {
			posXDist = findPost(world, x, y, z, ForgeDirection.EAST, closestCount);	//0 == closest, 1 == next closest, etc
			negXDist = findPost(world, x, y, z, ForgeDirection.WEST, closestCount);
			posZDist = findPost(world, x, y, z, ForgeDirection.SOUTH, closestCount);
			negZDist = findPost(world, x, y, z, ForgeDirection.NORTH, closestCount);


			ForgeDirection closerX = closerDir(ForgeDirection.EAST, posXDist, ForgeDirection.WEST, negXDist);
			ForgeDirection closerZ = closerDir(ForgeDirection.SOUTH, posZDist, ForgeDirection.NORTH, negZDist);

			if (closerX == null || closerZ == null) {
				closestCount++;
				continue;
			}

			//obtain the list of valid posts
			List<ChunkCoordinates> numCorrect = simulateCourt(world, x, y, z, closerX, closerX == ForgeDirection.EAST ? posXDist : negXDist, closerZ, closerZ == ForgeDirection.SOUTH ? posZDist : negZDist);

			//all four posts have been validated
			if (numCorrect.size() == 4) {
				ChunkCoordinates[] coordsArray = numCorrect.toArray(new ChunkCoordinates[numCorrect.size()]);
				return checkBoundsForValidity(coordsArray) ? coordsArray : null;
			}

			//increment the posts to look at (eg, closest, next closest, etc)
			closestCount++;
		}

		return null;
	}

	private static boolean checkBoundsForValidity(ChunkCoordinates[] posts) {
		final int DEFAULT_LONG_LENGTH = 19;
		final int DEFAULT_SHORT_LENGTH = 9;

		int minX = posts[0].posX < posts[1].posX ? posts[0].posX : posts[1].posX;
		int minZ = posts[1].posZ < posts[2].posZ ? posts[1].posZ : posts[2].posZ;
		int maxX = posts[0].posX > posts[1].posX ? posts[0].posX : posts[1].posX;
		int maxZ = posts[1].posZ > posts[2].posZ ? posts[1].posZ : posts[2].posZ;

		int longLength = maxX - minX > maxZ - minZ ? maxX - minX + 1: maxZ - minZ + 1;
		int shortLength = maxX - minX < maxZ - minZ ? maxX - minX + 1: maxZ - minZ + 1;

		if (longLength % 2 != 0) {	//is odd
			return shortLength >= MIN_COURT_SHORT_LENGTH && longLength >= MIN_COURT_LONG_LENGTH && isValidRatio(longLength, shortLength);
		}
		
		return false;
	}

	/**
	 * Check the ratio of the court against the default on
	 * @param longLength Length of the long side of the court
	 * @param shortLength Length of the short side of the court
	 * @return Returns whether the court size is valid
	 */
	private static boolean isValidRatio(int longLength, int shortLength) {
		return COURT_LENGTH_RATIO * (1 - 1/TOLERANCE) <= (longLength / shortLength) && (longLength / shortLength) <= COURT_LENGTH_RATIO * (1 + 1/TOLERANCE);
	}

	/**
	 * Simulate the building of the court, essentially checking to see if the posts are in locations that would allow for the court to be successfully built
	 * @param world World instance
	 * @param x xCoord
	 * @param y yCoord
	 * @param z zCoord
	 * @param xDir Direction to search in at first
	 * @param xDist Distance to search for the first parallel post in the X-direction
	 * @param zDir Z-Direction to search in
	 * @param zDist Distance to search for the first parallel post in the Z-direction
	 * @return List of correctly placed posts. If this has a size of 4, then the court can be built
	 */
	private static List<ChunkCoordinates> simulateCourt(World world, int x, int y, int z, ForgeDirection xDir, int xDist, ForgeDirection zDir, int zDist) {
		List<ChunkCoordinates> validPosts = new ArrayList<ChunkCoordinates>();

		//Add the original placed post
		validPosts.add(new ChunkCoordinates(x, y, z));

		//Supposed xCoord of parallel post
		int xDiff = xDir == ForgeDirection.EAST ? x + xDist : x - xDist;

		//Supposed zCoord of parallel post
		int zDiff = zDir == ForgeDirection.SOUTH ? z + zDist : z - zDist;

		//check parallel post on x-plane
		if (isPost(world, xDiff, y, z))
			validPosts.add(new ChunkCoordinates(xDiff, y, z));

		//check parallel post on z-plane
		if (isPost(world, x, y, zDiff))
			validPosts.add(new ChunkCoordinates(x, y, zDiff));

		//check post on the x/z planes with adjusted distances
		if (isPost(world, xDiff, y, zDiff))
			validPosts.add(new ChunkCoordinates(xDiff, y, zDiff));

		return validPosts;
	}

	/**
	 * Find the nearest post. The 'which' variable determines which post to return the distance to. 0 is closest, 1 is next closest, etc.
	 * @param world World instance
	 * @param x xCoord
	 * @param y yCoord
	 * @param z zCoord
	 * @param dir North/South/East/West cardinal direction
	 * @param which Which post to return the distance to
	 * @return Distance to the 'closest' post in the given direction
	 */
	private static int findPost(World world, int x, int y, int z, ForgeDirection dir, int which) {
		int whichCounter = -1, distance = 0;
		int i = x, j = y, k = z;

		while (whichCounter < which && distance++ <= MAX_SEARCH_DIST) {
			switch (dir) {
			case EAST:
				if (isPost(world, ++i, j, k) && ++whichCounter >= which)
					return i - x;
				break;
			case WEST:
				if (isPost(world, --i, j, k) && ++whichCounter >= which)
					return x - i;
				break;
			case NORTH:
				if (isPost(world, i, j, --k) && ++whichCounter >= which)
					return z - k;
				break;
			case SOUTH:
				if(isPost(world, i, j, ++k))
					return k - z;
			default:
				break;
			}		
		}

		return -1;
	}

	/**
	 * Returns whether the block at the given coordinates is a volleyball court post
	 * @param world World instance
	 * @param i xCoord
	 * @param j yCoord
	 * @param k zCoord
	 * @return Whether the block at the given coordinates is a volleyball court post
	 */
	private static boolean isPost(World world, int i, int j, int k) {
		return world.getBlockId(i, j, k) == Volleyball.post.blockID && world.getBlockMetadata(i, j, k) == 0;
	}

	/**
	 * Finds and returns the closer of the two distances, or null if either of the distances is invalid
	 * @param dir1 First direction
	 * @param dist1 First distance
	 * @param dir2 Second direction
	 * @param dist2 Second distance
	 * @return Returns the closer of the two distances, or null if either of the distances is invalid
	 */
	private static ForgeDirection closerDir(ForgeDirection dir1, int dist1, ForgeDirection dir2, int dist2) {
		if (dist1 <= dist2 && dist1 != -1)
			return dir1;
		else
			if (dist1 <= dist2 && dist2 != -1)
				return dir2;
			else
				if (dist2 <= dist1 && dist2 != -1)
					return dir2;
				else
					if (dist2 <= dist1 && dist1 != -1)
						return dir1;

		return null;
	}
}
