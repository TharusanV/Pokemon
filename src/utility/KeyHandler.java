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
			if(keyValue == KeyEvent.VK_Z) {
				gamePanel.setGameState(gamePanel.getBattleState());
			}
		}
		
		//BATTLE STATE
		else if(gamePanel.getGameState() == gamePanel.getBattleState()) {
			if(keyValue == KeyEvent.VK_ENTER) {
				
			}
			if(keyValue == KeyEvent.VK_W) {
			
			}
			if(keyValue == KeyEvent.VK_A) {
				
			}
			if(keyValue == KeyEvent.VK_S) {
				
			}
			if(keyValue == KeyEvent.VK_D) {
			
			}
			if(keyValue == KeyEvent.VK_Z) {
				gamePanel.setGameState(gamePanel.getPlayState());
			}
		}
		
		//DIALOGUE STATE
		else if(gamePanel.getGameState() == gamePanel.getDialogueState()) {
			if(keyValue == KeyEvent.VK_ENTER) {
				gamePanel.setGameState(gamePanel.getPlayState());
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
