package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.imageio.ImageIO;

import battle.Move;
import main.GamePanel;

public class Trainer extends Entity {
	
	private boolean canBattle;
	private String name;
	
	BufferedImage barIcon;
	BufferedImage barIconSilhouette;
	BufferedImage frontIcon;
	BufferedImage bar;
	
	public Trainer(GamePanel gamePanel, String folderName, String character, String p_name, boolean p_canBattle, String p_startingDirection) {
		super(gamePanel);
		
		speed = 1;
		this.direction = p_startingDirection;
		dialogueSet = -1;
		
		getCharacterImage(folderName, character);
		
		this.canBattle = p_canBattle;
		this.setName(p_name);
		
		loadTrainerImages(character);
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
	
	
	public int selectMoveIndex() {
		ArrayList<Integer> scoresPerMove = new ArrayList<Integer>();
			
		for(int i = 0; i<team.get(0).getAttacks().size(); i++) {
			Move move = team.get(0).getAttacks().get(i);
			
			if(move.getCurrentPP() > 0) {
				scoresPerMove.add(i);
			}
		}
		
		if (scoresPerMove.isEmpty()) {
            return -1; 
        }
		
		Random random = new Random();
		int randomIndex = random.nextInt(scoresPerMove.size());
		return scoresPerMove.get(randomIndex);
	}
	
	
	
	
	public boolean getCanBattle() {
		return this.canBattle;
	}
	
	public void setCanBattle(boolean p_canBattle) {
		this.canBattle = p_canBattle;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
	public BufferedImage getBarIcon() {
		return barIcon;
	}

	public void setBarIcon(BufferedImage barIcon) {
		this.barIcon = barIcon;
	}

	public BufferedImage getBarIconSilhouette() {
		return barIconSilhouette;
	}

	public void setBarIconSilhouette(BufferedImage barIconSilhouette) {
		this.barIconSilhouette = barIconSilhouette;
	}

	public BufferedImage getFrontIcon() {
		return frontIcon;
	}

	public void setFrontIcon(BufferedImage frontIcon) {
		this.frontIcon = frontIcon;
	}
	
	public BufferedImage getBar() {
		return bar;
	}

	public void setBar(BufferedImage bar) {
		this.bar = bar;
	}

	private void loadTrainerImages(String name) {
		try {
			barIcon = ImageIO.read(getClass().getResourceAsStream("/battleIcons/"+name+"_icon.png"));
			barIconSilhouette = ImageIO.read(getClass().getResourceAsStream("/battleIcons/"+name+"_icon_silhouette.png"));
			
			frontIcon = ImageIO.read(getClass().getResourceAsStream("/battleTrainers/"+name+"_front.png"));
			
			bar = ImageIO.read(getClass().getResourceAsStream("/battleBars/"+name+"_bar.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
