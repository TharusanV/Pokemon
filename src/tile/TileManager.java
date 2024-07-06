package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {

	GamePanel gamePanel;
	Tile[] tile;
	int mapTileNum[][];
	
	public TileManager(GamePanel p_gamePanel) {
		this.gamePanel = p_gamePanel;
		tile = new Tile[10];
		
		mapTileNum = new int[gamePanel.maxScreenCol][gamePanel.maxScreenRow];
		
		getTileImage();
		loadMap("/maps/palletTown.txt");
	}
	
	public void getTileImage() {
		try {
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass_v1_1.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void loadMap(String p_fileName) {
		try {
			InputStream is = getClass().getResourceAsStream(p_fileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
					
			while(col < gamePanel.maxScreenCol && row < gamePanel.maxScreenRow) {
				String line = br.readLine();
				while(col < gamePanel.maxScreenCol) {
					String numbers[] = line.split(" ");
					int num = Integer.parseInt(numbers[col]);
					mapTileNum[col][row] = num;
					col++;
				}
				if(col == gamePanel.maxScreenCol) {
					col = 0;
					row++;
				}
			}
			br.close();
		
		}catch(Exception e) {
			
		}
	}
	
	public void draw(Graphics2D p_g2) {
		int col = 0;
		int row = 0;
		int x = 0;
		int y = 0;
				
		while(col < gamePanel.maxScreenCol && row < gamePanel.maxScreenRow) {
			
			int tileNum = mapTileNum[col][row];
			
			p_g2.drawImage(tile[tileNum].image, x,y, gamePanel.scaledTileSize, gamePanel.scaledTileSize, null);
			
			col++;
			x += gamePanel.scaledTileSize;
			
			if(col == gamePanel.maxScreenCol) {
				col = 0;
				x = 0;
				row++;
				y += gamePanel.scaledTileSize;
			}
		}

	}
}