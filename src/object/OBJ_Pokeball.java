package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class OBJ_Pokeball extends Entity{
	
	public OBJ_Pokeball(GamePanel gp) {
		super(gp);
		
		name = "Pokeball";
		try {
			down1 = ImageIO.read(getClass().getResourceAsStream("/characters/player_up_1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
