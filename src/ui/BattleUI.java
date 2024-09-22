package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

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
	
	//Battle Start Intro
	BufferedImage playerBarIcon, rivalBarIcon;
	BufferedImage playerBarIconSilhouette, rivalBarIconSilhouette;
	BufferedImage playerBar, rivalBar;
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
	BufferedImage playerBack1,playerBack2,playerBack3,playerBack4,playerBack5,rivalFront;
	BufferedImage fieldBasePlayer, fieldBaseOpponent;
	int topRectIntro = 0;
	int bottomRectIntro = 320;
	
	int basePlayerX = 750;
	int baseOpponentX = -440;
	int playerBackX = 950;
	int opponentFrontX = -400;
	
	//Battle
	BufferedImage textbox, battleOverlay, battleOverlayTextbox;
	BufferedImage fightCommand, bagCommand, pokemonCommand, runCommand, cancelCommand;
	BufferedImage fightCommandSelected, bagCommandSelected, pokemonCommandSelected, runCommandSelected, cancelCommandSelected;
	int commandWidth = 200;
	int commandHeight = 60;
	int fightCommandX = 400;
	int fightCommandY = 522;
	int bagCommandX = fightCommandX + commandWidth - 7;
	int bagCommandY = fightCommandY;
	int pokemonCommandX = fightCommandX;
	int pokemonCommandY = fightCommandY + commandHeight - 6;
	int runCommandX = fightCommandX + commandWidth - 7;
	int runCommandY = fightCommandY + commandHeight - 6;
	
	Font pokeFont;
	public String currentDialogue = "";
	int charIndex = 0;
	String combinedText = "";
	
	
	
	
	public BattleUI(GamePanel p_gamePanel) {
		this.gamePanel = p_gamePanel;
		
		battleTextArray[0][0] = "Trainer would like to battle!";
		battleTextArray[0][1] = "Trainer sent out Charizard!";
		battleTextArray[0][2] = "Go! Pikachu!";
		
		loadBattleImages();
	}
	

    
    
    public void drawSubWindow(int x, int y, int width, int height, Color color) {
        g2.setColor(color);
        g2.fillRect(x, y, width, height);
    }
    
    
	public void draw(Graphics2D g2) {
		this.g2 = g2;
	
		g2.setFont(pokeFont);
		g2.setColor(Color.BLACK);
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
	
		if(battleIntroTransition) {
			startBattleIntro(); 
		}
		if(battleStartIntro) {
			battleIntro();
		}
		if(battleStarted) {
			inBattle();
		}
		
		g2.dispose();
	}
	
	
	public void startBattleIntro() {
		Color c = new Color(0, 0, 0, 100);
		
		drawSubWindow(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, c);
		
		g2.drawImage(playerBar, playerBarX, battleBarY, SCREEN_WIDTH/2, 196, null);
		g2.drawImage(rivalBar, opponentBarX, battleBarY, SCREEN_WIDTH/2, 196, null);
		
		g2.drawImage(playerBarIconSilhouette, playerIconBarX, battleBarY + 8, 256, 178, null);
		g2.drawImage(rivalBarIconSilhouette, opponentIconBarX, battleBarY + 8, 256, 178, null);
		
		
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
			g2.drawImage(rivalBarIcon, opponentIconBarX, battleBarY + 8, 256, 178, null);
			g2.drawImage(vsIcon,vs_X,vs_Y,vs_Width,vs_Height,null);
			
			countdown1++;
		}
		else if(countdown1 == 30) {
			g2.drawImage(playerBarIcon, playerIconBarX, battleBarY + 8, 256, 178, null);
			g2.drawImage(rivalBarIcon, opponentIconBarX, battleBarY + 8, 256, 178, null);
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
	
	
	public String battleTextArray[][] = new String[20][20];
	public int battleTextSet = 0;
	public int battleTextIndex = 0;
	
	
	public void battleIntro() {		
		g2.drawImage(fieldBG, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
		g2.drawImage(fieldBasePlayer, basePlayerX, 448, 512 + (512/3), 64, null);
		g2.drawImage(fieldBaseOpponent, baseOpponentX, 220, 265 + (265/3), 128 + (128/3), null);

		g2.drawImage(playerBack1, playerBackX, 272, 240, 240, null);
		g2.drawImage(rivalFront, opponentFrontX, 120, 192, 192, null);

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
			g2.drawImage(rivalFront, opponentFrontX, 120, 192, 192, null);				
		}
		else if(battleCounter >= 60 && battleCounter <= 65) {
			g2.drawImage(playerBack2, playerBackX, 272, 240, 240, null);
			g2.drawImage(rivalFront, opponentFrontX, 120, 192, 192, null);				
		}
		else if(battleCounter >= 65 && battleCounter <= 70) {
			g2.drawImage(playerBack3, playerBackX, 272, 240, 240, null);
			g2.drawImage(rivalFront, opponentFrontX, 120, 192, 192, null);				
		}
		else if(battleCounter >= 70 && battleCounter <= 75) {
			g2.drawImage(playerBack4, playerBackX, 272, 240, 240, null);
			g2.drawImage(rivalFront, opponentFrontX, 120, 192, 192, null);				
		}
		else if(battleCounter >= 75 && battleCounter < 80) {
			g2.drawImage(playerBack5, playerBackX, 272, 240, 240, null);
			g2.drawImage(rivalFront, opponentFrontX, 120, 192, 192, null);				
		}
	}
	
	
	
	public void inBattle() {
		g2.drawImage(fieldBG, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
		
		g2.drawImage(fieldBasePlayer, basePlayerX, 448, 512 + (512/3), 64, null);
		g2.drawImage(fieldBaseOpponent, baseOpponentX, 220, 265 + (265/3), 128 + (128/3), null);
		
		g2.drawImage(battleOverlay, 0, 512, gamePanel.getScreenWidth(), gamePanel.getScreenHeight() / 5, null);
		g2.drawImage(battleOverlayTextbox, 0, 512, gamePanel.getScreenWidth(), gamePanel.getScreenHeight() / 5, null);
		
		//Battle commands
		g2.drawImage(bagCommand, bagCommandX, bagCommandY, commandWidth, commandHeight, null);
		g2.drawImage(pokemonCommand, pokemonCommandX, pokemonCommandY, commandWidth, commandHeight, null);
		g2.drawImage(runCommand, runCommandX, runCommandY, commandWidth, commandHeight, null);
		g2.drawImage(fightCommandSelected, fightCommandX, fightCommandY, commandWidth, commandHeight, null);
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
	}
	
	
	
	
	
	
	
	
	
    public void loadBattleImages() {
		try {
			//Battle Icons
			playerBarIcon = ImageIO.read(getClass().getResourceAsStream("/battleIcons/TRAINER_icon.png"));
			rivalBarIcon = ImageIO.read(getClass().getResourceAsStream("/battleIcons/RIVAL_icon.png"));
			playerBarIconSilhouette = ImageIO.read(getClass().getResourceAsStream("/battleIcons/TRAINER_icon_silhouette.png"));
			rivalBarIconSilhouette = ImageIO.read(getClass().getResourceAsStream("/battleIcons/RIVAL_icon_silhouette.png"));
			
			//Battle bars
			playerBar = ImageIO.read(getClass().getResourceAsStream("/battleBars/Player_bar.png"));
			rivalBar = ImageIO.read(getClass().getResourceAsStream("/battleBars/Rival_bar.png"));
			
			//Battle UI
			textbox = ImageIO.read(getClass().getResourceAsStream("/ui/textbox.png"));
			vsIcon = ImageIO.read(getClass().getResourceAsStream("/battleUI/vsIcon.png"));
			battleOverlay = ImageIO.read(getClass().getResourceAsStream("/battleUI/overlay_battle.png"));
			battleOverlayTextbox = ImageIO.read(getClass().getResourceAsStream("/battleUI/overlay_battleTextBox.png"));
			
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
			rivalFront = ImageIO.read(getClass().getResourceAsStream("/battleTrainers/rival_front.png"));
			
			//Poke Front
			
			
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
