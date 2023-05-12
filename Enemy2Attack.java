package shoot;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Enemy2Attack {
	Image image = new ImageIcon("src/images/enemy2_attack.png").getImage();
	int x,y;
	int width = image.getWidth(null);
	int height = image.getHeight(null);
	int attack = 10;
	
	public Enemy2Attack(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void fire() {
		this.x -= 26;
				
	}

}
