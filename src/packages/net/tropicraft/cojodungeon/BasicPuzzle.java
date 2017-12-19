package net.tropicraft.cojodungeon;

import java.util.ArrayList;
import java.util.List;

public class BasicPuzzle extends Puzzle {

	/**
	 * The solution to the puzzle, generated upon instantiation of this puzzle
	 */
	private Piece[][] solution;
	
	/**
	 * List of pieces to give the player to solve the puzzle
	 */
	private List<Piece> pieces;

	public BasicPuzzle(Piece[][] pieces) {
		this.solution = pieces;
		
		this.pieces = new ArrayList<Piece>();
		
		for (int i = 0; i < solution[0].length; i++) {
			for (int j = 0; j < solution.length; j++) {
				this.pieces.add(solution[i][j]);
			}
		}
	}

	@Override
	public Piece[][] getSolution() {
		return this.solution;
	}

	@Override
	public List<Piece> getPieces() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Piece[][] defaultDisplay() {
		Piece[][] temp = new Piece[solution[0].length][solution.length];
		
		for (int i = 0; i < solution[0].length; i++) {
			for (int j = 0; j < solution.length; j++) {
				temp[i][j] = Piece.emptyPiece;
			}
		}
		
		return temp;
	}

}
