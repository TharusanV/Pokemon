package ui;

import java.awt.AlphaComposite;
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
import entity.Entity;
import entity.Player;
import entity.Trainer;
import main.GamePanel;

public class BattleUI {

	GamePanel gamePanel;
	Graphics2D g2;
	
	int SCREEN_WIDTH = 800;
	int SCREEN_HEIGHT = 640;

	HashMap<String, BufferedImage> movesTypeHM = new HashMap<String, BufferedImage>();
	
	ArrayList<String> introTextAL = new ArrayList<String>();
	ArrayList<String> inCombatTextAL = new ArrayList<String>();
	ArrayList<String> runAttemptTextAL = new ArrayList<String>();
	ArrayList<String> endingTextAL_oppWin = new ArrayList<String>();
	ArrayList<String> endingTextAL_playerWin = new ArrayList<String>();
	
	//////	Declaring Asset Variables //////
	//Battle Start
	BufferedImage playerBarIcon, opponentBarIcon;
	BufferedImage playerBarIconSilhouette, opponentBarIconSilhouette;
	BufferedImage playerBar, opponentBar;
	BufferedImage vsIcon;
	
	//Battle Intro
	BufferedImage fieldBG;
	BufferedImage playerBack1,playerBack2,playerBack3,playerBack4,playerBack5,opponentFront;
	BufferedImage fieldBasePlayer, fieldBaseOpponent;
	
	BufferedImage battleOverlayLineUp;
	BufferedImage ballIcon, emptyBallIcon, faintedBallIcon;
	
	//Battle
	BufferedImage textbox, battleOverlay, battleOverlayTextbox, battleOverlayFight;
	BufferedImage fightCommand, bagCommand, pokemonCommand, runCommand, cancelCommand;
	BufferedImage fightCommandSelected, bagCommandSelected, pokemonCommandSelected, runCommandSelected, cancelCommandSelected;
	
	//Type icons
	BufferedImage fireTypeIcon, waterTypeIcon, grassTypeIcon, electricTypeIcon, iceTypeIcon, fightingTypeIcon, poisonTypeIcon, groundTypeIcon, flyingTypeIcon, psychicTypeIcon, bugTypeIcon, rockTypeIcon, ghostTypeIcon, dragonTypeIcon, darkTypeIcon, steelTypeIcon, fairyTypeIcon, normalTypeIcon;
	
	BufferedImage userHealthBox;
	BufferedImage opponentHealthBox;
	
	BufferedImage oppPokeball;
	
	//Pokemon (testing)
	BufferedImage oppPokemonImage, playerPokemonImage;
	
	//Dialogue
	Font pokeFont;
	Font battlePPFont;
	
	
	//////Reset-able Variables //////

	Player playerObj = null;
	Trainer opponentObj = null;
	Move opponentMove = null;
	Move playerMove = null;
	
	//ALL States
	boolean battleIntroTransition = true;
	boolean battleStartIntro = false;
	boolean battleStarted = false;
	boolean battleEnding = false;
	
	//Dialogue
	String currentDialogue = "";
	int charIndex = 0;
	String combinedText = "";
	
	int introTextIndex = 0;
	int inCombatTextIndex = 0;
	int runAttemptTextIndex = 0;
	
	int playerPokemonY = 296;
	int opponentPokemonY = 120;
	
	int playerBarX = -SCREEN_WIDTH/2;
	int opponentBarX = SCREEN_WIDTH;
	int battleBarY = (SCREEN_HEIGHT / 2) - 130;
	int playerIconBarX = -380;
	int opponentIconBarX = 920;
	
	int vs_X = SCREEN_WIDTH/2 - (122/2);
	int vs_Y = 330;
	int vs_Width = 122;
	int vs_Height = 108;
	
	int topRectIntro = 0;
	int bottomRectIntro = 320;
	
	int basePlayerX = 750;
	int baseOpponentX = -440;
	int playerBackX = 950;
	int opponentFrontX = -400;
	
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
	
	int playerHealthBarWidth = 152;
	int oppHealthBarWidth = 152;
	
	int newHealthBarWidthP = 152;
	int newHealthBarWidthO = 152;
	
	int lengthOfDialogue = 0;
	
	//Intro Booleans
	boolean barsInPlace = false;
	boolean iconsInPlace = false;
	
	boolean basesInPlace = false;
	boolean rectanglesInPlace = false;
	
	boolean shiftedTrainerIcons = false;
	
	boolean shownOpponentText = false;
	boolean opponentPokemonDisplayed = false;
	boolean shiftedOpponentIcon = false;
	boolean shownPlayerText = false;
	
	int transitionIntroTimer = 0;
	
	int introTimer1 = 0;
	int introTimer2 = 0;
	int introTimer3 = 0;

	
	//Fight Booleans
	boolean commandsBoolean = true;
	boolean fightBoolean = false;
	boolean bagBoolean = false;
	boolean pokemonBoolean = false;
	boolean runBoolean = false;
	boolean playingMoveBoolean = false;
	
	public int currentCommand = 0;
	
	int playerAttackIndex = 0;
	int opponentDamageToPlayer = 0;
	int playerDamageToOpponent = 0;
	boolean playerIsFirst = false;
	int turnCounter = 0;

	//Ending variables
	float alpha = 0.0f;
	boolean playerLost = false;
	boolean opponentLost = false;
	int endingIndex = 0;
	int endingCounter = 0;
	int endingDialogueIndex = 0;
	
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
		endingTextAL_oppWin.clear();
		endingTextAL_playerWin.clear();
		
		opponentBarIcon =  opponentObj.getBarIcon();
		opponentBarIconSilhouette = opponentObj.getBarIconSilhouette();
		opponentBar = opponentObj.getBar();
		opponentFront = opponentObj.getFrontIcon();
		
		opponentObj.setSelectedPokemonIndex(opponentObj.findFirstMemberWithHealth());
		playerObj.setSelectedPokemonIndex(playerObj.findFirstMemberWithHealth());
	
		oppPokemonImage = opponentObj.getCurrentPokemon().getFrontImage(); 
		playerPokemonImage = playerObj.getCurrentPokemon().getBackImage();
		
		introTextAL.add(opponentObj.getName() + " would like to battle!");
		introTextAL.add(opponentObj.getName() + " sent out " + opponentObj.getCurrentPokemon().getName() + "!");
		introTextAL.add("Go! " + playerObj.getCurrentPokemon().getName() + "!");
			
		runAttemptTextAL.add("You cannot run from a Trainer battle!");
		
		endingTextAL_oppWin.add("You are out of usable pokemon!");
		endingTextAL_oppWin.add("You paid out 416 to the winner.");
		endingTextAL_oppWin.add("... ... ... ...");
		endingTextAL_oppWin.add("You blacked out!");
		
		endingTextAL_playerWin.add("You defeated " + opponentObj.getName());
		for(int i = 0; i < opponentObj.dialogues[1].length; i++) {
			if(opponentObj.dialogues[1][i] != null){
				endingTextAL_playerWin.add(opponentObj.dialogues[1][i]);
				lengthOfDialogue++;
			}
		}
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
		if(battleEnding) {
			endingBattle();
		}
		
		g2.dispose();
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	public void startBattle() {
		//Transparent BG
		Color c = new Color(0, 0, 0, 100);
		drawSubWindow(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, c);
		//Side-Bars
		g2.drawImage(playerBar, playerBarX, battleBarY, SCREEN_WIDTH/2, 196, null);
		g2.drawImage(opponentBar, opponentBarX, battleBarY, SCREEN_WIDTH/2, 196, null);
		//Silhouettes
		g2.drawImage(playerBarIconSilhouette, playerIconBarX, battleBarY + 8, 256, 178, null);
		g2.drawImage(opponentBarIconSilhouette, opponentIconBarX, battleBarY + 8, 256, 178, null);
		
		if (!barsInPlace) {
		    playerBarX += 20;
		    opponentBarX -= 20;
		    if (playerBarX == 0) {barsInPlace = true;}
		}

		if (barsInPlace && !iconsInPlace) {
		    playerIconBarX += 10;
		    opponentIconBarX -= 10;
		    if (playerIconBarX >= 60) {iconsInPlace = true;}
		}
		
		else if(barsInPlace && iconsInPlace && transitionIntroTimer != 30) { //Wait 30 frames
			g2.drawImage(playerBarIcon, playerIconBarX, battleBarY + 8, 256, 178, null);
			g2.drawImage(opponentBarIcon, opponentIconBarX, battleBarY + 8, 256, 178, null);
			g2.drawImage(vsIcon,vs_X,vs_Y,vs_Width,vs_Height,null);
			
			transitionIntroTimer++;
		}
		else if(transitionIntroTimer == 30) {
			g2.drawImage(playerBarIcon, playerIconBarX, battleBarY + 8, 256, 178, null);
			g2.drawImage(opponentBarIcon, opponentIconBarX, battleBarY + 8, 256, 178, null);
			g2.drawImage(vsIcon,vs_X,vs_Y,vs_Width,vs_Height,null);
			
			vs_Width += 4;
			vs_Height += 4;
		    vs_X -= 4 / 2;
		    vs_Y -= 4 / 2;
		    
		    if(vs_Width >= 600) {
		    	transitionIntroTimer = 0;
		    	barsInPlace = false; 
		    	iconsInPlace = false;
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
		
		g2.drawImage(battleOverlay, 0, 512, gamePanel.getScreenWidth(), gamePanel.getScreenHeight() / 5, null);
		g2.drawImage(textbox, 0, 512, gamePanel.getScreenWidth(), gamePanel.getScreenHeight() / 5, null);
		
		lineUpUI();
		
		//Part 1 - Move black rectangles and shift the bases/trainer icons into position
		if(!rectanglesInPlace) {
			topRectIntro -= 4;
			bottomRectIntro += 4;
			
			g2.fillRect(0, topRectIntro, gamePanel.getScreenWidth(), gamePanel.getScreenHeight() / 2);
	        g2.fillRect(0, bottomRectIntro, gamePanel.getScreenWidth(), gamePanel.getScreenHeight() / 2);
	        
	        if(bottomRectIntro == 640) {rectanglesInPlace = true;}
		}
		
		
		if(!basesInPlace) {
			g2.drawImage(playerBack1, playerBackX, 272, 240, 240, null);
			g2.drawImage(opponentFront, opponentFrontX, 120, 192, 192, null);
			
			basePlayerX -= 10;
			baseOpponentX += 10;
			playerBackX -= 10;
			opponentFrontX += 10;
			
			if(basePlayerX == -150) {basesInPlace = true;}
		}
		
		//Part 2 - States what trainer wants to battle
		if(basesInPlace && !shownOpponentText) {
			g2.drawImage(playerBack1, playerBackX, 272, 240, 240, null);
			g2.drawImage(opponentFront, opponentFrontX, 120, 192, 192, null);
				
			char characters[] = introTextAL.get(0).toCharArray();
			if(charIndex < characters.length) {
				String s = String.valueOf(characters[charIndex]);					
				combinedText = combinedText + s;
				currentDialogue = combinedText;
				charIndex++;
			}
			
			for(String line : currentDialogue.split("\n")) {
				g2.drawString(line, 64, 512+64);
			}
				
			if(charIndex == characters.length) {
				introTimer1++;
				
				if(introTimer1 == 45) {
					charIndex = 0;
					combinedText = "";
					introTimer1 = 0;
					shownOpponentText = true;
				}
			}
		}
		
		//Part 3  - Opponent throws pokemon
		if(shownOpponentText && !opponentPokemonDisplayed) {
			g2.drawImage(playerBack1, playerBackX, 272, 240, 240, null);
			
			char characters[] = introTextAL.get(1).toCharArray();
			if(charIndex < characters.length) {
				String s = String.valueOf(characters[charIndex]);					
				combinedText = combinedText + s;
				currentDialogue = combinedText;
				charIndex++;
			}
				
			for(String line : currentDialogue.split("\n")) {
				g2.drawString(line, 64, 512+64);
			}
			
			
			//Drop Pokeball whilst at the same time, shift opp to right
			if (!shiftedOpponentIcon) {
				g2.drawImage(opponentFront, opponentFrontX, 120, 192, 192, null);
				g2.drawImage(oppPokeball, 610, 260, 32, 64, null);
			    opponentFrontX += 10;
			
			    if(opponentFrontX == 750) {shiftedOpponentIcon = true;}
			}
			//When opp off the screen then show opponent pokemon animation
			if (shiftedOpponentIcon && charIndex == characters.length) {
				g2.drawImage(oppPokemonImage, 530, opponentPokemonY, 192, 192, null);
				introTimer2++;
				
				if(introTimer2 == 10) {
					charIndex = 0;
					combinedText = "";
					introTimer2 = 0;
					opponentPokemonDisplayed = true;
				}
			}
		}
	
		//Part 4 - User throws pokemon whilst shifting left 
		if(opponentPokemonDisplayed && !shownPlayerText) {
			g2.drawImage(oppPokemonImage, 530, opponentPokemonY, 192, 192, null);
			
			char characters[] = introTextAL.get(2).toCharArray();
			if(charIndex < characters.length) {				
				String s = String.valueOf(characters[charIndex]);					
				combinedText = combinedText + s;
				currentDialogue = combinedText;
				charIndex++;
				
				g2.drawImage(playerBack1, playerBackX, 272, 240, 240, null);
			}
				
			for(String line : currentDialogue.split("\n")) {
				g2.drawString(line, 64, 512+64);
			}
			
			if(charIndex == characters.length) {
				introTimer3++;
				int animationSpeed = 6; 
				BufferedImage[] playerBackFrames = { playerBack1, playerBack2, playerBack3, playerBack4, playerBack5 };
				int totalFrames = playerBackFrames.length;
				if (introTimer3 / animationSpeed < totalFrames) {g2.drawImage(playerBackFrames[introTimer3 / animationSpeed], playerBackX, 272, 240, 240, null);}
				else {
					g2.drawImage(playerBack1, playerBackX, 272, 240, 240, null);
					g2.drawImage(playerPokemonImage, 50, playerPokemonY, 240, 240, null);
				}
				
				if (!shiftedTrainerIcons) {
					playerBackX -= 10;
				    if(playerBackX == -250) {shiftedTrainerIcons = true;}
				}
				
				if(shiftedTrainerIcons == true) {
					charIndex = 0;
					combinedText = "";
					introTimer3 = 0;
					shownPlayerText = true;
				}
			}	
		}
		if(shownPlayerText) {
			introTimer1 = 0;
			introTimer2 = 0;
			introTimer3 = 0;
			introTextIndex = 0;
			battleStarted = true;
			battleStartIntro = false;
		}
		
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void inBattle() {
		g2.drawImage(fieldBG, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
		g2.drawImage(fieldBasePlayer, basePlayerX, 448, 512 + (512/3), 64, null);
		g2.drawImage(fieldBaseOpponent, baseOpponentX, 220, 265 + (265/3), 128 + (128/3), null);
		g2.drawImage(playerPokemonImage, 50, playerPokemonY, 240, 240, null);
		g2.drawImage(oppPokemonImage, 530, opponentPokemonY, 192, 192, null);
		g2.drawImage(battleOverlay, 0, 512, gamePanel.getScreenWidth(), gamePanel.getScreenHeight() / 5, null);
		
		
		if(commandsBoolean == true) {
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
		g2.drawImage(battleOverlayTextbox, 0, 512, gamePanel.getScreenWidth(), gamePanel.getScreenHeight() / 5, null);
		healthBarPlayerUI();
		healthBarOppUI();
		
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
		//Images
		g2.drawImage(battleOverlayFight, 0, 512, gamePanel.getScreenWidth(), gamePanel.getScreenHeight() / 5, null);
		healthBarPlayerUI();
		healthBarOppUI();
		
		// Set color and fill rectangles
		g2.setColor(Color.WHITE);
		g2.fillRect(60, 531, 190, 39); g2.fillRect(360, 531, 190, 39); 
		g2.fillRect(60, 586, 190, 39); g2.fillRect(360, 586, 190, 39); 

		// Draw Moves into rectangles
		showAllMovesUI();

		// Display type of move and PP
		g2.drawImage(movesTypeHM.get(playerObj.getCurrentPokemon().getAttacks().get(currentCommand).getTypeOfMove()), 655, 512+28, 64+24, 28+6, null);
		
		battlePPFont = pokeFont.deriveFont(18F);
		g2.setFont(battlePPFont);
		g2.setColor(Color.BLACK);
		g2.drawString("PP: " + playerObj.getCurrentPokemon().getAttacks().get(currentCommand).getCurrentPP() + "/" + playerObj.getCurrentPokemon().getAttacks().get(currentCommand).getPPMax(), 633, 512+88);
	
		//Enter Pressed
		if(gamePanel.getKeyHandler().enterPressed == true) {
			if(playerObj.getCurrentPokemon().canDoAnyMoves()) { //If player's pokemon can do any moves
				if(playerObj.getCurrentPokemon().checkCertainMove(currentCommand)) {//If the current selected pokemon has PP
					playerAttackIndex = currentCommand;
					turnPrep();
					playingMoveBoolean = true;
					fightBoolean = false;
				}
			}
			else { //Means the player's current pokemon can't use any moves, so they use 'struggle'
				playerAttackIndex = 4;
				turnPrep();
				playingMoveBoolean = true;
				fightBoolean = false;
			}
			
			gamePanel.getKeyHandler().enterPressed = false;
		}
		
		
		//Escape Pressed
		if(gamePanel.getKeyHandler().escapePressed == true) {
			commandsBoolean = true;
			fightBoolean = false;
			gamePanel.getKeyHandler().escapePressed = false;
			
		}
	}
	


	
	public void fightCommand_PlayOutMove() {
		g2.drawImage(textbox, 0, 512, gamePanel.getScreenWidth(), gamePanel.getScreenHeight() / 5, null);
		healthBarPlayerUI();
		healthBarOppUI();
		
		if(turnCounter == 0) {
			char characters[] = inCombatTextAL.get(inCombatTextIndex).toCharArray();
			
			if(charIndex < characters.length) {
				String s = String.valueOf(characters[charIndex]);
				combinedText = combinedText + s;
				currentDialogue = combinedText;
				charIndex++;
			}
				
			if(charIndex == characters.length) {
				if(playerIsFirst == true) {playerHitOpponent();}
				else {opponentHitPlayer();}
			}
				
			for(String line : currentDialogue.split("\n")) {g2.drawString(line, 64, 512+64);}
		}
		
		else if(turnCounter == 1) {
			if(playerObj.getCurrentPokemon().hasFainted() == false && opponentObj.getCurrentPokemon().hasFainted() == false) {				
				char characters[] = inCombatTextAL.get(inCombatTextIndex).toCharArray();
				
				if(charIndex < characters.length) {
					String s = String.valueOf(characters[charIndex]);
					combinedText = combinedText + s;
					currentDialogue = combinedText;
					charIndex++;
				}
					
				if(charIndex == characters.length) {
					if(playerIsFirst == true) {opponentHitPlayer();}
					else {playerHitOpponent();}
				
				}
					
				for(String line : currentDialogue.split("\n")) {g2.drawString(line, 64, 512+64);}
			}	
			else {
				turnCounter = 2;
			}
		}
		else if(turnCounter == 2) {
			if(playerObj.getCurrentPokemon().hasFainted()) {
				//1 - Display fainted text
				String text = playerObj.getCurrentPokemon().getName()+" has fainted!";
				char characters[] = text.toCharArray();
				
				if(charIndex < characters.length) {
					String s = String.valueOf(characters[charIndex]);
					combinedText = combinedText + s;
					currentDialogue = combinedText;
					charIndex++;
				}
				
				for(String line : currentDialogue.split("\n")) {g2.drawString(line, 64, 512+64);}
				
				//2 - Drop fainted pokemon sprite
				if(playerPokemonY < 500) {
					playerPokemonY += 4;
				}
				
				//3 - If text finished and drop sprited finished then we can continue then uses presses enter
				if(charIndex == characters.length && playerPokemonY >= 500) {
					if(gamePanel.getKeyHandler().enterPressed == true) {
						charIndex = 0;
						combinedText = "";
						
						checkForAvailablePokemon(playerObj);
						gamePanel.getKeyHandler().enterPressed = false;
						
					}
				}
			}
			else if(opponentObj.getCurrentPokemon().hasFainted()) {
				//1 - Display fainted text
				String text = opponentObj.getCurrentPokemon().getName()+" has fainted!";
				char characters[] = text.toCharArray();
				
				if(charIndex < characters.length) {
					String s = String.valueOf(characters[charIndex]);
					combinedText = combinedText + s;
					currentDialogue = combinedText;
					charIndex++;
				}
				
				for(String line : currentDialogue.split("\n")) {g2.drawString(line, 64, 512+64);}
				
				//2 - Drop fainted pokemon sprite
				if(opponentPokemonY < 600) {
					opponentPokemonY += 8;
				}
				
				//3 - If text finished and drop sprited finished then we can continue then uses presses enter
				if(charIndex == characters.length && opponentPokemonY >= 500) {
					if(gamePanel.getKeyHandler().enterPressed == true) {
						charIndex = 0;
						combinedText = "";
						
						checkForAvailablePokemon(opponentObj);
						gamePanel.getKeyHandler().enterPressed = false;
						
					}
				}
			}
			else {
				turnCounter++;
			}
		}
		else {
			inCombatTextIndex = 0;
			turnCounter = 0;
			currentCommand = 0;
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
	
	private void endingBattle() {
		if(playerLost) {
			playerLost_EndingBattle();
		}
		
		if(opponentLost) {
			oppLost_EndingBattle();
		}
		
	}
	
	private void playerLost_EndingBattle(){
		//Images
		g2.drawImage(fieldBG, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
		g2.drawImage(fieldBasePlayer, basePlayerX, 448, 512 + (512/3), 64, null);
		g2.drawImage(fieldBaseOpponent, baseOpponentX, 220, 265 + (265/3), 128 + (128/3), null);
		g2.drawImage(oppPokemonImage, 530, opponentPokemonY, 192, 192, null);
		healthBarOppUI();
		g2.drawImage(battleOverlay, 0, 512, gamePanel.getScreenWidth(), gamePanel.getScreenHeight() / 5, null);
		g2.drawImage(textbox, 0, 512, gamePanel.getScreenWidth(), gamePanel.getScreenHeight() / 5, null);
		
		if(endingIndex == 0) {
			//Dialogue
			char characters[] = endingTextAL_oppWin.get(endingDialogueIndex).toCharArray();
			if(charIndex < characters.length) {
				String s = String.valueOf(characters[charIndex]);					
				combinedText = combinedText + s;
				currentDialogue = combinedText;
				charIndex++;
			}
			
			for(String line : currentDialogue.split("\n")) {
				g2.drawString(line, 64, 512+64);
			}
			
			if(charIndex == characters.length) {
				endingCounter++;
				
				if(endingCounter == 60) {
					if(endingDialogueIndex != 3) {
						charIndex = 0;
						combinedText = "";
						endingCounter = 0;
						endingDialogueIndex++;
						
					}
					else {
						charIndex = 0;
						combinedText = "";
						endingCounter = 0;
						endingDialogueIndex = 0;
						endingIndex = 1;
					}
				}
			}
		}
		else {
			alpha += 0.01f; 
	        if (alpha > 1.0f) {
	        	alpha = 1.0f;
	        	
	        	endingCounter++;
	        	
	        	if(endingCounter == 60) {
	        		battleEnding = false;
		        	playerLost = false;
		        	endingIndex = 0;
		        	endingCounter = 0;
		        	gamePanel.setGameState(gamePanel.getPlayState());
	        	}
	        }
	        
	        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
	        g2.setColor(Color.BLACK);
	        g2.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		}
	}
	
	private void oppLost_EndingBattle() {
		//Images
		g2.drawImage(fieldBG, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
		g2.drawImage(fieldBasePlayer, basePlayerX, 448, 512 + (512/3), 64, null);
		g2.drawImage(fieldBaseOpponent, baseOpponentX, 220, 265 + (265/3), 128 + (128/3), null);
		g2.drawImage(playerPokemonImage, 50, playerPokemonY, 240, 240, null);
		healthBarPlayerUI();
		
		g2.drawImage(opponentFront, opponentFrontX, 120, 192, 192, null);
		
		g2.drawImage(battleOverlay, 0, 512, gamePanel.getScreenWidth(), gamePanel.getScreenHeight() / 5, null);
		g2.drawImage(textbox, 0, 512, gamePanel.getScreenWidth(), gamePanel.getScreenHeight() / 5, null);
		
		if(endingIndex == 0) {
			//Dialogue
			char characters[] = endingTextAL_playerWin.get(endingDialogueIndex).toCharArray();
			if(charIndex < characters.length) {
				String s = String.valueOf(characters[charIndex]);					
				combinedText = combinedText + s;
				currentDialogue = combinedText;
				charIndex++;
			}
			
			for(String line : currentDialogue.split("\n")) {
				g2.drawString(line, 64, 512+64);
			}
			
			if(endingDialogueIndex >= 1 && opponentFrontX >= 600) {
				opponentFrontX-=5;
			}
			
			if(charIndex == characters.length) {
				if(endingDialogueIndex < lengthOfDialogue) {
					if(gamePanel.getKeyHandler().enterPressed == true) {
						charIndex = 0;
						combinedText = "";
						endingCounter = 0;
						endingDialogueIndex++;
						gamePanel.getKeyHandler().enterPressed = false;
					}
				}
				else {
					endingCounter++;
		        	
		        	if(endingCounter == 60) {
		        		gamePanel.getKeyHandler().enterPressed = false;
		        		charIndex = 0;
						combinedText = "";
						endingCounter = 0;
						endingDialogueIndex = 0;
						endingIndex = 1;
		        	}
				}
			}
		}
		else {
			alpha += 0.01f; 
	        if (alpha > 1.0f) {
	        	alpha = 1.0f;
	        	
	        	endingCounter++;
	        	
	        	if(endingCounter == 70) {
	        		opponentObj.setCanBattle(false);
	        		opponentObj.dialogueSet = 1;
	        		battleEnding = false;
		        	opponentLost = false;
		        	endingIndex = 0;
		        	endingCounter = 0;
		        	gamePanel.setGameState(gamePanel.getPlayState());
	        	}
	        }
	        
	        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
	        g2.setColor(Color.BLACK);
	        g2.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		}
	}
	
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void showAllMovesUI() {
		g2.setColor(Color.BLACK);
		battlePPFont = pokeFont.deriveFont(16F);
		g2.setFont(battlePPFont);
		FontMetrics metrics = g2.getFontMetrics();

		int[][] rects = { {60, 531}, {360, 531}, {60, 586}, {360, 586} };
		
		if(playerObj.getCurrentPokemon().canDoAnyMoves()) {
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
		}
		else {
			for (int i = 0; i < 4; i++) {
				if(currentCommand == i) {g2.setColor(Color.RED);}
				else {g2.setColor(Color.BLACK);}	
				
			    String text = playerObj.getCurrentPokemon().getAttacks().get(4).getName();
			    int textWidth = metrics.stringWidth(text);
			    int textHeight = metrics.getAscent(); // Gets the height above baseline

			    int centerX = rects[i][0] + (190 - textWidth) / 2; 
			    int centerY = rects[i][1] + ((39 - textHeight) / 2) + textHeight; 
			    
			    g2.drawString(text, centerX, centerY);
			}
		}
		
	}
	
	public void turnPrep() {
		inCombatTextAL.clear();
		
		int opponentMoveIndex = opponentObj.selectMoveIndex();
		if(opponentMoveIndex != -1) {
			opponentMove = opponentObj.getCurrentPokemon().getAttacks().get(opponentMoveIndex);
		}
				
		playerMove = playerObj.getCurrentPokemon().getAttacks().get(playerAttackIndex);
				
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
	
	
	private void playerHitOpponent() {
		if(oppHealthBarWidth <= 0 || oppHealthBarWidth == newHealthBarWidthO) {
			opponentObj.getCurrentPokemon().takeDamage(playerDamageToOpponent);
			charIndex = 0;
			combinedText = "";
			inCombatTextIndex++;
					
			turnCounter++;
		}
				
		oppHealthBarWidth--;	
	}
	
	private void opponentHitPlayer() {
		if(playerHealthBarWidth <= 0 || playerHealthBarWidth == newHealthBarWidthP) {
			playerObj.getCurrentPokemon().takeDamage(opponentDamageToPlayer);
			charIndex = 0;
			combinedText = "";
			inCombatTextIndex++;
			
			turnCounter++;
		}
		
		playerHealthBarWidth--;	
	}
	
	
	private void checkForAvailablePokemon(Entity trainer) {
		if(trainer.getCurrentPokemon().hasFainted()) {
			if(trainer.hasPokemonWithHealth()) {
				trainer.setCurrentPokemon(trainer.findFirstMemberWithHealth());
				inCombatTextIndex = 0;
				turnCounter=0;
				playerPokemonY = 296;
				opponentPokemonY = 120;
				commandsBoolean = true;
				playingMoveBoolean = false;
			}
			else {
				inCombatTextIndex = 0;
				turnCounter = 0;
				playingMoveBoolean = false;
				battleStarted = false;
				battleEnding = true;
				
				if(trainer instanceof Player) {
					playerLost = true;
				}
				else {
					opponentLost = true;
				}
				
			}
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
	
	
	
	public void healthBarPlayerUI() {
		g2.drawImage(userHealthBox, SCREEN_WIDTH - 410, 340, 410, 122, null);
		
		g2.drawString("Lv" + Integer.toString(playerObj.getCurrentPokemon().getLevel()), 670, 400);
		
		g2.setColor(Color.GREEN);
		g2.fillRect(604, 419, playerHealthBarWidth, 12);
		
		pokeFont = pokeFont.deriveFont(Font.BOLD, 18F);
		g2.setColor(Color.BLACK);
		g2.setFont(pokeFont);
		g2.drawString(playerObj.getCurrentPokemon().getName().toUpperCase(), 440, 400);
	}
	
	public void healthBarOppUI() {
		g2.drawImage(opponentHealthBox, 0, 50, 410, 122, null);
		
		g2.drawString("Lv" + Integer.toString(opponentObj.getCurrentPokemon().getLevel()), 250, 110);
		
		g2.setColor(Color.GREEN);
		g2.fillRect(186, 129, oppHealthBarWidth, 12);
		
		pokeFont = pokeFont.deriveFont(Font.BOLD, 18F);
		g2.setColor(Color.BLACK);
		g2.setFont(pokeFont);
		g2.drawString(opponentObj.getCurrentPokemon().getName().toUpperCase(), 10, 110);
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
			
			oppPokeball = ImageIO.read(getClass().getResourceAsStream("/battleUI/oppPokeBall.png"));
			
			//////////////////////////////////////////////////////////////////////////////
			
			//Battle Icons
			playerBarIcon = ImageIO.read(getClass().getResourceAsStream("/battleIcons/TRAINER_icon.png"));
			playerBarIconSilhouette = ImageIO.read(getClass().getResourceAsStream("/battleIcons/TRAINER_icon_silhouette.png"));
			
			//Battle bars
			playerBar = ImageIO.read(getClass().getResourceAsStream("/battleBars/Player_bar.png"));
			
			
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

    
    
    
	///////////////////////////////////////////////////////////////////////////////////////////
	
	
	public void resetValues() {
		playerObj = null;
		opponentObj = null;
		opponentMove = null;
		playerMove = null;
		
		//ALL States
		battleIntroTransition = true;
		battleStartIntro = false;
		battleStarted = false;
		battleEnding = false;
		
		//Dialogue
		currentDialogue = "";
		charIndex = 0;
		combinedText = "";
		
		 introTextIndex = 0;
		 inCombatTextIndex = 0;
		 runAttemptTextIndex = 0;
		
		
		
		 playerBarX = -SCREEN_WIDTH/2;
		 opponentBarX = SCREEN_WIDTH;
		 battleBarY = (SCREEN_HEIGHT / 2) - 130;
		 playerIconBarX = -380;
		 opponentIconBarX = 920;
		
		 vs_X = SCREEN_WIDTH/2 - (122/2);
		 vs_Y = 330;
		 vs_Width = 122;
		 vs_Height = 108;
		
		 topRectIntro = 0;
		 bottomRectIntro = 320;
		
		 basePlayerX = 750;
		 baseOpponentX = -440;
		 playerBackX = 950;
		 opponentFrontX = -400;
		
		 commandWidth = SCREEN_WIDTH / 4;
		 commandHeight = 60;
		 fightCommandX = 400;
		 fightCommandY = 522;
		 bagCommandX = fightCommandX + commandWidth - 7;
		 bagCommandY = fightCommandY;
		 pokemonCommandX = fightCommandX;
		 pokemonCommandY = fightCommandY + commandHeight - 6;
		 runCommandX = fightCommandX + commandWidth - 7;
		 runCommandY = fightCommandY + commandHeight - 6;
		
		 playerHealthBarWidth = 152;
		 oppHealthBarWidth = 152;
		
		 newHealthBarWidthP = 152;
		 newHealthBarWidthO = 152;
		
		playerPokemonY = 296;
		opponentPokemonY = 120; 
		 
		//Intro Booleans
		barsInPlace = false;
		iconsInPlace = false;
		
		basesInPlace = false;
		rectanglesInPlace = false;
		
		shiftedTrainerIcons = false;
		
		shownOpponentText = false;
		opponentPokemonDisplayed = false;
		shiftedOpponentIcon = false;
		shownPlayerText = false;
		
		transitionIntroTimer = 0;
		
		introTimer1 = 0;
		introTimer2 = 0;
		introTimer3 = 0;

		
		//Fight Booleans
		commandsBoolean = true;
		fightBoolean = false;
		bagBoolean = false;
		pokemonBoolean = false;
		runBoolean = false;
		playingMoveBoolean = false;
		
		currentCommand = 0;
		
		playerAttackIndex = 0;
		opponentDamageToPlayer = 0;
		playerDamageToOpponent = 0;
		playerIsFirst = false;
		turnCounter = 0;

		//Ending variables
		alpha = 0.0f;
		playerLost = false;
		opponentLost = false;
		endingIndex = 0;
		endingCounter = 0;
		endingDialogueIndex = 0;
	}
    
    
    
    
    
    
    
    
    
    
    
    
    

	public Player getPlayerObj() {
		return playerObj;
	}


	public void setPlayerObj(Player playerObj) {
		this.playerObj = playerObj;
	}
	
	
	
}
