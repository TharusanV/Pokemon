package entity;

import java.awt.Rectangle;
import java.util.Random;

import main.GamePanel;

public class NPC extends Entity{
	
	
	
	public NPC(GamePanel gamePanel, String folderName, String character) {
		super(gamePanel);
		
		solidArea = new Rectangle(0, 12, 32 * gamePanel.getScale(), 36 * gamePanel.getScale());
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		speed = 1;
		direction = "down";
		dialogueSet = -1;
		
		getCharacterImage(folderName, character);
		setDialogue();
	}
	
	public void update() {
		setAction();
		
		if(canMove) {
			collisionOn = false;
			gamePanel.getCollisionChecker().checkTile(this, gamePanel.getTreeLayer());
			gamePanel.getCollisionChecker().checkTile(this, gamePanel.getBuildingLayer());
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
	
	public void setDialogue() {
		dialogues[0][0] = "Bill: I  heard  that  Tharusan  guy  \nis  a  decent  programmer.";
		dialogues[0][1] = "Bill: Have  Fun!";
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
