package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class BaseUI {

	GamePanel gamePanel;
	Font arial_40;
	BufferedImage image;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	
	public BaseUI(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		arial_40 = new Font("Arial", Font.BOLD, 40);
		
		//OBJ_ball pokeball = new OBJ_Ball();
		//image = pokeball.image;
	}
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2) {
		g2.setFont(arial_40);
		g2.setColor(Color.RED);
		g2.drawString("test", 50, 50);
		//g2.drawImage(keyImage, gamePanel.scaledTileSize/2, gamePanel.scaledTileSize/2, gamePanel.scaledTileSize, gamePanel.scaledTileSize, null);
		
		if(messageOn == true) {
			g2.drawString(message, 50, 100);
			
			messageCounter++;
			
			if(messageCounter > 120) {
				messageCounter = 0;
				messageOn = false;
			}
		}
	}
	
}
