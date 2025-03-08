package tile;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tile {
	public int value;
	public BufferedImage image;
	public boolean collision;
	
	public Tile(int p_value, boolean p_collision, String startingChar) {
		this.value = p_value;
		
		try {
			this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/"+startingChar+"_" + String.valueOf(p_value) + ".png"));
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
		this.collision = p_collision;
	}
	
}
