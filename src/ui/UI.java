package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.InputStream;

import main.GamePanel;

public class UI {
	
	GamePanel gamePanel;
	Graphics2D g2;
	Font pokeFont;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public String currentDialogue = "";
	
	public UI(GamePanel p_gamePanel) {
		this.gamePanel = p_gamePanel;
		
		try {
			InputStream is = getClass().getResourceAsStream("/font/PKMN RBYGSC.ttf");
			pokeFont = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		
		g2.setFont(pokeFont);
		g2.setColor(Color.BLACK);
		
		/*
		 if(messageOn == true) {
			g2.drawString(message, 50, 100);
			
			messageCounter++;
			
			if(messageCounter > 120) {
				messageCounter = 0;
				messageOn = false;
			}
		}
		 */
		
		//Play State
		if(gamePanel.getGameState() == gamePanel.getPlayState()) {
			
		}
		//Battle State
		if(gamePanel.getGameState() == gamePanel.getBattleState()) {
			drawBattleScreen();
		}
		//Dialogue State
		if(gamePanel.getGameState() == gamePanel.getDialogueState()) {
			drawDialogueScreen();
		}
	}
	
	public void drawBattleScreen() {
		
	}
	
	public void drawDialogueScreen() {
		//Dialogue Window
		int x = 0;
		int y = gamePanel.getScaledTileSize() / 2 + 480;
		int width = gamePanel.getScreenWidth();
		int height = gamePanel.getScaledTileSize() * 2;
		
		Color c = new Color(255, 255, 255);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		c = new Color(0, 0, 0);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x + 2, y + 2, width - 5, height - 4, 25, 25);
		
		//Drawing text
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
		x += gamePanel.getScaledTileSize();
		y += gamePanel.getScaledTileSize();
		
		for(String line : currentDialogue.split("\n")) {
			g2.drawString(currentDialogue, x, y);
			y+= 40;
		}
	}
}
