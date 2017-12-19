package tropicraft.volleyball;

public class Score {

	private String team1Name, team2Name;
	
	private int team1Score, team2Score;
	
	private Score() {
		team1Name = "team1";
		team2Name = "team2";
		team1Score = 0;
		team2Score = 0;
	}
	
	private Score(String name1, int score1, String name2, int score2) {
		this.team1Name = name1;
		this.team1Score = score1;
		this.team2Name = name2;
		this.team2Score = score2;
	}

	public static Score newScore(String team1Name, int team1Score, String team2Name, int team2Score) {
		return new Score("team1", 0, "team2", 0);
	}
	
	public static Score newScore() {
		return new Score();
	}
	
	/** Return the name of the winning team or the string "GAME_IS_TIED" if the game is tied*/
	public String winningTeam() {
		return team1Score > team2Score ? team1Name : team2Score > team1Score ? team2Name : "GAME_IS_TIED";
	}
	
	/** Return the score of the winning team or -1 if the game is tied*/
	public int winningScore() {
		return team1Score > team2Score ? team1Score : team2Score > team1Score ? team2Score : -1;
	}

}
