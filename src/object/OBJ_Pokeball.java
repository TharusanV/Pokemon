package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class OBJ_Pokeball extends Entity{
	
	GamePanel gp;
	
	public OBJ_Pokeball(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = "Pokeball";
		collision = true;
		
		try {
			down1 = ImageIO.read(getClass().getResourceAsStream("/characters/player_up_1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setDialogue();
	}
	
	public void setDialogue() {
		dialogues[0][0] = "This is a PokeBall";
	}
	
	public void interact() {
		startDialogue(this, 0);
	}

}
