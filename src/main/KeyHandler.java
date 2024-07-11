package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
		
		if(gamePanel.gameState == gamePanel.playState) {
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
		
		else if(gamePanel.gameState == gamePanel.battleState) {
			if(keyValue == KeyEvent.VK_W) {
			
			}
			if(keyValue == KeyEvent.VK_A) {
				
			}
			if(keyValue == KeyEvent.VK_S) {
				
			}
			if(keyValue == KeyEvent.VK_D) {
			
			}
		}
		
		else if(gamePanel.gameState == gamePanel.dialogueState) {
			if(keyValue == KeyEvent.VK_ENTER) {
				gamePanel.gameState = gamePanel.playState;
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
