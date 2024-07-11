package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Entity {

	GamePanel gamePanel;
	public int worldX_pos;
	public int worldY_pos;
	public int speed;
	
	public BufferedImage up1, up2, up3, up4, left1, left2, left3, left4, right1, right2, right3, right4, down1, down2, down3, down4;
	public String direction = "down";
	public int spriteCounter = 0;
	public int spriteNum = 1;
	
	public Rectangle solidArea;
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collisionOn = false;
	public int actionCooldownCounter = 0;
	
	String dialogues[] = new String[20];
	int dialogueIndex = 0;
	
	public BufferedImage image1, image2, image3;
	public String name;
	public boolean collision = false;
	
	
	public Entity(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	public void getCharacterImage(String character) {
		try {
			up1 = ImageIO.read(getClass().getResourceAsStream("/characters/"+character+"_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/characters/"+character+"_up_2.png"));
			up3 = ImageIO.read(getClass().getResourceAsStream("/characters/"+character+"_up_3.png"));
			up4 = ImageIO.read(getClass().getResourceAsStream("/characters/"+character+"_up_4.png"));

			down1 = ImageIO.read(getClass().getResourceAsStream("/characters/"+character+"_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/characters/"+character+"_down_2.png"));
			down3 = ImageIO.read(getClass().getResourceAsStream("/characters/"+character+"_down_3.png"));
			down4 = ImageIO.read(getClass().getResourceAsStream("/characters/"+character+"_down_4.png"));

			left1 = ImageIO.read(getClass().getResourceAsStream("/characters/"+character+"_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/characters/"+character+"_left_2.png"));
			left3 = ImageIO.read(getClass().getResourceAsStream("/characters/"+character+"_left_3.png"));
			left4 = ImageIO.read(getClass().getResourceAsStream("/characters/"+character+"_left_4.png"));

			right1 = ImageIO.read(getClass().getResourceAsStream("/characters/"+character+"_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/characters/"+character+"_right_2.png"));
			right3 = ImageIO.read(getClass().getResourceAsStream("/characters/"+character+"_right_3.png"));
			right4 = ImageIO.read(getClass().getResourceAsStream("/characters/"+character+"_right_4.png"));
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void setAction() {}
	public void startSpeaking() {
		if(dialogues[dialogueIndex] == null) {
			dialogueIndex = 0;
		}
		gamePanel.getUi().currentDialogue = dialogues[dialogueIndex];
		dialogueIndex++;
		
		switch(gamePanel.getPlayer().direction) {
		case "up":
			direction = "down";
			break;
		case "down":
			direction = "up";
			break;
		case "right":
			direction = "left";
			break;
		case "left":
			direction = "right";
			break;
		}
	}
	
	
	public void update() {
		setAction();
		
		collisionOn = false;
		gamePanel.getCollisionChecker().checkTile(this, gamePanel.getTreeLayer());
		gamePanel.getCollisionChecker().checkTile(this, gamePanel.getBuildingLayer());
		gamePanel.getCollisionChecker().checkObject(this, false);
		gamePanel.getCollisionChecker().checkEntity(this, gamePanel.getNpc());
		gamePanel.getCollisionChecker().checkPlayer(this);
		
		if(collisionOn == false) {
			switch(direction) {
			case "up":
				worldY_pos -= speed;
				break;
			case "down":
				worldY_pos += speed;
				break;
			case "left":
				worldX_pos -= speed;
				break;
			case "right":
				worldX_pos += speed;
				break;
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
	
	public void draw(Graphics2D g2) {
		BufferedImage currentImage = null;
		int screenX = worldX_pos - gamePanel.getPlayer().worldX_pos + gamePanel.getPlayer().screenX;
		int screenY = worldY_pos - gamePanel.getPlayer().worldY_pos + gamePanel.getPlayer().screenY;
		
		if (worldX_pos + gamePanel.getScaledTileSize() > gamePanel.getPlayer().worldX_pos - gamePanel.getPlayer().screenX && 
			worldX_pos - gamePanel.getScaledTileSize() < gamePanel.getPlayer().worldX_pos + gamePanel.getPlayer().screenX && 
			worldY_pos + gamePanel.getScaledTileSize() > gamePanel.getPlayer().worldY_pos - gamePanel.getPlayer().screenY && 
			worldY_pos - gamePanel.getScaledTileSize() < gamePanel.getPlayer().worldY_pos + gamePanel.getPlayer().screenY) {
			
			
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
			
			g2.drawImage(currentImage, screenX, screenY, 32 * 2, 48 * 2, null);
			g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
		}
	}
	
	
}
