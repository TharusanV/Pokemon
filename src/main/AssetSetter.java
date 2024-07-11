package main;

import entity.NPC;

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
		gamePanel.getNpc()[0] = new NPC(gamePanel, "NPC_06");
		gamePanel.getNpc()[0].worldX_pos = 600 * gamePanel.scale;
		gamePanel.getNpc()[0].worldY_pos = 645 * gamePanel.scale;
	}
}
