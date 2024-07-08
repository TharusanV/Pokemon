package main;

import entity.Entity;
import tile.CreateLayer;
import tile.Tile;

public class CollisionChecker {
	
	GamePanel gamePanel;
	
	public CollisionChecker(GamePanel p_gamePanel) {
		this.gamePanel = p_gamePanel;
	}
	
	public void checkTile(Entity entity) {
		int entityLeftWorldX = entity.worldX_pos + entity.solidArea.x;
		int entityRightWorldX = entity.worldX_pos + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.worldY_pos + entity.solidArea.y;
		int entityBottomWorldY = entity.worldY_pos + entity.solidArea.y + entity.solidArea.height;
		
		int entityLeftCol = entityLeftWorldX/gamePanel.scaledTileSize;
		int entityRightCol = entityRightWorldX/gamePanel.scaledTileSize;
		int entityTopRow = entityTopWorldY/gamePanel.scaledTileSize;
		int entityBottomRow = entityBottomWorldY/gamePanel.scaledTileSize;
		
		int tileNuml;
		int tileNum2;
		Tile tile1 = null;
		Tile tile2 = null;
		
		switch(entity.direction) {
		case "up":
			entityTopRow = (entityTopWorldY - entity.speed)/gamePanel.scaledTileSize;
			tileNuml = gamePanel.layer3.mapTileNum[entityLeftCol][entityTopRow]; 
			tileNum2 = gamePanel.layer3.mapTileNum[entityRightCol][entityTopRow];
			
			for (Tile tile : gamePanel.tileManager.allTiles) {
				if (tile.value == tileNuml) {
					tile1 = tile;
					break;
				}
			}
			
			for (Tile tile : gamePanel.tileManager.allTiles) {
				if (tile.value == tileNuml) {
					tile2 = tile;
					break;
				}
			}
			
			if(tile1.collision == true || tile2.collision == true) { 
				entity.collisionOn = true; 
			}
			break;
			
		case "down":
			entityBottomRow = (entityBottomWorldY + entity.speed)/gamePanel.scaledTileSize;
			tileNuml = gamePanel.layer3.mapTileNum[entityLeftCol][entityBottomRow]; 
			tileNum2 = gamePanel.layer3.mapTileNum[entityRightCol][entityBottomRow];
			
			for (Tile tile : gamePanel.tileManager.allTiles) {
				if (tile.value == tileNuml) {
					tile1 = tile;
					break;
				}
			}
			
			for (Tile tile : gamePanel.tileManager.allTiles) {
				if (tile.value == tileNuml) {
					tile2 = tile;
					break;
				}
			}
			
			if(tile1.collision == true || tile2.collision == true) { 
				entity.collisionOn = true; 
			}
			break;
			
		case "left":
			entityLeftCol = (entityLeftWorldX - entity.speed)/gamePanel.scaledTileSize;
			tileNuml = gamePanel.layer3.mapTileNum[entityLeftCol][entityTopRow]; 
			tileNum2 = gamePanel.layer3.mapTileNum[entityLeftCol][entityBottomRow];
			
			for (Tile tile : gamePanel.tileManager.allTiles) {
				if (tile.value == tileNuml) {
					tile1 = tile;
					break;
				}
			}
			
			for (Tile tile : gamePanel.tileManager.allTiles) {
				if (tile.value == tileNuml) {
					tile2 = tile;
					break;
				}
			}
			
			if(tile1.collision == true || tile2.collision == true) { 
				entity.collisionOn = true; 
			}
			break;
		
		case "right":
			entityRightCol = (entityRightWorldX + entity.speed)/gamePanel.scaledTileSize;
			tileNuml = gamePanel.layer3.mapTileNum[entityRightCol][entityTopRow]; 
			tileNum2 = gamePanel.layer3.mapTileNum[entityRightCol][entityBottomRow];
			
			for (Tile tile : gamePanel.tileManager.allTiles) {
				if (tile.value == tileNuml) {
					tile1 = tile;
					break;
				}
			}
			
			for (Tile tile : gamePanel.tileManager.allTiles) {
				if (tile.value == tileNuml) {
					tile2 = tile;
					break;
				}
			}
			
			if(tile1.collision == true || tile2.collision == true) { 
				entity.collisionOn = true; 
			}
			break;
		}
		
	}
}
