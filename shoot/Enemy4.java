package shoot;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Enemy4 {
	
	Image image = new ImageIcon("src/images/enemy4.png").getImage();
	Image image2 = new ImageIcon("src/images/enemy4 ultimate.png").getImage();
	int x,y;
	int width = image.getWidth(null);
	int height = image.getHeight(null);
	int hp = 30;
	static boolean isUltimate = false;
	
	public Enemy4(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void move() {
		this.x -= 2;
	}

	
}