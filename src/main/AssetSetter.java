package main;

import entity.NPC;
import entity.Trainer;

public class AssetSetter {
	GamePanel gamePanel;
	
	public AssetSetter(GamePanel gp) {
		this.gamePanel = gp;
	}
	
	public void setObject() {
		//gamePanel.obj[0] = new OBJ_Pokeball();
		//gamePanel.obj[0].worldX = 4 * gamePanel.scaledTileSize;
		//gamePanel.obj[0].worldX = 4 * gamePanel.scaledTileSize;
		
		//gamePanel.obj[1] = new OBJ_Door();
		//gamePanel.obj[1].worldX = 6 * gamePanel.scaledTileSize;
		//gamePanel.obj[1].worldX = 2 * gamePanel.scaledTileSize;
	}
	
	public void setNPC() {
		gamePanel.getNpc()[0] = new NPC(gamePanel, "FatGuy", "NPC_06");
		gamePanel.getNpc()[0].worldX_pos = 600 * gamePanel.scale;
		gamePanel.getNpc()[0].worldY_pos = 645 * gamePanel.scale;
		String bills_dialogues[][] = new String[20][20];
		bills_dialogues[0][0] = "Bill: I  heard  that  Tharusan  guy  \nis  a  decent  programmer.";
		bills_dialogues[0][1] = "Bill: Have  Fun!";
		gamePanel.getNpc()[0].setDialogue(bills_dialogues);
		
		gamePanel.getNpc()[1] = new Trainer(gamePanel, "RivalCharacter", "RIVAL", "Job Search", true, "right");
		gamePanel.getNpc()[1].worldX_pos = 530 * gamePanel.scale;
		gamePanel.getNpc()[1].worldY_pos = 550 * gamePanel.scale;
		String rival_dialogues[][] = new String[20][20];
		rival_dialogues[0][0] = "JobSearch: You want a job?";
		gamePanel.getNpc()[1].setDialogue(rival_dialogues);
	}
}
