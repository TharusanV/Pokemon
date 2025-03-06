package utility;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import main.GamePanel;

public class KeyHandler implements KeyListener {

	public boolean upPressed;
	public boolean leftPressed;
	public boolean rightPressed;
	public boolean downPressed;
	
	public boolean enterPressed;
	public boolean escapePressed;
	
	private GamePanel gamePanel;
	
	public KeyHandler(GamePanel gp) {
		this.gamePanel = gp;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {	
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyValue = e.getKeyCode();
		
		//PLAY STATE
		if(gamePanel.getGameState() == gamePanel.getPlayState()) {
			if(keyValue == KeyEvent.VK_W) {
				upPressed = true;
			}
			if(keyValue == KeyEvent.VK_A) {
				leftPressed = true;
			}
			if(keyValue == KeyEvent.VK_S) {
				downPressed = true;
			}
			if(keyValue == KeyEvent.VK_D) {
				rightPressed = true;
			}
			if(keyValue == KeyEvent.VK_ENTER) {
				enterPressed = true;
			}
		}
		
		//BATTLE STATE
		else if(gamePanel.getGameState() == gamePanel.getBattleState()) {
			if(keyValue == KeyEvent.VK_ENTER) {
				enterPressed = true;
			}
			else if(keyValue == KeyEvent.VK_ESCAPE) {
				escapePressed = true;
			}
			else if(keyValue == KeyEvent.VK_W) {
				if(gamePanel.getBattleUi().currentCommand == 2) {
					gamePanel.getBattleUi().currentCommand = 0;
				}
				if(gamePanel.getBattleUi().currentCommand == 3) {
					gamePanel.getBattleUi().currentCommand = 1;
				}
			}
			else if(keyValue == KeyEvent.VK_A) {
				if(gamePanel.getBattleUi().currentCommand == 1) {
					gamePanel.getBattleUi().currentCommand = 0;
				}
				if(gamePanel.getBattleUi().currentCommand == 3) {
					gamePanel.getBattleUi().currentCommand = 2;
				}
			}
			else if(keyValue == KeyEvent.VK_S) {
				if(gamePanel.getBattleUi().currentCommand == 0) {
					gamePanel.getBattleUi().currentCommand = 2;
				}
				if(gamePanel.getBattleUi().currentCommand == 1) {
					gamePanel.getBattleUi().currentCommand = 3;
				}
			}
			else if(keyValue == KeyEvent.VK_D) {
				if(gamePanel.getBattleUi().currentCommand == 0) {
					gamePanel.getBattleUi().currentCommand = 1;
				}
				if(gamePanel.getBattleUi().currentCommand == 2) {
					gamePanel.getBattleUi().currentCommand = 3;
				}
			}
			
		}
		
		//DIALOGUE STATE
		else if(gamePanel.getGameState() == gamePanel.getDialogueState()) {
			if(keyValue == KeyEvent.VK_ENTER) {
				//gamePanel.setGameState(gamePanel.getPlayState());
				enterPressed = true;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyValue = e.getKeyCode();
		
		if(keyValue == KeyEvent.VK_W) {
			upPressed = false;
		}
		if(keyValue == KeyEvent.VK_A) {
			leftPressed = false;
		}
		if(keyValue == KeyEvent.VK_S) {
			downPressed = false;
		}
		if(keyValue == KeyEvent.VK_D) {
			rightPressed = false;
		}
	}

}
