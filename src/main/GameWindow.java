package main;

import java.awt.Color;

import javax.swing.JFrame;

//The frame
public class GameWindow {
	private JFrame jFrame;
	
	public GameWindow(GamePanel p_gamePanel) {
		jFrame = new JFrame();

		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setResizable(false);
		jFrame.setTitle("Pokemon - Tharusan.V");
		
		jFrame.add(p_gamePanel);
		jFrame.pack(); //Will resize the frame to match the size of the panel
		
		jFrame.setLocationRelativeTo(null); //Places the game window in the center of the screen
		jFrame.setVisible(true);
		
		p_gamePanel.setUpGame();
		p_gamePanel.startGameThread();
		
	}

}
