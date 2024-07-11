package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import main.GamePanel;

public class UI {
	
	GamePanel gamePanel;
	Graphics2D g2;
	Font arial_40;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public String currentDialogue = "";
	
	public UI(GamePanel p_gamePanel) {
		this.gamePanel = p_gamePanel;
		arial_40 = new Font("Arial", Font.BOLD, 36);
		
	}
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		
		g2.setFont(arial_40);
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
		if(gamePanel.gameState == gamePanel.playState) {
			
		}
		//Battle State
		if(gamePanel.gameState == gamePanel.battleState) {
			drawBattleScreen();
		}
		//Dialogue State
		if(gamePanel.gameState == gamePanel.dialogueState) {
			drawDialogueScreen();
		}
	}
	
	public void drawBattleScreen() {
		
	}
	
	public void drawDialogueScreen() {
		//Dialogue Window
		int x = 0;
		int y = gamePanel.scaledTileSize / 2 + 480;
		int width = gamePanel.screenWidth;
		int height = gamePanel.scaledTileSize * 2;
		
		Color c = new Color(255, 255, 255);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		c = new Color(0, 0, 0);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x + 2, y + 2, width - 5, height - 4, 25, 25);
		
		//Drawing text
		x += gamePanel.scaledTileSize;
		y += gamePanel.scaledTileSize;
		
		for(String line : currentDialogue.split("\n")) {
			g2.drawString(currentDialogue, x, y);
			y+= 40;
		}
	}
}
