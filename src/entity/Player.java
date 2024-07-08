package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {

	private GamePanel gamePanel; 
	private KeyHandler keyHandler;
	
	public final int screenX;
	public final int screenY;
	
	public Player(GamePanel p_gamePanel, KeyHandler p_keyHandler) {
		this.gamePanel = p_gamePanel;
		this.keyHandler = p_keyHandler;
		
		screenX = gamePanel.screenWidth / 2 - (gamePanel.scaledTileSize/2);
		screenY = gamePanel.screenHeight / 2 - (gamePanel.scaledTileSize/2);
		
		solidArea = new Rectangle(4 * gamePanel.scale, 24 * gamePanel.scale, 28 * gamePanel.scale, 24 * gamePanel.scale);
		//solidArea = new Rectangle(4, 24, 28, 24);
		
		setDefaultValues();
		getPlayerImage();
	}
	
	public void setDefaultValues() {
		worldX_pos = 480 * gamePanel.scale;
		worldY_pos = 220 * gamePanel.scale;
		//worldX_pos = 0;
		//worldY_pos = 0;
		speed = 4;
		direction = "down";
	}
	
	public void getPlayerImage() {
		try {
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/player_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/player_up_2.png"));
			up3 = ImageIO.read(getClass().getResourceAsStream("/player/player_up_3.png"));
			up4 = ImageIO.read(getClass().getResourceAsStream("/player/player_up_4.png"));

			down1 = ImageIO.read(getClass().getResourceAsStream("/player/player_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/player_down_2.png"));
			down3 = ImageIO.read(getClass().getResourceAsStream("/player/player_down_3.png"));
			down4 = ImageIO.read(getClass().getResourceAsStream("/player/player_down_4.png"));

			left1 = ImageIO.read(getClass().getResourceAsStream("/player/player_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/player_left_2.png"));
			left3 = ImageIO.read(getClass().getResourceAsStream("/player/player_left_3.png"));
			left4 = ImageIO.read(getClass().getResourceAsStream("/player/player_left_4.png"));

			right1 = ImageIO.read(getClass().getResourceAsStream("/player/player_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/player_right_2.png"));
			right3 = ImageIO.read(getClass().getResourceAsStream("/player/player_right_3.png"));
			right4 = ImageIO.read(getClass().getResourceAsStream("/player/player_right_4.png"));
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	public void update() {
		if(keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed) {
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
			gamePanel.collisionChecker.checkTile(this);
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
		else {
			spriteNum = 1;
		}
	}
	
	public void draw(Graphics2D p_g2) {
		//p_g2.setColor(Color.BLACK);
		//p_g2.fillRect(x_pos, y_pos, gamePanel.scaledTileSize, gamePanel.scaledTileSize); //x, y, w, h	
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

		p_g2.drawImage(currentImage, screenX, screenY, 32 * gamePanel.scale, 48 * gamePanel.scale, null);
		//p_g2.drawImage(currentImage, screenX, screenY, 32, 48, null);
		//p_g2.fill(solidArea);
	}
	
}
