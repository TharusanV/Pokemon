package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.CreateLayer;
import tile.TileManager;
import ui.UI;

//The painting
public class GamePanel extends JPanel implements Runnable {
	
	//Variables for defining panel size
	final int originalTileSize = 32;
	public final int scale = 2;
	public final int scaledTileSize = originalTileSize * scale;
	public final int maxScreenCol = 24;
	public final int maxScreenRow = 20;
	public final int screenWidth = originalTileSize * maxScreenCol;
	public final int screenHeight = originalTileSize * maxScreenRow;
	
	//World settings
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int worldWidth = scaledTileSize * maxWorldCol;
	public final int worldHeight = scaledTileSize * maxWorldRow;
	
	//Variable for FPS
	int FPS = 60;
	
	TileManager tileManager = new TileManager(this);
	CreateLayer layer1 = new CreateLayer(this, tileManager, 0);
	CreateLayer layer2 = new CreateLayer(this, tileManager, 1);
	CreateLayer layer3 = new CreateLayer(this, tileManager, 2);
	
	
	public KeyHandler keyHandler = new KeyHandler(this);
	public CollisionChecker collisionChecker = new CollisionChecker(this);
	public AssetSetter assetSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	
	public Player player = new Player(this, keyHandler);
	
	public SuperObject obj[] = new SuperObject[10];
	public Entity npc[] = new Entity[10];
	
	//GameStates
	public int gameState;
	public final int playState = 1;
	public final int battleState = 2;
	public final int dialogueState = 3;
	
	Thread gameThread;
	
	public GamePanel(){
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setDoubleBuffered(true); //Enables better rendering performance
	
		this.setBackground(Color.black);
		
		this.addKeyListener(keyHandler);
		this.setFocusable(true);
	}
	
	public void setUpGame() {
		//assetSetter.setObject();
		assetSetter.setNPC();
		gameState = playState;
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
		if(gameState == playState) {
			//Player
			player.update();
			//NPC
			for(int i = 0; i < npc.length; i++) {
				if(npc[i] != null) {
					npc[i].update();
				}
			}
		}
		
		if(gameState == battleState) {
			
		}
		
		if(gameState == dialogueState) {
			
		}
	}
	
	//Graphics acts as the paint brush allowing us to draw
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//Change graphics to graphics2D
		Graphics2D g2 = (Graphics2D)g;
		
		layer1.draw(g2);
		layer2.draw(g2);
		layer3.draw(g2);
		
		for(int i = 0; i < obj.length; i++) {
			if(obj[i] != null) {
				obj[i].draw(g2, this);
			}
		}
		
		for(int i = 0; i < npc.length; i++) {
			if(npc[i] != null) {
				npc[i].draw(g2);
			}
		}
		
		player.draw(g2);
		
		ui.draw(g2);
		
		g2.dispose(); //Save memory after the drawing is created
	}

}
