package net.tropicraft.cojodungeon;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.tropicraft.encyclopedia.TropicalBook;

public class BasicPuzzleFactory {
	
	private static BasicPuzzle puzzles[];
	
	/**
	 * File to read basic puzzles in from
	 */
	public static File file;
	
	public BasicPuzzleFactory() {
		
	}
	
	public static BasicPuzzle createRandomPuzzle() {
		Random rand = new Random();
	//	int index = rand.nextInt(puzzles.length);
	//	return puzzles[index];
		
		loadPuzzlesFromFile();
		
		return null;
	}
	
	public static void loadPuzzlesFromFile() {
        BufferedReader contents = new BufferedReader(new InputStreamReader(BasicPuzzleFactory.class.getResourceAsStream("/tropicalmod/Puzzles.txt")));
        String line;
        
        List<Piece[][]> puzzlePieces = new ArrayList<Piece[][]>();
        
        try {
            while ((line = contents.readLine()) != null) {
                if (!line.contains("|")) {
                    continue;
                }
              
                String attribs = line.substring(0, line.indexOf("|") - 1).trim();
                System.out.printf("Attribs: %s\n", attribs);
                String puzzleVals = line.substring(line.indexOf("|") + 1).trim();
                System.out.printf("puzzleVals: %s\n", puzzleVals);
                
                int length = Integer.parseInt(attribs.split(" ")[0]);
                int height = Integer.parseInt(attribs.split(" ")[1]);
                List<Integer> values = new ArrayList<Integer>();
                
                for (String num : puzzleVals.split(" ", 20)) {
                	values.add(Integer.parseInt(num));
                }
                
                Piece[][] temp = new Piece[length][height];
                
                int count = 0;
                
                for (int i = 0; i < length; i++) {
                	for (int j = 0; j < height; j++) {
                		temp[i][j] = new Piece(values.get(count));
                		System.out.println(values.get(count));
                		count++;
                	}
                }
                
                puzzlePieces.add(temp);
            }
        } catch (IOException ex) {
            Logger.getLogger(TropicalBook.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        puzzles = new BasicPuzzle[puzzlePieces.size()];
        
        for (int i = 0; i < puzzlePieces.size(); i++) {
        	puzzles[i] = new BasicPuzzle(puzzlePieces.get(i));
        }
	}

}
