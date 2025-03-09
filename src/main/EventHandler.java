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
		    eventRect[col][row].x = 0;
		    eventRect[col][row].y = 0;
		    eventRect[col][row].width = gamePanel.getScaledTileSize(); 
		    eventRect[col][row].height = gamePanel.getScaledTileSize(); 
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
        
        if (canTouchEvent == true) {}
        
        //Pallet Town
    	if(gamePanel.currentMapIndex == 0) {
    		if(activateEvent(20,18,"up") == true ){openDoorEvent("lab");};
    		if(activateEvent(19,12,"up") == true ){openDoorEvent("playerHome");};
    		if(activateEvent(10,12,"up") == true ){openDoorEvent("rivalHome");};
    	}
    	//Lab
    	else if(gamePanel.currentMapIndex == 1) {
    		if(activateEvent(7,14,"down") == true ){openDoorEvent("palletTownOutsideLab");};
    	}
    	//Player Home F1
    	else if(gamePanel.currentMapIndex == 2) {
    		if(activateEvent(4,9,"down") == true ){openDoorEvent("outsidePlayerHome");};
    		if(activateEvent(10,3,"right") == true ){climbStairsEvent("playerHomeF2");};
    	}
    	//Player Home F2
    	else if(gamePanel.currentMapIndex == 3) {
    		if(activateEvent(11,3,"left") == true ){climbStairsEvent("playerHomeF1");};
    	}
    	//Rival Home F1
    	else if(gamePanel.currentMapIndex == 4) {
    		if(activateEvent(4,9,"down") == true ){openDoorEvent("outsideRivalHome");};
    		if(activateEvent(10,3,"right") == true ){climbStairsEvent("rivalHomeF2");};
    	}
    	//Rival Home F2
    	else if(gamePanel.currentMapIndex == 5) {
    		if(activateEvent(11,3,"left") == true ){climbStairsEvent("rivalHomeF1");};
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
	
	public void openDoorEvent(String location) {
		//Pallet Town
		if(gamePanel.currentMapIndex == gamePanel.palletTownIndex) {
			if(location == "lab") {
				gamePanel.currentMapIndex = 1;
				gamePanel.getPlayer().worldX_pos = gamePanel.enterLabPlayerX;
				gamePanel.getPlayer().worldY_pos = gamePanel.enterLabPlayerY;
			}
			else if(location == "playerHome") {
				gamePanel.currentMapIndex = 2;
				gamePanel.getPlayer().worldX_pos = gamePanel.enterHomePlayerX;
				gamePanel.getPlayer().worldY_pos = gamePanel.enterHomePlayerY;
			}
			else if(location == "rivalHome") {
				gamePanel.currentMapIndex = 4;
				gamePanel.getPlayer().worldX_pos = gamePanel.enterHomePlayerX;
				gamePanel.getPlayer().worldY_pos = gamePanel.enterHomePlayerY;
			}
		}
		else if(gamePanel.currentMapIndex == gamePanel.labIndex) {
			if(location == "palletTownOutsideLab") {
				gamePanel.currentMapIndex = 0;
				gamePanel.getPlayer().worldX_pos = gamePanel.exitLabPlayerX;
				gamePanel.getPlayer().worldY_pos = gamePanel.exitLabPlayerY;
			}	
		}
		else if(gamePanel.currentMapIndex == gamePanel.playerHomeF1Index) {
			if(location == "outsidePlayerHome") {
				gamePanel.currentMapIndex = 0;
				gamePanel.getPlayer().worldX_pos = gamePanel.exitPlayerHomePlayerX;
				gamePanel.getPlayer().worldY_pos = gamePanel.exitPlayerHomePlayerY;
			}	
		}
		else if(gamePanel.currentMapIndex == gamePanel.rivalHomeF1Index) {
			if(location == "outsideRivalHome") {
				gamePanel.currentMapIndex = 0;
				gamePanel.getPlayer().worldX_pos = gamePanel.exitRivalHomePlayerX;
				gamePanel.getPlayer().worldY_pos = gamePanel.exitRivalHomePlayerY;
			}	
		}
		
		//gamePanel.setGameState(gameState);
		//gamePanel.getDialogueUi().currentDialogue = "Test";
		//canTouchEvent = false; - //One time so event now can't happen. If you want this uncomment this
	}
	
	public void climbStairsEvent(String location) {
		// Inside Player Home F1
		if(gamePanel.currentMapIndex == gamePanel.playerHomeF1Index) {
			if(location == "playerHomeF2") {
				gamePanel.currentMapIndex = 3;
				gamePanel.getPlayer().worldX_pos = gamePanel.climbStairsHomePlayerX;
				gamePanel.getPlayer().worldY_pos = gamePanel.climbStairsHomePlayerY;
			}	
		}
		// Inside Player Home F2
		else if(gamePanel.currentMapIndex == gamePanel.playerHomeF2Index) {
			if(location == "playerHomeF1") {
				gamePanel.currentMapIndex = 2;
				gamePanel.getPlayer().worldX_pos = gamePanel.decendStairsHomePlayerX;
				gamePanel.getPlayer().worldY_pos = gamePanel.decendStairsHomePlayerY;
			}	
		}	
		// Inside Rival Home F1
		else if(gamePanel.currentMapIndex == gamePanel.rivalHomeF1Index) {
			if(location == "rivalHomeF2") {
				gamePanel.currentMapIndex = 5;
				gamePanel.getPlayer().worldX_pos = gamePanel.climbStairsHomePlayerX;
				gamePanel.getPlayer().worldY_pos = gamePanel.climbStairsHomePlayerY;
			}	
		}
		// Inside Rival Home F2
		else if(gamePanel.currentMapIndex == gamePanel.rivalHomeF2Index) {
			if(location == "rivalHomeF1") {
				gamePanel.currentMapIndex = 4;
				gamePanel.getPlayer().worldX_pos = gamePanel.decendStairsHomePlayerX;
				gamePanel.getPlayer().worldY_pos = gamePanel.decendStairsHomePlayerY;
			}	
		}
	}

}
