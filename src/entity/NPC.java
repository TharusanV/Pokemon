package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import main.GamePanel;

public class NPC extends Entity{
	
	
	public NPC(GamePanel gamePanel, String folderName, String character) {
		super(gamePanel);
		
		speed = 1;
		direction = "down";
		dialogueSet = -1;
		
		getCharacterImage(folderName, character);
	}
	
	public void update() {
		setAction();
		
		if(canMove) {
			collisionOn = false;
			gamePanel.getCollisionChecker().checkTile(this, gamePanel.getPalletTownTreeLayer());
			gamePanel.getCollisionChecker().checkTile(this, gamePanel.getPalletTownBuildingLayer());
			gamePanel.getCollisionChecker().checkObject(this, false);
			gamePanel.getCollisionChecker().checkEntity(this, gamePanel.getNpc());
			gamePanel.getCollisionChecker().checkPlayer(this);
			
			if(collisionOn == false) {
				switch(direction) {
				case "up": worldY_pos -= speed; break;
				case "down": worldY_pos += speed; break;
				case "left": worldX_pos -= speed; break;
				case "right": worldX_pos += speed; break;
				}
			}
			
			//Animation
			spriteCounter++;
			if(spriteCounter > 10) {
				if(spriteNum == 1) {
					spriteNum = 2;
				}
				else if(spriteNum == 2) {
					spriteNum = 3;
				}
				else if(spriteNum == 3) {
					spriteNum = 4;
				}
				else if(spriteNum == 4) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		}
		else {
			spriteNum = 1;
		}
		
		
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
		
		if(actionCooldownCounter >0 && actionCooldownCounter <= 120) {
			canMove = true;
		}
		else {
			canMove = false;
		}
		
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
	

}
