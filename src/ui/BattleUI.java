package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import entity.Trainer;
import main.GamePanel;

public class BattleUI {

	GamePanel gamePanel;
	Graphics2D g2;
	
	int SCREEN_WIDTH = 800;
	int SCREEN_HEIGHT = 640;
	
	Trainer opponentObj = null;
	
	boolean battleIntroTransition = true;
	boolean battleStartIntro = false;
	boolean battleStarted = false;
	
	int battleCounter = 0;
	
	////Note that the top left is (0,0)
	
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
	
	
	public String currentCommand = "Fight";
	boolean commandsBoolean = true;
	boolean fightBoolean = false;
	boolean bagBoolean = false;
	boolean pokemonBoolean = false;
	boolean runBoolean = false;
	
	//Pokemon (testing)
	BufferedImage charizardFront, pikachuBack;
	
	//Dialogue
	Font pokeFont;
	Font battlePPFont;
	public String currentDialogue = "";
	int charIndex = 0;
	String combinedText = "";
	
	//Type icons
	BufferedImage fireTypeIcon;
	BufferedImage waterTypeIcon;
	BufferedImage grassTypeIcon;
	BufferedImage electricTypeIcon;
	BufferedImage iceTypeIcon;
	BufferedImage fightingTypeIcon;
	BufferedImage poisonTypeIcon;
	BufferedImage groundTypeIcon;
	BufferedImage flyingTypeIcon;
	BufferedImage psychicTypeIcon;
	BufferedImage bugTypeIcon;
	BufferedImage rockTypeIcon;
	BufferedImage ghostTypeIcon;
	BufferedImage dragonTypeIcon;
	BufferedImage darkTypeIcon;
	BufferedImage steelTypeIcon;
	BufferedImage fairyTypeIcon;
	BufferedImage normalTypeIcon;

	public String battleTextArray[][] = new String[20][20];
	public int battleTextSet = 0;
	public int battleTextIndex = 0;
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public BattleUI(GamePanel p_gamePanel) {
		this.gamePanel = p_gamePanel;
		
		battleTextArray[0][0] = "Trainer would like to battle!";
		battleTextArray[0][1] = "Trainer sent out Charizard!";
		battleTextArray[0][2] = "Go! Pikachu!";
		
		loadBattleImages();
		
		
	}
	
    
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
		
		if(bottomRectIntro != 640) {
			topRectIntro -= 4;
			bottomRectIntro += 4;
			
			g2.fillRect(0, topRectIntro, gamePanel.getScreenWidth(), gamePanel.getScreenHeight() / 2);
	        g2.fillRect(0, bottomRectIntro, gamePanel.getScreenWidth(), gamePanel.getScreenHeight() / 2);
		}
		
		
		if(basePlayerX == -150) {
			//battleCounter++;
			if(battleTextArray[battleTextSet][battleTextIndex] != null) {
				char characters[] = battleTextArray[battleTextSet][battleTextIndex].toCharArray();
				if(charIndex < characters.length) {
					String s = String.valueOf(characters[charIndex]);
					combinedText = combinedText + s;
					currentDialogue = combinedText;
					charIndex++;
				}
				
				if(gamePanel.getKeyHandler().enterPressed == true) {
					charIndex = 0;
					combinedText = "";
					
					if(gamePanel.getGameState() == gamePanel.getBattleState()) {
						battleTextIndex++;
						gamePanel.getKeyHandler().enterPressed = false;
					}
				}
				
				for(String line : currentDialogue.split("\n")) {
					g2.drawString(line, 64, 512+64);
				}
			}

			else {
				battleTextIndex = 0;
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
		g2.drawImage(battleOverlayTextbox, 0, 512, gamePanel.getScreenWidth(), gamePanel.getScreenHeight() / 5, null);
		
		//Battle commands
		if(gamePanel.getKeyHandler().enterPressed == true) {
			commandsBoolean = !commandsBoolean;
			fightBoolean = !fightBoolean;
			gamePanel.getKeyHandler().enterPressed = false;
		}
		
		
		if(commandsBoolean == true) {
			battleShowCommands();
		}
		if(fightBoolean == true) {
			fightShowMoves();
		}
		

	}
	
	
	public void battleShowCommands() {
		if(currentCommand == "Fight") {
			g2.drawImage(bagCommand, bagCommandX, bagCommandY, commandWidth, commandHeight, null);
			g2.drawImage(pokemonCommand, pokemonCommandX, pokemonCommandY, commandWidth, commandHeight, null);
			g2.drawImage(runCommand, runCommandX, runCommandY, commandWidth, commandHeight, null);
			g2.drawImage(fightCommandSelected, fightCommandX, fightCommandY, commandWidth, commandHeight, null);
		}
		else if(currentCommand == "Bag") {
			g2.drawImage(bagCommandSelected, bagCommandX, bagCommandY, commandWidth, commandHeight, null);
			g2.drawImage(pokemonCommand, pokemonCommandX, pokemonCommandY, commandWidth, commandHeight, null);
			g2.drawImage(runCommand, runCommandX, runCommandY, commandWidth, commandHeight, null);
			g2.drawImage(fightCommand, fightCommandX, fightCommandY, commandWidth, commandHeight, null);
		}
		else if(currentCommand == "Pokemon") {
			g2.drawImage(bagCommand, bagCommandX, bagCommandY, commandWidth, commandHeight, null);
			g2.drawImage(pokemonCommandSelected, pokemonCommandX, pokemonCommandY, commandWidth, commandHeight, null);
			g2.drawImage(runCommand, runCommandX, runCommandY, commandWidth, commandHeight, null);
			g2.drawImage(fightCommand, fightCommandX, fightCommandY, commandWidth, commandHeight, null);
		}	
		else if(currentCommand == "Run"){
			g2.drawImage(bagCommand, bagCommandX, bagCommandY, commandWidth, commandHeight, null);
			g2.drawImage(pokemonCommand, pokemonCommandX, pokemonCommandY, commandWidth, commandHeight, null);
			g2.drawImage(runCommandSelected, runCommandX, runCommandY, commandWidth, commandHeight, null);
			g2.drawImage(fightCommand, fightCommandX, fightCommandY, commandWidth, commandHeight, null);
		}
	}
	
	public void fightShowMoves() {
		g2.drawImage(battleOverlayFight, 0, 512, gamePanel.getScreenWidth(), gamePanel.getScreenHeight() / 5, null);

		g2.setColor(Color.WHITE);
		g2.fillRect(60, 531, 190, 39); //Top left
		g2.fillRect(360, 531, 190, 39); //Top right
		g2.fillRect(60, 586, 190, 39); //Bot left
		g2.fillRect(360, 586, 190, 39); //Bot right
		
		g2.setColor(Color.BLACK);
		battlePPFont = pokeFont.deriveFont(20F);
		g2.setFont(battlePPFont);
		g2.drawString("Move 1", 100, 560);
		g2.drawString("Move 2", 400, 560);
		g2.drawString("Move 3", 100, 615);
		g2.drawString("Move 4", 400, 615);
		
		
		g2.drawImage(darkTypeIcon, 655, 512+28, 64+24, 28+6, null);
		
		battlePPFont = pokeFont.deriveFont(18F);
		g2.setFont(battlePPFont);
		g2.setColor(Color.BLACK);
		g2.drawString("PP: 10/10", 633, 512+88);
	}
	
	
	
	
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
		
		currentCommand = "Fight";
		
		commandsBoolean = true;
		fightBoolean = false;
		bagBoolean = false;
		pokemonBoolean = false;
		runBoolean = false;
	}
	
	
	
	
	

    public void drawSubWindow(int x, int y, int width, int height, Color color) {
        g2.setColor(color);
        g2.fillRect(x, y, width, height);
    }
	
	public void loadSpecificBattleImages() {
		try {
			//Battle Icons
			opponentBarIcon = ImageIO.read(getClass().getResourceAsStream("/battleIcons/RIVAL_icon.png"));
			opponentBarIconSilhouette = ImageIO.read(getClass().getResourceAsStream("/battleIcons/RIVAL_icon_silhouette.png"));
			
			//Battle Trainers
			opponentFront = ImageIO.read(getClass().getResourceAsStream("/battleTrainers/rival_front.png"));
						
			//Poke 
			charizardFront = ImageIO.read(getClass().getResourceAsStream("/battlePokeFront/CHARIZARD.png"));
			pikachuBack = ImageIO.read(getClass().getResourceAsStream("/battlePokeBack/PIKACHU.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
    public void loadBattleImages() {
		try {
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
			InputStream is = getClass().getResourceAsStream("/font/PKMN RBYGSC.ttf");
			pokeFont = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	
	
}
