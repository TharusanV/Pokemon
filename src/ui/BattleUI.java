package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import battle.Move;
import battle.Pokemon;
import entity.Player;
import entity.Trainer;
import main.GamePanel;

public class BattleUI {

	GamePanel gamePanel;
	Graphics2D g2;
	
	int SCREEN_WIDTH = 800;
	int SCREEN_HEIGHT = 640;
	
	boolean battleIntroTransition = true;
	boolean battleStartIntro = false;
	boolean battleStarted = false;
	
	int battleCounter = 0;
	
	//Battle Start
	BufferedImage playerBarIcon, opponentBarIcon;
	BufferedImage playerBarIconSilhouette, opponentBarIconSilhouette;
	BufferedImage playerBar, opponentBar;
	BufferedImage vsIcon;
	
	int playerBarX = -SCREEN_WIDTH/2;
	int opponentBarX = SCREEN_WIDTH;
	int battleBarY = (SCREEN_HEIGHT / 2) - 130;
	int playerIconBarX = -380;
	int opponentIconBarX = 920;
	
	int vs_X = SCREEN_WIDTH/2 - (122/2);
	int vs_Y = 330;
	int vs_Width = 122;
	int vs_Height = 108;
	
	int countdown1 = 0;
	
	//Battle Intro
	BufferedImage fieldBG;
	BufferedImage playerBack1,playerBack2,playerBack3,playerBack4,playerBack5,opponentFront;
	BufferedImage fieldBasePlayer, fieldBaseOpponent;
	
	BufferedImage battleOverlayLineUp;
	BufferedImage ballIcon, emptyBallIcon, faintedBallIcon;
	
	
	int topRectIntro = 0;
	int bottomRectIntro = 320;
	
	int basePlayerX = 750;
	int baseOpponentX = -440;
	int playerBackX = 950;
	int opponentFrontX = -400;
	
	//Battle
	BufferedImage textbox, battleOverlay, battleOverlayTextbox, battleOverlayFight;
	BufferedImage fightCommand, bagCommand, pokemonCommand, runCommand, cancelCommand;
	BufferedImage fightCommandSelected, bagCommandSelected, pokemonCommandSelected, runCommandSelected, cancelCommandSelected;
	
	int commandWidth = SCREEN_WIDTH / 4;
	int commandHeight = 60;
	int fightCommandX = 400;
	int fightCommandY = 522;
	int bagCommandX = fightCommandX + commandWidth - 7;
	int bagCommandY = fightCommandY;
	int pokemonCommandX = fightCommandX;
	int pokemonCommandY = fightCommandY + commandHeight - 6;
	int runCommandX = fightCommandX + commandWidth - 7;
	int runCommandY = fightCommandY + commandHeight - 6;
	
	
	public int currentCommand = 0;
	boolean commandsBoolean = true;
	
	boolean fightBoolean = false;
	boolean bagBoolean = false;
	boolean pokemonBoolean = false;
	boolean runBoolean = false;
	
	boolean playingMoveBoolean = false;
	int playerSelectedMoveIndex = 0;
	
	
	//Pokemon (testing)
	BufferedImage charizardFront, pikachuBack;
	
	//Dialogue
	Font pokeFont;
	Font battlePPFont;
	public String currentDialogue = "";
	int charIndex = 0;
	String combinedText = "";
	
	Player playerObj = null;
	Trainer opponentObj = null;
	
	//Type icons
	BufferedImage fireTypeIcon, waterTypeIcon, grassTypeIcon, electricTypeIcon, iceTypeIcon, fightingTypeIcon, poisonTypeIcon, groundTypeIcon, flyingTypeIcon, psychicTypeIcon, bugTypeIcon, rockTypeIcon, ghostTypeIcon, dragonTypeIcon, darkTypeIcon, steelTypeIcon, fairyTypeIcon, normalTypeIcon;

	public ArrayList<String> introTextAL = new ArrayList<String>();
	public int introTextIndex = 0;
	
	public ArrayList<String> inCombatTextAL = new ArrayList<String>();
	public int inCombatTextIndex = 0;
	
	public ArrayList<String> runAttemptTextAL = new ArrayList<String>();
	public int runAttemptTextIndex = 0;
	
	
	Move opponentMove = null;
	Move playerMove = null;
	int opponentDamageToPlayer = 0;
	int playerDamageToOpponent = 0;
	boolean playerIsFirst = false;
	
	
	HashMap<String, BufferedImage> movesTypeHM = new HashMap<String, BufferedImage>();
	
	BufferedImage userHealthBox;
	BufferedImage opponentHealthBox;
	
	int playerHealthBarWidth = 152;
	int oppHealthBarWidth = 152;
	
	int newHealthBarWidthP = 152;
	int newHealthBarWidthO = 152;
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public BattleUI(GamePanel p_gamePanel) {
		this.gamePanel = p_gamePanel;
		
		loadBattleImages();

	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	public void prepBattleState() {
		introTextAL.clear();
		inCombatTextAL.clear();
		runAttemptTextAL.clear();
		
		opponentObj.setSelectedPokemonIndex(opponentObj.findFirstMemberWithHealth());
		playerObj.setSelectedPokemonIndex(playerObj.findFirstMemberWithHealth());
	
		introTextAL.add(opponentObj.getName() + " would like to battle!");
		introTextAL.add(opponentObj.getName() + " sent out " + opponentObj.getCurrentPokemon().getName() + "!");
		introTextAL.add("Go! " + playerObj.getCurrentPokemon().getName() + "!");
			
		runAttemptTextAL.add("You cannot run from a Trainer battle!");
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
    
	public void draw(Graphics2D g2) {
		this.g2 = g2;
	
		g2.setFont(pokeFont);
		g2.setColor(Color.BLACK);
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
	
		if(battleIntroTransition) {
			startBattle(); 
		}
		if(battleStartIntro) {
			battleIntro();
		}
		if(battleStarted) {
			inBattle();
		}
		
		g2.dispose();
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void startBattle() {
		Color c = new Color(0, 0, 0, 100);
		
		drawSubWindow(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, c);
		
		g2.drawImage(playerBar, playerBarX, battleBarY, SCREEN_WIDTH/2, 196, null);
		g2.drawImage(opponentBar, opponentBarX, battleBarY, SCREEN_WIDTH/2, 196, null);
		
		g2.drawImage(playerBarIconSilhouette, playerIconBarX, battleBarY + 8, 256, 178, null);
		g2.drawImage(opponentBarIconSilhouette, opponentIconBarX, battleBarY + 8, 256, 178, null);
		
		
		if(playerBarX != 0) {
			playerBarX += 20;
			opponentBarX -= 20;
		}
		else if(playerBarX == 0 && playerIconBarX <= 60) {
			playerIconBarX += 10;
			opponentIconBarX -= 10;
		}
		else if(playerIconBarX >= 60 && countdown1 != 30) {
			g2.drawImage(playerBarIcon, playerIconBarX, battleBarY + 8, 256, 178, null);
			g2.drawImage(opponentBarIcon, opponentIconBarX, battleBarY + 8, 256, 178, null);
			g2.drawImage(vsIcon,vs_X,vs_Y,vs_Width,vs_Height,null);
			
			countdown1++;
		}
		else if(countdown1 == 30) {
			g2.drawImage(playerBarIcon, playerIconBarX, battleBarY + 8, 256, 178, null);
			g2.drawImage(opponentBarIcon, opponentIconBarX, battleBarY + 8, 256, 178, null);
			g2.drawImage(vsIcon,vs_X,vs_Y,vs_Width,vs_Height,null);
			
			vs_Width += 4;
			vs_Height += 4;
		    vs_X -= 4 / 2;
		    vs_Y -= 4 / 2;
		    
		    if(vs_Width >= 600) {
				battleStartIntro = true;
				battleIntroTransition = false;
			}
		}
	}
	
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void battleIntro() {		
		g2.drawImage(fieldBG, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
		g2.drawImage(fieldBasePlayer, basePlayerX, 448, 512 + (512/3), 64, null);
		g2.drawImage(fieldBaseOpponent, baseOpponentX, 220, 265 + (265/3), 128 + (128/3), null);

		g2.drawImage(playerBack1, playerBackX, 272, 240, 240, null);
		g2.drawImage(opponentFront, opponentFrontX, 120, 192, 192, null);

		if(basePlayerX != -150) {
			basePlayerX -= 10;
			baseOpponentX += 10;
			playerBackX -= 10;
			opponentFrontX += 10;
		}
		
		
		//Battle menu
		g2.drawImage(battleOverlay, 0, 512, gamePanel.getScreenWidth(), gamePanel.getScreenHeight() / 5, null);
		g2.drawImage(textbox, 0, 512, gamePanel.getScreenWidth(), gamePanel.getScreenHeight() / 5, null);
		
		
		lineUpUI();
		
		
		if(bottomRectIntro != 640) {
			topRectIntro -= 4;
			bottomRectIntro += 4;
			
			g2.fillRect(0, topRectIntro, gamePanel.getScreenWidth(), gamePanel.getScreenHeight() / 2);
	        g2.fillRect(0, bottomRectIntro, gamePanel.getScreenWidth(), gamePanel.getScreenHeight() / 2);
		}
		
		
		if(basePlayerX == -150) {
			//battleCounter++;
			if(introTextIndex < introTextAL.size()) {
				char characters[] = introTextAL.get(introTextIndex).toCharArray();
				if(charIndex < characters.length) {
					String s = String.valueOf(characters[charIndex]);
					combinedText = combinedText + s;
					currentDialogue = combinedText;
					charIndex++;
				}
				
				if(gamePanel.getKeyHandler().enterPressed == true) {
					charIndex = 0;
					combinedText = "";
					
					
					introTextIndex++;
					gamePanel.getKeyHandler().enterPressed = false;
				}
				
				for(String line : currentDialogue.split("\n")) {
					g2.drawString(line, 64, 512+64);
				}
			}

			else {
				introTextIndex = 0;
				battleStarted = true;
				battleStartIntro = false;
			}
		}
	
	}
	
	public void throwPokemon() {
		g2.drawImage(fieldBG, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
		
		g2.drawImage(fieldBasePlayer, basePlayerX, 448, 512 + (512/3), 64, null);
		g2.drawImage(fieldBaseOpponent, baseOpponentX, 220, 265 + (265/3), 128 + (128/3), null);
		
		g2.drawImage(battleOverlay, 0, 512, gamePanel.getScreenWidth(), gamePanel.getScreenHeight() / 5, null);
		g2.drawImage(textbox, 0, 512, gamePanel.getScreenWidth(), gamePanel.getScreenHeight() / 5, null);

		if(battleCounter < 60) {
			g2.drawImage(playerBack1, playerBackX, 272, 240, 240, null);
			g2.drawImage(opponentFront, opponentFrontX, 120, 192, 192, null);				
		}
		else if(battleCounter >= 60 && battleCounter <= 65) {
			g2.drawImage(playerBack2, playerBackX, 272, 240, 240, null);
			g2.drawImage(opponentFront, opponentFrontX, 120, 192, 192, null);				
		}
		else if(battleCounter >= 65 && battleCounter <= 70) {
			g2.drawImage(playerBack3, playerBackX, 272, 240, 240, null);
			g2.drawImage(opponentFront, opponentFrontX, 120, 192, 192, null);				
		}
		else if(battleCounter >= 70 && battleCounter <= 75) {
			g2.drawImage(playerBack4, playerBackX, 272, 240, 240, null);
			g2.drawImage(opponentFront, opponentFrontX, 120, 192, 192, null);				
		}
		else if(battleCounter >= 75 && battleCounter < 80) {
			g2.drawImage(playerBack5, playerBackX, 272, 240, 240, null);
			g2.drawImage(opponentFront, opponentFrontX, 120, 192, 192, null);				
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void inBattle() {
		g2.drawImage(fieldBG, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
		
		g2.drawImage(fieldBasePlayer, basePlayerX, 448, 512 + (512/3), 64, null);
		g2.drawImage(fieldBaseOpponent, baseOpponentX, 220, 265 + (265/3), 128 + (128/3), null);
		
		g2.drawImage(pikachuBack, playerBackX, 296, 240, 240, null);
		g2.drawImage(charizardFront, opponentFrontX+35, 120, 192, 192, null);
		
		g2.drawImage(battleOverlay, 0, 512, gamePanel.getScreenWidth(), gamePanel.getScreenHeight() / 5, null);
		
		healthBarsUI();
		
		if(commandsBoolean == true) {
			g2.drawImage(battleOverlayTextbox, 0, 512, gamePanel.getScreenWidth(), gamePanel.getScreenHeight() / 5, null);
			
			battleShowCommands();
		}
		else if(fightBoolean == true) {
			fightCommand_Open();
		}
		else if(playingMoveBoolean == true) {
			fightCommand_PlayOutMove();
		}
		
		else if(runBoolean == true) {
			runCommand_Open();
		}
		

	}
	
	
	public void battleShowCommands() {
		if(currentCommand == 0) {
			g2.drawImage(bagCommand, bagCommandX, bagCommandY, commandWidth, commandHeight, null);
			g2.drawImage(pokemonCommand, pokemonCommandX, pokemonCommandY, commandWidth, commandHeight, null);
			g2.drawImage(runCommand, runCommandX, runCommandY, commandWidth, commandHeight, null);
			g2.drawImage(fightCommandSelected, fightCommandX, fightCommandY, commandWidth, commandHeight, null);
		}
		else if(currentCommand == 1) {
			g2.drawImage(bagCommandSelected, bagCommandX, bagCommandY, commandWidth, commandHeight, null);
			g2.drawImage(pokemonCommand, pokemonCommandX, pokemonCommandY, commandWidth, commandHeight, null);
			g2.drawImage(runCommand, runCommandX, runCommandY, commandWidth, commandHeight, null);
			g2.drawImage(fightCommand, fightCommandX, fightCommandY, commandWidth, commandHeight, null);
		}
		else if(currentCommand == 2) {
			g2.drawImage(bagCommand, bagCommandX, bagCommandY, commandWidth, commandHeight, null);
			g2.drawImage(pokemonCommandSelected, pokemonCommandX, pokemonCommandY, commandWidth, commandHeight, null);
			g2.drawImage(runCommand, runCommandX, runCommandY, commandWidth, commandHeight, null);
			g2.drawImage(fightCommand, fightCommandX, fightCommandY, commandWidth, commandHeight, null);
		}	
		else if(currentCommand == 3){
			g2.drawImage(bagCommand, bagCommandX, bagCommandY, commandWidth, commandHeight, null);
			g2.drawImage(pokemonCommand, pokemonCommandX, pokemonCommandY, commandWidth, commandHeight, null);
			g2.drawImage(runCommandSelected, runCommandX, runCommandY, commandWidth, commandHeight, null);
			g2.drawImage(fightCommand, fightCommandX, fightCommandY, commandWidth, commandHeight, null);
		}
		
		
		if(gamePanel.getKeyHandler().enterPressed == true) {
			if(currentCommand == 0) {
				fightBoolean = true;
				commandsBoolean = false;
			}
				
			if(currentCommand == 3) {
				runBoolean = true;
				commandsBoolean = false;
			}
			
			gamePanel.getKeyHandler().enterPressed = false;
		}
	}
	
	
	public void fightCommand_Open() {
		g2.drawImage(battleOverlayFight, 0, 512, gamePanel.getScreenWidth(), gamePanel.getScreenHeight() / 5, null);

		// Set color and fill rectangles
		g2.setColor(Color.WHITE);
		g2.fillRect(60, 531, 190, 39); // Top left
		g2.fillRect(360, 531, 190, 39); // Top right
		g2.fillRect(60, 586, 190, 39); // Bot left
		g2.fillRect(360, 586, 190, 39); // Bot right

		g2.setColor(Color.BLACK);
		battlePPFont = pokeFont.deriveFont(16F);
		g2.setFont(battlePPFont);
		FontMetrics metrics = g2.getFontMetrics();

		int[][] rects = { {60, 531}, {360, 531}, {60, 586}, {360, 586} };
	
		for (int i = 0; i < 4; i++) {
			if(currentCommand == i) {g2.setColor(Color.RED);}
			else {g2.setColor(Color.BLACK);}	
			
		    String text = playerObj.getCurrentPokemon().getAttacks().get(i).getName();
		    int textWidth = metrics.stringWidth(text);
		    int textHeight = metrics.getAscent(); // Gets the height above baseline

		    int centerX = rects[i][0] + (190 - textWidth) / 2; 
		    int centerY = rects[i][1] + ((39 - textHeight) / 2) + textHeight; 
		    
		    g2.drawString(text, centerX, centerY);
		}


		g2.drawImage(movesTypeHM.get(playerObj.getCurrentPokemon().getAttacks().get(currentCommand).getTypeOfMove()), 655, 512+28, 64+24, 28+6, null);
		
		battlePPFont = pokeFont.deriveFont(18F);
		g2.setFont(battlePPFont);
		g2.setColor(Color.BLACK);
		g2.drawString("PP: " + playerObj.getCurrentPokemon().getAttacks().get(currentCommand).getCurrentPP() + "/" + playerObj.getCurrentPokemon().getAttacks().get(currentCommand).getPPMax(), 633, 512+88);
	
		
		if(gamePanel.getKeyHandler().enterPressed == true) {
			if(playerObj.canPlayerDoAnyMoves() && playerObj.checkCertainMove(currentCommand)) {
				playerSelectedMoveIndex = currentCommand;
				turnPrep();
				
				//System.out.println(opponentObj.getCurrentPokemon().getName() + " - " + playerObj.getCurrentPokemon().getName());
				//System.out.println(playerDamageToOpponent + " - " + opponentDamageToPlayer);
				
				playingMoveBoolean = true;
				fightBoolean = false;
			}
			
			gamePanel.getKeyHandler().enterPressed = false;
		}
		
		if(gamePanel.getKeyHandler().escapePressed == true) {
			commandsBoolean = true;
			fightBoolean = false;
			gamePanel.getKeyHandler().escapePressed = false;
			
		}
	}
	
	int turnIndex = 0;
	
	public void fightCommand_PlayOutMove() {
		g2.drawImage(textbox, 0, 512, gamePanel.getScreenWidth(), gamePanel.getScreenHeight() / 5, null);
		
		if(turnIndex == 0) {
			char characters[] = inCombatTextAL.get(inCombatTextIndex).toCharArray();
			
			if(charIndex < characters.length) {
				String s = String.valueOf(characters[charIndex]);
				combinedText = combinedText + s;
				currentDialogue = combinedText;
				charIndex++;
			}
				
			if(charIndex == characters.length) {
				if(playerIsFirst == true) {
					if(oppHealthBarWidth == newHealthBarWidthO) {
						opponentObj.getCurrentPokemon().takeDamage(playerDamageToOpponent);
						charIndex = 0;
						combinedText = "";
						inCombatTextIndex++;
						turnIndex++;
					}
					
					oppHealthBarWidth--;
				}
				else {
					if(playerHealthBarWidth == newHealthBarWidthP) {
						playerObj.getCurrentPokemon().takeDamage(opponentDamageToPlayer);
						charIndex = 0;
						combinedText = "";
						inCombatTextIndex++;
						turnIndex++;
					}
					
					playerHealthBarWidth--;	
				}
			}
				
			for(String line : currentDialogue.split("\n")) {
				g2.drawString(line, 64, 512+64);
			}
		}
		else if(turnIndex == 1) {
			char characters[] = inCombatTextAL.get(inCombatTextIndex).toCharArray();
			
			if(charIndex < characters.length) {
				String s = String.valueOf(characters[charIndex]);
				combinedText = combinedText + s;
				currentDialogue = combinedText;
				charIndex++;
			}
				
			if(charIndex == characters.length) {
				if(playerIsFirst == false) {
					if(oppHealthBarWidth == newHealthBarWidthO) {
						opponentObj.getCurrentPokemon().takeDamage(playerDamageToOpponent);
						charIndex = 0;
						combinedText = "";
						inCombatTextIndex++;
						turnIndex++;
					}
					
					oppHealthBarWidth--;
				}
				else {
					if(playerHealthBarWidth == newHealthBarWidthP) {
						playerObj.getCurrentPokemon().takeDamage(opponentDamageToPlayer);
						charIndex = 0;
						combinedText = "";
						inCombatTextIndex++;
						turnIndex++;
					}
					
					playerHealthBarWidth--;	
				}
			}
				
			for(String line : currentDialogue.split("\n")) {
				g2.drawString(line, 64, 512+64);
			}
		}
		else {
			inCombatTextIndex = 0;
			turnIndex = 0;
			commandsBoolean = true;
			playingMoveBoolean = false;
		}
	}
	
	public void bagCommand_Open() {
		
	}
	
	public void pokemonCommand_Open() {
		
	}
	
	public void runCommand_Open() {
		if(runAttemptTextAL.isEmpty()) {
			gamePanel.setGameState(gamePanel.getPlayState());
			resetValues();
		}
		else {
			g2.drawImage(textbox, 0, 512, gamePanel.getScreenWidth(), gamePanel.getScreenHeight() / 5, null);
			
			
			if(runAttemptTextIndex < runAttemptTextAL.size()) {
				char characters[] = runAttemptTextAL.get(runAttemptTextIndex).toCharArray();
				if(charIndex < characters.length) {
					String s = String.valueOf(characters[charIndex]);
					combinedText = combinedText + s;
					currentDialogue = combinedText;
					charIndex++;
				}
				
				if(gamePanel.getKeyHandler().enterPressed == true) {
					charIndex = 0;
					combinedText = "";
					
					runAttemptTextIndex++;
					gamePanel.getKeyHandler().enterPressed = false;
					
				}
				
				for(String line : currentDialogue.split("\n")) {
					g2.drawString(line, 64, 512+64);
				}
			}

			else {
				runAttemptTextIndex = 0;
				commandsBoolean = true;
				runBoolean = false;
			}
		}
		
		
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	public void turnPrep() {
		inCombatTextAL.clear();
		
		int opponentMoveIndex = opponentObj.selectMoveIndex();
		if(opponentMoveIndex != -1) {
			opponentMove = opponentObj.getCurrentPokemon().getAttacks().get(opponentMoveIndex);
		}
				
		playerMove = playerObj.getCurrentPokemon().getAttacks().get(playerSelectedMoveIndex);
				
		opponentDamageToPlayer = opponentMove.calculateDamage(opponentObj.getCurrentPokemon(), playerObj.getCurrentPokemon());
		playerDamageToOpponent = playerMove.calculateDamage(playerObj.getCurrentPokemon(), opponentObj.getCurrentPokemon());
				
		if(playerObj.getCurrentPokemon().getSpeed() >= opponentObj.getCurrentPokemon().getSpeed()) {
			playerIsFirst = true;
			
			inCombatTextAL.add(playerObj.getCurrentPokemon().getName() + " uses " + playerMove.getName());
			inCombatTextAL.add(opponentObj.getCurrentPokemon().getName() + " uses " + opponentMove.getName());
		}
		else {
			playerIsFirst = false;
			
			inCombatTextAL.add(opponentObj.getCurrentPokemon().getName() + " uses " + opponentMove.getName());
			inCombatTextAL.add(playerObj.getCurrentPokemon().getName() + " uses " + playerMove.getName());
		}
		
		
		int newHealthP = Math.max(0,playerObj.getCurrentPokemon().getHealth() - opponentDamageToPlayer);
		newHealthBarWidthP = (int) ((double) newHealthP / playerObj.getCurrentPokemon().getMaxHealth() * playerHealthBarWidth);
		
		int newHealthO = Math.max(0,opponentObj.getCurrentPokemon().getHealth() - playerDamageToOpponent);
		newHealthBarWidthO = (int) ((double) newHealthO / opponentObj.getCurrentPokemon().getMaxHealth() * oppHealthBarWidth);;
	}
	
	public void updateHealthBarOpp() {
		int targetHealth = opponentObj.getCurrentPokemon().getHealth() - playerDamageToOpponent;
		int targetWidthO = (targetHealth * oppHealthBarWidth) / opponentObj.getCurrentPokemon().getMaxHealth();
		
		for(int i = oppHealthBarWidth; i > targetWidthO; i--) {
			oppHealthBarWidth = i;
		}
	}
	

	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////
	
	
	public void resetValues() {
		battleIntroTransition = true;
		battleStartIntro = false;
		battleStarted = false;
		
		battleCounter = 0;
		
		playerBarX = -SCREEN_WIDTH/2;
		opponentBarX = SCREEN_WIDTH;
		battleBarY = (SCREEN_HEIGHT / 2) - 130;
		playerIconBarX = -380;
		opponentIconBarX = 920;
		
		vs_X = SCREEN_WIDTH/2 - (122/2);
		vs_Y = 330;
		vs_Width = 122;
		vs_Height = 108;
		
		countdown1 = 0;
		
		//Battle Intro
		topRectIntro = 0;
		bottomRectIntro = 320;
		
		basePlayerX = 750;
		baseOpponentX = -440;
		playerBackX = 950;
		opponentFrontX = -400;	
		
		currentCommand = 0;
		
		commandsBoolean = true;
		fightBoolean = false;
		bagBoolean = false;
		pokemonBoolean = false;
		runBoolean = false;
	}
	
	
	
	public void drawDialogueSingle(ArrayList<String> p_arrayList) {
		char characters[] = p_arrayList.get(inCombatTextIndex).toCharArray();
		if(charIndex < characters.length) {
			String s = String.valueOf(characters[charIndex]);
			combinedText = combinedText + s;
			currentDialogue = combinedText;
			charIndex++;
		}
			
		if(gamePanel.getKeyHandler().enterPressed == true) {
			charIndex = 0;
			combinedText = "";
			
			inCombatTextIndex++;
			gamePanel.getKeyHandler().enterPressed = false;
		}
			
		for(String line : currentDialogue.split("\n")) {
			g2.drawString(line, 64, 512+64);
		}
	}
	
	
	////////////////////////////////////////////////////////////////////////////
	
	public void lineUpUI() {
		g2.drawImage(battleOverlayLineUp, -10, 190, 440, 12, null);
		g2.drawImage(battleOverlayLineUp, 810, 430, -440, 12, null); //Flipped
		for(int i = 1; i <= 6; i++) {
			if(playerObj.getTeam().size() >= i) {
				if(playerObj.getTeam().get(i-1).hasFainted() == false) {
					g2.drawImage(ballIcon, 20 + (i * 50), 150, 40, 40, null); 
				}
				else {
					g2.drawImage(faintedBallIcon, 20 + (i * 50), 150, 40, 40, null); 
				}
			}
			else {
				g2.drawImage(emptyBallIcon, 20  + (i * 50), 150, 40, 40, null); 
			}
			
			
			if(opponentObj.getTeam().size() >= i) {
				if(opponentObj.getTeam().get(i-1).hasFainted() == false) {
					g2.drawImage(ballIcon, 420  + (i * 50), 390, 40, 40, null); 
				}
				else {
					g2.drawImage(faintedBallIcon, 420  + (i * 50), 390, 40, 40, null); 
				}
			}
			else {
				g2.drawImage(emptyBallIcon, 420  + (i * 50), 390, 40, 40, null); 
			}
		}
	}
	
	
	
	public void healthBarsUI() {
		g2.drawImage(opponentHealthBox, 0, 50, 410, 122, null);
		g2.drawImage(userHealthBox, SCREEN_WIDTH - 410, 340, 410, 122, null);
		
		g2.drawString("Lv" + Integer.toString(opponentObj.getCurrentPokemon().getLevel()), 250, 110);
		g2.drawString("Lv" + Integer.toString(playerObj.getCurrentPokemon().getLevel()), 670, 400);
		
		g2.setColor(Color.GREEN);
		g2.fillRect(186, 129, oppHealthBarWidth, 12);
		g2.fillRect(604, 419, playerHealthBarWidth, 12);
		
		pokeFont = pokeFont.deriveFont(Font.BOLD, 18F);
		g2.setColor(Color.BLACK);
		g2.setFont(pokeFont);
		g2.drawString(opponentObj.getCurrentPokemon().getName().toUpperCase(), 10, 110);
		g2.drawString(playerObj.getCurrentPokemon().getName().toUpperCase(), 440, 400);
	}
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
    public void drawSubWindow(int x, int y, int width, int height, Color color) {
        g2.setColor(color);
        g2.fillRect(x, y, width, height);
    }
	
	
    public void loadBattleImages() {
		try {
			userHealthBox = ImageIO.read(getClass().getResourceAsStream("/battleUI/databox_user.png"));
			opponentHealthBox = ImageIO.read(getClass().getResourceAsStream("/battleUI/databox_opp.png"));
			
			//Battle Icons
			opponentBarIcon = ImageIO.read(getClass().getResourceAsStream("/battleIcons/RIVAL_icon.png"));
			opponentBarIconSilhouette = ImageIO.read(getClass().getResourceAsStream("/battleIcons/RIVAL_icon_silhouette.png"));
			
			//Battle Trainers
			opponentFront = ImageIO.read(getClass().getResourceAsStream("/battleTrainers/rival_front.png"));
						
			//Poke 
			charizardFront = ImageIO.read(getClass().getResourceAsStream("/battlePokeFront/CHARIZARD.png"));
			pikachuBack = ImageIO.read(getClass().getResourceAsStream("/battlePokeBack/PIKACHU.png"));
			
			
			//////////////////////////////////////////////////////////////////////////////
			
			//Battle Icons
			playerBarIcon = ImageIO.read(getClass().getResourceAsStream("/battleIcons/TRAINER_icon.png"));
			playerBarIconSilhouette = ImageIO.read(getClass().getResourceAsStream("/battleIcons/TRAINER_icon_silhouette.png"));
			
			//Battle bars
			playerBar = ImageIO.read(getClass().getResourceAsStream("/battleBars/Player_bar.png"));
			opponentBar = ImageIO.read(getClass().getResourceAsStream("/battleBars/Rival_bar.png"));
			
			//Battle UI
			textbox = ImageIO.read(getClass().getResourceAsStream("/ui/textbox.png"));
			vsIcon = ImageIO.read(getClass().getResourceAsStream("/battleUI/vsIcon.png"));
			battleOverlay = ImageIO.read(getClass().getResourceAsStream("/battleUI/overlay_battle.png"));
			battleOverlayTextbox = ImageIO.read(getClass().getResourceAsStream("/battleUI/overlay_battleTextBox.png"));
			battleOverlayFight = ImageIO.read(getClass().getResourceAsStream("/battleUI/overlay_fight.png"));
			
			battleOverlayLineUp = ImageIO.read(getClass().getResourceAsStream("/battleUI/overlay_lineup.png"));
			ballIcon = ImageIO.read(getClass().getResourceAsStream("/battleUI/icon_ball.png"));
			emptyBallIcon = ImageIO.read(getClass().getResourceAsStream("/battleUI/icon_ball_empty.png"));
			faintedBallIcon = ImageIO.read(getClass().getResourceAsStream("/battleUI/icon_ball_faint.png"));
			
			//Battle commands
			fightCommand = ImageIO.read(getClass().getResourceAsStream("/battleCommands/command_01.png"));
			pokemonCommand = ImageIO.read(getClass().getResourceAsStream("/battleCommands/command_03.png"));
			bagCommand = ImageIO.read(getClass().getResourceAsStream("/battleCommands/command_05.png"));
			runCommand = ImageIO.read(getClass().getResourceAsStream("/battleCommands/command_07.png"));
			cancelCommand = ImageIO.read(getClass().getResourceAsStream("/battleCommands/command_19.png"));
			
			fightCommandSelected = ImageIO.read(getClass().getResourceAsStream("/battleCommands/command_02.png"));
			pokemonCommandSelected = ImageIO.read(getClass().getResourceAsStream("/battleCommands/command_04.png"));
			bagCommandSelected = ImageIO.read(getClass().getResourceAsStream("/battleCommands/command_06.png"));
			runCommandSelected = ImageIO.read(getClass().getResourceAsStream("/battleCommands/command_08.png"));
			cancelCommandSelected = ImageIO.read(getClass().getResourceAsStream("/battleCommands/command_20.png"));
			
			//Battle Background
			fieldBG = ImageIO.read(getClass().getResourceAsStream("/battleBG/field_bg.png"));
			
			//Battle Base
			fieldBasePlayer = ImageIO.read(getClass().getResourceAsStream("/battleBase/field_base0.png"));
			fieldBaseOpponent = ImageIO.read(getClass().getResourceAsStream("/battleBase/field_base1.png"));
			
			//Battle Trainers
			playerBack1 = ImageIO.read(getClass().getResourceAsStream("/battleTrainers/player_back_1.png"));
			playerBack2 = ImageIO.read(getClass().getResourceAsStream("/battleTrainers/player_back_2.png"));
			playerBack3 = ImageIO.read(getClass().getResourceAsStream("/battleTrainers/player_back_3.png"));
			playerBack4 = ImageIO.read(getClass().getResourceAsStream("/battleTrainers/player_back_4.png"));
			playerBack5 = ImageIO.read(getClass().getResourceAsStream("/battleTrainers/player_back_5.png"));
			
			//types
			fireTypeIcon = ImageIO.read(getClass().getResourceAsStream("/types/fire.png"));
			waterTypeIcon = ImageIO.read(getClass().getResourceAsStream("/types/water.png"));
			grassTypeIcon = ImageIO.read(getClass().getResourceAsStream("/types/grass.png"));
			electricTypeIcon = ImageIO.read(getClass().getResourceAsStream("/types/electric.png"));
			iceTypeIcon = ImageIO.read(getClass().getResourceAsStream("/types/ice.png"));
			fightingTypeIcon = ImageIO.read(getClass().getResourceAsStream("/types/fight.png"));
			poisonTypeIcon = ImageIO.read(getClass().getResourceAsStream("/types/poison.png"));
			groundTypeIcon = ImageIO.read(getClass().getResourceAsStream("/types/ground.png"));
			flyingTypeIcon = ImageIO.read(getClass().getResourceAsStream("/types/flying.png"));
			psychicTypeIcon = ImageIO.read(getClass().getResourceAsStream("/types/psychic.png"));
			bugTypeIcon = ImageIO.read(getClass().getResourceAsStream("/types/bug.png"));
			rockTypeIcon = ImageIO.read(getClass().getResourceAsStream("/types/rock.png"));
			ghostTypeIcon = ImageIO.read(getClass().getResourceAsStream("/types/ghost.png"));
			dragonTypeIcon = ImageIO.read(getClass().getResourceAsStream("/types/dragon.png"));
			darkTypeIcon = ImageIO.read(getClass().getResourceAsStream("/types/dark.png"));
			steelTypeIcon = ImageIO.read(getClass().getResourceAsStream("/types/steel.png"));
			fairyTypeIcon = ImageIO.read(getClass().getResourceAsStream("/types/fairy.png"));
			normalTypeIcon = ImageIO.read(getClass().getResourceAsStream("/types/normal.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
    	//Loading fonts
		try {
			InputStream is1 = getClass().getResourceAsStream("/font/PKMN RBYGSC.ttf");
			pokeFont = Font.createFont(Font.TRUETYPE_FONT, is1);
			
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		movesTypeHM.put("Fire", fireTypeIcon);
		movesTypeHM.put("Water", waterTypeIcon);
		movesTypeHM.put("Grass", grassTypeIcon);
		movesTypeHM.put("Electric", electricTypeIcon);
		movesTypeHM.put("Ice", iceTypeIcon);
		movesTypeHM.put("Fighting", fightingTypeIcon);
		movesTypeHM.put("Poison", poisonTypeIcon);
		movesTypeHM.put("Ground", groundTypeIcon);
		movesTypeHM.put("Flying", flyingTypeIcon);
		movesTypeHM.put("Psychic", psychicTypeIcon);
		movesTypeHM.put("Bug", bugTypeIcon);
		movesTypeHM.put("Rock", rockTypeIcon);
		movesTypeHM.put("Ghost", ghostTypeIcon);
		movesTypeHM.put("Dragon", dragonTypeIcon);
		movesTypeHM.put("Dark", darkTypeIcon);
		movesTypeHM.put("Steel", steelTypeIcon);
		movesTypeHM.put("Fairy", fairyTypeIcon);
		movesTypeHM.put("Normal", normalTypeIcon);
    }


	public Player getPlayerObj() {
		return playerObj;
	}


	public void setPlayerObj(Player playerObj) {
		this.playerObj = playerObj;
	}
	
	
	
}
