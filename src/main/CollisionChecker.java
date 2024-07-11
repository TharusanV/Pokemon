package main;

import entity.Entity;
import tile.CreateLayer;
import tile.Tile;

public class CollisionChecker {
	
	GamePanel gamePanel;
	
	public CollisionChecker(GamePanel p_gamePanel) {
		this.gamePanel = p_gamePanel;
	}
	
	public void checkTile(Entity entity, CreateLayer solidLayer) {
		int entityLeftWorldX = entity.worldX_pos + entity.solidArea.x;
		int entityRightWorldX = entity.worldX_pos + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.worldY_pos + entity.solidArea.y;
		int entityBottomWorldY = entity.worldY_pos + entity.solidArea.y + entity.solidArea.height;
		
		int entityLeftCol = entityLeftWorldX/gamePanel.getScaledTileSize();
		int entityRightCol = entityRightWorldX/gamePanel.getScaledTileSize();
		int entityTopRow = entityTopWorldY/gamePanel.getScaledTileSize();
		int entityBottomRow = entityBottomWorldY/gamePanel.getScaledTileSize();
		
		int tileNuml;
		int tileNum2;
		Tile tile1 = null;
		Tile tile2 = null;
		
		switch(entity.direction) {
		case "up":
			entityTopRow = (entityTopWorldY - entity.speed)/gamePanel.getScaledTileSize();
			tileNuml = solidLayer.mapTileNum[entityLeftCol][entityTopRow]; 
			tileNum2 = solidLayer.mapTileNum[entityRightCol][entityTopRow];
			
			for (Tile tile : gamePanel.getTileManager().allTiles) {
				if (tile.value == tileNuml) {
					tile1 = tile;
					break;
				}
			}
			
			for (Tile tile : gamePanel.getTileManager().allTiles) {
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
			entityBottomRow = (entityBottomWorldY + entity.speed)/gamePanel.getScaledTileSize();
			tileNuml = solidLayer.mapTileNum[entityLeftCol][entityBottomRow]; 
			tileNum2 = solidLayer.mapTileNum[entityRightCol][entityBottomRow];
			
			for (Tile tile : gamePanel.getTileManager().allTiles) {
				if (tile.value == tileNuml) {
					tile1 = tile;
					break;
				}
			}
			
			for (Tile tile : gamePanel.getTileManager().allTiles) {
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
			entityLeftCol = (entityLeftWorldX - entity.speed)/gamePanel.getScaledTileSize();
			tileNuml = solidLayer.mapTileNum[entityLeftCol][entityTopRow]; 
			tileNum2 = solidLayer.mapTileNum[entityLeftCol][entityBottomRow];
			
			for (Tile tile : gamePanel.getTileManager().allTiles) {
				if (tile.value == tileNuml) {
					tile1 = tile;
					break;
				}
			}
			
			for (Tile tile : gamePanel.getTileManager().allTiles) {
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
			entityRightCol = (entityRightWorldX + entity.speed)/gamePanel.getScaledTileSize();
			tileNuml = solidLayer.mapTileNum[entityRightCol][entityTopRow]; 
			tileNum2 = solidLayer.mapTileNum[entityRightCol][entityBottomRow];
			
			for (Tile tile : gamePanel.getTileManager().allTiles) {
				if (tile.value == tileNuml) {
					tile1 = tile;
					break;
				}
			}
			
			for (Tile tile : gamePanel.getTileManager().allTiles) {
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
	
	//Object collision
	public int checkObject(Entity entity, boolean player) {
		int index = 999;
		
		for(int i = 0; i < gamePanel.getObj().length; i++) {
			if(gamePanel.getObj()[i] != null) {
				//Gets entity solid area position
				entity.solidArea.x = entity.worldX_pos + entity.solidArea.x;
				entity.solidArea.y = entity.worldY_pos + entity.solidArea.y;
				//Get objects solid area position
				gamePanel.getObj()[i].solidArea.x = gamePanel.getObj()[i].worldX_pos + gamePanel.getObj()[i].solidArea.x;
				gamePanel.getObj()[i].solidArea.y = gamePanel.getObj()[i].worldY_pos + gamePanel.getObj()[i].solidArea.y;
				
				switch(entity.direction) {
				case "up":
					entity.solidArea.y -= entity.speed;
					break;
				case "down":
					entity.solidArea.y += entity.speed;
					break;
				case "left":
					entity.solidArea.x -= entity.speed;
					break;
				case "right":
					entity.solidArea.x += entity.speed;
					break;
				}
				
				if(entity.solidArea.intersects(gamePanel.getObj()[i].solidArea)) {
					if(gamePanel.getObj()[i].collision) {entity.collisionOn = true;}
					if(player == true) {index = i;}
				}
				
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				gamePanel.getObj()[i].solidArea.x = gamePanel.getObj()[i].solidAreaDefaultX;
				gamePanel.getObj()[i].solidArea.y = gamePanel.getObj()[i].solidAreaDefaultY;
			}
		}
		return index;
	}

	//NPC Collision
	public int checkEntity(Entity entity, Entity[] target) {
		int index = 999;
		
		for(int i = 0; i < target.length; i++) {
			if(target[i] != null) {
				//Gets entity solid area position
				entity.solidArea.x = entity.worldX_pos + entity.solidArea.x;
				entity.solidArea.y = entity.worldY_pos + entity.solidArea.y;
				//Get objects solid area position
				target[i].solidArea.x = target[i].worldX_pos + target[i].solidArea.x;
				target[i].solidArea.y = target[i].worldY_pos + target[i].solidArea.y;
				
				switch(entity.direction) {
				case "up":entity.solidArea.y -= entity.speed;break;
				case "down":entity.solidArea.y += entity.speed;break;
				case "left":entity.solidArea.x -= entity.speed;break;
				case "right":entity.solidArea.x += entity.speed; break;	
				}
				
				if(entity.solidArea.intersects(target[i].solidArea)) {
					if(target[i] != entity) {
						entity.collisionOn = true;
						index = i;
					}
				}
				
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				target[i].solidArea.x = target[i].solidAreaDefaultX;
				target[i].solidArea.y = target[i].solidAreaDefaultY;
			}
		}
		return index;
	}
	
	//Player Collision
	public void checkPlayer(Entity entity) {
		//Gets entity solid area position
		entity.solidArea.x = entity.worldX_pos + entity.solidArea.x;
		entity.solidArea.y = entity.worldY_pos + entity.solidArea.y;
		//Get objects solid area position
		gamePanel.getPlayer().solidArea.x = gamePanel.getPlayer().worldX_pos + gamePanel.getPlayer().solidArea.x;
		gamePanel.getPlayer().solidArea.y = gamePanel.getPlayer().worldY_pos + gamePanel.getPlayer().solidArea.y;
		
		switch(entity.direction) {
		case "up":
			entity.solidArea.y -= entity.speed;
			break;
		case "down":
			entity.solidArea.y += entity.speed;
			break;
		case "left":
			entity.solidArea.x -= entity.speed;
			break;
		case "right":
			entity.solidArea.x += entity.speed;
			break;
		}
		
		if(entity.solidArea.intersects(gamePanel.getPlayer().solidArea)) {
			entity.collisionOn = true;
		}
		
		entity.solidArea.x = entity.solidAreaDefaultX;
		entity.solidArea.y = entity.solidAreaDefaultY;
		gamePanel.getPlayer().solidArea.x = gamePanel.getPlayer().solidAreaDefaultX;
		gamePanel.getPlayer().solidArea.y = gamePanel.getPlayer().solidAreaDefaultY;
	}
}
