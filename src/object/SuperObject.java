package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class SuperObject {

	public BufferedImage image;
	public String name;
	public boolean collision = false;
	public int worldX, worldY;
	public Rectangle solidArea = new Rectangle(0, 0, 32 * 2, 48 * 2);
	public int solidAreaDefaultX = 0;
	public int solidAreaDefaultY = 0;
	
	public void draw(Graphics2D g2, GamePanel gamePanel) {
		int screenX = worldX - gamePanel.player.worldX_pos + gamePanel.player.screenX;
		int screenY = worldY - gamePanel.player.worldY_pos + gamePanel.player.screenY;
		
		
		if (worldX + gamePanel.scaledTileSize > gamePanel.player.worldX_pos - gamePanel.player.screenX && 
			worldX - gamePanel.scaledTileSize < gamePanel.player.worldX_pos + gamePanel.player.screenX && 
			worldY + gamePanel.scaledTileSize > gamePanel.player.worldY_pos - gamePanel.player.screenY && 
			worldY - gamePanel.scaledTileSize < gamePanel.player.worldY_pos + gamePanel.player.screenY) {
			
			g2.drawImage(image, screenX, screenY, gamePanel.scaledTileSize, gamePanel.scaledTileSize, null);
		}
	}
}
