package gameOfLife;

import java.util.ArrayList;


public class GameBoard {
	
	int squaresInRow;
	int squaresInCol;
	double squareSize;
	
	int usingTiles0Or1; // this int is either 0 or 1. 
	
	// there are two tiles sets. #1 serves as the scratch space for updating #0,
	// and then the reverse happens, alternating back and forth. 
	//ArrayList<boolean[][]> tileWrapper; // Java won't let me plug in "0" to get tiles0 dynamically
	boolean[][] tiles0; 
	boolean[][] tiles1;
	
	// these are used to refer to whether we talk about new or old in the
	// updateTilesByGameRule() method. 
	boolean[][] currentTiles;
	boolean[][] newTiles;
	
	int numberOfNeighbors = 8;
	IntPair[] neighborIndicesRing = new IntPair[numberOfNeighbors];
	
	public GameBoard(int squaresInRow, int squaresInCol, double squareSize) {
		this.squaresInRow = squaresInRow;
		this.squaresInCol = squaresInCol;
		this.squareSize = squareSize;
		// and now we construct the tiles with squaresInRow and squaresInCol initialized! 
		tiles0 = new boolean[squaresInRow][squaresInCol];
		tiles1 = new boolean[squaresInRow][squaresInCol];
	}
	
	
	public void initializeTiles0ToRandomBooleans() {
		usingTiles0Or1 = 0; // we start in board state 1
		for (int j = 0; j < squaresInRow; j++) {
			for (int k = 0; k < squaresInCol; k++) {
				tiles0[j][k] = generateBooleanBinaryRand();
			}
		}	
	}
	
	
	public void updateTilesByGameRule() {
		// grab the current board
		if (usingTiles0Or1 == 0) {
			currentTiles = tiles0;
			newTiles = tiles1;
		} else {
			currentTiles = tiles1;
			newTiles = tiles0;
		}
		
		// great, now we proceed to update
		for (int j = 0; j < squaresInRow; j++) {
			for (int k = 0; k < squaresInCol; k++) {
				newTiles[j][k] = computeNewTileByGameRule(j,k);
			}
		}
		
		// *** this executes at the end of this updateTilesByGameRule() method! ***
		// swap the status of which tiles we are using
		if (usingTiles0Or1 == 0) {
			usingTiles0Or1 = 1;
		} else {
			usingTiles0Or1 = 0;
		}
	}
	
	public boolean computeNewTileByGameRule(int j, int k) {
		
		// Here is the meat of the Game of Life updating. 
		// Given indices j and k, as well as the global variable currentTiles, we need 
		// to figure out if tile newTiles[j][k] should become true or false
		
		// first we fill up the neighborIndicesRing. We start start to the right and move
		// counter-clockwise like theta. The effective coordinates are using computer geometry, 
		// so moving right and down increases the j and k indices
		
		// Is there a pattern here? Meh, it doesn't seem worth the effort
		neighborIndicesRing[0] = new IntPair(j+1,k);
		neighborIndicesRing[1] = new IntPair(j+1,k-1);
		neighborIndicesRing[2] = new IntPair(j,k-1);
		neighborIndicesRing[3] = new IntPair(j-1,k-1);
		neighborIndicesRing[4] = new IntPair(j-1,k);
		neighborIndicesRing[5] = new IntPair(j-1,k+1);
		neighborIndicesRing[6] = new IntPair(j,k+1);
		neighborIndicesRing[7] = new IntPair(j+1,k+1);
		
		// but if the tile is on the edge, some of them should not count. 
		// So we first build up the valid tiles in the validNeighbors list.
		ArrayList<IntPair> validNeighbors = new ArrayList<IntPair>();
		for (int i = 0; i < numberOfNeighbors; i++) {
			if (intPairIsOnTheBoard(neighborIndicesRing[i])) {
				validNeighbors.add(neighborIndicesRing[i]);
			}
		}
		
		// alright, now we've got the valid neighbors. The next step is to plug 
		// these neighbors into the currentBoard so we can sum them and judge what
		// the value of newTiles[j][k] should be
		
		int trueNeighborAliveCount = 0;
		for (int i = 0; i < validNeighbors.size(); i++) {
			IntPair pair = validNeighbors.get(i);
			boolean neighborVal = currentTiles[pair.a][pair.b];
			if (neighborVal) {
				trueNeighborAliveCount += 1;
			}
		}
		
		// now we have the number of neighbors alive (aka true).
		// Here is Conway's Game of Life Rule:
		
		// if the current tile is false:
		if (currentTiles[j][k] == false) {
			if (trueNeighborAliveCount == 3) {
				return true;
			} else {
				return false;
			}
		// if the current tile is true:
		} else {
			if (trueNeighborAliveCount < 2) {
				return false;
			} else if (2 <= trueNeighborAliveCount && trueNeighborAliveCount <= 3) {
				return true;
			} else {
				return false;
			}
		}
		
	
		
		
	}
	
	// 
	public boolean intPairIsOnTheBoard(IntPair pair) {
		boolean defaultFlag = true;
		
		// horizontal index is too far left or too far right
		if (pair.a < 0 || pair.a >= squaresInRow) {
			return false;
		// vertical index is too far up or too far down
		} else if (pair.b < 0 || pair.b >= squaresInCol) {
			return false;
		}
		// goldilocks, so let's return true
		return defaultFlag;
		
		
	}
	
	
	
	
	public class IntPair {
		public int a; public int b;
		public IntPair(int a, int b) {
			this.a = a; this.b = b;
		}
		
	}
	
	
	private boolean generateBooleanBinaryRand() {
		double randZeroToOne = Math.random();
		int zeroOrOne = (int) Math.floor(2*randZeroToOne);
		if (zeroOrOne == 0) {
			return false;
		} else {
			return true;
		}
		
	}
	
	public void  printTiles0() {
		for (int j = 0; j < squaresInRow; j++) {
			for (int k = 0; k < squaresInCol; k++) {
				System.out.print(tiles0[j][k]);
				System.out.print(", ");
			}
			System.out.println("\n");
		}
	}
	

}
