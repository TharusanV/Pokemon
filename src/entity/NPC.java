package entity;

import java.awt.Rectangle;
import java.util.Random;

import main.GamePanel;

public class NPC extends Entity{

	public NPC(GamePanel gamePanel, String folderName, String character) {
		super(gamePanel);
		
		solidArea = new Rectangle(0, 0, 32 * gamePanel.getScale(), 48 * gamePanel.getScale());
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		speed = 1;
		direction = "down";
		dialogueSet = -1;
		
		getCharacterImage(folderName, character);
		setDialogue();
	}
	
	public void setAction() {
		Random random = new Random();
		actionCooldownCounter++;
		
		if(actionCooldownCounter == 180) {
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
		dialogues[0][0] = "Big Duck";
		dialogues[0][1] = "Big Duck2";
	}
	
	public void speak() {
		//Do NPC stuff
		facePlayer();
		startDialogue(this, dialogueSet);
	
		//This code can be removed and changed to fit a certain condition like maybe after a battle switch the dialogue set
		dialogueSet++;
		if(dialogues[dialogueSet][0] == null) {
			dialogueSet = 0; //Reset Dialogue
			//dialogueSet--; //Play last Dialogue set
		}
	}
	

}
