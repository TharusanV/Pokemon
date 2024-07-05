package main;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

//The painting
public class GamePanel extends JPanel {
	
	final int originalTileSize = 16;
	final int scale = 3;
	final int scaledTileSize = originalTileSize * scale;
	final int maxScreenCol = 16;
	final int maxScreenRow = 12;
	final int screenWidth = scaledTileSize * maxScreenCol;
	final int screenHeight = scaledTileSize * maxScreenRow;
	
	public GamePanel(){
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setDoubleBuffered(true); //Enables better rendering performance
	}
	
	//Gets automatically called when the game runs
	//Graphics allows acts as the paint brush allowing us to draw
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.fillRect(100, 100, 200, 50);
	}

}
