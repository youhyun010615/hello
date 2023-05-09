package shoot;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Enemy3Attack {
	
	Image image = new ImageIcon("src/images/enemy3_attack.png").getImage();
	int x,y;
	int width = image.getWidth(null);
	int height = image.getHeight(null);
	int attack = 5;
	
	public Enemy3Attack(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void fire() {
		this.x -= 20;
		this.y -= Math.random()*40;
		this.y += Math.random()*40;
				
	}

}
