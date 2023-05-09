package shoot;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Enemy4Attack {
	Image image = new ImageIcon("src/images/enemy4_attack.png").getImage();
	int x,y;
	int width = image.getWidth(null);
	int height = image.getHeight(null);
	int attack = 5;
	
	public Enemy4Attack(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void fire() {
		this.x -= 12; 
				
	}
}
