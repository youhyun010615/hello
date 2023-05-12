package shoot;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Enemy3Attack3 {
	
	Image image = new ImageIcon("src/images/enemy3_attack3.gif").getImage();
	int x,y;
	int width = image.getWidth(null);
	int height = image.getHeight(null);
	double attack = 0.5;
	
	public Enemy3Attack3(int x, int y) {
		this.x = x;
		this.y = y;
	}

}
