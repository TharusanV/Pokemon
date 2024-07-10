package main;

import java.awt.Font;
import java.awt.Graphics2D;

public class UI {
	
	GamePanel gamePanel;
	Graphics2D g2;
	boolean menuOn = false;
	Font arial_40;
	
	public UI(GamePanel p_gamePanel) {
		this.gamePanel = p_gamePanel;
		arial_40 = new Font("Arial", Font.BOLD, 36);
	}
	
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		g2.setFont(new Font("Arial", Font.BOLD, 36));
		
		if(gamePanel.gameState == gamePanel.playState) {
			
		}
		if(gamePanel.gameState == gamePanel.battleState) {
			drawBattleScreen();
		}
	}
	
	public void drawBattleScreen() {
		
	}
}
