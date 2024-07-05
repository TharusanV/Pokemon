package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

	public boolean upPressed;
	public boolean leftPressed;
	public boolean rightPressed;
	public boolean downPressed;
	
	@Override
	public void keyTyped(KeyEvent e) {	
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyValue = e.getKeyCode();
		
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
