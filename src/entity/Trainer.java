package entity;

import java.awt.Rectangle;
import java.util.Random;

import main.GamePanel;

public class Trainer extends Entity {
	
	private boolean canBattle;
	
	public Trainer(GamePanel gamePanel, String folderName, String character, boolean p_canBattle, String p_startingDirection) {
		super(gamePanel);
		
		speed = 1;
		this.direction = p_startingDirection;
		dialogueSet = -1;
		
		getCharacterImage(folderName, character);
		
		this.canBattle = p_canBattle;
	}
	
	public void update() {
		spriteNum = 1;
	}
	


	public void speak() {
		if(dialogues[0][0] != null) {
			
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
	
	public boolean getCanBattle() {
		return this.canBattle;
	}
	
	public void setCanBattle(boolean p_canBattle) {
		this.canBattle = p_canBattle;
	}
	
}
