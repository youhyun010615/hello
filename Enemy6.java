package shoot;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Enemy6 {
	Image image = new ImageIcon("src/images/enemy6.png").getImage();
	int x,y;
	int width = image.getWidth(null);
	int height = image.getHeight(null);
	int hp = 600;
	
	public Enemy6(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void move() {
		this.x -= 2;
	}


}
