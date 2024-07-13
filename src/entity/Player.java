package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import utility.KeyHandler;

public class Player extends Entity {

	private KeyHandler keyHandler;
	
	public final int screenX;
	public final int screenY;
	
	public Player(GamePanel p_gamePanel, KeyHandler p_keyHandler) {
		super(p_gamePanel);
		
		this.keyHandler = p_keyHandler;
		
		screenX = gamePanel.getScreenWidth() / 2 - (gamePanel.getScaledTileSize()/2);
		screenY = gamePanel.getScreenHeight() / 2 - (gamePanel.getScaledTileSize()/2);
		
		solidArea = new Rectangle(4 * gamePanel.getScale(), 24 * gamePanel.getScale(), 24 * gamePanel.getScale(), 20 * gamePanel.getScale());
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		setDefaultValues();
		getCharacterImage("player");
		
	}
	
	public void setDefaultValues() {
		worldX_pos = 480 * gamePanel.getScale();
		worldY_pos = 220 * gamePanel.getScale();
		speed = 4;
		direction = "down";
	}	
	
	public void update() {
		if(keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed || keyHandler.enterPressed) {
			//Set direction
			if(keyHandler.upPressed) {
				direction = "up";
			}
			else if(keyHandler.leftPressed) {
				direction = "left";
			}
			else if(keyHandler.rightPressed) {
				direction = "right";
			}
			else if(keyHandler.downPressed) {
				direction = "down";
			}
			
			//Check tile collision for movement
			collisionOn = false;
			gamePanel.getCollisionChecker().checkTile(this, gamePanel.getTreeLayer());
			gamePanel.getCollisionChecker().checkTile(this, gamePanel.getBuildingLayer());
			//Check object collision
			int objIndex = gamePanel.getCollisionChecker().checkObject(this, true);
			pickUpObject(objIndex);
			//Check NPC collision
			int npcIndex = gamePanel.getCollisionChecker().checkEntity(this, gamePanel.getNpc());
			interactNPC(npcIndex);
			//Check Event
			gamePanel.getEventHandler().checkEvent();
			
			
			
			if(collisionOn == false && keyHandler.enterPressed == false) {
				switch(direction) {
				case "up":worldY_pos -= speed;break;
				case "down":worldY_pos += speed;break;
				case "left":worldX_pos -= speed;break;
				case "right":worldX_pos += speed;break;
				}
			}
			
			gamePanel.getKeyHandler().enterPressed = false;
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
	
	public void pickUpObject(int i) {
		if(i != 999) {
			//Do something when on object
			String objectName = gamePanel.getObj()[i].name;
			
			switch(objectName) {
			case "Door":
				gamePanel.getDialogueUi().showMessage("Testing");
			case "Pokeball":
				
			}
		}
	}
	
	
	public void interactNPC(int i) {
		if(i != 999) {
			if(gamePanel.getKeyHandler().enterPressed) {
				gamePanel.setGameState(gamePanel.getDialogueState());
				gamePanel.getNpc()[i].startSpeaking();
			}
		}
	}
	
	public void draw(Graphics2D p_g2) {
		BufferedImage currentImage = null;
		
		switch(direction) {
	    case "up":
	        if(spriteNum == 1) {
	            currentImage = up1;
	        }
	        if(spriteNum == 2) {
	            currentImage = up2;
	        }
	        if(spriteNum == 3) {
	            currentImage = up3;
	        }
	        if(spriteNum == 4) {
	            currentImage = up4;
	        }
	        break;
	        
	    case "down":
	        if(spriteNum == 1) {
	            currentImage = down1;
	        }
	        if(spriteNum == 2) {
	            currentImage = down2;
	        }
	        if(spriteNum == 3) {
	            currentImage = down3;
	        }
	        if(spriteNum == 4) {
	            currentImage = down4;
	        }
	        break;
	        
	    case "left":
	        if(spriteNum == 1) {
	            currentImage = left1;
	        }
	        if(spriteNum == 2) {
	            currentImage = left2;
	        }
	        if(spriteNum == 3) {
	            currentImage = left3;
	        }
	        if(spriteNum == 4) {
	            currentImage = left4;
	        }
	        break;
	        
	    case "right":
	        if(spriteNum == 1) {
	            currentImage = right1;
	        }
	        if(spriteNum == 2) {
	            currentImage = right2;
	        }
	        if(spriteNum == 3) {
	            currentImage = right3;
	        }
	        if(spriteNum == 4) {
	            currentImage = right4;
	        }
	        break;
		}

		p_g2.drawImage(currentImage, screenX, screenY, 32 * gamePanel.getScale(), 48 * gamePanel.getScale(), null);
		//p_g2.drawImage(currentImage, screenX, screenY, 32, 48, null);
		p_g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
	}
	
}
