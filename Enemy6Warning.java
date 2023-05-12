package shoot;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Enemy6Warning {
	Image image = new ImageIcon("src/images/enemy6_warning.png").getImage();
	
	int x,y;
	
	public Enemy6Warning(int x, int y) {
		this.x = x;
		this.y = y;
	}

}
