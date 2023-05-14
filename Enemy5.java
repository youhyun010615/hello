package shoot;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Enemy5 {
    Image image;
    int x;
    int y;
    int width;
    int height;
    int hp;
    int velocityX; // ���� �ӵ�
    int moveDuration; // ������ ���� �ð� (������ ��)
    int stopDuration; // ���� ���� �ð� (������ ��)
    int patternTimer; // ���� Ÿ�̸�

    public Enemy5(int x, int y) {
        image = new ImageIcon("src/images/enemy5.png").getImage();
        width = image.getWidth(null);
        height = image.getHeight(null);
        hp = 100;
        this.x = x;
        this.y = y;
        velocityX = 10; // ���� �ӵ� ����
        moveDuration = 60; // ������ ���� �ð� (������ ��)
        stopDuration = 30; // ���� ���� �ð� (������ ��)
        patternTimer = 0; // ���� Ÿ�̸� �ʱ�ȭ
    }

    public void move() {
        // ���� Ÿ�̸� ����
        patternTimer++;

        // ���� Ÿ�̸ӿ� ���� ������ �Ǵ� ����
        if (patternTimer <= moveDuration) {
            // ������ ����
            x -= velocityX; // ���� ���� �̵�
        } else if (patternTimer > moveDuration + stopDuration) {
            // ���� ����
            patternTimer = 0; // ���� Ÿ�̸� �ʱ�ȭ
        }
    }
}
