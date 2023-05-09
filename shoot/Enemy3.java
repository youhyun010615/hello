package shoot;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Enemy3 {
	
	Image image = new ImageIcon("src/images/enemy3.png").getImage();
	Image image2 = new ImageIcon("src/images/enemy3 ultimate.png").getImage();
	int x,y;
	int width = image.getWidth(null);
	int height = image.getHeight(null);
	int hp = 300;
	static boolean isUltimate = false;
	
	public Enemy3(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void move() {
		this.x -= 2;
	}

	
}
