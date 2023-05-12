package shoot;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Enemy3Attack {
	
	Image image = new ImageIcon("src/images/enemy3_attack.gif").getImage();
	int x,y;
	int width = image.getWidth(null);
	int height = image.getHeight(null);
	int attack = 20;
	
	public Enemy3Attack(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void fire() {
		this.x -= 20;
		if(Math.random() <= 0.5)
		this.y -= Math.random()*35;
		else
		this.y += Math.random()*35;
				
	}

}
