package main;

import java.awt.Rectangle;

public class EventHandler {

	GamePanel gamePanel;
	EventRect eventRect[][];
	
	int previousEventX, previousEventY;
	boolean canEventRestart = true;
	
	public EventHandler(GamePanel gp) {
		this.gamePanel = gp;
		
		eventRect = new EventRect[gamePanel.getMaxWorldCol()][gamePanel.getMaxWorldRow()];
		
		int col = 0;
		int row = 0;
		
		while(col < gamePanel.getMaxWorldCol() && row < gamePanel.getMaxWorldRow()) {
		    eventRect[col][row] = new EventRect();
		    eventRect[col][row].x = 23;
		    eventRect[col][row].y = 23;
		    eventRect[col][row].width = 2;
		    eventRect[col][row].height = 2;
		    eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
		    eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;
		    
		    col++;
		    if(col == gamePanel.getMaxWorldCol()) {
		        col = 0;
		        row++;
		    }
		}		
		
	}
	
	public void checkEvent() {
		//Checks if the player is more than 1 tile away from the last event to restart it
        int xDistance = Math.abs(gamePanel.getPlayer().worldX_pos - previousEventX);
        int yDistance = Math.abs(gamePanel.getPlayer().worldY_pos - previousEventY);
        int distance = Math.max(xDistance, yDistance);

        if (distance > gamePanel.getScaledTileSize()) {
            canEventRestart = true;
        }
		if(canEventRestart == true) {
			if(activateEvent(10,10, "up") == true) {	
				redoEventExample(10, 10, gamePanel.getDialogueState());
			}
		}
        
        //For one time events
		if(activateEvent(30,30, "up") == true) {
			oneTimeEventExample(30, 30, gamePanel.getDialogueState());
		}
	}
	
	public boolean activateEvent(int col, int row, String reqDirection) {
		boolean hit = false;
		
		gamePanel.getPlayer().solidArea.x = gamePanel.getPlayer().worldX_pos + gamePanel.getPlayer().solidArea.x; 
		gamePanel.getPlayer().solidArea.y = gamePanel.getPlayer().worldY_pos + gamePanel.getPlayer().solidArea.y; 
		eventRect[col][row].x = col*gamePanel.getScaledTileSize() + eventRect[col][row].x;
		eventRect[col][row].y = row*gamePanel.getScaledTileSize() + eventRect[col][row].y;
		
		if (gamePanel.getPlayer().solidArea.intersects(eventRect[col][row]) && eventRect[col][row].eventDone == false){
			if(gamePanel.getPlayer().direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
				hit = true;
				
				previousEventX = gamePanel.getPlayer().worldX_pos; 
				previousEventY = gamePanel.getPlayer().worldY_pos;
			}
		}
		
		gamePanel.getPlayer().solidArea.x = gamePanel.getPlayer().solidAreaDefaultX;
		gamePanel.getPlayer().solidArea.y = gamePanel.getPlayer().solidAreaDefaultY;
		eventRect[col][row].y = eventRect[col][row].eventRectDefaultX;
		eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;
		
		return hit;
	}
	
	public void oneTimeEventExample(int col, int row, int switchedState) {
		//Do event stuff
		//gamePanel.gameState = switchedState;
		
		//One time so event now can't happen
		eventRect[col][row].eventDone = true;
	}
	
	public void redoEventExample(int col, int row, int switchedState) {
		//Do event stuff
		//gamePanel.gameState = switchedState;
		
		//One time so event now can't happen
		canEventRestart = false;
	}
}
