package net.tropicraft.cojodungeon;

import java.util.List;

public abstract class Puzzle {
	
	/**
	 * length of puzzle
	 */
	protected int length;
	
	/**
	 * height of puzzle
	 */
	protected int height;
	
	/**
	 * An array representing the solution of this puzzle, use null as a piece to represent empty space
	 * @return 2D array of Pieces representing the solution
	 */
	abstract Piece[][] getSolution();
	
	/**
	 * Returns a list of pieces to provide the user with for this puzzle
	 * @return list of pieces to provide user with for this puzzle
	 */
	abstract List<Piece> getPieces();

	/**
	 * How the board is generated, unsolved. In the case of BasicPuzzles, it would be completely empty
	 * @return
	 */
	abstract Piece[][] defaultDisplay();
}
