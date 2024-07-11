package entity;

import java.awt.Rectangle;
import java.util.Random;

import main.GamePanel;

public class NPC extends Entity{

	public NPC(GamePanel gamePanel, String character) {
		super(gamePanel);
		
		solidArea = new Rectangle(0, 0, 32 * gamePanel.scale, 48 * gamePanel.scale);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		speed = 1;
		direction = "down";
		
		getCharacterImage(character);
		setDialogue();
		
	}
	
	public void setAction() {
		Random random = new Random();
		actionCooldownCounter++;
		
		if(actionCooldownCounter == 120) {
			int i = random.nextInt(100) + 1;
			
			if(i <= 25) {
				direction = "up";
			}
			if(i > 25 && i <= 50) {
				direction = "down";
			}
			if(i > 50 && i <= 75) {
				direction = "left";
			}
			if(i > 75 && i <= 100) {
				direction = "left";
			}
			
			spriteNum = 1;
			actionCooldownCounter = 0;
		}
		
	}
	
	public void setDialogue() {
		dialogues[0] = "Big Duck";
	}
	
	public void startSpeaking() {
		//Do NPC stuff
		
		super.startSpeaking();
	}
	

}
