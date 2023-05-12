package shoot;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Enemy6Attack2 {
	Image image = new ImageIcon("src/images/enemy6_attack2.png").getImage();
	int x,y;
	int width = image.getWidth(null);
	int height = image.getHeight(null);
	int attack = 30;
	
	public Enemy6Attack2(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void fire() {
		this.x -= 26;
		if(Math.random() <= 0.5)
		this.y -= Math.random()*10 + 30;
		else
		this.y += Math.random()*10 + 30;
				
	}

}
