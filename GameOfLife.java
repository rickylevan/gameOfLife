package gameOfLife;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;


import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JApplet;
import javax.swing.JFrame;
import java.awt.geom.Rectangle2D;


public class GameOfLife extends JApplet {

	private static final long serialVersionUID = 1L;
	
	
	/* Okay, I should learn what the static fuss is about. Right now I'm just tacking
	 * it on to move on with the code :P */
	 
	static int squaresInRow = 15;
	static int squaresInCol = 15;
	
	static double totalWidth = 280;
	static double totalHeight = 280;
	static double rectangleWidth = totalWidth/((double) squaresInRow);
	static double rectangleHeight = totalHeight/((double) squaresInCol);
	
	// a little subtraction here for aesthetics!
	static double squareSize = rectangleWidth - rectangleWidth/8.0;
	
	static int runTime = 1000;
	static int updateTime = 100;
	
	static boolean isInitialized = false;
	
	
	static Rectangle2D.Double visualGrid[][] = new Rectangle2D.Double[squaresInRow][squaresInCol];
	static GameBoard board = new GameBoard(squaresInRow,squaresInCol,squareSize);
	
	
	public static void main(String[] args) throws InterruptedException {
		
		
		
		
		board.initializeTiles0ToRandomBooleans();
		initializeRectangleGrid();
		
		// initializing some basic stuff for the game
	    JFrame f = new JFrame("");
	    f.addWindowListener(new WindowAdapter() {
	      public void windowClosing(WindowEvent e) {
	        System.exit(0);
	      }
	    }); 
	    GameOfLife applet = new GameOfLife();
	    f.getContentPane().add("Center", applet);
	    applet.init();
	    //f.pack();
	    
	    f.setSize(new Dimension(300, 300));
	    f.setVisible(true);
	    for (int j = 0; j < runTime; j++) {
	    	Thread.sleep(updateTime);
	    	//board.printTiles0();
	    	board.updateTilesByGameRule();
	    	applet.validate();
	    	applet.repaint();
	    } 
	  } 
		
		
	

	public void paint(Graphics g) {
		paintCurrentBoard(board, g);
	}
	
	
	public void paintCurrentBoard(GameBoard board, Graphics g) {
		
		// checking to make sure we don't try to access uninitialized things
		if (isInitialized == false) {
			return;
		}
		
		// now we fill according to whether the rectangles are true or false
		Graphics2D g2d = (Graphics2D) g;
		for (int j = 0; j < squaresInRow; j++) {
			for (int k = 0; k < squaresInCol; k++) {
				if (board.tiles0[j][k] == true) {
					g2d.setColor(Color.RED);
				} else {
					g2d.setColor(Color.WHITE);
				}
				
				// and now we attach the appropriately colored rectangle
				g2d.fill(visualGrid[j][k]);
				
			}
		}
		
		
		
		
		
	}
	
	
	public static void initializeRectangleGrid() {
		// we fill up all the rectangles with the proper values
		for (int j = 0; j < squaresInRow; j++) {
			for (int k = 0; k < squaresInCol; k++) {
				// getting the coordinates of the top-left corner
				double topLeftCornerHorizontal = j*totalWidth/((double) squaresInRow);
				double topLeftCornerVertical = k*totalHeight/((double) squaresInCol);
				// and now filling the grid with all the rectangles we need 
				visualGrid[j][k] = new Rectangle2D.Double(topLeftCornerHorizontal, 
						topLeftCornerVertical, squareSize, squareSize);
			}
		}
		
		isInitialized = true;
		
	}
	
	
	
	
	
	
	
}
