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
	Font pokeFont;
	
	boolean battleIntroTransition = false;
	boolean battleStartIntro = true;
	boolean battleStarted = false;
	
	int battleCounter = 0;
	BufferedImage playerBarIcon, rivalBarIcon;
	BufferedImage playerBarIconSilhouette, rivalBarIconSilhouette;
	BufferedImage playerBar, rivalBar;
	BufferedImage vsIcon;
	int playerBarX = -320;
	int opponentBarX = 640;
	int battleBarY = (640 - 128 * 2) - 172;
	int playerIconBarX = -360;
	int opponentIconBarX = 720;
	
	BufferedImage fieldBG;
	BufferedImage playerBack, rivalFront;
	BufferedImage fieldBasePlayer, fieldBaseOpponent;
	int topRectIntro = 0;
	int bottomRectIntro = 320;
	
	int basePlayerX = 750;
	int baseOpponentX = -450;
	int playerBackX = 950;
	int opponentFrontX = -400;
	
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
	
	
	public BattleUI(GamePanel p_gamePanel) {
		this.gamePanel = p_gamePanel;
		
		loadBattleImages();
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
			playerBack = ImageIO.read(getClass().getResourceAsStream("/battleTrainers/player_back_1.png"));
			rivalFront = ImageIO.read(getClass().getResourceAsStream("/battleTrainers/rival_front.png"));
			
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
    
    
    public void drawSubWindow(int x, int y, int width, int height, Color color) {
        g2.setColor(color);
        g2.fillRect(x, y, width, height);
    }
    
    
	public void draw(Graphics2D g2) {
		this.g2 = g2;
	
		if(battleIntroTransition) {
			startBattleIntro(); 
		}
		if(battleStartIntro) {
			battleIntro();
		}
		if(battleStarted) {
			inBattle();
		}
		
		//if(battleCounter == 120) {battleIntroTransition = false;}
		
		g2.dispose();
	}
	
	public void startBattleIntro() {
		int width = gamePanel.getScreenWidth();
		int height = gamePanel.getScreenHeight();
		Color c = new Color(0, 0, 0, 100);
		
		drawSubWindow(0, 0, width, height, c);
		
		g2.drawImage(playerBar, playerBarX, battleBarY, 320, 196, null);
		g2.drawImage(rivalBar, opponentBarX, battleBarY, 320, 196, null);
		
		g2.drawImage(playerBarIconSilhouette, playerIconBarX, battleBarY + 8, 256, 178, null);
		g2.drawImage(rivalBarIconSilhouette, opponentIconBarX, battleBarY + 8, 256, 178, null);
		
		if(playerBarX != 0) {
			playerBarX += 32;
			opponentBarX -= 32;
		}
		else if(playerBarX == 0 && playerIconBarX != 40) {
			playerIconBarX += 10;
			opponentIconBarX -= 10;
		}
		else if(playerIconBarX == 40) {
			g2.drawImage(playerBarIcon, playerIconBarX, battleBarY + 8, 256, 178, null);
			g2.drawImage(rivalBarIcon, opponentIconBarX, battleBarY + 8, 256, 178, null);
			g2.drawImage(vsIcon, 260, 350, 122, 108, null);
		}
		
		battleCounter++;
	}
	
	public void battleIntro() {
		Color c = new Color(0, 0, 0);
		g2.setColor(c);
		
		//Battle BG
		g2.drawImage(fieldBG, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
		//Battle Trainers
		g2.drawImage(fieldBasePlayer, basePlayerX, 448, 512 + (512/3), 64, null);
		g2.drawImage(fieldBaseOpponent, baseOpponentX, 220, 265 + (265/3), 128 + (128/3), null);
		//Battle Base
		g2.drawImage(playerBack, playerBackX, 272, 240, 240, null);
		g2.drawImage(rivalFront, opponentFrontX, 120, 192, 192, null);
		//Battle menu
		g2.drawImage(battleOverlay, 0, 512, gamePanel.getScreenWidth(), gamePanel.getScreenHeight() / 5, null);
		g2.drawImage(textbox, 0, 512, gamePanel.getScreenWidth(), gamePanel.getScreenHeight() / 5, null);

		if(bottomRectIntro != 640) {
			g2.fillRect(0, topRectIntro, gamePanel.getScreenWidth(), gamePanel.getScreenHeight() / 2);
	        g2.fillRect(0, bottomRectIntro, gamePanel.getScreenWidth(), gamePanel.getScreenHeight() / 2);
			topRectIntro -= 4;
			bottomRectIntro += 4;
		}
		if(basePlayerX != -150) {
			basePlayerX -= 10;
			baseOpponentX += 10;
			playerBackX -= 10;
			opponentFrontX += 10;
		}
		if(basePlayerX == -150) {
			battleCounter++;
		}
	
	}
	
	public void inBattle() {
		//Battle commands
		g2.drawImage(bagCommand, bagCommandX, bagCommandY, commandWidth, commandHeight, null);
		g2.drawImage(pokemonCommand, pokemonCommandX, pokemonCommandY, commandWidth, commandHeight, null);
		g2.drawImage(runCommand, runCommandX, runCommandY, commandWidth, commandHeight, null);
		g2.drawImage(fightCommandSelected, fightCommandX, fightCommandY, commandWidth, commandHeight, null);
	}
	
	
	
}
