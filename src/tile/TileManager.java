package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {

	GamePanel gamePanel;
	
	public ArrayList<Tile> allTiles;
	
	public TileManager(GamePanel p_gamePanel) {
		this.gamePanel = p_gamePanel;
		
		allTiles = new ArrayList<Tile>();
	}
	
	public void getTileImage(String csvFile, boolean solid, String startingChar) {
		ArrayList<Integer> valuesList = new ArrayList<>();
		String line;
		
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                for (String value : values) {
                    try {
                        int intValue = Integer.parseInt(value.trim());
                        if (!valuesList.contains(intValue)) {
                            valuesList.add(intValue);
                        }
                    } catch (NumberFormatException e) {
                        // Handle the case where value is not an integer
                        System.out.println("Skipping invalid integer: " + value);
                    }
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        for (int value : valuesList) {
        	allTiles.add(new Tile(value, solid, startingChar));
        }
	}
	

	
}