package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Entity {

	public int worldX_pos;
	public int worldY_pos;
	public int speed;
	
	public BufferedImage up1, up2, up3, up4, left1, left2, left3, left4, right1, right2, right3, right4, down1, down2, down3, down4;
	public String direction;
	public int spriteCounter = 0;
	public int spriteNum = 1;
	
	public Rectangle solidArea;
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collisionOn = false;
	
	public void getCharacterImage(String character) {
		try {
			up1 = ImageIO.read(getClass().getResourceAsStream("/"+character+"/"+character+"_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/"+character+"/"+character+"_up_2.png"));
			up3 = ImageIO.read(getClass().getResourceAsStream("/"+character+"/"+character+"_up_3.png"));
			up4 = ImageIO.read(getClass().getResourceAsStream("/"+character+"/"+character+"_up_4.png"));

			down1 = ImageIO.read(getClass().getResourceAsStream("/"+character+"/"+character+"_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/"+character+"/"+character+"_down_2.png"));
			down3 = ImageIO.read(getClass().getResourceAsStream("/"+character+"/"+character+"_down_3.png"));
			down4 = ImageIO.read(getClass().getResourceAsStream("/"+character+"/"+character+"_down_4.png"));

			left1 = ImageIO.read(getClass().getResourceAsStream("/"+character+"/"+character+"_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/"+character+"/"+character+"_left_2.png"));
			left3 = ImageIO.read(getClass().getResourceAsStream("/"+character+"/"+character+"_left_3.png"));
			left4 = ImageIO.read(getClass().getResourceAsStream("/"+character+"/"+character+"_left_4.png"));

			right1 = ImageIO.read(getClass().getResourceAsStream("/"+character+"/"+character+"_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/"+character+"/"+character+"_right_2.png"));
			right3 = ImageIO.read(getClass().getResourceAsStream("/"+character+"/"+character+"_right_3.png"));
			right4 = ImageIO.read(getClass().getResourceAsStream("/"+character+"/"+character+"_right_4.png"));
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
