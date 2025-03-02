package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import battle.Move;
import battle.Pokemon;
import entity.Entity;
import entity.Player;
import tile.CreateLayer;
import tile.TileManager;
import ui.BattleUI;
import ui.DialogueUI;
import utility.KeyHandler;
import utility.LoadExcelFilesTool;
import utility.UtilityTool;

//The painting
public class GamePanel extends JPanel implements Runnable {
	
	private final KeyHandler keyHandler = new KeyHandler(this);
	private final CollisionChecker collisionChecker = new CollisionChecker(this);
	private final AssetSetter assetSetter = new AssetSetter(this);
	private final BattleUI battleUI = new BattleUI(this);
	private final DialogueUI dialogueUI = new DialogueUI(this);
	private final EventHandler eventHandler = new EventHandler(this);
	private final UtilityTool utilityTool = new UtilityTool();
	
	//Variables for defining panel size
	final int originalTileSize = 32;
	final int scale = 2;
	private final int scaledTileSize = originalTileSize * scale;
	private final int maxScreenCol = 25;
	private final int maxScreenRow = 20;
	private final int screenWidth = originalTileSize * maxScreenCol;
	private final int screenHeight = originalTileSize * maxScreenRow;
	
	//World settings
	private final int maxWorldCol = 50;
	private final int maxWorldRow = 50;
	private final int worldWidth = scaledTileSize * maxWorldCol;
	private final int worldHeight = scaledTileSize * maxWorldRow;
	
	//Variable for FPS
	private final int FPS = 60;
	
	private final TileManager tileManager = new TileManager(this);
	private final CreateLayer groundLayer = new CreateLayer(this, tileManager, 0);
	private final CreateLayer treeLayer = new CreateLayer(this, tileManager, 1);
	private final CreateLayer buildingLayer = new CreateLayer(this, tileManager, 2);
	
	private final Player player = new Player(this, keyHandler);
	private final Entity obj[] = new Entity[10];
	private final Entity npc[] = new Entity[10];
	final ArrayList<Entity> entityList = new ArrayList<>();
	
	//Moves
	private final LoadExcelFilesTool loadFiles = new LoadExcelFilesTool();
	private ArrayList<Move> moveList;
	private ArrayList<Pokemon> pokeList;
	private ArrayList<Pokemon> playerTeam;
	private ArrayList<Pokemon> rivalTeam;
	
	
	//GameStates
	private int gameState;
	private final int playState = 1;
	private final int battleState = 2;
	private final int dialogueState = 3;
	
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
		
		moveList = loadFiles.loadMoves();
		pokeList = loadFiles.loadPokemons();
		
		playerTeam = loadFiles.loadTrainersTeam(pokeList, moveList, "/csvFiles/playerTeam.csv");
		player.setTeam(playerTeam);
		
		rivalTeam = loadFiles.loadTrainersTeam(pokeList, moveList, "/csvFiles/rivalTeam.csv");
		getNpc()[1].setTeam(rivalTeam);
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
			battleUI.loadSpecificBattleImages();
		}
		
		if(gameState == dialogueState) {
			
		}
	}
	
	//Graphics acts as the paint brush allowing us to draw
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//Change graphics to graphics2D
		Graphics2D g2 = (Graphics2D)g;
		
		//Draw Map
		groundLayer.draw(g2);
		treeLayer.draw(g2);
		buildingLayer.draw(g2);
		
		//Add entities to list
		entityList.add(player);
		
		for(int i = 0; i < npc.length; i++) {
			if(npc[i] != null) {entityList.add(npc[i]);}
		}
		
		for(int i = 0; i < obj.length; i++) {
			if(obj[i] != null) {entityList.add(obj[i]);}
		}

		//Note: As we are sorting, an issue is that currently if an npc has a higher y value there is an overlap issue with the player
		Collections.sort(entityList, new Comparator<Entity>() {
			@Override
			public int compare(Entity e1, Entity e2) {
				int result = Integer.compare(e1.worldX_pos, e2.worldY_pos);
				return result;
			}
			
		});
		
		for(int i = 0; i < entityList.size(); i++) {
			entityList.get(i).draw(g2);
		}
		entityList.clear();
		
		//UI drawings based on states
		if(gameState == battleState) {battleUI.draw(g2);}
		if(gameState == dialogueState) {dialogueUI.draw(g2);}
		
		g2.dispose(); //Save memory after the drawing is created
	}

	
	
	////////////////////////////////////////////////////////////////////////////////
	
	
	
	//ALL SETTERS & GETTERS	- P1
	public int getOriginalTileSize() {
		return originalTileSize;
	}

	public int getScale() {
		return scale;
	}

	public int getScaledTileSize() {
		return scaledTileSize;
	}
	
	public int getMaxScreenCol() {
		return maxScreenCol;
	}

	public int getMaxScreenRow() {
		return maxScreenRow;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}
	
	
	//ALL SETTERS & GETTERS	- P2
	public int getMaxWorldCol() {
		return maxWorldCol;
	}

	public int getMaxWorldRow() {
		return maxWorldRow;
	}

	public int getWorldWidth() {
		return worldWidth;
	}

	public int getWorldHeight() {
		return worldHeight;
	}

	//ALL SETTERS & GETTERS	- P3
	public int getFPS() {
		return FPS;
	}
	
	//ALL SETTERS & GETTERS	- P4
	public TileManager getTileManager() {
		return tileManager;
	}

	public CreateLayer getGroundLayer() {
		return groundLayer;
	}

	public CreateLayer getTreeLayer() {
		return treeLayer;
	}

	public CreateLayer getBuildingLayer() {
		return buildingLayer;
	}
	
	//ALL SETTERS & GETTERS	- P5
	public KeyHandler getKeyHandler() {
		return keyHandler;
	}

	public CollisionChecker getCollisionChecker() {
		return collisionChecker;
	}

	public AssetSetter getAssetSetter() {
		return assetSetter;
	}

	public BattleUI getBattleUi() {
		return battleUI;
	}
	
	public DialogueUI getDialogueUi() {
		return dialogueUI;
	}

	public EventHandler getEventHandler() {
		return eventHandler;
	}
	
	public UtilityTool getUtilityTool() {
		return utilityTool;
	}
	
	//ALL SETTERS & GETTERS	- P6
	public Player getPlayer() {
		return player;
	}

	public Entity[] getObj() {
		return obj;
	}

	public Entity[] getNpc() {
		return npc;
	}

	public ArrayList<Entity> getEntityList() {
		return entityList;
	}
	
	
	//ALL SETTERS & GETTERS	- P7
	public int getGameState() {
		return gameState;
	}

	public void setGameState(int gameState) {
		this.gameState = gameState;
	}

	public int getPlayState() {
		return playState;
	}

	public int getBattleState() {
		return battleState;
	}

	public int getDialogueState() {
		return dialogueState;
	}
	
	//ALL SETTERS & GETTERS	- P8
	public Thread getGameThread() {
		return gameThread;
	}

	public void setGameThread(Thread gameThread) {
		this.gameThread = gameThread;
	}



	
}
