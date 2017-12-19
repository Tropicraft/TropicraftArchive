package tropicraft.volleyball;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;

public class Court {
	
	/** min and max coordinates */
	public double minX, minZ, maxX, maxZ, y;
	
	/** Lengh of court (x) */
	public double xLength;
	
	/** Length of court (z) */
	public double zLength;
	
	/** Current Volleyball Game instance, null if no game is active */
	private Game currentGame;
	
	/** Amount of leeway around the border to allow for balls out of bounds, and going to reach said ball */
	private static final double borderWeight = 1.2D;
	
	private AxisAlignedBB team1Bounds;
	
	private AxisAlignedBB team2Bounds;
	
	public Court() {
		this.currentGame = new Game(this);
	}
	
	/**
	 * Initialize the bounding boxes of the sides of the court for each team
	 */
	public void initTeamBounds() {
		if (xLength >= zLength) {
			team1Bounds = AxisAlignedBB.getBoundingBox(minX, y - 1, minZ, MathHelper.floor_double((maxX - minX)/ 2) + minX, y + 5, maxZ);
			team2Bounds = AxisAlignedBB.getBoundingBox(MathHelper.floor_double((maxX - minX)/ 2) + 1 + minX, y - 1, minZ, maxX, y + 5, maxZ);
		} else {
			team1Bounds = AxisAlignedBB.getBoundingBox(minX, y - 1, minZ, maxX, y + 5, MathHelper.floor_double((maxZ - minZ)/ 2) + minZ);
			team2Bounds = AxisAlignedBB.getBoundingBox(minX, y - 1, MathHelper.floor_double((maxZ - minZ)/ 2) + 1 + minX, maxX, y + 5, maxZ);
		}
	}
	
	public Game getCurrentGame() {
		return this.currentGame;
	}

	public boolean isValidSize() {
		return true;
	}
	
	public int maxPlayersPerTeam() {
		int areaOfSide = (int) (xLength > zLength ? (MathHelper.floor_double(xLength / 2) - 1)* (zLength - 2) : (MathHelper.floor_double(zLength / 2) - 1)* (xLength - 2));
		
		return MathHelper.floor_double(areaOfSide / 2);
	}
	
	/**
	 * 
	 * @param player
	 * @return 1 if the player is on team 1's side of the court, 2 otherwise
	 */
	public int getSideOfCourt(EntityPlayer player) {
		double x = player.posX;
		double y = player.posY;
		double z = player.posZ;
		
		if (team1Bounds == null || team2Bounds == null) {
			initTeamBounds();
		}
		
		System.out.printf("%f %f %f %f %f %f\n", team1Bounds.minX, team1Bounds.minZ, team1Bounds.maxX, team1Bounds.maxZ, team1Bounds.minY, team1Bounds.maxY);
		System.out.printf("%f %f %f\n", player.posX, player.posY, player.posZ);		
		
		if (x >= team1Bounds.minX && z >= team1Bounds.minZ && x <= team1Bounds.maxX && z <= team1Bounds.maxZ && y >= team1Bounds.minY && y <= team1Bounds.maxY) {
			return 1;
		} else
			return 2;
	}
	
	/**
	 * 
	 * @param player Player in game
	 * @return Whether said player is in the bounds of this volleyball court, border weight included
	 */
	private boolean isPlayerInCourtBounds(EntityPlayer player) {
		return player.posX >= minX - borderWeight && player.posX <= minX + borderWeight
				&& player.posZ >= minZ - borderWeight && player.posZ <= maxZ + borderWeight;
	}
	
	public void saveGame(NBTTagCompound nbt) {
		//TODO: move stuff from TE to here
		this.currentGame.saveGame(nbt);
	}
	
	public void loadGame(NBTTagCompound nbt) {
		//TODO: move stuff from TE to here
		this.currentGame = new Game(this);
		this.currentGame.loadGame(nbt);
	}

	public void onClick(EntityPlayer player) {
		System.out.println("yas" + currentGame.hasPlayerJoined(player));
		if (!currentGame.hasPlayerJoined(player))
			this.currentGame.joinGame(player);
		else
			this.currentGame.startGame();
	}
}
