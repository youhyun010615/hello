package shoot;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Enemy6Attack {
	Image image = new ImageIcon("src/images/enemy6_attack.gif").getImage();
	int x,y;
	int width = image.getWidth(null);
	int height = image.getHeight(null);
	int attack = 20;
	
	public Enemy6Attack(int x, int y) {
		this.x = x;
		this.y = y;
	}

}
