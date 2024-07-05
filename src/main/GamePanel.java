package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

//The painting
public class GamePanel extends JPanel implements Runnable {
	
	//Variables for defining panel size
	final int originalTileSize = 16;
	final int scale = 3;
	final int scaledTileSize = originalTileSize * scale;
	final int maxScreenCol = 16;
	final int maxScreenRow = 12;
	final int screenWidth = scaledTileSize * maxScreenCol;
	final int screenHeight = scaledTileSize * maxScreenRow;
	
	//Variable for FPS
	int FPS = 60;
	
	//Key binds instance and starting position
	KeyHandler keyHandler = new KeyHandler();
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4; //4 pixels
	
	Thread gameThread;
	
	public GamePanel(){
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setDoubleBuffered(true); //Enables better rendering performance
	
		this.addKeyListener(keyHandler);
		this.setFocusable(true);
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	@Override
	public void run() {
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		
		while(gameThread != null) {
			currentTime = System.nanoTime(); //Checks current time
			delta += (currentTime - lastTime) / drawInterval; 
			lastTime = currentTime;
			
			if(delta >= 1) {
				//Update
				update();
				
				//Draw
				repaint(); //Will call the paintComponent()
				
				delta--;
			}

		}	
	}
	
	public void update() {
		//Movement
		if(keyHandler.upPressed) {
			playerY -= playerSpeed;
		}
		else if(keyHandler.leftPressed) {
			playerX -= playerSpeed;
		}
		else if(keyHandler.rightPressed) {
			playerX += playerSpeed;
		}
		else if(keyHandler.downPressed) {
			playerY += playerSpeed;
		}
	}
	
	//Graphics acts as the paint brush allowing us to draw
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//Change graphics to graphics2D
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setColor(Color.BLACK);
		g2.fillRect(playerX, playerY, scaledTileSize, scaledTileSize); //x, y, w, h
		g2.dispose(); //Save memory after the drawing is created
	}

}
