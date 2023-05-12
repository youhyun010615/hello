package shoot;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Enemy3 {
	
	Image image = new ImageIcon("src/images/enemy3.gif").getImage();
	Image image2 = new ImageIcon("src/images/enemy3 ultimate.gif").getImage();
	int x,y;
	int width = image.getWidth(null);
	int height = image.getHeight(null);
	int hp = 30;
	static boolean isUltimate = false;
	int ultimateattack = 30;
	
	public Enemy3(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void move() {
		this.x -= 2;
	}

	
}
