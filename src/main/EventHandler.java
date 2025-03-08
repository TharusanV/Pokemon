package main;

import java.awt.Rectangle;

public class EventHandler {

	GamePanel gamePanel;
	EventRect eventRect[][];
	
	int previousEventX, previousEventY;
	boolean canTouchEvent = true;
	
	public EventHandler(GamePanel gp) {
		this.gamePanel = gp;
		
		eventRect = new EventRect[gamePanel.getMaxWorldCol()][gamePanel.getMaxWorldRow()]; //Now we got an EventRect on every tile with it being 50x50
		
		int col = 0;
		int row = 0;
		
		//This while loop creates the eventRect on each tile and stores it in the array
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
            canTouchEvent = true;
        }
        
        if (canTouchEvent == true) {
        	//Pallet Town
        	if(gamePanel.currentMapIndex == 0) {
        		if(activateEvent(6,6,"up") == true ){openDoorEvent(6,6,gamePanel.getDialogueState());};
        	}
        	//Lab
        	
        }
        
        
	}
	
	public boolean activateEvent(int col, int row, String reqDirection) {
		boolean hit = false;
		
		gamePanel.getPlayer().solidArea.x = gamePanel.getPlayer().worldX_pos + gamePanel.getPlayer().solidArea.x; 
		gamePanel.getPlayer().solidArea.y = gamePanel.getPlayer().worldY_pos + gamePanel.getPlayer().solidArea.y; 
		//Getting the eventRects solid area position
		eventRect[col][row].x = col * gamePanel.getScaledTileSize() + eventRect[col][row].x;
		eventRect[col][row].y = row * gamePanel.getScaledTileSize() + eventRect[col][row].y;		
		
		//Checks if the player has collided with the eventRect & if the event is available
		if (gamePanel.getPlayer().solidArea.intersects(eventRect[col][row]) && eventRect[col][row].eventDone == false ){
			//If statement to allow for an event to only occur if its facing a certain direction or if its specifically stated that any is fine
			if(gamePanel.getPlayer().direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
				hit = true;
				
				previousEventX = gamePanel.getPlayer().worldX_pos; 
				previousEventY = gamePanel.getPlayer().worldY_pos;
			}
		}
		
		//Resets player's solid area position with the default
		gamePanel.getPlayer().solidArea.x = gamePanel.getPlayer().solidAreaDefaultX;
		gamePanel.getPlayer().solidArea.y = gamePanel.getPlayer().solidAreaDefaultY;
		//Resets eventRects's solid area position with the default
		eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
		eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;
		
		return hit;
	}
	
	public void openDoorEvent(int col, int row, int gameState) {
		System.out.println("test");
		//gamePanel.setGameState(gameState);
		//gamePanel.getDialogueUi().currentDialogue = "Test";
		//canTouchEvent = false; - //One time so event now can't happen. If you want this uncomment this
	}
	

}
