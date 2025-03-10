package jUnit;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.KeyEvent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.GamePanel;
import utility.KeyHandler;

class InputHandlingTests {

	private GamePanel gp = new GamePanel();
	private KeyHandler keyListener = gp.getKeyHandler();
	
	@BeforeEach
    void setUp() { 
		gp.setGameState(gp.getPlayState());
    }
	
	 @Test
	 void testEnterKey() {
		// Simulate pressing the Enter key
	    KeyEvent enterKeyEvent = new KeyEvent(
	    		new java.awt.TextField(),  // Source component
	            KeyEvent.KEY_PRESSED,      // Event type
	            System.currentTimeMillis(),
	            0,                         // No modifiers (Ctrl, Shift, etc.)
	            KeyEvent.VK_ENTER,         // Key code for Enter
	            '\n'                       // Character representation of Enter
	    );
		 
	    keyListener.keyPressed(enterKeyEvent);
		 
	    assertEquals(keyListener.enterPressed, true);
	 }
	 
	
	 @Test
	 void testWKey() {
			// Simulate pressing the W key
		    KeyEvent wPressKeyEvent = new KeyEvent(
		    		new java.awt.TextField(), 
		            KeyEvent.KEY_PRESSED,      
		            System.currentTimeMillis(),
		            0,                         
		            KeyEvent.VK_W,       
		            '\n'                     
		    );
			 
		    keyListener.keyPressed(wPressKeyEvent);
			 
		    assertEquals(keyListener.upPressed, true);
		    
		    // Simulate release the W key
		    KeyEvent wReleaseKeyEvent = new KeyEvent(
		    		new java.awt.TextField(), 
		            KeyEvent.KEY_RELEASED,      
		            System.currentTimeMillis(),
		            0,                         
		            KeyEvent.VK_W,       
		            '\n'                     
		    );
			 
		    keyListener.keyReleased(wReleaseKeyEvent);
			 
		    assertEquals(keyListener.upPressed, false);
	 }
	 
	 @Test
	 void testAKey() {
	     // Simulate pressing the A key
	     KeyEvent aPressKeyEvent = new KeyEvent(
	             new java.awt.TextField(), 
	             KeyEvent.KEY_PRESSED,      
	             System.currentTimeMillis(),
	             0,                         
	             KeyEvent.VK_A,       
	             'a'                      
	     );

	     keyListener.keyPressed(aPressKeyEvent);
	     assertEquals(keyListener.leftPressed, true);

	     // Simulate releasing the A key
	     KeyEvent aReleaseKeyEvent = new KeyEvent(
	             new java.awt.TextField(), 
	             KeyEvent.KEY_RELEASED,      
	             System.currentTimeMillis(),
	             0,                         
	             KeyEvent.VK_A,       
	             'a'                      
	     );

	     keyListener.keyReleased(aReleaseKeyEvent);
	     assertEquals(keyListener.leftPressed, false);
	 }

	 @Test
	 void testSKey() {
	     // Simulate pressing the S key
	     KeyEvent sPressKeyEvent = new KeyEvent(
	             new java.awt.TextField(), 
	             KeyEvent.KEY_PRESSED,      
	             System.currentTimeMillis(),
	             0,                         
	             KeyEvent.VK_S,       
	             's'                      
	     );

	     keyListener.keyPressed(sPressKeyEvent);
	     assertEquals(keyListener.downPressed, true);

	     // Simulate releasing the S key
	     KeyEvent sReleaseKeyEvent = new KeyEvent(
	             new java.awt.TextField(), 
	             KeyEvent.KEY_RELEASED,      
	             System.currentTimeMillis(),
	             0,                         
	             KeyEvent.VK_S,       
	             's'                      
	     );

	     keyListener.keyReleased(sReleaseKeyEvent);
	     assertEquals(keyListener.downPressed, false);
	 }

	 @Test
	 void testDKey() {
	     // Simulate pressing the D key
	     KeyEvent dPressKeyEvent = new KeyEvent(
	             new java.awt.TextField(), 
	             KeyEvent.KEY_PRESSED,      
	             System.currentTimeMillis(),
	             0,                         
	             KeyEvent.VK_D,       
	             'd'                      
	     );

	     keyListener.keyPressed(dPressKeyEvent);
	     assertEquals(keyListener.rightPressed, true);

	     // Simulate releasing the D key
	     KeyEvent dReleaseKeyEvent = new KeyEvent(
	             new java.awt.TextField(), 
	             KeyEvent.KEY_RELEASED,      
	             System.currentTimeMillis(),
	             0,                         
	             KeyEvent.VK_D,       
	             'd'                      
	     );

	     keyListener.keyReleased(dReleaseKeyEvent);
	     assertEquals(keyListener.rightPressed, false);
	 }


}
