package tile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import main.GamePanel;

public class CreateLayer {

	private GamePanel gamePanel; 
	private TileManager tileManager;
	private boolean isASolidLayer = false; 
	public int mapTileNum[][];
	
	public CreateLayer(GamePanel p_gamePanel, TileManager p_tileManager, int p_imageSelect) {
		this.gamePanel = p_gamePanel;
		this.tileManager = p_tileManager;
		
		mapTileNum = new int[gamePanel.getMaxWorldCol()][gamePanel.getMaxWorldRow()];
		
		//Pallet Town
		if(p_imageSelect == 0) {
			tileManager.getTileImage("res/maps/palletTown_Ground.csv", false, "O");
			loadMap("/maps/palletTown_Ground.csv");
		}
		else if(p_imageSelect == 1) {
			this.isASolidLayer = true;
			tileManager.getTileImage("res/maps/palletTown_Tree.csv", true, "O");
			loadMap("/maps/palletTown_Tree.csv");
		}
		else if(p_imageSelect == 2) {
			this.isASolidLayer = true;
			tileManager.getTileImage("res/maps/palletTown_Buildings.csv", true, "O");
			loadMap("/maps/palletTown_Buildings.csv");
		}
		
		//Lab
		else if(p_imageSelect == 3) {
			tileManager.getTileImage("res/maps/lab_Ground.csv", false, "I");
			loadMap("/maps/lab_Ground.csv");
		}
		else if(p_imageSelect == 4) {
			tileManager.getTileImage("res/maps/lab_Decorations.csv", false, "I");
			loadMap("/maps/lab_Decorations.csv");
		}
		else if(p_imageSelect == 5) {
			this.isASolidLayer = true;
			tileManager.getTileImage("res/maps/lab_Objects.csv", true, "I");
			loadMap("/maps/lab_Objects.csv");
		}
		else if(p_imageSelect == 6) {
			this.isASolidLayer = true;
			tileManager.getTileImage("res/maps/lab_Wall.csv", true, "I");
			loadMap("/maps/lab_Wall.csv");
		}
		
	
		//Home F1
		else if(p_imageSelect == 7) {
			tileManager.getTileImage("res/maps/homeF1_Ground.csv", false, "I");
			loadMap("/maps/homeF1_Ground.csv");
		}
		else if(p_imageSelect == 8) {
			tileManager.getTileImage("res/maps/homeF1_Decorations.csv", false, "I");
			loadMap("/maps/homeF1_Decorations.csv");
		}
		else if(p_imageSelect == 9) {
			this.isASolidLayer = true;
			tileManager.getTileImage("res/maps/homeF1_Objects.csv", true, "I");
			loadMap("/maps/homeF1_Objects.csv");
		}
		else if(p_imageSelect == 10) {
			this.isASolidLayer = true;
			tileManager.getTileImage("res/maps/homeF1_Wall.csv", true, "I");
			loadMap("/maps/homeF1_Wall.csv");
		}
		
		//Home F2
		else if(p_imageSelect == 11) {
			tileManager.getTileImage("res/maps/homeF2_Ground.csv", false, "I");
			loadMap("/maps/homeF2_Ground.csv");
		}
		else if(p_imageSelect == 12) {
			tileManager.getTileImage("res/maps/homeF2_Decorations.csv", false, "I");
			loadMap("/maps/homeF2_Decorations.csv");
		}
		else if(p_imageSelect == 13) {
			this.isASolidLayer = true;
			tileManager.getTileImage("res/maps/homeF2_Objects.csv", true, "I");
			loadMap("/maps/homeF2_Objects.csv");
		}
		else if(p_imageSelect == 14) {
			this.isASolidLayer = true;
			tileManager.getTileImage("res/maps/homeF2_Wall.csv", true, "I");
			loadMap("/maps/homeF2_Wall.csv");
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
			            //p_g2.drawString(Integer.toString(worldCol)+"/"+Integer.toString(worldRow), screenX + 28, screenY + 34);
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

	public boolean isASolidLayer() {
		return isASolidLayer;
	}

	public void setASolidLayer(boolean isASolidLayer) {
		this.isASolidLayer = isASolidLayer;
	}
	
	
}
