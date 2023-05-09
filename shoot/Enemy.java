package shoot;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Enemy {
	Image image = new ImageIcon("src/images/enemy.png").getImage();
	int x,y;
	int width = image.getWidth(null);
	int height = image.getHeight(null);
	int hp = 30;;
	static boolean isMove = true;
	
	public Enemy(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void move() {
		if(isMove == true)
		this.x -= 2;
		else
		this.x -= 0;
	}

}
