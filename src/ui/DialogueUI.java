package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import entity.Entity;
import entity.NPC;
import entity.Trainer;
import main.GamePanel;

public class DialogueUI {

	GamePanel gamePanel;
	Graphics2D g2;
	
	Font pokeFont;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public String currentDialogue = "";
	BufferedImage textBox;
	int charIndex = 0;
	String combinedText = "";
	
	public Entity npc;
	
	
	
	
	public DialogueUI(GamePanel p_gamePanel) {
		this.gamePanel = p_gamePanel;
		
		loadDialogueStuff();
	}
	
    public void loadDialogueStuff() {
    	//Loading fonts
		try {
			InputStream is = getClass().getResourceAsStream("/font/PKMN RBYGSC.ttf");
			pokeFont = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Loading textbox image
		try {
			textBox = ImageIO.read(getClass().getResourceAsStream("/ui/textbox.png"));
		}catch (IOException e) {
			e.printStackTrace();
		}
    }
    
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		
		g2.setFont(pokeFont);
		g2.setColor(Color.BLACK);
		
		drawDialogueScreen();
	}
	
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
    
	public void drawDialogueScreen() {
		//Dialogue Window
		int x = 0;
		int y = gamePanel.getScaledTileSize() / 2 + 480;
		int width = gamePanel.getScreenWidth();
		int height = gamePanel.getScaledTileSize() * 2;
		
		g2.drawImage(textBox, x, y, width, height, null);
		
		//Drawing text
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
		x += gamePanel.getScaledTileSize();
		y += gamePanel.getScaledTileSize();
		
		
		if(npc.dialogues[npc.dialogueSet][npc.dialogueIndex] != null) {
			//currentDialogue = npc.dialogues[npc.dialogueSet][npc.dialogueIndex];
			
			//Text letter by letter
			char characters[] = npc.dialogues[npc.dialogueSet][npc.dialogueIndex].toCharArray();
			if(charIndex < characters.length) {
				String s = String.valueOf(characters[charIndex]);
				combinedText = combinedText + s;
				currentDialogue = combinedText;
				charIndex++;
			}
			
			
			if(gamePanel.getKeyHandler().enterPressed == true) {
				charIndex = 0;
				combinedText = "";
				
				if(gamePanel.getGameState() == gamePanel.getDialogueState()) {
					npc.dialogueIndex++;
					gamePanel.getKeyHandler().enterPressed = false;
				}
			}
		}
		else { //If no text in the array
			npc.dialogueIndex = 0;
			
			if(gamePanel.getGameState() == gamePanel.getDialogueState()) {
				if(npc instanceof Trainer) {
					Trainer tempTrainer = (Trainer) npc;
					if(tempTrainer.getCanBattle() == true) {
						gamePanel.setGameState(gamePanel.getBattleState());
					}
				}
				else {
					gamePanel.setGameState(gamePanel.getPlayState());
				}
			}
		}
		
		
		for(String line : currentDialogue.split("\n")) {
			g2.drawString(line, x, y);
			y+= 40;
		}
		
		g2.dispose();
	}
}
