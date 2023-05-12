package shoot;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Enemy3Attack2 {
	
	Image image = new ImageIcon("src/images/enemy3_attack2.png").getImage();
	int x,y;
	int width = image.getWidth(null);
	int height = image.getHeight(null);
	int attack = 5;
	
	public Enemy3Attack2(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void fire() {
		this.y -= 17;
		this.x -= Math.random()*33;
		this.x += Math.random()*33;
				
	}

}
