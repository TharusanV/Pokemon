package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import battle.Move;
import battle.Pokemon;
import main.GamePanel;
import tile.CreateLayer;

public class Entity {
	
	int selectedPokemonIndex = 0;
	Pokemon currentPokemon = null;

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
	
	public String dialogues[][] = new String[3][5];
	public int dialogueSet = 0;
	public int dialogueIndex = 0;
	
	public BufferedImage image1, image2, image3;
	public String name;
	public boolean collision = false;
	
	public boolean canMove = true;
	
	int assignedMap;
	
	ArrayList<Pokemon> team;
	
	public Entity(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		
		solidArea = new Rectangle(0, 12, 32 * gamePanel.getScale(), 32 * gamePanel.getScale());
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		this.team = new ArrayList<Pokemon>();
	}
	

	public void setAction() {}
	
	public void speak() {}
	
	public void facePlayer() {
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
	
	public void startDialogue(Entity entity, int setNum) {
		gamePanel.setGameState(gamePanel.getDialogueState());
		gamePanel.getDialogueUi().npc = entity;
		dialogueSet = setNum;
		
	}
	
	
	
	public void update() {
		setAction();
		
		collisionOn = false;
		
		for(int i = 0; i < gamePanel.getAllMapLayers().get(gamePanel.currentMapIndex).size(); i++) {
			CreateLayer solidLayer = gamePanel.getAllMapLayers().get(gamePanel.currentMapIndex).get(i);
			
			if(solidLayer.isASolidLayer()) {
				gamePanel.getCollisionChecker().checkTile(this, solidLayer);
			}
		}

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
			//g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
		}
	}
	
	
	public ArrayList<Pokemon> getTeam() {
		return this.team;
	}
	
	public void setTeam(ArrayList<Pokemon> p_team) {
		this.team = p_team;
	}
	
	
	public void setDialogue(String p_dialogues[][]) {
		this.dialogues = p_dialogues;
	}
	
	public int getAssignedMap() {
		return assignedMap;
	}

	public void setAssignedMap(int assignedMap) {
		this.assignedMap = assignedMap;
	}
	
	

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	
	public Pokemon getCurrentPokemon() {
		return currentPokemon;
	}

	public void setCurrentPokemon(int newIndex) {
		this.currentPokemon = team.get(newIndex);
	}
	
	public int getSelectedPokemonIndex() {
		return selectedPokemonIndex;
	}

	public void setSelectedPokemonIndex(int selectedPokemonIndex) {
		setCurrentPokemon(selectedPokemonIndex);
		this.selectedPokemonIndex = selectedPokemonIndex;
	}
	
	
	
	public boolean hasPokemonWithHealth() {
		for(int i = 0; i<team.size(); i++) {
			if(team.get(i).getHealth() > 0) {
				return true;
			}
		}
		
		return false;
	}
	
	public int findFirstMemberWithHealth() {
		for(int i = 0; i<team.size(); i++) {
			if(team.get(i).getHealth() > 0) {
				return i;
			}
		}
		
		return -1;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void getCharacterImage(String folderName, String character) {
		try {
			up1 = ImageIO.read(getClass().getResourceAsStream("/characters/"+folderName+"/"+character+"_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/characters/"+folderName+"/"+character+"_up_2.png"));
			up3 = ImageIO.read(getClass().getResourceAsStream("/characters/"+folderName+"/"+character+"_up_3.png"));
			up4 = ImageIO.read(getClass().getResourceAsStream("/characters/"+folderName+"/"+character+"_up_4.png"));

			down1 = ImageIO.read(getClass().getResourceAsStream("/characters/"+folderName+"/"+character+"_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/characters/"+folderName+"/"+character+"_down_2.png"));
			down3 = ImageIO.read(getClass().getResourceAsStream("/characters/"+folderName+"/"+character+"_down_3.png"));
			down4 = ImageIO.read(getClass().getResourceAsStream("/characters/"+folderName+"/"+character+"_down_4.png"));

			left1 = ImageIO.read(getClass().getResourceAsStream("/characters/"+folderName+"/"+character+"_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/characters/"+folderName+"/"+character+"_left_2.png"));
			left3 = ImageIO.read(getClass().getResourceAsStream("/characters/"+folderName+"/"+character+"_left_3.png"));
			left4 = ImageIO.read(getClass().getResourceAsStream("/characters/"+folderName+"/"+character+"_left_4.png"));

			right1 = ImageIO.read(getClass().getResourceAsStream("/characters/"+folderName+"/"+character+"_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/characters/"+folderName+"/"+character+"_right_2.png"));
			right3 = ImageIO.read(getClass().getResourceAsStream("/characters/"+folderName+"/"+character+"_right_3.png"));
			right4 = ImageIO.read(getClass().getResourceAsStream("/characters/"+folderName+"/"+character+"_right_4.png"));
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
}
