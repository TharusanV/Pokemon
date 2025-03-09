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
import utility.LoadAllFilesTool;
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
	
	//Map Index
	public int currentMapIndex = 0;
	int palletTownIndex = 0;
	int labIndex = 1;
	
	private final TileManager tileManager = new TileManager(this);
	private ArrayList<ArrayList<CreateLayer>> allMapLayers = new ArrayList<ArrayList<CreateLayer>>();
	private ArrayList<CreateLayer> palletTownLayers = new ArrayList<CreateLayer>();
	private ArrayList<CreateLayer> labPalletTownLayers = new ArrayList<CreateLayer>();
	
	//Starting Position
	int palletTownPlayerX = 480 * getScale();
	int palletTownPlayerY = 220 * getScale();
	
	int labPalletTownPlayerX = 230 * getScale();
	int labPalletTownPlayerY = 430 * getScale();
	
	//Entities
	private final Player player = new Player(this, keyHandler);
	private final Entity obj[] = new Entity[1];
	private final Entity npc[] = new Entity[3];
	final ArrayList<Entity> entityList = new ArrayList<>();
	
	//Moves
	private final LoadAllFilesTool loadFiles = new LoadAllFilesTool();
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
	
		allMapLayers.add(palletTownLayers);
		allMapLayers.add(labPalletTownLayers);
		
		palletTownLayers.add(new CreateLayer(this, tileManager, 0));
		palletTownLayers.add(new CreateLayer(this, tileManager, 1));
		palletTownLayers.add(new CreateLayer(this, tileManager, 2));
		
		
		labPalletTownLayers.add(new CreateLayer(this, tileManager, 3));
		labPalletTownLayers.add(new CreateLayer(this, tileManager, 6));
		labPalletTownLayers.add(new CreateLayer(this, tileManager, 4));
		labPalletTownLayers.add(new CreateLayer(this, tileManager, 5));
		
		player.worldX_pos = palletTownPlayerX;
		player.worldY_pos = palletTownPlayerY;
		
		
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
		rivalTeam = loadFiles.loadTrainersTeam(pokeList, moveList, "/csvFiles/rivalTeam.csv");
		
		player.setTeam(rivalTeam);
		getNpc()[1].setTeam(playerTeam);
		
		getBattleUi().setPlayerObj(player);
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
					if(currentMapIndex == npc[i].getAssignedMap()) {
						npc[i].update();
					}
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
		
		//Draw Map
		if(currentMapIndex == palletTownIndex) {
			for(int i = 0; i < palletTownLayers.size(); i++) {
				palletTownLayers.get(i).draw(g2);
			}
		}
		else if(currentMapIndex == labIndex) {
			for(int i = 0; i < labPalletTownLayers.size(); i++) {
				labPalletTownLayers.get(i).draw(g2);
			}
		}
		
		
		for(int i = 0; i < npc.length; i++) {
			if(npc[i] != null) {
				if(currentMapIndex == npc[i].getAssignedMap()) {
					entityList.add(npc[i]);
				}
			}
		}
		
		entityList.add(player);

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
	
	public ArrayList<ArrayList<CreateLayer>> getAllMapLayers() {
		return allMapLayers;
	}

	public void setAllMapLayers(ArrayList<ArrayList<CreateLayer>> allMapLayers) {
		this.allMapLayers = allMapLayers;
	}

	public ArrayList<CreateLayer> getPalletTownLayers() {
		return palletTownLayers;
	}

	public void setPalletTownLayers(ArrayList<CreateLayer> palletTownLayers) {
		this.palletTownLayers = palletTownLayers;
	}

	public ArrayList<CreateLayer> getLabPalletTownLayers() {
		return labPalletTownLayers;
	}

	public void setLabPalletTownLayers(ArrayList<CreateLayer> labPalletTownLayers) {
		this.labPalletTownLayers = labPalletTownLayers;
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
