package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import main.GamePanel;

public class CreateLayer {

	private GamePanel gamePanel; 
	private TileManager tileManager;
	int mapTileNum[][];
	
	public CreateLayer(GamePanel p_gamePanel, TileManager p_tileManager, int p_imageSelect) {
		this.gamePanel = p_gamePanel;
		this.tileManager = p_tileManager;
		
		mapTileNum = new int[gamePanel.maxWorldCol][gamePanel.maxWorldRow];
		
		if(p_imageSelect == 0) {
			tileManager.getTileImage("res/maps/palletTown_Ground.csv", false);
			loadMap("/maps/palletTown_Ground.csv");
		}
		else if(p_imageSelect == 1) {
			tileManager.getTileImage("res/maps/palletTown_Tree.csv", false);
			loadMap("/maps/palletTown_Tree.csv");
		}
		else if(p_imageSelect == 2) {
			tileManager.getTileImage("res/maps/palletTown_Buildings.csv", true);
			loadMap("/maps/palletTown_Buildings.csv");
		}
	}
	
	public void loadMap(String p_fileName) {
	    try {
	        InputStream is = getClass().getResourceAsStream(p_fileName);
	        BufferedReader br = new BufferedReader(new InputStreamReader(is));
	        
	        int col = 0;
	        int row = 0;
	        
	        String line;
	        while ((line = br.readLine()) != null && row < gamePanel.maxWorldRow) {
	            String[] numbers = line.split(",");
	            for (col = 0; col < numbers.length && col < gamePanel.maxWorldCol; col++) {
	                int num = Integer.parseInt(numbers[col].trim());
	                mapTileNum[col][row] = num;
	            }
	            row++;
	        }
	        
	        br.close();
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	
	public void draw(Graphics2D p_g2) {
		int worldCol = 0;
		int worldRow = 0;
				
		while(worldCol < gamePanel.maxWorldCol && worldRow < gamePanel.maxWorldRow) {
			
			int tileNum = mapTileNum[worldCol][worldRow];
			
			int worldX = worldCol * gamePanel.scaledTileSize;
			int worldY = worldRow * gamePanel.scaledTileSize;
			int screenX = worldX - gamePanel.player.worldX_pos + gamePanel.player.screenX;
			int screenY = worldY - gamePanel.player.worldY_pos + gamePanel.player.screenY;
			
			
			if (worldX + gamePanel.scaledTileSize > gamePanel.player.worldX_pos - gamePanel.player.screenX && 
				worldX - gamePanel.scaledTileSize < gamePanel.player.worldX_pos + gamePanel.player.screenX && 
				worldY + gamePanel.scaledTileSize > gamePanel.player.worldY_pos - gamePanel.player.screenY && 
				worldY - gamePanel.scaledTileSize < gamePanel.player.worldY_pos + gamePanel.player.screenY) {
				
				for (Tile tile : tileManager.allTiles) {
					if (tile.value == tileNum) {
			            p_g2.drawImage(tile.image, screenX, screenY, gamePanel.scaledTileSize, gamePanel.scaledTileSize, null);
			            break;
			        }
				}
			}
			
			worldCol++;
			
			if(worldCol == gamePanel.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
		}

	}
}
