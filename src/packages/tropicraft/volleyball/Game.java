package tropicraft.volleyball;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class Game {

	/** Players in game */
	private List<EntityPlayer> players;

	/** Players waiting to join game */
	private Queue<EntityPlayer> lobby;

	/** What state is this game currently in? */
	private GameState gameState;

	/** Score of the current game */
	private Score score;

	/** Court associated with this game */
	private Court court;

	/** List of players on team 1 */
	private List<EntityPlayer> team1;

	/** List of players on team 2 */
	private List<EntityPlayer> team2;

	/** Max number of players allowed on a team */
	private final int MAX_TEAM_SIZE;
	
	/** Random instance for this court */
	private Random rand;

	public Game(Court court) {
		players = new ArrayList<EntityPlayer>();
		team1 = new ArrayList<EntityPlayer>();
		team2 = new ArrayList<EntityPlayer>();
		lobby = new LinkedList<EntityPlayer>();
		gameState = GameState.BEFORE_GAME;
		score = Score.newScore();
		this.court = court;
		MAX_TEAM_SIZE = court.maxPlayersPerTeam();
		rand = new Random();
	}

	/**
	 * Start the game by initializing game states, scores, etc., and 
	 * validating the court I suppose
	 */
	public void startGame() {
		gameState = GameState.IN_GAME;
		score = Score.newScore();
		determineTeams();
		onGameStart();
		lobby.clear();

		//TODO: reset scoreboard
		//TODO: validate that all players are within bounds
		//TODO: run game loop
	}
	
	/**
	 * Populate team1 and team2 with players based on which side of the court
	 * players are on. If the max number of players is reached, the remaining
	 * plaers are added to the lobby queue.
	 */
	private void determineTeams() {
		while (!lobby.isEmpty() && team1.size() <= MAX_TEAM_SIZE
				&& team2.size() <= MAX_TEAM_SIZE) {
			EntityPlayer player = lobby.poll();
			int side = court.getSideOfCourt(player);

			if (side == 1) {
				team1.add(player);
			} else {
				team2.add(player);
			}

			System.err.printf("Adding %s to team%d\n", player.username, side);
		}
	}

	/**
	 * Add a player to the game
	 * @param player player to add
	 * @throws IllegalArgumentException
	 */
	public void joinGame(EntityPlayer player) throws IllegalArgumentException {
		if (!players.contains(player) && !lobby.contains(player)) {
			if (gameState == GameState.BEFORE_GAME)
				lobby.add(player);
			else
				System.err.printf("%s cannot join the game while it is in progress!\n", player.username);
		} else
			throw new IllegalArgumentException(String.format("Player %s who is already in game tried to join game!", player.username));
	}

	/**
	 * Merge the players in the lobby queue into the game if possible, usually performed before game or between rounds if necessary
	 */
	private void tryMergeLobby() {
		if (!(team1.size() <= MAX_TEAM_SIZE && team2.size() <= MAX_TEAM_SIZE)) {
			System.err.println("Could not add anybody to teams because the game is full!");
			//TODO possibly output this in-game
			return;
		}	

		if (lobby.isEmpty()) {
			System.err.println("Nobody in lobby, returning to game!");
			return;
		}

		while (!lobby.isEmpty() && team1.size() <= MAX_TEAM_SIZE && team2.size() <= MAX_TEAM_SIZE) {
			if (team1.size() > team2.size()) {
				team1.add(lobby.poll());
			} else
				if (team2.size() > team1.size()) {
					team2.add(lobby.poll());
				} else {
					int team = rand.nextInt(2);
					
					if (team == 0)
						team1.add(lobby.poll());
					else
						team2.add(lobby.poll());
				}
		}
	}
	
	/**
	 * Called before the game starts
	 */
	public void onGameStart() {
		//TODO move all players that aren't in game off court
		//TODO build invisible wall around court so players and mobs cannot intefere	
		System.out.println("Starting game");
	}
	
	/**
	 * Called in a tick loop by the tile entity managing the court/game throughout the game
	 */
	public void gameLoop() {
		
	}
	
	/**
	 * Called when the game ends
	 */
	public void onGameEnd() {
		
	}
	
	/**
	 * Called when the game is paused for whatever reason
	 */
	public void onGamePause() {
		
	}
	
	public void saveGame(NBTTagCompound nbt) {
		
	}
	
	public void loadGame(NBTTagCompound nbt) {
		
	}

	/**
	 * 
	 * @param player
	 * @return Has the player joined or is in the lobby?
	 */
	public boolean hasPlayerJoined(EntityPlayer player) {
		return lobby.contains(player) || players.contains(player) || team1.contains(player) || team2.contains(player);
	}
}
