package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import main.GamePanel;

public class CreateLayer {

	private GamePanel gamePanel; 
	private TileManager tileManager;
	public int mapTileNum[][];
	
	public CreateLayer(GamePanel p_gamePanel, TileManager p_tileManager, int p_imageSelect) {
		this.gamePanel = p_gamePanel;
		this.tileManager = p_tileManager;
		
		mapTileNum = new int[gamePanel.getMaxWorldCol()][gamePanel.getMaxWorldRow()];
		
		if(p_imageSelect == 0) {
			tileManager.getTileImage("res/maps/palletTown_Ground.csv", false);
			loadMap("/maps/palletTown_Ground.csv");
		}
		else if(p_imageSelect == 1) {
			tileManager.getTileImage("res/maps/palletTown_Tree.csv", true);
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
	        while ((line = br.readLine()) != null && row < gamePanel.getMaxWorldRow()) {
	            String[] numbers = line.split(",");
	            for (col = 0; col < numbers.length && col < gamePanel.getMaxWorldCol(); col++) {
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
				
		while(worldCol < gamePanel.getMaxWorldCol() && worldRow < gamePanel.getMaxWorldRow()) {
			
			int tileNum = mapTileNum[worldCol][worldRow];
			
			int worldX = worldCol * gamePanel.getScaledTileSize();
			int worldY = worldRow * gamePanel.getScaledTileSize();
			int screenX = worldX - gamePanel.getPlayer().worldX_pos + gamePanel.getPlayer().screenX;
			int screenY = worldY - gamePanel.getPlayer().worldY_pos + gamePanel.getPlayer().screenY;
			
			
			if (worldX + gamePanel.getScaledTileSize() > gamePanel.getPlayer().worldX_pos - gamePanel.getPlayer().screenX && 
				worldX - gamePanel.getScaledTileSize() < gamePanel.getPlayer().worldX_pos + gamePanel.getPlayer().screenX && 
				worldY + gamePanel.getScaledTileSize() > gamePanel.getPlayer().worldY_pos - gamePanel.getPlayer().screenY && 
				worldY - gamePanel.getScaledTileSize() < gamePanel.getPlayer().worldY_pos + gamePanel.getPlayer().screenY) {
				
				for (Tile tile : tileManager.allTiles) {
					if (tile.value == tileNum) {
			            p_g2.drawImage(tile.image, screenX, screenY, gamePanel.getScaledTileSize(), gamePanel.getScaledTileSize(), null);
			            break;
			        }
				}
			}
			
			worldCol++;
			
			if(worldCol == gamePanel.getMaxWorldCol()) {
				worldCol = 0;
				worldRow++;
			}
		}

	}
}
