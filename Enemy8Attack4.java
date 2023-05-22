package shoot;

import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;

public class Enemy8Attack4 {
    Image image = new ImageIcon("src/images/enemy8_attack4.png").getImage();
    int x, y;
    int width = image.getWidth(null);
    int height = image.getHeight(null);
    int attack = 30;

    public Enemy8Attack4(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void fire() {
    	this.x -= 15;
        this.y += 15;
    }
}
